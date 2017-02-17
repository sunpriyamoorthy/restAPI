package com.hm.routes

import com.hm.connector.Mysqlclient
import spray.routing.HttpService

/**
  * Created by vishnu on 2/17/17.
  */
trait TodoHandler extends HttpService{

  def addToDo=post{

    complete("")
  }

  def insertTodo(message:String,userID:Int)={
    val rs=Mysqlclient.executeQuery("insert into todo(user_id,message) values ('"+userID+",'"+message+"')")
    rs
  }
}
