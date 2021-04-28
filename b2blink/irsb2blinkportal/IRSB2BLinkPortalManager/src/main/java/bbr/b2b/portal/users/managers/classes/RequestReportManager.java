package bbr.b2b.portal.users.managers.classes;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.portal.utils.PortalStatusCodeUtils;
import bbr.b2b.users.managers.interfaces.ContextUtil;
import bbr.b2b.users.managers.interfaces.IRequestManager;
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

@Stateless(name = "managers/RequestReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RequestReportManager implements RequestReportManagerLocal {

	private IRequestManager requestReportManagerRemote = null;
		
	@PostConstruct
	public void getRemote() {
		try {
			requestReportManagerRemote = ContextUtil.getInstance().getIRequestManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public AddRequestResultDTO addRequestNewUserAdminPV(AddRequestAdminPVInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.addRequestNewUserAdminPV(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			AddRequestResultDTO resultDTO = new AddRequestResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public AddRequestResultDTO addRequestEditAdminPV(AddRequestAdminPVInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.addRequestEditAdminPV(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			AddRequestResultDTO resultDTO = new AddRequestResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public AddRequestResultDTO addRequestDeleteAdminPV(AddRequestAdminPVInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.addRequestDeleteAdminPV(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			AddRequestResultDTO resultDTO = new AddRequestResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public RequestReportResultDTO getRequestsByFilterType(RequestReportInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.getRequestsByFilterType(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			RequestReportResultDTO resultDTO = new RequestReportResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public RequestCountNotificationResultDTO getRequestCountNotification(RequestReportInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.getRequestCountNotification(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			RequestCountNotificationResultDTO resultDTO = new RequestCountNotificationResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public RequestDetailReportResultDTO getRequestDetail(RequestDetailReportInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.getRequestDetail(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDetailReportResultDTO resultDTO = new RequestDetailReportResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public RequestAdminRTReportResultDTO getRequestReportForAdminRT(RequestReportInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.getRequestReportForAdminRT(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			RequestAdminRTReportResultDTO resultDTO = new RequestAdminRTReportResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public BaseResultDTO approveRequestByAdminRT(RequestAdminRTActionInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.approveRequestByAdminRT(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}

	@Override
	public BaseResultDTO rejectRequestByAdminRT(RequestAdminRTActionInitParamDTO initParam) {
		try {
			return requestReportManagerRemote.rejectRequestByAdminRT(initParam);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResultDTO resultDTO = new BaseResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
	@Override
	public SelfManagementProfileResultDTO getLastSelfManagementProfileUpdate() {
		try {
			return requestReportManagerRemote.getLastSelfManagementProfileUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			SelfManagementProfileResultDTO resultDTO = new SelfManagementProfileResultDTO();
			return PortalStatusCodeUtils.getInstance().setStatusCode(resultDTO, "P200");// modulo usuarios no disp.
		}
	}
	
}
