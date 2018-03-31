import sbt.Resolver

lazy val settings = Seq[Setting[_]](

  name := "qproxy",
  organization := "com.devchain",
  version := "0.4.0",

  scalaVersion := "2.12.4",
  scalacOptions       := Seq("-feature", "-deprecation", "-unchecked", "-language:implicitConversions", "-language:higherKinds"),

  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/sbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
    if (sys.props.isDefinedAt("ivyLocal")) Resolver.defaultLocal else Resolver.mavenLocal
  ),

  libraryDependencies ++= Seq(
//    "commons-io" % "commons-io" % "2.4",
//    "org.typelevel" %% "cats" % "0.8.1",
    "joda-time" % "joda-time" % Versions.joda,
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
//    "com.github.scopt" %% "scopt" % "3.7.0",

    "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp,
    "com.typesafe.akka" %% "akka-actor" % Versions.akka,
    "com.typesafe.akka" %% "akka-stream" % Versions.akka,
    "com.typesafe.akka" %% "akka-slf4j" % Versions.akka,
    "com.typesafe.akka" %% "akka-http-spray-json" % Versions.akkaHttp,

    "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging,
    "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime,
//    "me.moocar" % "logback-gelf" % "0.12" % Runtime,
    "org.web3j" % "core" % "3.2.0",
    "org.web3j" % "quorum" % "0.8.0"
  ),

  excludeDependencies += "org.slf4j" % "slf4j-log4j12",

  Compile / packageBin / mappings ~= {
    _.filter(!_._1.getName.endsWith(".properties"))
      .filter(_._1.getName != "logback.xml")
      .filter(_._1.getName != "application.conf")
  }
)

lazy val qproxy = (project in file("."))
  .enablePlugins(JavaAppPackaging, UniversalPlugin)
  .settings(settings)
