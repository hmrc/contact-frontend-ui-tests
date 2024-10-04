lazy val root = (project in file("."))
  .settings(
    name := "contact-frontend-ui-tests",
    version := "0.1.0",
    scalaVersion := "2.13.13",
    libraryDependencies ++= Dependencies.test,
    (Compile / compile) := ((Compile / compile) dependsOn (Compile / scalafmtSbtCheck, Compile / scalafmtCheckAll)).value
  )
  .disablePlugins(JUnitXmlReportPlugin) //Required to prevent https://github.com/scalatest/scalatest/issues/1427
