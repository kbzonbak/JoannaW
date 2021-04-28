package bbr.b2b.regional.logistic.deliveries.managers.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.persistence.LockModeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.Channel;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.codehaus.xfire.util.dom.DOMOutHandler;
import org.jboss.annotation.IgnoreDependency;
import org.jboss.ejb3.annotation.TransactionTimeout;
import org.tempuri.AdmisionTO;
import org.tempuri.RespuestaAdmisionTO;
import org.tempuri.ServicioExternoAdmisionCEPClient;
import org.tempuri.ServicioExternoAdmisionCEPSoap;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.classes.VeVAuditServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVUpdateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.DirectOrderReportManagerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.ChileExpressTagServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.ChilexpressCourierStateTmpServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CommuneCourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CorreosChileTagServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierFileServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierScheduleLogServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierStateServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTagServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTicketDetailServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTicketServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.HiredCourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.RescheduleReasonServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.VendorAddressServerLocal;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChileExpressTagW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChilexpressCourierStateTmpW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CommuneCourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CorreosChileTagW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierFileW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierScheduleLogW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierStateW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTagW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketDetailW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTicketW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.HiredCourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.VendorAddressW;
import bbr.b2b.regional.logistic.couriers.report.classes.ChileExpressTagResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CorreosChileCSVDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CorreosChileCSVResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierExcelUploadDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierFileDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierScheduleLogArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierScheduleLogDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierScheduleLogInitParamDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierStateFileDataDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.RescheduleReasonArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.RescheduleReasonDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryImageServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DOReceptionTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryDetailPK;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryImageW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DOReceptionTypeW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerRemote;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.AddDODeliveryOfDirectOrdersWSResultDetailDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierFileReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierFileReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierStateReportDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierStateReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.CourierStateReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryDetailReportTotalDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryResultBean;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliverySourceDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliverySourceDataResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryStateTypeDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryWSRequestData;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryWSRequestDataResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryCourierChilexpressInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryCourierCorreosDeChileInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryIdsByPagesW;
import bbr.b2b.regional.logistic.deliveries.report.classes.DirectOrderDetailsWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DirectOrderReprogDateDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DirectOrdersWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DoChangeDODeliveryStateTypeInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DownloadDeliveryReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ExcelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ExcelDODeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ItemUnitsDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.LabelDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.LabelDODeliveryReportInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.LabelDODeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryDetailReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryDetailReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryDetailReportResultListResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PDFDODeliveryReportDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ShippingCertificationArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ShippingCertificationDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ShippingCertificationInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryDispatcherEmail;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryWSInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDODeliveryWSResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateDispatcherEmailResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UpdateUnitsOfDODeliveryDetailsInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadDODeliveryUpdatesInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadDODeliveryUpdatesResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.UploadDeliveryCourierInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.WithdrawalNumberDTO;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderDetailServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevPDServerLocal;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportDataDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.report.classes.UserDataInitParamDTO;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.TaxDocumentReportManagerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.Base64Utils;
import bbr.b2b.regional.logistic.utils.EmailValidation;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import cl.chilexpress.integracionasistida.IntegracionAsistidaClient;
import cl.chilexpress.integracionasistida.IntegracionAsistidaHandler;
import cl.chilexpress.integracionasistida.IntegracionAsistidaPT;
import cl.chilexpress.integracionasistida.IntegracionAsistidaRequest;
import cl.chilexpress.integracionasistida.IntegracionAsistidaResponse;
import cl.chilexpress.ws.osb.ebo.headerrequest.DatosHeaderRequest;
import cl.chilexpress.ws.osb.ebo.headerresponse.DatosHeaderResponse;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.DestinatarioType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.DireccionType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.GenerarIntegracionAsistidaRequestType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.PiezaType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.RemitenteType;
import cl.chilexpress.ws.osb.interno.com.generarintegracionasistidaresp.GenerarIntegracionAsistidaResponseType;

@Stateless(name = "managers/DODeliveryReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DODeliveryReportManager implements DODeliveryReportManagerLocal, DODeliveryReportManagerRemote {

	private static Logger chileexpressStateslog = Logger.getLogger("chilexpressStateslog");

	private static JAXBContext jc1887 = null;

	private static JAXBContext jc1879 = null;

	private static JAXBContext jc1846 = null;

	private static JAXBContext getJC1887() throws JAXBException {
		if (jc1887 == null)
			jc1887 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1887");
		return jc1887;
	}

	private static JAXBContext getJC1879() throws JAXBException {
		if (jc1879 == null)
			jc1879 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1879");
		return jc1879;
	}

	private static JAXBContext getJC1846() throws JAXBException {
		if (jc1846 == null)
			jc1846 = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.int1846");
		return jc1846;
	}

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	private static Logger logger = Logger.getLogger(DODeliveryReportManager.class);

	@EJB
	DirectOrderDetailServerLocal directorderdetailserver;

	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	DirectOrderStateServerLocal directorderstateserver;

	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;

	@EJB
	DODeliveryDetailServerLocal dodeliverydetailserver;

	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	DODeliveryServerLocal dodeliveryserver;

	@EJB
	DODeliveryStateServerLocal dodeliverystateserver;

	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;

	@EJB
	DODeliveryImageServerLocal dodeliveryimageserver;

	@EJB
	DOReceptionTypeServerLocal doreceptiontypeserver;

	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	ChileExpressTagServerLocal chileexpresstagserver;

	@EJB
	CorreosChileTagServerLocal correoschiletagserver;

	@EJB
	CourierServerLocal courierserver;

	@EJB
	VendorAddressServerLocal vendoraddressserver;

	@EJB
	HiredCourierServerLocal hiredcourierserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	KPIvevPDServerLocal kpivevpdserver;

	@EJB
	CourierTicketServerLocal courierticketserver;

	@EJB
	CourierTicketDetailServerLocal courierticketdetailserver;

	@EJB
	CourierTagServerLocal couriertagserver;

	@EJB
	CourierStateServerLocal courierstateserver;

	@EJB
	CourierFileServerLocal courierfileserver;

	@EJB
	CommuneCourierServerLocal communecourierserver;

	@EJB
	RescheduleReasonServerLocal reschedulereasonserver;

	@EJB
	CourierScheduleLogServerLocal courierschedulelogserver;

	@EJB
	ChilexpressCourierStateTmpServerLocal chilexpresscourierstatetmpserver;
	
	@EJB
	TaxDocumentServerLocal taxdocumentserver;
	
	@EJB
	VeVAuditServerLocal vevauditserverlocal;
	
	@EJB
	VeVUpdateTypeServerLocal vevupdatetypeserverlocal;
	
	@EJB
	@IgnoreDependency
	DirectOrderReportManagerLocal directorderreportmanager;

	@EJB
	TaxDocumentReportManagerLocal taxdocumentreportmanager;
	
	@EJB
	@IgnoreDependency
	SchedulerManagerLocal schedulermanager;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public AddDODeliveryOfDirectOrdersResultDTO doAddDODeliveryOfDirectOrders(AddDODeliveryOfDirectOrdersInitParamDTO initParamDTO, boolean byExcel) {
		AddDODeliveryOfDirectOrdersResultDTO resultDTO = new AddDODeliveryOfDirectOrdersResultDTO();

		try {
			Date now = new Date();

			List<Long> ordernumbers = new ArrayList<Long>();
			List<Long> orderIds = new ArrayList<Long>();

			boolean val1 = false;
			boolean sendMsg = false;

			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
			List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
			String error = "";

			DirectOrderStateTypeW dostReleased = directorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
			DirectOrderStateTypeW dostParisModified = directorderstatetypeserver.getByPropertyAsSingleResult("code", "MOD_PARIS");
			DirectOrderStateTypeW dostVendorRescheduled = directorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROG_PROV");

			DODeliveryStateTypeW dodstTagPendingRequest = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
			DODeliveryStateTypeW dodstTagRequested = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_SOLICITADA");
			DODeliveryStateTypeW dodstSchedulePending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");
			DODeliveryStateTypeW dodstWithdrawalPending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_RETIRO");
			DODeliveryStateTypeW dodstOnRoute = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");

			// Obtener el proveedor
					
			VendorW[] vendors = null;
			VendorW vendor;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendor = vendors[0];
			
			
			// JPE 20180528
			// Valida si el proveedor est� asociado a un Courier
			List<HiredCourierW> hiredCouriers = hiredcourierserver.getByProperty("vendor.id", vendor.getId());
						
			CourierW courier = null;
			if (hiredCouriers != null && hiredCouriers.size() > 0) {
				if (hiredCouriers.size() > 1) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Su empresa tiene configurada más de una empresa de Courier. Favor cont�ctese con el administrador para regularizar este problema", false);
				}
				
				// Obtener el Courier asociado
				courier = courierserver.getByPropertyAsSingleResult("id", hiredCouriers.get(0).getCourierid());
				if (courier == null) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				
				// Validar que el proveedor tiene completa su libreta de direcciones para el Courier asociado
				PropertyInfoDTO prop1 = new PropertyInfoDTO("hiredcourier.vendor.id", "vendorid", vendor.getId());
				PropertyInfoDTO prop2 = new PropertyInfoDTO("hiredcourier.courier.id", "courierid", courier.getId());
				List<VendorAddressW> vaList = vendoraddressserver.getByProperties(prop1, prop2);
				
				if (vaList != null && vaList.size() > 1) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				
				// Validar que el proveedor tenga configurada información básica para despachar con una empresa Courier
				if (vaList == null || vaList.size() < 1) {
					error = "Debe ingresar información básica para Dirección de devolución de productos y Dimensiones, para ello haga clic " + //
									"en la Libreta de Configuraciones para Courier, la cual se encuentra en menú Logística > " + //
									"Venta en Verde PD > Despachos VEV";
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", error, false);
				}
				
				VendorAddressW vendoraddressW = vaList.get(0);
				
				// Validar que la antig�edad de la información básica para despachar no supere la cantidad definida de meses
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("vendoraddressupdatemonths")));
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				if (vendoraddressW.getUpdatedate().before(cal.getTime())) {
					error = "Estimado Proveedor, por motivos de mantenimiento de información es requerido que actualice registro de datos en " + //
							"Libreta de Configuraciones para Courier, la cual se encuentra en menú Logística > Venta en Verde PD > " + //
							"Despachos VEV. En caso de no requerir cambiar datos ser� necesario de igual forma que ingrese y seleccione " + //
							"'Modificar y Guardar'"; //
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", error, false);
				}
			}
			
			if (byExcel) {
				// Validar que existan los N°meros de OC asociados al proveedor
				DirectOrderW[] orderArr = null;
				PropertyInfoDTO prop1 = null;
				PropertyInfoDTO prop2 = null;
				for (DirectOrderReprogDateDTO data : initParamDTO.getData()) {
					// Validar formato de email
					if (((!data.getDispatcheremail().equals("")) && data.getDispatcheremail() != null) && (!EmailValidation.isValid(data.getDispatcheremail()))) {
						error = "Fila " + data.getRownumber() + ": El dato del correo del proveedor " + data.getOrdernumber() + " no es válido";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					
					// Validar que la orden no se repita en más de una l�nea
					if (!ordernumbers.contains(data.getOrdernumber())) {
						ordernumbers.add(data.getOrdernumber());
						
						// Obtener la orden directa
						prop1 = new PropertyInfoDTO("number", "directordernumber", data.getOrdernumber());
						prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
						orderArr = directorderserver.getByPropertiesAsArray(prop1, prop2);

						// Validar que la orden pertenezca al proveedor del filtro
						if (orderArr == null || orderArr.length == 0) {
							error = "Fila " + data.getRownumber() + ": La orden " + data.getOrdernumber() + " no corresponde con el proveedor escogido en el filtro";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));

							Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
							resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
						}

						data.setOrderid(orderArr[0].getId());
					}
					else {
						error = "Fila " + data.getRownumber() + ": La orden de compra N° " + data.getOrdernumber() + " se repite en más de una l�nea";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));

						Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
						resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
					}
				}
			}

			Calendar rightNow = Calendar.getInstance();
			rightNow.set(Calendar.HOUR_OF_DAY, 0);
			rightNow.set(Calendar.MINUTE, 0);
			rightNow.set(Calendar.SECOND, 0);
			rightNow.set(Calendar.MILLISECOND, 0);

			List<DODeliveryW> arrDeliveries = new ArrayList<DODeliveryW>();
			List<Long> arrDeliverieIds = new ArrayList<Long>();

			// Obtener los tipos de auditor�a VeV 'Cambio estado OC VeV' y 'Cambio estado despacho VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");
			
			DODeliveryW delivery = null;
			DirectOrderStateTypeW dost = null;
			for (DirectOrderReprogDateDTO data : initParamDTO.getData()) {
				sendMsg = false;

				orderIds.add(data.getOrderid());

				DirectOrderW directOrder = directorderserver.getById(data.getOrderid(), LockModeType.PESSIMISTIC_WRITE);

				// Validar consistencia de proveedor
				if (!val1 && (!vendor.getId().equals(directOrder.getVendorid()))) {
					error = "Las órdenes seleccionadas no corresponden con el proveedor escogido en el filtro";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					val1 = true;
				}

				// Validar que la OC est� en un estado vigente
				DirectOrderStateTypeW statetype = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
				if (!statetype.getValid()) {
					error = "La orden " + directOrder.getNumber() + " no se encuentra en un estado vigente";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				// Por cada orden, se buscan los detalles de orden
				// Por cada detalle con unidades pendientes se crea un detalle de despacho correspondiente
				DirectOrderDetailW[] details = directorderdetailserver.getDirectOrderDetailDataofDirectOrder(directOrder.getId());
				List<DODeliveryDetailW> detailsArr = new ArrayList<DODeliveryDetailW>();
				for (int k = 0; k < details.length; k++) {
					DirectOrderDetailW detail = details[k];
					if (detail.getPendingunits() > 0) {
						DODeliveryDetailW oddetail = new DODeliveryDetailW();
						oddetail.setItemid(detail.getItemid());
						oddetail.setAvailableunits(detail.getPendingunits());
						oddetail.setPendingunits(detail.getPendingunits());
						oddetail.setReceivedunits(0D);

						// Agregar el detalle al arreglo
						detailsArr.add(oddetail);
					}
				}

				// Validar que la OC tiene unidades pendientes mayores a cero
				if (detailsArr.size() == 0) {
					error = "La orden " + directOrder.getNumber() + " no presenta unidades pendientes por entregar";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}

				// JPE 20180405
				// Si el proveedor est� asociado a un Courier
				if (courier != null) {
					// JPE 20180516
					// Validar que la OC no tenga despachos actuales en estados 'Etiqueta por Solicitar', 'Etiqueta Solicitada',
					// 'Pendiente de Agendar', 'Pendiente de Retiro' o 'En Ruta'
					DODeliveryW[] deliveries = dodeliveryserver.getByPropertyAsArray("directorder.id", data.getOrderid());
					for (int i = 0; i < deliveries.length; i++) {
						if (deliveries[i].getCurrentstatetypeid().longValue() == dodstTagPendingRequest.getId().longValue() ||
								deliveries[i].getCurrentstatetypeid().longValue() == dodstTagRequested.getId().longValue() ||
								deliveries[i].getCurrentstatetypeid().longValue() == dodstSchedulePending.getId().longValue() ||
								deliveries[i].getCurrentstatetypeid().longValue() == dodstWithdrawalPending.getId().longValue() ||
								deliveries[i].getCurrentstatetypeid().longValue() == dodstOnRoute.getId().longValue()) {
							error = "La orden de compra N° " + directOrder.getNumber() + " posee un despacho en curso";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}

					// Si hay errores hasta el momento, no se puede continuar
					if (baseresultlist.size() > 0) {
						// Ordenar errores
						Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
						resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6007");
					}

					// Crear un lote de despacho asociado al tipo de flujo, proveedor y tipo de orden
					delivery = new DODeliveryW();
					delivery.setCommiteddate(directOrder.getCurrentdeliverydate());
					delivery.setOriginaldate(directOrder.getCurrentdeliverydate());
					delivery.setCurrentstdate(now);
					delivery.setCurrentstatetypeid(dodstTagPendingRequest.getId());
					delivery.setCurrentstwho(initParamDTO.getUsername());
					delivery.setCurrentstcomment("");
					delivery.setOrderid(directOrder.getId());
					delivery.setVendorid(vendor.getId());
					delivery.setNumber(deliveryserver.getNextSequenceDeliveryNumber());
					delivery.setDispatcheremail(data.getDispatcheremail());

					delivery = dodeliveryserver.addDODelivery(delivery);

					// Agregar el estado del despacho
					DODeliveryStateW doDeliveryState = new DODeliveryStateW();
					doDeliveryState.setDeliveryid(delivery.getId());
					doDeliveryState.setDeliverystatetypeid(dodstTagPendingRequest.getId());
					doDeliveryState.setWhen(now);
					doDeliveryState.setDeliverystatedate(delivery.getCurrentstdate());
					doDeliveryState.setWho(initParamDTO.getUsername());
					doDeliveryState.setComment("");

					dodeliverystateserver.addDODeliveryState(doDeliveryState);
					
					// JPE 20200615
					// Registro de auditor�a
					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado Despacho VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata("");
					vevAudit.setFinaldata(dodstTagPendingRequest.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);

					// Agregar los detalles de despacho
					for (Iterator iter = detailsArr.iterator(); iter.hasNext();) {
						DODeliveryDetailW oddetail = (DODeliveryDetailW) iter.next();
						oddetail.setDodeliveryid(delivery.getId());
						dodeliverydetailserver.addDODeliveryDetail(oddetail);
					}

					// Aceptar la orden autom�ticamente si est� en estado 'Liberada' o 'Modificada Paris'
					if (directOrder.getCurrentstatetypeid().equals(dostReleased.getId()) ||
							directOrder.getCurrentstatetypeid().equals(dostParisModified.getId())) {
						directOrder = directorderreportmanager.doAcceptDirectOrders(directOrder.getId())[0];
					}
					
					// Actualizar la orden con el despacho creado
					directOrder.setDodeliveryid(delivery.getId());
					directOrder = directorderserver.updateDirectOrder(directOrder);

					// Agregar el lote al mapa
					arrDeliveries.add(delivery);
					arrDeliverieIds.add(delivery.getId());
				}
				// Si el proveedor no est� asociado a un Courier
				else {
					// JMA 20150701
					// Validar que la OC no tenga despachos actuales en estado 'En Ruta'
					DODeliveryW[] deliveries = dodeliveryserver.getByPropertyAsArray("directorder.id", data.getOrderid());
					for (int i = 0; i < deliveries.length; i++) {
						if (deliveries[i].getCurrentstatetypeid().longValue() == dodstOnRoute.getId().longValue()) {
							error = "La orden de compra N° " + directOrder.getNumber() + " posee un despacho en curso";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}

					// Si hay errores hasta el momento, no se puede continuar
					if (baseresultlist.size() > 0) {
						// Ordenar errores
						Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
						resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
						getSessionContext().setRollbackOnly();
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6007");
					}

					Date reprogdate = DateConverterFactory2.StrToDate(data.getReprogdate());

					// Crear un lote de despacho asociado al tipo de flujo, proveedor y tipo de orden
					// IRA 20091222
					// Cuando el estado es En Ruta, se setea la fecha en que se hizo el despacho
					delivery = new DODeliveryW();
					delivery.setCommiteddate(reprogdate);
					delivery.setOriginaldate(reprogdate);					
					delivery.setCurrentstdate(now);
					delivery.setCurrentstatetypeid(dodstOnRoute.getId());
					delivery.setCurrentstwho(initParamDTO.getUsername());
					delivery.setCurrentstcomment("");
					delivery.setOrderid(directOrder.getId());
					delivery.setVendorid(vendor.getId());
					delivery.setNumber(deliveryserver.getNextSequenceDeliveryNumber());
					delivery.setDispatcheremail(data.getDispatcheremail());

					delivery = dodeliveryserver.addDODelivery(delivery);

					// Agregar el estado del despacho
					DODeliveryStateW doDeliveryState = new DODeliveryStateW();
					doDeliveryState.setDeliveryid(delivery.getId());
					doDeliveryState.setDeliverystatetypeid(dodstOnRoute.getId());
					doDeliveryState.setWhen(now);
					doDeliveryState.setDeliverystatedate(delivery.getCurrentstdate());
					doDeliveryState.setWho(initParamDTO.getUsername());
					doDeliveryState.setComment("");

					dodeliverystateserver.addDODeliveryState(doDeliveryState);
					
					// JPE 20200615
					// Registro de auditor�a
					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado Despacho VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata("");
					vevAudit.setFinaldata(dodstOnRoute.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);

					// Agregar los detalles de despacho
					for (Iterator iter = detailsArr.iterator(); iter.hasNext();) {
						DODeliveryDetailW oddetail = (DODeliveryDetailW) iter.next();
						oddetail.setDodeliveryid(delivery.getId());
						dodeliverydetailserver.addDODeliveryDetail(oddetail);
					}

					// Agregar el lote al mapa
					arrDeliveries.add(delivery);

					// Se acepta la orden autom�ticamente si est� en estado 'Liberada' o 'Modificada Paris'
					if (directOrder.getCurrentstatetypeid().equals(dostReleased.getId()) ||
							directOrder.getCurrentstatetypeid().equals(dostParisModified.getId())) {
						directOrder = directorderreportmanager.doAcceptDirectOrders(directOrder.getId())[0];
					}

					// Si la fecha de reprogramación es distinta a la de la orden correspondiente, cambiar el estado
					// a Reprogramada Proveedor, con el comentario 'Reprogramada al momento del despacho'
					if (!directOrder.getCurrentdeliverydate().equals(reprogdate)) {
						dost = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
						
						// Actualizar la orden
						directOrder.setCurrentstatetypedate(now);
						directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
						directOrder.setCurrentstatetypecomment("Reprogramada al momento del despacho");
						directOrder.setCurrentstatetypeid(dostVendorRescheduled.getId());
						directOrder.setCurrentdeliverydate(reprogdate);
						
						DirectOrderStateW directOrderState = new DirectOrderStateW();
						directOrderState.setOrderid(directOrder.getId());
						directOrderState.setOrderstatetypeid(dostVendorRescheduled.getId());
						directOrderState.setWhen(now);
						directOrderState.setWho(initParamDTO.getUsername());
						directOrderState.setComment("Reprogramada al momento del despacho");
						directorderstateserver.addDirectOrderState(directOrderState);

						// 20200611
						// Registro de auditor�a
						vevAudit = new VeVAuditW();
						vevAudit.setWhen(now);
						vevAudit.setUsername(initParamDTO.getUsername());
						vevAudit.setUsertype(initParamDTO.getUsertype());
						vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
						vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
						vevAudit.setInterfacecontent("");
						vevAudit.setItem("Estado OC VeV PD");
						vevAudit.setItemvalue(directOrder.getNumber().toString());
						vevAudit.setInitialdata(dost.getName());
						vevAudit.setFinaldata(dostVendorRescheduled.getName());
						vevAudit.setVendorid(directOrder.getVendorid());
						vevAudit.setVevupdatetypeid(utOrderState.getId());					
						
						vevauditserverlocal.addVeVAudit(vevAudit);

						// Enviar mensaje para informar cambio de fecha de entrega
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
						doSendInt1887(directOrder, "Cambio Fecha B2B", sdf.format(reprogdate), "", null);
					}

					// Validar si la orden no tiene despacho actual
					if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
						sendMsg = true;
					}

					directOrder.setDodeliveryid(delivery.getId());
					directOrder = directorderserver.updateDirectOrder(directOrder);

					// Enviar mensaje de OC en ruta
					if (sendMsg) {
						doSendInt1846(directOrder, delivery, detailsArr.toArray(new DODeliveryDetailW[detailsArr.size()]), details, "1");
					}
				}
			}
			dodeliveryserver.flush();

			// JPE 20180405
			// Si el proveedor est� asociado a un Courier
			if (courier != null) {
				Long[] deliveryids = (Long[]) arrDeliverieIds.toArray(new Long[arrDeliverieIds.size()]);
				// Validar que la cantidad de despachos a procesar no supere el m�ximo configurado por el sistema
				if (initParamDTO.getData().length < Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierprocessing"))) {
					try {
						DODeliveryWSRequestDataResultDTO deliverycourierws = null;
						if (courier.getCode().equals("CE")) { // Chileexpress
							deliverycourierws = deliveryCourierChileExpressWS(courier, deliveryids, initParamDTO);
						} else if (courier.getCode().equals("CC")) { // Correos de Chile
							deliverycourierws = deliveryCourierCorreosDeChileWS(courier, deliveryids, initParamDTO);
						}

						resultDTO.setSuccessdeliveries(deliverycourierws.getSuccessdeliveries());
						resultDTO.setFaileddeliveries(deliverycourierws.getFaileddeliveries());

					} catch (Exception e) {
						getSessionContext().setRollbackOnly();
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005");
					}
				}
				else {
					// Generar un Ticket Courier de procesamiento de etiqueta para los despachos creados
					try {
						DODeliveryWSRequestDataResultDTO ticketrequest = doSaveCouriers(deliveryids, vendor.getId(), initParamDTO);

						if (ticketrequest.getValidationerrors() != null && ticketrequest.getValidationerrors().length > 0) {
							// Ordenar errores
							Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
							resultDTO.setValidationerrors(ticketrequest.getValidationerrors());
							getSessionContext().setRollbackOnly();
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6007");
						}

						resultDTO.setTicketnumber(ticketrequest.getTicketnumber());

					} catch (Exception e) {
						getSessionContext().setRollbackOnly();
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
					}
				}
			}

			List<AddDODeliveryOfDirectOrdersDataDTO> reportList = new ArrayList<AddDODeliveryOfDirectOrdersDataDTO>();
			for (int i = 0; i < arrDeliveries.size(); i++) {
				AddDODeliveryOfDirectOrdersDataDTO report = new AddDODeliveryOfDirectOrdersDataDTO();
				report.setDeliveryid(arrDeliveries.get(i).getId());
				report.setDeliverynumber(arrDeliveries.get(i).getNumber());

				reportList.add(report);
			}

			resultDTO.setReport(reportList.toArray(new AddDODeliveryOfDirectOrdersDataDTO[reportList.size()]));

			// Recalcular las unidades
			for (Long orderid : orderIds) {
				directorderreportmanager.doCalculateTotalOfDirectOrder(orderid);
			}

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public DODeliveryReportResultDTO doChangeDODeliveryStateType(DoChangeDODeliveryStateTypeInitParamDTO initParams) {

		DODeliveryReportResultDTO result = new DODeliveryReportResultDTO();

		DODeliveryW dodelivery = null;
		try {
			dodelivery = dodeliveryserver.getById(initParams.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L2215");
		}

		result = doChangeDODeliveryStateType(initParams, dodelivery, false);

		return result;

	}

	public DODeliveryReportResultDTO doChangeDODeliveryStateType(DoChangeDODeliveryStateTypeInitParamDTO initParamDTO, DODeliveryW dodelivery, boolean byFile) {
		DODeliveryReportResultDTO resultDTO = new DODeliveryReportResultDTO();

		try {
			// Obtener el proveedor
			// Valida proveedor
			VendorW[] vendors = null;
			VendorW vendor;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendor = vendors[0];
			
			DirectOrderW directOrder = directorderserver.getById(dodelivery.getOrderid());
			
			// Validar que la orden pertenezca al proveedor
			if (!directOrder.getVendorid().equals(vendor.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1903");
			}
			
			DirectOrderStateTypeW dostActual = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
			
			DODeliveryStateTypeW dodstActual = dodeliverystatetypeserver.getById(dodelivery.getCurrentstatetypeid());
			DODeliveryStateTypeW dodstNew = dodeliverystatetypeserver.getById(initParamDTO.getDodeliverystatetypeid());

			DOReceptionTypeW dortPartial = doreceptiontypeserver.getByPropertyAsSingleResult("code", "PARCIAL");
			DOReceptionTypeW dortTotal = doreceptiontypeserver.getByPropertyAsSingleResult("code", "TOTAL");
			DOReceptionTypeW dortNull = doreceptiontypeserver.getByPropertyAsSingleResult("code", "NULA");

			DirectOrderStateTypeW dostReceived = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
			DirectOrderStateTypeW dostRejected = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
			DirectOrderStateTypeW dostLost = directorderstatetypeserver.getByPropertyAsSingleResult("code", "EXTRAVIADO");

			List<DODeliveryDetailW> doddList = new ArrayList<DODeliveryDetailW>();

			// Obtener los detalles del despacho
			DODeliveryDetailW[] doddArr = dodeliverydetailserver.getByPropertyAsArray("id.dodeliveryid", dodelivery.getId());

			// Validar que el estado del despacho sea abierto
			if (dodstActual.getClosed()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se puede actualizar el despacho ya que el estado actual no es vigente.", false);
			}

			// Validar que el nuevo estado del despacho sea visible
			if (!dodstNew.getShowable()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "El nuevo estado no es válido para actualizar el despacho", false);
			}
			
			Date reprogDate = DateConverterFactory2.StrToDate(initParamDTO.getReprogdate());
			
			if (!byFile) {
				// Validar si el nuevo estado es 'Entregado'
				if (dodstNew.getCode().equalsIgnoreCase("REC_CONFORME")) {
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					Date today = cal.getTime();
					
					// Validar si la fecha de reprogramación de la orden es anterior o igual a hoy
					if (!directOrder.getCurrentdeliverydate().after(today)) {
						// Validar que la nueva fecha sea igual o posterior a hoy
						if (reprogDate.before(today)) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La fecha de entrega ingresada debe ser igual o posterior al d�a de hoy debido a que est� sobre la fecha comprometida de entrega a cliente", false);
						}
					}
				}
			}

			// Obtener el tipo de recepción del nuevo estado
			DOReceptionTypeW reActualT = doreceptiontypeserver.getById(dodstNew.getReceptiontypeid());

			// Actualizar las cantidades/montos del despacho seg�n el tipo de recepción del nuevo estado
			if (reActualT.getId().equals(dortPartial.getId()) || reActualT.getId().equals(dortTotal.getId())) {
				for (DODeliveryDetailW dodd : doddArr) {
					// Cantidades/montos entregados
					dodd.setReceivedunits(dodd.getAvailableunits());
					doddList.add(dodd);
					dodd = dodeliverydetailserver.updateDODeliveryDetail(dodd);
				}
			}
			else if (dodstNew.getReceptiontypeid().equals(dortNull.getId())) {
				for (DODeliveryDetailW dodd : doddArr) {
					// Cantidades/montos pendientes de entrega
					dodd.setReceivedunits(0D);
					doddList.add(dodd);
					dodd = dodeliverydetailserver.updateDODeliveryDetail(dodd);
				}
			}
			
			Date now = new Date();
			
			// Obtener los tipos de auditor�a VeV 'Cambio estado OC VeV' y 'Cambio estado despacho VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

			// Actualizar el despacho
			dodelivery.setCurrentstdate(reprogDate);
			dodelivery.setCurrentstcomment(initParamDTO.getComment() == null ? "" : initParamDTO.getComment().trim());
			dodelivery.setCurrentstwho(initParamDTO.getUsername());
			dodelivery.setCurrentstatetypeid(dodstNew.getId());
			if (initParamDTO.getCommiteddate() != null) {
				dodelivery.setCommiteddate(initParamDTO.getCommiteddate());
			}

			dodelivery = dodeliveryserver.updateDODelivery(dodelivery);

			// Agregar al historial
			DODeliveryStateW doDeliveryState = new DODeliveryStateW();
			doDeliveryState.setDeliveryid(dodelivery.getId());
			doDeliveryState.setDeliverystatetypeid(dodstNew.getId());
			doDeliveryState.setWhen(now);
			doDeliveryState.setDeliverystatedate(dodelivery.getCurrentstdate());
			doDeliveryState.setComment(initParamDTO.getComment() == null ? "" : initParamDTO.getComment().trim());
			doDeliveryState.setWho(initParamDTO.getUsername());

			dodeliverystateserver.addDODeliveryState(doDeliveryState);
			
			// JPE 20200615
			// Registro de auditor�a
			VeVAuditW vevAudit = new VeVAuditW();
			vevAudit.setWhen(now);
			vevAudit.setUsername(initParamDTO.getUsername());
			vevAudit.setUsertype(initParamDTO.getUsertype());
			vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
			vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
			vevAudit.setInterfacecontent("");
			vevAudit.setItem("Estado Despacho VeV PD");
			vevAudit.setItemvalue(directOrder.getNumber().toString());
			vevAudit.setInitialdata(dodstActual.getName());
			vevAudit.setFinaldata(dodstNew.getName());
			vevAudit.setVendorid(directOrder.getVendorid());
			vevAudit.setVevupdatetypeid(utDeliveryState.getId());
			
			vevauditserverlocal.addVeVAudit(vevAudit);

			// Actualizar el reporte
			DODeliveryReportDataDTO[] report = new DODeliveryReportDataDTO[1];
			report[0] = new DODeliveryReportDataDTO();
			report[0].setDeliveryid(dodelivery.getId());
			report[0].setDeliverynumber(dodelivery.getNumber());
			report[0].setDeliverystatetypecode(dodstNew.getCode());
			report[0].setDeliverystatetypedesc(dodstNew.getName());
			report[0].setReceptiontypecode(reActualT.getCode());
			report[0].setReceptiontypedesc(reActualT.getName());
			report[0].setCurrentstdate(initParamDTO.getReprogdate());
			report[0].setDlcomment(initParamDTO.getComment() == null ? "" : initParamDTO.getComment().trim());

			resultDTO.setDeliveryreport(report);

			dodeliverystateserver.flush();
			directOrder = directorderreportmanager.doCalculateTotalOfDirectOrder(dodelivery.getOrderid());

			// Enviar la interfaz en dependencia del nuevo estado
			DirectOrderDetailW[] directorderdetails = directorderdetailserver.getByPropertyAsArray("id.orderid", dodelivery.getOrderid());

			DirectOrderStateW directorderstate = new DirectOrderStateW();
			// Entregado
			if (dodstNew.getCode().equalsIgnoreCase("REC_CONFORME")) {
				if (directOrder.getNeedunits().doubleValue() == directOrder.getReceivedunits().doubleValue()) {
					directorderstate.setComment("Recibido conforme y total");
					doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "1");
				}
				else {
					directorderstate.setComment("Recibido conforme parcialmente");
					doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "2");
				}

				// Dejar la OC en estado "Recepcionada"
				directOrder.setCurrentstatetypeid(dostReceived.getId());
				directOrder.setCurrentstatetypedate(now);
				directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
				directOrder.setCurrentstatetypecomment("");
				directOrder = directorderserver.updateDirectOrder(directOrder);

				directorderstate.setOrderid(directOrder.getId());
				directorderstate.setOrderstatetypeid(dostReceived.getId());
				directorderstate.setWhen(now);
				directorderstate.setWho(initParamDTO.getUsername());
				directorderstate.setComment("");
				directorderstateserver.addDirectOrderState(directorderstate);
				
				// 20200611
				if (!byFile) {
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dostActual.getName());
					vevAudit.setFinaldata(dostReceived.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());					
					
					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}
			// Expectativas
			else if (dodstNew.getCode().equalsIgnoreCase("EXPECTATIVAS")) {
				doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "0");

				// Dejar la OC en estado "Rechazada"
				directOrder.setCurrentstatetypeid(dostRejected.getId());
				directOrder.setCurrentstatetypedate(now);
				directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
				directOrder.setCurrentstatetypecomment("Entrega rechazada");
				directOrder = directorderserver.updateDirectOrder(directOrder);

				directorderstate.setOrderid(directOrder.getId());
				directorderstate.setOrderstatetypeid(dostRejected.getId());
				directorderstate.setWhen(now);
				directorderstate.setWho(initParamDTO.getUsername());
				directorderstate.setComment("Entrega rechazada");
				directorderstateserver.addDirectOrderState(directorderstate);
				
				// 20200611
				if (!byFile) {
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dostActual.getName());
					vevAudit.setFinaldata(dostRejected.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());					
					
					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}
			// JPE 20180814
			// Extraviado
			else if (dodstNew.getCode().equalsIgnoreCase("EXTRAVIADO")) {
				//doSendInt1879(directorder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "5");

				// Dejar la OC en estado "Extraviado"
				directOrder.setCurrentstatetypeid(dostLost.getId());
				directOrder.setCurrentstatetypedate(now);
				directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
				directOrder.setCurrentstatetypecomment("Entrega extraviada");
				directOrder = directorderserver.updateDirectOrder(directOrder);

				directorderstate.setOrderid(directOrder.getId());
				directorderstate.setOrderstatetypeid(dostLost.getId());
				directorderstate.setWhen(now);
				directorderstate.setWho(initParamDTO.getUsername());
				directorderstate.setComment("Entrega extraviada");
				directorderstateserver.addDirectOrderState(directorderstate);
				
				// 20200611
				if (!byFile) {
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dostActual.getName());
					vevAudit.setFinaldata(dostLost.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());					
					
					vevauditserverlocal.addVeVAudit(vevAudit);
				}
			}
			// Otro estado
			else {
				doSendInt1887(directOrder, "CambioEstadoB2B", dodstNew.getName(), initParamDTO.getComment(), dodstNew);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public BaseResultDTO doUpdateAvailableUnitsOfDODeliveryDetails(UpdateUnitsOfDODeliveryDetailsInitParamDTO initParams) {
		BaseResultDTO result = new BaseResultDTO();

		try {

			Map<Long, DODeliveryDetailW> doddMap = new HashMap<Long, DODeliveryDetailW>();

			DODeliveryW dodelivery = dodeliveryserver.getById(initParams.getDodeliveryid());
			DODeliveryStateTypeW actualSt = dodeliverystatetypeserver.getById(dodelivery.getCurrentstatetypeid());

			// VERIFICA QUE EL ESTADO DEL DESPACHO SEA NO CERRADO
			if (actualSt.getClosed()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El estado actual no permite modificar las unidades en despacho.", false);
			}

			// TRAE LOS DETALLES DE DESPACHO
			DODeliveryDetailW[] doddArr = dodeliverydetailserver.getByPropertyAsArray("id.dodeliveryid", dodelivery.getId());
			for (DODeliveryDetailW dodd : doddArr) {
				if (!doddMap.containsKey(dodd.getItemid())) {
					doddMap.put(dodd.getItemid(), dodd);
				}
			}

			// ITERA DETALLES MODIFICADOS
			int ceroCount = 0;
			ItemUnitsDTO[] details = initParams.getDetails();
			for (ItemUnitsDTO detail : details) {

				// VALIDA QUE PERTENEZCA AL DETALLE
				if (!doddMap.containsKey(detail.getItemid())) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El detalle informado no pertenece al despacho.", false);
				}

				DODeliveryDetailW dodetail = doddMap.get(detail.getItemid());

				// VALIDA QUE LAS CANTIDADES SEAN MAYORES A CERO
				if (detail.getUnits() < 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Las cantidades  ajustadas deben ser mayores o iguales a cero.", false);
				}

				// VERIFICA SI EL DETALLE ES CERO
				if (detail.getUnits() == 0) {
					ceroCount++;
				}

				// VALIDA QUE LA CANTIDAD INFORMADA SEA MENOR O IGUAL A LA CANTIDAD ORIGINAL
				if (detail.getUnits() > dodetail.getPendingunits()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Las cantidades ajustadas no deben ser mayores a lo original.", false);
				}
			}

			if (ceroCount == doddArr.length) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Debe ingresar al menos una cantidad mayor a cero.", false);
			}

			// ACTUALIZA LAS CANTIDADES
			for (ItemUnitsDTO detail : details) {

				DODeliveryDetailW dodetail = doddMap.get(detail.getItemid());
				dodetail.setAvailableunits(detail.getUnits());

				dodetail = dodeliverydetailserver.updateDODeliveryDetail(dodetail);

				doddMap.put(detail.getItemid(), dodetail);
			}

			directorderreportmanager.doCalculateTotalOfDirectOrder(dodelivery.getOrderid());

			return result;

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public BaseResultDTO doUpdateReceivedUnitsOfDODeliveryDetails(UpdateUnitsOfDODeliveryDetailsInitParamDTO initParams) {
		BaseResultDTO result = new BaseResultDTO();

		try {
			Map<Long, DODeliveryDetailW> doddMap = new HashMap<Long, DODeliveryDetailW>();

			DODeliveryW dodelivery = dodeliveryserver.getById(initParams.getDodeliveryid());
			DODeliveryStateTypeW actualSt = dodeliverystatetypeserver.getById(dodelivery.getCurrentstatetypeid());
			DOReceptionTypeW reParcialT = doreceptiontypeserver.getByPropertyAsSingleResult("code", "PARCIAL");

			// VERIFICA QUE EL ESTADO DEL DESPACHO SEA NO CERRADO
			if (!actualSt.getClosed() && !actualSt.getReceptiontypeid().equals(reParcialT.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El estado actual no permite modificar las unidades entregadas.", false);
			}

			// TRAE LOS DETALLES DE DESPACHO
			DODeliveryDetailW[] doddArr = dodeliverydetailserver.getByPropertyAsArray("id.dodeliveryid", dodelivery.getId());
			for (DODeliveryDetailW dodd : doddArr) {
				if (!doddMap.containsKey(dodd.getItemid())) {
					doddMap.put(dodd.getItemid(), dodd);
				}
			}

			// ITERA DETALLES MODIFICADOS
			ItemUnitsDTO[] details = initParams.getDetails();
			for (ItemUnitsDTO detail : details) {

				// VALIDA QUE PERTENEZCA AL DETALLE
				if (!doddMap.containsKey(detail.getItemid())) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El detalle informado no pertenece al despacho.", false);
				}

				DODeliveryDetailW dodetail = doddMap.get(detail.getItemid());

				// VALIDA QUE LAS CANTIDADES SEAN MAYORES A CERO
				if (detail.getUnits() < 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Las cantidades  ajustadas deben ser mayores o iguales a cero.", false);
				}

				// VALIDA QUE LA CANTIDAD INFORMADA SEA MENOR O IGUAL A LA CANTIDAD ORIGINAL
				if (detail.getUnits() > dodetail.getAvailableunits()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "Las cantidades ajustadas no deben ser mayores a lo comprometido para despachar.", false);
				}
			}

			// ACTUALIZA LAS CANTIDADES
			for (ItemUnitsDTO detail : details) {

				DODeliveryDetailW dodetail = doddMap.get(detail.getItemid());
				dodetail.setReceivedunits(detail.getUnits());

				dodetail = dodeliverydetailserver.updateDODeliveryDetail(dodetail);

				doddMap.put(detail.getItemid(), dodetail);
			}

			// CALCULO DE OC
			directorderreportmanager.doCalculateTotalOfDirectOrder(dodelivery.getOrderid());

			return result;

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public ExcelDODeliveryReportResultDTO getExcelDODeliveryReport(DownloadDeliveryReportInitParamDTO initParamW, boolean byPages) {

		ExcelDODeliveryReportResultDTO resultW = new ExcelDODeliveryReportResultDTO();
		ExcelDODeliveryReportDataDTO[] deliveryWs = null;
		int totalRows = 0;
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamW.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];

			for (Long deliveryid : initParamW.getDeliveryids()) {
				properties[0] = new PropertyInfoDTO("id", "id", deliveryid);
				properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				List<DODeliveryW> deliveryTmp = dodeliveryserver.getByProperties(properties);

				if (deliveryTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = dodeliveryserver.getDODeliveryReportCountByDeliveries(initParamW.getDeliveryids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				deliveryWs = dodeliveryserver.getExcelDODeliveryReportByOrders(initParamW.getDeliveryids());
			} else {
				resultW.setTotalRows(totalRows);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			resultW.setExceldeliverys(deliveryWs);
			resultW.setTotalRows(totalRows);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public DODeliverySourceDataResultDTO getDODeliverySourceData(DownloadDeliveryReportInitParamDTO initParamW, boolean byPages) {

		DODeliverySourceDataResultDTO resultW = new DODeliverySourceDataResultDTO();
		DODeliverySourceDataDTO[] deliveryWs = null;
		int totalRows = 0;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamW.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Chequea la existencia del vendor
			try {
				vendorserver.getById(vendor.getId());
			} catch (NotFoundException e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
			}

			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];

			for (Long deliveryid : initParamW.getDeliveryids()) {
				properties[0] = new PropertyInfoDTO("id", "id", deliveryid);
				properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				List<DODeliveryW> deliveryTmp = dodeliveryserver.getByProperties(properties);

				if (deliveryTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = dodeliveryserver.getDODeliveryReportCountByDeliveries(initParamW.getDeliveryids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				deliveryWs = dodeliveryserver.getDODeliveryDataSource(initParamW.getDeliveryids());
			} else {
				resultW.setTotalRows(totalRows);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			resultW.setSourcedatadeliverys(deliveryWs);
			resultW.setTotalRows(totalRows);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public ExcelDODeliveryReportResultDTO getExcelDODeliveryDataSource(DownloadDeliveryReportInitParamDTO initParamW, boolean byPages) {

		ExcelDODeliveryReportResultDTO resultW = new ExcelDODeliveryReportResultDTO();
		ExcelDODeliveryReportDataDTO[] deliveryWs = null;
		int totalRows = 0;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamW.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1902");
//			}

			// Chequea la existencia del despacho para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];

			for (Long deliveryid : initParamW.getDeliveryids()) {
				properties[0] = new PropertyInfoDTO("id", "id", deliveryid);
				properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				List<DODeliveryW> deliveryTmp = dodeliveryserver.getByProperties(properties);

				if (deliveryTmp.size() == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1903");
				}
			}

			// Si es por paginas 'isPages = true' no es necesario realizar la consulta
			if (!byPages) {
				totalRows = dodeliveryserver.getDODeliveryReportCountByDeliveries(initParamW.getDeliveryids());
			}
			// Se valida la cantidad de registros con el m�ximo permitido dependiendo de isByPages

			if (totalRows < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				deliveryWs = dodeliveryserver.getExcelDODeliveryReportByOrders(initParamW.getDeliveryids());
			} else {
				resultW.setTotalRows(totalRows);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			resultW.setExceldeliverys(deliveryWs);
			resultW.setTotalRows(totalRows);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public DeliveryIdsByPagesW getExcelDODeliveryReportByPages(DODeliveryReportInitParamDTO initParamDTO, PageRangeDTO[] pageranges) {
		DeliveryIdsByPagesW resultW = new DeliveryIdsByPagesW();
		DeliveryIdsByPagesW result = null;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Se consulta por la cantidad de registros que implica el reporte excel para las paginas solicitadas
			// Además se traen los ids de las ordenes correspondientes
			Date since = null;
			if (initParamDTO.getSince() != null && !initParamDTO.getSince().equals("")) {
				//since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
				since = Date.from( initParamDTO.getSince().atZone( ZoneId.systemDefault()).toInstant());
			}

			Date until = null;
			if (initParamDTO.getUntil() != null && !initParamDTO.getUntil().equals("")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.from( initParamDTO.getUntil().atZone( ZoneId.systemDefault()).toInstant()));
				cal.add(Calendar.DAY_OF_YEAR, 1);
				until = cal.getTime();
			}

			result = dodeliveryserver.getDODeliveryIdsByPages(vendor.getId(), initParamDTO.getOcnumber(), initParamDTO.getRequestnumber(), initParamDTO.getClientrut(), since, until, initParamDTO.getDostate(), initParamDTO.getRows(), initParamDTO.getFiltertype(), initParamDTO
					.getOrderby(), pageranges, initParamDTO.getSendnumber(), initParamDTO.getWithdrawalnumber());

			// Se valida la cantidad de registros con el m�ximo permitido
			if (result.getTotalRows() < RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {

				// Si es válido se envian ids de ordenes al portal
				resultW.setDeliveryIds(result.getDeliveryIds());
				resultW.setTotalRows(result.getTotalRows());
			} else {
				resultW.setTotalRows(result.getTotalRows());
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L2102");
			}
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public UploadDODeliveryUpdatesResultDTO doUploadDODeliveryUpdates(UploadDODeliveryUpdatesInitParamDTO initParamDTO) {
		UploadDODeliveryUpdatesResultDTO resultDTO = new UploadDODeliveryUpdatesResultDTO();

		try {
			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
			List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();

			List<Long> orderIds = new ArrayList<Long>();
			List<DODeliveryReportDataDTO> reportList = new ArrayList<DODeliveryReportDataDTO>();

			// Almacena los despachos por N°mero
			Map<Long, DODeliveryW> doDlByNumberMap = new HashMap<Long, DODeliveryW>();
			// Almacena los estados de despacho por nombre
			Map<String, DODeliveryStateTypeW> dlDstByNameMap = new HashMap<String, DODeliveryStateTypeW>();
			// Almacena las oc por N°mero
			Map<Long, DirectOrderW> doByNumberMap = new HashMap<Long, DirectOrderW>();
			// Almacena item por sku
			Map<String, ItemW> itBySkuMap = new HashMap<String, ItemW>();
			// Almacena la información entregada por N°mero de despacho
			Map<Long, List<ExcelDODeliveryReportDataDTO>> doDlDataListMap = new HashMap<Long, List<ExcelDODeliveryReportDataDTO>>();
			// Almacena el nuevo estado asociado al despacho
			Map<Long, DODeliveryStateTypeW> doDlDstMap = new HashMap<Long, DODeliveryStateTypeW>();
			// Almacena la oc asociada al despacho
			Map<Long, DirectOrderW> doDlDocMap = new HashMap<Long, DirectOrderW>();
			// Almacena fecha de entrega asociada al despacho
			Map<Long, Date> doDlDateMap = new HashMap<Long, Date>();
			// Almacena comentario asociado al despacho
			Map<Long, String> doDlCommentMap = new HashMap<Long, String>();

			// Listado de despacho/item
			List<String> repeatedrowsList = new ArrayList<String>();

			// Suma de totales recepcionados por despacho
			Map<Long, Double> totalReceivedMap = new HashMap<Long, Double>();

			// Almacena los detalles de despacho
			Map<DODeliveryDetailPK, DODeliveryDetailW> doddMap = new TreeMap<DODeliveryDetailPK, DODeliveryDetailW>();

			// Obtener el proveedor
			VendorW[] vendors = null;
			VendorW vendor;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendor = vendors[0];
						
			// Obtener los estados de despacho abiertos
			List<DODeliveryStateTypeW> dodstOpened = dodeliverystatetypeserver.getByProperty("closed", false);
			HashMap<Long, DODeliveryStateTypeW> dodstMap = new HashMap<Long, DODeliveryStateTypeW>();
			for (DODeliveryStateTypeW dodst : dodstOpened) {
				dodstMap.put(dodst.getId(), dodst);
			}

			String error = "";

			double units = 0;

			DODeliveryW[] dodlArr = null;
			DODeliveryW doDelivery = null;
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			for (ExcelDODeliveryReportDataDTO excelData : initParamDTO.getData()) {
				List<ExcelDODeliveryReportDataDTO> dataList = null;

				String key = excelData.getDlnumber() + "_" + excelData.getItemsku().trim();
				if (repeatedrowsList.contains(key)) {
					error = "Fila " + excelData.getRownumber() + ": Despacho " + excelData.getDlnumber() + " con SKU " + excelData.getItemsku().trim() + " ya informados en otra fila";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				repeatedrowsList.add(key);

				// Sumar total por despacho
				if (totalReceivedMap.get(excelData.getDlnumber()) == null) {
					units = 0d;
				}
				else {
					units = totalReceivedMap.get(excelData.getDlnumber());
				}
				totalReceivedMap.put(excelData.getDlnumber(), excelData.getReceivedunits().doubleValue() + units);

				// Despacho
				if (doDlByNumberMap.containsKey(excelData.getDlnumber())) {
					// Asociar la información al despacho
					dataList = doDlDataListMap.get(excelData.getDlnumber());
					dataList.add(excelData);
					doDlDataListMap.put(excelData.getDlnumber(), dataList);
				}
				else {
					prop1 = new PropertyInfoDTO("number", "dodeliverynumber", excelData.getDlnumber());
					prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
					dodlArr = dodeliveryserver.getByPropertiesAsArray(prop1, prop2);

					// Validar que el despacho exista
					if (dodlArr == null || dodlArr.length == 0) {
						error = "Fila " + excelData.getRownumber() + ": El despacho N° " + excelData.getDlnumber() + " no existe";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
					else {
						// Validar que el estado del despacho sea abierto
						if (!dodstMap.containsKey(dodlArr[0].getCurrentstatetypeid())) {
							error = "Fila " + excelData.getRownumber() + ": El despacho N° " + excelData.getDlnumber() + " no se encuentra en un estado vigente";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						// Detalles del despacho
						DODeliveryDetailW[] doddArr = dodeliverydetailserver.getByPropertyAsArray("id.dodeliveryid", dodlArr[0].getId());
						DODeliveryDetailPK doddPK = null;
						for (DODeliveryDetailW dodd : doddArr) {
							doddPK = new DODeliveryDetailPK(dodd.getDodeliveryid(), dodd.getItemid());

							if (!doddMap.containsKey(doddPK)) {
								doddMap.put(doddPK, dodd);
							}
						}

						doDelivery = dodeliveryserver.getById(dodlArr[0].getId(), LockModeType.PESSIMISTIC_WRITE);
						doDlByNumberMap.put(excelData.getDlnumber(), doDelivery);

						// Asociar la información al despacho
						dataList = new ArrayList<ExcelDODeliveryReportDataDTO>();
						dataList.add(excelData);
						doDlDataListMap.put(excelData.getDlnumber(), dataList);
					}
				}
			}
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date today = cal.getTime();

			// Iterar por despacho
			DirectOrderW directOrder = null;
			DODeliveryStateTypeW dodst = null;
			ItemW item = null;
			Date reprogdate = null;
			String comment = null;
			for (Map.Entry<Long, List<ExcelDODeliveryReportDataDTO>> excel : doDlDataListMap.entrySet()) {
				doDelivery = doDlByNumberMap.get(excel.getKey());
				dodst = null;
				directOrder = null;
				reprogdate = null;
				comment = null;
				for (ExcelDODeliveryReportDataDTO exceldata : excel.getValue()) {
					// Estado
					if (doDlDstMap.containsKey(excel.getKey())) {
						// Validar que sea el mismo estado informado para todos los detalles del despacho
						if (!doDlDstMap.get(excel.getKey()).getId().equals(dodst.getId())) {
							error = "Fila " + exceldata.getRownumber() + ": No se informa el mismo nuevo estado para todos los detalles del despacho";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					else {
						if (dlDstByNameMap.containsKey(exceldata.getDeliverystatetype())) {
							dodst = dlDstByNameMap.get(exceldata.getDeliverystatetype());
						}
						else {
							DODeliveryStateTypeW[] doDstArr = dodeliverystatetypeserver.getByPropertyAsArray("name", exceldata.getDeliverystatetype());

							// Validar que el estado exista
							if (doDstArr == null || doDstArr.length == 0) {
								error = "Fila " + exceldata.getRownumber() + ": El estado " + exceldata.getDeliverystatetype() + " no existe";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
							else {
								dodst = doDstArr[0];
								dlDstByNameMap.put(exceldata.getDeliverystatetype(), dodst);
							}
						}
						
						// Si es un estado válido
						if (dodst != null) {
							// Validar si corresponde a un estado modificable
							if (!dodst.getModifiable()) {
								error = "Fila " + exceldata.getRownumber() + ": El nuevo estado " + dodst.getName() + " no es válido para actualizar el despacho";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}

							// Asociar el estado al despacho
							doDlDstMap.put(excel.getKey(), dodst);
						}
					}
					
					// Orden de compra
					if (doDlDocMap.containsKey(excel.getKey())) {
						// Validar que sea la misma orden informada para todos los detalles del despacho
						if (!doDlDocMap.get(excel.getKey()).getId().equals(directOrder.getId())) {
							error = "Fila " + exceldata.getRownumber() + ": No se informa la misma orden de compra para todos los detalles del despacho";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					else {
						if (doByNumberMap.containsKey(exceldata.getDocnumber())) {
							directOrder = doByNumberMap.get(exceldata.getDocnumber());
						}
						else {
							DirectOrderW[] doArr = directorderserver.getByPropertyAsArray("number", exceldata.getDocnumber());
							
							// Validar que la orden exista
							if (doArr == null || doArr.length == 0) {
								error = "Fila " + exceldata.getRownumber() + ": La orden de compra " + exceldata.getDocnumber() + " no existe";
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}
							else {
								directOrder = doArr[0];
								doByNumberMap.put(exceldata.getDocnumber(), directOrder);
							}
						}
						
						// Si es una orden v�lida
						if (directOrder != null) {
							// Validar que la orden est� asociada al despacho
							if (!doDelivery.getOrderid().equals(directOrder.getId())) {
								error = "Fila " + exceldata.getRownumber() + ": La orden de compra " + exceldata.getDocnumber() + " no esta asociada al despacho " + excel.getKey();
								baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
							}

							// Asociar la orden al despacho							
							doDlDocMap.put(excel.getKey(), directOrder);
							orderIds.add(directOrder.getId());
						}
					}
					
					// Fecha de entrega
					reprogdate = DateConverterFactory2.StrToDate(exceldata.getCommiteddatestr());
					if (doDlDateMap.containsKey(excel.getKey())) {
						// Validar que sea la misma fecha de entrega informada para todos los detalles del despacho
						if (!doDlDateMap.get(excel.getKey()).equals(reprogdate)) {
							error = "Fila " + exceldata.getRownumber() + ": No se informa la misma fecha de entrega para todos los detalles del despacho";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					else {
						doDlDateMap.put(excel.getKey(), reprogdate);
						
						// JPE 20200616
						// Si el nuevo estado es 'Entregado'
						if (dodst != null && directOrder != null && dodst.getCode().equalsIgnoreCase("REC_CONFORME")) {
							// Validar si la fecha de reprogramación de la orden es anterior o igual a hoy
							if (!directOrder.getCurrentdeliverydate().after(today)) {
								// Validar que la nueva fecha sea igual o posterior a hoy
								if (reprogdate.before(today)) {
									error = "Fila " + exceldata.getRownumber() + ": La fecha de entrega ingresada debe ser igual o posterior al día de hoy debido a que está sobre la fecha comprometida de entrega a cliente";
									baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
								}
							}
						}
					}

					// Comentario
					comment = exceldata.getCurrentstcomment() == null ? "" : exceldata.getCurrentstcomment();
					if (doDlCommentMap.containsKey(excel.getKey())) {
						// Validar que sea el mismo comentario informado para todos los detalles del despacho
						if (!doDlCommentMap.get(excel.getKey()).equals(comment)) {
							error = "Fila " + exceldata.getRownumber() + ": No se informa el mismo comentario para todos los detalles del despacho";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}
					else {
						doDlCommentMap.put(excel.getKey(), comment);
					}
					
					// Producto
					item = null;
					if (itBySkuMap.containsKey(exceldata.getItemsku())) {
						item = itBySkuMap.get(exceldata.getItemsku());
					}
					else {
						ItemW[] itArr = itemserver.getByPropertyAsArray("internalcode", exceldata.getItemsku());

						// Validar que el producto exista
						if (itArr == null || itArr.length == 0) {
							error = "Fila " + exceldata.getRownumber() + ": El producto sku " + exceldata.getItemsku() + " no existe";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
						else {
							item = itArr[0];
							itBySkuMap.put(exceldata.getItemsku(), item);
						}
					}
					
					// Si es un producto válido
					if (item != null) {
						// Validar que el producto est� asociado al despacho
						DODeliveryDetailPK doddPK = new DODeliveryDetailPK(doDelivery.getId(), item.getId());
						if (!doddMap.containsKey(doddPK)) {
							error = "Fila " + exceldata.getRownumber() + ": El producto SKU " + exceldata.getItemsku() + " no pertenece al despacho N° " + excel.getKey();
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}
					}					
					
					// Validar que las unidades sean mayores o iguales a 0
					if (exceldata.getReceivedunits().doubleValue() < 0) {
						error = "Fila " + exceldata.getRownumber() + ": Las cantidades recepcionadas del producto SKU " + exceldata.getItemsku() + " en el despacho " + excel.getKey() + " deben ser mayores o iguales a cero";
						baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}
			}

			// Si hay errores hasta el momento, no se puede continuar
			if (baseresultlist.size() > 0) {
				// Ordenar errores
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			baseresultlist.clear();

			Date now = new Date();
			Map<Long, ArrayList<DODeliveryDetailW>> doddListMap = new HashMap<Long, ArrayList<DODeliveryDetailW>>();

			DOReceptionTypeW reParcialT = doreceptiontypeserver.getByPropertyAsSingleResult("code", "PARCIAL");
			DOReceptionTypeW reTotalT = doreceptiontypeserver.getByPropertyAsSingleResult("code", "TOTAL");
			DOReceptionTypeW reNuloT = doreceptiontypeserver.getByPropertyAsSingleResult("code", "NULA");
			
			// Obtener los tipos de auditor�a VeV 'Cambio estado OC VeV' y 'Cambio estado despacho VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

			List<Long> deliveredList = new ArrayList<Long>();
			Long dodstInitialId = null;
			// Actualizar los despachos
			for (Map.Entry<Long, List<ExcelDODeliveryReportDataDTO>> excel : doDlDataListMap.entrySet()) {
				ArrayList<DODeliveryDetailW> doddList = new ArrayList<DODeliveryDetailW>();
				DODeliveryW dodelivery = doDlByNumberMap.get(excel.getKey());
				directOrder = doDlDocMap.get(excel.getKey());
				dodstInitialId = dodelivery.getCurrentstatetypeid();
				
				dodelivery.setCurrentstdate(doDlDateMap.get(excel.getKey()));
				dodelivery.setCurrentstatetypeid(doDlDstMap.get(excel.getKey()).getId());
				dodelivery.setCurrentstwho(initParamDTO.getUsername());
				dodelivery.setCurrentstcomment(doDlCommentMap.get(excel.getKey()));

				dodelivery = dodeliveryserver.updateDODelivery(dodelivery);
				doDlByNumberMap.put(excel.getKey(), dodelivery);

				// Agregar al historial
				DODeliveryStateW doDeliveryState = new DODeliveryStateW();
				doDeliveryState.setDeliveryid(dodelivery.getId());
				doDeliveryState.setDeliverystatetypeid(doDlDstMap.get(excel.getKey()).getId());
				doDeliveryState.setWhen(now);
				doDeliveryState.setDeliverystatedate(dodelivery.getCurrentstdate());
				doDeliveryState.setComment(doDlCommentMap.get(excel.getKey()));
				doDeliveryState.setWho(initParamDTO.getUsername());

				dodeliverystateserver.addDODeliveryState(doDeliveryState);
				
				// JPE 20200615
				// Registro de auditor�a
				VeVAuditW vevAudit = new VeVAuditW();
				vevAudit.setWhen(now);
				vevAudit.setUsername(initParamDTO.getUsername());
				vevAudit.setUsertype(initParamDTO.getUsertype());
				vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
				vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
				vevAudit.setInterfacecontent("");
				vevAudit.setItem("Estado Despacho VeV PD");
				vevAudit.setItemvalue(directOrder.getNumber().toString());
				vevAudit.setInitialdata(dodstMap.get(dodstInitialId).getName());
				vevAudit.setFinaldata(doDlDstMap.get(excel.getKey()).getName());
				vevAudit.setVendorid(directOrder.getVendorid());
				vevAudit.setVevupdatetypeid(utDeliveryState.getId());
				
				vevauditserverlocal.addVeVAudit(vevAudit);

				for (ExcelDODeliveryReportDataDTO exceldata : excel.getValue()) {
					DODeliveryDetailPK doddPK = new DODeliveryDetailPK(doDlByNumberMap.get(excel.getKey()).getId(), itBySkuMap.get(exceldata.getItemsku()).getId());
					DODeliveryDetailW doddTmp = doddMap.get(doddPK);

					if (doDlDstMap.get(excel.getKey()).getCode().equalsIgnoreCase("REC_CONFORME")) {
						// Guardar los despachos como 'Entregado'
						if (!deliveredList.contains(excel.getKey())) {
							deliveredList.add(excel.getKey());
						}

						// Validar que las unidades recepcionadas no sobrepasen lo comprometido
						if (exceldata.getReceivedunits().doubleValue() > doddTmp.getAvailableunits().doubleValue()) {
							error = "Fila " + exceldata.getRownumber() + ": Las cantidades recepcionadas del producto SKU " + exceldata.getItemsku() + " en el despacho " + excel.getKey() + " sobrepasan lo comprometido";
							baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
						}

						doddTmp.setReceivedunits(exceldata.getReceivedunits());
						doddTmp = dodeliverydetailserver.updateDODeliveryDetail(doddTmp);
					}

					doddList.add(doddTmp);
				}

				doddListMap.put(excel.getKey(), doddList);
			}

			for (Long delivered : deliveredList) {
				if (totalReceivedMap.get(delivered).doubleValue() <= 0) {
					error = "El despacho " + delivered + " tendrá estado 'Entregado', por lo tanto, las cantidades recepcionadas deben ser mayores a cero";
					baseresultlist.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
			}

			if (baseresultlist.size() > 0) {
				// Ordenar errores
				Arrays.sort(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]), comparator);
				resultDTO.setValidationerrors(baseresultlist.toArray(new BaseResultDTO[baseresultlist.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6002");
			}

			for (Map.Entry<Long, List<ExcelDODeliveryReportDataDTO>> excel : doDlDataListMap.entrySet()) {
				// Tipo de Recepción
				DOReceptionTypeW reActualT = null;
				if (doDlDstMap.get(excel.getKey()).getReceptiontypeid().equals(reParcialT.getId())) {
					reActualT = reParcialT;
				}
				else if (doDlDstMap.get(excel.getKey()).getReceptiontypeid().equals(reTotalT.getId())) {
					reActualT = reTotalT;
				}
				else {
					reActualT = reNuloT;
				}

				// Actualizar el reporte
				DODeliveryW dodelivery = doDlByNumberMap.get(excel.getKey());

				DODeliveryReportDataDTO report = new DODeliveryReportDataDTO();
				report.setDeliveryid(dodelivery.getId());
				report.setDeliverynumber(dodelivery.getNumber());
				report.setDeliverystatetypecode(doDlDstMap.get(excel.getKey()).getCode());
				report.setDeliverystatetypedesc(doDlDstMap.get(excel.getKey()).getName());
				report.setReceptiontypecode(reActualT.getCode());
				report.setReceptiontypedesc(reActualT.getName());
				report.setCommiteddate(DateConverterFactory2.DateToStr(dodelivery.getCommiteddate()));
				report.setCurrentstdate(DateConverterFactory2.DateToStr(doDlDateMap.get(excel.getKey())));

				reportList.add(report);

				dodeliverystateserver.flush();
				directOrder = directorderreportmanager.doCalculateTotalOfDirectOrder(dodelivery.getOrderid());

				DirectOrderStateTypeW dostActual = null;
				// 'Recepcionada'
				if (doDlDstMap.get(excel.getKey()).getCode().equalsIgnoreCase("REC_CONFORME")) {
					dostActual = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
				}
				// 'Rechazada'
				else if (doDlDstMap.get(excel.getKey()).getCode().equalsIgnoreCase("EXPECTATIVAS")) {
					dostActual = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
				}

				if (dostActual != null) {
					DirectOrderStateTypeW dost = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
					
					directOrder.setCurrentstatetypeid(dostActual.getId());
					directOrder.setCurrentstatetypedate(now);
					directOrder.setCurrentstatetypewho(initParamDTO.getUsername());
					directOrder.setCurrentstatetypecomment("Masivo");
					directorderserver.updateDirectOrder(directOrder);
					
					DirectOrderStateW directOrderState = new DirectOrderStateW();
					directOrderState.setOrderid(directOrder.getId());
					directOrderState.setOrderstatetypeid(dostActual.getId());
					directOrderState.setWhen(now);
					directOrderState.setWho(initParamDTO.getUsername());
					directOrderState.setComment("Masivo");
					directorderstateserver.addDirectOrderState(directOrderState);					
					dodeliverystateserver.flush();
					
					// 20200611
					// Registro de auditor�a
					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(initParamDTO.getUsername());
					vevAudit.setUsertype(initParamDTO.getUsertype());
					vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
					vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dostActual.getName());
					vevAudit.setFinaldata(dost.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());					
					
					vevauditserverlocal.addVeVAudit(vevAudit);
				}

				// ENVIA MENSAJE
				DirectOrderDetailW[] directorderdetails = directorderdetailserver.getByPropertyAsArray("id.orderid", dodelivery.getOrderid());

				ArrayList<DODeliveryDetailW> doddList = doddListMap.get(dodelivery.getNumber());

				if (doDlDstMap.get(excel.getKey()).getCode().equalsIgnoreCase("REC_CONFORME")) {					
					if (directOrder.getPendingunits().doubleValue() == 0) {
						doSendInt1879(doDlDocMap.get(dodelivery.getNumber()), dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "1");
					}
					else {
						doSendInt1879(doDlDocMap.get(dodelivery.getNumber()), dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "2");
					}
				}
				else if (doDlDstMap.get(excel.getKey()).getCode().equalsIgnoreCase("EXPECTATIVAS")) {
					doSendInt1879(doDlDocMap.get(dodelivery.getNumber()), dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "0");
				}
				else {
					doSendInt1887(doDlDocMap.get(dodelivery.getNumber()), "CambioEstadoB2B", doDlDstMap.get(excel.getKey()).getName(), doDlCommentMap.get(excel.getKey()), doDlDstMap.get(excel.getKey()));
				}
			}

			resultDTO.setDeliveryreportdata(reportList.toArray(new DODeliveryReportDataDTO[reportList.size()]));

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public LabelDODeliveryReportResultDTO getLabelDODeliveryReport(LabelDODeliveryReportInitParamDTO initParams) {

		LabelDODeliveryReportResultDTO resultW = new LabelDODeliveryReportResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {

			LabelDODeliveryReportDataDTO[] data = deliveryserver.getLabelDODeliveryReport(initParams.getDeliveryid(), vendor.getId());
			resultW.setReport(data);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	private void doSendInt1887(DirectOrderW directorder, String type, String note, String comment, DODeliveryStateTypeW dodst) {

		try {

			String finalNote = note;
			if ("CambioEstadoB2B".equals(type) && dodst != null) {
				if (dodst.getCode().equals("SIN_MORADORES") || dodst.getCode().equals("DANO") || dodst.getCode().equals("DIR_ERR") || dodst.getCode().equals("EQUIVOCADO") || dodst.getCode().equals("MOTIVO_CLI") || dodst.getCode().equals("MOTIVO_TRAN")) {
					finalNote = finalNote + "-" + comment;
				}
			} else if ("Cambio Fecha B2B".equals(type)) {
				finalNote = "Fecha Reprogramación despacho-" + note;
			}
			JAXBContext jc = getJC1887();
			bbr.b2b.regional.logistic.xml.int1887.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1887.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1887.Message message1887 = objFactory.createMessage();
			message1887.setOrigen("B2B");
			message1887.setAccion("Update");
			message1887.setTipo("CustomerCommunication");
			message1887.setIdCompania("1001");
			message1887.setTipoEntidad("Customer Order");
			message1887.setNumOrdencompra(directorder.getRequestnumber());
			message1887.setTipoNota(type);
			message1887.setNuevaFecha(finalNote);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1887, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, directorder.getNumber().toString(), "INT1887");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1887" + "\",\"" + directorder.getNumber().toString() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1887" + "\"");

			// planifica el envio
			schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_int1887", "int1887", directorder.getNumber().toString(), result);

			// // Se envía mensaje de ACK a la cola
			// try {
			// QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1887", result);
			// } catch (Exception ex) {
			// // Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
			// MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			// String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() +
			// RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1887_" + directorder.getNumber();
			// try {
			// msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
			// } catch (Exception e1) {
			// logger.debug(e1.getLocalizedMessage());
			// }
			// }
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Enviando mensaje INT1877 para orden " + directorder.getNumber());

	}

	public void doSendInt1879(DirectOrderW directorder, long deliveryNumber, DODeliveryDetailW[] doddArr, DirectOrderDetailW[] directorderdetails, String tipo) {

		try {
			// Obtener valores KPI del mes anterior
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, - 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();
		
			cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date until = cal.getTime();
			
			KPIvevPDReportDataDTO kpivevpdReportData = kpivevpdserver.getInt1879KPIGeneralData(directorder.getVendorid(), directorder.getDepartmentid(), directorder.getSalestoreid(), since, until);
			Double complianceRate = 0.0;
			if (kpivevpdReportData != null && kpivevpdReportData.getTotalevaluated() != null && kpivevpdReportData.getTotalevaluated() > 0) {
				complianceRate = (kpivevpdReportData.getTotaldelivered() * 100.0) / kpivevpdReportData.getTotalevaluated();
			}
			else {
				complianceRate = 100.0;
			}

			LocationW location = null;
			try {
				location = locationserver.getById(directorder.getSalestoreid());
			} catch (Exception e) {
			}

			DepartmentW department = departmentserver.getById(directorder.getDepartmentid());
			VendorW vendor = vendorserver.getById(directorder.getVendorid());

			Map<Long, ItemW> itemMap = new HashMap<Long, ItemW>();
			Map<Long, DirectOrderDetailW> orderdetailMap = new HashMap<Long, DirectOrderDetailW>();

			for (int i = 0; i < directorderdetails.length; i++) {
				orderdetailMap.put(directorderdetails[i].getItemid(), directorderdetails[i]);
			}

			JAXBContext jc = getJC1879();
			bbr.b2b.regional.logistic.xml.int1879.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1879.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1879.Message message1879 = objFactory.createMessage();
			message1879.setOrigen("B2B");
			message1879.setAccion("UPDATE");
			message1879.setTipo("Delivery Confirmation");
			message1879.setIdCompania("1001");
			message1879.setNumDespacho(deliveryNumber);
			message1879.setNumOrdencompra(directorder.getDistributionordernumber());
			message1879.setPersonalizado1("false");
			message1879.setPersonalizado2("false");
			message1879.setPersonalizado3("false");
			message1879.setPersonalizado4("false");
			if (location != null) {
				message1879.setTiendaOrigen(location.getCode());
			} else {
				message1879.setTiendaOrigen("");
			}
			message1879.setCodigoProveedor(vendor.getCode());
			message1879.setKpiProveedor(complianceRate);

			bbr.b2b.regional.logistic.xml.int1879.Estado estado = objFactory.createEstado();

			// Dependiendo del tipo, se ajustan los siguientes valores
			// Expectativas
			if ("0".equals(tipo)) {
				// JPE 20200527
				message1879.setPersonalizado3("true");
				estado.setCodTipo("03");
				estado.setCodDescripcion("Rechazado");
				estado.setCodId("51");
				estado.setCdIdDescripcion("Expectativ");
			}
			// Entregado sin pendientes
			if ("1".equals(tipo)) {
				message1879.setPersonalizado3("true");
				estado.setCodTipo("01");
				estado.setCodDescripcion("Entregado");
				estado.setCodId("61");
				estado.setCdIdDescripcion("Entregado");
			}
			// Entregado con pendientes
			if ("2".equals(tipo)) {
				message1879.setPersonalizado3("true");
				estado.setCodTipo("04");
				estado.setCodDescripcion("Entrega Parcial");
				estado.setCodId("51");
				estado.setCdIdDescripcion("Expectativ");
			}
			// Cierre de OC manual y Rechazado
			if ("3".equals(tipo)) {
				message1879.setPersonalizado3("true");
				estado.setCodTipo("03");
				estado.setCodDescripcion("Rechazado");
				estado.setCodId("51");
				estado.setCdIdDescripcion("Expectativ");
			}
			// Cierre de OC manual y Entrega parcial
			if ("4".equals(tipo)) {
				message1879.setPersonalizado3("true");
				estado.setCodTipo("04");
				estado.setCodDescripcion("Entrega Parcial");
				estado.setCodId("51");
				estado.setCdIdDescripcion("Expectativ");
			}
			// JPE 20200527
			// Extraviado
			if ("5".equals(tipo)) {
				message1879.setPersonalizado1("true");
				message1879.setPersonalizado3("true");
				estado.setCodTipo("02");
				estado.setCodDescripcion("No Entregado");
				estado.setCodId("06");
				estado.setCdIdDescripcion("Por Transp");
			}

			message1879.setEstado(estado);

			bbr.b2b.regional.logistic.xml.int1879.ListadoProductos listadoproductos = objFactory.createListadoProductos();

			// Recorrer el detalle de despacho
			if ("0".equals(tipo) || "1".equals(tipo) || "2".equals(tipo) || "5".equals(tipo)) {
				for (DODeliveryDetailW dodd : doddArr) {
					bbr.b2b.regional.logistic.xml.int1879.Producto producto = objFactory.createProducto();

					ItemW item = null;

					if (!itemMap.containsKey(dodd.getItemid())) {
						item = itemserver.getById(dodd.getItemid());
						itemMap.put(dodd.getItemid(), item);
					}

					item = itemMap.get(dodd.getItemid());

					producto.setCodProducto(item.getInternalcode());
					producto.setCtdadEnviada(orderdetailMap.get(item.getId()).getUnits().doubleValue());
					producto.setCtdadEntregada(orderdetailMap.get(item.getId()).getReceivedunits().doubleValue());
					producto.setUnidMedida("CU");
					producto.setNumLinea(orderdetailMap.get(item.getId()).getVisualorder().intValue());
					producto.setCodDepartmento(department.getCode());

					listadoproductos.getProducto().add(producto);
				}
			} else {
				// se recorre el detalle de orden (tipo 3 y 4)
				for (DirectOrderDetailW od : directorderdetails) {
					bbr.b2b.regional.logistic.xml.int1879.Producto producto = objFactory.createProducto();

					ItemW item = null;

					if (!itemMap.containsKey(od.getItemid())) {
						item = itemserver.getById(od.getItemid());
						itemMap.put(od.getItemid(), item);
					}

					item = itemMap.get(od.getItemid());

					producto.setCodProducto(item.getInternalcode());
					producto.setCtdadEnviada(orderdetailMap.get(item.getId()).getUnits().doubleValue());
					producto.setCtdadEntregada(orderdetailMap.get(item.getId()).getReceivedunits().doubleValue());
					producto.setUnidMedida("CU");
					producto.setNumLinea(orderdetailMap.get(item.getId()).getVisualorder().intValue());

					listadoproductos.getProducto().add(producto);
				}
			}

			message1879.setListadoProductos(listadoproductos);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1879, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, String.valueOf(directorder.getNumber().longValue()), "INT1879");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1879" + "\",\"" + directorder.getNumber().longValue() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1879" + "\"");

			// planifica el envio
			schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_int1879", "int1879", directorder.getNumber().toString(), result);

		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Enviando mensaje INT1879 para despacho " + String.valueOf(directorder.getNumber().longValue()));

	}

	public void doSendInt1846(DirectOrderW directorder, DODeliveryW dodelivery, DODeliveryDetailW[] doddArr, DirectOrderDetailW[] details, String tipo) {

		try {
			Map<Long, ItemW> itemMap = new HashMap<Long, ItemW>();
			VendorW vendor = vendorserver.getById(directorder.getVendorid());

			HashMap<Long, DirectOrderDetailW> detailByItem = new HashMap<Long, DirectOrderDetailW>();
			for (int i = 0; i < details.length; i++) {
				detailByItem.put(details[i].getItemid(), details[i]);
			}

			GregorianCalendar c = new GregorianCalendar();
			XMLGregorianCalendar date;
			if ("1".equals(tipo)) {
				c.setTime(dodelivery.getCurrentstdate());
			}
			if ("2".equals(tipo)) {
				c.setTime(new Date());
			}
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			JAXBContext jc = getJC1846();
			bbr.b2b.regional.logistic.xml.int1846.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.int1846.ObjectFactory();
			bbr.b2b.regional.logistic.xml.int1846.Message message1846 = objFactory.createMessage();
			message1846.setOrigen("B2B");
			message1846.setAccion("update");
			message1846.setTipo("ASNPD");
			message1846.setIdCompania("1001");
			message1846.setLocalizacion("Spanish (Mexico)");
			message1846.setTipoOrigen("W");
			message1846.setCancelado("0");
			message1846.setNumeroAsn(directorder.getDistributionordernumber());
			message1846.setTipoAsn("20");
			message1846.setEstadoAsn("20");
			message1846.setCodProveedor(vendor.getCode());
			message1846.setFechaCreacion(date);

			bbr.b2b.regional.logistic.xml.int1846.Cliente cliente = objFactory.createCliente();

			cliente.setCiudad(directorder.getClientcity());
			cliente.setCodPostal(directorder.getClientcommune());
			cliente.setComuna(directorder.getClientcommune());
			cliente.setDireccion(directorder.getClientaddress());
			cliente.setPais("CL");

			message1846.setCliente(cliente);

			bbr.b2b.regional.logistic.xml.int1846.DetalleAsn detalleasn = objFactory.createDetalleAsn();

			if ("1".equals(tipo)) {
				for (DODeliveryDetailW dodd : doddArr) {
					bbr.b2b.regional.logistic.xml.int1846.Detalle detalle = objFactory.createDetalle();

					ItemW item = null;

					if (!itemMap.containsKey(dodd.getItemid())) {
						item = itemserver.getById(dodd.getItemid());
						itemMap.put(dodd.getItemid(), item);
					}

					item = itemMap.get(dodd.getItemid());

					detalle.setAccion("Create");
					detalle.setCancelado("0");
					detalle.setTipoLpn("LPN");
					detalle.setNumOrdencompra(directorder.getDistributionordernumber());
					detalle.setNumLinea(detailByItem.get(item.getId()).getVisualorder().intValue());
					detalle.setCodProducto(item.getInternalcode());
					detalle.setCantidad(dodd.getAvailableunits());
					detalle.setUnidMedida("CU");

					detalleasn.getDetalle().add(detalle);
				}
			} else {
				for (DirectOrderDetailW dod : details) {
					bbr.b2b.regional.logistic.xml.int1846.Detalle detalle = objFactory.createDetalle();

					ItemW item = null;

					if (!itemMap.containsKey(dod.getItemid())) {
						item = itemserver.getById(dod.getItemid());
						itemMap.put(dod.getItemid(), item);
					}

					item = itemMap.get(dod.getItemid());

					detalle.setAccion("Create");
					detalle.setCancelado("0");
					detalle.setTipoLpn("LPN");
					detalle.setNumOrdencompra(directorder.getDistributionordernumber());
					detalle.setNumLinea(detailByItem.get(item.getId()).getVisualorder().intValue());
					detalle.setCodProducto(item.getInternalcode());
					detalle.setCantidad(dod.getUnits());
					detalle.setUnidMedida("CU");

					detalleasn.getDetalle().add(detalle);
				}
			}
			message1846.setDetalleAsn(detalleasn);

			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(message1846, sw);
			String result = sw.toString();

			// RESPALDA MENSAJE
			doBackUpMessage(result, directorder.getNumber().toString(), "INT1846");

			// Se registra el resultado de carga de mensajes en un log particular
			logger_ack.info(",\"" + "INT1846" + "\",\"" + directorder.getNumber().toString() + "\",\"" + "" + "\",\"" + "Se envió mensaje INT1846" + "\"");

			// planifica el envio
			schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_int1846", "int1846", directorder.getNumber().toString(), result);

			// // Se envía mensaje de ACK a la cola
			// try {
			// QSender.getInstance().putMessage("jboss/qcf_pariscl", "jboss/wsmq/q_int1846", result);
			// } catch (Exception ex) {
			// // Si ocurrió un error al enviar el archivo, se graba el mensaje para reencolarlo
			// MsgRecoveryServices msgRecoveryServices = MsgRecoveryServices.getInstance();
			// String msgtype = RegionalLogisticConstants.getInstance().getBUSINESSAREA() +
			// RegionalLogisticConstants.getInstance().getCOUNTRYCODE() + "_INT1846_" + directorder.getNumber();
			// try {
			// msgRecoveryServices.saveMsgToFile(msgtype, result, ex);
			// } catch (Exception e1) {
			// logger.debug(e1.getLocalizedMessage());
			// }
			// }
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger.error("Generación de XML de rechazo via JAXB fallo. " + e2.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Enviando mensaje INT1846 para orden " + directorder.getNumber().toString());
	}
	
	public DODeliveryStateTypeArrayResultDTO getDoDeliveryStateType() {
		DODeliveryStateTypeArrayResultDTO resultW = new DODeliveryStateTypeArrayResultDTO();
		try {
			DODeliveryStateTypeW[] doDeliveryStateTypeW = dodeliverystatetypeserver.getAllAsArray();
			DODeliveryStateTypeDTO[] doDeliveryStateTypeDTO = new DODeliveryStateTypeDTO[doDeliveryStateTypeW.length];
			BeanExtenderFactory.copyProperties(doDeliveryStateTypeW, doDeliveryStateTypeDTO, DODeliveryStateTypeDTO.class);
			resultW.setDodeliverystatetypes(doDeliveryStateTypeDTO);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		return resultW;
	}

	@Resource
	private ManagedExecutorService executor;

	private void doBackUpMessage(String content, String number, String msgType){
		
		// EJECUTA UNA TAREA QUE RESPALDA EL MENSAJE.
		// ESTA ES INDEPENDIENTE DE LA CARGA DEL MENSAJE.
		try{
			executor.submit(new BackUpUtils(content, number, msgType));			
		}catch (RejectedExecutionException e) {
			e.printStackTrace();
		}			
	}

	public AddDODeliveryOfDirectOrdersWSResultDTO doAddDODeliveryOfDirectOrdersWS(AddDODeliveryOfDirectOrdersWSInitParamDTO initParamDTO) {
		AddDODeliveryOfDirectOrdersWSResultDTO resultDTO = new AddDODeliveryOfDirectOrdersWSResultDTO();

		try {
			Date now = new Date();

			List<Long> ordernumbers = new ArrayList<Long>();
			List<String> itemsMsgeList = new ArrayList<String>();
			List<Long> orderIds = new ArrayList<Long>();
			HashMap<Long, DirectOrderDetailW> oddMapBySku = new HashMap<Long, DirectOrderDetailW>();

			boolean sendMsg = false;

			String error = "";

			// Obtener los tipos de estado de orden directa 'Liberada', 'Modificada Paris' y 'Reprograma Proveedor'
			DirectOrderStateTypeW dostReleased = directorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
			DirectOrderStateTypeW dostParisModified = directorderstatetypeserver.getByPropertyAsSingleResult("code", "MOD_PARIS");
			DirectOrderStateTypeW dostVendorRescheduled = directorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROG_PROV");
			
			VendorW vendor = null;
			try {
				vendor = vendorserver.getByPropertyAsSingleResult("rut", initParamDTO.getRutproveedor().trim());
			} catch (OperationFailedException e) {
				getSessionContext().setRollbackOnly();
				error = "No se puede obtener el proveedor";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004", error);
			}
			
			// Obtener el tipo de estado de despacho 'En Ruta'
			DODeliveryStateTypeW dodstOnRoute = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");

			HashMap<Long, String> results = new HashMap<Long, String>();
			Map<String, ItemW> itemMap = new HashMap<String, ItemW>();
			Map<Long, ItemW> itemByIdMap = new HashMap<Long, ItemW>();
			Map<String, DirectOrderDetailsWSInitParamDTO> dodetailwsMap = new HashMap<String, DirectOrderDetailsWSInitParamDTO>();

			// Validaciones
			DirectOrderW[] orderArr = null;
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			for (DirectOrdersWSInitParamDTO order : initParamDTO.getOrdenes()) {
				if (!ordernumbers.contains(order.getNumero())) {
					ordernumbers.add(order.getNumero());
				} else {
					error = "La orden de compra N° " + order.getNumero() + " viene repetida";
					results = addResult(results, order.getNumero(), error);
				}

				// JMA 20170321
				// Validar que la fecha de compromiso imformada no sea mayor a (hoy - 2 d�as)
				// Fecha compromiso
				SimpleDateFormat sdfddmmyyyy = new SimpleDateFormat("ddMMyyyy");
				Calendar cfc = Calendar.getInstance();
				cfc.setTime(sdfddmmyyyy.parse(order.getFechaCompromiso().trim()));
				cfc.set(Calendar.HOUR_OF_DAY, 0);
				cfc.set(Calendar.MINUTE, 0);
				cfc.set(Calendar.SECOND, 0);
				cfc.set(Calendar.MILLISECOND, 0);
				Date reprogDate = cfc.getTime();

				// Fecha actual
				Calendar cnow = Calendar.getInstance();
				cnow.setTime(now);
				cnow.set(Calendar.HOUR_OF_DAY, 0);
				cnow.set(Calendar.MINUTE, 0);
				cnow.set(Calendar.SECOND, 0);
				cnow.set(Calendar.MILLISECOND, 0);
				cnow.add(Calendar.DAY_OF_MONTH, -2);
				Date today2 = cnow.getTime();

				if (reprogDate.before(today2)) {
					error = "La fecha de Compromiso [" + order.getFechaCompromiso().trim() + "] informada para la orden " + order.getNumero() + " no es válida ";
					results = addResult(results, order.getNumero(), error);
					continue;
				}

				prop1 = new PropertyInfoDTO("number", "directordernumber", order.getNumero());
				prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
				orderArr = directorderserver.getByPropertiesAsArray(prop1, prop2);

				if (orderArr == null || orderArr.length == 0) {
					error = "La orden " + order.getNumero() + " no existe";
					results = addResult(results, order.getNumero(), error);
					continue;
				}

				DirectOrderW directOrder = directorderserver.getByPropertyAsSingleResult("number", order.getNumero());

				// JMA 20150701
				// Validar que la oc tenga un solo despacho en curso
				DODeliveryW[] deliveries = dodeliveryserver.getByPropertyAsArray("directorder.id", directOrder.getId());
				for (DODeliveryW delivery : deliveries) {
					if (delivery.getCurrentstatetypeid().longValue() == dodstOnRoute.getId().longValue()) {
						error = "La orden de compra N° " + order.getNumero() + " posee un despacho en curso";
						results = addResult(results, order.getNumero(), error);
					}

				}

				DirectOrderStateTypeW statetype = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
				if (!statetype.getValid()) {
					error = "La orden " + order.getNumero() + " no se encuentra en un estado vigente";
					results = addResult(results, order.getNumero(), error);
				}

				Double pendingunits = 0d;
				DirectOrderDetailW[] detailsod = directorderdetailserver.getDirectOrderDetailDataofDirectOrder(directOrder.getId());
				for (int k = 0; k < detailsod.length; k++) {
					DirectOrderDetailW detail = detailsod[k];
					pendingunits = pendingunits + detail.getPendingunits();

					oddMapBySku.put(detailsod[k].getItemid(), detailsod[k]);
				}
				if (pendingunits == 0) {
					error = "La orden " + order.getNumero() + " no tiene valores pendientes";
					results = addResult(results, order.getNumero(), error);
				}

				ItemW[] items = itemserver.getByQueryAsArray("select item from Item as item, DirectOrderDetail as dod, DirectOrder as do where dod.item = item and dod.directorder = do and do.number = " + order.getNumero());
				itemMap.clear();
				for (int i = 0; i < items.length; i++) {
					itemMap.put(items[i].getInternalcode(), items[i]);
				}

				itemsMsgeList.clear();
				Long units = 0l;
				DirectOrderDetailsWSInitParamDTO[] details = order.getDetalles();
				ItemW item = null;
				DirectOrderDetailW odd;

				if (details.length == 0) {
					error = "No se informan detalles para la OC " + order.getNumero();
					results = addResult(results, order.getNumero(), error);
				}

				for (DirectOrderDetailsWSInitParamDTO detail : details) {
					if (!itemMap.containsKey(detail.getSku())) {
						error = "El producto con SKU " + detail.getSku() + " no existe para la OC " + order.getNumero();
						results = addResult(results, order.getNumero(), error);
						continue; // para evitar problemas en las siguientes validaciones
					}

					if (!itemsMsgeList.contains(detail.getSku())) {
						itemsMsgeList.add(detail.getSku());
					}
					else {
						error = "En la OC " + order.getNumero() + " se repite el SKU " + detail.getSku();
						results = addResult(results, order.getNumero(), error);
					}

					item = itemMap.get(detail.getSku());
					if (item != null) {
						odd = oddMapBySku.get(item.getId());

						if (odd == null) {
							error = "El producto con SKU " + detail.getSku() + " no existe para la OC " + order.getNumero();
							results = addResult(results, order.getNumero(), error);
							continue;
						}

						if (detail.getCantidad() == null) {
							error = "Se debe informar cantidades para el producto con SKU " + detail.getSku() + " y orden " + order.getNumero();
							results = addResult(results, order.getNumero(), error);
							continue;
						}

						if (odd.getPendingunits() == 0) {
							error = "El producto con SKU " + detail.getSku() + " no posee unidades pendientes por entregar en la OC " + order.getNumero();
							results = addResult(results, order.getNumero(), error);
						}

						if (odd.getPendingunits() > 0 && detail.getCantidad() > odd.getPendingunits()) {
							error = "El producto con SKU " + detail.getSku() + " supera la cantidad pendiente para despachar en la OC " + order.getNumero();
							results = addResult(results, order.getNumero(), error);
						}
					}

					units = units + detail.getCantidad();
				}

				if (details.length > 0 && units == 0) {
					error = "Todas las cantidades para la  OC " + order.getNumero() + " se están informando con valores en cero";
					results = addResult(results, order.getNumero(), error);
				}
			}

			Calendar rightNow = Calendar.getInstance();
			rightNow.set(Calendar.HOUR_OF_DAY, 0);
			rightNow.set(Calendar.MINUTE, 0);
			rightNow.set(Calendar.SECOND, 0);
			rightNow.set(Calendar.MILLISECOND, 0);

			List<DODeliveryW> arrDeliveries = new ArrayList<DODeliveryW>();

			for (DirectOrdersWSInitParamDTO order : initParamDTO.getOrdenes()) {
				// Solo para OC sin error
				if (results.containsKey(order.getNumero())) {
					continue;
				}

				DirectOrderW directOrder = directorderserver.getByPropertyAsSingleResult("number", order.getNumero());

				sendMsg = false;

				orderIds.add(directOrder.getId());

				List<DODeliveryDetailW> detailsArr = new ArrayList<DODeliveryDetailW>();

				// Aceptar la orden autom�ticamente si est� en estado 'Liberada' o 'Modificada Paris'
				if (directOrder.getCurrentstatetypeid().equals(dostReleased.getId()) ||
						directOrder.getCurrentstatetypeid().equals(dostParisModified.getId())) {
					directOrder = directorderreportmanager.doAcceptDirectOrders(directOrder.getId())[0];
				}

				// Obtener los detalles de orden
				// Por cada detalle con unidades pendientes crear uno correspondiente de despacho con el valor enviado
				ItemW[] items = itemserver.getItemsByDirectOrder(directOrder.getId());
				itemByIdMap.clear();
				for (int i = 0; i < items.length; i++) {
					itemByIdMap.put(items[i].getId(), items[i]);
				}

				ItemW item = null;
				dodetailwsMap.clear();
				for (DirectOrderDetailsWSInitParamDTO detailWS : order.getDetalles()) {
					dodetailwsMap.put(detailWS.getSku(), detailWS);
				}

				DirectOrderDetailsWSInitParamDTO detailws;
				DirectOrderDetailW[] details = directorderdetailserver.getDirectOrderDetailDataofDirectOrder(directOrder.getId());
				for (int k = 0; k < details.length; k++) {
					DirectOrderDetailW detail = details[k];
					if (detail.getPendingunits() > 0) {
						DODeliveryDetailW oddetail = new DODeliveryDetailW();
						oddetail.setItemid(detail.getItemid());
						oddetail.setAvailableunits(0d);
						oddetail.setPendingunits(detail.getPendingunits());
						oddetail.setReceivedunits(0D);

						// Actualizar el detalle si se envió un valor v�a WS
						item = itemByIdMap.get(detail.getItemid());
						detailws = dodetailwsMap.get(item.getInternalcode());
						if (detailws != null) {
							oddetail.setAvailableunits(detailws.getCantidad().doubleValue());
						}

						// Agregar el detalle al arreglo
						detailsArr.add(oddetail);
					}
				}

				SimpleDateFormat sdfddmmyyyy = new SimpleDateFormat("ddMMyyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(sdfddmmyyyy.parse(order.getFechaCompromiso().trim()));
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				Date reprogdate = c.getTime();
				
				// Obtener los tipos de auditor�a VeV 'Cambio estado OC VeV' y 'Cambio estado despacho VeV'
				VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
				VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

				// Crear un lote de despacho 'En Ruta' asociado al tipo de flujo, proveedor y tipo de orden
				// 20091222 IRA
				// Actualizar la fecha de despacho
				DODeliveryW delivery = new DODeliveryW();
				delivery.setCommiteddate(reprogdate);
				delivery.setOriginaldate(reprogdate);			
				delivery.setCurrentstdate(now);
				delivery.setCurrentstatetypeid(dodstOnRoute.getId());
				delivery.setCurrentstwho("B2B Link");
				delivery.setCurrentstcomment("Vía WS");
				delivery.setOrderid(directOrder.getId());
				delivery.setVendorid(vendor.getId());
				delivery.setNumber(deliveryserver.getNextSequenceDeliveryNumber());

				DODeliveryW newdelivery = dodeliveryserver.addDODelivery(delivery);

				// Agregar el estado del despacho
				DODeliveryStateW doDeliveryState = new DODeliveryStateW();
				doDeliveryState.setDeliveryid(newdelivery.getId());
				doDeliveryState.setDeliverystatetypeid(dodstOnRoute.getId());
				doDeliveryState.setWhen(now);
				doDeliveryState.setDeliverystatedate(delivery.getCurrentstdate());
				doDeliveryState.setWho("B2B Link");
				doDeliveryState.setComment("Vía WS");

				dodeliverystateserver.addDODeliveryState(doDeliveryState);
				
				// JPE 20200615
				// Registro de auditor�a
				VeVAuditW vevAudit = new VeVAuditW();
				vevAudit.setWhen(now);
				vevAudit.setUsername("B2B Link");
				vevAudit.setUsertype("Sistema");
				vevAudit.setUserenterprisecode("77575650");
				vevAudit.setUserenterprisename("BBR SERVICIOS LTDA");
				vevAudit.setInterfacecontent("");
				vevAudit.setItem("Estado Despacho VeV PD");
				vevAudit.setItemvalue(directOrder.getNumber().toString());
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(dodstOnRoute.getName());
				vevAudit.setVendorid(directOrder.getVendorid());
				vevAudit.setVevupdatetypeid(utDeliveryState.getId());
				
				vevauditserverlocal.addVeVAudit(vevAudit);

				// Agregar los detalles de despacho
				for (Iterator iter = detailsArr.iterator(); iter.hasNext();) {
					DODeliveryDetailW oddetail = (DODeliveryDetailW) iter.next();
					oddetail.setDodeliveryid(newdelivery.getId());
					dodeliverydetailserver.addDODeliveryDetail(oddetail);
				}
				
				// Agregar el lote al mapa
				arrDeliveries.add(newdelivery);

				// Si la fecha de reprogramación (ddmmyyyy) es distinta a la de la orden correspondiente,
				// cambiar el estado a 'Reprograma Proveedor' con el comentario 'Reprogramada al momento del despacho'
				if (!sdfddmmyyyy.format(directOrder.getCurrentdeliverydate()).equals(sdfddmmyyyy.format(reprogdate))) {
					DirectOrderStateTypeW dostActual = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
					
					// Actualizar la orden
					directOrder.setCurrentstatetypedate(now);
					directOrder.setCurrentstatetypewho("B2B Link");
					directOrder.setCurrentstatetypecomment("Reprogramada al momento del despacho");
					directOrder.setCurrentstatetypeid(dostVendorRescheduled.getId());
					directOrder.setCurrentdeliverydate(reprogdate);
					
					DirectOrderStateW directOrderState = new DirectOrderStateW();
					directOrderState.setOrderid(directOrder.getId());
					directOrderState.setOrderstatetypeid(dostVendorRescheduled.getId());
					directOrderState.setWhen(now);
					directOrderState.setWho("B2B Link");
					directOrderState.setComment("Reprogramada al momento del despacho");
					directorderstateserver.addDirectOrderState(directOrderState);
				
					// 20200611
					// Registro de auditor�a
					vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername("B2B Link");
					vevAudit.setUsertype("Sistema");
					vevAudit.setUserenterprisecode("77575650");
					vevAudit.setUserenterprisename("BBR SERVICIOS LTDA");
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado OC VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dostActual.getName());
					vevAudit.setFinaldata(dostVendorRescheduled.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utOrderState.getId());					
					
					vevauditserverlocal.addVeVAudit(vevAudit);

					// Enviar mensaje 1887
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
					doSendInt1887(directOrder, "Cambio Fecha B2B", sdf.format(reprogdate), "", null);
				}

				// Asignar el estado actual
				if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() == 0) {
					sendMsg = true;
				}

				directOrder.setDodeliveryid(newdelivery.getId());
				directOrder = directorderserver.updateDirectOrder(directOrder);

				// Enviar mensaje 1846
				if (sendMsg) {
					doSendInt1846(directOrder, delivery, detailsArr.toArray(new DODeliveryDetailW[detailsArr.size()]), details, "1");
				}

				results = addResult(results, order.getNumero(), "OK");
			}

			// Recalcular unidades
			for (Long orderid : orderIds) {
				directorderreportmanager.doCalculateTotalOfDirectOrder(orderid);
			}

			AddDODeliveryOfDirectOrdersWSResultDetailDTO[] details = new AddDODeliveryOfDirectOrdersWSResultDetailDTO[results.size()];
			Iterator it = results.keySet().iterator();
			int i = 0;
			while (it.hasNext()) {
				Long numeroOC = (Long) it.next();
				String description = results.get(numeroOC);
				String code = "OK".equals(description) ? "0" : "1";
				details[i] = new AddDODeliveryOfDirectOrdersWSResultDetailDTO(numeroOC, code, description);
				i++;
			}

			resultDTO.setDetails(details);

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004");
		}
		
		return resultDTO;
	}

	public UpdateDODeliveryWSResultDTO doUpdateDODeliveryWS(UpdateDODeliveryWSInitParamDTO initParamDTO) {
		UpdateDODeliveryWSResultDTO resultDTO = new UpdateDODeliveryWSResultDTO();

		try {
			// Almacena los detalles de despacho
			Map<DODeliveryDetailPK, DODeliveryDetailW> doddMap = new TreeMap<DODeliveryDetailPK, DODeliveryDetailW>();

			DODeliveryStateTypeW dodstOnRoute = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");

			// Obtener el proveedor
			VendorW vendor = null;
			try {
				vendor = vendorserver.getByPropertyAsSingleResult("rut", initParamDTO.getRutproveedor());
			
			} catch (Exception e1) {
				e1.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
			}
			
			String error = "";

			DirectOrderW[] doArr = directorderserver.getByPropertyAsArray("number", initParamDTO.getNumeroOrden());
			if (doArr == null || doArr.length == 0) {
				getSessionContext().setRollbackOnly();
				error = "No existe la OC N° " + initParamDTO.getNumeroOrden();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}

			DirectOrderW directOrder = doArr[0];
			if (directOrder.getVendorid().longValue() != vendor.getId()) {
				getSessionContext().setRollbackOnly();
				error = "No existe la OC N° " + initParamDTO.getNumeroOrden();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}
			
			// Validar que la OC posea un despacho actual en estado 'En Ruta'
			DODeliveryW dodelivery = null;
			if (directOrder.getDodeliveryid() == null || directOrder.getDodeliveryid() <= 0) {
				getSessionContext().setRollbackOnly();
				error = "La OC N° " + initParamDTO.getNumeroOrden() + " no tiene despacho creado para actualizar";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}
			else {
				dodelivery = dodeliveryserver.getById(directOrder.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
				if (dodelivery.getCurrentstatetypeid().longValue() != dodstOnRoute.getId().longValue()) {
					getSessionContext().setRollbackOnly();
					error = "La OC N° " + initParamDTO.getNumeroOrden() + " no posee un despacho en estado 'En Ruta'";
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
				}
			}

			// Validar que el comentario ingresado no supere la cantidad de caracteres definidos
			if (initParamDTO.getComentario() == null)
				initParamDTO.setComentario("");

			if (initParamDTO.getComentario().length() > 200) {
				getSessionContext().setRollbackOnly();
				error = "El comentario indicado superó la cantidad de caracteres permitidos";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}
			
			DODeliveryStateTypeW doDst = null;
			try {
				doDst = dodeliverystatetypeserver.getByPropertyAsSingleResult("codews", initParamDTO.getNuevoestado());
			} catch (OperationFailedException e) {
				getSessionContext().setRollbackOnly();
				error = "El estado '" + initParamDTO.getNuevoestado() + "' no existe";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}
			
			// Validar que el nuevo estado corresponda a alguno de los marcados como visibles
			if (!doDst.getShowable()) {
				getSessionContext().setRollbackOnly();
				error = "El nuevo estado '" + initParamDTO.getNuevoestado() + "' no es válido para actualizar el despacho";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(format.parse(initParamDTO.getFechaEntrega().trim()));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);			
			Date reprogDate = cal.getTime();
			
			// Validar si el nuevo estado es 'Entregado'
			if (doDst.getCode().equalsIgnoreCase("REC_CONFORME")) {
				cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				Date today = cal.getTime();
				
				// Validar si la fecha de reprogramación de la orden es anterior o igual a hoy
				if (!directOrder.getCurrentdeliverydate().after(today)) {
					// Validar que la nueva fecha sea igual o posterior a hoy
					if (reprogDate.before(today)) {
						getSessionContext().setRollbackOnly();
						error = "La fecha de entrega ingresada debe ser igual o posterior al día de hoy debido a que está sobre la fecha comprometida de entrega a cliente";
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
					}
				}
			}

			// Detalles de despacho
			DODeliveryDetailW[] doddArr = dodeliverydetailserver.getByPropertyAsArray("id.dodeliveryid", dodelivery.getId());
			DODeliveryDetailPK doddPK = null;
			for (DODeliveryDetailW dodd : doddArr) {
				doddPK = new DODeliveryDetailPK(dodd.getDodeliveryid(), dodd.getItemid());

				if (!doddMap.containsKey(doddPK)) {
					doddMap.put(doddPK, dodd);
				}
			}

			ItemW item = null;
			for (DirectOrderDetailsWSInitParamDTO detail : initParamDTO.getDetalles()) {
				// Validar que todos los SKU informados est�n asociados al despacho.
				try {
					item = itemserver.getByPropertyAsSingleResult("internalcode", detail.getSku());
				} catch (OperationFailedException e) {
					getSessionContext().setRollbackOnly();
					error = "El producto SKU " + detail.getSku() + " no existe";
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
				}
				
				doddPK = new DODeliveryDetailPK(dodelivery.getId(), item.getId());
				if (!doddMap.containsKey(doddPK)) {
					getSessionContext().setRollbackOnly();
					error = "El producto SKU " + detail.getSku() + " no pertenece al despacho N° " + dodelivery.getNumber() + " de la OC " + initParamDTO.getNumeroOrden();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
				}

				// Validar que las cantidades indicadas como entregadas sean mayores o iguales a cero
				if (detail.getCantidad().longValue() < 0) {
					getSessionContext().setRollbackOnly();
					error = "Las cantidades entregadas del producto SKU " + detail.getSku() + " para la OC " + initParamDTO.getNumeroOrden() + " deben ser mayores o iguales a cero";
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
				}
			}

			/** *************** */
			Date now = new Date();

			boolean recconf = false;

			// Actualizar el despacho
			ArrayList<DODeliveryDetailW> doddList = new ArrayList<DODeliveryDetailW>();
			
			// Obtener los tipos de auditor�a VeV 'Cambio estado OC VeV' y 'Cambio estado despacho VeV'
			VeVUpdateTypeW utOrderState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "OC_STATE_CHANGE");
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

			dodelivery.setCurrentstdate(reprogDate);
			dodelivery.setCurrentstatetypeid(doDst.getId());
			dodelivery.setCurrentstwho("B2B Link");
			dodelivery.setCurrentstcomment(initParamDTO.getComentario() == null ? "" : initParamDTO.getComentario());

			dodelivery = dodeliveryserver.updateDODelivery(dodelivery);
			
			// Agregar al historial
			DODeliveryStateW doDeliveryState = new DODeliveryStateW();
			doDeliveryState.setDeliveryid(dodelivery.getId());
			doDeliveryState.setDeliverystatetypeid(doDst.getId());
			doDeliveryState.setWhen(now);
			doDeliveryState.setDeliverystatedate(dodelivery.getCurrentstdate());
			doDeliveryState.setComment(initParamDTO.getComentario() == null ? "" : initParamDTO.getComentario());
			doDeliveryState.setWho("B2B Link");

			dodeliverystateserver.addDODeliveryState(doDeliveryState);
			
			// JPE 20200615
			// Registro de auditor�a
			VeVAuditW vevAudit = new VeVAuditW();
			vevAudit.setWhen(now);
			vevAudit.setUsername("B2B Link");
			vevAudit.setUsertype("Sistema");
			vevAudit.setUserenterprisecode("77575650");
			vevAudit.setUserenterprisename("BBR SERVICIOS LTDA");
			vevAudit.setInterfacecontent("");
			vevAudit.setItem("Estado Despacho VeV PD");
			vevAudit.setItemvalue(directOrder.getNumber().toString());
			vevAudit.setInitialdata(dodstOnRoute.getName());
			vevAudit.setFinaldata(doDst.getName());
			vevAudit.setVendorid(directOrder.getVendorid());
			vevAudit.setVevupdatetypeid(utDeliveryState.getId());
			
			vevauditserverlocal.addVeVAudit(vevAudit);

			double totalDeliveryReceived = 0D;
			for (DirectOrderDetailsWSInitParamDTO detail : initParamDTO.getDetalles()) {
				item = itemserver.getByPropertyAsSingleResult("internalcode", detail.getSku());
				doddPK = new DODeliveryDetailPK(dodelivery.getId(), item.getId());
				DODeliveryDetailW doddTmp = doddMap.get(doddPK);

				if (doDst.getCode().equalsIgnoreCase("REC_CONFORME")) {
					recconf = true;

					if (detail.getCantidad().doubleValue() > doddTmp.getAvailableunits().doubleValue()) {
						error = "Las cantidades recepcionadas del producto SKU " + item.getInternalcode() + " en el despacho " + dodelivery.getNumber() + " sobrepasan lo comprometido";
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
					}

					doddTmp.setReceivedunits(detail.getCantidad().doubleValue());
					doddTmp = dodeliverydetailserver.updateDODeliveryDetail(doddTmp);
				}

				doddList.add(doddTmp);
				totalDeliveryReceived += detail.getCantidad().doubleValue();
			}

			if (recconf && totalDeliveryReceived <= 0) {
				getSessionContext().setRollbackOnly();
				error = "El despacho de la OC " + initParamDTO.getNumeroOrden() + " tendrá estado 'Entregado', por lo tanto, las cantidades entregadas deben ser mayores a cero";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

			// Agregar comprobante de recepción de cliente
			DODeliveryImageW dodeliveryimage = new DODeliveryImageW();
			dodeliveryimage.setDodeliveryid(dodelivery.getId());
			dodeliveryimage.setCurrenttimestamp(now);
			// dodeliveryimage.setImagedata(initParams.getActaentrega());
			dodeliveryimage.setReceivername(initParamDTO.getNombrereceptor());
			dodeliveryimage.setReceiverrut(initParamDTO.getRutreceptor());
			dodeliveryimage.setReceptiondate(dateFormat.parse(initParamDTO.getFechaEntrega()));
			dodeliveryimage.setTruckdispatcherid(null);

			if (initParamDTO.getActaentrega() != null && initParamDTO.getActaentrega().length() > 0) {
				File imagefile = null;
				try {
					// Almacenar imagen en disco
					String filename = String.valueOf(System.nanoTime()) + "." + RegionalLogisticConstants.getInstance().getACTAENTREGA_EXTENSION_IMAGE();
					imagefile = Base64Utils.getInstance().saveToFile(initParamDTO.getActaentrega(), RegionalLogisticConstants.getInstance().getACTAENTREGA_FOLDER_IMAGE(), filename);
				} catch (Exception e) {
					getSessionContext().setRollbackOnly();
					error = "No es posible almacenar la imagen";
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new UpdateDODeliveryWSResultDTO(), "L6004", error);
				}
				dodeliveryimage.setFilename(imagefile.getName());
			}
			dodeliveryimage = dodeliveryimageserver.addDODeliveryImage(dodeliveryimage);

			// Actualizar los reportes
			dodeliverystateserver.flush();
			directOrder = directorderreportmanager.doCalculateTotalOfDirectOrder(dodelivery.getOrderid());

			DirectOrderStateTypeW dost = null;
			// 'Recepcionada'
			if (doDst.getCode().equalsIgnoreCase("REC_CONFORME")) {
				dost = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
			}
			// 'Rechazada'
			else if (doDst.getCode().equalsIgnoreCase("EXPECTATIVAS")) {
				dost = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
			}

			if (dost != null) {
				DirectOrderStateTypeW dostActual = directorderstatetypeserver.getById(directOrder.getCurrentstatetypeid());
				
				directOrder.setCurrentstatetypeid(dost.getId());
				directOrder.setCurrentstatetypedate(now);
				directOrder.setCurrentstatetypewho("B2B Link");
				directOrder.setCurrentstatetypecomment("Vía WS");
				directorderserver.updateDirectOrder(directOrder);
				
				DirectOrderStateW directOrderState = new DirectOrderStateW();
				directOrderState.setOrderid(directOrder.getId());
				directOrderState.setOrderstatetypeid(dost.getId());
				directOrderState.setWhen(now);
				directOrderState.setWho("B2B Link");
				directOrderState.setComment("Vía WS");
				directorderstateserver.addDirectOrderState(directOrderState);			
				dodeliverystateserver.flush();
				
				// 20200611
				// Registro de auditor�a
				vevAudit = new VeVAuditW();
				vevAudit.setWhen(now);
				vevAudit.setUsername("B2B Link");
				vevAudit.setUsertype("Sistema");
				vevAudit.setUserenterprisecode("77575650");
				vevAudit.setUserenterprisename("BBR SERVICIOS LTDA");
				vevAudit.setInterfacecontent("");
				vevAudit.setItem("Estado OC VeV PD");
				vevAudit.setItemvalue(directOrder.getNumber().toString());
				vevAudit.setInitialdata(dostActual.getName());
				vevAudit.setFinaldata(dost.getName());
				vevAudit.setVendorid(directOrder.getVendorid());
				vevAudit.setVevupdatetypeid(utOrderState.getId());					
				
				vevauditserverlocal.addVeVAudit(vevAudit);
			}

			// Enviar mensaje
			DirectOrderDetailW[] directorderdetails = directorderdetailserver.getByPropertyAsArray("id.orderid", dodelivery.getOrderid());
			if (doDst.getCode().equalsIgnoreCase("REC_CONFORME")) {
				if (directOrder.getPendingunits().doubleValue() == 0) {
					doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "1");
				}
				else {
					doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "2");
				}
			}
			else if (doDst.getCode().equalsIgnoreCase("EXPECTATIVAS")) {
				doSendInt1879(directOrder, dodelivery.getNumber(), doddList.toArray(new DODeliveryDetailW[doddList.size()]), directorderdetails, "0");
			}
			else {
				doSendInt1887(directOrder, "CambioEstadoB2B", doDst.getName(), dodelivery.getCurrentstcomment(), doDst);
			}

			resultDTO.setStatuscode("0");
			resultDTO.setStatusmessage("OK");

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DODeliveryReportResultDTO getDODeliveryReport(DODeliveryReportInitParamDTO initParamDTO, boolean byFilter) {
		DODeliveryReportResultDTO resultDTO = new DODeliveryReportResultDTO();
		DODeliveryReportDataDTO[] deliveryreport = null;
		int total = 0;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			switch (initParamDTO.getFiltertype()) {
			case 0:
				// EN RUTA
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, null, null);
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), null, null);
				}
				break;
			// JPE 20180420
			case 1:
				// EN PREPARAción COURIER
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, initParamDTO.getSendnumber(), initParamDTO
						.getWithdrawalnumber());
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getSendnumber(), initParamDTO.getWithdrawalnumber());
				}
				break;
			case 2:
				// N°MERO DE OC
				deliveryreport = dodeliveryserver.getDODeliveryReport(initParamDTO.getOcnumber(), null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, null, null);
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(initParamDTO.getOcnumber(), null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), null, null);
				}
				break;
			case 3:
				// N°MERO DE SOLICITUD
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, initParamDTO.getRequestnumber(), null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, null, null);
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, initParamDTO.getRequestnumber(), vendor.getId(), null, null, null, initParamDTO.getFiltertype(), null, null);
				}
				break;
			case 4:
				// RUT CLIENTE
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, initParamDTO.getClientrut(), vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, null, null);
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, initParamDTO.getClientrut(), vendor.getId(), null, null, null, initParamDTO.getFiltertype(), null, null);
				}
				break;
			case 5:
				// ESTADO DE DESPACHO
				Date since = Date.from( initParamDTO.getSince().atZone( ZoneId.systemDefault()).toInstant());

				Calendar cal = Calendar.getInstance();
				cal.setTime(Date.from( initParamDTO.getUntil().atZone( ZoneId.systemDefault()).toInstant()));
				cal.add(Calendar.DAY_OF_YEAR, 1);
				Date until = cal.getTime();

				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, null, vendor.getId(), since, until, initParamDTO.getDostate(), initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, null, null);
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, null, vendor.getId(), since, until, initParamDTO.getDostate(), initParamDTO.getFiltertype(), null, null);
				}
				break;
			case 6:
				// N°MERO DE envío COURIER
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, initParamDTO.getSendnumber(), initParamDTO
						.getWithdrawalnumber());
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getSendnumber(), initParamDTO.getWithdrawalnumber());
				}
				break;
			case 7:
				// N°MERO DE RETIRO COURIER
				deliveryreport = dodeliveryserver.getDODeliveryReport(null, null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true, initParamDTO.getSendnumber(), initParamDTO
						.getWithdrawalnumber());
				if (byFilter) {
					total = dodeliveryserver.getDODeliveryCountReport(null, null, vendor.getId(), null, null, null, initParamDTO.getFiltertype(), initParamDTO.getSendnumber(), initParamDTO.getWithdrawalnumber());
				}
				break;
			default:
				break;
			}

			if (byFilter) {
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(deliveryreport.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setDeliveryreport(deliveryreport);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public DODeliveryDetailReportResultDTO getDODeliveryDetailReport(DODeliveryDetailReportInitParamDTO initParams, boolean isByFilter) {
		DODeliveryDetailReportResultDTO resultW = new DODeliveryDetailReportResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			DODeliveryDetailReportDataDTO[] deliverydetail = null;
			DODeliveryDetailReportTotalDataDTO totalresult = null;
			int total;

			deliverydetail = dodeliverydetailserver.getDODeliveryDetailReport(vendor.getId(), initParams.getDeliveryid(), initParams.getPageNumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (isByFilter) {
				totalresult = dodeliverydetailserver.getDODeliveryDetailCountReport(vendor.getId(), initParams.getDeliveryid());
				total = totalresult.getTotalreg();
				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPageNumber());
				pageInfo.setRows(deliverydetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultW.setPageInfo(pageInfo);
			}

			resultW.setTotals(totalresult);
			resultW.setOrderdeliverydetail(deliverydetail);
			return resultW;
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public UpdateDispatcherEmailResultDTO doUpdateDispatcherEmail(UpdateDODeliveryDispatcherEmail initParamDTO) {

		UpdateDispatcherEmailResultDTO resultDTO = new UpdateDispatcherEmailResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Valida que campo email contenga un valor valido
			if (!EmailValidation.isValid(initParamDTO.getDispatcheremail())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2220");
			}

			// Obtiene Despacho
			DODeliveryW dodelivery = dodeliveryserver.getById(initParamDTO.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			if (!dodelivery.getVendorid().equals(vendor.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2215");
			}

			// Actualiza información del correo
			dodelivery.setDispatcheremail(initParamDTO.getDispatcheremail());
			dodeliveryserver.updateDODelivery(dodelivery);

			resultDTO.setDispatcheremail(initParamDTO.getDispatcheremail());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;

	}
	
	/*
	 * Se almacena solo el primer error por orden
	 */
	private HashMap<Long, String> addResult(HashMap<Long, String> errorMap, Long order, String description) {

		if (!errorMap.containsKey(order)) {
			errorMap.put(order, description);
		}

		return errorMap;

	}

	public PDFDODeliveryDetailReportResultDTO getPDFDODeliveryReport(DODeliveryReportInitParamDTO initParamDTO) {
		PDFDODeliveryDetailReportResultDTO resultW = new PDFDODeliveryDetailReportResultDTO();

		PDFDODeliveryDetailReportDataDTO[] deliveryDetailReportDataDTO = null;
		PDFDODeliveryReportDataDTO deliveryReportDataDTO = null;
		DirectOrderW[] order = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {

			deliveryDetailReportDataDTO = dodeliveryserver.getPDFDeliveryDetailReport(vendor.getId(), initParamDTO.getDeliveryid());
			deliveryReportDataDTO = dodeliveryserver.getPDFDODeliveryReport(vendor.getId(), initParamDTO.getDeliveryid());

			// SE ACEPTA LA OC SI ES PROVEEDOR Y ESTA EN ESTADO LIBERADA O MODIFICADA

			if (order == null || order.length == 0) {
				order = new DirectOrderW[1];
			}

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		resultW.setDelivertDeliveryDetailReportDataDTO(deliveryDetailReportDataDTO);
		resultW.setDeliveryReportDataDTO(deliveryReportDataDTO);
		return resultW;
	}

	public PDFDODeliveryDetailReportResultListResultDTO getPDFDODeliveryReportByIDs(DODeliveryReportInitParamDTO initParamDTO) {

		PDFDODeliveryDetailReportResultListResultDTO resultWList = new PDFDODeliveryDetailReportResultListResultDTO();

		List<PDFDODeliveryDetailReportResultDTO> resultW = new ArrayList<PDFDODeliveryDetailReportResultDTO>();

		PDFDODeliveryDetailReportDataDTO[] deliveryDetailReportDataDTO = null;
		PDFDODeliveryReportDataDTO[] deliveryReportDataDTO = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultWList, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultWList, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			deliveryReportDataDTO = dodeliveryserver.getPDFDODeliveryReportByIDs(vendor.getId(), initParamDTO.getDeliveryids());

			for (PDFDODeliveryReportDataDTO deliveryReport : deliveryReportDataDTO) {
				PDFDODeliveryDetailReportResultDTO deliveryResult = new PDFDODeliveryDetailReportResultDTO();
				deliveryDetailReportDataDTO = dodeliveryserver.getPDFDeliveryDetailReport(vendor.getId(), deliveryReport.getId());
				deliveryResult.setDelivertDeliveryDetailReportDataDTO(deliveryDetailReportDataDTO);
				deliveryResult.setDeliveryReportDataDTO(deliveryReport);
				resultW.add(deliveryResult);
			}

			resultWList.setDeliveriesDetailReportResult(resultW);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultWList, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultWList, "L1");
		}

		return resultWList;
	}

	public String doValidateCourierDelivery(CourierExcelUploadDTO initParamDTO, String vendorcode) {

		DODeliveryW dodelivery = null;
		DirectOrderW directorder = null;

		// Valida proveedor
		VendorW[] vendors = null;
		Long vendorid = null;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			vendorid = -1L;
		}
		if(vendors == null || vendors.length == 0){
			vendorid = -1L;
		}
		vendorid = vendors[0].getId();
			
		
		// Valida que la orden de compra exista en el sistema, sea de tipo VeV PD y pertenezca al proveedor seleccionado
		// en el filtro del reporte
		try {
			directorder = directorderserver.getByPropertyAsSingleResult("number", initParamDTO.getDirectordernumber());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (directorder == null) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 1 + ": La orden de compra " + initParamDTO.getDirectordernumber() + " no existe como tipo VeV PD";
		}

		if (!directorder.getVendorid().equals(vendorid)) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 1 + ": La orden de compra " + initParamDTO.getDirectordernumber() + " no existe para el proveedor seleccionado en el filtro";
		}

		// Valida que el N°mero de despacho exista en el sistema y est� asociado a la OC
		try {
			dodelivery = dodeliveryserver.getByPropertyAsSingleResult("number", initParamDTO.getDodeliverynumber());
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if (dodelivery == null) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 2 + ": El despacho " + initParamDTO.getDodeliverynumber() + " no existe en B2B";
		}

		if (!dodelivery.getOrderid().equals(directorder.getId())) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 2 + ": El despacho " + dodelivery.getNumber() + " no existe para la OC " + directorder.getNumber();
		}

		DODeliveryStateTypeW typedlvbyrequest = null;
		try {
			typedlvbyrequest = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
		} catch (Exception e3) {
			e3.printStackTrace();
		}

		// JPE 20180405
		// Valida que el estado actual del despacho sea 'Etiqueta por Solicitar'
		if (!dodelivery.getCurrentstatetypeid().equals(typedlvbyrequest.getId())) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 2 + ": El despacho " + dodelivery.getNumber() + " debe estar en estado 'Etiqueta por Solicitar'";
		}

		// Valida que el despacho se encuentre con el campo Etiqueta de Courier en valor 0
		if (!dodelivery.getCouriertag().equals(0L)) {
			return "Fila " + initParamDTO.getLine() + ", columna " + 2 + ": El despacho " + dodelivery.getNumber() + " ya ha sido procesado para emitir documentos a Courier";
		}

		return "";

	}

	public DODeliveryWSRequestDataResultDTO doSaveCouriersExcel(UploadDeliveryCourierInitParamDTO initParamDTO) {
		DODeliveryWSRequestDataResultDTO resultDTO = new DODeliveryWSRequestDataResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			String error = "";
			
			// JPE 20200528
			// Valida que el proveedor est� asociado a un Courier
			List<HiredCourierW> hiredCouriers = hiredcourierserver.getByProperty("vendor.id", vendor.getId());
			if (hiredCouriers == null || hiredCouriers.size() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible ejecutar esta operación porque su empresa no tiene configurada una empresa de Courier", false);
			}
			
			if (hiredCouriers.size() > 1) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Su empresa tiene configurada más de una empresa de Courier. Favor cont�ctese con el administrador para regularizar este problema", false);
			}
			
			// Obtiene el Courier asociado
			CourierW courier = courierserver.getByPropertyAsSingleResult("id", hiredCouriers.get(0).getCourierid());
			if (courier == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}			
			
			// Valida que el proveedor tiene completa su libreta de direcciones para el Courier asociado
			PropertyInfoDTO prop1 = new PropertyInfoDTO("hiredcourier.vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("hiredcourier.courier.id", "courierid", courier.getId());
			List<VendorAddressW> vaList = vendoraddressserver.getByProperties(prop1, prop2);
			
			if (vaList != null && vaList.size() > 1) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			// Valida que el proveedor tenga configurada información básica para despachar con una empresa Courier
			if (vaList == null || vaList.size() < 1) {
				error = "Debe ingresar información básica para Dirección de devolución de productos y Dimensiones, para ello haga clic " + //
								"en la Libreta de Configuraciones para Courier, la cual se encuentra en menú Logística > " + //
								"Venta en Verde PD > Despachos VEV";
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
			}
			
			VendorAddressW vendoraddressW = vaList.get(0);
			
			// Valida que la antig�edad de la información básica para despachar no supere la cantidad definida de meses
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("vendoraddressupdatemonths")));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			if (vendoraddressW.getUpdatedate().before(cal.getTime())) {
				error = "Estimado Proveedor, por motivos de mantenimiento de información es requerido que actualice registro de datos en " + //
						"Libreta de Configuraciones para Courier, la cual se encuentra en menú Logística > Venta en Verde PD > " + //
						"Despachos VEV. En caso de no requerir cambiar datos ser� necesario de igual forma que ingrese y seleccione " + //
						"'Modificar y Guardar'"; //
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
			}
						
			Long[] dodeliveryids = dodeliveryserver.getDODeliveryIdsByNumbers(initParamDTO.getDodeliverynumbers());			
			
			resultDTO = doSaveCouriers(dodeliveryids, vendor.getId(), initParamDTO);

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
		}

		return resultDTO;
	}

	private DODeliveryWSRequestDataResultDTO doSaveCouriers(Long[] dodeliveryids, Long vendorid, UserDataInitParamDTO userData) throws OperationFailedException, NotFoundException, AccessDeniedException {

		DODeliveryWSRequestDataResultDTO result = new DODeliveryWSRequestDataResultDTO();

		List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
		String error = "";

		DODeliveryStateTypeW dodstByRequest = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
		DODeliveryStateTypeW dodsrRequested = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_SOLICITADA");

		List<DODeliveryW> dodeliveryList = new ArrayList<DODeliveryW>();
		DODeliveryW dodeliveryW = null;
		for (Long dodeliveryid : dodeliveryids) {
			dodeliveryW = dodeliveryserver.getById(dodeliveryid, LockModeType.PESSIMISTIC_WRITE);

			// JPE 20180405
			// Validar que el despacho se encuentre en estado 'ETIQUETA POR SOLICITAR'
			if (!dodeliveryW.getCurrentstatetypeid().equals(dodstByRequest.getId())) {
				error = "El despacho " + dodeliveryW.getNumber() + " debe estar en estado 'Etiqueta por Solicitar'";
				logger.error(error);
				baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new DODeliveryWSRequestDataResultDTO(), "L6000", error, false));
				continue;
			}
			
			// Valida que el despacho est� en valor 0 para el campo Etiqueta de Courier
			if (!dodeliveryW.getCouriertag().equals(0L)) {
				error = "El despacho " + dodeliveryW.getNumber() + " ya ha sido procesado para emitir documentos a Courier";
				logger.error(error);
				baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new DODeliveryWSRequestDataResultDTO(), "L6000", error, false));
				continue;
			}

			dodeliveryList.add(dodeliveryW);
		}

		if (baseresulList.size() > 0) {
			result.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
		}

		Date now = new Date();

		// Genera un ticket Courier en estado no procesado que asocie todos los despachos
		CourierTicketW ticket = new CourierTicketW();

		Long courierid = courierserver.getCourierIdByVendor(vendorid);
		ticket.setCourierid(courierid);
		ticket.setCreationdate(now);
		ticket.setUserid(userData.getUserid());
		ticket.setUsername(userData.getUsername());
		ticket.setUsermail(userData.getUseremail());
		ticket.setProcessed(false);

		ticket = courierticketserver.addCourierTicket(ticket);
		
		// Obtener el tipo de actualización VeV 'Cambio estado despacho VeV'
		VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

		// JPE 20180405
		DirectOrderW directOrder = null;
		DODeliveryStateW doDeliveryState = null;
		VeVAuditW vevAudit = null;
		for (DODeliveryW doDelivery : dodeliveryList) {
			// Actualiza el estado del despacho a ETIQUETA SOLICITADA y su historial de estados
			doDelivery.setCouriertag(1L); // actualiza etiqueta Courier
			doDelivery.setCurrentstdate(now);
			doDelivery.setCurrentstatetypeid(dodsrRequested.getId());
			doDelivery.setCurrentstwho(userData.getUsername());
			doDelivery.setCurrentstcomment("");

			doDelivery = dodeliveryserver.updateDODelivery(doDelivery);

			doDeliveryState = new DODeliveryStateW();
			doDeliveryState.setDeliveryid(doDelivery.getId());
			doDeliveryState.setDeliverystatetypeid(dodsrRequested.getId());
			doDeliveryState.setWhen(now);
			doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
			doDeliveryState.setWho(userData.getUsername());
			doDeliveryState.setComment("");

			dodeliverystateserver.addDODeliveryState(doDeliveryState);
			
			directOrder = directorderserver.getById(doDelivery.getOrderid());
			
			// JPE 20200615
			// Registro de auditor�a
			vevAudit = new VeVAuditW();
			vevAudit.setWhen(now);
			vevAudit.setUsername(userData.getUsername());
			vevAudit.setUsertype(userData.getUsertype());
			vevAudit.setUserenterprisecode(userData.getUserenterprisecode());
			vevAudit.setUserenterprisename(userData.getUserenterprisename());
			vevAudit.setInterfacecontent("");
			vevAudit.setItem("Estado Despacho VeV PD");
			vevAudit.setItemvalue(directOrder.getNumber().toString());
			vevAudit.setInitialdata(dodstByRequest.getName());
			vevAudit.setFinaldata(dodsrRequested.getName());
			vevAudit.setVendorid(directOrder.getVendorid());
			vevAudit.setVevupdatetypeid(utDeliveryState.getId());
			
			vevauditserverlocal.addVeVAudit(vevAudit);

			CourierTicketDetailW ticketdetail = new CourierTicketDetailW();
			ticketdetail.setCourierticketid(ticket.getId());
			ticketdetail.setDodeliveryid(doDelivery.getId());
			ticketdetail.setSuccess(false);
			ticketdetail.setProcessed(false);
			courierticketdetailserver.addCourierTicketDetail(ticketdetail);
		}

		result.setTicketnumber(ticket.getTicketnumber());

		return result;
	}

	public DODeliveryWSRequestDataResultDTO deliveryCourierCorreosDeChile(DeliveryCourierCorreosDeChileInitParamDTO initParamDTO) {

		DODeliveryWSRequestDataResultDTO resultDTO = new DODeliveryWSRequestDataResultDTO();

		logger.info("Iniciando proceso para consumir webservice de Correos de Chile");

		List<VendorAddressW> vaList = new ArrayList<VendorAddressW>();
		CourierW courier = null;
		DODeliveryW dodeliveryW = null;
		Long hiredcourierW = null;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {
			courier = courierserver.getByPropertyAsSingleResult("code", "CC");
			dodeliveryW = dodeliveryserver.getById(initParamDTO.getDodeliveryids()[0]);
			hiredcourierW = hiredcourierserver.getByVendor(dodeliveryW.getVendorid());

			PropertyInfoDTO prop1 = new PropertyInfoDTO("hiredcourier.vendor.id", "vendorid", dodeliveryW.getVendorid());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("hiredcourier.courier.id", "courierid", courier.getId());
			vaList = vendoraddressserver.getByProperties(prop1, prop2);

		} catch (Exception e1) {
			e1.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		if (vaList != null && vaList.size() > 1) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		String error = "";

		// Valida que el proveedor seleccionado en el filtro posea alguna empresa de Courier asociada (Correos de Chile)
		if (hiredcourierW < 1) {
			error = "No es posible ejecutar esta operación porque su empresa no tiene configurada una empresa de Courier";
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}

		// Valida que el proveedor seleccionado en el filtro posea solo una empresa de Courier asociada (Correos de Chile)
		if (hiredcourierW > 1) {
			error = "Su empresa tiene configurada más de una empresa de Courier. Favor cont�ctese con el administrador para regularizar este problema";
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}

		// Valida que el proveedor tenga configurada información básica para despachar con Correos de Chile
		if (vaList == null || vaList.size() < 1) {
			error = "Debe ingresar información básica para Dirección de devolución de productos y Dimensiones, para ello haga clic en la " + //
							"Libreta de Configuraciones para Courier"; //
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}
		VendorAddressW vendoraddressW = vaList.get(0);
		
		// Valida que la antig�edad de la información básica para despachar no supere la cantidad definida de meses
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("vendoraddressupdatemonths")));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		if (vendoraddressW.getUpdatedate().before(cal.getTime())) {
			error = "Estimado Proveedor, por motivos de mantenimiento de información es requerido que actualice registro de datos en " + //
					"Libreta de Configuraciones para Courier. En caso de no requerir cambiar datos ser� necesario de igual forma que " + //
					"ingrese y seleccione 'Modificar y Guardar'"; //
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}

		// Valida que la cantidad de despachos a procesar no supere el m�ximo configurado por el sistema
		if (initParamDTO.getDodeliveryids().length >= Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierprocessing"))) {
			// Genera ticket
			try {
				return doSaveCouriers(initParamDTO.getDodeliveryids(), vendor.getId(), initParamDTO);
			} catch (Exception e) {
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
			}
		}

		// Llamada a WS
		try {
			resultDTO = deliveryCourierCorreosDeChileWS(courier, initParamDTO.getDodeliveryids(), initParamDTO);
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005");
		}

		return resultDTO;
	}

	private DODeliveryWSRequestDataResultDTO deliveryCourierCorreosDeChileWS(CourierW courier, Long[] dodeliveryids, UserDataInitParamDTO userData) throws OperationFailedException, NotFoundException {

		DODeliveryWSRequestDataResultDTO resultDTO = new DODeliveryWSRequestDataResultDTO();

		HashMap<DODeliveryW, DirectOrderW> doMap = new HashMap<DODeliveryW, DirectOrderW>();
		DirectOrderW directOrder = null;
		DODeliveryW doDelivery = null;
		for (Long dodeliveryid : dodeliveryids) {
			doDelivery = dodeliveryserver.getById(dodeliveryid, LockModeType.PESSIMISTIC_WRITE);
			directOrder = directorderserver.getById(doDelivery.getOrderid());

			doMap.put(doDelivery, directOrder);
		}

		DODeliveryStateTypeW dodstByRequest = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
		DODeliveryStateTypeW dodstSchedulePending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");

		List<DODeliveryResultBean> faileddeliveries = new ArrayList<DODeliveryResultBean>();
		List<DODeliveryResultBean> successdeliveries = new ArrayList<DODeliveryResultBean>();

		String error = "";
		
		// Obtener el tipo de actualización VeV 'Cambio estado despacho VeV'
		VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

		BaseResultDTO baseResult = null;
		for (Map.Entry<DODeliveryW, DirectOrderW> entry : doMap.entrySet()) {
			doDelivery = entry.getKey();
			directOrder = entry.getValue();

			// JPE 20180405
			// Valida que el despacho se encuentre en estado 'ETIQUETA POR SOLICITAR'
			if (!doDelivery.getCurrentstatetypeid().equals(dodstByRequest.getId())) {
				error = "El despacho " + doDelivery.getNumber() + " debe estar en estado 'Etiqueta por Solicitar'";
				logger.error(error);
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				continue;
			}

			// Valida que el despacho est� en valor 0 para el campo Etiqueta de Courier
			if (!doDelivery.getCouriertag().equals(0L)) {
				error = "El despacho " + doDelivery.getNumber() + " ya ha sido procesado para emitir documentos a Courier";
				logger.error(error);
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				continue;
			}

			baseResult = courierCorreosDeChileWS(doDelivery, courier, userData.getUsername());
			
			if (baseResult.getStatuscode().equals("0")) {
				// JPE 20180405
				// Actualiza el estado del despacho a PENDIENTE DE AGENDAR y su historial de estados
				try {
					Date now = new Date();

					doDelivery.setCouriertag(2L); // actualiza etiqueta Courier
					doDelivery.setCurrentstdate(now);
					doDelivery.setCurrentstatetypeid(dodstSchedulePending.getId());
					doDelivery.setCurrentstwho(userData.getUsername());
					doDelivery.setCurrentstcomment("");

					doDelivery = dodeliveryserver.updateDODelivery(doDelivery);
					dodeliveryserver.flush();

					DODeliveryStateW doDeliveryState = new DODeliveryStateW();
					doDeliveryState.setDeliveryid(doDelivery.getId());
					doDeliveryState.setDeliverystatetypeid(dodstSchedulePending.getId());
					doDeliveryState.setWhen(now);
					doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
					doDeliveryState.setWho(userData.getUsername());
					doDeliveryState.setComment("");

					dodeliverystateserver.addDODeliveryState(doDeliveryState);
					
					// JPE 20200615
					// Registro de auditor�a
					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(userData.getUsername());
					vevAudit.setUsertype(userData.getUsertype());
					vevAudit.setUserenterprisecode(userData.getUserenterprisecode());
					vevAudit.setUserenterprisename(userData.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado Despacho VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dodstByRequest.getName());
					vevAudit.setFinaldata(dodstSchedulePending.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);

					successdeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), ""));

				} catch (Exception e) {
					error = "Ha ocurrido un error modificando el estado del despacho " + doDelivery.getNumber() + " a SOLICITAR RETIRO";
					logger.error(error + ": " + userData.getUsername());
					faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				}
			}
			else {
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), baseResult.getStatusmessage()));
			}
		}

		// Obtiene los resultados del procesamiento de cada despacho
		resultDTO.setFaileddeliveries((DODeliveryResultBean[]) faileddeliveries.toArray(new DODeliveryResultBean[faileddeliveries.size()]));
		resultDTO.setSuccessdeliveries((DODeliveryResultBean[]) successdeliveries.toArray(new DODeliveryResultBean[successdeliveries.size()]));

		return resultDTO;

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public BaseResultDTO courierCorreosDeChileWS(DODeliveryW doDelivery, CourierW courier, String fullusername) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		String error = "";

		try {
			// Ejecutar el WS de Correos de Chile
			DODeliveryWSRequestData[] data = dodeliveryserver.getWSRequestDataByDODeliveryId(doDelivery.getId());

			ServicioExternoAdmisionCEPClient client = new ServicioExternoAdmisionCEPClient();
			ServicioExternoAdmisionCEPSoap service = client.getServicioExternoAdmisionCEPSoap();

			Client cl = Client.getInstance(service);
			cl.addInHandler(new DOMInHandler());
			cl.addInHandler(new IntegracionAsistidaHandler());
			cl.addOutHandler(new DOMOutHandler());
			cl.addOutHandler(new IntegracionAsistidaHandler());

			if (RegionalLogisticConstants.getInstance().get_PROXY()) {
				cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_HOST, RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP());
				cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_PORT, RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT() + "");
				cl.setProperty(CommonsHttpMessageSender.DISABLE_PROXY_UTILS, true);
			}

			AdmisionTO admisionTo = doFillAdmisionTO(data[0], courier.getId());
			// AdmisionTO admisionTo = doFillAdmisionTOTest();

			RespuestaAdmisionTO response = service.admitirEnvio(data[0].getUser(), data[0].getPassword(), admisionTo);
			// RespuestaAdmisionTO response = service.admitirEnvio("PRUEBA WS 5", "055e7c1784a11213ac3a018d6ab5d8a1",
			// admisionTo);

			// Valida la respuesta del WS
			if (response == null) {
				error = "Se ha producido un error consumiendo servicio de Correos de Chile Despacho: " + doDelivery.getNumber();
				logger.error(error);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", "Se ha producido un error consumiendo servicio", false);
			}

			// Guarda los datos de etiqueta de Correos de Chile
			CorreosChileTagW chileTagW = doFillCorreosDeChileTagFromWSResponse(response, doDelivery.getId(), fullusername);
			correoschiletagserver.addCorreosChileTag(chileTagW);

			logger.info("Obtenida etiqueta de Correos de Chile para despacho " + doDelivery.getNumber() + ": " + fullusername);

		} catch (ServiceException e) {
			e.printStackTrace();
			error = "Ha ocurrido un error con el servicio de Chilexpress.";
			logger.error(error + " Despacho: " + doDelivery.getNumber());
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", error, false);
		} catch (XFireRuntimeException e) {
			e.printStackTrace();
			error = "Ha ocurrido un error con el servicio de Chilexpress.";
			logger.error(error + " Despacho: " + doDelivery.getNumber());
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", error, false);
		}

		return resultDTO;
	}

	private CorreosChileTagW doFillCorreosDeChileTagFromWSResponse(RespuestaAdmisionTO response, Long dodeliveryid, String fullusername) {

		CorreosChileTagW correos = new CorreosChileTagW();
		correos.setAbbreviationcenter(response.getAbreviaturaCentro());
		correos.setAbbreviationservice(response.getAbreviaturaServicio());
		correos.setAdmissioncode(response.getCodigoAdmision());
		correos.setDelegationcode(response.getCodigoDelegacionDestino());
		correos.setDelegationname(response.getNombreDelegacionDestino());
		correos.setDestinationaddress(response.getDireccionDestino());
		correos.setDestinationcommune(response.getComunaDestino());
		correos.setDodeliveryid(dodeliveryid);
		correos.setRecordsend(response.getGrabarEnvio());
		correos.setRoutingcode(response.getCodigoEncaminamiento());
		correos.setSendnumber(response.getNumeroEnvio());
		correos.setCuartel(response.getCuartel());
		correos.setSector(response.getSector());
		correos.setSdp(response.getSDP());
		correos.setMovil(response.getMovil());

		correos.setStartdate(new Date());
		correos.setUser1(fullusername);

		return correos;
	}

	public AdmisionTO doFillAdmisionTO(DODeliveryWSRequestData data, Long courierid) {
		org.tempuri.ObjectFactory objectFactory = new org.tempuri.ObjectFactory();
		AdmisionTO admision = objectFactory.createAdmisionTO();

		admision.setExtensionData(objectFactory.createExtensionDataObject());
		admision.setCodigoAdmision("string");
		admision.setClienteRemitente(data.getClientcode());
		admision.setNombreRemitente(data.getVendorname());
		admision.setDireccionRemitente(data.getVendorstreet() + " " + data.getVendornumber());
		admision.setPaisRemitente("056");
		admision.setComunaRemitente(data.getVendorcommune());
		admision.setNombreDestinatario(data.getClientname());
		admision.setDireccionDestinatario(data.getClientaddress());
		admision.setPaisDestinatario("056");

		CommuneCourierW commune = null;
		try {
			commune = communecourierserver.getCourierCommuneByCourierAndParisCommune(courierid, data.getClientcommune());
		} catch (Exception e) {
		}

		if (commune != null)
			admision.setComunaDestinatario(commune.getCouriercommune());
		else
			admision.setComunaDestinatario(data.getClientcommune());

		admision.setCodigoServicio("25");
		admision.setNumeroTotalPiezas(1);
		admision.setKilos(data.getWeight());
		admision.setVolumen(data.getHeight() * data.getWidth() * data.getLength());
		admision.setNumeroReferencia(data.getNumber().toString());
		admision.setTipoPortes("P");
		admision.setDevolucionConforme("N");
		admision.setNumeroDocumentos(0L);
		return admision;
	}

	public AdmisionTO doFillAdmisionTOTest() {
		org.tempuri.ObjectFactory objectFactory = new org.tempuri.ObjectFactory();
		AdmisionTO admision = objectFactory.createAdmisionTO();

		admision.setExtensionData(objectFactory.createExtensionDataObject());
		admision.setCodigoAdmision("string");
		admision.setClienteRemitente("525536");
		admision.setNombreRemitente("SKECHERS CHILE");
		admision.setDireccionRemitente("AV. KENNEDY 5118");
		admision.setPaisRemitente("056");
		admision.setComunaRemitente("VITACURA");
		admision.setPaisDestinatario("056");
		admision.setNombreDestinatario("Claudia Pavez.");
		admision.setDireccionDestinatario("LOS COPIHUES 3972 - 3972");
		admision.setComunaDestinatario("SAN JOAQUIN");
		admision.setCodigoServicio("25");
		admision.setNumeroTotalPiezas(1);
		admision.setKilos(1D);
		admision.setVolumen(1D);
		admision.setNumeroReferencia("436142");
		admision.setTipoPortes("P");
		admision.setDevolucionConforme("N");
		admision.setNumeroDocumentos(0L);
		return admision;
	}

	public DODeliveryWSRequestDataResultDTO deliveryCourierChileExpress(DeliveryCourierChilexpressInitParamDTO initParamDTO) {

		DODeliveryWSRequestDataResultDTO resultDTO = new DODeliveryWSRequestDataResultDTO();

		logger.info("Iniciando proceso para consumir webservice de Chilexpress");

		List<VendorAddressW> vaList = new ArrayList<VendorAddressW>();
		CourierW courier = null;
		DODeliveryW dodeliveryW = null;
		Long hiredcourierW = null;
		
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			courier = courierserver.getByPropertyAsSingleResult("code", "CE");
			dodeliveryW = dodeliveryserver.getById(initParamDTO.getDodeliveryids()[0]);
			hiredcourierW = hiredcourierserver.getByVendor(dodeliveryW.getVendorid());

			PropertyInfoDTO prop1 = new PropertyInfoDTO("hiredcourier.vendor.id", "vendorid", dodeliveryW.getVendorid());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("hiredcourier.courier.id", "courierid", courier.getId());
			vaList = vendoraddressserver.getByProperties(prop1, prop2);

		} catch (Exception e1) {
			e1.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		if (vaList != null && vaList.size() > 1) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		String error = "";

		// Valida que el proveedor seleccionado en el filtro posea alguna empresa de Courier asociada (Chilexpress)
		if (hiredcourierW < 1) {
			error = "No es posible ejecutar esta operación porque su empresa no tiene configurada una empresa de Courier";
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}

		// Valida que el proveedor seleccionado en el filtro posea solo una empresa de Courier asociada (Chilexpress)
		if (hiredcourierW > 1) {
			error = "Su empresa tiene configurada más de una empresa de Courier. Favor cont�ctese con el administrador para regularizar este problema";
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}

		// Valida que el proveedor tenga configurada información básica para despachar con Courier Chilexpress
		if (vaList == null || vaList.size() < 1) {
			error = "Debe ingresar información básica para Dirección de devolución de productos y Dimensiones, para ello haga clic en la " + //
							"Libreta de Configuraciones para Courier"; //
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}
		VendorAddressW vendoraddressW = vaList.get(0);
		
		// Valida que la antig�edad de la información básica para despachar no supere la cantidad definida de meses
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -Integer.parseInt(B2BSystemPropertiesUtil.getProperty("vendoraddressupdatemonths")));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		if (vendoraddressW.getUpdatedate().before(cal.getTime())) {
			error = "Estimado Proveedor, por motivos de mantenimiento de información es requerido que actualice registro de datos en " + //
					"Libreta de Configuraciones para Courier. En caso de no requerir cambiar datos ser� necesario de igual forma que " + //
					"ingrese y seleccione 'Modificar y Guardar'"; //
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", error, false);
		}
		
		// Valida que la cantidad de despachos a procesar no supere el m�ximo configurado por el sistema
		if (initParamDTO.getDodeliveryids().length >= Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierprocessing"))) {
			// Genera ticket
			try {
				return doSaveCouriers(initParamDTO.getDodeliveryids(), vendor.getId(), initParamDTO);
			} catch (Exception e) {
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6010");
			}
		}

		// Llamada a WS
		try {
			resultDTO = deliveryCourierChileExpressWS(courier, initParamDTO.getDodeliveryids(), initParamDTO);
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005");
		}

		return resultDTO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doCourierTicketDetailWS(Long ticketNumber, CourierW courier, CourierTicketDetailW courierTicketDetail, HashMap<String, DODeliveryStateTypeW> dodstMap) {
		
		boolean processed = true;
		
		DODeliveryW dodeliveryW = null;
		try {
			Date now = new Date();
			
			courierTicketDetail.setStartdate(now);
			
			dodeliveryW = dodeliveryserver.getById(courierTicketDetail.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			
			// JPE 20180409
			// Valida que el despacho se encuentre en estado 'ETIQUETA SOLICITADA'
			if (!dodeliveryW.getCurrentstatetypeid().equals(dodstMap.get("ETIQ_SOLICITADA").getId())) {
				courierTicketDetail.setResult("El despacho debe estar en estado 'Etiqueta Solicitada'");
				courierTicketDetail.setSuccess(false);
				courierTicketDetail.setProcessed(true);
				
				logger.error("El despacho " + dodeliveryW.getNumber() + " debe estar en estado 'Etiqueta Solicitada'");
			}
			// Valida que el despacho est� en valor 1 para el campo Etiqueta de Courier
			else if (!dodeliveryW.getCouriertag().equals(1L)) {
				courierTicketDetail.setResult("El despacho ya ha sido procesado para emitir documentos a Courier");
				courierTicketDetail.setSuccess(false);
				courierTicketDetail.setProcessed(true);
				
				logger.error("El despacho " + dodeliveryW.getNumber() + " ya ha sido procesado para emitir documentos a Courier");
			}
			else {
				BaseResultDTO resultDTO = null;
				if (courier.getCode().equals("CE")) {
					logger.info("Iniciando proceso para consumir webservice de Chilexpress");
					resultDTO = courierChileExpressWS(dodeliveryW, courier, "Sistema");
				}
				else if (courier.getCode().equals("CC")) {
					logger.info("Iniciando proceso para consumir webservice de Correos de Chile");
					resultDTO = courierCorreosDeChileWS(dodeliveryW, courier, "Sistema");
				}
				
				DODeliveryStateW dodeliverystate = new DODeliveryStateW();
				if (resultDTO.getStatuscode().equals("0")) {
					// JPE 20180405
					// Actualiza el estado del despacho a PENDIENTE DE AGENDAR y su historial de estados
					dodeliveryW.setCouriertag(2L); // actualiza etiqueta Courier
					dodeliveryW.setCurrentstatetypeid(dodstMap.get("PEND_AGENDAR").getId());
					
					dodeliverystate.setDeliverystatetypeid(dodeliveryW.getCurrentstatetypeid());
											
					courierTicketDetail.setResult("Procesado correctamente");
					courierTicketDetail.setSuccess(true);
					courierTicketDetail.setProcessed(true);					
				}
				else {
					dodeliveryW.setCouriertag(0L); // actualiza etiqueta Courier
					dodeliveryW.setCurrentstatetypeid(dodstMap.get("ETIQ_POR_SOLICITAR").getId());
					
					dodeliverystate.setDeliverystatetypeid(dodeliveryW.getCurrentstatetypeid());
											
					if (resultDTO.getStatusmessage() != null && resultDTO.getStatusmessage().length() > 0) {
						courierTicketDetail.setResult(resultDTO.getStatusmessage());
					}
					else {
						courierTicketDetail.setResult("Ha ocurrido un error con el servicio");
					}
					
					courierTicketDetail.setSuccess(false);
					courierTicketDetail.setProcessed(true);
				}
				
				dodeliveryW.setCurrentstdate(now);
				dodeliveryW.setCurrentstwho("Sistema");
				dodeliveryW.setCurrentstcomment("");
				
				dodeliveryW = dodeliveryserver.updateDODelivery(dodeliveryW);
	
				dodeliverystate.setDeliveryid(dodeliveryW.getId());
				dodeliverystate.setWhen(now);
				dodeliverystate.setDeliverystatedate(dodeliveryW.getCurrentstdate());
				dodeliverystate.setWho("Sistema");
				dodeliverystate.setComment("");
				
				dodeliverystateserver.addDODeliveryState(dodeliverystate);
				dodeliveryserver.flush();
			}
			
			courierticketdetailserver.updateCourierTicketDetail(courierTicketDetail);
			courierticketdetailserver.flush();
		
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return false;
		}

		return processed;
	}

	private DODeliveryWSRequestDataResultDTO deliveryCourierChileExpressWS(CourierW courier, Long[] dodeliveryids, UserDataInitParamDTO userData) throws OperationFailedException, NotFoundException {
		DODeliveryWSRequestDataResultDTO resultDTO = new DODeliveryWSRequestDataResultDTO();

		HashMap<DODeliveryW, DirectOrderW> doMap = new HashMap<DODeliveryW, DirectOrderW>();
		DirectOrderW directOrder = null;
		DODeliveryW doDelivery = null;

		for (Long dodeliveryid : dodeliveryids) {
			doDelivery = dodeliveryserver.getById(dodeliveryid, LockModeType.PESSIMISTIC_WRITE);
			directOrder = directorderserver.getById(doDelivery.getOrderid());

			doMap.put(doDelivery, directOrder);
		}

		DODeliveryStateTypeW dodstByRequest = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
		DODeliveryStateTypeW dodstSchedulePending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");

		List<DODeliveryResultBean> faileddeliveries = new ArrayList<DODeliveryResultBean>();
		List<DODeliveryResultBean> successdeliveries = new ArrayList<DODeliveryResultBean>();

		// Obtener el tipo de actualización VeV 'Cambio estado despacho VeV'
		VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");
		
		String error = "";
		BaseResultDTO baseResult = null;
		for (Map.Entry<DODeliveryW, DirectOrderW> entry : doMap.entrySet()) {
			doDelivery = entry.getKey();
			directOrder = entry.getValue();

			// JPE 20180405
			// Validar que el despacho se encuentre en estado 'ETIQUETA POR SOLICITAR'
			if (!doDelivery.getCurrentstatetypeid().equals(dodstByRequest.getId())) {
				error = "El despacho " + doDelivery.getNumber() + " debe estar en estado 'Etiqueta por Solicitar'";
				logger.error(error);
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				continue;
			}

			// Valida que el despacho est� en valor 0 para el campo Etiqueta de Courier
			if (!doDelivery.getCouriertag().equals(0L)) {
				error = "El despacho " + doDelivery.getNumber() + " ya ha sido procesado para emitir documentos a Courier";
				logger.error(error);
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				continue;
			}

			baseResult = courierChileExpressWS(doDelivery, courier, userData.getUsername());

			if (baseResult.getStatuscode().equals("0")) {
				// JPE 20180405
				// Actualiza el estado del despacho a PENDIENTE DE AGENDAR y su historial de estados
				try {
					Date now = new Date();

					doDelivery.setCouriertag(2L); // actualiza etiqueta Courier
					doDelivery.setCurrentstdate(now);
					doDelivery.setCurrentstatetypeid(dodstSchedulePending.getId());
					doDelivery.setCurrentstwho(userData.getUsername());
					doDelivery.setCurrentstcomment("");

					doDelivery = dodeliveryserver.updateDODelivery(doDelivery);
					dodeliveryserver.flush();

					DODeliveryStateW doDeliveryState = new DODeliveryStateW();
					doDeliveryState.setDeliveryid(doDelivery.getId());
					doDeliveryState.setDeliverystatetypeid(dodstSchedulePending.getId());
					doDeliveryState.setWhen(now);
					doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
					doDeliveryState.setWho(userData.getUsername());
					doDeliveryState.setComment("");

					dodeliverystateserver.addDODeliveryState(doDeliveryState);
					
					// JPE 20200615
					// Registro de auditor�a
					VeVAuditW vevAudit = new VeVAuditW();
					vevAudit.setWhen(now);
					vevAudit.setUsername(userData.getUsername());
					vevAudit.setUsertype(userData.getUsertype());
					vevAudit.setUserenterprisecode(userData.getUserenterprisecode());
					vevAudit.setUserenterprisename(userData.getUserenterprisename());
					vevAudit.setInterfacecontent("");
					vevAudit.setItem("Estado Despacho VeV PD");
					vevAudit.setItemvalue(directOrder.getNumber().toString());
					vevAudit.setInitialdata(dodstByRequest.getName());
					vevAudit.setFinaldata(dodstSchedulePending.getName());
					vevAudit.setVendorid(directOrder.getVendorid());
					vevAudit.setVevupdatetypeid(utDeliveryState.getId());
					
					vevauditserverlocal.addVeVAudit(vevAudit);

					successdeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), ""));

				} catch (Exception e) {
					error = "Ha ocurrido un error modificando el estado del despacho " + doDelivery.getNumber() + " a SOLICITAR RETIRO";
					logger.error(error + ": " + userData.getUsername());
					faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), error));
				}
			}
			else {
				faileddeliveries.add(new DODeliveryResultBean(doDelivery.getNumber(), directOrder.getNumber(), baseResult.getStatusmessage()));
			}
		}

		// Obtiene los resultados del procesamiento de cada despacho
		resultDTO.setFaileddeliveries((DODeliveryResultBean[]) faileddeliveries.toArray(new DODeliveryResultBean[faileddeliveries.size()]));
		resultDTO.setSuccessdeliveries((DODeliveryResultBean[]) successdeliveries.toArray(new DODeliveryResultBean[successdeliveries.size()]));

		return resultDTO;
	}

	public BaseResultDTO courierChileExpressWS(DODeliveryW dodeliveryW, CourierW courier, String fullusername) {

		BaseResultDTO resultDTO = new BaseResultDTO();

		String error = "";

		try {
			// Ejecutar el WS de Chilexpress
			DODeliveryWSRequestData[] data = dodeliveryserver.getWSRequestDataByDODeliveryId(dodeliveryW.getId());

			String endpointurl = B2BSystemPropertiesUtil.getProperty("WSDL_CHILEXPRESS");

			IntegracionAsistidaPT service = new IntegracionAsistidaClient(endpointurl).getIntegracionAsistidaSOAP();
			IntegracionAsistidaRequest integracionAsistidaRequest = doFillIntegracionAsistidaRequest(data[0], courier.getId());

			DatosHeaderRequest headerRequest = new DatosHeaderRequest();
			Holder<DatosHeaderResponse> headerResponse = new Holder<DatosHeaderResponse>();
			Client cl = Client.getInstance(service);
			cl.addInHandler(new DOMInHandler());
			cl.addInHandler(new IntegracionAsistidaHandler());
			cl.addOutHandler(new DOMOutHandler());
			cl.addOutHandler(new IntegracionAsistidaHandler());

			cl.setProperty(Channel.USERNAME, data[0].getUser());
			cl.setProperty(Channel.PASSWORD, data[0].getPassword());

			if (RegionalLogisticConstants.getInstance().get_PROXY()) {
				cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_HOST, RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP());
				cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_PORT, RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT() + "");
				cl.setProperty(CommonsHttpMessageSender.DISABLE_PROXY_UTILS, true);
			}

			IntegracionAsistidaResponse response = service.integracionAsistidaOp(integracionAsistidaRequest, headerRequest, headerResponse);
			GenerarIntegracionAsistidaResponseType responsetype = response.getRespGenerarIntegracionAsistida();

			// Valida la respuesta del WS
			if (responsetype.getEstadoOperacion().getCodigoEstado().intValue() != 0) {
				error = "Se ha producido un error consumiendo servicio de Chilexpress. " + responsetype.getEstadoOperacion().getDescripcionEstado() + " Despacho: " + dodeliveryW.getNumber();
				logger.error(error);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", responsetype.getEstadoOperacion().getDescripcionEstado(), false);
			}

			// Guarda los datos de etiqueta de Chilexpress
			ChileExpressTagW chileTagW = doFillChileExpressTagWFromWSResponse(responsetype, dodeliveryW.getId(), fullusername);
			chileexpresstagserver.addChileExpressTag(chileTagW);

			logger.info("Obtenida etiqueta de Chilexpress para despacho " + dodeliveryW.getNumber() + ": " + fullusername);

		} catch (ServiceException e) {
			e.printStackTrace();
			error = "Ha ocurrido un error con el servicio de Chilexpress";
			logger.error(error + " Despacho: " + dodeliveryW.getNumber());
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", error, false);
		} catch (XFireRuntimeException e) {
			e.printStackTrace();
			error = "Ha ocurrido un error con el servicio de Chilexpress";
			logger.error(error + " Despacho: " + dodeliveryW.getNumber());
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6005", error, false);
		}

		return resultDTO;
	}

	private ChileExpressTagW doFillChileExpressTagWFromWSResponse(GenerarIntegracionAsistidaResponseType responsetype, long dodeliveryid, String fullusername) {

		ChileExpressTagW chileTagW = new ChileExpressTagW();

		Date printoutdate = null;
		try {
			printoutdate = new SimpleDateFormat("dd-MM-yyyy").parse(responsetype.getDatosEtiqueta().getFechaImpresion());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		chileTagW.setSendnumber(responsetype.getDatosEtiqueta().getNumeroOT().toString());
		chileTagW.setParentnumber(responsetype.getDatosEtiqueta().getNumeroOTPadre().intValue());
		chileTagW.setItemcomment(responsetype.getDatosEtiqueta().getGlosaProductoOT());
		chileTagW.setAddresseename(responsetype.getDatosEtiqueta().getNombreDestinatario());
		chileTagW.setAddress(responsetype.getDatosEtiqueta().getDireccion());
		chileTagW.setGuidenumber(responsetype.getDatosEtiqueta().getNumeroGuia());
		chileTagW.setCoveragecomment(responsetype.getDatosEtiqueta().getGlosaCobertura());
		chileTagW.setRegioncode(responsetype.getDatosEtiqueta().getCodigoRegion());
		chileTagW.setCustomeradditionaldata(responsetype.getDatosEtiqueta().getAdicionales());
		chileTagW.setBulkweight(responsetype.getDatosEtiqueta().getPeso());
		chileTagW.setBulkheight(responsetype.getDatosEtiqueta().getAlto());
		chileTagW.setBulkwidth(responsetype.getDatosEtiqueta().getAncho());
		chileTagW.setBulklengh(responsetype.getDatosEtiqueta().getLargo());
		chileTagW.setEpllabelxml(responsetype.getDatosEtiqueta().getXmlSalidaEpl());
		chileTagW.setLabelbarcode(responsetype.getDatosEtiqueta().getBarcode());
		chileTagW.setLabeladditionalreference(responsetype.getDatosEtiqueta().getReferencia2());
		chileTagW.setItemdata(responsetype.getDatosEtiqueta().getInformacionProducto());
		chileTagW.setItemshortcomment(responsetype.getDatosEtiqueta().getGlosaCortaProductoOT());
		chileTagW.setPrintoutdate(printoutdate);
		chileTagW.setBulknumber(responsetype.getDatosEtiqueta().getNumeroBulto());
		chileTagW.setDestinationdc(responsetype.getDatosEtiqueta().getCentroDistribucionDestino());
		chileTagW.setSendnumber(responsetype.getDatosEtiqueta().getNumeroOT().toString());
		chileTagW.setLabelimagedata(responsetype.getDatosEtiqueta().getImagenEtiqueta());
		chileTagW.setServicecomment(responsetype.getDatosEtiqueta().getGlosaServicio());
		chileTagW.setStartdate(new Date());
		chileTagW.setUser1(fullusername);
		chileTagW.setDodeliveryid(dodeliveryid);

		return chileTagW;
	}

	// private static IntegracionAsistidaRequest doFillIntegracionAsistidaRequestTest() {
	//		
	// IntegracionAsistidaRequest request = new ObjectFactory().createIntegracionAsistidaRequest();
	//		
	// cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.ObjectFactory objectFactory = new
	// cl.chilexpress.ws.osb.interno.com.generarintegracionasistidareq.ObjectFactory();
	// GenerarIntegracionAsistidaRequestType gia = objectFactory.createGenerarIntegracionAsistidaRequestType();
	// gia.setCodigoProducto(3);
	// gia.setCodigoServicio(3);
	// gia.setComunaOrigen("RENCA");
	// gia.setNumeroTCC(22106942);
	// gia.setReferenciaEnvio("916893");
	// gia.setReferenciaEnvio2("Compra1");
	// // gia.setMontoCobrar(BigDecimal.ZERO);
	// gia.setMontoCobrar(0);
	// // gia.setEoc(false);
	// gia.setEoc(0);
	//
	// RemitenteType rem = objectFactory.createRemitenteType();
	// rem.setNombre("a");
	// rem.setEmail("a");
	// rem.setCelular("1");
	// gia.setRemitente(rem);
	//
	// DestinatarioType dest = objectFactory.createDestinatarioType();
	// dest.setNombre("Alexis Erazo");
	// dest.setEmail("aerazo@chilexpress.cl");
	// dest.setCelular("11111");
	// gia.setDestinatario(dest);
	//
	// DireccionType dir = objectFactory.createDireccionType();
	// dir.setComuna("PENALOLEN");
	// dir.setCalle("Camino de las Camelias");
	// dir.setNumero("0");
	// dir.setComplemento("Casa 33");
	// gia.setDireccion(dir);
	//
	// DireccionType dirdev = objectFactory.createDireccionType();
	// dirdev.setComuna("PUDAHUEL");
	// dirdev.setCalle("Jose Joaquin Perez");
	// dirdev.setNumero("1376");
	// dirdev.setComplemento("Piso 2");
	// gia.setDireccionDevolucion(dirdev);
	//
	// PiezaType pieza = objectFactory.createPiezaType();
	// pieza.setAlto(1);
	// pieza.setAncho(1);
	// pieza.setLargo(1);
	// pieza.setPeso(BigDecimal.ONE);
	// gia.setPieza(pieza);
	//
	// request.setReqGenerarIntegracionAsistida(gia);
	// return request;
	//		
	// }

	private IntegracionAsistidaRequest doFillIntegracionAsistidaRequest(DODeliveryWSRequestData data, Long courierid) {

		IntegracionAsistidaRequest request = new IntegracionAsistidaRequest();

		GenerarIntegracionAsistidaRequestType gia = new GenerarIntegracionAsistidaRequestType();
		gia.setCodigoProducto(3);
		gia.setCodigoServicio(data.getClientaddress().startsWith("CXP") ? 1 : 3);
		gia.setComunaOrigen(data.getVendorcommune());

		DestinatarioType dest = new DestinatarioType();
		dest.setCelular(data.getClientphone());
		dest.setEmail("");
		dest.setNombre(data.getClientname());
		gia.setDestinatario(dest);

		DireccionType dir = new DireccionType();
		dir.setCalle(data.getClientaddress());
		dir.setComplemento(data.getClientcomment());

		CommuneCourierW commune = null;
		try {
			commune = communecourierserver.getCourierCommuneByCourierAndParisCommune(courierid, data.getClientcommune());
		} catch (Exception e) {
		}

		if (commune != null)
			dir.setComuna(commune.getCouriercommune());
		else
			dir.setComuna(data.getClientcommune());

		dir.setNumero("0");
		gia.setDireccion(dir);

		DireccionType dirdev = new DireccionType();
		dirdev.setCalle(data.getVendorstreet());
		dirdev.setComplemento(data.getVendorcomment());
		dirdev.setComuna(data.getVendorcommune());
		dirdev.setNumero(data.getVendornumber());
		gia.setDireccionDevolucion(dirdev);

		gia.setEoc(0);
		// gia.setMontoCobrar(0);
		try {
			gia.setNumeroTCC(Integer.valueOf(data.getClientcode()));
		} catch (NumberFormatException e) {
			gia.setNumeroTCC(0);
		}

		PiezaType pieza = new PiezaType();
		pieza.setAlto(data.getHeight().intValue());
		pieza.setAncho(data.getWidth().intValue());
		pieza.setLargo(data.getLength().intValue());
		pieza.setPeso(BigDecimal.valueOf(data.getWeight()));
		gia.setPieza(pieza);

		gia.setReferenciaEnvio(data.getNumber().toString());
		gia.setReferenciaEnvio2(data.getRequestnumber());

		RemitenteType rem = new RemitenteType();
		// rem.setCelular("");
		rem.setEmail("");
		rem.setNombre(data.getVendorname());
		gia.setRemitente(rem);

		request.setReqGenerarIntegracionAsistida(gia);

		return request;

	}

	public ChileExpressTagResultDTO getDoDeliveryChileExpressReport(Long dodeliveryid, String vendorcode) {

		ChileExpressTagResultDTO resultDTO = new ChileExpressTagResultDTO();

		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
			
		
		
		DODeliveryW dodelivery = null;
		try {
			dodelivery = dodeliveryserver.getById(dodeliveryid);
		} catch (Exception e) {
		}

		if (dodelivery == null) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6009");
		}

		// Valida que el proveedor tenga asignado Chilexpress como empresa de Courier
		PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
		PropertyInfoDTO prop2 = new PropertyInfoDTO("courier.code", "couriercode", "CE");
		HiredCourierW[] hiredcouriers = null;
		try {
			hiredcouriers = hiredcourierserver.getByPropertiesAsArray(prop1, prop2);
		} catch (Exception e1) {
		}

		if (hiredcouriers == null || hiredcouriers.length <= 0) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible ejecutar esta operación porque su empresa no tiene configurado Courier Chilexpress", false);
		}

		// Valida que el despacho posea etiqueta de Courier en valor 2
		if (!dodelivery.getCouriertag().equals(2L)) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El despacho " + dodelivery.getNumber() + " no posee etiqueta generada", false);
		}

		// Obtiene la etiqueta
		ChileExpressTagW chileExpressTag = null;
		try {
			chileExpressTag = chileexpresstagserver.getByPropertyAsSingleResult("dodelivery.id", dodeliveryid);
		} catch (Exception e2) {
		}

		if (chileExpressTag == null) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6011");
		}

		// Valida que todas las etiquetas tengan N°mero de retiro y que sea un entero, mayor que cero, con largo entre 5
		// y 15 dígitos (excepcionalmente '1111')
		if (chileExpressTag.getWithdrawalnumber() == null || (chileExpressTag.getWithdrawalnumber() != 1111 && (chileExpressTag.getWithdrawalnumber() < 10000 || chileExpressTag.getWithdrawalnumber() > 999999999999999L))) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El N°mero de retiro ingresado para el despacho " + dodelivery.getNumber() + " debe ser num�rico, mayor a cero y con un largo entre 5 y 15 dígitos (o excepcionalmente 1111)", false);
		}

		resultDTO.setChileExpressTagW(chileExpressTag);
		resultDTO.setNumber(dodelivery.getNumber());

		return resultDTO;

	}

	public CourierInformationResultDTO getCourierInformationByDODelivery(Long dodeliveryid) {
		CourierInformationResultDTO result = new CourierInformationResultDTO();
		try {
			CourierInformationDTO[] courierinformation = courierserver.getCourierInformationByDODelivery(dodeliveryid);
			result.setCourierinformation(courierinformation);
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public CorreosChileCSVResultDTO getCorreosChileTagInformation(Long dodeliveryid, String vendorcode) {

		CorreosChileCSVResultDTO resultDTO = new CorreosChileCSVResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
			
		
		
		DODeliveryW dodelivery = null;
		try {
			dodelivery = dodeliveryserver.getById(dodeliveryid);
		} catch (Exception e) {
		}

		if (dodelivery == null) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6009");
		}

		// Valida que el proveedor tenga asignado Chilexpress como empresa de Courier
		PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
		PropertyInfoDTO prop2 = new PropertyInfoDTO("courier.code", "couriercode", "CC");
		HiredCourierW[] hiredcouriers = null;
		try {
			hiredcouriers = hiredcourierserver.getByPropertiesAsArray(prop1, prop2);
		} catch (Exception e1) {
		}

		if (hiredcouriers == null || hiredcouriers.length <= 0) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible ejecutar esta operación porque su empresa no tiene configurado Courier Correos de Chile", false);
		}

		// Valida que el despacho posea etiqueta de Courier en valor 2
		if (!dodelivery.getCouriertag().equals(2L)) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El despacho " + dodelivery.getNumber() + " no posee etiqueta generada", false);
		}

		// Obtiene la etiqueta
		CorreosChileCSVDTO chileCSVDTO = correoschiletagserver.getCorreosChileTagInformation(dodeliveryid);

		if (chileCSVDTO == null) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6011");
		}

		// Valida que todas las etiquetas tengan N°mero de retiro y que sea un entero, mayor que cero, con largo entre 5
		// y 15 dígitos (excepcionalmente '1111')
		if (chileCSVDTO.getWithdrawalnumber() == null || (chileCSVDTO.getWithdrawalnumber() != 1111 && (chileCSVDTO.getWithdrawalnumber() < 10000 || chileCSVDTO.getWithdrawalnumber() > 999999999999999L))) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El N°mero de retiro ingresado para el despacho " + dodelivery.getNumber() + " debe ser num�rico, mayor a cero y con un largo entre 5 y 15 dígitos (o excepcionalmente 1111)", false);
		}

		resultDTO.setChileCSVDTO(chileCSVDTO);

		return resultDTO;

	}
	
	public ShippingCertificationArrayResultDTO getShippingCertificationReport(ShippingCertificationInitParamDTO initParamDTO) {

		ShippingCertificationArrayResultDTO resultDTO = new ShippingCertificationArrayResultDTO();
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Validar que el proveedor tenga asignado Chilexpress como empresa de Courier
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("courier.code", "couriercode", "CE");
			HiredCourierW[] hiredcouriers = null;
			try {
				hiredcouriers = hiredcourierserver.getByPropertiesAsArray(prop1, prop2);
			} catch (Exception e1) {
				;
			}

			if (hiredcouriers == null || hiredcouriers.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No es posible ejecutar la descarga solicitada", false);
			}

			// Valida que se haya seleccionado al menos un despacho
			if (initParamDTO.getWithdrawalnumbers() == null || initParamDTO.getWithdrawalnumbers().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Debe seleccionar al menos un despacho", false);
			}

			DODeliveryStateTypeW dodstSchedulePending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");
			DODeliveryStateTypeW dodstWithdrawalPending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_RETIRO");
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date today = cal.getTime();

			HashMap<Long, DODeliveryW> deliveryMap = new HashMap<Long, DODeliveryW>();
			HashMap<Long, ChileExpressTagW> tagMap = new HashMap<Long, ChileExpressTagW>();
			List<Long> updateList = new ArrayList<Long>();
			List<Long> errorList = new ArrayList<Long>();
			CourierTagW[] couriertags = null;
			ChileExpressTagW chilexpresstag = null;
			PropertyInfoDTO prop3 = null;
			DODeliveryW doDelivery = null;
			Long dodeliveryid = null;
			Long withdrawalnumber = null;
			Date withdrawaldate = null;
			Long reschedulereasonid = null;
			for (WithdrawalNumberDTO wn : initParamDTO.getWithdrawalnumbers()) {
				dodeliveryid = wn.getDodeliveryid();
				withdrawalnumber = wn.getWithdrawalnumber();
				reschedulereasonid = wn.getReschedulereasonid();

				doDelivery = dodeliveryserver.getById(dodeliveryid, LockModeType.PESSIMISTIC_WRITE);
				deliveryMap.put(dodeliveryid, doDelivery);

				// Valida que para el despacho se est� ingresando N°mero de retiro
				if (withdrawalnumber == null) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "Debe ingresar N°meros de retiro para todos los despachos seleccionados", false);
				}

				// JPE 20180820
				// Valida que para el despacho se est� ingresando fecha de retiro
				if (wn.getWithdrawaldate() == null) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "Debe ingresar fechas de retiro para todos los despachos seleccionados", false);
				}
				withdrawaldate = DateConverterFactory2.StrToDate(wn.getWithdrawaldate());

				// JPE 20180820
				// Valida si tiene retiro ingresado con anterioridad, en cuyo caso la raz�n de agendamiento es
				// obligatoria
				chilexpresstag = chileexpresstagserver.getByPropertyAsSingleResult("dodelivery.id", dodeliveryid);
				tagMap.put(dodeliveryid, chilexpresstag);
				if (chilexpresstag.getWithdrawalnumber() != null && chilexpresstag.getWithdrawalnumber() != 0 && reschedulereasonid == null) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "Debe ingresar un motivo de reagendamiento para todos los despachos con N°mero de retiro previo", false);
				}

				// JPE 20180424
				// Valida que el N°mero de retiro sea entero, mayor a cero y con un largo entre 5 y 15 dígitos o
				// excepcionalmente '1111'
				if (withdrawalnumber != 1111 && (withdrawalnumber < 10000 || withdrawalnumber > 999999999999999L)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El N°mero de retiro ingresado para el despacho " + doDelivery.getNumber() + " debe ser num�rico, mayor a cero y con un largo entre 5 y 15 dígitos (o excepcionalmente 1111)", false);
				}

				// JPE 20180820
				// Valida que la fecha de retiro sea igual o posterior a hoy
				if (withdrawaldate.before(today)) {
					errorList.add(doDelivery.getNumber());
				}

				// JPE 20180820
				// Valida si se repite la combinación N°mero-fecha de retiro-motivo de reagendamiento con respecto a lo
				// que tiene registrado previamente
				// En ese caso solo habr� descarga de PDF
				prop1 = new PropertyInfoDTO("dodelivery.number", "dodeliverynumber", vendor.getId());
				prop2 = new PropertyInfoDTO("withdrawaldate", "withdrawaldate", withdrawaldate);
				prop3 = new PropertyInfoDTO("reschedulereason.id", "reschedulereasonid", reschedulereasonid);
				couriertags = couriertagserver.getByPropertiesAsArray(prop1, prop2, prop3);
				if (couriertags == null || couriertags.length <= 0) {
					updateList.add(dodeliveryid);
				}

				// JPE 20180405
				// Valida que el despacho posea etiqueta de Courier en valor 2
				if (!doDelivery.getCouriertag().equals(2L)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", "El despacho " + doDelivery.getNumber() + " no posee etiqueta generada", false);
				}
			}

			if (errorList.size() > 0) {
				String errorMessage = "Los siguientes despachos poseen fecha de retiro anterior a hoy:";

				for (Long deliverynumber : errorList) {
					errorMessage += "\n - " + deliverynumber;
				}
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000", errorMessage, false);
			}

			Date now = new Date();
			
			// Obtener el tipo de actualización VeV 'Cambio estado despacho VeV'
			VeVUpdateTypeW utDeliveryState = vevupdatetypeserverlocal.getByPropertyAsSingleResult("code", "DELIVERY_STATE_CHANGE");

			List<ShippingCertificationDTO> scList = new ArrayList<ShippingCertificationDTO>();
			ShippingCertificationDTO shippingcertification = null;
			CourierScheduleLogW courierschedulelog = null;
			DirectOrderW directOrder = null;
			DODeliveryStateW doDeliveryState = null;
			DODeliveryStateTypeW dodstActual = null;
			for (WithdrawalNumberDTO wn : initParamDTO.getWithdrawalnumbers()) {
				dodeliveryid = wn.getDodeliveryid();

				// Información de etiqueta Chilexpress asociada al despacho
				shippingcertification = dodeliveryserver.getShippingCertificationReport(dodeliveryid);

				if (updateList.contains(dodeliveryid)) {
					withdrawalnumber = wn.getWithdrawalnumber();
					withdrawaldate = DateConverterFactory2.StrToDate(wn.getWithdrawaldate());
					reschedulereasonid = wn.getReschedulereasonid();

					doDelivery = deliveryMap.get(dodeliveryid);

					chilexpresstag = tagMap.get(dodeliveryid);

					// Agrega una entrada en el historial de agendamientos
					courierschedulelog = new CourierScheduleLogW();
					courierschedulelog.setWithdrawalnumber(withdrawalnumber);
					courierschedulelog.setWithdrawaldate(withdrawaldate);
					courierschedulelog.setWhen(now);
					courierschedulelog.setUser(initParamDTO.getUsername());
					courierschedulelog.setCouriertagid(chilexpresstag.getId());

					// Validar si tiene retiro ingresado con anterioridad
					if (chilexpresstag.getWithdrawalnumber() != null && chilexpresstag.getWithdrawalnumber() != 0) {
						courierschedulelog.setReschedulereasonid(reschedulereasonid);
						chilexpresstag.setReschedulereasonid(reschedulereasonid);
					}

					courierschedulelogserver.addCourierScheduleLog(courierschedulelog);

					// Actualiza el N°mero y fecha de retiro en la etiqueta
					chilexpresstag.setWithdrawalnumber(withdrawalnumber);
					chilexpresstag.setWithdrawaldate(withdrawaldate);

					chilexpresstag = chileexpresstagserver.updateChileExpressTag(chilexpresstag);

					shippingcertification.setWithdrawalnumber(chilexpresstag.getWithdrawalnumber());
					shippingcertification.setWithdrawaldate(wn.getWithdrawaldate());
					shippingcertification.setReschedulereasonid(reschedulereasonid);

					// JPE 20180820
					// Valida si el despacho est� en estado 'Pendiente de Agendar' o 'Pendiente de Retiro'
					if (doDelivery.getCurrentstatetypeid().equals(dodstSchedulePending.getId()) ||
							doDelivery.getCurrentstatetypeid().equals(dodstWithdrawalPending.getId())) {
						dodstActual = dodeliverystatetypeserver.getById(doDelivery.getCurrentstatetypeid());
						
						// Actualiza o mantiene en 'Pendiente de Retiro' pero siempre modifica las fechas y el usuario
						doDelivery.setCurrentstdate(now);
						doDelivery.setCurrentstatetypeid(dodstWithdrawalPending.getId());
						doDelivery.setCurrentstwho(initParamDTO.getUsername());
						doDelivery.setCurrentstcomment("");

						doDelivery = dodeliveryserver.updateDODelivery(doDelivery);

						doDeliveryState = new DODeliveryStateW();
						doDeliveryState.setDeliveryid(doDelivery.getId());
						doDeliveryState.setDeliverystatetypeid(dodstWithdrawalPending.getId());
						doDeliveryState.setWhen(now);
						doDeliveryState.setDeliverystatedate(doDelivery.getCurrentstdate());
						doDeliveryState.setWho(initParamDTO.getUsername());
						doDeliveryState.setComment("");

						dodeliverystateserver.addDODeliveryState(doDeliveryState);
						
						directOrder = directorderserver.getById(doDelivery.getOrderid());
						
						// JPE 20200615
						// Registro de auditor�a
						VeVAuditW vevAudit = new VeVAuditW();
						vevAudit.setWhen(now);
						vevAudit.setUsername(initParamDTO.getUsername());
						vevAudit.setUsertype(initParamDTO.getUsertype());
						vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
						vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
						vevAudit.setInterfacecontent("");
						vevAudit.setItem("Estado Despacho VeV PD");
						vevAudit.setItemvalue(directOrder.getNumber().toString());
						vevAudit.setInitialdata(dodstActual.getName());
						vevAudit.setFinaldata(dodstWithdrawalPending.getName());
						vevAudit.setVendorid(directOrder.getVendorid());
						vevAudit.setVevupdatetypeid(utDeliveryState.getId());
						
						vevauditserverlocal.addVeVAudit(vevAudit);
					}
				}

				scList.add(shippingcertification);
			}

			resultDTO.setShippingcertifications((ShippingCertificationDTO[]) scList.toArray(new ShippingCertificationDTO[scList.size()]));

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public CourierStateReportResultDTO getCourierStateReport(CourierStateReportInitParamDTO initparam) {

		CourierStateReportResultDTO resultDTO = new CourierStateReportResultDTO();
		CourierStateReportDTO[] report = null;

		CourierTagW courierTag = null;
		try {
			courierTag = couriertagserver.getByPropertyAsSingleResult("dodelivery.id", initparam.getDeliveryid());
		} catch (Exception e1) {
		}

		if (courierTag != null) {
			try {
				DODeliveryW delivery = dodeliveryserver.getById(initparam.getDeliveryid());
				resultDTO.setSendNumber(courierTag.getSendnumber());
				resultDTO.setDeliveryNumber(delivery.getNumber().toString());
				OrderCriteriaDTO[] ordersby = initparam.getOrderby();
				for (OrderCriteriaDTO orderby : ordersby) {
					orderby.setPropertyname(orderby.getPropertyname().toLowerCase());
				}

				List<CourierStateW> list = courierstateserver.getByPropertyOrdered("couriertag.id", courierTag.getId(), initparam.getOrderby());
				report = new CourierStateReportDTO[list.size()];
				for (int i = 0; i < list.size(); i++) {
					CourierStateReportDTO cs = new CourierStateReportDTO();
					cs.setStarted(list.get(i).getStartdate());
					cs.setStartdate(DateConverterFactory2.DateToStr(list.get(i).getStartdate()));
					cs.setDescription(list.get(i).getDescription());
					report[i] = cs;
				}
				resultDTO.setCourierStates(report);

			} catch (OperationFailedException e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			} catch (NotFoundException e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
		}

		return resultDTO;
	}
	
	public CourierFileReportResultDTO getCourierFileReport(CourierFileReportInitParamDTO initParamDTO, boolean byPages, boolean byFilter) {
		CourierFileReportResultDTO resultDTO = new CourierFileReportResultDTO();
		
		try {
			if (initParamDTO.getSince().isAfter(initParamDTO.getUntil())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1003");
			}
			
			Date since = null;
			if (initParamDTO.getSince() != null && !initParamDTO.getSince().equals("")) {
				since = Date.from(initParamDTO.getSince().atZone( ZoneId.systemDefault()).toInstant());
			}

			Date until = null;
			if (initParamDTO.getUntil() != null && !initParamDTO.getUntil().equals("")) {
				until = Date.from(initParamDTO.getUntil().atZone( ZoneId.systemDefault()).toInstant());
			}
			
			CourierFileDTO[] courierFileDTO = courierfileserver.getCourierFileReport(since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), byPages);
		
			int total = 0;
			if (byFilter) {
				total = courierfileserver.getCourierFileCountReport(since, until);
				
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(courierFileDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setCourierfilereport(courierFileDTO);

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(value = 840)
	public boolean doUploadChilexpressFile(File file) {

		boolean result = true;
		
		Date now = new Date();
		
		try {
			chileexpressStateslog.info("Iniciando la carga de archivo de estados Courier Chilexpress " + file.getName());
			
			if (!file.getName().endsWith(".csv")) {
				// Mover archivo
				addLogAck(file.getName(), "FRACASO", "", "", "", file.getName() + " no es un archivo csv");
				moveFile(file);
				return result;
			}

			if (!file.getName().startsWith("CXP_TP")) {
				// Mover archivo
				addLogAck(file.getName(), "FRACASO", "", "", "", file.getName() + " no comienza con CXP_TP");
				moveFile(file);
				return result;
			}

			if (file.length() == 0) {
				// Mover archivo
				addLogAck(file.getName(), "FRACASO", "", "", "", file.getName() + " es un archivo vac�o");
				moveFile(file);
				return result;
			}

			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

				// Leer la primera l�nea del archivo (Encabezado)
				// NroOT;ReferenciaOT;NroOTPadre;CodigoEvento;Evento;FechaCompletaEvento;FechaEvento;HoraEvento;codigoOficina;Oficina;
				// Destino;FechaCompletaRecepcion;FechaRecepcion;HoraRecepcion;RutRecepcion;NombreRecepcion;Observacion;Motivo;NroTCC;
				// CodTipoEvento;num_orden
				String sCurrentLine = br.readLine();
				int line = 1;
				Date filedate = new Date(file.lastModified());

				// Leer cada l�nea con datos
				ChilexpressCourierStateTmpW chilexpressCourierStateTmp = null;
				String[] values = null;
				line: while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.isEmpty()) {
						continue line;
					}
					line++;

					// Separar los valores por el caracter ';' y luego recorrerlos para insertarlos en la tabla temporal
					values = sCurrentLine.split(";");
					if (values != null && values.length > 7) {
						chilexpressCourierStateTmp = new ChilexpressCourierStateTmpW();
						chilexpressCourierStateTmp.setFilename(file.getName());
						chilexpressCourierStateTmp.setFiledate(filedate);
						chilexpressCourierStateTmp.setWhen(now);
						chilexpressCourierStateTmp.setLine(line);
						chilexpressCourierStateTmp.setWorkordernumber(values[0]);
						chilexpressCourierStateTmp.setWorkorderreference(values[1]);
						chilexpressCourierStateTmp.setEventcode(values[3]);
						chilexpressCourierStateTmp.setEvent(values[4]);
						chilexpressCourierStateTmp.setEventdate(values[6]);
						chilexpressCourierStateTmp.setEventtime(values[7]);

						chilexpresscourierstatetmpserver.addChilexpressCourierStateTmp(chilexpressCourierStateTmp);
					}
				} // END WHILE READLINES
				
				if (line == 1) {
					addLogAck(file.getName(), "FRACASO", "", "", "", "Archivo solo con l�nea de encabezado");
				} else {
					// Crear registro si el archivo no est� en la tabla 'Archivo Courier'
					CourierFileW[] courierfiles = courierfileserver.getByPropertyAsArray("filename", file.getName());
					if (courierfiles == null || courierfiles.length <= 0) {
						CourierW courier = courierserver.getByPropertyAsSingleResult("code", "CE");

						CourierFileW courierfile = new CourierFileW();
						courierfile.setDayloaded(false);
						courierfile.setFilename(file.getName());
						courierfile.setCourierid(courier.getId());
						courierfile.setUploaddate(now);

						courierfile = courierfileserver.addCourierFile(courierfile);
					}
				}

			} catch (Exception e1) {
				e1.printStackTrace();
				getSessionContext().setRollbackOnly();
				addLogAck(file.getName(), "FRACASO", "", "", "", "Error en la carga de archivo de estados Courier Chilexpress (" + file.getName() + ")");
				result = false;
			} finally { // closing
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			// Mover archivo
			moveFile(file);

			chileexpressStateslog.info("Terminada la carga de archivo de estados Courier Chilexpress " + file.getName());

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			chileexpressStateslog.info("Error en la carga de archivo de estados Courier Chilexpress (" + file.getName() + ")");
			result = false;
		}
		
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doValidateChilexpressStateByFile(ChilexpressCourierStateTmpW state, HashMap<Long, DirectOrderW> doMap, HashMap<Long, DODeliveryW> dMap, HashMap<Long, CourierTagW> dctMap, HashMap<String, DODeliveryStateTypeW> dodstMap, List<Long> toDeleteStates) {
		
		boolean result = true;
		
		try {
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat dateandhour = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			
			// ReferenciaOT
			if (state.getWorkorderreference().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", "", "", "", "L�nea " + state.getLine() + ": El campo ReferenciaOT sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}
			
			Long referenceOt = null;
			try {
				referenceOt = Long.parseLong(state.getWorkorderreference());
			} catch (NumberFormatException e){
				addLogAck(state.getFilename(), "FRACASO", "", "", "", "L�nea " + state.getLine() + ": El campo ReferenciaOT no tiene un formato válido");
				toDeleteStates.add(state.getId());
				return result;
			}
			
			// NroOT
			if (state.getWorkordernumber().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", "", "L�nea " + state.getLine() + ": El campo NroOT sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}
			String nroOt = state.getWorkordernumber();
			
			// CodigoEvento
			if (state.getEventcode().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", nroOt, "L�nea " + state.getLine() + ": El campo CodigoEvento sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}

			// Evento
			if (state.getEvent().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", nroOt, "L�nea " + state.getLine() + ": El campo Evento sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}
									
			// Validar que exista el N°mero de orden de compra
			DirectOrderW directorder = null;
			DODeliveryW dodelivery = null;
			CourierTagW couriertag = null;
			if (doMap.containsKey(referenceOt)) {
				directorder = doMap.get(referenceOt);
				dodelivery = dMap.get(directorder.getDodeliveryid());
				couriertag = dctMap.get(directorder.getDodeliveryid());
				
				if (directorder == null || dodelivery == null || couriertag == null) {
					toDeleteStates.add(state.getId());
					return result;
				}
			}
			else {
				try {
					directorder = directorderserver.getByPropertyAsSingleResult("number", referenceOt);
				} catch(Exception e) {
					directorder = null;
				}
				
				if (directorder == null) {
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", nroOt,
							"L�nea " + state.getLine() + ": La orden de compra " + referenceOt.toString() + " indicada en campo ReferenciaOT no existe en B2B");
					toDeleteStates.add(state.getId());
					return result;
				}
				
				if (directorder.getDodeliveryid() == null || directorder.getDodeliveryid() == 0) {
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", nroOt, 
							"L�nea " + state.getLine() + ": La orden de compra " + referenceOt.toString() + " indicada en campo ReferenciaOT no tiene despacho creado en B2B");
					toDeleteStates.add(state.getId());
					return result;
				}
				
				try {
					dodelivery = dodeliveryserver.getById(directorder.getDodeliveryid());
				} catch(Exception e) {
					dodelivery = null;
				}
				
				if (dodelivery == null) {
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), "", nroOt,
							"L�nea " + state.getLine() + ": La orden de compra " + referenceOt + " indicada en campo ReferenciaOT no tiene despacho creado en B2B");
					toDeleteStates.add(state.getId());
					return result;
				}

				if (!dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_AGENDAR").getId()) &&
						!dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_RETIRO").getId()) &&
							!dodelivery.getCurrentstatetypeid().equals(dodstMap.get("EN_RUTA").getId())) {
					DODeliveryStateTypeW actualstate = dodeliverystatetypeserver.getById(dodelivery.getCurrentstatetypeid());
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
							"L�nea " + state.getLine() + ": El despacho " + dodelivery.getNumber() + " para la orden de compra " + referenceOt + " se encuentra en estado " + actualstate.getName());
					toDeleteStates.add(state.getId());
					return result;
				}
				
				try {
					couriertag = couriertagserver.getByPropertyAsSingleResult("dodelivery.id", dodelivery.getId());								
				} catch(Exception e) {
					couriertag = null;
				}
				dctMap.put(directorder.getDodeliveryid(), couriertag);
				
				if (couriertag == null || !dodelivery.getCouriertag().equals(2L)) {
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
							"L�nea " + state.getLine() + ": El despacho " + dodelivery.getNumber() + " para la orden de compra " + referenceOt + " no tiene etiqueta en B2B");
					toDeleteStates.add(state.getId());
					return result;
				}
				
				if(!couriertag.getSendnumber().equals(nroOt)){
					addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
							"L�nea " + state.getLine() + ": El despacho " + dodelivery.getNumber() + " para la orden de compra " + referenceOt + " no tiene asociado el N°mero de OT indicado en archivo");
					toDeleteStates.add(state.getId());
					return result;
				}
				
				doMap.put(referenceOt, directorder);
				dMap.put(directorder.getDodeliveryid(), dodelivery);
			}
			
			// FechaEvento
			if (state.getEventdate().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", "", "", "", "L�nea " + state.getLine() + ": El campo FechaEvento sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}
			
			try {
				date.parse(state.getEventdate());
			} catch (ParseException e) {
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
						"L�nea " + state.getLine() + ": La fecha del campo FechaEvento no tiene un formato válido");
				toDeleteStates.add(state.getId());
				return result;
			}
			String fechaevento = state.getEventdate();
			
			// HoraEvento
			if (state.getEventtime().isEmpty()) {
				addLogAck(state.getFilename(), "FRACASO", "", "", "", "L�nea " + state.getLine() + ": El campo HoraEvento sin valor para analizar");
				toDeleteStates.add(state.getId());
				return result;
			}
			
			try {
				hour.parse(state.getEventtime());
			} catch (ParseException e){
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
						"L�nea " + state.getLine() + ": La hora del campo HoraEvento no tiene un formato válido");
				toDeleteStates.add(state.getId());
				return result;
			}
			String horaevento = state.getEventtime();
			
			Date fechareal = null;
			try {
				fechareal = dateandhour.parse(fechaevento + " " + horaevento);
			} catch (ParseException e) {
				addLogAck(state.getFilename(), "FRACASO", referenceOt.toString(), dodelivery.getNumber().toString(), nroOt,
						"L�nea " + state.getLine() + ": La fecha del campo HoraEvento o FechaEvento no tiene un formato válido");
				toDeleteStates.add(state.getId());
				return result;
			}
			
			// Actualizar el registro con el despacho
			state.setEventfulldate(fechareal);
			state.setDirectorderid(directorder.getId());
			state.setDodeliveryid(dodelivery.getId());
			state.setCouriertagid(couriertag.getId());
			chilexpresscourierstatetmpserver.updateChilexpressCourierStateTmp(state);
		
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			addLogAck(state.getFilename(), "FRACASO", state.getWorkorderreference(), "", "", "L�nea " + state.getLine() + ": Error validando estado");
			result = false;
		}
		
		return result;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doUploadChilexpressStateByFile(CourierStateFileDataDTO courierstatefiledata, Long directorderid, HashMap<String, DODeliveryStateTypeW> dodstMap) {

		boolean result = true;
		
		DirectOrderW directorder = null;
		DODeliveryW dodelivery = null;
		try {
			directorder = directorderserver.getById(directorderid, LockModeType.PESSIMISTIC_WRITE); // captura las modificaciones de otros procesos
			dodelivery = dodeliveryserver.getById(courierstatefiledata.getDodeliveryid(), LockModeType.PESSIMISTIC_WRITE); // captura las modificaciones de otros procesos

			List<CourierStateW> cslist = null;
			try {
				PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
				properties[0] = new PropertyInfoDTO("startdate", "startdate", courierstatefiledata.getStartdate());
				properties[1] = new PropertyInfoDTO("couriertag.id", "couriertag", courierstatefiledata.getCouriertagid());
				properties[2] = new PropertyInfoDTO("description", "description", courierstatefiledata.getDescription());
				cslist = courierstateserver.getByProperties(properties);
			} catch (Exception e) {
			}

			Date now = new Date();
			
			if (cslist == null || cslist.isEmpty()) {
				if (dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_AGENDAR").getId()) ||
						dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_RETIRO").getId()) ||
							dodelivery.getCurrentstatetypeid().equals(dodstMap.get("EN_RUTA").getId())) {

					// Se guardan los datos del estado Courier
					CourierStateW courierState = new CourierStateW();
					courierState.setCouriertagid(courierstatefiledata.getCouriertagid());
					courierState.setDescription(courierstatefiledata.getDescription());
					courierState.setStartdate(courierstatefiledata.getStartdate());
					courierState.setUploaddate(courierstatefiledata.getUploaddate());
					courierState.setCourierfileid(courierstatefiledata.getCourierfileid());

					courierState = courierstateserver.addCourierState(courierState);

					addLogAck(courierstatefiledata.getFilename(), "EXITO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea " + courierstatefiledata.getLine() + ": Se registra al despacho el estado Chilexpress "
							+ courierstatefiledata.getDescription());

					// JPE 20180412
					// El sistema detecta que el despacho no ha actualizado los N°meros de retiro
					DODeliveryStateW dodeliverystate = null;
					if (dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_AGENDAR").getId())) {
						// Actualiza el estado del despacho a EN RUTA y su historial de estados
						dodelivery.setCurrentstdate(courierstatefiledata.getStartdate());
						dodelivery.setCurrentstatetypeid(dodstMap.get("PEND_RETIRO").getId());
						dodelivery.setCurrentstwho("Archivo Tracking Chilexpress");
						dodelivery.setCurrentstcomment("Actualizado vía archivo Tracking Push Chilexpress: " + courierstatefiledata.getFilename());

						dodelivery = dodeliveryserver.updateDODelivery(dodelivery);

						dodeliverystate = new DODeliveryStateW();
						dodeliverystate.setDeliveryid(dodelivery.getId());
						dodeliverystate.setDeliverystatetypeid(dodelivery.getCurrentstatetypeid());
						dodeliverystate.setWhen(now);
						dodeliverystate.setDeliverystatedate(dodelivery.getCurrentstdate());
						dodeliverystate.setWho("Archivo Tracking Chilexpress");
						dodeliverystate.setComment("Actualizado vía archivo Tracking Push Chilexpress: " + courierstatefiledata.getFilename());

						dodeliverystateserver.addDODeliveryState(dodeliverystate);
						
						// Crear archivo ACK de �xito de carga
						addLogAck(courierstatefiledata.getFilename(), "EXITO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea " + courierstatefiledata.getLine() + ": Despacho actualizado a estado "
								+ dodstMap.get("PEND_RETIRO").getName() + ". Chilexpress indicó " + courierstatefiledata.getDescription());
					}

					// JPE 20180410
					// El sistema detecta que el tracking push registrado para el despacho es el primero (nunca antes ha
					// recibido notificaciones)
					if (dodelivery.getCurrentstatetypeid().equals(dodstMap.get("PEND_RETIRO").getId())) {
						// Actualiza el estado del despacho a EN RUTA y su historial de estados
						dodelivery.setCurrentstdate(courierstatefiledata.getStartdate());
						dodelivery.setCurrentstatetypeid(dodstMap.get("EN_RUTA").getId());
						dodelivery.setCurrentstwho("Archivo Tracking Chilexpress");
						dodelivery.setCurrentstcomment("Actualizado vía archivo Tracking Push Chilexpress: " + courierstatefiledata.getFilename());

						dodelivery = dodeliveryserver.updateDODelivery(dodelivery);

						dodeliverystate = new DODeliveryStateW();
						dodeliverystate.setDeliveryid(dodelivery.getId());
						dodeliverystate.setDeliverystatetypeid(dodelivery.getCurrentstatetypeid());
						dodeliverystate.setWhen(now);
						dodeliverystate.setDeliverystatedate(dodelivery.getCurrentstdate());
						dodeliverystate.setWho("Archivo Tracking Chilexpress");
						dodeliverystate.setComment("Actualizado vía archivo Tracking Push Chilexpress: " + courierstatefiledata.getFilename());

						dodeliverystateserver.addDODeliveryState(dodeliverystate);
						
						DirectOrderDetailW[] directorderdetails = directorderdetailserver.getByPropertyAsArray("directorder.id", directorder.getId());
						DODeliveryDetailW[] dodeliverydetails = dodeliverydetailserver.getByPropertyAsArray("dodelivery.id", dodelivery.getId());

						doSendInt1846(directorder, dodelivery, dodeliverydetails, directorderdetails, "1");

						// Crear archivo ACK de �xito de carga
						addLogAck(courierstatefiledata.getFilename(), "EXITO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea " + courierstatefiledata.getLine() + ": Despacho actualizado a estado "
								+ dodstMap.get("EN_RUTA").getName() + ". Chilexpress indicó " + courierstatefiledata.getDescription());
					}

					DODeliveryStateTypeW newstate = null;
					if (courierstatefiledata.getDescription().equalsIgnoreCase("PIEZA ENTREGADA A DESTINATARIO")) {
						newstate = dodstMap.get("REC_CONFORME");
					} else if (courierstatefiledata.getDescription().equalsIgnoreCase("PIEZA DEVUELTA A REMITENTE")) {
						newstate = dodstMap.get("EXPECTATIVAS");
					}
					// JPE 20180814
					else if (courierstatefiledata.getDescription().toUpperCase().contains("PIEZA EXTRAVIADA")) {
						newstate = dodstMap.get("EXTRAVIADO");
					}
					// JPE 20200120
					else if (courierstatefiledata.getDescription().toUpperCase().contains("RECEPCIONADO") ||
								courierstatefiledata.getDescription().toUpperCase().contains("PIEZA RETIRADA POR CHILEXPRESS")) {
						directorder.setCourierreceived(true);
						directorder = directorderserver.updateDirectOrder(directorder);
					}

					if (newstate != null) {
						VendorW vendor = vendorserver.getById(dodelivery.getVendorid());
						
						DoChangeDODeliveryStateTypeInitParamDTO initParamDTO = new DoChangeDODeliveryStateTypeInitParamDTO();
						initParamDTO.setComment("Actualizado vía archivo Tracking Push Chilexpress: " + courierstatefiledata.getFilename());
						initParamDTO.setReprogdate(DateConverterFactory2.DateToStr(courierstatefiledata.getStartdate()));
						initParamDTO.setCommiteddate(dodelivery.getCommiteddate());
						initParamDTO.setDodeliveryid(dodelivery.getId());
						initParamDTO.setVendorcode(vendor.getRut());
						initParamDTO.setUsername("Archivo Tracking Chilexpress");
						initParamDTO.setDodeliverystatetypeid(newstate.getId());

						doChangeDODeliveryStateType(initParamDTO, dodelivery, true);

						// Crear archivo ACK de �xito de carga
						addLogAck(courierstatefiledata.getFilename(), "EXITO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea " + courierstatefiledata.getLine() + ": Despacho actualizado a estado " + newstate.getName()
								+ ". Chilexpress indic� " + courierstatefiledata.getDescription());
					}

					dodeliveryserver.flush();
				} else {
					DODeliveryStateTypeW actualstate = dodeliverystatetypeserver.getById(dodelivery.getCurrentstatetypeid());
					addLogAck(courierstatefiledata.getFilename(), "FRACASO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea " + courierstatefiledata.getLine() + ": El despacho " + dodelivery.getNumber()
							+ " se actualizó por otro proceso a estado " + actualstate.getName());
				}
			} else {
				addLogAck(courierstatefiledata.getFilename(), "FRACASO", directorder.getNumber().toString(), dodelivery.getNumber().toString(), courierstatefiledata.getCouriertagsendnumber(), "Línea" + courierstatefiledata.getLine() + ": El estado " + courierstatefiledata.getDescription()
						+ " ya fue registrado para el despacho");
			}

			// Eliminar el registro temporal
			chilexpresscourierstatetmpserver.deleteByProperty("id", courierstatefiledata.getChilexpresscourierstatetmpid());

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			addLogAck(courierstatefiledata.getFilename(), "FRACASO", directorder.getNumber().toString(), dodelivery != null ? dodelivery.getNumber().toString() : "", courierstatefiledata.getCouriertagsendnumber(), "Línea" + courierstatefiledata.getLine() + ": Error procesando estado "
					+ courierstatefiledata.getDescription());
			result = false;
		}
		
		return result;
	}

	public RescheduleReasonArrayResultDTO getShowableRescheduleReasons() {

		RescheduleReasonArrayResultDTO resultDTO = new RescheduleReasonArrayResultDTO();

		try {
			RescheduleReasonDTO[] reschedulereasons = reschedulereasonserver.getShowableRescheduleReasons();
			if (reschedulereasons == null || reschedulereasons.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6012");
			}

			resultDTO.setReschedulereasons(reschedulereasons);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public CourierScheduleLogArrayResultDTO getCourierScheduleLogByDODelivery(CourierScheduleLogInitParamDTO initParamDTO) {

		CourierScheduleLogArrayResultDTO resultDTO = new CourierScheduleLogArrayResultDTO();

		try {
			// Validar que el despacho posea un N°mero de retiro y sea diferente de cero
			CourierTagW couriertag = couriertagserver.getByPropertyAsSingleResult("dodelivery.id", initParamDTO.getDodeliveryid());
			if (couriertag.getWithdrawalnumber() == null || couriertag.getWithdrawalnumber() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6013");
			}

			CourierScheduleLogDTO[] courierschedulelog = courierschedulelogserver.getCourierScheduleLogByDODelivery(initParamDTO.getDodeliveryid(), initParamDTO.getOrderby());
			if (courierschedulelog == null || courierschedulelog.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6014");
			}

			resultDTO.setCourierschedulelog(courierschedulelog);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	private void addLogAck(String fileName, String result, String ocNumber, String dNumber, String sendNumber, String comment) {

		// Separador
		String s = ";";

		// Campos
		String tipoArchivo = "TrackingPushCXP";
		String identificador = fileName;
		String estadoActualizacion = result;
		String nroOc = ocNumber;
		String despacho = dNumber;
		String nroOt = sendNumber;
		String comentario = comment;

		chileexpressStateslog.info(tipoArchivo + s + identificador + s + estadoActualizacion + s + nroOc + s + despacho + s + nroOt + s + comentario);
	}

	private void moveFile(File file) throws OperationFailedException, IOException, IndexOutOfBoundsException {

		File dir = new File(RegionalLogisticConstants.getInstance().getUPLOADCHILEXPRESSSTATEBACKUP() + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + File.separator);
		dir.mkdir();

		int index = bbr.b2b.regional.logistic.utils.StringUtils.getInstance().lastIndexOf(file.getName(), ".");
		String backupfilename = bbr.b2b.regional.logistic.utils.StringUtils.getInstance().insertInto(file.getName(), "_" + (new Date()).getTime(), index);

		Files.move(Paths.get(file.getAbsolutePath()), Paths.get(dir.getAbsolutePath() + File.separator + backupfilename));
	}

}
