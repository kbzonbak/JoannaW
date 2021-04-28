package bbr.b2b.logistic.notifications.managers.classes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.managers.interfaces.NotificationsReportManagerRemote;
import bbr.b2b.logistic.notifications.classes.ContactServerLocal;
import bbr.b2b.logistic.notifications.classes.NotificationDataServerLocal;
import bbr.b2b.logistic.notifications.classes.NotificationServerLocal;
import bbr.b2b.logistic.notifications.classes.NotificationTimeServerLocal;
import bbr.b2b.logistic.notifications.classes.NotificationTypeServerLocal;
import bbr.b2b.logistic.notifications.data.wrappers.ContactW;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationDataW;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationTimeW;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationTypeW;
import bbr.b2b.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.logistic.notifications.report.classes.ContactInfoDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactNotificationInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactReportArrayDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactReportDTO;
import bbr.b2b.logistic.notifications.report.classes.ContactReportInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.DeleteContactInitParamDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationCSVDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationConfigDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationDataDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTimeArrayDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTimeDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTypeDTO;
import bbr.b2b.logistic.notifications.report.classes.NotificationTypesArrayDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/NotificationsReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationsReportManager implements NotificationsReportManagerLocal, NotificationsReportManagerRemote {

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	NotificationTypeServerLocal notificationTypeServer;

	@EJB
	ContactServerLocal contactServerLocal;

	@EJB
	NotificationTimeServerLocal notificationTimeServer;

	@EJB
	VendorServerLocal vendorServerLocal;

	@EJB
	NotificationServerLocal notificationServerLocal;

	@EJB
	NotificationDataServerLocal notificationDataServerLocal;
	
	@EJB
	VendorServerLocal vendorserverlocal;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public NotificationTypesArrayDTO getNotificationTypes() {
		NotificationTypesArrayDTO resultDTO = new NotificationTypesArrayDTO();
		NotificationTypeDTO[] notificationDTO;
		try {
			OrderCriteriaDTO criteria = new OrderCriteriaDTO("visualorder", true);
			NotificationTypeW[] notifications = notificationTypeServer.getByPropertyAsArrayOrdered("visible", true, criteria);
			notificationDTO = new NotificationTypeDTO[notifications.length];
			DateConverterFactory2.copyProperties(notifications, notificationDTO, NotificationTypeW.class, NotificationTypeDTO.class);
			resultDTO.setNotificationtypes(notificationDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	public NotificationTimeArrayDTO getNotificationTimes() {
		NotificationTimeArrayDTO resultDTO = new NotificationTimeArrayDTO();
		NotificationTimeDTO[] notificationDTO;
		try {
			NotificationTimeW[] notifications = notificationTimeServer.getAllAsArray();
			notificationDTO = new NotificationTimeDTO[notifications.length];
			DateConverterFactory2.copyProperties(notifications, notificationDTO, NotificationTimeW.class,
					NotificationTimeDTO.class);
			resultDTO.setNotificationTime(notificationDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}


	public NotificationConfigDTO getContactNotificationsConfig(Long contactid) {
		NotificationConfigDTO resultDTO = new NotificationConfigDTO();

		try {

			Long[] typeAssigned = notificationServerLocal.getNotificationsTypeAssignedByContact(contactid);
			Long[] hoursAssigned = notificationServerLocal.getNotificationsTimeAssignedByContact(contactid);

			resultDTO.setHoursAssigned(hoursAssigned);
			resultDTO.setNotificationTypeAssigned(typeAssigned);

		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;

	}

	public ContactReportArrayDTO getContactsReport(ContactReportInitParamDTO initParamDTO, Boolean isPaginated) {
		ContactReportArrayDTO resultDTO = new ContactReportArrayDTO();
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserverlocal.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			Long vendorid = vendorW.getId();
			ContactReportDTO[] contactreport = contactServerLocal.getContactsReport(vendorid, initParamDTO.getPagenumber(), initParamDTO.getPagerows(), initParamDTO.getOrder(), isPaginated);
			if (initParamDTO.getIsByFilter()) {
				int total = contactServerLocal.countContactsReport(vendorid);
				int rows = initParamDTO.getPagerows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPagenumber());
				pageInfo.setRows(contactreport.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageinfo(pageInfo);
			}
			resultDTO.setContacts(contactreport);
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}


	public BaseResultDTO doAddContact(ContactNotificationInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserverlocal.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			Long vendorid = vendorW.getId();
			
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
			properties[1] = new PropertyInfoDTO("email", "contactemail", initParamDTO.getContactemail());
			List<ContactW> contactWs = new ArrayList<>();
			contactWs = contactServerLocal.getByProperties(properties);
			
			if(! contactWs.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "8000");
			}
			ContactW contactW = new ContactW();
			contactW.setEmail(initParamDTO.getContactemail());
			contactW.setLastname(initParamDTO.getContactlastname());
			contactW.setName(initParamDTO.getContactname());
			contactW.setPosition(initParamDTO.getContactposition());
			contactW.setVendorid(vendorid);

			contactW = contactServerLocal.addContact(contactW);

			// Enviar correo
			String subject = "Aviso de suscripción a notificaciones logísticas";
			String mailsender = LogisticConstants.getMailSender();
			String mailSession = LogisticConstants.getMAIL_SESSION();
			String[] mailto = new String[] { contactW.getEmail() };

			String body = "Estimado(a), " + contactW.getName() + " " + contactW.getLastname() + ".<br><br>"
					+ "El presente correo es para informar a usted que se ha suscrito como contacto logístico de  "
					+ vendorW.getName() + " en el B2B de Hites. <br><br>"  + //
					"Atte.<br>" + //										
					"B2B Hites<br><br>" + // 										
					"<b>Favor no responder este correo dado que es generado de manera automática por el sistema B2B.</b>";
			

			body += "<br><br><b>Hites</b>";

			schedulermailmanager.doAddMailToQueue(mailsender, mailto, null, null, subject, body, mailSession, "NOTIFICACION", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}
	

	public ContactInfoDTO getContactInfo(Long contactid) {
		ContactInfoDTO resultDTO = new ContactInfoDTO();
		try {
			ContactW contactw = contactServerLocal.getById(contactid);
			resultDTO.setContactemail(contactw.getEmail());
			resultDTO.setContactid(contactw.getId());
			resultDTO.setContactlastname(contactw.getLastname());
			resultDTO.setContactname(contactw.getName());
			resultDTO.setContactposition(contactw.getPosition());
			VendorW vendor = null;
			try {
				vendor = vendorserverlocal.getById(contactw.getVendorid());
			} catch (OperationFailedException | NotFoundException e1) {
				e1.printStackTrace();
			}
			if (vendor == null) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "1902");
			}
			String vendorcode = vendor.getCode().toString();
			resultDTO.setVendorcode(vendorcode);
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public ContactInfoDTO doUpdateContact(ContactInfoDTO initParamDTO) {
		ContactInfoDTO resultDTO = new ContactInfoDTO();
		try {
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserverlocal.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			Long vendorid = vendorW.getId();
			properties[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorid);
			properties[1] = new PropertyInfoDTO("email", "contactemail", initParamDTO.getContactemail());
			List<ContactW> contactVendor = new ArrayList<>();
			try {
				contactVendor = contactServerLocal.getByProperties(properties);
			} catch (OperationFailedException|NotFoundException e) {
				e.printStackTrace();
			}
			if (contactVendor.size() > 1) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "8000");
			}

			ContactW contactw = contactServerLocal.getById(initParamDTO.getContactid());
			contactw.setEmail(initParamDTO.getContactemail());
			contactw.setName(initParamDTO.getContactname());
			contactw.setLastname(initParamDTO.getContactlastname());
			contactw.setPosition(initParamDTO.getContactposition());
			contactServerLocal.updateContact(contactw);

			DateConverterFactory2.copyProperties(initParamDTO, resultDTO);
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "0");
	}

	
	public BaseResultDTO doAddOrUpdateNotificationConfig(NotificationConfigDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		Long contactid = initParamDTO.getContactid();
		try {
			notificationServerLocal.deleteByProperty("contact.id", contactid);
			notificationServerLocal.flush();
			for (int i = 0; i < initParamDTO.getNotificationTypeAssigned().length; i++) {
				for (int j = 0; j < initParamDTO.getHoursAssigned().length; j++) {
					NotificationW notification = new NotificationW();
					notification.setContactid(contactid);
					notification.setNotificationtypeid(initParamDTO.getNotificationTypeAssigned()[i]);
					notification.setNotificationtimeid(initParamDTO.getHoursAssigned()[j]);
					notificationServerLocal.addNotification(notification);
				}
			}
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	public BaseResultDTO doDeleteContact(DeleteContactInitParamDTO initParamDTO) {
		BaseResultDTO result = new BaseResultDTO();
		try {
			for (int i = 0; i < initParamDTO.getContactids().length; i++) {
				notificationServerLocal.deleteByProperty("contact.id", initParamDTO.getContactids()[i]);
				contactServerLocal.deleteElement(initParamDTO.getContactids()[i]);
			}
		} catch (OperationFailedException|NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} 
		return LogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
	}

	public int doDeleteOldNotificationData() {
		int result = 0;
		try {
			LocalDateTime now = LocalDateTime.now().toLocalDate().atStartOfDay();
			result = notificationDataServerLocal.doDeleteOldNotificationData(now);
			System.out.println("Se ha eliminado " + result + " registros de Notificaciones");
			
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al eliminar registros de notificaciones");
			e.printStackTrace();
		}
		return result;
	}

	public ContactNotificationDTO[] getContactToNotification(LocalDateTime hour) {
		ContactNotificationDTO[] array = null;
		try {
			array = notificationServerLocal.getContactsToNotification(hour);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
		return array;
	}


	public NotificationDataDTO[] getNotificationsDataByContact(Long contactid, LocalDateTime when) {
		NotificationDataDTO[] array = null;
		LocalDateTime since;
		LocalDateTime until;
		try {
			since = when.with(LocalTime.MIN);
			until = when.with(LocalTime.MAX);
			
			array = notificationServerLocal.getNotificationsDataByContact(contactid, since, until);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
		return array;
	}
	
	public List<NotificationCSVDTO> doExportCSVNotificationsData(Long contactid, Long vendorid, LocalDateTime when) {
		List<NotificationCSVDTO> list = new ArrayList<NotificationCSVDTO>();
		LocalDateTime since, until;
		try {

			since = when.with(LocalTime.MIN);
			until = when.with(LocalTime.MAX);
			
			list = notificationServerLocal.doExportCSVNotificationsData(contactid, vendorid, since, until);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
		return list;
	}

	public void doAddNotificationData(Long vendorid, String number, String type) {
		NotificationDataW data = new NotificationDataW();
		try {
			LocalDateTime now = LocalDateTime.now();
			
			NotificationTypeW[] notifications = notificationTypeServer.getByPropertyAsArray("code", type);
			data.setLoaddate(now);
			data.setNotificationtypeid(notifications[0].getId());
			data.setNumber(number);
			data.setVendorid(vendorid);
			System.out.println("Agregando evento de notificación... LoadDate:" + data.getLoaddate());
			notificationDataServerLocal.addNotificationData(data);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
	}


}
