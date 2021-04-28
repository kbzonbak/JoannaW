package bbr.b2b.portal.fep.managers.interfaces;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseInitParamDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.cp.data.classes.CPArticleTypeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPHistItemInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemByArticleInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.fep.cp.data.classes.CPItemErrorArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemStateArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemSummaryFlowArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemSummaryFlowInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemTechnicalInfoArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemTechnicalInfoResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByArticleTypeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByFilterInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByIDsInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsByStatusInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPItemsResumeDataResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPPrivilegeInitParamDTO;
import bbr.b2b.fep.cp.data.classes.CPProductInfoResumeResultDTO;
import bbr.b2b.fep.cp.data.classes.CPRelatedProductArrayResultDTO;
import bbr.b2b.fep.cp.data.classes.CPUpdateItemsInitParamDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPCreateProductManager extends IGenericEJBInterface {
	
	//Privilegios
	CPPrivilegeArrayResultDTO getPrivilegies(CPPrivilegeInitParamDTO initparam);
	ErrorInfoArrayResultDTO addOrEditPrivilegies(CPPrivilegeInitParamDTO initparam);
	
	//Resumen de Items por Plantilla
	CPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeResume(CPItemsByFilterInitParamDTO  initparam, Long uskey);
	public CPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeAndTradeResume(CPItemByArticleInitParamDTO initparam);
	
	//Items
	CPItemArrayResultDTO getItemsByFilter(CPItemsByFilterInitParamDTO  initparam);
	CPItemArrayResultDTO getItemsByIDs(CPItemsByIDsInitParamDTO initparam);
	CPItemResultDTO getItemByID(CPItemsByIDsInitParamDTO initparam);
	//CPItemErrorArrayResultDTO addOrUpdateNewItems(CPAddNewItemsInitParamDTO initparam);
	
	//CPItemErrorArrayResultDTO updateItems(CPUpdateItemsInitParamDTO initparam);
	CPItemErrorArrayResultDTO addOrUpdateItems(CPUpdateItemsInitParamDTO initparam);
	CPItemErrorArrayResultDTO updateBuyerForItemsValues(CPUpdateItemsInitParamDTO initparam);
	
	public ErrorInfoArrayResultDTO approveItems(Long usertypeid, CPItemDTO[] items, PersonDTO user, String comment);
	public ErrorInfoArrayResultDTO rejectItems(Long usertypeid, CPItemDTO[] items, PersonDTO user, String comment, boolean isclosed);
	
	//Devuelve los estados del sistema.
	CPItemStateArrayResultDTO getItemStates(BaseInitParamDTO initparam);
	
	
	//Devuelve la plantilla y la información de los atributos que le pertenecen ysus privilegios segun estado y tipo de usuario
	public ArticleTypeResultDTO getArticleTypeWithCPPrivilegies(CPArticleTypeInitParamDTO initparam);
	
	public CPItemsResumeDataResultDTO getItemsResumeData(CPItemByArticleInitParamDTO initparam);
	public CPProductInfoResumeResultDTO getProductInfoItemsResumeData(CPItemByArticleInitParamDTO initparam);
	public CPItemArrayResultDTO getItemsByStatusData(CPItemsByStatusInitParamDTO  initparam, Long uskey);
	public CPItemTechnicalInfoArrayResultDTO getItemTechnicalInfoByID(CPItemsByIDsInitParamDTO initparam);
	
	//Historia
	CPHistItemArrayResultDTO getHistItemByID(CPHistItemInitParamDTO initparam);
	
	//Descargas y Subidas	
	public FileDownloadInfoResultDTO downloadHistItemByID(Long itemid, Long uskey, String fileformat, Locale locale);
	public FileDownloadInfoResultDTO downloadItemsByFilter(CPItemsByFilterInitParamDTO initparam, Long uskey, String fileformat);
	public FileDownloadInfoResultDTO downloadItemsByStatusData(CPItemsByStatusInitParamDTO initparam, Long uskey, String fileformat, boolean withSKU);
	public FileDownloadInfoResultDTO downloadPrivilegesByItemStateAndUserType(CPPrivilegeInitParamDTO initparam, Long uskey, String fileformat);
	
	public FileDownloadInfoResultDTO downloadRequestFileBase(Long uskey, Locale locale);	
	public CPItemErrorArrayResultDTO uploadNewRequest(String filename, Long uskey,PersonDTO person, Locale locale);
	
	//resumen por plantilla en info de productos
	public FileDownloadInfoResultDTO downloadItemsByArticleTypeResume(CPItemsByFilterInitParamDTO  initparam,Long uskey,String fileformat);
	
	//Informacion de productos con sus detalles
	public FileDownloadInfoResultDTO downloadExcelWithItemsToCreate(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, Long userKey);
	public FileDownloadInfoResultDTO downloadExcelWithItemsToEdit(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, Long userKey);
	public CPItemErrorArrayResultDTO uploadExcelWithNewsItems(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, String filename, Long userKey);
	public CPItemErrorArrayResultDTO uploadExcelWithEditedItems(CPArticleTypeInitParamDTO initparam, CPItemDTO[] items, String filename, Long userKey);
	
	//Resumen del historial
	public CPItemSummaryFlowArrayResultDTO getItemsSummaryInFlow (CPItemSummaryFlowInitParamDTO initparam);
	
	//Descargar ficha técnica de producto
	public FileDownloadInfoResultDTO downloaStandardProductSheet(DefinableAttributeInitParamDTO initparam, CPItemDTO[] items, boolean includeAleas, boolean includePosibleValues, Long userKey, Locale locale);
	
	public CPItemStateArrayResultDTO getItemsStateByProviderWorkFlow (String providercode);
	
	public BaseResultDTO removeItems(CPItemDTO[] itemsDTO, Long usertypeid, PersonDTO user);
	
	public CPItemErrorArrayResultDTO updateItemsStatus(CPItemDTO[] items, Long usertypeid, String newstate, PersonDTO person);
	
	//Generacion de reportes de productos
	public FileDownloadInfoResultDTO downloadPdfProductDatasheet(CPItemsByIDsInitParamDTO initparam, Long userKey);
	
	//Productos complementarios
	public FileDownloadInfoResultDTO downloadRelatedProducts(Long articletypeid, CPItemDTO[] items, Long uskey);
	public CPRelatedProductArrayResultDTO getRelatedProductsByMainProducts(Long[] ids);
	
}