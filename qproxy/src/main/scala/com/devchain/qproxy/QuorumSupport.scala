package com.devchain.qproxy

import org.web3j.protocol.admin.Admin
import org.web3j.protocol.http.HttpService
import org.web3j.quorum.Quorum
import org.web3j.quorum.tx.ClientTransactionManager

import scala.collection.JavaConverters._

trait QuorumSupport { _: Config =>

  val web3jService = new HttpService(s"http://${config.getString("qproxy.chain.host")}:${config.getString("qproxy.chain.port")}")
  val quorum: Quorum = Quorum.build(web3jService)
  val admin: Admin = Admin.build(web3jService)
  val defaultAccount: String = admin.personalListAccounts().send().getAccountIds.asScala.headOption.get
  val iamBlockMaker: Boolean = quorum.quorumNodeInfo().send().getNodeInfo.isCanCreateBlocks

  def createTM(): ClientTransactionManager = createTM(Seq.empty[String])

  def createTM(privateFor: Seq[String]): ClientTransactionManager = {
    new ClientTransactionManager(quorum, defaultAccount, privateFor.map(a => pbKeys(a)).asJava)
  }

}
