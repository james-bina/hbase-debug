name := "hbase-debug"

version := "1.0"

organization := "com.bina"

scalaVersion := "2.10.5"

coverageEnabled := false

val versions = Map(
  'hbase -> "1.0.0",
  'hadoop -> "2.6.0",
  'spark -> "1.5.1"
)

net.virtualvoid.sbt.graph.Plugin.graphSettings

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

libraryDependencies ++= Seq(
  ("org.apache.spark" %% "spark-core" % versions('spark) % "provided").
    exclude("javax.servlet", "servlet-api").
    exclude("org.mortbay.jetty", "servlet-api"),
  "org.apache.spark" %% "spark-streaming" % versions('spark) % "provided",
  "org.apache.spark" %% "spark-yarn" % versions('spark) % "provided",
  "org.apache.hbase" % "hbase-client" % versions('hbase) % "provided",
  "org.apache.hbase" % "hbase-common" % versions('hbase) % "provided",
  ("org.apache.hbase" % "hbase-server" % versions('hbase) % "provided").
    exclude("org.mortbay.jetty", "servlet-api"),
  ("org.apache.hadoop" % "hadoop-common" % versions('hadoop) % "provided").
    exclude("javax.servlet", "servlet-api").
    exclude("org.mortbay.jetty", "servlet-api"),
  ("org.apache.hadoop" % "hadoop-hdfs" % versions('hadoop) % "provided").
    exclude("javax.servlet", "servlet-api").
    exclude("org.mortbay.jetty", "servlet-api"),
  "org.apache.hadoop" % "hadoop-yarn-api" % versions('hadoop) % "provided",
  "org.apache.hadoop" % "hadoop-yarn-applications-distributedshell" % versions('hadoop) % "provided"
)

resolvers ++= Seq(
  "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + ".m2/repository",
  "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
  "Spray Repository" at "http://repo.spray.cc/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Cloudera Releases" at "https://repository.cloudera.com/artifactory/repo/",
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Twitter4J Repository" at "http://twitter4j.org/maven2/",
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  "Twitter Maven Repo" at "http://maven.twttr.com/",
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
  "bina-aws" at "http://bina-aws:8081/nexus/content/groups/public",
  "hadoop-bam" at "http://hadoop-bam.sourceforge.net/maven/",
  Resolver.sonatypeRepo("public"),
  Resolver.mavenLocal
)

mainClass in assembly := Some("aaim.hbase.focus")

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith ".RSA" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".xsd" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".dtd" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last == "spring.tooling" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last == "package-info.class" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
