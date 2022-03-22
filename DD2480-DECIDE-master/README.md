# DD2480 Group 7: DECIDE()

DECIDE() is a software application that generates a boolean signal determining whether an interceptor should be launched based on input radar tracking information. This radar tracking information is available at the instant the function is called.

DECIDE() determines which combination of the several possible Launch Interceptor Conditions (LIC’s) are relevant to the immediate situation. The interceptor launch button is normally considered locked; only if all relevant combinations of launch conditions are met will the launch-unlock signal be issued.

## Build requirements

A recent version of the Java JDK is necessary for building and testing the project. JDKs of version 11 and 13 have been tested to work. Ensure that environment variables are set correctly for automatic detection of the JDK.

## Build instructions

Building the project is automated using Gradlew. From the root directory of the project, running `./gradlew build` (on Linux or Mac OS X) or `.\gradlew.bat build` (on Windows) will compile the code in `src/main`. The resulting class files will be stored in the `bin` directory. Note that the current version of DECIDE does not support standard input, and is therefore useless on its own.

## Test instructions

All tests in `src/test` will be executed as part of the build process in the previous section. To rerun all tests, `./gradlew test --rerun-tasks` (on Linux or Mac OS X) or `.\gradlew.bat test --rerun-tasks` (on Windows) can be ran from the root directory of the project.

## Contributions

In general, everyone was helpfull in all areas of the project. Continuous dialog was held through meetings and discord. Major contributions of all members are mentioned below.

**Martijn Atema** (atema@kth.se)

- Implementing PUM and CMV functions
- Writing tests
- Setting up the testing framework
- Managing GitHub issues and reviewing pull requests

**Piere Colson** (coslon@kth.se)

- Implementing FUV and Launch functions
- Writing tests
- Solving bugs
- Managing GitHub issues and reviewing pull requests

**Hugo Heyman** (hheyman@kth.se)

- Implementing LIC 0 - 4
- Writing tests
- Communication

**Hafsa Aoutir** (hafsaa@kth.se)

- Implementing LIC 5 - 9
- Writing tests
- Debugging

**Linnea Bonnevier** (lmebo@kth.se)

- Implementing LIC 10 - 14
- Writing tests
- Managing GitHub issues and reviewing pull requests
