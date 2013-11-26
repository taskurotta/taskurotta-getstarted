package ru.taskurotta.example.starter;

import ru.taskurotta.client.ClientServiceManager;
import ru.taskurotta.client.DeciderClientProvider;
import ru.taskurotta.client.internal.CommonClientServiceManager;
import ru.taskurotta.client.jersey.JerseyHttpTaskServerProxy;

/**
 * User: romario
 * Date: 11/26/13
 * Time: 11:37 AM
 */
public class NotificationModule {

    // example options: http://localhost:8081 100
    public static void main(String[] args) {

        String endpoint = args[0];
        int processCount = Integer.valueOf(args[1]);

        NotificationDeciderClient deciderClient = getClient(endpoint);
        for (int i = 0; i < processCount; i++) {
            deciderClient.start("user_" + i, "text_" + i);
        }
    }

    public static NotificationDeciderClient getClient(String endpoint) {

        // TODO: should be refactored to simple API style (without spring taste)
        JerseyHttpTaskServerProxy taskServer = new JerseyHttpTaskServerProxy();
        taskServer.setEndpoint(endpoint);
        taskServer.setConnectTimeout(1000);
        taskServer.setReadTimeout(0);
        taskServer.setThreadPoolSize(1);
        taskServer.setMaxConnectionsPerHost(1);

        taskServer.init();

        ClientServiceManager clientServiceManager = new CommonClientServiceManager(taskServer);
        DeciderClientProvider deciderClientProvider = clientServiceManager.getDeciderClientProvider();


        return deciderClientProvider.getDeciderClient(NotificationDeciderClient.class);
    }
}
