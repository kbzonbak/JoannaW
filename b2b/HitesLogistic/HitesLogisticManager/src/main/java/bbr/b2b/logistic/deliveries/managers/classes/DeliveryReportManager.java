package bbr.b2b.logistic.deliveries.managers.classes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;


import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.BaseResultsDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO.ComparisonOperator;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryDetailServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryStateServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcFileServerLocal;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryDetailW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateTypeW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcFileW;
import bbr.b2b.logistic.ddcdeliveries.report.classes.AddDODeliveryOfDirectOrdersWSInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.AddDODeliveryOfDirectOrdersWSResultDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.AddDODeliveryOfDirectOrdersWSResultDetailDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DdcDeliveryStateTypeArrayResultDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DdcDeliveryStateTypeDataDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DdcDeliveryStateUpdateInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.DirectOrdersWSInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.UpdateDODeliveryWSInitParamDTO;
import bbr.b2b.logistic.ddcdeliveries.report.classes.UpdateDODeliveryWSResultDTO;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderDetailServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateTypeServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.BulkServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DatingRequestServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DocumentTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.TimeModuleServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DatingRequestW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryDetailW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.TimeModuleW;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrOrderDeliveryDetailId;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNUploadResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.AsnUploadErrorDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.CheckDataToDatingRequestInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestDetailDataResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DatingRequestResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DaysToDatingRequestResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DocumentDeleteInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DocumentUpdateDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DocumentUpdateInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDeleteInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailExcelReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDetailTotalDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryIdInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryStateTypeDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryStateTypeResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustUnitsDetailDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryAdjustUnitsInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderDeliveryUnitsInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderToDeliveryUnitsDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrOrderToDeliveryUnitsResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.InactiveDaysResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListDeleteResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.PackingListReportTotalDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.TimeModuleDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.TimeModuleReportDataDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadASNInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListInitParamDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.UploadPackingListResultDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResponseDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.WSSentMessageResultDTO;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrPreDeliveryDetailW;
import bbr.b2b.logistic.dvrorders.report.classes.DvhOrderReportDataDTO;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.data.wrappers.ItemW;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.managers.interfaces.ASNMessageManagerLocal;
import bbr.b2b.logistic.managers.interfaces.DeliveryReportManagerRemote;
import bbr.b2b.logistic.parameters.classes.ParameterServerLocal;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterW;
import bbr.b2b.logistic.report.classes.BaseResultComparator;
import bbr.b2b.logistic.report.classes.FileUploadErrorDTO;
import bbr.b2b.logistic.report.classes.UserLogDataDTO;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;
import bbr.b2b.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.logistic.utils.EmailValidation;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;


@Stateless(name = "managers/DeliveryReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DeliveryReportManager implements DeliveryReportManagerLocal, DeliveryReportManagerRemote {

	private static Logger logger = Logger.getLogger(DeliveryReportManager.class);
 
	// Packing List
	private static final Integer 	PL_UPLOAD_BULK_TOTAL_LENGTH  		= 18;
	
	private static final Integer 	PL_UPLOAD_LPN_PREFIX_SINCE  		= 1;
	private static final Integer 	PL_UPLOAD_LPN_PREFIX_LENGTH  		= 2;
	private static final String 	PL_UPLOAD_LPN_PREFIX_VALUE  		= "55";
	
	private static final Integer 	PL_UPLOAD_VENDORCODE_SINCE 			= 3;
	private static final Integer 	PL_UPLOAD_VENDORCODE_LENGTH  		= 8;
	
	private static final Integer 	PL_UPLOAD_LPN_SERIAL_SINCE  		= 11;
	private static final Integer 	PL_UPLOAD_LPN_SERIAL_LENGTH  		= 8;
	
	
//	private static final String[]	PL_UPLOAD_LPN_ORDERTYPEFILTER		= {"STOCK"};
	
	// WS
	private static final String 	WS_USER								= "B2B Link";
	private static final String 	WS_COMMENT							= "Vía WS";
	
	// ASN LGFDATA
/*	private static final String ASN_PREFIX_FILE	 						= "ISS";
	
	private static final String ASN_LGF_VERSION 						= "6.4";
	private static final String ASN_LGF_CLIENT_ENV_CODE					= "Hites";
	private static final String ASN_LGF_PARENT_COMPANY_CODE				= "CAD";
	private static final String ASN_LGF_ENTITY							= "ib_shipment";
	private static final String ASN_LGF_PRIORITY_DATE					= "20501231";
	

	private static final String ASN_LGF_COMPANYCODE						= "HITES";*/
	
	// Interfaz Citas Logfire
/*	private static final String DAT_PREFIX_FILE 						= "IAP";
	
	private static final String DAT_LGF_VERSION 						= "6.4";
	private static final String DAT_ORIGIN_SYSTEM 						= "B2B";
	private static final String DAT_LGF_CLIENT_ENV_CODE					= "WMS";
	private static final String DAT_LGF_PARENT_COMPANY_CODE				= "*";
	private static final String DAT_LGF_ENTITY							= "appointment";
	private static final String DAT_DOCK_VALUE							= "Entrada";
	

	private static JAXBContext jcLgfData = null;
	
	private static JAXBContext getJCLGFDATA() throws JAXBException {
		if (jcLgfData == null)
			jcLgfData = JAXBContext.newInstance("bbr.b2b.logistic.xml.lib_shipment");
		return jcLgfData;
	}
		
	private static JAXBContext jcLgfData_appointment = null;
	
	private static JAXBContext getJCLGFDATA_APPOINTMENT() throws JAXBException{
		if(jcLgfData_appointment == null)
			jcLgfData_appointment = JAXBContext.newInstance("bbr.b2b.logistic.xml.lib_appointment");
		return jcLgfData_appointment;
	}
	*/	
	
	@Resource
	private ManagedExecutorService executor;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanagerlocal;
	
	@EJB
	BulkServerLocal bulkserver;
	
	@EJB
	BulkDetailServerLocal bulkdetailserver;
	
	@EJB
	DatingRequestServerLocal datingrequestserver;

	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DdcDeliveryServerLocal ddcdeliveryserver;
	
	@EJB
	DdcDeliveryStateTypeServerLocal ddcdeliverystatetypeserver;
	
	@EJB
	DdcDeliveryDetailServerLocal ddcdeliverydetailserver;
	
	@EJB
	DdcOrderServerLocal ddcorderserver;
	
	@EJB
	DdcOrderDetailServerLocal ddcorderdetailserver;
	
	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;
	
	@EJB
	DdcFileServerLocal ddcfileserver;
	
	@EJB
	DdcDeliveryStateServerLocal ddcdeliverystateserver;
	
	@EJB
	DdcOrderStateServerLocal ddcorderstateserver;
	
	@EJB
	DocumentServerLocal documentserver;
	
	@EJB
	DocumentTypeServerLocal documenttypeserver;
	
	@EJB
	DocumentStateServerLocal documentstateserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateServerLocal dvrdeliverystateserver;
	
	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver;
	
	@EJB
	DvrOrderDeliveryDetailServerLocal dvrorderdeliverydetailserver;
	
	@EJB
	DvrPreDeliveryDetailServerLocal dvrpredeliverydetailserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	ParameterServerLocal parameterserver;
	
	@EJB
	RetailerServerLocal retailerserver;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@EJB
	TimeModuleServerLocal timemoduleserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ASNMessageManagerLocal asnmessagemanager;
	
	private Map<String, DayOfWeek> daysMapping = new HashMap<String, DayOfWeek>();
	{
		daysMapping.put("LUNES", DayOfWeek.MONDAY);
		daysMapping.put("MARTES", DayOfWeek.TUESDAY);
		daysMapping.put("MIERCOLES", DayOfWeek.WEDNESDAY);
		daysMapping.put("JUEVES", DayOfWeek.THURSDAY);
		daysMapping.put("VIERNES", DayOfWeek.FRIDAY);
		daysMapping.put("SABADO", DayOfWeek.SATURDAY);
		daysMapping.put("DOMINGO", DayOfWeek.SUNDAY);
	}
	
	// Obtiene parámetros de cantidad de días desde los que se puede realizar la solicitud de cita
	private DaysToDatingRequestResultDTO getDaystoDatingRequest() {
		DaysToDatingRequestResultDTO resultDTO = new DaysToDatingRequestResultDTO(); 
		
		try{
			// Obtiene la cantidad de días desde que se puede hacer la solicitud de cita. Código "DIAS_SOL_CITA"
			PropertyInfoDTO[] props = new PropertyInfoDTO[2]; 
			props[0] = new PropertyInfoDTO("code", "code", "DIAS_SOL_CITA");
			props[1] = new PropertyInfoDTO("active", "active", true);
			ParameterW[] parameterArr = parameterserver.getByPropertiesAsArray(props);
			
			if(parameterArr.length > 0){
				int days = Integer.valueOf(parameterArr[0].getValue());
				resultDTO.setDaysamount(days);
			}

			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L8500");
		}
	}
	
	
	private Boolean isValidDateDaysFordward(LocalDate requestdate, Integer daysfordward, DayOfWeek[] dowArray) throws OperationFailedException {
		
		// Crea set con días inhábiles
		Set<DayOfWeek> dowSet = new HashSet<DayOfWeek>();
		for(DayOfWeek dow : dowArray){
			dowSet.add(dow);
		}
		
		// Si todos los días de la semana están marcados como inhábiles, se devuelve una excepcion
		if(dowSet.size() == 7){
			throw new OperationFailedException("Todos los días están marcados como inhábiles. No se puede continuar");
		}
		
		// Obtiene menor fecha válida para solicitar cita
		LocalDate lowestDate = LocalDate.now(); 
		for(int i=0; i<daysfordward; i++){
			DayOfWeek dow ;
			do{
				lowestDate = lowestDate.plusDays(1);
				dow = lowestDate.getDayOfWeek();	
				
			} while(dowSet.contains(dow));
		}
		
		
		// Si el día elegido es menor a la menor facha válida
		if(requestdate.isBefore(lowestDate)){
			return false;
		}
		return true;		
	}
	
	private Boolean isValidDateActiveDay(LocalDate requestdate, DayOfWeek[] dowArray) throws OperationFailedException {
		// Crea set con días inhábiles
		Set<DayOfWeek> dowSet = new HashSet<DayOfWeek>();
		for(DayOfWeek dow : dowArray){
			dowSet.add(dow);
		}
		
		// Si todos los días de la semana están marcados como inhábiles, se devuelve una excepcion
		if(dowSet.size() == 7){
			throw new OperationFailedException("Todos los días están marcados como inhábiles. No se puede continuar");
		}
		
		// Si la fecha solicitada corresponde a un día inhábil
		if(dowSet.contains(requestdate.getDayOfWeek())){
			return false;
		}
		
		return true;
	}
	

	
//	private Asn doCreateASNMessage(ASNDataToMessageDTO asndata) throws OperationFailedException, NotFoundException {
//		
//		
//		bbr.b2b.logistic.xml.asn.ObjectFactory objectFactory = new bbr.b2b.logistic.xml.asn.ObjectFactory();
//		bbr.b2b.logistic.xml.asn.Asn message = objectFactory.createAsn();
//
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
//		
//		// Crea mensaje
//		message.setAccion(asndata.getAction());
//		message.setNumCitaB2B(asndata.getDeliverynumber().intValue());
//		message.setCodProveedor(asndata.getVendorcode());
//		message.setCodLocalEntrega(asndata.getDeliverylocationcode());
//		message.setFechaCita(df.format(asndata.getDatingdate()));
//		message.setNumAsn(Integer.valueOf(asndata.getAsnnumber()));
//		
//		// Detalle de Bultos
//		bbr.b2b.logistic.xml.asn.Asn.Bultos bultos = objectFactory.createAsnBultos();
//		
//		//List<bbr.b2b.logistic.xml.asn.Asn.Bultos.DetalleBulto> detalleList = message.getBultos().getDetalleBulto();
//		List<bbr.b2b.logistic.xml.asn.Asn.Bultos.DetalleBulto> detalleList = bultos.getDetalleBulto();
//		
//		for(ASNDetailDataToMessageDTO asndetaildata : asndata.getAsndetaildatatomessage()) {
//			
//			bbr.b2b.logistic.xml.asn.Asn.Bultos.DetalleBulto detalle = objectFactory.createAsnBultosDetalleBulto();
//			detalle.setNumOc(asndetaildata.getOcnumber().intValue());
//			detalle.setPosicion(asndetaildata.getPosition());
//			detalle.setCodProducto(asndetaildata.getItemsku());
//			detalle.setCodLocalDestino(asndetaildata.getLocationcode());
//			detalle.setCodLpn(asndetaildata.getLpncode());
//			detalle.setNumDocumento(Integer.valueOf(asndetaildata.getDocumentnumber()));
//			detalle.setTipoDocumento(asndetaildata.getDocumenttype());
//			detalle.setUnidadesComprometidas(new BigDecimal(asndetaildata.getUnits()));
//			
//			detalleList.add(detalle);
//		}
//		
//		
//		
//		message.setBultos(bultos);
//		
//		return message;
//			
//	}
	
/*	public LgfData doCreateASNMessageLgf(ASNDataToMessageDTO asndata) throws OperationFailedException, NotFoundException {
		
		// Formatos decimales
		DecimalFormat df = new DecimalFormat("0");
				
		// Formato de fechas
		DateTimeFormatter msgDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter asnDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd000000");
		DateTimeFormatter docDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		LocalDateTime now = LocalDateTime.now();
		
		// Bodega Internet
		LocationW internetLocationW = locationserver.getByPropertyAsSingleResult("code", "02");
		
		// Tipos de OC
		OrderTypeW predType = ordertypeserver.getByPropertyAsSingleResult("code", "PREDISTRIBUIDA");
		OrderTypeW stockType = ordertypeserver.getByPropertyAsSingleResult("code", "STOCK");
		OrderTypeW vevType = ordertypeserver.getByPropertyAsSingleResult("code", "DVH");

		// Se construye el xml para enviar a LOGFIRE
		bbr.b2b.logistic.xml.lib_shipment.ObjectFactory objFactory = new bbr.b2b.logistic.xml.lib_shipment.ObjectFactory();
		// Se crean los datos del encabezado LogFire
		Header header = objFactory.createHeader();
		
		header.setDocumentVersion(ASN_LGF_VERSION); // Versión Actual de LogFire
		header.setClientEnvCode(ASN_LGF_CLIENT_ENV_CODE); // Ambiente LogFire
		header.setParentCompanyCode(ASN_LGF_PARENT_COMPANY_CODE); // Valor fijo: CAD
		header.setEntity(ASN_LGF_ENTITY); // Nombre de la interfaz en LogFire
		header.setTimeStamp(msgDateFormat.format(now)); // Fecha y hora de envío del mensaje
		header.setMessageId(ASN_PREFIX_FILE + "_" + asndata.getAsnnumber()); // Nombre del mensaje
		
		// Se crean los detalles del ASN
		List<IbShipmentDtl> ibShipmentDtlList = new ArrayList<IbShipmentDtl>();
		IbShipmentDtl ibShipmentDtl = null;
		for (int i = 0 ; i < asndata.getAsndetaildatatomessage().length; i++){
			ibShipmentDtl = objFactory.createIbShipmentDtl();
			
			ibShipmentDtl.setSeqNbr(i + 1); // Contador de fila de detalle
			ibShipmentDtl.setActionCode(asndata.getAsndetaildatatomessage()[i].getAction()); // Acción: CREATE/UPDATE/DELETE
			ibShipmentDtl.setLpnNbr(asndata.getAsndetaildatatomessage()[i].getLpncode()); // Código LPN
			ibShipmentDtl.setLpnWeight(""); // Peso del LPN
			ibShipmentDtl.setLpnVolume(""); // Volumen del LPN
			ibShipmentDtl.setItemAlternateCode(""); // Código de empaque del SKU
			ibShipmentDtl.setItemPartA(asndata.getAsndetaildatatomessage()[i].getItemsku()); // Código SKU (primera parte)
			ibShipmentDtl.setItemPartB(""); // Código SKU (segunda parte)
			ibShipmentDtl.setItemPartC(""); // Código SKU (tercera parte)
			ibShipmentDtl.setItemPartD(""); // Código SKU (cuarta parte)
			ibShipmentDtl.setItemPartE(""); // Código SKU (quinta parte)
			ibShipmentDtl.setItemPartF(""); // Código SKU (sexta parte)
			ibShipmentDtl.setPrePackCode(""); // Código de pre-empaque
			ibShipmentDtl.setPrePackRatio(0); // SKU/pre-empaque
			ibShipmentDtl.setPrePackTotalUnits(0); // SKU totales en pre-empaques
			ibShipmentDtl.setInvnAttrA(""); // Atributo A
			ibShipmentDtl.setInvnAttrB(""); // Atributo B
			ibShipmentDtl.setInvnAttrC(""); // Atributo C
			ibShipmentDtl.setShippedQty(df.format(asndata.getAsndetaildatatomessage()[i].getUnits())); // Cantidad enviada
			ibShipmentDtl.setPriorityDate(ASN_LGF_PRIORITY_DATE); // Fecha de prioridad (Valor fijo:  20501231)				
			ibShipmentDtl.setPoNbr(String.valueOf(asndata.getAsndetaildatatomessage()[i].getOcnumber())); // Número de la OC
			ibShipmentDtl.setPalletNbr(""); // Número de pálet
			ibShipmentDtl.setPutawayType(""); // Tipo de ordenamiento
			ibShipmentDtl.setExpiryDate(""); // Fecha de vencimiento del inventario			
			ibShipmentDtl.setBatchNbr(asndata.getAsndetaildatatomessage()[i].getReferencenumber() != null ? asndata.getAsndetaildatatomessage()[i].getReferencenumber() : ""); // Número de lote del cliente
			ibShipmentDtl.setRecvXdockFacilityCode(asndata.getOrdertypecode().equals("PREDISTRIBUIDA") ? asndata.getAsndetaildatatomessage()[i].getDestinationlocationcode() : ""); // Código de la sucursal de destino (solo para orden tipo 'PREDISTRIBUIDA')		
			ibShipmentDtl.setCustField1(""); // Campo configurable 1
			ibShipmentDtl.setCustField2(""); // Campo configurable 2
			ibShipmentDtl.setCustField3(""); // Campo configurable 3
			ibShipmentDtl.setCustField4(""); // Campo configurable 4
			ibShipmentDtl.setCustField5(""); // Campo configurable 5
			ibShipmentDtl.setLpnIsPhysicalPalletFlg(false); // LPN-estiba
			ibShipmentDtl.setPoSeqNbr(0); // Posición en el detalle de OC
			ibShipmentDtl.setPrePackRatioSeq(0); // Secuencia en el pre-empaque
			ibShipmentDtl.setLpnLockCode(""); // Código de bloqueo del LPN
			ibShipmentDtl.setItemBarcode(""); // Código de barra del LPN
			ibShipmentDtl.setUom(""); // Unidad de medida del empaque
			ibShipmentDtl.setLpnLength(""); // Largo del LPN
			ibShipmentDtl.setLpnWidth(""); // Ancho del LPN
			ibShipmentDtl.setLpnHeight(""); // Altura del LPN
			
			ibShipmentDtlList.add(ibShipmentDtl);						
		}
		
		// Se crea la cabecera del ASN
		// Para determinar campo ShipmentType
		String shipmentType = "";
		
		// Obtiene la orden asociada
		DvrOrderW[] dvrorderArr = dvrorderserver.getByPropertyAsArray("id", asndata.getDvrorderid());
		if(dvrorderArr.length == 0) {
			throw new NotFoundException("Orden de copra no existe") ;
		}
		DvrOrderW dvrorderW =  dvrorderArr[0];
		Long[] dvrorderids = {dvrorderW.getId()};
		
		// Tipo de orden
		OrderTypeW[] orderTypeArr = ordertypeserver.getOrderTypeByDvrOrders(dvrorderids);
		OrderTypeW orderTypeW =  orderTypeArr[0];  
		
		// Local de destino de la orden
		LocationW deliverylocationW = locationserver.getById(dvrorderW.getDeliverylocationid());
		
		// INTNAC : ordenes de bodega 2 Internet.
		if(deliverylocationW.getId().equals(internetLocationW.getId())) {
			shipmentType = "INTNAC"; 
		} 
		
		// NAC : OC de stock
		else if(orderTypeW.getId().equals(stockType.getId())) {
			shipmentType = "NAC";
		}
		
		// XD: OC predistribuida
		else if(orderTypeW.getId().equals(predType.getId())) {
			shipmentType = "XD";
		}
		
		// DVH: OC VeV.
		else if(orderTypeW.getId().equals(vevType.getId())) {
			shipmentType = "DVH";
		}
		/////////////////////////////////////////////////////////////////////////////////
				
		IbShipmentHdr ibShipmentHbr = objFactory.createIbShipmentHdr();
		
		ibShipmentHbr.setShipmentNbr(asndata.getAsnnumber()); // Número de ASN (Nro OC/documento tributario/fecha/correlativo)
		ibShipmentHbr.setFacilityCode(asndata.getDeliverylocationcode()); // Centro de distribución
		ibShipmentHbr.setCompanyCode(ASN_LGF_COMPANYCODE); // Código de la compañía SAP 
		ibShipmentHbr.setTrailerNbr(asndata.getDocumenttype()); // Tipo de Documento
		ibShipmentHbr.setActionCode(asndata.getAction()); // Acción: CREATE/UPDATE/DELETE
		ibShipmentHbr.setRefNbr(String.valueOf(asndata.getDocumentnumber())); //Número de referencia
		ibShipmentHbr.setShipmentType(shipmentType); // Clase de documento ASN
		ibShipmentHbr.setLoadNbr("L" + asndata.getDvrdeliverynumber()); // Número de carga = Número de entrega B2B
		ibShipmentHbr.setManifestNbr(asndata.getDocumentcode()); // Tipo de documento (código)
		ibShipmentHbr.setTrailerType(""); // Tipo de camión
		ibShipmentHbr.setVendorInfo(asndata.getVendorcode()); // Información adicional del proveedor -> code
		ibShipmentHbr.setOriginInfo(""); // Información del origen
		ibShipmentHbr.setOriginCode(""); // Código del origen
		ibShipmentHbr.setOrigShippedUnits(0); // Total de unidades enviadas
		ibShipmentHbr.setLockCode(""); // Código de bloqueo
		ibShipmentHbr.setShippedDate(asndata.getPluploaddate() != null ? asnDateFormat.format(asndata.getPluploaddate()) : ""); // Fecha de carga de ASN en B2B 	--> guardar fecha de carga de PL en delivery
		ibShipmentHbr.setOrigShippedLpns(0); // Total de LPN enviados
		ibShipmentHbr.setCustField1(asndata.getNetamount() != null ? df.format(asndata.getNetamount()) : ""); // Monto neto	--> documento
		ibShipmentHbr.setCustField2(asndata.getTaxamount() != null ? df.format(asndata.getTaxamount()) : ""); // IVA --> diff en el doc
		ibShipmentHbr.setCustField3(asndata.getTotalamount() != null ? df.format(asndata.getTotalamount()) : ""); // Monto total --> doc
		ibShipmentHbr.setCustField4(docDateFormat.format(asndata.getExpirationdate())); // Fecha de vencimiento de la orden asociada
		ibShipmentHbr.setCustField5((asndata.getPluploaddate() != null) ? docDateFormat.format(asndata.getPluploaddate()) : ""); // Fecha de factura			-->  guardar fecha de carga de PL en delivery

		// Se crea el ASN
		IbShipment IbShipment = objFactory.createIbShipment();
			
		IbShipment.setIbShipmentHdr(ibShipmentHbr);
		IbShipment.getIbShipmentDtl().addAll(ibShipmentDtlList);
		
		// Se crea la lista de ASNs
		ListOfIbShipments listOfIbShipments = objFactory.createListOfIbShipments();
			
		listOfIbShipments.getIbShipment().add(IbShipment);
					
		// Se crean los datos de LOGFIRE
		LgfData lgfdata = objFactory.createLgfData();
					
		lgfdata.setHeader(header);
		lgfdata.setListOfIbShipments(listOfIbShipments);
		
		return lgfdata;
		
	}
	
	private bbr.b2b.logistic.xml.lib_appointment.LgfData doCreateDatingMessageLgf(DatingDataToMessageDTO[] datingdatatomessageArr) throws OperationFailedException, NotFoundException {
				
		LocalDateTime now = LocalDateTime.now();
		XMLGregorianCalendar xmlDateTimeNow = null;
		
		DateTimeFormatter msgDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		
		try {
			xmlDateTimeNow = DatatypeFactory.newInstance().newXMLGregorianCalendar(now.format(msgDateFormat));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		// Se construye el xml para enviar a LOGFIRE
		bbr.b2b.logistic.xml.lib_appointment.ObjectFactory objFactory = new bbr.b2b.logistic.xml.lib_appointment.ObjectFactory();
		// Se crean los datos del encabezado LogFire
		bbr.b2b.logistic.xml.lib_appointment.LgfData.Header header = objFactory.createLgfDataHeader();
		
		header.setDocumentVersion(DAT_LGF_VERSION); // Versión Actual de LogFire
		header.setOriginSystem(DAT_ORIGIN_SYSTEM);
		header.setClientEnvCode(DAT_LGF_CLIENT_ENV_CODE); // Ambiente LogFire
		header.setParentCompanyCode(DAT_LGF_PARENT_COMPANY_CODE); // Valor fijo: CAD
		header.setEntity(DAT_LGF_ENTITY); // Nombre de la interfaz en LogFire
		header.setTimeStamp(xmlDateTimeNow); // Fecha y hora de envío del mensaje
		header.setMessageId(DAT_PREFIX_FILE + "_" + datingdatatomessageArr[0].getDvrdeliverynumber()); // Nombre del mensaje

		/////////////////////////////////////////////////////////////////////////////////
		// Cabcera de la cita

		
		List<bbr.b2b.logistic.xml.lib_appointment.Appointment> appointmentList = new ArrayList<bbr.b2b.logistic.xml.lib_appointment.Appointment>();
		
		// Itera sobre citas informadas
		for(DatingDataToMessageDTO datingdatatomessage : datingdatatomessageArr) {
			bbr.b2b.logistic.xml.lib_appointment.Appointment appointment= objFactory.createAppointment();
			
			// Formato de la fecha de la cita
			XMLGregorianCalendar xmlReserveStart = null;
		
			try {
				xmlReserveStart = DatatypeFactory.newInstance().newXMLGregorianCalendar(datingdatatomessage.getReservestart().format(DateTimeFormatter.ISO_DATE_TIME));
			} catch (DatatypeConfigurationException e) {
				e.printStackTrace();
				return null;
			}
			
			appointment.setFacilityCode(datingdatatomessage.getDeliverylocationcode());
			appointment.setCompanyCode("HITES");
			appointment.setApptNbr(datingdatatomessage.getDvrdeliverynumber().toString());
			appointment.setLoadNbr("L" + datingdatatomessage.getDvrdeliverynumber());
			appointment.setDockType(DAT_DOCK_VALUE);
			appointment.setActionCode(datingdatatomessage.getAction());
			//appointment.setPreferredDockNbr("");
			appointment.setPlannedStartTs(xmlReserveStart);
			appointment.setDuration(BigInteger.valueOf(datingdatatomessage.getReserveminutes()));
			appointment.setEstimatedUnits(BigDecimal.valueOf(datingdatatomessage.getEstimatedunits()).toBigInteger());
			//appointment.setCarrierInfo("");
			//appointment.setTrailerNbr("");
			//appointment.setLoadType("");

			// Agrega cita al listado
			appointmentList.add(appointment);
		}
		
		// Lista de citas
		ListOfAppointments listOfAppointments = objFactory.createLgfDataListOfAppointments();
		listOfAppointments.getAppointment().addAll(appointmentList);
		
		
		// Se crean los datos de LOGFIRE
		bbr.b2b.logistic.xml.lib_appointment.LgfData lgfdata = objFactory.createLgfData();
		
		lgfdata.setHeader(header);
		lgfdata.setListOfAppointments(listOfAppointments);
		
		return  lgfdata;
		
		
	}
	
	
	private void doBackUpMessage(String content, String number, String msgType) {

		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try {
			executor.submit(new BackUpUtils(content, number, msgType));
		} catch (RejectedExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	// Envia mensaje ASN vía WS
	public WSResultDTO doSendMessageToWS(String xmlcontent, String messageid) throws IOException {
		
		WSResultDTO resultDTO = new WSResultDTO (); 
		
		CloseableHttpResponse response = null;

		CloseableHttpClient client = null;
		
		try {

			String responsecontent = "";
			String responseText = "";
			
			// URL para acceder al WS
			String url = LogisticConstants.getWS_ASN_URL();
			
			// Method
			String method = LogisticConstants.getWS_ASN_METHOD();
		
			// Credenciales
			String user = LogisticConstants.getWS_ASN_USER();
			String pass = LogisticConstants.getWS_ASN_PASSWORD();
			
			// Conexión
			client = null;
			
			try{
				client = HttpClients.custom()
		                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build(), NoopHostnameVerifier.INSTANCE))
		                .build();				
			}catch (Exception e) {
				e.printStackTrace();
			}	
			
			// Datos a enviar
			List<NameValuePair> data = new ArrayList<NameValuePair>();	
			data.add(new BasicNameValuePair("xml_data", xmlcontent));
			
			HttpEntity entity = new UrlEncodedFormEntity(data);
			
			// Seteando parámentros de autenticación
			String usernameAndPassword = user + ":" + pass;
			String authorizationHeaderName = "Authorization";
			String authorizationHeaderValue = "Basic " + new String(Base64.encodeBase64(usernameAndPassword.getBytes()));

			HttpPost httpPost = new HttpPost(url + "/" + method + "/" );
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			httpPost.addHeader(authorizationHeaderName, authorizationHeaderValue);
			httpPost.setEntity(entity);
			
			// Configuración de proxy
			if(LogisticConstants.getHttpProxy()) {
				// Proxy
				String proxyIP = LogisticConstants.getHttpProxyIP();
				Integer proxyPort = LogisticConstants.getHttpProxyPort();
				HttpHost proxyHost = new HttpHost(proxyIP, proxyPort, "http");
				
				// Set the proxy and build a RequestConfig object
				RequestConfig.Builder reqconfigconbuilder= RequestConfig.custom();
				reqconfigconbuilder = reqconfigconbuilder.setProxy(proxyHost);
				RequestConfig config = reqconfigconbuilder.build();
				
				// Agrega configuración del proxy
				httpPost.setConfig(config);	
			}
			
			
			// Log de llamada
			loggerWS.info("[INPUT " + messageid + "] " + xmlcontent);
			// Ejecuta llamada
			response = client.execute(httpPost);
			
			InputStream contentStream = response.getEntity().getContent();
			
			try {
				// Obtiene respuesta del WS
				StringBuilder textBuilder = new StringBuilder();
				Reader reader = new BufferedReader(new InputStreamReader(contentStream, Charset.forName(StandardCharsets.UTF_8.name())));
				int c = 0;
				while ((c = reader.read()) != -1) {
					textBuilder.append((char) c);
				}
				responsecontent = textBuilder.toString();
				

				String[] contentTags1 = null;
				String[] contentTags2 = null;
				
				// Se obtienen los tags de respuesta 
				// - ResponseText
				contentTags1 = responsecontent.split("<message>");
				contentTags2 = contentTags1[1].split("</message>");
				responseText = contentTags2[0].trim();
				
				// - success
				contentTags1 = responsecontent.split("<success>");
				contentTags2 = contentTags1[1].split("</success>");
				String success = contentTags2[0].trim();
				
				// Almacena valores informados por WS
				resultDTO.setWscode(String.valueOf(response.getStatusLine().getStatusCode()));
				resultDTO.setResponseText(responseText);
				resultDTO.setSuccess(Boolean.parseBoolean(success));
				
			} catch (Exception e) {
				e.printStackTrace();
				responseText = "No se pudo obtener la respuesta del WS (formato erróneo)";
				e.printStackTrace();
				resultDTO.setWscode(String.valueOf(response.getStatusLine().getStatusCode()));
				resultDTO.setResponseText(responseText);
				resultDTO.setSuccess(false);
			}
			
			loggerWS.info("[OUTPUT " +  messageid + "] " + responsecontent);
			
		} catch (Exception e) {
			e.printStackTrace();
 			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7000");
 			
 			
		} finally {
			if (response != null) {
				response.close();
			}
			if (client != null) {
				client.close();
			}
		}
		
		return resultDTO;
		
	}*/
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	// Obtiene parámetros de dias inactivos para solicitud de cita
	public InactiveDaysResultDTO getInactiveDaysToDatingRequest(){
		InactiveDaysResultDTO resultDTO = new InactiveDaysResultDTO();
		List<DayOfWeek> dowList = new ArrayList<DayOfWeek>(); 
		
		try{
			// Obtiene los dias inactivos en la tabla de parámentros con código "DIA_INHABIL"
			PropertyInfoDTO[] props = new PropertyInfoDTO[2]; 
			props[0] = new PropertyInfoDTO("code", "code", "DIA_INHABIL");
			props[1] = new PropertyInfoDTO("active", "active", true);
			ParameterW[] parameterArr = parameterserver.getByPropertiesAsArray(props);
			
			if(parameterArr.length > 0){
				// Por definición, los valores estpan separados por pipe '|'
				ParameterW parameterW = parameterArr[0];
				
				String[] values = parameterW.getValue().split("\\|");
				for(int i=0; i<values.length; i++) {
					dowList.add(daysMapping.get(values[i]));
				}
				
				DayOfWeek[] dowArr = dowList.toArray(new DayOfWeek[dowList.size()]);
				
				resultDTO.setInactivedays(dowArr);
			}
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L8500");
		}
		
	}

	
	public TimeModuleReportDataDTO getTimeModuleReportData(){
		TimeModuleReportDataDTO resultDTO = new TimeModuleReportDataDTO();
		
		try{
			OrderCriteriaDTO ordercriteria = new OrderCriteriaDTO();
			ordercriteria.setPropertyname("visualorder");
			ordercriteria.setAscending(true);
			
			TimeModuleW[] timemoduleArr = timemoduleserver.getAllAsArrayOrdered(ordercriteria);
			
			if (timemoduleArr == null || timemoduleArr.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L800");
			}

			TimeModuleDTO[] timemoduledtos = new TimeModuleDTO[timemoduleArr.length];
			BeanExtenderFactory.copyProperties(timemoduleArr, timemoduledtos, TimeModuleDTO.class);

			resultDTO.setTimemodule(timemoduledtos);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}
	
	public DvrDeliveryStateTypeResultDTO getDvrDeliveryStateTypes(){
		DvrDeliveryStateTypeResultDTO resultDTO = new DvrDeliveryStateTypeResultDTO();

		try {
			// Obtiene estados visibles
			DvrDeliveryStateTypeW[] dvrdeliverystatetypes = dvrdeliverystatetypeserver.getByPropertyAsArray("visible", true);
			if (dvrdeliverystatetypes == null || dvrdeliverystatetypes.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2250");
			}

			DvrDeliveryStateTypeDataDTO[] dvrdeliverystatetypedtos = new DvrDeliveryStateTypeDataDTO[dvrdeliverystatetypes.length];
			BeanExtenderFactory.copyProperties(dvrdeliverystatetypes, dvrdeliverystatetypedtos, DvrDeliveryStateTypeDataDTO.class);

			resultDTO.setDvrdeliverystatetypedata(dvrdeliverystatetypedtos);
			return resultDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	

	
	
	// Validación de datos previos para la solicitud de cita	
	public BaseResultsDTO doCheckDataToDatingRequest(CheckDataToDatingRequestInitParamDTO initParamDTO, Boolean byDelivery){
		
		BaseResultsDTO resultDTO = new BaseResultsDTO();
		List<BaseResultDTO> errorList = new ArrayList<BaseResultDTO>();
		BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
		String errorMsg = "";
		
		LocalDate now = LocalDate.now();
		
		Boolean ocDvhTypeBool = false; 
		
		try {
			// Tipo de OC DVH
			OrderTypeW dvhTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH"); 
			
			// Estados de OC no vigentes			
			DvrOrderStateTypeW[] nonValidDvrOCSt = dvrorderstatetypeserver.getByPropertyAsArray("valid", false);
			
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1800");
			}
			VendorW vendorW = vendorArr[0];
			
			// Almacena las órdenes
			DvrOrderW[] dvrorderArr;
			
			// Local de entrega
			Long deliverylocationid = -1L;
			
			// Validaciones para solicitud de cita desde OC
			if (!byDelivery) {
				// Obtiene las órdenes
				dvrorderArr = dvrorderserver.getDvrOrderByIds(initParamDTO.getDvrorderids());
				
				// Si no existen órdenes, termina
				if(dvrorderArr.length == 0) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
				}

				// Busca si las OC informadas son del tipo DVH
				Long[] dvhorderids = Arrays.stream(dvrorderArr).filter(oc -> oc.getOrdertypeid().equals(dvhTypeW.getId()))
																.map(oc->oc.getId()).toArray(Long[]::new);
				
				// Las OC seleccionadas son del tipo DVH
				if(dvhorderids.length > 0) {
					if(dvhorderids.length != dvrorderArr.length) {
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1930");
					}
					ocDvhTypeBool = true;	
				}
				
				// Id de órdenes existentes
				Long[] dvrordersArrIds = Arrays.stream(dvrorderArr).map(oc -> oc.getId()).toArray(Long[]::new); 
								
				// Valida que las OC informadas estén asociadas al proveedor seleccionado
				DvrOrderW[] orderNonVendorArr = Arrays.stream(dvrorderArr).filter(oc -> !oc.getVendorid().equals(vendorW.getId())).toArray(DvrOrderW[]::new);
				
				for (DvrOrderW orderNonVendor : orderNonVendorArr) {
					errorMsg = "La orden " + orderNonVendor.getNumber() + " no corresponde con el proveedor seleccionado en el filtro."; 
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// El sistema valida que las órdenes seleccionadas tienen el mismo lugar de entrega
				Long[] deliverylocationids = Arrays.stream(dvrorderArr).map(oc -> oc.getDeliverylocationid()).distinct().toArray(Long[]::new);
				if (deliverylocationids.length == 0) {
					errorMsg = "No se informa local de entrega";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				else if (deliverylocationids.length == 1) {
					deliverylocationid = deliverylocationids[0];
				}
				else if (deliverylocationids.length > 1) {
					errorMsg = "Las órdenes deben tener el mismo lugar de entrega";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// El sistema valida que las órdenes seleccionadas correspondan al mismo proveedor
				Long[] vendorIds = Arrays.stream(dvrorderArr).map(oc -> oc.getVendorid()).distinct().toArray(Long[]::new);
				if (vendorIds.length > 1) {
					errorMsg = "Las órdenes deben pertenecer al mismo proveedor";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// El sistema valida que las órdenes seleccionadas correspondan al mismo comprador
				Long[] retailerIds = Arrays.stream(dvrorderArr).map(oc -> oc.getRetailerid()).distinct().toArray(Long[]::new);
				if (retailerIds.length > 1) {
					errorMsg = "Las órdenes deben pertenecer al mismo comprador";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// El sistema valida que las órdenes sean del mismo tipo.
				OrderTypeW[] orderTypeArr = ordertypeserver.getOrderTypeByDvrOrders(dvrordersArrIds); 
				if (orderTypeArr.length > 1) {
					errorMsg = "Las órdenes deben corresponder al mismo tipo";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
			}			
			
			// Llamada desde despacho
			// JPE 20200312
			else {
				// Obtiene la entrega
				DvrDeliveryW dvrDelivery = dvrdeliveryserver.getById(initParamDTO.getDvrdeliveryid());
				if (dvrDelivery == null) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
				}
				
				// Valida que la entrega se encuentre en estado 'Re-Agendar'
				DvrDeliveryStateTypeW dstReschedule = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
				if (!dvrDelivery.getCurrentstatetypeid().equals(dstReschedule.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2211");
				}

				// Obtiene las órdenes asociadas
				DvrOrderDeliveryW[] dvrOrderDeliveries = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", dvrDelivery.getId());
				Long[] dvrOrderIds = Arrays.stream(dvrOrderDeliveries).map(oc->oc.getDvrorderid()).toArray(Long[]::new);
				dvrorderArr = dvrorderserver.getDvrOrderByIds(dvrOrderIds);
				
				// Obtiene local de entrega
				deliverylocationid = dvrDelivery.getDeliverylocationid();
			}
			
			// El sistema valida que las órdenes de compra seleccionadas estén en estado vigente
			// Busca las órdenes que están en estados no vigentes
			DvrOrderW[] nonValidStOrderArr = Arrays.stream(dvrorderArr)
							.filter(oc -> Arrays.stream(nonValidDvrOCSt).anyMatch(st -> st.getId().equals(oc.getCurrentstatetypeid())))
							.toArray(DvrOrderW[]::new);
			
			for (int i = 0; i < nonValidStOrderArr.length; i++) {
				errorMsg = "La orden " + nonValidStOrderArr[i].getNumber() + " no se encuentra en un estado vigente"; 
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}			
			
			// Si existen errores, se termina
			if (errorList.size() > 0) {
				Arrays.sort(errorList.toArray(new BaseResultDTO[errorList.size()]), comparator);
				resultDTO.setBaseresults(errorList.toArray(new BaseResultDTO[errorList.size()]));
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2200");
			}
			
			// El sistema valida que el lugar de entrega esté configurado como bodega
			LocationW deliverylocationW = locationserver.getById(deliverylocationid);
				
			if (!deliverylocationW.getWarehouse()) {
				errorMsg = "El lugar de entrega no se encuentra habilitado para solicitar cita";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Fechas para validaciones
//			LocalDate minExpiratioDate = LocalDate.MAX;
//			LocalDate maxDeliveryDate = LocalDate.MIN;			
			
			// Validaciones sobre OC
			for (DvrOrderW dvrOrderW : dvrorderArr) {				
				if (!byDelivery) {
					// El sistema valida que cada OC tenga unidades pendientes por despachar
					if(dvrOrderW.getPendingunits() <= 0.0){
						errorMsg = "La orden " + dvrOrderW.getNumber() + " no presenta unidades pendientes por entregar";
						errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
					}
				}
							
				// El sistema valida que cada orden tenga fecha de vencimiento igual o posterior a la fecha actual
				LocalDate expirationdate = dvrOrderW.getExpiration().toLocalDate();
				if (expirationdate.isBefore(now)) {
					errorMsg = "La orden " + dvrOrderW.getNumber() + " venció";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}				

				// Almacena la menor fecha de vencimiento
//				if (expirationdate.isBefore(minExpiratioDate)) {
//					minExpiratioDate = expirationdate; 
//				}
			
				// Almacena la mayor fecha de entrega
//				LocalDate deliverydate = dvrOrderW.getDeliverydate().toLocalDate();
//				if (deliverydate.isAfter(maxDeliveryDate)) {
//					maxDeliveryDate = deliverydate; 
//				}

			}
			
			// El sistema valida que la fecha de entrega mayor entre las órdenes de compra seleccionadas, sea menor o igual que la menor fecha de vencimiento de las demás órdenes
//			if (maxDeliveryDate.isAfter(minExpiratioDate)) {
//				errorMsg = "Las órdenes de compra seleccionadas no coinciden en el rango entre fecha de entrega y vencimiento.";
//				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));				
//			}
			
			// El sistema valida que no existan otras OC DVH del proveedor que se encuentren en estados vigentes, 
			// con fecha de reprogramación anterior al día actual y con cantidad pendiente mayor a cero.
			
			if(ocDvhTypeBool) {
				// Busca todas las otras OC DVH vigentes del proveedor
				DvhOrderReportDataDTO[] dvhReportValidOrdersArr = dvrorderserver.getDvhOrdersByVendorAndCriterium(vendorW.getId(), -1L, null, null, null, null, 0, 0, 0, null, false);
				
				// Filtra por:
				// - OC distintas a las seleccioandas
				// - Fecha de reprogramación < hoy 
				// - pendiente > 0
				Long[] dvhorderids = Arrays.stream(dvhReportValidOrdersArr)
														.filter(ocr -> Arrays.stream(dvrorderArr)
																		.noneMatch(oc -> oc.getId().equals(ocr.getDvrorderid()))
																&& (ocr.getReschedulingdate().toLocalDate().isBefore(LocalDate.now()))
																&& (ocr.getPendingunits() > 0))
														.map(oc-> oc.getDvrorderid())
														.toArray(Long[]::new);
				
				if(dvhorderids.length > 0) {
					errorMsg = "No puede solicitar cita de cualquier OC DVH si aún tiene otras que se encuentren vigentes, pendientes de reprogramar y con artículos sin despachar.";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));	
				}
				
			}
			
			
			// No puede solicitar cita de cualquier OC DVH si aún tiene otras que se encuentren vigentes, pendientes de reprogramar 
			// y con artículos sin despachar.
			
			
			// Si existen errores, se termina
			if (errorList.size() > 0) {
				Arrays.sort(errorList.toArray(new BaseResultDTO[errorList.size()]), comparator);
				resultDTO.setBaseresults(errorList.toArray(new BaseResultDTO[errorList.size()]));
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2200");
			}
						
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	// Solicita Sita
	public DatingRequestResultDTO doAddDatingRequest(DatingRequestInitParamDTO initParamDTO, UserLogDataDTO userdataDTO, Boolean byDelivery){
		
		DatingRequestResultDTO resultDTO = new DatingRequestResultDTO();
		
		try {
			// Estados de órdenes
			DvrOrderStateTypeW dvrocLibSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
			
			// Estados de entrega
			DvrDeliveryStateTypeW delReqSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DvrDeliveryStateTypeW recSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
			
			// Estados de despacho válidos
			List<DvrDeliveryStateTypeW> deliveryValidTypeList = dvrdeliverystatetypeserver.getByProperty("valid", true);
			deliveryValidTypeList.add(recSt);
						
			// Órdenes
			DvrOrderW[] dvrorderArr;

			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Validaciones de las órdenes seleccionadas
			// Si hay errores se informa y termina
			CheckDataToDatingRequestInitParamDTO validationInitParam = new CheckDataToDatingRequestInitParamDTO();
			validationInitParam.setDvrorderids(initParamDTO.getDvrorderids());
			validationInitParam.setDvrdeliveryid(initParamDTO.getDvrdeliveryid());
			validationInitParam.setVendorcode(initParamDTO.getVendorcode());
			BaseResultsDTO validationResultDTO = doCheckDataToDatingRequest(validationInitParam, byDelivery);
			if (!validationResultDTO.getStatuscode().equals("0")) {
				resultDTO.setValidationerrors(validationResultDTO.getBaseresults());
				resultDTO.setStatuscode(validationResultDTO.getStatuscode());
				resultDTO.setStatusmessage(validationResultDTO.getStatusmessage());
				return resultDTO;
			}
						
			// Solicitud desde OC 
			if (!byDelivery) {
				dvrorderArr = dvrorderserver.getDvrOrderByIdsAndVendor(initParamDTO.getDvrorderids(), vendorW.getId());
			}
			
			// Solicitud desde Despacho
			else {
				// Obtiene órdenes asociadas
				DvrOrderDeliveryW[] dvrorderdeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", initParamDTO.getDvrdeliveryid());
				Long[] dvrorderids = Arrays.stream(dvrorderdeliveryArr).map(oc->oc.getDvrorderid()).toArray(Long[]::new);
				dvrorderArr = dvrorderserver.getDvrOrderByIds(dvrorderids);
			}
			
			// Local de entrega
			LocationW deliveryLocationW = locationserver.getById(dvrorderArr[0].getDeliverylocationid());
			
			List<BaseResultDTO> errorList = new ArrayList<BaseResultDTO>();
			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
			String errorMsg = "";
			
			// Se validan campos obligatorios
			// - Solicitante
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getRequestername() == null || initParamDTO.getDatingrequestdata().getRequestername().length() == 0) {
				errorMsg = "Campo solicitante es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que campo no tenga largo mayor a 50 caracteres
			else if (initParamDTO.getDatingrequestdata().getRequestername().length() > 50) {
				errorMsg = "En campo 'Solicitante', se debe ingresar un valor no mayor a 50 caracteres";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - E-Mail
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getEmail() == null || initParamDTO.getDatingrequestdata().getEmail().length() == 0) {
				errorMsg = "Campo correo es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que se informe un correo válido y de largo no mayor a 50
			else if (EmailValidation.isValidWithUppercase(initParamDTO.getDatingrequestdata().getEmail()) && initParamDTO.getDatingrequestdata().getEmail().length() > 50) {
				errorMsg = "Se debe ingresar un correo válido y no mayor a 50 caracteres";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - Teléfono
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getPhone() == null || initParamDTO.getDatingrequestdata().getPhone().length() == 0) {
				errorMsg = "Campo Número de teléfono es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que número de teléfono no sea mayor a 20 caracteres 
			else if (initParamDTO.getDatingrequestdata().getPhone().length() > 20) {
				errorMsg = "Se debe ingresar un número de teléfono no mayor a 20 caracteres";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - N° Camiones
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getTrucks() == null) {
				errorMsg = "Campo Número de Camiones es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que la cantidad de camiones sea mayor a cero y menor a 100 
			else if (initParamDTO.getDatingrequestdata().getTrucks() <= 0 || initParamDTO.getDatingrequestdata().getTrucks() > 99) {
				errorMsg = "La cantidad de camiones debe ser mayor a cero, con un máximo de 2 dígitos";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - Bultos Estimados
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getBulks() == null) {
				errorMsg = "Campo Bultos Estimados es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que bultos estimados  sea mayor a cero y menor a 10000 
			else if (initParamDTO.getDatingrequestdata().getBulks() <= 0 || initParamDTO.getDatingrequestdata().getBulks() > 9999) {
				errorMsg = "La cantidad de bultos estimados debe ser mayor a cero, con un máximo de 4 dígitos";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - Tiempo Estimado Descarga
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getEstimatedtime() == null) {
				errorMsg = "Campo Timepo estimado de descarga es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que tiempo estimado de descarga sea mayor a cero y menor a 100 
			else if (initParamDTO.getDatingrequestdata().getEstimatedtime() <= 0 || initParamDTO.getDatingrequestdata().getEstimatedtime() > 99) {
				errorMsg = "El tiempo estimado de descarga debe ser mayor a cero, con un máximo de 4 dígitos";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// - Volumen Estimado
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getEstimatedvolume() == null) {
				errorMsg = "Campo Volumen estimado es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Valida que el volumen estimado sea mayor a cero y menor a 100
			else if (initParamDTO.getDatingrequestdata().getEstimatedvolume() <= 0 || initParamDTO.getDatingrequestdata().getEstimatedvolume() > 99) {
				errorMsg = "El volumen estimado debe ser mayor a cero, con un máximo de 4 dígitos";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}

			// - Módulo Horario
			// Valida que campo se informe
			TimeModuleW timemoduleW = new TimeModuleW(); 
			if (initParamDTO.getDatingrequestdata().getNeedmodule() == null || initParamDTO.getDatingrequestdata().getNeedmodule().length() == 0) {
				errorMsg = "Campo Módulo horario es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			// Valida que valor informado esté configurado en el sistema 
			else {
				TimeModuleW[] timemoduleArr = timemoduleserver.getByPropertyAsArray("description", initParamDTO.getDatingrequestdata().getNeedmodule());	
				if (timemoduleArr.length == 0) {
					errorMsg = "El valor para módulo horario no está configurado en el sistema";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				else {
					timemoduleW = timemoduleArr[0];
				}				
			}

			// Fecha Deseada
			// Valida que campo se informe
			if (initParamDTO.getDatingrequestdata().getRequestdate() == null) {
				errorMsg = "Campo Fecha deseada horario es obligatorio";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			else{
				// - Validación de fecha requerida
				// Obtener los días hacia adelante
				DaysToDatingRequestResultDTO daystodatingrequestdto = getDaystoDatingRequest(); 
				if (daystodatingrequestdto.getDaysamount() == null) {
					errorMsg = "Parámetro Días de Solicitud de Cita no definido";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
				Integer daysfordward = daystodatingrequestdto.getDaysamount();
				
				// Obtener días inhábiles
				InactiveDaysResultDTO inactivedaysdto = getInactiveDaysToDatingRequest();
				if (inactivedaysdto.getInactivedays().length == 0) {
					errorMsg = "Parámetro Días Inhábiles no definido";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
				DayOfWeek[] inactiveDaysArr = inactivedaysdto.getInactivedays();
				
				// Se eligió una "Fecha Deseada" anterior a X días con respecto a la fecha actual
				Boolean validDateDaysForward;
				try {
					validDateDaysForward = isValidDateDaysFordward(initParamDTO.getDatingrequestdata().getRequestdate().toLocalDate(), daysfordward, inactiveDaysArr);				
				} catch (OperationFailedException e) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", e.getMessage(), false);
				}
 
				if (!validDateDaysForward) {
					errorMsg = "La fecha ingresada debe corresponder a partir de " + daysfordward + " días hábiles hacia adelante con respecto a hoy";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// Se eligió una "Fecha Deseada" para un día configurado como inhábil
				Boolean validDateActiveDays;
				try {
					validDateActiveDays = isValidDateActiveDay(initParamDTO.getDatingrequestdata().getRequestdate().toLocalDate(), inactiveDaysArr);
				} catch (OperationFailedException e) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", e.getMessage(), false);
				}
				
				if (!validDateActiveDays) {
					errorMsg = "La fecha ingresada corresponde a un día en que no se puede solicitar cita";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}				
			}
			
			// Campo comentario (opcional)
			// Se debe ingresar un comentario no mayor a 200 caracteres
			if (initParamDTO.getDatingrequestdata().getComment() != null && initParamDTO.getDatingrequestdata().getComment().length() > 200) {
				errorMsg = "Se debe ingresar un comentario no mayor a 200 caracteres";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}

			// Si existen errores se termina
			if (errorList.size() > 0) {
				Arrays.sort(errorList.toArray(new BaseResultDTO[errorList.size()]), comparator);
				resultDTO.setValidationerrors(errorList.toArray(new BaseResultDTO[errorList.size()]));
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2200");
			}

			// Se crea la Solicitud de Cita
			
			LocalDateTime nowDateTime = LocalDateTime.now(); 
			DvrDeliveryW dvrdeliveryW;
			
			// Solicitud desde OC
			if (!byDelivery) {
				// El sistema crea una nueva entrega en estado 'Cita Solicitada' con detalle basado en las OC y la pre-distribución de cada una. 
				// Las cantidades comprometidas en la entrega corresponderán a las cantidades pendientes que registre cada OC en ese momento.
				dvrdeliveryW = new DvrDeliveryW();
				dvrdeliveryW.setCreated(nowDateTime);
				dvrdeliveryW.setCurrentstatetypedate(nowDateTime);
				dvrdeliveryW.setCurrentstatetypeid(delReqSt.getId());
				dvrdeliveryW.setDeliverylocationid(deliveryLocationW.getId());
				dvrdeliveryW.setNumber(dvrdeliveryserver.getNextSequenceDvrDeliveryNumber());
				dvrdeliveryW.setVendorid(vendorW.getId());
				dvrdeliveryW = dvrdeliveryserver.addDvrDelivery(dvrdeliveryW);
				
				// Crea despachos
				for (DvrOrderW dvrorderW : dvrorderArr) {					
					DvrOrderDeliveryW dvrorderdeliveryW = new DvrOrderDeliveryW();  
					dvrorderdeliveryW.setDvrorderid(dvrorderW.getId());
					dvrorderdeliveryW.setDvrdeliveryid(dvrdeliveryW.getId());
					dvrorderdeliveryW.setClosed(false);
					dvrorderdeliveryW.setEstimatedunits(dvrorderW.getPendingunits());
					
					dvrorderdeliveryW = dvrorderdeliveryserver.addDvrOrderDelivery(dvrorderdeliveryW);
										
					// Detalle de despacho
					// Obtiene Predistribución de la OC con pendiente > 0 
					PropertyInfoDTO[] propPred = new PropertyInfoDTO[2];
					propPred[0] = new PropertyInfoDTO("dvrorderdetail.dvrorder.id", "dvrorderid", dvrorderW.getId());
					propPred[1] = new PropertyInfoDTO("pendingunits", "pendingunits", 0.0, ComparisonOperator.GREATER_THAN); 
					DvrPreDeliveryDetailW[] dvrpredeliverydetailArr = dvrpredeliverydetailserver.getByPropertiesAsArray(propPred);

					for (DvrPreDeliveryDetailW dvrpredeliverydetailW : dvrpredeliverydetailArr) {
						DvrOrderDeliveryDetailW dvrorderdeliverydetailW = new DvrOrderDeliveryDetailW();
						dvrorderdeliverydetailW.setDvrorderid(dvrpredeliverydetailW.getDvrorderid());
						dvrorderdeliverydetailW.setDvrdeliveryid(dvrdeliveryW.getId());
						dvrorderdeliverydetailW.setItemid(dvrpredeliverydetailW.getItemid());
						dvrorderdeliverydetailW.setLocationid(dvrpredeliverydetailW.getLocationid());
						dvrorderdeliverydetailW.setPosition(dvrpredeliverydetailW.getPosition());
						dvrorderdeliverydetailW.setAvailableunits(dvrpredeliverydetailW.getPendingunits());
						dvrorderdeliverydetailW.setPendingunits(dvrpredeliverydetailW.getPendingunits());
						dvrorderdeliverydetailW.setReceivedunits(0.0);
						
						dvrorderdeliverydetailW = dvrorderdeliverydetailserver.addDvrOrderDeliveryDetail(dvrorderdeliverydetailW);						
					}					
				}
			}

			// Solicitud desde despacho
			else {
				// Actualiza despacho
				dvrdeliveryW = dvrdeliveryserver.getById(initParamDTO.getDvrdeliveryid());
				dvrdeliveryW.setCurrentstatetypedate(nowDateTime);
				dvrdeliveryW.setCurrentstatetypeid(delReqSt.getId());
				dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);
			}
			
			// DeliveryState
			DvrDeliveryStateW  dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setComment("");
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(delReqSt.getId());
			dvrdeliverystateW.setUser(userdataDTO.getUsername());
			dvrdeliverystateW.setUsertype(userdataDTO.getUsertype());
			dvrdeliverystateW.setWhen(nowDateTime);
			dvrdeliverystateW = dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);	 
						
			// El sistema crea una Solicitud de Cita al lugar de entrega de las OC y con los datos ingresados en el formulario.
			DatingRequestW datingrequestW = new DatingRequestW();
			datingrequestW.setComment(initParamDTO.getDatingrequestdata().getComment());
			datingrequestW.setDvrdeliveryid(dvrdeliveryW.getId());
			datingrequestW.setEmail(initParamDTO.getDatingrequestdata().getEmail());
			datingrequestW.setEstimatedbulks(initParamDTO.getDatingrequestdata().getBulks());
			datingrequestW.setEstimatedtime(initParamDTO.getDatingrequestdata().getEstimatedtime());
			datingrequestW.setEstimatedvolume(initParamDTO.getDatingrequestdata().getEstimatedvolume());
			datingrequestW.setNeedmodule(timemoduleW.getDescription());
			datingrequestW.setPhone(initParamDTO.getDatingrequestdata().getPhone());
			datingrequestW.setRequestdate(initParamDTO.getDatingrequestdata().getRequestdate().toLocalDate().atStartOfDay());
			datingrequestW.setRequester(initParamDTO.getDatingrequestdata().getRequestername());
			datingrequestW.setTrucks(initParamDTO.getDatingrequestdata().getTrucks());
			datingrequestW.setWhen(nowDateTime);
			
			datingrequestW = datingrequestserver.addDatingRequest(datingrequestW);

			// El sistema valida que existan OC en estado 'Liberada' o 'Modificada' 
			// -> Acepta las órdenes independiente del tipo de usuario
			for (DvrOrderW dvrorderW : dvrorderArr) {				
				if (dvrorderW.getCurrentstatetypeid().equals(dvrocLibSt.getId())) {
					// Acepta OC DVR
					String userInfo = userdataDTO.getUsername() + " - " +  userdataDTO.getUsercode();
					buyorderreportmanagerlocal.doAcceptDvrOrder(dvrorderW.getId(), userInfo, userdataDTO.getUsertype());					
				}
			}
			
			// Calcula totales
			Long[] dvrorderids = Arrays.stream(dvrorderArr).map(oc->oc.getId()).toArray(Long[]::new);
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrorderids);
			
			// Estado actual de la entrega
			DvrDeliveryStateTypeDataDTO currentstatetypeDTO = new DvrDeliveryStateTypeDataDTO();
			BeanExtenderFactory.copyProperties(delReqSt, currentstatetypeDTO);
			
			resultDTO.setCurrentstatetypeDTO(currentstatetypeDTO);
			resultDTO.setDeliverylocation(deliveryLocationW.getName());
			resultDTO.setDeliverynumber(dvrdeliveryW.getNumber());
			
			// Envía un correo electrónico a Hites notificando la solicitud de cita
			String subject = "B2B Hites - Solicitud de cita para entrega No. " + dvrdeliveryW.getNumber();			
			String mailSender = LogisticConstants.getMailSender();
			String mailSession = LogisticConstants.getMAIL_SESSION();
			String[] mailReceiver = LogisticConstants.getHITES_DATINGREQUEST_MAIL();
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			
			if (mailReceiver[0] != null && !mailReceiver[0].equals("")) {
				String msgtext =
						"Estimado:<br><br>" + //
						"El presente tiene como objetivo informarle que el proveedor " + vendorW.getName() + " de RUT " + vendorW.getCode() + //
						" ha solicitado una cita para la entrega No. " + dvrdeliveryW.getNumber() + ".<br><br>" + //
						"<b>Día deseado:</b> " + df.format(datingrequestW.getRequestdate()) + "<br><br>" + //
						"<b>Local de entrega:</b> " + deliveryLocationW.getCode() + " " + deliveryLocationW.getName() + "<br><br>" + //
						"Comentario: " + datingrequestW.getComment() + "<br><br>" + //
						"Le rogamos seguir con el procedimiento definido para la asignación o el rechazo de dicha solicitud.<br>" + //
						"Sin otro particular le saluda atentamente,<br>" + //
						"B2B Hites<br><br>" + //
						"<b>Favor no responder este correo dado que es generado de manera automática por el sistema B2B.<b>";
				
				// Envía correo a tabla de correos pendientes
				schedulermailmanager.doAddMailToQueue(mailSender, mailReceiver, null, null, subject, msgtext, mailSession, "SOLICITUD_CITA", dvrdeliveryW.getNumber().toString());		
			}
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	// Reporte de Entregas
	public DvrDeliveryReportResultDTO getDvrDeliveryReportData(DvrDeliveryReportInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		DvrDeliveryReportResultDTO resultDTO = new DvrDeliveryReportResultDTO();
		DvrDeliveryReportDataDTO[] dvrdeliveryreportdata;
		int total;
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			// Datos
			dvrdeliveryreportdata = dvrdeliveryserver.getDvrDeliveryReport(vendorW.getId(), initParamDTO.getSearchvalue(), initParamDTO.getDvrdeliverystatetypeid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated, initParamDTO.getFiltertype());

			if(withTotals){
				total = dvrdeliveryserver.getCountDvrDeliveryReport(vendorW.getId(), initParamDTO.getSearchvalue(), initParamDTO.getDvrdeliverystatetypeid(), initParamDTO.getFiltertype());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrdeliveryreportdata.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setDvrdeliveryreportdata(dvrdeliveryreportdata);		
			return resultDTO;

	
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
	}
	
	public DvrDeliveryReportResultDTO getDvrDeliveryReportDataById(DvrDeliveryIdInitParamDTO initParamDTO) {
		DvrDeliveryReportResultDTO resultDTO = new DvrDeliveryReportResultDTO();
				
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];

			DvrDeliveryReportDataDTO dvrdeliveryreportdata = dvrdeliveryserver.getDvrDeliveryReportDataById(initParamDTO.getDeliveryid(), vendorW.getId());
			DvrDeliveryReportDataDTO[] dvrdeliveryreportdataArr = { dvrdeliveryreportdata };

			resultDTO.setDvrdeliveryreportdata(dvrdeliveryreportdataArr);
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	// Detalle de entregas
	public DvrDeliveryDetailReportResultDTO getDvrDeliveryDetailReport(DvrDeliveryDetailInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		
		DvrDeliveryDetailReportResultDTO resultDTO = new DvrDeliveryDetailReportResultDTO();
		DvrDeliveryDetailDataDTO[] dvrdeliverydetaildata;
		DvrDeliveryDetailTotalDataDTO totalresult;
		int total;
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtiene Delivery
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2210");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Datos
			dvrdeliverydetaildata = dvrdeliveryserver.getDvrDeliveryDetailReport(vendorW.getId(), dvrdeliveryW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			

			if(withTotals){
				totalresult = dvrdeliveryserver.getCountDvrDeliveryDetailReport(vendorW.getId(), dvrdeliveryW.getId());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrdeliverydetaildata.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}
			
			resultDTO.setDvrdeliverydetaildata(dvrdeliverydetaildata);		
			return resultDTO;

			
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
	}

	public DvrDeliveryDetailExcelReportResultDTO getDvrDeliveryDetailExcelReportByDelivery(DvrDeliveryDetailInitParamDTO initParamDTO) {
		DvrDeliveryDetailExcelReportResultDTO resultDTO = new DvrDeliveryDetailExcelReportResultDTO();
		
		try {
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if (vendorArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			
			// Chequea la existencia de la entrega para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getDvrdeliveryid());
			properties[1] = new PropertyInfoDTO("vendor.dni", "vendordni", initParamDTO.getVendorcode());
			List<DvrDeliveryW> deliveriesTmp = dvrdeliveryserver.getByProperties(properties);

			if (deliveriesTmp.isEmpty()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2280");
			}
			
			// Si viene de reporte por páginas no es necesario realizar la consulta
			int totalRows = dvrorderdeliverydetailserver.countDvrDeliveryDetailExcelReportByDelivery(initParamDTO.getDvrdeliveryid());

			// Validar la cantidad de registros con el máximo permitido
			if (totalRows > LogisticConstants.getEXCEL_MAX_NUMBER_OF_ROWS()) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			resultDTO = dvrorderdeliverydetailserver.getDvrDeliveryDetailExcelReportByDelivery(initParamDTO.getDvrdeliveryid());
						
		} catch (NotFoundException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2100");
		} catch (OperationFailedException e) {
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2101");
		}
		
		return resultDTO;
	}
	
	public DvrOrderDeliveryAdjustResultDTO doAdjustDvrOrderDeliveryUnits(DvrOrderDeliveryAdjustUnitsInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		DvrOrderDeliveryAdjustResultDTO resultDTO = new DvrOrderDeliveryAdjustResultDTO();
		
		try {
			// Obtiene el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
						
			// Obtiene la entrega
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if (dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
				
			// Valida que la entrega se encuentre en estado 'Re-Agendar', 'Cita Solicitada' o 'Cita Asignada'
			DvrDeliveryStateTypeW dstReschedule = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
			DvrDeliveryStateTypeW dstRequested = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DvrDeliveryStateTypeW dstAssigned = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			
			if (!dvrdeliveryW.getCurrentstatetypeid().equals(dstReschedule.getId()) &&
					!dvrdeliveryW.getCurrentstatetypeid().equals(dstRequested.getId()) &&
						!dvrdeliveryW.getCurrentstatetypeid().equals(dstAssigned.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6003");
			}
			
			List<BaseResultDTO> errorList = new ArrayList<BaseResultDTO>(); 
			String errorMsg = "";
			
			// Órdenes informadas
			Map<Long, DvrOrderW> dvrOrderMap = new HashMap<Long, DvrOrderW>();
			// Locales informados
			Map<Long, LocationW> locationMap = new HashMap<Long, LocationW>();
			// Productos informados
			Map<Long, ItemW> itemMap = new HashMap<Long, ItemW>();
			// Detalles modificados de la entrega
			Map<DvrOrderDeliveryDetailId, Double> dvrOrderDeliveryDetailUpdatedMap = new HashMap<DvrOrderDeliveryDetailId, Double>();
			// Órdenes asociadas
			Set<Long> dvrOrderIdSet = new HashSet<Long>();
						
			// Itera sobre detalles informados
			DvrOrderW[] dvrOrderWs;
			LocationW[] locationWs;
			ItemW[] itemWs;
			DvrOrderDeliveryDetailW[] dvrOrderDeliveryDetailWs;
			DvrOrderDeliveryDetailW dvrOrderDeliveryDetailW;
			DvrPreDeliveryDetailW[] dvrPredeliveryDetailWs;
			DvrPreDeliveryDetailW dvrPredeliveryDetailW;
			DvrOrderDeliveryDetailId key;
			PropertyInfoDTO[] props;
			Double adjustDifference;
			for (DvrOrderDeliveryAdjustUnitsDetailDTO detail : initParamDTO.getDetails()){
				// Orden
				if (!dvrOrderMap.containsKey(detail.getDvrorderid())) {
					PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
					properties[0] = new PropertyInfoDTO("id", "id", detail.getDvrorderid());
					properties[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
					dvrOrderWs = dvrorderserver.getByPropertiesAsArray(properties);
					if (dvrOrderWs == null || dvrOrderWs.length == 0) {
						errorMsg = "Error obteniendo las órdenes";
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
					}
					else {
						dvrOrderMap.put(detail.getDvrorderid(), dvrOrderWs[0]);
					}
				}
				
				// Local 
				if (!locationMap.containsKey(detail.getDestinationlocationid())) {
					locationWs = locationserver.getByPropertyAsArray("id", detail.getDestinationlocationid());
					if (locationWs == null || locationWs.length == 0) {
						errorMsg = "Error obteniendo los locales";
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false); 
					}
					else {
						locationMap.put(detail.getDestinationlocationid(), locationWs[0]);
					}					
				}
				
				// Producto
				if (!itemMap.containsKey(detail.getItemid())){
					itemWs = itemserver.getByPropertyAsArray("id", detail.getItemid());
					if (itemWs == null || itemWs.length == 0) {
						errorMsg = "Error obteniendo los productos";
						return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
					}
					else {
						itemMap.put(detail.getItemid(), itemWs[0]);
					}
				}
				
				// Detalle de la predistribución asociada
				props = new PropertyInfoDTO[3] ;
				props[0] = new PropertyInfoDTO("dvrorderdetail.dvrorder.id", "dvrorderid", detail.getDvrorderid());
				props[1] = new PropertyInfoDTO("location.id", "locationid", detail.getDestinationlocationid());
				props[2] = new PropertyInfoDTO("dvrorderdetail.item.id", "itemid", detail.getItemid());
				dvrPredeliveryDetailWs = dvrpredeliverydetailserver.getByPropertiesAsArray(props) ;
				
				if (dvrPredeliveryDetailWs == null || dvrPredeliveryDetailWs.length == 0) {
					errorMsg = "La combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" no pertenece a la orden";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
				
				// Si existe más de un registro informa del error
				if (dvrPredeliveryDetailWs.length > 1) {
					errorMsg = "La combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" no es única en la orden";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}

				dvrPredeliveryDetailW = dvrPredeliveryDetailWs[0];
				
				// Detalle del despacho de orden
				props = new PropertyInfoDTO[4];
				props[0] = new PropertyInfoDTO("dvrorderdelivery.dvrdelivery.id", "dvrdeliveryid", dvrdeliveryW.getId());
				props[1] = new PropertyInfoDTO("dvrpredeliverydetail.dvrorderdetail.dvrorder.id", "dvrorderid", detail.getDvrorderid());
				props[2] = new PropertyInfoDTO("dvrpredeliverydetail.location.id", "locationid", detail.getDestinationlocationid());
				props[3] = new PropertyInfoDTO("dvrpredeliverydetail.dvrorderdetail.item.id", "itemid", detail.getItemid());
				dvrOrderDeliveryDetailWs = dvrorderdeliverydetailserver.getByPropertiesAsArray(props);
				
				if (dvrOrderDeliveryDetailWs == null || dvrOrderDeliveryDetailWs.length == 0) {
					errorMsg = "La combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" no pertenece a la entrega";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}
				
				// Si existe más de un registro informa error
				if (dvrOrderDeliveryDetailWs.length > 1) {
					errorMsg = "La combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" no es única en la entrega";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", errorMsg, false);
				}				
				
				dvrOrderDeliveryDetailW = dvrOrderDeliveryDetailWs[0]; 
								
				// Almacena el id de la orden asociada
				dvrOrderIdSet.add(dvrOrderDeliveryDetailW.getDvrorderid());
				
				// Valida que la cantidad ajustada es igual o mayor a cero
				if (detail.getAvailableunits() < 0.0) {
					errorMsg = "Para la combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" la cantidad ingresada debe ser mayor o igual a cero";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// Valida que las unidades informadas sean menores o iguales a las originalmente comprometidas para el detalle de entrega
				if (detail.getAvailableunits() > dvrOrderDeliveryDetailW.getPendingunits()) {
					errorMsg = "Para la combinación de orden " + dvrOrderMap.get(detail.getDvrorderid()).getNumber() + ", local de destino " + 
								locationMap.get(detail.getDestinationlocationid()).getCode() + " y producto " + itemMap.get(detail.getItemid()).getSku() +
								" la cantidad sobrepasa lo comprometido originalmente para ese detalle en la entrega";
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}

				// Valida que las unidades, por cada producto y local destino, no sea mayor a lo que existe pendiente en la predistribución de la orden correspondiente,
				// considerando aquellas entregas que no estén en estado 'No Asiste' y 'Rechazada'
				adjustDifference = detail.getAvailableunits() - dvrOrderDeliveryDetailW.getAvailableunits();
				if (adjustDifference > dvrPredeliveryDetailW.getPendingunits()) {
					errorMsg = "Las cantidades indicadas para el local " + locationMap.get(detail.getDestinationlocationid()).getCode() +
								" y producto " + itemMap.get(detail.getItemid()).getSku() +
								" sobrepasan lo pendiente en la orden de compra " + dvrOrderMap.get(detail.getDvrorderid()).getNumber();
					errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
				}
				
				// Agrega al mapa de detallesa actualizados
				key = new DvrOrderDeliveryDetailId(dvrOrderDeliveryDetailW.getDvrorderid(),
													dvrOrderDeliveryDetailW.getDvrdeliveryid(),
													dvrOrderDeliveryDetailW.getItemid(), 
													dvrOrderDeliveryDetailW.getLocationid(), 
													dvrOrderDeliveryDetailW.getPosition()); 
				dvrOrderDeliveryDetailUpdatedMap.put(key, detail.getAvailableunits());				
			}
			
			// Detalles de la entrega
			Map<DvrOrderDeliveryDetailId, Double> dvrOrderDeliveryDetailMap = new HashMap<DvrOrderDeliveryDetailId, Double>();
						
			// Carga todas las líneas del detalle en un mapa
			dvrOrderDeliveryDetailWs = dvrorderdeliverydetailserver.getByPropertyAsArray("id.dvrdeliveryid", dvrdeliveryW.getId());
			for (DvrOrderDeliveryDetailW oddW : dvrOrderDeliveryDetailWs) {
				 key = new DvrOrderDeliveryDetailId(oddW.getDvrorderid(),
						 							oddW.getDvrdeliveryid(),
													oddW.getItemid(), 
													oddW.getLocationid(), 
													oddW.getPosition());
				 dvrOrderDeliveryDetailMap.put(key, oddW.getAvailableunits());
			}
			
			// Actualiza el mapa del total de detalles
			Double updatedTotalUnits = 0.0;
			for (Map.Entry<DvrOrderDeliveryDetailId, Double> entry : dvrOrderDeliveryDetailMap.entrySet()) {
				// Si la linea fue informada actualiza el valor
				key = entry.getKey();				
				if (dvrOrderDeliveryDetailUpdatedMap.containsKey(key)) {
					dvrOrderDeliveryDetailMap.put(key, dvrOrderDeliveryDetailUpdatedMap.get(key));
				}
				updatedTotalUnits += dvrOrderDeliveryDetailMap.get(key);
			}
			
			// Valida que el total de las cantidades disponibles sea mayor que cero
			if (updatedTotalUnits.equals(0.0)) {
				errorMsg ="Todas las cantidades ingresadas están en cero";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", errorMsg, false));
			}
			
			// Si hay errores
 			if (errorList.size() > 0) {
				// Ordenar errores
				BaseResultDTO[] errorArray = (BaseResultDTO[]) errorList.toArray(new BaseResultDTO[errorList.size()]);
				resultDTO.setBaseresults(errorArray);
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2120");
			}

			// Actualizar los valores
			for (Map.Entry<DvrOrderDeliveryDetailId, Double> entry : dvrOrderDeliveryDetailUpdatedMap.entrySet()) {				
				dvrOrderDeliveryDetailW = dvrorderdeliverydetailserver.getById(entry.getKey(), LockModeType.PESSIMISTIC_WRITE);
				// Valida que el valor haya sido modificado
				if (!entry.getValue().equals(dvrOrderDeliveryDetailW.getAvailableunits())){
					dvrOrderDeliveryDetailW.setAvailableunits(entry.getValue());
					dvrOrderDeliveryDetailW = dvrorderdeliverydetailserver.updateDvrOrderDeliveryDetail(dvrOrderDeliveryDetailW);						
				}
			}
		
			// Actualizar las unidades estimadas de los despachos de órdenes
			dvrorderdeliveryserver.flush();
			dvrorderdeliveryserver.doUpdateEstimatedUnits(dvrdeliveryW.getId());
			
			// Recalcular los totales de las órdenes
			Long[] dvrOrderIds = dvrOrderIdSet.stream().toArray(Long[]::new);
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrOrderIds);

			// Agrega en el log detalles de los ajustes
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Ajuste de Unidades" + "," + "doAdjustDvrOrderDeliveryUnits" + "," + "Ajuste unidades Entrega Nro " + dvrdeliveryW.getNumber());
			
		}  catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
					
		return resultDTO;
	}
	
	public BaseResultsDTO doUploadDvrOrderDeliveryAdjustUnits(DvrOrderDeliveryAdjustUnitsInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultsDTO resultDTO = new BaseResultsDTO();
		
		try{
			// Obtiene el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
						
			// Obtiene la entrega
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if (dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];

			// Valida que la entrega se encuentre en estado 'Re-Agendar', 'Cita Solicitada' o 'Cita Asignada'
			DvrDeliveryStateTypeW dstReschedule = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
			DvrDeliveryStateTypeW dstRequested = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DvrDeliveryStateTypeW dstAssigned = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			
			if (!dvrdeliveryW.getCurrentstatetypeid().equals(dstReschedule.getId()) &&
					!dvrdeliveryW.getCurrentstatetypeid().equals(dstRequested.getId()) &&
						!dvrdeliveryW.getCurrentstatetypeid().equals(dstAssigned.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6003");
			}

			// Crea las tablas temporales para guardar los ajustes informados y los posibles errores de validación
			dvrdeliveryserver.doCreateAdjustTempTable();
			dvrdeliveryserver.doCreateAdjustTempErrorTable();
			
			// Almacena los totales de filas devueltos en cada query
			int total;
			
			// Almacena mensaje de error de una validación
			String error = "";
			
			// Almacena el conjunto de mensajes de error
			List<BaseResultDTO> errorList = new ArrayList<BaseResultDTO>();
			
			// Carga de Archivo CSV
			try {
				total = dvrdeliveryserver.doLoadCSVAdjust(initParamDTO.getFilename());
			
			} catch (Exception e) {
				e.printStackTrace();
				error = "Error procesando el archivo de Ajuste de Unidades";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6004", error, false));
				resultDTO.setBaseresults(errorList.toArray(new BaseResultDTO[errorList.size()]));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6006");
			}
			
			// No hay registros, ocurrió un error procesando el archivo
			if (total < 0) {
				error = "El archivo no contiene datos";
				errorList.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L6004", error, false));
				resultDTO.setBaseresults(errorList.toArray(new BaseResultDTO[errorList.size()]));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6006");
			}
			
			// Carga id de órdenes
			total = dvrdeliveryserver.doFillOrderData();

			// Carga id de locales de destino
			total = dvrdeliveryserver.doFillDestinationLocationData();

			// Carga id de productos
			total = dvrdeliveryserver.doFillItemData();

			// Carga id de entrega
			total = dvrdeliveryserver.doFillDeliveryData(dvrdeliveryW.getId());
			
			// Carga valor para posición
			total = dvrdeliveryserver.doFillDvrOrderDeliveryDetailPosition(dvrdeliveryW.getId());

			// Valida que no existan filas repetidas para la combinación orden, local de destino y producto
			total = dvrdeliveryserver.doCheckUniqueRows();
			
			// Valida que la orden exista en base al número indicado en la columna 'No. Orden'
			total = dvrdeliveryserver.doCheckOrders();
						
			// Valida que el local de destino exista en base al código indicado en la columna 'Código Local Destino'
			total = dvrdeliveryserver.doCheckDestinationLocations();
						
			// Valida que el producto exista en base al código indicado en la columna 'SKU'
			total = dvrdeliveryserver.doCheckItems();
			
			// Valida que la combinación orden, local de destino y producto pertenezca a la entrega
			total = dvrdeliveryserver.doCheckDvrOrderDeliveryDetails();
			
			// Máxima cantidad de errores a mostrar
			Integer maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();
						
			// Si hay errores hasta el momento, no se puede continuar
			FileUploadErrorDTO[] errors = dvrdeliveryserver.getErrorsOfAdjust();
			if (errors.length > 0) {
				// Calcula la cantidad de errores a mostrar
				int errorsSize = errors.length > maxErrorsQtyToShow ? maxErrorsQtyToShow : errors.length;
				BaseResultDTO[] errorArrayResultDTO = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO errorResultDTO = new BaseResultDTO();
					errorResultDTO.setStatuscode("L6004");
					errorResultDTO.setStatusmessage(errors[i].getError());
					errorArrayResultDTO[i] = errorResultDTO;
				}
				resultDTO.setBaseresults(errorArrayResultDTO);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6006");
			}

			// Valida que las unidades informadas sean mayores o iguales a cero
			total = dvrdeliveryserver.doCheckUnitsEqualOrGreaterThanZero();
			
			// Valida que las unidades informadas sean menores o iguales a las pendientes del detalle de entrega
			total = dvrdeliveryserver.doCheckUnitsEqualOrLessThanPendingDeliveryDetails();
			
			// Valida que las unidades informadas de la combinación producto y local sean menores o iguales a las pendientes del detalle de pre distribución de la orden
			total = dvrdeliveryserver.doCheckUnitsEqualOrLessThanPendingPredeliveryDetails();
			
			// Si hay errores hasta el momento, no se puede continuar
			errors = dvrdeliveryserver.getErrorsOfAdjust();
			if (errors.length > 0) {
				// Calcula la cantidad de errores a mostrar
				int errorsSize = errors.length > maxErrorsQtyToShow ? maxErrorsQtyToShow : errors.length;
				BaseResultDTO[] errorArrayResultDTO = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO errorResultDTO = new BaseResultDTO();
					errorResultDTO.setStatuscode("L6004");
					errorResultDTO.setStatusmessage(errors[i].getError());
					errorArrayResultDTO[i] = errorResultDTO;
				}
				resultDTO.setBaseresults(errorArrayResultDTO);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6006");
			}
			
			// Actualiza las unidades en cada detalle del despacho de acuerdo a lo indicado por el usuario como unidades a despachar
			total = dvrdeliveryserver.doUpdateDvrOrderDeliveryDetails();
			
			// Actualiza las unidades estimadas de los despachos de órdenes
			dvrorderdeliveryserver.flush();
			dvrorderdeliveryserver.doUpdateEstimatedUnits(dvrdeliveryW.getId());
			
			// Re-calcula las unidades y montos de las órdenes asociadas al despacho
			Long[] dvrorderids = dvrdeliveryserver.getDvrOrderIdsFromAdjustUnitsData();
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrorderids);
			
			// Agrega en log detalles de los ajustes
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Ajuste de Unidades" + "," + "doUploadDvrOrderDeliveryAdjustUnits" + "," + "Ajuste unidades Entrega Nro " + dvrdeliveryW.getNumber());
					
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BaseResultDTO doDeleteDvrDelivery(DvrDeliveryDeleteInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
				
		try {
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];			
			
			// Obtener la entrega
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("id", "id" , initParamDTO.getDvrdeliveryid());
			props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertiesAsArray(props);
			if (dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryserver.getById(dvrdeliveryArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Valida que la entrega se encuentre en estado 'Re-Agendar'
			DvrDeliveryStateTypeW dstRescheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RE_AGENDAR");
			if (!dvrdeliveryW.getCurrentstatetypeid().equals(dstRescheduled.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2300");
			}
			

			// Log de las OC que pertenecen al despacho eliminado			
			DvrOrderDeliveryW[] dvrOrderDeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			
			StringBuilder ordersSb = new StringBuilder();
			String separator = "";
			for (DvrOrderDeliveryW dvrod : dvrOrderDeliveryArr) {				
				// Orden asociada
				DvrOrderW dvrOrderW = dvrorderserver.getById(dvrod.getDvrorderid());
				ordersSb.append(separator);
				separator = "-";
				ordersSb.append(dvrOrderW.getNumber());
			}

			
			// Elimina detalles de despachos (dvrorderdeliverydetail)
			dvrorderdeliverydetailserver.deleteDvrOrderDeliveryDetailByDvrDeliveryById(dvrdeliveryW.getId());

			// Elimina despachos (dvrorderdelivery)
			dvrorderdeliveryserver.deleteByProperty("dvrdelivery.id", dvrdeliveryW.getId());
			
			
			// Elimina estados de la entrega
			dvrdeliverystateserver.deleteByProperty("dvrdelivery.id", dvrdeliveryW.getId());
			
			// Almacena número de la entrega
			Long deliveryNumber = dvrdeliveryW.getNumber();
			
			// Elimina la entrega
			dvrdeliveryserver.deleteElement(dvrdeliveryW.getId());
			dvrdeliveryserver.flush();
			
			// Re-calcula las cantidades de las órdenes involucradas y las libera para una nueva entrega
			Long[] orderids = Arrays.stream(dvrOrderDeliveryArr).map(oc -> oc.getDvrorderid()).toArray(Long[]::new);
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(orderids);			
			
			// Agrega en log Entrega eliminada
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorW.getName() + "(" + vendorW.getCode() + ")" + "," + "Eliminar Entrega" + "," +  "doDeleteDvrDelivery" + "," + "Eliminación Entrega Nro " + deliveryNumber + " liberando OCs: " + ordersSb.toString());

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}
		
	public PackingListReportResultDTO getPackingListReport(DvrDeliveryDetailInitParamDTO initParamDTO, boolean withTotals, boolean isPaginated){
		PackingListReportResultDTO resultDTO = new PackingListReportResultDTO();
		PackingListReportDataDTO[] packinglistreportdata;
		PackingListReportTotalDataDTO totalresult;
		int total;
		
		try{
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtiene lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Datos
			packinglistreportdata = dvrdeliveryserver.getPackingListReport(vendorW.getId(), dvrdeliveryW.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);

			if(withTotals){
				totalresult = dvrdeliveryserver.getCountPackingListReport(vendorW.getId(), dvrdeliveryW.getId());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				total = totalresult.getTotalreg();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(packinglistreportdata.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotal(totalresult);
			}
			
			resultDTO.setPackinglistreportdata(packinglistreportdata);		
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();		
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");

		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

	}

		
	public DatingRequestDetailDataResultDTO getDatingRequestDetailDataReport(DvrDeliveryInitParamDTO initParamDTO) {
		DatingRequestDetailDataResultDTO resultDTO = new DatingRequestDetailDataResultDTO();
		DatingRequestDataDTO datingrequestdata = new DatingRequestDataDTO();
		
		try {
			// Obtiene lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Obtiene datos
			datingrequestdata = datingrequestserver.getDatingRequestDetailDataReport(dvrdeliveryW.getId());
						
			resultDTO.setDatingrequestdata(datingrequestdata);
			return resultDTO;
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	// Obtiene detalle de las unidades de las ordenes asociadas a la cita
	public DvrOrderToDeliveryUnitsResultDTO getDvrOrderDeliveryUnits(DvrOrderDeliveryUnitsInitParamDTO initParamDTO){
		DvrOrderToDeliveryUnitsResultDTO resultDTO = new DvrOrderToDeliveryUnitsResultDTO();
		DvrOrderToDeliveryUnitsDTO[] dvrordertodeliveryunitsdto;

		try{
			
			// Obtiene lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Datos
			dvrordertodeliveryunitsdto = dvrorderdeliveryserver.getDvrOrderDeliveryUnits(dvrdeliveryW.getId());

			resultDTO.setDvrordertodeliveryunits(dvrordertodeliveryunitsdto);
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
		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public UploadPackingListResultDTO doUploadPackingList(UploadPackingListInitParamDTO initParamDTO, UserLogDataDTO userdataDTO){
		
		UploadPackingListResultDTO resultDTO = new UploadPackingListResultDTO();
		
		// Almacena los mensajes de error que se vayan produciendo
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
		
		// Fecha Actual
		LocalDateTime now = LocalDateTime.now();
		
		// Día actual
		LocalDateTime currentDay = LocalDateTime.now().toLocalDate().atStartOfDay(); 
		
		
		try {
			// Maxima cantidad de errores a mostrar
			Integer  maxErrorsQtyToShow =  LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();

			DvrDeliveryStateTypeW asgSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DvrDeliveryStateTypeW datSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			
			
			// Parámentro que indica si se hace validació nde los documentos contra servicio excterno
			ParameterW[] parameterArr = parameterserver.getByPropertyAsArray("code", "CHECK_DOCUMENT");
			// Parámetro no existe
			if(parameterArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L8500");
			}
			
			// Obtener Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			
			// Obtiene Lote
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if (dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2240");
			}

			DvrDeliveryW dvrdeliveryW = dvrdeliveryserver.getById(dvrdeliveryArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Almacena los totales de filas devueltos en cada query
			int total;

			// Para guardar los errores (en caso que existan)
			AsnUploadErrorDTO[] errorArr;

			// Almacena Mensaje de error que se envía al Portal
			String error = "";

			
			// El estado actual de la entrega no es "Cita Asignada
			if(! dvrdeliveryW.getCurrentstatetypeid().equals(asgSt.getId())){
				error = "La entrega debe estar en estado 'Cita Asignada'";
				baseresultlist.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// Crea tabla Temporal para guardar ASN
			bulkserver.doCreateTempTableAsn();
			// Crea tabla temporal para guardar Errores
			bulkserver.doCreateTempTableError();

			// Carga de Archivo CSV
			try {
				total = bulkserver.doLoadCSV(initParamDTO.getFilename());
			} catch (Exception e) {
				e.printStackTrace();
				error = "Error procesando el archivo de ASN";
				baseresultlist.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			// No hay registros, ocurrió un error procesando el archivo
			if (total < 0) {
				error = "Error al cargar el archivo de ASN. No hay lineas";
				baseresultlist.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// Carga id de Ordenes
			total = bulkserver.doFillOrderData();

			// Carga id de locations
			total = bulkserver.doFillLocationData();

			// Carga id de items
			total = bulkserver.doFillItemData();

			// Carga id de delivery
			total = bulkserver.doFillDeliveryData(dvrdeliveryW.getId());

			// Carga id del tipo de documento
			total = bulkserver.doFillDocumenttype();
			
			// Carga valor para posición
			total = bulkserver.doFillDvrOrderDetailPosition();

			// El sistema valida que no existan filas repetidas para la combinación No. Orden – Local destino – LPN – SKU.
			total = bulkserver.doCheckUniqueRows();
			
			// El sistema valida que el número de OC sea numérico y  exista en BD.			
			total = bulkserver.doCheckOrderExists();
			
			// El sistema valida que la OC esté asignada al proveedor seleccionado
			total = bulkserver.doCheckOrderAssignedVendor(vendorW.getId(), vendorW.getName());
			
			// El sistema valida que la orden informada esté vigente según su estado.
			total = bulkserver.doCheckOCValidState();
			
			// El sistema valida que la orden no esté vencida según su fecha de vencimiento.
			total = bulkserver.doCheckOCExpirationDate(currentDay);
		
			// El sistema valida que el SKU informado exista en BD.
			total = bulkserver.doCheckItemExists();
			
			// El sistema valida que el local de destino informado exista en BD.
			total = bulkserver.doCheckDeliveryLocationExists();			
			
			// El sistema valida que la combinación No. Orden - Local destino – SKU exista en el despacho.	
			total = bulkserver.doCheckOCItemLocalOnDelivery();

			// El sistema valida que las unidades informadas sean numéricas y mayores que 0.
			total = bulkserver.doCheckUnits();
			
			// El sistema valida que las cantidades informadas por SKU son  menores o iguales a las pendientes a nivel de detalle de despacho (No. Orden - Local destino – SKU).
			total = bulkserver.doCheckOCItemLocationUnits();
			
			// Valida largo del LPN
			total = bulkserver.doCheckLPNFormatTotalLength(PL_UPLOAD_BULK_TOTAL_LENGTH);
			
			// Se valida codigo fijo de LPN en las posiciones 1 y 2
			// Si es provedor nacional debe ser 55
			if(vendorW.getDomestic()) {
				total = bulkserver.doCheckLPNFormatStartsWith(PL_UPLOAD_LPN_PREFIX_SINCE, PL_UPLOAD_LPN_PREFIX_LENGTH, PL_UPLOAD_LPN_PREFIX_VALUE);
			}
			
			// Se valida código de proveedor. Se informa como parte del código LPN
			total = bulkserver.doCheckLPNFormatVendorCode(PL_UPLOAD_VENDORCODE_SINCE, PL_UPLOAD_VENDORCODE_LENGTH);
			
			// Se valida que correlativo de bulto tenga largo 8
			total = bulkserver.doCheckLPNFormatSerialLength(PL_UPLOAD_LPN_SERIAL_SINCE, PL_UPLOAD_LPN_SERIAL_LENGTH);
			
			// Se valida que el correlativo de bulto sea un número entero
			total = bulkserver.doCheckLPNFormatSerialType(PL_UPLOAD_LPN_SERIAL_SINCE);
			
			// Se valida que el correlativo de bulto sea mayor al asociado al proveedor (min)
			total = bulkserver.doCheckLPNFormatSerialValue(PL_UPLOAD_LPN_SERIAL_SINCE, vendorW.getMincorrelative());
			
			// El sistema valida que el LPN informado corresponde al proveedor asociado.
			total = bulkserver.doCheckLPNVendorCodeRelated(PL_UPLOAD_VENDORCODE_SINCE, PL_UPLOAD_VENDORCODE_LENGTH, vendorW.getCode());
			
			// El sistema valida que el LPN informado no exista en sistema.
			total = bulkserver.doCheckExistsLPN();
			
			// El sistema valida que para el LPN se informen productos de solo una Orden de compra.
			total = bulkserver.doCheckUniqueLPNOC();
			
			//	El sistema valida que el LPN este asociado a una única factura.  
			total = bulkserver.doCheckUniqueLPNDocument();
			
			// El sistema valida que el LPN esté asociado a solo un local de destino.
			total = bulkserver.doCheckUniqueLPNLocation();
			
			// El sistema valida que si la OC es de tipo Stock, el LPN no debe contener más de un SKU.
//			total = bulkserver.doCheckUniqueSKUByLPN(PL_UPLOAD_LPN_ORDERTYPEFILTER);
			
			// Validaciones de documentos:
			// El sistema valida que el tipo de documento exista en sistema.
			total = bulkserver.doCheckDocumentType();
			
			// El sistema valida que la combinación de número documento – tipo, no exista para el mismo proveedor en BD.
			total = bulkserver.doCheckDocumentNumberTypeVendorNotExists(vendorW.getCode());

			// El sistema valida que la combinación de número documento – tipo, ese asociada a solo una OC.
			total = bulkserver.doCheckUniqueDocumentNumberTypeOCUnique();
			
			// Si hay errores hasta el momento, no se puede continuar
			errorArr = bulkserver.getErrorsOfASN();
			if (errorArr.length > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = errorArr.length;
				if(errorArr.length > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}

				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// Agrega documentos
			total = bulkserver.doAddDocumentFromAsnData();

			// Actualiza ID de documento en tabla de ASN
			total = bulkserver.doFillDocumentid();
			
			// Valida que todos los registros agregaron un documento (el sistema agrega documentos sin estar relacionados aún con un proveedor, lo que se hace luego a través de los bultos)
			total = bulkserver.doCheckDocumentExists();
			
			// Si hay errores hasta el momento, no se puede continuar
			errorArr = bulkserver.getErrorsOfASN();
			if (errorArr.length > 0) {
				// Calcula cantidad de errores a mostrar
				int errorsSize = errorArr.length;
				if(errorArr.length > maxErrorsQtyToShow){
					errorsSize = maxErrorsQtyToShow;
				}

				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage(errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				resultDTO.setValidationerrors(curResultArr);
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}
			
			// El sistema valida que el valor de la propiedad asociada a la validación contra el SII sea 'true'.
			ParameterW parameterW = parameterArr[0];
 
			// Si parámetro está activo
			if (parameterW.getActive()) {
				// TODO: El sistema salta a CU LOG XXXX Validar documentos.

				// El sistema actualiza los documentos asociados. 
				// Si fue posible la validación contra el SII, se  modifica el campo ‘Validated’ de la tabla ‘Document’ a true

				
			}
			else {
				// El sistema actualiza los documentos asociados calculando los totales
				total = bulkserver.doUpdateDocumentFromAsnData();
			}
						
			// El sistema agrega el nuevo bulto con su detalle.
			// Carga Bulk
			total = bulkserver.doAddBulkFromAsnData();

			// Actualiza ID de Bulk en tabla de ASN
			total = bulkserver.doFillBulkId();

			// Carga BulkDetail
			total = bulkserver.doAddBulkDetailFromAsnData();

			// Dejar AvailableUnits de todos el despacho en 0
			total = bulkserver.doSetAvailableInDvrOrderDeliveryDetail(dvrdeliveryW.getId(), 0L);

			// Actualizamos OrderDeliveryDetail AvailableUnits
			// Si algún detalle de entrega no venia en el packinglist, va a
			// quedar en '0'
			total = bulkserver.doUpdateAvailableInDvrOrderDeliveryDetail();
			
			// Cerrar todos los despachos de órdenes no incluidas en el PL
			total = bulkserver.doCloseNotIncludedOrderDeliveries(dvrdeliveryW.getId());
						
			// Obtener total de bultos creados
			int packingListBulkAmount = bulkserver.getTotalBulkQty();

			// Obtener total de unidades informadas en el PL
			Double packingListTotalUnits = bulkserver.getTotalUnitsBulks();

			// Cálculo de totales
			//Long[] dvrorderids = bulkserver.doGetDvrOrderIdsFromASN(); // este solo busca las órdenes incluidas en el PL
			List<DvrOrderDeliveryW> dvrOrderDeliveries = dvrorderdeliveryserver.getByProperty("dvrdelivery.id", initParamDTO.getDvrdeliveryid()); // busca todas las órdenes de la entrega
			Long[] dvrorderids = dvrOrderDeliveries.stream().map(od -> od.getDvrorderid()).toArray(Long[]::new);
			
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrorderids);

			// Se actualiza el estado del lote
			// Cambio estado de la entrega a 'Agendada'
			dvrdeliveryW.setCurrentstatetypeid(datSt.getId());
			dvrdeliveryW.setCurrentstatetypedate(now);
			dvrdeliveryW.setPluploaddate(now);
			dvrdeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrdeliveryW);

			// Estado
			DvrDeliveryStateW state = new DvrDeliveryStateW();
			state.setDvrdeliveryid(dvrdeliveryW.getId());
			state.setDvrdeliverystatetypeid(datSt.getId());
			state.setWhen(now);
			state.setUser(userdataDTO.getUsername());
			state.setUsertype(userdataDTO.getUsertype());
			state = dvrdeliverystateserver.addDvrDeliveryState(state);
			
//			// Estado actual de la entrega
			DvrDeliveryStateTypeDataDTO currentstatetypeDTO = new DvrDeliveryStateTypeDataDTO();
			BeanExtenderFactory.copyProperties(datSt, currentstatetypeDTO);
			
			resultDTO.setCurrentstatetype(currentstatetypeDTO);
			resultDTO.setTotalbulks(packingListBulkAmount);
			resultDTO.setTotalunits(packingListTotalUnits);
						
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");

		}
		
		return resultDTO;
	}

	@TransactionTimeout(900) // timeout = 15 min
	public PackingListDeleteResultDTO doDeletePackingList(DvrDeliveryInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		PackingListDeleteResultDTO resultDTO = new PackingListDeleteResultDTO();
		
		String dvrDeliveryNumberStr = "";
		String vendorCode = "";
		String vendorName = "";
		try {
			// Obtiene la entrega
			DvrDeliveryW dvrDeliveryW = dvrdeliveryserver.getById(initParamDTO.getDvrdeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			if (dvrDeliveryW == null) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			dvrDeliveryNumberStr = dvrDeliveryW.getNumber().toString();
			
			// Obtener el proveedor
			VendorW vendorW = vendorserver.getById(dvrDeliveryW.getVendorid());
			vendorCode = vendorW.getCode();
			vendorName = vendorW.getName();
			
			// Valida que la cita seleccionada esté en estado 'Cita Confirmada' o 'Cita Agendada'
			DvrDeliveryStateTypeW dstConfirmed = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_CONFIRMADA");
			DvrDeliveryStateTypeW dstScheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			if (!dvrDeliveryW.getCurrentstatetypeid().equals(dstConfirmed.getId()) &&
					!dvrDeliveryW.getCurrentstatetypeid().equals(dstScheduled.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4008");
			}
			
			// Obtener los documentos asociados
			// Se puede dar el caso de que no existan por eliminación anterior pero la cita no haya sido informada correctamente a WMS
			DocumentW[] documents = documentserver.getDocumentRelatedToDelivery(dvrDeliveryW.getId());			
			
			resultDTO.setAsnsuccess(true);
						
			List<WSSentMessageResponseDTO> documentMessageList = new ArrayList<WSSentMessageResponseDTO>();
			WSSentMessageResponseDTO documentMessage = null;
			BaseResultDTO documentResultDTO;
						
			if (documents != null && documents.length > 0) {
				// Obtener mapas con los datos para los mensajes de documentos pendientes
				Long[] documentIds = Arrays.stream(documents).map(doc -> doc.getId()).toArray(Long[]::new);
				ASNDataToMessageDTO[] asnDataArr = documentserver.getDeliveryASNHeaderData(dvrDeliveryW.getId(), documentIds);
				HashMap<Long, List<ASNDataToMessageDTO>> headerMap = (HashMap<Long, List<ASNDataToMessageDTO>>) Arrays
																												.stream(asnDataArr)
																												.collect(Collectors.groupingBy(ASNDataToMessageDTO::getDocumentid));
				
				ASNDetailDataToMessageDTO[] asnDetail =  documentserver.getDeliveryASNDetailData(dvrDeliveryW.getId(), documentIds);
				HashMap<Long, List<ASNDetailDataToMessageDTO>> detailMap = (HashMap<Long, List<ASNDetailDataToMessageDTO>>) Arrays
																															.stream(asnDetail)
																															.collect(Collectors.groupingBy(ASNDetailDataToMessageDTO::getDocumentid));
				
				// Obtener los bultos asociados
				List<BulkW> bulks = bulkserver.getByProperty("dvrdelivery.id", dvrDeliveryW.getId());
				
				// Mapa de bultos x documentos
				Map<Long, List<Long>> bulkIdMap = bulks.stream()
													   .collect(Collectors.groupingBy(bu -> bu.getDocumentid(), Collectors.mapping(bu -> bu.getId(), Collectors.toList())));
				
				WSSentMessageResultDTO asnMessageData = null;
				for (DocumentW documentW : documents) {
					// Si el estado del documento es 'Enviado'
					if (documentW.getStatus()) {
						// El sistema genera por cada factura listada la interfaz de ASN asociada
						asnMessageData = asnmessagemanager.doSendDocumentASNDeleteMessage(documentW, headerMap.get(documentW.getId()), detailMap.get(documentW.getId()), bulkIdMap.get(documentW.getId()));
						
						// Agregar respuesta al listado
						documentMessage = new WSSentMessageResponseDTO();
						BeanExtenderFactory.copyProperties(asnMessageData, documentMessage);
						documentMessageList.add(documentMessage);
						
						resultDTO.setAsnsuccess(resultDTO.getAsnsuccess() && !asnMessageData.getSenterror());
					}
					else {
						// Elimina el documento, los bultos y sus detalles
						for (Long bulkId : bulkIdMap.get(documentW.getId())) {
							bulkdetailserver.deleteByProperty("bulk.id", bulkId);
							bulkserver.deleteByProperty("id", bulkId);
						}
						documentserver.deleteByProperty("id", documentW.getId());
					}
				}
			}
			
			resultDTO.setDocumentmessages((WSSentMessageResponseDTO[]) documentMessageList.toArray(new WSSentMessageResponseDTO[documentMessageList.size()]));
			
			// Valida que todas las interfaces de ASN hayan sido enviadas con éxito
			if (resultDTO.getAsnsuccess()) {
				// Obtener la cita
				DatingW dating = datingserver.getByPropertyAsSingleResult("dvrdelivery.id", dvrDeliveryW.getId());
				
				resultDTO.setDatingsuccess(true);
				
				// Valida que la cita se encuentre en estado 'Enviada'
				if (dating.getSentstatus()) {
					WSSentMessageResultDTO datingMessageData = asnmessagemanager.doSendDatingDeleteMessage(dvrDeliveryW);

					// Respuesta WS ASN
					documentMessage = new WSSentMessageResponseDTO();
					BeanExtenderFactory.copyProperties(datingMessageData, documentMessage);
										
					resultDTO.setDatingsuccess(!datingMessageData.getSenterror());
				}
				else {
					// Almacena resultado
					documentResultDTO = new BaseResultDTO();
					documentResultDTO.setStatuscode("0");
					documentResultDTO.setStatusmessage("Cita tiene envío previo exitoso");
					
					documentMessage = new WSSentMessageResponseDTO();
					documentMessage.setMessageid(dvrDeliveryNumberStr);
					documentMessage.setValidationresult(documentResultDTO);
				}
				
				//Respuesta WS Cita
				resultDTO.setDatingmessages(documentMessage);
				
				if (resultDTO.getDatingsuccess()) {
					LocalDateTime now = LocalDateTime.now();
					
					// Actualiza la cita con el estado 'No Enviado'
					dating.setSentstatus(false);
					dating = datingserver.updateDating(dating);
					
					// Actualiza el estado del despacho a 'Cita Asignada'
					DvrDeliveryStateTypeW dstAssigned = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
					dvrDeliveryW.setCurrentstatetypeid(dstAssigned.getId());
					dvrDeliveryW.setCurrentstatetypedate(now);
					dvrDeliveryW = dvrdeliveryserver.updateDvrDelivery(dvrDeliveryW);

					// Agrega el nuevo estado al historial de estados del despacho
					DvrDeliveryStateW dvrDeliveryStateW = new DvrDeliveryStateW();
					dvrDeliveryStateW.setWhen(now);
					dvrDeliveryStateW.setUser(userdataDTO.getUsername());
					dvrDeliveryStateW.setUsertype(userdataDTO.getUsertype());
					dvrDeliveryStateW.setComment("");
					dvrDeliveryStateW.setDvrdeliveryid(dvrDeliveryW.getId());
					dvrDeliveryStateW.setDvrdeliverystatetypeid(dstAssigned.getId());					
					dvrdeliverystateserver.addDvrDeliveryState(dvrDeliveryStateW);
					
					// Envía un correo electrónico al usuario que generó la solicitud de cita asociada al despacho
					DatingRequestW datingRequest = datingrequestserver.getByPropertyAsSingleResult("dvrdelivery.id", dvrDeliveryW.getId());
					
					String subject = "B2B Hites: Eliminación de Packing List para Entrega " + dvrDeliveryNumberStr;			
					String mailSender = LogisticConstants.getMailSender();
					String mailSession = LogisticConstants.getMAIL_SESSION();
					String[] mailReceiver = new String[1];
					mailReceiver[0] = datingRequest.getEmail();
					
					if (mailReceiver[0] != null && !mailReceiver[0].equals("")) {
						String msgtext =
								"Estimado(a) Usuario(a):<br><br> " + //
								"Informamos que el Packing List para la entrega N° " + dvrDeliveryNumberStr + " ha sido eliminado del sistema.<br><br>" + // 									
								"Atte.<br>" + //
								"B2B Hites<br><br>" + //
								"<b>Favor no responder este correo dado que es generado de manera automática por el sistema B2B.<b>";
						
						// Envía correo a tabla de correos pendientes
						schedulermailmanager.doAddMailToQueue(mailSender, mailReceiver, null, null, subject, msgtext, mailSession, "ELIMINACION_PL", dvrDeliveryNumberStr);		
					}
					
					// Log de PL eliminado 
					logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Eliminar PL" + "," +  "doDeletePackingList" + "," + "Eliminación de Packing List de Entrega Nro " + dvrDeliveryNumberStr);
				}
			}
			else {
				// Log de PL eliminación parcial 
				logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Eliminar PL" + "," +  "doDeletePackingList" + "," + "Eliminación parcial de Packing List de Entrega Nro " + dvrDeliveryNumberStr);
			}

		} catch (OperationFailedException | NotFoundException | AccessDeniedException e) {
			// Log de error en eliminación de PL 
			logger.error(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendorName + "(" + vendorCode + ")" + "," + "Eliminar PL" + "," +  "doDeletePackingList" + "," + "Error en eliminación parcial de Packing List de Entrega Nro " + dvrDeliveryNumberStr);
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;		
	}
	
	public DvrDeliveryDatingDocumentResultDTO getDvrDeliveryDatingDocumentReportData(DvrDeliveryDatingDocumentInitParamDTO initParamDTO, boolean withTotals, boolean ispaginated) {
		DvrDeliveryDatingDocumentResultDTO resultDTO = new DvrDeliveryDatingDocumentResultDTO();
		DvrDeliveryDatingDocumentReportDataDTO[] dvrdeliverydocumentreportdata;
		int total;
		LocalDateTime when = null;
		
		try {
			// Estados de las entregas usadas en el filtro
			DvrDeliveryStateTypeW agnStateW = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			Long[] dvrdelvierystatetypeids = {agnStateW.getId()};
					
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
			
			// Si se elige critero de búsqueda por fecha
			if (initParamDTO.getFiltertype() == 0) {
				// Validar que se ingrese fecha
				if (initParamDTO.getWhen() == null) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L500");
				}
				
				when = initParamDTO.getWhen().toLocalDate().atStartOfDay();	
			}
			
			// Datos
			dvrdeliverydocumentreportdata = dvrdeliveryserver.getDvrDeliveryDatingDocumentReport(dvrdelvierystatetypeids, vendorid, locationW.getId(), initParamDTO.getSearchvalue(), when, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), ispaginated);
 
			if(withTotals){
				total = dvrdeliveryserver.getCountDvrDeliveryDatingDocumentReport(dvrdelvierystatetypeids, vendorid, locationW.getId(), initParamDTO.getSearchvalue(), when, initParamDTO.getFiltertype());

				// Setea Información de paginación
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dvrdeliverydocumentreportdata.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
				resultDTO.setTotalreg(total);
			}

			
			resultDTO.setDvrdeliverydatingdocumentreportdata(dvrdeliverydocumentreportdata);
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;		
	}
	
	public DvrDeliveryDatingDocumentDetailResultDTO getDvrDeliveryDatingDocumentDetailReportData(DvrDeliveryDatingDocumentDetailInitParamDTO initParamDTO, boolean withTotals) {
		
		DvrDeliveryDatingDocumentDetailResultDTO resultDTO = new DvrDeliveryDatingDocumentDetailResultDTO();
		DvrDeliveryDatingDocumentDetailReportDataDTO[] dvrdeliverydatingdocumentdetailreportdata;
		int total;
		
		try {
			// Obtiene Delivery
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2210");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
			
			// Datos
			dvrdeliverydatingdocumentdetailreportdata = documentserver.getDvrDeliveryDatingDocumentDetailData(dvrdeliveryW.getId(), initParamDTO.getOrderby());
			
			if (withTotals) {
				total = documentserver.getCountDvrDeliveryDatingDocumentDetailData(dvrdeliveryW.getId());
				resultDTO.setTotalreg(total);
			}
			
			resultDTO.setDvrdeliverydatingdocumentdetailreportdata(dvrdeliverydatingdocumentdetailreportdata);
						
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		
		}  catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}		
		
		return resultDTO;
	}

	// Revisa si los documentos informados fueron validados por SII (si parámetro está configurado para la validación)
	public BaseResultDTO doCheckValidatedDocumentsToASN(UploadASNInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Obtiene parámetro que indica si se hace o no validación de los documentos contra sistema externo
			ParameterW[] parameterArr = parameterserver.getByPropertyAsArray("code", "CHECK_DOCUMENT");

			// Parámetro no existe
			if (parameterArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L8500");
			}
			ParameterW parameterW = parameterArr[0];
 
			// Si parámetro está activo
			if (parameterW.getActive()) {
				// Obtiene documentos
				DocumentW[] documentArr = documentserver.getDocumentByIds(initParamDTO.getDocumentids());
				
				// Busca si hay documentos no validados
				Boolean noValidatedDocs = Arrays.stream(documentArr).anyMatch(doc -> doc.getValidated().equals(false));
				
				if (noValidatedDocs) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
				}
			}
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;		
	}
	
	// Revisa si los documentos informados fueron validados por SII (si parámetro está configurado para la validación)
	private BaseResultDTO doCheckValidatedDocumentsToASN(DocumentW[] documentArr) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Obtiene parámetro que indica si se hace o no validación de los documentos contra sistema externo
			ParameterW[] parameterArr = parameterserver.getByPropertyAsArray("code", "CHECK_DOCUMENT");

			// Parámetro no existe
			if (parameterArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L8500");
			}
			ParameterW parameterW = parameterArr[0];
 
			// Si parámetro está activo
			if (parameterW.getActive()) {
				// Busca si hay documentos no validados
				Boolean noValidatedDocs = Arrays.stream(documentArr).anyMatch(doc -> doc.getValidated().equals(false));
				
				if (noValidatedDocs) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
				}
			}
			
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BaseResultDTO doUpdateDocuments(DocumentUpdateInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Obtener el proveedor
			VendorW[] vendors = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendors == null || vendors.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendor = vendors[0];			
			
			// Obtener la entrega
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("id", "id" , initParamDTO.getDvrdeliveryid());
			props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			DvrDeliveryW[] dvrDeliveries = dvrdeliveryserver.getByPropertiesAsArray(props);
			if (dvrDeliveries == null || dvrDeliveries.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrDelivery = dvrdeliveryserver.getById(dvrDeliveries[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Valida que la entrega esté en estado 'Agendada'
			DvrDeliveryStateTypeW dstScheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			if (!dvrDelivery.getCurrentstatetypeid().equals(dstScheduled.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4003");
			}
			
			// Obtener lista sin duplicados
			List<DocumentUpdateDTO> documentUpdates = Arrays.stream(initParamDTO.getUpdate()).distinct().collect(Collectors.toList());
			
			// Valida que se haya seleccionado al menos un registro a eliminar
			if (documentUpdates == null || documentUpdates.size() == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6012");
			}
			
			List<DocumentW> documents = new ArrayList<DocumentW>();
			List<BulkW> bulks;
			PropertyInfoDTO prop1;
			PropertyInfoDTO prop2;
			PropertyInfoDTO prop3;
			for (DocumentUpdateDTO documentUpdate : documentUpdates) {
				// Obtiene los documentos a eliminar
				DocumentW document = documentserver.getById(documentUpdate.getDocumentid(), LockModeType.PESSIMISTIC_WRITE);
				
				// Valida que el documento no haya sido informado previamente a WMS
				if (document.getStatus()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6016");
				}
				
				// Valida que el campo no esté vacío
				if (documentUpdate.getNumber() == null) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6017");
				}
				
				// Valida que el valor ingresado sea numérico mayor que cero
				if (documentUpdate.getNumber() <= 0) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6018");
				}
				
				// Valida que la combinación de número y tipo de documento no exista previamente para el proveedor
				prop1 = new PropertyInfoDTO("dvrdelivery.vendor.id", "vendorid", vendor.getId());
				prop2 = new PropertyInfoDTO("document.number", "documentnumber", documentUpdate.getNumber().toString());
				prop3 = new PropertyInfoDTO("document.documenttype.id", "documenttypeid", document.getDocumenttypeid());
				bulks = bulkserver.getByProperties(prop1, prop2, prop3);
				if (bulks != null && bulks.size() > 0) {
					DvrDeliveryW errorDvrDelivery = dvrdeliveryserver.getById(bulks.get(0).getDvrdeliveryid());
					DocumentTypeW errorDocumentType = documenttypeserver.getById(document.getDocumenttypeid());
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
													"El número de documento " + documentUpdate.getNumber() + " y tipo " + errorDocumentType.getName() +
													" ya existen para el proveedor en la entrega N° " + errorDvrDelivery.getNumber(), false);
				}
				
				document.setNumber(documentUpdate.getNumber().toString());
				documents.add(document);
			}
			
			// Actualiza el número asociado al documento seleccionado
			for (DocumentW document : documents) {
				documentserver.updateDocument(document);
			}
			
			// Agrega en log detalles de la actualización
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendor.getName() + "(" + vendor.getCode() + ")" + "," + "Modificación de documentos" + "," + "doUpdateDocuments" + "," + "Modificación de documentos Entrega Nro " + dvrDelivery.getNumber());
			
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		
		}  catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
				
		return resultDTO;
	}
	
	public BaseResultDTO doDeleteDocuments(DocumentDeleteInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Obtener el proveedor
			VendorW[] vendors = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendors == null || vendors.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendor = vendors[0];			
			
			// Obtener la entrega
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("id", "id" , initParamDTO.getDvrdeliveryid());
			props[1] = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			DvrDeliveryW[] dvrDeliveries = dvrdeliveryserver.getByPropertiesAsArray(props);
			if (dvrDeliveries == null || dvrDeliveries.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2241");
			}
			DvrDeliveryW dvrDelivery = dvrdeliveryserver.getById(dvrDeliveries[0].getId(), LockModeType.PESSIMISTIC_WRITE);
			
			// Valida que la entrega esté en estado 'Agendada'
			DvrDeliveryStateTypeW dstScheduled = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			if (!dvrDelivery.getCurrentstatetypeid().equals(dstScheduled.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4003");
			}
			
			// Obtener los bultos asociados
			List<BulkW> bulks = bulkserver.getByProperty("dvrdelivery.id", dvrDelivery.getId());
						
			// Obtener lista sin duplicados
			List<Long> documentIds = Arrays.stream(initParamDTO.getDocumentids()).distinct().collect(Collectors.toList());
			
			// Valida que se haya seleccionado al menos un registro a eliminar
			if (documentIds == null || documentIds.size() == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6012");
			}

			// Valida que los documentos a eliminar pertenezcan a la entrega
			if (documentIds.stream()
								.filter(id -> bulks.stream()
									  			   .noneMatch(bu -> bu.getDocumentid().equals(id)))
								.count() > 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6013");
			}
			
			// Valida que no se hayan seleccionado todos los documentos
			if (bulks.stream()
							.filter(bu -> documentIds.stream()
													 .noneMatch(id -> id.equals(bu.getDocumentid())))
							.count() == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6014");
			}
			
			// Mapa de bultos x documentos
			Map<Long, List<Long>> bulkIdMap = bulks.stream()
												   .filter(bu -> documentIds.stream()
														   					.anyMatch(id -> id.equals(bu.getDocumentid())))
												   .collect(Collectors.groupingBy(bu -> bu.getDocumentid(), Collectors.mapping(bu -> bu.getId(), Collectors.toList())));
			
			for (Long id : documentIds) {
				// Obtiene los documentos a eliminar
				DocumentW document = documentserver.getById(id, LockModeType.PESSIMISTIC_WRITE);
				
				// Valida que los documentos seleccionados no hayan sido informados previamente a WMS
				if (document.getStatus()) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6015");
				}
			}
			
			// Elimina los documentos, sus bultos y detalles
			for (Long documentId : documentIds) {
				for (Long bulkId : bulkIdMap.get(documentId)) {
					bulkdetailserver.deleteByProperty("bulk.id", bulkId);
					bulkserver.deleteByProperty("id", bulkId);
				}
				
				documentserver.deleteByProperty("id", documentId);
			}
			bulkdetailserver.flush();
			
			// Actualiza las unidades en cada detalle del despacho
			dvrorderdeliverydetailserver.doUpdateDvrOrderDeliveryDetails(dvrDelivery.getId());
			
			// Actualiza las unidades estimadas de los despachos de órdenes
			dvrorderdeliveryserver.flush();
			dvrorderdeliveryserver.doUpdateEstimatedUnits(dvrDelivery.getId());
			
			// Re-calcula las cantidades de las órdenes
			List<DvrOrderDeliveryW> dvrOrderDeliveries = dvrorderdeliveryserver.getByProperty("dvrdelivery.id", dvrDelivery.getId());
			Long[] dvrOrderIds = dvrOrderDeliveries.stream()
												   .map(od -> od.getDvrorderid())
												   .toArray(Long[]::new);
			buyorderreportmanagerlocal.doCalculateTotalOfDvrOrders(dvrOrderIds);
			
			// Agrega en log detalles de las eliminaciones
			logger.info(userdataDTO.getUsername() + "," + userdataDTO.getUsercode() + "," + vendor.getName() + "(" + vendor.getCode() + ")" + "," + "Eliminación de documentos" + "," + "doDeleteDocuments" + "," + "Eliminación de documentos Entrega Nro " + dvrDelivery.getNumber());
			
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		
		}  catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
				
		return resultDTO;
	}
	
	@TransactionTimeout(900) // timeout = 15 min
	public ASNUploadResultDTO doUploadASN(UploadASNInitParamDTO initParamDTO, UserLogDataDTO userdataDTO) {		
		ASNUploadResultDTO resultDTO = new ASNUploadResultDTO();
		
		List<WSSentMessageResponseDTO> asnuploadmessageList = new ArrayList<WSSentMessageResponseDTO>();
		WSSentMessageResponseDTO documentMessage = null;

		// Booleano que indica si hubo algún ASN que presentó error en el envío
		boolean asnsenterror = false;
		
		// Booleano que indica si el envío de la cita presentó algún error
		boolean datingsenterror = false;
		
		try {
			DvrDeliveryStateTypeW agnSt = dvrdeliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
					
			// Obtiene Delivery
			DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getByPropertyAsArray("id", initParamDTO.getDvrdeliveryid());
			if(dvrdeliveryArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2210");
			}
			DvrDeliveryW dvrdeliveryW = dvrdeliveryArr[0];
						
			// Obtiene Cita asociada
			DatingW[] datingArr = datingserver.getByPropertyAsArray("dvrdelivery.id", dvrdeliveryW.getId());
			if(datingArr.length == 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2230");
			}
			DatingW datingW = datingArr[0]; 
			
			// Obtiene Proveedor
			VendorW vendorW = vendorserver.getById(dvrdeliveryW.getVendorid());
			
			// Obtiene los documentos asociados al delivery actual
			DocumentW[] documentArr = documentserver.getDocumentRelatedToDelivery(dvrdeliveryW.getId());
						
			// Validaciones sobre los documentos
			BaseResultDTO validateResultDTO = doCheckValidatedDocumentsToASN(documentArr);
			if (!validateResultDTO.getStatuscode().equals("0")) {
				resultDTO.setStatuscode(validateResultDTO.getStatuscode());
				resultDTO.setStatusmessage(validateResultDTO.getStatusmessage());
				return resultDTO;
			}
			
			// El sistema agrega al historial del delivery un nuevo registro de estado, manteniéndose como 'Cita agendada' 
			// solo a fin de llevar un registro de cuántas veces se intentó el envío de ASN para esta entrega.
			DvrDeliveryStateW  dvrdeliverystateW = new DvrDeliveryStateW();
			dvrdeliverystateW.setComment("");
			dvrdeliverystateW.setDvrdeliveryid(dvrdeliveryW.getId());
			dvrdeliverystateW.setDvrdeliverystatetypeid(agnSt.getId());
			dvrdeliverystateW.setUser(userdataDTO.getUsername());
			dvrdeliverystateW.setUsertype(userdataDTO.getUsertype());
			dvrdeliverystateW.setWhen(LocalDateTime.now());
			dvrdeliverystateW = dvrdeliverystateserver.addDvrDeliveryState(dvrdeliverystateW);
			
			// Filtra solo los documentos pendientes de envío
			DocumentW[] documentPendingArr = Arrays.stream(documentArr).filter(doc-> doc.getStatus().equals(false)).toArray(DocumentW[]::new);
			if (documentPendingArr != null && documentPendingArr.length > 0) {
				// Obtener mapas con los datos para los mensajes de documentos pendientes
				Long[] documentIds = Arrays.stream(documentPendingArr).map(doc -> doc.getId()).toArray(Long[]::new);
			
				ASNDataToMessageDTO[] asnDataArr = documentserver.getDeliveryASNHeaderData(dvrdeliveryW.getId(), documentIds);
				HashMap<Long, List<ASNDataToMessageDTO>> headerMap = (HashMap<Long, List<ASNDataToMessageDTO>>) Arrays
																												.stream(asnDataArr)
																												.collect(Collectors.groupingBy(ASNDataToMessageDTO::getDocumentid));
				
				ASNDetailDataToMessageDTO[] asnDetail =  documentserver.getDeliveryASNDetailData(dvrdeliveryW.getId(), documentIds);
				HashMap<Long, List<ASNDetailDataToMessageDTO>> detailMap = (HashMap<Long, List<ASNDetailDataToMessageDTO>>) Arrays
																															.stream(asnDetail)
																															.collect(Collectors.groupingBy(ASNDetailDataToMessageDTO::getDocumentid));
			
				// Itera sobre documentos pendientes de envío
				WSSentMessageResultDTO asnMessageData = null;
				for (DocumentW documentW : documentPendingArr) {
					// El sistema genera por cada factura listada la interfaz de ASN asociada
					asnMessageData = asnmessagemanager.doSendDocumentASNCreationMessage(documentW, vendorW.getId(), headerMap.get(documentW.getId()), detailMap.get(documentW.getId()), userdataDTO.getUsername(), userdataDTO.getUsertype());
					
					// Agregar respuesta al listado
					documentMessage = new WSSentMessageResponseDTO();
					BeanExtenderFactory.copyProperties(asnMessageData, documentMessage);
					asnuploadmessageList.add(documentMessage);
					
					asnsenterror = asnsenterror || asnMessageData.getSenterror();
				}
			}
			
			// Respuesta WS ASN
			resultDTO.setAsnsentmessagedto(asnuploadmessageList.toArray(new WSSentMessageResponseDTO[asnuploadmessageList.size()]));
						
			// Si todos los documentos fueron informados de forma exitosa y la cita está pendiente de envío
			if (!asnsenterror && !datingW.getSentstatus()) {
				WSSentMessageResultDTO datingMessageData = asnmessagemanager.doSendDatingCreationMessage(datingW, dvrdeliveryW, userdataDTO.getUsername(), userdataDTO.getUsertype());

				// Respuesta WS ASN
				documentMessage = new WSSentMessageResponseDTO();
				BeanExtenderFactory.copyProperties(datingMessageData, documentMessage);
				resultDTO.setDatingsentmessagedto(documentMessage);
				
				datingsenterror = datingMessageData.getSenterror();
			}
			
			// Agregar mensaje según resultados de los envíos 
			// Caso 1: ASN y Cita enviados correctamente
			if (!asnsenterror && !datingsenterror) {
				resultDTO.setStatusmessage("El envío de cita y ASN asociados fue exitoso");
			}
			// Caso 2: ASN enviados con error
			else if (asnsenterror) {
				resultDTO.setStatusmessage("Falló el envío de al menos un ASN. Favor proceder a su reenvío ");
			}
			// Caso 3: ASN enviados correctamente y cita con error
			else if (!asnsenterror && datingsenterror) {
				resultDTO.setStatusmessage("Los ASN han sido enviados con éxito, sin embargo no se puede enviar la interfaz de Cita. Favor proceder a su reenvío");
			}
						
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (NotFoundException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	
	public DdcDeliveryStateTypeArrayResultDTO getSelectableDdcDeliveryStateTypes() {
		DdcDeliveryStateTypeArrayResultDTO resultDTO = new DdcDeliveryStateTypeArrayResultDTO();

		try {
			// Obtener los estados seleccionables
			DdcDeliveryStateTypeW[] ddcdeliverystatetypes = ddcdeliverystatetypeserver.getByPropertyAsArray("selectable", true);
			if (ddcdeliverystatetypes == null || ddcdeliverystatetypes.length <= 0) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2251");
			}

			DdcDeliveryStateTypeDataDTO[] ddcdeliverystatetypedtos = new DdcDeliveryStateTypeDataDTO[ddcdeliverystatetypes.length];
			BeanExtenderFactory.copyProperties(ddcdeliverystatetypes, ddcdeliverystatetypedtos, DdcDeliveryStateTypeDataDTO.class);

			resultDTO.setDdcdeliverystatetypes(ddcdeliverystatetypedtos);
			
		} catch (Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BaseResultDTO doUpdateDdcDeliveryStateByDelivery(DdcDeliveryStateUpdateInitParamDTO initParamDTO, UserLogDataDTO userDataDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			if (initParamDTO.getDdcdeliveryid() == null) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible actualizar el estado de la Orden. Debe generar un despacho", false);
			}
			
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getVendorcode());
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Chequea la existencia del despacho ddc para ese proveedor
			DdcDeliveryW ddcDeliveryW = ddcdeliveryserver.getById(initParamDTO.getDdcdeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			if (!ddcDeliveryW.getVendorid().equals(vendorW.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2280");
			}
			
			// Validar que el despacho se encuentre en estado 'En Ruta'
			DdcDeliveryStateTypeW dstUndelivered = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCDELIVERY_STATE_UNDELIVERED);
			DdcDeliveryStateTypeW dstRejected = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCDELIVERY_STATE_REJECTED);
			DdcDeliveryStateTypeW dstDelivered = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCDELIVERY_STATE_DELIVERED);
			
			if (ddcDeliveryW.getCurrentstatetypeid().equals(dstUndelivered.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible actualizar el estado de la Orden. Debe generar un despacho", false);
			}
			else if (ddcDeliveryW.getCurrentstatetypeid().equals(dstRejected.getId()) ||
						ddcDeliveryW.getCurrentstatetypeid().equals(dstDelivered.getId())) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible actualizar el estado de la Orden, no se encuentra en un estado válido", false);
			}
			
			// Obtener los detalles del despacho
			DdcDeliveryDetailW[] ddcDeliveryDetails = ddcdeliverydetailserver.getByPropertyAsArray("ddcdelivery.id", ddcDeliveryW.getId());
			
			// Obtener la orden
			DdcOrderW ddcOrderW = ddcorderserver.getById(ddcDeliveryW.getDdcorderid(), LockModeType.PESSIMISTIC_WRITE);
			
			// Validar el comentario
			if (initParamDTO.getComment() == null || initParamDTO.getComment().equals("")) {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe ingresar un comentario", false);
			}
			
			// Validar que la fecha de reprogramación ingresada sea igual o posterior a la fecha de reprogramación anterior
			if (initParamDTO.getDeliverydate() != null) {
				if (initParamDTO.getDeliverydate().isBefore(ddcOrderW.getCurrentdeliverydate())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La fecha seleccionada debe ser posterior o igual a la fecha de reprogramación actual", false);
				}
				
				// Actualizar la fecha de reprogramación de la orden con la ingresada
				ddcOrderW.setCurrentdeliverydate(initParamDTO.getDeliverydate());
			}
			
			LocalDateTime now = LocalDateTime.now();
			
			// Validar que se haya seleccionado un estado de despacho válido
			// No Entregada
			if (initParamDTO.getDdcdeliverystatetypeid().equals(dstUndelivered.getId())) {
				// Liberar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setAvailableunits(0.0);
					ddcDeliveryDetail.setReceivedunits(0.0);
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
			}
			// Rechazada
			else if (initParamDTO.getDdcdeliverystatetypeid().equals(dstRejected.getId())) {
				// Liberar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setAvailableunits(0.0);
					ddcDeliveryDetail.setReceivedunits(0.0);
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
				
				// La orden cambiará a estado 'Rechazada'
				DdcOrderStateTypeW ostRejected = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_REJECTED);
				
				ddcOrderW.setCurrentstatetypedate(now);
				ddcOrderW.setCurrentstatetypewho(userDataDTO.getUsername());
				ddcOrderW.setCurrentstatetypecomment(initParamDTO.getComment());
				ddcOrderW.setCurrentstatetypeid(ostRejected.getId());
			}
			// Entregada			
			else if (initParamDTO.getDdcdeliverystatetypeid().equals(dstDelivered.getId())) {
				// Validar que se haya ingresado un fichero
				if (initParamDTO.getFilename() == null || initParamDTO.getFilename().equals("")) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe adjuntar un documento de recepción", false);					
				}
				
				// Validar que el fichero sea de tipo válido
				List<String> validTypes = Arrays.asList(LogisticConstants.VALID_FILE_TYPES);
				String fileType = initParamDTO.getFilename().substring(initParamDTO.getFilename().lastIndexOf('.') + 1);
				if (!validTypes.contains(fileType)) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Formato de documento no válido", false);
				}
				
				// Almacenar el fichero
				DdcFileW ddcFileW = new DdcFileW();
				ddcFileW.setWhen(now);
				ddcFileW.setFilename(initParamDTO.getFilename().substring(0, initParamDTO.getFilename().lastIndexOf(".")));
				ddcFileW.setFiletype(fileType);
				ddcFileW.setDdcorderid(ddcOrderW.getId());
				
				ddcfileserver.addDdcFile(ddcFileW);
								
				// Actualizar la fecha de entrega del despacho 
				if (initParamDTO.getDeliverydate() != null) {
					ddcDeliveryW.setCommitteddate(initParamDTO.getDeliverydate());
				}
				
				// Recepcionar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setReceivedunits(ddcDeliveryDetail.getAvailableunits());
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
				
				// La orden cambiará a estado 'Recepcionada'
				DdcOrderStateTypeW ostReceived = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_RECEIVED);
				
				ddcOrderW.setCurrentstatetypedate(now);
				ddcOrderW.setCurrentstatetypewho(userDataDTO.getUsername());
				ddcOrderW.setCurrentstatetypecomment(initParamDTO.getComment());
				ddcOrderW.setCurrentstatetypeid(ostReceived.getId());
			}
			// Otro estado
			else {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe seleccionar un estado válido", false);
			}
			
			// Actualizar el estado del despacho
			ddcDeliveryW.setCurrentstatetypedate(now);
			ddcDeliveryW.setCurrentstatetypewho(userDataDTO.getUsername());
			ddcDeliveryW.setCurrentstatetypecomment(initParamDTO.getComment());
			ddcDeliveryW.setCurrentstatetypeid(initParamDTO.getDdcdeliverystatetypeid());
			
			ddcDeliveryW = ddcdeliveryserver.updateDdcDelivery(ddcDeliveryW);
			
			DdcDeliveryStateW ddcDeliveryStateW = new DdcDeliveryStateW();
			ddcDeliveryStateW.setWhen(now);
			ddcDeliveryStateW.setUsername(userDataDTO.getUsername());
			ddcDeliveryStateW.setUsertype(userDataDTO.getUsertype());
			ddcDeliveryStateW.setStatedate(ddcDeliveryW.getCommitteddate());
			ddcDeliveryStateW.setComment(initParamDTO.getComment());
			ddcDeliveryStateW.setDdcdeliveryid(ddcDeliveryW.getId());
			ddcDeliveryStateW.setDdcdeliverystatetypeid(initParamDTO.getDdcdeliverystatetypeid());
			
			ddcdeliverystateserver.addDdcDeliveryState(ddcDeliveryStateW);
			
			// Actualizar la orden
			ddcOrderW = ddcorderserver.updateDdcOrder(ddcOrderW);			
			ddcorderserver.flush();
			
			DdcOrderStateW ddcOrderStateW = new DdcOrderStateW();
			ddcOrderStateW.setWhen(now);
			ddcOrderStateW.setWho(userDataDTO.getUsername());
			ddcOrderStateW.setComment(initParamDTO.getComment());
			ddcOrderStateW.setDdcorderid(ddcOrderW.getId());
			ddcOrderStateW.setDdcorderstatetypeid(ddcOrderW.getCurrentstatetypeid());
			
			ddcorderstateserver.addDdcOrderState(ddcOrderStateW);
			
			// Recalcular las unidades
			buyorderreportmanagerlocal.doCalculateTotalOfDdcOrders(new Long[] {ddcOrderW.getId()});
	
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	
	public AddDODeliveryOfDirectOrdersWSResultDTO doAddDODeliveryOfDirectOrdersWS(AddDODeliveryOfDirectOrdersWSInitParamDTO initParamDTO) {
		AddDODeliveryOfDirectOrdersWSResultDTO resultDTO = new AddDODeliveryOfDirectOrdersWSResultDTO();
		String error = "";
		
		LocalDateTime now = LocalDateTime.now();
		
		try {

			// Valida Ordenes repetidas 
			List<Long> ordernumbers = new ArrayList<Long>();
			
			// MAPAS
			// Almacena estado de las OC (OK o con errores)
			// key: Nro ORden
			// value: Mensaje de error
			Map<Long, String> resultOrdersMap = new HashMap<Long, String>();
			
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getRutproveedor().trim());
			if(vendorArr.length == 0) {
				error = "No se puede obtener el proveedor";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004", error);
			}
			VendorW vendorW = vendorArr[0]; 
			
			// Estado de entrega 'En Ruta'
			DdcDeliveryStateTypeW dstOnRoute = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCDELIVERY_STATE_ON_ROUTE);
			
			DdcDeliveryW ddcDeliveryW;
			DdcDeliveryStateTypeW dstCurrent;
			
			// Validaciones
			for (DirectOrdersWSInitParamDTO order : initParamDTO.getOrdenes()) {
				// Valida que orden exista 
				DdcOrderW[] ddcOrderArr = ddcorderserver.getByPropertyAsArray("number", order.getNumero());
				if(ddcOrderArr.length == 0) {
					error = "La orden de compra N° " + order.getNumero() + " no existe";
					resultOrdersMap.putIfAbsent(order.getNumero(), error);
					continue;
				}
				DdcOrderW ddcOrderW = ddcOrderArr[0]; 
				
				// Valida que no se informen OC repetidas
				if (!ordernumbers.contains(order.getNumero())) {
					ordernumbers.add(order.getNumero());
				} else {
					error = "La orden de compra N° " + order.getNumero() + " viene repetida";
					resultOrdersMap.putIfAbsent(order.getNumero(), error);
					continue;
				}
				
				// Validar el proveedor de la orden
				if (!ddcOrderW.getVendorid().equals(vendorW.getId())) {
					return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
				}
				
				// Validar si la orden tiene despachos asociados
				if (ddcOrderW.getCurrentddcdeliveryid() != null && ddcOrderW.getCurrentddcdeliveryid() > 0) {
					// Validar que esté vigente y no sea 'En Ruta'
					ddcDeliveryW = ddcdeliveryserver.getById(ddcOrderW.getCurrentddcdeliveryid());
					dstCurrent = ddcdeliverystatetypeserver.getById(ddcDeliveryW.getCurrentstatetypeid());
					
					if (dstCurrent.getClosed()) {
						error =  "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " no se encuentra en un estado válido";
						resultOrdersMap.putIfAbsent(order.getNumero(), error);
					}
					else if (dstCurrent.getId().equals(dstOnRoute.getId())) {
						error = "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " ya tiene un despacho vigente asociado";
						resultOrdersMap.putIfAbsent(order.getNumero(), error);
					}
				}
				
				// Validar que la orden tenga unidades pendientes
				if (ddcOrderW.getPendingunits() == 0) {
					error = "No es posible generar un despacho. La Orden " + ddcOrderW.getNumber() + " no tiene unidades pendientes";
					resultOrdersMap.putIfAbsent(order.getNumero(), error);
				}		
			}
					
			DdcOrderDetailW[] ddcOrderDetailWs;
			DdcDeliveryDetailW ddcDeliveryDetailW;
			List<Long> validDdcOrderIds = new ArrayList<Long>();
			
			// Crea despachos para OC sin errores
			for (DirectOrdersWSInitParamDTO order : initParamDTO.getOrdenes()) {
				
				if (resultOrdersMap.containsKey(order.getNumero())) {
					continue;
				}
				
				DdcOrderW ddcOrderW = ddcorderserver.getByPropertyAsSingleResult("number", order.getNumero());
				validDdcOrderIds.add(ddcOrderW.getId());

				// Generar un despacho en estado 'En Ruta' por el total de unidades pendientes
				ddcDeliveryW = new DdcDeliveryW();
				ddcDeliveryW.setNumber(ddcdeliveryserver.getNextSequenceDeliveryNumber());
				ddcDeliveryW.setOriginaldate(ddcOrderW.getCurrentdeliverydate());
				ddcDeliveryW.setCommitteddate(null);
				ddcDeliveryW.setCurrentstatetypedate(now);
				ddcDeliveryW.setCurrentstatetypewho("B2B Link");
				ddcDeliveryW.setCurrentstatetypecomment("Vía WS");
				ddcDeliveryW.setDdcorderid(ddcOrderW.getId());
				ddcDeliveryW.setCurrentstatetypeid(dstOnRoute.getId());
				ddcDeliveryW.setVendorid(vendorW.getId());
				
				ddcDeliveryW = ddcdeliveryserver.addDdcDelivery(ddcDeliveryW);
				
				// Agregar el estado del despacho
				DdcDeliveryStateW ddcDeliveryStateW = new DdcDeliveryStateW();
				ddcDeliveryStateW.setWhen(now);
				ddcDeliveryStateW.setUsername(WS_USER);
				ddcDeliveryStateW.setUsertype(WS_USER);
				ddcDeliveryStateW.setStatedate(ddcDeliveryW.getCommitteddate());
				ddcDeliveryStateW.setComment(WS_COMMENT);
				ddcDeliveryStateW.setDdcdeliveryid(ddcDeliveryW.getId());
				ddcDeliveryStateW.setDdcdeliverystatetypeid(dstOnRoute.getId());
				
				ddcdeliverystateserver.addDdcDeliveryState(ddcDeliveryStateW);
								
				// Generar detalles del despacho por todos aquellos SKU que presentan unidades pendientes mayores a cero
				PropertyInfoDTO[] props = new PropertyInfoDTO[2]; 
				props[0] = new PropertyInfoDTO("ddcorder.id", "ddcorderid", ddcOrderW.getId());
				props[1] = new PropertyInfoDTO("pendingunits", "pendingunits", 0.0, ComparisonOperator.GREATER_THAN);
				ddcOrderDetailWs = ddcorderdetailserver.getByPropertiesAsArray(props); 
				
				for (DdcOrderDetailW ddcOrderDetailW : ddcOrderDetailWs) {
					ddcDeliveryDetailW = new DdcDeliveryDetailW();
					ddcDeliveryDetailW.setPendingunits(ddcOrderDetailW.getPendingunits());
					ddcDeliveryDetailW.setAvailableunits(ddcOrderDetailW.getPendingunits());
					ddcDeliveryDetailW.setReceivedunits(0.0);
					ddcDeliveryDetailW.setDdcdeliveryid(ddcDeliveryW.getId());
					ddcDeliveryDetailW.setItemid(ddcOrderDetailW.getItemid());
					ddcDeliveryDetailW.setPosition(ddcOrderDetailW.getPosition());
					
					ddcdeliverydetailserver.addDdcDeliveryDetail(ddcDeliveryDetailW);
				}
				
				// Actualizar la orden con su último despacho
				ddcOrderW.setCurrentddcdeliveryid(ddcDeliveryW.getId());
				ddcOrderW = ddcorderserver.updateDdcOrder(ddcOrderW);			
				ddcorderserver.flush();
				
				// Aceptar OC DDC
				buyorderreportmanagerlocal.doAcceptDdcOrder(ddcOrderW.getId(), WS_USER, WS_USER, WS_COMMENT);
				ddcorderserver.flush();
				
				// Agrega a mapa ordenes a las que se crearon despacho de forma exitosa
				resultOrdersMap.putIfAbsent(order.getNumero(), "OK");
			}
			
			// Recalcular las unidades de las órdenes			 
			buyorderreportmanagerlocal.doCalculateTotalOfDdcOrders((Long[]) validDdcOrderIds.toArray(new Long[validDdcOrderIds.size()]));
		
			// Carga datos de salida
			AddDODeliveryOfDirectOrdersWSResultDetailDTO[] details = new AddDODeliveryOfDirectOrdersWSResultDetailDTO[resultOrdersMap.size()];
			Iterator<Long> it = resultOrdersMap.keySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Long numeroOC = (Long) it.next();
				String description = resultOrdersMap.get(numeroOC);
				String code = "OK".equals(description) ? "0" : "1";
				details[i] = new AddDODeliveryOfDirectOrdersWSResultDetailDTO(numeroOC, code, description);
				i++;
			}
			
			resultDTO.setDetails(details);
			
		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
		}
		
		return resultDTO;
	}
	
	
	public UpdateDODeliveryWSResultDTO doUpdateDODeliveryWS(UpdateDODeliveryWSInitParamDTO initParamDTO) {

		UpdateDODeliveryWSResultDTO resultDTO = new UpdateDODeliveryWSResultDTO();
		String error;
		
		LocalDateTime now = LocalDateTime.now();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
		
		LocalDateTime newDeliveryDate = LocalDateTime.now();
		
		try {
			
			// Estados Despachos
			DdcDeliveryStateTypeW dstUndelivered = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", "NO_ENTREGADO");
			DdcDeliveryStateTypeW dstRejected 	 = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADO");
			DdcDeliveryStateTypeW dstDelivered 	 = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", "ENTREGADO");
			DdcDeliveryStateTypeW dstOnRoute	 = ddcdeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");
			
			// Estado OC
			DdcOrderStateTypeW ostRejected = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
			
			// Obtener el proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", initParamDTO.getRutproveedor() );
			if(vendorArr.length == 0){
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}
			VendorW vendorW = vendorArr[0];
			
			// Valida que exista OC y esté asociada al proveedor (hacer solo una validación)
			PropertyInfoDTO[] props = new PropertyInfoDTO[2];
			props[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
			props[1] = new PropertyInfoDTO("number", "number", initParamDTO.getNumeroOrden());
			
			DdcOrderW[] ddcorderArr = ddcorderserver.getByPropertiesAsArray(props);
			if(ddcorderArr.length == 0) {
				error = "No existe la OC N° " + initParamDTO.getNumeroOrden() + " asociada al proveedor código " + vendorW.getCode(); 
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004", error);
			}
			DdcOrderW ddcOrderW = ddcorderArr[0];
			
			// Valida que la OC posee un despacho
			DdcDeliveryW ddcdeliveryW = null;
			if (ddcOrderW.getCurrentddcdeliveryid() == null || ddcOrderW.getCurrentddcdeliveryid() <= 0) {
				error = "La OC N° " + initParamDTO.getNumeroOrden() + " no tiene despacho creado para actualizar";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
			}

			// Exsite despacho, validar que la OC posea un despacho actual en estado 'En Ruta'
			else {
				ddcdeliveryW = ddcdeliveryserver.getById(ddcOrderW.getCurrentddcdeliveryid());
				if (ddcdeliveryW.getCurrentstatetypeid().longValue() != dstOnRoute.getId().longValue()) {
					error = "La OC N° " + initParamDTO.getNumeroOrden() + " no posee un despacho en estado 'En Ruta'";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
				}
			}
			
			// Validar que el comentario ingresado no supere la cantidad de caracteres definidos
			if (initParamDTO.getComentario() == null)
				initParamDTO.setComentario("");

			if (initParamDTO.getComentario().length() > 200) {
				getSessionContext().setRollbackOnly();
				error = "El comentario indicado superó la cantidad de caracteres permitidos";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
			}
			
			// Validar que la fecha de reprogramación ingresada sea igual o posterior a la fecha de reprogramación anterior
			if (initParamDTO.getFechaEntrega() != null) {
				try {
					newDeliveryDate = LocalDate.parse(initParamDTO.getFechaEntrega(), dtf).atStartOfDay();
				} catch (DateTimeException e) {
					e.printStackTrace();
					error = "El formato de la fecha no es válido";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
				}
				
				if (newDeliveryDate.isBefore(ddcOrderW.getCurrentdeliverydate())) {
					error = "La fecha seleccionada debe ser posterior o igual a la fecha de reprogramación actual";
					return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
				}
			}

			// Valida que el estado del despacho informado  existe
			DdcDeliveryStateTypeW[] stateInformedArr = ddcdeliverystatetypeserver.getByPropertyAsArray("codews", initParamDTO.getNuevoestado());
			if(stateInformedArr.length == 0) {
				error = "El estado '" + initParamDTO.getNuevoestado() + "' no existe";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);				
			}
			DdcDeliveryStateTypeW stateInformedW =  stateInformedArr[0];
			
			// Obtener los detalles del despacho
			DdcDeliveryDetailW[] ddcDeliveryDetails = ddcdeliverydetailserver.getByPropertyAsArray("ddcdelivery.id", ddcdeliveryW.getId());
						
			// Validar que se haya seleccionado un estado de despacho válido
			// - No Entregada
			if (stateInformedW.getId().equals(dstUndelivered.getId())) {
				// Liberar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setAvailableunits(0.0);
					ddcDeliveryDetail.setReceivedunits(0.0);
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
			}
			
			// - Rechazada
			else if (stateInformedW.getId().equals(dstRejected.getId())) {
				// Liberar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setAvailableunits(0.0);
					ddcDeliveryDetail.setReceivedunits(0.0);
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
				
				// La orden cambiará a estado 'Rechazada'
				ddcOrderW.setCurrentstatetypedate(now);
				ddcOrderW.setCurrentstatetypewho(WS_USER);
				ddcOrderW.setCurrentstatetypecomment(initParamDTO.getComentario());
				ddcOrderW.setCurrentstatetypeid(ostRejected.getId());				
			}
			
			// - Entregada		
			else if (stateInformedW.getId().equals(dstDelivered.getId())) {
//				// Validar que se haya ingresado un fichero
//				if (initParamDTO.getActaentrega() == null || initParamDTO.getActaentrega().equals("")) {
//					error = "Debe adjuntar un documento de recepción";
//					return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
//				}
//	
				String filename;
				if(initParamDTO.getActaentrega() != null && (!initParamDTO.getActaentrega().equals(""))) {
					try {
						// Almacenar imagen en disco
						filename = String.valueOf(System.nanoTime()) + "." + LogisticConstants.getACTAENTREGA_EXTENSION_IMAGE();
						byte[] decoded = Base64.getDecoder().decode(initParamDTO.getActaentrega());
				        Path path = Paths.get(LogisticConstants.getACTAENTREGA_FOLDER_IMAGE() + File.separator + filename);
				        Files.write(path, decoded);
						
					} catch (Exception e) {
						//getSessionContext().setRollbackOnly();
						error = "No es posible almacenar la imagen";
						return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
					}
					
					// Almacenar el fichero
					DdcFileW ddcFileW = new DdcFileW();
					ddcFileW.setWhen(now);
					ddcFileW.setFilename(filename);
					ddcFileW.setFiletype("");
					ddcFileW.setDdcorderid(ddcOrderW.getId());
					
					ddcfileserver.addDdcFile(ddcFileW);
				}
				
				// Actualizar la fecha de entrega del despacho (se validó previamente que exista) 
				ddcdeliveryW.setCommitteddate(newDeliveryDate);
				
				// Recepcionar las unidades asociadas al despacho
				for (DdcDeliveryDetailW ddcDeliveryDetail : ddcDeliveryDetails) {
					ddcDeliveryDetail.setReceivedunits(ddcDeliveryDetail.getAvailableunits());
					ddcdeliverydetailserver.updateDdcDeliveryDetail(ddcDeliveryDetail);
				}
				ddcdeliverydetailserver.flush();
				
				// La orden cambiará a estado 'Recepcionada'
				DdcOrderStateTypeW ostReceived = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_RECEIVED);
				
				ddcOrderW.setCurrentstatetypedate(now);
				ddcOrderW.setCurrentstatetypewho(WS_USER);
				ddcOrderW.setCurrentstatetypecomment(initParamDTO.getComentario());
				ddcOrderW.setCurrentstatetypeid(ostReceived.getId());
			}
			
			// Otro estado -> error
			else {
				error = "Debe seleccionar un estado válido";
				return LogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L7004", error);
			}
			
			// Actualizar el estado del despacho
			ddcdeliveryW.setCurrentstatetypedate(now);
			ddcdeliveryW.setCurrentstatetypewho(WS_USER);
			ddcdeliveryW.setCurrentstatetypecomment(initParamDTO.getComentario());
			ddcdeliveryW.setCurrentstatetypeid(stateInformedW.getId());
			
			ddcdeliveryW = ddcdeliveryserver.updateDdcDelivery(ddcdeliveryW);
			
			DdcDeliveryStateW ddcDeliveryStateW = new DdcDeliveryStateW();
			ddcDeliveryStateW.setWhen(now);
			ddcDeliveryStateW.setUsername(WS_USER);
			ddcDeliveryStateW.setUsertype(WS_USER);
			ddcDeliveryStateW.setStatedate(ddcdeliveryW.getCommitteddate());
			ddcDeliveryStateW.setComment(initParamDTO.getComentario());
			ddcDeliveryStateW.setDdcdeliveryid(ddcdeliveryW.getId());
			ddcDeliveryStateW.setDdcdeliverystatetypeid(stateInformedW.getId());
			
			ddcdeliverystateserver.addDdcDeliveryState(ddcDeliveryStateW);

			// Actualizar la fecha de reprogramación de la orden con la ingresada
			ddcOrderW.setCurrentdeliverydate(newDeliveryDate);

			// Actualizar la orden
			ddcOrderW = ddcorderserver.updateDdcOrder(ddcOrderW);			
			ddcorderserver.flush();
			
			DdcOrderStateW ddcOrderStateW = new DdcOrderStateW();
			ddcOrderStateW.setWhen(now);
			ddcOrderStateW.setWho(WS_USER);
			ddcOrderStateW.setComment(initParamDTO.getComentario());
			ddcOrderStateW.setDdcorderid(ddcOrderW.getId());
			ddcOrderStateW.setDdcorderstatetypeid(ddcOrderW.getCurrentstatetypeid());
			
			ddcorderstateserver.addDdcOrderState(ddcOrderStateW);
			
			// Recalcular las unidades
			buyorderreportmanagerlocal.doCalculateTotalOfDdcOrders(new Long[] {ddcOrderW.getId()});
	
			resultDTO.setStatuscode("0");
			resultDTO.setStatusmessage("OK");
			
		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
		}
		
		return resultDTO;
	}
	
}

