package worker;

import java.util.concurrent.ThreadLocalRandom;

public class MailServiceImpl implements MailService {

    @Override
    public boolean send(String email, String text) {
        return ThreadLocalRandom.current().nextBoolean();
    }

}
