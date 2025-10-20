import scala.language.postfixOps
import sbt.*
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import scala.sys.process.*

lazy val publishJourneyTestRecording = taskKey[Unit]("Publish journey test recording")

publishJourneyTestRecording := {
  val log                    = streams.value.log
  val journeyTestProjectName = name.value

  log.info("Attempting to publish journey test recording to artifactory")
  IO.withTemporaryDirectory { tmp =>
    log.info("Downloading journey test recording from ZAP")
    val journeyTestRecording = tmp / s"$journeyTestProjectName.har"
    url(
      s"http://${sys.env("ZAP_HOST")}/OTHER/exim/other/exportHar/?baseurl=http%3A%2F%2Flocalhost&start=&count="
    ) #> journeyTestRecording !!

    log.info("Compressing journey test recording with ZIP")
    val compressedJourneyTestRecording = tmp / s"$journeyTestProjectName.har.zip"
    IO.zip(
      sources = List(journeyTestRecording -> journeyTestRecording.name),
      outputZip = compressedJourneyTestRecording,
      time = None // because we don't need a static timestamp for all entries
    )

    log.info("Uploading journey test recording to artifactory")
    val artifactoryUrl =
      s"${sys.env("ARTIFACTORY_URI")}/hmrc-platform-ui-local/journey-test-recordings/$journeyTestProjectName/${compressedJourneyTestRecording.name}"
    val response       = HttpClient
      .newHttpClient()
      .send(
        HttpRequest
          .newBuilder()
          .uri(java.net.URI.create(artifactoryUrl))
          .PUT(HttpRequest.BodyPublishers.ofFile(compressedJourneyTestRecording.toPath))
          .header("Authorization", s"Bearer ${sys.env("PLATUI_ARTIFACTORY_TOKEN")}")
          .build(),
        HttpResponse.BodyHandlers.ofString()
      )
    if (response.statusCode() >= 200 && response.statusCode() < 300) {
      log.info("Successfully published journey test recording to artifactory")
    } else {
      log.error(s"Upload to artifactory failed: ${response.statusCode()} - ${response.body()}")
    }
  }
}

lazy val root = (project in file("."))
  .settings(
    name := "contact-frontend-ui-tests",
    version := "0.1.0",
    scalaVersion := "2.13.16",
    libraryDependencies ++= Dependencies.test,
    (Compile / compile) := ((Compile / compile) dependsOn (Compile / scalafmtSbtCheck, Compile / scalafmtCheckAll)).value
  )
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
