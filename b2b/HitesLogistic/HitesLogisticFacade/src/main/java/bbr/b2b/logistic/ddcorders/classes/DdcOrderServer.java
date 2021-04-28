package bbr.b2b.logistic.ddcorders.classes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.buyorders.entities.Client;
import bbr.b2b.logistic.buyorders.entities.OrderType;
import bbr.b2b.logistic.buyorders.entities.Responsable;
import bbr.b2b.logistic.buyorders.entities.Retailer;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryServerLocal;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.entities.DdcOrderStateType;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportDataDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderExcelReportResultDTO;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderReportDataDTO;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.entities.Location;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.entities.SOAStateType;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/ddcorders/DdcOrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcOrderServer extends LogisticElementServer<DdcOrder, DdcOrderW> implements DdcOrderServerLocal {

	private Map<String, String> mapGetOrderSQL = new HashMap<String, String>();
	{
		mapGetOrderSQL.put("DDCORDERNUMBER", "oc.number");
		mapGetOrderSQL.put("DDCORDERSTATETYPECODE", "ddcost.code");
		mapGetOrderSQL.put("DDCORDERSTATETYPENAME", "ddcost.name");
		mapGetOrderSQL.put("CURRENTDDCDELIVERYSTATETYPECODE", "currentddcdeliverystatetypecode");
		mapGetOrderSQL.put("CURRENTDDCDELIVERYSTATETYPENAME", "currentddcdeliverystatetypename");
		mapGetOrderSQL.put("CURRENTDDCDELIVERYSTATETYPEDATE", "ddcd.currentstatetypedate");
		mapGetOrderSQL.put("EMITTEDDATE", "oc.emitted");
		mapGetOrderSQL.put("DDCORDERREFERENCENUMBER", "ddcoc.referencenumber");
		mapGetOrderSQL.put("ORIGINALDELIVERYDATE", "ddcoc.originaldeliverydate");
		mapGetOrderSQL.put("CURRENTDELIVERYDATE", "ddcoc.currentdeliverydate");
		mapGetOrderSQL.put("EXPIRATIONDATE", "ddcoc.expiration");
		mapGetOrderSQL.put("CLIENTNAME", "clientname");
		mapGetOrderSQL.put("CLIENTDNI", "clientdni");
		mapGetOrderSQL.put("CLIENTADDRESS", "clientaddress");
		mapGetOrderSQL.put("CLIENTCOMMUNE", "clientcommune");
		mapGetOrderSQL.put("CLIENTCITY", "clientcity");
		mapGetOrderSQL.put("NEEDUNITS", "ddcoc.needunits");
		mapGetOrderSQL.put("TOTALNEED", "ddcoc.totalneed");
		mapGetOrderSQL.put("DDCFILENAME", "ddcfilename");
		mapGetOrderSQL.put("PENDINGUNITS", "pendingunits");
	}
	
	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;

	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	ClientServerLocal clientserver;
	
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
	DdcDeliveryServerLocal ddcdeliveryserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;
	
	public DdcOrderW addDdcOrder(DdcOrderW ddcorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderW) addElement(ddcorder);
	}
	public DdcOrderW[] getDdcOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderW[]) getIdentifiables();
	}
	public DdcOrderW updateDdcOrder(DdcOrderW ddcorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcOrderW) updateElement(ddcorder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcOrder entity, DdcOrderW wrapper) {
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setSalelocationid(entity.getSalelocation() != null ? new Long(entity.getSalelocation().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));		
		wrapper.setSectionid(entity.getSection() != null ? new Long(entity.getSection().getId()) : new Long(0));
		wrapper.setRetailerid(entity.getRetailer() != null ? new Long(entity.getRetailer().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setResponsableid(entity.getResponsable() != null ? new Long(entity.getResponsable().getId()) : new Long(0));
		wrapper.setOrdertypeid(entity.getOrdertype() != null ? new Long(entity.getOrdertype().getId()) : new Long(0));
		wrapper.setCurrentddcdeliveryid(entity.getCurrentddcdelivery() != null ? new Long(entity.getCurrentddcdelivery().getId()) : new Long(0));
		wrapper.setCurrentsoastatetypeid(entity.getCurrentsoastatetype() != null ? new Long(entity.getCurrentsoastatetype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcOrder entity, DdcOrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DdcOrderStateType currentstatetype = ddcorderstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getSalelocationid() != null && wrapper.getSalelocationid() > 0) { 
			Location salelocation = locationserver.getReferenceById(wrapper.getSalelocationid());
			if (salelocation != null) { 
				entity.setSalelocation(salelocation);
			}
		}
		if (wrapper.getClientid() != null && wrapper.getClientid() > 0) { 
			Client client = clientserver.getReferenceById(wrapper.getClientid());
			if (client != null) { 
				entity.setClient(client);
			}
		}
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
		if (wrapper.getCurrentddcdeliveryid() != null && wrapper.getCurrentddcdeliveryid() > 0) { 
			DdcDelivery currentddcdelivery = ddcdeliveryserver.getReferenceById(wrapper.getCurrentddcdeliveryid());
			if (currentddcdelivery != null) { 
				entity.setCurrentddcdelivery(currentddcdelivery);
			}
		}
		if (wrapper.getCurrentsoastatetypeid() != null && wrapper.getCurrentsoastatetypeid() > 0){
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getCurrentsoastatetypeid());
			if(soastatetype != null){
				entity.setCurrentsoastatetype(soastatetype);
			}
		}	
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcOrder";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcOrder";
	}
	
	public void doCalculateTotalDdcOrders(Long[] ddcorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrder.doCalculateTotalDdcOrders");
		query.setParameterList("ddcorderids", ddcorderids);
		query.executeUpdate();
	}
	
	public DdcOrderW[] getDdcOrderByIdsAndVendor(Long[] ddcorderids, Long vendorid) throws NotFoundException, OperationFailedException {
		DdcOrderW[] result = null;
		List<DdcOrderW> resultList = new ArrayList<DdcOrderW>();
		
		List<Long> ddcorderidsList = Arrays.asList(ddcorderids);
		String queryStr = 	"select ddcoc from DdcOrder as ddcoc " + // 
							"where " + //
							"ddcoc.vendor.id = :vendorid " + //
							"and ddcoc.id in (:ddcorderids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("ve.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("ddcoc.id ", "ddcorderids", ddcorderidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DdcOrderW[]) resultList.toArray(new DdcOrderW[resultList.size()]);
		return result;
	}	
	
	public DdcOrderW[] getDdcOrderByNumbers(Long[] ddcordernumbers) throws NotFoundException, OperationFailedException {
		DdcOrderW[] result = null;
		List<DdcOrderW> resultList = new ArrayList<DdcOrderW>();
		
		List<Long> ddcordernumbersList = Arrays.asList(ddcordernumbers);
		String queryStr = 	"select ddcoc from DdcOrder as ddcoc " + // 
							"where ddcoc.number in (:ddcordernumbers) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("ddcoc.number", "ddcordernumbers", ddcordernumbersList);
		
		resultList = getByQuery(queryStr, properties);
		result =(DdcOrderW[]) resultList.toArray(new DdcOrderW[resultList.size()]);
		return result;
	}	
	
	public DdcOrderReportDataDTO[] getDdcOrdersByVendorAndCriterium(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		LocalDateTime now = LocalDateTime.now();
		
		String whereCondition = "";
		
		switch (criterium) {
			case 0:
				// ORDENES VIGENTES
				whereCondition = "AND ddcost.valid IS TRUE "; //			
				break;
				
			case 1:
				// NÚMERO DE ORDEN
				whereCondition = "AND oc.number = :filtervalue "; //
				break;
				
			case 2:
				// FECHA DE COMPROMISO
				whereCondition = "AND ddcoc.originaldeliverydate >= :since AND ddcoc.originaldeliverydate <= :until "; //
				break;
				
			case 3:
				// FECHA DE EMISIÓN
				whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until "; //
				break;
				
			case 4:
				// ESTADO DE ORDEN
				whereCondition = "AND ddcost.id = :ddcorderstatetypeid ";
				break;
				
			case 5:
				// RUT DEL CLIENTE
				whereCondition = "AND cl.dni = :filtervalue ";
				break;
				
			case 6:
				// NÚMERO DE BOLETA
				whereCondition = "AND ddcoc.referencenumber = :filtervalue "; //
				break;
				
			default:
				break;
		}
		
		String SQL = getDdcOrdersQueryString(2, orderby, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setParameter("now", now.toLocalDate().atStartOfDay());
		switch (criterium) {
		case 1:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 2:
			// FECHA DE COMPROMISO
						
		case 3:
			// FECHA DE EMISIÓN
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
			
		case 4:
			// ESTADO DE ORDEN
			query.setLong("ddcorderstatetypeid", ddcorderstatetypeid);
			break;
			
		case 5:
			// RUT DEL CLIENTE
			
		case 6:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List<?> list = query.list();
		DdcOrderReportDataDTO[] result = (DdcOrderReportDataDTO[]) list.toArray(new DdcOrderReportDataDTO[list.size()]);		
		
		return result;	
	}
	
	public int countDdcOrdersByVendorAndCriterium(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium) throws AccessDeniedException, OperationFailedException {

		String whereCondition = "";
		
		switch (criterium) {
		case 0:
			// ORDENES VIGENTES
			whereCondition = "AND ddcost.valid = TRUE "; //			
			break;
			
		case 1:
			// NÚMERO DE ORDEN
			whereCondition = "AND oc.number = :filtervalue "; //
			break;
			
		case 2:
			// FECHA DE COMPROMISO
			whereCondition = "AND ddcoc.originaldeliverydate >= :since AND ddcoc.originaldeliverydate <= :until "; //
			break;
			
		case 3:
			// FECHA DE EMISIÓN
			whereCondition = "AND oc.emitted >= :since AND oc.emitted <= :until "; //
			break;
			
		case 4:
			// ESTADO DE ORDEN
			whereCondition = "AND ddcost.id = :ddcorderstatetypeid ";
			break;
			
		case 5:
			// RUT DEL CLIENTE
			whereCondition = "AND cl.dni = :filtervalue ";
			break;
			
		case 6:
			// NÚMERO DE BOLETA
			whereCondition = "AND ddcoc.referencenumber = :filtervalue "; //
			break;
			
		default:
			break;
	}
		
		String SQL = getDdcOrdersQueryString(1, null, whereCondition);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
				
		switch (criterium) {
		case 1:
			// NÚMERO DE ORDEN
			query.setLong("filtervalue", Long.parseLong(filtervalue));
			break;
			
		case 2:
			// FECHA DE COMPROMISO
						
		case 3:
			// FECHA DE EMISIÓN
			query.setParameter("since", since.toLocalDate().atStartOfDay());
			query.setParameter("until", until.toLocalDate().atStartOfDay());
			break;
			
		case 4:
			// ESTADO DE ORDEN
			query.setLong("ddcorderstatetypeid", ddcorderstatetypeid);
			break;
			
		case 5:
			// RUT DEL CLIENTE
			
		case 6:
			// NÚMERO DE BOLETA
			query.setString("filtervalue", filtervalue);
			break;
			
		default:
			break;
		}
				
		int total = Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
		return total;		
	}
	
	private String getDdcOrdersQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws AccessDeniedException, OperationFailedException {
		
		String SQL = "";
		String sqlWith1 = "";
		String sqlSelect = "";
		String sqlFrom = "";
		String sqlExtraFrom = "";
		String orderByCondition = "";
				
		// Count
		if (queryType == 1) {
			sqlSelect =
					"SELECT " + //
						"COUNT(oc.id) ";  //
		}
		
		// Datos
		else if(queryType == 2) {

			sqlWith1 = "WITH warningoc AS ( " + //
						"	SELECT " + //
						"		oc.id AS ddcorderid " + //
						"	FROM logistica.order AS oc " + //
						"	JOIN logistica.ddcorder AS ddcoc ON ddcoc.id = oc.id " + //
						"	JOIN logistica.ddcorderstatetype AS ddcost ON ddcost.id = ddcoc.currentstatetype_id " + //
						"	WHERE oc.vendor_id = :vendorid " + //
							whereCondition + //
						"	AND ddcost.valid IS true " + //
						"	AND ddcoc.currentdeliverydate < :now " + //
						"	AND ddcoc.pendingunits > 0 " + //
						") ";
			
			sqlSelect =
					"SELECT " + //
						"oc.id AS ddcorderid, " + //
						"oc.number AS ddcordernumber, " + //
						"ddcost.id AS ddcorderstatetypeid, " + //
						"ddcost.code AS ddcorderstatetypecode, " + //
						"ddcost.name AS ddcorderstatetypename, " + //
						"ddcd.id AS currentddcdeliveryid, " + //
						"ddcdst.id AS currentddcdeliverystatetypeid, " + //
						"COALESCE(ddcdst.code, '') AS currentddcdeliverystatetypecode, " + //
						"COALESCE(ddcdst.name, '') AS currentddcdeliverystatetypename, " + //
						"ddcd.currentstatetypedate AS currentddcdeliverystatetypedate, " + //
						"oc.emitted AS emitteddate, " + //
						"ddcoc.referencenumber AS ddcorderreferencenumber, " + //
						"ddcoc.originaldeliverydate AS originaldeliverydate, " + //
						"ddcoc.currentdeliverydate AS currentdeliverydate, " + //
						"ddcoc.expiration AS expirationdate, " + //
						"COALESCE(ddcoc.reschedulingcounter, 0) AS reschedulingcounter, " + //
						"ddcoc.comment AS ddcordercomment, " + //
						"cl.id AS clientid, " + //
						"COALESCE(cl.name, '') AS clientname, " + //
						"COALESCE(cl.dni, '') AS clientdni, " + //
						"COALESCE(cl.address, '') AS clientaddress, " + //
						"COALESCE(cl.commune, '') AS clientcommune, " + //
						"COALESCE(cl.city, '') AS clientcity, " + //
						"ddcoc.needunits AS needunits, " + //
						"ddcoc.totalneed AS totalneed, " + //
						"ddcf.id AS ddcfileid, " + //
						"CASE WHEN ddcf.filename IS NOT NULL AND ddcf.filename != '' " + //  
							 "THEN ddcf.filename || '.' || ddcf.filetype " + //
							 "ELSE NULL " + //
							 "END AS ddcfilename, " + //
						"CASE " + //
						"	WHEN woc.ddcorderid IS NOT NULL then true " + //
						"	ELSE false " + //
						"END as warningoc, " + //
						"ddcoc.pendingunits as pendingunits ";					 
			
			sqlExtraFrom = 	"LEFT JOIN warningoc as woc " + //
							"ON woc.ddcorderid = ddcoc.id ";
			
			orderByCondition = getOrderByString(mapGetOrderSQL, orderby);
		}
		
		else {
			throw new AccessDeniedException("El tipo de query no es válido");			
		}
		
		sqlFrom =
				"FROM " + //
					"logistica.order AS oc JOIN " + //
					"logistica.ddcorder AS ddcoc ON ddcoc.id = oc.id JOIN " + //
					"logistica.ddcorderstatetype AS ddcost ON ddcost.id = ddcoc.currentstatetype_id JOIN " + //
					"logistica.vendor AS ve ON ve.id = oc.vendor_id JOIN " + //
					"logistica.client AS cl ON cl.id = oc.client_id LEFT JOIN " + //
					"logistica.ddcfile AS ddcf ON ddcf.ddcorder_id = ddcoc.id LEFT JOIN " + //
					"logistica.ddcdelivery AS ddcd ON ddcd.id = ddcoc.currentddcdelivery_id LEFT JOIN " + //
					"logistica.ddcdeliverystatetype AS ddcdst ON ddcdst.id = ddcd.currentstatetype_id "; //
		
		SQL =
			sqlWith1 + //
			sqlSelect + //
			sqlFrom + //
			sqlExtraFrom + //
			"WHERE " + //
				"oc.vendor_id = :vendorid " + //
				whereCondition + //
			orderByCondition; //
		
		return SQL;
	}
	
	public DdcOrderExcelReportResultDTO getDdcOrdersExcelReportByOrders(Long[] ddcorderids) throws OperationFailedException {
		DdcOrderExcelReportResultDTO resultDTO = new DdcOrderExcelReportResultDTO();
		
		if (ddcorderids == null || ddcorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}

		// Se divide el parámetro de entrada en bloques de 50 elementos
		DdcOrderExcelReportDataDTO[] ddcOrders = null;
		
		int chunksize = 50;
		Object[] splitids = ClassUtilities.splitArray(ddcorderids, Long.class, chunksize);
		Object[] ddcOrdersArray = new Object[splitids.length];

		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				ddcOrdersArray[i] = new DdcOrderExcelReportDataDTO[0];
				continue;
			}
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrder.getDdcOrdersExcelReportByOrders");
			query.setParameterList("ddcorderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderExcelReportDataDTO.class));
			
			List<?> list = query.list();
			DdcOrderExcelReportDataDTO[] ddcOrdersTmp = list.toArray(new DdcOrderExcelReportDataDTO[list.size()]);
			
			ddcOrdersArray[i] = ddcOrdersTmp;
		}
		
		ddcOrders = (DdcOrderExcelReportDataDTO[]) ClassUtilities.unsplitArrays(ddcOrdersArray, DdcOrderExcelReportDataDTO.class);
		resultDTO.setDdcorders(ddcOrders);
		
		return resultDTO;
	}
	
	public int countDdcOrdersExcelReportByOrders(Long[] ddcorderids) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcorders.entities.DdcOrder.countDdcOrdersExcelReportByOrders");
		query.setParameterList("ddcorderids", ddcorderids);
		return Long.valueOf(((BigInteger) query.uniqueResult()).longValue()).intValue();
	}
	
	public Long[] getDdcOrderIdsByPages(Long vendorid, String filtervalue, LocalDateTime since, LocalDateTime until, Long ddcorderstatetypeid, Integer criterium, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageRangeDTO) throws OperationFailedException, AccessDeniedException {
		Long[] result = null;
				
		List<Object> ddcOrderList = new ArrayList<>();
		DdcOrderReportDataDTO[] ddcOrders;			
		for (int i = 0; i < pageRangeDTO.length; i++) {
			for (int j = pageRangeDTO[i].getSincePage(); j <= pageRangeDTO[i].getUntilPage(); j++) {
				ddcOrders = getDdcOrdersByVendorAndCriterium(vendorid, filtervalue, since, until, ddcorderstatetypeid, criterium, j, rows, orderby, true);
						
				ddcOrderList.add(ddcOrders);
			}
		}

		Object[] ddcOrderArray = (Object[]) ddcOrderList.toArray(new Object[ddcOrderList.size()]);
		ddcOrders = (DdcOrderReportDataDTO[]) ClassUtilities.unsplitArrays(ddcOrderArray, DdcOrderReportDataDTO.class);

		result = new Long[ddcOrders.length];

		// Obtengo solo los ids de las órdenes de las páginas solicitadas
		for (int i = 0; i < ddcOrders.length; i++) {
			result[i] = ddcOrders[i].getDdcorderid();
		}
			
		return result;
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
	
	
	/*
	 * Retorna las oc pendientes por enviar a SOA
	 * Se puede agregar mas de un estado soa pendiente por enviar
	 * */
	public DdcOrderW[] getOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		
		properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("os.when", "since", since );
        properties[2] = new PropertyInfoDTO("currentsoastatetype.id", "currentsoastatetypeid", Arrays.asList(soastateids));

        StringBuilder sb = new StringBuilder("Select distinct ddcoc FROM DdcOrder ddcoc, Order oc " +	//tablas
        		"where  ddcoc.id = oc.id  " +	//on de tablas
        		"and  oc.vendor.id = :vendorid AND oc.emitted >= :since AND oc.currentsoastatetype.id  in (:currentsoastatetypeid) ");
        List<?> list = getByQuery(sb.toString(), properties);

        return (DdcOrderW[]) list.toArray(new DdcOrderW[list.size()]);
	}
	
	public OrderW[] getDdcOrdersToSendSoa(Long vendorid, LocalDateTime since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
	
		properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[1] = new PropertyInfoDTO("oc.emitted", "since", since);
		properties[2] = new PropertyInfoDTO("currentsoastatetype.id", "currentsoastatetypeid", Arrays.asList(soastateids));
	
		StringBuilder sb = new StringBuilder("Select distinct oc FROM Order oc, DdcOrder ddcoc " + // tablas
				"WHERE  oc.id = ddcoc.id AND oc.vendor.id = :vendorid AND oc.emitted >= :since AND oc.currentsoastatetype.id  in (:currentsoastatetypeid) ");
		List<?> list = getByQuery(sb.toString(), properties);

		return (OrderW[]) list.toArray(new OrderW[list.size()]);
	}
}
