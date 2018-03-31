package com.devchain.qproxy

import com.devchain.qproxy.converters.Implicits._
import com.devchain.qproxy.model.{HashReq, SubscriptionReq, TokenReq}
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.{Bytes32, Bytes8}
import org.web3j.protocol.core.methods.response.TransactionReceipt

trait OSAtokenSupport { _: Config with QuorumSupport with MainRegistrySupport =>

  def createContractAlias (tr: TokenReq): Either[Throwable, TransactionReceipt] = {
    if (!iamBlockMaker) {
      logger.warn(s"Node is NOT a blockmaker! It is not possible to store new value for parameters: [$tr]")
      Left(new Exception("Node is NOT a blockmaker!"))
    } else {
      val kpi_levels: Iterable[Bytes8] = tr.kpi_levels.toIterable
      val kpi_values: Iterable[Bytes8] = tr.kpi_values.toIterable
      //val contract_price_per_day = tr.contract_price_per_day
     // val osa_price: BigInteger = tr.osa_price
      val name= tr.name
      val vendor = tr.vendor
      val retailer = tr.retailer
      val qtm = createTM(Seq(vendor, retailer))
      val TokenContract = OSA_VR_m_1.deploy(quorum, qtm, GAS_PRICE, GAS_LIMIT,
        kpi_levels, kpi_values, tr.contract_price_per_day, tr.osa_price, name,  vendor,  retailer).send()
      if (TokenContract.isValid) {
        Right(mainRegistry.placeNewContract(tr.vendor, retailer, name.sha256, TokenContract.getContractAddress).send())
        //logger.info(s"Deployed new TokenContract instance at address: ${TokenContract.getContractAddress}")
        //Right(newCnt.publishHash(hashReq.hash_tree_root, tv.getOrElse(Bytes8.DEFAULT), mdt.getOrElse(Bytes32.DEFAULT)).send())
      } else {
        logger.error("Unable to deploy new contract for Token movement")
        Left(new Exception("Unable to deploy new contract for Token movement"))
      }
    }
  }
  def subscribe (name: String, hashReq: SubscriptionReq): Either[Throwable, TransactionReceipt] = {
    if (!iamBlockMaker) {
      logger.warn(s"Node is NOT a blockmaker! It is not possible to store new value for parameters: [$name,$hashReq]")
      Left(new Exception("Node is NOT a blockmaker!"))
    } else {
      val hashName = name.sha256
      val days = hashReq.days
      val retailer = hashReq.retailer
      val qtm = createTM(Seq(hashReq.vendor, hashReq.retailer))
      val vendor = hashReq.vendor
          Seq(hashReq.vendor, hashReq.retailer).map(prt => mainRegistry.getContractByName(hashName, prt).send()) match {
            case Address.DEFAULT :: Address.DEFAULT :: Nil =>
              logger.error("Cannot found the token contract for this vendor/retailer.")
              Left(new Exception("Cannot found the token contract for this vendor/retailer."))
            case vCnt :: rCnt :: Nil if vCnt.equals(rCnt) =>
              val tokenCnt = OSA_VR_m_1.load(vCnt.getValue, quorum, qtm, GAS_PRICE, GAS_LIMIT)
              if (tokenCnt.isValid) {
                logger.debug(s"Found existing Token instance at address: ${tokenCnt.getContractAddress}")
                Right(tokenCnt.subscribe_service(vendor, retailer, days).send())
              } else {
                logger.error(s"Unable to load contract at address ${vCnt.getValue} for hash publising")
                Left(new Exception(s"Unable to load contract at address ${vCnt.getValue} for hash publising"))
              }
            case _ =>
              logger.warn(s"Contract name collision detected! [$name,$hashReq]")
              Left(new Exception(s"Contract name collision detected! [$name,$hashReq]"))
          }
      }
    }
  def publishv2(name: String, hashReq: HashReq): Either[Throwable, TransactionReceipt] = {
    if (!iamBlockMaker) {
      logger.warn(s"Node is NOT a blockmaker! It is not possible to store new value for parameters: [$name,$hashReq]")
      Left(new Exception("Node is NOT a blockmaker!"))
    } else {
      val hashName = name.sha256
      val qtm = createTM(Seq(hashReq.vendor, hashReq.retailer))
      val tv: Option[Bytes8] = hashReq.target_value
      val mdt: Option[Bytes32] = hashReq.metric_datetime

      Seq(hashReq.vendor, hashReq.retailer).map(prt => mainRegistry.getContractByName(hashName, prt).send()) match {
        case Address.DEFAULT :: Address.DEFAULT :: Nil =>
          logger.error("Cannot found the token contract for this vendor/retailer.")
          Left(new Exception("Cannot found the token contract for this vendor/retailer."))
        case vCnt :: rCnt :: Nil if vCnt.equals(rCnt) =>
          val tokenCnt = OSA_VR_m_1.load(vCnt.getValue, quorum, qtm, GAS_PRICE, GAS_LIMIT)
          if (tokenCnt.isValid) {
            logger.debug(s"Found existing Token instance at address: ${tokenCnt.getContractAddress}")
            Right(tokenCnt.publishHash(hashReq.hash_tree_root, tv.getOrElse(Bytes8.DEFAULT), mdt.getOrElse(Bytes32.DEFAULT)).send())
          } else {
            logger.error(s"Unable to load contract at address ${vCnt.getValue} for hash publising")
            Left(new Exception(s"Unable to load contract at address ${vCnt.getValue} for hash publising"))
          }
        case _ =>
          logger.warn(s"Contract name collision detected! [$name,$hashReq]")
          Left(new Exception(s"Contract name collision detected! [$name,$hashReq]"))
      }
    }
  }

}

