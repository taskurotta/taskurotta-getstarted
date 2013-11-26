package ru.taskurotta.example.decider;

import org.junit.Before;
import org.junit.Test;
import ru.taskurotta.ProxyFactory;
import ru.taskurotta.RuntimeProcessor;
import ru.taskurotta.RuntimeProvider;
import ru.taskurotta.RuntimeProviderManager;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.decider.client.MailServiceClient;
import ru.taskurotta.example.decider.client.SMSServiceClient;
import ru.taskurotta.example.decider.client.UserProfileServiceClient;
import ru.taskurotta.example.worker.profile.Profile;
import ru.taskurotta.example.worker.profile.ProfileUtil;
import ru.taskurotta.test.AssertFlow;

/**
 * User: romario
 * Date: 11/26/13
 * Time: 8:26 AM
 */
public class NotificationDeciderImplTest {

    // test initialization
    // ===================

    RuntimeProcessor runtimeProcessor;
    MailServiceClient mailService;
    SMSServiceClient smsService;
    UserProfileServiceClient userProfileService;
    NotificationDeciderImpl asynchronousDecider;

    @Before
    public void init() {

        // create stubs
        mailService = ProxyFactory.getWorkerClient(MailServiceClient.class);
        smsService = ProxyFactory.getWorkerClient(SMSServiceClient.class);
        userProfileService = ProxyFactory.getWorkerClient(UserProfileServiceClient.class);
        asynchronousDecider = ProxyFactory.getAsynchronousClient(NotificationDeciderImpl
                .class);

        // create and initialize decider instance
        NotificationDeciderImpl notificationDeciderImpl = new NotificationDeciderImpl();

        notificationDeciderImpl.setMailService(mailService);
        notificationDeciderImpl.setSmsService(smsService);
        notificationDeciderImpl.setUserProfileService(userProfileService);
        notificationDeciderImpl.setAsynchronousDecider(asynchronousDecider);


        // initialize runtime processor for decider implementation
        RuntimeProvider runtimeProvider = RuntimeProviderManager.getRuntimeProvider();
        runtimeProcessor = runtimeProvider.getRuntimeProcessor(notificationDeciderImpl);

    }


    private String userId = "alex";
    private String message = "text";

    @Test
    public void startMethodTest() {

        new AssertFlow(runtimeProcessor) {

            public void execute() {
                asynchronousDecider.start(userId, message);
            }

            public Promise expectedFlow() {

                Promise<Profile> profilePromise = userProfileService.get(userId);
                Promise<Boolean> sendResultPromise = asynchronousDecider.sendToTransport(profilePromise, message);
                asynchronousDecider.blockOnFail(userId, sendResultPromise);

                return null;
            }

        };

    }


    @Test
    public void sendToTransportMethodTest() {

        final Profile profile = ProfileUtil.createRandomProfile(userId);

        new AssertFlow(runtimeProcessor) {

            public void execute() {
                profile.setDeliveryType(Profile.DeliveryType.SMS);
                Promise<Profile> profilePromise = Promise.asPromise(profile);

                asynchronousDecider.sendToTransport(profilePromise, message);
            }

            public Promise expectedFlow() {

                return smsService.send(profile.getPhone(), message);
            }

        };

    }


}
