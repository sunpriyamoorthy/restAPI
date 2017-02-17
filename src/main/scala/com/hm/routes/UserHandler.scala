package com.hm.routes

import com.hm.connector.Mysqlclient
import spray.json.{JsArray, JsNumber, JsObject, JsString}
import spray.routing.HttpService
import collection.JavaConversions._
import collection.JavaConverters
/**
  * Created by vishnu on 2/17/17.
  */
trait UserHandler extends HttpService{

  def user=optionalCookie("userName") {
    case Some(nameCookie) => {
      val userId = nameCookie.content.toInt
      val r=userDashBorad(userId)


      complete(JsArray(r.map(i=>JsObject("id"->JsNumber(i._1),"message"->JsString(i._2))).toVector).prettyPrint)
    }
    case None => complete("No user logged in")
  }
  def userDashBorad(userID:Int):Array[(Int,String)]={
    val rs = Mysqlclient.getResultSet("select * from todo where user_id="+userID+"")
    val result=new collection.mutable.ArrayBuffer[(Int,String)]
    while (rs.next())
    {
      result.add((rs.getInt("todo_id"),rs.getString("message")))
    }
    result.toArray

  }
}
