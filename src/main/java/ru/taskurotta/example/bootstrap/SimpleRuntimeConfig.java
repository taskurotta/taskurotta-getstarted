package ru.taskurotta.example.bootstrap;

import ru.taskurotta.ProxyFactory;
import ru.taskurotta.RuntimeProcessor;
import ru.taskurotta.RuntimeProvider;
import ru.taskurotta.RuntimeProviderManager;
import ru.taskurotta.bootstrap.config.RuntimeConfig;
import ru.taskurotta.example.decider.NotificationDecider;
import ru.taskurotta.example.decider.NotificationDeciderImpl;
import ru.taskurotta.example.decider.client.MailServiceClient;
import ru.taskurotta.example.decider.client.SMSServiceClient;
import ru.taskurotta.example.decider.client.UserProfileServiceClient;
import ru.taskurotta.example.worker.mail.MailService;
import ru.taskurotta.example.worker.mail.MailServiceImpl;
import ru.taskurotta.example.worker.profile.UserProfileService;
import ru.taskurotta.example.worker.profile.UserProfileServiceImpl;
import ru.taskurotta.example.worker.sms.SMSService;
import ru.taskurotta.example.worker.sms.SMSServiceImpl;

import java.util.HashMap;
import java.util.Map;


public class SimpleRuntimeConfig implements RuntimeConfig {

    private Map<Class, RuntimeProcessor> processors = new HashMap<>();
    private String context;

    @Override
    public void init() {

        // instantiate Workers instances
        SMSService smsService = new SMSServiceImpl();
        MailService mailService = new MailServiceImpl();
        UserProfileService userProfileService = new UserProfileServiceImpl();

        // initialize decider fields of client stubs
        MailServiceClient mailServiceClient = ProxyFactory.getWorkerClient(MailServiceClient.class);
        SMSServiceClient smsServiceClient = ProxyFactory.getWorkerClient(SMSServiceClient.class);
        UserProfileServiceClient userProfileServiceClient = ProxyFactory.getWorkerClient(UserProfileServiceClient
                .class);
        NotificationDeciderImpl asynchronousDecider = ProxyFactory.getAsynchronousClient(NotificationDeciderImpl
                .class);


        // create and initialize decider instance
        NotificationDeciderImpl notificationDeciderImpl = new NotificationDeciderImpl();

        notificationDeciderImpl.setMailService(mailServiceClient);
        notificationDeciderImpl.setSmsService(smsServiceClient);
        notificationDeciderImpl.setUserProfileService(userProfileServiceClient);
        notificationDeciderImpl.setAsynchronousDecider(asynchronousDecider);


        // prepare processors map
        RuntimeProvider runtimeProvider = RuntimeProviderManager.getRuntimeProvider();

        processors.put(SMSService.class, runtimeProvider.getRuntimeProcessor(smsService));
        processors.put(MailService.class, runtimeProvider.getRuntimeProcessor(mailService));
        processors.put(UserProfileService.class, runtimeProvider.getRuntimeProcessor(userProfileService));
        processors.put(NotificationDecider.class, runtimeProvider.getRuntimeProcessor(notificationDeciderImpl));

    }

    @Override
    public RuntimeProcessor getRuntimeProcessor(Class aClass) {

        return processors.get(aClass);
    }

    public void setContext(String context) {
        this.context = context;
    }
}
