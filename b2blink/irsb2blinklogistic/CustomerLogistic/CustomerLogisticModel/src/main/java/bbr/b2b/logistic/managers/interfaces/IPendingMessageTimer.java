package bbr.b2b.logistic.managers.interfaces;

public interface IPendingMessageTimer {

	public void scheduleTimer(long initialinterval, long milliseconds);
	
}
