package bbr.b2b.regional.logistic.kpi.interfaces;

import java.util.Date;
import java.util.List;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPD;

public interface IKPIvevPDServer extends IElementServer<KPIvevPD, KPIvevPDW>{
	
	public KPIvevPDW addKPIvevPD(KPIvevPDW kpivevpd) throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDW[] getKPIvevPDs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	public KPIvevPDW updateKPIvevPD(KPIvevPDW kpivevpd) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public Long[] getAllVendorIds();
	public Long[] getAllDepartmentIds();
	public void deleteByIds(List<Long> kpivevpdids);
	public void deleteByVendorAndPeriod(Long vendorid, Date since, Date until, boolean courier) throws OperationFailedException;
	public KPIvevPDReportDataDTO getInt1879KPIGeneralData(Long vendorid, Long departmentid, Long salestoreid, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	public KPIvevPDReportDataDTO[] getKPIGeneralData(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	public KPIvevPDComplianceDTO countKPIGeneralData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException;
	public KPIvevPDReportDataDTO[] getKPIGeneralDataTotal(Float meta, Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean isPaginated) throws OperationFailedException, AccessDeniedException;
	public KPIvevPDSummaryDTO[] getKPIvevPDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException;
	public KPIvevPDSummaryTotalDTO countKPIvevPDSummary(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException;
}
