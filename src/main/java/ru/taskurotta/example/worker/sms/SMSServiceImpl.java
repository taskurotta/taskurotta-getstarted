package ru.taskurotta.example.worker.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;


public class SMSServiceImpl implements SMSService {

    private static final Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);

    @Override
    public boolean send(String number, String text) {
        logger.info(".sendMessage(number = [{}], text = [{}])", number, text);

        return ThreadLocalRandom.current().nextBoolean();
    }
}
