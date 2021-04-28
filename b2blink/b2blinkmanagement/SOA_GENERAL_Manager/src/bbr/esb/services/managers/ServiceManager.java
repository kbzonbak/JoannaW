package bbr.esb.services.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.persistence.LockModeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.common.adtclasses.classes.PropertyInfoDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.dataset.util.DataColumn;
import bbr.common.dataset.util.DataColumnStyle;
import bbr.common.dataset.util.DataColumnStyleInfo;
import bbr.common.dataset.util.DataRow;
import bbr.common.dataset.util.DataTable;
import bbr.common.dataset.util.XLSConverterJXL;
import bbr.common.factories.BeanExtenderFactory;
import bbr.common.factories.IDateTimeConstants;
import bbr.esb.events.data.classes.DocumentToResendInitParamDTO;
import bbr.esb.events.data.classes.DocumentTraceDTO;
import bbr.esb.events.data.classes.DocumentTraceLastDaysDTO;
import bbr.esb.events.data.classes.DocumentTraceTypeDTO;
import bbr.esb.events.data.classes.DocumentsToResendInitParamDTO;
import bbr.esb.events.data.classes.FileEventDTO;
import bbr.esb.events.data.classes.FileEventDataDTO;
import bbr.esb.events.data.classes.ResendJsonDTO;
import bbr.esb.events.data.classes.ServiceEventDTO;
import bbr.esb.events.data.classes.ServiceEventDataDTO;
import bbr.esb.events.data.classes.TicketEventDTO;
import bbr.esb.events.data.classes.TicketEventDataDTO;
import bbr.esb.events.data.classes.TicketEventResultDTO;
import bbr.esb.events.data.classes.TicketEventStatusDataDTO;
import bbr.esb.events.data.classes.TicketStateDTO;
import bbr.esb.events.data.classes.TicketStateTypeDTO;
import bbr.esb.events.facade.DocumentTraceLastDaysServerLocal;
import bbr.esb.events.facade.DocumentTraceServerLocal;
import bbr.esb.events.facade.DocumentTraceTypeServerLocal;
import bbr.esb.events.facade.FileEventServerLocal;
import bbr.esb.events.facade.ServiceEventServerLocal;
import bbr.esb.events.facade.TicketEventServerLocal;
import bbr.esb.events.facade.TicketStateServerLocal;
import bbr.esb.events.facade.TicketStateTypeServerLocal;
import bbr.esb.services.data.classes.CompaniesServiceStatusDataDTO;
import bbr.esb.services.data.classes.CompaniesServiceStatusReportDTO;
import bbr.esb.services.data.classes.ContractedServiceDTO;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.data.classes.ContractedServiceForCustomDTO;
import bbr.esb.services.data.classes.ContractedServiceMonitorDataDTO;
import bbr.esb.services.data.classes.InitParamCSDTO;
import bbr.esb.services.data.classes.ServiceDTO;
import bbr.esb.services.data.classes.SiteDTO;
import bbr.esb.services.data.classes.SiteServiceDTO;
import bbr.esb.services.data.classes.SiteServiceReportDTO;
import bbr.esb.services.entities.ContractedServiceKey;
import bbr.esb.services.entities.SiteServiceKey;
import bbr.esb.services.facade.ContractedServiceServerLocal;
import bbr.esb.services.facade.MessageFormatServerLocal;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.services.facade.SiteServiceServerLocal;
import bbr.esb.services.facade.WSEndpointServerLocal;
import bbr.esb.storage.data.classes.S3FileDataDTO;
import bbr.esb.storage.managers.FileServiceManagerLocal;
import bbr.esb.users.data.classes.AccessDTO;
import bbr.esb.users.data.classes.AccessDataDTO;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.data.classes.MessageDataDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderDataDTO;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.users.data.classes.MessageFormatDTO;
import bbr.esb.users.data.classes.MessageFormatDataDTO;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.data.classes.WSEndpointDTO;
import bbr.esb.users.data.classes.WSEndpointDataDTO;
import bbr.esb.users.entities.AccessKey;
import bbr.esb.users.entities.UserCompanyKey;
import bbr.esb.users.facade.AccessServerLocal;
import bbr.esb.users.facade.CompanyServerLocal;
import bbr.esb.users.facade.FTPMessageFolderServerLocal;
import bbr.esb.users.facade.MessageFolderServerLocal;
import bbr.esb.users.facade.MessageFolderTypeServerLocal;
import bbr.esb.users.facade.UserCompanyServerLocal;
import bbr.esb.users.facade.UserServerLocal;
import bbr.esb.users.facade.UserTypeServerLocal;
import bbr.esb.utils.BBRStringUtils;
import bbr.esb.utils.JsonUtils;


@Stateless(name = "managers/ServiceManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceManager implements ServiceManagerLocal {

	private static final String NOT_RUT_COMPANY = "Rut no disponible";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm"); // 201507060833

	private static final Logger log = LoggerFactory.getLogger(ServiceManager.class);

	@EJB
	AccessServerLocal accessserver;

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	ContractedServiceServerLocal contractedserviceserver;

	@EJB
	FileEventServerLocal fileeventserver;

	@EJB
	MessageFolderServerLocal folderserver;

	@EJB
	MessageFormatServerLocal formatserver;

	@EJB
	ServiceEventServerLocal serviceeventserver;

	@EJB
	ServiceServerLocal serviceserver;

	@EJB
	SiteServerLocal siteserver;

	@EJB
	SiteServiceServerLocal siteserviceserver;

	@EJB
	UserCompanyServerLocal usercompanyserver;

	@EJB
	UserServerLocal userserver;

	@EJB
	UserTypeServerLocal usertypeserver;
	
	@EJB
	TicketEventServerLocal ticketeventserver;
	
	@EJB
	TicketStateTypeServerLocal ticketstatetypeserver;

	@EJB
	TicketStateServerLocal ticketstateserver;
	
	@EJB
	WSEndpointServerLocal wsendpointserver;
	
	@EJB
	MessageFolderTypeServerLocal foldertypeserver;
	
	@EJB
	FTPMessageFolderServerLocal ftpfolderserver;
	
	@EJB
	DocumentTraceServerLocal documenttraceserver;	
	
	@EJB
	DocumentTraceLastDaysServerLocal documenttracelastdaysserver;	
	
	@EJB
	DocumentTraceTypeServerLocal documentracetypeserver;	
	
	@EJB
	FileServiceManagerLocal fileservicemanager;

	
	
	public AccessDTO addAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return accessserver.addIdentifiable(access);
	}

	public MessageFolderDTO addMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Validar que no existe otra casilla con la misma ruta para el mismo proveedor
		if (folder.getPath() == null || folder.getPath().trim().length() == 0)
			throw new AccessDeniedException("La ruta especificada no es válida");
		PropertyInfoDTO p1 = new PropertyInfoDTO("path", "path", folder.getPath());
		PropertyInfoDTO p2 = new PropertyInfoDTO("company.id", "companyid", folder.getCompanykey());
		List<MessageFolderDTO> listFolders = folderserver.getByProperties(new PropertyInfoDTO[] { p1, p2 });
		if (listFolders != null && !listFolders.isEmpty() && !listFolders.get(0).getId().equals(folder.getId()))
			throw new AccessDeniedException("Ya existen casillas asociadas a la misma ruta para el mismo proveedor");
		return folderserver.addMessageFolder(folder);
	}

	public ServiceDTO addService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return serviceserver.addService(service);
	}

	public ServiceEventDTO addServiceEvent(ServiceEventDTO event) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return serviceeventserver.addServiceEvent(event);
	}

	public SiteDTO addSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserver.addSite(site);
	}

	public void deleteMessageFolder(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Validar si la casilla tiene asociado un servicio
		List<ContractedServiceDTO> listServices = contractedserviceserver.getByProperty("folder.id", folderid);
		if (listServices != null && !listServices.isEmpty())
			throw new AccessDeniedException("No se puede borrar la casilla, ya que existen servicios contratados asociados a ella");
		folderserver.deleteMessageFolder(folderid);
	}

	public void deleteService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		serviceserver.deleteService(serviceid);
	}

	public void deleteSite(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		siteserver.deleteSite(siteid);
	}

	public SiteServiceDTO doActivateInactivateSiteService(Long siteid, Long serviceid, boolean enabled) throws AccessDeniedException, OperationFailedException, NotFoundException {
		SiteServiceKey sskey = new SiteServiceKey(siteid, serviceid);
		SiteServiceDTO siteservice = siteserviceserver.getById(sskey);
		siteservice.setActive(enabled);
		siteservice = siteserviceserver.updateIdentifiable(siteservice);
		SiteDTO site = siteserver.getById(siteid);
		ServiceDTO service = serviceserver.getById(serviceid);
		if (enabled)
			log.info("El servicio '" + service.getName() + "' se ha ACTIVADO para el sitio '" + site.getName() + "'");
		else
			log.info("El servicio '" + service.getName() + "' se ha DESACTIVADO para el sitio '" + site.getName() + "'");
		return siteservice;
	}

	public FileEventDTO doAddFileEvent(String sitename, String servicename, String accesscode, String documentid, String filename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		addlog("Nuevo evento de documento", sitename, servicename, accesscode, documentid, "flujointerno");		
		try {			
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				addlog("No existe un sitio registrado con el nombre especificado", sitename);
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				addlog("No existe un servicio registrado con el nombre especificado", sitename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					addlog("El servicio se encuentra inactivo para el sitio especificado", sitename);
					return null;
				}
			} catch (NotFoundException e) {
				addlog("No existe el servicio registrado para el sitio especificado", sitename);
				return null;
			}
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				addlog("No existen accesos asociados al sitio especificado ", sitename);
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				addlog("El servicio no está contratado para el sitio y la empresa especificados", sitename);
				return null;
			}
			if (contractedService == null || !contractedService.getActive()) {
				addlog("El servicio contratado se encuentra inactivo para el sitio y la empresa especificados", sitename);
				return null;
			}
			// Verificar que el archivo no esté ya registrado
			List<FileEventDTO> listFE = fileeventserver.getByProperty("filename", filename);
			if (listFE != null && !listFE.isEmpty()) {
				addlog("El archivo ya se encuentra registrado : " + filename, sitename);
				return null;
			}
			FileEventDTO event = new FileEventDTO();
			event.setDatecreated(new Date());
			event.setDocumentid(documentid);
			event.setFilename(filename);
			event.setReceived(false);
			event.setInformed(false);
			event.setSitekey(site.getId());
			event.setServicekey(service.getId());
			event.setCompanykey(access.getCompanykey());
			event = fileeventserver.addFileEvent(event);
			addlog("Documento disponible para el proveedor: "+filename, sitename, servicename, accesscode, documentid, "documentodisponible");			
			return event;
		} catch (Exception e) {
			addlog("Error al almacenar evento de archivo creado", sitename);
			return null;
		}
	}

	public ServiceEventDTO[] doAddGenericServiceEvent(String sitename, String servicename) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
//		addlog("Nueva evento de servicio generico, sin proveedor ( notificacion )", sitename, servicename, "", "", "carga");
		try {
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {				
				addlog("No existe un sitio registrado con el nombre especificado  " , sitename);
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				addlog("No existe un servicio registrado con el nombre especificado", sitename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					addlog("El servicio se encuentra inactivo para el sitio especificado ", sitename);					
					return null;
				}
			} catch (NotFoundException e) {
				addlog("No existe el servicio registrado para el sitio especificado " , sitename);
				return null;
			}
			// Buscar los accesos asociados al sitio
			AccessDTO[] accesses = accessserver.getByPropertyAsArray("site.id", site.getId());
			if (accesses == null || accesses.length == 0) {
				addlog("No existen accesos asociados al sitio especificado ", sitename);
				return null;
			}
			List<ServiceEventDTO> listEvents = new ArrayList<ServiceEventDTO>(accesses.length);
			for (int i = 0; i < accesses.length; i++) {
				AccessDTO access = accesses[i];
				// Verificar que el servicio esté contratado y activo
				ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
				ContractedServiceDTO contractedService = null;
				try {
					contractedService = contractedserviceserver.getById(cskey);
				} catch (NotFoundException e) {
					// OK, continuar con la siguiente iteración
					continue;
				}
				if (contractedService == null || !contractedService.getActive()) {
					// OK, continuar con la siguiente iteración
					continue;
				}
				ServiceEventDTO event = new ServiceEventDTO();
				event.setProcessed(false);
				event.setDatecreated(new Date());
				event.setSitekey(site.getId());
				event.setServicekey(service.getId());
				event.setCompanykey(access.getCompanykey());
				event.setCustom(contractedService.getCustom() == null ? "" : contractedService.getCustom());
				event = serviceeventserver.addServiceEvent(event);
				listEvents.add(event);
				addlog("Se ha registrado exitosamente un evento de servicio para la empresa : " + access.getCode(), sitename, servicename, "", "", "carga");
			}
			ServiceEventDTO[] result = (ServiceEventDTO[]) listEvents.toArray(new ServiceEventDTO[listEvents.size()]);
			return result;
		} catch (Exception e) {
			addlog("Error al almacenar evento maviso de servicio", sitename);
			return null;
		}
	}
	
	public 	BaseResultDTO doAddServiceEventByContracted(DocumentsToResendInitParamDTO documentstoresend ) {
		
		BaseResultDTO result = new BaseResultDTO();	
		result.setStatuscode("0");
		result.setStatusmessage("OK");
		
		ArrayList<DocumentToResendInitParamDTO> documents = documentstoresend.getDocuments();
		Map<String, String> resendmap= new TreeMap<String, String>();
			
		for (DocumentToResendInitParamDTO documentToResendInitParamDTO : documents) {
			
			addlogById("Reprocesando documento: "+documentToResendInitParamDTO.getDocument(),Integer.parseInt(documentToResendInitParamDTO.getSiteid()),
					Integer.parseInt(documentToResendInitParamDTO.getServiceid()), documentToResendInitParamDTO.getCompanyid(),	documentToResendInitParamDTO.getDocument(), "reenvio");
			
			String key=documentToResendInitParamDTO.getCompanyid()+","+documentToResendInitParamDTO.getServiceid()+","+documentToResendInitParamDTO.getSiteid();
			
			if(resendmap.containsKey(key)){
				String docs = resendmap.get(key);
				docs=docs+","+documentToResendInitParamDTO.getDocument();
				resendmap.replace(key, docs);
			}else{
				resendmap.put(key, documentToResendInitParamDTO.getDocument());
			}
		}
		
		for (Map.Entry<String, String> entry : resendmap.entrySet()) {
			String[] ids=entry.getKey().split(",");			
			
			ContractedServiceDataDTO contractedService = new ContractedServiceDataDTO();
			contractedService.setCompanykey(Long.parseLong(ids[0]));
			contractedService.setServicekey(Long.parseLong(ids[1]));
			contractedService.setSitekey(Long.parseLong(ids[2]));
			contractedService.setActive(true);
			
			contractedService.setCompanyname(ids[0]);
			contractedService.setSitename(ids[2]);
			
//			EN ESTA PARTE SE DEBE CREAR EL JSON PARA REENVIO
			ResendJsonDTO json = new ResendJsonDTO();
			String[] orders =entry.getValue().split(",");
			Long[] orderslong =  new Long[orders.length];
			for (int i = 0; i < orders.length; i++) {
				orderslong[i] = Long.parseLong(orders[i]);
			}
			json.setOrders(orderslong);			
					 

			try {
				ServiceEventDTO evt = doAddServiceEventByContracted(contractedService,JsonUtils.getJsonFromObject(json, ResendJsonDTO.class));
				if(evt==null){
					result.setStatuscode("1");
					result.setStatusmessage("Ocurrio un problema al reenviar las ordenes seleccionadas");
				}
				
			} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
				result.setStatuscode("1");
				result.setStatusmessage("Ocurrio un problema al reenviar las ordenes seleccionadas");
				return result;
			}
		}
		
		return result;
		
	}
	public ServiceEventDTO doAddServiceEventByContracted(ContractedServiceDataDTO contractedService, String resendingdata) throws AccessDeniedException, OperationFailedException, NotFoundException {
//		valida que el contrato este activo
		try{
			if (contractedService == null || !contractedService.getActive()) {
				addlog("El servicio contratado se encuentra inactivo para el sitio y la empresa especificados ",
						contractedService.getSitename());
				return null;
			}
			// 2011-12-20 Antes de registrar el evento, validar si ya existe un evento similar no procesado
			PropertyInfoDTO[] props = new PropertyInfoDTO[5];
			props[0] = new PropertyInfoDTO("site.id", "sitekey", contractedService.getSitekey());
			props[1] = new PropertyInfoDTO("service.id", "servicekey", contractedService.getServicekey());
			props[2] = new PropertyInfoDTO("company.id", "companykey", contractedService.getCompanykey());
			props[3] = new PropertyInfoDTO("processed", "processed", false);			
			props[4] = new PropertyInfoDTO("resenddata","resenddata",resendingdata);
			
			
			List<ServiceEventDTO> prevevents = serviceeventserver.getByProperties(props);
			if (prevevents != null && !prevevents.isEmpty()) {
				addlog("Ya existen eventos similares no procesados, para el sitio y la empresa especificados ",
						contractedService.getSitename());
				return null;
			}
			ServiceEventDTO event = new ServiceEventDTO();
			event.setProcessed(false);
			event.setDatecreated(new Date());
			event.setSitekey(contractedService.getSitekey());
			event.setServicekey(contractedService.getServicekey());
			event.setCompanykey(contractedService.getCompanykey());			
			event.setResenddata(resendingdata);
			
			
			event = serviceeventserver.addServiceEvent(event);
			
			addlog("Se ha registrado exitosamente un evento de servicio para la empresa ",
					contractedService.getSitename(),contractedService.getServicename() , "", "", "carga");
			
			
			return event;
		} catch (Exception e) {
			log.error("Error al almacenar evento de servicio", e);
			return null;
		}
	}

	public ServiceEventDTO doAddServiceEvent(String sitename, String servicename, String accesscode, String numorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		//addlog("Nueva evento de servicio ( doAddServiceEvent )", sitename, servicename, accesscode, numorder, "carga");
		//log.info("Nuevo evento de servicio ( doAddServiceEvent )" + sitename +" "+ servicename+" "+ accesscode+" "+ numorder+" "+ "carga");
		try {			
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				addlog("No existe un sitio registrado con el nombre especificado", sitename);
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				addlog("No existe un servicio registrado con el nombre especificado", sitename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					addlog("El servicio se encuentra inactivo para el sitio especificado", sitename);
					return null;
				}
			} catch (NotFoundException e) {
				addlog("No existe el servicio registrado para el sitio especificado", sitename);
				return null;
			}
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				addlog("No existen accesos asociados al sitio especificado ", sitename);
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				addlog("El servicio no está contratado para el sitio y la empresa especificados", sitename);
				return null;
			}
			if (contractedService == null || !contractedService.getActive()) {
				addlog("El servicio contratado se encuentra inactivo para el sitio y la empresa especificados", sitename);
				return null;
			}
			// 2011-12-20 Antes de registrar el evento, validar si ya existe un evento similar no procesado
			PropertyInfoDTO[] props = new PropertyInfoDTO[4];
			props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
			props[1] = new PropertyInfoDTO("service.id", "servicekey", service.getId());
			props[2] = new PropertyInfoDTO("company.id", "companykey", access.getCompanykey());
			props[3] = new PropertyInfoDTO("processed", "processed", false);
			List<ServiceEventDTO> prevevents = serviceeventserver.getByProperties(props);
			
			if (prevevents.size() > 0) {
				addlog("Ya existen eventos similares no procesados, para el sitio y la empresa especificados", sitename);
				return null;
			}
			ServiceEventDTO event = new ServiceEventDTO();
			event.setProcessed(false);
			event.setDatecreated(new Date());
			event.setSitekey(site.getId());
			event.setServicekey(service.getId());
			event.setCompanykey(access.getCompanykey());
			event.setCustom(contractedService.getCustom() == null ? "" : contractedService.getCustom());
			event = serviceeventserver.addServiceEvent(event);
			addlog("Se ha registrado exitosamente un evento de servicio para la empresa :" + access.getCode()+" ", sitename, servicename, accesscode, numorder, "carga");
			return event;
		} catch (Exception e) {
			addlog("Error al almacenar evento de servicio", sitename);
			e.printStackTrace();
			return null;
		}
		
	}
	
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public 	BaseResultDTO doAddTicketRefNumber(Long ticketnumber, String numref, String sitename, String accesscode, String servicecode) throws OperationFailedException, NotFoundException, AccessDeniedException{
//		addlog("[doAddTicketRefNumber] params  : " + ticketnumber +", "+numref +", "+sitename+", "+accesscode+", "+servicecode , sitename  );
//		BaseResultDTO result = new BaseResultDTO();
//		Date now = new Date();
//		PropertyInfoDTO[] props = new PropertyInfoDTO[1];
//		props[0] = new PropertyInfoDTO("ticketnumber", "ticketnumber", Long.valueOf(ticketnumber));
//		
//		//se valida que exista el ticket
//		List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
//		if(ticketevents.size() == 0){
//			//Se crea un ticket en estado "en proceso"
//			addlog("[doAddTicketRefNumber] Se crea un ticket en estado en proceso " + ticketnumber, sitename);
//			TicketStateTypeDTO ticketstatetype = ticketstatetypeserver.getByPropertyAsSingleResult("code", "1");
//			SiteDTO site = siteserver.getByPropertyAsSingleResult("code", sitename);
//			AccessDTO[] accessArr = accessserver.getByPropertyAsArray("code", accesscode);
//			ServiceDTO service = serviceserver.getByPropertyAsSingleResult("code", servicecode);
//			
//			TicketEventDTO ticketevent = new TicketEventDTO();
//			ticketevent.setTicketnumber(ticketnumber);
//			ticketevent.setReferencia(numref);
//			
//			//obligatorios
//			ticketevent.setDatecreated(now);
//			ticketevent.setCurrentstatetypedate(now);
//			ticketevent.setCurrentstatetypekey(ticketstatetype.getId());
//			ticketevent.setSitekey(site.getId());
//			ticketevent.setServicekey(service.getId());
//			ticketevent.setCompanykey(accessArr[0].getCompanykey());
//			ticketevent = ticketeventserver.addTicketEvent(ticketevent);
//		}else{
//			
//			TicketEventDTO ticketevent = ticketevents.get(0);
//			
//			//se valida que este en estado en proceso
//			TicketStateTypeDTO ticketstatetype = ticketstatetypeserver.getById(ticketevent.getCurrentstatetypekey());
//			if(!ticketstatetype.getCode().equals("1")){
//				addlog("El ticket no se encuentra en proceso, estado ticket : " + ticketstatetype.getName(), sitename);
//				result.setStatuscode("1");
//				result.setStatusmessage("El ticket no se encuentra en proceso, estado ticket : " + ticketstatetype.getName());
//				return result;
//			}
//			ticketevent.setReferencia(numref);
//			addlog("[doAddTicketRefNumber] Agregando referencia " + ticketnumber, sitename);
//			ticketevent = ticketeventserver.updateTicketEvent(ticketevent);			
//		}
//		return result;
//	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TicketEventDTO doAddTicketEventStatus(Long ticketnumber, String sitename, String servicename, String accesscode, String ticketstate, String userlogid ) throws AccessDeniedException, OperationFailedException, NotFoundException {
		try {
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			// SE CONSULTA POR CODIGO
			addlog("[doAddTicketEventStatus] params  : " + ticketnumber +", "+sitename +", "+servicename+", "+accesscode+", "+ticketstate+", "  , sitename  );
			
			List<SiteDTO> sitesList = siteserver.getByProperty("code", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				addlog("No existe un sitio registrado con el código especificado ", sitename);
				
				return null;
			}
			SiteDTO site = sitesList.get(0);
			
			// SE CONSULTA POR CODIGO					
			List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				addlog("No existe un servicio registrado con el código especificado : " + servicename, sitename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					addlog("El servicio se encuentra inactivo para el sitio especificado ", sitename);
					return null;
				}
			} catch (NotFoundException e) {
				addlog("No existe el servicio registrado para el sitio especificado " , sitename);
				return null;
			}
			
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				addlog("No existen accesos asociados al sitio especificado ", sitename);
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				addlog("El servicio no está contratado para el sitio y la empresa especificados "  + accesscode, sitename);
				return null;
			}
			if (contractedService == null || !contractedService.getActive()) {
				addlog("El servicio contratado se encuentra inactivo para el sitio y la empresa especificados  "  + accesscode, sitename);
				return null;
			}
			
			// REVISA EL ESTADO DEL TICKET
			List<TicketStateTypeDTO> ticketStateTypeList = ticketstatetypeserver.getByProperty("code", ticketstate);
			
			if (ticketStateTypeList == null || ticketStateTypeList.size() == 0){
				return null;
			}
			
			TicketStateTypeDTO ticketStateType = ticketStateTypeList.get(0);
			
			Date now = new Date();
			TicketEventDTO ticketevent = null;
			TicketStateDTO ticketdetail = null;
			
			// ESTADOS POSIBLES:
			// 1 : En Proceso
			
			Long userid = 0L;
			//se busca el usuario por el rut
			List<UserDTO> userList = userserver.getByProperty("rut", userlogid);
			if (userList == null || userList.size() != 1 ) {
				addlog("No se encuentra el usuario con RUT:  " +userlogid , sitename);
			}else{
				userid = userList.get(0).getId();
			}
			
			if (ticketstate.equalsIgnoreCase("1")){
				
				//buscar por ticket y sitename, si existe se actualiza
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
				props[1] = new PropertyInfoDTO("ticketnumber", "ticketnumber", ticketnumber);
				
				List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
				
				if (ticketevents == null || ticketevents.isEmpty()) {
					addlog("[doAddTicketEventStatus] Creando Ticket " + ticketnumber, sitename);
					// PRIMER ESTADO DEL TICKET, POR ENDE SE DEBE CREAR
					ticketevent = new TicketEventDTO();
					ticketevent.setTicketnumber(ticketnumber);
					ticketevent.setCurrentstatetypekey(ticketStateType.getId());
					ticketevent.setCurrentstatetypedate(now);
					ticketevent.setDatecreated(now);
					ticketevent.setMessageid("");
					ticketevent.setSitekey(site.getId());
					ticketevent.setServicekey(service.getId());
					ticketevent.setCompanykey(access.getCompanykey());
					//ticketevent.setUserid(userid);
					ticketevent = ticketeventserver.addTicketEvent(ticketevent);
				}
				
			}
			else if (ticketstate.equalsIgnoreCase("0") || ticketstate.equalsIgnoreCase("-1")){
				addlog("[doAddTicketEventStatus] El ticket debe crearse con estado en proceso " + ticketnumber, sitename);
			
			}			

			
			addlog("Se ha registrado exitosamente un evento de ticket para la empresa : " + access.getCode(), sitename, servicename, accesscode, ticketnumber.toString(), "flujointerno");
			return ticketevent;		
			
		} catch (Exception e) {
			addlog("Error al almacenar evento de servicio (ticket)",sitename);
			log.error("Error al almacenar evento de servicio", e);
			return null;
		}
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TicketEventDTO doUpdateTicketEventStatus(Long ticketnumber, String refnumber, String sitename, String servicename, String accesscode, String ticketstate, String url, String userlogid, TicketEventStatusDataDTO[] details) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		try {
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			
			addlog("[doUpdateTicketEventStatus] params  : ticketNumber [" + ticketnumber +"] , refnumber ["+refnumber +"] , sitename ["+sitename+"] , servicename ["+servicename+"] , accesscode ["+accesscode+ "] , ticketstate ["+ticketstate +" ],  url ["+url+ "] , userlogid ["+ userlogid+"] , "+ url, sitename  );
			
			List<SiteDTO> sitesList = siteserver.getByProperty("code", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				addlog("No existe un sitio registrado con el código especificado ", sitename);
				return null;
			}
			SiteDTO site = sitesList.get(0);
			
			// SE CONSULTA POR CODIGO					
			List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				addlog("[doUpdateTicketEventStatus] No existe un servicio registrado con el código especificado : " + servicename, sitename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					addlog("[doUpdateTicketEventStatus] El servicio se encuentra inactivo para el sitio especificado ", sitename);
					return null;
				}
			} catch (NotFoundException e) {
				addlog("[doUpdateTicketEventStatus] No existe el servicio registrado para el sitio especificado " , sitename);
				return null;
			}
			
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				addlog("[doUpdateTicketEventStatus] No existen accesos asociados al sitio especificado ", sitename);
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				addlog(" [doUpdateTicketEventStatus] El servicio no está contratado para el sitio y la empresa especificados "  + accesscode, sitename);
				return null;
			}
			if (contractedService == null || !contractedService.getActive()) {
				addlog("[doUpdateTicketEventStatus] El servicio contratado se encuentra inactivo para el sitio y la empresa especificados  "  + accesscode, sitename);
				return null;
			}
			
			TicketStateTypeDTO ticketStateType = null;

			// REVISA EL ESTADO DEL TICKET
			System.out.println("[doUpdateTicketEventStatus] "+ticketstate);
			if(!ticketstate.equalsIgnoreCase("numref")){
				List<TicketStateTypeDTO> ticketStateTypeList = ticketstatetypeserver.getByProperty("code", ticketstate);
				if (ticketStateTypeList == null || ticketStateTypeList.size() == 0){
					addlog("[doUpdateTicketEventStatus] No existe el estado del ticket señalado  "  + accesscode, sitename);
					return null;
				}
				ticketStateType = ticketStateTypeList.get(0);
			}
			
			Date now = new Date();
			TicketEventDTO ticketevent = null;
			TicketStateDTO ticketdetail = null;
			
			// ESTADOS POSIBLES:
			// 0 : Éxito
			// -1: Error
			// 1 : En Proceso
			
			Long userid = 0L;
			//se busca el usuario por el rut
			List<UserDTO> userList = userserver.getByProperty("rut", userlogid);
			if (userList == null || userList.size() != 1 ) {
				addlog("[doUpdateTicketEventStatus] No se encuentra el usuario con RUT:  " +userlogid , sitename);
			}else{
				userid = userList.get(0).getId();
			}
			
			if (ticketstate.equalsIgnoreCase("1")){
				
				//buscar por ticket y sitename, si existe se actualiza
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
				props[1] = new PropertyInfoDTO("ticketnumber", "ticketnumber", ticketnumber);
				
				List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
				
				if (ticketevents != null && !ticketevents.isEmpty()) {
					addlog("[doUpdateTicketEventStatus] actualizando ticket" + ticketnumber, sitename);
					ticketevent = ticketevents.get(0);
					ticketevent.setCurrentstatetypekey(ticketStateType.getId());
					ticketevent.setCurrentstatetypedate(now);
					ticketevent.setDatecreated(now);
					ticketevent.setMessageid("");
					ticketevent.setSitekey(site.getId());
					ticketevent.setServicekey(service.getId());
					ticketevent.setCompanykey(access.getCompanykey());
					ticketevent.setAdjunto(url);
					ticketevent.setUserid(userid);
					ticketevent = ticketeventserver.updateTicketEvent(ticketevent);
					
				}else{
					addlog("[doUpdateTicketEventStatus] El ticket no existe, no se actualizará "  + ticketnumber, sitename);
					return null;
				}
				
			}
			else if (ticketstate.equalsIgnoreCase("0") || ticketstate.equalsIgnoreCase("-1")){
				
				// CONSULTA POR EL TICKET				
				PropertyInfoDTO[] props = new PropertyInfoDTO[4];
				props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
				props[1] = new PropertyInfoDTO("service.id", "servicekey", service.getId());
				props[2] = new PropertyInfoDTO("company.id", "companykey", access.getCompanykey());
				props[3] = new PropertyInfoDTO("ticketnumber", "ticketnumber", ticketnumber);
				
				List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
				
				if (ticketevents != null && !ticketevents.isEmpty()) {
					ticketevent = ticketevents.get(0);
					
					ticketevent.setCurrentstatetypekey(ticketStateType.getId());
					ticketevent.setCurrentstatetypedate(now);
					ticketevent.setAdjunto(url);
					
					ticketevent = ticketeventserver.updateTicketEvent(ticketevent);			
				}
				else{
					addlog("[doUpdateTicketEventStatus] El ticket no existe, no se actualizará "  + ticketnumber, sitename);
					return null;					
				}				
			}else if (ticketstate.equals("numref")){ //actualiza el numero de referencia
				
				//buscar por ticket y sitename, si existe se actualiza
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
				props[1] = new PropertyInfoDTO("ticketnumber", "ticketnumber", ticketnumber);
				
				List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
				
				if (ticketevents != null && !ticketevents.isEmpty()) {
					addlog("[doUpdateTicketEventStatus] actualizando referencia ticket" + ticketnumber, sitename);
					ticketevent = ticketevents.get(0);
					ticketevent.setCurrentstatetypedate(now);
					ticketevent.setReferencia(refnumber == null? "" : refnumber);
					ticketevent = ticketeventserver.updateTicketEvent(ticketevent);
					
				}else{
					addlog("[doUpdateTicketEventStatus] El ticket no existe, no se actualizará "  + ticketnumber, sitename);
					return null;
				}
			}
				
			// AGREGA DETALLES
			
			if (details != null && details.length > 0 ){
				if(!ticketstate.equalsIgnoreCase("numref")){
					
					addlog("[doUpdateTicketEventStatus] Ticket con detalles, validando servicio para generar fichero de error.. " + ticketnumber, sitename);
					System.out.println("[doUpdateTicketEventStatus] largo detalle : "+details.length);
					System.out.println("[doUpdateTicketEventStatus] DETALLE " + details[0].getCode() + " " + details[0].getDescription() + " " + details[0].getState() + " " + details[0].getType());
					
					
					if((service.getCode().equals("CPL") || service.getCode().equals("AST") || service.getCode().equals("ISP") || service.getCode().equals("PSP") || service.getCode().equals("HSP")) /*&& ticketstate.equalsIgnoreCase("-1")*/){
						String s3url ="";
						if(url == null || url.isEmpty()){
							addlog("[doUpdateTicketEventStatus] Generando fichero de error.. " + ticketnumber, sitename);
							s3url = createErrorFile(ticketnumber.toString(), details, service.getCode());
						}	
						addlog("[doUpdateTicketEventStatus] URL: "+ s3url + " "  + ticketnumber, sitename);
						ticketevent.setAdjunto(s3url);
						ticketevent = ticketeventserver.updateTicketEvent(ticketevent);
					}else{
						addlog("[doUpdateTicketEventStatus] Servicio("+service.getCode()+") no válido para generar fichero de error.. " + ticketnumber, sitename);
					}
					
					System.out.println("[doUpdateTicketEventStatus] TicketStateType: "+ticketStateType.getCode() + " " +ticketStateType.getId());
					
					for (TicketEventStatusDataDTO data : details){
						ticketdetail = new TicketStateDTO();
						ticketdetail.setTicketkey(ticketevent.getId());
						ticketdetail.setTicketstatetypekey(ticketStateType.getId());
						ticketdetail.setWhen(now);
						ticketdetail.setCode(data.getCode() == null ? "" : data.getCode());
						ticketdetail.setType(data.getType() == null ? "" : data.getType());
						ticketdetail.setState(data.getState() == null ? "" : data.getState());
						ticketdetail.setDescription(data.getDescription() == null ? "" : data.getDescription());
						ticketdetail = ticketstateserver.addTicketState(ticketdetail);				
					}
				}
			}else{
				addlog("[doUpdateTicketEventStatus] Ticket sin detalles " + ticketnumber, sitename);
			}
			addlog("[doUpdateTicketEventStatus] Se ha actualizado exitosamente el ticket "+ticketnumber.toString()+" para la empresa : " + access.getCode(), sitename, servicename, accesscode, ticketnumber.toString(), "flujointerno");
			return ticketevent;	
		
			
		} catch (Exception e) {
			addlog("[doUpdateTicketEventStatus] Error al actualizar evento de servicio (ticket)",sitename);
			log.error("[doUpdateTicketEventStatus] Error al actualizar evento de servicio", e);
			return null;
		}
	}

	public ContractedServiceDTO doContractSiteService(Long sitekey, Long servicekey, Long companykey, Long folderkey, Long formatkey,boolean compresseddocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SiteServiceKey sskey = new SiteServiceKey(sitekey, servicekey);
		SiteServiceDTO siteservice = siteserviceserver.getById(sskey);
		AccessKey accesskey = new AccessKey(companykey, sitekey);
		AccessDTO access = null;
		try {
			access = accessserver.getById(accesskey);
		} catch (NotFoundException e) {
			throw new NotFoundException("La empresa no tiene un acceso registrado para el sitio especificado.\nDebe crear el acceso previamente.");
		}
		ContractedServiceKey cskey = new ContractedServiceKey(sitekey, servicekey, companykey);
		try {
			contractedserviceserver.getById(cskey);
			throw new AccessDeniedException("El servicio ya se encuentra contratado para la empresa y sitio especificados");
		} catch (NotFoundException e) {
			// OK, continuar con la siguiente iteración
		}

		ContractedServiceDTO result = new ContractedServiceDTO();
		MessageFolderDTO folder = folderserver.getById(folderkey);
		MessageFormatDTO format = formatserver.getById(formatkey);
		
		// TODO: AGREGAR ENDPOINT DE SERVICIO EN B2B		
		
		// Validar que el formato esté asociado al servicio
		if (!format.getServicekey().equals(servicekey))
			throw new AccessDeniedException("El formato no corresponde al servicio");
		// Validar que la casilla pertenezca al proveedor
		if (!folder.getCompanykey().equals(companykey))
			throw new AccessDeniedException("La casilla no pertenece al proveedor");

		result.setActive(true);
		result.setCompanykey(access.getCompanykey());
		result.setServicekey(siteservice.getServicekey());
		result.setSitekey(siteservice.getSitekey());
		result.setFolderkey(folder.getId());
		result.setFormatkey(format.getId());
		// 2015-09-25 DVI AL CONTRATAR EL SERVICIO, FIJAR FECHA DE ACTIVACIÓN Y NO MONITOREAR
		result.setMonitor(false);
		result.setActivation(new Date());
		result.setCompresseddocument(compresseddocument);
		result = contractedserviceserver.addContractedService(result);
		// 2015-06-11 DVI AL CONTRATAR EL SERVICIO, REGISTRAR AUTOMÁTICAMENTE UN EVENTO INICIAL
		ServiceDTO service = serviceserver.getById(siteservice.getServicekey());
		SiteDTO site = siteserver.getById(siteservice.getSitekey());
		CompanyDTO company = companyserver.getById(companykey);
		ServiceEventDTO event = new ServiceEventDTO();
		event.setProcessed(false);
		event.setDatecreated(new Date());
		event.setSitekey(siteservice.getSitekey());
		event.setServicekey(siteservice.getServicekey());
		event.setCompanykey(access.getCompanykey());
		event = serviceeventserver.addServiceEvent(event);
		log.info("Se ha contratado exitosamente el servicio '" + service.getName() + "' para el sitio '" + site.getName() + "' y para la empresa '" + company.getName() + "'. Se ha creado un evento de servicio automático.'");
		return result;
	}

	public void doRevokeSiteService(Long siteid, Long serviceid, Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		ContractedServiceKey key = new ContractedServiceKey(siteid, serviceid, companyid);
		contractedserviceserver.deleteIdentifiable(key);
	}

	public ContractedServiceDTO doUpdateContractedService(Long sitekey, Long servicekey, Long companykey, Long folderkey, Long formatkey, boolean monitor, boolean compresseddocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		SiteServiceKey sskey = new SiteServiceKey(sitekey, servicekey);
		SiteServiceDTO siteservice = siteserviceserver.getById(sskey);
		AccessKey accesskey = new AccessKey(companykey, sitekey);
		try {
			AccessDTO access = accessserver.getById(accesskey);
		} catch (NotFoundException e) {
			throw new NotFoundException("La empresa no tiene un acceso registrado para el sitio especificado.\nDebe crear el acceso previamente.");
		}
		ContractedServiceKey cskey = new ContractedServiceKey(sitekey, servicekey, companykey);
		try {
			ContractedServiceDTO result = contractedserviceserver.getById(cskey, LockModeType.PESSIMISTIC_WRITE);
			MessageFolderDTO folder = folderserver.getById(folderkey);
			MessageFormatDTO format = formatserver.getById(formatkey);
			// Validar que el formato esté asociado al servicio
			if (!format.getServicekey().equals(servicekey))
				throw new AccessDeniedException("El formato no corresponde al servicio");
			// Validar que la casilla pertenezca al proveedor
			if (!folder.getCompanykey().equals(companykey))
				throw new AccessDeniedException("La casilla no pertenece al proveedor");
			// Actualizar formato y casilla
			result.setFolderkey(folder.getId());
			result.setFormatkey(format.getId());
			result.setMonitor(monitor);
			result.setCompresseddocument(compresseddocument);
			result = contractedserviceserver.updateIdentifiable(result);
			return result;
		} catch (NotFoundException e) {
			throw new AccessDeniedException("El servicio ya se encuentra contratado para la empresa y sitio especificados");
		}
	}

	public FileEventDTO doUpdateFileEventStatus(String as2id, String filename, String status, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			Date dreceived = sdf.parse(datereceived);
			// Buscar el evento de archivo por nombre de archivo
			List<FileEventDTO> listFE = fileeventserver.getByProperty("filename", filename);
			if (listFE == null || listFE.isEmpty()) {				
				log.error("No existe evento registrado asociado al archivo que se está informando : " + filename);
				return null;
			}
			FileEventDTO result = listFE.get(0);
			// Buscar la empresa asociada al evento
			CompanyDTO company = companyserver.getById(result.getCompanykey());
			// Validar que coincida la empresa con la asociada al evento de archivo
			if (!company.getAs2id().trim().equalsIgnoreCase(as2id.trim())) {
				log.error("El evento de archivo se encontró para el archivo : " + filename + ", pero el ID AS2 especificado (" + as2id + ") no corresponde asl ID AS2 de la empresa asociada al evento (" + company.getAs2id() + ")");
				return null;
			}
			AccessDTO access = accessserver.getById(new AccessKey(result.getCompanykey(), result.getSitekey()));
			// Actualizar el estado y la fecha del evento de archivo
			boolean success = "Exito".equals(status);
			result.setDatereceived(new Date());
			result.setReceived(true);
			// Esto por si primero llega con error y luego el archivo es reenviado manualmente
			result.setInformed(false);
			result.setOk(success);
			result = fileeventserver.updateFileEvent(result);
			log.info("Se ha actualizado exitosamente un evento de archivo recibido para la empresa : " + access.getCode() + ", archivo : " + filename + ", estado : " + status + ", fecha : " + datereceived);
//			addlog(String description				  , String sitename				   , String servicename               , String accesscode, String numorder, String comentcode)
			addlogById("Acknowledge de documento", result.getSitekey().intValue(), result.getServicekey().intValue(), access.getCode(), result.getDocumentid(), "ack");
			return result;
		} catch (ParseException e) {
			log.error("Error al actualizar evento de archivo", e);
			return null;
		}
	}

	public FileEventDTO doUpdateFileEventStatus(String sitename, String servicename, String accesscode, String filename, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			Date dreceived = sdf.parse(datereceived);
			// Buscar el evento de archivo por nombre de archivo
			List<FileEventDTO> listFE = fileeventserver.getByProperty("filename", filename);
			if (listFE == null || listFE.isEmpty()) {
				log.error("No existe evento registrado asociado al archivo que se está informando : " + filename);
				return null;
			}
			FileEventDTO result = listFE.get(0);

			// Buscar sitio y servicio por nombre
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre especificado :" + sitename);
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre especificado : " + servicename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
			} catch (NotFoundException e) {
				log.info("No existe el servicio registrado para el sitio especificado : " + sitename);
				return null;
			}
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				log.info("No existe el servicio registrado para el sitio y la empresa especificados : " + sitename + " , " + accesscode);
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				log.info("El servicio no está contratado para el sitio y la empresa especificados : " + sitename + " , " + accesscode);
				return null;
			}

			// Validar que el archivo corresponde al servicio contratado
			if (!result.getCompanykey().equals(contractedService.getCompanykey()) || !result.getSitekey().equals(contractedService.getSitekey()) || !result.getServicekey().equals(contractedService.getServicekey())) {
				log.info("El evento de archivo no corresponde al servicio informado : " + sitename + " , " + ", " + servicename + ", " + accesscode + ", " + filename);
				return null;
			}

			// Actualizar el estado y la fecha del evento de archivo
			result.setDatereceived(dreceived);
			result.setReceived(true);
			// Esto por si primero llega con error y luego el archivo es reenviado manualmente
			result.setInformed(false);
			result.setOk(true);
			result = fileeventserver.updateFileEvent(result);
//			log.info("Se ha actualizado exitosamente un evento de archivo recibido (SOAP) para la empresa : " + access.getCode() + ", archivo : " + filename + ", estado : Exito, fecha : " + datereceived);
			addlog("Se ha actualizado exitosamente un evento de archivo recibido (SOAP) para la empresa : " + access.getCode() + ", archivo : " + filename + ", estado : Exito, fecha : " + datereceived, result.getSitekey().toString() , result.getServicekey().toString(), access.getCode(), result.getDocumentid(), "ack");

			return result;

		} catch (Exception e) {
			log.error("Error al actualizar evento de archivo", e);
			return null;
		}
	}

	public FileEventDTO doUpdateFileEventStatusofCompany(String servicename, String companyrut, String filename, String userlogid, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			Date dreceived = sdf.parse(datereceived);
			// Buscar el evento de archivo por nombre de archivo
			List<FileEventDTO> listFE = fileeventserver.getByProperty("filename", filename);
			if (listFE == null || listFE.isEmpty()) {
				log.error("No existe evento registrado asociado al archivo que se está informando : " + filename);
				return null;
			}
			FileEventDTO result = listFE.get(0);

			// Buscar servicio por nombre
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre especificado : " + servicename);
				return null;
			}
			ServiceDTO service = servicesList.get(0);

			// Buscar si existe la relación Usuario-Empresa para la empresa y el usuario
			PropertyInfoDTO info1 = new PropertyInfoDTO("company.rut", "companyrut", companyrut);
			PropertyInfoDTO info2 = new PropertyInfoDTO("user.rut", "userrut", userlogid);

			List<UserCompanyDTO> usercompanies = usercompanyserver.getByProperties(new PropertyInfoDTO[] { info1, info2 });
			if (usercompanies == null || usercompanies.isEmpty()) {
				log.info("El usuario con RUT: '" + userlogid + "' no está asociado a la empresa con RUT: '" + companyrut + "'");
				return null;
			} else if (usercompanies.size() > 1) {
				log.info("El usuario con RUT: '" + userlogid + "' está asociado a más de una empresa con RUT: '" + companyrut + "'");
				return null;
			}
			UserCompanyDTO usercompany = usercompanies.get(0);

			// Validar que el evento corresponda al servicio
			if (!result.getServicekey().equals(service.getId())) {
				log.info("El evento de archivo no corresponde al servicio informado : " + servicename + ", " + filename);
				return null;
			}

			SiteServiceKey sskey = new SiteServiceKey(result.getSitekey(), result.getServicekey());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
			} catch (NotFoundException e) {
				log.info("El servicio de sitio no existe para el evento de archivo especificado : " + servicename + " , " + filename);
				return null;
			}
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(result.getSitekey(), result.getServicekey(), usercompany.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				log.info("El servicio no está contratado para el evento de archivo especificado : " + servicename + " , " + filename);
				return null;
			}

			if (!contractedService.getActive()) {
				log.info("El servicio contratado no se encuentra activo para el evento de archivo especificado : " + servicename + " , " + filename);
				return null;
			}

			// Actualizar el estado y la fecha del evento de archivo
			result.setDatereceived(dreceived);
			result.setReceived(true);
			// Esto por si primero llega con error y luego el archivo es reenviado manualmente
			result.setInformed(false);
			result.setOk(true);
			result = fileeventserver.updateFileEvent(result);
			log.info("Se ha actualizado exitosamente un evento de archivo recibido (SOAP) para la empresa : " + companyrut + ", archivo : " + filename + ", estado : Exito, fecha : " + datereceived);
			return result;

		} catch (Exception e) {
			log.error("Error al actualizar evento de archivo", e);
			return null;
		}
	}

	public FileEventDTO doUpdateFileEventStatusofPathandUser(String path, String userlogid, String filename, String datereceived) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			Date dreceived = sdf.parse(datereceived);
			// Buscar el evento de archivo por nombre de archivo
			List<FileEventDTO> listFE = fileeventserver.getByProperty("filename", filename);
			if (listFE == null || listFE.isEmpty()) {
				log.error("No existe evento registrado asociado al archivo que se está informando : " + filename);
				return null;
			}
			FileEventDTO result = listFE.get(0);

			// Buscar usuario por logid
			List<UserDTO> listUsers = userserver.getByProperty("rut", userlogid);
			if (listUsers == null || listUsers.isEmpty())
				throw new AccessDeniedException("No existe el usuario con el RUT especificado");
			UserDTO user = listUsers.get(0);

			// Buscar empresa asociada al archivo
			CompanyDTO company = companyserver.getById(result.getCompanykey());

			// Buscar si existe la relación Usuario-Empresa para la empresa y el usuario
			UserCompanyKey userCompanyKey = new UserCompanyKey();
			userCompanyKey.setCompanykey(company.getId());
			userCompanyKey.setUserkey(user.getId());

			try {
				UserCompanyDTO userCompanyW = usercompanyserver.getById(userCompanyKey);
				if (!userCompanyW.getActive()) {
					log.info("El usuario con RUT: '" + userlogid + "' no está asociado a la empresa con RUT: '" + company.getRut() + "'");
					return null;
				}
			} catch (NotFoundException exc) {
				log.info("El usuario con RUT: '" + userlogid + "' no está asociado a la empresa con RUT: '" + company.getRut() + "'");
				return null;
			}
			
			AccessKey accesskey;
			AccessDTO access;			
			try{
				accesskey = new AccessKey();
				accesskey.setCompanykey(company.getId());
				accesskey.setSitekey(result.getSitekey());
				access = accessserver.getById(accesskey);
			} catch (NotFoundException exc) {
				log.info("no se encontró el acceso para registrar el ACK por SOAP para " + company.getId() + " sitio "+ result.getSitekey());
				return null;
			}

			// Actualizar el estado y la fecha del evento de archivo
			result.setDatereceived(dreceived);
			result.setReceived(true);
			// Esto por si primero llega con error y luego el archivo es reenviado manualmente
			result.setInformed(false);
			result.setOk(true);
			result = fileeventserver.updateFileEvent(result);
			addlogById("Se ha actualizado exitosamente un evento de archivo recibido (SOAP) para la empresa : " + company.getRut() + ", archivo : " + filename, result.getSitekey().intValue(), result.getServicekey().intValue(), access.getCode(), result.getDocumentid(), "ack");
			return result;

		} catch (Exception e) {
			log.error("Error al actualizar evento de archivo", e);
			return null;
		}
	}

	public AccessDTO[] getAccessesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return accessserver.getByPropertyAsArray("company.id", companyid);
	}

	public ContractedServiceDataDTO[] getAllContractedServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return contractedserviceserver.getAllContractedServices();
	}

	public File getAllContractedServicesXLS() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		ContractedServiceDataDTO[] contractedservices = contractedserviceserver.getAllContractedServices();
		if (contractedservices == null) {
			contractedservices = new ContractedServiceDataDTO[0];
		}
		File result = getContractedServicesExcelFile(contractedservices);
		return result;
	}

	public CompaniesServiceStatusReportDTO[] getCompaniesServiceStatusReport() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		// Obtener los servicios de sitio activos e iterar
		SiteServiceDTO[] siteservices = siteserviceserver.getByPropertyAsArray("active", true);
		CompaniesServiceStatusReportDTO[] result = new CompaniesServiceStatusReportDTO[siteservices.length];
		for (int i = 0; i < siteservices.length; i++) {
			SiteServiceDTO siteService = siteservices[i];
			SiteDTO site = siteserver.getById(siteService.getSitekey());
			ServiceDTO service = serviceserver.getById(siteService.getServicekey());
			CompaniesServiceStatusReportDTO report = new CompaniesServiceStatusReportDTO();
			report.setServicekey(service.getId());
			report.setServicename(service.getName());
			report.setSitekey(site.getId());
			report.setSitename(site.getName());
			CompaniesServiceStatusDataDTO[] datas = contractedserviceserver.getAllContractedServicesBySiteService(siteService.getSitekey(), siteService.getServicekey());
			report.setDatas(datas);
			result[i] = report;
		}
		return result;
	}

	private File getContractedServicesExcelFile(ContractedServiceDataDTO[] contractedservices) throws OperationFailedException {
		// Construcción del excel
		// 1 - Obtener el nombre del archivo
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh.mm.ss");
		String downloadfilename = "Servicios_contratados_" + dateFormat.format(new Date()) + ".xls";
		String folderStr = System.getProperty("download_folder");
		File file = new File(folderStr + downloadfilename);
		try (OutputStream stream = new FileOutputStream(file)) {

			// Escribir descripcion del filtro seleccionado
			DataTable dt0 = new DataTable("SERVICIOS");

			DataColumn col01 = new DataColumn("SITENAME", "SITIO", String.class, 16);
			DataColumn col02 = new DataColumn("SERVICENAME", "SERVICIO", String.class, 25);
			DataColumn col03 = new DataColumn("COMPANYNAME", "EMPRESA", String.class, 35);
			DataColumn col04 = new DataColumn("ENCRYPT", "ENCRIPTAR", String.class, 12);
			DataColumn col05 = new DataColumn("ENCPWD", "PWD ENCRIPT.", String.class, 15);
			DataColumn col06 = new DataColumn("ACTIVE", "ACTIVO", String.class, 8);
			DataColumn col07 = new DataColumn("ACTIVATIONDATE", "FECHA ACTIVACION", Date.class, 20);
			DataColumn col08 = new DataColumn("MONITOR", "MONITOREAR", String.class, 15);
			DataColumn col09 = new DataColumn("FOLDERNAME", "CASILLA", String.class, 50);
			DataColumn col10 = new DataColumn("FORMATNAME", "FORMATO", String.class, 10);
			DataColumn col11 = new DataColumn("LASTSENTMESSAGE", "ULTIMO MENSAJE", Date.class, 20);
			DataColumn col12 = new DataColumn("LASTMONITORED", "ULTIMO MONITOREO", Date.class, 20);
			DataColumn col13 = new DataColumn("PENDINGMESSAGES", "MENSAJES PENDIENTES", Integer.class, 20);

			dt0.addColumns(col01, col02, col03, col06, col07, col08, col04, col05, col09, col10, col11, col12, col13);

			dateFormat = new SimpleDateFormat(IDateTimeConstants.DATETIME_FORMAT_NOTIMEZONE);
			for (int i = 0; i < contractedservices.length; i++) {
				ContractedServiceDataDTO line = contractedservices[i];
				DataRow row = dt0.newRow();
				row.setCellValue("SITENAME", line.getSitename());				
				row.setCellValue("SERVICENAME", line.getServicename());
				row.setCellValue("COMPANYNAME", line.getCompanyname());
				row.setCellValue("ENCRYPT", line.getEncrypt() ? "Sí" : "No");
				row.setCellValue("ENCPWD", line.getEncpwd());
				row.setCellValue("ACTIVE", line.getActive() ? "Sí" : "No");
				row.setCellValue("ACTIVATIONDATE", line.getActivation());
				row.setCellValue("MONITOR", line.getMonitor() ? "Sí" : "No");
				row.setCellValue("FOLDERNAME", line.getFoldername());
				row.setCellValue("FORMATNAME", line.getFormatname());
				row.setCellValue("LASTSENTMESSAGE", line.getLastsentmessage());
				row.setCellValue("LASTMONITORED", line.getLastmonitored());
				row.setCellValue("PENDINGMESSAGES", line.getPendingmessages());
				dt0.addRow(row);
			}

			// CREAR ARCHIVO
			XLSConverterJXL converter = new XLSConverterJXL();
			converter.setExcelheaderbold(true);
			converter.setShowexceltableborder(true);

			// Estilos
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
			symbols.setGroupingSeparator(',');

			DataColumnStyleInfo styleinfo = new DataColumnStyleInfo();
			DataColumnStyle styledate = new DataColumnStyle("styledate", new SimpleDateFormat("dd-MM-yyyy HH:mm"), 20);
			styleinfo.addDataColumnStyles(styledate, col09);
			converter.ExportToXLS(dt0, styleinfo, stream);

		} catch (FileNotFoundException e1) {
			throw new OperationFailedException("Error al generar archivo : ", e1);
		} catch (IOException e1) {
			throw new OperationFailedException("Error al generar archivo : ", e1);
		}
		return file;
	}

	public ContractedServiceDataDTO[] getContractedServicesofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return contractedserviceserver.getContractedServicesofCompany(companyid);
	}

	public MessageDataDTO getMessageData(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre '" + sitename + "'");
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre '" + servicename + "'");
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				log.info("No existe un acceso '" + accesscode + "' registrado para el sitio '" + sitename + "'");
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey, LockModeType.PESSIMISTIC_WRITE);
				if (!contractedService.getActive()) {
					log.info("El servicio contratado '" + servicename + "' se encuentra inactivo para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
					return null;
				}
			} catch (NotFoundException e) {
				log.info("El servicio '" + servicename + "' no está contratado para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
				return null;
			}
			
			MessageFolderDataDTO folderdata = new MessageFolderDataDTO();			
			MessageFolderDTO folder = folderserver.getById(contractedService.getFolderkey());
			
			// OBTIENE EL TIPO DE FOLDER
			MessageFolderTypeDTO folderType = foldertypeserver.getById(folder.getMessagefoldertypekey());
			folderdata.setType(folderType.getCode());
									
			if (folderType.getCode().equals("FTP")){
				
				// SI ES FTP, SE DEBE TRAER DATOS DE CONFIGURACION
				FTPMessageFolderDTO ftpmessage = ftpfolderserver.getById(contractedService.getFolderkey());			
				
				folderdata.setHost(ftpmessage.getHost());
				folderdata.setUsername(ftpmessage.getUsername());
				folderdata.setPassword(ftpmessage.getPassword());
				folderdata.setProtocol(ftpmessage.getProtocol());
			}
			
			folderdata.setPath(folder.getPath());
			
			MessageFormatDTO format = formatserver.getById(contractedService.getFormatkey());
			
			// EBO: SE AGREGA ENDPOINT WS
			WSEndpointDataDTO wsendpointdata = new WSEndpointDataDTO();
			if (contractedService.getWsendpointkey() != null && contractedService.getWsendpointkey() > 0){
				WSEndpointDTO wsendpoint = wsendpointserver.getById(contractedService.getWsendpointkey());
				wsendpointdata.setEndpoint(wsendpoint.getFullpath());
			}			
			
			CompanyDTO company = companyserver.getById(contractedService.getCompanykey());
						
			
			MessageFormatDataDTO formatdata = new MessageFormatDataDTO();
			formatdata.setFormat(format.getName());
			MessageDataDTO result = new MessageDataDTO();
			result.setFolder(folderdata);
			result.setFormat(formatdata);		
			result.setEndpoint(wsendpointdata);
			result.setCompresseddocument(contractedService.getCompresseddocument());
			// DVI 2014-05-08 DATOS PARA ENCRIPTACIÓN SIMÉTRICA
			result.setEncrypt(company.getEncrypt());
			result.setEncryptpassword(company.getEncryptpassword());
			// DVI 2012-03-05 FECHA ÚLTIMO MENSAJE ENVIADO
			contractedService.setLastsentmessage(new Date());
			contractedserviceserver.updateIdentifiable(contractedService);
			return result;
		} catch (Exception e) {
			log.error("Error al obtener datos de servicio", e);
			return null;
		}
	}

	public MessageDataDTO getMessageDataofUser(String sitename, String servicename, String accesscode, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		//LOGUEAR INTENTO DE VALIDACION DE WS 
		// TODO EBO TEST
		try {
			boolean hasUser = false;
			UserDTO user = null;
			
			// EBO: 04-05-2018
			// SI VIENE VACIO NO SE VALIDA
			if (userlogid != null && !userlogid.isEmpty()){
				List<UserDTO> listUsers = userserver.getByProperty("rut", userlogid);
				if (listUsers == null || listUsers.isEmpty())
					throw new AccessDeniedException("No existe el usuario con el RUT especificado");
				user = listUsers.get(0);			
				
				hasUser = true;				
			}			
			
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			// EBO: 04-05-2018
			// SE BUSCA POR CODIGO EL SITIO
			List<SiteDTO> sitesList = siteserver.getByProperty("code", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre '" + sitename + "'");
				return null;
			}
			SiteDTO site = sitesList.get(0);
						
			// EBO: 04-05-2018
			// SE BUSCA POR CODIGO EL SERVICIO			
			List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre '" + servicename + "'");
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				log.info("No existe un acceso '" + accesscode + "' registrado para el sitio '" + sitename + "'");
				return null;
			}
			AccessDTO access = accesses[0];
			
			if (hasUser){
				// VALIDA QUE EL USUARIO ESTE ASOCIADO A LA EMPRESA
				UserCompanyKey ucKey = new UserCompanyKey(access.getCompanykey(), user.getId());		
				UserCompanyDTO userCompany = null;
				
				try {
					userCompany = usercompanyserver.getById(ucKey);
					if (!userCompany.getActive()) {
						log.info("El usuario '" + user.getRut() + "' se encuentra inactivo para acceso '" + accesscode + "'");
						return null;
					}
				} catch (NotFoundException e) {
					log.info("El usuario '" + user.getRut() + "' se encuentra inactivo para acceso '" + accesscode + "'");
					return null;
				}
			}			
						
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey, LockModeType.PESSIMISTIC_WRITE);
				if (!contractedService.getActive()) {
					log.info("El servicio contratado '" + servicename + "' se encuentra inactivo para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
					return null;
				}
			} catch (NotFoundException e) {
				log.info("El servicio '" + servicename + "' no está contratado para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
				return null;
			}

			MessageFolderDataDTO folderdata = new MessageFolderDataDTO();			
			MessageFolderDTO folder = folderserver.getById(contractedService.getFolderkey());
			
			// OBTIENE EL TIPO DE FOLDER
			MessageFolderTypeDTO folderType = foldertypeserver.getById(folder.getMessagefoldertypekey());
			folderdata.setType(folderType.getCode());
									
			if (folderType.getCode().equals("FTP")){
				
				// SI ES FTP, SE DEBE TRAER DATOS DE CONFIGURACION
				FTPMessageFolderDTO ftpmessage = ftpfolderserver.getById(contractedService.getFolderkey());			
				
				folderdata.setHost(ftpmessage.getHost());
				folderdata.setUsername(ftpmessage.getUsername());
				folderdata.setPassword(ftpmessage.getPassword());
				folderdata.setProtocol(ftpmessage.getProtocol());
			}
			
			folderdata.setPath(folder.getPath());
			
			MessageFormatDTO format = formatserver.getById(contractedService.getFormatkey());
			
			// EBO: SE AGREGA ENDPOINT WS
			WSEndpointDataDTO wsendpointdata = new WSEndpointDataDTO();
			if (contractedService.getWsendpointkey() != null && contractedService.getWsendpointkey() > 0){
				WSEndpointDTO wsendpoint = wsendpointserver.getById(contractedService.getWsendpointkey());
				wsendpointdata.setEndpoint(wsendpoint.getFullpath());
			}			
			
			CompanyDTO company = companyserver.getById(contractedService.getCompanykey());			
			folderdata.setPath(folder.getPath());
			MessageFormatDataDTO formatdata = new MessageFormatDataDTO();
			formatdata.setFormat(format.getName());
			MessageDataDTO result = new MessageDataDTO();
			result.setFolder(folderdata);
			result.setFormat(formatdata);
			result.setEndpoint(wsendpointdata);
			// DVI 2014-05-08 DATOS PARA ENCRIPTACIÓN SIMÉTRICA
			result.setEncrypt(company.getEncrypt());
			result.setEncryptpassword(company.getEncryptpassword());
			// EBO SE AGREGA INFORMACION DEL SITIO Y SERVICIO
			result.setSitename(site.getName());
			result.setServicename(service.getName());			
			// DVI 2012-03-05 FECHA ÚLTIMO MENSAJE ENVIADO
			contractedService.setLastsentmessage(new Date());
			contractedserviceserver.updateIdentifiable(contractedService);
			return result;
		} catch (Exception e) {
			log.error("Error al obtener datos de servicio", e);
			return null;
		}
	}
	
	public TicketEventResultDTO checkUploadStatus(String sitename, String servicename, String accesscode, String ticketnumber, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO EBO TEST
		try {
			
			List<UserDTO> listUsers = userserver.getByProperty("rut", userlogid);
			if (listUsers == null || listUsers.isEmpty())
				throw new AccessDeniedException("No existe el usuario con el RUT especificado");
			UserDTO user = listUsers.get(0);
			
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("code", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre '" + sitename + "'");
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre '" + servicename + "'");
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				log.info("No existe un acceso '" + accesscode + "' registrado para el sitio '" + sitename + "'");
				return null;
			}
			AccessDTO access = accesses[0];
			
			// VALIDA QUE EL USUARIO ESTE ASOCIADO A LA EMPRESA
			UserCompanyKey ucKey = new UserCompanyKey(access.getCompanykey(), user.getId());		
			UserCompanyDTO userCompany = null;
			
			try {
				userCompany = usercompanyserver.getById(ucKey);
				if (!userCompany.getActive()) {
					log.info("El usuario '" + user.getRut() + "' se encuentra inactivo para acceso '" + accesscode + "'");
					return null;
				}
			} catch (NotFoundException e) {
				log.info("El usuario '" + user.getRut() + "' se encuentra inactivo para acceso '" + accesscode + "'");
				return null;
			}
						
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey, LockModeType.PESSIMISTIC_WRITE);
				if (!contractedService.getActive()) {
					log.info("El servicio contratado '" + servicename + "' se encuentra inactivo para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
					return null;
				}
			} catch (NotFoundException e) {
				log.info("El servicio '" + servicename + "' no está contratado para el sitio '" + sitename + "' y el acceso '" + accesscode + "'");
				return null;
			}
			
			// REPORTE ESTADO DEL TICKET		
			TicketEventResultDTO result = new TicketEventResultDTO();
			
			// DETALLE
			List<TicketEventDataDTO> resultList = new ArrayList<TicketEventDataDTO>();
			TicketEventDataDTO data = null;
			
			// CONSULTA POR EL TICKET				
			PropertyInfoDTO[] props = new PropertyInfoDTO[4];
			props[0] = new PropertyInfoDTO("site.id", "sitekey", site.getId());
			props[1] = new PropertyInfoDTO("service.id", "servicekey", service.getId());
			props[2] = new PropertyInfoDTO("company.id", "companykey", access.getCompanykey());
			props[3] = new PropertyInfoDTO("ticketnumber", "ticketnumber", Long.valueOf(ticketnumber));
			
			List<TicketEventDTO> ticketevents = ticketeventserver.getByProperties(props);
			TicketEventDTO ticketevent = null;
			
			if (ticketevents == null || ticketevents.isEmpty()) {
				log.info("No existe evento de ticket para el sitio y la empresa especificados : " + sitename + " , " + accesscode);				
				return null;	
			}
			
			ticketevent = ticketevents.get(0);
			
			// BUSCA EL ESTADO Y SU DESCRIPCION
			props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("ticketevent.id", "ticketeventkey", ticketevent.getId());
			props[1] = new PropertyInfoDTO("ticketstatetype.id", "ticketstatetypekey", ticketevent.getCurrentstatetypekey());
			
			List<TicketStateDTO> ticketstates = ticketstateserver.getByProperties(props);
			
			if (ticketstates == null || ticketstates.isEmpty()) {
				log.error("No existe el estado de ticket para el sitio y la empresa especificados : " + sitename + " , " + accesscode);
				return null;
			}			
			
			for (TicketStateDTO ticketState : ticketstates){
				data = new TicketEventDataDTO();
				
				data.setCode(ticketState.getCode());
				data.setType(ticketState.getType());
				data.setState(ticketState.getState());
				data.setDescription(ticketState.getDescription());
				
				resultList.add(data);				
			}
			
			result.setTicketresults(resultList.toArray(new TicketEventDataDTO[resultList.size()]));			
			
			// TIPO DE ESTADO ACTUAL
			TicketStateTypeDTO ticketstatetype = ticketstatetypeserver.getById(ticketevent.getCurrentstatetypekey());
						
			result.setStatuscode(ticketstatetype.getCode());
			result.setStatusmessage(ticketstatetype.getName());			
			
			return result;
		} catch (Exception e) {
			log.error("Error al obtener datos de servicio", e);
			return null;
		}
	}
	
	public MessageFolderDataDTO getMessageFolder(String sitename, String servicename, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", sitename);
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre especificado");
				return null;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", servicename);
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre especificado");
				return null;
			}
			ServiceDTO service = servicesList.get(0);
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), accesscode);
			if (accesses == null || accesses.length != 1) {
				log.info("No existe el servicio registrado para el sitio y la empresa especificados");
				return null;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey);
			} catch (NotFoundException e) {
				log.info("El servicio no está contratado para el sitio y la empresa especificados");
				return null;
			}
			
			MessageFolderDataDTO folderdata = new MessageFolderDataDTO();			
			MessageFolderDTO folder = folderserver.getById(contractedService.getFolderkey());
			
			// OBTIENE EL TIPO DE FOLDER
			MessageFolderTypeDTO folderType = foldertypeserver.getById(folder.getMessagefoldertypekey());
			folderdata.setType(folderType.getCode());
									
			if (folderType.getCode().equals("FTP")){
				
				// SI ES FTP, SE DEBE TRAER DATOS DE CONFIGURACION
				FTPMessageFolderDTO ftpmessage = ftpfolderserver.getById(contractedService.getFolderkey());			
				
				folderdata.setHost(ftpmessage.getHost());
				folderdata.setUsername(ftpmessage.getUsername());
				folderdata.setPassword(ftpmessage.getPassword());
				folderdata.setProtocol(ftpmessage.getProtocol());
			}
			
			folderdata.setPath(folder.getPath());	
			
			return folderdata;
		} catch (Exception e) {
			log.error("Error al obtener casilla para servicio", e);
			return null;
		}
	}

	public MessageFolderDataDTO getMessageFolderofUser(String path, String userlogid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		// Obtener el usuario por logid
		List<UserDTO> listUsers = userserver.getByProperty("rut", userlogid);
		if (listUsers == null || listUsers.isEmpty())
			throw new AccessDeniedException("No existe el usuario con el RUT especificado");
		UserDTO user = listUsers.get(0);
		// Obtener las casillas asociadas al usuario y con la ruta especificada
		MessageFolderDTO[] folders = folderserver.getMessageFoldersByPathAndUser(path, user.getId());
		if (folders == null || folders.length == 0)
			return null;
		MessageFolderDTO folder = folders[0];
		MessageFolderDataDTO result = new MessageFolderDataDTO();
		
		MessageFolderTypeDTO folderType = foldertypeserver.getById(folder.getMessagefoldertypekey());
		result.setType(folderType.getCode());
		
		if (folderType.getCode().equals("FTP")){
			
			// SI ES FTP, SE DEBE TRAER DATOS DE CONFIGURACION
			FTPMessageFolderDTO ftpmessage = ftpfolderserver.getById(folder.getId());			
			
			result.setHost(ftpmessage.getHost());
			result.setUsername(ftpmessage.getUsername());
			result.setPassword(ftpmessage.getPassword());
			result.setProtocol(ftpmessage.getProtocol());
		}
		
		result.setPath(folder.getPath());
		return result;
	}

	public MessageFolderDTO[] getMessageFoldersofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return folderserver.getByPropertyAsArray("company.id", companyid);
	}

	public MessageFormatDTO[] getMessageFormatsofService(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return formatserver.getByPropertyAsArray("service.id", serviceid);
	}

	public ServiceDTO getServiceByPK(Long serviceid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return serviceserver.getServiceByPK(serviceid);
	}

	public ServiceDTO[] getServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return serviceserver.getAllAsArray();
	}

	public SiteDTO getSiteByPK(Long siteid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserver.getSiteByPK(siteid);
	}

	public SiteDTO[] getSites() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserver.getAllAsArray();
	}

	public SiteServiceDTO[] getSiteServices() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserviceserver.getAllAsArray();
	}

	public FileEventDataDTO[] getUninformedFileEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		FileEventDataDTO[] result = fileeventserver.getUninformedFileEvents();
		// Marcar todos los eventos como procesados
		for (int i = 0; i < result.length; i++) {
			FileEventDataDTO eventdata = result[i];
			FileEventDTO event = fileeventserver.getById(eventdata.getId());
			event.setInformed(true);
			fileeventserver.updateFileEvent(event);
		}
		return result;
	}

	public ServiceEventDataDTO[] getUnprocessedServiceEvents() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		ServiceEventDataDTO[] result = serviceeventserver.getUnprocessedServiceEvents();
		String documentos="";
		for (ServiceEventDataDTO serviceEventDataDTO : result) {
			String resenddata = serviceEventDataDTO.getResenddata();
			
			JsonObject body;
			try {
				body = Json.createReader(new StringReader(resenddata)).readObject();
				
				if(body!=null && body.containsKey("orders") &&  !body.isNull("orders")){					
					List<JsonValue> docnumber = body.getJsonArray("orders");					
					for (Object jsonvalue : docnumber) {
						documentos = documentos+jsonvalue+",";
					}				
				}else if(body.containsKey("state")){
					JsonString js = body.getJsonString("state");
					documentos=js.getString();
				}
			} catch (Exception e) {
				log.info("Problemas al parsear el Json. Evento de servicio sin json para reprocesar o las ordenes ingresadas no son numeros "+resenddata);
			}		
		}
		// Marcar todos los eventos como procesados
		Date dateprocessed = new Date();
		for (int i = 0; i < result.length; i++) {
			ServiceEventDataDTO eventdata = result[i];
			ServiceEventDTO event = serviceeventserver.getById(eventdata.getId());
			event.setProcessed(true);
			event.setDateprocessed(dateprocessed);
			serviceeventserver.updateServiceEvent(event);
			
			if(eventdata.getResenddata() == null){			
				addlog("preparando solicitud a b2b",eventdata.getSitecode());
			}else{
				addlog("preparando solicitud a b2b de reenvios",eventdata.getSitecode());
			}
		}	
		return result;
	}

	public ContractedServiceDTO switchSiteService(Long siteid, Long serviceid, Long companyid, boolean active) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		ContractedServiceKey key = new ContractedServiceKey(siteid, serviceid, companyid);
		ContractedServiceDTO contservice = contractedserviceserver.getById(key, LockModeType.PESSIMISTIC_WRITE);
		contservice.setActive(active);
		// 2015-09-25 SI SE ACTIVA EL SERVICIO, ACTUALIZAR LA FECHA DE ACTIVACIÓN, SI NO, ANULARLA
		if (active)
			contservice.setActivation(new Date());
		else
			contservice.setActivation(null);
		contservice = contractedserviceserver.updateIdentifiable(contservice);
		SiteDTO site = siteserver.getById(siteid);
		ServiceDTO service = serviceserver.getById(serviceid);
		CompanyDTO company = companyserver.getById(companyid);
		if (active) {
			// 2015-06-11 DVI AL ACTIVAR EL SERVICIO, REGISTRAR AUTOMÁTICAMENTE UN EVENTO INICIAL
			ServiceEventDTO event = new ServiceEventDTO();
			event.setProcessed(false);
			event.setDatecreated(new Date());
			event.setSitekey(site.getId());
			event.setServicekey(service.getId());
			event.setCompanykey(company.getId());
			event = serviceeventserver.addServiceEvent(event);
			log.info("El servicio '" + service.getName() + "' se ha ACTIVADO para el sitio '" + site.getName() + "' y la empresa '" + company.getName() + "'. Se ha creado un evento de servicio automático.'");
		} else
			log.info("El servicio '" + service.getName() + "' se ha DESACTIVADO para el sitio '" + site.getName() + "' y la empresa '" + company.getName() + "'");
		return contservice;
	}

	public AccessDTO updateAccess(AccessDTO access) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return accessserver.updateIdentifiable(access);
	}

	public void updateContractedServiceMonitorData(ContractedServiceMonitorDataDTO[] monitordata) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		
		Date now = new Date();
		
		for (int i = 0; i < monitordata.length; i++) {
			
			ContractedServiceMonitorDataDTO data = monitordata[i];
			
			// Buscar sitio y servicio por nombre y validar que exista y que esté activo
			List<SiteDTO> sitesList = siteserver.getByProperty("name", data.getSitename());
			
			if (sitesList == null || sitesList.size() != 1) {
				log.info("No existe un sitio registrado con el nombre especificado :" + data.getSitename());
				continue;
			}
			SiteDTO site = sitesList.get(0);
			List<ServiceDTO> servicesList = serviceserver.getByProperty("name", data.getServicename());
			if (servicesList == null || servicesList.size() != 1) {
				log.info("No existe un servicio registrado con el nombre especificado : " + data.getServicename());
				continue;
			}
			ServiceDTO service = servicesList.get(0);
			SiteServiceKey sskey = new SiteServiceKey(site.getId(), service.getId());
			SiteServiceDTO sitesservice = null;
			try {
				sitesservice = siteserviceserver.getById(sskey);
				if (!sitesservice.getActive()) {
					log.info("El servicio se encuentra inactivo para el sitio especificado : " + data.getSitename());
					continue;
				}
			} catch (NotFoundException e) {
				log.info("No existe el servicio registrado para el sitio especificado : " + data.getSitename());
				continue;
			}
			// Buscar el acceso por código
			AccessDTO[] accesses = accessserver.getAccessesBySiteAndCode(site.getId(), data.getAccesscode());
			if (accesses == null || accesses.length != 1) {
				log.info("No existe el servicio registrado para el sitio y la empresa especificados : " + data.getSitename() + " , " + data.getAccesscode());
				continue;
			}
			AccessDTO access = accesses[0];
			// Verificar que el servicio esté contratado y activo
			ContractedServiceKey cskey = new ContractedServiceKey(site.getId(), service.getId(), access.getCompanykey());
			ContractedServiceDTO contractedService = null;
			try {
				contractedService = contractedserviceserver.getById(cskey, LockModeType.PESSIMISTIC_WRITE);
			} catch (NotFoundException e) {
				log.info("El servicio no está contratado para el sitio y la empresa especificados : " + data.getSitename() + " , " + data.getAccesscode());
				continue;
			}
			if (contractedService == null || !contractedService.getActive()) {
				log.info("El servicio contratado se encuentra inactivo para el sitio y la empresa especificados : " + data.getSitename() + " , " + data.getAccesscode());
				continue;
			}
			
			//Agregar detalles a la tabla ContractedService del SOA
			contractedService.setPendingmessages(data.getPendingmessages());
			contractedService.setLastmonitored(now);
			//Se agrega linea de prueba git
			contractedService.setDetail(data.getDetail() != null ? data.getDetail() : "");
			
			contractedserviceserver.updateIdentifiable(contractedService);
		}
	}

	public MessageFolderDTO updateMessageFolder(MessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Validar que no existe otra casilla con la misma ruta para el mismo proveedor
		if (folder.getPath() == null || folder.getPath().trim().length() == 0)
			throw new AccessDeniedException("La ruta especificada no es válida");
		PropertyInfoDTO p1 = new PropertyInfoDTO("path", "path", folder.getPath());
		PropertyInfoDTO p2 = new PropertyInfoDTO("company.id", "companyid", folder.getCompanykey());
		List<MessageFolderDTO> listFolders = folderserver.getByProperties(new PropertyInfoDTO[] { p1, p2 });
		if (listFolders != null && !listFolders.isEmpty() && !listFolders.get(0).getId().equals(folder.getId()))
			throw new AccessDeniedException("Ya existen casillas asociadas a la misma ruta para el mismo proveedor");
		return folderserver.updateMessageFolder(folder);
	}

	public ServiceDTO updateService(ServiceDTO service) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return serviceserver.updateService(service);
	}

	public SiteDTO updateSite(SiteDTO site) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserver.updateSite(site);
	}
	
	public FTPMessageFolderDTO addFTPMessageFolder(FTPMessageFolderDTO folder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Validar que no existe otra casilla con la misma ruta para el mismo proveedor
		if (folder.getPath() == null || folder.getPath().trim().length() == 0 || folder.getHost() == null || folder.getUsername() == null || folder.getPassword() == null)
			throw new AccessDeniedException("La ruta especificada no es válida");
		
		PropertyInfoDTO p1 = new PropertyInfoDTO("path", "path", folder.getPath());
		PropertyInfoDTO p2 = new PropertyInfoDTO("company.id", "companyid", folder.getCompanykey());
		PropertyInfoDTO p3 = new PropertyInfoDTO("messagefoldertype.id", "nessagefoldertypeid", folder.getMessagefoldertypekey());
		
		PropertyInfoDTO p4 = new PropertyInfoDTO("host", "host", folder.getHost());
		PropertyInfoDTO p5 = new PropertyInfoDTO("username", "username", folder.getUsername());
		PropertyInfoDTO p6 = new PropertyInfoDTO("password", "password", folder.getPassword());
		PropertyInfoDTO p7 = new PropertyInfoDTO("protocol", "protocol", folder.getProtocol());
		
		
		List<FTPMessageFolderDTO> listFolders = ftpfolderserver.getByProperties(new PropertyInfoDTO[] { p1, p2, p3, p4, p5, p6, p7 });
		
		if (listFolders != null && !listFolders.isEmpty() && !listFolders.get(0).getId().equals(folder.getId()))
			throw new AccessDeniedException("Ya existen casillas asociadas a la misma ruta para el mismo proveedor");
		
		
		return ftpfolderserver.addFTPMessageFolder(folder);
	}
	
	@Override
	public int countFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return fileeventserver.countFileEventsByContractedService(sitekey, servicekey, companykey);
	}
	
	@Override
	public AccessDataDTO[] getAccessDataofCompany(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return accessserver.getAccessDataofCompany(companyid);
	}
	
	@Override
	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.getAllAsArrayOrdered(new OrderCriteriaDTO("name", true));
	}
	
	@Override
	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return fileeventserver.getFileEventsByContractedService(sitekey, servicekey, companykey);
	}

	@Override
	public FileEventDataDTO[] getFileEventsByContractedService(Long sitekey, Long servicekey, Long companykey, boolean ispaginated, int pagenumber, int rows) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return fileeventserver.getFileEventsByContractedService(sitekey, servicekey, companykey, ispaginated, pagenumber, rows);
	}
	
//	public MessageFolderDTO[] getMessageFoldersofCompanyAndType(Long companyid) throws AccessDeniedException, OperationFailedException, NotFoundException {
//		//		carga en memoria lod tipos de carpeta
//		Hashtable<Long, String> typename = new Hashtable<>();			
//		MessageFolderTypeDTO[] types = getAllMessageFolderType();			
//		for (MessageFolderTypeDTO messageFolderTypeDTO : types) {
//			typename.put(messageFolderTypeDTO.getId(), messageFolderTypeDTO.getCode());
//		}			
//		//			mapea el tipo de carpeta al DTO a retornar
//		MessageFolderDTO[] folders = folderserver.getByPropertyAsArray("company.id", companyid);			
//		MessageFolderAndFolderTypeDTO result = new MessageFolderAndFolderTypeDTO();
//		
//		//		poblar result
//		for (int i = 0; i < folders.length; i++) {
//			
//		}	
//		
//		return null;
//	}		
	
	public MessageFolderDTO getMessageFolderById(Long mfolderid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return folderserver.getById(mfolderid);
	}
	
	public MessageFolderTypeDTO[] getAllMessageFolderType() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return foldertypeserver.getAllAsArray();
	}
	
	public FTPMessageFolderDTO getFtpMessageFolderbyId(Long folderid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return ftpfolderserver.getFTPMessageFolderByPK(folderid);
	}

	public MessageFolderTypeDTO getMessageFolderTypeById (Long foldertypeid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return foldertypeserver.getById(foldertypeid);
	}
	
	@Override
	public SiteServiceReportDTO[] getSiteServiceReport() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return siteserviceserver.getSiteServiceReport();
	}
	
	@Override
	public InitParamCSDTO[] getCredentialsBySiteService(String sitecode, String servicecode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		List<InitParamCSDTO> result = new ArrayList<InitParamCSDTO>();
		
		List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicecode);
		if (servicesList == null || servicesList.size() != 1) {
			log.info("No existe un servicio registrado con el codigo especificado : " +sitecode);
			return null;
		}
		List<SiteDTO> siteList    = siteserver.getByProperty("code", sitecode); 
		if (siteList == null || siteList.size() != 1) {
			log.info("No existe un sitio registrado con el codigo especificado : " +sitecode);
			return null;
		}
		PropertyInfoDTO p1 = new PropertyInfoDTO("service.id", "service_id", servicesList.get(0).getId());
		PropertyInfoDTO p2 = new PropertyInfoDTO("site.id", "site_id", siteList.get(0).getId());
		
		List <ContractedServiceDTO> contractedServices = contractedserviceserver.getByProperties(new PropertyInfoDTO[] { p1, p2,});
		
		for (ContractedServiceDTO contractedServiceDTO : contractedServices) {
			if(contractedServiceDTO.getExtratorcredentials()== null)
				continue;
			InitParamCSDTO csdto = new InitParamCSDTO();
			
			CompanyDTO company= companyserver.getById(contractedServiceDTO.getCompanykey());
			if(company == null){
				log.info("no existe la compañia para ese  id proporcionado "+contractedServiceDTO.getCompanykey());
				return null;
			}
			
			AccessDTO access = accessserver.getById(new AccessKey(contractedServiceDTO.getCompanykey(), contractedServiceDTO.getSitekey()));
			
			if(access == null ){
				log.info("No existe el access para el sevicio y sitio proporcionado");
				return null;
			}
			
			csdto.setAccesscode(access.getCode());
			csdto.setCompanyname(company.getName());
			csdto.setCredenciales(contractedServiceDTO.getExtratorcredentials());
			log.info("Se consultaron credenciales para "+sitecode+"-"+servicecode+" de forma exitosa");
			result.add(csdto);
		}
		//InitParamCSDTO[] array = new InitParamCSDTO[result.size()];
		return (InitParamCSDTO[])result.toArray(new InitParamCSDTO[result.size()]);
	}
	
	@Override
	public InitParamCSDTO getCredentialsBySiteServiceAndCompany(String sitecode, String servicecode, String accesscode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		InitParamCSDTO result = new InitParamCSDTO();
		
		List<ServiceDTO> servicesList = serviceserver.getByProperty("code", servicecode);
		if (servicesList == null || servicesList.size() != 1) {
			log.info("No existe un servicio registrado con el codigo especificado : " +sitecode);
			return null;
		}
		List<SiteDTO> siteList    = siteserver.getByProperty("code", sitecode); 
		if (siteList == null || siteList.size() != 1) {
			log.info("No existe un sitio registrado con el codigo especificado : " +sitecode);
			return null;
		}
		List<AccessDTO> accessesList = accessserver.getByProperty("code", accesscode); 
		if (accessesList == null || accessesList.size() == 0) {
			log.info("No existe una compañía registrada con el codigo especificado : " +accesscode);
			return null;
		}
		PropertyInfoDTO p1 = new PropertyInfoDTO("service.id", "service_id", servicesList.get(0).getId());
		PropertyInfoDTO p2 = new PropertyInfoDTO("site.id", "site_id", siteList.get(0).getId());
		PropertyInfoDTO p3 = new PropertyInfoDTO("company.id", "company_id", accessesList.get(0).getCompanykey());
		
		List <ContractedServiceDTO> contractedServices = contractedserviceserver.getByProperties(new PropertyInfoDTO[] { p1, p2, p3,});
		
		ContractedServiceDTO contractedservice = contractedServices.get(0);
		if(contractedservice.getExtratorcredentials()== null || contractedservice.getExtratorcredentials().isEmpty()){
			log.info("No se encontraron credenciales para "+sitecode+"-"+servicecode+"-"+accesscode);
		}
			
		CompanyDTO company= companyserver.getById(contractedservice.getCompanykey());
		if(company == null){
			log.info("no existe la compañia para ese  id proporcionado "+contractedservice.getCompanykey());
			return null;
		}
		
		AccessDTO access = accessserver.getById(new AccessKey(contractedservice.getCompanykey(), contractedservice.getSitekey()));
		if(access == null ){
			log.info("No existe el access para el sevicio y sitio proporcionado");
			return null;
		}
		
		result.setAccesscode(access.getCode());
		result.setCompanyname(company.getName());
		result.setCredenciales(contractedservice.getExtratorcredentials());
		
		log.info("Se consultaron credenciales para "+sitecode+"-"+servicecode+" de forma exitosa");
		
		return result;
	}
	
	
	private void addlog(String description, String sitename, String servicename, String accesscode, String numorder, String comentcode) {
		addlog( description,  sitename,  servicename,  accesscode,  numorder,  comentcode, true);
	}
	
	private void addlog(String description, String sitename){
		log.info(description +" " + sitename);	
	}
	
	public void addlogByCompanyRut(String description, String sitename, String servicename, String companyrut, String numorder, String comentcode, Boolean iscontracted) {
		try {
			String accesscode = accessserver.getAccessByCompanyRutAndSiteName(companyrut, sitename);
			addlog(description, sitename, servicename, accesscode, numorder, comentcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addlogByCompanyAcess(String description, String sitename, String servicename, String access, String numorder, String comentcode, Boolean iscontracted) {
		try {
			
			addlog(description, sitename, servicename, access, numorder, comentcode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
		
	public void addlog(String description, String sitename, String servicename, String accesscode, String numorder, String comentcode, Boolean iscontracted) {
	
	log.info(description +" " + sitename);		
		try {
			 	
			doAddDocumentTrace(sitename, servicename, accesscode, numorder, comentcode, description, iscontracted);
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			log.error("no se pudo registrar la traza de documento");
		}
	}
	
//	addlog pero con Integer en site y service
	private void addlogById(String description, Integer siteid, Integer serviceid, String accesscode, String numorder, String comentcode) {
		addlogById( description,  siteid,  serviceid,  accesscode,  numorder,  comentcode, true);
	}
	public void addlogById(String description, Integer siteid, Integer serviceid, String accesscode, String numorder, String comentcode, Boolean iscontracted) {
		
		log.info(description +" :" + accesscode);	
		
		SiteDTO site= new SiteDTO();
		ServiceDTO service= new ServiceDTO();
		
		try {
			site = siteserver.getById(siteid.longValue());
		} catch (OperationFailedException | NotFoundException e1) {			
			log.info("Sitio no encontrado");
		}			
		
		try {
			service = serviceserver.getById(serviceid.longValue());
		} catch (OperationFailedException | NotFoundException e1) {			
			log.info("servicio no encontrado");
			
		}
		if (site.getName() != null && service.getName() != null){
			try {			 	
				doAddDocumentTrace(site.getName(), service.getName(), accesscode, numorder, comentcode, description, iscontracted);
			} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
				log.error("no se pudo registrar la traza de documento");
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private DocumentTraceDTO doAddDocumentTrace(String sitename, String servicename, String accesscode, String numorder, String comentcode, String description, Boolean iscontracted) throws AccessDeniedException, OperationFailedException, NotFoundException {

		List<DocumentTraceTypeDTO> type = documentracetypeserver.getByProperty("code", comentcode);	
		DocumentTraceDTO dtdto = new DocumentTraceDTO();	
		
		dtdto.setCompanyRut(NOT_RUT_COMPANY);
		dtdto.setAccess(accesscode);
		dtdto.setSiteCode(sitename);
		dtdto.setServiceCode(servicename);
		dtdto.setDescription(description);
		dtdto.setTimestamp(new Date());
		
//		System.out.println(numorder);
		if(numorder!=null && !numorder.equals("") && !numorder.equals("null")){
			dtdto.setNumDoc(new Long(numorder));		
		}
		if(type != null && !type.isEmpty()){
			DocumentTraceTypeDTO dto = type.get(0);
			dtdto.setDocumenttracetypekey(dto.getId());	
		}				

		CompanyDTO[] companys = companyserver.getCompanyByAccessAndSitename(sitename, accesscode);		
		if(companys != null && companys.length > 0)
			dtdto.setCompanyRut(companys[0].getRut());		
		
		documenttraceserver.addDocumentTrace(dtdto);		
		DocumentTraceLastDaysDTO dtlddto = new DocumentTraceLastDaysDTO();
		
		BeanExtenderFactory.copyProperties(dtdto, dtlddto);
		
		if(iscontracted && !dtlddto.getCompanyRut().equals(NOT_RUT_COMPANY))
			documenttracelastdaysserver.addDocumentTraceLastDays(dtlddto);
		
		return dtdto;
	}
	
	public void doAddServiceEventBySericeProvider(String companyrut, String servicecode, String sitename){
		
		try {
			ContractedServiceForCustomDTO[] csfc = serviceserver.getContractedServicesForCustom(companyrut, servicecode, sitename);
			
			if(csfc.length>0){
				for (ContractedServiceForCustomDTO contractedServiceForCustomDTO : csfc) {
					
					PropertyInfoDTO[] props = new PropertyInfoDTO[4];
					props[0] = new PropertyInfoDTO("site.id", "sitekey", contractedServiceForCustomDTO.getSiteid());
					props[1] = new PropertyInfoDTO("service.id", "servicekey",contractedServiceForCustomDTO.getServiceid());
					props[2] = new PropertyInfoDTO("company.id", "companykey", contractedServiceForCustomDTO.getCompanyid());
					props[3] = new PropertyInfoDTO("processed", "processed", false);			
					//props[4] = new PropertyInfoDTO("custom","custom",contractedServiceForCustomDTO.getCustom());
					
					List<ServiceEventDTO> prevevents = serviceeventserver.getByProperties(props);
					
					if (prevevents == null || prevevents.isEmpty()) {
						//implica que o hay eventos de servicio sin procesar para sitio, servicio, proveedor
						
						ServiceEventDTO event = new ServiceEventDTO();
						event.setProcessed(false);
						event.setDatecreated(new Date());
						event.setSitekey(contractedServiceForCustomDTO.getSiteid());
						event.setServicekey(contractedServiceForCustomDTO.getServiceid());
						event.setCompanykey(contractedServiceForCustomDTO.getCompanyid());			
						event.setCustom(contractedServiceForCustomDTO.getCustom());						
						
						event = serviceeventserver.addServiceEvent(event);
					}
				}		
			}
		}catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
				e.printStackTrace();
		}		
	}
	
	public void doMonitor(){
			try {
				ContractedServiceDTO[] cs = contractedserviceserver.getMonitoredContractedServices();
				if(cs.length>0){
					for (ContractedServiceDTO contractedServiceDTO : cs) {	
						int delay =contractedServiceDTO.getCustommaxdelayendflow();
						LocalDateTime pasttime = LocalDateTime.now().minusMinutes(delay);
						ServiceEventDTO[] serviceevents = serviceeventserver.getServiceEventOfContract(contractedServiceDTO);
						if(serviceevents.length>0){ 
							Date createddate = serviceevents[0].getDatecreated();
							Instant instant  = createddate.toInstant();
							LocalDateTime createdtime =   LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
							if(createdtime.compareTo(pasttime) < 0 ){
								FileEventDTO[] fileevent = fileeventserver.FileEventOfContract(serviceevents[0],delay);
								if(fileevent.length==0){
									contractedserviceserver.updateContractedService(contractedServiceDTO,true);
								}else{
									contractedserviceserver.updateContractedService(contractedServiceDTO,false);
								}
							}
						}else{						 
							contractedserviceserver.updateContractedService(contractedServiceDTO,false);
						}
					}
				}
			} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
				e.printStackTrace();
			}

	}
	
	private String createErrorFile(String ticketnumber, TicketEventStatusDataDTO[] errores, String servicecode){
		String bucketname = System.getProperty("S3_BUCKET_NAME");
		String folderPath = System.getProperty("ERROR_PLFILE_PATH");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String[][] data = new String[errores.length][4];
		int i = 0;
		for (TicketEventStatusDataDTO detail : errores){
			data[i][0] = detail.getCode() == null ? "" : detail.getCode();
			data[i][1] = detail.getType() == null ? "" : detail.getType();
			data[i][2] = detail.getState() == null ? "" : detail.getState();
			data[i][3] = detail.getDescription() == null ? "" : detail.getDescription();
			i++;
		}
		
		String filename = servicecode+"_Errors" + ticketnumber + "_" + sdf.format(new Date()) + ".csv";
		try {
			BBRStringUtils.doCSVString(data, "|", filename);
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("No fue posible generar archivo de errores " + ticketnumber);
		}
		try {
			File file = new File(folderPath + filename);
			S3FileDataDTO filedata = fileservicemanager.createObject(bucketname, filename, file, filename);
			// se setea la url publica
			return String.valueOf(filedata.getSignedUrl());
		} catch (Exception e2) {
			e2.printStackTrace();
			log.error("No fue posible subir archivo de errores al S3 " + ticketnumber);
		}
		return null;
	}
	
}