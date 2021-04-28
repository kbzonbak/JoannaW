package bbr.b2b.portal.fep.commercial.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import bbr.b2b.fep.commercial.data.classes.CategoryArrayResultDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.IFEPCategoryProductManager;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;

@Stateless(name = "managers/FEPCategoryProductManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPCategoryProductManager implements FEPCategoryProductManagerLocal {

	private IFEPCategoryProductManager categoryProductManager = null;
	//private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	//private static LoggingUtil logger = new LoggingUtil(CategoryProductManager.class);
	
	@EJB
	private UserReportManagerLocal userreportmanager;

	@PostConstruct
	public void getRemote() {
		try {
			categoryProductManager = ContextUtil.getInstance().getCategoryProductManager();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	public CategoryArrayResultDTO getCategoryProductByProvider(String pvcode, Long uskey, boolean count) {
		
		CategoryArrayResultDTO resultDTO = new CategoryArrayResultDTO();
		
		// Se agregó el boolean porque este método se utiliza también para subir categorías y no se debe contar de nuevo al proveedor
		if(count){
			userreportmanager.saveCompanySelectedAndAddCountUserProvider(uskey, pvcode);	
		}	
				
		try {
			resultDTO = categoryProductManager.getCategoryProductByProvider(pvcode);
			
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");
		}
		
		return resultDTO;
	}
	
}