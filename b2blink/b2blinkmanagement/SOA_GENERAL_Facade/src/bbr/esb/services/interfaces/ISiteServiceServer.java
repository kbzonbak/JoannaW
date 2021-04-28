package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.esb.services.data.classes.SiteServiceDTO;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.services.entities.SiteService;
import bbr.esb.services.entities.SiteServiceKey;

public interface ISiteServiceServer extends IGenericServer<SiteService, SiteServiceKey, SiteServiceDTO> {

	public SiteServiceDTO addSiteService(SiteServiceDTO userService) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteSiteService(Long userkey, Long sitekey) throws OperationFailedException, NotFoundException;

	public SiteServiceReportDTO[] getSiteServiceReport() throws AccessDeniedException, OperationFailedException, NotFoundException;

}
