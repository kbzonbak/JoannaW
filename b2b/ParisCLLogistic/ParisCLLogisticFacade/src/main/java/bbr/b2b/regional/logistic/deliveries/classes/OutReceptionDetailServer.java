package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetail;
import bbr.b2b.regional.logistic.buyorders.entities.PreDeliveryDetailId;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OutReceptionDetailW;
import bbr.b2b.regional.logistic.deliveries.entities.OutReception;
import bbr.b2b.regional.logistic.deliveries.entities.OutReceptionDetail;
import bbr.b2b.regional.logistic.deliveries.entities.OutReceptionDetailId;

@Stateless(name = "servers/deliveries/OutReceptionDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OutReceptionDetailServer extends BaseLogisticEJB3Server<OutReceptionDetail, OutReceptionDetailId, OutReceptionDetailW> implements OutReceptionDetailServerLocal {


	@EJB
	OutReceptionServerLocal outreceptionserver;

	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	public OutReceptionDetailW addOutReceptionDetail(OutReceptionDetailW outreceptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionDetailW) addIdentifiable(outreceptiondetail);
	}
	public OutReceptionDetailW[] getOutReceptionDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionDetailW[]) getIdentifiables();
	}
	public OutReceptionDetailW updateOutReceptionDetail(OutReceptionDetailW outreceptiondetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OutReceptionDetailW) updateIdentifiable(outreceptiondetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OutReceptionDetail entity, OutReceptionDetailW wrapper) {
		wrapper.setOutreceptionid(entity.getOutreception() != null ? new Long(entity.getOutreception().getId()) : new Long(0));
		wrapper.setOrderid(entity.getPredeliverydetail().getId() != null ? new Long(entity.getPredeliverydetail().getId().getOrderid()) : new Long(0));
		wrapper.setItemid(entity.getPredeliverydetail().getId() != null ? new Long(entity.getPredeliverydetail().getId().getItemid()) : new Long(0));
		wrapper.setLocationid(entity.getPredeliverydetail().getId() != null ? new Long(entity.getPredeliverydetail().getId().getLocationid()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(OutReceptionDetail entity, OutReceptionDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOutreceptionid() != null && wrapper.getOutreceptionid() > 0) { 
			OutReception outreception = outreceptionserver.getReferenceById(wrapper.getOutreceptionid());
			if (outreception != null) { 
				entity.setOutreception(outreception);
			}
		}
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getLocationid() != null && wrapper.getLocationid() > 0)) {
			PreDeliveryDetailId key = new PreDeliveryDetailId();
			key.setOrderid(wrapper.getOrderid());
			key.setItemid(wrapper.getItemid());
			key.setLocationid(wrapper.getLocationid());
			PreDeliveryDetail var = predeliverydetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setPredeliverydetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OutReceptionDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "OutReceptionDetail";
	}
}
