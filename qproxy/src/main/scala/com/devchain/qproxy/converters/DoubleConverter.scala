package com.devchain.qproxy.converters

import java.math.BigInteger
import java.nio.ByteBuffer

import org.web3j.abi.datatypes.generated.{Bytes8, Uint256}

protected trait DoubleConverter  {

  private implicit def double2ByteArray(value: Double): Array[Byte] = {
    val bytes: Array[Byte] = Array.fill[Byte](8)(0)
    ByteBuffer.wrap(bytes).putDouble(value)
    bytes
  }

  private implicit def byteArray2Double(bytes: Array[Byte]): Double = {
    val x = ByteBuffer.wrap(bytes)
    if (x.limit() != 8) 0.0 else x.getDouble()
  }

  private implicit def byteArray2Byte8(bytes: Array[Byte]): Bytes8 = {
    val fitH = if (bytes.length > 8) {
      bytes.take(8)
    } else {
      Array.fill[Byte](8 - bytes.length)(0) ++ bytes
    }
    new Bytes8(fitH)
  }

  implicit def double2Bytes8(value: Double): Bytes8 = {
    val h: Array[Byte] = value
    h
  }

  implicit def bytes82Double(bytes: Bytes8): Double = {
    bytes.getValue.dropWhile(_ == 0)
  }

  implicit def Uint2562Double(uint256: Uint256) : Double = {
    uint256.getValue().doubleValue()
  }

  implicit def Double2Uint256(double: Double) : Uint256 = {
    new Uint256(double.longValue())
  }
}
