package bbr.b2b.portal.users.managers.interfaces;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;
import bbr.b2b.users.report.classes.AddRequestAdminPVInitParamDTO;
import bbr.b2b.users.report.classes.AddRequestResultDTO;
import bbr.b2b.users.report.classes.RequestAdminRTActionInitParamDTO;
import bbr.b2b.users.report.classes.RequestAdminRTReportResultDTO;
import bbr.b2b.users.report.classes.RequestCountNotificationResultDTO;
import bbr.b2b.users.report.classes.RequestDetailReportInitParamDTO;
import bbr.b2b.users.report.classes.RequestDetailReportResultDTO;
import bbr.b2b.users.report.classes.RequestReportInitParamDTO;
import bbr.b2b.users.report.classes.RequestReportResultDTO;
import bbr.b2b.users.report.classes.SelfManagementProfileResultDTO;


public interface IRequestManager extends IGenericEJBInterface {
	
	public AddRequestResultDTO addRequestNewUserAdminPV(AddRequestAdminPVInitParamDTO initParam);
	
	public AddRequestResultDTO addRequestEditAdminPV(AddRequestAdminPVInitParamDTO initParam);
	
	public AddRequestResultDTO addRequestDeleteAdminPV(AddRequestAdminPVInitParamDTO initParam);
	
	public RequestReportResultDTO getRequestsByFilterType(RequestReportInitParamDTO initParam);
	
	public RequestCountNotificationResultDTO getRequestCountNotification(RequestReportInitParamDTO initParam);
	
	public RequestDetailReportResultDTO getRequestDetail(RequestDetailReportInitParamDTO initParam);
	
	public RequestAdminRTReportResultDTO getRequestReportForAdminRT(RequestReportInitParamDTO initParam);
	
	public BaseResultDTO approveRequestByAdminRT(RequestAdminRTActionInitParamDTO initParam);
	
	public BaseResultDTO rejectRequestByAdminRT(RequestAdminRTActionInitParamDTO initParam);
	
	public SelfManagementProfileResultDTO getLastSelfManagementProfileUpdate();
	
}
