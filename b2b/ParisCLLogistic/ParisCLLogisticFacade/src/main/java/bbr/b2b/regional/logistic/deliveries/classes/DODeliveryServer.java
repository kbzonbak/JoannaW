package bbr.b2b.regional.logistic.deliveries.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;
//import org.jboss.annotation.IgnoreDependency;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.deliveries.entities.DODeliveryStateType;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliverySourceDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryWSRequestData;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryIdsByPagesW;
import bbr.b2b.regional.logistic.deliveries.report.classes.ExcelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ShippingCertificationDTO;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryReportDataMobileDTO;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/deliveries/DODeliveryServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryServer extends LogisticElementServer<DODelivery, DODeliveryW> implements DODeliveryServerLocal {

	private Map<String, String> mapGetDeliverySQL = new HashMap<String, String>();
	{
		mapGetDeliverySQL.put("DELIVERYNUMBER", "dodl.number");
		mapGetDeliverySQL.put("OCNUMBER", "doc.number");
		mapGetDeliverySQL.put("REQUESTNUMBER", "doc.requestnumber");
		mapGetDeliverySQL.put("CLIENTCOMMUNE", "cl.commune");
		mapGetDeliverySQL.put("CLIENTNAME", "cl.name");
		mapGetDeliverySQL.put("CLIENTRUT", "cl.rut");
		mapGetDeliverySQL.put("CLIENTADDRESS", "cl.address");
		mapGetDeliverySQL.put("CLIENTCITY", "cl.city");
		mapGetDeliverySQL.put("CLIENTPHONE", "cl.phone");
		mapGetDeliverySQL.put("COMMITEDDATE", "commiteddate");		
		mapGetDeliverySQL.put("DELIVERYSTATETYPECODE", "dodst.code");		
		mapGetDeliverySQL.put("DELIVERYSTATETYPEDESC", "dodst.name");		
		mapGetDeliverySQL.put("CURRENTSTDATE", "currentstdate");	
		mapGetDeliverySQL.put("OCCOMMENT", "doc.currentstatetypecomment");	
		mapGetDeliverySQL.put("DLCOMMENT", "dodl.currentstcomment");		
		mapGetDeliverySQL.put("RECEPTIONTYPECODE", "dort.code");		
		mapGetDeliverySQL.put("RECEPTIONTYPEDESC", "dort.name");	
		mapGetDeliverySQL.put("STCHANGEDATE", "stchangedate");
		mapGetDeliverySQL.put("DISPATCHEREMAIL", "dodl.dispatcheremail");		
		mapGetDeliverySQL.put("COURIERTAG", "dodl.couriertag");
		mapGetDeliverySQL.put("SENDNUMBER", "ct.sendnumber");
		mapGetDeliverySQL.put("WITHDRAWALNUMBER", "ct.withdrawalnumber");
		mapGetDeliverySQL.put("WITHDRAWALDATE", "withdrawaldate");
		mapGetDeliverySQL.put("FIRSTWITHDRAWALNUMBER", "csl.withdrawalnumber");
		mapGetDeliverySQL.put("FIRSTWITHDRAWALDATE", "firstwithdrawaldate");
		mapGetDeliverySQL.put("RESCHEDULEREASONCODE", "rsr.code");
		mapGetDeliverySQL.put("RESCHEDULEREASONDESC", "rsr.description");
		mapGetDeliverySQL.put("COURIERRECEIVED", "doc.courierreceived");		
	}		
	
	@EJB
	VendorServerLocal vendorserver;
	
//	@IgnoreDependency
	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	DODeliveryStateTypeServerLocal currentstatetypeserver;

	public DODeliveryW addDODelivery(DODeliveryW dodelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryW) addElement(dodelivery);
	}
	public DODeliveryW[] getDODeliverys() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryW[]) getIdentifiables();
	}
	public DODeliveryW updateDODelivery(DODeliveryW dodelivery) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DODeliveryW) updateElement(dodelivery);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DODelivery entity, DODeliveryW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setOrderid(entity.getDirectorder() != null ? new Long(entity.getDirectorder().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DODelivery entity, DODeliveryW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getOrderid() != null && wrapper.getOrderid() > 0) { 
			DirectOrder directorder = directorderserver.getReferenceById(wrapper.getOrderid());
			if (directorder != null) { 
				entity.setDirectorder(directorder);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DODeliveryStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
	}
	
	private String getQueryString(int queryType, int filtertype, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 				= "";
		String select			= "";
		String orderByCondition	= "";
		
		String whereCondition = "";
		
		switch (filtertype) {
		case 0:
			whereCondition = "dodst.code = :dodstcode "; //		
			break;
		// JPE 20180420
		case 1:
			whereCondition = "dodst.code IN (:dodstcodes) "; //
			break;
		case 2:
			whereCondition = "doc.number = :ordernumber "; //
			break;		
		case 3:
			whereCondition = "doc.requestnumber = :requestnumber "; //		
			break;
		case 4:
			whereCondition = "cl.rut = :clientrut "; //
			break;
		case 5:
			whereCondition = "dodst.name = :dotype AND dodl.currentstdate >= :since AND dodl.currentstdate < :until "; //
			break;
		case 6:
			whereCondition = "ct.sendnumber = :sendnumber "; //
			break;
		case 7:
			whereCondition = "ct.withdrawalnumber = :withdrawalnumber "; //
			break;
		default:
			break;
		}	
		
		select =
			"WITH cslmin AS(" + //
					"SELECT " + //
						"ct.id AS couriertag_id, " + //
						"MIN(csl.when1) AS when1 " + //
					"FROM " + //
						"logistica.courierschedulelog AS csl JOIN " + //
						"logistica.couriertag AS ct ON ct.id = csl.couriertag_id JOIN " + //
						"logistica.dodelivery AS dodl ON dodl.id = ct.dodelivery_id " + //
					"WHERE " + //
						"dodl.vendor_id = :vendorid " + //
					"GROUP BY " + //
						"ct.id), " + //
				"dodsmax AS(" + //
					"SELECT " + //
						"dods.delivery_id, " + //
						"MAX(dods.when1) AS when1 " + //
					"FROM " + //
						"logistica.dodeliverystate AS dods JOIN " + //
						"logistica.dodelivery AS dodl ON dodl.id = dods.delivery_id " + //
					"WHERE " + //
						"dodl.vendor_id = :vendorid " + //
					"GROUP BY " + //
						"dods.delivery_id) " ; //
				
		if (queryType == 1){
			select +=
				"SELECT " + //
					"COUNT (DISTINCT dodl.id)"; //
		} else {
			select +=
				"SELECT DISTINCT " + //
					"dodl.id AS deliveryid, " + //
					"dodl.number AS deliverynumber, " + //
					"doc.number AS ocnumber, " + //
					"doc.requestnumber, " + //
					"cl.commune AS clientcommune, " + //			
					"cl.name AS clientname, " + //
					"cl.rut AS clientrut, " + //
					"cl.address AS clientaddress, " + //
					"cl.city AS clientcity, " + //
					"cl.phone AS clientphone, " + //
					"logistica.tostr(dodl.commiteddate) AS commiteddate, " + //
					"dodst.code AS deliverystatetypecode, " + //
					"dodst.name AS deliverystatetypedesc, " + //
					"logistica.tostr(dodl.currentstdate) AS currentstdate, " + //
					"doc.currentstatetypecomment AS occomment, " + //
					"dodl.currentstcomment AS dlcomment, " + //
					"dort.code AS receptiontypecode, " + //
					"dort.name AS receptiontypedesc, " + //
					"logistica.tostr(dodsmax.when1) AS stchangedate, " + //
					"dodl.dispatcheremail, " + //
					"dodl.couriertag, " + //
					"ct.sendnumber, " + //
					"ct.withdrawalnumber AS withdrawalnumber, " +
					"COALESCE(logistica.tostr(ct.withdrawaldate), '') AS withdrawaldate, " + //
					"csl.withdrawalnumber AS firstwithdrawalnumber, " +
					"COALESCE(logistica.tostr(csl.withdrawaldate), '') AS firstwithdrawaldate, " + //
					"rsr.id AS reschedulereasonid, " + //
					"rsr.code AS reschedulereasoncode, " + //
					"rsr.description AS reschedulereasondesc, " + //
					"doc.courierreceived "; //
			
			orderByCondition = getOrderByString(mapGetDeliverySQL, orderby);
		}
		
		SQL = 
			select + 
			" FROM " + //
				"logistica.directorder AS doc JOIN " + //
				"logistica.dodelivery AS dodl ON dodl.order_id = doc.id JOIN " + //
				"dodsmax ON dodsmax.delivery_id = dodl.id JOIN " + //
				"logistica.dodeliverystatetype AS dodst ON dodst.id = dodl.currentstatetype_id JOIN " + //
				"logistica.client AS cl ON cl.id = doc.client_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = doc.vendor_id JOIN " + //
				"logistica.doreceptiontype AS dort ON dort.id = dodst.receptiontype_id LEFT JOIN " + //
				"logistica.couriertag AS ct ON ct.dodelivery_id = dodl.id LEFT JOIN " + //
				"cslmin ON cslmin.couriertag_id = ct.id LEFT JOIN " + //
				"logistica.courierschedulelog AS csl ON csl.couriertag_id = cslmin.couriertag_id AND csl.when1 = cslmin.when1 LEFT JOIN " + //
				"logistica.reschedulereason AS rsr ON rsr.id = ct.reschedulereason_id " + //
			"WHERE " + //
				"vd.id = :vendorid AND " + //
				whereCondition + //
			orderByCondition ; //
		
		return SQL;		
	}

	public DODeliveryReportDataDTO[] getDODeliveryReport(Long number, String requestnumber, String text, Long vendorid, Date since, Date until, String dotype, int filtertype, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, String sendnumber, Long withdrawalnumber) throws OperationFailedException, AccessDeniedException{
		
		String SQL = getQueryString(2, filtertype, orderby);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		switch (filtertype) {
		case 0:
			query.setString("dodstcode", "EN_RUTA");
			break;
		// JPE 20180420
		case 1:
			query.setParameterList("dodstcodes", new String[]{"ETIQ_POR_SOLICITAR", "ETIQ_SOLICITADA", "PEND_AGENDAR", "PEND_RETIRO"});	
			break;
		case 2:
			query.setLong("ordernumber", number);
			break;
		case 3:
			query.setString("requestnumber" , requestnumber);
			break;
		case 4:
			query.setString("clientrut", text);	
			break;
		case 5:
			query.setString("dotype", dotype);
			query.setDate("since", since);	
			query.setDate("until", until);	
			break;
		case 6:
			query.setString("sendnumber", sendnumber);	
			break;
		case 7:
			query.setLong("withdrawalnumber", withdrawalnumber);	
			break;
		default:
			break;
		}				
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		DODeliveryReportDataDTO[] result = (DODeliveryReportDataDTO[]) list.toArray(new DODeliveryReportDataDTO[list.size()]);		
		return result;
	}
	
	public Integer getDODeliveryCountReport(Long number, String text, Long vendorid, Date since, Date until, String dotype,int filtertype, String sendnumber, Long withdrawalnumber) throws OperationFailedException, AccessDeniedException{
		
		String SQL = getQueryString(1, filtertype, null);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		switch (filtertype) {
		case 0:
			query.setString("dodstcode", "EN_RUTA");
			break;
		// JPE 20180420
		case 1:
			query.setParameterList("dodstcodes", new String[]{"ETIQ_POR_SOLICITAR", "ETIQ_SOLICITADA", "PEND_AGENDAR", "PEND_RETIRO"});	
			break;
		case 2:
			query.setLong("ordernumber", number);
			break;
		case 3:
			query.setString("requestnumber" , text);
			break;
		case 4:
			query.setString("clientrut", text);	
			break;
		case 5:
			query.setString("dotype", dotype);
			query.setDate("since", since);	
			query.setDate("until", until);	
			break;
		case 6:
			query.setString("sendnumber", sendnumber);	
			break;
		case 7:
			query.setLong("withdrawalnumber", withdrawalnumber);	
			break;
		default:
			break;
		}				
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;	
	}
	
	public int getDODeliveryReportCountByDeliveries(Long[] deliveryIds){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getDODeliveryReportCountByDeliveries");
		query.setParameterList("deliveryids", deliveryIds);
		int total = ((BigInteger)query.list().get(0)).intValue();	
		return total;		
	}	
	
	public ExcelDODeliveryReportDataDTO[] getExcelDODeliveryReportByOrders(Long[] deliveryids) throws ServiceException {
		
		if (deliveryids == null || deliveryids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}
		
		// Se divide el parámetro de entrada en bloques de 50 elementos
		ExcelDODeliveryReportDataDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(deliveryids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new ExcelDODeliveryReportDataDTO[0];
				continue;
			}
			
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getExcelDODeliveryReportByOrders");
			query.setParameterList("deliveryids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(ExcelDODeliveryReportDataDTO.class));
			
			List list = query.list();
			ExcelDODeliveryReportDataDTO[] result = (ExcelDODeliveryReportDataDTO[]) list.toArray(new ExcelDODeliveryReportDataDTO[list.size()]);
			reportarray[i] = result;
		}
		
		ocreport = (ExcelDODeliveryReportDataDTO[]) ClassUtilities.UnsplitArrays(reportarray, ExcelDODeliveryReportDataDTO.class);
		return ocreport;
	}
	
	public DODeliverySourceDataDTO[] getDODeliveryDataSource(Long[] deliveryids) throws ServiceException {
		if (deliveryids == null || deliveryids.length <= 0) {
			throw new OperationFailedException("No se puede generar dato fuente de entregas. Parámetro nulo o vacío.");
		}
		
		// Se divide el parámetro de entrada en bloques de 50 elementos
		DODeliverySourceDataDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(deliveryids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new DODeliverySourceDataDTO[0];
				continue;
			}
			
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getDODeliverySourceData");
			query.setParameterList("deliveryids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(DODeliverySourceDataDTO.class));
			
			List list = query.list();
			DODeliverySourceDataDTO[] result = (DODeliverySourceDataDTO[]) list.toArray(new DODeliverySourceDataDTO[list.size()]);
			reportarray[i] = result;
		}
		
		ocreport = (DODeliverySourceDataDTO[]) ClassUtilities.UnsplitArrays(reportarray, DODeliverySourceDataDTO.class);
		return ocreport;
	}
	
	public DeliveryIdsByPagesW getDODeliveryIdsByPages(Long vendorid, Long ocnumber, String requestnumber, String clientrut, Date since, Date until, String dotype, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges, String sendnumber, Long withdrawalnumber) throws ServiceException{
		
		DeliveryIdsByPagesW resultW =  new DeliveryIdsByPagesW();
		
		int total = 0;		
		DODeliveryReportDataDTO[] result = null;
		DODeliveryReportDataDTO[] orders;		
		List<Object> totalOrders = new ArrayList<Object>();
						
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				
				switch (filterType) {
				// EN RUTA
				case 0:
					result = getDODeliveryReport(null, null, null, vendorid, null, null, null, filterType, j, rows, orderby, true, null, null);
					break;
				// JPE 20180720
				case 1:
					// EN PREPARAción COURIER
					result = getDODeliveryReport(null, null, null, vendorid, null, null, null, filterType, j, rows, orderby, true, sendnumber, withdrawalnumber);
					break;
				case 2:
					// N°MERO DE OC
					result = getDODeliveryReport(ocnumber, null, null, vendorid, null, null, null, filterType, j, rows, orderby, true, null, null);
					break;
				case 3:
					// N°MERO DE SOLICITUD
					result = getDODeliveryReport(null, requestnumber, null, vendorid, null, null, null, filterType, j, rows, orderby, true, null, null);
					break;
				case 4:
					// RUT CLIENTE
					result = getDODeliveryReport(null, null, clientrut, vendorid, null, null, null, filterType, j, rows, orderby, true, null, null);
					break;
				case 5:
					// ESTADO DE DESPACHO
					result = getDODeliveryReport(null, null, null, vendorid, since, until, dotype, filterType, j, rows, orderby, true, null, null);
					break;
				case 6:
					// N°MERO DE envío COURIER
					result = getDODeliveryReport(null, null, null, vendorid, null, null, null, filterType, j, rows, orderby, true, sendnumber, withdrawalnumber);
					break;
				case 7:
					// N°MERO DE RETIRO COURIER
					result = getDODeliveryReport(null, null, null, vendorid, null, null, null, filterType, j, rows, orderby, true, sendnumber, withdrawalnumber);
					break;
				default:
					break;
				}		
				
				totalOrders.add(result);					
			}			
		}
		Object[] reportorderarray = (Object[]) totalOrders.toArray(new Object[totalOrders.size()]);		
		orders = (DODeliveryReportDataDTO[]) ClassUtilities.UnsplitArrays(reportorderarray, DODeliveryReportDataDTO.class);
		
		Long[] orderIds = new Long[orders.length];
		
		//Obtengo solo los ids de las ordenes de las paginas solicitadas		
		for (int i = 0 ; i < orders.length; i++){
			orderIds[i] = orders[i].getDeliveryid();
		}
		
		//Consultar por cantidad de registros que lanzar�a reporte excel
		if (orderIds.length > 0){
			total = getDODeliveryReportCountByDeliveries(orderIds);
			
		}
		resultW.setDeliveryIds(orderIds);
		resultW.setTotalRows(total);		
		return resultW;		
	}	
	
	
	public DODeliveryReportDataMobileDTO[] getDODeliveryDataByTruckDispatcher(String truckdispatcheremail, Long[] validstatetypeids){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getDODeliveryDataByTruckDispatcher");
		query.setString("truckdispatcheremail", truckdispatcheremail);
		query.setParameterList("validstatetypeids", validstatetypeids);
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryReportDataMobileDTO.class));
		List list = query.list();
		DODeliveryReportDataMobileDTO[] result = (DODeliveryReportDataMobileDTO[]) list.toArray(new DODeliveryReportDataMobileDTO[list.size()]);
		return result;	
	}
	
	// Detalles del despacho Mobile
	public DODeliveryMobileDataDTO getDODeliveryDataById(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getDODeliveryDataById");
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryMobileDataDTO.class));
		DODeliveryMobileDataDTO result = (DODeliveryMobileDataDTO)query.uniqueResult();
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
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DODelivery";
	}
	@Override
	protected void setEntityname() {
		entityname = "DODelivery";
	}
	
	public PDFDODeliveryReportDataDTO getPDFDODeliveryReport(Long vendorid, Long deliveryid) throws ServiceException {
		String whereCondition = "";		
				whereCondition = " AND DODL.ID = :deliveryid ";
				
		String SQL = getPDFDODeliveryReportQueryString(whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);	
		query.setLong("deliveryid", deliveryid);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(PDFDODeliveryReportDataDTO.class));
		PDFDODeliveryReportDataDTO result = (PDFDODeliveryReportDataDTO) query.uniqueResult();		
		
		return result;	
	}
	
	private String getPDFDODeliveryReportQueryString(String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";	
		
			SQL = 	
			"SELECT " +
					"DODL.ID, " + 
					"DODL.NUMBER AS DELIVERYNUMBER, " +
					"DOC.NUMBER AS DOCNUMBER, " + 
					"DOC.REQUESTNUMBER, " +
					"TO_CHAR(DODL.COMMITEDDATE, 'DD-MM-YYYY') as ORIGINALDELIVERYDATE, " +
					"CL.RUT AS CLIENTRUT, " + 
					"CL.NAME AS CLIENTNAME, " +
					"CL.PHONE AS CLIENTPHONE, " +
					"DOC.CLIENTADDRESS AS CLIENTADDRESS, " +
					"LOC.NAME AS SELLERSTORE, " +
					"DOC.CLIENTCITY AS CITYDELIVERY, "+ 
					"DOC.CLIENTCOMMUNE AS COMMUNEDELIVERY, "+  
					"DOC.CLIENTREGION AS REGIONDELIVERY   " ;			
				
			SQL = SQL + 
				"FROM " +
					"LOGISTICA.DODELIVERY DODL JOIN "+
					"LOGISTICA.DIRECTORDER DOC ON DODL.ORDER_ID = DOC.ID JOIN " 	+				
					"LOGISTICA.CLIENT CL ON DOC.CLIENT_ID = CL.ID LEFT JOIN " 	+ 
					"LOGISTICA.LOCATION AS LOC ON LOC.ID = DOC.SALESTORE_ID " 	+
					"WHERE " +  
					"DOC.VENDOR_ID = :vendorid " 								+
					whereCondition;
			return SQL;		
			}
	

	public PDFDODeliveryReportDataDTO[] getPDFDODeliveryReportByIDs(Long vendorid, Long[] deliveryid) throws ServiceException {
		String whereCondition = "";		
		whereCondition = " AND DODL.ID in (:deliveryid) ";
		String SQL = getPDFDODeliveryReportQueryString(whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);	
		query.setParameterList("deliveryid", deliveryid);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(PDFDODeliveryReportDataDTO.class));
		
		List list = query.list();
		PDFDODeliveryReportDataDTO[] result = (PDFDODeliveryReportDataDTO[]) list.toArray(new PDFDODeliveryReportDataDTO[list.size()]);		
		return result;	
	}
	
	public PDFDODeliveryDetailReportDataDTO[] getPDFDeliveryDetailReport(Long vendorid, Long dodeliveryid) throws ServiceException{
		String SQL = getPDFDeliveryDetailReportQueryString();		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", dodeliveryid);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(PDFDODeliveryDetailReportDataDTO.class));
//		if(ispaginated){//si quiero el reporte paginado
//			query.setFirstResult((pagenumber-1) * rows);
//			query.setMaxResults(rows);
//		}
		List list = query.list();
		PDFDODeliveryDetailReportDataDTO[] result = (PDFDODeliveryDetailReportDataDTO[]) list.toArray(new PDFDODeliveryDetailReportDataDTO[list.size()]);		
		return result;	
	}

	private String getPDFDeliveryDetailReportQueryString() throws AccessDeniedException{
			
			String SQL 				= "";
			String condicionOrderBy	= "";
					
				SQL = 	
				"SELECT	" +  	
					"IT.INTERNALCODE AS ITEMSKU, " +
					"IT.NAME AS ITEMDESCRIPTION, " +
					"cast(OD.todeliveryunits as integer) as PENDINGUNITS " ;
			
			SQL =		
				SQL +
				"FROM " + 
					"LOGISTICA.DODELIVERY DODL JOIN " +	
					"LOGISTICA.DIRECTORDER O ON DODL.ORDER_ID = O.ID JOIN " 	+
					"LOGISTICA.DIRECTORDERDETAIL OD ON O.ID = OD.ORDER_ID JOIN " +				
					"LOGISTICA.VENDOR AS VD ON VD.ID = O.VENDOR_ID JOIN " +
					"LOGISTICA.ITEM AS IT ON IT.ID = OD.ITEM_ID " + 
				"WHERE " +
					"DODL.ID = :deliveryid AND VD.ID = :vendorid " +			
				condicionOrderBy;		
			return SQL;		
	}
	
	public DODeliveryWSRequestData[] getWSRequestDataByDODeliveryId(Long dodeliveryid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getWSRequestDataByDODeliveryId");
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryWSRequestData.class));
		List list = query.list();
		DODeliveryWSRequestData[] result = (DODeliveryWSRequestData[]) (DODeliveryWSRequestData[]) list.toArray(new DODeliveryWSRequestData[list.size()]) ;
		return result;
	}
	
	public Integer countDODeliveryCouriers(Long dodeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.countDODeliveryCouriers");
		query.setLong("dodeliveryid", dodeliveryid);
//		query.setResultTransformer(new LowerCaseResultTransformer(DODeliveryWSRequestData.class));
		Integer result = ((BigInteger)query.list().get(0)).intValue();
//		Integer total = (Integer) query.uniqueResult();
		return result;
	}
	
	public Long[] getDODeliveryIdsByNumbers(List<Long> numbers){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getDODeliveryIdsByNumbers");
		query.setParameterList("numbers", numbers);
				
		List list = query.list();
		Long[] result = new Long[list.size()];
		// El valor en la query es un BigInteger y debe convertirse a Long
		for(int i = 0; i < list.size(); i++){
			result[i] =((BigInteger)list.get(i)).longValue();			 
		}
		
		return result;	
	}
	
	public ShippingCertificationDTO getShippingCertificationReport(Long dodeliveryid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.deliveries.entities.DODelivery.getShippingCertificationReport");
		query.setLong("dodeliveryid", dodeliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(ShippingCertificationDTO.class));
		ShippingCertificationDTO result = (ShippingCertificationDTO)query.uniqueResult();
		return result;
	}

}
