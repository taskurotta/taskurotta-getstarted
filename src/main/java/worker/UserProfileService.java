package worker;

import ru.taskurotta.annotation.Worker;

@Worker
public interface UserProfileService {

    public Profile get(String userId);

    public void blockNotification(String userId);

}
