package com.samples.map

import com.samples.bean.Greeting
import com.samples.conf.DbConf.dbConfig.driver.api._

/**
  * @author Viral Mehta
  */
object GreetingMap {

  val greetingMap = TableQuery[GreetingMap]

  class GreetingMap(tag: Tag) extends Table[Greeting](tag, "GREETING") {
    def * = (id, text) <> (Greeting.tupled, Greeting.unapply)

    def id = column[Option[Int]]("ID", O.Length(8), O.PrimaryKey, O.AutoInc)

    def text = column[String]("TEXT", O.Length(36))
  }

}