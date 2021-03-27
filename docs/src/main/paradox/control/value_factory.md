# Value Factory

SFX ValueFactories supports property and format.

* SFXTableCellFactory
* SFXTreeTableCellFactory

## Format

* Date and Number formats are supported

## Property

Property defines the key,  that is used to resolve the value for a key in SFXBean objects.

@@@ note { title=Hint }

Expressions in property are supported, but you have to use ! as expression prefix
@@@

## Sample

```
 <cellValueFactory>
      <SFXTreeTableValueFactory property="balance" format="#,##0.00" />
</cellValueFactory>
```

```
<cellValueFactory>
      <SFXTableValueFactory property="!{_self.age()} / !{_self.gender()}" />
</cellValueFactory>
```
