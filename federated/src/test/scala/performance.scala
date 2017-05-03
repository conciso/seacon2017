import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class performance extends Simulation {

  val REALM = "keycloak-example"
  val TOKEN_URL = "http://localhost:9080/auth/realms/" + REALM + "/protocol/openid-connect/token"

  val USER = "seacon"
  val PASS = "seacon"

  val BASE_URL = "http://localhost:8082"
  val GREEN_URL = BASE_URL + "/green"

  val httpProtocol = http
    .warmUp(BASE_URL)
    .baseURL(BASE_URL)
    .acceptHeader("*/*")

  val login =
    exec(http("Login")
      .post(TOKEN_URL)
      .formParamMap(Map(
        "username" -> USER,
        "password" -> PASS,
        "client_id" -> "keycloak-example",
        "client_secret" -> "b4ef589e-b369-419c-aa99-fe48e521a373",
        "grant_type" -> "password"
      ))
      .check(jsonPath("$.access_token").saveAs("accessToken")))

  val request =
      exec(http("Request")
        .get(GREEN_URL)
        .header("Authorization", "Bearer ${accessToken}"))


  val scn = scenario("Login and call Green")
    .repeat(1) {
      exec(login)
    }
    .repeat(10) {
      exec(request)
    }

  setUp(scn.inject(rampUsers(1) over 1)).protocols(httpProtocol)
}
