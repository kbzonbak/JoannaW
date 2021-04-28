package bbr.b2b.regional.logistic.buyorders.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.entities.VeVUpdateType;

public interface IVeVUpdateTypeServer extends IElementServer<VeVUpdateType, VeVUpdateTypeW> {

	VeVUpdateTypeW addVeVUpdateType(VeVUpdateTypeW vevupdatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VeVUpdateTypeW[] getVeVUpdateTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VeVUpdateTypeW updateVeVUpdateType(VeVUpdateTypeW vevupdatetype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
