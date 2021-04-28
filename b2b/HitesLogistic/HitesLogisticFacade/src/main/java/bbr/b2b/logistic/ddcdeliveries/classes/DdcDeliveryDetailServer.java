package bbr.b2b.logistic.ddcdeliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryDetailW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryDetail;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryDetailId;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.entities.Item;

@Stateless(name = "servers/ddcdeliveries/DdcDeliveryDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcDeliveryDetailServer extends BaseLogisticEJB3Server<DdcDeliveryDetail, DdcDeliveryDetailId, DdcDeliveryDetailW> implements DdcDeliveryDetailServerLocal {

	@EJB
	DdcDeliveryServerLocal ddcdeliveryserver;
	
	@EJB
	ItemServerLocal itemserver;

	public DdcDeliveryDetailW addDdcDeliveryDetail(DdcDeliveryDetailW ddcdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryDetailW) addIdentifiable(ddcdeliverydetail);
	}
	
	public DdcDeliveryDetailW[] getDdcDeliveryDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryDetailW[]) getIdentifiables();
	}
	
	public DdcDeliveryDetailW updateDdcDeliveryDetail(DdcDeliveryDetailW ddcdeliverydetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryDetailW) updateIdentifiable(ddcdeliverydetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcDeliveryDetail entity, DdcDeliveryDetailW wrapper) {
		wrapper.setDdcdeliveryid(entity.getDdcdelivery().getId() != null ? new Long(entity.getDdcdelivery().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem().getId() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcDeliveryDetail entity, DdcDeliveryDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcdeliveryid() != null && wrapper.getDdcdeliveryid() > 0) {
			DdcDelivery ddcdelivery = ddcdeliveryserver.getReferenceById(wrapper.getDdcdeliveryid());
			if (ddcdelivery != null) {
				entity.setDdcdelivery(ddcdelivery);
			}
		}
		if (wrapper.getItemid() > 0) {
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) {
				entity.setItem(item);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcDeliveryDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcDeliveryDetail";
	}
}
