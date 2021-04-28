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
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.entities.Order;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedNonDeliveredOrderDTO;
import bbr.b2b.regional.logistic.datings.report.classes.OrderUnitsDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDelivery;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;

@Stateless(name = "servers/deliveries/OrderDeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderDeliveryServer extends BaseLogisticEJB3Server<OrderDelivery, OrderDeliveryId, OrderDeliveryW> implements OrderDeliveryServerLocal {


	@EJB
	OrderServerLocal orderserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	DeliveryServerLocal deliveryserver;

	public OrderDeliveryW addOrderDelivery(OrderDeliveryW orderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryW) addIdentifiable(orderdelivery);
	}
	public OrderDeliveryW[] getOrderDeliveries() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryW[]) getIdentifiables();
	}
	
	public OrderDeliveryW[] getOrderDeliveriesByOrderRefDocumentAndContainer(Long orderid, Long guidenumber, String containernumber) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"od.closed, " + //
				"od.asnimp, " + //
				"od.refdocument, " + //
				"od.imp, " + //				
				"od.deliveryindex, " + //
				"od.estimatedunits, " + //
				"od.originalestimunits, " + //
				"od.realbulkcount, " + //
				"od.realpalletcount, " + //
				"od.realpackedunits, " + //
				"od.delivery_id AS deliveryid, " + //
				"od.order_id AS orderid, " + //
				"od.department_id AS departmentid, " + //
				"od.flowtype_id AS flowtypeid " + //
			"FROM " + //
				"logistica.orderdelivery AS od JOIN " + //
				"logistica.delivery AS dl ON dl.id = od.delivery_id " + //
			"WHERE " + //
				"od.order_id = :orderid AND dl.refdocument = :guidenumber AND dl.container = :containernumber";
						
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("guidenumber", guidenumber);
		query.setString("containernumber", containernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDeliveryW.class));

		List list = query.list();
		return (OrderDeliveryW[]) list.toArray(new OrderDeliveryW[list.size()]);
	}
	
	public OrderDeliveryW[] getOrderDeliveriesOfOrder(Long orderid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"closed, " + //
				"asnimp, " + //
				"refdocument, " + //
				"imp, " + //				
				"deliveryindex, " + //
				"estimatedunits, " + //
				"originalestimunits, " + //
				"realbulkcount, " + //
				"realpalletcount, " + //
				"realpackedunits, " + //
				"delivery_id AS deliveryid, " + //
				"order_id AS orderid, " + //
				"department_id AS departmentid, " + //
				"flowtype_id AS flowtypeid " + //
			"FROM " + //
				"logistica.orderdelivery " + //
			"WHERE " + //
				"order_id = :orderid "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDeliveryW.class));

		List list = query.list();
		return (OrderDeliveryW[]) list.toArray(new OrderDeliveryW[list.size()]);
		
	}
	
	public OrderDeliveryW[] getOrderDeliveriesByOrderAndDelivery(Long orderid, Long deliveryid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"closed, " + //
				"asnimp, " + //
				"refdocument, " + //
				"imp, " + //				
				"deliveryindex, " + //
				"estimatedunits, " + //
				"originalestimunits, " + //
				"realbulkcount, " + //
				"realpalletcount, " + //
				"realpackedunits, " + //
				"delivery_id AS deliveryid, " + //
				"order_id AS orderid, " + //
				"department_id AS departmentid, " + //
				"flowtype_id AS flowtypeid " + //
			"FROM " + //
				"logistica.orderdelivery " + //
			"WHERE " + //
				"order_id = :orderid AND delivery_id = :deliveryid"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDeliveryW.class));

		List list = query.list();
		return (OrderDeliveryW[]) list.toArray(new OrderDeliveryW[list.size()]);
		
	}
	
	public OrderDeliveryW[] getOrderDeliveriesOfOrder(Long[] orderid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"closed, " + //
				"asnimp, " + //
				"refdocument, " + //
				"imp, " + //				
				"deliveryindex, " + //
				"estimatedunits, " + //
				"originalestimunits, " + //
				"realbulkcount, " + //
				"realpalletcount, " + //
				"realpackedunits, " + //
				"delivery_id AS deliveryid, " + //
				"order_id AS orderid, " + //
				"department_id AS departmentid, " + //
				"flowtype_id AS flowtypeid " + //
			"FROM " + //
				"logistica.orderdelivery " + //
			"WHERE " + //
				"order_id IN (:orderid)"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("orderid", orderid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderDeliveryW.class));

		List list = query.list();
		return (OrderDeliveryW[]) list.toArray(new OrderDeliveryW[list.size()]);
		
	}
	
	public OrderUnitsDTO[] getOrderDeliveryUnitsByDeliveryAndVendor(Long deliveryid, Long vendorid) {
		
		String SQL =
				"SELECT DISTINCT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ocnumber, " + //
					"od.estimatedunits AS estimatedunits, " + //
					"oc.pendingunits AS pendingunits, " + //
					"oc.todeliveryunits AS todeliveryunits, " + //
					"logistica.tostr(oc.expiration) AS expirationdate, " + //
					"logistica.tostr(oc.valid) AS validdate, " + //
					"ovd.id AS originalvendorid, " + //
					"ovd.code AS originalvendorcode, " + //
					"ovd.name AS originalvendorname " + //
				"FROM " + //
					"logistica.orderdelivery AS od JOIN " + //
					"logistica.order AS oc ON oc.id = od.order_id LEFT JOIN " + //
					"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
				"WHERE " + //
					"od.delivery_id = :deliveryid AND oc.vendor_id = :vendorid"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderUnitsDTO.class));

		List list = query.list();
		return (OrderUnitsDTO[]) list.toArray(new OrderUnitsDTO[list.size()]);
	}
	
	public OrderUnitsDTO[] getOrderUnitsRequestByDelivery(Long deliveryid) {
		
		String SQL =
				"SELECT DISTINCT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ocnumber, " + //
					"od.estimatedunits AS estimatedunits, " + //
					"0.0 AS pendingunits, " + //
					"od.estimatedunits AS todeliveryunits, " + //
					"logistica.tostr(oc.expiration) AS expirationdate, " + //
					"logistica.tostr(oc.valid) AS validdate, " + //
					"ovd.id AS originalvendorid, " + //
					"ovd.code AS originalvendorcode, " + //
					"ovd.name AS originalvendorname " + //
				"FROM " + //
					"logistica.orderdelivery AS od JOIN " + //
					"logistica.order AS oc ON oc.id = od.order_id LEFT JOIN " + //
					"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
				"WHERE " + //
					"od.delivery_id = :deliveryid"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(OrderUnitsDTO.class));

		List list = query.list();
		return (OrderUnitsDTO[]) list.toArray(new OrderUnitsDTO[list.size()]);
	}
	
	public void doDeleteByOrderDeliveries(Long deliveryid, Long[] orderids) {
		
		String SQL =
			"DELETE " + //
			"FROM logistica.orderdelivery " + //
			"WHERE " + //
				"delivery_id = :deliveryid AND order_id IN (:orderids)"; //
	
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setParameterList("orderids", orderids);

		query.executeUpdate();
	}
	
	public ImportedNonDeliveredOrderDTO[] getNonDeliveredOrdersByDating(Long deliveryid) {
		
		String SQL =
				"SELECT " + //
					"oc.id AS orderid, " + //
					"oc.number AS ordernumber, " + //
					"od.delivery_id AS deliveryid, " + //
					"COALESCE(od.asnimp, 0) AS asnimp, " + //
					"ovd.id AS originalvendorid, " + //
					"COALESCE(ovd.code, '') AS originalvendorcode, " + //
					"COALESCE(ovd.name, '') AS originalvendorname " + //
				"FROM " + //
					"logistica.orderdelivery AS od JOIN " + //
					"logistica.order AS oc ON oc.id = od.order_id LEFT JOIN " + //
					"logistica.vendor AS ovd ON ovd.code = oc.vendorcodeimp " + //
				"WHERE " + //
					"od.delivery_id = :deliveryid AND od.closed IS FALSE"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(ImportedNonDeliveredOrderDTO.class));

		List list = query.list();
		return (ImportedNonDeliveredOrderDTO[]) list.toArray(new ImportedNonDeliveredOrderDTO[list.size()]);
	}
	
	public OrderDeliveryW updateOrderDelivery(OrderDeliveryW orderdelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderDeliveryW) updateIdentifiable(orderdelivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(OrderDelivery entity, OrderDeliveryW wrapper) {
		wrapper.setOrderid(entity.getOrder() != null ? new Long(entity.getOrder().getId()) : new Long(0));
		wrapper.setFlowtypeid(entity.getFlowtype() != null ? new Long(entity.getFlowtype().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setDeliveryid(entity.getDelivery() != null ? new Long(entity.getDelivery().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(OrderDelivery entity, OrderDeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			Order order = orderserver.getReferenceById(wrapper.getOrderid());
			if (order != null) { 
				entity.setOrder(order);
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
		if (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) { 
			Delivery delivery = deliveryserver.getReferenceById(wrapper.getDeliveryid());
			if (delivery != null) { 
				entity.setDelivery(delivery);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "OrderDelivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "OrderDelivery";
	}
}
