package bbr.b2b.regional.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.buyorders.report.classes.CSVOrderVeVReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DoChangeOrderStateInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DoCloseOrderInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.DownloadOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderIdsByPagesW;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.PDFVeVPDOrderDetailReportResultListResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDExcelOrderReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderDetailReportResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportInitParamDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVPDOrderReportResultDTO;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataArrayResultDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDDataChangeInitParamDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDMassDataChangeArrayDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.VeVPDUnitaryDataChangeInitParamDTO;

public interface IDirectOrderReportManager {
	
	DirectOrderW doCalculateTotalOfDirectOrder(Long orderid) throws NotFoundException, AccessDeniedException, OperationFailedException;
	DirectOrderW[] doAcceptDirectOrders(Long... orderids) throws AccessDeniedException, OperationFailedException, NotFoundException;
			
	FileDownloadInfoResultDTO getCSVDirectOrderVeVReport(CSVOrderVeVReportInitParamDTO initParamDTO, Long userId);
	FileDownloadInfoResultDTO getCSVDirectOrderVeVCourierReport(CSVOrderVeVReportInitParamDTO initParamDTO, Long userId);
	
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndShowableOrderStateType(VeVPDOrderReportInitParamDTO initParams, boolean byFilter) ;
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndOrderStateType(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndRequestNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndClientRut(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndCommittedDate(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
	VeVPDOrderDetailReportResultDTO getVeVPDOrdersDetailByOrder(VeVPDOrderDetailReportInitParamDTO initParams, boolean providerUser, boolean byFilter);
	
	VeVPDOrderReportResultDTO doChangeDirectOrderState(DoChangeOrderStateInitParamDTO initParams);
	VeVPDExcelOrderReportResultDTO getExcelVeVPDOrdersReportByOrders(DownloadOrderReportInitParamDTO initParamW, boolean providerUser, boolean byPages);
	OrderIdsByPagesW getExcelVeVPDOrdersReportByPages(VeVPDOrderReportInitParamDTO initParamDTO, PageRangeDTO[] pageranges);
	
	BaseResultDTO doCloseDirectOrder(DoCloseOrderInitParamDTO initParams);
	
	VeVPDDataArrayResultDTO doValidateVeVPDUnitaryDataChange(VeVPDUnitaryDataChangeInitParamDTO initparams);
	VeVPDDataArrayResultDTO doValidateVeVPDMassDataChange(VeVPDMassDataChangeArrayDTO initparams);
	VeVPDDataArrayResultDTO doChangeVeVPDData(VeVPDDataChangeInitParamDTO initparams);
	
	PDFVeVPDOrderDetailReportResultDTO getPDFVeVPDOrdersDetailByOrder(VeVPDOrderDetailReportInitParamDTO initParams);	
	PDFVeVPDOrderDetailReportResultListResultDTO getPDFVeVPDOrdersDetailByOrders(VeVPDOrderDetailReportInitParamDTO initParams);	
	VeVPDOrderReportResultDTO getVeVPDOrdersByVendorAndSendNumber(VeVPDOrderReportInitParamDTO initParams, boolean byFilter);
}

