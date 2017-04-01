package com.samples.conf

import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

/**
  * @author Viral Mehta
  */
object DbConf {
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("db")
  val db = dbConfig.db
}
