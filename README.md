# Java SE 19 - examples
The repository is dedicated to the features available in Java 19 version. The examples uses command line and are based on the OpenJDK 19 relase


### How to compile and run
```bash

# compile examples
$ javac --release 19 --enable-preview -g -classpath out -sourcepath src -d out  <JEP_FOLDER>/src/*.java

# run
$ java --enable-preview -classpath out  <EXECUTABLE_CLASS>

# with JFR recording
$ java --enable-preview -classpath out -XX:StartFlightRecording=settings=profile,filename=some_recording_file.jfr <EXECUTABLE_CLASS>
```


