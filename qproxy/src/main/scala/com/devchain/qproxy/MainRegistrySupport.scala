package com.devchain.qproxy

trait MainRegistrySupport { _: Config with QuorumSupport =>

  val mainRegistry: MainRegistry =
    MainRegistry.load(
      MainRegistry.getPreviouslyDeployedAddress(config.getString("qproxy.chain.networkId")),
      quorum, createTM(), GAS_PRICE, GAS_LIMIT
    )

  if (!mainRegistry.isValid) {
    logger.error("Unable to load MainRegistry contract")
    throw new Exception("Unable to load MainRegistry contract")
  }

}
