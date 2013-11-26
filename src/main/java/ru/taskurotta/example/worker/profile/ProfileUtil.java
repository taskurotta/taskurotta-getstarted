package ru.taskurotta.example.worker.profile;


import java.util.concurrent.ThreadLocalRandom;

public class ProfileUtil {

    public static Profile createRandomProfile(String userId) {

        ThreadLocalRandom random = ThreadLocalRandom.current();

        Profile profile = new Profile();
        profile.setEmail(userId + "@example.com");
        profile.setPhone("910" + random.nextLong(1000000, 9999999));
        profile.setDeliveryType(Profile.DeliveryType.values()[random.nextInt(3)]);

        return profile;
    }
}
