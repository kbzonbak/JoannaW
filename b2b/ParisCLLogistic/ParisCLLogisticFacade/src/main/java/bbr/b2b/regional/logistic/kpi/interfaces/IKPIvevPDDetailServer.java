package bbr.b2b.regional.logistic.kpi.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDDetailW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevPDDetail;

public interface IKPIvevPDDetailServer extends IElementServer<KPIvevPDDetail, KPIvevPDDetailW>{
	
	KPIvevPDDetailW addKPIvevPDDetail(KPIvevPDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevPDDetailW[] getKPIvevPDDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevPDDetailW updateKPIvevPDDetail(KPIvevPDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	void deleteByVendorAndPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException;
	KPIvevPDDetailW[] getDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException;
	int getCountDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until, Boolean courier) throws OperationFailedException;
	KPIvevPDDetailReportDTO[] getKPIvevPDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countKPIvevPDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	KPIvevPDSummaryDetailReportDTO[] getKPIvevPDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countKPIvevPDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, boolean courier) throws OperationFailedException, AccessDeniedException;
	FileDownloadInfoResultDTO getKPIvevPDDetailDataSourceReport(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, String vendorname, Long userKey, boolean courier) throws OperationFailedException;
	KPIvevVendorDimensionDTO[] getVendorsByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier) throws OperationFailedException, AccessDeniedException;
	KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, boolean courier) throws OperationFailedException;
	KPIvevFineDataDTO[] getFineDataByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier) throws OperationFailedException;
	KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevPDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, boolean courier, OrderCriteriaDTO[] orderCriteria) throws OperationFailedException, AccessDeniedException;
	FileDownloadInfoResultDTO getExcelKPIvevPDFineDataReport(Long vendorid, Long[] departmentids, Integer month, Integer year, Double firstdayfine, Double nextdaysfine, boolean courier, Long userKey) throws OperationFailedException, AccessDeniedException;
}
