package bbr.b2b.regional.logistic.managers.interfaces;

public interface ILoadRecoveredMessagesManager {

	public void scheduleTimer(long initialinterval, long milliseconds);
}
