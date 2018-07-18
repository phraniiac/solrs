name := "solrs"

description := "A solr client for scala, providing a query interface like SolrJ, just asynchronously / non-blocking"

homepage := Some(url("https://github.com/inoio/solrs"))

organization := "io.ino"

version := "2.1.0"

licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.11.12", "2.12.6")

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps"
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error(s"Java 8 is required for this project. Running: ${sys.props("java.specification.version")}")
}

resolvers ++= Seq(
  "Restlet Repositories" at "http://maven.restlet.org"
)

val solrVersion = "7.4.0"
val slf4jVersion = "1.7.25"

libraryDependencies ++= Seq(
  "org.apache.solr"         % "solr-solrj"        % solrVersion,
  "com.typesafe.play"      %% "play-ahc-ws"       % "2.7.0-M1",
  "com.typesafe.play"      %% "play-ws"           % "2.7.0-M1",
  "com.typesafe.play"      %% "play-json"         % "2.6.8",
  "org.scala-lang.modules" %% "scala-xml"         % "1.1.0",
  "org.scala-lang.modules" %% "scala-java8-compat"% "0.9.0",
  "io.dropwizard.metrics"   % "metrics-core"      % "3.2.6" % "optional",
  "org.slf4j"               % "slf4j-api"         % slf4jVersion,
  "org.slf4j"               % "slf4j-simple"      % slf4jVersion % "test",
  "org.scalatest"          %% "scalatest"         % "3.0.5" % "test",
  "com.novocode"            % "junit-interface"   % "0.11" % "test",
  "org.mockito"             % "mockito-core"      % "1.10.19" % "test",
  "org.hamcrest"            % "hamcrest-library"  % "1.3" % "test",
  "org.clapper"            %% "grizzled-scala"    % "4.5.1" % "test",
  // Cloud testing, solr-core for ZkController (upconfig)
  "org.apache.solr"         % "solr-core"         % solrVersion % "test",
  "org.apache.solr"         % "solr-test-framework" % solrVersion % "test",
  "com.twitter"            %% "util-core"         % "18.7.0" % "optional",
  "commons-logging"         % "commons-logging"   % "1.2"
)

// Fork tests so that SolrRunner's shutdown hook kicks in
fork in Test := true

// Publish settings
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

// enable publishing the jar produced by `test:package`
publishArtifact in (Test, packageBin) := true

pomIncludeRepository := { _ => false }

pomExtra := (
  <scm>
    <url>git@github.com:inoio/solrs.git</url>
    <connection>scm:git:git@github.com:inoio/solrs.git</connection>
  </scm>
  <developers>
    <developer>
      <id>martin.grotzke</id>
      <name>Martin Grotzke</name>
      <url>https://github.com/magro</url>
    </developer>
  </developers>)
