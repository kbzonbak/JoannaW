package bbr.b2b.logistic.customer.classes;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.data.dto.HistoryDetailDTO;
import bbr.b2b.logistic.buyorders.data.dto.HistoryHeaderDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportDataDTO;
import bbr.b2b.logistic.buyorders.data.dto.OrderReportInitParamDTO;
import bbr.b2b.logistic.customer.data.dto.PendingSOAOrderDTO;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.entities.Action;
import bbr.b2b.logistic.customer.entities.Buyer;
import bbr.b2b.logistic.customer.entities.Client;
import bbr.b2b.logistic.customer.entities.Location;
import bbr.b2b.logistic.customer.entities.Order;
import bbr.b2b.logistic.customer.entities.OrderStateType;
import bbr.b2b.logistic.customer.entities.OrderType;
import bbr.b2b.logistic.customer.entities.Section;
import bbr.b2b.logistic.customer.entities.SoaStateType;
import bbr.b2b.logistic.customer.entities.Vendor;

@Stateless(name = "servers/customer/OrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OrderServer extends LogisticElementServer<Order, OrderW> implements OrderServerLocal {

	private Map<String, String> mapGetOrderSQL = new HashMap<String, String>();//TODO ordenar
	{
		mapGetOrderSQL.put("ORDERID", "oc.id");
		mapGetOrderSQL.put("OCNUMBER", "oc.number");
		mapGetOrderSQL.put("ORDERTYPE", "ot.name");
		mapGetOrderSQL.put("SOASTATETYPE", "soa.name");
		mapGetOrderSQL.put("CLIENTE", "cl.address");
		mapGetOrderSQL.put("EMITTED", "oc.issue");
		mapGetOrderSQL.put("RECEPTIONDATE", "oc.created");
		mapGetOrderSQL.put("TOTALAMOUNT", "od.final_cost*od.quantity_umc");
		mapGetOrderSQL.put("TOTALUNITS", "od.quantity_umc");
		mapGetOrderSQL.put("VALID", "oc.valid");
		mapGetOrderSQL.put("COMPLETE", "oc.complete");
		
	}
	
	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	LocationServerLocal deliveryplaceserver;

	@EJB
	BuyerServerLocal buyerserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	SoaStateTypeServerLocal soastatetypeserver;

	@EJB
	ClientServerLocal clientserver;

	@EJB
	LocationServerLocal saleplaceserver;
	
	@EJB
	SectionServerLocal sectionserver;
	
	@EJB
	ActionServerLocal actionserver;

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
		wrapper.setOrderstatetypeid(entity.getOrderstatetype() != null ? new Long(entity.getOrderstatetype().getId()) : new Long(0));
		wrapper.setDeliveryplaceid(entity.getDeliveryplace() != null ? new Long(entity.getDeliveryplace().getId()) : new Long(0));
		wrapper.setBuyerid(entity.getBuyer() != null ? new Long(entity.getBuyer().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setSoastatetypeid(entity.getSoastatetype() != null ? new Long(entity.getSoastatetype().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));
		wrapper.setSaleplaceid(entity.getSaleplace() != null ? new Long(entity.getSaleplace().getId()) : new Long(0));
		wrapper.setSectionid(entity.getSection() != null ? new Long(entity.getSection().getId()) : new Long(0));
		wrapper.setActionid(entity.getAction() != null ? new Long(entity.getAction().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Order entity, OrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getOrderstatetypeid() != null && wrapper.getOrderstatetypeid() > 0) { 
			OrderStateType orderstatetype = orderstatetypeserver.getReferenceById(wrapper.getOrderstatetypeid());
			if (orderstatetype != null) { 
				entity.setOrderstatetype(orderstatetype);
			}
		}
		if (wrapper.getDeliveryplaceid() != null && wrapper.getDeliveryplaceid() > 0) { 
			Location deliveryplace = deliveryplaceserver.getReferenceById(wrapper.getDeliveryplaceid());
			if (deliveryplace != null) { 
				entity.setDeliveryplace(deliveryplace);
			}
		}
		if (wrapper.getBuyerid() != null && wrapper.getBuyerid() > 0) { 
			Buyer buyer = buyerserver.getReferenceById(wrapper.getBuyerid());
			if (buyer != null) { 
				entity.setBuyer(buyer);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getOrdertypeid() != null && wrapper.getOrdertypeid() > 0) { 
			OrderType ordertype = ordertypeserver.getReferenceById(wrapper.getOrdertypeid());
			if (ordertype != null) { 
				entity.setOrdertype(ordertype);
			}
		}
		if (wrapper.getSoastatetypeid() != null && wrapper.getSoastatetypeid() > 0) { 
			SoaStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getSoastatetypeid());
			if (soastatetype != null) { 
				entity.setSoastatetype(soastatetype);
			}
		}
		if (wrapper.getClientid() != null && wrapper.getClientid() > 0) { 
			Client client = clientserver.getReferenceById(wrapper.getClientid());
			if (client != null) { 
				entity.setClient(client);
			}
		}
		if (wrapper.getSaleplaceid() != null && wrapper.getSaleplaceid() > 0) { 
			Location saleplace = saleplaceserver.getReferenceById(wrapper.getSaleplaceid());
			if (saleplace != null) { 
				entity.setSaleplace(saleplace);
			}
		}
		if (wrapper.getSectionid() != null && wrapper.getSectionid() > 0) { 
			Section section = sectionserver.getReferenceById(wrapper.getSectionid());
			if (section != null) { 
				entity.setSection(section);
			}
		}
		if (wrapper.getActionid() != null && wrapper.getActionid() > 0) { 
			Action action = actionserver.getReferenceById(wrapper.getActionid());
			if (action != null) { 
				entity.setAction(action);
			}
		}
	}
	
	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate){		
		//SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.customer.entities.Order.getPendingSOAOrdersByVendor");
		String SQL = 
				"SELECT " + //
				"OC.NUMBER AS ORDERNUMBER, " + //
				"OC.issue_date as emitted, " + //
				"V.RUT AS VENDOR, " + //
				"SST.CODE AS CURRENTSOASTATE, " + //
				"SS.when1 as	CURRENTSOASTATETYPEDATE, " + //
				"ost.\"name\" as CURRENTSTATETYPE, " + //
				"ot.\"name\" as ORDERTYPE " + //
			"FROM " + //
				"LOGISTICA.\"order\" AS OC JOIN " + //
				"LOGISTICA.VENDOR AS V ON (OC.VENDOR_ID = V.ID)  " + //
				"join LOGISTICA.SOASTATETYPE AS SST ON SST.ID = OC.soastatetype_id  " + //
				"left join logistica.soastate SS on oc.id = ss.order_id and ss.soastatetype_id = oc.soastatetype_id  " + //
				"left join logistica.orderstatetype ost on oc.orderstatetype_id = ost.id " + //
				"left join logistica.ordertype ot on ot.id = oc.ordertype_id " + //
			"WHERE  " + //
				"SST.ID <> :soastatetype AND " + //
				"ss.when1 <= :soapendingtime AND " + //
				"V.ID = :vendorid  " + //
				"and OC.issue_date >= :activationdate ";
						
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List<PendingSOAOrderDTO> list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;	
	}
	
	public OrderReportDataDTO[] getOrdersByVendorAndCriterium(Long vendorid, Long clientid, OrderReportInitParamDTO initparam) throws AccessDeniedException, OperationFailedException, ParseException {
		
		String whereCondition = "";
		
		switch (initparam.getFiltertype()) {
		
			case 0:
				// ORDENES VIGENTES
				//whereCondition = "AND OC.VALID = TRUE ";			
				whereCondition = "AND (soa.code = 'POR_NOTIFICAR' OR soa.code = 'NOTIFICADO') ";
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND OST.ID = :orderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :ocnumber "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND OC.issue_date >= :emittedsince AND OC.issue_date <= :emitteduntil ";
				break;
				
			case 4:
				// FECHA DE RECEPCION
				whereCondition = "AND OC.created >= :receptionsince AND OC.created <= receptionuntil "; 
				break;
				
			default:
				break;
		}
		
		if(!clientid.equals(-1L)){
			whereCondition = whereCondition+ "AND oc.client_id =:clientid ";
		}
		
		String SQL = getOrdersQueryString( 2, initparam.getOrderby(), whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		
		switch (initparam.getFiltertype()) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("orderstatetypeid", initparam.getOrderstatetypeid());
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("ocnumber", Long.parseLong(initparam.getOrdernumber()));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
			
			String str = initparam.getEmitteduntil();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until = LocalDate.parse(str, formatter);
			until = until.plusDays(1);
			String strUntil = until.format(formatter);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("emittedsince", format.parse(initparam.getEmittedsince()));
			query.setDate("emitteduntil", format.parse(strUntil));
			
			break;
			
		case 4:
			// FECHA DE ENTREGA
			
			String str1 = initparam.getReceptionuntil();
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until1 = LocalDate.parse(str1, formatter1);
			until1 = until1.plusDays(1);
			String strUntil1 = until1.format(formatter1);
			
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("receptionsince", format2.parse(initparam.getReceptionsince()));
			query.setDate("receptionuntil", format2.parse(strUntil1));
			
			break;
			
		default:
			break;
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(OrderReportDataDTO.class));
		if(initparam.isPaginated()){//si quiero el reporte paginado
			query.setFirstResult((initparam.getPageNumber()-1) * initparam.getRows());
			query.setMaxResults(initparam.getRows());
		}
		List<?> list = query.list();
		OrderReportDataDTO[] result = (OrderReportDataDTO[]) list.toArray(new OrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getCountOrdersByVendorAndCriterium(Long vendorid, Long clientid, OrderReportInitParamDTO initparam) throws AccessDeniedException, OperationFailedException, ParseException {

		String whereCondition = "";
		
		switch (initparam.getFiltertype()) {
			case 0:
				// ORDENES VIGENTES
				//whereCondition = "AND OC.VALID = TRUE ";
				whereCondition = "AND (soa.code = 'POR_NOTIFICAR' OR soa.code = 'NOTIFICADO') ";
				break;
				
			case 1:
				// ESTADO DE ORDEN
				whereCondition = "AND OST.ID = :orderstatetypeid ";
				break;
				
			case 2:
				// NÚMERO DE ORDEN
				whereCondition = "AND OC.NUMBER = :ocnumber "; 
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND OC.issue_date >= :emittedsince AND OC.issue_date <= :emitteduntil ";
				break;
				
			case 4:
				// FECHA DE RECEPCION
				whereCondition = "AND OC.created >= :receptionsince AND OC.created <= receptionuntil "; 
				break;
				
			default:
				break;
		}
		
		if(!clientid.equals(-1L)){
			whereCondition = whereCondition+ "AND oc.client_id =:clientid ";
		}
		
		String SQL = getOrdersQueryString( 1, null, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		
		switch (initparam.getFiltertype()) {
		case 1:
			// ESTADO DE ORDEN
			query.setLong("orderstatetypeid", initparam.getOrderstatetypeid());
			break;
			
		case 2:
			// NÚMERO DE ORDEN
			query.setLong("ocnumber", Long.parseLong(initparam.getOrdernumber()));
			break;
			
		case 3:
			// FECHA DE EMISIÓN
			
			String str = initparam.getEmitteduntil();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until = LocalDate.parse(str, formatter);
			until = until.plusDays(1);
			String strUntil = until.format(formatter);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("emittedsince", format.parse(initparam.getEmittedsince()));
			query.setDate("emitteduntil", format.parse(strUntil));
			
			break;
			
		case 4:
			// FECHA DE ENTREGA
			
			String str1 = initparam.getReceptionuntil();
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate until1 = LocalDate.parse(str1, formatter1);
			until1 = until1.plusDays(1);
			String strUntil1 = until1.format(formatter1);
			
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			query.setDate("receptionsince", format2.parse(initparam.getReceptionsince()));
			query.setDate("receptionuntil", format2.parse(strUntil1));
			
			break;
			
		default:
			break;
		}
				
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
		
	}
	
	private String getOrdersQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws AccessDeniedException, OperationFailedException {
		
		String SQL = "";
		String sqlSelect = "";
		String sqlFrom1 = "";
		String sqlView = "";
		String orderByCondition = "";
		
		// Count
		if (queryType == 1) {
			sqlView = 	"with history as ( " + // 
					"select " + // 
						"count (oc.number) as numorders, " + // 
						"oc.number as ocnumber " + // 
					"from " + // 
						"logistica.order as oc " + // 
					"group by ocnumber " + // 
					") ";
			sqlSelect = "SELECT COUNT(oc.id) "; 
			
		}
		
		// Datos
		else if(queryType == 2){
			sqlView = 	"with history as ( " + // 
							"select " + // 
								"count (oc.number) as numorders, " + // 
								"oc.number as ocnumber " + // 
							"from " + // 
								"logistica.order as oc " + // 
							"group by ocnumber " + // 
							") ";
					
			sqlSelect = "SELECT " + //  
					" oc.id as orderid, " + // 
					" oc.number as ordernumber, " + // 
					" ot.name as ordertype, " + // 
					" soa.name as soastatetype, " + // 
					" cl.address as cliente, " + // 
					" to_char(oc.issue_date, 'YYYY-MM-DD') as emitted, " + // 
					" to_char(oc.created, 'YYYY-MM-DD') as receptiondate, " + // 
					" (od.final_cost*od.quantity_umc) as totalamount, " + // 
					" od.quantity_umc as totalunits, " + // 
					" oc.valid as valid, " + // 
					" case when history.numorders > 1 then true else false end as history, "+ //
					" case when oc.complete is null then true else oc.complete end as complete ";
			
			orderByCondition = getOrderByString(mapGetOrderSQL, orderby);
		}
		

		sqlFrom1 = "from logistica.order as oc " +//
				"left join logistica.detail as od on " +//
				"oc.id = od.order_id " +//
				"left join logistica.ordertype as ot on " +//
				"ot.id = oc.ordertype_id " +//
					"left join logistica.client as cl on " +//
				"oc.client_id = cl.id " +//
				"left join logistica.soastatetype as soa on " +//
				"oc.soastatetype_id = soa.id " +//
				"left join logistica.orderstatetype as ost on " +//
				"ost.id = oc.orderstatetype_id " +//
				"left join history on oc.number = history.ocnumber ";
		
		SQL  = 	sqlView +
				sqlSelect + 
				sqlFrom1 + 
				"WHERE oc.vendor_id = :vendorid AND oc.valid = true " + 
				whereCondition + 
				orderByCondition;
		
		return SQL;
	}

	private String getOrderByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
	
	private String getHistoryQueryString(String whereCondition, int querytype){
		
		String sqlQuery = "";
		String select  = "";
		String from = "";
		String orderby = "";
		String limit = "";
		
		if(querytype == 1){
			select = "select distinct " +//
					"oc.id as orderid, " +//
					"CAST(oc.number as VARCHAR) as ocnumber, " +//
					"ot.name as octype, " +//
					"od.quantity_umc as quantity, " +//
					"(od.final_cost*od.quantity_umc) as ammount, " +//
					"cl.address as client, " +//
					" to_char(oc.issue_date, 'YYYY-MM-DD') as emitted, " + // 
					" to_char(oc.created, 'YYYY-MM-DD') as receptiondate, " + //
					"oc.valid as valid ";
			
			orderby = " order by oc.id desc ";
			limit = " LIMIT 1";
			
		}
		if(querytype == 2){
			select =	"select " +//
					"oc.id as orderid, " +//
					"CAST(oc.number as VARCHAR) as ocnumber, " +//
					" to_char(oc.issue_date, 'YYYY-MM-DD') as emitted, " + // 
					" to_char(oc.created, 'YYYY-MM-DD') as receptiondate, " + //
					"(od.final_cost*od.quantity_umc) as ammount, " +//
					"od.quantity_umc as quantity ";
			orderby = " order by oc.id desc ";
			
		}
		if(querytype == 3){
			select = "select count(distinct oc.id) ";
		}
		
		from  = "from logistica.order as oc " +//
				"left join logistica.detail as od on " +//
				"oc.id = od.order_id " +//
				"left join logistica.ordertype as ot on " +//
				"ot.id = oc.ordertype_id " +//
				"left join logistica.client as cl on " +//
				"oc.client_id = cl.id " +//
				"left join logistica.soastatetype as soa on " +//
				"oc.soastatetype_id = soa.id " +//
				"left join logistica.orderstatetype as ost on " +//
				"ost.id = oc.orderstatetype_id ";
		
		sqlQuery  = select +
					from + 
					whereCondition +
					orderby +
					limit;
	
		return sqlQuery;
	}
	

	
	public HistoryHeaderDTO getHistoryHeader(String ocnumber, Long vendorid, Long clientid){
		
		String whereCondition = "WHERE oc.vendor_id = :vendorid AND oc.valid = true AND oc.number = :ocnumber ";
		if(!clientid.equals(-1L)){
			whereCondition += " AND oc.client_id =:clientid ";
		}
		String SQL = getHistoryQueryString(whereCondition, 1);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("ocnumber", Long.parseLong(ocnumber));
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(HistoryHeaderDTO.class));
		HistoryHeaderDTO result = (HistoryHeaderDTO) query.uniqueResult();
		return result;
		
	}
	
	public HistoryDetailDTO[] getHistoryDetail(String ocnumber, Long vendorid, Long clientid){
		
		String whereCondition = " WHERE oc.vendor_id = :vendorid AND oc.number =:ocnumber ";
		if(!clientid.equals(-1L)){
			whereCondition = whereCondition+ " AND oc.client_id =:clientid ";
		}
		String SQL = getHistoryQueryString(whereCondition, 2);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("ocnumber", Long.parseLong(ocnumber));
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(HistoryDetailDTO.class));
		List<?> list = query.list();
		HistoryDetailDTO[] result = (HistoryDetailDTO[]) list.toArray(new HistoryDetailDTO[list.size()]);		
		return result;	
		
	}
	
	public int getCountHistoryDetail(String ocnumber, Long vendorid, Long clientid){
		
		String whereCondition = "WHERE oc.vendor_id = :vendorid ";
		if(!clientid.equals(-1L)){
			whereCondition = whereCondition+ " AND oc.client_id =:clientid ";
		}
		String SQL = getHistoryQueryString(whereCondition, 3);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		if(!clientid.equals(-1L)){
			query.setLong("clientid", clientid);
		}
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;
		
	}
	

	@Override
	protected void setEntitylabel() {
		entitylabel = "Order";
	}
	@Override
	protected void setEntityname() {
		entityname = "Order";
	}
	@Override
	public int deleteElements(Long[] arg0) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO Auto-generated method stub
		return 0;
	}
}
