package bbr.b2b.soa.integrator.queue.manager.classes;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.common.dto.exceptions.OperationFailedException;
import bbr.b2b.soa.integrator.config.classes.ServiceConfiguration;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.entities.FileData;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.OrderDetail;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.FileDataRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;

@Component
@Transactional
public class PurchaseOrderMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderMessageProcessor.class);	

	private static final DateTimeFormatter longDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter shortDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	public void processEocMessage(String message, String vendorCode) {
		
		// OBTIENE CABECERA DE LA INTERFAZ EOC
		String headers = serviceConfiguration.getEocHeader();
		
		Map<String, List<String>> numOrders = new HashMap<>();
		Map<String, String> mapMessage = new HashMap<String, String>();
				
		try {
			if (headers != null && !headers.isEmpty()) {	
				String[] headersArray = headers.split("\\|",-1);
				String[] messageLines = message.split("\n");			

				Optional<SoaStateType> toNotifyOp = soaStateTypeRepository.findByCode("POR_NOTIFICAR");
				SoaStateType toNotify = toNotifyOp.isPresent() ? toNotifyOp.get() : null; 
				
				Optional<Vendor> vendorOp = companyRepository.findByCode(vendorCode);					
				Vendor vendor = vendorOp.isPresent() ? vendorOp.get() : null;
				
				boolean isFirst = true;
				
				if (vendor != null) {
					// AGRUPO LAS LINEAS POR NUMERO ORDEN
					for (String linea : messageLines) {					

						// SALTAR LOS TITULOS
						if (isFirst) {
							isFirst = false;
							continue;
						}
						
						String[] info = linea.split("\\|",-1);;

						String numOrder = info[1];

						if (numOrders.containsKey(numOrder)) {
							List<String> temLinea = numOrders.get(numOrder);
							temLinea.add(linea);
							numOrders.put(numOrder, temLinea);
						} else {
							List<String> lineaList = new ArrayList<>();
							lineaList.add(linea);
							numOrders.put(numOrder, lineaList);
						}				
					}				
					
					for (HashMap.Entry<String, List<String>> entry : numOrders.entrySet()) {
						
						// Seteo las lineas correspondientes a la Orden
						List<String> lineas = entry.getValue();
						
						// Seteo datos de cabecera
						String[] infoMensaje = new String[28];
						infoMensaje = lineas.get(0).split("\\|",-1);
						
						// For para setear las cabecera con su respectivo valor
						for (int j = 0; j < headersArray.length; j++) {
							mapMessage.put(headersArray[j], infoMensaje[j]);
						}
						
						// N° OC
						Long ocNumber = getLongField(mapMessage, "NRO_OC", true);
						
						// EBO: 26-03-2021: LAS EOC DE TIPO 'SE' SE CARGAN IGUAL A LA ESPERA DE UNA EOE
//						if(getField(mapMessage, "TIPO_OC", true).equals("SE")) {
//							LOGGER.info("Se descarta la orden EOC " + ocNumber + " al ser tipo SE para Sodimac");
//							break;					
//						}
						
						// ALMACENA EL MENSAJE EN FILEDATA
						FileData fileData = new FileData();
						fileData.setLoadDate(LocalDateTime.now());
						fileData.setType("eOC");
						fileData.setReference(ocNumber.toString());
						fileData.setVendorCode(vendorCode);
						fileData.setData(message);
						
						fileDataRepository.save(fileData);					
						
						// CONSULTA SI EXISTE UN NUMERO DE OC PREVIO EN LA BD					
						Optional<Order> orderOp = orderRepository.findByOcNumber(ocNumber);
						
						if (orderOp.isPresent()) {
							
							// EN ESTE CASO, FALTAN LOS VALORES DE LA CABECERA DE EOE O EOD, POR LO TANTO CON ESTE MENSAJE SE DEBEN COMPLETAR						
							Order order = orderOp.get();
							if (!order.getComplete()) {
										
								Order completeOrder = null;
								List<OrderDetail> completeDetails = null;
								
								// 1- ES DE TIPO EOE LA ORDEN EXISTENTE							
								if (Optional.ofNullable(order.getEoeCreatedDate()).isPresent() && !Optional.of(order.getEoeCreatedDate()).isEmpty()) {
									
									// COMPLETAMOS LOS DATOS DE EOC
									completeOrder = fillEocData(mapMessage, order);									
									completeOrder.setVev(true);
									completeOrder.setComplete(true);															
								}															
									
								// 2- ES DE TIPO EOD LA ORDEN EXISTENTE
								else if (Optional.ofNullable(order.getEodCreatedDate()).isPresent() && !Optional.of(order.getEodCreatedDate()).isEmpty()) {
									
									// COMPLETAMOS LOS DATOS DE EOC
									completeOrder = fillEocData(mapMessage, order);								
									completeOrder.setVev(false);
									completeOrder.setComplete(true);
									
									// TRAEMOS LOS DETALLES DE LA OC
									List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());	
									
									if (Optional.ofNullable(orderDetails).isPresent() && !Optional.of(orderDetails).isEmpty()) {
																
										// COMPLETAMOS LOS DETALLES DE EOC
										completeDetails = fillEocDetailData(lineas, headersArray, orderDetails, order);																
									}																
								}else
									LOGGER.info("Para N° EOC " + ocNumber + " la orden previa no es de tipo EOE o EOD");
								
								
								// ACTUALIZA LA ORDEN
								if (completeOrder != null)									
									orderRepository.save(completeOrder);									
															
								// ACTUALIZA LOS DETALLES
								if (completeDetails != null)
									orderDetailRepository.saveAll(completeDetails);			
								
								
								LOGGER.info("N° EOC " + ocNumber + " cargado exitosamente");
								
							}
							else {
								// EL MENSAJE SE RECHAZA
								LOGGER.info("Para N° EOC " + ocNumber + " la orden ya esta completa");					
							}						
						}	
						else {
							
							// MENSAJE DE EOC NUEVO
							Order inCompleteOrder = fillEocData(mapMessage, null);
							inCompleteOrder.setCurrentSoaStateType(toNotify);
							inCompleteOrder.setCurrentSoaStateTypeDate(LocalDateTime.now());
							inCompleteOrder.setVendor(vendor);
							inCompleteOrder.setVev(false);
							inCompleteOrder.setComplete(false);
							
							// SE AGREGA UNA EOC
							if (inCompleteOrder != null) {
								
								inCompleteOrder = orderRepository.save(inCompleteOrder);
								
								// SE DEJA EN ESTADO POR NOTIFICAR
								SoaState state = new SoaState();
								state.setOrder(inCompleteOrder);
								state.setSoaStateType(toNotify);
								state.setWhen(LocalDateTime.now());
								state.setComment("Por notificar");
								
								soaStateRepository.save(state);								
							}						
							
							// BUSCA LOS DETALLES DE EOC
							List<OrderDetail> inCompleteDetails = fillEocDetailData(lineas, headersArray, null, inCompleteOrder);
													
							// AGREGA LOS DETALLES
							if (inCompleteDetails != null)
								orderDetailRepository.saveAll(inCompleteDetails);	
							
							LOGGER.info("N° EOC " + ocNumber + " cargado exitosamente");
											
						}					
					}			
				}					
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private Order fillEocData(Map<String, String> mapMessage, Order order) throws ParseException, OperationFailedException{
		
		Order newOrder = new Order();
		
		// COPIA LOS DATOS EXISTENTES SI LOS HAY
		if (order != null)
			BeanUtils.copyProperties(order, newOrder);		
		
		// NUMERO DE OC
		newOrder.setOcNumber(getLongField(mapMessage, "NRO_OC", true));
		
		// FECHA CREACION EOC
		newOrder.setEocCreatedDate(LocalDateTime.now());
		
		String fechaTransformada = "";
		
		// FECHA DESCARGA EOC
		if (getDateField(mapMessage, "FECHA_DESCARGA_OC", false) != null) {
			fechaTransformada = mapMessage.get("FECHA_DESCARGA_OC");
			fechaTransformada.replace("/", "-");
			LocalDate downloadDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			newOrder.setEocDownloadDate(downloadDate);
		}														
		
		// FECHA EMISION
		fechaTransformada = mapMessage.get("FECHA_EMISION");
		fechaTransformada.replace("/", "-");
		LocalDate emittedDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		newOrder.setEmittedDate(emittedDate);
		
		// FECHA VIGENCIA								
		fechaTransformada = mapMessage.get("FECHA_ESPER");
		fechaTransformada.replace("/", "-");
		LocalDate expectedDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		newOrder.setExpectedDate(expectedDate);
		
		// FECHA EXPIRACION								
		LocalDate expirationDate = null;
		
		if (getDateField(mapMessage, "FECHA_VENC", false) != null) {
			fechaTransformada = mapMessage.get("FECHA_VENC");
			fechaTransformada.replace("/", "-");
			expirationDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			newOrder.setExpirationDate(expirationDate);
		} else {
			fechaTransformada = mapMessage.get("FECHA_ESPER");
			fechaTransformada.replace("/", "-");
			expirationDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			
		}
		newOrder.setExpirationDate(expirationDate);
		
		// FECHA DE COMPROMISO (= a FECHA DE VIGENCIA)
		newOrder.setCommitmentDeliveryDate(expectedDate);
		
		// ESTADO OC
		newOrder.setOcState(getField(mapMessage, "ESTADO_OC", false));
		
		// TIPO DE OC
		newOrder.setOcType(getField(mapMessage, "TIPO_OC", false));
		
		// CODIGO VENDEDOR INTERNO SODIMAC
		newOrder.setVendorCode(getField(mapMessage, "COD_PROVEEDOR", false));
		
		// RUT
		newOrder.setRut(getField(mapMessage, "RUT", false));
		
		// DIGITO VERIFICADOR
		newOrder.setDvRut(getField(mapMessage, "DV_RUT", false));
		
		// RAZÓN SOCIAL
		newOrder.setTradename(getField(mapMessage, "RAZON_SOCIAL", false));
		
		// DIRECCION
		newOrder.setAddress(getField(mapMessage, "DIRECCION", false));
		
		// TELEFONO
		newOrder.setPhone(getField(mapMessage, "FONO", false));
		
		// MONEDA
		newOrder.setCurrency(getField(mapMessage, "MONEDA", false));
		
		// CARGO OC
		newOrder.setCharge(getDoubleField(mapMessage, "CARGO_OC", false));
																	
		// DESCUENTO OC
		newOrder.setDiscount(getDoubleField(mapMessage, "DESCUENTO_OC", false));
		
		// TOTAL NETO
		newOrder.setTotalNeto(getDoubleField(mapMessage, "TOTAL_OC_NETO", false));
		
		// TOTAL IVA
		newOrder.setTotalIVA(getDoubleField(mapMessage, "TOTAL_IVA", false));
		
		// TOTAL BRUTO
		newOrder.setTotalGross(getDoubleField(mapMessage, "TOTAL_OC_BRUTO", true));
		
		// FORMA DE PAGO
		newOrder.setPaymentType(getField(mapMessage, "FORMA_PAGO", true));
		
		// COMPRADOR
		newOrder.setBuyer(getField(mapMessage, "COMPRADOR", true));
		
		// TOTAL DESCUENTOS 
		newOrder.setTotalDiscount(getDoubleField(mapMessage, "TOTAL_DESCUENTOS", false) != null ? getDoubleField(mapMessage, "TOTAL_DESCUENTOS", false) : 0);
		
		// TOTAL CARGOS
		newOrder.setTotalCharge(getDoubleField(mapMessage, "TOTAL_CARGOS", false) != null ? getDoubleField(mapMessage, "TOTAL_CARGOS", false) : 0);
		
		return newOrder;		
	}
	
	private List<OrderDetail> fillEocDetailData(List<String> lineas, String[] headersArray, List<OrderDetail> orderDetails, Order order) throws OperationFailedException{
		
		Map<String, String> mapMessageSku = new HashMap<String, String>();		
		Map<String, String> mapMessage = new HashMap<String, String>();
		
		Map<String, OrderDetail> skuOrderDetailMap = new HashMap<String, OrderDetail>();
		
		List<OrderDetail> newOrderDetails = new ArrayList<OrderDetail>();
		
		// SI EXISTEN DETALLES PREVIOS, SE GENERA UN MAPA POR SKU
		if (orderDetails != null) {			
			skuOrderDetailMap = orderDetails.stream().collect(Collectors.toMap(OrderDetail::getSku, detail -> detail));		
		}
		
		int position = 0;
		
		for (int i = 0; i < headersArray.length; i++) {
			if (headersArray[i].equals("SKU")) {
				position = i;
				break;
			}
		}

		for (String linea : lineas) {
			String[] lineaSeparadas = linea.split("\\|",-1);
			mapMessageSku.put(lineaSeparadas[position], linea);
		}
		
		List<String> listadoSkuOrdenado = new ArrayList<>(mapMessageSku.keySet());
		Collections.sort(listadoSkuOrdenado);
		
		for (String skuData : listadoSkuOrdenado) {
			
			// TENGO EL SKU ORDENADO Y LA LINEA COMPLETA
			String linea = mapMessageSku.get(skuData);
			String[] lineaSeparada = linea.split("\\|",-1);

			for (int i = 0; i < headersArray.length; i++) {
				mapMessage.put(headersArray[i], lineaSeparada[i].trim());
			}
			
			OrderDetail newOrderDetail = null;
			
			// SKU
			String sku = getField(mapMessage, "SKU", true);
			
			if (skuOrderDetailMap.isEmpty()) {
				newOrderDetail = new OrderDetail();
				newOrderDetail.setOrder(order);
				newOrderDetail.setSku(sku);
				
			}else {
				// CONSULTA SI EL SKU ESTA EN LOS DETALLES PREVIOS
				if (skuOrderDetailMap.containsKey(sku))
					newOrderDetail = skuOrderDetailMap.get(sku);
				else {
					//ERROR, EL SKU NO ESTA EN EL DETALLE PREVIO
					throw new OperationFailedException("El sku " + sku + " no existe en la Orden previa");
				}
			}
			
			// POSICION
			newOrderDetail.setPosition(getIntegerField(mapMessage, "N_ITEM", true));		
				
			// UNIDAD DE EMPAQUE 
			newOrderDetail.setUmPack(getField(mapMessage, "UM_EMPAQUE", false));
			
			// EAN 13
			newOrderDetail.setUpc(getField(mapMessage, "UPC", false));
			
			// DESCRIPCION LARGA
			newOrderDetail.setLongDescription(getField(mapMessage, "DESCRIPCION_LARGA", true));
			
			// CANTIDAD DE EMPAQUE
			newOrderDetail.setPackAmount(getIntegerField(mapMessage, "CANTIDAD_EMPAQ", true));
			
			// CODIGO DE EMPAQUE
			newOrderDetail.setCodePack(getField(mapMessage, "COD_EMPAQUE", false));
			
			// MARCA
			newOrderDetail.setBrand(getField(mapMessage, "MARCA", false));
			
			// FAMILIA
			newOrderDetail.setFamily(getField(mapMessage, "FAMILIA", false));
			
			// DEPARTAMENTO
			newOrderDetail.setDepartment(getField(mapMessage, "DEPARTAMENTO", false));
			
			// CANTIDAD DE PRODUCTO
			newOrderDetail.setProductAmount(getIntegerField(mapMessage, "CANTIDAD_PROD", false));
			
			// TOTAL CARGOS
			newOrderDetail.setTotalCharge(getDoubleField(mapMessage, "TOT_CARGOS", false));
			
			// TOTAL DESCUENTOS
			newOrderDetail.setTotalDiscount(getDoubleField(mapMessage, "TOT_DESCUEN", false)); 	
			
			// COSTO UNITARIO
			newOrderDetail.setUnitPrice(getDoubleField(mapMessage, "PRECIO_UNI", false));
			
			// CODIGO DE EMPAQUE
			newOrderDetail.setCodePack(getField(mapMessage, "COD_EMPAQUE", true));
						
			// DESCUENTO ADICIONAL
			newOrderDetail.setAdditionalDiscount(getDoubleField(mapMessage, "DESCUENTO_ADIC", false));
			
			// CARGO ADICIONAL
			newOrderDetail.setAdditionalCharge(getDoubleField(mapMessage, "CARGO_ADIC", false));	
			
			newOrderDetails.add(newOrderDetail);
		}
		
		return newOrderDetails;		
	}
	
	
	private String getField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {
		String field = mappedfields.get(key);
		if (isRequired && (field == null)) {
			throw new OperationFailedException("Campo " + key + " nulo o ausente");
		}
		return field.trim();
	}

	private Integer getIntegerField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {

		String field = mappedfields.get(key);
		Integer intField = field != null && !field.isEmpty() ? Integer.valueOf(field) : null;
		if (isRequired && (intField == null || intField == 0))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (intField == null ? 0 : (intField >= 0 ? intField : 0));

	}
	
	private Long getLongField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {

		String field = mappedfields.get(key);
		Long longField = field != null && !field.isEmpty() ? Long.valueOf(field) : null;
		if (isRequired && (longField == null || longField == 0))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (longField == null ? 0 : (longField >= 0 ? longField : 0));

	}
	
	private LocalDate getDateField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException, ParseException {

		LocalDate field = dateFormatter(mappedfields.get(key));
		if (isRequired && field == null)
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");

		return field;
	}

	private LocalDate dateFormatter(String date) throws ParseException {
		if (date != null && !date.isEmpty()) {
			if (date.length() == 8) {
				return LocalDate.parse(date, shortDateFormatter);
			} else {
				return LocalDate.parse(date, longDateFormatter);
			}
		}
		return null;
	}

	private Double getDoubleField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {

		String field = mappedfields.get(key);
		Double doubleField = field != null && !field.isEmpty() ? Double.valueOf(field.replace(",", ".")) : null;
		if (isRequired && (doubleField == null || doubleField == 0))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (doubleField == null ? 0 : (doubleField >= 0 ? doubleField : 0));
	}
	
	public int transformarFecha(String fecha) {
		int result = 0;
		if(fecha.contains("ene") || fecha.contains("jan")) {
			result = 1;
		}
		else if(fecha.contains("feb")) {
			result = 2;
		}
		else if(fecha.contains("mar")) {
			result = 3;
		}
		else if(fecha.contains("abr") || fecha.contains("apr")) {
			result = 4;
		}
		else if(fecha.contains("may")) {
			result = 5;
		}
		else if(fecha.contains("jun")) {
			result = 6;
		}
		else if(fecha.contains("jul")) {
			result = 7;
		}
		else if(fecha.contains("ago") || fecha.contains("aug")) {
			result = 8;
		}
		else if(fecha.contains("sep")) {
			result = 9;
		}
		else if(fecha.contains("oct")) {
			result = 10;
		}
		else if(fecha.contains("nov")) {
			result = 11;
		}
		else if(fecha.contains("dic") || fecha.contains("dec")) {
			result = 12;
		}
		return result;
	}
}
