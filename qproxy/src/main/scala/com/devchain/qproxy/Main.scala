package com.devchain.qproxy

import com.typesafe.scalalogging.StrictLogging

object Main extends StrictLogging {

  def main(args: Array[String]): Unit = {

    val server = new Server()

    sys.ShutdownHookThread {
      logger.info("QProxy is going shutdown!")
      server.stop()
    }

    server.start()
    server.awaitTermination()

  }

}
