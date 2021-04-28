package bbr.b2b.extractors.timers.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.ServiceException;

public interface ITimerManager {

	public void execute() throws ServiceException;	
}
