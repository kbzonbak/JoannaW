package bbr.b2b.logistic.datings.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.datings.data.wrappers.BlockingTypeW;
import bbr.b2b.logistic.datings.entities.BlockingType;

public interface IBlockingTypeServer extends IGenericServer<BlockingType, Long, BlockingTypeW> {

	public BlockingTypeW addBlockingType(BlockingTypeW blockingTypeW) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public BlockingTypeW[] getBlockingTypes() throws AccessDeniedException, OperationFailedException, NotFoundException;	

	public BlockingTypeW updateBlockingType(BlockingTypeW blockingTypeW) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
}
