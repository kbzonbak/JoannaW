package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.users.entities.Access;
import bbr.esb.users.entities.AccessKey;

public interface IAccessServer extends IGenericServer<Access, AccessKey, AccessDTO> {

	public AccessDTO[] getAccessesBySiteAndCode(Long sitekey, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public AccessDataDTO[] getAccessDataofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public String getAccessByCompanyRutAndSiteName(String companyrut, String sitename) throws AccessDeniedException;
}
