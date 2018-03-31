package com.devchain.qproxy.model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

final case class HashReq(vendor: String, retailer: String, hash_tree_root: String, target_value: Option[Double] = None, metric_datetime: Option[String] = None) {
  override def toString: String = s"$vendor,$retailer,$hash_tree_root${target_value.map("," + _).getOrElse("")}${metric_datetime.map("," + _).getOrElse("")}"
}

trait HashReqJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val hq = jsonFormat5(HashReq)
}