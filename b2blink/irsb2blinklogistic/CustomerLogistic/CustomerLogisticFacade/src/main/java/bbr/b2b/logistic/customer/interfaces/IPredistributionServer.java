package bbr.b2b.logistic.customer.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.logistic.customer.entities.Predistribution;
import bbr.b2b.logistic.customer.data.wrappers.PredistributionW;
import bbr.b2b.logistic.customer.entities.PredistributionId;
public interface IPredistributionServer extends IGenericServer<Predistribution, PredistributionId, PredistributionW> {

	PredistributionW addPredistribution(PredistributionW predistribution) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PredistributionW[] getPredistributions() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PredistributionW updatePredistribution(PredistributionW predistribution) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
