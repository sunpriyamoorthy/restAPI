package com.hm.routes

import java.util

import com.hm.connector.Mysqlclient
import spray.http.HttpCookie
import spray.http.MediaTypes.`text/html`
import spray.json.JsString
import spray.routing.HttpService
import spray.json._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
/**
  * Created by hari on 17/2/17.
  */
trait Routes extends HttpService{



  val route =
    path("login")
  {
    post{
      entity(as[String])
      {
        body=>{
          val json=body.parseJson.asJsObject
          val u=json.getFields("userName").head.asInstanceOf[JsString].value
          val userName:String=if(json.getFields("userName").nonEmpty){

            json.getFields("userName").head.asInstanceOf[JsString].value
          }
          else
            {
              ""
            }
          val password = if(json.getFields("password").nonEmpty){
            json.getFields("password").head.asInstanceOf[JsString].value
          } else {
            ""
          }
          if(loginCheck(userName,password)._1){
            val uid=loginCheck(userName,password)._2
            setCookie(HttpCookie("userName",content = ""+uid)) {
              complete("User has logged in ")
            }
          }else {
            complete("wrong credentials")
          }
        }
      }

    }
  }~path("user")
  {
    optionalCookie("userName") {
      case Some(nameCookie) => {
        val userId = nameCookie.content.toInt
        userDashBorad(userId)

      
        complete("")
      }
      case None => complete("No user logged in")
    }
  }~path("logout")
  {
   deleteCookie("userName")
    {
      complete("user Logged out")
    }
  }~path("signup")
  {
    post{
      entity(as[String])
      {
        body=>{
          val json=body.parseJson.asJsObject
          val name=json.getFields("name").head.asInstanceOf[JsString].value
          val userName=json.getFields("userName").head.asInstanceOf[JsString].value
          val password=json.getFields("password").head.asInstanceOf[JsString].value
          if(!registerUser(name,userName,password)) {
            complete("signup successful")
          }
          else {
            complete("signup failed")
          }
        }
      }

    }
  }~path("") {
      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    }

  def userDashBorad(userID:Int):util.ArrayList[Array[Any]]={
    val rs = Mysqlclient.getResultSet("select * from todo where u_id="+userID+");");
    val result=new util.ArrayList[Array[Any]]
    while (rs.next())
      {

        result.add(Array(rs.getInt("todo_id"),rs.getString("message")))
      }
      result

  }

  def loginCheck(username:String,password:String)={
    val rs = Mysqlclient.getResultSet("select * from user where user_name='"+username+"' AND password='"+password+"'")
    val response = if(rs.next()){
      (true,rs.getInt("u_id"))

    }else{
      println("Invalid session")
      (false,0)
    }
    rs.close()
    response
  }
  def registerUser(name:String,userName:String,password:String)={
    val rs=Mysqlclient.executeQuery("insert into user(name,user_name,password) values ('"+name+"','"+userName+"','"+password+"')")
    rs
  }


}
