# Sapphire JavaFX (SFX)

A JavaFX  Application Framework for Scala User. It combines scala programming patterns with MVC for building complex JavaFX Applications.

## Cross Build

Build and tested against Scala 2.12/2.13 and JDK 11/12


## Used Frameworks

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on OpenJFX 11/12.

JavaFX Code Samples Samles under [CatalogJavaFX](http://www.java2s.com/Code/Java/JavaFX/CatalogJavaFX.htm)

[https://openjfx.io](https://openjfx.io)

### Data Framework (sapphire-data)

[sapphire-data](https://sfxcode.github.io/sapphire-data/) is used for reflection based property handling.


### Dependency Injection (Optional)

Sapphire use [Apache Deltaspike](http://deltaspike.apache.org) as CDI Abstraction Layer (1.9.x).

The default [CDI](https://de.wikipedia.org/wiki/Contexts_and_Dependency_Injection) implementation depends on [Apache OpenWebBeans](http://openwebbeans.apache.org) (2.0.x).

### Expression Language

Expressions are resolved by EL 3 [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html).

Expression lookup imported from [sapphire-data](https://sfxcode.github.io/sapphire-data/)

Expressions can be used in code and FXML files.

## Maven

Sapphire is published to Bintray and linked to Maven Central.

### Repository

```
resolvers += "sfxcode-bintray" at "https://dl.bintray.com/sfxcode/maven"

```

### Artifact

@@dependency[sbt,Maven,Gradle] {
  group="com.sfxcode.sapphire"
  artifact="sapphire-javafx_2.13"
  version="$project.version$"
}

### Demos

Explore demos and Tutorial in project demo directory.

Also there is a showcase for commonly used pattern.



## Licence

[Apache 2](https://github.com/sfxcode/sapphire-javafx/blob/master/LICENSE)

@@@ index

 - [Features](features.md)
 - [getting_started](getting_started.md)
 - [Core Concepts](detail/index.md)
 - [control](control/index.md)
 - [Tutorial](tutorial/index.md)
 - [Development](development/index.md)
 - [Examples](sample/index.md)
 - [ScalaFX](scalafx.md)
 - [Changes ](changes.md)
 - [migration](migration.md)

@@@
