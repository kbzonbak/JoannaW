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
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailCalculateDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDDetailW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPD;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDDetail;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDType;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.utils.RuntimeProcessUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/kpi/KPIvevPDDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevPDDetailServer extends LogisticElementServer<KPIvevPDDetail, KPIvevPDDetailW> implements KPIvevPDDetailServerLocal {

	private Map<String, String> mapKPIDetailSQL = new HashMap<String, String>();
	{
		mapKPIDetailSQL.put("ID", "kpidetail.id");	
		mapKPIDetailSQL.put("ORDERNUMBER", "kpidetail.ordernumber");	
		mapKPIDetailSQL.put("ORDERREQUEST", "kpidetail.orderrequestnumber");		
		mapKPIDetailSQL.put("DEPARTMENTCODE", "de.code");		
		mapKPIDetailSQL.put("DEPARTMENTNAME", "de.name");
		mapKPIDetailSQL.put("SALESTORECODE", "ss.code");		
		mapKPIDetailSQL.put("SALESTORENAME", "ss.name");
		mapKPIDetailSQL.put("CURRENTDELIVERYSTATE", "kpidetail.currentdeliverystate");	
		mapKPIDetailSQL.put("DELIVERYCURRENTDATE", "kpidetail.deliverycurrentdate");
		mapKPIDetailSQL.put("FCM", "kpidetail.fcm");
		mapKPIDetailSQL.put("FREP", "kpidetail.frep");
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
		mapKPISummaryDetailSQL.put("ID", "kpidetail.id");	
		mapKPISummaryDetailSQL.put("ORDERNUMBER", "kpidetail.ordernumber");	
		mapKPISummaryDetailSQL.put("ORDERREQUEST", "kpidetail.orderrequestnumber");
		mapKPISummaryDetailSQL.put("VENDORCODE", "vd.code");		
		mapKPISummaryDetailSQL.put("VENDORSOCIALREASON", "vd.name");
		mapKPISummaryDetailSQL.put("DEPARTMENTCODE", "de.code");		
		mapKPISummaryDetailSQL.put("DEPARTMENTNAME", "de.name");
		mapKPISummaryDetailSQL.put("SALESTORECODE", "ss.code");		
		mapKPISummaryDetailSQL.put("SALESTORENAME", "ss.name");
		mapKPISummaryDetailSQL.put("CURRENTDELIVERYSTATE", "kpidetail.currentdeliverystate");	
		mapKPISummaryDetailSQL.put("DELIVERYCURRENTDATE", "kpidetail.deliverycurrentdate");
		mapKPISummaryDetailSQL.put("FCM", "kpidetail.fcm");
		mapKPISummaryDetailSQL.put("FREP", "kpidetail.frep");
		mapKPISummaryDetailSQL.put("ITEMINTERNALCODE", "kpidetail.iteminternalcode");
		mapKPISummaryDetailSQL.put("DESCRIPTION", "kpitype.description");
		mapKPISummaryDetailSQL.put("ITEMNAME", "kpidetail.itemname");
		mapKPISummaryDetailSQL.put("REQUESTEDUNITS", "kpidetail.detailunits");
		mapKPISummaryDetailSQL.put("TODELIVERYUNITS", "kpidetail.detailtodeliveryunits");
		mapKPISummaryDetailSQL.put("RECEIVEDUNITS", "kpidetail.detailreceivedunits");
		mapKPISummaryDetailSQL.put("PENDINGUNITS", "kpidetail.detailpendingunits");		
	}
	
	private Map<String, String> mapKPIvevPDOrderDetailSQL = new HashMap<String, String>();
	{
		mapKPIvevPDOrderDetailSQL.put("SALESTORECODE", "salestorecode");	
		mapKPIvevPDOrderDetailSQL.put("SALESTORENAME", "salestorename");	
		mapKPIvevPDOrderDetailSQL.put("VENDORCODE", "vd.code");		
		mapKPIvevPDOrderDetailSQL.put("VENDORNAME", "vd.name");		
		mapKPIvevPDOrderDetailSQL.put("ORDERNUMBER", "kpid.ordernumber");
		mapKPIvevPDOrderDetailSQL.put("ORDERREQUESTNUMBER", "kpid.orderrequestnumber");	
		mapKPIvevPDOrderDetailSQL.put("DEPARTMENTCODE", "dp.code");
		mapKPIvevPDOrderDetailSQL.put("DEPARTMENTNAME", "dp.name");
		mapKPIvevPDOrderDetailSQL.put("CURRENTORDERSTATETYPENAME", "kpid.currentorderstate");
		mapKPIvevPDOrderDetailSQL.put("CURRENTDELIVERYSTATETYPENAME", "kpid.currentdeliverystate");
		mapKPIvevPDOrderDetailSQL.put("SENDINGDATE", "kpid.sendingdate");	
		mapKPIvevPDOrderDetailSQL.put("FCM", "kpid.fcm");	
		mapKPIvevPDOrderDetailSQL.put("FREP", "kpid.frep");	
		mapKPIvevPDOrderDetailSQL.put("CURRENTSTATETYPEDATE", "kpid.deliverycurrentdate");		
		mapKPIvevPDOrderDetailSQL.put("DELAYEDDAYS", "delayeddays");		
		mapKPIvevPDOrderDetailSQL.put("KPIVEVTYPENAME", "kpit.description");
		mapKPIvevPDOrderDetailSQL.put("ITEMINTERNALCODE", "kpid.iteminternalcode");	
		mapKPIvevPDOrderDetailSQL.put("ITEMNAME", "kpid.itemname");
		mapKPIvevPDOrderDetailSQL.put("ITEMFINALCOST", "kpid.itemfinalcost");
		mapKPIvevPDOrderDetailSQL.put("REQUESTEDUNITS", "kpid.detailunits");
		mapKPIvevPDOrderDetailSQL.put("TODELIVERYUNITS", "kpid.detailtodeliveryunits");
		mapKPIvevPDOrderDetailSQL.put("RECEIVEDUNITS", "kpid.detailreceivedunits");
		mapKPIvevPDOrderDetailSQL.put("PENDINGUNITS", "kpid.detailpendingunits");
		mapKPIvevPDOrderDetailSQL.put("FIRSTDAYFINEPERCENT", "firstdayfinepercent");
		mapKPIvevPDOrderDetailSQL.put("NEXTDAYSFINEPERCENT", "nextdaysfinepercent");
		mapKPIvevPDOrderDetailSQL.put("FIRSTDAYFINEAMOUNT", "firstdayfineamount");
		mapKPIvevPDOrderDetailSQL.put("NEXTDAYSFINEAMOUNT", "nextdaysfineamount");
	}
		
	private final int WINDOWS_BUFFER_SIZE = 1000;
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	VendorServerLocal vendorserver;	
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	KPIvevPDTypeServerLocal kpivevpdtypeserver;
	
	@EJB
	KPIvevPDServerLocal kpivevpdserver;

	public KPIvevPDDetailW addKPIvevPDDetail(KPIvevPDDetailW kpivevpddetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDDetailW) addElement(kpivevpddetail);
	}
	public KPIvevPDDetailW[] getKPIvevPDDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDDetailW[]) getIdentifiables();
	}
	public KPIvevPDDetailW updateKPIvevPDDetail(KPIvevPDDetailW kpivevpddetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevPDDetailW) updateElement(kpivevpddetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevPDDetail entity, KPIvevPDDetailW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
		wrapper.setSalestoreid(entity.getSalestore() != null ? new Long(entity.getSalestore().getId()) : new Long(0));
		wrapper.setKpivevpdtypeid(entity.getKpivevpdtype() != null ? new Long(entity.getKpivevpdtype().getId()) : new Long(0));
		wrapper.setKpivevpdid(entity.getKpivevpd() != null ? new Long(entity.getKpivevpd().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevPDDetail entity, KPIvevPDDetailW wrapper) throws OperationFailedException, NotFoundException {
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
		if (wrapper.getKpivevpdtypeid() != null && wrapper.getKpivevpdtypeid() > 0) { 
			KPIvevPDType kpivevpdtype = kpivevpdtypeserver.getReferenceById(wrapper.getKpivevpdtypeid());
			if (kpivevpdtype != null) { 
				entity.setKpivevpdtype(kpivevpdtype);
			}
		}
		if (wrapper.getKpivevpdid() != null && wrapper.getKpivevpdid() > 0) { 
			KPIvevPD kpivevpd = kpivevpdserver.getReferenceById(wrapper.getKpivevpdid());
			if (kpivevpd != null) { 
				entity.setKpivevpd(kpivevpd);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "KPIvevPDDetail";
	}
	
	@Override
	protected void setEntityname() {
		entityname = "KPIvevPDDetail";
	}
	
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException{
		
		String SQL =
			"DELETE FROM logistica.kpivevpddetail AS kd " + //
			"USING (" + //
				"SELECT " + //
					"kd.id " + //
				"FROM " + //
					"logistica.kpivevpddetail AS kd LEFT JOIN " + //
					"logistica.hiredcourier AS hc ON hc.vendor_id = kd.vendor_id " + //
				"WHERE " + //
					"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL) AS a " + //
			"WHERE " + //
				"a.id = kd.id AND kd.fcm >= :since AND kd.fcm <= :until "; //
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL += "AND kd.vendor_id = :vendorid "; //
		}		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		
		query.executeUpdate();		
	}
	
	public KPIvevPDDetailW[] getDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException{
		
		// Courier debe recoger mercanc�a antes de esta hora del d�a anterior a la entrega programada
		// Ac� se calculan los minutos a restar desde las 00:00
		String maxPickUpTime = RegionalLogisticConstants.getInstance().getMaxCourierPickUpTime();
		String[] maxPickUpTimeArray = maxPickUpTime.split(":");
		int interval = 1440 - Integer.parseInt(maxPickUpTimeArray[0]) * 60 - Integer.parseInt(maxPickUpTimeArray[1]);
		
		String SQL =
			"WITH cpd AS(" + //
					"SELECT " + //
						"dods.delivery_id, " + //
						"MAX(dods.deliverystatedate) AS deliverystatedate " + //
					"FROM " + //
						"logistica.directorder AS dor JOIN " + //
						"logistica.directorderstatetype AS dost ON dost.id = dor.currentstatetype_id JOIN " + //
						"logistica.dodeliverystate AS dods ON dods.delivery_id = dor.dodelivery_id JOIN " + //
						"logistica.dodeliverystatetype AS dodst ON dodst.id = dods.deliverystatetype_id JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = dor.vendor_id " + //
					"WHERE " + //
						"dor.number > 0 AND dost.code <> 'BLOQ_PARIS' AND " + //
						"dor.originaldeliverydate >= :since AND dor.originaldeliverydate <= :until AND " + //
						"dodst.code = 'PEND_RETIRO' " + //
						(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? "AND dor.vendor_id = :vendorid " : "") + //						
					"GROUP BY " + //
						"dods.delivery_id), " + //
				 "crd AS(" + //
					"SELECT " + //
						"dos.order_id, " + //
						"MAX(dos.when1) AS when " + //
					"FROM " + //
						"logistica.directorder AS dor JOIN " + //
						"logistica.directorderstatetype AS cdost ON cdost.id = dor.currentstatetype_id JOIN " + //
						"logistica.directorderstate AS dos ON dos.order_id = dor.id JOIN " + //
						"logistica.directorderstatetype AS dost ON dost.id = dos.orderstatetype_id " + //
					"WHERE " + //
						"dor.number > 0 AND cdost.code <> 'BLOQ_PARIS' AND " + //
						"dor.originaldeliverydate >= :since AND dor.originaldeliverydate <= :until AND " + //
						"dost.code = 'REPROG_CLIENTE' " + //
						(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? "AND dor.vendor_id = :vendorid " : "") + //						
					"GROUP BY " + //
						"dos.order_id), " + //
				 "vrd AS(" + //
					"SELECT " + //
						"dos.order_id, " + //
						"MAX(dos.when1) AS when " + //
					"FROM " + //
						"logistica.directorder AS dor JOIN " + //
						"logistica.directorderstatetype AS cdost ON cdost.id = dor.currentstatetype_id JOIN " + //
						"logistica.directorderstate AS dos ON dos.order_id = dor.id JOIN " + //
						"logistica.directorderstatetype AS dost ON dost.id = dos.orderstatetype_id " + //
					"WHERE " + //
						"dor.number > 0 AND cdost.code <> 'BLOQ_PARIS' AND " + //
						"dor.originaldeliverydate >= :since AND dor.originaldeliverydate <= :until AND " + //
						"dost.code = 'REPROG_PROV' " + //
						(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? "AND dor.vendor_id = :vendorid " : "") + //
					"GROUP BY " + //
						"dos.order_id) " + //
			"SELECT " + //
				"dor.number AS ordernumber, " + //
				"dor.requestnumber AS orderrequestnumber, " + //
				"dor.currentstatetype_id AS currentorderstatetypeid, " + //
				"dost.name AS currentorderstate, " + //
				"COALESCE(dode.currentstatetype_id, 0) AS currentdeliverystatetypeid, " + //
				"COALESCE(dodst.name, '') AS currentdeliverystate, " + //
				"dode.currentstdate AS deliverycurrentdateldt, " + //
				"cpd.deliverystatedate AS courierpendingdateldt, " + //
				"dor.originaldeliverydate AS fcmldt, " + //
				"dor.currentdeliverydate AS frepldt, " + //
				"dor.emitted AS sendingdateldt, " + //				
				"crd.when AS clientrescheduledateldt, " + //
				"vrd.when AS vendorrescheduledateldt, " + //
				"CASE WHEN dode.currentstdate IS NULL " + //
					 "THEN NULL " + //
					 "ELSE DATE(dode.currentstdate) - DATE(dor.currentdeliverydate) " + //
					 "END AS deliverydelayeddays, " + //
				"CASE WHEN cpd.deliverystatedate IS NULL " + //
					 "THEN NULL " + //
					 "ELSE CAST(CEIL(EXTRACT(EPOCH FROM cpd.deliverystatedate - (DATE(dor.currentdeliverydate) - INTERVAL '" + interval + " minutes')) / 86400) AS INTEGER) " + // se compara con el d�a anterior a las HH:mm convertido a intervalo de minutos (18:00 son 360 minutos menos)
					 "END AS courierdeliverydelayeddays, " + //
				"CASE WHEN dost.code = 'CANC_PARIS' " + //
					 "THEN DATE(dor.currentstatetypedate) - DATE(dor.currentdeliverydate) " + //
					 "ELSE CURRENT_DATE - DATE(dor.currentdeliverydate) " + //
					 "END AS delayeddays, " + //
				"rsr.responsibility AS rescheduleresponsibility, " + //
				"i.internalcode AS iteminternalcode, " + //
				"vi.vendoritemcode AS vendoritemcode, " + //
				"i.name AS itemname, " + //
				"dod.finalcost AS itemfinalcost, " + //
				"dod.units AS detailunits, " + //
				"dod.todeliveryunits AS detailtodeliveryunits, " + //
				"dod.receivedunits AS detailreceivedunits, " + //
				"dod.pendingunits AS detailpendingunits, " + //
				"0 AS finefactordays, " + //
				// "0.0 AS finefactor, " quedar� en NULL para nuevos cálculos
				"dor.vendor_id AS vendorid, " + //
				"dor.department_id AS departmentid, " + //
				"dor.salestore_id AS salestoreid " + //
			"FROM " + //
				"logistica.directorder AS dor JOIN " + //
				"logistica.directorderstatetype AS dost ON dost.id = dor.currentstatetype_id LEFT JOIN " + //
				"crd ON crd.order_id = dor.id LEFT JOIN " + //
				"vrd ON vrd.order_id = dor.id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = dor.vendor_id JOIN " + //
				"logistica.directorderdetail AS dod ON dod.order_id = dor.id JOIN " + //
				"logistica.item AS i ON i.id = dod.item_id LEFT JOIN " + //
				"logistica.vendoritem AS vi ON vi.item_id = i.id AND vi.vendor_id = dor.vendor_id LEFT JOIN " + //
				"logistica.dodelivery AS dode ON dode.id = dor.dodelivery_id LEFT JOIN  " + //
				"logistica.dodeliverystatetype AS dodst ON dodst.id = dode.currentstatetype_id LEFT JOIN " + //
				"cpd ON cpd.delivery_id = dode.id LEFT JOIN " + //
				"logistica.couriertag AS ct ON ct.dodelivery_id = dode.id LEFT JOIN " + //
				"logistica.reschedulereason AS rsr ON rsr.id = ct.reschedulereason_id " + //
			"WHERE " +
				"dor.number > 0 AND dost.code <> 'BLOQ_PARIS' AND " + //
				"dor.originaldeliverydate >= :since AND dor.originaldeliverydate <= :until AND " + //
				"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL "; //
				
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			SQL += "AND dor.vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDDetailCalculateDTO.class));
		List<KPIvevPDDetailCalculateDTO> dataList = query.list();
		/*

		private LocalDateTime fcmldt;
			private LocalDateTime frepldt;
		private LocalDateTime clientrescheduledateldt;
		private LocalDateTime vendorrescheduledateldt;
		*/
		dataList.stream()
        .forEach(s -> {
            ((KPIvevPDDetailCalculateDTO) s).setDeliverycurrentdate(((KPIvevPDDetailCalculateDTO) s).getDeliverycurrentdateldt()!=null ? Date.from(((KPIvevPDDetailCalculateDTO) s).getDeliverycurrentdateldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            ((KPIvevPDDetailCalculateDTO) s).setCourierpendingdate(((KPIvevPDDetailCalculateDTO) s).getCourierpendingdateldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getCourierpendingdateldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            ((KPIvevPDDetailCalculateDTO) s).setSendingdate(((KPIvevPDDetailCalculateDTO) s).getSendingdateldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getSendingdateldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            
            ((KPIvevPDDetailCalculateDTO) s).setFcm(((KPIvevPDDetailCalculateDTO) s).getFcmldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getFcmldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            ((KPIvevPDDetailCalculateDTO) s).setFrep(((KPIvevPDDetailCalculateDTO) s).getFrepldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getFrepldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            ((KPIvevPDDetailCalculateDTO) s).setClientrescheduledate(((KPIvevPDDetailCalculateDTO) s).getClientrescheduledateldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getClientrescheduledateldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            ((KPIvevPDDetailCalculateDTO) s).setVendorrescheduledate(((KPIvevPDDetailCalculateDTO) s).getVendorrescheduledateldt()!=null ?Date.from(((KPIvevPDDetailCalculateDTO) s).getVendorrescheduledateldt().atZone(ZoneId.systemDefault()).toInstant()):null);
            
            
        }); 
		KPIvevPDDetailW[] resultW = new KPIvevPDDetailW[dataList.size()];
		BeanExtenderFactory.copyProperties(dataList.toArray(new KPIvevPDDetailCalculateDTO[dataList.size()]), resultW, KPIvevPDDetailW.class);
		
		return resultW;		

	}
	
	public int getCountDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException {
		
		String SQL =
			"SELECT " + //
				"COUNT(1) " + //
			"FROM " + //
				"logistica.directorder AS dor JOIN " + //
				"logistica.directorderstatetype AS dost ON dost.id = dor.currentstatetype_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = dor.vendor_id " + //
			"WHERE " +
				"dor.number > 0 AND dost.code <> 'BLOQ_PARIS' AND " + //
				"dor.originaldeliverydate >= :since AND dor.originaldeliverydate <= :until AND " + //
				"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL "; //
		
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			SQL += "AND dor.vendor_id = :vendorid "; //
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		
		query.setDate("since", since);
		query.setDate("until", until);
		if(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	public KPIvevPDDetailReportDTO[] getKPIvevPDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid "; //
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) "; //
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) "; //
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.kpivevpdtype_id IN (:kpitypeids) "; //
		}
		
		String SQL = getQueryString(1, orderby, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("kpitypeids", kpitypeids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDDetailReportDTO.class));
		
		if (isPaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		return (KPIvevPDDetailReportDTO[]) list.toArray(new KPIvevPDDetailReportDTO[list.size()]);
	}
	
	public int countKPIvevPDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid "; //
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) "; //
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) "; //
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.kpivevpdtype_id IN (:kpitypeids) "; //
		}
		
		String SQL = getQueryString(2, null, whereCondition);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("until", until);
		query.setDate("since", since);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
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
					"kpidetail.currentdeliverystate AS currentdeliverystate, " + //
					"logistica.tostr(kpidetail.sendingdate) AS sendingdate, " + //
					"logistica.tostr(kpidetail.deliverycurrentdate) AS deliverycurrentdate, " + //
					"logistica.tostr(kpidetail.fcm) AS fcm, " + //
					"logistica.tostr(kpidetail.frep) AS frep, " + //
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
				"logistica.kpivevpddetail AS kpidetail JOIN " + //
				"logistica.kpivevpdtype AS kpitype ON kpitype.id = kpidetail.kpivevpdtype_id JOIN " + //
				"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpidetail.salestore_id " + //
			"WHERE " + //
				"kpidetail.fcm >= :since AND kpidetail.fcm < :until " + //
				whereCondition + //
			orderBy; //
		
		return SQL;
	}
	
	public KPIvevPDSummaryDetailReportDTO[] getKPIvevPDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid "; //
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) "; //
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) "; //
		}
		
		String SQL = getSummaryQueryString(1, orderby, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevPDSummaryDetailReportDTO.class));
		
		if (ispaginated) {	//Si quiero el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		return (KPIvevPDSummaryDetailReportDTO[]) list.toArray(new KPIvevPDSummaryDetailReportDTO[list.size()]);
	}
	
	public int countKPIvevPDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = :vendorid "; //
		}
				
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.department_id IN (:departmentids) "; //
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.salestore_id IN (:salestoreids) "; //
		}
		
		String SQL = getSummaryQueryString(2, null, whereCondition, courier);	
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("departmentids", departmentids);	
		}
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);	
		}
		
		int result = ((BigInteger)query.uniqueResult()).intValue();
		
		return result;
	}
	
	private String getSummaryQueryString(int queryType, OrderCriteriaDTO[] orderCriteria, String whereCondition, boolean courier) throws AccessDeniedException {		
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
					"kpidetail.currentdeliverystate AS currentdeliverystate, " + //
					"logistica.tostr(kpidetail.sendingdate) AS sendingdate, " + //
					"logistica.tostr(kpidetail.deliverycurrentdate) AS deliverycurrentdate, " + //
					"logistica.tostr(kpidetail.fcm) AS fcm, " + //
					"logistica.tostr(kpidetail.frep) AS frep, " + //
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
				"logistica.kpivevpddetail AS kpidetail JOIN " + //
				"logistica.kpivevpdtype AS kpitype ON kpitype.id = kpidetail.kpivevpdtype_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpidetail.vendor_id JOIN " + //
				"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpidetail.salestore_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
				"kpidetail.fcm >= :since AND kpidetail.fcm < :until AND " + //
				"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
				whereCondition + //
			orderBy; //
		
		return SQL;
	}
	
	public FileDownloadInfoResultDTO getKPIvevPDDetailDataSourceReport(Long vendorid, Long departmentids[], Long salestoreids[], Long[] kpitypeids, Date since, Date until, String vendorname, Long userKey, boolean courier) throws OperationFailedException {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat filedateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND kpidetail.vendor_id = " + vendorid + " "; //
		}
		
		if (departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.department_id IN ("; //
			for (int i = 0; i < departmentids.length; i++) {
				and += departmentids[i];
				if (i < departmentids.length - 1) {
					and += ","; //
				}
				else {
					and += ") "; //
				}
			}
			whereCondition += and;
		}
		
		if (salestoreids != null && salestoreids.length > 0 && salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.salestore_id IN ("; //
			for (int i = 0; i < salestoreids.length; i++) {
				and += salestoreids[i];
				if (i < salestoreids.length - 1) {
					and += ","; //
				}
				else {
					and += ") "; //
				}
			}
			whereCondition += and;
		}
		
		if (kpitypeids != null && kpitypeids.length > 0 && kpitypeids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			String and = "AND kpidetail.kpivevpdtype_id IN ("; //
			for (int i = 0; i < kpitypeids.length; i++) {
				and += kpitypeids[i];
				if (i < kpitypeids.length - 1) {
					and += ","; //
				}
				else {
					and += ") "; //
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
					"kpidetail.currentdeliverystate, " + //
					"to_char(kpidetail.sendingdate, 'DD-MM-YYYY'), " + //
					"to_char(kpidetail.fcm, 'DD-MM-YYYY'), " + //
					"date_part('month', kpidetail.fcm) AS MONTH_COMPROMISED, " + //
					"date_part('year', kpidetail.fcm) AS YEAR_COMPROMISED, " + //
					"to_char(kpidetail.frep, 'DD-MM-YYYY'), " + //
					"CASE WHEN dost.code = 'CANC_PARIS' " + //
					 	 "THEN to_char(dor.currentstatetypedate, 'DD-MM-YYYY') " + //
					 	 "ELSE to_char(kpidetail.deliverycurrentdate, 'DD-MM-YYYY') " + //
					 	 "END AS currentstatetypedate, " + //
					"kpitype.description, " + //
					"kpidetail.iteminternalcode, " + //
					"kpidetail.itemname, " + //
					"kpidetail.detailunits AS requestedunits, " + //
					"kpidetail.detailtodeliveryunits AS todeliveryunits, " + //
					"kpidetail.detailreceivedunits AS receivedunits, " + //
					"kpidetail.detailpendingunits AS pendingunits " + //
				"FROM " + //
					"logistica.kpivevpddetail AS kpidetail JOIN " + //
					"logistica.directorder AS dor ON dor.number = kpidetail.ordernumber AND dor.vendor_id = kpidetail.vendor_id JOIN " + //
					"logistica.directorderstatetype AS dost ON dost.id = dor.currentstatetype_id JOIN " + //
					"logistica.kpivevpdtype AS kpitype ON kpitype.id = kpidetail.kpivevpdtype_id JOIN " + //
					"logistica.vendor AS vd ON vd.id = kpidetail.vendor_id JOIN " + //
					"logistica.department AS de ON de.id = kpidetail.department_id LEFT JOIN " + //
					"logistica.location AS ss ON ss.id = kpidetail.salestore_id LEFT JOIN " + //
					"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
				"WHERE " + //
					"kpidetail.fcm >= '" + dateFormat.format(since) + "' AND " + //
					"kpidetail.fcm < '" + dateFormat.format(until) + "' " + //
					whereCondition + //
					" AND hc.vendor_id IS "+(courier ? " NOT " : "") + " NULL " + //
				"ORDER BY " + //
					"kpidetail.ordernumber"; //
		
		String comando1 = null;
		String comando2 = null;
		
		try {
			final Session session = getSession();

			Date date = new Date();
			SQLQuery query_orig = (SQLQuery) getSession().createSQLQuery(SQL);

			String realfilename = "KPIVEVPDDTOFTE" + date.getTime() + "_" + userKey + ".csv";
		
			final String filename = "/tmp/" + realfilename;
			final String filetemp = "/tmp/" + realfilename.replace(".csv", ".sql");
			String txt_query = query_orig.getQueryString();
			File archivo = new File(filetemp);
			FileWriter escribir = new FileWriter(archivo, true);
			escribir.write(txt_query);
			escribir.close();

			String downloadfilename = "Dato Fuente KPI VEV PD " + vendorname + "(Fecha Comp. Desde " + filedateFormat.format(since) + " Hasta " + filedateFormat.format(until) + ").zip";

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
			comando2 = "sh /b2b/shell/fileToDownload/moveFile1.sh " + realfilename + " " + RegionalLogisticConstants.TITLESKPIVEVPD;
	
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
	
	public KPIvevVendorDimensionDTO[] getVendorsByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier) throws OperationFailedException, AccessDeniedException {
		
		String whereCondition =
				"AND hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
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
	
	public KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, boolean courier) throws OperationFailedException {
		
		String SQL =
			"SELECT DISTINCT " + //
				"dp.id, " + //
				"dp.code, " + //
				"dp.name " + //
			"FROM " + //
				"logistica.kpivevpddetail AS kpid JOIN " + //
				"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVPD' JOIN " + //			
				"logistica.department AS dp ON dp.id = kpid.department_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
			"kpid.fcm >= :since AND kpid.fcm < :until AND kpifp.since = :since AND " + //
				"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
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
	
	public KPIvevFineDataDTO[] getFineDataByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier) throws OperationFailedException {
		
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
				"logistica.kpivevpddetail AS kpid JOIN " + //
				"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVPD' JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
				"kpid.fcm >= :since AND kpid.fcm < :until AND kpifp.since = :since AND " + //
				"hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
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
	
	public KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier, OrderCriteriaDTO[] orderCriteria) throws OperationFailedException, AccessDeniedException{
		
		String whereCondition =
			"AND hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
			(vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND kpid.vendor_id = :vendorid " : //
					"") + //
			(departmentids != null && departmentids.length > 0 && departmentids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS() ? //
					"AND kpid.department_id IN (:departmentids) " : //
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
	
	public FileDownloadInfoResultDTO getExcelKPIvevPDFineDataReport(Long vendorid, Long[] departmentids, Integer month, Integer year, Double firstdayfine, Double nextdaysfine, boolean courier, Long userKey) throws OperationFailedException, AccessDeniedException {
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
			"AND hc.vendor_id IS " + (courier ? "NOT " : "") + "NULL " + //
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
		KPIvevDepartmentDimensionDTO[] departments = getDepartmentsByKPIvevPDFinePeriod(vendorid, departmentids, since, until, courier); 
		if (departments == null || departments.length <= 0) {
			throw new OperationFailedException("Error obteniendo departamentos para la descarga");
		}
		
		// Multas
		KPIvevFineDataDTO[] finedata = getFineDataByKPIvevPDFinePeriod(vendorid, departmentids, since, until, firstdayfine, nextdaysfine, courier); 
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
		orderCriteria[0].setPropertyname("FCM");
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
		String downloadfilename = "Multas " + monthStr + " " + year + " PE-PD" + (courier ? " Courier" : "") + ".xlsx";
		String realfilename = "Multas_" + monthStr + year + "_PE-PD_" + (courier ? " Courier" : "") + "_" + dateFormat.format(new Date()) + "hrs_" + userKey + ".xlsx";
				
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
			cell.setCellValue("% MULTA - FLUJO PE-PD" + (courier ? " Courier" : ""));
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
					cell.setCellValue(departments[j].getCode() + "\n" + departments[j].getName());
					cell.setCellStyle(departmentcs);
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
						cell1.setCellValue(fineMap.get(key).getFinalfine());
						cell1.setCellStyle(numericcs);
						
						cell2.setCellValue(Math.round(fineMap.get(key).getCompliance() * 10) / 10.0);
						cell2.setCellStyle(compliancecs);
						
						cell3.setCellValue(fineMap.get(key).getCurrencyfine());
						cell3.setCellStyle(currencycs);					
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
			cell = row.createCell(1);	cell.setCellValue("Nombre Tienda"); 				cell.setCellStyle(headercs);
			cell = row.createCell(2);	cell.setCellValue("Código Proveedor"); 				cell.setCellStyle(headercs);
			cell = row.createCell(3);	cell.setCellValue("Razón Social"); 					cell.setCellStyle(headercs);
			cell = row.createCell(4);	cell.setCellValue("Número Orden"); 					cell.setCellStyle(headercs);
			cell = row.createCell(5);	cell.setCellValue("Número Solicitud"); 				cell.setCellStyle(headercs);
			cell = row.createCell(6);	cell.setCellValue("Código Depto."); 				cell.setCellStyle(headercs);
			cell = row.createCell(7);	cell.setCellValue("Departamento"); 					cell.setCellStyle(headercs);
			cell = row.createCell(8);	cell.setCellValue("Estado de Orden"); 				cell.setCellStyle(headercs);
			cell = row.createCell(9);	cell.setCellValue("Estado de último Despacho"); 	cell.setCellStyle(headercs);
			cell = row.createCell(10);	cell.setCellValue("Fecha Envío"); 					cell.setCellStyle(headercs);
			cell = row.createCell(11);	cell.setCellValue("Fecha Compromiso"); 				cell.setCellStyle(headercs);
			cell = row.createCell(12);	cell.setCellValue("Fecha Reprogramada"); 			cell.setCellStyle(headercs);
			cell = row.createCell(13);	cell.setCellValue("Fecha Cambio Estado"); 			cell.setCellStyle(headercs);
			cell = row.createCell(14);	cell.setCellValue("Días Atraso"); 					cell.setCellStyle(headercs);
			cell = row.createCell(15);	cell.setCellValue("Evaluación"); 					cell.setCellStyle(headercs);
			cell = row.createCell(16);	cell.setCellValue("SKU Paris"); 					cell.setCellStyle(headercs);
			cell = row.createCell(17);	cell.setCellValue("Descripción SKU"); 				cell.setCellStyle(headercs);
			cell = row.createCell(18);	cell.setCellValue("Costo del producto"); 			cell.setCellStyle(headercs);
			cell = row.createCell(19);	cell.setCellValue("Unidades Solicitadas"); 			cell.setCellStyle(headercs);
			cell = row.createCell(20);	cell.setCellValue("Unidades en Despacho"); 			cell.setCellStyle(headercs);
			cell = row.createCell(21);	cell.setCellValue("Unidades Entregadas"); 			cell.setCellStyle(headercs);
			cell = row.createCell(22);	cell.setCellValue("Unidades Pendientes"); 			cell.setCellStyle(headercs);
			cell = row.createCell(23);	cell.setCellValue("% Primer día Atraso");			cell.setCellStyle(headercs);
			cell = row.createCell(24);	cell.setCellValue("% Siguientes días Atraso");		cell.setCellStyle(headercs);
			cell = row.createCell(25);	cell.setCellValue("Monto primer día atraso");		cell.setCellStyle(headercs);
			cell = row.createCell(26);	cell.setCellValue("Monto siguientes días atraso");	cell.setCellStyle(headercs);
			
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
				cell = row.createCell(9);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[9]);
				cell = row.createCell(10);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[10]));
				cell = row.createCell(11);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[11]));
				cell = row.createCell(12);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(sdf.format((Date) a[12]));
				cell = row.createCell(13);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(a[13] != null ? sdf.format((Date) a[13]) : "");
				cell = row.createCell(14);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue((Integer) a[14]);
				cell = row.createCell(15);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[15]);
				cell = row.createCell(16);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[16]);
				cell = row.createCell(17);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue((String) a[17]);
				cell = row.createCell(18);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[18]).intValue());
				cell = row.createCell(19);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[19]).intValue());
				cell = row.createCell(20);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[20]).intValue());
				cell = row.createCell(21);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[21]).intValue());
				cell = row.createCell(22);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[22]).intValue());
				cell = row.createCell(23);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(df.format((Double) a[23]));
				cell = row.createCell(24);	cell.setCellType(SXSSFCell.CELL_TYPE_STRING);	cell.setCellValue(df.format((Double) a[24]));
				cell = row.createCell(25);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[25]).intValue());
				cell = row.createCell(26);	cell.setCellType(SXSSFCell.CELL_TYPE_NUMERIC);	cell.setCellValue(((Double) a[26]).intValue());
				
				i++;
			}
		
//			range = new CellRangeAddress(1, i, 0, 26);
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
						"logistica.kpivevpddetail AS kpid JOIN " + //
						"logistica.kpivevfine AS kpif ON kpif.vendor_id = kpid.vendor_id AND kpif.department_id = kpid.department_id JOIN " + //
						"logistica.kpivevfineperiod AS kpifp ON kpifp.id = kpif.fineperiod_id AND kpifp.vevtype = 'VEVPD' JOIN " + //
						"logistica.vendor AS vd ON vd.id = kpid.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
					"WHERE " + //
						"kpid.fcm >= :since AND kpid.fcm < :until AND kpifp.since = :since " + //
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
						"kpid.currentdeliverystate, " + //
						"kpid.sendingdate, " + //
						"kpid.fcm, " + //
						"kpid.frep, " + //
						"CASE WHEN dost.code = 'CANC_PARIS' " + //
							 "THEN dor.currentstatetypedate " + //
							 "ELSE kpid.deliverycurrentdate " + //
							 "END AS currentstatetypedate, " + //
						"CASE WHEN kpid.deliverydelayeddays IS NULL THEN NULL " + //
							 "WHEN kpid.deliverydelayeddays > 0 THEN kpid.deliverydelayeddays " + //
							 "ELSE 0 " + //
							 "END AS deliverydelayeddays, " + //
						"CASE WHEN kpid.delayeddays > 0 " + //
							 "THEN kpid.delayeddays " + //
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
						"kpid.kpivevpdtype_id, " + //
						"kpid.salestore_id " + //
					"FROM " + //
						"logistica.kpivevpddetail AS kpid JOIN " + //
						"logistica.directorder AS dor ON dor.number = kpid.ordernumber AND dor.vendor_id = kpid.vendor_id JOIN " + //
						"logistica.directorderstatetype AS dost ON dost.id = dor.currentstatetype_id JOIN " + //
						"logistica.vendor AS vd ON vd.id = kpid.vendor_id LEFT JOIN " + //
						"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
					"WHERE " + //
						"kpid.fcm >= :since AND kpid.fcm < :until " + //
						whereCondition + //
					") " + //
			"SELECT DISTINCT " + //
				"COALESCE(ss.code, '') AS salestorecode, " + //
				"COALESCE(ss.name, '') AS salestorename, " + //
				"vd.code AS vendorcode, " + //
				"vd.name AS vendorname, " + //
				"kpid.ordernumber AS ordernumber, " + //
				"kpid.orderrequestnumber AS orderrequestnumber, " + //
				"dp.code AS departmentcode, " + //
				"dp.name AS departmentname, " + //
				"kpid.currentorderstate AS currentorderstatetypename, " + //
				"kpid.currentdeliverystate AS currentdeliverystatetypename, " + //
				"kpid.sendingdate AS sendingdate, " + //
				"kpid.fcm AS fcm, " + //
				"kpid.frep AS frep, " + //
				"kpid.currentstatetypedate AS currentstatetypedate, " + //
				"CASE WHEN kpid.deliverydelayeddays IS NULL " + //
					 "THEN kpid.delayeddays " + //
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
				"logistica.kpivevpdtype AS kpit ON kpit.id = kpid.kpivevpdtype_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpid.salestore_id " + //
			getOrderByString(mapKPIvevPDOrderDetailSQL, orderCriteria); //

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
