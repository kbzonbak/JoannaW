package bbr.b2b.regional.logistic.notifications.managers.classes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.common.util.StringUtils;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.notifications.classes.ContactServerLocal;
import bbr.b2b.regional.logistic.notifications.classes.NotificationDataServerLocal;
import bbr.b2b.regional.logistic.notifications.classes.NotificationServerLocal;
import bbr.b2b.regional.logistic.notifications.classes.NotificationTimeServerLocal;
import bbr.b2b.regional.logistic.notifications.classes.NotificationTypeServerLocal;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactInfoDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactNotificationInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.ContactReportInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.DeleteContactInitParam;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationConfigDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationDataDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTimeArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTimeDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTypeDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.NotificationTypesArrayDTO;
import bbr.b2b.regional.logistic.notifications.data.classes.VendorNoticationDTO;
import bbr.b2b.regional.logistic.notifications.data.wrappers.ContactW;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationDataW;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTimeW;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationTypeW;
import bbr.b2b.regional.logistic.notifications.data.wrappers.NotificationW;
import bbr.b2b.regional.logistic.notifications.managers.interfaces.NotificationsReportManagerLocal;
import bbr.b2b.regional.logistic.notifications.managers.interfaces.NotificationsReportManagerRemote;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/NotificationsReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NotificationsReportManager implements NotificationsReportManagerLocal,NotificationsReportManagerRemote{
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	@EJB
	private VendorServerLocal vendorserverlocal;
	
	@EJB NotificationTypeServerLocal notificationTypeServer;
	
	@EJB ContactServerLocal contactServerLocal;
	
	@EJB NotificationTimeServerLocal notificationTimeServer;
	
	@EJB VendorServerLocal vendorServerLocal;
	
	@EJB NotificationServerLocal notificationServerLocal;
	
	@EJB NotificationDataServerLocal notificationDataServerLocal;
	
	public NotificationTypesArrayDTO getNotificationTypes(){
		NotificationTypesArrayDTO result = new NotificationTypesArrayDTO();
		NotificationTypeDTO[] notificationDTO;
		try{
			OrderCriteriaDTO criteria = new OrderCriteriaDTO("visualorder",true);
			NotificationTypeW[] notifications = notificationTypeServer.getByPropertyAsArrayOrdered("visible", true, criteria);
			notificationDTO = new NotificationTypeDTO[notifications.length];
			DateConverterFactory2.copyProperties(notifications, notificationDTO, NotificationTypeW.class, NotificationTypeDTO.class);
			result.setNotificationtypes(notificationDTO);
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public NotificationTimeArrayDTO getNotificationTimes(){
		NotificationTimeArrayDTO result = new NotificationTimeArrayDTO();
		NotificationTimeDTO[] notificationDTO;
		try{
			NotificationTimeW[] notifications = notificationTimeServer.getAllAsArray();
			notificationDTO = new NotificationTimeDTO[notifications.length];
			DateConverterFactory2.copyProperties(notifications, notificationDTO, NotificationTimeW.class, NotificationTimeDTO.class);
			result.setNotificationTime(notificationDTO);
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public void createTime(){
		
		Locale locale = null;
		locale = new Locale("es", "CL");
		Calendar cal = null;
		cal = Calendar.getInstance(locale);
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);		
		cal.set(Calendar.HOUR_OF_DAY, 0); //Inicio 7:00
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		for(Integer i = 0;i<24;i++){
			NotificationTimeW time = new NotificationTimeW();
			
			time.setHourname(StringUtils.getInstance().addLeadingZeros(i.toString(),2)+":00");
			time.setHourdate(cal.getTime());
			try {
				time = notificationTimeServer.addNotificationTime(time);
			} catch (AccessDeniedException e) {

				e.printStackTrace();
			} catch (OperationFailedException e) {

				e.printStackTrace();
			} catch (NotFoundException e) {

				e.printStackTrace();
			}
			cal.add(Calendar.HOUR_OF_DAY, 1);
			
			System.out.println(time.getHourname()+"-"+time.getHourdate()+"\n");
		}
		
	}
	
	public BaseResultDTO doAddContact(ContactNotificationInitParam initParam){
		BaseResultDTO result = new BaseResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParam.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("vendor.id","vendorid",vendor.getId());
		properties[1] = new PropertyInfoDTO("email","contactemail",initParam.getContactemail());
		List<ContactW> ContactWs = new ArrayList<ContactW>();
		try {
			ContactWs = contactServerLocal.getByProperties(properties);
		} catch (OperationFailedException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		if(ContactWs.size() != 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L9000");
		}
		
		ContactW contactw = new ContactW();
		contactw.setEmail(initParam.getContactemail());
		contactw.setLastname(initParam.getContactlastname());
		contactw.setName(initParam.getContactname());
		contactw.setPosition(initParam.getContactposition());
		contactw.setVendorid(vendor.getId());
		
		try {
			contactw = contactServerLocal.addContact(contactw);
			//VendorW vendor = vendorServerLocal.getById(contactw.getVendorid());
		
//			for(int i = 0;i<initParam.getNotificationtypesids().length;i++){
//				NotificationTypeW notificationtype = notificationTypeServer.getById(initParam.getNotificationtypesids()[i]);
//				notificationTypesAssigned.add(notificationtype.getDescription());
//				for(int j=0;j<initParam.getNotificationtimeids().length;j++){
//					NotificationW notification = new NotificationW();
//					notification.setContactid(contactw.getId());
//					notification.setNotificationtypeid(initParam.getNotificationtypesids()[i]);
//					notification.setNotificationtimeid(initParam.getNotificationtimeids()[j]);
//					notificationServerLocal.addNotification(notification);
//					if(i==0){
//						NotificationTimeW notificationtime = notificationTimeServer.getById(initParam.getNotificationtimeids()[j]);
//						notificationTimesAssigned.add(notificationtime.getHourname());
//					}
//				}
//			}
			
		//enviar correo
			String subject = "B2B Paris: Aviso de suscripción a notificaciones logísticas"; 
			Mailer mailer = Mailer.getInstance();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String[] mailto = new String[]{contactw.getEmail()};
			
			String body = 
				"Estimado(a), "+contactw.getName()+" "+contactw.getLastname()+".<br><br>" +
				"El presente correo es para informar a usted que se ha suscrito como contacto logístico de  "+vendor.getName()+" en el B2B de Paris-Johnson." +
				"<br><br><b><em>Esto tiene finalidad de alertar el envío de OC de stock, pre-distribuidas y VEV (cualquiera sea el subtipo PD, CD, PD Courier). No sustituye la autogestión de revisar en B2B si tiene órdenes de Compras o Gestiones pendientes.</b></em>"+				
				"<br><br>Atte.";
				
			body+="<br><br><imagen>"+B2BSystemPropertiesUtil.getProperty("logoMail")+"</imagen>";
		
			//mailer.sendMailBySession(mailto, null, mailsender, subject, body, mailSession);	
			mailer.sendMailBySession(mailto, null, null, mailsender, subject, body, true, null, mailSession);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public BaseResultDTO doDeleteContact(DeleteContactInitParam initParam){
		BaseResultDTO result = new BaseResultDTO();
		try {
			for(int i = 0 ; i<initParam.getContactids().length;i++){
				contactServerLocal.deleteElement(initParam.getContactids()[i]);
			}
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
	}
	
	public ContactReportArrayDTO getContactsReport(ContactReportInitParam initParam, Boolean isPaginated){
		ContactReportArrayDTO result = new ContactReportArrayDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParam.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try{
			ContactReportDTO[] contactreport = contactServerLocal.getContactsReport(vendor.getId(), initParam.getPagenumber(), initParam.getPagerows(), initParam.getOrder(), isPaginated);
			if(initParam.getIsByFilter()){
				int total = contactServerLocal.countContactsReport(vendor.getId());
				int rows = initParam.getPagerows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParam.getPagenumber());
				pageInfo.setRows(contactreport.length);
				pageInfo.setTotalpages((total % rows)!= 0 ? (total/rows)+1 : (total/rows));
				pageInfo.setTotalrows(total);
				result.setPageinfo(pageInfo);
			}
			result.setContacts(contactreport);
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public ContactInfoDTO doUpdateContact(ContactInfoDTO initParam){
		ContactInfoDTO result = new ContactInfoDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParam.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try{
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id","vendorid",vendor.getId());
			properties[1] = new PropertyInfoDTO("email","contactemail",initParam.getContactemail());
			List<ContactW> contactVendor = new ArrayList<ContactW>();
			try {
				contactVendor = contactServerLocal.getByProperties(properties);
			} catch (OperationFailedException e) {
				e.printStackTrace();
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			if(contactVendor.size() > 1){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L9000");
			}
			
			ContactW contactw = contactServerLocal.getById(initParam.getContactid());
			contactw.setEmail(initParam.getContactemail());
			contactw.setName(initParam.getContactname());
			contactw.setLastname(initParam.getContactlastname());
			contactw.setPosition(initParam.getContactposition());
			contactw = contactServerLocal.updateContact(contactw);
			
			DateConverterFactory2.copyProperties(initParam, result);
		}catch(Exception e){
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
	}
	
	public ContactInfoDTO getContactInfo(Long contactid){
		ContactInfoDTO result = new ContactInfoDTO();
		try{
			ContactW contactw = contactServerLocal.getById(contactid);
			VendorW vendor = vendorserverlocal.getByPropertyAsSingleResult("id", contactw.getVendorid());
			
			result.setContactemail(contactw.getEmail());
			result.setContactid(contactw.getId());
			result.setContactlastname(contactw.getLastname());
			result.setContactname(contactw.getName());
			result.setContactposition(contactw.getPosition());
			result.setVendorcode(vendor.getRut());
			
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public NotificationConfigDTO getVendorNotificationsConfig(String vendorcode){
		NotificationConfigDTO result = new NotificationConfigDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try{
			//VendorW vendor = vendorserverlocal.getByPropertyAsSingleResult("id", vendorid);
			Long[] typeAssigned = notificationServerLocal.getNotificationsTypeAssignedByVendor(vendor.getId());
			Long[] hoursAssigned = notificationServerLocal.getNotificationsTimeAssignedByVendor(vendor.getId());
			
			result.setVendorcode(vendor.getRut());
			result.setHoursAssigned(hoursAssigned);
			result.setNotificationTypeAssigned(typeAssigned);
			
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
		
	}
	
	public BaseResultDTO doAddOrUpdateNotificationConfig(NotificationConfigDTO initParam){
		BaseResultDTO result = new BaseResultDTO();
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserverlocal.getByPropertyAsArray("rut", initParam.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		try{
			notificationServerLocal.deleteByProperty("vendor.id", vendor.getId());
			notificationServerLocal.flush();
			for(int i = 0;i<initParam.getNotificationTypeAssigned().length;i++){
				for(int j=0;j<initParam.getHoursAssigned().length;j++){
					NotificationW notification = new NotificationW();
					notification.setVendorid(vendor.getId());
					notification.setNotificationtypeid(initParam.getNotificationTypeAssigned()[i]);
					notification.setNotificationtimeid(initParam.getHoursAssigned()[j]);
					notificationServerLocal.addNotification(notification);
				}
			}
		}catch(Exception e){
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public void doAddNotificationData(String vendorcode, String number,String type){
		NotificationDataW data = new NotificationDataW();
		
		
		try{
			VendorW vendor = vendorserverlocal.getByPropertyAsSingleResult("rut", vendorcode);
			NotificationTypeW notTypeW = notificationTypeServer.getByPropertyAsSingleResult("code", type);
			data.setLoaddate(new Date());
			data.setNotificationtypeid(notTypeW.getId());
			data.setNumber(number);
			data.setVendorid(vendor.getId());
			notificationDataServerLocal.addNotificationData(data);
		}catch(Exception e){
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
	}
	
	public VendorNoticationDTO[] getVendorsToNotification(Date hour){
		VendorNoticationDTO[] array = null;
		try{
			Locale locale = null;
			locale = new Locale("es", "CL");
			Calendar cal = Calendar.getInstance(locale);
			cal.setLenient(false);
			cal.setTime(hour);
			cal.set(Calendar.YEAR, 1970);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);	
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			array = notificationServerLocal.getVendorsToNotification(cal.getTime());
		}catch(Exception e){
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
		return array;
	}
	
	public NotificationDataDTO[] getNotificationsDataByVendor(String vendorcode, Date hour){
		NotificationDataDTO[] array = null;
		

		
		try{
			VendorW vendor = vendorserverlocal.getByPropertyAsSingleResult("rut", vendorcode);
			
			Locale locale = null;
			locale = new Locale("es", "CL");
			Calendar cal = Calendar.getInstance(locale);
			Date since = null;
			Date until = null;
			cal.setLenient(false);
			cal.setTime(hour);
			if (cal.get(Calendar.HOUR) == 00) {
				cal.add(Calendar.HOUR_OF_DAY, -1);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				until = cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 1);
				since = cal.getTime();
			} else {
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				until = cal.getTime();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 1);
				since = cal.getTime();
			}
			array = notificationServerLocal.getNotificationsDataByVendor(vendor.getId(), since, until);
		} catch (Exception e) {
			System.out.println("Ha ocurrido un error al agregar registro de carga para notificaciones");
			e.printStackTrace();
		}
		return array;

	}
	
	
	public int doDeleteOldNotificationData(){
		int result = 0;
		try{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			result = notificationDataServerLocal.doDeleteOldNotificationData(cal.getTime());
			System.out.println("Se ha eliminado "+result+" registros de Notificaciones");
		}catch(Exception e){
			System.out.println("Ha ocurrido un error al eliminar registros de notificaciones");
			e.printStackTrace();
		}
		return result;
	}

}
