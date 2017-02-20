package com.hm.routes

import java.util

import com.hm.connector.Mysqlclient
import spray.http.HttpCookie
import spray.http.MediaTypes.`text/html`
import spray.json.JsString
import spray.routing.HttpService
import spray.json._
import collection.JavaConversions._
import collection.JavaConverters

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
/**
  * Created by hari on 17/2/17.
  */
trait Routes extends HttpService
  with TodoHandler
  with GroupsHandler
  with UserHandler
  with AuthenticationHandler {



  val route =


    path("addTodo")
  {
    addToDo
  }~path("login")
  {
    login
  }~path("user")
  {

    user
  }~path("logout")
  {
   logout
  }~path("signup")
  {
      signup

    }~path("createGroup") {
  createGroup
}~path("") {

      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>welcome to Todo :)</h1>
              </body>
            </html>
          }
        }
      }
    }




  def userDashBoard(userID:Int):Array[(Int,String)]= {
    val rs = Mysqlclient.getResultSet("select * from todo where u_id=" + userID + ");")
    val result = new collection.mutable.ArrayBuffer[(Int, String)]
    while (rs.next()) {
      result.add((rs.getInt("todo_id"), rs.getString("message")))
    }
    result.toArray

  }







}
