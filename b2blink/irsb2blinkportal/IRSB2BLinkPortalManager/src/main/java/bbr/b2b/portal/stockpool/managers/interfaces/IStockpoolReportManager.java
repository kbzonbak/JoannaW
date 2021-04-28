package bbr.b2b.portal.stockpool.managers.interfaces;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportResultDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportResultDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockDetailInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportResultDTO;
import bbr.b2b.logistic.report.data.dto.BuyerFilterDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ClientsFilterInitParamDTO;
import bbr.b2b.logistic.report.data.dto.DeletePendingItemsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.DisableAlertInitParamDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsResultDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationInitParamDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationResultDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ResendTxInitParamDTO;
import bbr.b2b.logistic.report.data.dto.StockDetailResultDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemInitParamDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IStockpoolReportManager extends IGenericEJBInterface{


	public ClientAvailabilityReportResultDTO getClientAvailabilityWS(ClientAvailabilityInitParamDTO  initParamDTO, Long uskey);
	public StockDetailResultDTO getTransactionDetailsWS(AvailableStockDetailInitParamDTO initParamDTO , Long uskey);
	public IndicatorsResultDTO getIndicatorsWS(IndicatorsInitParamDTO initparamDTO, Long uskey);
	public BaseResultsDTO doDisableAlertWS(DisableAlertInitParamDTO initParamDTO, Long uskey);
	public NotificationReportResultDTO getNotificationReportWS(NotificationReportInitParamDTO initParamDTO, Long uskey);
	public UpdateItemResultDTO doUpdateItem(UpdateItemInitParamDTO initParamDTO, Long uskey);
	public BuyerFilterDTO doUpdateItem(ClientsFilterInitParamDTO initParamDTO, Long uskey);
	public ProductNotificationReportResultDTO getProductNotificationReportWS(ProductNotificationReportInitParamDTO initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO downloadItemNotificationReport(ProductNotificationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	public FileDownloadInfoResultDTO downloadNotificationReport(NotificationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	
	
	//Available
	public AvailableStockReportResultDTO getAvailableStockByVendorWS(AvailableStockInitParamDTO initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO downloadAvailableStockReport(AvailableStockInitParamDTO initParam, String selectedFormat, Long userId, Locale locale);
	public FileDownloadInfoResultDTO downloadAvailableStockDetails(AvailableStockDetailInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	
	//alertas
	public AlertReportResultDTO  getAlertReportWS(AlertReportInitParamDTO  initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO getExcelAlertReportWS(AlertReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	
	//Disponibilidad
	public AvailabilityReportResultDTO getAvailabilityReportWS(AvailabilityReportInitParamDTO  initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO getExcelAvailabilityReportWS(AvailabilityReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	
	//Sin Homologacion
	public PendingHomologationResultDTO getPendingHomologationReportWS(PendingHomologationInitParamDTO initParamDTO, Long uskey);
	public BaseResultsDTO reSendMessageWS(ResendTxInitParamDTO initParamDTO, Long uskey);
	public BaseResultsDTO doDeletePendingItemsWS(DeletePendingItemsInitParamDTO initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO getExcelPendingHomologationReportWS(PendingHomologationInitParamDTO pendingHomologationinitParamDTO, String value, Long id, Locale locale);
	
	//Homologacion
	public HomologationReportResultDTO getHomologationReport(HomologationReportInitParamDTO initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO downloadHomologationReport(HomologationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
	
	//detale productos
	public ProductDetailReportResultDTO getProductDetailReportWS(ProductDetailReportInitParamDTO  initParamDTO, Long uskey);
	public FileDownloadInfoResultDTO getExcelProductDetailReportWS(ProductDetailReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale);
}
