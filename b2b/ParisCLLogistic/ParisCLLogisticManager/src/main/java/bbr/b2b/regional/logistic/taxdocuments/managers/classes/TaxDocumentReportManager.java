package bbr.b2b.regional.logistic.taxdocuments.managers.classes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
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
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.parser.dte.DTEDefType;
import bbr.b2b.regional.logistic.parser.dte.DTEDefType.Documento.Referencia;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.TaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.TaxDocumentReportManagerLocal;
import bbr.b2b.regional.logistic.taxdocuments.managers.interfaces.TaxDocumentReportManagerRemote;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionCSVReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailTotalDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionExcelReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionExcelReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentAddResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentIdsByPagesDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentValidationResultDTO;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.PropertyVendorServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import cl.paperless.webapp.online.webservices.OnlineClient;
import cl.paperless.webapp.online.webservices.OnlinePortType;
import cl.paperless.webapp.online.webservices.OnlineRecoveryRecHandler;

@Stateless(name = "managers/TaxDocumentReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TaxDocumentReportManager implements TaxDocumentReportManagerLocal, TaxDocumentReportManagerRemote {

	private static Logger logger = Logger.getLogger(TaxDocumentReportManager.class);
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	TaxDocumentServerLocal taxdocumentserver;
	
	@EJB
	PropertyVendorServerLocal propertyvendorserver;
	
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
	
	public TaxDocumentAddResultDTO doAddTaxDocumentsByOrders(Map<String, Double> tdOcMap, VendorW vendor, Date datingdate){
		TaxDocumentAddResultDTO resultDTO = new TaxDocumentAddResultDTO();
		
		// Validar contra sistema Paperless (PPL) la información ingresada por el usuario
		try {
			// Determinar si se deben omitir las validaciones de documentos para este proveedor
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("code", "code", "NO_VALIDA_FACTPL");
			PropertyVendorW[] pvNoValidations = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
			boolean validations = pvNoValidations == null || pvNoValidations.length == 0;
			
			// Obtener el m�ximo de d�as previos a la fecha de cita para facturar
			Integer invoiceMaxPreviousDays = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("maxinvoicepreviousdays").trim());
			
			// Obtener las credenciales para la conexión
			String pplUser = B2BSystemPropertiesUtil.getProperty("paperlessuser").trim();
			String pplPassword = B2BSystemPropertiesUtil.getProperty("paperlesspassword").trim();
			
			// Obtener el RUT sin dígito verificador del proveedor
			String vendorRUT = vendor.getRut() == null ? "0" : vendor.getRut();
					
			// Contruir el request general para el WS de PPL
			Integer arg0 = 81201000; 						// RUT de Paris, sin dígito verificador
			String arg1 = pplUser; 							// Usuario para conectarse al WS
			String arg2 = pplPassword; 						// Password para conectarse al WS
			Integer arg3 = Integer.valueOf(vendorRUT);		// RUT del proveedor (sin dígito verificador)
			Integer arg4 = 33;								// Tipo de documento Electr�nico seg�n el SII
			Long arg5 = null;								// Folio del documento
			Integer arg6 = null;							// Tipo de devolución de respuesta
			
			String proxyIP = RegionalLogisticConstants.getInstance().getHTTP_PROXY_IP();
			int proxyPort = RegionalLogisticConstants.getInstance().getHTTP_PROXY_PORT();
			
			Map<Long, Long> validatedMap = new HashMap<Long, Long>();
			Map<Long, Long> pendingMap = new HashMap<Long, Long>();
			List<BaseResultDTO> baseresultList = new ArrayList<BaseResultDTO>();
			TaxDocumentValidationResultDTO validation = null;
			TaxDocumentW taxdocument = null;
			JAXBContext jc = null;
			Unmarshaller u = null;
			Source source = null;
			DTEDefType dteParser = null;
			XMLGregorianCalendar emittedCal = null;
			List<Referencia> referenceList = null;
			JAXBElement<DTEDefType> dte = null;
			Proxy proxy = null;
			HttpURLConnection connection = null;
			XMLInputFactory xmlInputFactory = null;
			XMLStreamReader xmlStreamReader = null;
			InputStream inputStream = null;			
			Long taxDocumentNumber = null;
			Long orderNumber = null;
			Double netamount = null;
			Double plAmount = null;
			String key = "";
			String error = "";
			Calendar cal = null;
			Date now = null;
			Date emitted = null;
			Date emittedMin = null;
			boolean hasReferenceNumber;
			for (Map.Entry<String, Double> e : tdOcMap.entrySet()) {
				key = e.getKey();
				taxDocumentNumber = Long.parseLong(key.split("_")[0]);
				orderNumber = Long.parseLong(key.split("_")[1]);
				plAmount = e.getValue();
				
				now = new Date();
				
				taxdocument = new TaxDocumentW();
				taxdocument.setNumber(taxDocumentNumber);
				taxdocument.setWhen(now);
				taxdocument.setValidated(false);
				// El N°mero y la fecha de recepción se llenan en otra instancia
				taxdocument.setVendorid(vendor.getId());
				taxdocument = taxdocumentserver.addTaxDocument(taxdocument);
				
				arg5 = taxDocumentNumber;
				
				// Obtener ruta del xml
				arg6 = 1;
				validation = paperlessTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, orderNumber);
				
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
						
						taxdocument.setXmlurl(validation.getMessage());
						
						dteParser = dte.getValue();
						
						// Obtener la fecha de emisión del documento
						emittedCal = dteParser.getDocumento().getEncabezado().getIdDoc().getFchEmis();
						cal = emittedCal.toGregorianCalendar();
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						emitted = cal.getTime();
						
						// Obtener el monto neto del documento
						netamount = (double)dteParser.getDocumento().getEncabezado().getTotales().getMntNeto().longValue();
												
						// Si debe validar los documentos de este proveedor
						if (validations) {
							// Validar que la fecha de emisión del documento sea mayor o igual a la fecha de cita - X d�as
							cal = Calendar.getInstance();
							cal.setTime(datingdate);
							cal.set(Calendar.HOUR_OF_DAY, 0);
							cal.set(Calendar.MINUTE, 0);
							cal.set(Calendar.SECOND, 0);
							cal.set(Calendar.MILLISECOND, 0);
							cal.add(Calendar.DAY_OF_YEAR, -invoiceMaxPreviousDays);
							emittedMin = cal.getTime();
							
							if (emitted.before(emittedMin)) {
								error = "La fecha de emisión del documento no debe ser anterior a " + invoiceMaxPreviousDays + " días con respecto a la fecha asignada para la cita de este despacho";
								logger.error(error + " Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
										"Factura " + taxDocumentNumber + " para OC " + orderNumber + ": " + error, false));
								continue;
							}
							
							// Validar que el monto neto facturado posea una tolerancia con respecto a lo cargado en el PL para la OC
							if (Math.abs(plAmount - netamount) > Double.parseDouble(B2BSystemPropertiesUtil.getProperty("invoicetolerance").trim())) {
								error = "El monto neto facturado ($" + netamount + ") no coincide con lo cargado en el PL ($" + plAmount + ") para la OC";
								logger.error(error + " Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
										"Factura " + taxDocumentNumber + " para OC " + orderNumber + ": " + error, false));
								continue;
							}
							
							// Validar que la factura contiene como dato de referencia el N°mero de la OC a la cual se est� asociando el folio
							referenceList = dteParser.getDocumento().getReferencia();
							hasReferenceNumber = false;
							for (Referencia reference : referenceList) {
								if (reference.getTpoDocRef().equals("801")) {
									hasReferenceNumber = reference.getFolioRef().equals(orderNumber.toString());
									break;
								}
							}
							
							if (!hasReferenceNumber) {
								error = "El documento para la factura no hace referencia a la OC";
								logger.error(error + " Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
								baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
										"Factura " + taxDocumentNumber + " para OC " + orderNumber + ": " + error, false));
								continue;
							}
						}
						
						taxdocument.setEmitted(emitted);
						taxdocument.setAmount(netamount);
												
						// Obtener ruta del pdf
						arg6 = 2;
						validation = paperlessTaxDocumentValidationWS(arg0, arg1, arg2, arg3, arg4, arg5, arg6, orderNumber);
						
						if (validation.getStatuscode().equals("0")) {
							// Almacenar las facturas con ruta de pdf
							taxdocument.setPdfurl(validation.getMessage());
							taxdocument.setValidated(true);
							taxdocument = taxdocumentserver.updateTaxDocument(taxdocument);
							
							validatedMap.put(taxDocumentNumber, taxdocument.getId());
							
							logger.info("Factura validada correctamente, Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
						}
						else if (!validations) {
							// Si no se deben validar los documentos de este proveedor se permite la carga del documento
							// Almacenar las facturas sin ruta de pdf
							taxdocument = taxdocumentserver.updateTaxDocument(taxdocument);
							
							pendingMap.put(taxDocumentNumber, taxdocument.getId());
							
							logger.info("Factura de proveedor que no exige validación, Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
						}
						else if (validation.getStatuscode().equals("L4506")) {
							// Los errores en la llamada al ws no se toman como de validación y permiten la carga del documento
							// Almacenar las facturas sin ruta de pdf
							taxdocument = taxdocumentserver.updateTaxDocument(taxdocument);
							
							pendingMap.put(taxDocumentNumber, taxdocument.getId());
							
							logger.info("Factura pendiente de validación, Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
						}
						else {
							baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
									validation.getStatusmessage(), false));
						}

					} catch (Exception e1) {
						e1.printStackTrace();
						error = "Error parseando el xml: " + validation.getMessage();
						logger.error(error + " Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
						baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
								"Factura " + taxDocumentNumber + " para OC " + orderNumber + ": " + error, false));
					}
				}
				else if (!validations) {
					// Si no se deben validar los documentos de este proveedor se permite la carga del documento
					// Almacenar las facturas sin fecha de emisión ni rutas de xml y pdf
					pendingMap.put(taxDocumentNumber, taxdocument.getId());
					
					logger.info("Factura de proveedor que no exige validación, Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
				}
				else if (validation.getStatuscode().equals("L4506")) {
					// Los errores en la llamada al ws no se toman como de validación y permiten la carga del documento
					// Almacenar las facturas sin fecha de emisión ni rutas de xml y pdf
					pendingMap.put(taxDocumentNumber, taxdocument.getId());
					
					logger.info("Factura pendiente de validación, Factura: " + taxDocumentNumber + " asociada a la orden " + orderNumber);
				}
				else {
					baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1",
							validation.getStatusmessage(), false));
				}
			}

			// Si hay errores
			if (baseresultList.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]), comparator);
				resultDTO.setValidationerrors(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4505");
			}
			
			resultDTO.setValidatedtaxdocuments(validatedMap);
			resultDTO.setPendingtaxdocuments(pendingMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4505");
		}
	
		return resultDTO;
	}
	
	private TaxDocumentValidationResultDTO paperlessTaxDocumentValidationWS(Integer arg0, String arg1, String arg2, Integer arg3, Integer arg4, Long arg5, Integer arg6, Long ordernumber) {
		
		TaxDocumentValidationResultDTO resultDTO = new TaxDocumentValidationResultDTO();
		
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
				error = "Error de servicio: " + message + " Factura: " + arg5 + " asociada a la orden " + ordernumber;
				logger.error(error);
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4505", error, false);
			}
			
			resultDTO.setMessage(message);
			logger.info("Ejecutada en Paperless la validación de la factura " + arg5 + ", asociada a la orden " + ordernumber);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			error = "Ha ocurrido un error accediendo al servicio de Paperless";
			logger.error(error + " Factura: " + arg5 + " asociada a la orden " + ordernumber);
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4506", error, false);
		}

		return resultDTO;		
	}
	
	public ReceptionReportResultDTO getReceptionReportByVendorDeliveryLocationAndFilter(ReceptionReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated) {
		ReceptionReportResultDTO resultDTO = new ReceptionReportResultDTO();
		
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
			Calendar cal = null;
			Date since = null;
			Date until = null;
			switch(initParamDTO.getFiltertype()) {
			case 0:
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
			case 1:
			case 4:
				// Validar que sea un N°mero mayor que cero y con una cantidad m�xima de 10 dígitos
				if (initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
				}
				break;
			case 2:
				// Validar que el código tenga una cantidad m�xima de 20 caracteres
				if (initParamDTO.getCode().length() > 20) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4512");
				}
				break;
			case 3:
				// Validar que sea un N°mero mayor que cero y con una cantidad m�xima de 15 dígitos
				if (initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 999999999999999L) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4516");
				}
				break;
			default:
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			ReceptionReportDataDTO[] receptions = null;
			int total = 0;
			switch (initParamDTO.getFiltertype()) {
			case 0:
				// FECHA DE RECEPción
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndDate(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until, initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndDate(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until);
				}
				break;
			case 1:
				// N°MERO DE ORDEN DE COMPRA
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndOrderNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndOrderNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber());
				}
				break;
			case 2:
				// SKU
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndItemInternalCode(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getCode(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndItemInternalCode(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getCode());
				}
				break;
			case 3:
				// N°MERO DE RECEPción
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber().toString(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber().toString());
				}
				break;
			case 4:
				// N°MERO DE FACTURA
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber());
				}
				break;
			case 5:
				// FECHA DE EMISION DE FACTURA
				receptions = taxdocumentserver.getReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until, initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
				if (byFilter) {
					total = taxdocumentserver.countReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until);
				}
				break;
			}

			if (byFilter) {
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPagenumber());
				pageInfo.setRows(receptions.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}

			resultDTO.setReceptions(receptions);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public ReceptionExcelReportResultDTO getExcelReceptionReportByTaxDocuments(ReceptionDownloadInitParamDTO initParamDTO) {
		ReceptionExcelReportResultDTO resultDTO = new ReceptionExcelReportResultDTO();
		
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}

			// Chequea la existencia de las facturas para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			
			for (Long taxDocumentId : initParamDTO.getTaxdocumentids()) {
				properties[1] = new PropertyInfoDTO("id", "id", taxDocumentId);
				List<TaxDocumentW> taxdocuments = taxdocumentserver.getByProperties(properties);

				if (taxdocuments == null || taxdocuments.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4515");
				}
			}

			ReceptionExcelReportDataDTO[] receptions = taxdocumentserver.getExcelReceptionReportByTaxDocuments(initParamDTO.getTaxdocumentids(), initParamDTO.getOrderby());

			resultDTO.setReceptions(receptions);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public ReceptionCSVReportResultDTO getCSVReceptionReportByTaxDocuments(ReceptionDownloadInitParamDTO initParamDTO) {
		ReceptionCSVReportResultDTO resultDTO = new ReceptionCSVReportResultDTO();
		
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}

			// Chequea la existencia de las facturas para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			
			for (Long taxDocumentId : initParamDTO.getTaxdocumentids()) {
				properties[1] = new PropertyInfoDTO("id", "id", taxDocumentId);
				List<TaxDocumentW> taxdocuments = taxdocumentserver.getByProperties(properties);

				if (taxdocuments == null || taxdocuments.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4515");
				}
			}

			ReceptionCSVReportDataDTO[] receptions = taxdocumentserver.getCSVReceptionReportByTaxDocuments(initParamDTO.getTaxdocumentids(), initParamDTO.getOrderby());

			resultDTO.setReceptions(receptions);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public TaxDocumentIdsByPagesDTO getReceptionReportByPages(ReceptionReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO) {
		TaxDocumentIdsByPagesDTO resultDTO = new TaxDocumentIdsByPagesDTO();
		
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
			Calendar cal = null;
			Date since = null;
			Date until = null;
			switch(initParamDTO.getFiltertype()) {
			case 0:
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
			case 1:
			case 4:
				// Validar que sea un N°mero mayor que cero y con una cantidad m�xima de 10 dígitos
				if (initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 9999999999L) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4501");
				}
				break;
			case 2:
				// Validar que el código tenga una cantidad m�xima de 20 caracteres
				if (initParamDTO.getCode().length() > 20) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4512");
				}
				break;
			case 3:
				// Validar que sea un N°mero mayor que cero y con una cantidad m�xima de 15 dígitos
				if (initParamDTO.getNumber() <= 0 || initParamDTO.getNumber() > 999999999999999L) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4516");
				}
				break;
			default:
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			Long[] taxdocumentids = null;
			switch (initParamDTO.getFiltertype()) {
			case 0:
				// FECHA DE RECEPción
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationDateAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until, initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 1:
				// N°MERO DE ORDEN DE COMPRA
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationOrderNumberAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 2:
				// SKU
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationItemInternalCodeAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getCode(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 3:
				// N°MERO DE RECEPción
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationNumberAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber().toString(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 4:
				// N°MERO DE FACTURA
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationTaxDocumentNumberAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), initParamDTO.getNumber(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			case 5:
				// FECHA DE EMISION DE FACTURA
				taxdocumentids = taxdocumentserver.getReceptionIdsByVendorDeliveryLocationTaxDocumentEmittedAndPages(vendor.getId(), initParamDTO.getDeliverylocationid(), since, until, initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), pageRangesDTO);
				break;
			}
			
			resultDTO.setTaxdocumentids(taxdocumentids);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public ReceptionDetailArrayResultDTO getReceptionDetailByTaxDocument(ReceptionDetailInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated) {
		ReceptionDetailArrayResultDTO resultDTO = new ReceptionDetailArrayResultDTO();
		
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}

			// Chequea la existencia de la factura para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			properties[1] = new PropertyInfoDTO("id", "id", initParamDTO.getTaxdocumentid());
			List<TaxDocumentW> taxdocuments = taxdocumentserver.getByProperties(properties);

			if (taxdocuments == null || taxdocuments.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4515");
			}
			
			ReceptionDetailDTO[] receptiondetails = taxdocumentserver.getReceptionDetailByTaxDocument(initParamDTO.getTaxdocumentid(), initParamDTO.getPagenumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), isPaginated);
			
			ReceptionDetailTotalDTO totals = null;
			if (byFilter) {
				totals = taxdocumentserver.countReceptionDetailByTaxDocument(initParamDTO.getTaxdocumentid());
				resultDTO.setTotals(totals);
				
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPagenumber());
				pageInfo.setRows(receptiondetails.length);
				pageInfo.setTotalpages((totals.getRegisters().intValue() % rows) != 0 ? (totals.getRegisters().intValue() / rows) + 1 : (totals.getRegisters().intValue() / rows));
				pageInfo.setTotalrows(totals.getRegisters().intValue());
				resultDTO.setPageInfo(pageInfo);
			}
			
			resultDTO.setReceptiondetails(receptiondetails);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public ReceptionDetailExcelArrayResultDTO getExcelReceptionDetailByTaxDocuments(ReceptionDetailExcelInitParamDTO initParamDTO) {
		ReceptionDetailExcelArrayResultDTO resultDTO = new ReceptionDetailExcelArrayResultDTO();
		
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
//			// Chequea la existencia del vendor
//			try {
//				vendorserver.getById(vendor.getId());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}

			// Chequea la existencia de la factura para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			properties[1] = new PropertyInfoDTO("id", "id", initParamDTO.getTaxdocumentid());
			List<TaxDocumentW> taxdocuments = taxdocumentserver.getByProperties(properties);

			if (taxdocuments == null || taxdocuments.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4515");
			}

			ReceptionDetailExcelDTO[] receptiondetails = taxdocumentserver.getExcelReceptionDetailByTaxDocuments(initParamDTO.getTaxdocumentid(), initParamDTO.getOrderby());

			resultDTO.setReceptiondetails(receptiondetails);
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public TaxDocumentReportResultDTO getTaxDocumentReportByDate(TaxDocumentReportInitParamDTO initParamDTO) {
		TaxDocumentReportResultDTO resultDTO = new TaxDocumentReportResultDTO();
		
		try {
			Locale locale = new Locale("es", "CL");
			
			Calendar calSince = Calendar.getInstance(locale);
			Date since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			calSince.setTime(since);
			calSince.set(Calendar.HOUR_OF_DAY, 0);
			calSince.set(Calendar.MINUTE, 0);
			calSince.set(Calendar.SECOND, 0);
			calSince.set(Calendar.MILLISECOND, 0);
			since = calSince.getTime();

			Calendar calUntil = Calendar.getInstance(locale);
			Date until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			calUntil.setTime(until);
			calUntil.add(Calendar.DAY_OF_MONTH, 1);
			calUntil.set(Calendar.HOUR_OF_DAY, 0);
			calUntil.set(Calendar.MINUTE, 0);
			calUntil.set(Calendar.SECOND, 0);
			calUntil.set(Calendar.MILLISECOND, 0);
			until = calUntil.getTime();
			
			// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
			if(!until.after(since)){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4500");
			}
			
			// Validar que el rango seleccionado sea de a lo más 1 a�o
			if (calSince.get(Calendar.YEAR) < calUntil.get(Calendar.YEAR) && 
					calSince.get(Calendar.DAY_OF_YEAR) < calUntil.get(Calendar.DAY_OF_YEAR)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4514");
			}

			// Validar que la cantidad de facturas no supere el valor parametrizado para este tipo de descarga
			if (taxdocumentserver.countTaxDocumentReportByDate(since, until) > RegionalLogisticConstants.getInstance().getMAX_ROWS_INVOICE_PENDING_REPORT()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2104");
			}
			
			TaxDocumentReportDataDTO[] taxdocuments = taxdocumentserver.getTaxDocumentReportByDate(since, until, initParamDTO.getOrderby());

			resultDTO.setTaxdocuments(taxdocuments);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
}
