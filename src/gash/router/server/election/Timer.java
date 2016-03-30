package gash.router.server.election;

import java.util.concurrent.TimeUnit;

/**
 * Created by vinay on 3/30/16.
 */
public interface Timer {

    public interface TimeoutTask {
        void run(Timer.TimeoutHandle timeoutHandle);
    }

    public interface TimeoutHandle {
        boolean cancel();
    }

    TimeoutHandle newTimeout(TimeoutTask task, long relativeTimeout, TimeUnit timeUnit);
}
