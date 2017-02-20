package com.hm.routes

import spray.json.JsString
import spray.routing.HttpService
import spray.json._

/**
  * Created by vishnu on 2/17/17.
  */
trait GroupsHandler extends HttpService{



  /*
  *
  * Json Parameter = {"id":"1",
  * "gName":"demo",
  * "listOfUsers":["1","2","3"]
  * }


   }
  * */
  def createGroup =  post{
    entity(as[String])
    {
      body=>{
        val json=body.parseJson.asJsObject
        val id = json.getFields("id").head.asInstanceOf[JsString].value
        val gName=json.getFields("gName").head.asInstanceOf[JsString].value
        val listOfUsers=json.getFields("").head.asInstanceOf[JsString].value

      }
        complete("group created WIP")

    }
  }

//  def createGroupApi(gName:String,noOfParticipants:Int, listOfUsers:Array[Int])= {
//    val rs=Mysqlclient.executeQuery("insert into group values ("+1+",'"+gName+"','"++"')")
//    rs
//  }


}
