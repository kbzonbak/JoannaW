package bbr.b2b.logistic.datings.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.data.wrappers.BlockingTypeW;
import bbr.b2b.logistic.datings.entities.BlockingType;

@Stateless(name = "servers/BlockingTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BlockingTypeServer extends LogisticElementServer<BlockingType, BlockingTypeW> implements BlockingTypeServerLocal {

	public BlockingTypeW addBlockingType(BlockingTypeW module) throws OperationFailedException, NotFoundException {
		return (BlockingTypeW) addElement(module);
	}

	public BlockingTypeW[] getBlockingTypes() throws OperationFailedException, NotFoundException {
		
		return (BlockingTypeW[]) getAllAsArray();
	}
	
	public BlockingTypeW updateBlockingType(BlockingTypeW module) throws OperationFailedException, NotFoundException {
		
		return (BlockingTypeW) updateElement(module);
	}
	
	@Override
	protected void copyRelationsEntityToWrapper(BlockingType entity, BlockingTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void copyRelationsWrapperToEntity(BlockingType entity, BlockingTypeW wrapper) throws OperationFailedException, NotFoundException {
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "BlockingType";
	}

	@Override
	protected void setEntityname() {
		entityname = "BlockingType";
	}

	@Override
	public int deleteElements(Long[] ids) throws OperationFailedException, NotFoundException, AccessDeniedException {
		
		return 0;
	}
	

}
