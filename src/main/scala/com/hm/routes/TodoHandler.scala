package com.hm.routes

import com.hm.connector.Mysqlclient
import spray.routing.HttpService
import spray.json._

/**
  * Created by vishnu on 2/17/17.
  */
trait TodoHandler extends HttpService {


  /**
    * Created by pooja on 17/2/17.
    */
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

}
