package ru.taskurotta.example.bootstrap;

import ru.taskurotta.bootstrap.config.SpreaderConfig;
import ru.taskurotta.client.ClientServiceManager;
import ru.taskurotta.client.TaskSpreader;
import ru.taskurotta.client.TaskSpreaderProvider;
import ru.taskurotta.client.internal.CommonClientServiceManager;
import ru.taskurotta.client.jersey.JerseyHttpTaskServerProxy;
import ru.taskurotta.util.ActorDefinition;


public class SimpleSpreaderConfig implements SpreaderConfig {

    private String endpoint = "http://localhost:8081";
    private int threadPoolSize = 10;
    private int readTimeout = 0;
    private int connectTimeout = 3000;
    private TaskSpreaderProvider taskSpreaderProvider;

    @Override
    public void init() {

        JerseyHttpTaskServerProxy taskServer = new JerseyHttpTaskServerProxy();
        taskServer.setEndpoint(endpoint);
        taskServer.setConnectTimeout(connectTimeout);
        taskServer.setReadTimeout(readTimeout);
        taskServer.setThreadPoolSize(threadPoolSize);
        taskServer.setMaxConnectionsPerHost(threadPoolSize);

        taskServer.init();

        ClientServiceManager clientServiceManager = new CommonClientServiceManager(taskServer);
        taskSpreaderProvider = clientServiceManager.getTaskSpreaderProvider();
    }

    @Override
    public TaskSpreader getTaskSpreader(Class aClass) {
        return taskSpreaderProvider.getTaskSpreader(ActorDefinition.valueOf(aClass));
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
