import sbt.url

import scala.xml.transform.{RewriteRule, RuleTransformer}
import scala.xml.{Comment, Elem, Node => XmlNode, NodeSeq => XmlNodeSeq}

name := "sapphire-javafx"

organization := "com.sfxcode.sapphire"

crossScalaVersions := Seq("2.13.10", "2.12.12")

scalaVersion := crossScalaVersions.value.head

compileOrder := CompileOrder.JavaThenScala

lazy val sapphire_javafx = Project(id = "sapphire-javafx", base = file("."))

test / javacOptions += "-Dorg.apache.deltaspike.ProjectStage=Test"

scalacOptions += "-deprecation"

test / parallelExecution := false

val LogbackVersion    = "1.4.5"
val DeltaspikeVersion = "1.9.5"
val IkonliVersion     = "12.3.1"

addCommandAlias("run-showcase", "sapphire-javafx-showcase/run")

lazy val demo_login = Project(id = "sapphire-javafx-login", base = file("demos/login"))
  .settings(
    scalaVersion := "2.13.10",
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
    scalaVersion := "2.13.10",
    name := "sapphire-javafx-issues",
    description := "Sapphire Issues Demo",
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
    libraryDependencies += "ch.qos.logback"                % "logback-classic"        % LogbackVersion,
    libraryDependencies += "org.scalafx"                  %% "scalafx"                % "18.0.1-R28",
    libraryDependencies += "javax.enterprise"              % "cdi-api"                % "2.0",
    libraryDependencies += "javax.annotation"              % "javax.annotation-api"   % "1.3.2",
    libraryDependencies += "org.apache.openwebbeans"       % "openwebbeans-impl"      % "2.0.22",
    libraryDependencies += "org.apache.deltaspike.core"    % "deltaspike-core-impl"   % DeltaspikeVersion,
    libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-api" % DeltaspikeVersion,
    libraryDependencies += "org.apache.deltaspike.cdictrl" % "deltaspike-cdictrl-owb" % DeltaspikeVersion,
    mainClass := Some("com.sfxcode.sapphire.javafx.demo.issues.Application")
  )
  .dependsOn(sapphire_javafx)

addCommandAlias("run-issues", "sapphire-issues-demo/run")

lazy val tutorial = Project(id = "sapphire-javafx-tutorial", base = file("demos/tutorial"))
  .settings(
    scalaVersion := "2.13.10",
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
    scalaVersion := "2.13.10",
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
      scalaVersion := "2.13.10",
      name := "sapphire-javafx-showcase",
      description := "Sapphire JavaFX Showcase",
      libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % JavaFXVersion classifier osName),
      libraryDependencies ++= Seq(
        "io.circe" %% "circe-core",
        "io.circe" %% "circe-generic",
        "io.circe" %% "circe-parser"
      ).map(_ % circeVersion),
      libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion,
      resolvers += "sandec" at "https://sandec.jfrog.io/artifactory/repo",
      libraryDependencies += "com.sandec"            % "mdfx"         % "0.2.8",
      libraryDependencies += "com.jfoenix"           % "jfoenix"      % "9.0.10",
      libraryDependencies += "org.fxmisc.richtext"   % "richtextfx"   % "0.11.0",
      libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1",
      resolvers += "jasperreports-repo" at "https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts",
      libraryDependencies += "net.sf.jasperreports" % "jasperreports" % "6.20.0",
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
    scalaVersion := "2.13.8",
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

val JavaFXVersion = "19"

val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _                            => throw new Exception("Unknown platform!")
}

addCommandAlias("run-tutorial", "sapphire-tutorial/run")

// Test

libraryDependencies += "org.specs2" %% "specs2-core" % "4.19.0" % Test

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % LogbackVersion % Test

val circeVersion = "0.14.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion % Test)

// Compile

libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(m =>
  "org.openjfx" % s"javafx-$m" % JavaFXVersion % Provided classifier osName
)

libraryDependencies += "com.sfxcode.sapphire" %% "sapphire-data" % "2.0.2"

libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.2" intransitive ()

libraryDependencies += "org.kordamp.ikonli" % "ikonli-javafx" % IkonliVersion

libraryDependencies += "org.kordamp.ikonli" % "ikonli-fontawesome-pack" % IkonliVersion

// extension showcase

libraryDependencies += "com.jfoenix" % "jfoenix" % "9.0.10" % Provided

libraryDependencies += "org.fxmisc.richtext" % "richtextfx" % "0.11.0" % Provided

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

ThisBuild / bintrayReleaseOnPublish := true

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
