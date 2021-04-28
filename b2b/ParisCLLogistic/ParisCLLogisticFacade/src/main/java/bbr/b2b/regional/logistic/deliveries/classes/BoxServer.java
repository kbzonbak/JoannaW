package bbr.b2b.regional.logistic.deliveries.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.Box;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;

@Stateless(name = "servers/deliveries/BoxServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BoxServer extends LogisticElementServer<Box, BoxW> implements BoxServerLocal {

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	DepartmentServerLocal departmentserver;
	
	public BoxW addBox(BoxW box) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BoxW) addElement(box);
	}
	public BoxW[] getBoxs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BoxW[]) getIdentifiables();
	}
	public BoxW updateBox(BoxW box) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BoxW) updateElement(box);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Box entity, BoxW wrapper) {
		wrapper.setLocationid(entity.getLocation() != null ? new Long(entity.getLocation().getId()) : new Long(0));
		wrapper.setOrderid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getOrderid()) : new Long(0));
		wrapper.setDeliveryid(entity.getOrderdelivery().getId() != null ? new Long(entity.getOrderdelivery().getId().getDeliveryid()) : new Long(0));
		wrapper.setFlowtypeid(entity.getFlowtype() != null ? new Long(entity.getFlowtype().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Box entity, BoxW wrapper) throws OperationFailedException, NotFoundException {
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

	@Override
	protected void setEntitylabel() {
		entitylabel = "Box";
	}
	@Override
	protected void setEntityname() {
		entityname = "Box";
	}
	
	public int deleteByDeliveryId(Long deliveryid) {
		
		String SQL =
			"DELETE " + //
			"FROM " + //
				"logistica.box " + //
			"WHERE " + //
				"id IN (" + //
					"SELECT " + //
						"id " + //
					"FROM " + //
						"logistica.bulk " + //
					"WHERE " + //
						"delivery_id = :deliveryid)"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate();
		return total;
	}
}
