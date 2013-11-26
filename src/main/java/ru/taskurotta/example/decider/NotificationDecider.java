package ru.taskurotta.example.decider;


import ru.taskurotta.annotation.Decider;
import ru.taskurotta.annotation.Execute;


@Decider
public interface NotificationDecider {

    @Execute
    public void start(String userId, String message);
}
