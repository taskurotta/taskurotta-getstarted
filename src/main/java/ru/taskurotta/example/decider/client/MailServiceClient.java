package ru.taskurotta.example.decider.client;

import ru.taskurotta.annotation.WorkerClient;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.worker.mail.MailService;


@WorkerClient(worker = MailService.class)
public interface MailServiceClient {

    public Promise<Boolean> send(String email, String text);
}
