package bbr.b2b.portal.stockpool.managers.classes;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AlertReportResultDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailabilityReportResultDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockDetailInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockInitParamDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportDTO;
import bbr.b2b.logistic.report.data.dto.AvailableStockReportResultDTO;
import bbr.b2b.logistic.report.data.dto.BuyerFilterDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ClientAvailabilityReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ClientsFilterInitParamDTO;
import bbr.b2b.logistic.report.data.dto.DeletePendingItemsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.DisableAlertInitParamDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.HomologationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsInitParamDTO;
import bbr.b2b.logistic.report.data.dto.IndicatorsResultDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.NotificationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationInitParamDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationReportDTO;
import bbr.b2b.logistic.report.data.dto.PendingHomologationResultDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductDetailReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportInitParamDTO;
import bbr.b2b.logistic.report.data.dto.ProductNotificationReportResultDTO;
import bbr.b2b.logistic.report.data.dto.ResendTxInitParamDTO;
import bbr.b2b.logistic.report.data.dto.StockDetailResultDTO;
import bbr.b2b.logistic.report.data.dto.TransactionDetailsDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemInitParamDTO;
import bbr.b2b.logistic.report.data.dto.UpdateItemResultDTO;
import bbr.b2b.logistic.rest.client.IRSB2BStockPoolRestFulWSClient;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.FileHandlerUtils;
import bbr.b2b.portal.utils.I18NManager;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;

@Stateless(name = "managers/StockpoolReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StockpoolReportManager implements StockpoolReportManagerLocal
{

	@EJB
	private UserReportManagerLocal userReportManager;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	
	public AvailableStockReportResultDTO getAvailableStockByVendorWS(AvailableStockInitParamDTO initParamDTO, Long uskey)
	{

		AvailableStockReportResultDTO result = null;

		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());

			result = IRSB2BStockPoolRestFulWSClient.getInstance().getAvailableStockByVendorWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new AvailableStockReportResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}

	public StockDetailResultDTO getTransactionDetailsWS(AvailableStockDetailInitParamDTO initParamDTO , Long uskey)
	{
		StockDetailResultDTO result = null;
		try
		{
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getTransactionDetailsWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new StockDetailResultDTO();
			result.setStatuscode("P600");
		}

		return result;

	}
	
	public PendingHomologationResultDTO getPendingHomologationReportWS(PendingHomologationInitParamDTO initParamDTO, Long uskey) {
		PendingHomologationResultDTO result = null;
		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getPendingHomologationReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new PendingHomologationResultDTO();
			result.setStatuscode("P600");
		}

		return result;
		
	}

	public AlertReportResultDTO  getAlertReportWS(AlertReportInitParamDTO  initParamDTO, Long uskey) {
		AlertReportResultDTO  result = null;
		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getAlertReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new AlertReportResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public IndicatorsResultDTO getIndicatorsWS(IndicatorsInitParamDTO initparamDTO, Long uskey) {
		IndicatorsResultDTO result = null;
		try
		{
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getIndicatorsWS(initparamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new IndicatorsResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public BaseResultsDTO reSendMessageWS(ResendTxInitParamDTO initParamDTO, Long uskey) {
		BaseResultsDTO result = null;
		try
		{
			
			result = IRSB2BStockPoolRestFulWSClient.getInstance().reSendMessageWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new BaseResultsDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public BaseResultsDTO doDisableAlertWS(DisableAlertInitParamDTO initParamDTO, Long uskey) {
		BaseResultsDTO result = null;
		try
		{
			result = IRSB2BStockPoolRestFulWSClient.getInstance().doDisableAlertWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new BaseResultsDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	
	public BaseResultsDTO doDeletePendingItemsWS(DeletePendingItemsInitParamDTO initParamDTO, Long uskey) {
		BaseResultsDTO result = null;
		try
		{
			result = IRSB2BStockPoolRestFulWSClient.getInstance().doDeletePendingItemsWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new BaseResultsDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public NotificationReportResultDTO getNotificationReportWS(NotificationReportInitParamDTO initParamDTO, Long uskey) {
		NotificationReportResultDTO result = null;
		try
		{
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getNotificationReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new NotificationReportResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}

	public FileDownloadInfoResultDTO downloadAvailableStockReport(AvailableStockInitParamDTO initParam, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AvailableStockReportResultDTO availableStockData = null;
	
		try {
			availableStockData = IRSB2BStockPoolRestFulWSClient.getInstance().getAvailableStockByVendorWS(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(availableStockData != null && !availableStockData.getStatuscode().equals("0")){
			resultDTO.setStatuscode(availableStockData.getStatuscode());
			resultDTO.setStatusmessage(availableStockData.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "availability_details", locale);
		String realname = "REPORTE_DISPONIBILIDAD";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "availability", locale));

		DataColumn col0 = new DataColumn("sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku", locale), String.class);
		DataColumn col1 = new DataColumn("description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description", locale), String.class);
		DataColumn col2 = new DataColumn("available_stock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_available_stock_excel", locale), Integer.class);
		DataColumn col3 = new DataColumn("available_critical_stock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_available_critical_stock_excel", locale), Integer.class);
		DataColumn col4 = new DataColumn("variation", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_variation", locale), Integer.class);
		DataColumn col7 = new DataColumn("last_uploaded_stock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_last_uploaded_stock_excel", locale), Integer.class);
		DataColumn col6 = new DataColumn("update_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_update_date_excel", locale), String.class);
		DataColumn col8 = new DataColumn("sales", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sales_excel", locale), Integer.class);
		DataColumn col5 = new DataColumn("last_notification", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_last_notification_excel", locale), String.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col6);
		dt0.addColumn(col7);
		dt0.addColumn(col8);
		dt0.addColumn(col5);

		DataRow row = null;
		if( availableStockData != null && availableStockData.getAvailablestock()!= null && availableStockData.getAvailablestock().length > 0){
			for (AvailableStockReportDTO availableStock : availableStockData.getAvailablestock()) {
				row = dt0.newRow(); 
				row.setCellValue("sku", availableStock.getSku());
				row.setCellValue("description", availableStock.getDescription());
				row.setCellValue("available_stock", availableStock.getAvailablestock());
				row.setCellValue("available_critical_stock", availableStock.getCriticalstock());
				row.setCellValue("variation", availableStock.getThreshold());
				row.setCellValue("update_date", availableStock.getUpdatedate());
				row.setCellValue("last_uploaded_stock", availableStock.getLastuploadedstock());
				row.setCellValue("sales", availableStock.getSales());
				row.setCellValue("last_notification", availableStock.getNotificationdate());
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadAvailableStockDetails(AvailableStockDetailInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		StockDetailResultDTO stockDetailsData = null;
		try {
			stockDetailsData = IRSB2BStockPoolRestFulWSClient.getInstance().getTransactionDetailsWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(stockDetailsData != null && !stockDetailsData.getStatuscode().equals("0")){
			resultDTO.setStatuscode(stockDetailsData.getStatuscode());
			resultDTO.setStatusmessage(stockDetailsData.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "transaction_details", locale);
		String realname = "DETALLE_TRANSACCIONES";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "transaction", locale));

		DataColumn col0 = new DataColumn("txdate", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_txdate", locale), String.class);
		DataColumn col1 = new DataColumn("type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_type", locale), String.class);
		DataColumn col2 = new DataColumn("client", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client", locale), String.class);
		DataColumn col3 = new DataColumn("oc_number_short", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_number_short", locale), Long.class);
		DataColumn col4 = new DataColumn("quantity", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity", locale), Integer.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		DataRow row = null;
		if( stockDetailsData != null && stockDetailsData.getProductdetail()!= null && stockDetailsData.getProductdetail().length > 0){
			for (TransactionDetailsDTO availableStock : stockDetailsData.getProductdetail()) {
				row = dt0.newRow(); 
				row.setCellValue("txdate",  availableStock.getTxdate());
				row.setCellValue("type", availableStock.getTxtype());
				row.setCellValue("client", availableStock.getClientname());
				row.setCellValue("oc_number_short", availableStock.getOrdernumber());
				row.setCellValue("quantity", availableStock.getQuantity());
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadNotificationReport(NotificationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		NotificationReportResultDTO notificationReport = null;
		try {
			notificationReport = IRSB2BStockPoolRestFulWSClient.getInstance().getNotificationReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(notificationReport != null && !notificationReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(notificationReport.getStatuscode());
			resultDTO.setStatusmessage(notificationReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "details_notifications", locale);
		String realname = "DETALLE_NOTIFICACIONES";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "details", locale));

		DataColumn col0 = new DataColumn("sku_retails", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_retails", locale), String.class);
		DataColumn col1 = new DataColumn("sku_proveedor", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_proveedor", locale), String.class);
		DataColumn col2 = new DataColumn("description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description", locale), String.class);
		DataColumn col3 = new DataColumn("quantity", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity", locale), Integer.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		
		DataRow row = null;
		if( notificationReport != null && notificationReport.getNotificationreport()!= null && notificationReport.getNotificationreport().length > 0){
			for (NotificationReportDTO notification : notificationReport.getNotificationreport()) {
				row = dt0.newRow(); 
				row.setCellValue("sku_retails",  notification.getSkubuyer());
				row.setCellValue("sku_proveedor", notification.getSkuvendor());
				row.setCellValue("description", notification.getDescription());
				row.setCellValue("quantity", notification.getQuantity());
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO downloadHomologationReport(HomologationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		HomologationReportResultDTO homologationReport = null;
		try {
			homologationReport = IRSB2BStockPoolRestFulWSClient.getInstance().getHomologationReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(homologationReport != null && !homologationReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(homologationReport.getStatuscode());
			resultDTO.setStatusmessage(homologationReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "homologation_report", locale);
		String realname = "Reporte_Homologación";

//		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "details", locale));

		DataColumn col0 = new DataColumn("sku_vendor", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_proveedor", locale), String.class);
		DataColumn col1 = new DataColumn("vendoritem_description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_description", locale), String.class);
		DataColumn col2 = new DataColumn("activevev", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_activevev", locale), String.class);
		DataColumn col3 = new DataColumn("buyername", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client", locale), String.class);
		DataColumn col4 = new DataColumn("sku_buyer", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer", locale), String.class);
		DataColumn col5 = new DataColumn("enabledspl", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_enabledspl", locale), String.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		
		DataRow row = null;
		if( homologationReport != null && homologationReport.getHomologationreport()!= null && homologationReport.getHomologationreport().length > 0){
			for (HomologationReportDTO homologation : homologationReport.getHomologationreport()) {
				row = dt0.newRow(); 
				row.setCellValue("sku_vendor",  homologation.getSkuvendor());
				row.setCellValue("vendoritem_description", homologation.getVendoritemdescription());
				row.setCellValue("activevev", homologation.isActive() ? "SI" : "NO");
				row.setCellValue("buyername",  homologation.getBuyername());
				row.setCellValue("sku_buyer", homologation.getSkubuyer());
				row.setCellValue("enabledspl", homologation.isEnabledspl() ? "SI" : "NO");
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public HomologationReportResultDTO getHomologationReport(HomologationReportInitParamDTO initParamDTO, Long uskey)
	{
		HomologationReportResultDTO result = null;

		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());

			result = IRSB2BStockPoolRestFulWSClient.getInstance().getHomologationReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new HomologationReportResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public UpdateItemResultDTO doUpdateItem(UpdateItemInitParamDTO initParamDTO, Long uskey)
	{
		UpdateItemResultDTO result = null;

		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());

			result = IRSB2BStockPoolRestFulWSClient.getInstance().doUpdateItemWS(initParamDTO);
			//result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new UpdateItemResultDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	
	public BuyerFilterDTO doUpdateItem(ClientsFilterInitParamDTO initParamDTO, Long uskey)
	{
		BuyerFilterDTO result = null;

		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());

			result = IRSB2BStockPoolRestFulWSClient.getInstance().getClientsByVendorCodeWS(initParamDTO);
			//result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new BuyerFilterDTO();
			result.setStatuscode("P600");
		}

		return result;
	}
	
	public ProductNotificationReportResultDTO getProductNotificationReportWS(ProductNotificationReportInitParamDTO initParamDTO, Long uskey)
	{
		ProductNotificationReportResultDTO result = null;
		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getProductNotificationReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new ProductNotificationReportResultDTO();
			result.setStatuscode("P600");
		}
		return result;
	}
	
	public FileDownloadInfoResultDTO downloadItemNotificationReport(ProductNotificationReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		ProductNotificationReportResultDTO itemNotificationReport = null;	
		try {
			itemNotificationReport = IRSB2BStockPoolRestFulWSClient.getInstance().getProductNotificationReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(itemNotificationReport != null && !itemNotificationReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(itemNotificationReport.getStatuscode());
			resultDTO.setStatusmessage(itemNotificationReport.getStatusmessage());
			return resultDTO;
		}
		
		
	

//		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "details", locale));

		DataColumn col0 = new DataColumn("notif_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_date", locale), String.class);
		DataColumn col1 = new DataColumn("notificationid", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_notifid", locale), Long.class);
		DataColumn col2 = new DataColumn("clientname", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_client", locale), String.class);
		DataColumn col3 = new DataColumn("sku_buyer", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer", locale), String.class);
		DataColumn col4 = new DataColumn("quantity", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity", locale), Integer.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		
		DataRow row = null;
		if( itemNotificationReport != null && itemNotificationReport.getProductreport()!= null && itemNotificationReport.getProductreport().length > 0){
			for (ProductNotificationReportDTO notification : itemNotificationReport.getProductreport()) {
				row = dt0.newRow(); 
				row.setCellValue("notif_date",  notification.getNotifdate());
				row.setCellValue("notificationid", notification.getNotificationid());
				row.setCellValue("clientname", notification.getClientname());
				row.setCellValue("sku_buyer",  notification.getSkubuyer());
				row.setCellValue("quantity", notification.getNotifquantity());
				dt0.addRow(row);
			}
		}
		String downloadname =   "Reporte_notificaciones_"+itemNotificationReport.getProductreport()[0].getSkuvendor();
		String realname = "Reporte_notificaciones_"+itemNotificationReport.getProductreport()[0].getSkuvendor();

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO getExcelAlertReportWS(AlertReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AlertReportResultDTO alertResultReport = null;
		try {
			alertResultReport = IRSB2BStockPoolRestFulWSClient.getInstance().getAlertReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(alertResultReport != null && !alertResultReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(alertResultReport.getStatuscode());
			resultDTO.setStatusmessage(alertResultReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "detal_alertas", locale);
		String realname = "ALERTAS";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "details", locale));

		DataColumn col0 = new DataColumn("alert_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_txdate", locale), String.class);
		DataColumn col1 = new DataColumn("alert_type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_alert_type", locale), String.class);
		DataColumn col2 = new DataColumn("description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_description", locale), String.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		
		DataRow row = null;
		if( alertResultReport != null && alertResultReport.getAlertsreport()!= null && alertResultReport.getAlertsreport().length > 0){
			for (AlertReportDTO notification : alertResultReport.getAlertsreport()) {
				row = dt0.newRow(); 
				row.setCellValue("alert_date",  notification.getAlertdate());
				row.setCellValue("alert_type", notification.getAlerttype());
				row.setCellValue("description", notification.getDescription());
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public AvailabilityReportResultDTO getAvailabilityReportWS(AvailabilityReportInitParamDTO  initParamDTO, Long uskey)
	{
		AvailabilityReportResultDTO result = null;
		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getAvailabilityReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new AvailabilityReportResultDTO();
			result.setStatuscode("P600");
		}
		return result;
	}
	
	public FileDownloadInfoResultDTO getExcelAvailabilityReportWS(AvailabilityReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		AvailabilityReportResultDTO availabilityReport = null;
		try {
			availabilityReport = IRSB2BStockPoolRestFulWSClient.getInstance().getAvailabilityReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(availabilityReport != null && !availabilityReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(availabilityReport.getStatuscode());
			resultDTO.setStatusmessage(availabilityReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "detal_availability", locale);
		String realname = "DISPONIBILIDAD";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "details", locale));

		DataColumn col0 = new DataColumn("buyer_name", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_buyer_name", locale), String.class);
		DataColumn col1 = new DataColumn("buyer_sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_buyer_sku", locale), String.class);
		DataColumn col2 = new DataColumn("vendor_sku", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_vendor_sku", locale), String.class);
		DataColumn col3 = new DataColumn("description", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_description", locale), String.class);
		DataColumn col4 = new DataColumn("notification_date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_notification_date", locale), String.class);
		DataColumn col5 = new DataColumn("notified_stock", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_notified_stock", locale), Integer.class);
		DataColumn col6 = new DataColumn("last_sales", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_last_sales", locale), Integer.class);
		DataColumn col7 = new DataColumn("client_availability", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "col_client_availability", locale), Integer.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		dt0.addColumn(col6);
		dt0.addColumn(col7);
		
		
		DataRow row = null;
		if( availabilityReport != null && availabilityReport.getAvailabilityreport()!= null && availabilityReport.getAvailabilityreport().length > 0){
			for (AvailabilityReportDTO notification : availabilityReport.getAvailabilityreport()) {
				row = dt0.newRow(); 
				row.setCellValue("buyer_name",  notification.getBuyername());
				row.setCellValue("buyer_sku", notification.getSkubuyer());
				row.setCellValue("vendor_sku", notification.getSkuvendor());
				row.setCellValue("description", notification.getItemdescription());
				row.setCellValue("notification_date", notification.getNotificationdate());
				row.setCellValue("notified_stock", notification.getNotifiedstock());
				row.setCellValue("last_sales", notification.getLastsales());
				row.setCellValue("client_availability", notification.getClientavailability());
				
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO getExcelPendingHomologationReportWS(PendingHomologationInitParamDTO pendingHomologationinitParamDTO, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		PendingHomologationResultDTO pendingHomologationReport = null;
		try {
			pendingHomologationReport = IRSB2BStockPoolRestFulWSClient.getInstance().getPendingHomologationReportWS(pendingHomologationinitParamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(pendingHomologationReport != null && !pendingHomologationReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(pendingHomologationReport.getStatuscode());
			resultDTO.setStatusmessage(pendingHomologationReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "detal_pending_homologation", locale);
		String realname = "PRODUCTOS SIN HOMOLOGAR";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_CUSTOMER, "details", locale));

		DataColumn col0 = new DataColumn("date", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_date", locale), String.class);
		DataColumn col1 = new DataColumn("oc_number", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_oc_number", locale), String.class);
		DataColumn col2 = new DataColumn("quantity", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_quantity", locale), Integer.class);
		DataColumn col3 = new DataColumn("buyer", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_buyer", locale), String.class);
		DataColumn col4 = new DataColumn("sku_buyer", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_sku_buyer", locale), String.class);
		DataColumn col5 = new DataColumn("desc_buyer", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "col_desc_buyer", locale), String.class);
	
		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		
		DataRow row = null;
		if( pendingHomologationReport != null && pendingHomologationReport.getPendinghomologationitems()!= null && pendingHomologationReport.getPendinghomologationitems().length > 0){
			for (PendingHomologationReportDTO pendingHomologation : pendingHomologationReport.getPendinghomologationitems()) {
				row = dt0.newRow(); 
				row.setCellValue("date",  pendingHomologation.getRequestdate());
				row.setCellValue("oc_number", pendingHomologation.getOcnumber());
				row.setCellValue("quantity", pendingHomologation.getQuantity());
				row.setCellValue("buyer", pendingHomologation.getBuyer());
				row.setCellValue("sku_buyer", pendingHomologation.getSkubuyer());
				row.setCellValue("desc_buyer", pendingHomologation.getDescription());
				
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
	
	
	public ClientAvailabilityReportResultDTO getClientAvailabilityWS(ClientAvailabilityInitParamDTO  initParamDTO, Long uskey)
	{
		ClientAvailabilityReportResultDTO  result = null;
		try
		{
			userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getClientAvailabilityWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new ClientAvailabilityReportResultDTO ();
			result.setStatuscode("P600");
		}
		return result;
	}
	
	public ProductDetailReportResultDTO getProductDetailReportWS(ProductDetailReportInitParamDTO  initParamDTO, Long uskey)
	{
		ProductDetailReportResultDTO result = null;
		try
		{
			//userReportManager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamDTO.getVendorcode());
			result = IRSB2BStockPoolRestFulWSClient.getInstance().getProductDetailReportWS(initParamDTO);
			result.setStatuscode((result.getStatusmessage().length()>0)?"-1":"0");// por problemas de json...
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = new ProductDetailReportResultDTO();
			result.setStatuscode("P600");
		}
		return result;
	}
	
	

	
	public FileDownloadInfoResultDTO getExcelProductDetailReportWS(ProductDetailReportInitParamDTO initParams, String selectedFormat, Long userId, Locale locale)
	{
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		ProductDetailReportResultDTO productDetailReport = null;
		try {
			productDetailReport = IRSB2BStockPoolRestFulWSClient.getInstance().getProductDetailReportWS(initParams);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P600");// modulo no disp.
		}

		if(productDetailReport != null && !productDetailReport.getStatuscode().equals("0")){
			resultDTO.setStatuscode(productDetailReport.getStatuscode());
			resultDTO.setStatusmessage(productDetailReport.getStatusmessage());
			return resultDTO;
		}
		
		
		String downloadname =   "Detalle_producto";
		String realname = "Detalle_producto";

		//Tabla			
		DataTable dt0 = new DataTable(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "details", locale));
		
		DataColumn col0 = new DataColumn("date_time", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "date_time", locale), String.class);
		DataColumn col1 = new DataColumn("type", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "type", locale), String.class);
		DataColumn col2 = new DataColumn("client", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "client", locale), String.class);
		DataColumn col3 = new DataColumn("reference", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "reference", locale), String.class);
		DataColumn col4 = new DataColumn("update", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "update", locale), Integer.class);
		DataColumn col5 = new DataColumn("sale", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "sale", locale), Integer.class);
		DataColumn col6 = new DataColumn("notifications", I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_LOGISTIC, "notifications", locale), Integer.class);

		dt0.addColumn(col0);
		dt0.addColumn(col1);
		dt0.addColumn(col2);
		dt0.addColumn(col3);
		dt0.addColumn(col4);
		dt0.addColumn(col5);
		dt0.addColumn(col6);
		
		
		DataRow row = null;
		if( productDetailReport != null && productDetailReport.getProductdetailreport()!= null && productDetailReport.getProductdetailreport().length > 0){
			for (ProductDetailReportDTO productdetail : productDetailReport.getProductdetailreport()) {
				row = dt0.newRow(); 
				row.setCellValue("date_time",  productdetail.getDatetime());
				row.setCellValue("type", productdetail.getType());
				row.setCellValue("client", productdetail.getClientname());
				row.setCellValue("reference", productdetail.getReference());
				row.setCellValue("update", productdetail.getUpdate());
				row.setCellValue("sale", productdetail.getSale());
				row.setCellValue("notifications", productdetail.getNotification());
				
				dt0.addRow(row);
			}
		}

		resultDTO = FileHandlerUtils.getInstance().downloadFile(dt0, selectedFormat, downloadname, realname, userId);
		
		return resultDTO;
	}
}
