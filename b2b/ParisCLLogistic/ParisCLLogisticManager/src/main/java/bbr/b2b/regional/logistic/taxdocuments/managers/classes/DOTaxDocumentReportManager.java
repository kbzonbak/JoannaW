package bbr.b2b.regional.logistic.taxdocuments.managers.classes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.util.dom.DOMInHandler;
import org.codehaus.xfire.util.dom.DOMOutHandler;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO.ComparisonOperator;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.regional.logistic.buyorders.report.classes.ClientDTO;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.CourierStateServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.HiredCourierServerLocal;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierStateW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.HiredCourierW;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.parser.dte.DTEDefType;
import bbr.b2b.regional.logistic.parser.dte.DTEDefType.Documento.Referencia;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentStateServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentStateTypeServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.classes.DOTaxDocumentTicketServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateTypeW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentTicketW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.DOTaxDocumentReportManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.DOTaxDocumentReportManagerRemote;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingCSVReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentAddDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentAddInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentAddResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationInitParam;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationUpdateDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationUpdateInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationValidationBean;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentResultBean;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateTypeDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentValidationResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DirectOrderIdsByPagesDTO;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.utils.StringUtils;
import bbr.b2b.regional.logistic.vendors.classes.PropertyVendorServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import cl.paperless.webapp.online.webservices.OnlineClient;
import cl.paperless.webapp.online.webservices.OnlinePortType;
import cl.paperless.webapp.online.webservices.OnlineRecoveryRecHandler;

@Stateless(name = "managers/DOTaxDocumentReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOTaxDocumentReportManager implements DOTaxDocumentReportManagerLocal, DOTaxDocumentReportManagerRemote {

	private static Logger logger = Logger.getLogger(DOTaxDocumentReportManager.class);
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DirectOrderServerLocal directorderserver;
	
	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;
	
	@EJB
	DOTaxDocumentServerLocal dotaxdocumentserver;
	
	@EJB
	DOTaxDocumentStateServerLocal dotaxdocumentstateserver;
	
	@EJB
	DOTaxDocumentStateTypeServerLocal dotaxdocumentstatetypeserver;
	
	@EJB
	DOTaxDocumentTicketServerLocal documentticketserver;
	
	@EJB
	PropertyVendorServerLocal propertyvendorserver;
	
	@EJB
	HiredCourierServerLocal hiredcourierserver;
	
	@EJB
	CourierStateServerLocal courierstateserver;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	private static JAXBContext jDTE = null;
	
	private static JAXBContext getDTEJC() throws JAXBException {
		if (jDTE == null) {
			jDTE = JAXBContext.newInstance("bbr.b2b.regional.logistic.parser.dte");
		}
		return jDTE;
	}
	
	public DOTaxDocumentStateTypeArrayResultDTO getDOTaxDocumentStateTypes(){
		
		DOTaxDocumentStateTypeArrayResultDTO resultDTO = new DOTaxDocumentStateTypeArrayResultDTO();
		
		try {
			OrderCriteriaDTO orderby = new OrderCriteriaDTO();
			orderby.setPropertyname("id");
			orderby.setAscending(true);
						
			List<DOTaxDocumentStateTypeW> tdstList = dotaxdocumentstatetypeserver.getByPropertyOrdered("showable", true, orderby);
						
			if(tdstList != null){
				DOTaxDocumentStateTypeW[] doTaxDocumentStateTypeW = tdstList.toArray(new DOTaxDocumentStateTypeW[0]);
				DOTaxDocumentStateTypeDTO[] doTaxDocumentStateTypeDTO = new DOTaxDocumentStateTypeDTO[doTaxDocumentStateTypeW.length];
				BeanExtenderFactory.copyProperties(doTaxDocumentStateTypeW, doTaxDocumentStateTypeDTO, DOTaxDocumentStateTypeDTO.class);
				
				resultDTO.setDotaxdocumentstatetypes(doTaxDocumentStateTypeDTO);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public DOTaxDocumentReportResultDTO getDOTaxDocumentReportByVendorAndFilter(DOTaxDocumentReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated){
		
		DOTaxDocumentReportResultDTO resultDTO = new DOTaxDocumentReportResultDTO();
		
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
			// Validar que el proveedor escogido tenga rollout de Facturación
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("code", "code", "INICIO_FACT_VEVPD");
			PropertyVendorW[] propertyvendors = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
			if (propertyvendors == null || propertyvendors.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4511");
			}
			PropertyVendorW propertyvendor = propertyvendors[0];
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
			Calendar cal = null;
			Date since = null;
			Date until = null;
			Date minEmittedDate = null;
			Date maxCourierStateDate = null;
			switch(initParamDTO.getFiltertype()) {
			case 1:
				break;
			case 2:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 5:
				Locale locale = new Locale("es", "CL");
				cal = Calendar.getInstance(locale);

				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
				cal.setTime(since);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				since = cal.getTime();

				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
				cal.setTime(until);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				until = cal.getTime();
				
				// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
				if(!until.after(since)){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
				}
				break;
			case 3:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 4:
				// Validar que el N°mero sea mayor que cero y con un m�ximo de 10 dígitos
				if(initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
				}
				break;
			case 0:								
			default:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
				break;
			}
			
			DOTaxDocumentReportDataDTO[] taxdocumentreport = null;
			int total = 0;
			switch (initParamDTO.getFiltertype()) {
			case 1:
				// ESTADO DE FACTURACION
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndStateType(vendor.getId(), initParamDTO.getTaxdocumentstatetypeid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndStateType(vendor.getId(), initParamDTO.getTaxdocumentstatetypeid());
				}
				break;
			case 2:
				// FECHA ENTREGA OC
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndDeliveryDate(vendor.getId(), minEmittedDate, maxCourierStateDate, since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndDeliveryDate(vendor.getId(), minEmittedDate, maxCourierStateDate, since, until);
				}
				break;
			case 3:
				// N° ORDEN DE COMPRA
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndOrderNumber(vendor.getId(), minEmittedDate, maxCourierStateDate, initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndOrderNumber(vendor.getId(), minEmittedDate, maxCourierStateDate, initParamDTO.getNumber());
				}
				break;
			case 4:
				// N° DOCUMENTO
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndNumber(vendor.getId(), initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndNumber(vendor.getId(), initParamDTO.getNumber());
				}
				break;
			case 5:
				// FECHA EMISION DOCUMENTO
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndEmissionDate(vendor.getId(), since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndEmissionDate(vendor.getId(), since, until);
				}
				break;
			case 0:
			default:
				// PENDIENTE DE FACTURACION
				taxdocumentreport = dotaxdocumentserver.getDOTaxDocumentReportByVendorAndPending(vendor.getId(), minEmittedDate, maxCourierStateDate, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOTaxDocumentReportByVendorAndPending(vendor.getId(), minEmittedDate, maxCourierStateDate);
				}
				break;
			}

			if (byFilter) {
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(taxdocumentreport.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setDotaxdocuments(taxdocumentreport);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
		
	}
	
	public DOTaxDocumentReportResultDTO getDOTaxDocumentReportByDirectOrders(DOTaxDocumentDownloadInitParamDTO initParamDTO) {
		
		DOTaxDocumentReportResultDTO resultDTO = new DOTaxDocumentReportResultDTO();
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
			// Chequea la existencia del vendor
			try {
				vendorserver.getById(vendor.getId());
			} catch (NotFoundException e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
			}

			// Chequea la existencia de las órdenes para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			
			for (Long orderId : initParamDTO.getDirectorderids()) {
				properties[1] = new PropertyInfoDTO("id", "id", orderId);
				List<DirectOrderW> directorders = directorderserver.getByProperties(properties);

				if (directorders == null || directorders.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4504");
				}
			}

			DOTaxDocumentReportDataDTO[] dotaxdocuments = dotaxdocumentserver.getDOTaxDocumentReportByDirectOrders(initParamDTO.getDirectorderids(), initParamDTO.getOrderby());

			resultDTO.setDotaxdocuments(dotaxdocuments);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DirectOrderIdsByPagesDTO getDOTaxDocumentReportByPages(DOTaxDocumentReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO) {
		
		DirectOrderIdsByPagesDTO resultDTO = new DirectOrderIdsByPagesDTO();
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
			// Validar que el proveedor escogido tenga rollout de Facturación
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("code", "code", "INICIO_FACT_VEVPD");
			PropertyVendorW[] propertyvendors = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
			if (propertyvendors == null || propertyvendors.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4511");
			}
			PropertyVendorW propertyvendor = propertyvendors[0];
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = null;
			Date since = null;
			Date until = null;
			Date minEmittedDate = null;
			Date maxCourierStateDate = null;
			switch(initParamDTO.getFiltertype()) {
			case 1:
				break;
			case 2:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 5:
				Locale locale = new Locale("es", "CL");
				cal = Calendar.getInstance(locale);

				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
				cal.setTime(since);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				since = cal.getTime();

				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
				cal.setTime(until);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				until = cal.getTime();
				
				// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
				if(!until.after(since)){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
				}
				break;
			case 3:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 4:
				// Validar que el N°mero sea mayor que cero y con un m�ximo de 10 dígitos
				if(initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
				}
				break;
			case 0:
			default:
				minEmittedDate = StringUtils.getInstance().StrToDateIgnoreTime(propertyvendor.getValue());
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
				break;
			}
			
			Long[] directorderids = null;
			switch (initParamDTO.getFiltertype()) {
			case 1:
				// ESTADO DE FACTURACION
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorStateTypeAndPages(vendor.getId(), initParamDTO.getTaxdocumentstatetypeid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 2:
				// FECHA ENTREGA OC
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorDeliveryDateAndPages(vendor.getId(), minEmittedDate, maxCourierStateDate, since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 3:
				// N° ORDEN DE COMPRA
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorOrderNumberAndPages(vendor.getId(), minEmittedDate, maxCourierStateDate, initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 4:
				// N° DOCUMENTO
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorNumberAndPages(vendor.getId(), initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 5:
				// FECHA EMISION DOCUMENTO
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorEmissionDateAndPages(vendor.getId(), since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 0:
			default:
				// PENDIENTE DE FACTURACION
				directorderids = dotaxdocumentserver.getDOTaxDocumentIdsByVendorPendingAndPages(vendor.getId(), minEmittedDate, maxCourierStateDate, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			}

			resultDTO.setDirectorderids(directorderids);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DOTaxDocumentStateReportResultDTO getDOTaxDocumentStateReportByDOTaxDocuments(DOTaxDocumentStateInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated) {
		
		DOTaxDocumentStateReportResultDTO resultDTO = new DOTaxDocumentStateReportResultDTO();
		
		try {
			if (initParamDTO.getDotaxdocumentids() == null || initParamDTO.getDotaxdocumentids().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4502");
			}
			
			if (!initParamDTO.getVendorcode().equals(String.valueOf(RegionalLogisticConstants.getInstance().getINT_TODOS()))) {
				// Chequea la existencia del vendor
				VendorW vendor;
				try {
					vendor = vendorserver.getByPropertyAsSingleResult("rut", initParamDTO.getVendorcode());
				} catch (NotFoundException e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
				}
				
				// Chequea la existencia de las facturas para el proveedor
				PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
				properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
				
				for (Long doTaxDocumentId : initParamDTO.getDotaxdocumentids()) {
					properties[1] = new PropertyInfoDTO("id", "id", doTaxDocumentId);
					List<DOTaxDocumentW> taxdocuments = dotaxdocumentserver.getByProperties(properties);

					if (taxdocuments == null || taxdocuments.size() <= 0) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4507");
					}
				}
			}

			DOTaxDocumentStateDTO[] dotaxdocumentstates = dotaxdocumentstateserver.getDOTaxDocumentStateReportByDOTaxDocuments(initParamDTO.getDotaxdocumentids(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);

			if (byFilter) {
				int total = dotaxdocumentstateserver.countDOTaxDocumentStateReportByDOTaxDocuments(initParamDTO.getDotaxdocumentids());
				
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(dotaxdocumentstates.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);				
			}
			
			resultDTO.setDotaxdocumentstates(dotaxdocumentstates);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DOTaxDocumentAddResultDTO doAddDOTaxDocumentsByDirectOrders(DOTaxDocumentAddInitParamDTO initParamDTO, boolean fromFile){
		DOTaxDocumentAddResultDTO resultDTO = new DOTaxDocumentAddResultDTO();
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
			// Validar que se haya seleccionado al menos un registro
			if (initParamDTO.getAdddata() == null || initParamDTO.getAdddata().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4502");
			}
			
			// Obtener los estados de órdenes 'Recepcionada' y 'Extraviado'
			DirectOrderStateTypeW dostReceived = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
			DirectOrderStateTypeW dostLost = directorderstatetypeserver.getByPropertyAsSingleResult("code", "EXTRAVIADO");
			
			// Obtener los estados de factura 'Documento Faltante', 'Rechazado por �rea VeV' o 'RM Rechazada'
			DOTaxDocumentStateTypeW tdstMissingDocument = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "DOC_FAL");
			DOTaxDocumentStateTypeW tdstVeVRejected = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "REC_VEV");
			DOTaxDocumentStateTypeW tdstRMRejected = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "RM_REC");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			
			PropertyInfoDTO prop1 = null;
			PropertyInfoDTO prop2 = null;
			PropertyInfoDTO prop3 = null;
			DirectOrderW[] directorders = null;
			DirectOrderW directorder = null;
			DOTaxDocumentW[] dotaxdocuments = null;
			CourierStateW[] courierStates = null;
			HashMap<Long, DirectOrderW> doMap = new HashMap<Long, DirectOrderW>();
			HashMap<String, DOTaxDocumentW> dtdMap = new HashMap<String, DOTaxDocumentW>();
			List<Long> dotdnList = new ArrayList<Long>();
			DOTaxDocumentAddDataDTO data = null;
			String error = "";
			List<BaseResultDTO> baseresultList = new ArrayList<BaseResultDTO>();
			for (int i = 0; i < initParamDTO.getAdddata().length; i++) {
				data = initParamDTO.getAdddata()[i];
				
				// Validar que la orden de compra sea tipo VeV PD
				directorders = directorderserver.getByPropertyAsArray("number", data.getDonumber());
				if (directorders != null && directorders.length > 0) {
					directorder = directorders[0];
					
					// Validar que la orden de compra corresponda al proveedor
					if (directorder.getVendorid().equals(vendor.getId())) {
						doMap.put(data.getDonumber(), directorder);
						
						// JPE 20200611
						// Validar que la orden est� en estado 'Recepcionada' o 'Extraviado'
						if (!directorder.getCurrentstatetypeid().equals(dostReceived.getId()) &&
								!directorder.getCurrentstatetypeid().equals(dostLost.getId())) {
							// JPE 20200706
							// Validar que la orden tenga estado Courier 'RECEPCIONADO' desde cierta fecha hacia atr�s
							prop1 = new PropertyInfoDTO("couriertag.dodelivery.id", "dodeliveryid", directorder.getDodeliveryid());
							prop2 = new PropertyInfoDTO("description", "description", "RECEPCIONADO");
							prop3 = new PropertyInfoDTO("startdate", "startdate", maxCourierStateDate, ComparisonOperator.LESS_THAN);
							courierStates = courierstateserver.getByPropertiesAsArray(prop1, prop2, prop3);
							if (courierStates == null || courierStates.length == 0) {
								error = (fromFile ? "Fila " + (i + 1) + ", columna 1: " : "") + "El estado actual de la orden de compra " + data.getDonumber() + " no permite informar su factura";
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
							}
						}
												
						// Obtener la factura asociada a la orden
						dotaxdocuments = dotaxdocumentserver.getByPropertyAsArray("directorder.id", directorder.getId());
						
						boolean isOK = true;
						if (dotaxdocuments != null && dotaxdocuments.length > 0) {
							// Validar que la factura se encuentre en estado 'Documento Faltante', 'Rechazado por �rea VeV' o 'RM Rechazada'
							isOK = dotaxdocuments[0].getCurrentstatetypeid().equals(tdstMissingDocument.getId()) ||
									dotaxdocuments[0].getCurrentstatetypeid().equals(tdstVeVRejected.getId()) ||
										dotaxdocuments[0].getCurrentstatetypeid().equals(tdstRMRejected.getId());
							dtdMap.put(data.getDonumber() + "_" + data.getDotaxdocumentnumber(), dotaxdocuments[0]);
						}						
						
						// Validar que la orden de compra no posea una factura o se encuentre en los estados anteriores
						if (dotaxdocuments == null || dotaxdocuments.length <= 0 || isOK) {
							// Validar que el N°mero de documento tributario sea mayor que cero y con un m�ximo de 10 dígitos
							if (data.getDotaxdocumentnumber() <= 0 || data.getDotaxdocumentnumber() > 9999999999L) {
								error = (fromFile ? "Fila " + (i + 1) + ", columna 2: " : "") + "Para la OC N° " + data.getDonumber() + " debe ingresar N°mero mayor a cero y como m�ximo 10 dígitos";
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
							}
							
							// JPE 20181003
							// Validar que no se repita el mismo N°mero de documento en la selección
							if (dotdnList.contains(data.getDotaxdocumentnumber())) {
								error = (fromFile ? "Fila " + (i + 1) + ", columna 2: " : "") + "El N°mero de documento " + data.getDotaxdocumentnumber() + " se est� ingresando para más de una orden de compra";
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
							}
							else {
								dotdnList.add(data.getDotaxdocumentnumber());
							}
														
							// Validar que el N°mero de documento no exista para facturas del mismo proveedor
							prop1 = new PropertyInfoDTO("number", "number", data.getDotaxdocumentnumber());
							prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
							dotaxdocuments = dotaxdocumentserver.getByPropertiesAsArray(prop1, prop2);
							if (dotaxdocuments != null && dotaxdocuments.length > 0 && !dotaxdocuments[0].getDirectorderid().equals(directorder.getId())) {
								error = (fromFile ? "Fila " + (i + 1) + ", columna 2: " : "") + "El N°mero de documento " + data.getDotaxdocumentnumber() + " ya est� cargado para otra orden en el sistema";
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
							}
						}
						else {
							error = (fromFile ? "Fila " + (i + 1) + ", columna 1: " : "") + "La orden de compra " + data.getDonumber() + " ya posee una factura cargada y en proceso de validación";
							baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
						}
					}
					else{
						error = (fromFile ? "Fila " + (i + 1) + ", columna 1: " : "") + "La orden de compra " + data.getDonumber() + " no est� cargada para el proveedor";
						baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
					}
				}
				else{
					error = (fromFile ? "Fila " + (i + 1) + ", columna 1: " : "") + "La orden de compra " + data.getDonumber() + " no es de tipo VeV PD";
					baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4503", error, false));
				}
			}
			
			if (baseresultList.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]), comparator);
				resultDTO.setValidationerrors(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4503");				
			}
			
			DOTaxDocumentW dotaxdocument = null;
			DOTaxDocumentStateW dotaxdocumentstate = null;
			Date now = null;
			
			// JPE 20180903
			// Validar si la cantidad de OCs a procesar es mayor al máximo configurado por el sistema
			if (initParamDTO.getAdddata().length > Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxcourierprocessing"))) {
				// Generar un ticket, en estado no procesado, que agrupe todas las facturas creadas
				DOTaxDocumentTicketW documentTicket = new DOTaxDocumentTicketW();
				documentTicket.setCreationdate(new Date());
				documentTicket.setUserid(initParamDTO.getUserid());
				documentTicket.setUsermail(initParamDTO.getUseremail());
				documentTicket.setUsername(initParamDTO.getUsername());
				documentTicket.setProcessed(false);
				documentTicket.setStartdate(null);
				documentTicket.setEnddate(null);
				documentTicket = documentticketserver.addDOTaxDocumentTicket(documentTicket);
				
				// Obtener el tipo de estado de factura 'Procesando Documento'
				DOTaxDocumentStateTypeW tdstProcessingDocument = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "PRO_DOC");
				
				// Almacenar/actualizar las facturas y su estado
				for (DOTaxDocumentAddDataDTO adddata : initParamDTO.getAdddata()) {					
					now = new Date();
					
					dotaxdocumentstate = new DOTaxDocumentStateW();				
					dotaxdocument = dtdMap.get(adddata.getDonumber() + "_" + adddata.getDotaxdocumentnumber());
					if (dotaxdocument == null) {
						dotaxdocument = new DOTaxDocumentW();
						dotaxdocument.setNumber(adddata.getDotaxdocumentnumber());
						dotaxdocument.setAmount(doMap.get(adddata.getDonumber()).getTotalreceived());
						dotaxdocument.setCurrentstatetypeid(tdstProcessingDocument.getId());
						dotaxdocument.setCurrentstatetypedate(now);
						dotaxdocument.setVendorid(vendor.getId());
						dotaxdocument.setDirectorderid(doMap.get(adddata.getDonumber()).getId());
						dotaxdocument.setDotaxdocumentticketid(documentTicket.getId());
						dotaxdocument.setEmitted(null);
						dotaxdocument.setPdfurl(null);
						dotaxdocument = dotaxdocumentserver.addDOTaxDocument(dotaxdocument);
						
						dtdMap.put(adddata.getDonumber() + "_" + adddata.getDotaxdocumentnumber(), dotaxdocument);
						
						dotaxdocumentstate.setComment("Carga manual � N°mero " + adddata.getDotaxdocumentnumber());
					}
					else {
						dotaxdocument.setNumber(adddata.getDotaxdocumentnumber());
						dotaxdocument.setAmount(doMap.get(adddata.getDonumber()).getTotalreceived());
						dotaxdocument.setCurrentstatetypeid(tdstProcessingDocument.getId());
						dotaxdocument.setCurrentstatetypedate(now);
						dotaxdocument.setDotaxdocumentticketid(documentTicket.getId());
						dotaxdocument.setEmitted(null);
						dotaxdocument.setPdfurl(null);
										
						dotaxdocument = dotaxdocumentserver.updateDOTaxDocument(dotaxdocument);
						
						dotaxdocumentstate.setComment("Actualización manual � N°mero " + adddata.getDotaxdocumentnumber());
					}
						
					dotaxdocumentstate.setDotaxdocumentid(dotaxdocument.getId());
					dotaxdocumentstate.setDotaxdocumentstatetypeid(tdstProcessingDocument.getId());
					dotaxdocumentstate.setWhen(now);
					dotaxdocumentstate.setWho(initParamDTO.getUsername());
					dotaxdocumentstate.setUsertype(initParamDTO.getUsetype());
					dotaxdocumentstate.setTicketnumber(documentTicket.getTicketnumber());
					dotaxdocumentstate = dotaxdocumentstateserver.addDOTaxDocumentState(dotaxdocumentstate);
				}
				
				resultDTO.setTicketnumber(documentTicket.getTicketnumber());
			}
			else {
				// Crear las facturas
				for (DOTaxDocumentAddDataDTO adddata : initParamDTO.getAdddata()) {					
					now = new Date();
					
					dotaxdocument = dtdMap.get(adddata.getDonumber() + "_" + adddata.getDotaxdocumentnumber());
					if (dotaxdocument == null) {
						dotaxdocument = new DOTaxDocumentW();
						dotaxdocument.setNumber(adddata.getDotaxdocumentnumber());
						dotaxdocument.setAmount(doMap.get(adddata.getDonumber()).getTotalreceived());
						dotaxdocument.setCurrentstatetypeid(null); // se setea tras la validación
						dotaxdocument.setCurrentstatetypedate(null);
						dotaxdocument.setVendorid(vendor.getId());
						dotaxdocument.setDirectorderid(doMap.get(adddata.getDonumber()).getId());
						dotaxdocument.setDotaxdocumentticketid(null);
						dotaxdocument.setEmitted(null);
						dotaxdocument.setPdfurl(null);
												
						dtdMap.put(adddata.getDonumber() + "_" + adddata.getDotaxdocumentnumber(), dotaxdocument);
					}
					else {
						dotaxdocument.setNumber(adddata.getDotaxdocumentnumber());
						dotaxdocument.setAmount(doMap.get(adddata.getDonumber()).getTotalreceived());
						dotaxdocument.setDotaxdocumentticketid(null);
						dotaxdocument.setEmitted(null);
						dotaxdocument.setPdfurl(null);
					}
				}
				
				// Validar contra sistema Paperless (PPL) la información ingresada por el usuario
				try {
					resultDTO = doPaperlessValidationWS(dtdMap, doMap, initParamDTO.getUsername(), initParamDTO.getUsetype());
					
				} catch (Exception e) {
					e.printStackTrace();
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4505");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public DOTaxDocumentAddResultDTO doPaperlessValidationWS(HashMap<String, DOTaxDocumentW> dtdMap, HashMap<Long, DirectOrderW> doMap, String username, String usertype) throws NotFoundException, OperationFailedException, AccessDeniedException {
				
		DOTaxDocumentAddResultDTO resultDTO = new DOTaxDocumentAddResultDTO();

		List<DOTaxDocumentResultBean> faileddeliveries = new ArrayList<DOTaxDocumentResultBean>();
		List<DOTaxDocumentResultBean> successdeliveries = new ArrayList<DOTaxDocumentResultBean>();
		List<DOTaxDocumentResultBean> validatingtaxdocuments = new ArrayList<DOTaxDocumentResultBean>();
		
		// Obtener el proveedor
		VendorW vendor = vendorserver.getById(doMap.values().iterator().next().getVendorid());
		String vendorRUT = vendor.getRut() == null ? "0" : vendor.getRut();
		
		// JPE 20181213
		// Obtener el proveedor rollout para 'DIAS_FACT_VEVPD'
		PropertyVendorW[] pvInvoiceMaxPreviousDays = null;
		PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
		PropertyInfoDTO prop2 = new PropertyInfoDTO("code", "code", "DIAS_FACT_VEVPD");
		pvInvoiceMaxPreviousDays = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
		int invoiceMaxPreviousDays = Integer.valueOf(pvInvoiceMaxPreviousDays[0].getValue());
				
		// Obtener el estado de document tributario 'En Validación DTE' y 'Facturada sin RM'
		DOTaxDocumentStateTypeW dotdstValidating = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "DTE_VAL");
		DOTaxDocumentStateTypeW dotdstWRMInvoiced = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "W_RM_INV");
		
		// Obtener las credenciales para la conexión
		String pplUser = B2BSystemPropertiesUtil.getProperty("paperlessuser").trim();
		String pplPassword = B2BSystemPropertiesUtil.getProperty("paperlesspassword").trim();
		
		// Contruir el request general para el WS de PPL
		Integer arg0 = 81201000; 					// RUT de Paris, sin dígito verificador
		String arg1 = pplUser; 						// Usuario para conectarse al WS
		String arg2 = pplPassword; 					// Password para conectarse al WS
		Integer arg3 = Integer.valueOf(vendorRUT); 	// RUT del proveedor (sin dígito verificador)
		Integer arg4 = 33;							// Tipo de documento Electr�nico seg�n el SII
		Long arg5 = null;							// Folio del documento
		Integer arg6 = null;						// Tipo de devolución de respuesta
		
		String proxyIP = RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP();
		int proxyPort = RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT();
		
		DOTaxDocumentValidationResultDTO validation = null;
		DirectOrderW directorder = null;
		DOTaxDocumentW dotaxdocument = null;
		JAXBContext jc = null;
		Unmarshaller u = null;
		Source source = null;
		DTEDefType dteParser = null;
		XMLGregorianCalendar emittedCal = null;
		List<Referencia> referenceList = null;
		Calendar cal = null;
		Date emitted = null;
		Date emittedMin = null;
		Double netamount = null;
		Long dotdstid = null;
		Date now = null;
		JAXBElement<DTEDefType> dte = null;
		Proxy proxy = null;
		HttpURLConnection connection = null;
		XMLInputFactory xmlInputFactory = null;
		XMLStreamReader xmlStreamReader = null;
		InputStream inputStream = null;
		String errorStr = "";
		String key = "";
		boolean completeProcessing = true;
		for (Map.Entry<String, DOTaxDocumentW> e : dtdMap.entrySet()) {
			key = e.getKey();
						
			dotaxdocument = e.getValue();
			arg5 = dotaxdocument.getNumber();
			
			// Obtener la orden asociada
			directorder = doMap.get(Long.parseLong(key.split("_")[0]));
			
			now = new Date();
			
			// Obtener ruta del xml
			arg6 = 1;
			validation = paperlessDOTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, directorder.getNumber());
			
			if (validation.getStatuscode().equals("0")) {
				// Obtener el xml
				try {
					jc = getDTEJC();
					u = jc.createUnmarshaller();
					
					xmlInputFactory = XMLInputFactory.newInstance();
					xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // NAMESPACE OPTIONAL
					
					if (RegionalLogisticConstants.getInstance().get_PROXY()) {
						proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));
					    connection = (HttpURLConnection) new URL(validation.getMessage()).openConnection(proxy);
					    inputStream = connection.getInputStream();
					    xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
					    dte = (JAXBElement<DTEDefType>) u.unmarshal(xmlStreamReader);
					}
					else {
						source = new StreamSource(validation.getMessage());
						xmlStreamReader = xmlInputFactory.createXMLStreamReader(source);
						dte = u.unmarshal(xmlStreamReader, DTEDefType.class);
					}
										
					dteParser = dte.getValue();					
										
					// Validar que la fecha de emisión del documento sea menor o igual a HOY - X d�as
					cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					cal.add(Calendar.DAY_OF_YEAR, -invoiceMaxPreviousDays); // JPE 20181213
					emittedMin = cal.getTime();
					
					emittedCal = dteParser.getDocumento().getEncabezado().getIdDoc().getFchEmis();
					cal = emittedCal.toGregorianCalendar();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					emitted = cal.getTime();

					if (emitted.before(emittedMin)) {
						errorStr = "La fecha de emisión del documento no debe ser anterior a " + invoiceMaxPreviousDays + " días con respecto a hoy";
						logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directorder.getNumber());
						faileddeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), errorStr));
					}
					else {
						// JPE 20191003
						// Determinar si el monto recibido de la OC es cero
						Double directordertotal = directorder.getTotalreceived();
						if (directordertotal.equals(0.0)) {
							// Si el proveedor tiene Courier contratado corresponde a un despacho en ruta y se valida contra el monto solicitado
							HiredCourierW[] hiredcouriers = hiredcourierserver.getByPropertyAsArray("vendor.id", directorder.getVendorid());
							if (hiredcouriers != null && hiredcouriers.length > 0) {
								directordertotal = directorder.getTotalneed();
							}			
						}
						
						// Validar que el monto neto facturado posea una tolerancia con respecto el monto total de la OC
						netamount = (double)dteParser.getDocumento().getEncabezado().getTotales().getMntNeto().longValue();
						if (Math.abs(directordertotal - netamount) > Double.parseDouble(B2BSystemPropertiesUtil.getProperty("invoicetolerance").trim())) {
							errorStr = "El monto neto facturado no coincide con lo recibido en la OC";
							logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directorder.getNumber());
							faileddeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), errorStr));
						}
						else {
							// Guardar la fecha de emisión
							dotaxdocument.setEmitted(emitted);
							
							// Validar que la factura contiene como dato de referencia el N°mero de la OC a la cual se est� asociando el folio
							referenceList = dteParser.getDocumento().getReferencia();
							dotdstid = dotdstValidating.getId();
							for (Referencia reference : referenceList) {
								if (reference.getTpoDocRef().equals("801")) {
									if (reference.getFolioRef().equals(directorder.getNumber().toString())) {
										dotdstid = dotdstWRMInvoiced.getId();
									}
									break;
								}
							}
							
							// Obtener ruta del pdf
							arg6 = 2;
							validation = paperlessDOTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, directorder.getNumber());
							
							if (validation.getStatuscode().equals("0")) {
								// Guardar la url del PDF
								dotaxdocument.setPdfurl(validation.getMessage());
								
								// Actualizar la factura a estado 'En Validación DTE' o 'Facturada sin RM'
								dotaxdocument.setCurrentstatetypeid(dotdstid);
								dotaxdocument.setCurrentstatetypedate(now);
								
								successdeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), ""));
								
								if (dotdstid == dotdstValidating.getId()) {
									validatingtaxdocuments.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), ""));
								}
							}
							else {
								errorStr = validation.getStatusmessage();
								faileddeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), errorStr));
								if (!validation.getStatuscode().equals("L4505")) {
									completeProcessing = false;
								}
							}							
						}
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
					errorStr = "Error parseando el xml: " + validation.getMessage();
					logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directorder.getNumber());
					faileddeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), errorStr));
				}
			}
			else {
				errorStr = validation.getStatusmessage();
				faileddeliveries.add(new DOTaxDocumentResultBean(arg5, directorder.getNumber(), errorStr));
				if (!validation.getStatuscode().equals("L4505")) {
					completeProcessing = false;
				}
			}
		}

		// Obtener los resultados del procesamiento de cada despacho
		resultDTO.setFaileddeliveries((DOTaxDocumentResultBean[]) faileddeliveries.toArray(new DOTaxDocumentResultBean[faileddeliveries.size()]));
		resultDTO.setSuccessdeliveries((DOTaxDocumentResultBean[]) successdeliveries.toArray(new DOTaxDocumentResultBean[successdeliveries.size()]));
		resultDTO.setValidatingtaxdocuments((DOTaxDocumentResultBean[]) validatingtaxdocuments.toArray(new DOTaxDocumentResultBean[validatingtaxdocuments.size()]));
		resultDTO.setCompleteprocessing(completeProcessing);
		
		if (!completeProcessing) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4506");
		}
		
		// JPE 20180921
		// Almacenar/actualizar las facturas y su estado si la validación fue exitosa
		DOTaxDocumentStateW dotaxdocumentstate = null;
		for (DOTaxDocumentResultBean successdelivery : successdeliveries) {					
			dotaxdocumentstate = new DOTaxDocumentStateW();
			dotaxdocument = dtdMap.get(successdelivery.getDirectordernumber() + "_" + successdelivery.getDotaxdocumentnumber());
			if (dotaxdocument.getId() == null) {
				dotaxdocument = dotaxdocumentserver.addDOTaxDocument(dotaxdocument);
				
				dotaxdocumentstate.setComment("Carga manual � N°mero " + successdelivery.getDotaxdocumentnumber());
			}
			else {
				dotaxdocument = dotaxdocumentserver.updateDOTaxDocument(dotaxdocument);
				
				dotaxdocumentstate.setComment("Actualización manual � N°mero " + successdelivery.getDotaxdocumentnumber());
			}
			
			now = new Date();
			
			dotaxdocumentstate.setDotaxdocumentid(dotaxdocument.getId());
			dotaxdocumentstate.setDotaxdocumentstatetypeid(dotaxdocument.getCurrentstatetypeid());
			dotaxdocumentstate.setWhen(dotaxdocument.getCurrentstatetypedate());
			dotaxdocumentstate.setWho(username);
			dotaxdocumentstate.setUsertype(usertype);
			dotaxdocumentstate.setTicketnumber(resultDTO.getTicketnumber());
			dotaxdocumentstate = dotaxdocumentstateserver.addDOTaxDocumentState(dotaxdocumentstate);			
		}
		
		return resultDTO;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doPaperlessValidationWSByCron(DOTaxDocumentW dotaxdocument, DirectOrderW directOrder, String vendorRUT, int invoiceMaxPreviousDays, Long ticketnumber, HashMap<String, DOTaxDocumentStateTypeW> dotdstMap) {
				
		boolean completeProcessing = true;

		try {
			Date now = new Date();
			
			// Obtener las credenciales para la conexión
			String pplUser = B2BSystemPropertiesUtil.getProperty("paperlessuser").trim();
			String pplPassword = B2BSystemPropertiesUtil.getProperty("paperlesspassword").trim();
			
			// Contruir el request general para el WS de PPL
			Integer arg0 = 81201000; 					// RUT de Paris, sin dígito verificador
			String arg1 = pplUser; 						// Usuario para conectarse al WS
			String arg2 = pplPassword; 					// Password para conectarse al WS
			Integer arg3 = Integer.valueOf(vendorRUT); 	// RUT del proveedor (sin dígito verificador)
			Integer arg4 = 33;							// Tipo de documento Electr�nico seg�n el SII
			Long arg5 = dotaxdocument.getNumber();		// Folio del documento
			Integer arg6 = 1;							// Tipo de devolución de respuesta
						
			// Obtener ruta del xml
			DOTaxDocumentValidationResultDTO validation = paperlessDOTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, directOrder.getNumber());
			
			DOTaxDocumentStateW dotaxdocumentstate = null;
			String errorStr = "";
			boolean error = false;
			if (validation.getStatuscode().equals("0")) {
				// Obtener el xml
				try {
					JAXBContext jc = getDTEJC();
					Unmarshaller u = jc.createUnmarshaller();
					
					XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
					xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // NAMESPACE OPTIONAL
					
					JAXBElement<DTEDefType> dte = null;
					XMLStreamReader xmlStreamReader = null;
					if (RegionalLogisticConstants.getInstance().get_PROXY()) {
						String proxyIP = RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP();
						int proxyPort = RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT();
						
						Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));
						HttpURLConnection connection = (HttpURLConnection) new URL(validation.getMessage()).openConnection(proxy);
						InputStream inputStream = connection.getInputStream();
					    xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);
					    dte = (JAXBElement<DTEDefType>) u.unmarshal(xmlStreamReader);
					}
					else {
						Source source = new StreamSource(validation.getMessage());
						xmlStreamReader = xmlInputFactory.createXMLStreamReader(source);
						dte = u.unmarshal(xmlStreamReader, DTEDefType.class);
					}
										
					DTEDefType dteParser = dte.getValue();					
										
					// Validar que la fecha de emisión del documento sea menor o igual a HOY - X d�as
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					cal.add(Calendar.DAY_OF_YEAR, -invoiceMaxPreviousDays); // JPE 20181213
					Date emittedMax = cal.getTime();
					
					XMLGregorianCalendar emittedCal = dteParser.getDocumento().getEncabezado().getIdDoc().getFchEmis();
					cal = emittedCal.toGregorianCalendar();
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					Date emitted = cal.getTime();

					if (emitted.before(emittedMax)) {
						errorStr = "La fecha de emisión del documento no debe ser anterior a " + invoiceMaxPreviousDays + " días con respecto a hoy";
						logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directOrder.getNumber());
						error = true;
					}
					else {
						// JPE 20191003
						// Determinar si el monto recibido de la OC es cero
						Double directordertotal = directOrder.getTotalreceived();
						if (directordertotal.equals(0.0)) {
							// Si el proveedor tiene Courier contratado corresponde a un despacho en ruta y se valida contra el monto solicitado
							HiredCourierW[] hiredcouriers = hiredcourierserver.getByPropertyAsArray("vendor.id", directOrder.getVendorid());
							if (hiredcouriers != null && hiredcouriers.length > 0) {
								directordertotal = directOrder.getTotalneed();
							}			
						}
																
						// Validar que el monto neto facturado posea una tolerancia con respecto el monto total recibido de la OC
						Double netamount = (double)dteParser.getDocumento().getEncabezado().getTotales().getMntNeto().longValue();
						if (Math.abs(directordertotal - netamount) > Double.parseDouble(B2BSystemPropertiesUtil.getProperty("invoicetolerance").trim())) {
							errorStr = "El monto neto facturado no coincide con lo recibido en la OC";
							logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directOrder.getNumber());
							error = true;
						}
						else {
							// Guardar la fecha de emisión
							dotaxdocument.setEmitted(emitted);
							
							dotaxdocument = dotaxdocumentserver.updateDOTaxDocument(dotaxdocument);
														
							// Validar que la factura contiene como dato de referencia el N°mero de la OC a la cual se est� asociando el folio
							List<Referencia> referenceList = dteParser.getDocumento().getReferencia();
							Long dotdstid = dotdstMap.get("DTE_VAL").getId();
							for (Referencia reference : referenceList) {
								if (reference.getTpoDocRef().equals("801")) {
									if (reference.getFolioRef().equals(directOrder.getNumber().toString())) {
										dotdstid = dotdstMap.get("W_RM_INV").getId();
									}
									break;
								}
							}
							
							// Obtener ruta del pdf
							arg6 = 2;
							validation = paperlessDOTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, directOrder.getNumber());
							
							if (validation.getStatuscode().equals("0")) {
								// Guardar la url del PDF
								dotaxdocument.setPdfurl(validation.getMessage());
								
								// Actualizar la factura a estado 'En Validación DTE' o 'Facturada sin RM'
								dotaxdocument.setCurrentstatetypeid(dotdstid);
								dotaxdocument.setCurrentstatetypedate(now);
								
								dotaxdocument = dotaxdocumentserver.updateDOTaxDocument(dotaxdocument);
								
								// Agrega un registro de historial de la factura
								dotaxdocumentstate = new DOTaxDocumentStateW();
								dotaxdocumentstate.setDotaxdocumentid(dotaxdocument.getId());
								dotaxdocumentstate.setDotaxdocumentstatetypeid(dotdstid);
								dotaxdocumentstate.setWhen(now);
								dotaxdocumentstate.setTicketnumber(ticketnumber);
								dotaxdocumentstate.setWho("Proceso autom�tico B2B");
								dotaxdocumentstate.setUsertype("Sistema");
								dotaxdocumentstate.setComment("Factura validada en PPL");
								
								dotaxdocumentstateserver.addDOTaxDocumentState(dotaxdocumentstate);
							}
							else {
								errorStr = validation.getStatusmessage();
								if (validation.getStatuscode().equals("L4505")) {
									error = true;
								}
								else {
									completeProcessing = false;
								}
							}							
						}
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
					errorStr = "Error parseando el xml: " + validation.getMessage();
					logger.error(errorStr + " Factura: " + arg5 + " asociada a la orden directa " + directOrder.getNumber());
					error = true;
				}
			}
			else {
				errorStr = validation.getStatusmessage();
				if (validation.getStatuscode().equals("L4505")) {
					error = true;
				}
				else {
					completeProcessing = false;
				}
			}
			
			if (error) {
				dotaxdocument.setCurrentstatetypeid(dotdstMap.get("DOC_FAL").getId());
				dotaxdocument.setCurrentstatetypedate(now);
				
				dotaxdocument = dotaxdocumentserver.updateDOTaxDocument(dotaxdocument);
				
				// Agrega un registro de historial de la factura
				dotaxdocumentstate = new DOTaxDocumentStateW();
				dotaxdocumentstate.setDotaxdocumentid(dotaxdocument.getId());
				dotaxdocumentstate.setDotaxdocumentstatetypeid(dotaxdocument.getCurrentstatetypeid());
				dotaxdocumentstate.setWhen(now);
				dotaxdocumentstate.setTicketnumber(ticketnumber);
				dotaxdocumentstate.setWho("Proceso autom�tico B2B");
				dotaxdocumentstate.setUsertype("Sistema");
				dotaxdocumentstate.setComment("Error de validación en PPL: " + errorStr);
				
				dotaxdocumentstateserver.addDOTaxDocumentState(dotaxdocumentstate);
			}
			
		} catch (Exception e) {
			completeProcessing = false;
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
		}

		return completeProcessing;
	}
	
	private DOTaxDocumentValidationResultDTO paperlessDOTaxDocumentValidationWS(Integer arg0, String arg1, String arg2, Integer arg3, Integer arg4, Long arg5, Integer arg6, Long directordernumber) {
		
		DOTaxDocumentValidationResultDTO resultDTO = new DOTaxDocumentValidationResultDTO();
		
		String error = "";
		try {
			// Obtener la conexión al ws de PPL
			String endpointurl = B2BSystemPropertiesUtil.getProperty("wsdl_paperless");
			OnlinePortType service = new OnlineClient().getOnlineHttpSoap11Endpoint(endpointurl);
			
	        Client cl = Client.getInstance(service);
	        cl.addInHandler(new DOMInHandler());
			cl.addInHandler(new OnlineRecoveryRecHandler());
			cl.addOutHandler(new DOMOutHandler());
			cl.addOutHandler(new OnlineRecoveryRecHandler());
			
    		if (RegionalLogisticConstants.getInstance().get_PROXY()) {
	    		cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_HOST, RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP());
				cl.setProperty(CommonsHttpMessageSender.HTTP_PROXY_PORT, RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT() + "");
				cl.setProperty(CommonsHttpMessageSender.DISABLE_PROXY_UTILS, true);
    		}
    		
			// Ejecutar la llamada al servicio
			String response = service.onlineRecoveryRec(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
			
			// Obtener el código de respuesta
			String[] strArray1 = response.split("<Codigo>");
			String[] strArray2 = null;
			String code = "";
			if (strArray1.length <= 1) {
				code = "";
			}
			else {
				strArray2 = strArray1[1].split("</Codigo>");
				code = strArray2[0].trim();
			}
			
			// Obtener el mensaje de respuesta
			strArray1 = response.split("<Mensaje>");
			String message = "";
			if (strArray1.length <= 1) {
				message = "";
			}
			else {
				strArray2 = strArray1[1].split("</Mensaje>");
				message = strArray2[0].trim();
			}				
			
			// Validar si la respuesta del servicio fall�
			if (!code.equals("0")) {
				error = "Error de servicio: " + message;
				logger.error(error + " Factura: " + arg5 + " asociada a la orden directa " + directordernumber);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4505", error, false);
			}
			
			resultDTO.setMessage(message);
			logger.info("Ejecutada en Paperless la validación de la factura " + arg5 + ", asociada a la orden directa " + directordernumber);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			error = "Ha ocurrido un error accediendo al servicio de Paperless";
			logger.error(error + " Factura: " + arg5 + " asociada a la orden directa " + directordernumber);
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4506", error, false);
		}

		return resultDTO;		
	}
	
	public DOVirtualReceptionReportResultDTO getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndFilter(DOVirtualReceptionReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated){
		
		DOVirtualReceptionReportResultDTO resultDTO = new DOVirtualReceptionReportResultDTO();
			
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1")) {
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
			vendorid = vendors[0].getId();
		}
		

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = null;
			Date maxCourierStateDate = null;
			Date since = null;
			Date until = null;
			switch(initParamDTO.getFiltertype()) {
			case 3:
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 6:
				Locale locale = new Locale("es", "CL");
				cal = Calendar.getInstance(locale);

				since = Date.from(initParamDTO.getSince().atZone(ZoneId.systemDefault()).toInstant());
				cal.setTime(since);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				since = cal.getTime();

				until = Date.from(initParamDTO.getUntil().atZone(ZoneId.systemDefault()).toInstant());
				
				cal.setTime(until);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				until = cal.getTime();
				
				// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
				if(!until.after(since)){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
				}
				break;
			case 4:
				maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			case 5:
				// Validar que el N°mero sea mayor que cero y con un m�ximo de 10 dígitos
				if(initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
				}
				break;
			case 0:
			case 1:
			case 2:
			default:
				break;
			}
			
			DOVirtualReceptionReportDataDTO[] virtualreceptionreport = null;
			int total = 0;
			switch (initParamDTO.getFiltertype()) {
			case 1:
				// FACTURAS SIN RM
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid());
				}
				break;
			case 2:
				// ESTADO DE FACTURACION
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getTaxdocumentstatetypeid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getTaxdocumentstatetypeid());
				}
				break;				
			case 3:
				// FECHA ENTREGA OC
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, since, until);
				}
				break;
			case 4:
				// N° ORDEN DE COMPRA
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, initParamDTO.getNumber());
				}
				break;
			case 5:
				// N° DOCUMENTO
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getNumber());
				}
				break;				
			case 6:
				// FECHA EMISION DOCUMENTO
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), since, until);
				}
				break;
			case 0:
			default:
				// FACTURAS POR VALIDAR
				virtualreceptionreport = dotaxdocumentserver.getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid());
				}
				break;
			}

			if (byFilter) {
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(virtualreceptionreport.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setVirtualreceptionreport(virtualreceptionreport);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;				
	}
	
	public DOVirtualReceptionDetailResultDTO getDOVirtualReceptionDetailByOrder(DOVirtualReceptionDetailInitParamDTO initParamDTO){
		
		DOVirtualReceptionDetailResultDTO resultDTO = new DOVirtualReceptionDetailResultDTO();
		
		try{
			ClientW clientW = clientserver.getByPropertyAsSingleResult("id", initParamDTO.getClientid());
			ClientDTO client = new ClientDTO();
			BeanExtenderFactory.copyProperties(clientW, client);
			
			DOVirtualReceptionDetailDataDTO[] details = dotaxdocumentserver.getDOVirtualReceptionDetailByOrder(initParamDTO.getDoid());
			
			resultDTO.setClient(client);
			resultDTO.setDetails(details);	
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
		
	}
	
	public DOVirtualReceptionReportResultDTO getDOVirtualReceptionReportByDirectOrders(DOVirtualReceptionDownloadInitParamDTO initParamDTO, boolean byPages) {
		
		DOVirtualReceptionReportResultDTO resultDTO = new DOVirtualReceptionReportResultDTO();

		try {			
			// Proveedor
			Long vendorid = -1L;
			if(! initParamDTO.getVendorcode().equals("-1")) {
				VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				if(vendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");
				}
				vendorid = vendorArr[0].getId();
			}
			
			if (!byPages) {
				int total = dotaxdocumentserver.countDOVirtualReceptionReportByDirectOrders(vendorid, initParamDTO.getDirectorderids());
				if (total > RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
				}
			}
									
			DOVirtualReceptionReportDataDTO[] dovirtualreceptions = dotaxdocumentserver.getDOVirtualReceptionReportByDirectOrders(vendorid, initParamDTO.getDirectorderids(), initParamDTO.getOrderby());

			resultDTO.setVirtualreceptionreport(dovirtualreceptions);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DirectOrderIdsByPagesDTO getDOVirtualReceptionReportByPages(DOVirtualReceptionReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO) {
		
		DirectOrderIdsByPagesDTO resultDTO = new DirectOrderIdsByPagesDTO();

		try {
			
			// Proveedor
			Long vendorid = -1L;
			if(! initParamDTO.getVendorcode().equals("-1")) {
				VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				if(vendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");
				}
				vendorid = vendorArr[0].getId();
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = null;
			Date maxCourierStateDate = null;
			Date since = null;
			Date until = null;
			switch(initParamDTO.getFiltertype()) {
				case 3:
					maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
				case 6:
					Locale locale = new Locale("es", "CL");
					cal = Calendar.getInstance(locale);
	
					since = Date.from(initParamDTO.getSince().atZone(ZoneId.systemDefault()).toInstant());
					cal.setTime(since);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					since = cal.getTime();
	
					until = Date.from(initParamDTO.getUntil().atZone(ZoneId.systemDefault()).toInstant());
					cal.setTime(until);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					until = cal.getTime();
					
					// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
					if(!until.after(since)){
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
					}
					break;
				case 4:
					maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
				case 5:
					// Validar que el N°mero sea mayor que cero y con un m�ximo de 10 dígitos
					if(initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L){
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
					}
					break;
				case 0:
				case 1:
				case 2:
				default:
					break;
			}
			
			// Validación del número de registros
			int total = 0;
			switch (initParamDTO.getFiltertype()) {
				case 0:
					// FACTURAS POR VALIDAR
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedInDTEAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid());
					break;				
				case 1:
					// FACTURAS SIN RM
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid());
					break;
				case 2:
					// ESTADO DE FACTURACION
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getTaxdocumentstatetypeid());
					break;				
				case 3:
					// FECHA ENTREGA OC
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentDeliveryDateAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, since, until);
					break;				
				case 4:
					// N° ORDEN DE COMPRA
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentOrderNumberAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, initParamDTO.getNumber());
					break;				
				case 5:
					// N° DOCUMENTO
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getNumber());
					break;				
				case 6:
					// FECHA EMISION DOCUMENTO
					total = dotaxdocumentserver.countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), since, until);
					break;
				default:
			}
			
			int totalPages = (total % initParamDTO.getRows()) != 0 ? (total / initParamDTO.getRows()) + 1 : (total / initParamDTO.getRows());
			int lastPageRows = (total % initParamDTO.getRows()) != 0 ? total % initParamDTO.getRows() : initParamDTO.getRows();
			int requestedRows = 0;
			for (PageRangeDTO pageRange : pageRangesDTO) {
				if (pageRange.getSincePage() > pageRange.getUntilPage() || pageRange.getSincePage() > totalPages) {
					continue;
				}
				
				if (pageRange.getUntilPage() < totalPages) {
					requestedRows += (pageRange.getUntilPage() - pageRange.getSincePage() + 1) * initParamDTO.getRows();
				}
				else { // la última página puede tener distinto número de filas
					requestedRows += (totalPages - pageRange.getSincePage()) * initParamDTO.getRows();
					requestedRows += lastPageRows;
				}
			}
						
			if (requestedRows > RegionalLogisticConstants.getInstance().getMAX_NUMBER_OF_ROWS()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2102");
			}
			
			Long[] directorderids = null;
			switch (initParamDTO.getFiltertype()) {
				case 0:
					// FACTURAS POR VALIDAR
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedInDTEAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;				
				case 1:
					// FACTURAS SIN RM
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;
				case 2:
					// ESTADO DE FACTURACION
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getTaxdocumentstatetypeid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;				
				case 3:
					// FECHA ENTREGA OC
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentDeliveryDateAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;				
				case 4:
					// N° ORDEN DE COMPRA
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentOrderNumberAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), maxCourierStateDate, initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;				
				case 5:
					// N° DOCUMENTO
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), initParamDTO.getNumber(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;				
				case 6:
					// FECHA EMISION DOCUMENTO
					directorderids = dotaxdocumentserver.getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(vendorid, initParamDTO.getSalestoreids(), initParamDTO.getDepartmentid(), since, until, initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
					break;
				default:
			}
			
			resultDTO.setDirectorderids(directorderids);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DOVirtualReceptionCSVReportResultDTO getCSVDOVirtualReceptionReportByOrderIds(DOVirtualReceptionCSVReportInitParamDTO initParamDTO){
		
		DOVirtualReceptionCSVReportResultDTO resultDTO = new DOVirtualReceptionCSVReportResultDTO();
		
		try{
			if(initParamDTO.getDoids() != null){
				DOVirtualReceptionCSVReportDataDTO[] virtualreceptionreport = dotaxdocumentserver.getCSVDOVirtualReceptionReportByOrderIds(initParamDTO.getDoids());
				
				resultDTO.setVirtualreceptionreport(virtualreceptionreport);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;		
	}
	
	public DOInvoicePendingReportResultDTO getDOInvoicePendingReport(DOInvoicePendingCSVReportInitParamDTO initParamDTO) {
		DOInvoicePendingReportResultDTO resultDTO = new DOInvoicePendingReportResultDTO();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date maxCourierStateDate = sdf.parse(B2BSystemPropertiesUtil.getProperty("maxcourierreceiveddate").trim());
			
			// Validar que la fecha 'Desde' sea anterior o igual a la 'Hasta'
			Calendar calSince = Calendar.getInstance();
			calSince.setTime(DateConverterFactory2.StrToDate(initParamDTO.getSince()));
			calSince.set(Calendar.HOUR_OF_DAY, 0);
			calSince.set(Calendar.MINUTE, 0);
			calSince.set(Calendar.SECOND, 0);
			calSince.set(Calendar.MILLISECOND, 0);
			Date since = calSince.getTime();
			
			Calendar calUntil = Calendar.getInstance();
			calUntil.setTime(DateConverterFactory2.StrToDate(initParamDTO.getUntil()));
			calUntil.add(Calendar.DAY_OF_YEAR, 1);
			calUntil.set(Calendar.HOUR_OF_DAY, 0);
			calUntil.set(Calendar.MINUTE, 0);
			calUntil.set(Calendar.SECOND, 0);
			calUntil.set(Calendar.MILLISECOND, 0);
			Date until = calUntil.getTime();
												
			if (!until.after(since)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
			}
			
			// Validar que el rango seleccionado sea de a lo más 1 a�o
			if (calSince.get(Calendar.YEAR) < calUntil.get(Calendar.YEAR) && 
					calSince.get(Calendar.DAY_OF_YEAR) < calUntil.get(Calendar.DAY_OF_YEAR)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4514");
			}
			
			// Validar que la cantidad de OC no supere el valor parametrizado para este tipo de descarga
			if (dotaxdocumentserver.countDOInvoicePendingByDate(maxCourierStateDate, since, until) > RegionalLogisticConstants.getInstance().getMAX_ROWS_INVOICE_PENDING_REPORT()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2104");
			}
			
			DOInvoicePendingDTO[] invoicepending = dotaxdocumentserver.getDOInvoicePendingByDate(maxCourierStateDate, since, until);
							
			resultDTO.setInvoicepending(invoicepending);
								
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public BaseResultDTO doChangeDOTaxDocumentsStateToConfirmedInvoice(DOVirtualReceptionCSVReportInitParamDTO initParamDTO){
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try{
			if(initParamDTO.getDoids() != null){
				DOTaxDocumentDTO[] dotaxdocuments = dotaxdocumentserver.getDOTaxDocumentsByOrderIdsAndWithoutReception(initParamDTO.getDoids());
				
				// Si existe alguna factura en estado 'Facturada sin RM' se actualiza
				if(dotaxdocuments != null && dotaxdocuments.length > 0){
					// Obtener el tipo de estado de factura 'Factura Confirmada'
					DOTaxDocumentStateTypeW tdstConfirmedInvoice = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "C_INV");
					
					Date now = null;
					DOTaxDocumentStateW dotaxdocumentstate = null;
					for(DOTaxDocumentDTO dotaxdocument : dotaxdocuments){
						now = new Date();
						
						DOTaxDocumentW dotaxdocumentW = dotaxdocumentserver.getById(dotaxdocument.getId());
						dotaxdocumentW.setCurrentstatetypeid(tdstConfirmedInvoice.getId());
						dotaxdocumentW.setCurrentstatetypedate(now);
						
						dotaxdocumentW = dotaxdocumentserver.updateDOTaxDocument(dotaxdocumentW);
						
						dotaxdocumentstate = new DOTaxDocumentStateW();
						dotaxdocumentstate.setDotaxdocumentid(dotaxdocument.getId());
						dotaxdocumentstate.setDotaxdocumentstatetypeid(tdstConfirmedInvoice.getId());
						dotaxdocumentstate.setWhen(now);
						dotaxdocumentstate.setWho(initParamDTO.getUsername());
						dotaxdocumentstate.setUsertype(initParamDTO.getUsertype());
						dotaxdocumentstate.setComment("Descarga manual desde B2B");
						
						dotaxdocumentstate = dotaxdocumentstateserver.addDOTaxDocumentState(dotaxdocumentstate);
					}
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
		
	}
	
	public BaseResultDTO doEvaluateDocument(DOTaxDocumentEvaluationInitParam evaluation){
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			DOTaxDocumentStateTypeW state = null;
			if (evaluation.getEvaluation()) {
				state = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "W_RM_INV");
			}
			else {
				state = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "REC_VEV");
			}
			
			Date now = null;
			
			DOTaxDocumentDTO[] documents = dotaxdocumentserver.getDOTaxDocumentsById(evaluation.getDocumentsId());
			for(DOTaxDocumentDTO document : documents){
				now = new Date();
				now = new Date();
				
				DOTaxDocumentW documentW = new DOTaxDocumentW();
				documentW.setAmount(document.getAmount());
				documentW.setDirectorderid(document.getDirectorderid());
				documentW.setDotaxdocumentticketid(document.getDotaxdocumentticketid());
				documentW.setEmitted(Date.from(document.getEmitted().atZone(ZoneId.systemDefault()).toInstant()));
				documentW.setId(document.getId());
				documentW.setNumber(document.getNumber());
				documentW.setPdfurl(document.getPdfurl());
				documentW.setVendorid(document.getVendorid());
				
				documentW.setCurrentstatetypedate(now);
				documentW.setCurrentstatetypeid(state.getId());
				documentW = dotaxdocumentserver.updateDOTaxDocument(documentW);
				
				DOTaxDocumentStateW documentstate = new DOTaxDocumentStateW();
				documentstate.setComment("Carga manual en B2B");
				documentstate.setTicketnumber(null);
				documentstate.setDotaxdocumentid(document.getId());
				documentstate.setDotaxdocumentstatetypeid(state.getId());
				documentstate.setWhen(now);
				documentstate.setWho(evaluation.getUsername());
				documentstate.setUsertype(evaluation.getUsertype());
				dotaxdocumentstateserver.addDOTaxDocumentState(documentstate);
			}
				
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}
	
	public BaseResultDTO doEvaluateDocumentRM(DOTaxDocumentEvaluationInitParam evaluation){
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			DOTaxDocumentStateTypeW state = null;
			if (evaluation.getEvaluation()) {
				state = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "RM");
			}
			else {
				state = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "RM_REC");
			}
			
			Date now = null;
			
			DOTaxDocumentDTO[] documents = dotaxdocumentserver.getDOTaxDocumentsById(evaluation.getDocumentsId());
			for(DOTaxDocumentDTO document : documents){
				now = new Date();
				
				DOTaxDocumentW documentW = new DOTaxDocumentW();
				documentW.setAmount(document.getAmount());
				documentW.setDirectorderid(document.getDirectorderid());
				documentW.setDotaxdocumentticketid(document.getDotaxdocumentticketid());
				documentW.setEmitted(Date.from(document.getEmitted().atZone(ZoneId.systemDefault()).toInstant()));
				documentW.setId(document.getId());
				documentW.setNumber(document.getNumber());
				documentW.setPdfurl(document.getPdfurl());
				documentW.setVendorid(document.getVendorid());
				
				documentW.setCurrentstatetypedate(now);
				documentW.setCurrentstatetypeid(state.getId());
				documentW = dotaxdocumentserver.updateDOTaxDocument(documentW);
				
				DOTaxDocumentStateW documentstate = new DOTaxDocumentStateW();
				documentstate.setComment("Carga manual en B2B");
				documentstate.setTicketnumber(null);
				documentstate.setDotaxdocumentid(document.getId());
				documentstate.setDotaxdocumentstatetypeid(state.getId());
				documentstate.setWhen(now);
				documentstate.setWho(evaluation.getUsername());
				documentstate.setUsertype(evaluation.getUsertype());
				dotaxdocumentstateserver.addDOTaxDocumentState(documentstate);
			}
				
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}
	
	public DOTaxDocumentAddResultDTO doEvaluateDocumentUpload(DOTaxDocumentEvaluationUpdateInitParamDTO initParamDTO){
		
		DOTaxDocumentAddResultDTO resultDTO = new DOTaxDocumentAddResultDTO();
		
		try {
			Long[] dotaxdocumentnumbers = Arrays.stream(initParamDTO.getUpdatedata()).map(data -> data.getDotaxdocumentnumber()).toArray(Long[]::new);
 			DOTaxDocumentEvaluationValidationBean[] validations = dotaxdocumentserver.getDocumentsByNumbers(dotaxdocumentnumbers);
 			HashMap<String, DOTaxDocumentEvaluationValidationBean> validationMap = new HashMap<String, DOTaxDocumentEvaluationValidationBean>();
 			for (DOTaxDocumentEvaluationValidationBean validation : validations) {
 				validationMap.put(validation.getDotaxdocumentnumber() + "_" + validation.getDonumber(), validation);
 			}
			
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			String error = "";
			
			DOTaxDocumentEvaluationValidationBean validation;
			for (DOTaxDocumentEvaluationUpdateDataDTO data : initParamDTO.getUpdatedata()) {
				if (!validationMap.containsKey(data.getDotaxdocumentnumber() + "_" + data.getDonumber())) {
					error = "Fila " + data.getRow() + ": El número de factura " + data.getDotaxdocumentnumber() + " no está cargado para la orden " + data.getDonumber();
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				else {
					validation = validationMap.get(data.getDotaxdocumentnumber() + "_" + data.getDonumber());
					
					if (!validation.getState().equals("DTE_VAL")) {
						error = "Fila " + data.getRow() + ": La orden " + data.getDonumber() + " debe estar en estado de facturación 'En Validación DTE'";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000");
			}
			
			// Obtener los tipos de estado de factura 'Facturada sin RM' y 'Rechazado por área VeV'
			DOTaxDocumentStateTypeW dotdstInvoicedWithoutRM = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "W_RM_INV");
			DOTaxDocumentStateTypeW dotdstVeVAreaRejected = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "REC_VEV");
			
			Date now = new Date();
			
			DOTaxDocumentW[] documents;
			DOTaxDocumentW document;
			DOTaxDocumentStateTypeW state;
			PropertyInfoDTO prop1;
			PropertyInfoDTO prop2;
			for (DOTaxDocumentEvaluationUpdateDataDTO data : initParamDTO.getUpdatedata()) {
				prop1 = new PropertyInfoDTO("number", "number", data.getDotaxdocumentnumber());
				prop2 = new PropertyInfoDTO("directorder.number", "directordernumber", data.getDonumber());
				documents = dotaxdocumentserver.getByPropertiesAsArray(prop1, prop2);
							
				state = data.getApprove() ? dotdstInvoicedWithoutRM : dotdstVeVAreaRejected;
				
				document = documents[0];
				document.setCurrentstatetypeid(state.getId());
				document.setCurrentstatetypedate(now);
				document = dotaxdocumentserver.updateDOTaxDocument(document);
				
				DOTaxDocumentStateW documentstate = new DOTaxDocumentStateW();
				documentstate.setComment("Evaluación vía carga masiva (Excel)");
				documentstate.setTicketnumber(null);
				documentstate.setDotaxdocumentid(document.getId());
				documentstate.setDotaxdocumentstatetypeid(state.getId());
				documentstate.setWhen(now);
				documentstate.setWho(initParamDTO.getUsername());
				documentstate.setUsertype(initParamDTO.getUsertype());
				dotaxdocumentstateserver.addDOTaxDocumentState(documentstate);
			}
				
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DOTaxDocumentAddResultDTO doEvaluateDocumentUploadRM(DOTaxDocumentEvaluationUpdateInitParamDTO initParamDTO){
		
		DOTaxDocumentAddResultDTO resultDTO = new DOTaxDocumentAddResultDTO();
		
		try {
			Long[] dotaxdocumentnumbers = Arrays.stream(initParamDTO.getUpdatedata()).map(data -> data.getDotaxdocumentnumber()).toArray(Long[]::new);
 			DOTaxDocumentEvaluationValidationBean[] validations = dotaxdocumentserver.getDocumentsByNumbers(dotaxdocumentnumbers);
 			HashMap<String, DOTaxDocumentEvaluationValidationBean> validationMap = new HashMap<String, DOTaxDocumentEvaluationValidationBean>();
 			for (DOTaxDocumentEvaluationValidationBean validation : validations) {
 				validationMap.put(validation.getDotaxdocumentnumber() + "_" + validation.getDonumber(), validation);
 			}
			
			List<BaseResultDTO> baseresulList = new ArrayList<BaseResultDTO>();
			String error = "";
			
			DOTaxDocumentEvaluationValidationBean validation;
			for (DOTaxDocumentEvaluationUpdateDataDTO data : initParamDTO.getUpdatedata()) {
				if (!validationMap.containsKey(data.getDotaxdocumentnumber() + "_" + data.getDonumber())) {
					error = "Fila " + data.getRow() + ": El número de factura " + data.getDotaxdocumentnumber() + " no está cargado para la orden " + data.getDonumber();
					baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				}
				else {
					validation = validationMap.get(data.getDotaxdocumentnumber() + "_" + data.getDonumber());
					
					if (!validation.getState().equals("C_INV")) {
						error = "Fila " + data.getRow() + ": La orden " + data.getDonumber() + " debe estar en estado de facturación 'Factura Confirmada'";
						baseresulList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
					}
				}
			}
			
			if (baseresulList.size() > 0) {
				// Ordenar errores
				resultDTO.setValidationerrors(baseresulList.toArray(new BaseResultDTO[baseresulList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6000");
			}
			
			// Obtener los tipos de estado de factura 'Factura con RM' y 'RM Rechazada'
			DOTaxDocumentStateTypeW dotdstRMInvoice = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "RM");
			DOTaxDocumentStateTypeW dotdstRMRejected = dotaxdocumentstatetypeserver.getByPropertyAsSingleResult("code", "RM_REC");
			
			Date now = new Date();
			
			DOTaxDocumentW[] documents;
			DOTaxDocumentW document;
			DOTaxDocumentStateTypeW state;
			PropertyInfoDTO prop1;
			PropertyInfoDTO prop2;
			for (DOTaxDocumentEvaluationUpdateDataDTO data : initParamDTO.getUpdatedata()) {
				prop1 = new PropertyInfoDTO("number", "number", data.getDotaxdocumentnumber());
				prop2 = new PropertyInfoDTO("directorder.number", "directordernumber", data.getDonumber());
				documents = dotaxdocumentserver.getByPropertiesAsArray(prop1, prop2);
							
				state = data.getApprove() ? dotdstRMInvoice : dotdstRMRejected;
				
				document = documents[0];
				document.setCurrentstatetypeid(state.getId());
				document.setCurrentstatetypedate(now);
				document = dotaxdocumentserver.updateDOTaxDocument(document);
				
				DOTaxDocumentStateW documentstate = new DOTaxDocumentStateW();
				documentstate.setComment("Evaluación vía carga masiva (Excel)");
				documentstate.setTicketnumber(null);
				documentstate.setDotaxdocumentid(document.getId());
				documentstate.setDotaxdocumentstatetypeid(state.getId());
				documentstate.setWhen(now);
				documentstate.setWho(initParamDTO.getUsername());
				documentstate.setUsertype(initParamDTO.getUsertype());
				dotaxdocumentstateserver.addDOTaxDocumentState(documentstate);
			}
				
		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}	
}
