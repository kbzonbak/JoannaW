package bbr.b2b.regional.logistic.couriers.classes;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.couriers.data.wrappers.HiredCourierW;
import bbr.b2b.regional.logistic.couriers.entities.Courier;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourier;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourierId;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorHiredCourierDTO;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/couriers/HiredCourierServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HiredCourierServer extends BaseLogisticEJB3Server<HiredCourier, HiredCourierId, HiredCourierW> implements HiredCourierServerLocal {
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	CourierServerLocal courierserver;
	
	public HiredCourierW addHiredCourier(HiredCourierW hiredCourier) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HiredCourierW) addIdentifiable(hiredCourier);
	}
	public HiredCourierW[] getHiredCouriers() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HiredCourierW[]) getIdentifiables();
	}
	public HiredCourierW updateHiredCourier(HiredCourierW hiredCourier) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (HiredCourierW) updateIdentifiable(hiredCourier);
	}
	public int deleteHiredCourier(PropertyInfoDTO[] pven) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return deleteByProperties(pven);
	}

	@Override
	protected void copyRelationsEntityToWrapper(HiredCourier entity, HiredCourierW wrapper){
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setCourierid(entity.getCourier() != null ? new Long(entity.getCourier().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(HiredCourier entity, HiredCourierW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) {
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) {
				entity.setVendor(vendor);
			}
		}
		
		if (wrapper.getCourierid() != null && wrapper.getCourierid() > 0) {
			Courier courier = courierserver.getReferenceById(wrapper.getCourierid());
			if (courier != null) { 
				entity.setCourier(courier);
			}
		}
		
	}
	
	public Long getByVendor(Long vendorid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.HiredCourier.getByVendor");
		query.setLong("VENDORID", vendorid);
		Long result = ((BigInteger) query.uniqueResult()).longValue();
		return result;	
	}
	
	public VendorHiredCourierDTO[] getAllVendorsHiredCourier(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.HiredCourier.getAllVendorsHiredCourier");
		query.setResultTransformer(new LowerCaseResultTransformer(VendorHiredCourierDTO.class));
		
		List list = query.list();	
		return (VendorHiredCourierDTO[]) list.toArray(new VendorHiredCourierDTO[list.size()]);	
	}
	
	

	@Override
	protected void setEntitylabel() {
		entitylabel = "HiredCourier";
		
	}

	@Override
	protected void setEntityname() {
		entityname = "HiredCourier";
		
	}

}
