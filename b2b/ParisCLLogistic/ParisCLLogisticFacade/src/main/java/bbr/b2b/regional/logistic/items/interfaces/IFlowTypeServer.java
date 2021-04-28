package bbr.b2b.regional.logistic.items.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;

public interface IFlowTypeServer extends IElementServer<FlowType, FlowTypeW> {

	FlowTypeW addFlowType(FlowTypeW flowtype) throws AccessDeniedException, OperationFailedException, NotFoundException;
	FlowTypeW[] getFlowTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;
	FlowTypeW updateFlowType(FlowTypeW flowtype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
