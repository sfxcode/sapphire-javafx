# ScalaFX

Most of my own applications use scalafx because of a very useful DSL and
some nice wrapper functions around javafx with extended functionality
and / or the more scala friendly syntax.

## Documentation

Documentation can be found at [www.scalafx.org/docs/home/](http://www.scalafx.org/docs/home/)

Documentation is a little bit out of date, but there are also some other
places to find useful information.

* [Github](https://github.com/scalafx/scalafx)
* [Release Notes](https://github.com/scalafx/scalafx/blob/master/notes/15.0.1-R21.md)
* [Stackoverflow](https://stackoverflow.com/search?q=scalafx)
* [ScalaFX-Tutorials](https://github.com/scalafx/ScalaFX-Tutorials)
* [ProScalaFX](https://github.com/scalafx/ProScalaFX)

## Usage

Include following snippet in your build.sbt file:

```
libraryDependencies += "org.scalafx" %% "scalafx" % "12.0.2-R18"
```

In your classes add scalafx Includes to your imports:

```
import scalafx.Includes._
```

## Sapphire with ScalaFX

### IssueTrackingLite

Sapphire IssueTrackingLite Demo in the sapphire-core demo section use ScalaFX.

### Reasons to use ScalaFX

I use [ScalaFX](http://www.scalafx.org/) in nearly all of my projects.

* UI DSL for declarative scene graph objects creation
* Automatic conversion to Scala
* Scala like Collection support
* ...


