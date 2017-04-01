package com.samples.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.samples.bean.Greeting
import spray.json.DefaultJsonProtocol

/**
  * @author Viral Mehta
  */

trait JsonHelper extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val greetingFormat = jsonFormat2(Greeting.apply)

}
