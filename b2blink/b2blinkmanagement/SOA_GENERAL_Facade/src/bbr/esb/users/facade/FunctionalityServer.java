package bbr.esb.users.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.factories.LowerCaseResultTransformer;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.users.data.classes.FunctionalityDTO;
import bbr.esb.users.entities.Functionality;

@Stateless(name = "servers/users/FunctionalityServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FunctionalityServer extends ElementServer<Functionality, FunctionalityDTO> implements FunctionalityServerLocal {

	public FunctionalityDTO addFunctionality(FunctionalityDTO functionality) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(functionality);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Functionality entity, FunctionalityDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(Functionality entity, FunctionalityDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteFunctionality(Long functionalityid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(functionalityid);
	}

	public FunctionalityDTO[] getFunctionalitiesByUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.esb.users.entities.Functionality.getFunctionalitiesByUserType");
		query.setLong("usertypeid", usertypeid);
		query.setResultTransformer(new LowerCaseResultTransformer(FunctionalityDTO.class));
		List list = query.list();
		FunctionalityDTO[] result = (FunctionalityDTO[]) list.toArray(new FunctionalityDTO[list.size()]);
		return result;
	}

	public FunctionalityDTO getFunctionalityByPK(Long functionalityid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(functionalityid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Funcionalidad";

	}

	@Override
	protected void setEntityname() {
		entityname = "Functionality";
	}

	public FunctionalityDTO updateFunctionality(FunctionalityDTO functionality) throws OperationFailedException, NotFoundException {
		return updateElement(functionality);
	}

}
