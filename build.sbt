name := "play_via_idea"

version := "1.0"

//lazy val `play_via_idea` = (project in file(".")).enablePlugins(PlayScala)
lazy val `play_via_idea` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc, cache, ws, javaJpa, evolutions, specs2 % Test )

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
libraryDependencies += "dom4j" % "dom4j" % "1.6.1" intransitive()
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"