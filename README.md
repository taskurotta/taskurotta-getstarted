## Assemble server of Taskurotta

### Clone repository

    git clone https://github.com/taskurotta/taskurotta.git
    cd taskurotta/

Checkout last tested version
    
    git checkout 246146d

### Assemble Taskurotta

    mvn install -DskipTests

## Test servers

Run two nodes on same physical server (for test reason). Note: you can run as many nodes as you need.
Please correct port numbers if you will use same physical server.

Run first node:

    java -Xmx256m -DassetsMode=dev -Dts.node.custom.name="node1" -Ddw.http.port=8081 -Ddw.http.adminPort=9081 -Ddw.logging.file.currentLogFilename="assemble/target/server1.log" -jar assemble/target/assemble-0.3.1.jar server assemble/src/main/resources/hz.yml
    
Run second node:

    java -Xmx256m -DassetsMode=dev -Dts.node.custom.name="node2" -Ddw.http.port=8082 -Ddw.http.adminPort=9082 -Ddw.logging.file.currentLogFilename="assemble/target/server2.log" -jar assemble/target/assemble-0.3.1.jar server assemble/src/main/resources/hz.yml
    
When both servers connected to each other, you can find message like this

    Members [2] {
    	Member [192.168.1.2]:7777
    	Member [192.168.1.2]:7778 this
    }


Open console in browser:

[http://localhost:8081/index.html](http://localhost:8081/index.html) or [http://localhost:8082/index.html](http://localhost:8082/index.html)

You can use ANY node console later. In general, consoles provide the same information.
Note: console is not implemented yet. It is support all features only in configuration with mongodb and oracle
backends.

## Assemble simple process project

    git clone https://github.com/taskurotta/taskurotta-getstarted.git
    cd taskurotta-getstarted/

### Create and test process

Package ru.taskurotta.example.worker contains three worker interfaces and its implementations.

Package ru.taskurotta.example.decider contains decider interface NotificationDecider, its implementation and
clients interfaces which are used by decider to communicate with workers.

NotificationDeciderImlTest class contains example of decider initialisation and testing of two its methods.

### Create and test starter of process

    mvn install
    java -cp target/process-1.0-SNAPSHOT.jar ru.taskurotta.example.starter.NotificationModule http://localhost:8081 91

Check console [http://localhost:8081/index.html](http://localhost:8081/index.html) . Select "Queues" menu item. There should be 91 tasks in
ru.taskurotta.example.decider.NotificationDecider#1.0 queue.

### Run decider

    java -Xmx256m -jar target/process-1.0-SNAPSHOT.jar -f src/main/resources/config-decider.yml

Our decider going to register on taskurotta to port 8081 just check src/main/resources/config-decider.yml

     spreader:
          - Spreader:
              class: ru.taskurotta.example.bootstrap.SimpleSpreaderConfig
              instance:
                endpoint: "http://localhost:8081"
                threadPoolSize: 10
                readTimeout: 0
                connectTimeout: 3000

After decider start. Check console [http://localhost:8081/index.html](http://localhost:8081/index.html) . Select "Queues" menu item. There should be 91 tasks in ru.taskurotta.example.worker.profile.UserProfileService#1.0 queue.
### Run actors

    java -Xmx256m -jar target/process-1.0-SNAPSHOT.jar -f src/main/resources/config-actors.yml

Our actors going to ask taskurotta to port 8082 just check src/main/resources/config.yml

    spreader:
      - Spreader:
          class: ru.taskurotta.example.bootstrap.SimpleSpreaderConfig
          instance:
            endpoint: "http://localhost:8082"
            threadPoolSize: 10
            readTimeout: 0
            connectTimeout: 3000

Now you see how our small cluster works. Test starter creates processes on localhost:8081 and decider started with registration on port 8081.
But our actors executes processes getting them from localhost:8082. If you open web console on [http://localhost:8081/index.html](http://localhost:8081/index.html) or on [http://localhost:8082/index.html](http://localhost:8082/index.html), you will see that all queues have zero tasks because all already executed after actors run.
Try to change configuration and you will see that two taskurotta servers can be used vice versa.

