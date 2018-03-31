package com.devchain.qproxy.converters

import java.nio.charset.StandardCharsets

import org.web3j.abi.datatypes.generated.Bytes32

protected trait StringConverter {

  protected implicit def byteArray2Byte32(bytes: Array[Byte]): Bytes32 = {
    val fitH = if (bytes.length > 32) {
      bytes.take(32)
    } else {
      Array.fill[Byte](32 - bytes.length)(0) ++ bytes
    }
    new Bytes32(fitH)
  }

  implicit def string2Bytes32(value: String): Bytes32 = {
    value.getBytes(StandardCharsets.UTF_8)
  }

  implicit def bytes322String(bytes: Bytes32): String = {
    new String(bytes.getValue.dropWhile(_ == 0), StandardCharsets.UTF_8)
  }

}
