//package gash.router.server.election;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.TimerTask;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by vinay on 3/30/16.
// */
//public class WrappedTimer implements Timer {
//
//    private final class WrappedTimeoutHandle implements TimeoutHandle {
//
//        private final TimeoutTask task;
//        private final TimerTask timerTask;
//
//        private WrappedTimeoutHandle(TimeoutTask task) {
//            this.task = task;
//            this.timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    WrappedTimeoutHandle.this.task.run(WrappedTimeoutHandle.this);
//                }
//            };
//        }
//
//        @Override
//        public final boolean cancel() {
//            return timerTask.cancel();
//        }
//
//        private TimerTask getTimerTask() {
//            return timerTask;
//        }
//    }
//
//    private boolean running;
//    private java.util.Timer timer;
//
//    /**
//     * Start the timer.
//     * <p/>
//     * Creates the underlying {@code java.util.Timer} and its task-running daemon thread.
//     * Following a successful call to {@code start()} subsequent calls are noops.
//     */
//    public synchronized void start() {
//        if (!running) {
//            checkState(timer == null);
//
//            timer = new java.util.Timer(true); // use daemon thread internally
//            schedulePurging(); // schedule timeout to purge cancelled tasks from the timer task queue
//
//            running = true;
//        }
//    }
//
//    // NOTE: I explicitly chose not to execute any outstanding tasks
//    // when stop() is called. stop() may be called in both normal and
//    // error conditions. If its called in error conditions the system
//    // may be in an undefined state, and running any additional tasks
//    // may exacerbate the situation, and are unlikely to succeed.
//    /**
//     * Stop the timer.
//     * <p/>
//     * Terminates the task-running daemon thread. Outstanding tasks
//     * are <strong>not</strong> executed. Following a successful call
//     * to {@code stop()} subsequent calls are noops.
//     */
//    public synchronized void stop() {
//        if (running) {
//            timer.cancel();
//            timer = null;
//            running = false;
//        }
//    }
//
//    @Override
//    public synchronized TimeoutHandle newTimeout(final TimeoutTask task, long timeout, TimeUnit timeUnit) {
//        checkArgument(timeout >= 0, "negative timeout:%s", timeout);
//
//        WrappedTimeoutHandle timeoutHandle = new WrappedTimeoutHandle(task);
//
//        checkState(running, "timer not running");
//        timer.schedule(timeoutHandle.getTimerTask(), timeUnit.toMillis(timeout));
//
//        return timeoutHandle;
//    }
//
//    private void schedulePurging() {
//        try {
//            timer.scheduleAtFixedRate(new TimerTask() {
//                @Override
//                public void run() {
//                    try {
//                        timer.purge();
//                    } catch (IllegalStateException e) {
//                        LOGGER.warn("purging failed - timer cancelled");
//                    }
//                }
//            }, 0, TimeUnit.SECONDS.toMillis(30));
//        } catch (IllegalStateException e) {
//            LOGGER.warn("purge scheduling failed - timer cancelled");
//        }
//    }
//
//}
