# Tutorial Application

## Application object

A sapphire application must contain an Application object that extends SFXApplication.

All startup code is inside this SFXApplication pattern.


@@snip [Application.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/Application.scala)

## SFXApplicationController

Application controller is used for startup purposes.

Normally the main scene content is replaced by a SFXViewController.

Here we will use a MainViewController and later we connect a Navigation-, Workspace- and StatusBarController.

@@snip [ApplicationController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/ApplicationController.scala)

## MainViewController

The following code snippet loads the MainViewController by the [FXMLoader](https://github.com/sfxcode/sapphire-javafx/blob/master/src/main/scala/com/sfxcode/sapphire/javafx/fxml/FxmlLoading.scala) from the CDI managed ApplicationController Bean.

```scala
  lazy val mainViewController = getController[MainViewController]()

```

This pattern for Controller-Loading is commonly used in sapphire-javafx Framework.
