package worker;

import ru.taskurotta.annotation.Worker;

@Worker
public interface MailService {

    public boolean send(String email, String text);

}
