package bbr.b2b.regional.logistic.couriers.classes;

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
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChilexpressCourierStateTmpW;
import bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp;
import bbr.b2b.regional.logistic.couriers.entities.CourierTag;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;

@Stateless(name = "servers/couriers/ChilexpressCourierStateTmpServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChilexpressCourierStateTmpServer extends LogisticElementServer<ChilexpressCourierStateTmp, ChilexpressCourierStateTmpW> implements ChilexpressCourierStateTmpServerLocal {
	
	@EJB
	DirectOrderServerLocal directOrderServerLocal;
	
	@EJB
	DODeliveryServerLocal doDeliveryServerLocal;
	
	@EJB
	CourierTagServerLocal courierTagServerLocal;
	
	@Override
	protected void copyRelationsEntityToWrapper(ChilexpressCourierStateTmp entity, ChilexpressCourierStateTmpW wrapper) {
		wrapper.setDirectorderid(entity.getDirectorder() != null ? new Long(entity.getDirectorder().getId()) : new Long(0));
		wrapper.setDodeliveryid(entity.getDodelivery() != null ? new Long(entity.getDodelivery().getId()) : new Long(0));
		wrapper.setCouriertagid(entity.getCouriertag() != null ? new Long(entity.getCouriertag().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(ChilexpressCourierStateTmp entity, ChilexpressCourierStateTmpW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDirectorderid() != null && wrapper.getDirectorderid() > 0) { 
			DirectOrder directorder = directOrderServerLocal.getReferenceById(wrapper.getDirectorderid());
			if (directorder != null) { 
				entity.setDirectorder(directorder);
			}
		}
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) { 
			DODelivery dodelivery = doDeliveryServerLocal.getReferenceById(wrapper.getDodeliveryid());
			if (dodelivery != null) { 
				entity.setDodelivery(dodelivery);
			}
		}
		if (wrapper.getCouriertagid() != null && wrapper.getCouriertagid() > 0) { 
			CourierTag couriertag = courierTagServerLocal.getReferenceById(wrapper.getCouriertagid());
			if (couriertag != null) { 
				entity.setCouriertag(couriertag);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ChilexpressCourierStateTmp";
	}
	@Override
	protected void setEntityname() {
		entityname = "ChilexpressCourierStateTmp";
	}
	
	public ChilexpressCourierStateTmpW addChilexpressCourierStateTmp(ChilexpressCourierStateTmpW chilexpressCourierStateTmp) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChilexpressCourierStateTmpW) addElement(chilexpressCourierStateTmp);
	}
	
	public ChilexpressCourierStateTmpW[] getChilexpressCourierStateTmps() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChilexpressCourierStateTmpW[]) getIdentifiables();
	}
	
	public ChilexpressCourierStateTmpW updateChilexpressCourierStateTmp(ChilexpressCourierStateTmpW chilexpressCourierStateTmp) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChilexpressCourierStateTmpW) updateElement(chilexpressCourierStateTmp);
	}
	
	public ChilexpressCourierStateTmpW[] getFirstFileOutdatedChilexpressCourierStates() {
		int max = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxuploadedcourierstatesregisters"));
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp.getFirstFileOutdatedChilexpressCourierStates");
		query.setInteger("max", max);
		query.setResultTransformer(new LowerCaseResultTransformer(ChilexpressCourierStateTmpW.class));
		List list = query.list();
		ChilexpressCourierStateTmpW[] result = (ChilexpressCourierStateTmpW[]) list.toArray(new ChilexpressCourierStateTmpW[list.size()]);
		return result;
	}
	
	public ChilexpressCourierStateTmpW[] getFirstFileUpdatedChilexpressCourierStates() {
		int max = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierstatesdeliveries"));
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp.getFirstFileUpdatedChilexpressCourierStates");
		query.setInteger("max", max);
		query.setResultTransformer(new LowerCaseResultTransformer(ChilexpressCourierStateTmpW.class));
		List list = query.list();
		ChilexpressCourierStateTmpW[] result = (ChilexpressCourierStateTmpW[]) list.toArray(new ChilexpressCourierStateTmpW[list.size()]);
		return result;
	}
	
	public ChilexpressCourierStateTmpW[] getChilexpressCourierStatesByFile(String filename) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp.getChilexpressCourierStatesByFile");
		query.setString("filename", filename);
		query.setResultTransformer(new LowerCaseResultTransformer(ChilexpressCourierStateTmpW.class));
		List list = query.list();
		ChilexpressCourierStateTmpW[] result = (ChilexpressCourierStateTmpW[]) list.toArray(new ChilexpressCourierStateTmpW[list.size()]);
		return result;
	}
	
	public int deleteByIds(List<Long> chilexpresscourierstatetmpids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp.deleteByIds");
		query.setParameterList("chilexpresscourierstatetmpids", chilexpresscourierstatetmpids);
		int total = query.executeUpdate();
		return total;
	}
	
}