package ru.taskurotta.example.worker.mail;

import ru.taskurotta.annotation.Worker;


@Worker
public interface MailService {

    public boolean send(String email, String text);

}
