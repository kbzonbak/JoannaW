package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.entities.User;

public interface IUserServer extends IElementServer<User, UserDTO> {

	public UserDTO addUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO getUserByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserDTO updateUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
