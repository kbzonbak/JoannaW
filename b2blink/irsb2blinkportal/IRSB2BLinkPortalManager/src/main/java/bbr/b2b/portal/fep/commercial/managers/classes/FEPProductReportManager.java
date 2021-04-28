package bbr.b2b.portal.fep.commercial.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.fep.commercial.data.classes.ProductByCodeResultDTO;
import bbr.b2b.fep.commercial.data.classes.ProductReportInitParamDTO;
import bbr.b2b.fep.commercial.data.classes.ProductsRecordsResultDTO;
import bbr.b2b.fep.managers.interfaces.ContextUtil;
import bbr.b2b.fep.managers.interfaces.IFEPProductReportManager;
import bbr.b2b.portal.users.managers.classes.UserReportManagerLocal;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;

@Stateless(name = "managers/FEPProductReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FEPProductReportManager implements FEPProductReportManagerLocal {

	private IFEPProductReportManager productReportManager 	= null;
	
	@EJB
	private UserReportManagerLocal userreportmanager;

	@PostConstruct
	public void getRemote() {
		
		try {
			productReportManager = ContextUtil.getInstance().getProductReportManager();
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ProductsRecordsResultDTO getProductsRecords(ProductReportInitParamDTO initParamW, Integer pageNumber, Integer rows, OrderCriteriaDTO[] order, boolean filter, boolean ispaginated, Long uskey) {
		ProductsRecordsResultDTO result = new ProductsRecordsResultDTO();

		userreportmanager.saveCompanySelectedAndAddCountUserProvider(uskey, initParamW.getPvcode());
		try {
			result = productReportManager.getProductsRecords(initParamW, pageNumber, rows, order, filter,true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			ProductsRecordsResultDTO resultDTO = new ProductsRecordsResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P100");// modulo comercial no disp.
		}
	}

	@Override
	public ProductByCodeResultDTO getProductByCode(String productcode) {
		ProductByCodeResultDTO result = new ProductByCodeResultDTO();
		
		try {
			result = productReportManager.getProductByCode(productcode);
		} catch (Exception e) {
			e.printStackTrace();
			return PortalStatusCodeUtils.getInstance().setStatusCode(result, "P100");// modulo comercial no disp.
		}
		
		return result;
	}
	
}