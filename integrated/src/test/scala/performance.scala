import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.util.Base64
import java.nio.charset.StandardCharsets

class performance extends Simulation {

  val USER = "seacon"
  val PASS = "seacon"
  val userPassBase64 = Base64.getEncoder.encodeToString((USER + ":" + PASS).getBytes(StandardCharsets.UTF_8))

  val BASE_URL = "http://localhost:8081"
  val GREEN_URL = BASE_URL + "/green"

  val httpProtocol = http
    .warmUp(BASE_URL)
    .baseURL(BASE_URL)
    .acceptHeader("*/*")

  val request =
      exec(http("Request")
        .get(GREEN_URL)
        .header("Authorization", "Basic " + userPassBase64))

  val scn = scenario("Call Green")
    .repeat(10) {
      exec(request)
    }

  setUp(scn.inject(rampUsers(1) over 1)).protocols(httpProtocol)
}
