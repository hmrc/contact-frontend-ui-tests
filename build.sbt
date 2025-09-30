import scala.language.postfixOps
import scala.sys.process.*

lazy val downloadHarFromZap = taskKey[Unit]("Download HAR from ZAP")

downloadHarFromZap := {
  url(
    s"http://${System.getenv("ZAP_HOST")}/OTHER/exim/other/exportHar/?baseurl=http%3A%2F%2Flocalhost&start=&count="
  ) #> file("requests.har") !
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
