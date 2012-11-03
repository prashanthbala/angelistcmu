import sbt._
import Keys._
import PlayProject._
import com.typesafe.sbtscalariform.ScalariformPlugin._
import scalariform.formatter.preferences._


object ApplicationBuild extends Build {

    val appName         = "angelistcmu"
    val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.codahale" % "jerkson_2.9.1" % "0.5.0",
    "org.mongodb" % "casbah_2.9.2" % "2.4.1",
    "org.mongodb" % "casbah-core_2.9.2" % "2.4.1",
    "org.mongodb" % "casbah-query_2.9.2" % "2.4.1",
    "org.mongodb" % "casbah-gridfs_2.9.2" % "2.4.1",
    "org.mongodb" % "casbah-util_2.9.2" % "2.4.1",
    "com.typesafe.akka" % "akka-actor" % "2.0.3",
    "net.liftweb" %% "lift-webkit" % "2.4",
    "net.liftweb" %% "lift-mapper" % "2.4",
    "net.liftweb" %% "lift-wizard" % "2.4",
    "com.novus" %% "salat" % "1.9.0",
    "org.mindrot" % "jbcrypt" % "0.3m",
    "io.netty" % "netty" % "3.5.8.Final",
    "com.github.mumoshu" %% "play2-memcached" % "0.2.2-SNAPSHOT"

    //"org.scalatest" %% "scalatest" % "1.8" % "test"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    resolvers ++= Seq(
      Resolver.url("Codahale Repo", url("http://repo.codahale.com")),
      Resolver.url("Scala Tools Repo", url("http://scala-tools.org/repo-releases/")),
      "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
      "Sonatype OSS Snapshots Repository" at "http://oss.sonatype.org/content/groups/public", //for play2-memcached plugin
      "Spy Repository" at "http://files.couchbase.com/maven2" // required to resolve `spymemcached`, play2-memcached dependency.
    ),
    testOptions in Test := Nil,
    ScalariformKeys.preferences := FormattingPreferences().
      setPreference(DoubleIndentClassDeclaration, false).
      setPreference(AlignParameters, true).
      setPreference(IndentSpaces, 4).
      setPreference(AlignSingleLineCaseStatements, true).
      setPreference(PreserveDanglingCloseParenthesis, true).
      setPreference(PreserveSpaceBeforeArguments, true)
  )
}
