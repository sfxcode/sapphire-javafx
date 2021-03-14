# Sapphire JavaFX (SFX)

A JavaFX Application Framework for Scala User.

This project replaces [sapphire-core](https://sfxcode.github.io/sapphire-core) and [sapphire-extension](https://sfxcode.github.io/sapphire-extension).

It provides a solid base for Rapid Application Development with JavaFX and Scala and is a good place for start development.

Examples of using [ScalaFX](http://www.scalafx.org/) and [JFoenix](http://www.jfoenix.com/) are provided.

Also there is an example of using it with a CDI Framework ([Deltaspike](https://deltaspike.apache.org/)).

If you like to try an easy way to setup a desktop app with scala, give it a try.

## History

I started JavaFX GUI develpment with the project sapphire-core, because for every application almost the same pattern were needed.

As my projects need better datalist support and more stuff, i created another project named sapphire-extension and called it experimental.

With the time i wanted to have all code, demos and showcase to be at one place. This was the start of sapphire-javafx.

All data-handling was refactored out in another project called sapphire-data, because this date and expression handling is also useful in non JavaFX projects.

This project replaces [sapphire-core](https://sfxcode.github.io/sapphire-core) and [sapphire-extension](https://sfxcode.github.io/sapphire-extension).

At this time i decided to add a clear namespace prefix: SFX (for Sapphire JavaFX).

## Documentation

Documentation under [https://sfxcode.github.io/sapphire-javafx](https://sfxcode.github.io/sapphire-javafx)

## Showcase

A showcase can be found in the showcase folder inside this project.

The showcase show some of the common pattern used by this project.

## Migrate from sapphire-core

Migration from sapphire-core (and sapphire-extension) by replace:

```
sapphire.core (sapphire.extension)
```

with

```
sapphire.javafx
```

This should make all problems in packages work again.

## CI

![Scala CI](https://github.com/sfxcode/sapphire-javafx/workflows/Scala%20CI/badge.svg)

## Download

[ ![Download](https://api.bintray.com/packages/sfxcode/maven/sapphire-javafx/images/download.svg) ](https://bintray.com/sfxcode/maven/sapphire-javafx/_latestVersion)

## Giter8 Template

[Giter8 template](http://www.foundweekends.org/giter8/): [sapphire-sbt](https://github.com/sfxcode/sapphire-sbt.g8).

## Cross Build

Build and tested against Scala 2.12/2.13 and JDK 11/12

### Usage

g8 https://github.com/sfxcode/sapphire-sbt.g8

## Licence

Apache 2 License.


## Demos

### Tutorial

Simple JavaFX application with controller composition, controller exchange, ...

```
sbt run-tutorial
```

### Login

JavaFX Login demo enhanced by sapphire-core

```
sbt run-login
```

### Issues

spphire-core with ScalaFX and CDI (Deltaspike).

```
sbt run-issues
```

### Multiple Windows

usage of multiple window controller

### Showcase

Showcase Demo as visual reference for sapphire-core key concepts.

### JFoenix Demo

[JFoenix Demo](https://github.com/sfxcode/sapphire-jfoenix-demo)

## Technology Stack

### Java  JDK 11/12

Sapphire runs agains the latest JDK 11/12 version.

### JavaFX

Java UI Application Framework as replacement for Swing.

Sapphire depends on OpenJFX 12

[https://openjfx.io](https://openjfx.io)

### Optional Dependency Injection (Issues Demo)

Sapphire use Apache Deltaspike as CDI Abstraction Layer (1.9.x).

[http://deltaspike.apache.org](http://deltaspike.apache.org)

The default implementation depends on Apache OpenWebBeans (2.0.x).

[http://openwebbeans.apache.org](http://openwebbeans.apache.org)

### Expression Language

Expressions are resolved by EL 3 [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html).

### ScalaFX (optional dependency)

A DSL for JavaFX written in Scala.

[ScalaFX Website](http://www.scalafx.org/)

ScalaFX plays very nice on top of sapphire-core applications.

## Features

sapphire-java makes rapid UI Development easy.
Multiple pre-build pattern help to fulfill most data centric parts.
Demo applications and showcase available as blueprints.

### Application Environment

- Application Controller
- UI Controller loading
- FXML Loading
- [Hot Reloding](https://sfxcode.github.io/sapphire-core/development.html)

### Bean Enhancement

- Every Java / Scala Bean can be used for [FXBean](https://sfxcode.github.io/sapphire-core/detail/fxbean.html)
- FXBean has additional support for java/scala Maps
- FXBean resolves Expressions on bean
- FXBean creates Properties needed for Binding on demand
- FXBean has change management by default

### Scala JavaFX Bean Binding

- Bindings by form id
- Binding with converter
- Adapter Pattern (FXBean Adapter)

### ViewController
- ContentManager for component exchange
- View Transitions
- Controller Lifecycle
- Node Locator
- DataTable  Controller (Filter Support)
- Master/Detail Controller

### FXML Support

- Easy Connect FXML with ViewController
- Different FXML path options
- Expression Language Support
- Custom Table Cell / Column Factories

### UI Widgets
- Searchable DataList
- Dual DataList

## maven

sapphire-javafx is deployed on bintray (jcenter) and maven central.

## Supporters

JetBrains is supporting this open source project with:

[![Intellij IDEA](http://www.jetbrains.com/img/logos/logo_intellij_idea.png)](http://www.jetbrains.com/idea/)

