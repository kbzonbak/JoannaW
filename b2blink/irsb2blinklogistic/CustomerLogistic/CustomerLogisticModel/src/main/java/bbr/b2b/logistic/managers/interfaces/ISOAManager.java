package bbr.b2b.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;

public interface ISOAManager {

	public void doReportingSalesMsge() throws OperationFailedException;
	public void doReportingInventaryMsge() throws OperationFailedException;
	
}
