package bbr.b2b.regional.logistic.items.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;

@Stateless(name = "servers/items/FlowTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FlowTypeServer extends LogisticElementServer<FlowType, FlowTypeW> implements FlowTypeServerLocal {


	public FlowTypeW addFlowType(FlowTypeW flowtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FlowTypeW) addElement(flowtype);
	}
	public FlowTypeW[] getFlowTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FlowTypeW[]) getIdentifiables();
	}
	public FlowTypeW updateFlowType(FlowTypeW flowtype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FlowTypeW) updateElement(flowtype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(FlowType entity, FlowTypeW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(FlowType entity, FlowTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "FlowType";
	}
	@Override
	protected void setEntityname() {
		entityname = "FlowType";
	}
}
