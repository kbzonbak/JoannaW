package bbr.b2b.regional.logistic.couriers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CommuneCourierW;
import bbr.b2b.regional.logistic.couriers.entities.CommuneCourier;

public interface ICommuneCourierServer extends IElementServer<CommuneCourier, CommuneCourierW> {
	CommuneCourierW getCourierCommuneByCourierAndParisCommune(Long courierid, String pariscommune);
	CommuneCourierW addCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException;
	CommuneCourierW updateCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException;
	void deleteCommuneCourier(CommuneCourierW communeCourier) throws OperationFailedException, NotFoundException;
}
