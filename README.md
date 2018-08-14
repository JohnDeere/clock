[![Build Status](https://travis-ci.org/JohnDeere/clock.svg?branch=master)](https://travis-ci.org/JohnDeere/clock)

Clock
=====

System Clock Abstractions &amp; Joda DateTime providers.

## Installation
```xml
<dependency>
    <groupId>com.deere</groupId>
    <artifactId>clock</artifactId>
    <version>3.0.0</version>
</dependency>
```

## Usage
Getting the current date-time in UTC
```java
   DateTime now = Clock.now();
   Long nowAsMillies = Clock.milliseconds();
   DateTime theUnixEpoch = Clock.epoch(); // -> 1970-01-01:00:00:00
```

## Freezing the clock
  Many time in tests you might want to set the system clock to a specific time or simply stop it from progressing.
  You can do this with __freeze__. Note that because the clock is static (like the real clock), you must __unfreeze__ it
  in your teardown.

```java
   Clock.freeze(2011,09,28,4,20); // by a full date-time
   Clock.freeze(2011,09,28); // by just the date
   Clock.freeze(420); // by millies
   Clock.freeze(new DateTime()); // by a specific datetime instance
   Clock.freeze(); //Just stops the clock at the current datetime.
   Clock.freeze(new YourOwnClock()); // define time on your own terms

   Clock.clear() // unfreezes the clock and returns it to system clock progression.
```

## Testing Locally
This builds with [Maven 3.0](https://maven.apache.org/docs/3.0/release-notes.html) 
and [Java 8](http://openjdk.java.net/install/)

```bash
mvn clean verify
```

## Contributing
Please see the [Contribution Guidelines](./.github/CONTRIBUTING.md).
