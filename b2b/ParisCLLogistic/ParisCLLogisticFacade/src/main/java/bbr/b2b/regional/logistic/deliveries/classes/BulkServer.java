package bbr.b2b.regional.logistic.deliveries.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;
import bbr.b2b.regional.logistic.deliveries.entities.Bulk;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulksCountAndAvailableUnitsDataDTO;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;

@Stateless(name = "servers/deliveries/BulkServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BulkServer extends LogisticElementServer<Bulk, BulkW> implements BulkServerLocal {


	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	DepartmentServerLocal departmentserver;

	public BulkW addBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW) addElement(bulk);
	}
	public BulkW[] getBulks() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW[]) getIdentifiables();
	}
	public BulkW updateBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW) updateElement(bulk);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Bulk entity, BulkW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
		wrapper.setOrderid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getOrderid()) : new Long(0));
		wrapper.setDeliveryid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getDeliveryid()) : new Long(0));
		wrapper.setFlowtypeid(entity.getFlowtype() != null ? new Long(entity.getFlowtype().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Bulk entity, BulkW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getLocationid() != null && wrapper.getLocationid() > 0) { 
			Location location = locationserver.getReferenceById(wrapper.getLocationid());
			if (location != null) { 
				entity.setLocation(location);
			}
		}
		if ((wrapper.getOrderid() != null && wrapper.getOrderid() > 0) && (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0)) {
			OrderDeliveryId key = new OrderDeliveryId();
			key.setOrderid(wrapper.getOrderid());
			key.setDeliveryid(wrapper.getDeliveryid());
			OrderDelivery var = orderdeliveryserver.getReferenceById(key);
			if (var != null) { 
				entity.setOrderdelivery(var);
			}
		}
		if (wrapper.getFlowtypeid() != null && wrapper.getFlowtypeid() > 0) { 
			FlowType flowtype = flowtypeserver.getReferenceById(wrapper.getFlowtypeid());
			if (flowtype != null) { 
				entity.setFlowtype(flowtype);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
	}	
	
	public BulksCountAndAvailableUnitsDataDTO[] getBulksCountAndAvailableUnitsByDelivery(Long[] deliveryids) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.Bulk.getBulksCountAndAvailableUnitsByDelivery");
		query.setParameterList("deliveryids", deliveryids);
		query.setResultTransformer(new LowerCaseResultTransformer(BulksCountAndAvailableUnitsDataDTO.class));
		List list = query.list();
		BulksCountAndAvailableUnitsDataDTO[] result = (BulksCountAndAvailableUnitsDataDTO[]) list.toArray(new BulksCountAndAvailableUnitsDataDTO[list.size()]);
		return result;
	}
	
	public BulkDTO[] getBulksByOrderDeliveries(Long deliveryid, Long[] orderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.Bulk.getBulksByOrderDeliveries");
		query.setLong("deliveryid", deliveryid);
		query.setParameterList("orderids", orderids);
		query.setResultTransformer(new LowerCaseResultTransformer(BulkDTO.class));
		List list = query.list();
		BulkDTO[] result = (BulkDTO[]) list.toArray(new BulkDTO[list.size()]);
		return result;
	}
		
	@Override
	protected void setEntitylabel() {
		entitylabel = "Bulk";
	}
	@Override
	protected void setEntityname() {
		entityname = "Bulk";
	}
}
