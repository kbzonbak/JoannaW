package bbr.b2b.regional.logistic.fillrate.classes;

import java.math.BigInteger;
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
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateW;
import bbr.b2b.regional.logistic.fillrate.entities.FillRate;
import bbr.b2b.regional.logistic.fillrate.entities.FillRatePeriod;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateEvolutionReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateReportDataDTO;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/fillrate/FillRateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FillRateServer extends LogisticElementServer<FillRate, FillRateW> implements FillRateServerLocal {

	private Map<String, String> mapGetFillRatesByFillRatePeriodSQL = new HashMap<String, String>();
	{
		mapGetFillRatesByFillRatePeriodSQL.put("VENDORRUT", "VD.RUT");
		mapGetFillRatesByFillRatePeriodSQL.put("VENDORNAME", "VD.NAME");
		mapGetFillRatesByFillRatePeriodSQL.put("DEPARTMENTNAME", "DP.NAME");
		mapGetFillRatesByFillRatePeriodSQL.put("NEEDEDAMOUNT", "FR.TOTALNEED");
		mapGetFillRatesByFillRatePeriodSQL.put("RECEIVEDAMOUNT", "FR.TOTALRECEIVED");
		mapGetFillRatesByFillRatePeriodSQL.put("NEEDEDUNITS", "FR.TOTALUNITS");
		mapGetFillRatesByFillRatePeriodSQL.put("RECEIVEDUNITS", "FR.RECEIVEDUNITS");
		mapGetFillRatesByFillRatePeriodSQL.put("AMOUNTFILLRATE", "AMOUNTFILLRATE");
		mapGetFillRatesByFillRatePeriodSQL.put("UNITSFILLRATE", "UNITSFILLRATE");
	}		
	
	private Map<String, String> mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL = new HashMap<String, String>();
	{
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("YEAR", "YEAR");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("MONTH", "MONTH");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("NEEDEDAMOUNT", "FR.TOTALNEED");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("RECEIVEDAMOUNT", "FR.TOTALRECEIVED");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("NEEDEDUNITS", "FR.TOTALUNITS");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("RECEIVEDUNITS", "FR.RECEIVEDUNITS");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("AMOUNTFILLRATE", "AMOUNTFILLRATE");
		mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL.put("UNITSFILLRATE", "UNITSFILLRATE");
	}		
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	FillRatePeriodServerLocal fillrateperiodserver;

	@EJB
	DepartmentServerLocal departmentserver;

	public FillRateW addFillRate(FillRateW fillrate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateW) addElement(fillrate);
	}
	public FillRateW[] getFillRates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateW[]) getIdentifiables();
	}
	public FillRateW updateFillRate(FillRateW fillrate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateW) updateElement(fillrate);
	}

	public FillRateReportDataDTO[] getFillRatesReportByFillRatePeriod(Long fillrateperiodid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getFillRatesByFillRatePeriodQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fillrateperiodid", fillrateperiodid);
		query.setResultTransformer(new LowerCaseResultTransformer(FillRateReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		FillRateReportDataDTO[] result = (FillRateReportDataDTO[]) list.toArray(new FillRateReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getFillRatesCountByFillRatePeriod(Long fillrateperiodid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getFillRatesByFillRatePeriodQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fillrateperiodid", fillrateperiodid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}
	
	public FillRateEvolutionReportDataDTO[] getFillRatesReportByVendorAndDepartmentAndLatestPeriods(Long vendorid, Long departmentid, int latestperiods, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getFillRatesByVendorAndDepartmentAndLatestPeriodsQueryString(latestperiods, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setLong("departmentid", departmentid);
		query.setResultTransformer(new LowerCaseResultTransformer(FillRateEvolutionReportDataDTO.class));
		
		List list = query.list();
		
		FillRateEvolutionReportDataDTO[] result = (FillRateEvolutionReportDataDTO[]) list.toArray(new FillRateEvolutionReportDataDTO[list.size()]);		
		return result;	
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(FillRate entity, FillRateW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setFillrateperiodid(entity.getFillrateperiod() != null ? new Long(entity.getFillrateperiod().getId()) : new Long(0));
		wrapper.setDepartmentid(entity.getDepartment() != null ? new Long(entity.getDepartment().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(FillRate entity, FillRateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getFillrateperiodid() != null && wrapper.getFillrateperiodid() > 0) { 
			FillRatePeriod fillrateperiod = fillrateperiodserver.getReferenceById(wrapper.getFillrateperiodid());
			if (fillrateperiod != null) { 
				entity.setFillrateperiod(fillrateperiod);
			}
		}
		if (wrapper.getDepartmentid() != null && wrapper.getDepartmentid() > 0) { 
			Department department = departmentserver.getReferenceById(wrapper.getDepartmentid());
			if (department != null) { 
				entity.setDepartment(department);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "FillRate";
	}
	@Override
	protected void setEntityname() {
		entityname = "FillRate";
	}
	
	private String getFillRatesByFillRatePeriodQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL = "";
		String condicionOrderBy		= "";		

		if (queryType == 1){
			SQL = "SELECT " +
					"COUNT(*) ";			
		}
		else {
			SQL = "SELECT " +
					"VD.ID AS VENDORID, " +
					"VD.RUT AS VENDORRUT, " +
					"VD.NAME AS VENDORNAME, " +
					"DP.ID AS DEPARTMENTID, " +
					"DP.NAME AS DEPARTMENTNAME, " +
					"FR.TOTALNEED AS NEEDEDAMOUNT, " +
					"FR.TOTALRECEIVED AS RECEIVEDAMOUNT, " +
					"FR.TOTALUNITS AS NEEDEDUNITS, " +
					"FR.RECEIVEDUNITS AS RECEIVEDUNITS, " +
					"FR.ID AS FILLRATEID, " +
					"((FR.TOTALRECEIVED / FR.TOTALNEED) * 100) AS AMOUNTFILLRATE, " +
					"((FR.RECEIVEDUNITS / FR.TOTALUNITS) * 100) AS UNITSFILLRATE ";

			condicionOrderBy = getFillRateByString(mapGetFillRatesByFillRatePeriodSQL, orderby);
		}

		String fromSQL = "FROM " +
							"LOGISTICA.FILLRATE FR JOIN " +
							"LOGISTICA.VENDOR VD ON VD.ID = FR.VENDOR_ID JOIN " +
							"LOGISTICA.DEPARTMENT DP ON DP.ID = FR.DEPARTMENT_ID ";

		String whereSQL = "WHERE " +
							"FR.FILLRATEPERIOD_ID = :fillrateperiodid ";

		SQL = SQL + 
				fromSQL +
					whereSQL +
						condicionOrderBy;		

		return SQL;	

	}
	
	private String getFillRatesByVendorAndDepartmentAndLatestPeriodsQueryString(int latestperiods, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL 					= "";
		String condicionOrderBy		= "";		
				
		SQL = "SELECT " +
				"int4(date_part('YEAR', FRP.SINCE)) AS YEAR, " +
				"LOGISTICA.MONTH(FRP.SINCE) AS MONTH, " +
				"FR.TOTALNEED AS NEEDEDAMOUNT, " +
				"FR.TOTALRECEIVED AS RECEIVEDAMOUNT, " +
				"FR.TOTALUNITS AS NEEDEDUNITS, " +
				"FR.RECEIVEDUNITS AS RECEIVEDUNITS, " +
				"((FR.TOTALRECEIVED / FR.TOTALNEED) * 100) AS AMOUNTFILLRATE, " +
				"((FR.RECEIVEDUNITS / FR.TOTALUNITS) * 100) AS UNITSFILLRATE " +
			"FROM " +
				"LOGISTICA.FILLRATE FR JOIN " +
				"LOGISTICA.FILLRATEPERIOD FRP ON FRP.ID = FR.FILLRATEPERIOD_ID " +
				"WHERE FR.VENDOR_ID = :vendorid AND FR.DEPARTMENT_ID = :departmentid AND " +
					"FR.FILLRATEPERIOD_ID IN (SELECT FRP.ID " +
												"FROM " +
													"LOGISTICA.FILLRATEPERIOD FRP " +
												"ORDER BY FRP.ID DESC FETCH FIRST " + String.valueOf(latestperiods) + " ROWS ONLY) ";
		
		condicionOrderBy = getFillRateByString(mapGetFillRatesByVendorAndDepartmentAndLatestPeriodsSQL, orderby);				
		
		SQL = SQL +
				condicionOrderBy;		
		
		return SQL;		
	}
		
	private String getFillRateByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
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
