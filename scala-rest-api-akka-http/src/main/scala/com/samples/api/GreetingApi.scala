package com.samples.api

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import com.samples.bean.Greeting
import com.samples.dao.GreetingDao._
import com.samples.util.JsonHelper


/**
  * @author Viral Mehta
  */
object GreetingApi extends JsonHelper {

  import scala.concurrent.ExecutionContext.Implicits.global

  val greetingService = {
    (path("greeting" / IntNumber) & get) { id =>
      onSuccess(findByGreetingId(id)) {
        case Some(greeting) => complete(greeting)
        case None => complete(StatusCodes.NotFound)
      }
    } ~
      (path("greetings") & get) {
        complete(findAllGreetings())
      } ~
      (path("greeting") & post) {
        entity(as[Greeting]) { greeting =>
          complete {
            insertGreeting(greeting).map {
              result => HttpResponse(entity = "Greeting is created successfully")
            }
          }
        }
      } ~
      (path("greeting") & put) {
        entity(as[Greeting]) { greeting =>
          complete {
            updateGreeting(greeting).map {
              result => HttpResponse(entity = "Greeting is updated successfully")
            }
          }
        }
      } ~
      (path("greeting" / IntNumber) & delete) { id =>
        complete {
          deleteGreeting(id).map {
            result => HttpResponse(entity = "Greeting is deleted successfully")
          }
        }
      }
  }
}
