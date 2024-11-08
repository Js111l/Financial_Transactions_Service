package ecom.controller

import akka.http.scaladsl.server.{Directive, Directive1}
import akka.http.scaladsl.server.Directives.optionalHeaderValueByName

class AuthFilter {

//  def requestAuthFilter: Directive[0] = {
//
//  }


  val extractAuthHeader: Directive1[Option[String]] = {
    optionalHeaderValueByName("Authorization")
  }

}
