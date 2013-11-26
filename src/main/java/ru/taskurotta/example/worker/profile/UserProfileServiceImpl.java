package ru.taskurotta.example.worker.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;


public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Override
    public Profile get(String userId) {
        logger.info(".get(userId = [{}]", userId);

        return ProfileUtil.createRandomProfile(userId);
    }

    @Override
    public void blockNotification(String userId) {
        logger.info(".blockNotification(userId = [{}]", userId);

        // blockIt
    }
}
