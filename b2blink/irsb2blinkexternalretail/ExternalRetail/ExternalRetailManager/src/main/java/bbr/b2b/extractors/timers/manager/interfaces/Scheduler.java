package bbr.b2b.extractors.timers.manager.interfaces;

public interface Scheduler {

    void initialize(String info, long initialinterval, long milliseconds);
    void stop();
}