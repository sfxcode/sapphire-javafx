# Expression Language

Expression Language is based on [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html) .

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


### Custom Functions

## Base Usage

## EL in WindowController / ViewController

WindowController- and ViewController-Beans are automatically registered by name


