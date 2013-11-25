package worker;

import ru.taskurotta.annotation.Worker;

@Worker
public interface SMSService {

    public boolean send(int number, String text);

}
