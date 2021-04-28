package bbr.b2b.regional.logistic.couriers.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CommuneCourierW;
import bbr.b2b.regional.logistic.couriers.entities.CommuneCourier;
import bbr.b2b.regional.logistic.couriers.entities.Courier;

@Stateless(name = "servers/couriers/CommuneCourierServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CommuneCourierServer extends LogisticElementServer<CommuneCourier, CommuneCourierW> implements CommuneCourierServerLocal {
	
	@EJB
	CourierServerLocal courierServerLocal;


	@Override
	protected void copyRelationsEntityToWrapper(CommuneCourier entity, CommuneCourierW wrapper) {
		wrapper.setCourierid(entity.getCourier() != null ? new Long(entity.getCourier().getId()) : new Long(0));
	}
	
	public CommuneCourierW addCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException{
		return (CommuneCourierW) addElement(communeCourier);
	}
	
	public CommuneCourierW updateCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException{
		return (CommuneCourierW) updateElement(communeCourier);
	}
	
	public void deleteCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException{
		deleteElement(communeCourier.getId());
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(CommuneCourier entity, CommuneCourierW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCourierid() != null && wrapper.getCourierid() > 0) { 
			Courier courier = courierServerLocal.getReferenceById(wrapper.getCourierid());
			if (courier != null) { 
				entity.setCourier(courier);
			}
		}
	}
	
	public CommuneCourierW getCourierCommuneByCourierAndParisCommune(Long courierid, String pariscommune){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.CommuneCourier.getCourierCommuneByCourierAndParisCommune");
		query.setLong("courierid", courierid);
		query.setString("pariscommune", pariscommune);
		query.setResultTransformer(new LowerCaseResultTransformer(CommuneCourierW.class));
		
//		CommuneCourierW result = 
		return (CommuneCourierW)query.uniqueResult();
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "CommuneCourier";
	}
	@Override
	protected void setEntityname() {
		entityname = "CommuneCourier";
	}
}
