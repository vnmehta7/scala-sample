package com.samples.dao

import com.samples.bean.Greeting
import com.samples.conf.DbConf.db
import com.samples.conf.DbConf.dbConfig.driver.api._
import com.samples.map.GreetingMap.greetingMap
import slick.backend.DatabasePublisher

import scala.concurrent.Future

/**
  * @author Viral Mehta
  */
object GreetingDao {
  implicit val ec = db.ioExecutionContext

  def greetingStream: DatabasePublisher[Greeting] = {
    db stream greetingMap.result
  }

  //This ddl is just used for testing purpose through WebServer class. Otherwise it is not needed
  def ddl: Future[Unit] = {
    val schema = greetingMap.schema
    println(schema.createStatements.mkString(";\n"))
    db.run(schema.create)
  }

  def insertGreeting(greeting: Greeting): Future[Option[Int]] = {
    val insert = greetingMap returning greetingMap.map(_.id) += greeting
    db.run(insert)
  }

  def updateGreeting(greeting: Greeting): Future[Int] = {
    val update = greetingMap.filter(_.id === greeting.id.get).update(greeting)
    db.run(update)
  }

  def deleteGreeting(id: Int): Future[Int] = {
    val delete = greetingMap.filter(_.id === id).delete
    db.run(delete)
  }

  def findByGreetingId(id: Int): Future[Option[Greeting]] = {
    val query = greetingMap.filter(_.id === id).result
    db.run(query.headOption)
  }

  def findAllGreetings(): Future[List[Greeting]] = {
    db.run(greetingMap.to[List].result)
  }
}
