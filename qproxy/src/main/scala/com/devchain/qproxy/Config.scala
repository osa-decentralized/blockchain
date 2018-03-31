package com.devchain.qproxy

import java.math.BigInteger
import java.util.Map.Entry

import com.typesafe.config.{ConfigFactory, ConfigObject, ConfigValue}
import com.typesafe.scalalogging.StrictLogging

import scala.collection.JavaConverters._

trait Config extends StrictLogging {

  lazy val config: com.typesafe.config.Config = ConfigFactory.load()

  lazy val GAS_PRICE: BigInteger = if (config.hasPath("qproxy.chain.gasPrice")) BigInteger.valueOf(config.getLong("qproxy.chain.gasPrice")) else BigInteger.valueOf(400000L)
  lazy val GAS_LIMIT: BigInteger = if (config.hasPath("qproxy.chain.gasLimit")) BigInteger.valueOf(config.getLong("qproxy.chain.gasLimit")) else BigInteger.valueOf(400000L)

  lazy val pbKeys: Map[String, String] = {
    val list : Iterable[ConfigObject] = config.getObjectList("qproxy.keys").asScala
    (for {
      item : ConfigObject <- list
      entry : Entry[String, ConfigValue] <- item.entrySet().asScala
      key = entry.getKey
      uri = entry.getValue.unwrapped().toString
    } yield (key, uri)).toMap
  }

}
