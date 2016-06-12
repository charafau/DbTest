package com.nullpointerbay.dbtest

import java.util.concurrent.Executor

import android.app.Activity
import android.os.{AsyncTask, Bundle, Handler, Looper}
import android.util.Log
import android.view.View
import android.view.View.OnClickListener

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class MainActivity extends Activity with TypedFindView {
  lazy val textview = findView(TR.text)

  lazy val btnAdd = findView(TR.btn_add)
  lazy val btnList = findView(TR.btn_list)

  implicit lazy val Pool = ExecutionContext.fromExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

  lazy val Ui = ExecutionContext.fromExecutor(new Executor {
    private val handler = new Handler(Looper.getMainLooper)

    override def execute(command: Runnable) = handler.post(command)
  })

  private[this] lazy val repository = new Repository(new SlickDatabaseHelper(this))


  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    textview.setText("Hello world, from dbtest")

    btnAdd.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        repository.insertTodo(Todo(title = "one one one"))
      }
    })

    btnList.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = {
        val todos: Future[Seq[TodoEntity]] = repository.fetchAllTodos()

        todos.onComplete {
          case Success(ttodos) => {
            ttodos.foreach(some => Log.d("MainAcivity", some.toString))
          }
          case Failure(fail) => Log.d("MainActivity", fail.getMessage)
        }

      }
    })


  }
}