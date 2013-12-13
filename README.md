This guide will help you to run example process with Taskurotta framework. Step-by-step, you'll assemble and
run Taskurotta server cluster, start actors and follow up the process workflow with the help of a web console.

## 1. Assemble Taskurotta framework

### Clone repository

    git clone https://github.com/taskurotta/taskurotta.git
    cd taskurotta/

Checkout last tested version

    git checkout develop
    git checkout <todo: set real version>

### Assemble Taskurotta framework

    mvn clean install -DskipTests

## 2. Start server cluster

Run cluster of two server nodes (it will use the same machine for test purposes). Note: you can run as many nodes as you need in production environment.
But be sure to correct the port numbers if you're going to use the same machine.

Run the first node:

    java -Xmx64m -Ddw.http.port=8081 -Ddw.http.adminPort=9081 -Ddw.logging.file.currentLogFilename="assemble/target/server1.log" -jar assemble/target/assemble-0.4.0-SNAPSHOT.jar server assemble/src/main/resources/hz.yml
    
Run the second node:

    java -Xmx64m -Ddw.http.port=8082 -Ddw.http.adminPort=9082 -Ddw.logging.file.currentLogFilename="assemble/target/server2.log" -jar assemble/target/assemble-0.4.0-SNAPSHOT.jar server assemble/src/main/resources/hz.yml
    
When both servers are connected to each other, a log message like this appears:

    Members [2] {
    	Member [192.168.1.2]:7777
    	Member [192.168.1.2]:7778 this
    }


Open console in a web browser:

[http://localhost:8081/index.html](http://localhost:8081/index.html) or [http://localhost:8082/index.html](http://localhost:8082/index.html)

You can use ANY node console later. In general, consoles provide the same information.

## 3. Run example process

### Clone repository

    git clone https://github.com/taskurotta/taskurotta-getstarted.git
    cd taskurotta-getstarted/
    git checkout develop

### Example process content

Package **ru.taskurotta.example.worker** contains three worker interfaces and their implementations.

Package **ru.taskurotta.example.decider** contains decider interface **NotificationDecider**, its implementation and
client interfaces which are used by the decider to communicate with workers.

**NotificationDeciderImlTest** class contains an example of decider initialization and testing code for two of its methods.

### Assemble example process project

    mvn clean install

### Submit process starters tasks

To do the actual job actor should obtain task from the server. So lets submit some tasks for a decider to start the example process.

    java -cp target/getstarted-process-1.0-SNAPSHOT.jar ru.taskurotta.example.starter.NotificationModule http://localhost:8081 91

Check the console [http://localhost:8081/index.html](http://localhost:8081/index.html) . Select "Queues" menu item. There should be 91 tasks in the
ru.taskurotta.example.decider.NotificationDecider#1.0 queue. They are the process starters tasks for deciders.

![Image](https://raw.github.com/taskurotta/taskurotta-getstarted/develop/img/step1.jpg)

### Run the decider

    java -Xmx64m -jar target/getstarted-process-1.0-SNAPSHOT.jar -f src/main/resources/config-decider.yml

The example decider uses server endpoint provided via YAML file and pointed to the first cluster node (port 8081).
Check src/main/resources/config-decider.yml for configuration details.

     spreader:
          - Spreader:
              class: ru.taskurotta.example.bootstrap.SimpleSpreaderConfig
              instance:
                endpoint: "http://localhost:8081"
                threadPoolSize: 10
                readTimeout: 0
                connectTimeout: 3000

The result of example decider execution is a task for the worker that would appear in worker's queue on server.
Every taskurotta actor is bind to the corresponding server queue and executes tasks from it.
Check the console [http://localhost:8081/index.html](http://localhost:8081/index.html) . On "Queues" menu item there should be 91 tasks in ru.taskurotta.example.worker.profile.UserProfileService#1.0 queue.

![Image](https://raw.github.com/taskurotta/taskurotta-getstarted/develop/img/step2.jpg)

### Run the workers

    java -Xmx64m -jar target/getstarted-process-1.0-SNAPSHOT.jar -f src/main/resources/config-workers.yml

The workers also use the endpoint provided via YAML configuration file, but they would poll the second cluster node (port 8082).
Check src/main/resources/config.yml for details.

    spreader:
      - Spreader:
          class: ru.taskurotta.example.bootstrap.SimpleSpreaderConfig
          instance:
            endpoint: "http://localhost:8082"
            threadPoolSize: 10
            readTimeout: 0
            connectTimeout: 3000

It should demonstrate how our small cluster works: test starter class creates processes on the first node [http://localhost:8081/index.html](http://localhost:8081/index.html) and
**decider** is started pointed to this node (**port 8081**) too.
But the **actors** execute processes by getting them from the second node [http://localhost:8082/index.html](http://localhost:8082/index.html).

If you open the web console on [http://localhost:8081/index.html](http://localhost:8081/index.html) or [http://localhost:8082/index.html](http://localhost:8082/index.html), you will see that all
queues have now zero tasks because all tasks have already been executed after actors run.

![Image](https://raw.github.com/taskurotta/taskurotta-getstarted/develop/img/step3.jpg)

Try to change the configuration and you will see that two taskurotta servers can be used vice versa.

