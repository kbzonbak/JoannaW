package bbr.b2b.regional.logistic.couriers.interfaces;

import java.util.List;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChilexpressCourierStateTmpW;
import bbr.b2b.regional.logistic.couriers.entities.ChilexpressCourierStateTmp;

public interface IChilexpressCourierStateTmpServer extends IElementServer<ChilexpressCourierStateTmp, ChilexpressCourierStateTmpW> {
	
	ChilexpressCourierStateTmpW updateChilexpressCourierStateTmp(ChilexpressCourierStateTmpW chilexpresscourierstatetmp) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChilexpressCourierStateTmpW[] getChilexpressCourierStateTmps() throws AccessDeniedException, OperationFailedException, NotFoundException;
	ChilexpressCourierStateTmpW addChilexpressCourierStateTmp(ChilexpressCourierStateTmpW chilexpresscourierstatetmp) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	ChilexpressCourierStateTmpW[] getFirstFileOutdatedChilexpressCourierStates();
	ChilexpressCourierStateTmpW[] getFirstFileUpdatedChilexpressCourierStates();
	ChilexpressCourierStateTmpW[] getChilexpressCourierStatesByFile(String filename);
	int deleteByIds(List<Long> chilexpresscourierstatetmpids);
}