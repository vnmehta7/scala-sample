package com.samples.api

import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.samples.api.GreetingApi._
import com.samples.bean.Greeting
import com.samples.util.JsonHelper
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}

/**
  * @author Viral Mehta
  */
class GreetingApiTest extends WordSpec with Matchers with ScalatestRouteTest with JsonHelper with BeforeAndAfter {

  "The greeting api" should {
    "get all greetings" in {
      Get("/greetings") ~> greetingService ~> check {
        status shouldBe OK
        contentType shouldBe `application/json`
        responseAs[List[Greeting]].length === 4
      }
    }

    "get specific greeting by id" in {
      Get("/greeting/1") ~> greetingService ~> check {
        println(responseAs[Greeting])
        responseAs[Greeting] shouldEqual Greeting(Some(1), "Hi Mandeep")
      }
    }

    "create new greeting" in {
      Post("/greeting", Greeting(None, "Hi Aarav")) ~> greetingService ~> check {
        responseAs[String] shouldEqual "Greeting is created successfully"
      }
    }

    "update greeting whose id is 2" in {
      Put("/greeting", Greeting(Some(2), "Hi Sneha")) ~> greetingService ~> check {
        responseAs[String] shouldEqual "Greeting is updated successfully"
      }
    }

    "delete greeting whose id is 4" in {
      Delete("/greeting/4") ~> greetingService ~> check {
        responseAs[String] shouldEqual "Greeting is deleted successfully"
      }
    }
  }
}
