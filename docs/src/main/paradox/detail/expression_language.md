# Expression Language

Expression Language is based on [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html).

## Usage

### Sample

@@snip [PersonBeanSpec.scala](../../../../../src/test/scala/com/sfxcode/sapphire/javafx/value/PersonBeanSpec.scala) { #expression }

### FXML

Usage in FXML is supported, but because of the actual use of the Dollar Char ($) you have to use ! instead.

So ```${_self.name()}``` and ```!{_self.name()}``` are evaluated equal.

```
<TableColumn prefWidth="200.0" text="Description">
   <cellValueFactory>
       <SFXTableValueFactory property="Name: !{_self.name()} Age: !{_self.age()} (!{_self.id()}) "/>
   </cellValueFactory>
</TableColumn>
```

## Functions

EL supports defining own functions in expressions. There is a FunctionHelper in
the Expressions object. Some Functions are predefined.

### Predefined Functions

Functions can have a prefix. The Sapphire Javafx Functions has the prefix: sf.

| function         | sample                                 | info                        |
|:-----------------|:---------------------------------------|:----------------------------|
| frameworkName    | ${sfx:frameworkName()}                  |                             |
| frameworkVersion | ${sfx:frameworkVersion()}               |                             |
| dateString       | ${sfx:dateString(testDate)}             | Default Pattern: YYYY-MM-dd |
| now              | ${sfx:frameworkName()}                  |                             |
| nowAsString      | ${sfx:nowAsString()}").toString         | Default Pattern: YYYY-MM-dd |
| boolString       | ${sfx:boolString(testBoolean,'Y', 'N')} |                             |
| configString     | ${sfx:configString('test.string')}      |                             |
| i18n             | ${sfx:i18n('personText')}               |                             |


## Default Usage

### EL in WindowController / ViewController

WindowController- and ViewController-Beans are automatically registered by name


