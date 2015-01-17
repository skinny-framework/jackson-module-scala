import sbtrelease.ReleasePlugin._
import ReleaseKeys._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.Utilities._
import com.typesafe.sbt.osgi.OsgiKeys
import com.typesafe.sbt.pgp.PgpKeys._

// OSGI bundles
osgiSettings

OsgiKeys.exportPackage := Seq(
  "com.fasterxml.jackson.module.scala.*"
)

OsgiKeys.privatePackage := Nil

// publishing
publishMavenStyle := true

publishTo <<= version { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := {
  <url>http://wiki.fasterxml.com/JacksonModuleScala</url>
  <licenses>
    <license>
      <name>The Apache Software License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:git@github.com:FasterXML/jackson-module-scala.git</connection>
    <developerConnection>scm:git:git@github.com:FasterXML/jackson-module-scala.git</developerConnection>
    <url>http://github.com/FasterXML/jackson-module-scala</url>
  </scm>
  <developers>
    <developer>
      <id>tatu</id>
      <name>Tatu Saloranta</name>
      <email>tatu@fasterxml.com</email>
    </developer>
    <developer>
      <id>christopher</id>
      <name>Christopher Currie</name>
      <email>christopher@currie.com</email>
    </developer>
  </developers>
  <contributors>
    <contributor>
      <name>Nathaniel Bauernfeind</name>
    </contributor>
    <contributor>
      <name>Anton Panasenko</name>
    </contributor>
  </contributors>
}

// release
releaseSettings

// bump bugfix on release
nextVersion := { ver => sbtrelease.Version(ver).map(_.bumpBugfix.asSnapshot.string).getOrElse(sbtrelease.versionFormatError) }

// use maven style tag name
tagName <<= (name, version in ThisBuild) map { (n,v) => n + "-" + v }

// sign artifacts

publishArtifactsAction := PgpKeys.publishSigned.value

