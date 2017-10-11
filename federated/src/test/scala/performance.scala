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
      .silent
      .formParamMap(Map(
        "username" -> USER,
        "password" -> PASS,
        "client_id" -> "keycloak-example",
        "grant_type" -> "password"
      ))
      .check(jsonPath("$.access_token").saveAs("accessToken")))

  val request =
      exec(http("Request")
        .get(GREEN_URL)
        .header("Authorization", "Bearer ${accessToken}"))


  val scn = scenario("Login and call Green")
        .exec(login)
        .exec(request)

  setUp(scn.inject(rampUsers(10) over 1)).protocols(httpProtocol)
}
