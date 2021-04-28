package bbr.b2b.regional.logistic.directorders.classes;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.regional.logistic.buyorders.entities.Client;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDExcelOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportTotalDataDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVVendorNotificationDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.DODelivery;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrderStateType;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderReportDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.report.classes.PendingSOAOrderDTO;
import bbr.b2b.regional.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.regional.logistic.soa.entities.SOAStateType;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Responsable;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/directorders/DirectOrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DirectOrderServer extends LogisticElementServer<DirectOrder, DirectOrderW> implements DirectOrderServerLocal {
	
	private Map<String, String> mapGetVeVPDOrderSQL = new HashMap<String, String>();
	{
		mapGetVeVPDOrderSQL.put("DOCNUMBER", "DOC.NUMBER ");
		mapGetVeVPDOrderSQL.put("REQUESTNUMBER", "lpad(DOC.REQUESTNUMBER, 30, '0')");//para ordenamiento de string como numero
		mapGetVeVPDOrderSQL.put("DORDERESTATETYPECODE", "DOST.CODE");
		mapGetVeVPDOrderSQL.put("DORDERESTATETYPENAME", "DOST.NAME");
		mapGetVeVPDOrderSQL.put("DODELIVERYSTATATYPE", "DODST.NAME ");
		mapGetVeVPDOrderSQL.put("CURRENTSTATETYPEDATE", "DOC.CURRENTSTATETYPEDATE");
		mapGetVeVPDOrderSQL.put("EMITTED", "DOC.EMITTED");
		mapGetVeVPDOrderSQL.put("VALID", "DOC.ORIGINALDELIVERYDATE");
		mapGetVeVPDOrderSQL.put("REPROGRAMMINGDATE", "DOC.CURRENTDELIVERYDATE");
		mapGetVeVPDOrderSQL.put("CLIENTRUT", "CL.RUT");
		mapGetVeVPDOrderSQL.put("CLIENTNAME", "CL.NAME");
		mapGetVeVPDOrderSQL.put("CLIENTCOMMUNE", "DOC.CLIENTCOMMUNE");
		mapGetVeVPDOrderSQL.put("CLIENTADDRESS", "DOC.CLIENTADDRESS");
		mapGetVeVPDOrderSQL.put("CLIENTCITY", "DOC.CLIENTCITY");
		mapGetVeVPDOrderSQL.put("COMMENT", "DOC.COMMENT");
		mapGetVeVPDOrderSQL.put("NEEDUNITS", "DOC.NEEDUNITS");
		mapGetVeVPDOrderSQL.put("TOTALNEED", "DOC.TOTALNEED");
		mapGetVeVPDOrderSQL.put("TODELIVERYUNITS", "DOC.TODELIVERYUNITS");
		mapGetVeVPDOrderSQL.put("RECEIVEDUNITS", "DOC.RECEIVEDUNITS");
		mapGetVeVPDOrderSQL.put("PENDINGUNITS", "DOC.PENDINGUNITS");
		mapGetVeVPDOrderSQL.put("TOTALRECEIVED", "DOC.TOTALRECEIVED");
		mapGetVeVPDOrderSQL.put("TOTALTODELIVERY", "DOC.TOTALTODELIVERY");
		mapGetVeVPDOrderSQL.put("TOTALPENDING", "DOC.TOTALPENDING");
		mapGetVeVPDOrderSQL.put("SALESTORE", "LOSS.CODE");
		mapGetVeVPDOrderSQL.put("SENDNUMBER", "CT.SENDNUMBER");
	}	

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ResponsableServerLocal responsableserver;

//	@IgnoreDependency
	@EJB
	DODeliveryServerLocal currentdeliveryserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	ClientServerLocal clientserver;

	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	DirectOrderStateTypeServerLocal currentstatetypeserver;
		
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	public DirectOrderW addDirectOrder(DirectOrderW directorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderW) addElement(directorder);
	}
	public DirectOrderW[] getDirectOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderW[]) getIdentifiables();
	}
	public DirectOrderW updateDirectOrder(DirectOrderW directorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DirectOrderW) updateElement(directorder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DirectOrder entity, DirectOrderW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setResponsableid(entity.getResponsable() != null ? new Long(entity.getResponsable().getId()) : new Long(0));
		wrapper.setDodeliveryid(entity.getCurrentdelivery() != null ? new Long(entity.getCurrentdelivery().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setClientid(entity.getClient() != null ? new Long(entity.getClient().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setCurrentsoastatetypeid(entity.getCurrentsoastatetype() != null ? new Long(entity.getCurrentsoastatetype().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DirectOrder entity, DirectOrderW wrapper) throws OperationFailedException, NotFoundException {
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
		if (wrapper.getDodeliveryid() != null && wrapper.getDodeliveryid() > 0) { 
			DODelivery currentdelivery = currentdeliveryserver.getReferenceById(wrapper.getDodeliveryid());
			if (currentdelivery != null) { 
				entity.setCurrentdelivery(currentdelivery);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
		if (wrapper.getClientid() != null && wrapper.getClientid() > 0) { 
			Client client = clientserver.getReferenceById(wrapper.getClientid());
			if (client != null) { 
				entity.setClient(client);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DirectOrderStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		if (wrapper.getCurrentsoastatetypeid() != null && wrapper.getCurrentsoastatetypeid() > 0) {
			SOAStateType soastatetype = soastatetypeserver.getReferenceById(wrapper.getCurrentsoastatetypeid());
			if (soastatetype != null) {
				entity.setCurrentsoastatetype(soastatetype);
			}
		}
		if (wrapper.getSalestoreid() != null && wrapper.getSalestoreid() > 0) {
			Location salestore = locationserver.getReferenceById(wrapper.getSalestoreid());
			if (salestore != null) {
				entity.setSalestore(salestore);
			}
		}				
	}

	private String getVeVPDOrderReportQueryString(int queryType, OrderCriteriaDTO[] orderby, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
			
		if (queryType == 1){
			SQL = 
				"SELECT " +
					"CAST(COUNT(DOC.ID) AS integer) AS TOTALREG, " +
					"SUM(DOC.NEEDUNITS) AS NEEDUNITS, " +
					"SUM(DOC.TOTALNEED) AS TOTALNEED, " +					
					"SUM(DOC.TODELIVERYUNITS) AS TODELIVERYUNITS, " + 
					"SUM(DOC.RECEIVEDUNITS) AS RECEIVEDUNITS, " + 
					"SUM(DOC.PENDINGUNITS) AS PENDINGUNITS, " + 
					"SUM(DOC.TOTALRECEIVED) AS TOTALRECEIVED, " + 
					"SUM(DOC.TOTALTODELIVERY) AS TOTALTODELIVERY, " + 
					"SUM(DOC.TOTALPENDING) AS TOTALPENDING ";
		}
		else {
			SQL = 	
			"SELECT " +
					"DOC.ID, " + 
					"DOC.NUMBER AS DOCNUMBER, " + 
					"DOC.REQUESTNUMBER, " + 
					"DOST.CODE AS DORDERESTATETYPECODE, " +
					"DOST.NAME AS DORDERESTATETYPENAME, " +
					"COALESCE(DODST.NAME, '') AS DODELIVERYSTATATYPE, " +
					"LOGISTICA.TOSTR(DOC.CURRENTSTATETYPEDATE) AS CURRENTSTATETYPEDATE, " + 
					"LOGISTICA.TOSTR(DOC.EMITTED) AS EMITTED, " + 
					"LOGISTICA.TOSTR(DOC.ORIGINALDELIVERYDATE) AS VALID, " +
					"LOGISTICA.TOSTR(DOC.CURRENTDELIVERYDATE) AS REPROGRAMMINGDATE, " + 
					"CL.RUT AS CLIENTRUT, " + 
					"CL.NAME AS CLIENTNAME, " +
					"CL.PHONE AS CLIENTPHONE, " +
					"DOC.CLIENTCOMMUNE AS CLIENTCOMMUNE, " +
					"DOC.CLIENTADDRESS AS CLIENTADDRESS, " +
					"DOC.CLIENTCITY AS CLIENTCITY, " +
					"DOC.COMMENT, " +
					"DOC.NEEDUNITS, " +
					"DOC.TOTALNEED, " +					
					"DOC.TODELIVERYUNITS AS TODELIVERYUNITS, " + 
					"DOC.RECEIVEDUNITS AS RECEIVEDUNITS, " + 
					"DOC.PENDINGUNITS AS PENDINGUNITS, " + 
					"DOC.TOTALRECEIVED AS TOTALRECEIVED, " + 
					"DOC.TOTALTODELIVERY AS TOTALTODELIVERY, " + 
					"DOC.TOTALPENDING AS TOTALPENDING, "+
					"LOSS.CODE || ' - ' || LOSS.NAME AS SALESTORE, "+
					"CT.SENDNUMBER ";
						
				condicionOrderBy = getOrderByString(mapGetVeVPDOrderSQL, orderby);				
		}
		SQL = SQL + 
			"FROM " +
				"LOGISTICA.DIRECTORDER DOC JOIN " +				
				"LOGISTICA.DIRECTORDERSTATETYPE AS DOST ON DOST.ID = DOC.CURRENTSTATETYPE_ID JOIN " + 
				"LOGISTICA.CLIENT CL ON DOC.CLIENT_ID = CL.ID LEFT JOIN " + 
				"LOGISTICA.DODELIVERY AS DL ON DOC.DODELIVERY_ID = DL.ID LEFT JOIN " +
				"LOGISTICA.LOCATION AS LOSS ON DOC.SALESTORE_ID = LOSS.ID LEFT JOIN " +
				"LOGISTICA.DODELIVERYSTATETYPE AS DODST ON DODST.ID = DL.CURRENTSTATETYPE_ID LEFT JOIN " + 
				"LOGISTICA.COURIERTAG AS CT ON CT.DODELIVERY_ID = DL.ID  "+
			"WHERE " +  
				"DOC.VENDOR_ID = :vendorid " +
				whereCondition +				  
				condicionOrderBy;					
		return SQL;		
	}
	
	private String getPDFVeVPDOrderReportQueryString(String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";	
		
			SQL = 	
			"SELECT " +
					"DOC.ID, " + 
					"DOC.NUMBER AS DOCNUMBER, " + 
					"DOC.REQUESTNUMBER, " +
					"TO_CHAR(DOC.ORIGINALDELIVERYDATE, 'DD-MM-YYYY') as ORIGINALDELIVERYDATE, " +
					"CL.RUT AS CLIENTRUT, " + 
					"CL.NAME AS CLIENTNAME, " +
					"CL.PHONE AS CLIENTPHONE, " +
					"DOC.CLIENTADDRESS AS CLIENTADDRESS, " +
					"LOC.NAME AS SELLERSTORE, " +
					"LOC.CITY AS CITYDELIVERY, " +
					"LOC.COMMUNE AS COMMUNEDELIVERY, " +
					"LOC.ADDRESS AS REGIONDELIVERY " ;			
				
		SQL = SQL + 
			"FROM " +
				"LOGISTICA.DIRECTORDER DOC JOIN " +				
				"LOGISTICA.CLIENT CL ON DOC.CLIENT_ID = CL.ID LEFT JOIN " + 
				"LOGISTICA.LOCATION AS LOC ON LOC.ID = DOC.SALESTORE_ID " +
			"WHERE " +  
				"DOC.VENDOR_ID = :vendorid " +
				whereCondition;
		return SQL;		
	}

	private String getPDFVeVPDOrderReportQueryStringByIDs(String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";	
		
			SQL = 	
			"SELECT " +
					"DOC.ID, " + 
					"DOC.NUMBER AS DOCNUMBER, " + 
					"DOC.REQUESTNUMBER, " +
					"TO_CHAR(DOC.ORIGINALDELIVERYDATE, 'DD-MM-YYYY') as ORIGINALDELIVERYDATE, " +
					"CL.RUT AS CLIENTRUT, " + 
					"CL.NAME AS CLIENTNAME, " +
					"CL.PHONE AS CLIENTPHONE, " +
					"DOC.CLIENTADDRESS AS CLIENTADDRESS, " +
					"LOC.NAME AS SELLERSTORE, " +
					"LOC.CITY AS CITYDELIVERY, " +
					"LOC.COMMUNE AS COMMUNEDELIVERY, " +
					"LOC.ADDRESS AS REGIONDELIVERY " ;			
				
		SQL = SQL + 
			"FROM " +
				"LOGISTICA.DIRECTORDER DOC JOIN " +				
				"LOGISTICA.CLIENT CL ON DOC.CLIENT_ID = CL.ID LEFT JOIN " + 
				"LOGISTICA.LOCATION AS LOC ON LOC.ID = DOC.SALESTORE_ID " +
			"WHERE " +  
				"DOC.VENDOR_ID = :vendorid " +
				whereCondition;
		return SQL;		
	}
	
	public VeVPDOrderReportDataDTO[] getVeVPDOrdersByVendorAndCriterium(Long vendorid, Long ocnumber, Long orderstatetypeid, String requestnumber, String clientrut, Date since, Date until, Integer criterium, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, Long[] salestoresid, String sendnumber) throws ServiceException {
		String whereCondition = "";
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition = "AND DOST.VALID = TRUE ";			
			break;
		case 1:
			// N°MERO DE ORDEN
			whereCondition = "AND DOC.NUMBER = :ordernumber ";			
			break;
		case 2:
			// ESTADO DE ORDEN
			whereCondition = "AND DOST.ID = :osttypeid ";
			break;
		case 3:
			// N°MERO DE SOLICITUD
			whereCondition = "AND DOC.REQUESTNUMBER = :requestnumber ";
			break;
		case 4:
			// RUT DE CLIENTE
			whereCondition = "AND CL.RUT = :clientrut ";
			break;
		case 5:
			// FECHA DE COMPROMISO
			whereCondition = "AND (DOC.ORIGINALDELIVERYDATE >= :since AND DOC.ORIGINALDELIVERYDATE <= :until) ";
			break;
		case 6:
			//NUMERO DE ENVIO COURIER
			whereCondition = "AND CT.SENDNUMBER = :sendnumber ";
			break;				
		default:
			break;
		}	
		
		if(salestoresid != null && salestoresid.length>0 && salestoresid[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += " AND DOC.SALESTORE_ID in (:salestoresid) ";
		}
				
		String SQL = getVeVPDOrderReportQueryString(2, orderby, whereCondition);	
		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);	
		
		if(salestoresid != null && salestoresid.length>0 && salestoresid[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoresid", salestoresid);
		}
		
		switch (criterium) {
		case 0:
			// VIGENTES
			break;
		case 1:
			// N°MERO DE ORDEN
			query.setLong("ordernumber", ocnumber);	
			break;
		case 2:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 3:
			// N°MERO DE SOLICITUD
			query.setString("requestnumber", requestnumber);
			break;
		case 4:
			// RUT DE CLIENTE
			query.setString("clientrut", clientrut);			
			break;
		case 5:
			// FECHA DE COMPROMISO
			query.setDate("since", since);
			query.setDate("until", until);
			break;		
		case 6:
			// NUMERO DE ENVIO COURIER
			query.setString("sendnumber", sendnumber);			
			break;
		default:
			break;
		}		
		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVPDOrderReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		VeVPDOrderReportDataDTO[] result = (VeVPDOrderReportDataDTO[]) list.toArray(new VeVPDOrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public PDFVeVPDOrderReportDataDTO[] getPDFVeVPDOrder(Long vendorid, Long ocnumber) throws ServiceException {
		String whereCondition = "";
		
		whereCondition = "AND DOC.NUMBER = :ordernumber ";				
				
		String SQL = getPDFVeVPDOrderReportQueryString(whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);		
		
		query.setLong("ordernumber", ocnumber);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(PDFVeVPDOrderReportDataDTO.class));
		
		List list = query.list();
		PDFVeVPDOrderReportDataDTO[] result = (PDFVeVPDOrderReportDataDTO[]) list.toArray(new PDFVeVPDOrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public PDFVeVPDOrderReportDataDTO[] getPDFVeVPDOrderByIDs(Long vendorid, List<Long> docId) throws ServiceException {
		String whereCondition = "";
		
		if(docId != null && docId.size()>0)
		whereCondition = "AND DOC.ID in (:ordersID) ";				
				
		String SQL = getPDFVeVPDOrderReportQueryStringByIDs(whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setLong("vendorid", vendorid);		
		
		if(docId != null && docId.size()>0)
		query.setParameterList("ordersID", docId);	
		
		query.setResultTransformer(new LowerCaseResultTransformer(PDFVeVPDOrderReportDataDTO.class));
		
		List list = query.list();
		PDFVeVPDOrderReportDataDTO[] result = (PDFVeVPDOrderReportDataDTO[]) list.toArray(new PDFVeVPDOrderReportDataDTO[list.size()]);		
		return result;	
	}
	
	public VeVPDOrderReportTotalDataDTO getVeVPDOrdersCountByVendorAndCriterium(Long vendorid, Long ocnumber, Long orderstatetypeid, String requestnumber, String clientrut, Date since, Date until, Integer criterium, Long[] salestoresid, String sendnumber) throws ServiceException{
		String whereCondition = "";
		
		switch (criterium) {
		case 0:
			// VIGENTES
			whereCondition = "AND DOST.VALID = TRUE ";			
			break;
		case 1:
			// N°MERO DE ORDEN
			whereCondition = "AND DOC.NUMBER = :ordernumber ";			
			break;
		case 2:
			// ESTADO DE ORDEN
			whereCondition = "AND DOST.ID = :osttypeid ";
			break;
		case 3:
			// N°MERO DE SOLICITUD
			whereCondition = "AND DOC.REQUESTNUMBER = :requestnumber ";
			break;
		case 4:
			// RUT DE CLIENTE
			whereCondition = "AND CL.RUT = :clientrut ";
			break;
		case 5:
			// FECHA DE COMPROMISO
			whereCondition = "AND (DOC.ORIGINALDELIVERYDATE >= :since AND DOC.ORIGINALDELIVERYDATE <= :until) ";
			break;	
		case 6:
			//NUMERO DE ENVIO COURIER
			whereCondition = "AND CT.SENDNUMBER = :sendnumber ";
			break;	
		default:
			break;
		}	
		
		if(salestoresid != null && salestoresid.length>0 && salestoresid[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			whereCondition += " AND DOC.SALESTORE_ID in (:salestoresid) ";
		}
		
		String SQL = getVeVPDOrderReportQueryString(1, null, whereCondition);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		
		if(salestoresid != null && salestoresid.length>0 && salestoresid[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoresid", salestoresid);
		}

		switch (criterium) {
		case 0:
			// VIGENTES
			break;
		case 1:
			// N°MERO DE ORDEN
			query.setLong("ordernumber", ocnumber);	
			break;
		case 2:
			// ESTADO DE ORDEN
			query.setLong("osttypeid", orderstatetypeid);
			break;
		case 3:
			// N°MERO DE SOLICITUD
			query.setString("requestnumber", requestnumber);
			break;
		case 4:
			// RUT DE CLIENTE
			query.setString("clientrut", clientrut);			
			break;
		case 5:
			// FECHA DE COMPROMISO
			query.setDate("since", since);
			query.setDate("until", until);
			break;	
		case 6:
			// NUMERO DE ENVIO COURIER
			query.setString("sendnumber", sendnumber);			
			break;
		default:
			break;
		}			
				
		query.setResultTransformer(new LowerCaseResultTransformer(VeVPDOrderReportTotalDataDTO.class));
		VeVPDOrderReportTotalDataDTO result = (VeVPDOrderReportTotalDataDTO) query.uniqueResult();				
		return result;
	}			
	
	public DirectOrderReportDTO[] getDirectOrderReportByVendorAndNumber(Long ordernumber, Long vendorid) throws ServiceException{
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getDirectOrderReportByVendorAndNumber");
		query.setLong("ordernumber", ordernumber);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(DirectOrderReportDTO.class));
		List list = query.list();
		DirectOrderReportDTO[] result = (DirectOrderReportDTO[]) list.toArray(new DirectOrderReportDTO[list.size()]);
		return result;
	}
	
	public int getDirectOrdersReportCountByOrders(Long[] orderIds){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getCountDirectOrderReportByOrder");
		query.setParameterList("orderids", orderIds);
		int total = ((BigInteger)query.list().get(0)).intValue();	
		return total;		
	}	
	
	public VeVPDExcelOrderReportDataDTO[] getExcelVeVPDOrderReportByOrders(Long[] orderids) throws ServiceException {
		if (orderids == null || orderids.length <= 0) {
			throw new OperationFailedException("No se puede generar reporte de órdenes. Parámetro nulo o vacío.");
		}
		
		// Se divide el parámetro de entrada en bloques de 50 elementos
		VeVPDExcelOrderReportDataDTO[] ocreport = null;
		int chunksize = 50;
		Object[] splitids = ClassUtilities.SplitArray(orderids, Long.class, chunksize);
		Object[] reportarray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportarray[i] = new VeVPDExcelOrderReportDataDTO[0];
				continue;
			}
			
			SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getExcelVeVPDOrderReportByOrder");
			query.setParameterList("orderids", ids);
			query.setResultTransformer(new LowerCaseResultTransformer(VeVPDExcelOrderReportDataDTO.class));
			
			List list = query.list();
			VeVPDExcelOrderReportDataDTO[] result = (VeVPDExcelOrderReportDataDTO[]) list.toArray(new VeVPDExcelOrderReportDataDTO[list.size()]);
			reportarray[i] = result;
		}
		
		ocreport = (VeVPDExcelOrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportarray, VeVPDExcelOrderReportDataDTO.class);
		return ocreport;
	}
	
	public OrderIdsByPagesW getVeVPDOrdersIdsByPages(Long vendorid, Long ocnumber, Long ostid, String requestnumber, String clientrut, Date since, Date until, int rows, Integer filterType, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges, Long[] salesstoresid, String sendnumber) throws ServiceException{
		
		OrderIdsByPagesW resultW =  new OrderIdsByPagesW();
		
		int total = 0;		
		VeVPDOrderReportDataDTO[] result;
		VeVPDOrderReportDataDTO[] orders;		
		List<Object> totalOrders = new ArrayList<Object>();
						
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage(); j <= pageranges[i].getUntilPage(); j++){				
				result = getVeVPDOrdersByVendorAndCriterium(vendorid, ocnumber, ostid, requestnumber, clientrut, since, until, filterType, j, rows, orderby, true, salesstoresid, sendnumber);
				totalOrders.add(result);					
			}			
		}
		Object[] reportorderarray = (Object[]) totalOrders.toArray(new Object[totalOrders.size()]);		
		orders = (VeVPDOrderReportDataDTO[]) ClassUtilities.UnsplitArrays(reportorderarray, VeVPDOrderReportDataDTO.class);
		
		Long[] orderIds = new Long[orders.length];
		
		//Obtengo solo los ids de las ordenes de las paginas solicitadas		
		for (int i = 0 ; i < orders.length; i++){
			orderIds[i] = orders[i].getId();
		}
		
		//Consultar por cantidad de registros que lanzar�a reporte excel
		if (orderIds.length > 0){
			total = getDirectOrdersReportCountByOrders(orderIds);
			
		}
		resultW.setOrderIds(orderIds);
		resultW.setTotalRows(total);		
		return resultW;		
	}	
	
	public Integer doReceiveDirectOrders() throws OperationFailedException{
		
		Integer countorders = 0;
		
		String SQL = "select logistica.recibe_direct_oc()";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		countorders = ((Integer) query.uniqueResult());				
		return countorders;
		
//		Integer countorders = 0;
//		ResultSet resultSet;
//		
//		try{
//			String sql = "select * from logistica.recibe_direct_oc()";
//			CallableStatement callStmt1 = getSession().connection().prepareCall(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//			resultSet = callStmt1.executeQuery();
//			if (resultSet.absolute(1))
//				countorders = resultSet.getInt(1);
//			return countorders;								
//		} catch (SQLException e) {
//			throw new OperationFailedException("Error al ejecutar CallableStatement", e);
//		}		
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
		entitylabel = "DirectOrder";
	}
	@Override
	protected void setEntityname() {
		entityname = "DirectOrder";
	}
		
	/*
	 * Retorna las oc pendientes por enviar a SOA
	 * Se puede agregar mas de un estado soa pendiente por enviar
	 * */
	public DirectOrderW[] getOrdersToSendSoa(Long vendorid, Date since, Long... soastateids) throws OperationFailedException, NotFoundException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[4];
		
		properties[0] = new PropertyInfoDTO("ost.code", "occodestate", "LIBERADA");
		properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
		properties[2] = new PropertyInfoDTO("os.when", "since", since );
        properties[3] = new PropertyInfoDTO("currentsoastatetype.id", "currentsoastatetypeid", Arrays.asList(soastateids));

        StringBuilder sb = new StringBuilder("Select distinct oc FROM DirectOrder oc, DirectOrderState os, DirectOrderStateType ost " +	//tablas
        		"where oc.id = os.directorder.id and os.directorderstatetype.id = ost.id " +	//on de tablas
        		"and ost.code = :occodestate and oc.vendor.id = :vendorid AND os.when >= :since AND oc.currentsoastatetype.id  in (:currentsoastatetypeid) ");
        List list = getByQuery(sb.toString(), properties);

        return (DirectOrderW[]) list.toArray(new DirectOrderW[list.size()]);
	}		
	
	public PendingSOAOrderDTO[] getPendingSOAOrdersByVendor(Long soastatetype, Long vendorid, Date soapendingtime, Date activationdate){		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getPendingSOAOrdersByVendor");
		query.setLong("soastatetype", soastatetype);
		query.setLong("vendorid", vendorid);		
		query.setTimestamp("soapendingtime", soapendingtime);
		query.setTimestamp("activationdate", activationdate);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingSOAOrderDTO.class));
		List list = query.list();
		PendingSOAOrderDTO[] result = (PendingSOAOrderDTO[]) list.toArray(new PendingSOAOrderDTO[list.size()]);
		return result;		
	}	
	
	public VeVPDDataDTO getVeVPDDataByDirectOrderNumber(Long directordernumber){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getVeVPDDataByDirectOrderNumber");
		query.setLong("directordernumber", directordernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(VeVPDDataDTO.class));
		
		return (VeVPDDataDTO) query.uniqueResult();		
	}
	
	public List<VeVVendorNotificationDTO> getDirectOrdersByVendorsAndReleasedDate(Long[] vendorIds, Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getDirectOrdersByVendorsAndReleasedDate");
		query.setParameterList("vendorIds", vendorIds);
		query.setDate("since", since);		
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(VeVVendorNotificationDTO.class));
		List result = (List<VeVVendorNotificationDTO>)query.list();
		return result;
	}
	
	public List<DirectOrderW> getByDOTaxDocumentTicket(Long ticketid) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.directorders.entities.DirectOrder.getByDOTaxDocumentTicket");
		query.setLong("ticketid", ticketid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DirectOrderW.class));
		List result = (List<DirectOrderW>)query.list();
		return result;
	}
}
