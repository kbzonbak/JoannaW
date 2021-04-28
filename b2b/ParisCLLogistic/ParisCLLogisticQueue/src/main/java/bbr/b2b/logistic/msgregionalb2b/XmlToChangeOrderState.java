package bbr.b2b.logistic.msgregionalb2b;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.ClientServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.DetailDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderDiscountServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderDetailServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateServerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderDetailW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateW;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderReportDTO;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemClassServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.classes.UnitServerLocal;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticReportManagerLocal;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.ResponsableServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.xml.qchangeorderstate.QCAMBIOESTADO;


@Stateless(name = "handlers/XmlToChangeOrderState")
public class XmlToChangeOrderState implements XmlToChangeOrderStateLocal {

	@EJB
	ItemServerLocal itemserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderDetailServerLocal orderdetailserver;

	@EJB
	OrderServerLocal orderserver;

	@EJB
	OrderStateServerLocal orderstateserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	@EJB
	ResponsableServerLocal responsableserver;

	@EJB
	VendorItemServerLocal vendoritemserver;

	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ClientServerLocal clientserver;
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	ItemClassServerLocal itemclassserver;
	
	@EJB
	FlowTypeServerLocal flowtypeserver;
	
	@EJB
	OrderDiscountServerLocal orderdiscountserver;
	
	@EJB
	UnitServerLocal unitserver;
	
	@EJB
	DetailDiscountServerLocal detaildiscountserver;
	
	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;
	
	@EJB
	DirectOrderServerLocal directorderserver;
	
	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;
	
	@EJB
	DirectOrderStateServerLocal directorderstateserver;
	
	@EJB
	DirectOrderDetailServerLocal directorderdetailserver;
	
	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;
	
	@EJB
	DODeliveryServerLocal dodeliveryserver;
	
	@EJB
	BuyOrderReportManagerLocal ordermanager;
	
	@EJB
	LogisticReportManagerLocal logisticreportmanager;
	
	@EJB
	DeliveryReportManagerLocal deliveryreportmanager;
	
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();	
	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private void doValidateChangeStateMessage(QCAMBIOESTADO qcambioestado) throws LoadDataException {
				
		// N°MERO DE SECUENCIA
		qutils.datoObligatorio(qcambioestado.getSecuencia(), "No se especifica N°mero de secuencia");
		qutils.validaLargo(qcambioestado.getSecuencia(), 12, "El N°mero de secuencia debe tener un largo de a los más 12 dígitos");		
		
		// N°MERO DE ORDEN
		qutils.datoObligatorio(qcambioestado.getNorden(), "No se especifica N°mero de orden");
		qutils.validaLargo(qcambioestado.getNorden(), 6, "El N°mero de ordeb debe tener un largo de a los más 6 caracter(es)");
		
		// NUEVO ESTADO
		qutils.datoObligatorio(qcambioestado.getNuevoestado(), "No se especifica nuevo estado");
		qutils.validaLargo(qcambioestado.getNuevoestado(), 1, "La descripción de producto debe tener un largo de a los más 1 caracter(es)");		
	}

	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}

	private Long doChangeDirectOrderState(QCAMBIOESTADO changestateParser, Long orderNumber, String newstate) throws LoadDataException, ServiceException {
		
		Date now = new Date();
		
		DirectOrderW[] directArr = directorderserver.getByPropertyAsArray("number", orderNumber);		
		if (directArr == null || directArr.length == 0){
			return orderNumber;
		}
		
		DirectOrderW directOrder = directArr[0];
		DirectOrderDetailW[] directOrderDetails = directorderdetailserver.getDirectOrderDetailDataofDirectOrder(directOrder.getId());		

		// Validar si puedo cambiar el estado de la orden
		if (newstate.equalsIgnoreCase("B")) {
			// Validar si tiene alguna unidad pendiente
			boolean pending = false;
			for (DirectOrderDetailW directOrderDetail : directOrderDetails) {
				if (directOrderDetail.getPendingunits() > 0) {
					pending = true;
					break;
				}
			}

			if (!pending) {
				throw new LoadDataException("OC VeVPD " + directOrder.getNumber() + " no posee pendientes. No se puede bloquear");
			}
		}
		
		if (newstate.equalsIgnoreCase("E")) {
			DirectOrderReportDTO doreport = directorderserver.getDirectOrderReportByVendorAndNumber(directOrder.getNumber(), directOrder.getVendorid())[0];

			// Validar si OC tiene despachos asociados 
			if (doreport.getCurrentdeliveryid() > 0) {
				throw new LoadDataException("OC VeV PD N° " + directOrder.getNumber()  + " posee despachos asociados");
			}
		}

		// Modificar el tipo de estado de orden
		String newstatecode = "";
		if (newstate.equalsIgnoreCase("E")) {
			newstatecode = "CANC_PARIS";
		}
		else if (newstate.equalsIgnoreCase("B")) {
			newstatecode = "BLOQ_PARIS";
		}
		else { 
			throw new LoadDataException("Nuevo estado " + newstate + " no conocido!");
		}
		
		// Nuevo estado
		DirectOrderStateTypeW dost = directorderstatetypeserver.getByPropertyAsSingleResult("code", newstatecode);
		
		// Actualizar la orden
		directOrder.setCurrentstatetypeid(dost.getId());
		directOrder.setCurrentstatetypedate(now);
		
		directOrder = directorderserver.updateDirectOrder(directOrder);
		
		// Guardar en el historial
		DirectOrderStateW directorderstatew = new DirectOrderStateW();
		directorderstatew.setOrderstatetypeid(dost.getId());
		directorderstatew.setWhen(now);
		directorderstatew.setOrderid(directOrder.getId());

		directorderstateserver.addDirectOrderState(directorderstatew);
		
		logger.info("Se cambi� exitosamente el estado de la orden " + orderNumber + " a " + dost.getName());		
		
		return orderNumber;
	}	
	
	public Long processMessage(QCAMBIOESTADO changestateParser) throws LoadDataException, ServiceException {
		
		// Validación del Mensaje
		doValidateChangeStateMessage(changestateParser);
		
		/** *************************************************************************************** */
		/** ********************************** CAMBIO DE ESTADO  ********************************** */
		/** *************************************************************************************** */		
		Date now = new Date();
		OrderW order = null;
				
		Long orderNumber;
		String newstate;
						
		// TIPO VeV CD
		OrderTypeW vevCDType = ordertypeserver.getByPropertyAsSingleResult("code", "V");
		
		// ESTADO AGENDADA
		DeliveryStateTypeW agendSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
		
		// N°MERO DE ORDEN		
		try{
			orderNumber = changestateParser.getNorden();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el N°mero de orden");
		}
		
		// NUEVO ESTADO
		try{
			newstate = changestateParser.getNuevoestado().trim();				
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el nuevo estado");
		}
		
		if (!newstate.equalsIgnoreCase("E") && 
			!newstate.equalsIgnoreCase("B") && 
			!newstate.equalsIgnoreCase("A")){
			throw new LoadDataException("El valor del nuevo estado no es válido");
		}
		
		// BUSCA LA ORDEN
		OrderW[] orderArr = orderserver.getByPropertyAsArray("number", orderNumber);
		if (orderArr == null || orderArr.length == 0){
			
			// SE BUSCA COMO ORDEN VeV PROVEEDOR
			return doChangeDirectOrderState(changestateParser, orderNumber, newstate);			
		}			
		order = orderArr[0];
		
		//21-02-2008 Para VeV CD al bloquear OC revisar si tiene lotes agendados
		if ((order.getOrdertypeid().equals(vevCDType.getId()) && newstate.equalsIgnoreCase("B"))) {
			DeliveryW[] deliveries = deliveryserver.getByQueryAsArray("select dl from Delivery as dl, OrderDelivery as odl, DeliveryStateType as dst where odl.delivery = dl and dl.currentstatetype = dst and odl.order.id = " + order.getId() + " and dl.vendor.id = " + order.getVendorid() + " and dst.id = " + agendSt.getId());
			if (deliveries != null && deliveries.length > 0)
				throw new LoadDataException("No se puede bloquear, existen lotes en estado Agendada");
		}
		
		//VeV CD y solicitud de eliminacion
		if (newstate.equalsIgnoreCase("E") && order.getOrdertypeid().equals(vevCDType.getId())) {
			//IRA, 22-01-2008. Eliminar OC de los lotes
			order = deliveryreportmanager.doDeleteOrderInDeliveries(order.getId());
		}

		//20120430-LFL: Oc normales se valida que no se cambie el estado si hay despachos vigentes
		if (!order.getOrdertypeid().equals(vevCDType.getId())) {

			// Busca todos los despachos de la orden
			DeliveryW[] deliveries = deliveryserver.getByQueryAsArray("select dl from Delivery as dl, OrderDelivery as odl where odl.delivery = dl and odl.order.id = " + order.getId());			

			if (deliveries != null && deliveries.length > 0) {

				// Verifica si un despacho es en el estado Cita por confirmar
				DeliveryStateTypeW deliverystatetypew = null;

				// Arreglo de los despachos

				for (int i = 0; i < deliveries.length; i++) {

					//	Busca el tipo del deliverystate
					deliverystatetypew = deliverystatetypeserver.getById(deliveries[i].getCurrentstatetypeid());					

					// Si el lote no esta en estado closed no se puede eliminar la orden
					if (!deliverystatetypew.getClosed())
						throw new LoadDataException("No se puede cambiar el estado de la orden " + orderNumber	+ " a eliminada, existen despachos en proceso!");
				}
			}			
		}
		
		String newstatecode = "";
		
		if (newstate.equalsIgnoreCase("E"))
			newstatecode = "ELIMINADA";
		else if (newstate.equalsIgnoreCase("B"))
			newstatecode = "BLOQUEADA";
		else if (newstate.equalsIgnoreCase("A"))
			newstatecode = "LIBERADA";
		else 
			throw new LoadDataException("Nuevo estado " + newstate + " no conocido!");
					
		OrderStateTypeW neworderStateType = orderstatetypeserver.getByPropertyAsSingleResult("code", newstatecode);	
		
		// GUARDA ESTADO DE LA ORDEN
		OrderStateW orderstatew = new OrderStateW();
		orderstatew.setOrderstatetypeid(neworderStateType.getId());
		orderstatew.setWhen(now);
		orderstatew.setOrderid(order.getId());

		orderstatew = orderstateserver.addOrderState(orderstatew);
		
		// ACTUALIZA LA OC
		order.setCurrentstatetypeid(neworderStateType.getId());
		order.setCurrentstatetypedate(now);
		
		order = orderserver.updateOrder(order);
				
		
		logger.info("Se cambi� exitosamente el estado de la orden " + orderNumber + " a " + neworderStateType.getName());			
		return orderNumber;
	}		
}
