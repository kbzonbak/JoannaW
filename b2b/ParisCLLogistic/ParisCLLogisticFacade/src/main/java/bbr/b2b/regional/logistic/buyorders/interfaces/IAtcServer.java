package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.AtcW;
import bbr.b2b.regional.logistic.buyorders.entities.Atc;

public interface IAtcServer extends IElementServer<Atc, AtcW> {

	AtcW addAtc(AtcW atc) throws AccessDeniedException, OperationFailedException, NotFoundException;
	AtcW[] getAtcs() throws AccessDeniedException, OperationFailedException, NotFoundException;
	AtcW updateAtc(AtcW matc) throws AccessDeniedException, OperationFailedException, NotFoundException;
	List<String> getByATCCodes(List<String> codeList);
}
