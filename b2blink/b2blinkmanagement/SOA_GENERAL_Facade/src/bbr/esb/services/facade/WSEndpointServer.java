package bbr.esb.services.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.services.entities.Service;
import bbr.esb.services.entities.WSEndpoint;
import bbr.esb.users.data.classes.WSEndpointDTO;

@Stateless(name = "servers/users/WSEndpointServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WSEndpointServer extends ElementServer<WSEndpoint, WSEndpointDTO> implements WSEndpointServerLocal {

	@EJB
	ServiceServerLocal serviceserver;

	public WSEndpointDTO addWSEndpoint(WSEndpointDTO wsenpoint) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(wsenpoint);
	}

	@Override
	protected void copyRelationsEntityToWrapper(WSEndpoint entity, WSEndpointDTO wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setServicekey(entity.getService().getId() != null ? new Long(entity.getService().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(WSEndpoint entity, WSEndpointDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getServicekey() != null && wrapper.getServicekey() > 0) {
			Service service = serviceserver.findById(wrapper.getServicekey());
			if (service != null) {
				entity.setService(service);
			}
		}
	}

	public void deleteWSEndpoint(Long wsenpointid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(wsenpointid);
	}

	public WSEndpointDTO getWSEndpointByPK(Long wsenpointid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(wsenpointid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Endpoint Servicio Web";

	}

	@Override
	protected void setEntityname() {
		entityname = "WSEndpoint";
	}

	public WSEndpointDTO updateWSEndpoint(WSEndpointDTO wsenpoint) throws OperationFailedException, NotFoundException {
		return updateElement(wsenpoint);
	}

}
