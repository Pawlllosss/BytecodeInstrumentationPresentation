# Java Bytecode instrumentation presentation
Presentation on the topic of the Java bytecode instrumentation. It was preapred for the Weekly Knowledge Sharing session within my company.

# Tools

* Java 11
* Javaassist
* Gradle

# Repository structure

It consist of four folders `part1`, `part2`, `part3` and `presentation`. First three contain source codes of apps used for the presentation.

# How to run

Every app need to be build manually with use of the `./gradlew build` command

## part1 (Static instrumentation)

`./App/script1.sh decompile` - shows bytecode of the `SamplePrinter` class

`./App/script1.sh run` - runs example application

`./App/script1.sh run_with_example_args` - runs instrumented example application with argument `part1/SamplePrinter+part1/Main` (class names to instrument are separated by `+`)

`./App/script1.sh run_with_args class1+class2` - runs instrumented example application with arguments

## part2 (Annotation based static instrumentation)

`./App/script2.sh run` - runs example application

`./App/script2.sh run_instrumented` - runs instrumented example application, it instruments methods within class anotated with `@DetailedLogs`


## part3 (Dynamic instrumentation of the Spring Boot application)


`./WebApp/script3.sh list_jvms` - lists running jvm instances

`./WebApp/script3.sh run` - runs example Spring application

`./WebApp/script3.sh attach_agent_with_example_args` - attach agent to *already running* Spring application with argument `pl.oczadly.example.webapp.matter.control.MatterService` (name of class to check for `@DetailedLogs` annotation)

`./WebApp/script3.sh attach_agent class1+class2` - attach agent to *already running* Spring application with arguments


# Sources
When I was preparing presentation I stumbled upon a lot of the useful materials on the blogs, in the documentation or within Stack Overflow answers. I've tried to list them all here:

* https://www.javassist.org/tutorial/tutorial.html
* https://javapapers.com/core-java/java-instrumentation/
* https://medium.com/@jakubhal/instrumentation-of-spring-boot-application-with-byte-buddy-bbd28619b7c
* https://web.archive.org/web/20141014195801/http://dhruba.name/2010/02/07/creation-dynamic-loading-and-instrumentation-with-javaagents/
* https://www.infoq.com/articles/Living-Matrix-Bytecode-Manipulation/
* https://blog.newrelic.com/engineering/diving-bytecode-manipulation-creating-audit-log-asm-javassist/
* https://stackoverflow.com/questions/18567552/how-to-retransform-a-class-at-runtime
