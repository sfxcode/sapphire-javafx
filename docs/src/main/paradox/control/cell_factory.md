# Cell Factory

SFX CellFactories supports simpleClassName, packageName, alignment and converter property.

* SFXTableCellFactory
* SFXTreeTableCellFactory

## Class and Package

packeName is ```javafx.scene.control.cell.``` by default

TextFieldTableCell is used by default. For other cell Types you need to provide a simpleClassName.

simpleClassName: CheckBoxTableCell leads to javafx.scene.control.cell.CheckBoxTableCell,

To provide another class prefix for custom classes you have to overwrite the packageName

## Alignment

Possible values: center, left, right

## Converter

String converters by converter type name

converter: Double leads to DoubleStringConverter, Boolean leads to BooleanStringConverter

## Sample

```
<cellFactory>
     <SFXTableCellFactory simpleClassName="CheckBoxTableCell" alignment="right"/>
</cellFactory>
```

```
<cellFactory>
     <SFXTableCellFactory alignment="right" converter="Double"/>
</cellFactory>
```


