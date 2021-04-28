package bbr.b2b.logistic.ddcdeliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcFileW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcFile;
import bbr.b2b.logistic.ddcorders.report.classes.DdcFileDTO;

public interface IDdcFileServer extends IElementServer<DdcFile, DdcFileW> {

	DdcFileW addDdcFile(DdcFileW ddcfile) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcFileW[] getDdcFiles() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcFileW updateDdcFile(DdcFileW ddcfile) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DdcFileDTO[] getDdcFilesByDdcOrders(Long[] ddcorderids);
}
