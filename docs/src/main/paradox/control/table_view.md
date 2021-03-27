# TableView

For TableView with SFXBean objects create an ObservableList and use the property attribute in SFXTableValueFactory tag.

## Usage in SFXViewController

@@@ note { title=Hint }
To convert List[FXBean] to ObservableList you can use the trait SFXBeanConversions
@@@

@@snip [SimplePersonTableController.scala](../../../../../showcase/src/main/scala/com/sfxcode/sapphire/javafx/showcase/controller/table/SimplePersonTableController.scala) { #table_view }

## FXML Sample
@@snip [SimplePersonTable.fxml](../../../../../showcase/src/main/resources/fxml/SimplePersonTable.fxml) { #table_view }
