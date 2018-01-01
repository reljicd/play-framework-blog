name := """play-blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += jdbc
// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.194"

// BCrypt
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// WebJars
libraryDependencies += "org.webjars.bower" % "jquery" % "3.2.1"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test


// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"