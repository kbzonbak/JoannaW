package bbr.b2b.regional.logistic.managers.interfaces;

public interface TimerSetupManagement {

	public void create() throws Exception;

	public void start() throws Exception;

	public void stop();

	public void destroy();

}
