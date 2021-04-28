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
import bbr.b2b.soa.integrator.facade.entities.PreDeliveryDetail;
import bbr.b2b.soa.integrator.facade.entities.SoaState;
import bbr.b2b.soa.integrator.facade.entities.SoaStateType;
import bbr.b2b.soa.integrator.facade.repository.classes.VendorRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.FileDataRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.OrderRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.PreDeliveryDetailRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateRepository;
import bbr.b2b.soa.integrator.facade.repository.classes.SoaStateTypeRepository;

@Component
@Transactional
public class PreDeliveryOrderMessageProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PreDeliveryOrderMessageProcessor.class);	

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
	private PreDeliveryDetailRepository preDeliveryDetailRepository;
	
	@Autowired
	private SoaStateTypeRepository soaStateTypeRepository;
	
	@Autowired
	private SoaStateRepository soaStateRepository;
	
	@Autowired
	private VendorRepository companyRepository;	
	
	public void processEodMessage(String message, String vendorCode) {
		
		// OBTIENE CABECERA DE LA INTERFAZ EOD
		String headers = serviceConfiguration.getPredeliveryHeader();
		
		Map<String, List<String>> numOrders = new HashMap<>();
		Map<String, String> mapMessage = new HashMap<String, String>();
		
		Map<Long, List<PreDeliveryDetail>> predDataMap = new HashMap<Long, List<PreDeliveryDetail>>();
				
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
						
						if(getField(mapMessage, "TIPO_OC", true).equals("SE")) {
							LOGGER.info("Se descarta la orden OED " + ocNumber + " al ser tipo SE para Sodimac");
							break;					
						}
						
						// ALMACENA EL MENSAJE EN FILEDATA
						FileData fileData = new FileData();
						fileData.setLoadDate(LocalDateTime.now());
						fileData.setType("eOD");
						fileData.setReference(ocNumber.toString());
						fileData.setVendorCode(vendorCode);
						fileData.setData(message);
						
						fileDataRepository.save(fileData);					
											
						// CONSULTA SI EXISTE UN NUMERO DE OC PREVIO EN LA BD					
						Optional<Order> orderOp = orderRepository.findByOcNumber(ocNumber);
						
						if (orderOp.isPresent()) {
							
							// EN ESTE CASO, EXISTE UNA EOC PREVIA POR ENDE FALTAN LOS DATOS DE PREDISTRIBUCION						
							Order order = orderOp.get();
							if (!order.getComplete()) {
										
								Order completeOrder = null;
								List<OrderDetail> completeDetails = null;
								
								// DEBE SER DE TIPO EOC LA ORDEN EXISTENTE					
								if (Optional.ofNullable(order.getEocCreatedDate()).isPresent() && !Optional.of(order.getEocCreatedDate()).isEmpty()) {
									
									// COMPLETAMOS LOS DATOS DE EOC
									completeOrder = fillEodData(mapMessage, order);
									completeOrder.setVev(false);
									completeOrder.setComplete(true);
									
									// TRAEMOS LOS DETALLES DE LA OC
									List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());	
									
									if (Optional.ofNullable(orderDetails).isPresent() && !Optional.of(orderDetails).isEmpty()) {
																		
										completeDetails = fillEodDetailData(lineas, headersArray, orderDetails, order);									 
									}	
																
									// COMPLETAMOS LOS DATOS DE LA PREDISTRIBUCION
									for (OrderDetail orderDetail : completeDetails) {	
										if (!predDataMap.containsKey(orderDetail.getId()))
											predDataMap.put(orderDetail.getId(), fillEodPreDeliveryDetailData(lineas, headersArray, orderDetail));								
									}								
								}
								else
									// LA ORDEN PREVIA NO ES DE TIPO EOC
									LOGGER.info("Para N° EOD " + ocNumber + " la orden previa no es de tipo EOC");								
													
								// ACTUALIZA LA EOD
								if (completeOrder != null)
									orderRepository.save(completeOrder);
								
								// ACTUALIZA LOS DETALLES DE EOD
								if (completeDetails != null)
									orderDetailRepository.saveAll(completeDetails);															
								
								// GUARDA LOS DETALLES DE PREDISTRIBUCION
								if (predDataMap != null)
									predDataMap.forEach((k ,v) -> {									
										preDeliveryDetailRepository.saveAll(v);
									});			
								
								LOGGER.info("N° EOD " + ocNumber + " cargado exitosamente");
								
							}
							else {
								// EL MENSAJE SE RECHAZA
								LOGGER.info("Para N° EOD " + ocNumber + " la orden ya esta completa");						
							}						
						}	
						else {
							
							// MENSAJE DE EOD NUEVO
							Order inCompleteOrder = fillEodData(mapMessage, null);
							inCompleteOrder.setVendor(vendor);
							inCompleteOrder.setCurrentSoaStateType(toNotify);
							inCompleteOrder.setCurrentSoaStateTypeDate(LocalDateTime.now());
							inCompleteOrder.setVev(false);
							inCompleteOrder.setComplete(false);
							
							// GUARDA EOD
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
							
							// BUSCA LOS DETALLES DE EOD
							List<OrderDetail> inCompleteDetails = fillEodDetailData(lineas, headersArray, null, inCompleteOrder);
							
							// GUARDA LOS DETALLES DE EOD
							inCompleteDetails = orderDetailRepository.saveAll(inCompleteDetails);
							
							// COMPLETAMOS LOS DATOS DE PREDISTRIBUCION
							for (OrderDetail orderDetail : inCompleteDetails) {	
								if (!predDataMap.containsKey(orderDetail.getId()))
									predDataMap.put(orderDetail.getId(), fillEodPreDeliveryDetailData(lineas, headersArray, orderDetail));								
							}	
							
							// GUARDA LOS DETALLES DE PREDISTRIBUCION
							predDataMap.forEach((k ,v) -> {									
								preDeliveryDetailRepository.saveAll(v);
							});						
							
							
							LOGGER.info("N° EOD " + ocNumber + " cargado exitosamente");
						}					
					}			
				}					
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private Order fillEodData(Map<String, String> mapMessage, Order order) throws ParseException, OperationFailedException{
		
		Order newOrder = new Order();
		
		// COPIA LOS DATOS EXISTENTES SI LOS HAY
		if (order != null)
			BeanUtils.copyProperties(order, newOrder);	
		
		// NUMERO DE OC
		newOrder.setOcNumber(getLongField(mapMessage, "NRO_OC", true));
		
		// FECHA CREACION EOD
		newOrder.setEodCreatedDate(LocalDateTime.now());
		
		// NUMERO OD
		newOrder.setOdNumber(getLongField(mapMessage, "NRO_OD", true));
				
		String fechaTransformada = "";
		
		// FECHA DESCARGA EOD
		if (getDateField(mapMessage, "FECHA_DESCARGA_OD", false) != null) {
			fechaTransformada = mapMessage.get("FECHA_DESCARGA_OD");
			fechaTransformada.replace("/", "-");
			LocalDate downloadDate = LocalDate.parse(fechaTransformada, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			newOrder.setEodDownloadDate(downloadDate);
		}	
		
		// CODIGO LOCAL DE ENTREGA
		newOrder.setEodLocalCode(getField(mapMessage, "COD_L_ENTREGA", true));
		
		// DESCRIPCION LOCAL DE ENTREGA
		newOrder.setEodLocalDescription(getField(mapMessage, "DESC_L_ENTREGA", true));
		
		// DIRECCION LOCAL DE ENTREGA
		newOrder.setEodLocalAddress(getField(mapMessage, "DIREC_L_ENTREGA", false));	
		
		return newOrder;		
	}
	
	private List<OrderDetail> fillEodDetailData(List<String> lineas, String[] headersArray, List<OrderDetail> orderDetails, Order order) throws OperationFailedException{
		
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
			
			// NUMERO DE PALLETS
			newOrderDetail.setPalletLayerAmount(getDoubleField(mapMessage, "NRO_CAPAS_PALET", false));		
				
			// CANTIDAD DE EMPAQUE DE PALLETS 
			newOrderDetail.setPalletLayerPack(getDoubleField(mapMessage, "EMPAQ_CAPA_PALET", false));
			
			// HALL
			newOrderDetail.setHall(getField(mapMessage, "PASILLO", false));
			
			// LINEAL
			newOrderDetail.setLineal(getField(mapMessage, "LINEAL", false));
			
			// TOTAL DE UNIDADES
			newOrderDetail.setTotalUnits(getDoubleField(mapMessage, "CANTIDAD_PROD", true));
			
			//  TODO DESCUENTO TOTAL DE PRODUCTO
			newOrderDetail.setItemTotalDiscount(0D);
			
			newOrderDetails.add(newOrderDetail);
		}
		
		return newOrderDetails;		
	}
	
	private List<PreDeliveryDetail> fillEodPreDeliveryDetailData(List<String> lineas, String[] headersArray, OrderDetail orderDetail) throws OperationFailedException{
		
		Map<String, String> mapMessageSku = new HashMap<String, String>();		
		Map<String, String> mapMessage = new HashMap<String, String>();
		
		List<PreDeliveryDetail> newPredeliveryDetails = new ArrayList<PreDeliveryDetail>();
			
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
			
			PreDeliveryDetail newPreDeliveryDetail = new PreDeliveryDetail();
			
			// SKU
			String sku = getField(mapMessage, "SKU", true);
			
			//Valido si el sku por parametro es igual al que viene en las lineas.
			if(sku.equals(orderDetail.getSku())) {
				
				// DETALLE DE OC 
				newPreDeliveryDetail.setOrderDetail(orderDetail);
				
				newPreDeliveryDetail.setSku(sku);
				
				// NUMERO DE TIENDA
				newPreDeliveryDetail.setStoreNumber(getField(mapMessage, "NRO_TIENDA", true));
				
				// TIENDA
				newPreDeliveryDetail.setStore(getField(mapMessage, "TIENDA", true));
				
				// CANTIDAD DE PRODUCTO
				newPreDeliveryDetail.setProductAmount(getDoubleField(mapMessage, "CANTIDAD_PROD", true));
				
				// CANTIDAD EMPAQUES
				newPreDeliveryDetail.setPackAmount(getDoubleField(mapMessage, "CANTIDAD_EMPAQ", false));
				
				newPredeliveryDetails.add(newPreDeliveryDetail);				
			}	
		}
		
		return newPredeliveryDetails;		
	}
	
	private String getField(Map<String, String> mappedfields, String key, boolean isRequired) throws OperationFailedException {
		String field = mappedfields.get(key);
		if (isRequired && (field == null)) {
			throw new OperationFailedException("Campo " + key + " nulo o ausente");
		}
		return field.trim();
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
