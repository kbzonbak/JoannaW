package bbr.esb.users.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.base.facade.ElementServer;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.entities.User;
import bbr.esb.users.entities.UserType;

@Stateless(name = "servers/users/UserServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserServer extends ElementServer<User, UserDTO> implements UserServerLocal {

	@EJB
	UserTypeServerLocal usertypeserver;

	public UserDTO addUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return addElement(user);
	}

	@Override
	protected void copyRelationsEntityToWrapper(User entity, UserDTO wrapper) throws OperationFailedException, NotFoundException {
		wrapper.setUsertypeid(entity.getUsertype() != null ? new Long(entity.getUsertype().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(User entity, UserDTO wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getUsertypeid() != null && wrapper.getUsertypeid() > 0) {
			UserType usertype = usertypeserver.getReferenceById(wrapper.getUsertypeid());
			if (usertype != null) {
				entity.setUsertype(usertype);
			}
		}
	}

	public void deleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		deleteElement(userid);
	}

	public UserDTO getUserByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return getById(userid);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Usuario";

	}

	@Override
	protected void setEntityname() {
		entityname = "User";
	}

	public UserDTO updateUser(UserDTO user) throws OperationFailedException, NotFoundException {
		return updateElement(user);
	}

}
