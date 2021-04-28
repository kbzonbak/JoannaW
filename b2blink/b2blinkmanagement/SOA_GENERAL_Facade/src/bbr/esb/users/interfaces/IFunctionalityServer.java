package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.FunctionalityDTO;
import bbr.esb.users.entities.Functionality;

public interface IFunctionalityServer extends IElementServer<Functionality, FunctionalityDTO> {

	public FunctionalityDTO[] getFunctionalitiesByUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
