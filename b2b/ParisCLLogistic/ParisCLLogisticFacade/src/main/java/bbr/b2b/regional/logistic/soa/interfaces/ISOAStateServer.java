package bbr.b2b.regional.logistic.soa.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.regional.logistic.soa.entities.SOAState;

public interface ISOAStateServer extends IGenericServer<SOAState, Long, SOAStateW> {

	public SOAStateW addSOAState(SOAStateW soastate) throws OperationFailedException, NotFoundException;
	public SOAStateW[] getSOAStates() throws OperationFailedException, NotFoundException;
	public SOAStateW updateSOAState(SOAStateW soastate) throws OperationFailedException, NotFoundException;
}
