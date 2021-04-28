package bbr.b2b.regional.logistic.managers.interfaces;

public interface IDeletePendingMailTimer {

	public void scheduleTimer(long initialinterval, long milliseconds);
	
}
