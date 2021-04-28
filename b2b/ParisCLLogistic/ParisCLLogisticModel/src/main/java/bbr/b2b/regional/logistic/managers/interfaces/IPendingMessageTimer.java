package bbr.b2b.regional.logistic.managers.interfaces;

public interface IPendingMessageTimer {

	public void scheduleTimer(long initialinterval, long milliseconds);
	
}
