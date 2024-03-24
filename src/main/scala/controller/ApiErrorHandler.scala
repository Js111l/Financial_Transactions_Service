package controller

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpEntity, StatusCode, StatusCodes}
import exceptions.LogicalException

object ApiErrorHandler {

  def getHttpEntity(ex: Throwable): ToResponseMarshallable = {
    val response = ex match {
      case logicalEx: LogicalException =>
        generateResponse(logicalEx.code, logicalEx.message)
      case _ =>
        generateResponse(StatusCodes.InternalServerError, "An unexpected error occurred")
    }

    response
  }

  private def generateResponse(statusCode: StatusCode, message: String): (StatusCode, HttpEntity.Strict) = {
    statusCode -> HttpEntity(message)
  }
}