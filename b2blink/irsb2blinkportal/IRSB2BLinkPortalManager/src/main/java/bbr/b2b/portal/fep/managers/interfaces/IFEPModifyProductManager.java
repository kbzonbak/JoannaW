package bbr.b2b.portal.fep.managers.interfaces;

import java.util.Locale;

import bbr.b2b.common.adtclasses.classes.BaseInitParamDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.ErrorInfoArrayResultDTO;
import bbr.b2b.fep.common.data.classes.PersonDTO;
import bbr.b2b.fep.mp.data.classes.MPAddNewItemsInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPArticleTypeInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPHistItemArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPHistItemInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemByArticleInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemDTO;
import bbr.b2b.fep.mp.data.classes.MPItemErrorArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemStateArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemTechnicalInfoResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByArticleTypeArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByFilterInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByIDsInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsByStatusInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPItemsResumeDataResultDTO;
import bbr.b2b.fep.mp.data.classes.MPPrivilegeArrayResultDTO;
import bbr.b2b.fep.mp.data.classes.MPPrivilegeInitParamDTO;
import bbr.b2b.fep.mp.data.classes.MPProductInfoResumeResultDTO;
import bbr.b2b.fep.mp.data.classes.MPProductStateInfoResumeResultDTO;
import bbr.b2b.fep.mp.data.classes.MPUpdateItemsInitParamDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;

public interface IFEPModifyProductManager extends IGenericEJBInterface
{

	// Privilegios
	public MPPrivilegeArrayResultDTO getPrivilegies(MPPrivilegeInitParamDTO initparam);

	public ErrorInfoArrayResultDTO addOrEditPrivilegies(MPPrivilegeInitParamDTO initparam);
	
	public FileDownloadInfoResultDTO downloadPrivilegesByItemState (MPPrivilegeInitParamDTO initparam, Long uskey, String fileformat);

		public MPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeResume(MPItemsByFilterInitParamDTO initparam, Long uskey);

	public MPItemsByArticleTypeArrayResultDTO getItemsByArticleTypeAndTradeResume(MPItemByArticleInitParamDTO initparam, Long uskey);

	// Items
	public MPItemArrayResultDTO getItemsByFilter(MPItemsByFilterInitParamDTO initparam);

	public MPItemArrayResultDTO getItemsByIDs(MPItemsByIDsInitParamDTO initparam);

	public MPItemResultDTO getItemByID(MPItemsByIDsInitParamDTO initparam);

	public MPItemErrorArrayResultDTO addOrUpdateNewItems(MPAddNewItemsInitParamDTO initparam);

	public MPItemErrorArrayResultDTO updateItems(MPUpdateItemsInitParamDTO initparam);
	
	BaseResultDTO removeItems (MPItemDTO[] itemsDTO, PersonDTO user);

	// Devuelve los estados del sistema.
	public MPItemStateArrayResultDTO getItemStates(BaseInitParamDTO initparam);

	// Devuelve la plantilla y la información de los atributos que le pertenecen ysus privilegios segun estado y tipo de usuario
	public ArticleTypeResultDTO getArticleTypeWithMPProvilegies(MPArticleTypeInitParamDTO initparam);

	public MPItemsResumeDataResultDTO getItemsResumeData(MPItemByArticleInitParamDTO initparam);

	public MPProductInfoResumeResultDTO getProductInfoItemsResumeData(MPItemByArticleInitParamDTO initparam);

	public MPItemArrayResultDTO getItemsByStatusData(MPItemsByStatusInitParamDTO initparam);
	
	public MPProductStateInfoResumeResultDTO getProductStateInfoItemsResumeData(MPItemsByStatusInitParamDTO initparam);

	public MPItemTechnicalInfoResultDTO getItemTechnicalInfoByID(MPItemsByIDsInitParamDTO initparam);

	// Historia
	public MPHistItemArrayResultDTO getHistItemByID(MPHistItemInitParamDTO initparam);

	// Descargas y Subidas
	public FileDownloadInfoResultDTO downloadHistItemByID(Long itemid, Long uskey, String fileformat, Locale locale);

	public FileDownloadInfoResultDTO downloadRequestFileBase(Long uskey, Locale locale);

	public MPItemErrorArrayResultDTO uploadNewRequest(String filename, Long uskey, PersonDTO person, Locale locale);
	
	public FileDownloadInfoResultDTO downloadItemsByFilter (MPItemsByFilterInitParamDTO initparam, Long uskey, String fileformat);

	// resumen por plantilla en info de productos
	public FileDownloadInfoResultDTO downloadItemsByArticleTypeResume(MPItemByArticleInitParamDTO initparam, Long uskey, String fileformat);

	// Informacion de productos con sus detalles
	public FileDownloadInfoResultDTO downloadExcelItems(MPArticleTypeInitParamDTO initparam, MPItemDTO[] items, boolean includeAleas, boolean includePosibleValues, Long userKey);

	public MPItemErrorArrayResultDTO uploadExcelItems(MPArticleTypeInitParamDTO initparam, MPItemDTO[] items, String filename, Long userKey);

	public MPItemErrorArrayResultDTO markItemsAsDownloaded(BaseInitParamDTO initparam, MPItemDTO[] items,PersonDTO user);

	//Generacion de reportes de productos
	public FileDownloadInfoResultDTO downloadPdfProductDatasheet(MPItemsByIDsInitParamDTO initparam, Long userKey);
}