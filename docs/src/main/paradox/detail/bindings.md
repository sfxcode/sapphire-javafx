# SFXKeyBindings

SFXKeyBindings is a helper class for @ref:[SFXBeanAdapter](fxbean_adapter.md).
It provides Bindings from beans to ui representations.

It contains convenience functions for adding keys to @ref:[FXBean Adapter](fxbean_adapter.md) and for conversion.
## Code Sample

### FXML Snippet
```xml
<Label id="person"/>
<TextField id="name"/>
<TextField fx:id="age"/>

```
### Scala ViewController Snippet

@@snip [PersonPageController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/page/PersonPageController.scala) { #bindingList }

## Create Bindings

Bindings are created by the value of the id or fx:id  attributes in the fxml file.

@@@ note

If node lookup from appication root node does not work, you can fix it by adding a parent Node to FXBeanAdapter.

@@snip [PersonPageController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/page/PersonPageController.scala) { #adapter_create}

@@@



## Add Converter

@@snip [PersonPageController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/page/PersonPageController.scala) { #addConverter }
