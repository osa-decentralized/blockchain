package com.devchain.qproxy.converters

import com.devchain.qproxy.HashPublisher.HashPublishedEventResponse
import com.devchain.qproxy.model.HashEntity
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

protected trait HashPublishedEventResponseConverter { _: DoubleConverter with StringConverter =>
  implicit def hper2he(x: HashPublishedEventResponse): HashEntity = {
    val tv: Double = x.targetValue
    val mdt: String = x.metricDatetime
    val txDt = new DateTime(x.txDatetime.getValue.longValueExact() * 1000L, DateTimeZone.UTC)
    HashEntity(
      hash_tree_root = x.hash,
      tx_datetime = txDt.toString(ISODateTimeFormat.dateTime()),
      target_value = if (tv > 0) Some(tv) else None,
      metric_datetime = if (mdt != "") Some(mdt) else None
    )
  }
}
