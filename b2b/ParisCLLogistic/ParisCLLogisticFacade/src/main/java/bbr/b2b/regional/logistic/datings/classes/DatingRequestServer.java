package bbr.b2b.regional.logistic.datings.classes;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.datings.entities.DatingRequest;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingRequestReportData;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.entities.Delivery;

@Stateless(name = "servers/datings/DatingRequestServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingRequestServer extends LogisticElementServer<DatingRequest, DatingRequestW> implements DatingRequestServerLocal {

	private Map<String, String> mapGetDatingRequestSQL = new HashMap<String, String>();
	{
		mapGetDatingRequestSQL.put("DATINGNUMBER", "DL.NUMBER");		
		mapGetDatingRequestSQL.put("VENDORNAME", "VD.NAME");	
		mapGetDatingRequestSQL.put("DEPARTMENTCODE", "DP.CODE");	
		mapGetDatingRequestSQL.put("DEPARTMENT", "DP.NAME");	
		mapGetDatingRequestSQL.put("DELIVERYTYPE", "DT.NAME");	
		mapGetDatingRequestSQL.put("FLOWTYPE", "FT.NAME");	
		mapGetDatingRequestSQL.put("PROPOSEDDATE", "DR.REQUESTEDDATE");	
		mapGetDatingRequestSQL.put("REQUESTDATE", "DR.WHEN1");		
		mapGetDatingRequestSQL.put("TRUCKS", "DR.TRUCKS");
		mapGetDatingRequestSQL.put("ESTIMATEDBOXES", "DR.ESTIMATEDBOXES");
		mapGetDatingRequestSQL.put("COMMENT", "DR.COMMENT1");
		mapGetDatingRequestSQL.put("ESTIMATEDPALLETS", "DR.ESTIMATEDPALLETS");
		mapGetDatingRequestSQL.put("NEEDUNITS", "SUM(ODL.ESTIMATEDUNITS)");		  
	}	
	
	private Map<String, String> mapGetImportedDatingRequestSQL = new HashMap<String, String>();
	{
		mapGetImportedDatingRequestSQL.put("DATINGNUMBER", "dl.number");		
		mapGetImportedDatingRequestSQL.put("VENDORNAME", "vd.name");	
		mapGetImportedDatingRequestSQL.put("TAXDOCUMENTNUMBER", "dl.refdocument");	
		mapGetImportedDatingRequestSQL.put("CONTAINER", "dl.container");
		mapGetImportedDatingRequestSQL.put("IMPORTEDNUMBER", "dl.imp");	
		mapGetImportedDatingRequestSQL.put("DELIVERYTYPE", "dt.name");
		mapGetImportedDatingRequestSQL.put("FLOWTYPE", "ft.name");
		mapGetImportedDatingRequestSQL.put("PROPOSEDDATE", "logistica.tostr(dr.requesteddate)");	
		mapGetImportedDatingRequestSQL.put("REQUESTDATE", "logistica.tostr(dr.when1)");		
		mapGetImportedDatingRequestSQL.put("ESTIMATEDBOXES", "dr.estimatedboxes");
		mapGetImportedDatingRequestSQL.put("PALLETS", "dr.estimatedpallets");
		mapGetImportedDatingRequestSQL.put("COMMENT", "dr.comment1");
		mapGetImportedDatingRequestSQL.put("NEEDUNITS", "SUM(odl.estimatedunits)");
		mapGetImportedDatingRequestSQL.put("ESTIMATEDTIME", "estimatedtime");
	}	

	@EJB
	DeliveryServerLocal deliveryserver;

	public DatingRequestW addDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW) addElement(datingrequest);
	}
	public DatingRequestW[] getDatingRequests() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW[]) getIdentifiables();
	}
	public DatingRequestW updateDatingRequest(DatingRequestW datingrequest) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DatingRequestW) updateElement(datingrequest);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DatingRequest entity, DatingRequestW wrapper) {
		wrapper.setDeliveryid(entity.getDelivery() != null ? new Long(entity.getDelivery().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(DatingRequest entity, DatingRequestW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDeliveryid() != null && wrapper.getDeliveryid() > 0) { 
			Delivery delivery = deliveryserver.getReferenceById(wrapper.getDeliveryid());
			if (delivery != null) { 
				entity.setDelivery(delivery);
			}
		}
	}
	
	private String getQueryString(int queryType, OrderCriteriaDTO[] orderby, Long[] vendorids, Long ordertypeid, Long flowtypeid) throws OperationFailedException, AccessDeniedException{
		
		String SQL 						= "";
		String SQLCount 				= "";
		String condicionOrderBy			= "";
		String vendorCondition 			= "";
		String ordertypeCondition		= "";
		String flowtypeCondition 		= "";
		String viewOpen 				= "";
		String viewClose 				= "";
		
		if (!ordertypeid.equals(-1L)){
			ordertypeCondition = "OT.ID = " + ordertypeid + " AND ";
		}
		
		if (!flowtypeid.equals(-1L)){
			flowtypeCondition = "FT.ID = " + flowtypeid + " AND ";
		}
		
		String vendorKeys = "";
		
		for (int i = 0 ; i < vendorids.length ; i++ ){
			
			vendorKeys = vendorKeys + vendorids[i];
			
			if (i != vendorids.length - 1){
				vendorKeys = vendorKeys + ", ";
			}
		}
			
		vendorCondition = "VD.ID IN (" + vendorKeys + ") " + "AND ";		
		
		condicionOrderBy = getOrderByString(mapGetDatingRequestSQL, orderby);	
		
		if (queryType == 1){
			SQLCount = 	"SELECT COUNT(*) " +
						"FROM AA ";
			viewOpen = 	"WITH AA AS ( ";
			viewClose = " ) ";
		}
		
		SQL = 	
			viewOpen +
			"SELECT " + 
				"DL.ID AS DELIVERYID, " + 
				"DL.NUMBER AS DATINGNUMBER, " +  
				"VD.NAME AS VENDORNAME, " +
				"DP.CODE AS DEPARTMENTCODE, " +
				"DP.NAME AS DEPARTMENT, " +  
				"DT.CODE AS DELIVERYTYPECODE, " + 
				"DT.NAME AS DELIVERYTYPE, " +
				"FT.NAME AS FLOWTYPE, " +
				"DR.REQUESTEDDATE AS PROPOSEDDATE, " +  
				"DR.WHEN1 AS REQUESTDATE, " +  
				"DR.TRUCKS, " +  
				"DR.ESTIMATEDBOXES, " +
				"DR.COMMENT1 AS COMMENT, " +
				"DR.ESTIMATEDPALLETS, " +  
				"VD.RUT AS VENDORCODE, " +  
				"SUM(ODL.ESTIMATEDUNITS) AS NEEDUNITS " +
			"FROM " +
				"LOGISTICA.DELIVERY AS DL JOIN " +  
				"LOGISTICA.ORDERDELIVERY AS ODL ON ODL.DELIVERY_ID = DL.ID JOIN " +  
				"LOGISTICA.VENDOR AS VD ON VD.ID = DL.VENDOR_ID JOIN " +  
				"LOGISTICA.DATINGREQUEST AS DR ON DR.DELIVERY_ID = DL.ID JOIN " +  
				"LOGISTICA.LOCATION AS LO ON LO.ID = DL.LOCATION_ID JOIN " +  
				"LOGISTICA.DELIVERYSTATETYPE AS DST ON DST.ID = DL.CURRENTSTATETYPE_ID JOIN " +
				"LOGISTICA.ORDER AS OC ON OC.ID = ODL.ORDER_ID JOIN " +
				"LOGISTICA.DEPARTMENT AS DP ON DP.ID = OC.DEPARTMENT_ID JOIN " +
				"LOGISTICA.ORDERTYPE AS OT ON OT.ID = OC.ORDERTYPE_ID JOIN " +
				"LOGISTICA.DELIVERYTYPE AS DT ON DT.ID = DL.DELIVERYTYPE_ID LEFT JOIN " +
				"LOGISTICA.FLOWTYPE AS FT ON FT.ID = DL.FLOWTYPE_ID " +
			"WHERE " +
				vendorCondition +
				ordertypeCondition +
				flowtypeCondition +
				"LO.ID = :locationid AND " +
				"DR.REQUESTEDDATE >= :since AND " +
				"DR.REQUESTEDDATE <= :until AND " +
				"DST.CODE = 'CITA_SOLICITADA' " +
			"GROUP BY " +
				"DL.ID, " + 
				"DL.NUMBER, " +  
				"VD.NAME, " +
				"DP.CODE, " +
				"DP.NAME, " +
				"DT.CODE, " + 
				"DT.NAME, " +
				"FT.NAME, " +
				"DR.REQUESTEDDATE, " +  
				"DR.WHEN1, " +  
				"DR.TRUCKS, " +  
				"DR.ESTIMATEDBOXES, " +
				"DR.COMMENT1, " +
				"DR.ESTIMATEDPALLETS, " +  
				"VD.RUT " +
			condicionOrderBy +
			viewClose +
			SQLCount;		
		
		return SQL;		
	}	
	
	private String getImportedQueryString(int queryType, OrderCriteriaDTO[] orderby, Long[] vendorids, Long ordertypeid, Long flowtypeid, String whereCondition) throws OperationFailedException, AccessDeniedException{
		
		String SQL 						= "";
		String SQLCount 				= "";
		String condicionOrderBy			= "";
		String vendorCondition 			= "";
		String ordertypeCondition		= "";
		String flowtypeCondition 		= "";
		String viewOpen 				= "";
		String viewClose 				= "";
		
		if (!ordertypeid.equals(-1L)){
			ordertypeCondition = "ot.id = " + ordertypeid + " AND ";
		}
		
		if (!flowtypeid.equals(-1L)){
			flowtypeCondition = "ft.id = " + flowtypeid + " AND ";
		}		
		
		String vendorKeys = "";
		
		for (int i = 0 ; i < vendorids.length ; i++) {			
			vendorKeys = vendorKeys + vendorids[i];
			
			if (i != vendorids.length - 1) {
				vendorKeys = vendorKeys + ", ";
			}
		}
			
		vendorCondition = "vd.id IN (" + vendorKeys + ") " + "AND ";		
		
		condicionOrderBy = getOrderByString(mapGetImportedDatingRequestSQL, orderby);	
		
		if (queryType == 1){
			SQLCount = 	"SELECT " + //
							"COUNT(*) " + //
						"FROM " + //
							"AA "; //
			
			viewOpen = 	"WITH aa AS ("; //
			viewClose = ") "; //
		}
		
		SQL = 	
			viewOpen + //
			"SELECT " + //
				"dr.id AS datingrequestid, " + //
				"dl.id AS deliveryid, " + //
				"dl.number AS datingnumber, " + //
				"vd.name AS vendorname, " + //
				"dl.refdocument AS taxdocumentnumber, " + // JPE 20190403: NO SE EST� MOSTRANDO
				"dl.container, " + //
				"dl.imp AS importednumber, " + // JPE 20190403: NO SE EST� MOSTRANDO
				"dt.name AS deliverytype, " + //
				"ft.name AS flowtype, " + //
				"dr.requesteddate AS proposeddate, " + //
				"dr.when1 AS requestdate, " + //
				"dr.estimatedboxes, " + //
				"dr.estimatedpallets AS pallets, " + //
				"dr.comment1 AS comment, " + //
				"vd.rut AS vendorcode, " + //
				"SUM(odl.estimatedunits) AS needunits, " + //
				"(ft.estimatedtime * dr.estimatedboxes) AS estimatedtime " + //
			"FROM " + //
				"logistica.delivery AS dl JOIN " + //
				"logistica.orderdelivery AS odl ON odl.delivery_id = dl.id JOIN " + //
				"logistica.vendor AS vd ON vd.id = dl.vendor_id JOIN " + //
				"logistica.datingrequest AS dr ON dr.delivery_id = dl.id JOIN " + //
				"logistica.LOCATION AS LO ON LO.ID = DL.LOCATION_ID JOIN " + //
				"logistica.deliverystatetype AS dst ON dst.id = dl.currentstatetype_id JOIN " + //
				"logistica.order AS oc ON oc.id = odl.order_id JOIN " + //
				"logistica.ordertype AS ot ON ot.id = oc.ordertype_id JOIN " + //
				"logistica.deliverytype AS dt ON dt.id = dl.deliverytype_id LEFT JOIN " + //
				"logistica.flowtype AS ft ON ft.id = dl.flowtype_id " + //
			"WHERE " + //
				vendorCondition + //
				ordertypeCondition + //
				flowtypeCondition + //
				whereCondition + //
				"lo.id = :locationid AND dst.code = 'CITA_SOLICITADA' " + //
			"GROUP BY " + //
				"dr.id, dl.id, dl.number, vd.name, dl.refdocument, dl.container, dl.imp, dt.name, ft.name, dr.requesteddate, " + //
				"dr.when1, dr.estimatedboxes, dr.estimatedpallets, dr.comment1, vd.rut, ft.estimatedtime " + //
			condicionOrderBy + //
			viewClose + //
			SQLCount; //
		
		return SQL;		
	}	

	public DatingRequestReportDataDTO[] getDatingRequestReport(Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		String SQL = getQueryString(2, orderby, vendorids, ordertypeid, flowtypeid);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("locationid", locationid);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(DatingRequestReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		List list = query.list();
		DatingRequestReportDataDTO[] result = (DatingRequestReportDataDTO[]) list.toArray(new DatingRequestReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getDatingRequestCountReport(Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		String SQL = getQueryString(1, orderby, vendorids, ordertypeid, flowtypeid);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("locationid", locationid);
		query.setDate("since", since);
		query.setDate("until", until);
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;	
	}	
	
	public ImportedDatingRequestReportDataDTO[] getImportedDatingRequestReport(String value, Long number, int filterType, Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition = "";
		
		switch(filterType) {
		case 1:
			whereCondition = "dr.requesteddate >= :since AND dr.requesteddate < :until AND "; // 
			break;
		case 2:
			whereCondition = "dl.container = :container AND "; //
			break;
		case 3:
			whereCondition = "oc.number = :ocnumber AND "; //
			break;
		// JPE 20190403 (SE ELIMINA DEL FILTRO)
//		case 4:
//			whereCondition = "dl.refdocument = :guidenumber AND "; //
//			break;
		}
		
		String SQL = getImportedQueryString(2, orderby, vendorids, ordertypeid, flowtypeid, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("locationid", locationid);
		
		switch(filterType) {
		case 1:
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 2:
			query.setString("container", value);
			break;
		case 3:
			query.setLong("ocnumber", number);
			break;
		// JPE 20190403 (SE ELIMINA DEL FILTRO)
//		case 4:
//			query.setLong("guidenumber", number);
//			break;
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(ImportedDatingRequestReportDataDTO.class));
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		ImportedDatingRequestReportDataDTO[] result = (ImportedDatingRequestReportDataDTO[]) list.toArray(new ImportedDatingRequestReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getImportedDatingRequestCountReport(String value, Long number, int filterType, Long locationid, Long ordertypeid, Long flowtypeid, Long[] vendorids, Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		String whereCondition = "";
		
		switch(filterType) {
		case 1:
			whereCondition = "dr.requesteddate >= :since AND dr.requesteddate <= :until AND "; // 
			break;
		case 2:
			whereCondition = "dl.container = :container AND "; //
			break;
		case 3:
			whereCondition = "oc.number = :ocnumber AND "; //
			break;
		// JPE 20190403 (SE ELIMINA DEL FILTRO)
//		case 4:
//			whereCondition = "dl.refdocument = :guidenumber AND "; //
//			break;
		}
		
		String SQL = getImportedQueryString(1, orderby, vendorids, ordertypeid, flowtypeid, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("locationid", locationid);
		
		switch(filterType) {
		case 1:
			query.setDate("since", since);
			query.setDate("until", until);
			break;
		case 2:
			query.setString("container", value);
			break;
		case 3:
			query.setLong("ocnumber", number);
			break;
		// JPE 20190403 (SE ELIMINA DEL FILTRO)
//		case 4:
//			query.setLong("guidenumber", number);
//			break;
		}
			
		int total = ((BigInteger)query.list().get(0)).intValue();
		return total;	
	}	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DatingRequest";
	}
	@Override
	protected void setEntityname() {
		entityname = "DatingRequest";
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
	
	public Long getNextSequenceDatingRequestNumber() throws OperationFailedException, NotFoundException{
		Query query = getEntityManager().createNativeQuery("select nextval('LOGISTICA.DATINGREQUESTNUMBER_SEQ')");
        BigInteger bigint = (BigInteger) query.getSingleResult();
        Long result = bigint.longValue();
        return result;
	}
	
	public ExcelDatingRequestReportData[] getExcelDatingRequest(Long[] deliveryids) throws AccessDeniedException, OperationFailedException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.DatingRequest.getExcelDatingRequest");
		query.setParameterList("deliveryids", deliveryids);
		query.setResultTransformer(new LowerCaseResultTransformer(ExcelDatingRequestReportData.class));
		List list = query.list();
		ExcelDatingRequestReportData[] result = (ExcelDatingRequestReportData[]) list.toArray(new ExcelDatingRequestReportData[list.size()]);
		return result;
		
		
	}
	
	
}
