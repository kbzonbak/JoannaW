package bbr.b2b.logistic.ddcorders.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderState;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderStateType;

@Stateless(name = "servers/ddcorders/DdcOrderStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderStateServer extends LogisticElementServer<DdcOrderState, DdcOrderStateW> implements DdcOrderStateServerLocal {


	@EJB
	DdcOrderServerLocal ddcorderserver;

	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;

	public DdcOrderStateW addDdcOrderState(DdcOrderStateW ddcorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateW) addElement(ddcorderstate);
	}
	public DdcOrderStateW[] getDdcOrderStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateW[]) getIdentifiables();
	}
	public DdcOrderStateW updateDdcOrderState(DdcOrderStateW ddcorderstate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderStateW) updateElement(ddcorderstate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrderState entity, DdcOrderStateW wrapper) {
		wrapper.setDdcorderid(entity.getDdcorder() != null ? new Long(entity.getDdcorder().getId()) : new Long(0));
		wrapper.setDdcorderstatetypeid(entity.getDdcorderstatetype() != null ? new Long(entity.getDdcorderstatetype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrderState entity, DdcOrderStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) { 
			DdcOrder ddcorder = ddcorderserver.getReferenceById(wrapper.getDdcorderid());
			if (ddcorder != null) { 
				entity.setDdcorder(ddcorder);
			}
		}
		if (wrapper.getDdcorderstatetypeid() != null && wrapper.getDdcorderstatetypeid() > 0) { 
			DdcOrderStateType ddcorderstatetype = ddcorderstatetypeserver.getReferenceById(wrapper.getDdcorderstatetypeid());
			if (ddcorderstatetype != null) { 
				entity.setDdcorderstatetype(ddcorderstatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrderState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrderState";
	}
}
