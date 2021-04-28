package bbr.b2b.extractors.manager.interfaces;

import javax.ejb.Local;
import bbr.b2b.extractors.timers.manager.interfaces.ITimerManager;

@Local
public interface FalabellaWSOrderManagerLocal extends ITimerManager{
	
	public void doProcessOC() throws Exception;
	
	public void doProcessOD() throws Exception;

	public void doProcessOE() throws Exception;

	public void doProcessVtaInv() throws Exception;

}

