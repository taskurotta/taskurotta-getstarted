package ru.taskurotta.example.worker.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;


public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public boolean send(String email, String text) {
        logger.info(".send(email = [{}], text = [{}])", email, text);

        return ThreadLocalRandom.current().nextBoolean();
    }

}
