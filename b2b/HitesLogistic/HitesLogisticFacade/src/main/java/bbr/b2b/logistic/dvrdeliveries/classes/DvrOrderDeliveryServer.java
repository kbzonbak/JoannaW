package bbr.b2b.logistic.dvrdeliveries.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryId;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderToDeliveryUnitsDTO;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;

@Stateless(name = "servers/dvrdeliveries/DvrOrderDeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DvrOrderDeliveryServer extends BaseLogisticEJB3Server<DvrOrderDelivery, DvrOrderDeliveryId, DvrOrderDeliveryW> implements DvrOrderDeliveryServerLocal {


	@EJB
	DvrOrderServerLocal dvrorderserver;

	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	public DvrOrderDeliveryW addDvrOrderDelivery(DvrOrderDeliveryW dvrorderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryW) addIdentifiable(dvrorderdelivery);
	}
	public DvrOrderDeliveryW[] getDvrOrderDeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryW[]) getIdentifiables();
	}
	public DvrOrderDeliveryW updateDvrOrderDelivery(DvrOrderDeliveryW dvrorderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DvrOrderDeliveryW) updateIdentifiable(dvrorderdelivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DvrOrderDelivery entity, DvrOrderDeliveryW wrapper) {
		wrapper.setDvrorderid(entity.getDvrorder() != null ? new Long(entity.getDvrorder().getId()) : new Long(0));
		wrapper.setDvrdeliveryid(entity.getDvrdelivery() != null ? new Long(entity.getDvrdelivery().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DvrOrderDelivery entity, DvrOrderDeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) { 
			DvrOrder dvrorder = dvrorderserver.getReferenceById(wrapper.getDvrorderid());
			if (dvrorder != null) { 
				entity.setDvrorder(dvrorder);
			}
		}
		if (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) { 
			DvrDelivery dvrdelivery = dvrdeliveryserver.getReferenceById(wrapper.getDvrdeliveryid());
			if (dvrdelivery != null) { 
				entity.setDvrdelivery(dvrdelivery);
			}
		}
	}

	public DvrOrderToDeliveryUnitsDTO[] getDvrOrderDeliveryUnits(Long dvrdeliveryid){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery.getDvrOrderDeliveryUnits");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DvrOrderToDeliveryUnitsDTO.class));
		List<?> list = query.list();
		DvrOrderToDeliveryUnitsDTO[] result = (DvrOrderToDeliveryUnitsDTO[]) list.toArray(new DvrOrderToDeliveryUnitsDTO[list.size()]);		
		return result;
		
	}
	
	public void doUpdateEstimatedUnits(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDelivery.doUpdateEstimatedUnits");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		query.executeUpdate();
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DvrOrderDelivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "DvrOrderDelivery";
	}
}
