package com.samples.dao

import com.samples.bean.Greeting
import com.samples.dao.GreetingDao._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
  * @author Viral Mehta
  */
class GreetingDaoTest extends FunSuite with ScalaFutures with BeforeAndAfter {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))


  test("Add new greeting ") {
    val response = insertGreeting(Greeting(None, "Hi Aarav"))
    whenReady(response) { greetingId =>
      assert(greetingId === Some(5))
    }
  }

  test("Update ") {
    val response = updateGreeting(Greeting(Some(1), "Hi Viral"))
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Delete ") {
    val response = deleteGreeting(4)
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Get For Id 2") {
    val greetings = findByGreetingId(2)
    whenReady(greetings) { res =>
      assert(res.get.text === "Hi Kiran")
    }
  }
}
