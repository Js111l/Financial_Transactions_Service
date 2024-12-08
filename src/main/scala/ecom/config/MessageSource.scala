package ecom.config

import java.util.{Locale, ResourceBundle}

object MessageSource {

  def getMessage(key: String, messageLocale: Locale): String = {
    val locale = messageLocale.toString
    try {
      val resourceBundle = ResourceBundle.getBundle(s"messages_$locale")
      resourceBundle.getString(key)
    } catch {
      case e: Exception => throw new RuntimeException(s"No message for locale $locale")
    }
  }

}