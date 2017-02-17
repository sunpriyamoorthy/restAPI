package com.hm.routes

import spray.json.JsString
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

  def deleteTask(todo_id:Int) ={
    val rs = Mysqlclient.executeQuery("delete from todo where todo_id= '" + todo_id +"'")
    rs
  }

}
