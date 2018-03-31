package com.devchain.qproxy.model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

final case class TokenReq(vendor: String, retailer: String, osa_price: Double, contract_price_per_day: Double, name: String, kpi_values: Seq[Double], kpi_levels: Seq[Double]) {
  override def toString: String = s"$vendor,$retailer,$osa_price,$contract_price_per_day,$name,$kpi_values,$kpi_levels"
}

trait TokenReqJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val tr = jsonFormat7(TokenReq)
}

