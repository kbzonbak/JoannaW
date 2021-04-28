package bbr.b2b.logistic.dvrdeliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.dvrdeliveries.entities.Bulk;
import bbr.b2b.logistic.dvrdeliveries.entities.BulkDetail;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkDetailW;
import bbr.b2b.logistic.dvrdeliveries.entities.BulkDetailId;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetail;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetailId;

@Stateless(name = "servers/dvrdeliveries/BulkDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BulkDetailServer extends BaseLogisticEJB3Server<BulkDetail, BulkDetailId, BulkDetailW> implements BulkDetailServerLocal {


	@EJB
	BulkServerLocal bulkserver;

	@EJB
	DvrOrderDeliveryDetailServerLocal dvrorderdeliverydetailserver;

	public BulkDetailW addBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW) addIdentifiable(bulkdetail);
	}
	public BulkDetailW[] getBulkDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW[]) getIdentifiables();
	}
	public BulkDetailW updateBulkDetail(BulkDetailW bulkdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkDetailW) updateIdentifiable(bulkdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(BulkDetail entity, BulkDetailW wrapper) {
		wrapper.setBulkid(entity.getBulk() != null ? new Long(entity.getBulk().getId()) : new Long(0));
		wrapper.setDvrorderid(entity.getDvrorderdeliverydetail().getId() != null ? new Long(entity.getDvrorderdeliverydetail().getId().getDvrorderid()) : new Long(0));
		wrapper.setDvrdeliveryid(entity.getDvrorderdeliverydetail().getId() != null ? new Long(entity.getDvrorderdeliverydetail().getId().getDvrdeliveryid()) : new Long(0));
		wrapper.setItemid(entity.getDvrorderdeliverydetail().getId() != null ? new Long(entity.getDvrorderdeliverydetail().getId().getItemid()) : new Long(0));
		wrapper.setLocationid(entity.getDvrorderdeliverydetail().getId() != null ? new Long(entity.getDvrorderdeliverydetail().getId().getLocationid()) : new Long(0));
		wrapper.setPosition(entity.getDvrorderdeliverydetail().getId() != null ? new Integer(entity.getDvrorderdeliverydetail().getId().getPosition()) : new Integer(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(BulkDetail entity, BulkDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getBulkid() != null && wrapper.getBulkid() > 0) { 
			Bulk bulk = bulkserver.getReferenceById(wrapper.getBulkid());
			if (bulk != null) { 
				entity.setBulk(bulk);
			}
		}
		if ((wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) && (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) && (wrapper.getPosition() != null && wrapper.getPosition() > 0)) {
			DvrOrderDeliveryDetailId key = new DvrOrderDeliveryDetailId();
			key.setDvrorderid(wrapper.getDvrorderid());
			key.setDvrdeliveryid(wrapper.getDvrdeliveryid());
			key.setItemid(wrapper.getItemid());
			key.setLocationid(wrapper.getLocationid());
			key.setPosition(wrapper.getPosition());
			DvrOrderDeliveryDetail var = dvrorderdeliverydetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDvrorderdeliverydetail(var);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "BulkDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "BulkDetail";
	}
}
