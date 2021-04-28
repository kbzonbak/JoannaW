package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.entities.Company;

public interface ICompanyServer extends IElementServer<Company, CompanyDTO> {

	public CompanyDTO addCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO getCompanyByPK(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO updateCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public CompanyDTO[] getCompaniesofUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public CompanyDTO[] getCompanyByAccessAndSitename(String sitename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;
		
}
