package bbr.b2b.regional.logistic.kpi.classes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailCalculateDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDDetailW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCD;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDDetail;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.utils.RuntimeProcessUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
@Stateless(name = "servers/kpi/KPIvevCDDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevCDDetailServer extends LogisticElementServer<KPIvevCDDetail, KPIvevCDDetailW> implements KPIvevCDDetailServerLocal {

	private Map<String, String> mapKPIDetailSQL = new HashMap<String, String>();
	{
		mapKPIDetailSQL.put("ORDERNUMBER", "kpidetail.ordernumber");	
		mapKPIDetailSQL.put("ORDERREQUEST", "kpidetail.orderrequestnumber");
		mapKPIDetailSQL.put("DEPARTMENTCODE", "de.code");		
		mapKPIDetailSQL.put("DEPARTMENTNAME", "de.name");
		mapKPIDetailSQL.put("SALESTORECODE", "salestorecode");		
		mapKPIDetailSQL.put("SALESTORENAME", "salestorename");
		mapKPIDetailSQL.put("CURRENTORDERSTATE", "kpidetail.currentorderstate");
		mapKPIDetailSQL.put("SENDINGDATE", "sendingdate");
		mapKPIDetailSQL.put("CURRENTORDERSTATEDATE", "currentorderstatedate");
		mapKPIDetailSQL.put("FPI", "fpi");
		mapKPIDetailSQL.put("ITEMINTERNALCODE", "kpidetail.iteminternalcode");
		mapKPIDetailSQL.put("DESCRIPTION", "kpitype.description");
		mapKPIDetailSQL.put("ITEMNAME", "kpidetail.itemname");
		mapKPIDetailSQL.put("REQUESTEDUNITS", "kpidetail.detailunits");
		mapKPIDetailSQL.put("TODELIVERYUNITS", "kpidetail.detailtodeliveryunits");
		mapKPIDetailSQL.put("RECEIVEDUNITS", "kpidetail.detailreceivedunits");
		mapKPIDetailSQL.put("PENDINGUNITS", "kpidetail.detailpendingunits");
	}
	
	private Map<String, String> mapKPISummaryDetailSQL = new HashMap<String, String>();
	{
		mapKPISummaryDetailSQL.put("ORDERNUMBER", "kpidetail.ordernumber");	
		mapKPISummaryDetailSQL.put("ORDERREQUEST", "kpidetail.orderrequestnumber");
		mapKPISummaryDetailSQL.put("VENDORCODE", "vd.code");
		mapKPISummaryDetailSQL.put("VENDORSOCIALREASON", "vd.name");
		mapKPISummaryDetailSQL.put("DEPARTMENTCODE", "de.code");		
		mapKPISummaryDetailSQL.put("DEPARTMENTNAME", "de.name");
		mapKPISummaryDetailSQL.put("SALESTORECODE", "salestorecode");		
		mapKPISummaryDetailSQL.put("SALESTORENAME", "salestorename");
		mapKPISummaryDetailSQL.put("CURRENTORDERSTATE", "kpidetail.currentorderstate");
		mapKPISummaryDetailSQL.put("SENDINGDATE", "sendingdate");
		mapKPISummaryDetailSQL.put("CURRENTORDERSTATEDATE", "currentorderstatedate");
		mapKPISummaryDetailSQL.put("FPI", "fpi");
		mapKPISummaryDetailSQL.put("ITEMINTERNALCODE", "kpidetail.iteminternalcode");
		mapKPISummaryDetailSQL.put("DESCRIPTION", "kpitype.description");
		mapKPISummaryDetailSQL.put("ITEMNAME", "kpidetail.itemname");
		mapKPISummaryDetailSQL.put("REQUESTEDUNITS", "kpidetail.detailunits");
		mapKPISummaryDetailSQL.put("TODELIVERYUNITS", "kpidetail.detailtodeliveryunits");
		mapKPISummaryDetailSQL.put("RECEIVEDUNITS", "kpidetail.detailreceivedunits");
		mapKPISummaryDetailSQL.put("PENDINGUNITS", "kpidetail.detailpendingunits");
	}
	
	private Map<String, String> mapKPIvevCDOrderDetailSQL = new HashMap<String, String>();
	{
		mapKPIvevCDOrderDetailSQL.put("SALESTORECODE", "salestorecode");	
		mapKPIvevCDOrderDetailSQL.put("SALESTORENAME", "salestorename");	
		mapKPIvevCDOrderDetailSQL.put("VENDORCODE", "vd.code");		
		mapKPIvevCDOrderDetailSQL.put("VENDORNAME", "vd.name");		
		mapKPIvevCDOrderDetailSQL.put("ORDERNUMBER", "kpid.ordernumber");
		mapKPIvevCDOrderDetailSQL.put("ORDERREQUESTNUMBER", "kpid.orderrequestnumber");	
		mapKPIvevCDOrderDetailSQL.put("DEPARTMENTCODE", "dp.code");
		mapKPIvevCDOrderDetailSQL.put("DEPARTMENTNAME", "dp.name");
		mapKPIvevCDOrderDetailSQL.put("CURRENTORDERSTATETYPENAME", "kpid.currentorderstate");
		mapKPIvevCDOrderDetailSQL.put("SENDINGDATE", "kpid.sendingdate");	
		mapKPIvevCDOrderDetailSQL.put("FPI", "kpid.fpi");	
		mapKPIvevCDOrderDetailSQL.put("CURRENTSTATETYPEDATE", "kpid.currentorderstatedate");		
		mapKPIvevCDOrderDetailSQL.put("DELAYEDDAYS", "delayeddays");		
		mapKPIvevCDOrderDetailSQL.put("KPIVEVTYPENAME", "kpit.description");
		mapKPIvevCDOrderDetailSQL.put("ITEMINTERNALCODE", "kpid.iteminternalcode");	
		mapKPIvevCDOrderDetailSQL.put("ITEMNAME", "kpid.itemname");
		mapKPIvevCDOrderDetailSQL.put("ITEMFINALCOST", "kpid.itemfinalcost");
		mapKPIvevCDOrderDetailSQL.put("REQUESTEDUNITS", "kpid.detailunits");
		mapKPIvevCDOrderDetailSQL.put("TODELIVERYUNITS", "kpid.detailtodeliveryunits");
		mapKPIvevCDOrderDetailSQL.put("RECEIVEDUNITS", "kpid.detailreceivedunits");
		mapKPIvevCDOrderDetailSQL.put("PENDINGUNITS", "kpid.detailpendingunits");	
		mapKPIvevCDOrderDetailSQL.put("FIRSTDAYFINEPERCENT", "firstdayfinepercent");
		mapKPIvevCDOrderDetailSQL.put("NEXTDAYSFINEPERCENT", "nextdaysfinepercent");
		mapKPIvevCDOrderDetailSQL.put("FIRSTDAYFINEAMOUNT", "firstdayfineamount");
		mapKPIvevCDOrderDetailSQL.put("NEXTDAYSFINEAMOUNT", "nextdaysfineamount");		
	}
	
	private final int WINDOWS_BUFFER_SIZE = 1000;
		
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	KPIvevCDTypeServerLocal kpivevcdtypeserver;
	
	@EJB
	KPIvevCDServerLocal kpivevcdserver;
	
	public KPIvevCDDetailW addKPIvevCDDetail(KPIvevCDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDDetailW) addElement(kpivevcddetail);
	}
	public KPIvevCDDetailW[] getKPIvevCDDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDDetailW[]) getIdentifiables();
	}
	public KPIvevCDDetailW updateKPIvevCDDetail(KPIvevCDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevCDDetailW) updateElement(kpivevcddetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevCDDetail entity, KPIvevCDDetailW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));
		wrapper.setKpivevcdtypeid(entity.getKpivevcdtype() != null ? new Long(entity.getKpivevcdtype().getId()) : new Long(0));
		wrapper.setKpivevcdid(entity.getKpivevcd() != null ? new Long(entity.getKpivevcd().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevCDDetail entity, KPIvevCDDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
		if (wrapper.getSalestoreid() != null && wrapper.getSalestoreid() > 0) { 
			Location salestore = locationserver.getReferenceById(wrapper.getSalestoreid());
			if (salestore != null) { 
				entity.setSalestore(salestore);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getKpivevcdtypeid() != null && wrapper.getKpivevcdtypeid() > 0) { 
			KPIvevCDType kpivevcdtype = kpivevcdtypeserver.getReferenceById(wrapper.getKpivevcdtypeid());
			if (kpivevcdtype != null) { 
				entity.setKpivevcdtype(kpivevcdtype);
			}
		}
		if (wrapper.getKpivevcdid() != null && wrapper.getKpivevcdid() > 0) { 
			KPIvevCD kpivevcd = kpivevcdserver.getReferenceById(wrapper.getKpivevcdid());
			if (kpivevcd != null) { 
				entity.setKpivevcd(kpivevcd);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "KPIvevCDDetail";
	}
	
	@Override
	protected void setEntityname() {
		entityname = "KPIvevCDDetail";
	}
	
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until) throws OperationFailedException{
		
		String SQL =
			"DELETE FROM logistica.kpivevcddetail " + //
			"WHERE " + //
				"fpi >= :since AND fpi <= :until "; //
		
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			SQL += "AND vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		
		query.executeUpdate();		
	}
	
	public KPIvevCDDetailW[] getDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until) throws OperationFailedException{
		
		String SQL =
			"SELECT " +
				"o.vendor_id AS vendorid, " + //
				"o.department_id AS departmentid, " + //
				"o.salestore_id AS salestoreid, " + //
				"o.number AS ordernumber, " + //
				"o.requestnumber AS orderrequestnumber, " +	//
				"o.currentstatetype_id AS currentorderstatetypeid, " + //
				"ost.name AS currentorderstate,	" + //
				"o.currentstatetypedate AS currentorderstatedateldt, " + // 
				"o.valid AS fpildt, " + //
				"o.emitted AS sendingdateldt, " + //
				"CASE WHEN ost.code = 'ELIMINADA' OR ost.code = 'RECEPCIONADA' " + //
					 "THEN DATE(o.currentstatetypedate) - DATE(o.valid) " + //
					 "ELSE NULL " + //
					 "END AS deliverydelayeddays, " + //
				"CURRENT_DATE - DATE(o.valid) AS delayeddays, " + //	
				"i.internalcode AS iteminternalcode, " + //
				"vi.vendoritemcode AS vendoritemcode, " + //
				"i.name AS itemname, " + //
				"od.finalcost AS itemfinalcost, " + //
				"pdd.units AS detailunits, " + //
				"pdd.todeliveryunits AS detailtodeliveryunits, " + //
				"pdd.receivedunits AS detailreceivedunits, " + //
				"pdd.pendingunits AS detailpendingunits, " + //
				"ld.code AS finallocationcode, " + //
				"ld.name AS finallocationname, " + //
				"0 AS finefactordays " + //
				// "0.0 AS finefactor " quedar� en NULL para nuevos cálculos
			"FROM " + //
				"logistica.order AS o JOIN " +//
				"logistica.orderstatetype AS ost ON ost.id = o.currentstatetype_id JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = o.id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = od.order_id AND pdd.item_id = od.item_id JOIN " + //
				"logistica.location AS ld ON ld.id = pdd.location_id JOIN " + //
				"logistica.item AS i ON i.id = pdd.item_id LEFT JOIN " + //
				"logistica.vendoritem AS vi ON vi.item_id = i.id AND vi.vendor_id = o.vendor_id " + //
			"WHERE " + //
				"o.number > 0 AND o.vevcd IS TRUE AND o.valid >= :since AND o.valid <= :until "; //
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL += "AND o.vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDDetailCalculateDTO.class));
		List<KPIvevCDDetailCalculateDTO> dataList = query.list();
		
		
		dataList.stream()
		            .forEach(s -> {
		                ((KPIvevCDDetailCalculateDTO) s).setCurrentorderstatedate(Date.from(((KPIvevCDDetailCalculateDTO) s).getCurrentorderstatedateldt().atZone(ZoneId.systemDefault()).toInstant()));
		                ((KPIvevCDDetailCalculateDTO) s).setFpi(Date.from(((KPIvevCDDetailCalculateDTO) s).getFpildt().atZone(ZoneId.systemDefault()).toInstant()));
		                ((KPIvevCDDetailCalculateDTO) s).setSendingdate(Date.from(((KPIvevCDDetailCalculateDTO) s).getSendingdateldt().atZone(ZoneId.systemDefault()).toInstant()));
		}); 
		KPIvevCDDetailW[] resultW = new KPIvevCDDetailW[dataList.size()];
		BeanExtenderFactory.copyProperties(dataList.toArray(new KPIvevCDDetailCalculateDTO[dataList.size()]), resultW, KPIvevCDDetailW.class);

		return resultW;		
	}
	
	public int getCountDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until) throws OperationFailedException{
		
		String SQL =
			"SELECT " + //
				"COUNT(1) " + //
			"FROM " + //
				"logistica.order " + //
			"WHERE " + //
				"number > 0 AND vevcd IS TRUE AND valid >= :since AND valid <= :until "; //
		
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			SQL += "AND vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		return ((BigInteger)query.uniqueResult()).intValue();		
	}
	
	public KPIvevCDDetailReportDTO[] getKPIvevCDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid ";
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) ";	
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.kpivevcdtype_id IN (:kpitypeids) ";	
		}
		
		String SQL = getQueryString(1, orderby, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 &&departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("kpitypeids", kpitypeids);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDDetailReportDTO.class));
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		return (KPIvevCDDetailReportDTO[]) list.toArray(new KPIvevCDDetailReportDTO[list.size()]);
	}
	
	public int countKPIvevCDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid ";
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) ";	
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.kpivevcdtype_id IN (:kpitypeids) ";	
		}
		
		String SQL = getQueryString(2, null, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 &&departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("kpitypeids", kpitypeids);
		}
		
		int result = ((BigInteger)query.uniqueResult()).intValue();
		
		return result;
	}
	
	private String getQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException {		
		String SQL = "";
		String orderBy = "";
		
		if (queryType == 1) {
			SQL +=
				"SELECT " + //
					"kpidetail.id AS id, " + //
					"kpidetail.ordernumber AS ordernumber, " + //
					"kpidetail.orderrequestnumber AS orderrequest, " + //
					"de.code AS departmentcode, " + //
					"de.name AS departmentname, " + //
					"COALESCE(ss.code, '') AS salestorecode, " + //
					"COALESCE(ss.name, '') AS salestorename, " + //
					"kpidetail.currentorderstate AS currentorderstate, " + //
					"logistica.tostr(kpidetail.sendingdate) AS sendingdate, " + //
					"logistica.tostr(kpidetail.currentorderstatedate) AS currentorderstatedate, " + //
					"logistica.tostr(kpidetail.fpi) AS fpi, " + //
					"kpidetail.iteminternalcode AS iteminternalcode, " + //
					"kpitype.description AS description, " + //
					"kpidetail.itemname AS itemname, " + //
					"kpidetail.detailunits AS requestedunits, " + //
					"kpidetail.detailtodeliveryunits AS todeliveryunits, " + //
					"kpidetail.detailreceivedunits AS receivedunits, " + //
					"kpidetail.detailpendingunits AS pendingunits "; //
			
			orderBy = getOrderByString(mapKPIDetailSQL, orderCriteria);			
		}
		else {
			SQL +=
				"SELECT " + //
					"COUNT(DISTINCT kpidetail.id) "; //		
		}
			
		SQL +=
			"FROM " + //
				"logistica.kpivevcddetail AS kpidetail JOIN " + //
				"logistica.kpivevcdtype AS kpitype ON kpitype.id = kpidetail.kpivevcdtype_id JOIN " + //
				"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpidetail.salestore_id " + //
			"WHERE " + //
				"kpidetail.fpi >= :since AND kpidetail.fpi < :until " + //
				whereCondition + //
			orderBy; //
		
		return SQL;
	}
	
	public KPIvevCDSummaryDetailReportDTO[] getKPIvevCDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid ";
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(1, orderby, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 &&departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevCDSummaryDetailReportDTO.class));
		
		if (ispaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		return (KPIvevCDSummaryDetailReportDTO[]) list.toArray(new KPIvevCDSummaryDetailReportDTO[list.size()]);
	}
	
	public int countKPIvevCDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid ";
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) ";	
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) ";	
		}
		
		String SQL = getSummaryQueryString(2, null, whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 &&departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		
		int result = ((BigInteger)query.uniqueResult()).intValue();
		
		return result;
	}
	
	private String getSummaryQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition) throws AccessDeniedException {
		String SQL = "";
		String orderBy = "";
		
		if (queryType == 1) {
			SQL +=
				"SELECT " + //
					"kpidetail.id AS id, " + //
					"kpidetail.ordernumber AS ordernumber, " + //
					"kpidetail.orderrequestnumber AS orderrequest, " + //
					"vd.code AS vendorcode, " + //
					"vd.name AS vendorsocialreason, " + //
					"de.code AS departmentcode, " + //
					"de.name AS departmentname, " + //
					"COALESCE(ss.code, '') AS salestorecode, " + //
					"COALESCE(ss.name, '') AS salestorename, " + //
					"kpidetail.currentorderstate AS currentorderstate, " + //
					"logistica.tostr(kpidetail.sendingdate) AS sendingdate, " + //
					"logistica.tostr(kpidetail.currentorderstatedate) AS currentorderstatedate, " + //
					"logistica.tostr(kpidetail.fpi) AS fpi, " + //
					"kpidetail.iteminternalcode AS iteminternalcode, " + //
					"kpitype.description AS description, " + //
					"kpidetail.itemname AS itemname, " + //
					"kpidetail.detailunits AS requestedunits, " + //
					"kpidetail.detailtodeliveryunits AS todeliveryunits, " + //
					"kpidetail.detailreceivedunits AS receivedunits, " + //
					"kpidetail.detailpendingunits AS pendingunits "; //
			
			orderBy = getOrderByString(mapKPISummaryDetailSQL, orderCriteria);			
		}
		else {
			SQL +=
				"SELECT " + //
					"COUNT(DISTINCT kpidetail.id) "; //
		}
			
		SQL +=
			"FROM " + //
				"logistica.kpivevcddetail AS kpidetail JOIN " + //
				"logistica.kpivevcdtype AS kpitype ON kpitype.id = kpidetail.kpivevcdtype_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpidetail.vendor_id JOIN " + //
				"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpidetail.salestore_id " + //
			"WHERE " + //
				"kpidetail.fpi >= :since AND kpidetail.fpi < :until " + //
				whereCondition + //
			orderBy; //
		
		return SQL;
	}
	
	public FileDownloadInfoResultDTO getKPIvevCDDetailDataSourceReport(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, String vendorname, Long userKey) throws OperationFailedException{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat filedateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = " + vendorid + " ";	
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.department_id IN (";
			for (int i = 0; i < departmentids.length; i++) {
				and += departmentids[i];
				if (i < departmentids.length - 1) {
					and += ",";
				}
				else {
					and += ") ";
				}
			}
			whereCondition += and;
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.salestore_id IN (";
			for (int i = 0; i < salestoreids.length; i++) {
				and += salestoreids[i];
				if (i < salestoreids.length - 1) {
					and += ",";
				}
				else {
					and += ") ";
				}
			}
			whereCondition += and;
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.kpivevcdtype_id IN (";
			for (int i = 0; i < kpitypeids.length; i++) {
				and += kpitypeids[i];
				if (i < kpitypeids.length - 1) {
					and += ",";
				}
				else {
					and += ") ";
				}
			}
			whereCondition += and;
		}
		
		String SQL =
				"SELECT " + //
					"COALESCE(ss.code, ''), " + //
					"COALESCE(ss.name, ''), " + //
					"vd.code, " + //
					"vd.name, " + //
					"kpidetail.ordernumber, " + //
					"kpidetail.orderrequestnumber, " + //
					"de.code, " + //
					"de.name, " + //
					"kpidetail.currentorderstate, " + //
					"to_char(kpidetail.sendingdate, 'DD-MM-YYYY'), " + //
					"to_char(kpidetail.fpi, 'DD-MM-YYYY'), " + //
					"date_part('month',kpidetail.fpi) AS MONTH_FPI, " + //
					"date_part('year',kpidetail.fpi) AS YEAR_FPI, " + //
					"to_char(kpidetail.currentorderstatedate, 'DD-MM-YYYY'), " + //
					"kpidetail.iteminternalcode, " + //
					"kpitype.description, " + //
					"kpidetail.itemname, " + //
					"kpidetail.detailunits AS requestedunits, " + //
					"kpidetail.detailtodeliveryunits AS todeliveryunits, " + //
					"kpidetail.detailreceivedunits AS receivedunits, " + //
					"kpidetail.detailpendingunits AS pendingunits " + //
				"FROM " + //
					"logistica.kpivevcddetail AS kpidetail JOIN " + //
					"logistica.kpivevcdtype AS kpitype ON kpitype.id = kpidetail.kpivevcdtype_id JOIN " + //
					"logistica.vendor AS vd ON vd.id = kpidetail.vendor_id JOIN " + //
					"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
					"logistica.location AS ss ON ss.id = kpidetail.salestore_id " + //
				"WHERE " + //
					"kpidetail.fpi >= '" + dateFormat.format(since) + "' AND " + //
					"kpidetail.fpi < '" + dateFormat.format(until) + "' " + //
					whereCondition + //
				"ORDER BY " + //
					"kpidetail.ordernumber"; //
		
		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL);

			String realfilename = "KPIVEVCDDTOFTE" + date.getTime() + "_" + userKey + ".csv";
		
			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();

			String downloadfilename = "Dato Fuente KPI VEV CD " + vendorname + "(FPI Desde " + filedateFormat.format(since) + " Hasta " + filedateFormat.format(until) + ").zip";

			resultDTO.setRealfilename(realfilename.replace(".csv", ".zip"));
			resultDTO.setDownloadfilename(downloadfilename);

			// Obtener el comando para generar reporte, usando la metada de conexión de la sesión
			comando1 = session.doReturningWork(new ReturningWork<String>() {
				public String execute(Connection connection) throws SQLException {


					String urlDB = connection.getMetaData().getURL();
					String[] aux1 = urlDB.split(":");
					String dbserver = aux1[2].substring(2); // quita '//'
					String dbname = aux1[3].split("/")[1]; // quita el puerto
					dbname = dbname.split("\\?")[0];

					log.info(dbserver);

					// dbname, dbserver, queryfile, realname
					//String comando = "sh /b2b/shell/fileToDownload/getFileFromDB.sh " + dbname + " " + dbserver + " " + filetemp + " " + filename;
					String comando = "sh /b2b/shell/fileToDownload/getFileFromDB_csv.sh " + dbname + " " + dbserver + " " + filetemp + " " + filename;
					log.info(comando);

					return comando;
				}
			});

			/** ******** llamo a shell que mueve archivo desde AS comercial a PORTAL *** */
			// Obtener el comando para mover el archivo de reporte generado
			// lleva como parámetro el nombre del archivo y los titulos del archivo

			comando2 = "sh /b2b/shell/fileToDownload/moveFile1.sh " + realfilename + " " + RegionalLogisticConstants.TITLESKPIVEVCD;
		
			log.info(comando2);

		} catch (HibernateException e) {
			throw new OperationFailedException("Error al obtener la query", e);
		} catch (IOException e) {
			throw new OperationFailedException("Error al escribir el archivo", e);
		}

		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando1);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}		
		
		try {
			// ESTA RUTINA EJECUTA UN PROCESO FUERA DEL CONTEXTO DEL SERVIDOR DE APLICACIONES
			Process p = Runtime.getRuntime().exec(comando2);

			// SE MANEJAN LOS STREAM DE SALIDA DEL PROCESO, PARA EVITAR CAIDAS POR
			// LIMITACION DE TAMA�O DE BUFFER
			handleStream(p.getInputStream(), "OUTPUT");
			handleStream(p.getErrorStream(), "ERROR");

			// PARS DEVOLVER EL CONTROL EL PROCESO DEBE FINALIZAR
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new OperationFailedException("");
		}	

		return resultDTO;
	}
	
	public KPIvevVendorDimensionDTO[] getVendorsByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition =
				(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.vendor_id = :vendorid " : //
						"") + //
				(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.department_id IN (:departmentids) " : //
						""); //
		
		String SQL = getVendorQueryString(whereCondition);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		query.setDouble("firstdayfine", firstdayfine);
		query.setDouble("nextdaysfine", nextdaysfine);
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevVendorDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevVendorDimensionDTO[]) list.toArray(new KPIvevVendorDimensionDTO[list.size()]);		
	}
	
	public KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until) throws OperationFailedException {
		
		String SQL =
			"SELECT DISTINCT " + //
				"dp.id, " + //
				"dp.code, " + //
				"dp.name " + //
			"FROM " + //
				"logistica.kpivevcddetail AS kpid JOIN " + //
				"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVCD' JOIN " + //
				"logistica.department AS dp ON dp.id = kpid.department_id " + //
			"WHERE " + //
				"kpid.fpi >= :since AND kpid.fpi < :until AND kpifp.since = :since " + //
				(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.vendor_id = :vendorid " : //
						"") + //
				(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.department_id IN (:departmentids) " : //
						"") + //
			"ORDER BY " + //
				"dp.code "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevDepartmentDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevDepartmentDimensionDTO[]) list.toArray(new KPIvevDepartmentDimensionDTO[list.size()]);		
	}
	
	public KPIvevFineDataDTO[] getFineDataByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine) throws OperationFailedException {
		
		String SQL =
			"SELECT " + //
				"kpid.vendor_id AS vendorid, " + //
				"kpid.department_id AS departmentid, " + //
				"kpif.compliance, " + //
				"SUM(CASE WHEN kpid.finefactordays = 0 " + //
				 	 	 "THEN 0.0 " + //
				 	 	 "ELSE :firstdayfine + (:nextdaysfine * (kpid.finefactordays - 1)) " + //
				 	 	 "END) AS finalfine, " + //
				"SUM(CASE WHEN kpid.finefactordays = 0 " + //
					 	 "THEN 0.0 " + //
					 	 "ELSE (:firstdayfine + (:nextdaysfine * (kpid.finefactordays - 1))) * kpid.itemfinalcost * kpid.detailunits " + //
					 	 "END) AS currencyfine " + //
			"FROM " + //
				"logistica.kpivevcddetail AS kpid JOIN " + //
				"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVCD' " + //
			"WHERE " + //
				"kpid.fpi >= :since AND kpid.fpi < :until AND kpifp.since = :since " + //
				(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.vendor_id = :vendorid " : //
						"") + //
				(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
						"AND kpid.department_id IN (:departmentids) " : //
						"") + //
			"GROUP BY " + //
				"kpid.vendor_id, kpid.department_id, kpif.compliance"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		query.setDouble("firstdayfine", firstdayfine);
		query.setDouble("nextdaysfine", nextdaysfine);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevFineDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevFineDataDTO[]) list.toArray(new KPIvevFineDataDTO[list.size()]);		
	}
	
	public KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, OrderCriteriaDTO[] orderCriteria) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition =
			(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND vendor_id = :vendorid " : //
					"") + //
			(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND department_id IN (:departmentids) " : //
					""); //
		
		String SQL = getOrderDetailQueryString(whereCondition, orderCriteria);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);
		}
		query.setDouble("firstdayfine", firstdayfine);
		query.setDouble("nextdaysfine", nextdaysfine);
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevOrderDetailDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevOrderDetailDataDTO[]) list.toArray(new KPIvevOrderDetailDataDTO[list.size()]);		
	}
	
	public FileDownloadInfoResultDTO getExcelKPIvevCDFineDataReport(Long vendorid, Long[] departmentids, Integer month, Integer year, Double firstdayfine, Double nextdaysfine, Long userKey) throws OperationFailedException, AccessDeniedException {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		// Determinar el rango de fecha de búsqueda de información (un mes completo)
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, month.intValue());
		cal.set(Calendar.YEAR, year.intValue());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date since = cal.getTime();

		cal.add(Calendar.MONTH, 1);
		Date until = cal.getTime();
		
		// Proveedores
		String whereCondition =
			(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND kpid.vendor_id = :vendorid " : //
					"") + //
			(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND kpid.department_id IN (:departmentids) " : //
					""); //	
		
		String SQL = getVendorQueryString(whereCondition);

		SQLQuery vendorQuery = (SQLQuery) getSession().createSQLQuery(SQL);
		vendorQuery.setDate("since", since);
		vendorQuery.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorQuery.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorQuery.setParameterList("departmentids", departmentids);
		}
		vendorQuery.setDouble("firstdayfine", firstdayfine);
		vendorQuery.setDouble("nextdaysfine", nextdaysfine);
		
		// Departamentos
		KPIvevDepartmentDimensionDTO[] departments = getDepartmentsByKPIvevCDFinePeriod(vendorid, departmentids, since, until); 
		if (departments == null || departments.length <= 0) {
			throw new OperationFailedException("Error obteniendo departamentos para la descarga");
		}
		
		// Multas
		KPIvevFineDataDTO[] finedata = getFineDataByKPIvevCDFinePeriod(vendorid, departmentids, since, until, firstdayfine, nextdaysfine); 
		if (finedata == null || finedata.length <= 0) {
			throw new OperationFailedException("Error obteniendo datos de multas para la descarga");
		}
		
		Map<String, KPIvevFineDataDTO> fineMap = new HashMap<String, KPIvevFineDataDTO>();
		for (KPIvevFineDataDTO fd : finedata) {
			fineMap.put(fd.getVendorid() + "_" + fd.getDepartmentid(), fd);
		}
		
		// Detalles de órdenes
		OrderCriteriaDTO[] orderCriteria = new OrderCriteriaDTO[2];
		orderCriteria[0] = new OrderCriteriaDTO();
		orderCriteria[0].setPropertyname("FPI");
		orderCriteria[0].setAscending(true);
		orderCriteria[1] = new OrderCriteriaDTO();
		orderCriteria[1].setPropertyname("ORDERNUMBER");
		orderCriteria[1].setAscending(true);
		
		SQL = getOrderDetailQueryString(whereCondition, orderCriteria);
		SQLQuery detailQuery = (SQLQuery) getSession().createSQLQuery(SQL);
		detailQuery.setDate("since", since);
		detailQuery.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			detailQuery.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			detailQuery.setParameterList("departmentids", departmentids);
		}
		detailQuery.setDouble("firstdayfine", firstdayfine);
		detailQuery.setDouble("nextdaysfine", nextdaysfine);
		
		// Construcción del excel 
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");		
		String monthStr = ClassUtilities.theMonth(month);
		String downloadfilename = "Multas " + monthStr + " " + year + " PE-CD.xlsx";
		String realfilename = "Multas_" + monthStr + year + "_PE-CD_" + dateFormat.format(new Date()) + "hrs_" + userKey + ".xlsx";
				
		try (ScrollableResults cursorVendor = vendorQuery.setFetchSize(WINDOWS_BUFFER_SIZE).scroll(ScrollMode.FORWARD_ONLY);
			 ScrollableResults cursorDetail = detailQuery.setFetchSize(WINDOWS_BUFFER_SIZE).scroll(ScrollMode.FORWARD_ONLY);
			 FileOutputStream fileOut = new FileOutputStream("/opt/fileserver/" + realfilename);) {
		
			SXSSFWorkbook workbook = new SXSSFWorkbook(WINDOWS_BUFFER_SIZE);

			// ESTILOS DE CELDAS
			CellStyle headercs = workbook.createCellStyle();
			Font textfont = workbook.createFont();
			textfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headercs.setFont(textfont);
			headercs.setAlignment(CellStyle.ALIGN_CENTER);
			headercs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headercs.setWrapText(true);
					
			CellStyle departmentcs = workbook.createCellStyle();
			departmentcs.setFont(textfont);
			departmentcs.setAlignment(CellStyle.ALIGN_CENTER);
			departmentcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			departmentcs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
			departmentcs.setFillPattern(CellStyle.SOLID_FOREGROUND);
			departmentcs.setWrapText(true);
			departmentcs.setBorderRight(CellStyle.BORDER_THIN);
			departmentcs.setRightBorderColor(IndexedColors.BLACK.getIndex());
					
			CellStyle vendorcs = workbook.createCellStyle();
			vendorcs.setAlignment(CellStyle.ALIGN_LEFT);
			vendorcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			vendorcs.setBorderBottom(CellStyle.BORDER_THIN);
			vendorcs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			
			CellStyle numericcs = workbook.createCellStyle();
			DataFormat numericformat = workbook.createDataFormat();
			numericcs.setDataFormat(numericformat.getFormat("0.00"));
			numericcs.setAlignment(CellStyle.ALIGN_RIGHT);
			numericcs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle compliancecs = workbook.createCellStyle();
			compliancecs.setDataFormat(numericformat.getFormat("0.0"));
			compliancecs.setAlignment(CellStyle.ALIGN_RIGHT);
			compliancecs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			CellStyle currencycs = workbook.createCellStyle();
			DataFormat currencyformat = workbook.createDataFormat();
			currencycs.setDataFormat(currencyformat.getFormat("$#,##0"));
			currencycs.setAlignment(CellStyle.ALIGN_RIGHT);
			currencycs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			// HOJA DE MULTAS
			SXSSFSheet sheet = workbook.createSheet("MULTAS_VEV");
			
			// FILA 0
			SXSSFRow row = sheet.createRow(0);
			
			SXSSFCell cell = row.createCell(0);
			cell.setCellStyle(headercs);
			cell.setCellValue("PROVEEDORES");

			cell = row.createCell(1);
			headercs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
			headercs.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(headercs);
			cell.setCellValue("% MULTA - FLUJO PE-CD");
			CellRangeAddress range = new CellRangeAddress(0, 0, 1, departments.length);
			if (departments.length != 1) {
				sheet.addMergedRegion(range);
			}
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			cell = row.createCell(departments.length + 1);
			cell.setCellStyle(headercs);
			cell.setCellValue("% CUMPLIMIENTOS");
			range = new CellRangeAddress(0, 0, departments.length + 1, 2 * departments.length);
			if ((departments.length + 1) != (2 * departments.length)) {
				sheet.addMergedRegion(range);
			}
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			cell = row.createCell(2 * departments.length + 1);
			cell.setCellStyle(headercs);
			cell.setCellValue("MULTA $");
			range = new CellRangeAddress(0, 0, 2 * departments.length + 1, 3 * departments.length);
			if ((2 * departments.length + 1) != (3 * departments.length)) {
				sheet.addMergedRegion(range);
			}
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			cell = row.createCell(3 * departments.length + 1);
			headercs.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			headercs.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(headercs);
			cell.setCellValue("TOTAL $");
			
			sheet.trackColumnForAutoSizing(3 * departments.length + 1);
			sheet.autoSizeColumn(3 * departments.length + 1);
						
			// FILA 1
			row = sheet.createRow(1);
			
			cell = row.createCell(0);
			range = new CellRangeAddress(0, 1, 0, 0);
			sheet.addMergedRegion(range);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
					
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < departments.length; j++) {
					cell = row.createCell(i * departments.length + j + 1);
					cell.setCellStyle(departmentcs);
					cell.setCellValue(departments[j].getCode() + "\n" + departments[j].getName());
				}
				
				range = new CellRangeAddress(1, 1, i * departments.length + 1, departments.length * (i + 1));
				setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			}
			
			range = new CellRangeAddress(0, 1, 3 * departments.length + 1, 3 * departments.length + 1);
			sheet.addMergedRegion(range);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
			
			// FILAS 2..N
			SXSSFCell cell1 = null;
			SXSSFCell cell2 = null;
			SXSSFCell cell3 = null;
			String key = "";
			int i = 0;
			while (cursorVendor.next()) { // iterar filas
				Object[] a = cursorVendor.get();
				
				row = sheet.createRow(2 + i);
				
				cell = row.createCell(0);
				cell.setCellStyle(vendorcs);
				cell.setCellType(SXSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String) a[2]);
											
				for (int j = 0; j < departments.length; j++) {
					key = ((BigInteger) a[0]) + "_" + departments[j].getId();
					
					cell1 = row.createCell(j + 1);
					cell2 = row.createCell(departments.length + j + 1);
					cell3 = row.createCell(2 * departments.length + j + 1);
					if (fineMap.containsKey(key)) {
						cell1.setCellStyle(numericcs);
						cell1.setCellValue(fineMap.get(key).getFinalfine());
						
						cell2.setCellStyle(compliancecs);
						cell2.setCellValue(Math.round(fineMap.get(key).getCompliance() * 10) / 10.0);
						
						cell3.setCellStyle(currencycs);
						cell3.setCellValue(fineMap.get(key).getCurrencyfine());
					}
				}
				
				cell = row.createCell(3 * departments.length + 1);
				cell.setCellStyle(currencycs);
				cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Double) a[4]);
				
				i++;
			}
			
			if (i == 0) {
				throw new OperationFailedException("Error obteniendo proveedores para la descarga");
			}
			
			range = new CellRangeAddress(2, i + 1, 0, 0);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			range = new CellRangeAddress(2, i + 1, 1, departments.length);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			range = new CellRangeAddress(2, i + 1, departments.length + 1, 2 * departments.length);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			range = new CellRangeAddress(2, i + 1, 2 * departments.length + 1, 3 * departments.length);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			range = new CellRangeAddress(2, i + 1, 3 * departments.length + 1, 3 * departments.length + 1);
			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			// TAMAÑO DE COLUMNAS
			for (int k = 0; k < 3 * departments.length + 1; k++) {
				sheet.trackColumnForAutoSizing(k);
				sheet.autoSizeColumn(k);
			}
			
			// HOJA DE DETALLE DE ÓRDENES
			sheet = workbook.createSheet("DETALLE_OC");
			
			// FILA 0
			row = sheet.createRow(0);
			
			headercs.setBorderBottom(CellStyle.BORDER_MEDIUM);
			headercs.setBorderLeft(CellStyle.BORDER_MEDIUM);
			headercs.setBorderRight(CellStyle.BORDER_MEDIUM);
			headercs.setBorderTop(CellStyle.BORDER_MEDIUM);
			headercs.setFillForegroundColor(IndexedColors.GOLD.getIndex());
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			cell = row.createCell(0);	cell.setCellValue("Código Tienda"); 				cell.setCellStyle(headercs);	
			cell = row.createCell(1);	cell.setCellValue("Nombre Tienda");					cell.setCellStyle(headercs);
			cell = row.createCell(2);	cell.setCellValue("Código Proveedor"); 				cell.setCellStyle(headercs);
			cell = row.createCell(3);	cell.setCellValue("Razón Social"); 					cell.setCellStyle(headercs);
			cell = row.createCell(4);	cell.setCellValue("Número Orden"); 					cell.setCellStyle(headercs);
			cell = row.createCell(5);	cell.setCellValue("Número Solicitud"); 				cell.setCellStyle(headercs);
			cell = row.createCell(6);	cell.setCellValue("Código Depto."); 				cell.setCellStyle(headercs);
			cell = row.createCell(7);	cell.setCellValue("Departamento"); 					cell.setCellStyle(headercs);
			cell = row.createCell(8);	cell.setCellValue("Estado de Orden"); 				cell.setCellStyle(headercs);
			cell = row.createCell(9);	cell.setCellValue("Fecha Envío"); 					cell.setCellStyle(headercs);
			cell = row.createCell(10);	cell.setCellValue("FPI"); 							cell.setCellStyle(headercs);
			cell = row.createCell(11);	cell.setCellValue("Fecha RM Estado"); 				cell.setCellStyle(headercs);
			cell = row.createCell(12);	cell.setCellValue("Días Atraso"); 					cell.setCellStyle(headercs);
			cell = row.createCell(13);	cell.setCellValue("SKU Paris"); 					cell.setCellStyle(headercs);
			cell = row.createCell(14);	cell.setCellValue("Evaluación"); 					cell.setCellStyle(headercs);
			cell = row.createCell(15);	cell.setCellValue("Descripción SKU"); 				cell.setCellStyle(headercs);
			cell = row.createCell(16);	cell.setCellValue("Costo del producto"); 			cell.setCellStyle(headercs);
			cell = row.createCell(17);	cell.setCellValue("Unidades Solicitadas");			cell.setCellStyle(headercs);
			cell = row.createCell(18);	cell.setCellValue("Unidades en Despacho");			cell.setCellStyle(headercs);
			cell = row.createCell(19);	cell.setCellValue("Unidades Entregadas"); 			cell.setCellStyle(headercs);
			cell = row.createCell(20);	cell.setCellValue("Unidades Pendientes");			cell.setCellStyle(headercs);
			cell = row.createCell(21);	cell.setCellValue("% Primer día Atraso");			cell.setCellStyle(headercs);
			cell = row.createCell(22);	cell.setCellValue("% Siguientes días Atraso");		cell.setCellStyle(headercs);
			cell = row.createCell(23);	cell.setCellValue("Monto primer día atraso");		cell.setCellStyle(headercs);
			cell = row.createCell(24);	cell.setCellValue("Monto siguientes días atraso");	cell.setCellStyle(headercs);
			
			DecimalFormat df = new DecimalFormat("0.00");
			df.setMaximumFractionDigits(2);			
			
			// FILAS 1..N
			i = 0;
			while (cursorDetail.next()) {  //itero filas
				Object[] a = cursorDetail.get();
				
				row = sheet.createRow(i + 1);
				
				cell = row.createCell(0);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[0]);
				cell = row.createCell(1);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[1]);
				cell = row.createCell(2);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[2]);
				cell = row.createCell(3);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[3]);
				cell = row.createCell(4);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((BigInteger) a[4]).longValue());
				cell = row.createCell(5);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[5]);
				cell = row.createCell(6);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[6]);
				cell = row.createCell(7);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[7]);
				cell = row.createCell(8);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[8]);
				cell = row.createCell(9);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[9]));
				cell = row.createCell(10);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[10]));
				cell = row.createCell(11);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[11]));
				cell = row.createCell(12);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue((Integer) a[12]);
				cell = row.createCell(13);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[13]);
				cell = row.createCell(14);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[14]);
				cell = row.createCell(15);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[15]);
				cell = row.createCell(16);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[16]).intValue());
				cell = row.createCell(17);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[17]).intValue());
				cell = row.createCell(18);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[18]).intValue());
				cell = row.createCell(19);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[19]).intValue());
				cell = row.createCell(20);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[20]).intValue());
				cell = row.createCell(21);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(df.format((Double) a[21]));
				cell = row.createCell(22);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(df.format((Double) a[22]));
				cell = row.createCell(23);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[23]).intValue());
				cell = row.createCell(24);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[24]).intValue());
				
				i++;
			}
			
//			range = new CellRangeAddress(1, i, 0, 24);
//			setBorderCellStyle(range, sheet, workbook, CellStyle.BORDER_MEDIUM, IndexedColors.BLACK.getIndex());
			
			// Crear Archivo xlsx	
			workbook.write(fileOut);		
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new OperationFailedException("Error al escribir archivo", e);
		}
			
		resultDTO.setDownloadfilename(downloadfilename);
		resultDTO.setRealfilename(realfilename);
		
		return resultDTO;
	}
	
	private String getVendorQueryString(String whereCondition) throws AccessDeniedException {
				
		String SQL =
			"SELECT " + //
				"vd.id, " + //
				"vd.code, " + //
				"vd.name, " + //
				"SUM(CASE WHEN kpid.finefactordays = 0 " + //
					 	 "THEN 0.0 " + //
					     "ELSE :firstdayfine + (:nextdaysfine * (kpid.finefactordays - 1)) " + //
					     "END) AS totalfine, " + //
				"SUM(CASE WHEN kpid.finefactordays = 0 " + //
					 	 "THEN 0.0 " + //
					 	 "ELSE (:firstdayfine + (:nextdaysfine * (kpid.finefactordays - 1))) * kpid.itemfinalcost * kpid.detailunits " + //
					 	 "END) AS totalcurrencyfine " + //
			"FROM " + //
				"logistica.kpivevcddetail AS kpid JOIN " + //
				"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVCD' JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id " + //
			"WHERE " + //
				"kpid.fpi >= :since AND kpid.fpi < :until AND kpifp.since = :since " + //
				whereCondition + //
			"GROUP BY " + //
				"vd.id, vd.code, vd.name " + //
			"ORDER BY " + //
				"vd.name"; //
		
		return SQL;
	}
	
	private String getOrderDetailQueryString(String whereCondition, OrderCriteriaDTO[] orderCriteria) throws AccessDeniedException {
		
		String SQL = 
			"WITH kpid AS(" + //
					"SELECT DISTINCT " + //
						"kpid.ordernumber, " + //
						"kpid.orderrequestnumber, " + //
						"kpid.currentorderstate, " + //
						"kpid.sendingdate, " + //
						"kpid.fpi, " + //
						"kpid.currentorderstatedate, " + //
						"CASE WHEN kpid.deliverydelayeddays IS NULL THEN NULL " + //
							 "WHEN kpid.deliverydelayeddays > 0 THEN kpid.deliverydelayeddays " + //
							 "ELSE 0 " + //
							 "END AS deliverydelayeddays, " + //
						"CASE WHEN kpid.delayeddays > 0 THEN kpid.delayeddays " + //
							 "ELSE 0 " + //
							 "END AS delayeddays, " + //
						"kpid.iteminternalcode, " + //
						"kpid.itemname, " + //
						"kpid.itemfinalcost, " + //
						"kpid.detailunits, " + //
						"kpid.detailtodeliveryunits, " + //
						"kpid.detailreceivedunits, " + //
						"kpid.detailpendingunits, " + //
						"kpid.finefactordays, " + //
						"kpid.vendor_id, " + //
						"kpid.department_id, " + //
						"kpid.kpivevcdtype_id, " + //
						"kpid.salestore_id " + //
					"FROM " + //
						"logistica.kpivevcddetail AS kpid " + //
					"WHERE " + //
						"kpid.fpi >= :since AND kpid.fpi < :until " + //
						whereCondition + //
					") " + //
			"SELECT " + //
				"COALESCE(ss.code, '') AS salestorecode, " + //
				"COALESCE(ss.name, '') AS salestorename, " + //
				"vd.code AS vendorcode, " + //
				"vd.name AS vendorname, " + //
				"kpid.ordernumber AS ordernumber, " + //
				"kpid.orderrequestnumber AS orderrequestnumber, " + //
				"dp.code AS departmentcode, " + //
				"dp.name AS departmentname, " + //
				"kpid.currentorderstate AS currentorderstatetypename, " + //
				"kpid.sendingdate AS sendingdate, " + //
				"kpid.fpi AS fpi, " + //
				"kpid.currentorderstatedate AS currentstatetypedate, " + //
				"CASE WHEN kpid.deliverydelayeddays IS NULL THEN kpid.delayeddays " + //
					 "ELSE kpid.deliverydelayeddays " + //
					 "END AS delayeddays, " + //
				"kpit.description AS kpivevtypename, " + //
				"kpid.iteminternalcode AS iteminternalcode, " + //
				"kpid.itemname AS itemname, " + //
				"kpid.itemfinalcost AS itemfinalcost, " + //
				"kpid.detailunits AS requestedunits, " + //
				"kpid.detailtodeliveryunits AS todeliveryunits, " + //
				"kpid.detailreceivedunits AS receivedunits, " + //
				"kpid.detailpendingunits AS pendingunits, " + //
				":firstdayfine * 100 AS firstdayfinepercent, " + //
				":nextdaysfine * 100 AS nextdaysfinepercent, " + //
				"CASE WHEN kpid.finefactordays = 0 " + //
					 "THEN 0.0 " + //
					 "ELSE :firstdayfine * kpid.itemfinalcost * kpid.detailunits " + //
					 "END AS firstdayfineamount, " + //
				"CASE WHEN kpid.finefactordays = 0 " + //
				 	 "THEN 0.0 " + //
				 	 "ELSE :nextdaysfine * (kpid.finefactordays - 1) * kpid.itemfinalcost * kpid.detailunits " + //
				 	 "END AS nextdaysfineamount " + //
			"FROM " + //
				"kpid JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id JOIN " + //
				"logistica.department AS dp ON dp.id = kpid.department_id JOIN " + //
				"logistica.kpivevcdtype AS kpit ON kpit.id = kpid.kpivevcdtype_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpid.salestore_id " + //
			getOrderByString(mapKPIvevCDOrderDetailSQL, orderCriteria); //

		return SQL;
	}
	
	private void setBorderCellStyle(CellRangeAddress range, SXSSFSheet sheet, SXSSFWorkbook workbook, short borderline, short bordercolor) {		
		RegionUtil.setBorderBottom(borderline, range, sheet, workbook);
		RegionUtil.setBottomBorderColor(bordercolor, range, sheet, workbook);
		RegionUtil.setBorderLeft(borderline, range, sheet, workbook);
		RegionUtil.setLeftBorderColor(bordercolor, range, sheet, workbook);
		RegionUtil.setBorderRight(borderline, range, sheet, workbook);
		RegionUtil.setRightBorderColor(bordercolor, range, sheet, workbook);
		RegionUtil.setBorderTop(borderline, range, sheet, workbook);
		RegionUtil.setTopBorderColor(bordercolor, range, sheet, workbook);		
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
		
	@Resource
	private ManagedExecutorService executor;

	private void handleStream(InputStream inputStream, String type) {

		// EJECUTA UNA TAREA INDEPENDIENTE PARA MANEJAR LOS STREAM DE SALIDA
		try{
			executor.submit(new RuntimeProcessUtils(inputStream, type));			
		
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}				
	}
	
}
