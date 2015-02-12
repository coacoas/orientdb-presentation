name := """orientdb-demo"""

version := "1.0"

scalaVersion := "2.11.5"

// Change this to another test framework if you prefer
val testLibraries = Seq("org.scalatest" %% "scalatest" % "2.1.6" % "test")

val orientDB = Seq(
  "com.orientechnologies" % "orientdb-graphdb" % "2.0.2"
  ,"com.orientechnologies" % "orientdb-client" % "2.0.2"
  ,"com.orientechnologies" % "orientdb-enterprise" % "2.0.2"
)

val graphAPI = Seq(
  "com.tinkerpop.gremlin" % "gremlin-java" % "2.6.0"
  ,"com.michaelpollmeier" %% "gremlin-scala" % "2.6.1"
)

libraryDependencies ++= orientDB ++ graphAPI ++ testLibraries
