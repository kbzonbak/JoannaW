package bbr.b2b.regional.logistic.kpi.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDDetailW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevCDDetail;

public interface IKPIvevCDDetailServer extends IElementServer<KPIvevCDDetail, KPIvevCDDetailW>{
	
	KPIvevCDDetailW addKPIvevCDDetail(KPIvevCDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevCDDetailW[] getKPIvevCDDetails() throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevCDDetailW updateKPIvevCDDetail(KPIvevCDDetailW kpivevcddetail) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	void deleteByVendorAndPeriod(Long vendorid, Date since, Date until) throws OperationFailedException;
	KPIvevCDDetailW[] getDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until) throws OperationFailedException;
	int getCountDataToCalculateKPIByPeriod(Long vendorid, Date since, Date until) throws OperationFailedException;
	KPIvevCDDetailReportDTO[] getKPIvevCDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countKPIvevCDDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	KPIvevCDSummaryDetailReportDTO[] getKPIvevCDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countKPIvevCDSummaryDetailData(Long vendorid, Long[] departmentids, Long[] salestoreids, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	FileDownloadInfoResultDTO getKPIvevCDDetailDataSourceReport(Long vendorid, Long[] departmentids, Long[] salestoreids, Long[] kpitypeids, Date since, Date until, String vendorname, Long userKey) throws OperationFailedException;
	KPIvevVendorDimensionDTO[] getVendorsByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine) throws OperationFailedException, AccessDeniedException;;
	KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until) throws OperationFailedException;
	KPIvevFineDataDTO[] getFineDataByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine) throws OperationFailedException;
	KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevCDFinePeriod(Long vendorid, Long[] departmentids, Date since, Date until, Double firstdayfine, Double nextdaysfine, OrderCriteriaDTO[] orderCriteria) throws OperationFailedException, AccessDeniedException;
	FileDownloadInfoResultDTO getExcelKPIvevCDFineDataReport(Long vendorid, Long[] departmentids, Integer month, Integer year, Double firstdayfine, Double nextdaysfine, Long userKey) throws OperationFailedException, AccessDeniedException;
}
