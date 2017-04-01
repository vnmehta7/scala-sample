package com.samples

/**
  * @author Viral Mehta
  */

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.samples.api.GreetingApi.greetingService
import com.samples.bean.Greeting
import com.samples.dao.GreetingDao

import scala.io.StdIn

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher

    GreetingDao.ddl.onComplete {
      _ =>
        GreetingDao.insertGreeting(Greeting(None, "Hi Mandeep"))
        GreetingDao.insertGreeting(Greeting(None, "Hi Viral"))
        val bindingFuture = Http().bindAndHandle(greetingService, "localhost", 8080)
        println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
        StdIn.readLine() // let it run until user presses return
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ => system.terminate()) // and shutdown when done
    }

  }
}
