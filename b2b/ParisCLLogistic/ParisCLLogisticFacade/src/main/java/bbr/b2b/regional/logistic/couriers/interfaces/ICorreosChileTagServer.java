package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CorreosChileTagW;
import bbr.b2b.regional.logistic.couriers.entities.CorreosChileTag;
import bbr.b2b.regional.logistic.couriers.report.classes.CorreosChileCSVDTO;

public interface ICorreosChileTagServer extends IElementServer<CorreosChileTag, CorreosChileTagW> {
	
	CorreosChileTagW addCorreosChileTag(CorreosChileTagW correosChileTag) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CorreosChileTagW[] getCorreosChileTags() throws AccessDeniedException, OperationFailedException, NotFoundException;
	CorreosChileTagW updateCorreosChileTag(CorreosChileTagW correosChileTag) throws AccessDeniedException, OperationFailedException, NotFoundException;
	CorreosChileCSVDTO getCorreosChileTagInformation(Long dodeliveryid);
}
