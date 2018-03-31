package com.devchain.qproxy.model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

final case class HashEntity(hash_tree_root: String, tx_datetime: String, target_value: Option[Double] = None, metric_datetime: Option[String] = None) {
  override def toString: String = s"$hash_tree_root,$tx_datetime${target_value.map("," + _).getOrElse("")}${metric_datetime.map("," + _).getOrElse("")}"
}

trait HashEntityJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val he = jsonFormat4(HashEntity)
}
