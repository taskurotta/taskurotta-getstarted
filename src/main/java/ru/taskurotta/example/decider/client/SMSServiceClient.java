package ru.taskurotta.example.decider.client;

import ru.taskurotta.annotation.WorkerClient;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.worker.sms.SMSService;


@WorkerClient(worker = SMSService.class)
public interface SMSServiceClient {

    public Promise<Boolean> send(String number, String text);
}
