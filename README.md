## Assemble

### Clone repository

    git clone https://github.com/taskurotta/taskurotta.git
    cd taskurotta/

Checkout last tested version
    
    git checkout 738f5b7eff

### Download and install oracle driver

It is needed for integration tests and will be removed soon.

Download oracle driver 11.1.0.7.0 for jdk 1.5 (ojdbc5.jar) from http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html
Install driver to local maven repository. Note: You should specify correct path to file

    mvn install:install-file -DgroupId=com.oracle -DartifactId=oracle-jdbc15 -Dversion=11.1.0.7.0 -Dpackaging=jar -Dfile=/home/romario/Downloads/ojdbc5.jar -DgeneratePom=true
    
### Assemble Taskurotta

mvn install -DskipTests

## Test servers

Run first node:

    java -Xmx256m -DassetsMode=dev -Dts.node.custom.name="node1" -Ddw.http.port=8081 -Ddw.http.adminPort=9081 -Ddw.logging.file.currentLogFilename="assemble/target/server1.log" -jar assemble/target/assemble-0.3.0-SNAPSHOT.jar server assemble/src/main/resources/hz.yml
    
I other you can run second node:

    java -Xmx256m -DassetsMode=dev -Dts.node.custom.name="node2" -Ddw.http.port=8082 -Ddw.http.adminPort=9082 -Ddw.logging.file.currentLogFilename="assemble/target/server2.log" -jar assemble/target/assemble-0.3.0-SNAPSHOT.jar server assemble/src/main/resources/hz.yml
    
In both consoles should be message "Members [2] {" when both servers connected to each other.

Open console in browser:
    
    http://localhost:8081/index.html
or

    http://localhost:8082/index.html

You can use any server console late.

## Asseble simple process project

    git clone https://github.com/taskurotta/taskurotta-getstarted.git
    cd taskurotta-getstarted/
    
