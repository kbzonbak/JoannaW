package bbr.b2b.regional.logistic.deliveries.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.deliveries.entities.Pallet;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.PalletW;

public interface IPalletServer extends IElementServer<Pallet, PalletW> {

	PalletW addPallet(PalletW pallet) throws AccessDeniedException, OperationFailedException, NotFoundException;
	PalletW[] getPallets() throws AccessDeniedException, OperationFailedException, NotFoundException;
	PalletW updatePallet(PalletW pallet) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int deleteByDeliveryId(Long deliveryid);
}
