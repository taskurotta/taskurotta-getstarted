package ru.taskurotta.example.starter;

import ru.taskurotta.annotation.DeciderClient;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.decider.NotificationDecider;


@DeciderClient(decider = NotificationDecider.class)
public interface NotificationDeciderClient {

    public Promise<Void> start(String userId, String message);
}
