name := "Pam"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.2"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += javaJdbc % Test
libraryDependencies += evolutions

libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test
testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

libraryDependencies ++= Seq(
  filters,
  "mysql" % "mysql-connector-java" % "5.1.41",
  "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B3",
  "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "3.2.1",
  "org.webjars" % "font-awesome" % "4.7.0",
  "org.webjars" % "bootstrap-datepicker" % "1.4.0" exclude("org.webjars", "bootstrap"),
  "be.objectify" %% "deadbolt-java" % "2.6.1",
  "be.objectify" %% "deadbolt-java-gs" % "2.6.0",
  "commons-beanutils" % "commons-beanutils" % "1.9.3"
)

