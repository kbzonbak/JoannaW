package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIResetInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPISummaryDetailInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPISummaryInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIdetailInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIgeneralInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDTypeArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevComplianceInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDTypeArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.SaleStoreArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorsLogArrayResultDTO;

public interface IKPIReportManager {
	
	SaleStoreArrayResultDTO getSaleStores();
	SaleStoreArrayResultDTO findSaleStoresByCode(String code);
	SaleStoreArrayResultDTO findSaleStoresByName(String name);
	
	// CD
	VendorsLogArrayResultDTO getKPIvevCDVendors();
	boolean doSetKPIvevCDData(String vendorcode, Date since, Date until);
	BaseResultDTO doResetKPIvevCDData(KPIResetInitParamDTO initParamDTO);
	KPIvevCDTypeArrayResultDTO getKPIvevCDTypes();
	KPIvevCDReportArrayResultDTO getKPIvevCDDataForReport(KPIgeneralInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);	
	KPIvevCDDetailReportArrayResultDTO getKPIvevCDDetailData(KPIdetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);
	KPIvevCDSummaryArrayResultDTO getKPIvevCDSummaryForReport(KPISummaryInitParamDTO initParamDTO);
	KPIvevCDSummaryDetailArrayResultDTO getKPIvevCDSummaryDetailData(KPISummaryDetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);
	FileDownloadInfoResultDTO getKPIvevCDDetailDataSourceReport(KPIdetailInitParamDTO initParamDTO, Long userKey);
		
	// PD
	VendorsLogArrayResultDTO getKPIvevPDVendors();
	boolean doSetKPIvevPDData(String vendorcode, Date since, Date until, Boolean courier);
	BaseResultDTO doResetKPIvevPDData(KPIResetInitParamDTO initParamDTO);
	KPIvevPDTypeArrayResultDTO getKPIvevPDTypes();
	KPIvevPDReportArrayResultDTO getKPIvevPDDataForReport(KPIgeneralInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);	
	KPIvevPDDetailReportArrayResultDTO getKPIvevPDDetailData(KPIdetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);
	KPIvevPDSummaryArrayResultDTO getKPIvevPDSummaryForReport(KPISummaryInitParamDTO initParamDTO);
	KPIvevPDSummaryDetailArrayResultDTO getKPIvevPDSummaryDetailData(KPISummaryDetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated);
	FileDownloadInfoResultDTO getKPIvevPDDetailDataSourceReport(KPIdetailInitParamDTO initParamDTO, Long userKey);
		
	// Multas por Incumplimiento de entrega
	boolean doSetKPIvevComplianceData();
	boolean doSetKPIvevCDComplianceData(Date since, Date until);
	boolean doSetKPIvevPDComplianceData(Date since, Date until);
	BaseResultDTO doResetKPIvevCDComplianceData(KPIvevComplianceInitParamDTO initParamDTO);
	BaseResultDTO doResetKPIvevPDComplianceData(KPIvevComplianceInitParamDTO initParamDTO);
	KPIvevFineReportArrayResultDTO getKPIvevCDFineDataForReport(KPIvevFineInitParamDTO initParamDTO);
	KPIvevFineReportArrayResultDTO getKPIvevPDFineDataForReport(KPIvevFineInitParamDTO initParamDTO);
	KPIvevOrderDetailReportArrayResultDTO getKPIvevCDOrderDetailDataForReport(KPIvevFineInitParamDTO initParamDTO);
	KPIvevOrderDetailReportArrayResultDTO getKPIvevPDOrderDetailDataForReport(KPIvevFineInitParamDTO initParamDTO);
	FileDownloadInfoResultDTO getExcelKPIvevCDFineDataReport(KPIvevFineInitParamDTO initParamDTO, Long userKey);
	FileDownloadInfoResultDTO getExcelKPIvevPDFineDataReport(KPIvevFineInitParamDTO initParamDTO, Long userKey);
//	KPIvevComplianceFactorArrayResultDTO getKPIvevComplianceFactors();
//	BaseResultDTO doUpdateKPIvevComplianceFactors(KPIvevComplianceFactorInitParamDTO initParamDTO);	
}
