package bbr.esb.users.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.users.data.classes.UserTypeDTO;
import bbr.esb.users.entities.UserType;

@Stateless(name = "servers/users/UserTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserTypeServer extends ElementServer<UserType, UserTypeDTO> implements UserTypeServerLocal {

	public UserTypeDTO addUserType(UserTypeDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(user);
	}

	@Override
	protected void copyRelationsEntityToWrapper(UserType entity, UserTypeDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(UserType entity, UserTypeDTO wrapper) throws OperationFailedException, NotFoundException {
	}

	public void deleteUserType(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public UserTypeDTO getUserTypeByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}

	@Override
	public boolean isCacheable() {
		return true;
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Tipo de Usuario";

	}

	@Override
	protected void setEntityname() {
		entityname = "UserType";
	}

	public UserTypeDTO updateUserType(UserTypeDTO usertype) throws OperationFailedException, NotFoundException {
		return updateElement(usertype);
	}

}
