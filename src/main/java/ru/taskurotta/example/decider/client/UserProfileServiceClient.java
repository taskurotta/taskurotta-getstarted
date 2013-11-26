package ru.taskurotta.example.decider.client;

import ru.taskurotta.annotation.WorkerClient;
import ru.taskurotta.core.Promise;
import ru.taskurotta.example.worker.profile.Profile;
import ru.taskurotta.example.worker.profile.UserProfileService;


@WorkerClient(worker = UserProfileService.class)
public interface UserProfileServiceClient {

    public Promise<Profile> get(String userId);

    public void blockNotification(String userId);
}
