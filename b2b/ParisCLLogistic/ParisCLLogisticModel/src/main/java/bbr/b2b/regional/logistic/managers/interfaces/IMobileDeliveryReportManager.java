package bbr.b2b.regional.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.TruckDispatcherW;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryDetailMobileBackendInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryDetailMobileInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryItemsMobileResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileBackendInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryReportResultMobileDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryTokenCreationInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryUpdateStatusMobileBackendInitParmDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryUpdateStatusMobileInitParmDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherCheckTokenInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherInputDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.UserTokenResultDTO;

public interface IMobileDeliveryReportManager {

	public TruckDispatcherW getTruckDispatcherByEmail(String email);

	public TruckDispatcherResultDTO doAddOrUpdateTruckDispatcher(TruckDispatcherInputDataDTO inputData);

	public UserTokenResultDTO doCreateUserToken(DODeliveryTokenCreationInitParamDTO initParamDTO);

	public BaseResultDTO doCheckUserValidData(DODeliveryTokenCreationInitParamDTO initParamDTO);

	public BaseResultDTO doCheckUserToken(TruckDispatcherCheckTokenInitParamDTO initParamDTO);

	public DODeliveryReportResultMobileDTO getDODeliveriesMobileByTruckDispatcher(DODeliveryMobileInitParamDTO initDataDTO);

	public DODeliveryReportResultMobileDTO getDODeliveriesMobileByTruckDispatcherBackend(DODeliveryMobileBackendInitParamDTO initDataDTO);

	public DODeliveryMobileResultDTO getDODeliveryDataMobile(DODeliveryDetailMobileInitParamDTO initParamDTO);

	public DODeliveryMobileResultDTO getDODeliveryDataMobileBackend(DODeliveryDetailMobileBackendInitParamDTO initParamDTO);

	public DODeliveryItemsMobileResultDTO getDODeliveryDetailItemsMobile(DODeliveryDetailMobileInitParamDTO initParamDTO);

	public DODeliveryItemsMobileResultDTO getDODeliveryDetailItemsMobileBackend(DODeliveryDetailMobileBackendInitParamDTO initParamDTO);

	public BaseResultDTO doUpdateDODeliveryStatusMobile(DODeliveryUpdateStatusMobileInitParmDTO initParamDTO);

	public BaseResultDTO doUpdateDODeliveryStatusMobileBackend(DODeliveryUpdateStatusMobileBackendInitParmDTO initParamDTO);
}
