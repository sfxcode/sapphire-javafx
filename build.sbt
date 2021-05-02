import sbt.url

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Comment, Elem, Node => XmlNode, NodeSeq => XmlNodeSeq}

name := "sapphire-javafx"

organization := "com.sfxcode.sapphire"

crossScalaVersions := Seq("2.13.5", "2.12.12")

scalaVersion := crossScalaVersions.value.head

compileOrder := CompileOrder.JavaThenScala

lazy val sapphire_javafx = Project(id = "sapphire-javafx", base = file("."))

javacOptions in test += "-Dorg.apache.deltaspike.ProjectStage=Test"

scalacOptions += "-deprecation"

parallelExecution in Test := false

val Json4sVersion     = "3.6.11"
val LogbackVersion    = "1.2.3"
val DeltaspikeVersion = "1.9.4"
val IkonliVersion     = "12.2.0"

addCommandAlias("run-showcase", "sapphire-javafx-showcase/run")

lazy val demo_login = Project(id = "sapphire-javafx-login", base = file("demos/login"))
  .settings(
    scalaVersion := "2.13.5",
    name := "sapphire-javafx-login",
    description := "Sapphire Login Demo",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.javafx.demo.login.Application")
  )
  .dependsOn(sapphire_javafx)

addCommandAlias("run-login", "sapphire-login-demo/run")

lazy val demo_issues = Project(id = "sapphire-javafx-issues", base = file("demos/issues"))
  .settings(
    scalaVersion := "2.13.5",
    name := "sapphire-javafx-issues",
    description := "Sapphire Issues Demo",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback"                % "logback-classic"        % LogbackVersion,
    libraryDependencies += "org.scalafx"                  %% "scalafx"                % "15.0.1-R21",
    libraryDependencies += "javax.enterprise"              % "cdi-api"                % "2.0",
    libraryDependencies += "javax.annotation"              % "javax.annotation-api"   % "1.3.2",
    libraryDependencies += "org.apache.openwebbeans"       % "openwebbeans-impl"      % "2.0.17",
    libraryDependencies += "org.apache.deltaspike.core"    % "deltaspike-core-impl"   % DeltaspikeVersion,
    libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion,
    libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion,
    mainClass := Some("com.sfxcode.sapphire.javafx.demo.issues.Application")
  )
  .dependsOn(sapphire_javafx)

addCommandAlias("run-issues", "sapphire-issues-demo/run")

lazy val tutorial = Project(id = "sapphire-javafx-tutorial", base = file("demos/tutorial"))
  .settings(
    scalaVersion := "2.13.5",
    name := "sapphire-javafx-tutorial",
    description := "Sapphire Tutorial",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.javafx.demo.tutorial.Application")
  )
  .dependsOn(sapphire_javafx)

lazy val windows = Project(id = "sapphire-javafx-windows", base = file("demos/windows"))
  .settings(
    scalaVersion := "2.13.5",
    name := "sapphire-javafx-windows",
    description := "Sapphire Windows",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
    mainClass := Some("com.sfxcode.sapphire.javafx.demo.windows.Application")
  )
  .dependsOn(sapphire_javafx)

lazy val showcase =
  Project(id = "sapphire-javafx-showcase", base = file("showcase"))
    .settings(
      scalaVersion := "2.13.5",
      name := "sapphire-javafx-showcase",
      description := "Sapphire JavaFX Showcase",
      libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
      libraryDependencies += "org.json4s"    %% "json4s-native"   % Json4sVersion,
      libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
      resolvers += "sandec" at "https://sandec.bintray.com/repo",
      libraryDependencies += "com.sandec"            % "mdfx"         % "0.1.7",
      libraryDependencies += "com.jfoenix"           % "jfoenix"      % "9.0.10",
      libraryDependencies += "org.fxmisc.richtext"   % "richtextfx"   % "0.10.3",
      libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1",
      resolvers += "jasperreports-repo" at "https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts",
      libraryDependencies += "net.sf.jasperreports" % "jasperreports" % "6.15.0",
      libraryDependencies += "xerces"               % "xercesImpl"    % "2.12.0",
      mainClass := Some("com.sfxcode.sapphire.javafx.showcase.Application")
    )
    .dependsOn(sapphire_javafx)

lazy val sapphire_extension_scenebuilder = Project(
  id = "sapphire-javafx-scenebuilder",
  base = file("scenebuilder")
).settings(
  name := "sapphire-javafx-scenebuilder",
  description := "Sapphire JavaFX Scenebuilder",
  crossPaths := false,
  libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName)
)

lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxSitePlugin)
  .enablePlugins(ParadoxMaterialThemePlugin)
  .enablePlugins(GhpagesPlugin)
  .settings(
    scalaVersion := "2.13.5",
    name := "sapphire-javafx-docs",
    publish / skip := true,
    ghpagesNoJekyll := true,
    git.remoteRepo := "git@github.com:sfxcode/sapphire-javafx.git",
    Compile / paradoxMaterialTheme ~= {
      _.withRepository(uri("https://github.com/sfxcode/sapphire-javafx"))

    },
    (Compile / paradoxMarkdownToHtml / excludeFilter) := (Compile / paradoxMarkdownToHtml / excludeFilter).value ||
    ParadoxPlugin.InDirectoryFilter((Compile / paradox / sourceDirectory).value / "includes")
  )

val JavaFXVersion = "17-ea+8"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _                            => throw new Exception("Unknown platform!")
}

addCommandAlias("run-tutorial", "sapphire-tutorial/run")

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.6" % Test

libraryDependencies += "org.json4s" %% "json4s-native" % Json4sVersion % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

// Compile

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(m =>
  "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName
)

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-data" % "1.1.0"

libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.0" intransitive ()

libraryDependencies += "org.kordamp.ikonli" % "ikonli-javafx" % IkonliVersion

libraryDependencies += "org.kordamp.ikonli" % "ikonli-fontawesome-pack" % IkonliVersion

// extension akka

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.13" % Provided

// extension showcase

libraryDependencies += "com.jfoenix" % "jfoenix" % "9.0.10" % Provided

libraryDependencies += "org.fxmisc.richtext" % "richtextfx" % "0.10.6" % Provided

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

enablePlugins(BuildInfoPlugin)

buildInfoPackage := "com.sfxcode.sapphire.javafx"

buildInfoOptions += BuildInfoOption.BuildTime

// publish

// Use `pomPostProcess` to remove dependencies marked as "provided" from publishing in POM
// This is to avoid dependency on wrong OS version JavaFX libraries [Issue #289]
// See also [https://stackoverflow.com/questions/27835740/sbt-exclude-certain-dependency-only-during-publish]

pomPostProcess := { node: XmlNode =>
  new RuleTransformer(new RewriteRule {
    override def transform(node: XmlNode): XmlNodeSeq =
      node match {
        case e: Elem
            if e.label == "dependency" && e.child.exists(c => c.label == "scope" && c.text == "provided")
              && e.child.exists(c => c.label == "groupId" && c.text == "org.openjfx") =>
          val organization = e.child.filter(_.label == "groupId").flatMap(_.text).mkString
          val artifact     = e.child.filter(_.label == "artifactId").flatMap(_.text).mkString
          val version      = e.child.filter(_.label == "version").flatMap(_.text).mkString
          Comment(s"provided dependency $organization#$artifact;$version has been omitted")
        case _ => node
      }
  }).transform(node).head
}

releaseCrossBuild := true

bintrayReleaseOnPublish in ThisBuild := true

publishMavenStyle := true

homepage := Some(url("https://github.com/sfxcode/sapphire-javafx"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/sfxcode/sapphire-javafx"),
    "scm:https://github.com/sfxcode/sapphire-javafx.git"
  )
)

developers := List(
  Developer(
    id = "sfxcode",
    name = "Tom Lamers",
    email = "tom@sfxcode.com",
    url = url("https://github.com/sfxcode")
  )
)

packageOptions += {
  Package.ManifestAttributes(
    "Created-By"               -> "Simple Build Tool",
    "Built-By"                 -> "sfxcode",
    "Build-Jdk"                -> System.getProperty("java.version"),
    "Specification-Title"      -> name.value,
    "Specification-Version"    -> version.value,
    "Specification-Vendor"     -> organization.value,
    "Implementation-Title"     -> name.value,
    "Implementation-Version"   -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor"    -> organization.value
  )
}
