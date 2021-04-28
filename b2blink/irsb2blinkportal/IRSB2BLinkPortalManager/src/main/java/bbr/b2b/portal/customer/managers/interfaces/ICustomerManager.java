package bbr.b2b.portal.customer.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.customer.report.classes.AddOrUpdatePendingFileInitParamDTO;
import bbr.b2b.customer.report.classes.AssociateProductProviderProductClientInitParamDTO;
import bbr.b2b.customer.report.classes.ClientArrayResultDTO;
import bbr.b2b.customer.report.classes.CustomerServiceControlPanelResultDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailInitParamDTO;
import bbr.b2b.customer.report.classes.LateSalesDetailResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedLocalDetailResultDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailInitParamDTO;
import bbr.b2b.customer.report.classes.NotLoadedProductDetailResultDTO;
import bbr.b2b.customer.report.classes.PendingLoadFilesResultDTO;
import bbr.b2b.customer.report.classes.PendingProcessInventoryDetailInitParamDTO;
import bbr.b2b.customer.report.classes.PendingProcessSalesDetailInitParamDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryArrayResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessInventoryDetailResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesArrayResultDTO;
import bbr.b2b.customer.report.classes.PendingReprocessSalesDetailResultDTO;
import bbr.b2b.customer.report.classes.ProductProviderArrayResultDTO;
import bbr.b2b.customer.report.classes.ProductReportInitParamDTO;
import bbr.b2b.customer.report.classes.ProductReportResultDTO;
import bbr.b2b.customer.report.classes.ScoreCardTableBbrDTO;
import bbr.b2b.customer.report.classes.SearchProductProviderInitParamDTO;
import bbr.b2b.customer.report.classes.SearchProductProviderWithoutHomologationInitParamDTO;
import bbr.b2b.customer.report.classes.UploadMastersLoadInitParamDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;


public interface ICustomerManager extends IGenericEJBInterface {
	
	
	
	/*LastInventorySendArrayResultDTO getLastSendInventoryByClient (String pvcode); //ultimos inventarios enviados
	LastSalesSendArrayResultDTO getLastSendSalesByClient (String pvcode);    //ultimas ventas enviadas
	NotLoadedLocalArrayResultDTO countNotLoadedLocalByClient (String pvcode); //Locales sin homologar
	NotLoadedProductArrayResultDTO countNotProductLocalByClient (String pvcode); //Productos sin homologar
	PendingReprocessSalesArrayResultDTO countPendingReprocessSalesByClient (String pvcode); //reprocesos de vetna pendientes
	PendingReprocessInventoryArrayResultDTO countPendingReprocessInventoryByClient (String pvcode); //reprocesos pendientes inventario
	LateSalesArrayResultDTO countLateSalesByClient (String pvcode); //ventas atrasadas por cliente
	*/
	
	PendingReprocessSalesArrayResultDTO countPendingReprocessSalesByClient (String pvcode); //reprocesos de vetna pendientes
	PendingReprocessInventoryArrayResultDTO countPendingReprocessInventoryByClient (String pvcode); //reprocesos pendientes inventario
	
	CustomerServiceControlPanelResultDTO getCustomerServiceControlPanel (Long uskey, String pvcode);
	
	NotLoadedLocalDetailResultDTO getNotLoadedLocalDetail (NotLoadedLocalDetailInitParamDTO initParamDTO); //detalle locales sin homologar
	FileDownloadInfoResultDTO downloadNotLoadedLocalDetail(NotLoadedLocalDetailInitParamDTO initParamDTO, Long uskey);
	
	NotLoadedProductDetailResultDTO getNotLoadedProductDetail (NotLoadedProductDetailInitParamDTO initParamDTO); //detalle productos sin homologar
	FileDownloadInfoResultDTO downloadNotLoadedProductDetail(NotLoadedProductDetailInitParamDTO initParamDTO, Long uskey);
	
	PendingReprocessSalesDetailResultDTO getPendingReprocessSalesDetail (PendingProcessSalesDetailInitParamDTO initParamDTO); //detalle reprocesos pendientes de venta
	FileDownloadInfoResultDTO downloadPendingReprocessSalesDetail (PendingProcessSalesDetailInitParamDTO initParamDTO, Long uskey);
	
	PendingReprocessInventoryDetailResultDTO getPendingReprocessInventoryDetail (PendingProcessInventoryDetailInitParamDTO initParamDTO); //detalle reprocesos pendientes de inventario
	FileDownloadInfoResultDTO downloadPendingReprocessInventoryDetail (PendingProcessInventoryDetailInitParamDTO initParamDTO, Long uskey);
	
	LateSalesDetailResultDTO getLateSalesByClient (LateSalesDetailInitParamDTO initParamDTO); //detalle ventas atrasadas para un cliente
	
	
	//carga maestros
	/*PendingLoadFileResultDTO getPendingLoadProductFileByProvider (String pvcode); // archivo pendiente producto
	PendingLoadFileResultDTO getPendingLoadLocalFileByProvider (String pvcode); // archivo pendiente locales
	PendingLoadFileResultDTO getPendingLoadHomologatorFileByProvider (String pvcode); // archivo pendiente homologacion*/
	PendingLoadFilesResultDTO getPendingLoadFilesByProvider (String pvcode);
	
	
	BaseResultDTO addOrUpdatePendingProductFileByProvider (AddOrUpdatePendingFileInitParamDTO initParamDTO); //archivo para carga
	BaseResultDTO addOrUpdatePendingLocalFileByProvider (AddOrUpdatePendingFileInitParamDTO initParamDTO); //archivo para carga
	BaseResultDTO addOrUpdatePendingHomologatorFileByProvider (AddOrUpdatePendingFileInitParamDTO initParamDTO); //archivo para carga
	
	BaseResultsDTO doUploadMasters(UploadMastersLoadInitParamDTO initParamDTO);
	
	//asociar productos sin homologar
	ProductProviderArrayResultDTO searchProviderProductWhitoutHomologation(SearchProductProviderWithoutHomologationInitParamDTO initParamDTO); //buscador de productos del proveedor que no estan asociados a ningun producto del cliente
	ProductProviderArrayResultDTO searchProviderProduct(SearchProductProviderInitParamDTO initParamDTO); //buscador de productos del proveedor (tengan o no tengan homologacion)
	BaseResultDTO associateProductProviderProductClient(AssociateProductProviderProductClientInitParamDTO initParamDTO ); //asociar producto del proveedor con producto del cliente
	
	//reporte productos
	ClientArrayResultDTO getAllClients(); //filtro clientes
	ProductReportResultDTO getProductReport (ProductReportInitParamDTO initParamDTO); // reporte productos
	FileDownloadInfoResultDTO downloadProductReport(ProductReportInitParamDTO initParamDTO, Long uskey);

	
	
	void saveCompanySelectedAndAddCountUserProvider(Long uskey, String pvcode);
	
	FileDownloadInfoResultDTO downloadScoreCardDetailReport(ScoreCardTableBbrDTO[] data, Long uskey);

}

