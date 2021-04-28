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
import bbr.b2b.soa.integrator.facade.entities.Client;
import bbr.b2b.soa.integrator.facade.entities.Vendor;
import bbr.b2b.soa.integrator.facade.entities.FileData;
import bbr.b2b.soa.integrator.facade.entities.Order;
import bbr.b2b.soa.integrator.facade.entities.OrderDetail;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.repository.classes.ClientRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.FileDataRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;

@Component
@Transactional
public class VeVOrderMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VeVOrderMessageProcessor.class);	

	private static final DateTimeFormatter longDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter shortDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private FileDataRepository fileDataRepository;
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	public void processEoeMessage(String message, String vendorCode) {
		
		// OBTIENE CABECERA DE LA INTERFAZ EOE
		String headers = serviceConfiguration.getVevHeader();
		
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
						
						// ALMACENA EL MENSAJE EN FILEDATA
						FileData fileData = new FileData();
						fileData.setLoadDate(LocalDateTime.now());
						fileData.setType("eOE");
						fileData.setReference(ocNumber.toString());
						fileData.setVendorCode(vendorCode);
						fileData.setData(message);
						
						fileDataRepository.save(fileData);					
						
						// CONSULTA SI EXISTE UN NUMERO DE OC PREVIO EN LA BD					
						Optional<Order> orderOp = orderRepository.findByOcNumber(ocNumber);
						
						if (orderOp.isPresent()) {
							
							// EN ESTE CASO, EXISTE UNA EOC PREVIA POR ENDE FALTAN LOS DATOS DE CLIENTE						
							Order order = orderOp.get();
							if (!order.getComplete()) {
										
								Order completeOrder = null;
								Client client = null;
								List<OrderDetail> completeDetails = null;
								
								// DEBE SER DE TIPO EOC LA ORDEN EXISTENTE							
								if (Optional.ofNullable(order.getEocCreatedDate()).isPresent() && !Optional.of(order.getEocCreatedDate()).isEmpty()) {
									
									// COMPLETAMOS LOS DATOS DE EOE
									completeOrder = fillEoeData(mapMessage, order);									
									completeOrder.setVev(true);
									completeOrder.setComplete(true);
									
									// COMPLETAMOS DATOS DE CLIENTE
									client = fillClientData(mapMessage, order);
									
									// TRAEMOS LOS DETALLES DE LA OC
									List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());	
									
									if (Optional.ofNullable(orderDetails).isPresent() && !Optional.of(orderDetails).isEmpty()) {
													
										// COMPLETAMOS LOS DETALLES DE EOE
										completeDetails = fillEoeDetailData(lineas, headersArray, orderDetails, order);															
									}							
								}
								else {
									// LA ORDEN PREVIA NO ES DE TIPO EOC
									LOGGER.info("Para N° EOE " + ocNumber + " la orden previa no es de tipo EOC tipo SE");
								}
														
								// ACTUALIZA LA EOE
								if (completeOrder != null)
									orderRepository.save(completeOrder);
								
								// GUARDA LOS DATOS DEL CLIENTE
								if (client != null)
									clientRepository.save(client);
								
								// ACTUALIZA LOS DETALLES
								if (completeDetails != null)
									orderDetailRepository.saveAll(completeDetails);		
								
								LOGGER.info("N° EOE " + ocNumber + " cargado exitosamente");
							}
							else {
								// EL MENSAJE SE RECHAZA
								LOGGER.info("Para N° EOE " + ocNumber + " la orden ya esta completa");							
							}						
						}	
						else {
							
							// MENSAJE DE EOE NUEVO
							Order inCompleteOrder = fillEoeData(mapMessage, null);
							inCompleteOrder.setCurrentSoaStateType(toNotify);
							inCompleteOrder.setCurrentSoaStateTypeDate(LocalDateTime.now());
							inCompleteOrder.setVendor(vendor);
							inCompleteOrder.setVev(true);
							inCompleteOrder.setComplete(true);
							
							// GUARDA LA EOE
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
							
							// GUARDA LOS DATOS DEL CLIENTE
							Client client = fillClientData(mapMessage, inCompleteOrder);
							
							if (client != null)
								clientRepository.save(client);
							
							// BUSCA LOS DETALLES DE EOE
							List<OrderDetail> inCompleteDetails = fillEoeDetailData(lineas, headersArray, null, inCompleteOrder);
													
							if (inCompleteDetails != null)
								orderDetailRepository.saveAll(inCompleteDetails);		
							
							
							LOGGER.info("N° EOE " + ocNumber + " cargado exitosamente");
						}					
					}			
				}				
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private Order fillEoeData(Map<String, String> mapMessage, Order order) throws ParseException, OperationFailedException{
		
		Order newOrder = new Order();
		
		// COPIA LOS DATOS EXISTENTES SI LOS HAY
		if (order != null)
			BeanUtils.copyProperties(order, newOrder);
		
		// NUMERO DE OC
		newOrder.setOcNumber(getLongField(mapMessage, "NRO_OC", true));
		
		// FECHA CREACION EOE
		newOrder.setEoeCreatedDate(LocalDateTime.now());
		
		// DESPACHAR A
		newOrder.setDeliveryTo(getField(mapMessage, "DESPACHAR_A", true));
		
		// NUMERO DE LOCAL
		newOrder.setEoeLocalNumber(getField(mapMessage, "NRO_LOCAL", false));
		
		// DESCRIPCION DE LOCAL
		newOrder.setEoeLocalDescription(getField(mapMessage, "LOCAL", false)); 
		
		// TOTAL BRUTO
		newOrder.setTotalGross(getDoubleField(mapMessage, "PRECIO_COSTO", true));
		
		// ESTADO OC
		newOrder.setOcState("");
		
		// FECHA DE COMPRIMISO DE CLIENTE
		String fechaTransformada = mapMessage.get("FECHA_REPARTO_CLIENTE");
		fechaTransformada.replace("/", "-");
		int numero = transformarFecha(fechaTransformada);
		if(numero < 10) {
			fechaTransformada = fechaTransformada.substring(0,3) + 0 + numero +fechaTransformada.substring(6);
		}else {
			fechaTransformada = fechaTransformada.substring(0,3) + numero + fechaTransformada.substring(6);
		}
		LocalDate date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		newOrder.setCommitmentDeliveryDate(date);		
		
		// FECHA EMISION
		fechaTransformada = mapMessage.get("FECHA_EMISION_OC");
		fechaTransformada.replace("/", "-");
		date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		newOrder.setEmittedDate(date);
		
		// FECHA DE VIGENCIA
		fechaTransformada = mapMessage.get("FECHA_DESPACHO_PACTADA");
		fechaTransformada.replace("/", "-");
		date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		newOrder.setExpectedDate(date);
		
		// FECHA VENCIMIENTO
		if (getDateField(mapMessage, "FECHA_VENC", false) != null) {
			fechaTransformada = mapMessage.get("FECHA_VENC");
			fechaTransformada.replace("/", "-");
			date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));		
		} else {
			fechaTransformada = mapMessage.get("FECHA_DESPACHO_PACTADA");
			fechaTransformada.replace("/", "-");
			date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd/MM/yyyy"));	
		}
		newOrder.setExpirationDate(date);
		
		return newOrder;		
	}	
	
	private Client fillClientData(Map<String, String> mapMessage, Order order) throws ParseException, OperationFailedException{
		
		Client newClient = new Client();
		
		// ORDER
		newClient.setOrder(order);
		
		// NUMERO DE RESERVA	
		newClient.setReserveNumber(getLongField(mapMessage, "RESERVA", false));
		
		// NOMBRE DE RECEPTOR
		String receiverName = getField(mapMessage, "NOM_RECEPTOR", false);
		if (receiverName != null && !receiverName.isEmpty()) {
			receiverName = receiverName.trim().replaceAll("\\s+", " ");
		}
		newClient.setReceiverName(receiverName);
		
		// NOBRE COMPRADOR
		newClient.setBuyerName(getField(mapMessage, "NOM_COMPRADOR", false));
		
		// DNI COMPRADOR
		newClient.setBuyerDni(getField(mapMessage, "DNI_COMPRADOR", false));
		
		// TELEFONO COMPRADOR
		newClient.setBuyerPhone(getField(mapMessage, "TELEFONO_COMPRADOR", false));
		
		// DIRECCION RECEPTOR
		newClient.setReceiverAddress(getField(mapMessage, "DIRECCION_RECEPTOR", false));
		
		// CODIGO POSTAL
		newClient.setReceiverCp(getField(mapMessage, "COD_POSTAL_RECEPTOR", false));
		
		// TELEFONO RECEPTOR
		newClient.setReceiverPhone(getField(mapMessage, "TELEFONO_RECEPTOR", false));
		
		// NUMERO DE CALLE
		newClient.setStreet(getField(mapMessage, "ENTRE_CALLE", false));
		
		// OFICINA
		newClient.setHouseOffice(getField(mapMessage, "DOMICILIO/OFICINA", false));
		
		// REGION		
		newClient.setRegion(getField(mapMessage, "REGION", false));
		
		// COMUNA
		newClient.setReceiverCommune(getField(mapMessage, "COMUNA_RECEPTOR", false));
		
		// CIUDAD
		newClient.setReceiverCity(getField(mapMessage, "CIUDAD_RECEPTOR", false));
		
		// OBSERVACIONES
		newClient.setObservation(getField(mapMessage, "OBSERVACION", false));
		
		// ATENCION
		newClient.setAttention(getField(mapMessage, "ATENCION", false));
		
		// EMAIL
		newClient.setEmail(getField(mapMessage, "EMAIL", false));
		
		// IDENTIFICACION CLIENTE
		newClient.setClientIdentification(getField(mapMessage, "IDENTIFICACION_CLIENTE", false));
		
		// FECHA REPARTO CLIENTE		
		String fechaTransformada = mapMessage.get("FECHA_REPARTO_CLIENTE");
		fechaTransformada.replace("/", "-");
		int numero = transformarFecha(fechaTransformada);
		if(numero < 10) {
			fechaTransformada = fechaTransformada.substring(0,3) + 0 + numero +fechaTransformada.substring(6);
		}else {
			fechaTransformada = fechaTransformada.substring(0,3) + numero + fechaTransformada.substring(6);
		}
		LocalDate date = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		newClient.setClientDeliveryDate(date);	
		
		// PROGRAMACION
		if (getDateField(mapMessage, "HORARIO_PROGRAMADO", false) != null) {
			fechaTransformada = mapMessage.get("HORARIO_PROGRAMADO");
			fechaTransformada.replace("/", "-");
			LocalDate schedule = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			newClient.setSchedule(schedule);
		}			
		
		return newClient;		
	}	
	
	private List<OrderDetail> fillEoeDetailData(List<String> lineas, String[] headersArray, List<OrderDetail> orderDetails, Order order) throws OperationFailedException{
		
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
			newOrderDetail.setPosition(0);			
			
			// DESCRIPCION
			if (getField(mapMessage, "DESCRIPCION", false) != null) {
				newOrderDetail.setShortDescription(getField(mapMessage, "DESCRIPCION", false));	
			}else {
				newOrderDetail.setShortDescription(getField(mapMessage, "DESCRIPCION_EXTENDIDA", false));
			}		
			
			// DEDICACION
			newOrderDetail.setDedication(getField(mapMessage, "SALUDO", false));
			
			// PAPEL DE REGALO
			newOrderDetail.setGiftPaper(getField(mapMessage, "PAPEL_REGALO", false));
			
			// PRECIO COSTO
			newOrderDetail.setUnitPrice(getDoubleField(mapMessage, "PRECIO_COSTO", false));
			
			// TOTAL DE UNIDADES
			newOrderDetail.setTotalUnits(getDoubleField(mapMessage, "UNIDADES", true));
			
			// TOTAL DESCUENTO
			newOrderDetail.setTotalDiscount(getDoubleField(mapMessage, "DESCUENTO", false));						
			
			// UPC
			newOrderDetail.setUpc(getField(mapMessage, "UPC", false));
			
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
	
	private Double getDoubleField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {

		String field = mappedfields.get(key);
		Double doubleField = field != null && !field.isEmpty() ? Double.valueOf(field.replace(",", ".")) : null;
		if (isRequired && (doubleField == null || doubleField == 0))
			throw new OperationFailedException("Campo " + key + " nulo o ausente desde el ws");
		else
			return (doubleField == null ? 0 : (doubleField >= 0 ? doubleField : 0));

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
