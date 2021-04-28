package bbr.b2b.regional.logistic.locations.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.SaleStoreDTO;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.report.classes.LocationLogDTO;

public interface ILocationServer extends IElementServer<Location, LocationW> {

	LocationW addLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW[] getLocations() throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW updateLocation(LocationW location) throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW[] getLocationsByIds(Long[] ids) throws ServiceException;
	LocationW[] getLocationByIds(Long[] lokeys, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	LocationW[] getLocationsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException;
	LocationW[] getDestinationLocationsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException;
	int countLocationOfUser(Long[] lokeys) throws ServiceException;
	LocationLogDTO[] findLocationsLogLikeCodeAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countLocationsLogLikeCodeAssigned(String code, Long[] assignedids) throws ServiceException;
	LocationLogDTO[] findLocationsLogLikeCodeNotAssigned(String code, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countLocationsLogLikeCodeNotAssigned(String code, Long[] assignedids) throws ServiceException;
	LocationLogDTO[] findLocationsLogLikeNameAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countLocationsLogLikeNameAssigned(String name, Long[] assignedids) throws ServiceException;
	LocationLogDTO[] findLocationsLogLikeNameNotAssigned(String name, Long[] assignedids, int pagenumber, int rows, boolean isPaginated, OrderCriteriaDTO[] order) throws ServiceException;
	int countLocationsLogLikeNameNotAssigned(String name, Long[] assignedids) throws ServiceException;
	
	SaleStoreDTO[] getSaleStoresOrderedByDescription() throws OperationFailedException, NotFoundException;
	SaleStoreDTO[] findSaleStoresByCode(String code) throws OperationFailedException, NotFoundException;
	SaleStoreDTO[] findSaleStoresByName(String name) throws OperationFailedException, NotFoundException;
	
	public int getCountLocations();
}
