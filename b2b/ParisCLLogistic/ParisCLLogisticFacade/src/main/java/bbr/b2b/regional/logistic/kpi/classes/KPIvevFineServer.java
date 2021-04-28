package bbr.b2b.regional.logistic.kpi.classes;

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
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFineW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevFine;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevFinePeriod;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/kpi/KPIvevFineServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIvevFineServer extends LogisticElementServer<KPIvevFine, KPIvevFineW> implements KPIvevFineServerLocal {

	@EJB
	KPIvevFinePeriodServerLocal kpivevfineperiodserver;
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
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
		mapKPIvevCDOrderDetailSQL.put("REQUESTEDUNITS", "kpid.detailunits");
		mapKPIvevCDOrderDetailSQL.put("TODELIVERYUNITS", "kpid.detailtodeliveryunits");
		mapKPIvevCDOrderDetailSQL.put("RECEIVEDUNITS", "kpid.detailreceivedunits");
		mapKPIvevCDOrderDetailSQL.put("PENDINGUNITS", "kpid.detailpendingunits");
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
		mapKPIvevPDOrderDetailSQL.put("REQUESTEDUNITS", "kpid.detailunits");
		mapKPIvevPDOrderDetailSQL.put("TODELIVERYUNITS", "kpid.detailtodeliveryunits");
		mapKPIvevPDOrderDetailSQL.put("RECEIVEDUNITS", "kpid.detailreceivedunits");
		mapKPIvevPDOrderDetailSQL.put("PENDINGUNITS", "kpid.detailpendingunits");
	}
	
	public KPIvevFineW addKPIvevFine(KPIvevFineW kpivevfine) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFineW) addElement(kpivevfine);
	}
	public KPIvevFineW[] getKPIvevFines() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFineW[]) getIdentifiables();
	}
	public KPIvevFineW updateKPIvevFine(KPIvevFineW kpivevfine) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (KPIvevFineW) updateElement(kpivevfine);
	}

	@Override
	protected void copyRelationsEntityToWrapper(KPIvevFine entity, KPIvevFineW wrapper) {
		wrapper.setKpivevfineperiodid(entity.getKpivevfineperiod() != null ? new Long(entity.getKpivevfineperiod().getId()) : new Long(0));
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(KPIvevFine entity, KPIvevFineW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getKpivevfineperiodid() != null && wrapper.getKpivevfineperiodid() > 0) { 
			KPIvevFinePeriod kpivevfineperiod = kpivevfineperiodserver.getReferenceById(wrapper.getKpivevfineperiodid());
			if (kpivevfineperiod != null) { 
				entity.setKpivevfineperiod(kpivevfineperiod);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
	}
	
	protected void setEntitylabel() {
		entitylabel = "KPIvevFine";
	}
	
	protected void setEntityname() {
		entityname = "KPIvevFine";
	}
	
	public KPIvevFineW[] getKPIvevCDCompliancesByPeriod(Date since, Date until) throws OperationFailedException {
		
		String SQL =
			"WITH ff AS (" + //
				"SELECT " + //
					"vendor_id, " + //
					"department_id, " + //
					"CASE WHEN SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) > 0 " + //
						 "THEN CAST(SUM(totalreceivedconformed) AS REAL) / SUM(totalreceivedconformed + totalreceiveddelayed + totalproviderdelayed) * 100 " + //
						 "ELSE 100 " + //
						 "END AS compliance " + // 
				"FROM " + //
					"logistica.kpivevcd " + //
				"WHERE " + //
					"fpi >= :since AND fpi <= :until " + //
				"GROUP BY " + //
					"vendor_id, department_id) " + //
			"SELECT DISTINCT " + //
				// "0.0 AS totalfinefactor, " quedar� en NULL para nuevos cálculos
				"ff.compliance, " + // 
				// "0.0 AS finalfine, " quedar� en NULL para nuevos cálculos
				"kpid.vendor_id AS vendorid, " + //
				"kpid.department_id AS departmentid " + //
			"FROM " + //
				"logistica.kpivevcddetail AS kpid JOIN " + //
				"ff ON ff.vendor_id = kpid.vendor_id AND ff.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevcdtype AS kpit ON kpit.id = kpid.kpivevcdtype_id " + //
			"WHERE " + //
				"(kpit.code = 'RECIBO_ATRASO' OR kpit.code = 'ATRASO_PROVEEDOR') AND kpid.fpi >= :since AND kpid.fpi <= :until"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
				
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevFineW.class));
		
		List list = query.list();
		
		return (KPIvevFineW[]) list.toArray(new KPIvevFineW[list.size()]);		
	}
	
	public KPIvevFineW[] getKPIvevPDCompliancesByPeriod(Date since, Date until) throws OperationFailedException{
		
		String SQL =
			"WITH ff AS(" + //
				"SELECT " + //
					"vendor_id, " + //
					"department_id, " + //
					"CASE WHEN SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + totalproviderdelayed + totalcourierdelayed) > 0 " + //
						 "THEN CAST(SUM(totaldelivered) AS REAL) / SUM(totaldelivered + totalreceiveddelayed + totalcourierreceiveddelayed + " + //
						 											  "totalproviderdelayed + totalcourierdelayed) * 100 " + //
						 "ELSE 100 " + //
						 "END AS compliance " + // 
				"FROM " + //
					"logistica.kpivevpd " + //
				"WHERE " + //
					"fcm >= :since AND fcm <= :until " + //
				"GROUP BY " + //
					"vendor_id, department_id) " + //
			"SELECT DISTINCT " + //
				// "0.0 AS totalfinefactor, " quedar� en NULL para nuevos cálculos
				"ff.compliance, " + //
				// "0.0 AS finalfine, " quedar� en NULL para nuevos cálculos
				"kpid.vendor_id AS vendorid, " + //
				"kpid.department_id AS departmentid " + //
			"FROM " + //
				"logistica.kpivevpddetail AS kpid INNER JOIN " + //
				"ff ON ff.vendor_id = kpid.vendor_id AND ff.department_id = kpid.department_id JOIN " + //
				"logistica.kpivevpdtype AS kpit ON kpit.id = kpid.kpivevpdtype_id " + //
			"WHERE " + //
				"(kpit.code = 'RECIBO_ATRASO' OR kpit.code = 'RECIBO_ATRASO_COURIER' OR kpit.code = 'ATRASO_PROVEEDOR' OR " + //
				 "kpit.code = 'ATRASO_COURIER' OR kpit.code = 'EN_DESPACHO') AND " + //
				"kpid.fcm >= :since AND kpid.fcm <= :until"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
				
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevFineW.class));
		
		List list = query.list();
		
		return (KPIvevFineW[]) list.toArray(new KPIvevFineW[list.size()]);		
	}
	
	public KPIvevVendorDimensionDTO[] getVendorsByKPIvevCDFinePeriod(Long fineperiodid, Double uf){
		
		String SQL =
			"SELECT DISTINCT " + //
				"vd.id, " + //
				"vd.code, " + //
				"vd.name, " + //
				"SUM(fn.finalfine) AS totalfine, " + //
				"SUM(fn.finalfine) * :uf AS totalcurrencyfine " + //
			"FROM " + //
				"logistica.kpivevfine AS fn JOIN " + //
				"logistica.vendor AS vd ON vd.id = fn.vendor_id " + //
			"WHERE " + //
				"fn.fineperiod_id = :fineperiodid " + //
			"GROUP BY " + //
				"vd.id, vd.name " + //
			"ORDER BY " + //
				"vd.name"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
		query.setDouble("uf", uf);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevVendorDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevVendorDimensionDTO[]) list.toArray(new KPIvevVendorDimensionDTO[list.size()]);		
	}
	
	public KPIvevVendorDimensionDTO[] getVendorsByKPIvevPDFinePeriod(Long fineperiodid, Double uf, boolean courier){
		
		String SQL =
			"SELECT DISTINCT " + //
				"vd.id, " + //
				"vd.code, " + //
				"vd.name, " + //
				"SUM(fn.finalfine) AS totalfine, " + //
				"SUM(fn.finalfine) * :uf AS totalcurrencyfine " + //
			"FROM " + //
				"logistica.kpivevfine AS fn JOIN " + //
				"logistica.vendor AS vd ON vd.id = fn.vendor_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
				"fn.fineperiod_id = :fineperiodid AND " + //
				"hc.vendor_id IS " + (courier ? " NOT " : "") + " NULL " + //
			"GROUP BY " + //
				"vd.id, vd.name " + //
			"ORDER BY " + //
				"vd.name"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
		query.setDouble("uf", uf);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevVendorDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevVendorDimensionDTO[]) list.toArray(new KPIvevVendorDimensionDTO[list.size()]);		
	}
	
	public KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevCDFinePeriod(Long fineperiodid){
		
		String SQL =
			"SELECT DISTINCT " + //
				"dp.id, " + //
				"dp.code, " + //
				"dp.name " + //
			"FROM " + //
				"logistica.kpivevfine AS fn JOIN " + //
				"logistica.department AS dp ON dp.id = fn.department_id " + //
			"WHERE " + //
				"fn.fineperiod_id = :fineperiodid " + //
			"ORDER BY " + //
				"dp.code "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevDepartmentDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevDepartmentDimensionDTO[]) list.toArray(new KPIvevDepartmentDimensionDTO[list.size()]);		
	}
	
	public KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevPDFinePeriod(Long fineperiodid, boolean courier){
		
		String SQL =
			"SELECT DISTINCT " + //
				"dp.id, " + //
				"dp.code, " + //
				"dp.name " + //
			"FROM " + //
				"logistica.kpivevfine AS fn JOIN " + //
				"logistica.department AS dp ON dp.id = fn.department_id JOIN " + //
				"logistica.vendor AS vd ON vd.id = fn.vendor_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //
			"WHERE " + //
				"fn.fineperiod_id = :fineperiodid AND " + //
				"hc.vendor_id IS " + (courier ? " NOT " : "") + " NULL " + //
			"ORDER BY " + //
				"dp.code "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevDepartmentDimensionDTO.class));
		
		List list = query.list();
		
		return (KPIvevDepartmentDimensionDTO[]) list.toArray(new KPIvevDepartmentDimensionDTO[list.size()]);		
	}
	
	public KPIvevFineDataDTO[] getFineDataByKPIvevCDFinePeriod(Long fineperiodid, Double uf){
		
		String SQL =
			"SELECT " + //
				"vendor_id AS vendorid, " + //
				"department_id AS departmentid, " + //
				"totalfinefactor AS initialfine, " + //
				"compliance AS compliance, " + //
				"finalfine AS finalfine, " + //
				"finalfine * :uf AS currencyfine " + //
			"FROM " + //
				"logistica.kpivevfine " + //
			"WHERE " + //
				"fineperiod_id = :fineperiodid "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
		query.setDouble("uf", uf);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevFineDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevFineDataDTO[]) list.toArray(new KPIvevFineDataDTO[list.size()]);		
	}
	
	public KPIvevFineDataDTO[] getFineDataByKPIvevPDFinePeriod(Long fineperiodid, Double uf, boolean courier){
		
		String SQL =
			"SELECT " + //
				"fn.vendor_id AS vendorid, " + //
				"fn.department_id AS departmentid, " + //
				"fn.totalfinefactor AS initialfine, " + //
				"fn.compliance AS compliance, " + //
				"fn.finalfine AS finalfine, " + //
				"fn.finalfine * :uf AS currencyfine " + //
			"FROM " + //
				"logistica.kpivevfine AS fn JOIN " + //
				"logistica.vendor AS vd ON vd.id = fn.vendor_id LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = vd.id " + //			
			"WHERE " + //
				"fn.fineperiod_id = :fineperiodid AND " + //
				"hc.vendor_id IS " + (courier ? " NOT " : "") + " NULL "; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fineperiodid", fineperiodid);
		query.setDouble("uf", uf);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevFineDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevFineDataDTO[]) list.toArray(new KPIvevFineDataDTO[list.size()]);		
	}
	
	public KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevCDFinePeriod(Date since, Date until, OrderCriteriaDTO[] orderCriteria) throws AccessDeniedException{
		
		String SQL = 
			"WITH kpid AS(" + //
					"SELECT DISTINCT " + //
						"ordernumber, " + //
						"orderrequestnumber, " + //
						"currentorderstate, " + //
						"sendingdate, " + //
						"fpi, " + //
						"currentstateorderdate, " + //
						"CASE WHEN deliverydelayeddays IS NULL THEN NULL " + //
							 "WHEN deliverydelayeddays > 0 THEN deliverydelayeddays " + //
							 "ELSE 0 " + //
							 "END AS deliverydelayeddays, " + //
						"CASE WHEN delayeddays > 0 THEN delayeddays " + //
							 "ELSE 0 " + //
							 "END AS delayeddays, " + //
						"iteminternalcode, " + //
						"itemname, " + //
						"detailunits, " + //
						"detailtodeliveryunits, " + //
						"detailreceivedunits, " + //
						"detailpendingunits, " + //
						"vendor_id, " + //
						"department_id, " + //
						"kpivevcdtype_id, " + //
						"salestore_id " + //
					"FROM " + //
						"logistica.kpivevcddetail " + //
					"WHERE " + //
						"fpi >= :since AND fpi <= :until) " + //
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
				"kpid.detailunits AS requestedunits, " + //
				"kpid.detailtodeliveryunits AS todeliveryunits, " + //
				"kpid.detailreceivedunits AS receivedunits, " + //
				"kpid.detailpendingunits AS pendingunits " + //
			"FROM " + //
				"kpid JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id JOIN " + //
				"logistica.department AS dp ON dp.id = kpid.department_id JOIN " + //
				"logistica.kpivevcdtype AS kpit ON kpit.id = kpid.kpivevcdtype_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpid.salestore_id " + //
			getOrderByString(mapKPIvevCDOrderDetailSQL, orderCriteria); //
				
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevOrderDetailDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevOrderDetailDataDTO[]) list.toArray(new KPIvevOrderDetailDataDTO[list.size()]);		
	}
	
	public KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevPDFinePeriod(Date since, Date until, boolean courier, OrderCriteriaDTO[] orderCriteria) throws AccessDeniedException{
		
		String SQL =
			"WITH kpid AS(" + //
					"SELECT DISTINCT " + //
						"ordernumber, " + //
						"orderrequestnumber, " + //
						"currentorderstate, " + //
						"currentdeliverystate, " + //
						"sendingdate, " + //
						"fcm, " + //
						"frep, " + //
						"deliverycurrentdate, " + //
						"CASE WHEN deliverydelayeddays IS NULL THEN NULL " + //
							 "WHEN deliverydelayeddays > 0 THEN deliverydelayeddays " + //
							 "ELSE 0 " + //
							 "END AS deliverydelayeddays, " + //
						"CASE WHEN delayeddays > 0 THEN delayeddays " + //
							 "ELSE 0 " + //
							 "END AS delayeddays, " + //
						"iteminternalcode, " + //
						"itemname, " + //
						"detailunits, " + //
						"detailtodeliveryunits, " + //
						"detailreceivedunits, " + //
						"detailpendingunits, " + //
						"vendor_id" + //
						"department_id" + //
						"kpivevpdtype_id, " + //
						"salestore_id " + //
					"FROM " + //
						"logistica.kpivevpddetail " + //
					"WHERE " + //
						"fcm >= :since AND fcm <= :until) " + //
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
				"kpid.deliverycurrentdate AS currentstatetypedate, " + //
				"CASE WHEN kpid.deliverydelayeddays IS NULL " + //
					 "THEN kpid.delayeddays " + //
					 "ELSE kpid.deliverydelayeddays " + //
					 "END AS delayeddays, " + //
				"kpit.description AS kpivevtypename, " + //
				"kpid.iteminternalcode AS iteminternalcode, " + //
				"kpid.itemname AS itemname, " + //
				"kpid.detailunits AS requestedunits, " + //
				"kpid.detailtodeliveryunits AS todeliveryunits, " + //
				"kpid.detailreceivedunits AS receivedunits, " + //
				"kpid.detailpendingunits AS pendingunits " + //
			"FROM " + //
				"kpid JOIN " + //
				"logistica.vendor AS vd ON vd.id = kpid.vendor_id JOIN " + //
				"logistica.department AS dp ON dp.id = kpid.department_id JOIN " + //
				"logistica.kpivevpdtype AS kpit ON kpit.id = kpid.kpivevpdtype_id LEFT JOIN " + //
				"logistica.location AS ss ON ss.id = kpid.salestore_id " + //
			getOrderByString(mapKPIvevPDOrderDetailSQL, orderCriteria); //
			
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
						
		query.setResultTransformer(new LowerCaseResultTransformer(KPIvevOrderDetailDataDTO.class));
		
		List list = query.list();
		
		return (KPIvevOrderDetailDataDTO[]) list.toArray(new KPIvevOrderDetailDataDTO[list.size()]);		
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
}