# GrowthBook - JAVA SDK

Powerful feature flagging and A/B testing for Java apps using [GrowthBook](https://www.growthbook.io/)

- **Lightweight and fast**
    - **JDK 11 & Above**

## Installation

```
mvn clean package
```

Add dependency to your pom file

```
  <dependency>
    <groupId>com.fersoft</groupId>
    <artifactId>growthbook</artifactId>
    <version>1.0</version>
  </dependency>
```

## Usage

This library is based on the [GrowthBook SDK specs](https://docs.growthbook.io/lib/build-your-own) and should be
compatible
with the usage examples in the [GrowthBook docs](https://docs.growthbook.io/).
For detailed usage see unit tests in the project.

```
    GrowthBook growthBook=new GrowthBook(context);
    growthBook.run(experiment);
```