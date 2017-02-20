package com.hm.routes

import spray.json.JsString
import com.hm.connector.Mysqlclient
import spray.routing.HttpService
import spray.json._
import com.hm.connector.Mysqlclient
/**
  * Created by vishnu on 2/17/17.
  */
trait TodoHandler extends HttpService{
  def deleteToDo() = post {
    entity(as[String]){
      body =>{
        val json = body.parseJson.asJsObject
        val todo_id = json.getFields("todo_id").head.asInstanceOf[JsString].value
        if(!deleteTask(todo_id.toInt)){
          complete("delete successful")
        }
        else{
          complete("delete failed")
        }
      }
    }
  }

  def updateTodo() = post {
    entity(as[String]) {
      body => {
        val json = body.parseJson.asJsObject
        val user_id = json.getFields("user_id").head.asInstanceOf[JsString].value
        val message = json.getFields("message").head.asInstanceOf[JsString].value
        if (!updateTodoApi(user_id.toInt, message)) {
          complete("update successful")
        }
        else {
          complete("update failed")
        }
      }
    }

  }


  def updateTodoApi(user_id: Int, message: String) = {
    val rs = Mysqlclient.executeQuery("update todo set message='" + message + "' where user_id='" + user_id + "'")
    rs
  }
  def deleteTask(todo_id:Int) ={
    val rs = Mysqlclient.executeQuery("delete from todo where todo_id= '" + todo_id +"'")
    rs
  }

  def addToDo=post{

    complete("")

  }

  def insertTodo(message:String,userID:Int)={
    val rs=Mysqlclient.executeQuery("insert into todo(user_id,message) values ('"+userID+",'"+message+"')")
    rs
  }
}
