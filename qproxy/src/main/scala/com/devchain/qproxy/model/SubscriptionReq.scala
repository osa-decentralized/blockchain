package com.devchain.qproxy.model

import java.math.BigInteger

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.web3j.abi.datatypes.generated.Uint256
import spray.json.DefaultJsonProtocol


final case class SubscriptionReq(vendor: String, retailer: String, days:  Double) {
  override def toString: String = s"$vendor,$retailer,$days"
}

trait SubscriptionReqJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val sr = jsonFormat3(SubscriptionReq)
}