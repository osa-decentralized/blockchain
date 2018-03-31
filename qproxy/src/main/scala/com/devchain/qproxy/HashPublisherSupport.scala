package com.devchain.qproxy

import com.devchain.qproxy.HashPublisher.HashPublishedEventResponse
import com.devchain.qproxy.converters.Implicits._
import com.devchain.qproxy.model.{HashEntity, HashReq}
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.{Bytes32, Bytes8}
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.compat.java8.OptionConverters._


trait HashPublisherSupport { _: Config with QuorumSupport with MainRegistrySupport =>

  def publish(name: String, hashReq: HashReq): Either[Throwable, TransactionReceipt] = {
    if (!iamBlockMaker) {
      logger.warn(s"Node is NOT a blockmaker! It is not possible to store new value for parameters: [$name,$hashReq]")
      Left(new Exception("Node is NOT a blockmaker!"))
    } else {
      val hashName = name.sha256
      val qtm = createTM(Seq(hashReq.vendor, hashReq.retailer))
      val tv: Option[Bytes8] = hashReq.target_value
      val mdt: Option[Bytes32] = hashReq.metric_datetime

      // vendor           "0xca69c9b549f0c5a339755c9626171106bc21b505"
      // retailer         "0x21f456a417877d125f8ea715e5ff9eb981b3b104"

      Seq(hashReq.vendor, hashReq.retailer).map(prt => mainRegistry.getContractByName(hashName, prt).send()) match {
        case Address.DEFAULT :: Address.DEFAULT :: Nil =>
          val newCnt = HashPublisher.deploy(quorum, qtm, GAS_PRICE, GAS_LIMIT, hashReq.vendor, hashReq.retailer, hashName).send()
          if (newCnt.isValid) {
            mainRegistry.placeNewContract(hashReq.vendor, hashReq.retailer, hashName, newCnt.getContractAddress).send()
            logger.info(s"Deployed new HashPublisher instance at address: ${newCnt.getContractAddress}")
            Right(newCnt.publishHash(hashReq.hash_tree_root, tv.getOrElse(Bytes8.DEFAULT), mdt.getOrElse(Bytes32.DEFAULT)).send())
          } else {
            logger.error("Unable to deploy new contract for hash publising")
            Left(new Exception("Unable to deploy new contract for hash publising"))
          }
        case vCnt :: rCnt :: Nil if vCnt.equals(rCnt) =>
          val hashCnt = HashPublisher.load(vCnt.getValue, quorum, qtm, GAS_PRICE, GAS_LIMIT)
          if (hashCnt.isValid) {
            logger.debug(s"Found existing HashPublisher instance at address: ${hashCnt.getContractAddress}")
            Right(hashCnt.publishHash(hashReq.hash_tree_root, tv.getOrElse(Bytes8.DEFAULT), mdt.getOrElse(Bytes32.DEFAULT)).send())
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

  def getTreeHash(txHash: String): Either[Throwable, HashEntity] = {
    quorum.ethGetTransactionReceipt(txHash).send().getTransactionReceipt.asScala match {
      case Some(txr) =>
        val hashCnt = HashPublisher.load(Address.DEFAULT.getValue, quorum, createTM(), GAS_PRICE, GAS_LIMIT)
        hashCnt.getHashPublishedEvents(txr).asScala.headOption match {
          case Some(hper) =>
            Right(hper)
          case _ =>
            logger.error(s"Unable to find Event in transaction: $txHash")
            Left(new Exception(s"Unable to find Event in transaction: $txHash"))
        }
      case _ =>
        logger.error(s"Unable to find transaction for hash: $txHash")
        Left(new Exception(s"Unable to find transaction for hash: $txHash"))
    }
  }

  def getTreeHashList(name: String, partner: String, count: Int): Iterable[HashEntity] = {
    Option(mainRegistry.getContractByName(name.sha256, partner).send()) match {
      case Some(cntAddr) if !Address.DEFAULT.equals(cntAddr) =>
        val hashCnt = HashPublisher.load(cntAddr.getValue, quorum, createTM(Seq(partner)), GAS_PRICE, GAS_LIMIT)
        if (hashCnt.isValid) {
          logger.debug(s"Found existing HashPublisher instance at address: ${hashCnt.getContractAddress}")
          val oldValues = mutable.ArrayBuffer.empty[HashPublishedEventResponse]
          hashCnt.hashPublishedEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
            .subscribe( t => oldValues += t )
          oldValues.takeRight(count).toIterable
        } else {
          logger.error(s"Unable to load contract at address ${cntAddr.getValue} for hash publising")
          Nil
        }
      case _ =>
        logger.error(s"Unable to find contract for given parameters: [name: $name, partner: $partner]")
        Nil
    }
  }

}
