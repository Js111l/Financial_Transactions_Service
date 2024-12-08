package ecom.utils;

import ecom.actors.model.CustomerData
import io.jsonwebtoken.Jwts

import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.security.{InvalidKeyException, Key, NoSuchAlgorithmException}
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Map;


class TokenUtil {
  private val secret: String = "hsef"
  private val salt: String = "fsj98"
  private val ITERATION_COUNT = 10000
  private val KEY_SIZE = 256

  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeyException]
  def createUserToken(claims: Map[String, String], email: String, userId: String): String = {
    Jwts.builder()
      .claims(claims)
      .subject(email)
      .issuedAt(Date.from(Instant.now()))
      .expiration(getTokenExpirationDate(Instant.now()))
      .issuer(userId)
      .signWith(getKey())
      .compact()
  }

  def getCustomerDataFromToken(token: String) ={
    val payload = Jwts.parser()
      .verifyWith(getKey())
      .build()
      .parseSignedClaims(token)
      .getPayload
    CustomerData(
      payload.get("userId") match {
        case str: String if str.isEmpty => -1;
        case integer: Integer => integer.asInstanceOf[Int]
      },
      payload.get("name").asInstanceOf[String],
      payload.get("userEmail").asInstanceOf[String],
      payload.get("phoneNumber").asInstanceOf[String]
    )
  }
  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeyException]
  private def getKey(): SecretKeySpec = {
    val pbeKeySpec = new PBEKeySpec(secret.toCharArray, salt.getBytes, ITERATION_COUNT, KEY_SIZE)
    val pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(pbeKeySpec)
    new SecretKeySpec(pbeKey.getEncoded, "HmacSHA256")
  }

  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeyException]
  private def getKeyFromInput(secret: String): Key = {
    val pbeKeySpec = new PBEKeySpec(secret.toCharArray, salt.getBytes, ITERATION_COUNT, KEY_SIZE)
    val pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(pbeKeySpec)
    new SecretKeySpec(pbeKey.getEncoded, "HmacSHA256")
  }

  private def getTokenExpirationDate(now: Instant): Date = {
    Date.from(now.plus(60, ChronoUnit.MINUTES))
  }

  private def validateAudience(strings: java.util.Set[String]): Unit = {

  }

  @throws[NoSuchAlgorithmException]
  @throws[InvalidKeyException]
  def verifyToken(token: String): Unit = {
    val payload = Jwts.parser()
      .verifyWith(getKey())
      .build()
      .parseSignedClaims(token)
      .getPayload

    this.validateAudience(payload.getAudience)
  }

}
