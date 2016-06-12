package com.nullpointerbay.dbtest

import slick.driver.SQLiteDriver.api._
import slick.lifted.ProvenShape

trait SlickDatabaseConfig {

  class TodoTable(tag: Tag) extends Table[TodoEntity](tag, "title"){

    def id = column[Int]("_id", O.PrimaryKey, O.AutoInc)

    def title = column[String]("title")

    def * = (id.?, title) <> (TodoEntity.tupled, TodoEntity.unapply)
  }

  lazy val todoTable = TableQuery[TodoTable]

}
