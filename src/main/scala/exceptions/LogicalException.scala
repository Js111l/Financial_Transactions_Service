package exceptions

import akka.http.scaladsl.model.StatusCode

case class LogicalException(code: StatusCode, message: String) extends RuntimeException {

}
