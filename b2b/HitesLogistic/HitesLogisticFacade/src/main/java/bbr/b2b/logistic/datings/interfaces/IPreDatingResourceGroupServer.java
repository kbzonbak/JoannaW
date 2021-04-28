package bbr.b2b.logistic.datings.interfaces;

import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.datings.data.wrappers.PreDatingResourceGroupW;
import bbr.b2b.logistic.datings.entities.PreDatingResourceGroup;

public interface IPreDatingResourceGroupServer extends IElementServer<PreDatingResourceGroup, PreDatingResourceGroupW> {

	PreDatingResourceGroupW addPreDatingResourceGroup(PreDatingResourceGroupW predatingresourcegroup) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDatingResourceGroupW[] getPreDatingResourceGroups() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDatingResourceGroupW updatePreDatingResourceGroup(PreDatingResourceGroupW predatingresourcegroup) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PreDatingResourceGroupW[] getPreDatingResourceGroupsByDateAndLocation(LocalDateTime since, LocalDateTime until, Long locationid) throws NotFoundException, OperationFailedException;
	int getCountPreDatingResourceGroupByLocationAndDate(Long vendorid, Long locationid, LocalDateTime reservedate);
}
