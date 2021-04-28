package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.CompanyTypeArrayResultDTO;
import bbr.b2b.users.report.classes.CompanyTypeResultDTO;


public interface ICompanyTypeManager extends IGenericEJBInterface{

	public CompanyTypeArrayResultDTO getCompanyTypes();
	public CompanyTypeResultDTO findTypeCompanyById(Long companyTypePK);
	public CompanyTypeArrayResultDTO getCompanyTypesOfUserForFilter(Long userid);

}
