package bbr.b2b.logistic.datings.managers.classes;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.datings.classes.BlockingTypeServerLocal;
import bbr.b2b.logistic.datings.classes.DatingResourceServerLocal;
import bbr.b2b.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.logistic.datings.classes.DockServerLocal;
import bbr.b2b.logistic.datings.classes.ModuleServerLocal;
import bbr.b2b.logistic.datings.classes.PreDatingResourceGroupServerLocal;
import bbr.b2b.logistic.datings.classes.ReserveDetailServerLocal;
import bbr.b2b.logistic.datings.classes.ResourceBlockingGroupServerLocal;
import bbr.b2b.logistic.datings.classes.ResourceBlockingServerLocal;
import bbr.b2b.logistic.datings.data.wrappers.BlockingTypeW;
import bbr.b2b.logistic.datings.data.wrappers.DatingResourceW;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.datings.data.wrappers.DockW;
import bbr.b2b.logistic.datings.data.wrappers.ModuleW;
import bbr.b2b.logistic.datings.data.wrappers.PreDatingResourceGroupW;
import bbr.b2b.logistic.datings.data.wrappers.ReserveDetailPK;
import bbr.b2b.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingGroupW;
import bbr.b2b.logistic.datings.data.wrappers.ResourceBlockingW;
import bbr.b2b.logistic.datings.entities.ReserveDetailId;
import bbr.b2b.logistic.datings.managers.interfaces.DatingReportManagerLocal;
import bbr.b2b.logistic.datings.report.classes.AddDatingAndDetailsInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingDataDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingResultDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedPreDatingResourceGroupDataDTO;
import bbr.b2b.logistic.datings.report.classes.AssignedResourceBlockingDataDTO;
import bbr.b2b.logistic.datings.report.classes.BlockingTypeArrayResultDTO;
import bbr.b2b.logistic.datings.report.classes.BlockingTypeDTO;
import bbr.b2b.logistic.datings.report.classes.CheckExistsVendorReserveInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DatingInformationDTO;
import bbr.b2b.logistic.datings.report.classes.DatingInformationResultDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestRejectInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DatingRequestReportResultDTO;
import bbr.b2b.logistic.datings.report.classes.DatingTimeDockDataDTO;
import bbr.b2b.logistic.datings.report.classes.DeleteAllPreDatingResourceGroupInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DeleteAllResourceBlockingGroupInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DeleteDatingInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DeletePreDatingResourceGroupDetailInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DeleteResourceBlockingGroupDetailInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.DockDTO;
import bbr.b2b.logistic.datings.report.classes.DocksArrayResultDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.ExcelDatingReportResultDTO;
import bbr.b2b.logistic.datings.report.classes.ModuleDataDTO;
import bbr.b2b.logistic.datings.report.classes.ModulesArrayResultDTO;
import bbr.b2b.logistic.datings.report.classes.PreDatingReserveDetailArrayResultDTO;
import bbr.b2b.logistic.datings.report.classes.PreDatingReserveDetailDTO;
import bbr.b2b.logistic.datings.report.classes.PreDatingReserveDetailInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.PreDatingResourceGroupInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.PreDatingResourceGroupResultDTO;
import bbr.b2b.logistic.datings.report.classes.ReceptionCloseResultDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailBlockingDataDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailBlockingReportDTO;
import bbr.b2b.logistic.datings.report.classes.ReserveDetailDTO;
import bbr.b2b.logistic.datings.report.classes.ResourceBlockingGroupDetailDataInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.ResourceBlockingGroupInitParamDTO;
import bbr.b2b.logistic.datings.report.classes.ResourceBlockingGroupResultDTO;
import bbr.b2b.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DatingRequestServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DatingRequestW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResponseDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResultDTO;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.managers.interfaces.ASNMessageManagerLocal;
import bbr.b2b.logistic.managers.interfaces.DatingReportManagerRemote;
import bbr.b2b.logistic.parameters.classes.ParameterServerLocal;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterW;
import bbr.b2b.logistic.report.classes.BooleanResultDTO;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.logistic.utils.IWeekDayConstants;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/DatingReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingReportManager implements DatingReportManagerLocal, DatingReportManagerRemote  {
	
	private static Logger logger = Logger.getLogger(DatingReportManager.class);
	
/*	// ASN LGFDATA
	private static final String ASN_PREFIX_FILE	 						= "ISS";
	
	private static JAXBContext jcLgfData = null;
	
	private static JAXBContext getJCLGFDATA() throws JAXBException {
		if (jcLgfData == null)
			jcLgfData = JAXBContext.newInstance("bbr.b2b.logistic.xml.lib_shipment");
		return jcLgfData;
	}*/
		
	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanagerlocal;
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	DatingResourceServerLocal datingresourceserver;
	
	@EJB
	DatingRequestServerLocal datingrequestserver;
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DockServerLocal dockserver;
	
	@EJB
	DocumentServerLocal documentserver;
	
	@EJB
	DocumentStateServerLocal documentstateserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	DvrDeliveryStateServerLocal dvrdeliverystateserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DvrOrderStateServerLocal dvrorderstateserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;
	
	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver;
	
	@EJB
	DvrOrderDeliveryDetailServerLocal dvrorderdeliverydetailserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	ModuleServerLocal moduleserver;
	
	@EJB
	ParameterServerLocal parameterserver;
	
	@EJB
	BlockingTypeServerLocal blockingtypeserver;
	
	@EJB
	PreDatingResourceGroupServerLocal predatingresourcegroupserver;
	
	@EJB
	ResourceBlockingServerLocal resourceblockingserver;
	
	@EJB
	ResourceBlockingGroupServerLocal resourceblockinggroupserver;
	
	@EJB
	ReserveDetailServerLocal reservedetailserver;
	
	@EJB
	DeliveryReportManagerLocal deliveryreportmanager;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ASNMessageManagerLocal asnmessagemanager;
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
/*	@Resource
	private ManagedExecutorService executor;
	
	private void doBackUpMessage(String content, String number, String msgType) {

		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}*/
	
	// Agrega DatingResource para nuevos locales con agenda
	public BaseResultDTO doAddDatingResourceByLocation(String nameInitialModule, String nameFinalModule, String locationcode, boolean hasChangeDay) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try{
			// Location
			LocationW locationW = locationserver.getByPropertyAsSingleResult("code", locationcode);

			// Docks asociados a LOCAL
			DockW[] dockArr = dockserver.getByPropertyAsArray("location.id", locationW.getId());

			// Modules
			ModuleW moduleInit = moduleserver.getByPropertyAsSingleResult("name", nameInitialModule);
			ModuleW moduleEnd = moduleserver.getByPropertyAsSingleResult("name", nameFinalModule);

			ModuleW[] moduleInterval = moduleserver.getModulesByInterval(moduleInit.getStarts(), moduleEnd.getEnds(), hasChangeDay);

			for (int i = 0; i < dockArr.length; i++) {
				// Obtiene maximo visual order para el dock actual
				int visualorderStart = (datingresourceserver.getMaxVisualOrderbyDock(dockArr[i].getId())) + 1;
				
				
				for (int j = 0; j < moduleInterval.length; j++) {
					DatingResourceW datingResourceW = new DatingResourceW();
					datingResourceW.setDockid(dockArr[i].getId());
					datingResourceW.setModuleid(moduleInterval[j].getId());
					datingResourceW.setActive(true);
					datingResourceW.setVisualorder(visualorderStart + j);
					datingResourceW = datingresourceserver.addDatingResource(datingResourceW);
				}
			}
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}

	
	
	// Información de la cita
	public DatingInformationResultDTO getDatingInformationData(DvrDeliveryInitParamDTO initParamDTO) {
		DatingInformationResultDTO resultDTO = new DatingInformationResultDTO();
		DatingInformationDTO datinginformation = new DatingInformationDTO(); 
		DatingTimeDockDataDTO[] datingtimedockdata;
		
		try {
			// Obtiene lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Datos de Cita
			datinginformation = datingserver.getDatingInformation(dvrdeliveryW.getId());
			
			// Detalle de andenes y horarios
			datingtimedockdata = datingserver.getDatingTimeDockData(dvrdeliveryW.getId());
			
			resultDTO.setDatinginformation(datinginformation);
			resultDTO.setDatingtimedockdata(datingtimedockdata);
			return resultDTO;
					
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public DatingRequestReportResultDTO getDatingRequestReport(DatingRequestReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated) {
		DatingRequestReportResultDTO resultDTO = new DatingRequestReportResultDTO();
		DatingRequestReportDataDTO[] datingrequestreportdata;
		Integer total;
		
		try{
			// Estado Cita
			DvrDeliveryStateTypeW reqSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			
			
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];

			// Obtener Proveedor (excepto si se informa -1)
			Long vendorid = -1L;
			if(! initParamDTO.getVendorcode().equals("-1")){
				VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
				if(vendorArr.length == 0){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
				}
				vendorid = vendorArr[0].getId();
			}

			// Validar que se ingresen fechas
			if(initParamDTO.getSince() == null || initParamDTO.getUntil() == null){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L500");
			}
			
			// Datos
			datingrequestreportdata = datingrequestserver.getDatingRequestReport(vendorid, locationW.getId(), reqSt.getId(), initParamDTO.getSince(), initParamDTO.getUntil(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			// Si es consulta desde el filtro, se muesta información de la paginación
			if(withTotals){
				total = datingrequestserver.getCountDatingRequestReport(vendorid, locationW.getId(), reqSt.getId(), initParamDTO.getSince(), initParamDTO.getUntil());

				// Setea inforamción de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPagenumber());
				pageInfo.setRows(datingrequestreportdata.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}

			resultDTO.setDatingrequestreportdata(datingrequestreportdata);
			return resultDTO;
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4100");
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4100");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4100");
		}
		
	}

	public BaseResultDTO doRejectDatingRequest(DatingRequestRejectInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
				
		try{
			// Valida que exista un despacho asociado a la solicitud de cita
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if (dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Valida que el despacho asociado esté en estado 'Cita Solicitada'
			DvrDeliveryStateTypeW dstRequested = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			if (!dvrdeliveryW.getCurrentstatetypeid().equals(dstRequested.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2234");
			}
			
			// Valida que se ingresó el comentario
			if (initParamDTO.getReason() == null || initParamDTO.getReason().length() == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2233");
			}
			
			// Obtiene el proveedor
			VendorW vendorW = vendorserver.getById(dvrdeliveryW.getVendorid());
			
			// Elimina la actual solicitud de cita
			DatingRequestW[] datingRequestArr = datingrequestserver.getByPropertyAsArray("dvrdelivery.id", initParamDTO.getDvrdeliveryid());
			if (datingRequestArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2220");
			}
			DatingRequestW datingRequestW = datingrequestserver.getById(datingRequestArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);

			// Almacena correo de usuario que solicitó la cita
			String emailRequester = datingRequestW.getEmail(); 
			
			datingrequestserver.deleteElement(datingRequestW.getId());
			
			LocalDateTime now = LocalDateTime.now();
			
			// Actualiza el estado del despacho a 'Re-agendar'
			DvrDeliveryStateTypeW dstRescheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
			
			dvrdeliveryW.setCurrentstatetypeid(dstRescheduled.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			
			// Estado
			DvrDeliveryStateW dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setWhen(now);
			dvrdeliverystateW.setUser(userdataDTO.getUsername());
			dvrdeliverystateW.setUsertype(userdataDTO.getUsertype());
			dvrdeliverystateW.setUserwhen(null);
			dvrdeliverystateW.setComment(initParamDTO.getReason());
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(dstRescheduled.getId());
						
			dvrdeliverystateW = dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);
			
			// Envía un correo electrónico al proveedor asociado a la solicitud de cita informando el rechazo
			String subject = "B2B Hites: Rechazo Cita para Entrega " + dvrdeliveryW.getNumber();			
			String mailSender = LogisticConstants.getMailSender();
			String mailSession = LogisticConstants.getMAIL_SESSION();
			String[] mailReceiver = new String[1];
			mailReceiver[0] = emailRequester;
			
			if (mailReceiver[0] != null && !mailReceiver[0].equals("")) {
				String msgtext =
						"Estimado(a) Usuario(a):<br><br> " + //
						"La solicitud de cita para la entrega N° " +  dvrdeliveryW.getNumber() + " ha sido rechazada con el siguiente comentario:<br><br>" + // 									
						initParamDTO.getReason() + "<br><br>" + //
						"Atte.<br>" + //
						"B2B Hites<br><br>" + //
						"<b>Favor no responder este correo dado que es generado de manera automática por el sistema B2B.<b>";
				
				// Envía correo a tabla de correos pendientes
				schedulermailmanager.doAddMailToQueue(mailSender, mailReceiver, null, null, subject, msgtext, mailSession, "RECHAZO_CITA", dvrdeliveryW.getNumber().toString());		
			}
			
			// Agrega en log rechazo de solicitud
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Rechazar solicitud de Cita" + "," +  "doRejectDatingRequest" + "," + "Rechazo de Cita Entrega Nro " + dvrdeliveryW.getNumber());
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	// Validar si un proveedor posee reservas para un local en  fecha seleccionada
	public BooleanResultDTO checkExistsVendorReserveByLocationAndDate(CheckExistsVendorReserveInitParamDTO initParamDTO) {
		BooleanResultDTO resultDTO = new BooleanResultDTO();
		
		try {
			// Formatea fecha
			LocalDateTime reservedate = initParamDTO.getReservedate().toLocalDate().atStartOfDay();
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			// Valida si existen reservas del proveedor asociadas
			int countvendorreserve = predatingresourcegroupserver.getCountPreDatingResourceGroupByLocationAndDate(vendorW.getId(), locationW.getId(), reservedate);
			
			if(countvendorreserve >0) {
				resultDTO.setValid(true);
			}
			else {
				resultDTO.setValid(false);
			}
			
			return resultDTO;
			

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	

	
	public BaseResultDTO doAddDatingandDetails(AddDatingAndDetailsInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
				
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		LocalDateTime now = LocalDateTime.now();
		
		// Lista de detalles de reserva que deben ser eliminadas
		List<ReserveDetailW> reserveDetailToDeleteList = new ArrayList<ReserveDetailW>();

		// Mensajes de error
		String errorMsg = "";
		
		try {
			Map<Long, ModuleW> moduleMap = new HashMap<Long, ModuleW>();
			
			// Estados del Despacho
			DvrDeliveryStateTypeW asgSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			
			// Estados de OC no vigentes			
			DvrOrderStateTypeW[] nonValidDvrOCSt = dvrorderstatetypeserver.getByPropertyAsArray("valid", false);
			
			// Fecha
			LocalDateTime since = initParamDTO.getRequesteddate().toLocalDate().atStartOfDay();
			LocalDateTime until = initParamDTO.getRequesteddate().toLocalDate().atTime(LocalTime.MAX);


			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			// Obtiene lote
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrdeliveryid());
			props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertiesAsArray(props);
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Solicitud de cita asociada
			DatingRequestW[] datingrequestArr = datingrequestserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			if(datingrequestArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2220");
			}
			DatingRequestW datingrequestW = datingrequestArr[0]; 
			
			// Se cargan modulos horarios
			ModuleW[] moduleArr = moduleserver.getAllAsArray();
			for(ModuleW moduleW : moduleArr){
				moduleMap.put(moduleW.getId(), moduleW);
			}
			
			// Obtiene parámetro de cantidad de dias hacia adelante en que se puede agendar la cita
			ParameterW[] daystoDatingParamArr = parameterserver.getByPropertyAsArray("code", "DIAS_CITA");
			if(daystoDatingParamArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4205");
			}
			
			// Valida que valor del parámentro sea numérico
			Long daystoDatingValue ;
			try{
				// Si parámetro estpa inactivo, toma valor 0 por defecto
				if(! daystoDatingParamArr[0].getActive()) {
					daystoDatingValue = 0L;
				}
				else {
					daystoDatingValue = Long.valueOf(daystoDatingParamArr[0].getValue());	
				}
			} catch (Exception e) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4206");
			}
			
			// El sistema valida que se haya seleccionado al menos un módulo y andén.
			if(initParamDTO.getReservedetail() == null || initParamDTO.getReservedetail().length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4201");
			}
			
			// El sistema valida que la fecha deseada por el proveedor cuando solicitó la cita es mayor o igual a hoy.
			if(initParamDTO.getRequesteddate().toLocalDate().isBefore(now.toLocalDate())){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4202");
			}
			
			// Obtiene despachos asociados al delivery seleccionado 
			DvrOrderDeliveryW[] dvrorderdeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
						
			// Obtiene ids de las ordenes asociadas
			Long[] dvrorderids = Arrays.stream(dvrorderdeliveryArr).map(od -> od.getDvrorderid()).toArray(Long[]::new);

			// Obtiene Ordenes
			DvrOrderW[] dvrordersArr = dvrorderserver.getDvrOrderByIdsAndVendor(dvrorderids, vendorW.getId());
			
			// El sistema valida que las órdenes de compra seleccionadas estén en estado vigente
			// Busca las órdenes que están en estados no vigentes
			DvrOrderW[] nonValidStOrderArr = Arrays.stream(dvrordersArr)
							.filter(oc -> Arrays.stream(nonValidDvrOCSt).anyMatch(st -> st.getId().equals(oc.getCurrentstatetypeid())))
							.toArray(DvrOrderW[]::new);
			
			for (DvrOrderW nonValidStOrder : nonValidStOrderArr) {
				errorMsg = "La orden " + nonValidStOrder.getNumber() + " no se encuentra en un estado vigente"; 
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
			}			

			for(DvrOrderW dvrorderW: dvrordersArr){
				// El sistema valida que la fecha de agendamiento seleccionada igual o mayor al día actual
				if(initParamDTO.getRequesteddate().toLocalDate().isBefore(now.toLocalDate())) {
					errorMsg = "No se puede asignar cita para días ya pasados";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
				}

				// El sistema valida que la fecha de agendamiento seleccionada sea al menos X cantidad de días posteriores a la fecha actual, 
				//contando a partir del día actual, siendo X un valor paramétrico del sistema (inicialmente 0, o sea, día actual).
				if(initParamDTO.getRequesteddate().toLocalDate().isBefore(now.toLocalDate().plusDays(daystoDatingValue))) {
					errorMsg = "No se puede asignar cita para el día seleccionado. Debe ser al menos " + daystoDatingValue + " días posterior al día actual";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
				}
				
				// El sistema valida que cada orden tenga fecha de vencimiento igual o posterior a la fecha actual
				if (dvrorderW.getExpiration().toLocalDate().isBefore(initParamDTO.getRequesteddate().toLocalDate())) {
					errorMsg = "Para el día seleccionado la orden " + dvrorderW.getNumber() + " ya estará vencida";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
				}
								
				// El sistema valida que la fecha seleccionada sea menor que la menor fecha de vencimiento de las órdenes de compra
				if(initParamDTO.getRequesteddate().toLocalDate().isAfter(dvrorderW.getExpiration().toLocalDate())){
					errorMsg = "El día seleccionado es posterior al vencimiento de la orden " + dvrorderW.getNumber();
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
				}				
			}

			// El sistema valida que la entrega no tenga una cita previa asignada (En caso de una confirmación paralela) (rechequeo, se hace justo andes de crear las reservas)
			DatingW[] datingArr = datingserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			if(datingArr.length > 0){
				errorMsg = "La Entrega " + dvrdeliveryW.getNumber() + " ya tiene cita asignada";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
			}
			
			// El sistema valida que hayan módulos horarios seleccionados y que estén disponibles.
			if(initParamDTO.getReservedetail().length == 0) {
				errorMsg = "Debe seleccionar al menos un módulo horario.";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4200", errorMsg, false);
			}
			
			// Se obtienen las reservas y bloqueos para el día solicitado excluyendo las del proveedor
			ReserveDetailDTO[] reservedetaildtoArr = reservedetailserver.getReserveDetailsByDateAndLocationExcludeVendor(since, until, locationW.getId(), vendorW.getId());
			Map<ReserveDetailId, ReserveDetailDTO> reservedetailMap = new HashMap<ReserveDetailId, ReserveDetailDTO>();
			for(ReserveDetailDTO reservedetaildto : reservedetaildtoArr){
				ReserveDetailId reservedetailKey = new ReserveDetailId(-1L, reservedetaildto.getModuleid(), reservedetaildto.getDockid());
				
				if(! reservedetailMap.containsKey(reservedetailKey)){
					reservedetailMap.put(reservedetailKey, reservedetaildto);
				}	
			}
			
			// Se obtienen las reservas del proveedor para el día solicitado
			ReserveDetailW[] vendorreservedetailArr = reservedetailserver.getReserveDetailByVendorDateLocation(initParamDTO.getRequesteddate(), locationW.getId(), vendorW.getId()); 
			Map<ReserveDetailId, ReserveDetailW> vendorreservedetailMap = new HashMap<ReserveDetailId, ReserveDetailW>();
			for(ReserveDetailW vendorreservedetailW : vendorreservedetailArr){
				ReserveDetailId reservedetailKey = new ReserveDetailId(-1L, vendorreservedetailW.getModuleid(), vendorreservedetailW.getDockid());
				
				if(! vendorreservedetailMap.containsKey(reservedetailKey)){
					vendorreservedetailMap.put(reservedetailKey, vendorreservedetailW);
				}	
			}
			
			// Itera sobre reservas a crear, para validar si hay disponibilidad de dock en el modulo
			List<Long> resourceBlockingList = new ArrayList<Long>();
			List<Long> predatingResourceList = new ArrayList<Long>();
			ResourceBlockingW resourceBlocking = null;
			for (int i=0; i<initParamDTO.getReservedetail().length; i++){
				ReserveDetailId reservedetailpk = new ReserveDetailId(-1L, initParamDTO.getReservedetail()[i].getModuleid(), initParamDTO.getReservedetail()[i].getDockid());
				
				// Si ya existe reserva
				if(reservedetailMap.containsKey(reservedetailpk)){
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2229");
				}
				
				// Si existe una reserva para el proveedor
				// se marca para borrado
				if(vendorreservedetailMap.containsKey(reservedetailpk)){
					ReserveDetailW reservedetailtodelete = vendorreservedetailMap.get(reservedetailpk);
					reserveDetailToDeleteList.add(reservedetailtodelete);
					
					// Obtener la lista de bloqueos de recursos que van a eliminar detalles
					if (!resourceBlockingList.contains(reservedetailtodelete.getReserveid())) {
						resourceBlocking = resourceblockingserver.getById(reservedetailtodelete.getReserveid(), LockModeType.PESSIMISTIC_WRITE);
						
						if (resourceBlocking == null) {
							return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2431");
						}
						
						resourceBlockingList.add(reservedetailtodelete.getReserveid());
						
						// Obtener la lista de recursos de preagendamiento que van a eliminar detalles
						if (!predatingResourceList.contains(resourceBlocking.getBlockinggroupid())) {
							PreDatingResourceGroupW predatingresourcegroupW = predatingresourcegroupserver.getById(resourceBlocking.getBlockinggroupid(), LockModeType.PESSIMISTIC_WRITE);
							if (!predatingresourcegroupW.getVendorid().equals(vendorW.getId())) {
								return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2432");
							}		
							
							ResourceBlockingGroupW resourceblockinggroupW = resourceblockinggroupserver.getById(resourceBlocking.getBlockinggroupid());
							if (!resourceblockinggroupW.getLocationid().equals(locationW.getId())) {
								return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2433");
							}	
							
							predatingResourceList.add(resourceBlocking.getBlockinggroupid());
						}
					}
				}
			}

			// Se eliminan las reservas del proveedor (reserveDetailToDeleteList)
			for(ReserveDetailW reservedetailtodeleteW : reserveDetailToDeleteList){
				ReserveDetailId rdId = new ReserveDetailId(reservedetailtodeleteW.getReserveid(), reservedetailtodeleteW.getModuleid(), reservedetailtodeleteW.getDockid());
				reservedetailserver.deleteIdentifiable(rdId);
			}
			reservedetailserver.flush();
			
			// Validar si las reservas asociadas aún tienen detalles
			for (Long reserveid : resourceBlockingList) {
				ReserveDetailW[] rdArr = reservedetailserver.getByPropertyAsArray("reserve.id", reserveid);
				if (rdArr.length == 0) {
					// NO existen detalles para esa reserva, se elimina el resourceblocking
					resourceblockingserver.deleteElement(reserveid);
				}
			}
			
			// Validar si los preagendamientos utilizados aún tiene detalles
			for (Long blockinggroupid : predatingResourceList) {
				ReserveDetailW[] reservesDetails = reservedetailserver.getReserveDetailsOfBlockingGroup(blockinggroupid);
				if (reservesDetails == null || reservesDetails.length <= 0) {
					// NO existen detalles para ese preagendamiento, se elimina el predatingresourcegroup
					predatingresourcegroupserver.deleteElement(blockinggroupid);
				}
			}
			
			// Se crea la cita
			DatingW datingW = new DatingW();
			datingW.setDvrdeliveryid(dvrdeliveryW.getId());
			datingW.setNumber(dvrdeliveryW.getNumber());
			datingW.setLocationid(locationW.getId());
			datingW.setComment(initParamDTO.getComment());
			datingW.setConfirmationdate(now); 
			datingW.setVendorid(vendorW.getId());
			datingW.setWhen(initParamDTO.getRequesteddate().toLocalDate().atStartOfDay());
			datingW.setSentstatus(false);
			
			datingW = datingserver.addDating(datingW);

			// Detalles
			ReserveDetailW[] detailbusy = null;
			ReserveDetailW detailW = new ReserveDetailW();

			// Hora de llegada
			ModuleW arrive = null;
			
			for (int i = 0; i < initParamDTO.getReservedetail().length; i++) {
				ReserveDetailDTO detailDTO = initParamDTO.getReservedetail()[i];
				
				// Obtengo el minimo modulo para ver el horario de llegada
				ModuleW currmodule = moduleMap.get(detailDTO.getModuleid());
				if (arrive == null)
					arrive = currmodule;
				else if (currmodule.getStarts().isBefore(arrive.getStarts()))
					arrive = currmodule;
				
				// Vuelve  validar si el modulo está disponible
				detailbusy = reservedetailserver.getReserveDetailByDateLocationDockModule(initParamDTO.getRequesteddate(), locationW.getId(), detailDTO.getDockid(), detailDTO.getModuleid());
				
				if (detailbusy != null && detailbusy.length > 0) {
					getSessionContext().setRollbackOnly();
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2229");
				}
				detailDTO.setReserveid(datingW.getId());
				BeanExtenderFactory.copyProperties(detailDTO, detailW);
				
				// Agrega Fecha a detalle
				detailW.setWhen(initParamDTO.getRequesteddate());
				
				// Crea detalle
				reservedetailserver.addReserveDetail(detailW);				
			}
			
			
			//	El sistema actualiza el estado del despacho asociado a ‘Cita Asignada’.
			dvrdeliveryW.setCurrentstatetypedate(now);			
			dvrdeliveryW.setCurrentstatetypeid(asgSt.getId());			
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			
			DvrDeliveryStateW dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(asgSt.getId());
			dvrdeliverystateW.setWhen(now);
			dvrdeliverystateW.setUser(userdataDTO.getUsername());
			dvrdeliverystateW.setUsertype(userdataDTO.getUsertype());
			dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);
			
			
			
			// El sistema envía un correo al usuario que solicito la Cita, según estructura del documento Hites - Definición Correos ML B2B" y hoja "Confirmación Sol".
			String subject = "B2B Hites: Agendamiento de cita para entrega No. " + dvrdeliveryW.getNumber();
			String mailsender = LogisticConstants.getMailSender();
			String mailSession = LogisticConstants.getMAIL_SESSION();
			String[] mailreciever = new String[1];
			mailreciever[0] = datingrequestW.getEmail();

			
			if (mailreciever[0] != null && !mailreciever[0].equals("")) {

				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				
				String msgtext = "Estimado " + datingrequestW.getRequester() + " <br><br> " +
								 "El presente tiene como objetivo informarle que su solictud de cita asociada a la entrega No." + dvrdeliveryW.getNumber() + " fue asignada para: <br><br>" + // 
								 "<b>Dia:</b> " + dateFormatter.format(datingW.getWhen()) + "<br>" + //
								 "<b>Hora asignada: </b> " +  timeFormatter.format(arrive.getStarts())  + "<br><br>" + //
								 "Favor proceda a cargar el Packing List<br>" + //
								 "Comentario: " + datingW.getComment() + "<br><br>" + //
								 "Le rogamos seguir con el procedimiento definido y llegar con la debida anticipación.<br>" + // 
								 "Sin otro particular le saluda atentamente,<br>" + //				
								 "B2B Hites";
								 

				// Envía correo a tabla de correos pendientes
				schedulermailmanager.doAddMailToQueue(mailsender, mailreciever, null, null, subject, msgtext, mailSession, "ASIGNACION_CITA", dvrdeliveryW.getNumber().toString());
			}			

			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;

	}

	// Obtiene el detalle de las citas existentes para CD y día seleccionado
	public AssignedDatingResultDTO getAssignedDatingByDate(AssignedDatingInitParamDTO initParamDTO){
		AssignedDatingResultDTO resultDTO = new AssignedDatingResultDTO();
		
		try{
			// Mapas
			Map<Long, VendorW> vendorMap					= new HashMap<Long, VendorW>();
			Map<Long, DatingW> datingMap 					= new HashMap<Long, DatingW>();
			Map<Long, DvrDeliveryW> dvrdeliveryMap 			= new HashMap<Long, DvrDeliveryW>();
			Map<Long, DvrDeliveryStateTypeW> dstMap 		= new HashMap<Long, DvrDeliveryStateTypeW>(); 
			Map<Long, ResourceBlockingW> rBlockingMap 		= new HashMap<Long, ResourceBlockingW>();
			Map<Long, ResourceBlockingGroupW> rbGroupMap 	= new HashMap<Long, ResourceBlockingGroupW>();
			Map<Long, PreDatingResourceGroupW> pdGroupMap 	= new HashMap<Long, PreDatingResourceGroupW>();
			Map<Long, VendorW> vPreDatingResourceMap 		= new HashMap<Long, VendorW>();
			
			
			// Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if (locationArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
			}
			LocationW locationW = locationArr[0];
			
			// Proveedores con cita por fecha y local
			LocalDateTime reserveDate = initParamDTO.getDate().toLocalDate().atStartOfDay();
			VendorW[] vendorArray = vendorserver.getVendorsByLocationWithDating(locationW.getId(), reserveDate);
			
			// Carga mapa de proveedores
			for (VendorW vendorW : vendorArray) {
				vendorMap.put(vendorW.getId(), vendorW);
			}
			
			// Obtiene detalles de reservas por fecha y CD
			LocalDateTime since = initParamDTO.getDate().toLocalDate().atStartOfDay();
			LocalDateTime until = initParamDTO.getDate().toLocalDate().atTime(LocalTime.MAX.truncatedTo(ChronoUnit.SECONDS));
			
			ReserveDetailW[] rdArray = reservedetailserver.getReserveDetailsByDateAnLocation(since, until, locationW.getId());

			
			// Tipos de estado de entrega
			DvrDeliveryStateTypeW[] dstArray = dvrdeliverystatetypeserver.getAllAsArray();
			for (DvrDeliveryStateTypeW dvrdeliveryStateTypeW : dstArray) {
				dstMap.put(dvrdeliveryStateTypeW.getId(), dvrdeliveryStateTypeW);
			}

			// Obtiene las citas por dia y CD
			DatingW[] datingArr = datingserver.getDatingByDateAndLocation(since, until, locationW.getId());
			
			// Carga mapa de citas y despachos
			for(DatingW datingW : datingArr){
				datingMap.put(datingW.getId(), datingW);
				
				// Obtiene DvrDelivery relacionado
				DvrDeliveryW dvrdeliveryW = dvrdeliveryserver.getById(datingW.getDvrdeliveryid());
				dvrdeliveryMap.put(dvrdeliveryW.getId(), dvrdeliveryW);
			}
			
			
			// Obtiene bloqueos por fecha y CD
			ResourceBlockingW[] rbArr = resourceblockingserver.getResourceBlockingsByDateAndLocation(since, until, locationW.getId());
			for (ResourceBlockingW rbW : rbArr) {
				rBlockingMap.put(rbW.getId(), rbW);
			}
			
			// Grupos de bloqueo por fecha y CD
			ResourceBlockingGroupW[] rbgArr = resourceblockinggroupserver.getResourceBlockingGroupsByDateAndLocation(since, until, locationW.getId());
			for(ResourceBlockingGroupW rbgW : rbgArr){
				rbGroupMap.put(rbgW.getId(), rbgW);
			}
			
			// Reservas de proveedor por fecha y CD
			PreDatingResourceGroupW[] pdrArr = predatingresourcegroupserver.getPreDatingResourceGroupsByDateAndLocation(since, until, locationW.getId());
			for(PreDatingResourceGroupW pdrW :pdrArr){
				pdGroupMap.put(pdrW.getId(), pdrW);
			}
			
			 ////////////////////////////////////////////////////////////////////////////
			// Datos para la agenda
			
			AssignedDatingDataDTO adData = null;
			AssignedResourceBlockingDataDTO abkData = null;
			AssignedPreDatingResourceGroupDataDTO pdrData = null;
			List<AssignedDatingDataDTO> adDataList = new ArrayList<AssignedDatingDataDTO>();
			List<AssignedResourceBlockingDataDTO> abkDataList = new ArrayList<AssignedResourceBlockingDataDTO>();
			List<AssignedPreDatingResourceGroupDataDTO> pdrList = new ArrayList<AssignedPreDatingResourceGroupDataDTO>();
			
			DatingW datingW = null;
			DvrDeliveryW dvrdeliveryW = null;
			VendorW vendorW = null;
			PreDatingResourceGroupW predatingresourcegroupW = null;
			ResourceBlockingGroupW resourceblockinggroupW = null;
			
			// Si existen destalles de reservas
			if (rdArray.length > 0) {
				
				for (ReserveDetailW reservedetail : rdArray) {
					
					// Caso 1: Si la reserva es una cita
					if(datingMap.containsKey(reservedetail.getReserveid())){
						
						datingW = datingMap.get(reservedetail.getReserveid());
						dvrdeliveryW = dvrdeliveryMap.get(datingW.getDvrdeliveryid()); 
						vendorW = vendorMap.get(datingW.getVendorid());
						
						adData = new AssignedDatingDataDTO();
						adData.setDockid(reservedetail.getDockid());
						adData.setModuleid(reservedetail.getModuleid());
						adData.setReserveid(reservedetail.getReserveid());
						adData.setVendorid(vendorW.getId());
						adData.setVendorname(vendorW.getName());
						adData.setVendorcode(vendorW.getCode());
						adData.setCurrentdvrdeliverystatecode(dstMap.get(dvrdeliveryW.getCurrentstatetypeid()).getCode());
						adData.setCurrentdvrdeliverystate(dstMap.get(dvrdeliveryW.getCurrentstatetypeid()).getDescription());
						adData.setDvrdeliveryid(dvrdeliveryW.getId());
						adData.setDvrdeliverynumber(dvrdeliveryW.getNumber());
						adDataList.add(adData);
						
					}
					
					
					// Caso 2: Si la reserva es un bloqueo o una reserva de proveedor
					else if (rBlockingMap.containsKey(reservedetail.getReserveid())) {
						ResourceBlockingW rBlockingW = rBlockingMap.get(reservedetail.getReserveid());
					
						
						// Caso 2.1: Si la reserva es un Pre-Agendamiento
						if (pdGroupMap.containsKey(rBlockingW.getBlockinggroupid())) {
							predatingresourcegroupW = pdGroupMap.get(rBlockingW.getBlockinggroupid());
							
							// Obtiene el Proveedor
							if (vPreDatingResourceMap.containsKey(predatingresourcegroupW.getVendorid())) {
								vendorW = vPreDatingResourceMap.get(predatingresourcegroupW.getVendorid());
							} else {
								vendorW = vendorserver.getById(predatingresourcegroupW.getVendorid());
								vPreDatingResourceMap.put(vendorW.getId(), vendorW);
							}

							pdrData = new AssignedPreDatingResourceGroupDataDTO();
							pdrData.setDockid(reservedetail.getDockid());
							pdrData.setModuleid(reservedetail.getModuleid());
							pdrData.setReserveid(rBlockingW.getId());
							pdrData.setBlockingroupid(predatingresourcegroupW.getId());
							pdrData.setWho(predatingresourcegroupW.getWho());
							pdrData.setCreationdate(predatingresourcegroupW.getCreated());
							pdrData.setVendorid(predatingresourcegroupW.getVendorid());
							pdrData.setVendorcode(vendorW.getCode());
							pdrData.setVendorname(vendorW.getName());
							
							pdrList.add(pdrData);
							
						}
						
						// Caso 2.2: Si la reserva es un bloqueo de agenda 
						else {
							resourceblockinggroupW = rbGroupMap.get(rBlockingW.getBlockinggroupid());
							
							abkData = new AssignedResourceBlockingDataDTO();
							abkData.setDockid(reservedetail.getDockid());
							abkData.setModuleid(reservedetail.getModuleid());
							abkData.setReserveid(reservedetail.getReserveid());
							abkData.setReason(resourceblockinggroupW.getReason());
							abkData.setBlockingroupid(resourceblockinggroupW.getId());
							abkData.setComment(resourceblockinggroupW.getComment());
							abkData.setCreated(resourceblockinggroupW.getCreated());
							abkData.setWho(resourceblockinggroupW.getWho());

							abkDataList.add(abkData);
						}
					}					
				}
				
				resultDTO.setAssignedDatingData((AssignedDatingDataDTO[]) adDataList.toArray(new AssignedDatingDataDTO[adDataList.size()]));
				resultDTO.setAssignedBlockadeData((AssignedResourceBlockingDataDTO[]) abkDataList.toArray(new AssignedResourceBlockingDataDTO[abkDataList.size()]));
				resultDTO.setAssignedPreDatingResourceGroupData((AssignedPreDatingResourceGroupDataDTO[]) pdrList.toArray(new AssignedPreDatingResourceGroupDataDTO[pdrList.size()]));
				
			}
			
			// No hay reservas
			else{
				resultDTO.setAssignedBlockadeData(new AssignedResourceBlockingDataDTO[0]);
				resultDTO.setAssignedDatingData(new AssignedDatingDataDTO[0]);
				resultDTO.setAssignedPreDatingResourceGroupData(new AssignedPreDatingResourceGroupDataDTO[0]);
				
			}
			
			// Totalizadores por anden
			AssignedDatingTotalizerByDockDTO[] assignedDatingTotalizerArr = dvrorderdeliverydetailserver.getAssignedDatingTotalizerDockByDateAndLocation(since, until, locationW.getId());
			resultDTO.setAssignedDatingTotalizerByDock(assignedDatingTotalizerArr);
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		
		return resultDTO;	

	}


	
	public ModulesArrayResultDTO getModulesByLocation(String locationcode) {
		ModulesArrayResultDTO resultDTO = new ModulesArrayResultDTO();
		try {
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", locationcode);
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			ModuleDataDTO[] moduleData = moduleserver.getModulesActiveByLocation(locationW.getId());
			resultDTO.setModules(moduleData);

		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	
	
	public DocksArrayResultDTO getDocksOfLocation(String locationcode)  {
		DocksArrayResultDTO resultDTO = new DocksArrayResultDTO();
		try {
			OrderCriteriaDTO[] criterialist = new OrderCriteriaDTO[1];
			OrderCriteriaDTO criteria = new OrderCriteriaDTO("visualorder", true);
			criterialist[0] = criteria;
			
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("location.code", "locationcode", locationcode);
			properties[1] = new PropertyInfoDTO("active", "active", true);
			
			DockW[] dockArr = dockserver.getByPropertiesAsArrayOrdered(properties, criterialist);
			DockDTO[] docksDTO = new DockDTO[dockArr.length];
			BeanExtenderFactory.copyProperties(dockArr, docksDTO, DockDTO.class);

			resultDTO.setDocks(docksDTO);
			return resultDTO;

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4001");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4001");
		}
	}
	
	public ExcelDatingReportResultDTO getExcelDatingReport(ExcelDatingReportInitParamDTO initParamDTO) {
		ExcelDatingReportResultDTO resultDTO = new ExcelDatingReportResultDTO();
		ExcelDatingReportDataDTO[] exceldatingreportdata;
		
		try {
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			// Fecha
			LocalDateTime since = initParamDTO.getSince().toLocalDate().atStartOfDay();
			LocalDateTime until = initParamDTO.getUntil().toLocalDate().atTime(LocalTime.MAX);
			
			//El sistema valida que la fecha "desde" sea igual o anterior a la fecha "hasta"
			if(since.isAfter(until)) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2244");
			}
			
			// El sistema valida que el rango elegido no supere los días definidos en el parámentro
			ParameterW[] daystoExcelDatingParamArr = parameterserver.getByPropertyAsArray("code", "RANGE_EXCEL_DATING");
			if(daystoExcelDatingParamArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4205");
			}
			
			Period periodOfDays = Period.between(initParamDTO.getSince().toLocalDate(), initParamDTO.getUntil().toLocalDate());
			if(periodOfDays.getDays() > Integer.valueOf(daystoExcelDatingParamArr[0].getValue())) {
				String error = "El rango de fechas no puede superar los " + Integer.valueOf(daystoExcelDatingParamArr[0].getValue()) + " días”";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", error, false);
			}
			
			// Obtiene datos
			exceldatingreportdata = datingserver.getExcelDatingReport(locationW.getId(), since, until);
			resultDTO.setExceldatingreportdata(exceldatingreportdata);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}

	
	public BaseResultDTO doMarkDatingAsNotAttend(DvrDeliveryInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		LocalDateTime now = LocalDateTime.now();
		
		try {
			
			// Mapa de documentos
			Map<Long, DocumentW> documentMap = new HashMap<Long, DocumentW>();
			
			// Lista de bultos
			List<String> bulkCodeList = new ArrayList<String>();  
			// Lista de Documentos
			List<String> documentCodeList = new ArrayList<String>();
			
			// Estados Despacho
			DvrDeliveryStateTypeW notAttSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "NO_ASISTE");
			DvrDeliveryStateTypeW agnDatSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			DvrDeliveryStateTypeW confDatSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_CONFIRMADA");
			
			// Obtiene lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];

			// Proveedor asociado a delivery
			VendorW vendorW = vendorserver.getById(dvrdeliveryW.getVendorid());
			
			// El sistema valida que la cita seleccionada se encuentre en estado 'Cita Confirmada' o Cita Agendada
			if(! dvrdeliveryW.getCurrentstatetypeid().equals(confDatSt.getId()) && ! dvrdeliveryW.getCurrentstatetypeid().equals(agnDatSt.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4006");
			}

			// Si el estado de Cita Confirmada, envía interfaz a WS informado la eliminación
			if(dvrdeliveryW.getCurrentstatetypeid().equals(confDatSt.getId())) {
				
				
				
				
			}
			
			
			
			// El sistema elimina los bultos, detalles de bulto y los documentos asociados a la cita.
			BulkW[] bulkArr = bulkserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			if(bulkArr.length > 0) {
				for(BulkW bulkW : bulkArr) {
					//Almacena código para Log
					bulkCodeList.add(bulkW.getLpncode());
					
					BulkW bulkToDel = bulkserver.getById(bulkW.getId(), LockModeType.PESSIMISTIC_WRITE);
					
					// Guarda documento asociado
					if(! documentMap.containsKey(bulkToDel.getDocumentid())) {
						DocumentW documentW = documentserver.getById(bulkToDel.getDocumentid());
						documentMap.put(documentW.getId(), documentW);
					}
					
					// Borra detalles del bulto
					PropertyInfoDTO propbd = new PropertyInfoDTO("bulk.id", "bulkid", bulkToDel.getId());
					bulkdetailserver.deleteByProperties(propbd);
					
					// Borra bulto
					bulkserver.deleteElement(bulkToDel.getId());
				}
				
				// Borra Documentos
				for (Map.Entry<Long, DocumentW> entry : documentMap.entrySet()) {
					// Obtiene Documento
					DocumentW docTodel = documentserver.getById(entry.getValue().getId(), LockModeType.PESSIMISTIC_WRITE);
					
					// Guarda cádigo para log
					documentCodeList.add(docTodel.getNumber());
					
					// Borra estado del documento
					documentstateserver.deleteByProperty("document.id", docTodel.getId());
					
					// Borra documento
					documentserver.deleteElement(docTodel.getId());
				}
			}
			
			// El sistema actualiza el estado del despacho a 'No asiste'.
			dvrdeliveryW.setCurrentstatetypeid(notAttSt.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			
			// Estado
			DvrDeliveryStateW stateW = new DvrDeliveryStateW();				
			stateW.setDvrdeliveryid(dvrdeliveryW.getId());
			stateW.setDvrdeliverystatetypeid(notAttSt.getId());
			stateW.setWhen(now);
			stateW.setUser(userdataDTO.getUsername());
			stateW.setUsertype(userdataDTO.getUsertype());
			stateW = dvrdeliverystateserver.addDvrDeliveryState(stateW);
			
			// El sistema marca como 'closed' el despacho y sus order-deliveries asociadas.
			DvrOrderDeliveryW[] dvrorderdeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			for(DvrOrderDeliveryW dvrorderdeliveryW : dvrorderdeliveryArr) {
				dvrorderdeliveryW.setClosed(true);
				dvrorderdeliveryserver.updateDvrOrderDelivery(dvrorderdeliveryW);
			}
			
			// Calculo de totales
			Long[] orderids = Arrays.stream(dvrorderdeliveryArr).map(oc -> oc.getDvrorderid()).toArray(Long[]::new);
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(orderids);			
			
			// LOGS
			// Eliminación de bultos
			for(String bulkcode: bulkCodeList) {
				logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Elimina Bulto" + "," +  "doMarkDatingAsNotAttend" + "," + "Eliminación Bulto Nro " + bulkcode + " desde entrega Nro " + dvrdeliveryW.getNumber());
			}
			
			// Eliminación de documentos
			for(String documentnumber : documentCodeList) {
				logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Elimina Documento" + "," +  "doMarkDatingAsNotAttend" + "," + "Eliminación Documento Nro " + documentnumber + " desde entrega Nro " + dvrdeliveryW.getNumber());
			}
			
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

	}
	
	public BaseResultDTO doDeleteDating(DeleteDatingInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		
		BaseResultDTO resultDTO = new BaseResultDTO(); 
		LocalDateTime now = LocalDateTime.now(); 
		
		try{
			// Estados de la cita
			DvrDeliveryStateTypeW reqSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DvrDeliveryStateTypeW asgSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			
			// Cita
			DatingW[] datingArr = datingserver.getByPropertyAsArray("id", initParamDTO.getReserveid());
			if(datingArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2230");
			}

			DatingW datingW = datingserver.getById(datingArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Obtiene lote
			DvrDeliveryW dvrdeliveryW = dvrdeliveryserver.getById(datingW.getDvrdeliveryid());
			
			// El sistema valida que la cita se encuentre en estado 'Cita asignada'
			if(! dvrdeliveryW.getCurrentstatetypeid().equals(asgSt.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4007");
			}
			
			// Proveedor
			VendorW vendorW = vendorserver.getById(dvrdeliveryW.getVendorid());
			
			// Solicitud de cita asociada
			DatingRequestW[] datingrequestArr = datingrequestserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			if(datingrequestArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2220");
			}
			DatingRequestW datingrequestW = datingrequestArr[0]; 

			// El sistema elimina la cita con su detalle de andenes y módulos.				
			// Elimina reservedetail
			ReserveDetailW[] reservedetailArr = reservedetailserver.getByPropertyAsArray("reserve.id", datingW.getId()); 
			for(ReserveDetailW reservedetailW : reservedetailArr ) {
				ReserveDetailId rdpk = new ReserveDetailId(reservedetailW.getReserveid(), 
															reservedetailW.getModuleid(), 
															reservedetailW.getDockid());
				
				reservedetailserver.getById(rdpk, LockModeType.PESSIMISTIC_WRITE);
				reservedetailserver.deleteIdentifiable(rdpk);
			}
			
			 // Elimina Cita
			datingserver.deleteElement(datingW.getId());
			
			// El sistema actualiza el estado de la entrega a “Cita Solicitada”.
			dvrdeliveryW.setCurrentstatetypeid(reqSt.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			
			// Estado
			DvrDeliveryStateW stateW = new DvrDeliveryStateW();				
			stateW.setDvrdeliveryid(dvrdeliveryW.getId());
			stateW.setDvrdeliverystatetypeid(reqSt.getId());
			stateW.setWhen(now);
			stateW.setUser(userdataDTO.getUsername());
			stateW.setUsertype(userdataDTO.getUsertype());
			stateW = dvrdeliverystateserver.addDvrDeliveryState(stateW);

			
			// El sistema construye y envía un correo al usuario que generó la solicitud de cita, indicando de la modificación
			String subject = "B2B Hites: Elimina Cita  " + dvrdeliveryW.getNumber();
			String mailsender = LogisticConstants.getMailSender();
			String mailSession = LogisticConstants.getMAIL_SESSION();
			String[] mailreciever = new String[1];
			mailreciever[0] = datingrequestW.getEmail();
			
			if (mailreciever[0] != null && !mailreciever[0].equals("")) {					
				String msgtext = "Estimado(a) Usuario(a): <br><br> " + //
								"La cita para la entrega N° " + dvrdeliveryW.getNumber()  + " ha sido eliminada con el siguiente comentario:<br><br> " + //									
								initParamDTO.getComment() + "<br><br>" + //	
									
								"Atte.<br>" + //										
								"B2B Hites<br><br>" + // 										
								"<b>Favor no responder este correo dado que es generado de manera automática por el sistema B2B.</b>";
				
				// Envía correo a tabla de correos pendientes
				schedulermailmanager.doAddMailToQueue(mailsender, mailreciever, null, null, subject, msgtext, mailSession, "ELIMINACION_CITA", dvrdeliveryW.getNumber().toString());
			}		
			
			// Log de reserva eliminada 
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Eliminar Reserva" + "," +  "doDeleteReserve" + "," + "Eliminación Cita asociada a Entrega Nro " + dvrdeliveryW.getNumber());

			
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}
	
	@TransactionTimeout(900) // timeout = 15 min
	public ReceptionCloseResultDTO doCloseReception(DvrDeliveryInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		ReceptionCloseResultDTO resultDTO = new ReceptionCloseResultDTO();

		Long dvrDeliveryNumber = 0L;
		String vendorCode = "";
		String vendorName = "";
		try {
			// Obtiene la entrega
			DvrDeliveryW dvrDelivery = dvrdeliveryserver.getById(initParamDTO.getDvrdeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			if (dvrDelivery == null) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			dvrDeliveryNumber = dvrDelivery.getNumber();
			
			// Obtener el proveedor
			VendorW vendorW = vendorserver.getById(dvrDelivery.getVendorid());
			vendorCode = vendorW.getCode();
			vendorName = vendorW.getName();
			
			// Obtener todos los documentos asociados a la cita
			DocumentW[] documents = documentserver.getDocumentRelatedToDelivery(dvrDelivery.getId());
			
			// Obtener los bultos asociados
			List<BulkW> bulks = bulkserver.getByProperty("dvrdelivery.id", dvrDelivery.getId());
			
			// Mapa de bultos x documentos
			Map<Long, List<Long>> bulkIdMap = bulks.stream()
												   .collect(Collectors.groupingBy(bu -> bu.getDocumentid(), Collectors.mapping(bu -> bu.getId(), Collectors.toList())));
			
			LocalDateTime now = LocalDateTime.now();
			
			resultDTO.setAsnsuccess(true);
			
			List<WSSentMessageResponseDTO> documentMessageList = new ArrayList<WSSentMessageResponseDTO>();
			WSSentMessageResponseDTO documentMessage = null;
			
			// Valida si la cita está en estado 'En Recepción'
			DvrDeliveryStateTypeW dstReceiving = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RECEPCION");
			
			HashMap<Long, List<ASNDataToMessageDTO>> headerMap = null;
			HashMap<Long, List<ASNDetailDataToMessageDTO>> detailMap = null;
			if (documents != null && documents.length > 0) {
				// Obtener mapas con los datos para los mensajes de documentos pendientes
				Long[] documentIds = Arrays.stream(documents).map(doc -> doc.getId()).toArray(Long[]::new);
				ASNDataToMessageDTO[] asnDataArr = documentserver.getDeliveryASNHeaderData(dvrDelivery.getId(), documentIds);
				headerMap = (HashMap<Long, List<ASNDataToMessageDTO>>) Arrays
																			.stream(asnDataArr)
																			.collect(Collectors.groupingBy(ASNDataToMessageDTO::getDocumentid));
				
				ASNDetailDataToMessageDTO[] asnDetail = documentserver.getDeliveryASNDetailData(dvrDelivery.getId(), documentIds);
				detailMap = (HashMap<Long, List<ASNDetailDataToMessageDTO>>) Arrays
																				.stream(asnDetail)
																				.collect(Collectors.groupingBy(ASNDetailDataToMessageDTO::getDocumentid));
			}
						
			// Recepción Parcial
			if (dvrDelivery.getCurrentstatetypeid().equals(dstReceiving.getId())) {
				WSSentMessageResultDTO asnMessageData = null;
				for (DocumentW document : documents) {
					// Valida si el estado del documento es 'Abierto'
					if (!document.getClosed()) {
						// Valida si el estado del documento es 'Enviado'
						if (document.getStatus()) {
							// El sistema genera por cada factura listada la interfaz de ASN asociada
							asnMessageData = asnmessagemanager.doSendDocumentASNDeleteMessage(document, headerMap.get(document.getId()), detailMap.get(document.getId()), bulkIdMap.get(document.getId()));
							
							// Agregar respuesta al listado
							documentMessage = new WSSentMessageResponseDTO();
							BeanExtenderFactory.copyProperties(asnMessageData, documentMessage);
							documentMessageList.add(documentMessage);
							
							resultDTO.setAsnsuccess(resultDTO.getAsnsuccess() && !asnMessageData.getSenterror());
						}
						else {
							// Elimina el documento, los bultos y sus detalles
							for (Long bulkId : bulkIdMap.get(document.getId())) {
								bulkdetailserver.deleteByProperty("bulk.id", bulkId);
								bulkserver.deleteByProperty("id", bulkId);
							}
							documentserver.deleteByProperty("id", document.getId());
						}						
					}				
				}
				
				resultDTO.setDocumentmessages((WSSentMessageResponseDTO[]) documentMessageList.toArray(new WSSentMessageResponseDTO[documentMessageList.size()]));
				
				if (resultDTO.getAsnsuccess()) {
					DvrOrderDeliveryW[] dvrOrderDeliveries = dvrorderdeliveryserver.getByPropertyAsArray("id.dvrdeliveryid", dvrDelivery.getId());
					for (DvrOrderDeliveryW dvrOrderDelivery : dvrOrderDeliveries) {
						// Cierra todos los despachos asociados a la entrega (algunos pueden haber sido cerrados anteriormente)
						dvrOrderDelivery.setClosed(true);
						
						// Almacena la fecha y hora de recepción
						dvrOrderDelivery.setReceptiondate(now);
						
						dvrorderdeliveryserver.updateDvrOrderDelivery(dvrOrderDelivery);
					}
					
					// Actualiza el estado de la entrega a 'Cita Recepcionada'
					DvrDeliveryStateTypeW dstReceived = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
					dvrDelivery.setCurrentstatetypeid(dstReceived.getId());
					dvrDelivery.setCurrentstatetypedate(now);
					dvrDelivery = dvrdeliveryserver.updateDvrDelivery(dvrDelivery);
					
					// Agrega el nuevo estado al historial de estados del despacho
					DvrDeliveryStateW dvrDeliveryStateW = new DvrDeliveryStateW();
					dvrDeliveryStateW.setWhen(now);
					dvrDeliveryStateW.setUser(userdataDTO.getUsername());
					dvrDeliveryStateW.setUsertype(userdataDTO.getUsertype());
					dvrDeliveryStateW.setComment("");
					dvrDeliveryStateW.setDvrdeliveryid(dvrDelivery.getId());
					dvrDeliveryStateW.setDvrdeliverystatetypeid(dstReceived.getId());					
					dvrdeliverystateserver.addDvrDeliveryState(dvrDeliveryStateW);
					
					// Actualiza las unidades en cada detalle del despacho
					dvrorderdeliverydetailserver.doUpdateDvrOrderDeliveryDetails(dvrDelivery.getId());
					dvrorderdeliverydetailserver.flush();
					
					// Recalcula las unidades y montos asociados a las órdenes incluidas en el despacho, liberando las cantidades
					Long[] dvrOrderIds = Arrays.stream(dvrOrderDeliveries).map(od -> od.getDvrorderid()).toArray(Long[]::new);
					buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrOrderIds);	
					dvrorderserver.flush();
					
					// Obtiene el tipo de estado de orden 'Recepcionada'
					DvrOrderStateTypeW ostReceived = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
					
					DvrOrderStateW dvrOrderState;
					DvrOrderW dvrOrder;
					for (Long dvrOrderId : dvrOrderIds) {
						dvrOrder = dvrorderserver.getById(dvrOrderId, LockModeType.PESSIMISTIC_WRITE);
						
						// Valida que la cantidad de unidades recepcionadas sea igual a lo solicitado
						if (dvrOrder.getReceivedunits().equals(dvrOrder.getTotalunits())) {
							// Actualiza el estado de la orden a 'Recepcionada'
							dvrOrder.setCurrentstatetypeid(ostReceived.getId());
							dvrOrder.setCurrentstatetypedate(now);
							dvrOrder = dvrorderserver.updateDvrOrder(dvrOrder);
							
							dvrOrderState = new DvrOrderStateW();
							dvrOrderState.setDvrorderid(dvrOrder.getId());
							dvrOrderState.setDvrorderstatetypeid(ostReceived.getId());
							dvrOrderState.setWhen(now);
							dvrOrderState.setWho(userdataDTO.getUsername());
							dvrorderstateserver.addDvrOrderState(dvrOrderState);
						}
					}
					
					// Log de Cierre de Recepción Parcial
					logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Cierre de Recepción" + "," +  "doCloseReception" + "," + "Cierre de Recepción de Entrega Nro " + dvrDeliveryNumber);
				}
				else {
					// Log de Cierre parcial de Recepción Parcial
					logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Cierre de Recepción" + "," +  "doCloseReception" + "," + "Cierre parcial de Recepción de Entrega Nro " + dvrDeliveryNumber);
				}
			}
			// Recepción General
			else {
				// Valida que la cita seleccionada se encuentre en estado 'Cita Confirmada' o 'Cita Agendada'
				DvrDeliveryStateTypeW dstConfirmed = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_CONFIRMADA");
				DvrDeliveryStateTypeW dstScheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
				if (!dvrDelivery.getCurrentstatetypeid().equals(dstConfirmed.getId()) && !dvrDelivery.getCurrentstatetypeid().equals(dstScheduled.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4006");
				}
				
				WSSentMessageResultDTO asnMessageData = null;
				for (DocumentW document : documents) {
					// Si el estado del documento es 'Enviado'
					if (document.getStatus()) {
						// El sistema genera por cada factura listada la interfaz de ASN asociada
						asnMessageData = asnmessagemanager.doSendDocumentASNDeleteMessage(document, headerMap.get(document.getId()), detailMap.get(document.getId()), bulkIdMap.get(document.getId()));
						
						// Agregar respuesta al listado
						documentMessage = new WSSentMessageResponseDTO();
						BeanExtenderFactory.copyProperties(asnMessageData, documentMessage);
						documentMessageList.add(documentMessage);
						
						resultDTO.setAsnsuccess(resultDTO.getAsnsuccess() && !asnMessageData.getSenterror());
					}
					else {
						// Elimina el documento, los bultos y sus detalles
						for (Long bulkId : bulkIdMap.get(document.getId())) {
							bulkdetailserver.deleteByProperty("bulk.id", bulkId);
							bulkserver.deleteByProperty("id", bulkId);
						}
						documentserver.deleteByProperty("id", document.getId());
					}
				}
				
				resultDTO.setDocumentmessages((WSSentMessageResponseDTO[]) documentMessageList.toArray(new WSSentMessageResponseDTO[documentMessageList.size()]));
				
				if (resultDTO.getAsnsuccess()) {
					// Cierra todos los despachos asociados a la entrega
					DvrOrderDeliveryW[] dvrOrderDeliveries = dvrorderdeliveryserver.getByPropertyAsArray("id.dvrdeliveryid", dvrDelivery.getId());
					for (DvrOrderDeliveryW dvrOrderDelivery : dvrOrderDeliveries) {
						dvrOrderDelivery.setClosed(true);
						dvrorderdeliveryserver.updateDvrOrderDelivery(dvrOrderDelivery);
					}

					// Actualiza el estado de la entrega a 'Cita Rechazada'
					DvrDeliveryStateTypeW dstRejected = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
					dvrDelivery.setCurrentstatetypeid(dstRejected.getId());
					dvrDelivery.setCurrentstatetypedate(now);
					dvrDelivery = dvrdeliveryserver.updateDvrDelivery(dvrDelivery);
					
					// Agrega el nuevo estado al historial de estados del despacho
					DvrDeliveryStateW dvrDeliveryStateW = new DvrDeliveryStateW();
					dvrDeliveryStateW.setWhen(now);
					dvrDeliveryStateW.setUser(userdataDTO.getUsername());
					dvrDeliveryStateW.setUsertype(userdataDTO.getUsertype());
					dvrDeliveryStateW.setComment("");
					dvrDeliveryStateW.setDvrdeliveryid(dvrDelivery.getId());
					dvrDeliveryStateW.setDvrdeliverystatetypeid(dstRejected.getId());					
					dvrdeliverystateserver.addDvrDeliveryState(dvrDeliveryStateW);
					
					// Actualiza las unidades en cada detalle del despacho
					dvrorderdeliverydetailserver.doUpdateDvrOrderDeliveryDetails(dvrDelivery.getId());
					dvrorderdeliverydetailserver.flush();
					
					// Recalcula las unidades y montos asociados a las órdenes incluidas en el despacho, liberando las cantidades
					Long[] dvrOrderIds = Arrays.stream(dvrOrderDeliveries).map(od -> od.getDvrorderid()).toArray(Long[]::new);
					buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrOrderIds);	
					
					// Log de Cierre de Recepción General
					logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Cierre de Recepción" + "," +  "doCloseReception" + "," + "Cierre de Recepción de Entrega Nro " + dvrDeliveryNumber);
				}
				else {
					// Log de Cierre parcial de Recepción General
					logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Cierre de Recepción" + "," +  "doCloseReception" + "," + "Cierre parcial de Recepción de Entrega Nro " + dvrDeliveryNumber);
				}		
			}
	
		}  catch (OperationFailedException | NotFoundException | AccessDeniedException e) {
			// Log de error en Cierre de Recepción
			logger.error(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Cierre de Recepción" + "," +  "doCloseReception" + "," + "Error en Cierre de Recepción de Entrega Nro " + dvrDeliveryNumber);
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BaseResultDTO doDeleteReserveDetailsOfResourceBlockingGroup(DeleteResourceBlockingGroupDetailInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtener grupo
			ResourceBlockingGroupW[] resourceblockinggroupArr = resourceblockinggroupserver.getByPropertyAsArray("id", initParamDTO.getResourceblockinggroupid());
			if(resourceblockinggroupArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2430");
			}
			
			List<Long> rbListToDelete = new ArrayList<Long>();
			ReserveDetailId rdKey = null;
			ResourceBlockingW resourceBlocking = null;
			Long resourceBlockingId = null;
			for (ReserveDetailDTO reservedetail : initParamDTO.getReservedetails()) {
				// Validar que el bloqueo exista y esté asociado al grupo de bloqueo seleccionado
				resourceBlockingId = reservedetail.getReserveid();
				if (!rbListToDelete.contains(resourceBlockingId)) {
					resourceBlocking = resourceblockingserver.getById(resourceBlockingId);
					if (resourceBlocking == null || !resourceBlocking.getBlockinggroupid().equals(initParamDTO.getResourceblockinggroupid())) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2419");
					}
					rbListToDelete.add(resourceBlockingId);
				}
			}
			
			// Todos los detalles informados existen y están asociados al grupo de bloqueo
			for (ReserveDetailDTO reservedetail : initParamDTO.getReservedetails()) {
				rdKey = new ReserveDetailId(reservedetail.getReserveid(), reservedetail.getModuleid(), reservedetail.getDockid());
				reservedetailserver.deleteIdentifiable(rdKey);		
			}			
			reservedetailserver.flush();

			// Validar si las reservas asociadas tienen detalles
			for(Long rbid : rbListToDelete) {
				ReserveDetailW[] rdArr = reservedetailserver.getByPropertyAsArray("reserve.id", rbid);
				if(rdArr.length == 0) {
					// NO existen detalles para esa reserva, se elimina el resourceblocking
					ResourceBlockingW rbToDel = resourceblockingserver.getById(rbid, LockModeType.PESSIMISTIC_WRITE);
					resourceblockingserver.deleteElement(rbToDel.getId());
				}
			}
			
			// Validar que el preagendamiento actualizado aún tiene detalles
			ReserveDetailW[] reservesDetails = reservedetailserver.getReserveDetailsOfBlockingGroup(initParamDTO.getResourceblockinggroupid());
			if (reservesDetails == null || reservesDetails.length <= 0) {
				ResourceBlockingGroupW resourceblockinggroupW = resourceblockinggroupserver.getById(resourceblockinggroupArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
				resourceblockinggroupserver.deleteElement(resourceblockinggroupW.getId());
			}

		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} 
		
		return resultDTO;
	}

	public BaseResultDTO doDeleteReserveDetailsOfPreDatingResourceGroup(DeletePreDatingResourceGroupDetailInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {

		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtener el local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if (locationArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
			}
			LocationW locationW = locationArr[0];
			
			List<Long> resourceBlockingList = new ArrayList<Long>();
			List<Long> predatingResourceList = new ArrayList<Long>();
			ResourceBlockingW resourceBlocking;
			ReserveDetailPK detailPK;
			for (ReserveDetailDTO detail : initParamDTO.getReservedetails()) {
				detailPK = new ReserveDetailPK();
				DateConverterFactory2.copyProperties(detail, detailPK);
				
				// Obtener la lista de bloqueos de recursos que van a eliminar detalles
				if (!resourceBlockingList.contains(detailPK.getReserveid())) {
					resourceBlocking = resourceblockingserver.getById(detailPK.getReserveid(), LockModeType.PESSIMISTIC_WRITE);
					
					if (resourceBlocking == null) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2431");
					}
					
					resourceBlockingList.add(detailPK.getReserveid());
					
					// Obtener la lista de recursos de preagendamiento que van a eliminar detalles
					if (!predatingResourceList.contains(resourceBlocking.getBlockinggroupid())) {
						PreDatingResourceGroupW predatingresourcegroupW = predatingresourcegroupserver.getById(resourceBlocking.getBlockinggroupid(), LockModeType.PESSIMISTIC_WRITE);
						if (!predatingresourcegroupW.getVendorid().equals(vendorW.getId())) {
							return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2432");
						}		
						
						ResourceBlockingGroupW resourceblockinggroupW = resourceblockinggroupserver.getById(resourceBlocking.getBlockinggroupid());
						if (!resourceblockinggroupW.getLocationid().equals(locationW.getId())) {
							return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2433");
						}	
						
						predatingResourceList.add(resourceBlocking.getBlockinggroupid());
					}
				}
			}
						
			// Eliminar los detalles de reserva
			for (ReserveDetailDTO detail : initParamDTO.getReservedetails()) {
				detailPK = new ReserveDetailPK();
				DateConverterFactory2.copyProperties(detail, detailPK);
				
				// Eliminar el detalle de bloqueo de recurso
				reservedetailserver.deleteIdentifiable(detailPK);
			}
			reservedetailserver.flush();
			
			// Validar si las reservas asociadas aún tienen detalles
			for (Long rbid : resourceBlockingList) {
				ReserveDetailW[] rdArr = reservedetailserver.getByPropertyAsArray("reserve.id", rbid);
				if (rdArr.length == 0) {
					// NO existen detalles para esa reserva, se elimina el resourceblocking
					resourceblockingserver.deleteElement(rbid);
				}
			}
			
			// Validar si los preagendamientos actualizados aún tiene detalles
			for (Long bgid : predatingResourceList) {
				ReserveDetailW[] reservesDetails = reservedetailserver.getReserveDetailsOfBlockingGroup(bgid);
				if (reservesDetails == null || reservesDetails.length <= 0) {
					// NO existen detalles para ese preagendamiento, se elimina el predatingresourcegroup
					predatingresourcegroupserver.deleteElement(bgid);
				}
			}

		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} 
		
		return resultDTO;

	}
	
	public BlockingTypeArrayResultDTO getBlockingTypes(){
		BlockingTypeArrayResultDTO resultDTO = new BlockingTypeArrayResultDTO();
				
		try {
			BlockingTypeW[] blockingTypeWs = blockingtypeserver.getBlockingTypes();
			BlockingTypeDTO[] blockingTypeDTOs = new BlockingTypeDTO[blockingTypeWs.length];
			BeanExtenderFactory.copyProperties(blockingTypeWs, blockingTypeDTOs, BlockingTypeDTO.class);

			resultDTO.setBlockingTypes(blockingTypeDTOs);

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public PreDatingReserveDetailArrayResultDTO getPreDatingReserveDetailByLocationAndDate(PreDatingReserveDetailInitParamDTO initParamDTO) {
		PreDatingReserveDetailArrayResultDTO resultDTO = new PreDatingReserveDetailArrayResultDTO();
		
		try {
			// Obtener el proveedor
			VendorW[] vendors = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendors == null || vendors.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendor = vendors[0];
			
			// Obtener el local
			LocationW[] locations = locationserver.getByPropertyAsArray("code", initParamDTO.getLocationcode());
			if (locations == null || locations.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW location = locations[0];
			
			initParamDTO.setDate(initParamDTO.getDate().truncatedTo(ChronoUnit.DAYS));
			
			PreDatingReserveDetailDTO[] details = reservedetailserver.getPreDatingReserveDetailByLocationAndDate(vendor.getId(), location.getId(), initParamDTO.getDate(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);

			if (initParamDTO.getIspageinfo()) {
				int total = reservedetailserver.countPreDatingReserveDetailByLocationAndDate(vendor.getId(), location.getId(), initParamDTO.getDate());

				int rows = initParamDTO.getRows();
				PageInfoDTO pageinfo = new PageInfoDTO();
				pageinfo.setPagenumber(initParamDTO.getPagenumber());
				pageinfo.setRows(details.length);
				pageinfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageinfo.setTotalrows(total);
				resultDTO.setPageinfo(pageinfo);
			}
			
			resultDTO.setDetails(details);

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public PreDatingResourceGroupResultDTO doAddPreDatingResourceGroupAndDetails(PreDatingResourceGroupInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		PreDatingResourceGroupResultDTO resultDTO = new PreDatingResourceGroupResultDTO();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getPredatingresource().getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getPredatingresource().getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			// Fechas
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime since = initParamDTO.getPredatingresource().getSince().toLocalDate().atStartOfDay();
			LocalDateTime until = initParamDTO.getPredatingresource().getUntil().toLocalDate().atTime(LocalTime.MAX);
			
			// El sistema valida que la fecha "Desde" sea igual o mayor a la fecha actual
			if(now.toLocalDate().isAfter(since.toLocalDate())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2411");
			}
			
			// El sistema valida que la fecha "Hasta" sea igual o mayor a la fecha "Desde"
			if(since.isAfter(until)) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2412");
			}
			
			// Validar que la solicitud traiga detalles de reserva para bloquear (módulos de la agenda)
			if (initParamDTO.getDetails() == null || initParamDTO.getDetails().length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2410");
			}
			ReserveDetailDTO[] requestedReserveDetails = initParamDTO.getDetails();
			
			// El sistema valida que el usuario seleccionó al menos un día de la semana para reservar
			if(initParamDTO.getPredatingresource().getRecurrence() <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2416");
			}
			
			// El sistema valida que el rango de fechas definido contiene todos los días de la semana seleccionados al menos una vez
			if (!validateDatesInRangeWithRecurrence(since, until, initParamDTO.getPredatingresource().getRecurrence())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2420");
			}
			
			// Obtener las fechas incluidas en la reserva en base a las fechas de inicio y término y a los días de la semana seleccionados
			LocalDateTime[] dates = getDatesInRangeWithRecurrence(since, until, initParamDTO.getPredatingresource().getRecurrence());
			
			// Días no disponibles
			StringBuilder dayNotAvailableSB = new StringBuilder();
						
			// El sistema valida que los módulos seleccionados no tengan reserva de ningún tipo en los días o fechas especificadas
			// Valida que para las fechas ingresadas, los módulos elegidos estén disponibles			
			for (LocalDateTime date : dates) {
				// Validar que los módulos seleccionados no estén reservados para ese día y ese local
				if (!getDatingResourcesAvailabilityByDateAndLocation(requestedReserveDetails, date, locationW.getId())) {
					dayNotAvailableSB.append(date.format(dtf) + "\n");
				}
			}
			// Si hay dias no disponibles, los informa
			if(dayNotAvailableSB.length() > 0) {
				String error = "No se pudo crear la reserva debido a que existen módulos ocupados, en las siguientes fechas: \n" +  dayNotAvailableSB.toString(); 
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2418", error, false);
			}			

			// Generar un grupo de reserva de recursos para las condiciones solicitadas
			PreDatingResourceGroupW preDatingResourceGroup = new PreDatingResourceGroupW();
			preDatingResourceGroup.setCreated(now);
			preDatingResourceGroup.setLocationid(locationW.getId());
			preDatingResourceGroup.setRecurrence(initParamDTO.getPredatingresource().getRecurrence());
			preDatingResourceGroup.setSince(since);
			preDatingResourceGroup.setUntil(until);
			preDatingResourceGroup.setWho(userdataDTO.getUsername());
			preDatingResourceGroup.setVendorid(vendorW.getId());

			preDatingResourceGroup = predatingresourcegroupserver.addPreDatingResourceGroup(preDatingResourceGroup);

			// Generar las reservas del proveedor a partir de las fechas
			// solicitadas y los detalles
			ResourceBlockingW resourceBlocking = null;
			ReserveDetailW reserveDetail = null;
			for (LocalDateTime date : dates) {
				resourceBlocking = new ResourceBlockingW();
				resourceBlocking.setWhen(date);
				resourceBlocking.setLocationid(locationW.getId());
				resourceBlocking.setBlockinggroupid(preDatingResourceGroup.getId());

				resourceBlocking = resourceblockingserver.addResourceBlocking(resourceBlocking);

				// Agregar los detalles del bloqueo
				for (ReserveDetailDTO requestedReserveDetail : requestedReserveDetails) {
					reserveDetail = new ReserveDetailW();
					reserveDetail.setReserveid(resourceBlocking.getId());
					reserveDetail.setDockid(requestedReserveDetail.getDockid());
					reserveDetail.setModuleid(requestedReserveDetail.getModuleid());
					reserveDetail.setWhen(date);

					reservedetailserver.addReserveDetail(reserveDetail);
				}
			}

			resultDTO.setStatusmessage("La reserva se ha añadido correctamente");

		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public ResourceBlockingGroupResultDTO doAddResourceBlockingGroupAndDetails(ResourceBlockingGroupInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		ResourceBlockingGroupResultDTO resultDTO = new ResourceBlockingGroupResultDTO(); 
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try {
			// Obtener Local
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initParamDTO.getBlockingroup().getLocationcode());
			if(locationArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1501");
			}
			LocationW locationW = locationArr[0];
			
			// Fechas
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime since = initParamDTO.getBlockingroup().getSince().toLocalDate().atStartOfDay();
			LocalDateTime until = initParamDTO.getBlockingroup().getUntil().toLocalDate().atTime(LocalTime.MAX);
			
			// El sistema valida que la fecha "Desde" sea igual o mayor a la fecha actual
			if(now.toLocalDate().isAfter(since.toLocalDate())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2411");
			}
			
			// El sistema valida que la fecha "Hasta" sea igual o mayor a la fecha "Desde"
			if(since.isAfter(until)) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2412");
			}
			
			// Validar que la solicitud traiga detalles de reserva para bloquear (módulos de la agenda)
			if (initParamDTO.getDetails() == null || initParamDTO.getDetails().length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2410");
			}
			ReserveDetailDTO[] requestedReserveDetails = initParamDTO.getDetails();
			
			// El sistema valida que el usuario seleccionó al menos un día de la semana para reservar
			if(initParamDTO.getBlockingroup().getRecurrence() <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2416");
			}
			
			// El sistema valida que el rango de fechas definido contiene todos los días de la semana seleccionados al menos una vez
			if (!validateDatesInRangeWithRecurrence(since, until, initParamDTO.getBlockingroup().getRecurrence())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2420");
			}
			
			// Obtener las fechas incluidas en la reserva en base a las fechas de inicio y término y a los días de la semana seleccionados
			LocalDateTime[] dates = getDatesInRangeWithRecurrence(since, until, initParamDTO.getBlockingroup().getRecurrence());
						
			// Días no disponibles
			StringBuilder dayNotAvailableSB = new StringBuilder();
			
			// El sistema valida que los módulos seleccionados no tengan reserva de ningún tipo en los días o fechas especificadas
			// Valida que para las fechas ingresadas, los módulos elegidos estén disponibles
			for (LocalDateTime date : dates) {
				// Validar que los módulos seleccionados no estén reservados para ese día y ese local
				if (!getDatingResourcesAvailabilityByDateAndLocation(requestedReserveDetails, date, locationW.getId())) {
					dayNotAvailableSB.append(date.format(dtf) + "\n");
				}
			}

			// Si hay dias no disponibles, los informa
			if(dayNotAvailableSB.length() > 0) {
				String error = "No se pudo crear el bloqueo debido a que existen módulos ocupados, en las siguientes fechas: \n" +  dayNotAvailableSB.toString(); 
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2418", error, false);
			}
			
			// Generar un grupo de reserva de recursos para las condiciones solicitadas
			ResourceBlockingGroupW resouceBlockingGroup = new ResourceBlockingGroupW();
			resouceBlockingGroup.setComment(initParamDTO.getBlockingroup().getComment().trim());
			resouceBlockingGroup.setCreated(now);
			resouceBlockingGroup.setLocationid(locationW.getId());
			resouceBlockingGroup.setReason(initParamDTO.getBlockingroup().getReason().trim());
			resouceBlockingGroup.setRecurrence(initParamDTO.getBlockingroup().getRecurrence());
			resouceBlockingGroup.setSince(since);
			resouceBlockingGroup.setUntil(until);
			resouceBlockingGroup.setWho(initParamDTO.getBlockingroup().getWho());

			resouceBlockingGroup = resourceblockinggroupserver.addResourceBlockingGroup(resouceBlockingGroup);
			
			// Generar los bloqueos a partir de las fechas solicitadas y los detalles
			ResourceBlockingW resourceBlocking = null;
			ReserveDetailW reserveDetail = null;
			for (LocalDateTime date : dates) {

				resourceBlocking = new ResourceBlockingW();
				resourceBlocking.setWhen(date);
				resourceBlocking.setLocationid(locationW.getId());
				resourceBlocking.setBlockinggroupid(resouceBlockingGroup.getId());

				resourceBlocking = resourceblockingserver.addResourceBlocking(resourceBlocking);

				// Agregar los detalles del bloqueo
				for (ReserveDetailDTO requestedReserveDetail : requestedReserveDetails) {
					reserveDetail = new ReserveDetailW();
					reserveDetail.setReserveid(resourceBlocking.getId());
					reserveDetail.setDockid(requestedReserveDetail.getDockid());
					reserveDetail.setModuleid(requestedReserveDetail.getModuleid());
					reserveDetail.setWhen(date);

					reservedetailserver.addReserveDetail(reserveDetail);
				}
			}

			resultDTO.setStatusmessage("El bloqueo solicitado fue añadido exitosamente");
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch(AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public ReserveDetailBlockingReportDTO getReserveDetailDataOfBlockingGroup(ResourceBlockingGroupDetailDataInitParamDTO initParamDTO) {
		ReserveDetailBlockingReportDTO resultDTO = new ReserveDetailBlockingReportDTO();
		ReserveDetailBlockingDataDTO[] details = null;
		int total = 0;
		
		try {
			details = reservedetailserver.getReserveDetailDataOfBlockingGroup(initParamDTO.getBlockinggroupid(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
			
			resultDTO.setReservedetailblockingdata(details);
			if (initParamDTO.getIspageinfo()) {
				total = reservedetailserver.countReserveDetailDataOfBlockingGroup(initParamDTO.getBlockinggroupid());

				int rows = initParamDTO.getRows();
				PageInfoDTO pageinfo = new PageInfoDTO();
				pageinfo.setPagenumber(initParamDTO.getPagenumber());
				pageinfo.setRows(details.length);
				pageinfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageinfo.setTotalrows(total);
				resultDTO.setPageinfo(pageinfo);
			}
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
		
	public BaseResultDTO doDeleteAllReserveDetailsOfPreDatingResourceGroup(DeleteAllPreDatingResourceGroupInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0]; 
			
			// Obtener grupo
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("id", "id", initParamDTO.getPredatingresourcegroupid());
			props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			PreDatingResourceGroupW[] predatingresourcegroupArr = predatingresourcegroupserver.getByPropertiesAsArray(props);
			if(predatingresourcegroupArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2430");
			}
			PreDatingResourceGroupW predatingresourcegroupW = predatingresourcegroupserver.getById(predatingresourcegroupArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Obtener los  detalles de las reservas asociadas al grupo
			ReserveDetailW[] reservesDetails = reservedetailserver.getReserveDetailsOfBlockingGroup(initParamDTO.getPredatingresourcegroupid());
			
			// Elimina detalles  
			ReserveDetailId rdKey = null;
			for(ReserveDetailW reservesDetailW :  reservesDetails) {
				rdKey = new ReserveDetailId(reservesDetailW.getReserveid(), reservesDetailW.getModuleid(), reservesDetailW.getDockid());
				reservedetailserver.deleteIdentifiable(rdKey);
			}
			
			// Obtiene reservas asociadas al grupo (resourceblocking)
			ResourceBlockingW[] resourceblockingArr = resourceblockingserver.getByPropertyAsArray("blockinggroup.id", predatingresourcegroupW.getId());
			for(ResourceBlockingW resourceblockingW :resourceblockingArr) {
				ResourceBlockingW resourceblockingToDel = resourceblockingserver.getById(resourceblockingW.getId(), LockModeType.PESSIMISTIC_WRITE);
				resourceblockingserver.deleteElement(resourceblockingToDel.getId());
			}
			
			// Elimina Grupo
			predatingresourcegroupserver.deleteElement(predatingresourcegroupW.getId());
			
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	public BaseResultDTO doDeleteAllReserveDetailsOfResourceBlockingGroup(DeleteAllResourceBlockingGroupInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try{
			// Obtener grupo
			ResourceBlockingGroupW[] resourceblockinggroupArr = resourceblockinggroupserver.getByPropertyAsArray("id", initParamDTO.getResourceblockinggroupid());
			if(resourceblockinggroupArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2430");
			}
			ResourceBlockingGroupW resourceblockinggroupW = resourceblockinggroupserver.getById(resourceblockinggroupArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Obtener los  detalles de las reservas asociadas al grupo
			ReserveDetailW[] reservesDetails = reservedetailserver.getReserveDetailsOfBlockingGroup(initParamDTO.getResourceblockinggroupid());
			
			// Elimina detalles  
			ReserveDetailId rdKey = null;
			for(ReserveDetailW reservesDetailW :  reservesDetails) {
				rdKey = new ReserveDetailId(reservesDetailW.getReserveid(), reservesDetailW.getModuleid(), reservesDetailW.getDockid());
				reservedetailserver.deleteIdentifiable(rdKey);
			}
			
			// Obtiene reservas asociadas al grupo (resourceblocking)
			ResourceBlockingW[] resourceblockingArr = resourceblockingserver.getByPropertyAsArray("blockinggroup.id", resourceblockinggroupW.getId());
			for(ResourceBlockingW resourceblockingW :resourceblockingArr) {
				ResourceBlockingW resourceblockingToDel = resourceblockingserver.getById(resourceblockingW.getId(), LockModeType.PESSIMISTIC_WRITE);
				resourceblockingserver.deleteElement(resourceblockingToDel.getId());
			}
			
			// Elimina Grupo
			resourceblockinggroupserver.deleteElement(resourceblockinggroupW.getId());
			
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

	}
	
	private LocalDateTime[] getDatesInRangeWithRecurrence(LocalDateTime since, LocalDateTime until, int recurrence) {

		LocalDateTime targetDate = since;

		// Obtener los días en la semana incluidos en la recurrencia
		boolean monday = (IWeekDayConstants.MONDAY & recurrence) == IWeekDayConstants.MONDAY;
		boolean tuesday = (IWeekDayConstants.TUESDAY & recurrence) == IWeekDayConstants.TUESDAY;
		boolean wednesday = (IWeekDayConstants.WEDNESDAY & recurrence) == IWeekDayConstants.WEDNESDAY;
		boolean thursday = (IWeekDayConstants.THURSDAY & recurrence) == IWeekDayConstants.THURSDAY;
		boolean friday = (IWeekDayConstants.FRIDAY & recurrence) == IWeekDayConstants.FRIDAY;
		boolean saturday = (IWeekDayConstants.SATURDAY & recurrence) == IWeekDayConstants.SATURDAY;
		boolean sunday = (IWeekDayConstants.SUNDAY & recurrence) == IWeekDayConstants.SUNDAY;

		List<LocalDateTime> dateList = new ArrayList<LocalDateTime>();

		DayOfWeek dow;
		do {
			dow = targetDate.getDayOfWeek();
			
			if((dow == DayOfWeek.MONDAY && monday) || (dow == DayOfWeek.TUESDAY && tuesday) 
				|| (dow == DayOfWeek.WEDNESDAY && wednesday ) || (dow == DayOfWeek.THURSDAY && thursday) 
				|| (dow == DayOfWeek.FRIDAY && friday) || (dow == DayOfWeek.SATURDAY && saturday) 
				|| (dow == DayOfWeek.SUNDAY && sunday)){
				
				dateList.add(targetDate);
			}
			
			targetDate = targetDate.plusDays(1);
			
		} while (! until.isBefore(targetDate));
			
		return (LocalDateTime[]) dateList.toArray(new LocalDateTime[dateList.size()]);
	}
	
	private boolean validateDatesInRangeWithRecurrence(LocalDateTime since, LocalDateTime until, int recurrence) {

		LocalDateTime targetDate = since;

		// Obtener los días en la semana incluidos en la recurrencia
		boolean monday = (IWeekDayConstants.MONDAY & recurrence) == IWeekDayConstants.MONDAY;
		boolean tuesday = (IWeekDayConstants.TUESDAY & recurrence) == IWeekDayConstants.TUESDAY;
		boolean wednesday = (IWeekDayConstants.WEDNESDAY & recurrence) == IWeekDayConstants.WEDNESDAY;
		boolean thursday = (IWeekDayConstants.THURSDAY & recurrence) == IWeekDayConstants.THURSDAY;
		boolean friday = (IWeekDayConstants.FRIDAY & recurrence) == IWeekDayConstants.FRIDAY;
		boolean saturday = (IWeekDayConstants.SATURDAY & recurrence) == IWeekDayConstants.SATURDAY;
		boolean sunday = (IWeekDayConstants.SUNDAY & recurrence) == IWeekDayConstants.SUNDAY;

		DayOfWeek dow;
		do {
			dow = targetDate.getDayOfWeek();
			
			if (monday && dow == DayOfWeek.MONDAY) {
				monday = false;
			}
			if (tuesday && dow == DayOfWeek.TUESDAY) {
				tuesday = false;
			}
			if (wednesday && dow == DayOfWeek.WEDNESDAY) {
				wednesday = false;
			}
			if (thursday && dow == DayOfWeek.THURSDAY) {
				thursday = false;
			}
			if (friday && dow == DayOfWeek.FRIDAY) {
				friday = false;
			}
			if (saturday && dow == DayOfWeek.SATURDAY) {
				saturday = false;
			}
			if (sunday && dow == DayOfWeek.SUNDAY) {
				sunday = false;
			}
			
			targetDate = targetDate.plusDays(1);
			
		} while (!until.isBefore(targetDate));
			
		return !(monday || tuesday || wednesday || thursday || friday || saturday || sunday);
	}
	
	private boolean getDatingResourcesAvailabilityByDateAndLocation(ReserveDetailDTO[] requestedReserveDetails, LocalDateTime date, Long locationid) throws OperationFailedException, NotFoundException {

		// Se determina si los resercedetails indicados como input están disponbles
		boolean available = true;

		// Se obtienen todas las reservas para el día y bodega especificados
		PropertyInfoDTO prop1 = new PropertyInfoDTO("reserve.when", "when", date);
		PropertyInfoDTO prop2 = new PropertyInfoDTO("reserve.location.id", "locationid", locationid);
		ReserveDetailW[] reserveDetails = reservedetailserver.getByPropertiesAsArray(prop1, prop2);
		if (reserveDetails != null && reserveDetails.length > 0) {
			List<String> rdList = new ArrayList<String>();
			String key = "";
			for (ReserveDetailW reserveDetail : reserveDetails) {
				key = reserveDetail.getDockid() + "_" + reserveDetail.getModuleid();
				rdList.add(key);
			}

			for (ReserveDetailDTO requestedReserveDetail : requestedReserveDetails) {
				key = requestedReserveDetail.getDockid() + "_" + requestedReserveDetail.getModuleid();
				if (rdList.contains(key)) {
					available = false;
					break;
				}
			}
		}
		return available;
	}

}
