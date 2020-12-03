# FXBean Adapter

FXBeanAdapter reflects changes of bean properties to the UI and vice versa.

It automatically creates bidirectional bindings to any bean.On bean update old bindings are cleared and new bindings for the new bean are created.




## Example

### Define Adapter
@@snip [PersonController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/PersonController.scala) { #adapter_create}

### Add Bindings and Converter
@@snip [PersonController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/PersonController.scala) { #bindings}

### Change Adapter items
@@snip [PersonController.scala](../../../../../demos/tutorial/src/main/scala/com/sfxcode/sapphire/javafx/demo/tutorial/controller/PersonController.scala) { #adapter_use}
