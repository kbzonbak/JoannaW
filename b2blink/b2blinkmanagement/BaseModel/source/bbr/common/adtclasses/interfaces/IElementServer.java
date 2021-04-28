package bbr.common.adtclasses.interfaces;

import java.io.Serializable;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;

public interface IElementServer<T, W extends Serializable> extends IGenericServer<T, Long, W> {

	public W addElement(W wrapper) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public void deleteElement(Long id) throws OperationFailedException, NotFoundException;

	public int deleteElements(Long[] ids) throws OperationFailedException, NotFoundException, AccessDeniedException;

	public W updateElement(W wrapper) throws OperationFailedException, NotFoundException;

}
