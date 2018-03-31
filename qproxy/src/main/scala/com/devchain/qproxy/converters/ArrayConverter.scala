package com.devchain.qproxy.converters

import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.generated.Bytes8

import scala.collection.JavaConverters._

protected trait ArrayConverter {

  implicit def DynamicArray2ListBytes8(dynamicArray: DynamicArray[Bytes8]): Iterable[Bytes8] = {
    dynamicArray.getValue().asScala
  }

  implicit def List2DynamicArrayBytes8 (list: Iterable[Bytes8]): DynamicArray[Bytes8] = {
    new DynamicArray[Bytes8](list.toBuffer.asJava)
  }

}