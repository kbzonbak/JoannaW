package bbr.b2b.regional.logistic.soa.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;

public interface ISOAStateTypeServer extends IGenericServer<SOAStateType, Long, SOAStateTypeW> {

	public SOAStateTypeW addSOAStateType(SOAStateTypeW soastatetype) throws OperationFailedException, NotFoundException;
	public SOAStateTypeW[] getSOAStateTypes() throws OperationFailedException, NotFoundException;
	public SOAStateTypeW updateSOAStateType(SOAStateTypeW soastatetype) throws OperationFailedException, NotFoundException;
}
