package com.nullpointerbay.dbtest

import com.nullpointerbay.dbtest.{Todo, SlickDatabaseHelper}
import slick.driver.SQLiteDriver.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
  * Created by rafal on 6/12/16.
  */
class Repository(helper: SlickDatabaseHelper) {

  def insertTodo(todo: Todo)(implicit executionContext: ExecutionContext): Future[Todo] = {
    helper.database.run(
      for {
        todoId <- (helper.todoTable returning helper.todoTable.map(_.id)) += toTodoEntity(todo)
      } yield todo.copy(id = Some(todoId)))

  }

  def fetchAllTodos()(implicit executionContext: ExecutionContext): Future[Seq[TodoEntity]] = {
    //    val select = for {
    //      todoEntities <- helper.todoTable.map(t => (t.id, t.title))
    //    } yield todoEntities

    //    val query: Query[(Column[Int], Column[String]), (Int, String), Seq] = helper.todoTable.map(t => (t.id, t.title))
    //     helper.database.run(query.result) map fromTodoEntitySeq
    //    helper.database.run(select.result) map fromTodoEntitySeq

    helper.database.run(helper.todoTable.result)


  }

  private def fromTodoEntitySeq(todoEntity: TodoEntity): Todo = Todo(todoEntity.id, todoEntity.title)

  private def toTodoEntity(todo: Todo): TodoEntity = TodoEntity(todo.id, todo.title)
}

