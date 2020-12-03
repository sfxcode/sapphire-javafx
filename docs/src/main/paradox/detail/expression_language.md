# Expression Language

Expression Language is based on [Tomcat Expression Language](https://tomcat.apache.org/tomcat-8.0-doc/elapi/index.html) .

## Functions

EL supports defining own functions in expressions. There is a FunctionHelper in
the Expressions object. Some Functions are predefined.

### Predefined Functions

Functions can have a prefix. The Sapphire Javafx Functions has the prefix: sf.

| function         | sample                                 | info                        |
|:-----------------|:---------------------------------------|:----------------------------|
| frameworkName    | ${sf:frameworkName()}                  |                             |
| frameworkVersion | ${sf:frameworkVersion()}               |                             |
| dateString       | ${sf:dateString(testDate)}             | Default Pattern: YYYY-MM-dd |
| now              | ${sf:frameworkName()}                  |                             |
| nowAsString      | ${sf:nowAsString()}").toString         | Default Pattern: YYYY-MM-dd |
| boolString       | ${sf:boolString(testBoolean,'Y', 'N')} |                             |
| configString     | ${sf:configString('test.string')}      |                             |
| i18n             | ${sf:i18n('personText')}               |                             |


### Custom Functions

## Base Usage

## EL in WindowController / ViewController

WindowController- and ViewController-Beans are automatically registered by name


