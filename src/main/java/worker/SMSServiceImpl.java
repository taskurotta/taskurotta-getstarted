package worker;

import java.util.concurrent.ThreadLocalRandom;

public class SMSServiceImpl implements SMSService {
    @Override
    public boolean send(int number, String text) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
