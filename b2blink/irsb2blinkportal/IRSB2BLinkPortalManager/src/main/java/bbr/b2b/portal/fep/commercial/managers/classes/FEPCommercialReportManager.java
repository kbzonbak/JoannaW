package bbr.b2b.portal.fep.commercial.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import bbr.b2b.fep.commercial.data.classes.CategoryArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.GetMarksByProviderAndCategoryInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.GetProductByPropertyArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.GetProductLabelByProviderAndCategoryDataInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.LabelValueArrayResultDTO;
import bbr.b2b.fep.commercial.data.classes.MarkArrayResultDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.IFEPCommercialReportManager;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;

@Stateless(name = "managers/FEPCommercialReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPCommercialReportManager implements FEPCommercialReportManagerLocal {

	private IFEPCommercialReportManager commercialReportManager = null;
	//private IUserManager userManager = null;
	//private static LoggingUtil logger = new LoggingUtil(CommercialReportManager.class);
	
	@EJB
	private UserReportManagerLocal userreportmanager;

	@PostConstruct
	public void getRemote() {
		
		try {
			commercialReportManager = ContextUtil.getInstance().getCommercialReportManager();
			//userManager = bbr.b2b.users.managers.interfaces.ContextUtil.getInstance().getIUserManager();
			
		} catch (NamingException e) {
			e.printStackTrace();
			
		}
	}
	

	@Override
	public CategoryArrayResultDTO getCategoryProductDataByLevelAndProvider(Boolean catRetailer, String pvcode, Integer level, Long catkey) {
		
		CategoryArrayResultDTO resultDTO = new CategoryArrayResultDTO();
		
		try {
			resultDTO = commercialReportManager.getCategoryProductDataByLevelAndProvider(catRetailer, pvcode, level, catkey);
		
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		
		return resultDTO;
	}

	@Override
	public CategoryArrayResultDTO getCategoryProductDataByLevelAndProvider(Boolean catRetailer, String pvcode, Integer level) {
		
		CategoryArrayResultDTO resultDTO = new CategoryArrayResultDTO();
		
		try {
			resultDTO = commercialReportManager.getCategoryProductDataByLevelAndProvider(catRetailer, pvcode, level);
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
		
		return resultDTO;
	}
	

	

	@Override
	public GetProductByPropertyArrayResultDTO getProductsByProperty(String searchValue, Boolean catRetailer, String pvcode, Integer criterion) {
		try {
			return commercialReportManager.getProductsByProperty(searchValue, catRetailer, pvcode, criterion);
		} catch (Exception e) {
			GetProductByPropertyArrayResultDTO resultDTO = new GetProductByPropertyArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
	}

	@Override
	public MarkArrayResultDTO getMarksByProviderAndCategory(GetMarksByProviderAndCategoryInitParamDTO initparamDTO) {
		try {
			return commercialReportManager.getMarksByProviderAndCategory(initparamDTO);
		} catch (Exception e) {
			e.printStackTrace();
			MarkArrayResultDTO resultDTO = new MarkArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
	}


	@Override
	public LabelValueArrayResultDTO getProductLabelByProviderAndCategoryData(
			GetProductLabelByProviderAndCategoryDataInitParamDTO initParamW) {
		try {
			return commercialReportManager.getProductLabelByProviderAndCategoryData(initParamW);
		} catch (Exception e) {
			e.printStackTrace();
			LabelValueArrayResultDTO resultDTO = new LabelValueArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
	}
}
