package bbr.b2b.regional.logistic.managers.interfaces;

public interface IPendingMailTimer {

	public void scheduleTimer(long initialinterval, long milliseconds);
}
