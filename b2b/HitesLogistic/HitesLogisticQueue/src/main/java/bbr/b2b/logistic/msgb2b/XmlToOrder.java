package bbr.b2b.logistic.msgb2b;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderAttributeServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.buyorders.classes.ResponsableServerLocal;
import bbr.b2b.logistic.buyorders.classes.RetailerServerLocal;
import bbr.b2b.logistic.buyorders.classes.SectionServerLocal;
import bbr.b2b.logistic.buyorders.data.wrappers.ClientW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderAttributeW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.logistic.buyorders.data.wrappers.ResponsableW;
import bbr.b2b.logistic.buyorders.data.wrappers.RetailerW;
import bbr.b2b.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryServerLocal;
import bbr.b2b.logistic.ddcdeliveries.classes.DdcDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateTypeW;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryW;
import bbr.b2b.logistic.ddcorders.classes.DdcChargeDiscountServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderChargeDiscountDetailServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderChargeDiscountServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderDetailServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateServerLocal;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderStateTypeServerLocal;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcChargeDiscountW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderChargeDiscountDetailW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderChargeDiscountW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderDetailW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateTypeW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderStateW;
import bbr.b2b.logistic.ddcorders.data.wrappers.DdcOrderW;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrDeliveryStateTypeServerLocal;
import bbr.b2b.logistic.dvrdeliveries.classes.DvrOrderDeliveryServerLocal;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryStateTypeW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrDeliveryW;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DvrOrderDeliveryW;
import bbr.b2b.logistic.dvrorders.classes.ChargeDiscountDvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.ChargeDiscountDvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.ChargeDiscountServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderDetailServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrOrderStateTypeServerLocal;
import bbr.b2b.logistic.dvrorders.classes.DvrPreDeliveryDetailServerLocal;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderW;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateTypeW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderStateW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrOrderW;
import bbr.b2b.logistic.dvrorders.data.wrappers.DvrPreDeliveryDetailW;
import bbr.b2b.logistic.items.classes.ItemAttributeServerLocal;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.logistic.items.data.wrappers.ItemAttributeW;
import bbr.b2b.logistic.items.data.wrappers.ItemW;
import bbr.b2b.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.logistic.soa.classes.SOAStateServerLocal;
import bbr.b2b.logistic.soa.classes.SOAStateTypeServerLocal;
import bbr.b2b.logistic.soa.data.classes.SOAStateTypeW;
import bbr.b2b.logistic.soa.data.classes.SOAStateW;
import bbr.b2b.logistic.utils.CommonQueueUtils;
import bbr.b2b.logistic.utils.EmailValidation;
import bbr.b2b.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.logistic.xml.order.Orden;
import bbr.b2b.logistic.xml.order.Orden.ListadoProductos.Producto;
import bbr.b2b.logistic.xml.order.Orden.ListadoProductos.Producto.Cargos.Cargo;
import bbr.b2b.logistic.xml.order.Orden.ListadoProductos.Producto.Descuentos.Descuento;
import bbr.b2b.logistic.xml.order.Orden.ListadoProductos.Producto.OtrosAtributosProducto.Atributo;
import bbr.b2b.logistic.xml.order.Orden.MaestroLocales.Local;
import bbr.b2b.logistic.xml.order.Orden.Predistribuciones.Predistribucion;


@Stateless(name = "handlers/XmlToOrder")
public class XmlToOrder implements XmlToOrderLocal {

	// Acciones del Mensaje
	private static final String ACCION_CREAR 				= "CREAR";
	private static final String ACCION_MODIFICAR 			= "MODIFICAR";
	private static final String ACCION_CANCELAR 			= "CANCELAR";
	
	// Tipos de Orden en el mensaje
	private static final String OCTYPE_MSG_DVH 				= "DVH";
	private static final String OCTYPE_MSG_DDC				= "DDC";
	private static final String OCTYPE_MSG_PREDISTRIBUIDA	= "PREDISTRIBUIDA";
	
	// Proveedor
	private static final String VENDORTYPE_NACIONAL 		= "Nacional";
//	private static final String VENDORTYPE_IMPORTADO 		= "Importado";
	
	// Descuentos Generales
	private static final String GENERAL_DESC_MONTO 			= "monto";
	private static final String GENERAL_DESC_PORC 			= "porcentaje";
	
	// Descuentos Productos
	private static final String PROD_DESC_MONTO 			= "monto";
	private static final String PROD_DESC_PORC 				= "porcentaje";
	
	// Cargos Productos
	private static final String PROD_CARGO_MONTO 			= "monto";
	private static final String PROD_CARGO_PORC 			= "porcentaje";
	

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanager;
		
	@EJB
	ChargeDiscountServerLocal chargediscountserver;
	
	@EJB
	ChargeDiscountDvrOrderServerLocal chargediscountdvrorderserver;
	
	@EJB
	ChargeDiscountDvrOrderDetailServerLocal chargediscountdvrorderdetailserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;
	
	@EJB
	DvrDeliveryStateTypeServerLocal dvrdeliverystatetypeserver;
	
	@EJB
	DvrOrderDeliveryServerLocal dvrorderdeliveryserver;
	
	@EJB
	DvrOrderServerLocal dvrorderserver;
	
	@EJB
	DvrOrderStateTypeServerLocal dvrorderstatetypeserver;

	@EJB
	DvrOrderStateServerLocal dvrorderstateserver;
	
	@EJB
	DvrOrderDetailServerLocal dvrorderdetailserver;
	
	@EJB
	DvrPreDeliveryDetailServerLocal dvrpredeliverydetailserver;
	
	@EJB
	DdcOrderServerLocal ddcorderserver;
	
	@EJB
	DdcOrderStateTypeServerLocal ddcorderstatetypeserver;

	@EJB
	DdcOrderStateServerLocal ddcorderstateserver;
	
	@EJB
	DdcOrderDetailServerLocal ddcorderdetailserver;
	
	@EJB
	DdcChargeDiscountServerLocal ddcchargediscountserver;
	
	@EJB
	DdcOrderChargeDiscountServerLocal ddcorderchargediscountserver;
	
	@EJB
	DdcOrderChargeDiscountDetailServerLocal ddcorderchargediscountdetailserver;
	
	@EJB
	DdcDeliveryServerLocal ddcdeliveryserver;
	
	@EJB
	DdcDeliveryStateTypeServerLocal ddcdeliverystatetypeserver;
	
	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderAttributeServerLocal orderattributeserver;
	
	@EJB
	OrderTypeServerLocal ordertypeserver;
	
	@EJB
	ItemServerLocal itemserver;
	
	@EJB
	ItemAttributeServerLocal itemattributeserver;
	
	@EJB
	LocationServerLocal locationserver;
	
	@EJB
	ResponsableServerLocal responsableserver;
	
	@EJB
	RetailerServerLocal retailerserver;

	@EJB
	SectionServerLocal sectionserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	VendorItemServerLocal vendoritemserver;
	
	@EJB
	SOAStateTypeServerLocal soastatetypeserver;

	@EJB
	SOAStateServerLocal soastateserver;
		
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();
	
	private String datePattern = "yyyyMMdd";
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);	
	
	private void doValidateOrderMessage(Orden orderParser) throws LoadDataException {
		
		// Set para locales
		Set<String> localCodeSet = new HashSet<String>();
		// Set para items
		Set<String> itemCodeSet = new HashSet<String>();
		// Set para unicidad de productos y posición 
		// Se usa como clave identificador la posisción y el SKU
		Set<String> productMsgSet = new HashSet<String>();		
		
		// Mapa para totales de unidades de productos
		// Key: codigo producto
		Map<String, Double> productoTotalMap = new HashMap<String, Double>(); 
		// Mapa para totales de las predistribución
		// Key: codigo producto
		Map<String, Double> predistribucionTotalMap = new HashMap<String, Double>();		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Locales
		if (orderParser.getMaestroLocales() == null) {
			throw new LoadDataException("No existe tag de Maestro de Locales");
		}
		
		if (orderParser.getMaestroLocales().getLocal() != null) {
			for (Local location : orderParser.getMaestroLocales().getLocal()) {
				// Valida que se informe codigo de local
				qutils.datoObligatorio(location.getCodigo(), "No se especifica valor para código de local");
				
				// Valida que se informe nombre de local
				qutils.datoObligatorio(location.getNombre(), "No se especifica valor para nombre de local");
				
				// Agrega local a Set para validaciones posteriores
				localCodeSet.add(location.getCodigo().trim());
			}
		}
				
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Encabezado
		if (orderParser.getEncabezado() == null) {
			throw new LoadDataException("No existe tag de Encabezado");
		}
		
		// Tag acción
		qutils.datoObligatorio(orderParser.getEncabezado().getAccion(), "No se informa Acción");
		
		// Nro de  OC
		qutils.datoObligatorio(orderParser.getEncabezado().getNumOc(), "No se informa Número de OC");

		// Tipo de OC
		qutils.datoObligatorio(orderParser.getEncabezado().getTipoOc(), "No se informa Tipo de OC");
		
		// Monto Neto
		qutils.datoObligatorio(orderParser.getEncabezado().getMontoNeto(), "No se informa Monto Neto de OC");
		
		// Fecha de emisión
		qutils.datoObligatorio(orderParser.getEncabezado().getFechaEmision(), "No se informa valor para fecha de emisión");
		
		// Valida formato de fecha
		String emittedDateStr = orderParser.getEncabezado().getFechaEmision().trim();
		try {
			qutils.getDate(emittedDateStr, datePattern);
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la Fecha de Emisión debe ser " + datePattern);
		}
		
		// Valida que sea una fecha real
		try {
			qutils.getLocalDate(emittedDateStr, datePattern);
		} catch (DateTimeParseException e) {
			throw new LoadDataException("La Fecha de Emisión debe ser válida");
		}

		// Fecha de entrega
		qutils.datoObligatorio(orderParser.getEncabezado().getFechaEntrega(), "No se informa valor para fecha de entrega");
		
		// Valida formato de fecha
		String deliveryDateStr = orderParser.getEncabezado().getFechaEntrega().trim();
		try {
			qutils.getDate(deliveryDateStr, datePattern);
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la Fecha de Entrega debe ser " + datePattern);
		}
		
		// Valida que sea una fecha real
		try {
			qutils.getLocalDate(deliveryDateStr, datePattern);
		} catch (DateTimeParseException e) {
			throw new LoadDataException("La Fecha de Entrega debe ser válida");
		}
		
		// Fecha de vencimiento
		qutils.datoObligatorio(orderParser.getEncabezado().getFechaVencimiento(), "No se informa valor para fecha de vencimiento");
		
		// Valida formato de fecha
		String expirationDateStr = orderParser.getEncabezado().getFechaVencimiento().trim();
		try {
			qutils.getDate(expirationDateStr, datePattern);
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la Fecha de Vencimiento debe ser " + datePattern);
		}
		
		// Valida que sea una fecha real
		try {
			qutils.getLocalDate(expirationDateStr, datePattern);
		} catch (DateTimeParseException e) {
			throw new LoadDataException("La Fecha de Vencimiento debe ser válida");
		}

		// Comprador
		if (orderParser.getEncabezado().getComprador() == null) {
			throw new LoadDataException("No existe tag de Comprador");
		}

		// Código comprador
		qutils.datoObligatorio(orderParser.getEncabezado().getComprador().getCodigo(), "No se informa valor para Código de comprador");
		
		// Rut comprador
		qutils.datoObligatorio(orderParser.getEncabezado().getComprador().getRut(), "No se informa valor para RUT de comprador");
		
		// Razón social comprador
		qutils.datoObligatorio(orderParser.getEncabezado().getComprador().getRazonSocial(), "No se informa valor para Razón Social de comprador");
		
		// Proveedor
		if (orderParser.getEncabezado().getProveedor() == null) {
			throw new LoadDataException("No existe tag de Proveedor");
		}
		
		// Código proveedor
		qutils.datoObligatorio(orderParser.getEncabezado().getProveedor().getCodigo(), "No se informa valor para Código de proveedor");
		
		// RUT proveedor
		qutils.datoObligatorio(orderParser.getEncabezado().getProveedor().getRut(), "No se informa valor para RUT de proveedor");
		
		// Razón social proveedor	
		qutils.datoObligatorio(orderParser.getEncabezado().getProveedor().getRazonSocial(), "No se informa valor para Razón Social de proveedor");
		
		// Atributos de orden
		if (orderParser.getEncabezado().getOtrosAtributosOrden() == null) {
			throw new LoadDataException("No existe tag de Atributos de Orden");
		}
					
		// Valida si existe listado de atributos
		if (orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo() != null) {
			for (bbr.b2b.logistic.xml.order.Orden.Encabezado.OtrosAtributosOrden.Atributo atributo : orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo()) {
				// Tipo Atributo
				qutils.datoObligatorio(atributo.getTipoAtributo(), "No se informa valor para Tipo de Atributo de Orden");
				
				// Código
				qutils.datoObligatorio(atributo.getCodigo(), "No se informa valor para Código de Atributo de Orden");
				
				// Valor
				qutils.datoObligatorio(atributo.getValor(), "No se informa Valor de Atributo de Orden");
			}
		}
		
		// Datos VeV
		// Se valida solo si las OC es del tipo DDC o DVH
		if (orderParser.getEncabezado().getTipoOc().trim().equalsIgnoreCase(OCTYPE_MSG_DVH) ||
				orderParser.getEncabezado().getTipoOc().trim().equalsIgnoreCase(OCTYPE_MSG_DDC)) {
			if (orderParser.getEncabezado().getDatosVev() == null) {
				throw new LoadDataException("No existen datos de cliente asociados a la orden de compra número" +  orderParser.getEncabezado().getNumOc());
			}
						
			// RUT Cliente
			qutils.datoObligatorio(orderParser.getEncabezado().getDatosVev().getRutCliente(), "No se informa valor para RUT Cliente VeV");
			
			// Nombre Cliente
			qutils.datoObligatorio(orderParser.getEncabezado().getDatosVev().getNombreCliente(), "No se informa valor para Nombre Cliente VeV");
			
			// Dirección Cliente
			qutils.datoObligatorio(orderParser.getEncabezado().getDatosVev().getDireccionCliente(), "No se informa valor para Dirección Cliente VeV");
			
			// Comuna cliente
			qutils.datoObligatorio(orderParser.getEncabezado().getDatosVev().getComunaCliente(), "No se informa valor para Comuna Cliente VeV");
			
			// Referencia venta
			qutils.datoObligatorio(orderParser.getEncabezado().getDatosVev().getReferenciaVenta(), "No se informa valor para Referencia Venta Cliente VeV");

			// Código local de Venta
			// En caso que se haya enviado el campo ‘cod_local_venta’, este local debe estar informado en el maestro de locales. 
			// De lo contrario el sistema genera un error
			if (orderParser.getEncabezado().getDatosVev().getCodLocalVenta() != null && !orderParser.getEncabezado().getDatosVev().getCodLocalVenta().trim().equals("") &&
					!localCodeSet.contains(orderParser.getEncabezado().getDatosVev().getCodLocalVenta().trim())) {
				throw new LoadDataException("El local de venta enviado para el cliente con RUT " + orderParser.getEncabezado().getDatosVev().getRutCliente().trim() + ", debe estar informado en el maestro de locales");
			}
		}
		
		// Local de entrega
		// Se valida excepto si la OC es del tipo DDC  
		if (!orderParser.getEncabezado().getTipoOc().trim().equalsIgnoreCase(OCTYPE_MSG_DDC)) {
			qutils.datoObligatorio(orderParser.getEncabezado().getLocalEntrega(), "No se informa valor para Local de Entrega");
			
			// Valida que local sea informado en el maestro
			if (!localCodeSet.contains(orderParser.getEncabezado().getLocalEntrega().trim())) {
				throw new LoadDataException("El local " + orderParser.getEncabezado().getLocalEntrega().trim() + " no fue informado en maestro de locales");
			}
		}
		
		// Descuentos generales
		// Si existe tag de descuentos generales se valida si existe listado de descuentos
		if (orderParser.getDescuentosGenerales() != null && orderParser.getDescuentosGenerales().getDescuento() != null) {
			for(bbr.b2b.logistic.xml.order.Orden.DescuentosGenerales.Descuento descuento : orderParser.getDescuentosGenerales().getDescuento()) {
				// Tipo Descuento
				qutils.datoObligatorio(descuento.getTipoDescuento(), "No se informa valor para Tipo de descuentos generales");
				
				// Valida que en los tipos de descuento se informe un valor válido
				if (!descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_MONTO) &&
						!descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_PORC)) {
					throw new LoadDataException("Valor de tipo de descuento en Descuentos Generales no es válido");
				}
				
				// Porcentaje de descuento
				qutils.datoObligatorio(descuento.getValorDescuentoPorc(), "No se informa valor para porcentaje en descuentos generales");
			}
		}
				
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Listado de productos
		if (orderParser.getListadoProductos().getProducto() == null) {
			throw new LoadDataException("No se especifica tag para listado de productos");
		}
		
		// Valida que se informen los productos
		if (orderParser.getListadoProductos().getProducto().size() == 0) {
			throw new LoadDataException("No existen productos asociados a la orden de compra");
		}
		
		for (Producto producto : orderParser.getListadoProductos().getProducto()) {
			// Acción
			qutils.datoObligatorio(producto.getAccion(), "No se informa valor para acción del producto");
			
			// Posicion
			qutils.datoObligatorio(producto.getPosicion(), "No se informa valor para posición del producto");
			
			// Código Producto
			qutils.datoObligatorio(producto.getCodProducto(), "No se informa valor para Código de producto");
				
			// Código de Barra 1
			qutils.datoObligatorio(producto.getCodBarra1(), "No se informa valor para Código de Barra");
			
			// Descripción	
			qutils.datoObligatorio(producto.getDescripcion(), "No se informa valor para Descripción de Producto");

			// Unidad de Medida Base
			qutils.datoObligatorio(producto.getUnidadMedidaBase(), "No se informa valor para unidad de medida base");
			
			// Unidad de Medida Conpra
			qutils.datoObligatorio(producto.getUnidadMedidaCompra(), "No se informa valor para unidad de medida compra");
			
			// Contenido
			qutils.datoObligatorio(producto.getContenido(), "No se informa valor para Contenido del Producto");
			
			// Cantidad Solicitada UMC
			qutils.datoObligatorio(producto.getCantidadSolicitadasUmc(), "No se informa valor para Cantidad Solicitada UMC");
			
			// Costo Compra
			qutils.datoObligatorio(producto.getCostoCompra(), "No se informa valor para Cantidad Solicitada UMC");
			
			// Valida que la combinación SKU - Posición no se repita en el maestro de productos
			String productPK = producto.getPosicion() + "_" + producto.getCodProducto().trim();
			if (!productMsgSet.contains(productPK)) {
				productMsgSet.add(productPK);
			}
			else {
				throw new LoadDataException("La combinación producto con código SKU " + producto.getCodProducto().trim() + " y posición " + producto.getPosicion() + " se encuentra repetida en la OC " +  orderParser.getEncabezado().getNumOc());				
			}

			// Atributos de los productos
			if (producto.getOtrosAtributosProducto() == null) {
				throw new LoadDataException("No se expecifica tag para Atributos de Producto");
			}
						
			// Valida si existe listado de atributos
			if (producto.getOtrosAtributosProducto().getAtributo() != null) {
				for (Atributo atributo : producto.getOtrosAtributosProducto().getAtributo()) {
					// Tipo Atributo
					qutils.datoObligatorio(atributo.getTipoAtributo(), "No se informa valor para Tipo de Atributo de Producto");
					
					// Código
					qutils.datoObligatorio(atributo.getCodigo(), "No se informa valor para Código de Atributo de Producto");
					
					// Valor
					qutils.datoObligatorio(atributo.getValor(), "No se informa Valor de Atributo de Producto");
				}
			}
						
			// Descuentos 
			if (producto.getDescuentos() != null && producto.getDescuentos().getDescuento() != null) {
				for (Descuento descuento : producto.getDescuentos().getDescuento()) {
					// Tipo Descuento
					qutils.datoObligatorio(descuento.getTipoDescuento(), "No se informa valor para Tipo de descuento de Producto");
					
					// Valida que en los tipos de descuento se informe un valor válido
					if (!descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_MONTO) &&
							!descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_PORC)) {
						throw new LoadDataException("Valor de tipo de descuento en Descuentos de Producto no es válido");
					}
					
					// Valor descuento Porcentaje
					qutils.datoObligatorio(descuento.getTipoDescuento(), "No se informa valor para Tipo de descuento de Producto");
				}
			}
			
			// Cargos
			if (producto.getCargos() != null && producto.getCargos().getCargo() != null) {
				for(Cargo cargo : producto.getCargos().getCargo()) {
					// Tipo Cargo
					qutils.datoObligatorio(cargo.getTipoCargo(), "No se informa valor para Tipo de cargo de Producto");
					
					// Valida que en los tipos de cargo se informe un valor válido
					if (!cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_MONTO) &&
							!cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_PORC)) {
						throw new LoadDataException("Valor de tipo de cargo en Cargos de Producto no es válido");
					}
					
					// Valor descuento Cargo
					qutils.datoObligatorio(cargo.getTipoCargo(), "No se informa valor para Tipo de cargo de Producto");
				}
			}
			
			// Agrega item a Set para validaciones posteriores
			itemCodeSet.add(producto.getCodProducto().trim());
			
			// Almacena unidades informadas en mapa
			productoTotalMap.put(producto.getCodProducto().trim(), producto.getCantidadSolicitadasUmc().doubleValue());
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Predistribuciones
		if (orderParser.getPredistribuciones() == null) {
			throw new LoadDataException("No se expecifica tag para Predistribuciones");
		}

		// JMI 20200117
		// Solo para el tipo de OC "PREDISTRIBUIDA" se valida que se informen las predistribuciones
		// Cualquier otro tipo, se descarta la información 
		if (orderParser.getEncabezado().getTipoOc().trim().equalsIgnoreCase(OCTYPE_MSG_PREDISTRIBUIDA)) {
			if (orderParser.getPredistribuciones().getPredistribucion() == null || orderParser.getPredistribuciones().getPredistribucion().size() == 0) {
				throw new LoadDataException("No se expecifican predistribuciones para la orden " + orderParser.getEncabezado().getNumOc());
			}
						
			// Valida que no se informen líneas repetidas
			Set<String> predistributionMsgSet = new HashSet<String>();			
			for (Predistribucion predistribucion : orderParser.getPredistribuciones().getPredistribucion()) {
				// Posición
				qutils.datoObligatorio(predistribucion.getPosicion(), "No se informa valor para Posición en predistribución de producto");
				
				// Código Local
				qutils.datoObligatorio(predistribucion.getCodLocal(), "No se informa valor para Código Local en predistribución de producto");
				
				// Valida que local sea informado en el maestro
				if (!localCodeSet.contains(predistribucion.getCodLocal().trim())) {
					throw new LoadDataException("El local " + predistribucion.getCodLocal().trim() + " no fue informado en maestro de locales");
				}
				
				// Código Producto
				qutils.datoObligatorio(predistribucion.getCodProducto(), "No se informa valor para Código Producto en predistribución de producto");
				
				// Valida que producto sea informado en el maestro
				if (!itemCodeSet.contains(predistribucion.getCodProducto().trim())) {
					throw new LoadDataException("El producto " + predistribucion.getCodProducto().trim() + " no fue informado en el listado de productos");
				}
				
				// Unidades solcitadas umc
				qutils.datoObligatorio(predistribucion.getUnidadesSolicitadasUmc(), "No se informa valor para Unidades Solicitadas UMC en predistribución de producto");
				
				// Carga mapa con unidades informadas 
				String key = predistribucion.getCodProducto().trim();
				Double totalUnits = predistribucion.getUnidadesSolicitadasUmc().doubleValue();
				if (!predistribucionTotalMap.containsKey(key)) {
					predistribucionTotalMap.put(key, totalUnits);
				}
				else {
					Double newTotal = predistribucionTotalMap.get(key) + totalUnits;
					predistribucionTotalMap.put(key, newTotal);
				}
				
				// Valida que la combinación orden-local-producto no se repita
				String predistributionPK = predistribucion.getCodLocal().trim() + "_" + predistribucion.getCodProducto().trim();
				if (!predistributionMsgSet.contains(predistributionPK)) {
					predistributionMsgSet.add(predistributionPK);
				}
				else {
					throw new LoadDataException("La predistribución para la orden " + orderParser.getEncabezado().getNumOc() + ", el producto SKU " + predistribucion.getCodProducto().trim() + " y el local " + predistribucion.getCodLocal().trim() + " se encuentra repetida");				
				}
				
				// Valida que producto local informado exista en el maestro de productos
				String positionProductKey = predistribucion.getPosicion()  + "_" + predistribucion.getCodProducto().trim();
				if (!productMsgSet.contains(positionProductKey)) {
					throw new LoadDataException("En la predistribución el producto " + predistribucion.getCodProducto().trim() + " en la posición " + predistribucion.getPosicion() + " no fue informado en el maestro de productos");
				}
			}
			
			// Valida que el total de productos en la predistribución coincida con lo solicitado para la OC
			// Itera sobre mapa de la predistribución
		    for (Map.Entry<String, Double> entry : predistribucionTotalMap.entrySet()){
		    	Double totalProd = productoTotalMap.get(entry.getKey());
		    	Double totalpred = entry.getValue();
		    	if(! totalProd.equals(totalpred)) {
		    		throw new LoadDataException("Para el producto con SKU " + entry.getKey() + ", el total a nivel de pre-distribución (" + totalpred + " unidades) no coincide con lo solicitado para el producto (" + totalProd + "unidades)");
		    	}
		    }			
		}
	}
	
	private void doValidateOrderMessageToModify(Orden orderParser) throws LoadDataException {
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (orderParser.getEncabezado() == null) {
			throw new LoadDataException("No existe tag de Encabezado");
		}
		
		// Tag acción
		qutils.datoObligatorio(orderParser.getEncabezado().getAccion(), "No se informa un valor de acción");
		
		// Nro de  OC
		qutils.datoObligatorio(orderParser.getEncabezado().getNumOc(), "No se informa Número de OC");

		// Tipo de OC
		qutils.datoObligatorio(orderParser.getEncabezado().getTipoOc(), "No se informa Tipo de OC");

		// Fecha de vencimiento
		qutils.datoObligatorio(orderParser.getEncabezado().getFechaVencimiento(), "No se informa valor para fecha de vencimiento");
		
		// Valida formato de fecha
		String expirationDateStr = orderParser.getEncabezado().getFechaVencimiento().trim();
		try {
			qutils.getDate(expirationDateStr, datePattern);
		} catch (ParseException e) {
			throw new LoadDataException("El formato de la Fecha de Vencimiento debe ser " + datePattern);
		}
		
		// Valida que sea una fecha real
		try {
			qutils.getLocalDate(expirationDateStr, datePattern);
		} catch (DateTimeParseException e) {
			throw new LoadDataException("La Fecha de Vencimiento debe ser válida");
		}		
	}
	
	private void doValidateOrderMessageToCancel(Orden orderParser) throws LoadDataException {
		
		// Encabezado
		if (orderParser.getEncabezado() == null) {
			throw new LoadDataException("No existe tag de Encabezado");
		}
		
		// Tag acción
		qutils.datoObligatorio(orderParser.getEncabezado().getAccion(), "No se informa un valor de acción");
		
		// Nro de  OC
		qutils.datoObligatorio(orderParser.getEncabezado().getNumOc(), "No se informa Número de OC");

		// Tipo de OC
		qutils.datoObligatorio(orderParser.getEncabezado().getTipoOc(), "No se informa Tipo de OC");
	}
	
	private RetailerW doAddOrGetRetailer(Orden orderParser) throws NotFoundException, OperationFailedException, AccessDeniedException{
		
		RetailerW retailerW; 
		String retailerCode = orderParser.getEncabezado().getComprador().getCodigo().trim();

		// Si retailer no existe lo crea
		RetailerW[] retailerArr = retailerserver.getByPropertyAsArray("code", retailerCode);
		if(retailerArr.length == 0){
			retailerW = new RetailerW();
			retailerW.setCode(retailerCode);
			
			retailerW = retailerserver.addRetailer(retailerW);
		}		
		else {
			retailerW = retailerArr[0];
		}
		
		return retailerW;
	}
	
	private VendorW doAddOrUpdateVendor(Orden orderParser) throws NotFoundException, OperationFailedException, AccessDeniedException {
		
		VendorW vendorW;

		
		String vendorcode = orderParser.getEncabezado().getProveedor().getCodigo().trim();
		String vendorrut = orderParser.getEncabezado().getProveedor().getRut().trim();
		String vendorname = orderParser.getEncabezado().getProveedor().getRazonSocial().trim();
		String vendoraddress = "";
		String vendorcity = "";
		String vendortype = "";
		
		// Dirección
		try{
			if(orderParser.getEncabezado().getProveedor().getDireccion() != null){
				vendoraddress = orderParser.getEncabezado().getProveedor().getDireccion().trim();
			}
		} catch (Exception e) {
			vendoraddress = "";
		}
		
		// Ciudad
		try{
			if(orderParser.getEncabezado().getProveedor().getCiudad() != null){
				vendorcity = orderParser.getEncabezado().getProveedor().getCiudad().trim();
			}
		} catch (Exception e) {
			vendorcity = "";
		}
		
		// Tipo
		try{
			if(orderParser.getEncabezado().getProveedor().getTipo() != null){
				vendortype = orderParser.getEncabezado().getProveedor().getTipo().trim();
			}
		} catch (Exception e) {
			vendortype = "";
		}
		
		boolean isdomestic = true;
		if(vendortype != "") {
			isdomestic = vendortype.equalsIgnoreCase(VENDORTYPE_NACIONAL) ? true : false;
		}
		
		VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", vendorcode);
		// Crea 
		if(vendorArr.length == 0){
			vendorW = new VendorW();

			vendorW.setCode(vendorcode);
			vendorW.setName(vendorname);
			vendorW.setDni(vendorrut);
			vendorW.setTradename(vendorname);
			vendorW.setAddress(vendoraddress);
			vendorW.setCity(vendorcity);
			vendorW.setDomestic(isdomestic);
			vendorW.setMincorrelative(0);
			
			vendorW = vendorserver.addVendor(vendorW);
		}
		
		// Actualiza
		else{
			vendorW = vendorArr[0];
			vendorW.setCode(vendorcode);
			vendorW.setName(vendorname);
			vendorW.setDni(vendorrut);
			vendorW.setTradename(vendorname);
			vendorW.setAddress(vendoraddress);
			vendorW.setCity(vendorcity);
			vendorW.setDomestic(isdomestic);
			// no se modifica el mínimo correlativo
			
			vendorW = vendorserver.updateVendor(vendorW);
		}
		
		return vendorW;		
	}
	
	private ResponsableW doAddOrUpdateResponsable(Orden orderParser) throws LoadDataException, NotFoundException, OperationFailedException, AccessDeniedException {

		ResponsableW responsableW = new ResponsableW();
		
		// Busca se si informa tag de responsable
		boolean responsableExists = true;
		
		try {
			orderParser.getEncabezado().getResponsable();
		} catch (Exception e) {
			responsableExists = false;
		}
		
		if(responsableExists) {
			String name = "";
			String email = "";			
			
			try {
				name = orderParser.getEncabezado().getResponsable().getNombre().trim();
			} catch (Exception e) {
				name = "";
			}
			
			boolean valid = true;
			try {
				email = orderParser.getEncabezado().getResponsable().getCorreo().trim();
				
				if (!email.equals("")) {
					valid = EmailValidation.isValidWithUppercase(email);
				}

			} catch (Exception e) {
				email = "";
				valid = true;
			}
			
			if (!valid) {
				throw new LoadDataException("Error en formato de correo del responsable de orden "+ orderParser.getEncabezado().getNumOc());
			}
			
			ResponsableW[] responsableArr = responsableserver.getByPropertyAsArray("name", name);

			// Crea
			if(responsableArr.length == 0){
				responsableW = new ResponsableW();
				responsableW.setCode(name);
				responsableW.setName(name);
				responsableW.setEmail(email);
				responsableW = responsableserver.addResponsable(responsableW);
			}
			
			// Actualiza
			else{
				responsableW = responsableArr[0];
				responsableW = responsableserver.updateResponsable(responsableW);
			}			
		}
		
		return responsableW;		
	}
	
	private ClientW doAddClient(Orden orderParser) throws LoadDataException, NotFoundException, OperationFailedException, AccessDeniedException {
		
		ClientW clientW;
		
		String name = orderParser.getEncabezado().getDatosVev().getNombreCliente().trim();
		String address = orderParser.getEncabezado().getDatosVev().getDireccionCliente().trim();
		String dni = orderParser.getEncabezado().getDatosVev().getRutCliente().trim();
		String commune = orderParser.getEncabezado().getDatosVev().getComunaCliente().trim();
		String city = "";
		String comment = "";
		String phone = "";
		String email = "";
		
		// Ciudad
		try {
			city = orderParser.getEncabezado().getDatosVev().getCiudadCliente().trim();
		} catch (Exception e) {
			city = "";
		}

		// Comentarios
		try {
			comment = orderParser.getEncabezado().getDatosVev().getComentario().trim();
		} catch (Exception e) {
			comment = "";
		}
		
		// Teléfono
		try {
			phone = orderParser.getEncabezado().getDatosVev().getTelefonoCliente().trim();
		} catch (Exception e) {
			phone = "";
		}
				
		// Email
		boolean valid = true;
		try {
			email = orderParser.getEncabezado().getDatosVev().getMailCliente().trim();
			
			if (!email.equals("")) {
				valid = EmailValidation.isValidWithUppercase(email);
			}

		} catch (Exception e) {
			email = "";
			valid = true;
		}
		
		if (!valid) {
			throw new LoadDataException("Error en formato de correo del cliente de orden "+ orderParser.getEncabezado().getNumOc());
		}
		
		// Agrega
		clientW = new ClientW();
		clientW.setName(name);
		clientW.setAddress(address);
		clientW.setDni(dni);
		clientW.setCommune(commune);
		clientW.setCity(city);
		clientW.setComment(comment);
		clientW.setPhone(phone);
		clientW.setEmail(email);
		
		clientW = clientserver.addClient(clientW);		

		return clientW;
	}
	
	private LocationW doAddOrUpdateLocation(Orden orderParser,  int position) throws LoadDataException, NotFoundException, OperationFailedException, AccessDeniedException {
		
		LocationW locationW;
		
		String code = orderParser.getMaestroLocales().getLocal().get(position).getCodigo().trim();
		String name = orderParser.getMaestroLocales().getLocal().get(position).getNombre().trim();
		String address = "";
		String managername = "";
		String manageremail = "";
		
		// Dirección
		try {
			address = orderParser.getMaestroLocales().getLocal().get(position).getDireccion().trim();
		} catch (Exception e) {
			address = "";
		}
		
		// Nombre Encargado
		try {
			managername = orderParser.getMaestroLocales().getLocal().get(position).getNombreEncargado().trim();
		} catch (Exception e) {
			managername = "";
		}
		
		// Correo Encargado
		boolean valid = true;
		try {
			manageremail = orderParser.getMaestroLocales().getLocal().get(position).getEmailEncargado().trim();
			
			if (!manageremail.equals("")) {
				valid = EmailValidation.isValidWithUppercase(manageremail);
			}

		} catch (Exception e) {
			manageremail = "";
			valid = true;
		}
		
		if (!valid) {
			throw new LoadDataException("Error en formato de correo del encargado de local " + code + " de orden "+ orderParser.getEncabezado().getNumOc());
		}
		
		LocationW[] locationArr = locationserver.getByPropertyAsArray("code", code);
		
		// Agrega
		if(locationArr.length == 0){
			locationW = new LocationW();
			locationW.setCode(code);
			locationW.setName(name);
			locationW.setAddress(address);
			locationW.setManagername(managername);
			locationW.setManageremail(manageremail);
			locationW.setWarehouse(false);

			locationW = locationserver.addLocation(locationW);
		}		
		
		// Actualiza
		else{
			locationW = locationArr[0];
			locationW.setName(name);
			locationW.setAddress(address);
			locationW.setManagername(managername);
			locationW.setManageremail(manageremail);
			
			locationW = locationserver.updateLocation(locationW);			
		}
		
		return locationW;		
	}
	
	private ItemW doAddOrUpdateItem(Producto producto, VendorW vendorW) throws AccessDeniedException, OperationFailedException, NotFoundException {
		
		// Mapa con atributos
		Map<String, ItemAttributeW> attributeMap = new HashMap<String, ItemAttributeW>();
		
		ItemW itemW;
		ItemAttributeW itemAttributeW;
		VendorItemW vendorItemW;
		
		String sku = producto.getCodProducto().trim();
		String description = producto.getDescripcion().trim();
		String vendoritemcode = "";
		
		// Código Proveedor
		try {
			vendoritemcode = producto.getCodProveedor().trim();
		} catch (Exception e) {
			vendoritemcode = "";
		}
			
		ItemW[] itemArr = itemserver.getByPropertyAsArray("sku", sku);

		// Crea
		if(itemArr.length == 0){
			itemW = new ItemW();
			itemW.setSku(sku);
			itemW.setName(description);
			itemW = itemserver.addItem(itemW);
		}
		
		// Actualiza
		else {
			itemW = itemArr[0];
			itemW.setName(description);
			itemW = itemserver.updateItem(itemW);
		}
			
		// Atributos del item
		// Busca si exite
		boolean hasAttributes = true;
		try {
			producto.getOtrosAtributosProducto().getAtributo();
		} catch (Exception e) {
			hasAttributes = false;
		}
		
		if(hasAttributes) {
			// Carga Mapa con los atributos que existen actualmente
			// Atributos
			ItemAttributeW[] itemAttributeArr = itemattributeserver.getByPropertyAsArray("item.id", itemW.getId());
			for(ItemAttributeW attributeW : itemAttributeArr) {
				attributeMap.put(attributeW.getAttributetype(), attributeW);
			}
			
			for(Atributo atributo : producto.getOtrosAtributosProducto().getAtributo()) {
				// Si el tipo de atributo informado no  existe, lo crea
				if(! attributeMap.containsKey(atributo.getTipoAtributo().trim())) {
					itemAttributeW = new ItemAttributeW();
					itemAttributeW.setItemid(itemW.getId());
					itemAttributeW.setAttributetype(atributo.getTipoAtributo().trim().toUpperCase());
					itemAttributeW.setCode(atributo.getCodigo().trim());
					itemAttributeW.setValue(atributo.getValor().trim());
					itemAttributeW = itemattributeserver.addItemAttribute(itemAttributeW);
					
				}
				// El atributo ya existe, lo actualiza
				else {
					itemAttributeW = attributeMap.get(atributo.getTipoAtributo().trim());
					itemAttributeW.setAttributetype(atributo.getTipoAtributo().trim().toUpperCase());
					itemAttributeW.setCode(atributo.getCodigo().trim());
					itemAttributeW.setValue(atributo.getValor().trim());
					itemAttributeW = itemattributeserver.updateItemAttribute(itemAttributeW);
				}				
			}
		}
		
		// VendorItem
		PropertyInfoDTO[] props = new PropertyInfoDTO[2];
		props[0] = new PropertyInfoDTO("vendor.id", "vendorid", vendorW.getId());
		props[1] = new PropertyInfoDTO("item.id", "itemid", itemW.getId()); 	
		VendorItemW[] vendoritemArr = vendoritemserver.getByPropertiesAsArray(props);  
	
		// Crear
		if(vendoritemArr.length == 0){
			vendorItemW = new VendorItemW();
			vendorItemW.setItemid(itemW.getId());
			vendorItemW.setVendorid(vendorW.getId());
			vendorItemW.setVendoritemcode(vendoritemcode);
			vendorItemW = vendoritemserver.addVendorItem(vendorItemW);
		}
	
		// Si ya existe vendor item, se valida que el nuevo valor informado sea distinto de vacío
		else if(vendoritemcode != null && ! vendoritemcode.equals("")){
			vendorItemW = vendoritemArr[0];
			vendorItemW.setVendoritemcode(vendoritemcode);
			vendorItemW = vendoritemserver.updateVendorItem(vendorItemW);
		}
	
		return itemW;		
	}

	private void doAddDvrOrderDetail(List<Producto> productList, DvrOrderW dvrorderW, VendorW vendorW, Map<String, ItemW> itemMap) throws NotFoundException, OperationFailedException, AccessDeniedException {

		DvrOrderDetailW dvrorderdetailW;
	
		// El sistema analiza cada una de las líneas de productos que contiene el mensaje.
		for(int i= 0; i < productList.size(); i++) {
			// Nuevo detalle
			dvrorderdetailW = new DvrOrderDetailW();
			dvrorderdetailW.setDvrorderid(dvrorderW.getId());
			
			Producto producto = productList.get(i);

			// Item
			ItemW itemW = itemMap.get(productList.get(i).getCodProducto().trim());
					
			// Agrega item a detalle
			dvrorderdetailW.setItemid(itemW.getId());
			
			// Agrega Position
			dvrorderdetailW.setPosition(producto.getPosicion());
			
			// Costo Lista
			boolean hasbasecost = true;
			try {
				producto.getCostoLista().doubleValue();
			} catch (Exception e) {
				hasbasecost = false;
			}
			if(hasbasecost) {
				dvrorderdetailW.setBasecost(producto.getCostoLista().doubleValue());
			}
			
			// Costo Compra
			dvrorderdetailW.setFinalcost(producto.getCostoCompra().doubleValue());
			
			// Unidad de Medida Base
			dvrorderdetailW.setUmbdescription(producto.getUnidadMedidaBase().trim());
			
			// Unidad de Medida Compra
			dvrorderdetailW.setUmcdescription(producto.getUnidadMedidaBase().trim());
			
			// UMB x UMC	(productpackingrate)
			dvrorderdetailW.setUmbxumc(producto.getContenido().doubleValue());
			
			// Tolerancia
			boolean tolerance = true;
			try {
				producto.getTolerancia().intValue();
			} catch (Exception e) {
				tolerance = false;
			}
			if(tolerance) {
				dvrorderdetailW.setTolerance(producto.getTolerancia().intValue());
			}
			
			// Fecha Entrega de Producto (solo se guarda si viene en el mensaje y con el formato adecuado)
			boolean hasitemdeliverydate = true;
			try {
				LocalDate.parse(producto.getFechaEntrega().trim(), dateFormatter);
			} catch (Exception e) {
				hasitemdeliverydate = false;
			}
			if (hasitemdeliverydate) {
				dvrorderdetailW.setItemdeliverydate(LocalDate.parse(producto.getFechaEntrega().trim(), dateFormatter).atStartOfDay());
			}			
			
//			// Comentarios
//			dvrorderdetailW.setComment(producto.getc);
			
			// Precio Normal
			boolean hasnormalprice = true;
			try {
				producto.getPrecioNormal().doubleValue();	
			} catch (Exception e) {
				hasnormalprice = false;
			}
			if(hasnormalprice) {
				dvrorderdetailW.setNormalprice(producto.getPrecioNormal().doubleValue());
			}
			
			// Precio Oferta
			boolean hasofferprice = true;
			try {
				producto.getPrecioOferta().doubleValue();
			} catch (Exception e) {
				hasofferprice = false;
			}
			if(hasofferprice) {
				dvrorderdetailW.setOfferprice(producto.getPrecioOferta().doubleValue());
			}
			
			// Código Barra 1
			dvrorderdetailW.setBarcode1(producto.getCodBarra1().trim());
			
			// Código Barra 2
			boolean hasbarcode2 = true;
			try {
				producto.getCodBarra2().trim();
			} catch (Exception e) {
				hasbarcode2 = false;
			}
			if(hasbarcode2) {
				dvrorderdetailW.setBarcode2(producto.getCodBarra2().trim());
			}
						
			// Agrega Totales
			dvrorderdetailW.setTotalunits(producto.getCantidadSolicitadasUmc().doubleValue());
			dvrorderdetailW.setPendingunits(0.0);
			dvrorderdetailW.setTodeliveryunits(0.0);
			dvrorderdetailW.setReceivedunits(0.0);
			dvrorderdetailW.setTotalneed(0.0);
			dvrorderdetailW.setTotalpending(0.0);
			dvrorderdetailW.setTotaltodelivery(0.0);
			dvrorderdetailW.setTotalreceived(0.0);
			
			// Crea Detalle
			dvrorderdetailW = dvrorderdetailserver.addDvrOrderDetail(dvrorderdetailW);
			dvrorderdetailserver.flush();
			
			////////////////////////////////////////////////////////////////////////////////////////
			// Busca descuentos
			boolean discountExists = true;
			try {
				producto.getDescuentos().getDescuento();
			} catch (Exception e) {
				discountExists = false;
			}
			
			if(discountExists){
				for(int j = 0; j < producto.getDescuentos().getDescuento().size() ; j++) {
					Descuento descuento = producto.getDescuentos().getDescuento().get(j);
					
					ChargeDiscountW chargediscountW = new ChargeDiscountW();
					
					// Descuento es porcentaje
					if(descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_PORC)) {
						// Se verifica el valor (valor_descuento_porc) y si es distinto de cero se registra este como descuento.
						if(descuento.getValorDescuentoPorc().doubleValue() != 0.0) {
							chargediscountW.setCharge(false);
							chargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
							chargediscountW.setPercentage(true);
							chargediscountW.setValue(descuento.getValorDescuentoPorc().doubleValue());
							chargediscountW.setVisualorder(j);
						}
					}
					
					// Descuento es monto
					else if(descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_MONTO)) {
						 // Se verifica el valor (valor_descuento_monto) y si es distinto de cero se registra este como descuento
						if(descuento.getValorDescuentoMonto().doubleValue() != 0.0) {
							chargediscountW.setCharge(false);
							chargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
							chargediscountW.setPercentage(false);
							chargediscountW.setValue(descuento.getValorDescuentoMonto().doubleValue());
							chargediscountW.setVisualorder(j);
						}
					}					
					chargediscountW = chargediscountserver.addChargeDiscount(chargediscountW);
					chargediscountserver.flush();
					
					// Agrega descuento al detalle
					ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetailW = new ChargeDiscountDvrOrderDetailW();
					chargediscountdvrorderdetailW.setChargediscountid(chargediscountW.getId());
					chargediscountdvrorderdetailW.setDvrorderid(dvrorderdetailW.getDvrorderid());
					chargediscountdvrorderdetailW.setItemid(dvrorderdetailW.getItemid());
					chargediscountdvrorderdetailW.setPosition(dvrorderdetailW.getPosition());
					chargediscountdvrorderdetailW = chargediscountdvrorderdetailserver.addChargeDiscountDvrOrderDetail(chargediscountdvrorderdetailW);					
				}
			}
			
			// Busca cargos
			boolean chargeExists = true;
			try {
				producto.getCargos().getCargo();
			} catch (Exception e) {
				chargeExists = false;
			}
			
			if(chargeExists){
				for(int j = 0; j < producto.getCargos().getCargo().size() ; j++) {
					Cargo cargo = producto.getCargos().getCargo().get(j);
					
					ChargeDiscountW chargediscountW = new ChargeDiscountW();
					
					// Cargo es porcentaje
					if(cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_PORC)) {
						// Se verifica el valor (valor_cargo_porc) y si es distinto de cero se registra este como descuento.
						if(! cargo.getValorCargoPorc().equals(0.0)) {
							chargediscountW.setCharge(true);
							chargediscountW.setDescription(cargo.getDescripcionCargo().trim());
							chargediscountW.setPercentage(true);
							chargediscountW.setValue(cargo.getValorCargoPorc().doubleValue());
							chargediscountW.setVisualorder(j);
						}
					}
					
					// Cargo es monto
					else if(cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_MONTO)) {
						 // Se verifica el valor (valor_cargo_monto) y si es distinto de cero se registra este como descuento
						if(! cargo.getValorCargoMonto().equals(0.0)) {
							chargediscountW.setCharge(true);
							chargediscountW.setDescription(cargo.getDescripcionCargo().trim());
							chargediscountW.setPercentage(false);
							chargediscountW.setValue(cargo.getValorCargoMonto().doubleValue());
							chargediscountW.setVisualorder(j);
						}
					}					
					chargediscountW = chargediscountserver.addChargeDiscount(chargediscountW);
					
					// Agrega cargo al detalle
					ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetailW = new ChargeDiscountDvrOrderDetailW();
					chargediscountdvrorderdetailW.setChargediscountid(chargediscountW.getId());
					chargediscountdvrorderdetailW.setDvrorderid(dvrorderdetailW.getDvrorderid());
					chargediscountdvrorderdetailW.setItemid(dvrorderdetailW.getItemid());
					chargediscountdvrorderdetailW.setPosition(dvrorderdetailW.getPosition());
					chargediscountdvrorderdetailW = chargediscountdvrorderdetailserver.addChargeDiscountDvrOrderDetail(chargediscountdvrorderdetailW);					
				}
			}
		}		
	}
		
	private void doAddDvrPredeliveryDetail(List<Predistribucion> predistribucionList, DvrOrderW dvrorderW, Map<String, ItemW> itemMap, Map<String, LocationW> locationMap) throws ServiceException {

		for(Predistribucion predistribucion : predistribucionList) {
			// Validaciones fueron hechas
			// Crea predistribución
			
			// Item
			ItemW itemW = itemMap.get(predistribucion.getCodProducto().trim());
			
			// Local
			LocationW locationW = locationMap.get(predistribucion.getCodLocal().trim());
			
			DvrPreDeliveryDetailW dvrpredeliveryW = new DvrPreDeliveryDetailW(); 
			dvrpredeliveryW.setDvrorderid(dvrorderW.getId());
			dvrpredeliveryW.setItemid(itemW.getId());
			dvrpredeliveryW.setLocationid(locationW.getId());
			dvrpredeliveryW.setPosition(predistribucion.getPosicion());
			dvrpredeliveryW.setTotalunits(predistribucion.getUnidadesSolicitadasUmc().doubleValue());
			dvrpredeliveryW.setPendingunits(0.0);
			dvrpredeliveryW.setTodeliveryunits(0.0);
			dvrpredeliveryW.setReceivedunits(0.0);
			dvrpredeliveryW.setTotalneed(0.0);
			dvrpredeliveryW.setTotaltodelivery(0.0);
			dvrpredeliveryW.setTotalreceived(0.0);
			dvrpredeliveryW.setTotalpending(0.0);
			
			dvrpredeliveryW = dvrpredeliverydetailserver.addDvrPreDeliveryDetail(dvrpredeliveryW);
		}		
	}
	
	private void doAddPredeliveryFromOrderDetail(List<Producto> productList, DvrOrderW dvrorderW, Map<String, ItemW> itemMap, LocationW deliveryLocationW) throws ServiceException {
				
		for(Producto producto : productList) {
			// Item
			ItemW itemW = itemMap.get(producto.getCodProducto().trim());
						
			DvrPreDeliveryDetailW dvrpredeliveryW = new DvrPreDeliveryDetailW(); 
			dvrpredeliveryW.setDvrorderid(dvrorderW.getId());
			dvrpredeliveryW.setItemid(itemW.getId());
			dvrpredeliveryW.setLocationid(deliveryLocationW.getId());
			dvrpredeliveryW.setPosition(producto.getPosicion());
			dvrpredeliveryW.setTotalunits(producto.getCantidadSolicitadasUmc().doubleValue());
			dvrpredeliveryW.setPendingunits(0.0);
			dvrpredeliveryW.setTodeliveryunits(0.0);
			dvrpredeliveryW.setReceivedunits(0.0);
			dvrpredeliveryW.setTotalneed(0.0);
			dvrpredeliveryW.setTotaltodelivery(0.0);
			dvrpredeliveryW.setTotalreceived(0.0);
			dvrpredeliveryW.setTotalpending(0.0);
			
			dvrpredeliveryW = dvrpredeliverydetailserver.addDvrPreDeliveryDetail(dvrpredeliveryW);
		}
	}
	
	private void doAddDdcOrderDetail(List<Producto> productList, DdcOrderW ddcorderW, VendorW vendorW, Map<String, ItemW> itemMap) throws NotFoundException, OperationFailedException, AccessDeniedException {

		DdcOrderDetailW ddcorderdetailW;
	
		// El sistema analiza cada una de las líneas de productos que contiene el mensaje.
		for (int i = 0; i < productList.size(); i++) {
			Producto producto = productList.get(i);
			
			// Nuevo detalle
			ddcorderdetailW = new DdcOrderDetailW();
			
			// Agrega Position
			ddcorderdetailW.setPosition(producto.getPosicion());
			
			// Costo Lista
			boolean hasbasecost = true;
			try {
				producto.getCostoLista().doubleValue();
			} catch (Exception e) {
				hasbasecost = false;
			}
			if (hasbasecost) {
				ddcorderdetailW.setBasecost(producto.getCostoLista().doubleValue());
			}
			
			// Costo Compra
			ddcorderdetailW.setFinalcost(producto.getCostoCompra().doubleValue());
			
			// Precio Normal
			boolean hasnormalprice = true;
			try {
				producto.getPrecioNormal().doubleValue();
			} catch (Exception e) {
				hasnormalprice = false;
			}
			if (hasnormalprice) {
				ddcorderdetailW.setNormalprice(producto.getPrecioNormal().doubleValue());
			}
			
			// Precio Oferta
			boolean hasofferprice = true;
			try {
				producto.getPrecioOferta().doubleValue();
			} catch (Exception e) {
				hasofferprice = false;
			}
			if (hasofferprice) {
				ddcorderdetailW.setOfferprice(producto.getPrecioOferta().doubleValue());
			}
						
			// Código de empaquetado
			ddcorderdetailW.setPackingcode("");
			
			// Descripción de empaquetado
			ddcorderdetailW.setPackingdescription(producto.getUnidadMedidaCompra().trim());
			
			// Unidad de medida
			ddcorderdetailW.setBaseunit(producto.getUnidadMedidaBase().trim());
			
			// UMB x UMC
			ddcorderdetailW.setProductpackingrate(producto.getContenido().doubleValue());
			
			// Código de Barra 1 (EAN)
			ddcorderdetailW.setEan(producto.getCodBarra1().trim());
			
			// Código de Barra 2
			String barcode2 = "";
			try {
				barcode2 = producto.getCodBarra1().trim();
			} catch (Exception e) {
				barcode2 = "";
			}
			ddcorderdetailW.setBarcode2(barcode2);
			
			// Observaciones
			String comment = "";
			try {
				comment = producto.getObservaciones().trim();
			} catch (Exception e) {
				comment = "";
			}
			ddcorderdetailW.setComment(comment);
			
			// Tolerancia
			boolean hastolerance = true;
			try {
				producto.getTolerancia().doubleValue();
			} catch (Exception e) {
				hastolerance = false;
			}
			if (hastolerance) {
				ddcorderdetailW.setTolerance(producto.getTolerancia().doubleValue());
			}
			
			// Fecha de entrega del producto (solo se guarda si viene en el mensaje y con el formato adecuado)
			boolean hasitemdeliverydate = true;
			try {
				LocalDate.parse(producto.getFechaEntrega().trim(), dateFormatter);
			} catch (Exception e) {
				hasitemdeliverydate = false;
			}
			if (hasitemdeliverydate) {
				ddcorderdetailW.setItemdeliverydate(LocalDate.parse(producto.getFechaEntrega().trim(), dateFormatter).atStartOfDay());
			}

			// Agrega Totales
			ddcorderdetailW.setNeedunits(producto.getCantidadSolicitadasUmc().doubleValue());
			ddcorderdetailW.setTodeliveryunits(0.0);
			ddcorderdetailW.setReceivedunits(0.0);
			ddcorderdetailW.setPendingunits(0.0);
			ddcorderdetailW.setTotalneed(0.0);
			ddcorderdetailW.setTotaltodelivery(0.0);
			ddcorderdetailW.setTotalreceived(0.0);			
			ddcorderdetailW.setTotalpending(0.0);
			
			ddcorderdetailW.setDdcorderid(ddcorderW.getId());
			
			// Item
			ItemW itemW = itemMap.get(productList.get(i).getCodProducto().trim());
					
			// Agrega item a detalle
			ddcorderdetailW.setItemid(itemW.getId());
			
			// Crea Detalle
			ddcorderdetailW = ddcorderdetailserver.addDdcOrderDetail(ddcorderdetailW);
			ddcorderdetailserver.flush();
			
			////////////////////////////////////////////////////////////////////////////////////////
			// Busca descuentos
			boolean discountExists = true;
			try {
				producto.getDescuentos().getDescuento();
			} catch (Exception e) {
				discountExists = false;
			}
			
			if (discountExists) {
				for (int j = 0; j < producto.getDescuentos().getDescuento().size() ; j++) {
					Descuento descuento = producto.getDescuentos().getDescuento().get(j);
					
					DdcChargeDiscountW ddcchargediscountW = new DdcChargeDiscountW();
					
					// Descuento es porcentaje
					if (descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_PORC)) {
						// Se verifica el valor (valor_descuento_porc) y si es distinto de cero se registra este como descuento.
						if(descuento.getValorDescuentoPorc().doubleValue() != 0.0) {
							ddcchargediscountW.setCharge(false);
							ddcchargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
							ddcchargediscountW.setVisualorder(j);
							ddcchargediscountW.setPercentage(true);
							ddcchargediscountW.setValue(descuento.getValorDescuentoPorc().doubleValue());
						}
					}
					
					// Descuento es monto
					else if(descuento.getTipoDescuento().trim().equalsIgnoreCase(PROD_DESC_MONTO)) {
						 // Se verifica el valor (valor_descuento_monto) y si es distinto de cero se registra este como descuento
						if(descuento.getValorDescuentoMonto().doubleValue() != 0.0) {
							ddcchargediscountW.setCharge(false);
							ddcchargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
							ddcchargediscountW.setVisualorder(j);
							ddcchargediscountW.setPercentage(false);
							ddcchargediscountW.setValue(descuento.getValorDescuentoMonto().doubleValue());
						}
					}
					
					ddcchargediscountW = ddcchargediscountserver.addDdcChargeDiscount(ddcchargediscountW);
					ddcchargediscountserver.flush();
					
					// Agrega descuento al detalle
					DdcOrderChargeDiscountDetailW ddcorderchargediscountdetailW = new DdcOrderChargeDiscountDetailW();
					ddcorderchargediscountdetailW.setDdcchargediscountid(ddcchargediscountW.getId());
					ddcorderchargediscountdetailW.setDdcorderid(ddcorderdetailW.getDdcorderid());
					ddcorderchargediscountdetailW.setItemid(ddcorderdetailW.getItemid());
					ddcorderchargediscountdetailW.setPosition(ddcorderdetailW.getPosition());
					ddcorderchargediscountdetailW = ddcorderchargediscountdetailserver.addDdcOrderChargeDiscountDetail(ddcorderchargediscountdetailW);					
				}
			}
			
			// Busca cargos
			boolean chargeExists = true;
			try {
				producto.getCargos().getCargo();
			} catch (Exception e) {
				chargeExists = false;
			}
			
			if (chargeExists) {
				for(int j = 0; j < producto.getCargos().getCargo().size() ; j++) {
					Cargo cargo = producto.getCargos().getCargo().get(j);
					
					DdcChargeDiscountW ddcchargediscountW = new DdcChargeDiscountW();
					
					// Cargo es porcentaje
					if(cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_PORC)) {
						// Se verifica el valor (valor_cargo_porc) y si es distinto de cero se registra este como descuento.
						if(!cargo.getValorCargoPorc().equals(0.0)) {							
							ddcchargediscountW.setCharge(true);
							ddcchargediscountW.setDescription(cargo.getDescripcionCargo().trim());
							ddcchargediscountW.setVisualorder(j);
							ddcchargediscountW.setPercentage(true);
							ddcchargediscountW.setValue(cargo.getValorCargoPorc().doubleValue());
						}
					}
					
					// Cargo es monto
					else if(cargo.getTipoCargo().trim().equalsIgnoreCase(PROD_CARGO_MONTO)) {
						 // Se verifica el valor (valor_cargo_monto) y si es distinto de cero se registra este como descuento
						if(! cargo.getValorCargoMonto().equals(0.0)) {
							ddcchargediscountW.setCharge(true);
							ddcchargediscountW.setDescription(cargo.getDescripcionCargo().trim());
							ddcchargediscountW.setVisualorder(j);
							ddcchargediscountW.setPercentage(false);
							ddcchargediscountW.setValue(cargo.getValorCargoMonto().doubleValue());
						}
					}
					ddcchargediscountW = ddcchargediscountserver.addDdcChargeDiscount(ddcchargediscountW);
					
					// Agrega cargo al detalle
					DdcOrderChargeDiscountDetailW ddcorderchargediscountdetailW = new DdcOrderChargeDiscountDetailW();
					ddcorderchargediscountdetailW.setDdcchargediscountid(ddcchargediscountW.getId());
					ddcorderchargediscountdetailW.setDdcorderid(ddcorderdetailW.getDdcorderid());
					ddcorderchargediscountdetailW.setItemid(ddcorderdetailW.getItemid());
					ddcorderchargediscountdetailW.setPosition(ddcorderdetailW.getPosition());
					ddcorderchargediscountdetailW = ddcorderchargediscountdetailserver.addDdcOrderChargeDiscountDetail(ddcorderchargediscountdetailW);					
				}
			}
		}
	}
	
	private Long doCreateOrder(Orden orderParser) throws LoadDataException, ServiceException {
		
		// Valida campos obligatorios del mensaje
		doValidateOrderMessage(orderParser);
		
		// Fecha Actual
		LocalDateTime now = LocalDateTime.now();		
		
		Long orderNumberMsg;
		
		// Número de la orden creada
		Long orderNumber = -1L;
		
		// Tipos de Orden
		OrderTypeW dvhOrderTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DVH");
		OrderTypeW ddcOrderTypeW = ordertypeserver.getByPropertyAsSingleResult("code", "DDC");
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// El sistema valida que el número de orden de compra no exista
		orderNumberMsg = orderParser.getEncabezado().getNumOc();
		
		OrderW[] orderArr = orderserver.getByPropertyAsArray("number", orderNumberMsg);
		if(orderArr.length > 0) {
			throw new LoadDataException("El número de OC " + orderNumberMsg + " ya existe en el sistema");
		}
		
		// El sistema valida que el tipo de orden existe y que es distinta a DDC (Despacho directo a cliente).
		String orderTypeMsg = orderParser.getEncabezado().getTipoOc().trim(); 

		// Mapa de Locales
		// Key: codigo de  local
		Map<String , LocationW> locationMap = new HashMap<String, LocationW>();
					
		// Maestro de Productos
		// Key: codigo de producto
		Map<String , ItemW> itemMap = new HashMap<String, ItemW>();			
		
		// CASO 1: El tipo de orden es DDC
		if(orderTypeMsg.equalsIgnoreCase(OCTYPE_MSG_DDC)) {
			// Estados de Orden
			DdcOrderStateTypeW stReleased = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_RELEASED);
			DdcOrderStateTypeW stExpired = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", LogisticConstants.DDCORDER_STATE_EXPIRED);
						
			// Obtiene fecha de entrega (yyyyMMdd)
			String deliveryDateStr = orderParser.getEncabezado().getFechaEntrega().trim();
			LocalDate deliveryDate = LocalDate.parse(deliveryDateStr, dateFormatter);
			LocalDateTime deliveryDateTime =  deliveryDate.atStartOfDay();
			
			// Obtiene fecha emisión
			String emittedDateStr =  orderParser.getEncabezado().getFechaEmision().trim();
			LocalDate emittedDate = LocalDate.parse(emittedDateStr, dateFormatter);
			LocalDateTime emittedDateTime =  emittedDate.atStartOfDay();
							
			// Obtiene fecha vencimiento
			String expirationDateStr =  orderParser.getEncabezado().getFechaVencimiento().trim();
			LocalDate expirationDate = LocalDate.parse(expirationDateStr, dateFormatter);
			LocalDateTime expirationDateTime =  expirationDate.atStartOfDay();
			
			// El sistema valida que Fecha de entrega > fecha de emisión.
			if(deliveryDateTime.isBefore(emittedDateTime)) {
				throw new LoadDataException("La fecha de entrega es anterior a la fecha de emisión");
			}
			
			// El sistema valida que Fecha de vencimiento > fecha de entrega. 
			if(expirationDateTime.isBefore(deliveryDateTime)) {
				throw new LoadDataException("La fecha de vencimiento es anterior a la fecha de entrega");
			}
			
			// El sistema obtiene el retailer en la BD, en base al código de comprador informado.
			RetailerW retailerW = doAddOrGetRetailer(orderParser);
			
			// Carga Maestro de locales
			for (int i = 0; i < orderParser.getMaestroLocales().getLocal().size(); i++) {
				LocationW locationW = doAddOrUpdateLocation(orderParser, i);
				locationMap.put(locationW.getCode(), locationW);
			}
			
			// Responsable
			ResponsableW responsableW = doAddOrUpdateResponsable(orderParser);
			
			// Proveedor
			VendorW vendorW = doAddOrUpdateVendor(orderParser);
			
			// Carga Maestro de items
			for(Producto producto : orderParser.getListadoProductos().getProducto()) {
				ItemW itemW = doAddOrUpdateItem(producto, vendorW);
				itemMap.put(producto.getCodProducto(), itemW);
			}

			// El sistema valida que existan datos de cliente asociados a las ventas en verde
			try {
				orderParser.getEncabezado().getDatosVev();
			} catch (Exception e) {
				throw new LoadDataException("No existen datos de cliente asociados a la orden de compra número " + orderNumberMsg);
			}

			// Obtiene información del cliente
			ClientW clientW = doAddClient(orderParser);
						
			// En caso que se haya enviado el campo 'cod_local_venta', este local debe estar informado en el maestro de locales
			String salesLocationCode = "";
			try {
				salesLocationCode = orderParser.getEncabezado().getDatosVev().getCodLocalVenta().trim();
			} catch (Exception e) {
				salesLocationCode = "";
			}

			Long saleLocationId = null;
			if(salesLocationCode.length() > 0) {
				if(!locationMap.containsKey(salesLocationCode)) {
					throw new LoadDataException("El local de venta enviado para el cliente con RUT " + clientW.getDni().trim() + ", debe estar informado en el maestro de locales");
				}
				
				saleLocationId = locationMap.get(salesLocationCode).getId();
			}
			
			// Obtiene Condición de pago
			String paymentDays = "";
			try {
				paymentDays = orderParser.getEncabezado().getCondicionPago().trim();
			} catch (Exception e) {
				paymentDays = "";
			}
			
			// Comentario de la orden
			String comment = "";
			try {
				comment = orderParser.getEncabezado().getObservaciones().trim();
			} catch (Exception e) {
				comment = "";
			}
			
			// Comentario de la orden
			Long dispatchGuide;
			try {
				dispatchGuide = orderParser.getEncabezado().getDatosVev().getGuiaDespacho();
			} catch (Exception e) {
				dispatchGuide = null;
			}
			
			// Referencia de venta
			String referencenNumber = orderParser.getEncabezado().getDatosVev().getReferenciaVenta().trim();
						
			LocalDate today = now.toLocalDate();
			
			// LAS OC DEBEN QUEDAR EN ESTADO SOA POR NOTIFICAR
			SOAStateTypeW porNotSt = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
			
			// El sistema crea la orden de compra con sus datos asociados
			DdcOrderW ddcorderW = new DdcOrderW();
			ddcorderW.setNumber(orderNumberMsg);
			ddcorderW.setEmitted(emittedDateTime);
			ddcorderW.setCreationdate(now);
			ddcorderW.setOriginaldeliverydate(deliveryDateTime);
			ddcorderW.setCurrentdeliverydate(deliveryDateTime);
			ddcorderW.setExpiration(expirationDateTime);
			ddcorderW.setNeedunits(0.0);
			ddcorderW.setTodeliveryunits(0.0);
			ddcorderW.setReceivedunits(0.0);
			ddcorderW.setPendingunits(0.0);
			ddcorderW.setTotalneed(0.0);
			ddcorderW.setTotaltodelivery(0.0);
			ddcorderW.setTotalreceived(0.0);
			ddcorderW.setTotalpending(0.0);
			ddcorderW.setPaymentdays(paymentDays);
			ddcorderW.setComment(comment);
			ddcorderW.setCurrentstatetypedate(now);
			ddcorderW.setCurrentstatetypewho("MQ");
			ddcorderW.setCurrentstatetypecomment("Creación");
			ddcorderW.setReferencenumber(referencenNumber);
			ddcorderW.setResponsableid(responsableW.getId());
			ddcorderW.setRetailerid(retailerW.getId());
			ddcorderW.setVendorid(vendorW.getId());
			ddcorderW.setSectionid(null);
			ddcorderW.setOrdertypeid(ddcOrderTypeW.getId());
			ddcorderW.setClientid(clientW.getId());
			ddcorderW.setCurrentstatetypeid(expirationDate.isBefore(today) ? stExpired.getId() : stReleased.getId());
			ddcorderW.setSalelocationid(saleLocationId);
			ddcorderW.setCurrentddcdeliveryid(null);
			ddcorderW.setDispatchguide(dispatchGuide);
			//SOA
			ddcorderW.setCurrentsoastatetypeid(porNotSt.getId());
			ddcorderW.setCurrentsoastatetypedate(now);
						
			ddcorderW = ddcorderserver.addDdcOrder(ddcorderW);
			
			// Atributos de la orden
			// Busca si exite
			boolean hasAttributes = true;
			try {
				orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo();
			} catch (Exception e) {
				hasAttributes = false;
			}
			
			OrderAttributeW orderAttributeW;
			if (hasAttributes) {
				for(Orden.Encabezado.OtrosAtributosOrden.Atributo atributo : orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo()) {
					orderAttributeW = new OrderAttributeW();
					orderAttributeW.setOrderid(ddcorderW.getId());
					orderAttributeW.setAttributetype(atributo.getTipoAtributo().trim().toUpperCase());
					orderAttributeW.setCode(atributo.getCodigo().trim());
					orderAttributeW.setValue(atributo.getValor().trim());
					orderAttributeW = orderattributeserver.addOrderAttribute(orderAttributeW);	
				}
			}
			
			// Agrega el estado
			DdcOrderStateW state = new DdcOrderStateW();
			state.setDdcorderstatetypeid(stReleased.getId());
			state.setDdcorderid(ddcorderW.getId());
			state.setWhen(now);
			state.setWho("MQ");
			state.setComment("Creación");
			state = ddcorderstateserver.addDdcOrderState(state);
			
			// NUEVO ESTADO SOA
			SOAStateW soastate = new SOAStateW();
			soastate.setOrderid(ddcorderW.getId());
			soastate.setSoastatetypeid(porNotSt.getId());
			soastate.setWhen(now);
			soastate = soastateserver.addSOAState(soastate);

			if (expirationDate.isBefore(today)) {
				state = new DdcOrderStateW();
				state.setDdcorderstatetypeid(stExpired.getId());
				state.setDdcorderid(ddcorderW.getId());
				state.setWhen(now);
				state.setWho("MQ");
				state.setComment("Carga");
				state = ddcorderstateserver.addDdcOrderState(state);
			}
			
			// El sistema valida que se informen descuentos generales para la orden de compra.
			boolean generalDiscountExists = true;
			try{
				orderParser.getDescuentosGenerales().getDescuento();
			} catch (Exception e) {
				generalDiscountExists = false;
			}
			
			if(generalDiscountExists) {
				for (int i = 0; i < orderParser.getDescuentosGenerales().getDescuento().size(); i++) {
					
					bbr.b2b.logistic.xml.order.Orden.DescuentosGenerales.Descuento descuento = orderParser.getDescuentosGenerales().getDescuento().get(i) ;
					
					DdcChargeDiscountW ddcchargediscountW = new DdcChargeDiscountW();
					
					// a.	Si el tipo de descuento es en porcentaje, se verifica el valor (valor_descuento_porc) y si es distinto de cero se registra este como descuento.
					if(descuento.getTipoDescuento().trim().equalsIgnoreCase(GENERAL_DESC_PORC) && descuento.getValorDescuentoPorc().doubleValue() != 0.0) {
						ddcchargediscountW.setCharge(false);
						ddcchargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
						ddcchargediscountW.setVisualorder(i);
						ddcchargediscountW.setPercentage(true);
						ddcchargediscountW.setValue(descuento.getValorDescuentoPorc().doubleValue());
						ddcchargediscountW = ddcchargediscountserver.addDdcChargeDiscount(ddcchargediscountW);						
					}
					
					// Si el tipo de descuento es en monto, se verifica el valor (valor_descuento_monto) y si es distinto de cero se registra este como descuento.
					else if(descuento.getTipoDescuento().trim().equalsIgnoreCase(GENERAL_DESC_MONTO) && descuento.getValorDescuentoMonto().doubleValue() != 0.0) {
						ddcchargediscountW.setCharge(false);
						ddcchargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
						ddcchargediscountW.setVisualorder(i);
						ddcchargediscountW.setPercentage(false);
						ddcchargediscountW.setValue(descuento.getValorDescuentoMonto().doubleValue());
						ddcchargediscountW = ddcchargediscountserver.addDdcChargeDiscount(ddcchargediscountW);							
					}

					ddcchargediscountserver.flush();
					
					// Asocia el descuento a la orden
					DdcOrderChargeDiscountW ddcorderchargediscountW = new DdcOrderChargeDiscountW();
					ddcorderchargediscountW.setDdcchargediscountid(ddcchargediscountW.getId());
					ddcorderchargediscountW.setDdcorderid(ddcorderW.getId());
					ddcorderchargediscountW = ddcorderchargediscountserver.addDdcOrderChargeDiscount(ddcorderchargediscountW);
				}
			}
			
			// Crea detalles
			doAddDdcOrderDetail(orderParser.getListadoProductos().getProducto(), ddcorderW, vendorW, itemMap);
			ddcorderdetailserver.flush();
			
			// Cálculo de totales para DdcOrder
			Long[] ddcorderids = {ddcorderW.getId()};
			buyorderreportmanager.doCalculateTotalOfDdcOrders(ddcorderids);

			orderNumber = ddcorderW.getNumber();
		}
		
		// CASO 2: El tipo es uno distinto
		else {
			// Estados de Orden
			DvrOrderStateTypeW libSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
			DvrOrderStateTypeW vencSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "VENCIDA");
			
			DvrOrderW dvrorderW = new DvrOrderW();

			// Obtiene OC de la Orden (según lo informado)
			OrderTypeW[] ordertypeArr = ordertypeserver.getByPropertyAsArray("code", orderTypeMsg);
			if(ordertypeArr.length == 0) {
				throw new LoadDataException("El tipo de orden informado no es válido");
			}
			OrderTypeW ordertypeW = ordertypeArr[0];
			
			// Obtiene fecha de entrega (yyyyMMdd)
			String deliveryDateStr = orderParser.getEncabezado().getFechaEntrega().trim();
			LocalDate deliveryDate = LocalDate.parse(deliveryDateStr, dateFormatter);
			LocalDateTime deliveryDateTime =  deliveryDate.atStartOfDay();
			
			// Obtiene fecha emisión
			String emittedDateStr =  orderParser.getEncabezado().getFechaEmision().trim();
			LocalDate emittedDate = LocalDate.parse(emittedDateStr, dateFormatter);
			LocalDateTime emittedDateTime =  emittedDate.atStartOfDay();
							
			// Obtiene fecha vencimiento
			String expirationDateStr =  orderParser.getEncabezado().getFechaVencimiento().trim();
			LocalDate expirationDate = LocalDate.parse(expirationDateStr, dateFormatter);
			LocalDateTime expirationDateTime =  expirationDate.atStartOfDay();
			
			// El sistema valida que Fecha de entrega > fecha de emisión.
			if(deliveryDateTime.isBefore(emittedDateTime)) {
				throw new LoadDataException("La fecha de entrega es anterior a la fecha de emisión");
			}
			
			// El sistema valida que Fecha de vencimiento > fecha de entrega. 
			if(expirationDateTime.isBefore(deliveryDateTime)) {
				throw new LoadDataException("La fecha de vencimiento es anterior a la fecha de entrega");
			}
			
			// El sistema obtiene el retailer en la BD, en base al código de comprador informado.
			RetailerW retailerW = doAddOrGetRetailer(orderParser);

			// Carga Maestro de locales
			for(int i=0; i< orderParser.getMaestroLocales().getLocal().size(); i++) {
				LocationW locationW = doAddOrUpdateLocation(orderParser, i);
				locationMap.put(locationW.getCode(), locationW);
			}
							
			// Responsable
			ResponsableW responsableW = doAddOrUpdateResponsable(orderParser);
			
			// Proveedor
			VendorW vendorW = doAddOrUpdateVendor(orderParser);

			// Local de entrega
			LocationW deliverylocationW = locationMap.get(orderParser.getEncabezado().getLocalEntrega().trim());
			
			// Carga Maestro de items
			for(Producto producto : orderParser.getListadoProductos().getProducto()) {
				ItemW itemW = doAddOrUpdateItem(producto, vendorW);
				itemMap.put(producto.getCodProducto(), itemW);
			}
			
			// El sistema valida que la orden de compra sea de tipo venta en verde (DVH).
			if(ordertypeW.getId().equals(dvhOrderTypeW.getId())) {
				// El sistema valida que existan datos de cliente asociados a las ventas en verde.
				try {
					orderParser.getEncabezado().getDatosVev();
				} catch (Exception e) {
					throw new LoadDataException("No existen datos de cliente asociados a la orden de compra número " + orderNumberMsg);		 
				}
				
				// Obtiene información del cliente
				ClientW clientW = doAddClient(orderParser);
				
				// Agrega cliente a la orden
				dvrorderW.setClientid(clientW.getId());
				
				// Agrega número de referencia
				dvrorderW.setReferencenumber(orderParser.getEncabezado().getDatosVev().getReferenciaVenta().trim());
				
				// En caso que se haya enviado el campo ‘cod_local_venta’, este local debe estar informado en el maestro de locales. 
				// De lo contrario el sistema genera un error con la descripción: “El local de venta enviado para el cliente con RUT XXXX, debe estar informado en el maestro de locales”, para ser enviado posteriormente en un ACK de error y salta a paso 42.
				String salesLocationCode = "";
				try {
					salesLocationCode = orderParser.getEncabezado().getDatosVev().getCodLocalVenta().trim();
				} catch (Exception e) {
					salesLocationCode = "";
				}
				
				Long saleLocationId = null;
				if(salesLocationCode.length() > 0) {
					if(!locationMap.containsKey(salesLocationCode)) {
						throw new LoadDataException("El local de venta enviado para el cliente con RUT " + clientW.getDni().trim() + ", debe estar informado en el maestro de locales");
					}
					
					saleLocationId = locationMap.get(salesLocationCode).getId();
				}

				// Agrega el local de venta a la orden
				dvrorderW.setSalelocationid(saleLocationId);
			}
			
			// Comentario de la orden
			String comment = "";
			try {
				comment = orderParser.getEncabezado().getObservaciones().trim();
			} catch (Exception e) {
				comment = "";
			}
			
			// Obtiene Condición de pago
			String paymentdays = "";
			try {
				paymentdays = orderParser.getEncabezado().getCondicionPago().trim();
			} catch (Exception e) {
				paymentdays ="";
			}
			
			// Fecha de Despacho a Cliente (solo se guarda si viene en el mensaje y con el formato adecuado)
			LocalDateTime clientDeliveryDate;
			try {
				clientDeliveryDate = LocalDate.parse(orderParser.getEncabezado().getDatosVev().getFechaDespachoCliente().trim(), dateFormatter).atStartOfDay();
			} catch (Exception e) {
				clientDeliveryDate = null;
			}
						
			// Monto total neto de la OC
			Double netamount = orderParser.getEncabezado().getMontoNeto().doubleValue();
			
			// Monto total impuesto (IVA, IGV)
			Double taxamount = 0.0;
			try {
				taxamount = orderParser.getEncabezado().getImpuesto().doubleValue();
			} catch (Exception e) {
				taxamount = 0.0;
			}
			
			// Monto total de la orden
			Double totalamountoc = 0.0;
			try {
				totalamountoc = orderParser.getEncabezado().getMontoOrden().doubleValue();
			} catch (Exception e) {
				totalamountoc = 0.0;
			}
			
			// LAS OC DEBEN QUEDAR EN ESTADO SOA POR NOTIFICAR
			SOAStateTypeW porNotSt = soastatetypeserver.getByPropertyAsSingleResult("code", "POR_NOTIFICAR");
			
			// El sistema crea la orden de compra con sus datos asociados.
			dvrorderW.setNumber(orderNumberMsg);
			dvrorderW.setEmitted(emittedDateTime);
			dvrorderW.setDeliverydate(deliveryDateTime);
			dvrorderW.setExpiration(expirationDateTime);
			dvrorderW.setPaymentdays(paymentdays);
			dvrorderW.setComment(comment);
			dvrorderW.setOrdertypeid(ordertypeW.getId());
			dvrorderW.setRetailerid(retailerW.getId());
			dvrorderW.setResponsableid(responsableW.getId());
			dvrorderW.setVendorid(vendorW.getId());
			dvrorderW.setDeliverylocationid(deliverylocationW.getId());
			dvrorderW.setCurrentstatetypeid(libSt.getId());
			dvrorderW.setCurrentstatetypedate(now);
			dvrorderW.setClientdeliverydate(clientDeliveryDate);
			dvrorderW.setTotalunits(0.0);
			dvrorderW.setReceivedunits(0.0);
			dvrorderW.setTodeliveryunits(0.0);
			dvrorderW.setPendingunits(0.0);
			dvrorderW.setTotalneed(0.0);
			dvrorderW.setTotalreceived(0.0);
			dvrorderW.setTotaltodelivery(0.0);
			dvrorderW.setTotalpending(0.0);
			dvrorderW.setNetamount(netamount);
			dvrorderW.setTaxamount(taxamount);
			dvrorderW.setTotalamountoc(totalamountoc);
			// TODO MME: Acordar si la fecha quedará con valor inicial para todo tipo de OC DVR o solo se asignará a las "DVH"
			dvrorderW.setReschedulingdate(deliveryDateTime);
			dvrorderW.setReschedulingcounter(0);
			//SOA
			dvrorderW.setCurrentsoastatetypeid(porNotSt.getId());
			dvrorderW.setCurrentsoastatetypedate(now);

			dvrorderW = dvrorderserver.addDvrOrder(dvrorderW);
			
			// Atributos de la orden
			// Busca si exite
			boolean hasAttributes = true;
			try {
				orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo();
			} catch (Exception e) {
				hasAttributes = false;
			}
			
			OrderAttributeW orderAttributeW;
			if (hasAttributes) {
				for(Orden.Encabezado.OtrosAtributosOrden.Atributo atributo : orderParser.getEncabezado().getOtrosAtributosOrden().getAtributo()) {
					orderAttributeW = new OrderAttributeW();
					orderAttributeW.setOrderid(dvrorderW.getId());
					orderAttributeW.setAttributetype(atributo.getTipoAtributo().trim().toUpperCase());
					orderAttributeW.setCode(atributo.getCodigo().trim());
					orderAttributeW.setValue(atributo.getValor().trim());
					orderAttributeW = orderattributeserver.addOrderAttribute(orderAttributeW);	
				}
			}

			// Agrega el estado
			DvrOrderStateW state = new DvrOrderStateW();
			state.setDvrorderstatetypeid(libSt.getId());
			state.setDvrorderid(dvrorderW.getId());
			state.setWhen(now);
			state.setWho("MQ");
			state = dvrorderstateserver.addDvrOrderState(state);	
			
			// NUEVO ESTADO SOA
			SOAStateW soastate = new SOAStateW();
			soastate.setOrderid(dvrorderW.getId());
			soastate.setSoastatetypeid(porNotSt.getId());
			soastate.setWhen(now);
			soastate = soastateserver.addSOAState(soastate);

			// El sistema valida que se informen descuentos generales para la orden de compra.
			boolean descGeneralExists = true;
			try{
				orderParser.getDescuentosGenerales().getDescuento();
			} catch (Exception e) {
				descGeneralExists = false;
			}
			
			if(descGeneralExists) {
				for(int i=0; i< orderParser.getDescuentosGenerales().getDescuento().size(); i++) {
					bbr.b2b.logistic.xml.order.Orden.DescuentosGenerales.Descuento descuento = orderParser.getDescuentosGenerales().getDescuento().get(i) ;
					
					ChargeDiscountW chargediscountW = new ChargeDiscountW();
					
					// a.	Si el tipo de descuento es en porcentaje, se verifica el valor (valor_descuento_porc) y si es distinto de cero se registra este como descuento.
					if(descuento.getTipoDescuento().trim().equalsIgnoreCase(GENERAL_DESC_PORC) && descuento.getValorDescuentoPorc().doubleValue() != 0.0) {
						chargediscountW.setCharge(false);
						chargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
						chargediscountW.setPercentage(true);
						chargediscountW.setValue(descuento.getValorDescuentoPorc().doubleValue());
						chargediscountW.setVisualorder(i);
						chargediscountW = chargediscountserver.addChargeDiscount(chargediscountW);
					}
					
					// Si el tipo de descuento es en monto, se verifica el valor (valor_descuento_monto) y si es distinto de cero se registra este como descuento.
					else if(descuento.getTipoDescuento().trim().equalsIgnoreCase(GENERAL_DESC_MONTO) && descuento.getValorDescuentoMonto().doubleValue() != 0.0) {
						chargediscountW.setCharge(false);
						chargediscountW.setDescription(descuento.getDescripcionDescuento().trim());
						chargediscountW.setPercentage(false);
						chargediscountW.setValue(descuento.getValorDescuentoMonto().doubleValue());
						chargediscountW.setVisualorder(i);
						chargediscountW = chargediscountserver.addChargeDiscount(chargediscountW);							
					}

					chargediscountserver.flush();
					
					// Asocia descuento a orden
					ChargeDiscountDvrOrderW chargediscountdvrorderW = new ChargeDiscountDvrOrderW();
					chargediscountdvrorderW.setChargediscountid(chargediscountW.getId());
					chargediscountdvrorderW.setDvrorderid(dvrorderW.getId());
					chargediscountdvrorderW = chargediscountdvrorderserver.addChargeDiscountDvrOrder(chargediscountdvrorderW);
				}
			}
			
			// Carga maestro de productos
			
			// Crea detalles
			doAddDvrOrderDetail(orderParser.getListadoProductos().getProducto(), dvrorderW, vendorW, itemMap);
			dvrorderdetailserver.flush(); 				
						
			// Crea predistribución
			// - Si el tipo de la OC es PREDISTRIBUIDA
			if(orderParser.getEncabezado().getTipoOc().trim().equalsIgnoreCase(OCTYPE_MSG_PREDISTRIBUIDA)) {
				doAddDvrPredeliveryDetail(orderParser.getPredistribuciones().getPredistribucion(), dvrorderW, itemMap, locationMap);
			}
			
			// - Otro caso, se crea la predistribución con los mismos datos del detalle de la OC
			else{
				doAddPredeliveryFromOrderDetail(orderParser.getListadoProductos().getProducto(), dvrorderW, itemMap, deliverylocationW);
			}
			
			// Si la fecha de vencimiento es igual o anterior a hoy, el estado es "Vencida"
			LocalDate nowDate = now.toLocalDate();
			if(expirationDate.isBefore(nowDate)){
				dvrorderW.setCurrentstatetypeid(vencSt.getId());
				dvrorderW.setCurrentstatetypedate(now); 
				dvrorderW = dvrorderserver.updateDvrOrder(dvrorderW);
		
				// Agrega el estado
				state = new DvrOrderStateW();
				state.setDvrorderstatetypeid(vencSt.getId());
				state.setDvrorderid(dvrorderW.getId());
				state.setWhen(now);
				state = dvrorderstateserver.addDvrOrderState(state);	
			}

			// Calculo de totales para  DvrOrder
			Long[] dvrorderids = {dvrorderW.getId()};
			buyorderreportmanager.doCalculateTotalOfDvrOrders(dvrorderids);

			orderNumber = dvrorderW.getNumber();			
		}
		
		return orderNumber;
	}
	
	private Long doModifyOrder(Orden orderParser) throws LoadDataException, ServiceException{

		// Validaiones necesarias para validar mensaje de modificación
		doValidateOrderMessageToModify(orderParser);
		
		// Fecha Actual
		LocalDateTime now = LocalDateTime.now();
		
		Long orderNumberMsg;
		
		// Número de la orden creada
		Long orderNumber = -1L;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// El sistema valida que la orden exista
		orderNumberMsg = orderParser.getEncabezado().getNumOc();

		OrderW[] orderArr = orderserver.getByPropertyAsArray("number", orderNumberMsg);
		if(orderArr.length == 0) {
			throw new LoadDataException("La OC número " + orderNumberMsg + " no existe en el sistema");
		}
		orderNumber = orderArr[0].getNumber();
		
		// El sistema valida que el tipo de orden existe y que es distinta a DDC (Despacho directo a cliente).
		String orderTypeMsg = orderParser.getEncabezado().getTipoOc().trim(); 

		String expirationDateStr;
		LocalDate newExpirationDate;
		LocalDateTime newExpirationDateTime;
		LocalDateTime currExpirationDateTime;
		
		// CASO 1: El tipo de orden es DDC.
		if(orderTypeMsg.equalsIgnoreCase(OCTYPE_MSG_DDC)) {
			DdcOrderW ddcorderW;
			try {
				ddcorderW = ddcorderserver.getById(orderArr[0].getId());
			} catch (Exception e) {
				throw new LoadDataException("No existe orden DDC con número " + orderNumberMsg);
			}
			
			// Obtiene fecha vencimiento informada enel mensaeje
			expirationDateStr =  orderParser.getEncabezado().getFechaVencimiento().trim();
			newExpirationDate = LocalDate.parse(expirationDateStr, dateFormatter);
			newExpirationDateTime =  newExpirationDate.atStartOfDay();
			
			currExpirationDateTime = ddcorderW.getExpiration();
			
			// Valida que la fecha de vencimiento informada sea posterior a la actual
			if (currExpirationDateTime.isAfter(newExpirationDateTime) || currExpirationDateTime.isEqual(newExpirationDateTime)) {
				throw new LoadDataException("La fecha de vencimiento informada en la modificación de la OC número " + orderNumberMsg + " no es válida. Debe ser posterior a la existente");
			}
			
			DdcOrderStateTypeW ostModified = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");
			
			// Fecha es válida, modifica la OC
			ddcorderW.setExpiration(newExpirationDateTime);
			ddcorderW.setCurrentstatetypedate(now);
			ddcorderW.setCurrentstatetypewho("MQ");
			ddcorderW.setCurrentstatetypecomment("Modificación");
			ddcorderW.setCurrentstatetypeid(ostModified.getId());
			ddcorderW = ddcorderserver.updateDdcOrder(ddcorderW);
						
			// Estado
			DdcOrderStateW state = new DdcOrderStateW();
			state.setWhen(now);
			state.setWho("MQ");
			state.setComment("Modificación");
			state.setDdcorderstatetypeid(ostModified.getId());
			state.setDdcorderid(ddcorderW.getId());
			state = ddcorderstateserver.addDdcOrderState(state);
		}

		// CASO 2: El tipo es uno distinto
		else {
			DvrOrderW dvrorderW;
			try {
				dvrorderW = dvrorderserver.getById(orderArr[0].getId());
			} catch (Exception e) {
				throw new LoadDataException("No existe orden DVR con número " + orderNumberMsg);
			}
			
			DvrOrderStateTypeW modSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "MODIFICADA");
			
			// Obtiene fecha vencimiento informada enel mensaeje
			expirationDateStr =  orderParser.getEncabezado().getFechaVencimiento().trim();
			newExpirationDate = LocalDate.parse(expirationDateStr, dateFormatter);
			newExpirationDateTime =  newExpirationDate.atStartOfDay();
			
			currExpirationDateTime = dvrorderW.getExpiration();
			
			// Valida que la fecha de vencimiento informada sea posterior a la actual
			if(currExpirationDateTime.isAfter(newExpirationDateTime) || currExpirationDateTime.isEqual(newExpirationDateTime)) {
				throw new LoadDataException("La fecha de vencimiento informada en la modificación de la OC número " + orderNumberMsg + " no es válida. Debe ser posterior a la existente");
			}
			
			// Fecha es válida, modifica la OC
			dvrorderW.setExpiration(newExpirationDateTime);
			dvrorderW.setCurrentstatetypeid(modSt.getId());
			dvrorderW.setCurrentstatetypedate(now);
			dvrorderW = dvrorderserver.updateDvrOrder(dvrorderW);
			
			// Estado
			DvrOrderStateW state = new DvrOrderStateW();
			state.setDvrorderstatetypeid(modSt.getId());
			state.setDvrorderid(dvrorderW.getId());
			state.setWhen(now);
			state = dvrorderstateserver.addDvrOrderState(state);		
		}
		
		return orderNumber;
	}
	
	private Long doCancelOrder(Orden orderParser) throws LoadDataException, ServiceException{
		
		// Validaiones necesarias para validar mensaje de cancelación 
		doValidateOrderMessageToCancel(orderParser);
		
		// Fecha Actual
		LocalDateTime now = LocalDateTime.now();
		
		Long orderNumberMsg;
		
		// Número de la orden creada
		Long orderNumber = -1L;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// El sistema valida que la orden exista
		orderNumberMsg = orderParser.getEncabezado().getNumOc();

		OrderW[] orderArr = orderserver.getByPropertyAsArray("number", orderNumberMsg);
		if(orderArr.length == 0) {
			throw new LoadDataException("La OC número " + orderNumberMsg + " no existe en el sistema");
		}
		orderNumber = orderArr[0].getNumber();
		
		// El sistema valida que el tipo de orden existe y que es distinta a DDC (Despacho directo a cliente).
		String orderTypeMsg = orderParser.getEncabezado().getTipoOc().trim(); 
		
		// CASO 1: El tipo de orden es DDC.
		if (orderTypeMsg.equalsIgnoreCase(OCTYPE_MSG_DDC)) {
			DdcOrderW ddcorderW;
			try {
				ddcorderW = ddcorderserver.getById(orderArr[0].getId());
			} catch (Exception e) {
				throw new LoadDataException("No existe orden DDC con número " + orderNumberMsg);
			}
						
			// Busca si existe despacho asociado a la orden
			if (ddcorderW.getCurrentddcdeliveryid() != null && ddcorderW.getCurrentddcdeliveryid() > 0) {
				// Obtiene el despacho y su estado
				DdcDeliveryW ddcdelivery = ddcdeliveryserver.getById(ddcorderW.getCurrentddcdeliveryid());
				DdcDeliveryStateTypeW dstCurrent = ddcdeliverystatetypeserver.getById(ddcdelivery.getCurrentstatetypeid());
				
				// Si el despacho asociado está vigente, error
				if (!dstCurrent.getClosed()) {
					throw new LoadDataException("La OC número " + orderNumberMsg + " tiene un despacho vigente asociado");
				}
			}			
			
			DdcOrderStateTypeW ostCancelled = ddcorderstatetypeserver.getByPropertyAsSingleResult("code", "CANCELADA");
			
			// Se cambia estado de la OC
			ddcorderW.setCurrentstatetypedate(now);
			ddcorderW.setCurrentstatetypewho("MQ");
			ddcorderW.setCurrentstatetypecomment("Cancelación");
			ddcorderW.setCurrentstatetypeid(ostCancelled.getId());
			ddcorderW = ddcorderserver.updateDdcOrder(ddcorderW);
			
			// Estado
			DdcOrderStateW state = new DdcOrderStateW();
			state.setWhen(now);
			state.setWho("MQ");
			state.setComment("Cancelación");
			state.setDdcorderstatetypeid(ostCancelled.getId());
			state.setDdcorderid(ddcorderW.getId());
			state = ddcorderstateserver.addDdcOrderState(state);
		}
		
		// CASO 2: El tipo es uno distinto
		else {
			// Estados de Delviery vigentes			
			DvrDeliveryStateTypeW[] validDelSt = dvrdeliverystatetypeserver.getByPropertyAsArray("valid", true);

			DvrOrderW dvrorderW;
			try {
				dvrorderW = dvrorderserver.getById(orderArr[0].getId());
			} catch (Exception e) {
				throw new LoadDataException("No existe orden DVR con número " + orderNumberMsg);
			}
			
			DvrOrderStateTypeW cancSt = dvrorderstatetypeserver.getByPropertyAsSingleResult("code", "CANCELADA");
			
			// Busca si existe despacho asociado a la orden
			DvrOrderDeliveryW[] dvrorderdeliveryArr = dvrorderdeliveryserver.getByPropertyAsArray("dvrorder.id", dvrorderW.getId());
			
			if(dvrorderdeliveryArr.length > 0) {
				// Obtiene despachos
				Long[] dvrdeliveryids = Arrays.stream(dvrorderdeliveryArr).map(d-> d.getDvrdeliveryid()).toArray(Long[]::new); 
				DvrDeliveryW[] dvrdeliveryArr = dvrdeliveryserver.getDvrDeliveryByIds(dvrdeliveryids); 
				
				// Valida si entre los despachos asociados
				DvrDeliveryW[] validStateDvrDeliveryArr =  Arrays.stream(dvrdeliveryArr)
																.filter(d-> Arrays.stream(validDelSt).anyMatch(st-> st.getId().equals(d.getCurrentstatetypeid())))
																.toArray(DvrDeliveryW[]::new);
				
				// Si existe un despacho vigentes asociados, error
				if(validStateDvrDeliveryArr.length > 0) {
					throw new LoadDataException("La OC número " + orderNumberMsg + " tiene un despacho asociado");
				}
			}			
			
			// Se cambia estado de la OC
			dvrorderW.setCurrentstatetypeid(cancSt.getId());
			dvrorderW.setCurrentstatetypedate(now);
			dvrorderW = dvrorderserver.updateDvrOrder(dvrorderW);
			
			// Estado
			DvrOrderStateW state = new DvrOrderStateW();
			state.setDvrorderstatetypeid(cancSt.getId());
			state.setDvrorderid(dvrorderW.getId());
			state.setWhen(now);
			state = dvrorderstateserver.addDvrOrderState(state);
		}
		
		return orderNumber;		
	}
	
	public Long processMessageOrder(Orden orderParser) throws LoadDataException, ServiceException {
		
		// Número de la orden creada
		Long orderNumber = -1L;
				
		// Valida que el TAG acción exista
		try {
			orderParser.getEncabezado().getAccion();
		} catch (Exception e) {
			throw new LoadDataException("No se especifica tag 'acción'");
		}
		qutils.datoObligatorio(orderParser.getEncabezado().getAccion(), "No se informa un valor de acción");
		
		// La acción a nivel de orden sea ‘CREAR’.
		if(orderParser.getEncabezado().getAccion().trim().equalsIgnoreCase(ACCION_CREAR)) {
			orderNumber = doCreateOrder(orderParser);
		}
		
		// La acción a nivel de orden sea ‘MODIFICAR’’.
		else if(orderParser.getEncabezado().getAccion().trim().equalsIgnoreCase(ACCION_MODIFICAR)) {
			orderNumber = doModifyOrder(orderParser);
		}
				
		// La acción a nivel de orden sea ‘CANCELAR’.
		else if(orderParser.getEncabezado().getAccion().trim().equalsIgnoreCase(ACCION_CANCELAR)) {
			orderNumber = doCancelOrder(orderParser);
		}

		// La acción indicada no es ninguno de los tipos definidos
		else {
			throw new LoadDataException("La acción indicada para la orden XXXX no es válida");
		}
		
		return orderNumber;		
	}
	
}