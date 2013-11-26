package ru.taskurotta.example.decider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.taskurotta.annotation.Asynchronous;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.decider.client.MailServiceClient;
import ru.taskurotta.example.decider.client.SMSServiceClient;
import ru.taskurotta.example.decider.client.UserProfileServiceClient;
import ru.taskurotta.example.worker.profile.Profile;

/**
 * User: romario
 * Date: 11/26/13
 * Time: 12:14 AM
 */
public class NotificationDeciderImpl implements NotificationDecider {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDeciderImpl.class);

    private UserProfileServiceClient userProfileService;
    private MailServiceClient mailService;
    private SMSServiceClient smsService;
    private NotificationDeciderImpl decider;

    @Override
    public void start(String userId, String message) {
        logger.info(".start(userId = [{}], message = [{}])", userId, message);

        Promise<Profile> profilePromise = userProfileService.get(userId);
        Promise<Boolean> sendResultPromise = decider.sendToTransport(profilePromise, message);
        decider.blockOnFail(userId, sendResultPromise);
    }

    @Asynchronous
    public Promise<Boolean> sendToTransport(Promise<Profile> profilePromise, String message) {
        logger.info(".sendToTransport(profilePromise = [{}], message = [{}])", profilePromise, message);

        Profile profile = profilePromise.get();

        switch (profile.getDeliveryType()) {
            case SMS: {
                return smsService.send(profile.getPhone(), message);
            }
            case EMAIL: {
                return mailService.send(profile.getEmail(), message);
            }

        }

        return Promise.asPromise(Boolean.TRUE);
    }


    @Asynchronous
    public void blockOnFail(String userId, Promise<Boolean> sendResultPromise) {
        logger.info(".blockOnFail(userId = [{}], sendResultPromise = [{}])", userId, sendResultPromise);

        if (!sendResultPromise.get()) {
            userProfileService.blockNotification(userId);
        }
    }


    public void setAsynchronousDecider(NotificationDeciderImpl decider) {
        this.decider = decider;
    }

    public void setSmsService(SMSServiceClient smsService) {
        this.smsService = smsService;
    }

    public void setMailService(MailServiceClient mailService) {
        this.mailService = mailService;
    }

    public void setUserProfileService(UserProfileServiceClient userProfileService) {
        this.userProfileService = userProfileService;
    }

}
