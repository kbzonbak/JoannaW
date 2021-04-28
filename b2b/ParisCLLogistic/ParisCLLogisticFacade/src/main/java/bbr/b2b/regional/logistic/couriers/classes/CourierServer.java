package bbr.b2b.regional.logistic.couriers.classes;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.entities.Courier;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationDTO;


@Stateless(name = "servers/couriers/CourierServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CourierServer extends LogisticElementServer<Courier, CourierW> implements CourierServerLocal{

	@Override
	protected void copyRelationsEntityToWrapper(Courier arg0, CourierW arg1) throws OperationFailedException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void copyRelationsWrapperToEntity(Courier arg0, CourierW arg1) throws OperationFailedException, NotFoundException {
		// TODO Auto-generated method stub
		
	}
	
	public CourierInformationDTO[] getCourierInformation(Long vendorid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.Courier.getCourierInformation");
		query.setLong("vendor_id", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(CourierInformationDTO.class));
		List list = query.list();
		CourierInformationDTO[] result = (CourierInformationDTO[]) list.toArray(new CourierInformationDTO[list.size()]);
		return result;	
	}
	
	public CourierInformationDTO[] getCourierInformationByDODelivery(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.Courier.getCourierInformationByDODelivery");
		query.setLong("dodelivery_id", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(CourierInformationDTO.class));
		List list = query.list();
		CourierInformationDTO[] result = (CourierInformationDTO[]) list.toArray(new CourierInformationDTO[list.size()]);
		return result;	
	}
	
	public Long getCourierIdByDODelivery(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.Courier.getCourierIdByDODelivery");
		query.setLong("dodelivery_id", dodeliveryid);
		Long id = (Long)query.list().get(0);
		return id;	
	}
	
	public Long getCourierIdByVendor(Long vendorid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.couriers.entities.Courier.getCourierIdByVendor");
		query.setLong("vendorid", vendorid);
		Long id = ((BigInteger)query.list().get(0)).longValue();	
		return id;	
	}

	public int getOrdersReportCountByOrders(Long[] orderIds){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Order.getCountOrderReportByOrder");
		query.setParameterList("orderids", orderIds);
		int total = ((BigInteger)query.list().get(0)).intValue();	
		return total;		
	}	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Courier";
		
	}

	@Override
	protected void setEntityname() {
		entityname = "Courier";
		
	}

}
