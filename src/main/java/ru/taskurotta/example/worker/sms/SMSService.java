package ru.taskurotta.example.worker.sms;

import ru.taskurotta.annotation.Worker;


@Worker
public interface SMSService {

    public boolean send(String number, String text);

}
