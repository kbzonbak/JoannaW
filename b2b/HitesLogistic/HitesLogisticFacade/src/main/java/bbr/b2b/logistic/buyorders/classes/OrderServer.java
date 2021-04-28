package bbr.b2b.logistic.buyorders.classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.buyorders.entities.Client;
import bbr.b2b.logistic.buyorders.entities.Order;
import bbr.b2b.logistic.buyorders.entities.OrderType;
import bbr.b2b.logistic.buyorders.entities.Responsable;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.entities.SOAStateType;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/buyorders/OrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderServer extends LogisticElementServer<Order, OrderW> implements OrderServerLocal {


	@EJB
	SectionServerLocal sectionserver;

	@EJB
	RetailerServerLocal retailerserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ResponsableServerLocal responsableserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	public OrderW addOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderW) addElement(order);
	}
	public OrderW[] getOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderW[]) getIdentifiables();
	}
	public OrderW updateOrder(OrderW order) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (OrderW) updateElement(order);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Order entity, OrderW wrapper) {
		wrapper.setSectionid(entity.getSection() != null ? new Long(entity.getSection().getId()) : new Long(0));
		wrapper.setRetailerid(entity.getRetailer() != null ? new Long(entity.getRetailer().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setResponsableid(entity.getResponsable() != null ? new Long(entity.getResponsable().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));
		wrapper.setCurrentsoastatetypeid(entity.getCurrentsoastatetype() != null ? new Long(entity.getCurrentsoastatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Order entity, OrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getSectionid() != null && wrapper.getSectionid() > 0) { 
			Section section = sectionserver.getReferenceById(wrapper.getSectionid());
			if (section != null) { 
				entity.setSection(section);
			}
		}
		if (wrapper.getRetailerid() != null && wrapper.getRetailerid() > 0) { 
			Retailer retailer = retailerserver.getReferenceById(wrapper.getRetailerid());
			if (retailer != null) { 
				entity.setRetailer(retailer);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getResponsableid() != null && wrapper.getResponsableid() > 0) { 
			Responsable responsable = responsableserver.getReferenceById(wrapper.getResponsableid());
			if (responsable != null) { 
				entity.setResponsable(responsable);
			}
		}
		if (wrapper.getOrdertypeid() != null && wrapper.getOrdertypeid() > 0) { 
			OrderType ordertype = ordertypeserver.getReferenceById(wrapper.getOrdertypeid());
			if (ordertype != null) { 
				entity.setOrdertype(ordertype);
			}
		}
		if (wrapper.getClientid() != null && wrapper.getClientid() > 0){
			Client client = clientserver.getReferenceById(wrapper.getClientid());
			if (client != null) { 
				entity.setClient(client);
			}
		}
		if (wrapper.getCurrentsoastatetypeid() != null && wrapper.getCurrentsoastatetypeid() > 0){
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getCurrentsoastatetypeid());
			if(soastatetype != null){
				entity.setCurrentsoastatetype(soastatetype);
			}
		}
	}
	
	/*
	 * Retorna las oc pendientes por enviar a SOA Se puede agregar mas de un estado soa pendiente por enviar
	 */
	public OrderW[] getOrdersToSendSoa(ArrayList<Long> docs, Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];

		properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("oc.emitted", "since", since);
		properties[2] = new PropertyInfoDTO("oc.number", "number", docs);

		StringBuilder sb = new StringBuilder();

		sb = new StringBuilder("Select distinct oc FROM Order oc " + // tablas
				"WHERE oc.vendor.id = :vendorid AND oc.emitted >= :since AND oc.number in (:number) ");

		List list = getByQuery(sb.toString(), properties);

		return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
	
	/*
	 * Retorna las oc pendientes por enviar a SOA Se puede agregar mas de un estado soa pendiente por enviar
	 */
	public OrderW[] getOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];

		properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("oc.emitted", "since", since);
		properties[2] = new PropertyInfoDTO("currentsoastatetype.id", "currentsoastatetypeid", Arrays.asList(soastateids));

		StringBuilder sb = new StringBuilder("Select distinct oc FROM Order oc, DvrOrder dvroc " + // tablas
				"WHERE  oc.id = dvroc.id AND oc.vendor.id = :vendorid AND oc.emitted >= :since AND oc.currentsoastatetype.id  in (:currentsoastatetypeid) ");
		List list = getByQuery(sb.toString(), properties);

		return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
	
	
	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate){		
		SQLQuery query=null;
		query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.buyorders.entities.Order.getPendingSOAOrdersByVendor");
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;		
	}
	
	public PendingSOAOrderDTO[] getPendingSOADdcOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate){		
		SQLQuery query=null;
		query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.buyorders.entities.Order.getPendingSOAODdcOrdersByVendor");
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;		
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Order";
	}
	@Override
	protected void setEntityname() {
		entityname = "Order";
	}
}
