package bbr.esb.users.interfaces;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.esb.users.data.classes.UserTypeDTO;
import bbr.esb.users.entities.UserType;

public interface IUserTypeServer extends IElementServer<UserType, UserTypeDTO> {

	public UserTypeDTO addUserType(UserTypeDTO usertype) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteUserType(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserTypeDTO getUserTypeByPK(Long usertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public UserTypeDTO updateUserType(UserTypeDTO usertype) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
