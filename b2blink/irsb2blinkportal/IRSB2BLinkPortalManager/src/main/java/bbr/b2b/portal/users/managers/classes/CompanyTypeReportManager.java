package bbr.b2b.portal.users.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.NamingException;

import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.ICompanyTypeManager;
import bbr.b2b.users.report.classes.CompanyTypeArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeResultDTO;

@Stateless(name = "managers/CompanyTypeReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CompanyTypeReportManager implements CompanyTypeReportManagerLocal {

	private ICompanyTypeManager companyTypeReportManager = null;
	
	@PostConstruct
	public void getRemote() {
		try {
			companyTypeReportManager = ContextUtil.getInstance().getICompanyTypeManager();
		} catch (NamingException e) {
			e.printStackTrace();			
		}
	}	
	
	public CompanyTypeArrayResultDTO getCompanyTypes() {
		try {
			return companyTypeReportManager.getCompanyTypes();

		} catch (Exception e) {
			CompanyTypeArrayResultDTO resultW = new CompanyTypeArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo			
		}
	}
	
	public CompanyTypeResultDTO findTypeCompanyById(Long companyTypePK) {
		try {
			return companyTypeReportManager.findTypeCompanyById(companyTypePK);

		} catch (Exception e) {
			CompanyTypeResultDTO resultW = new CompanyTypeResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");// modulo			
		}
	}
	
	public CompanyTypeArrayResultDTO getCompanyTypesOfUserForFilter(Long userid){
		try {
			return companyTypeReportManager.getCompanyTypesOfUserForFilter(userid);

		} catch (Exception e) {
			CompanyTypeArrayResultDTO resultW = new CompanyTypeArrayResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultW, "P200");
		}
	}		
}
