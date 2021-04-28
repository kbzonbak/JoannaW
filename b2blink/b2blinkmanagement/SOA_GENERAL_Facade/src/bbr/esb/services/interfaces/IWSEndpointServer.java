package bbr.esb.services.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.services.entities.WSEndpoint;
import bbr.esb.users.data.classes.WSEndpointDTO;

public interface IWSEndpointServer extends IElementServer<WSEndpoint, WSEndpointDTO> {

	public WSEndpointDTO addWSEndpoint(WSEndpointDTO wsendpoint) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteWSEndpoint(Long wsendpointid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public WSEndpointDTO getWSEndpointByPK(Long wsendpointid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public WSEndpointDTO updateWSEndpoint(WSEndpointDTO wsendpoint) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
