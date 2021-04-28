package bbr.b2b.regional.logistic.kpi.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCD;

public interface IKPIvevCDServer extends IElementServer<KPIvevCD, KPIvevCDW>{
	
	public KPIvevCDW addKPIvevCD(KPIvevCDW kpivevcd) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDW[] getKPIvevCDs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevCDW updateKPIvevCD(KPIvevCDW kpivevcd) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public Long[] getAllVendorIds();
	public Long[] getAllDepartmentIds();
	public void deleteByIds(List<Long> kpivevcdids);
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until) throws OperationFailedException;
	public KPIvevCDReportDataDTO[] getKPIGeneralData(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	public KPIvevCDComplianceDTO countKPIGeneralData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	public KPIvevCDReportDataDTO[] getKPIGeneralDataTotal(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	public KPIvevCDSummaryDTO[] getKPIvevCDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException;
	public KPIvevCDSummaryTotalDTO countKPIvevCDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException;
}
