package com.hm.routes

import com.hm.connector.Mysqlclient
import spray.json.JsString
import spray.routing.HttpService
import spray.json._
/**
  * Created by vishnu on 2/17/17.
  */
trait TodoHandler extends HttpService{

  path("add") {
    post {
      entity(as[String]) {
        body => {
          val json = body.parseJson.asJsObject
          val user_id=json.getFields("user_id").head.asInstanceOf[JsString].value
          val message = json.getFields("message").head.asInstanceOf[JsString].value
          if (!addmessage(user_id.toInt,message)) {

            complete("to do added")
          }else {
            complete("not added")
          }
        }
      }
    }
  }

  def addmessage(user_id:Int,message:String)={
    val rs=Mysqlclient.executeQuery("insert into todo values ('"+user_id+"','"+message+"')")
    rs
  }


}
