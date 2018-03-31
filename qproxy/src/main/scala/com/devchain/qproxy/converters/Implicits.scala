package com.devchain.qproxy.converters

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Bytes32

object Implicits extends OptConverter
  with DoubleConverter
  with StringConverter
  with HashPublishedEventResponseConverter
  with ArrayConverter {

  private[this] val digest: MessageDigest = MessageDigest.getInstance("SHA-256")

  implicit class StringHasher(val s: String) extends AnyVal {
    def sha256: Bytes32 = {
      digest.digest(s.getBytes(StandardCharsets.UTF_8))
    }
  }

  implicit def string2Address(s: String): Address = new Address(s)

}
