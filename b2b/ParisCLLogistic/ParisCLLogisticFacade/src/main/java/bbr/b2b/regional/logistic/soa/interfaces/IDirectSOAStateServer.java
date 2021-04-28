package bbr.b2b.regional.logistic.soa.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.soa.data.classes.DirectOrderSOAStateW;
import bbr.b2b.regional.logistic.soa.entities.DirectOrderSOAState;

public interface IDirectSOAStateServer extends IGenericServer<DirectOrderSOAState, Long, DirectOrderSOAStateW> {

	public DirectOrderSOAStateW addSOAState(DirectOrderSOAStateW soastate) throws OperationFailedException, NotFoundException;
	public DirectOrderSOAStateW[] getSOAStates() throws OperationFailedException, NotFoundException;
	public DirectOrderSOAStateW updateSOAState(DirectOrderSOAStateW soastate) throws OperationFailedException, NotFoundException;
}
