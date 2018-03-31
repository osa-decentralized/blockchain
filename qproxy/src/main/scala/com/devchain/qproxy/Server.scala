package com.devchain.qproxy

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.scalalogging.StrictLogging
import com.devchain.qproxy.model._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._


import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success, Try}

class Server extends StrictLogging with Config with QuorumSupport
  with HashReqJsonSupport with HashEntityJsonSupport
  with SubscriptionReqJsonSupport with TokenReqJsonSupport
  with MainRegistrySupport with HashPublisherSupport  with OSAtokenSupport {

  implicit val actorSystem: ActorSystem = ActorSystem("QProxyServer")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = actorSystem.dispatcher

  val route: Route =
    pathPrefix("v1") {
      pathPrefix("sc" / Segment) { contractName =>
        pathEndOrSingleSlash {
          post {
            entity(as[HashReq])(hr =>
              publish(
                contractName,
                hr
              ) match {
                case Right(s) =>
                  logger.debug(s"New hash [${hr.hash_tree_root}] successfully published. Hash set in block ${s.getBlockNumberRaw} as part of transaction ${s.getTransactionHash}.")
                  val redirectUrl = s"/v1/callback/${s.getTransactionHash}"
                  complete {
                    HttpResponse(
                      status = StatusCodes.SeeOther,
                      headers = headers.RawHeader("Retry-After", "60000") :: headers.Location(redirectUrl) :: Nil,
                      entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, redirectUrl)
                    )
                  }
                case Left(t) =>
                  logger.error(s"Unable to publish hash. ${t.getMessage}")
                  complete(StatusCodes.FailedDependency, "Unable to publish hash")
              }
            )
          }
        } ~
        pathPrefix("peer" / Segment) { partnerHash =>
          pathPrefix("values" / Segment) { values =>
            pathEndOrSingleSlash {
              get {
                Try(values.toInt) match {
                  case Success(v) =>
                    val oldValues = getTreeHashList(contractName, partnerHash, v)
                    logger.debug(s"Found last ${oldValues.size} hash_tree_root values for contract $contractName and partner $partnerHash: [${oldValues.mkString(",")}]")
                    complete(StatusCodes.OK, oldValues)
                  case Failure(e) => complete(StatusCodes.BadRequest)
                }
              }
            }
          }
        }
      } ~
      pathPrefix("callback" / Segment) { tx =>
        pathEndOrSingleSlash {
          get {
            getTreeHash(tx) match {
              case Right(ent) =>
                logger.debug(s"Found transaction $tx with data $ent")
                complete(StatusCodes.OK, ent)
              case Left(t) =>
                complete(StatusCodes.NotFound)
            }
          }
        }
      }
    } ~
    pathPrefix("v2") {
      pathPrefix("sc"){
        pathEndOrSingleSlash{
          post{
            entity(as[TokenReq])(tr =>
              createContractAlias (tr) match {
                case Right(s) =>
                  logger.debug(s"contract created.")
                  //val redirectUrl = s"/v1/callback/${s.getTransactionHash}"
                  complete {
                    HttpResponse(
                      status = StatusCodes.OK,
                      headers = headers.RawHeader("Retry-After", "60000") :: Nil
                      //entity = HttpEntity(ContentTypes.`text/html(UTF-8)`)
                    )
                  }
                case Left(t) =>
                  logger.error(s"Error on contract publishing. ${t.getMessage}")
                  complete(StatusCodes.FailedDependency, "Error on contract publishing.")
              }
            )
          }
        }
      }~
      pathPrefix("sc" / Segment) { contractName =>
        pathEndOrSingleSlash {
          post {
            entity(as[HashReq])(hr =>
              publishv2(
                contractName,
                hr
              ) match {
                case Right(s) =>
                  logger.debug(s"Hash is published successfully")
                  //val redirectUrl = s"/v1/callback/${s.getTransactionHash}"
                  complete {
                    HttpResponse(
                      status = StatusCodes.SeeOther,
                      headers = headers.RawHeader("Retry-After", "60000")  :: Nil
                      //entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, redirectUrl)
                    )
                  }
                case Left(t) =>
                  logger.error(s"Unable to publish hash. ${t.getMessage}")
                  complete(StatusCodes.FailedDependency, "Unable to publish hash")
              }
            )
          }
        } ~
          pathPrefix("peer" / Segment) { partnerHash =>
            pathPrefix("values" / Segment) { values =>
              pathEndOrSingleSlash {
                get {
                  Try(values.toInt) match {
                    case Success(v) =>
                      val oldValues = getTreeHashList(contractName, partnerHash, v)
                      logger.debug(s"Found last ${oldValues.size} hash_tree_root values for contract $contractName and partner $partnerHash: [${oldValues.mkString(",")}]")
                      complete(StatusCodes.OK, oldValues)
                    case Failure(e) => complete(StatusCodes.BadRequest)
                  }
                }
              }
            }
          }
         } ~
      pathPrefix("sc" / Segment) { contractName =>
        pathPrefix ("subscribe") {
          post {
            entity(as[SubscriptionReq])(sr =>
              subscribe(
                contractName,
                sr
              ) match {
                case Right(s) =>
                  logger.debug(s"Subscribed successfully")
                  complete {
                    HttpResponse(
                      status = StatusCodes.SeeOther
                      //headers = headers.RawHeader("Retry-After", "60000") :: headers.Location(redirectUrl) :: Nil,
                    )
                  }
                case Left(t) =>
                  logger.error(s"Unable to publish hash. ${t.getMessage}")
                  complete(StatusCodes.FailedDependency, "Unable to publish hash")
              }
            )
          }
        }
      }
    }
  private lazy val bf = Http().bindAndHandle(route, "0.0.0.0", config.getInt("qproxy.port"))

  def start(): Unit = {
    bf.isCompleted
    logger.info(s"Server started at 0.0.0.0:${config.getInt("qproxy.port")}")
  }

  def stop(): Unit = {
    bf.flatMap(_.unbind()).onComplete(_ => Await.ready(actorSystem.terminate(), 30.seconds))
  }

  def awaitTermination(): Unit = {
    Await.result(actorSystem.whenTerminated, Duration.Inf)
  }

}
