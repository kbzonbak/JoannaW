package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.entities.Site;

public interface ISiteServer extends IElementServer<Site, SiteDTO> {

	public SiteDTO addSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteSite(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO getSiteByPK(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public SiteDTO updateSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	public SiteDTO[] getSitesByCompanyRut(String companyrut) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
