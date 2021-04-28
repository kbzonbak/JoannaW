package bbr.b2b.logistic.timers.managers.interfaces;

public interface Scheduler {

    void initialize(String info, long initialinterval, long milliseconds);
    void stop();
}