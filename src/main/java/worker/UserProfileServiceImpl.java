package worker;

import java.util.concurrent.ThreadLocalRandom;

public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public Profile get(String userId) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        Profile profile = new Profile();
        profile.setEmail(userId + "@example.com");
        profile.setPhone("910" + random.nextLong(1000000, 9999999));
        profile.setNotificationType(Profile.NotificationType.values()[random.nextInt(3)]);

        return profile;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void blockNotification(String userId) {
        // blockIt
    }
}
