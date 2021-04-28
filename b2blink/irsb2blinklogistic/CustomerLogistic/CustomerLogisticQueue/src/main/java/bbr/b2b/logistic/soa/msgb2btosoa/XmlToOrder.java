package bbr.b2b.logistic.soa.msgb2btosoa;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.TransactionTimeout;
import bbr.b2b.logistic.customer.classes.ActionServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerServerLocal;
import bbr.b2b.logistic.customer.classes.BuyerVendorServerLocal;
import bbr.b2b.logistic.customer.classes.ClientServerLocal;
import bbr.b2b.logistic.customer.classes.DetailChargeServerLocal;
import bbr.b2b.logistic.customer.classes.DetailDiscountServerLocal;
import bbr.b2b.logistic.customer.classes.DetailServerLocal;
import bbr.b2b.logistic.customer.classes.LocationServerLocal;
import bbr.b2b.logistic.customer.classes.OrderChargeServerLocal;
import bbr.b2b.logistic.customer.classes.OrderDiscountServerLocal;
import bbr.b2b.logistic.customer.classes.OrderTypeServerLocal;
import bbr.b2b.logistic.customer.classes.PredistributionServerLocal;
import bbr.b2b.logistic.customer.classes.SectionServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateServerLocal;
import bbr.b2b.logistic.customer.classes.SoaStateTypeServerLocal;
import bbr.b2b.logistic.customer.classes.VendorServerLocal;
import bbr.b2b.logistic.customer.classes.OrderServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateServerLocal;
import bbr.b2b.logistic.customer.classes.OrderStateTypeServerLocal;
import bbr.b2b.logistic.customer.data.wrappers.ActionW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerVendorW;
import bbr.b2b.logistic.customer.data.wrappers.BuyerW;
import bbr.b2b.logistic.customer.data.wrappers.ClientW;
import bbr.b2b.logistic.customer.data.wrappers.DetailChargeW;
import bbr.b2b.logistic.customer.data.wrappers.DetailDiscountW;
import bbr.b2b.logistic.customer.data.wrappers.DetailW;
import bbr.b2b.logistic.customer.data.wrappers.LocationW;
import bbr.b2b.logistic.customer.data.wrappers.OrderChargeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderDiscountW;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderStateW;
import bbr.b2b.logistic.customer.data.wrappers.OrderTypeW;
import bbr.b2b.logistic.customer.data.wrappers.OrderW;
import bbr.b2b.logistic.customer.data.wrappers.PredistributionW;
import bbr.b2b.logistic.customer.data.wrappers.SectionW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateTypeW;
import bbr.b2b.logistic.customer.data.wrappers.SoaStateW;
import bbr.b2b.logistic.customer.data.wrappers.VendorW;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.b2blink.logistic.xml.OC_customer.DiscountCharge;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions.Predistribution;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;

@Stateless(name = "handlers/XmlToOrder")
public class XmlToOrder implements XmlToOrderLocal {

	private static JAXBContext jc = null;

	private static Logger logger = Logger.getLogger(XmlToOrder.class);

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_customer");
		return jc;
	}
	
	@EJB
	SchedulerManagerLocal schedulermanager;

	@EJB
	BuyerServerLocal buyerServerLocal;

	@EJB
	BuyerVendorServerLocal buyerVendorServerLocal;

	@EJB
	VendorServerLocal vendorServerLocal;

	@EJB
	OrderServerLocal orderLocalServer;

	@EJB
	OrderTypeServerLocal orderTypeServerLocal;

	@EJB
	SoaStateTypeServerLocal soaStateTypeServerLocal;

	@EJB
	OrderStateTypeServerLocal orderStateTypeServer;

	@EJB
	LocationServerLocal locationServerLocal;

	@EJB
	ClientServerLocal clientServerLocal;

	@EJB
	OrderServerLocal orderServerLocal;

	@EJB
	DetailServerLocal detailServerLocal;

	@EJB
	PredistributionServerLocal predistributionServerLocal;

	@EJB
	OrderDiscountServerLocal orderDiscountServerLocal;

	@EJB
	OrderChargeServerLocal orderChargeServerLocal;

	@EJB
	DetailDiscountServerLocal detailDiscountServerLocal;

	@EJB
	DetailChargeServerLocal detailChargeServerLocal;

	@EJB
	SoaStateServerLocal soaStateServerLocal;

	@EJB
	OrderStateServerLocal orderStateServerLocal;

	@EJB
	SectionServerLocal sectionServerLocal;

	@EJB
	ActionServerLocal actionServerLocal;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@TransactionTimeout(1800)
	public void processMessage(Order orderCustomer) throws ServiceException, JAXBException, OperationFailedException {
			OrderW orderW = null;
			Date now = new Date();
			boolean update = false;
			boolean valid = true;
			BuyerW buyerW = null;
			VendorW vendorW = null;
			try {
				buyerW = buyerServerLocal.getByPropertyAsSingleResult("code", orderCustomer.getBuyer());
			} catch (OperationFailedException ex) {
				//TODO agregar noc y cliente
				throw new OperationFailedException("OC: "+orderCustomer.getNumber() +" Cliente: "+orderCustomer.getBuyer()+" Motivo: El Código de comprador " + orderCustomer.getBuyer() + " no existe.");
			}
			PropertyInfoDTO[] propertiesVendor = new PropertyInfoDTO[2];
			propertiesVendor[0] = new PropertyInfoDTO("id.buyerid", "buyer_id", buyerW.getId());
			propertiesVendor[1] = new PropertyInfoDTO("id.code", "code", orderCustomer.getVendor());
			List<BuyerVendorW> buyerVendorListW = buyerVendorServerLocal.getByProperties(propertiesVendor);
			if (buyerVendorListW.size() > 0) {
				vendorW = vendorServerLocal.getById(buyerVendorListW.get(0).getVendorid());
			} else {
				//TODO agregar noc y cliente
				throw new OperationFailedException("OC: "+orderCustomer.getNumber() +" Cliente: "+orderCustomer.getBuyer()+" Motivo: Código de vendedor " + orderCustomer.getVendor() + " no existe.");
			}

			OrderStateTypeW orderstatetypeLiberada = orderStateTypeServer.getByPropertyAsSingleResult("code","LIBERADA");
			OrderStateTypeW orderstatetypePendiente = orderStateTypeServer.getByPropertyAsSingleResult("code","SIN_DISTRIBUCION");
			SoaStateTypeW soaStateTypeLiberadaW = soaStateTypeServerLocal.getByPropertyAsSingleResult("code","POR_NOTIFICAR");
			OrderTypeW currentOrderTypeW = createOrderTypeIfNotExist(buyerW.getId(), orderCustomer);

			Long number = Long.valueOf(orderCustomer.getNumber());

			PropertyInfoDTO[] propertyInfoDTO = new PropertyInfoDTO[3];
			propertyInfoDTO[0] = new PropertyInfoDTO("number", "number", number);
			propertyInfoDTO[1] = new PropertyInfoDTO("valid", "valid", true);
			propertyInfoDTO[2] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerW.getId());
			List<OrderW> list = orderLocalServer.getByProperties(propertyInfoDTO);
			if (list.size() == 1) {
				orderW = list.get(0);
			} else if (list.size() == 0) {
				orderW = null;
			} else if (list.size() > 1) {
				//TODO agregar noc y cliente
				throw new OperationFailedException("OC: "+orderCustomer.getNumber() +" Cliente: "+orderCustomer.getBuyer()+" Motivo: Hay más de una orden válida");
			}

			// SOLO SI LA ORDEN NO EXISTE EN LA BDD
			if (orderW == null) {
				// SOLO eOC Y eOE
				if (orderCustomer.getPreviousordertype().isEmpty()) {
					orderW = new OrderW();
					// SOLO eOE
					if (orderCustomer.isComplete()) {
						orderW.setOrderstatetypeid(orderstatetypeLiberada.getId());
						// SOLO eOC
					} else {
						orderW.setOrderstatetypeid(orderstatetypePendiente.getId());
					}
				} else {
					// NUNCA VA A ENTRAR EN ESTE FLUJO (EN MI CASO)
					if (orderCustomer.getOrdertype().equals(orderCustomer.getPreviousordertype())) {
						orderW = new OrderW();
						orderW.setOrderstatetypeid(orderstatetypeLiberada.getId());
						// SOLO eOD
					} else {
						SendXML(orderCustomer);
						return;
					}
				}

				// SOLO SI EXISTE EN LA BDD
			} else {
	
				// SOLO eOC Y eOE
				if (orderCustomer.getPreviousordertype().isEmpty()) {
					// Logica especial
					if (orderCustomer.getAction() != null) {
						// Solo eOC especial
						if (orderCustomer.getAction().getCode().equals("IGNORAR SI EXISTE")) {
							logger.info("Orden número : " + orderCustomer.getNumber() + " se almacenará invalida ya que existe una orden previa");
							update = false;
							valid = false;
							orderW = new OrderW();
							orderW.setOrderstatetypeid(orderstatetypeLiberada.getId());
							// solo eOE especial
						} else if (orderCustomer.getAction().getCode().equals("PROCESAR Y DEJAR NULA ANTERIOR")) {
							logger.info("Se procesará orden número : " + orderCustomer.getNumber() + " y se dejará invalida la orden anterior");
							orderW.setValid(false);
							orderServerLocal.updateOrder(orderW);
							orderW = new OrderW();
							orderW.setOrderstatetypeid(orderstatetypePendiente.getId());

						} else if (orderCustomer.getAction().getCode().equals("C")) {
							// Para caso especial Ripley.
							logger.warn("La orden número : " + orderCustomer.getNumber() + " ya existe, se descarta orden entrante con action C");
							return;
						} else if (orderCustomer.getAction().getCode().equals("U")) {
							// Para caso especial Ripley.
							logger.info("Se procesará orden número : " + orderCustomer.getNumber() + " y se dejará invalida la orden anterior");
							orderW.setValid(false);
							orderServerLocal.updateOrder(orderW);
							orderW = new OrderW();
							orderW.setOrderstatetypeid(orderstatetypePendiente.getId());
						}
					}
					
					//Logica para que no se dupliquen las EOC de SODIMAC
					if(!orderCustomer.getOrdertype().equals("eOE") && orderCustomer.getOrdertype().equals("eOC")) {
						logger.info("Ya existe la orden número : " + orderCustomer.getNumber() + " de tipo " + orderCustomer.getOrdertype() +" en la BDD. Se procesara esta nueva OC y se dejará invalida la orden anterior");
						orderW.setValid(false);
						orderW.setComplete(false);
						orderServerLocal.updateOrder(orderW);
						orderW = new OrderW();
						update = true;
						orderW.setOrderstatetypeid(orderstatetypePendiente.getId());
					}

				} else {
					// NUNCA VA A ENTRAR EN ESTE FLUJO (EN MI CASO)
					if (orderCustomer.getOrdertype().equals(orderCustomer.getPreviousordertype())) {
						orderW.setValid(false);
						orderServerLocal.updateOrder(orderW);
						orderW = new OrderW();
						orderW.setOrderstatetypeid(orderstatetypeLiberada.getId());
						// SOLO eOD
					} else {
						OrderTypeW orderTypeW = orderTypeServerLocal.getById(orderW.getOrdertypeid());
						// Solo si el previous order_type es eOC en la BDD
						if (orderCustomer.getPreviousordertype().equals(orderTypeW.getCode())) {
							update = true;
							// Siempre va entrar acá
							if (orderCustomer.isComplete()) {
								orderW.setOrderstatetypeid(orderstatetypeLiberada.getId());
							}
						//Si cae acá significa que una EOD que no deberia llegar ya que la orden esta completa.	
						} else {
							logger.info("Ya existe una orden número : " + orderCustomer.getNumber() + " completa en la BDD , se descarta eOD entrante.");
							return;
						}
					}
				}
			}

			orderW.setComplete(orderCustomer.isComplete());
			orderW.setBuyerid(buyerW.getId());
			orderW.setVendorid(vendorW.getId());
			orderW.setCreated(now);
			orderW.setNumber(number);
			orderW.setStatus(orderCustomer.getStatus() != null ? orderCustomer.getStatus() : orderW.getStatus());
			orderW.setTicket(orderCustomer.getTicket() != null ? orderCustomer.getTicket() : orderW.getTicket());
			orderW.setReceiptnumber(orderCustomer.getReceiptnumber() != null ? orderCustomer.getReceiptnumber(): orderW.getReceiptnumber());
			orderW.setRequest(orderCustomer.getRequest() != null ? orderCustomer.getRequest() : orderW.getRequest());
			orderW.setNumref1(orderCustomer.getNumref1() != null ? orderCustomer.getNumref1() : orderW.getNumref1());
			orderW.setNumref2(orderCustomer.getNumref2() != null ? orderCustomer.getNumref2() : orderW.getNumref2());
			orderW.setNumref3(orderCustomer.getNumref3() != null ? orderCustomer.getNumref3() : orderW.getNumref3());
			orderW.setIssue_date(orderCustomer.getIssuedate() != null ? orderCustomer.getIssuedate().toGregorianCalendar().getTime(): orderW.getIssue_date());
			orderW.setEffectiv_date(orderCustomer.getEffectivdate() != null ? orderCustomer.getEffectivdate().toGregorianCalendar().getTime() : orderW.getEffectiv_date());
			orderW.setExpiration_date(orderCustomer.getExpirationdate() != null ? orderCustomer.getExpirationdate().toGregorianCalendar().getTime() : orderW.getExpiration_date());
			orderW.setCommitment_date(orderCustomer.getCommitmentdate() != null ? orderCustomer.getCommitmentdate().toGregorianCalendar().getTime() : orderW.getCommitment_date());
			orderW.setPayment_condition(orderCustomer.getPaymentcondition() == null || orderCustomer.getPaymentcondition().isEmpty() ? orderW.getPayment_condition(): orderCustomer.getPaymentcondition());
			orderW.setObservation(orderCustomer.getObservation() != null ? orderCustomer.getObservation() : orderW.getObservation());
			orderW.setResponsible(orderCustomer.getResponsible() == null || orderCustomer.getResponsible().isEmpty() ? orderW.getResponsible() : orderCustomer.getResponsible());
			orderW.setResponsible_email(orderCustomer.getResponsibleemail() != null ? orderCustomer.getResponsibleemail(): orderW.getResponsible_email());
			orderW.setCurrency(orderCustomer.getCurrency() != null ? orderCustomer.getCurrency() : orderW.getCurrency());

			// VALIDACION PARA CASOS ESPECIALES
			orderW.setValid(valid);

			orderW.setSoastatetypeid(soaStateTypeLiberadaW.getId());
			orderW.setOrdertypeid(currentOrderTypeW.getId());
			
			if(orderCustomer.getSection() != null ) {
				SectionW sectionW = createSectionIfNotExist(buyerW.getId(), orderCustomer);
				orderW.setSectionid(sectionW.getId());
			}

			// SET DELIVERY PLACE
			orderW.setDeliveryplaceid(orderCustomer.getDeliveryplace() != null ? createLocationIfNotExist(orderW.getNumber().toString(), buyerW.getId(), orderCustomer.getDeliveryplace()) : orderW.getDeliveryplaceid() );
			
			// SET SALE PLACE
			orderW.setSaleplaceid(orderCustomer.getSaleplace() != null ? createLocationIfNotExist(orderW.getNumber().toString(), buyerW.getId(), orderCustomer.getSaleplace()) : orderW.getSaleplaceid());

			// SET ACTION
			orderW.setActionid(createActionIfNotExist(buyerW.getId(), orderCustomer));
			// TOTAL
			if (orderCustomer.getTotal() != null) {
				orderW.setTotal(orderCustomer.getTotal());
			} else {
				// Preguntar primero el dato en la BDD antes , para no sobreescribir
				if (orderW.getTotal() != null) {
					orderW.setTotal(orderW.getTotal());
				} else {
					Double total = orderCustomer.getDetails().getDetail().stream()
							.mapToDouble(o -> o.getListcost() != null ? o.getListcost() : 0).sum();
					orderW.setTotal(total > 0 ? total : null);
				}
			}

			if (update) {
				orderW = orderServerLocal.updateOrder(orderW);
				setClient(orderW.getClientid(), orderCustomer.getClient());
			} else {
				orderW.setClientid(setClient(null, orderCustomer.getClient()));
				orderW = orderServerLocal.addOrder(orderW);
			}

			setOrderDiscount(orderW, orderCustomer.getDiscounts());
			setOrderCharges(orderW, orderCustomer.getCharges());
			addDetails(orderW, orderCustomer.getDetails());

			SoaStateW soaStateW = new SoaStateW();
			soaStateW.setWhen(now);
			soaStateW.setOrderid(orderW.getId());
			soaStateW.setSoastatetypeid(soaStateTypeLiberadaW.getId());
			soaStateServerLocal.addSoaState(soaStateW);

			OrderStateW orderStateW = new OrderStateW();
			orderStateW.setWhen(now);
			orderStateW.setOrderid(orderW.getId());
			orderStateW.setOrderstatetypeid(orderW.getOrderstatetypeid());
			orderStateServerLocal.addOrderState(orderStateW);
	}

	private OrderTypeW createOrderTypeIfNotExist(Long buyerid, Order occustomer) throws OperationFailedException, NotFoundException, AccessDeniedException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[3];
		properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
		properties[1] = new PropertyInfoDTO("code", "code", occustomer.getOrdertype().trim());
		properties[2] = new PropertyInfoDTO("name", "name", occustomer.getOrdertypename().trim());
		List<OrderTypeW> listOrderTypeW = orderTypeServerLocal.getByProperties(properties);
		
		if (listOrderTypeW.size() == 0) {
			//16916: eliminar la creación de ordertype en caso que no exista...rechazar orden
//			OrderTypeW orderTypeW = new OrderTypeW();
//			orderTypeW.setBuyerid(buyerid);
//			orderTypeW.setCode(code);
//			orderTypeW.setName(name);
//			return orderTypeServerLocal.addOrderType(orderTypeW);
			throw new OperationFailedException("OC: "+occustomer.getNumber() +" Cliente: "+occustomer.getBuyer()+" Motivo: No existe el tipo de orden "+ occustomer.getOrdertype().trim() +" - "
					+ occustomer.getOrdertypename().trim());
			
		} else if (listOrderTypeW.size() == 1) {
			
			return listOrderTypeW.get(0);
		
		} else {
			throw new OperationFailedException("OC: "+occustomer.getNumber() +" Cliente: "+occustomer.getBuyer()+" Motivo: Existe mas de un tipo de orden para el código " +occustomer.getOrdertype().trim() +" - "
					+ occustomer.getOrdertypename().trim());
		}
	}

	private SectionW createSectionIfNotExist(Long buyerid, Order occustomer)
			throws OperationFailedException, NotFoundException, AccessDeniedException {
		//String code, String name
		// orderCustomer.getSection().getCode(),orderCustomer.getSection().getName()
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
		properties[1] = new PropertyInfoDTO("code", "code", occustomer.getSection().getCode());
		List<SectionW> listOrderTypeW = sectionServerLocal.getByProperties(properties);
		if (listOrderTypeW.size() == 0) {
			SectionW sectionW = new SectionW();
			sectionW.setBuyerid(buyerid);
			sectionW.setCode(occustomer.getSection().getCode());
			sectionW.setName(occustomer.getSection().getName());
			return sectionServerLocal.addSection(sectionW);
		} else if (listOrderTypeW.size() == 1) {
			return listOrderTypeW.get(0);
		} else {//TODO agregar noc y cliente
			throw new OperationFailedException("OC: "+occustomer.getNumber() +" Cliente: "+occustomer.getBuyer()+" Motivo: Existe mas de una sección para el código");
		}
	}

	private Long createActionIfNotExist(Long buyerid, Order occustomer)
			throws OperationFailedException, NotFoundException, AccessDeniedException {
		if (occustomer.getAction() != null) {
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
			properties[1] = new PropertyInfoDTO("code", "code", occustomer.getAction().getCode());
			List<ActionW> listActionW = actionServerLocal.getByProperties(properties);
			if (listActionW.size() == 0) {
				ActionW actionW = new ActionW();
				actionW.setBuyerid(buyerid);
				actionW.setCode(occustomer.getAction().getCode());
				actionW.setName(occustomer.getAction().getDescription());
				actionW = actionServerLocal.addAction(actionW);
				return actionW.getId();
			} else if (listActionW.size() == 1) {
				return listActionW.get(0).getId();
			} else {//TODO agregar noc y cliente
				throw new OperationFailedException("OC: "+occustomer.getNumber() +" Cliente: "+occustomer.getBuyer()+" Existe mas de una action para el código");
			}
		}
		return null;

	}

	private Long createLocationIfNotExist(String ocnumber, Long buyerid, bbr.b2b.b2blink.logistic.xml.OC_customer.Local local)
			throws AccessDeniedException, OperationFailedException, NotFoundException {

		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("buyer.id", "buyer_id", buyerid);
		properties[1] = new PropertyInfoDTO("code", "code", local.getCode());
		List<LocationW> listDeliveryPlaceW = locationServerLocal.getByProperties(properties);
		if (listDeliveryPlaceW.size() == 0) {
			LocationW deliveryPlaceW = new LocationW();
			deliveryPlaceW.setBuyerid(buyerid);
			deliveryPlaceW.setCode(local.getCode());
			deliveryPlaceW.setName(local.getName());
			deliveryPlaceW.setEan13(local.getEan13());
			deliveryPlaceW.setAddress(local.getAddress());
			deliveryPlaceW = locationServerLocal.addLocation(deliveryPlaceW);
			return deliveryPlaceW.getId();
		} else if (listDeliveryPlaceW.size() == 1) {
			return listDeliveryPlaceW.get(0).getId();
		} else {//TODO agregar noc y cliente
			throw new OperationFailedException("OC: "+ocnumber +" Existe mas de un local para el código "+local.getCode());
		}
	}

	private void setOrderCharges(OrderW orderW, bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Charges charges)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		if (charges != null && charges.getCharges().size() > 0) {
			try {
				List<OrderChargeW> listOrderChargeW = orderChargeServerLocal.getByProperty("order.id", orderW.getId());
				if (listOrderChargeW.size() > 0) {
					orderChargeServerLocal.deleteByProperty("order.id", orderW.getId());
				}
			} catch (OperationFailedException | NotFoundException ex) {
				logger.info("Orden: " + orderW.getNumber() + " cargos se cargarán nuevamente");
			}
			for (DiscountCharge item : charges.getCharges()) {
				OrderChargeW orderChargeW = new OrderChargeW();
				orderChargeW.setOrderid(orderW.getId());
				orderChargeW.setDescription(item.getDescription());
				orderChargeW.setProcentaje(item.isPercentage());
				orderChargeW.setValue(item.getValue());
				orderChargeW = orderChargeServerLocal.addOrderCharge(orderChargeW);
			}
		}
	}

	private void setOrderDiscount(OrderW orderW, bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Discounts discounts)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		if (discounts != null && discounts.getDiscounts().size() > 0) {
			try {
				List<OrderDiscountW> listOrderDiscountW = orderDiscountServerLocal.getByProperty("order.id",
						orderW.getId());
				if (listOrderDiscountW.size() > 0) {
					orderDiscountServerLocal.deleteByProperty("order.id", orderW.getId());
				}
			} catch (OperationFailedException | NotFoundException ex) {
				logger.info("Orden: " + orderW.getNumber() + " descuentos se cargarán nuevamente");

			}
			for (DiscountCharge item : discounts.getDiscounts()) {
				OrderDiscountW orderDiscountW = new OrderDiscountW();
				orderDiscountW.setOrderid(orderW.getId());
				orderDiscountW.setDescription(item.getDescription());
				orderDiscountW.setProcentaje(item.isPercentage());
				orderDiscountW.setValue(item.getValue());
				orderDiscountW = orderDiscountServerLocal.addOrderDiscount(orderDiscountW);
			}
		}

	}

	private void addDetails(OrderW orderW, Details details)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		List<DetailW> ListDetailW = detailServerLocal.getByProperty("id.orderid", orderW.getId());
		if (ListDetailW.size() > 0) {
			for (Detail detail : details.getDetail()) {
				try {
					DetailW detailW = ListDetailW.stream().filter(
							o -> o.getOrderid().equals(orderW.getId()) && o.getSkubuyer().equals(detail.getSkubuyer()))
							.findFirst().get();
					updateDetail(orderW, detail, detailW);
					ListDetailW.removeIf(
							o -> o.getOrderid().equals(orderW.getId()) && o.getSkubuyer().equals(detail.getSkubuyer()));
				} catch (NullPointerException ex) {
					addDetail(orderW, detail);
				}
			}
		} else {
			for (Detail detail : details.getDetail()) {
				addDetail(orderW, detail);
			}
		}
	}

	private void addDetail(OrderW orderW, Detail detail)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		setDetail(orderW, detail, null);
	}

	private void updateDetail(OrderW orderW, Detail detail, DetailW detailW)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		setDetail(orderW, detail, detailW);
	}

	private void setDetail(OrderW orderW, Detail detail, DetailW detailW)
			throws AccessDeniedException, OperationFailedException, NotFoundException {

		boolean isUpdate = (detailW != null);
		detailW = detailW != null ? detailW : new DetailW();

		detailW.setOrderid(orderW.getId());
		detailW.setPosition(detail.getPosition());
		detailW.setSkubuyer(detail.getSkubuyer() != null ? detail.getSkubuyer() : detailW.getSkubuyer());
		detailW.setSkuvendor(detail.getSkuvendor() != null ? detail.getSkuvendor() : detailW.getSkuvendor());
		detailW.setEan13(detail.getEan13() != null ? detail.getEan13() : detailW.getEan13());
		detailW.setEan13_buyer(detail.getEan13Buyer() != null ? detail.getEan13Buyer() : detailW.getEan13_buyer());
		detailW.setProduct_description(detail.getProductdescription() != null ? detail.getProductdescription() : detailW.getProduct_description());
		detailW.setCode_umc(detail.getCodeumc() != null ? detail.getCodeumc() : detailW.getCode_umc());
		detailW.setDescription_umc(detail.getDescriptionumc() != null ? detail.getDescriptionumc() : detailW.getDescription_umc());
		detailW.setCode_umb(detail.getCodeumb() != null ? detail.getCodeumb() : detailW.getCode_umb());
		detailW.setDescription_umb(detail.getDescriptionumb() != null ? detail.getDescriptionumb() : detailW.getDescription_umb());
		detailW.setUmb_x_umc(detail.getUmbXUmc() != null ? detail.getUmbXUmc() : detailW.getUmb_x_umc());
		detailW.setQuantity_umc(detail.getQuantityumc() != null ? detail.getQuantityumc() : detailW.getQuantity_umc());
		detailW.setNumref1(detail.getNumref1() != null ? detail.getNumref1() : detailW.getNumref1());
		detailW.setNumref2(detail.getNumref2() != null ? detail.getNumref2() : detailW.getNumref2());
		detailW.setItem(detail.getItem() != null ? detail.getItem() : detailW.getItem());
		detailW.setEan1(detail.getEan1() != null ? detail.getEan1() : detailW.getEan1());
		detailW.setEan2(detail.getEan2() != null ? detail.getEan2() : detailW.getEan2());
		detailW.setEan3(detail.getEan3() != null ? detail.getEan3() : detailW.getEan3());
		detailW.setStylecode(detail.getStylecode() != null ? detail.getStylecode() : detailW.getStylecode());
		detailW.setStyledescription(detail.getStyledescription() != null ? detail.getStyledescription() : detailW.getStyledescription());
		detailW.setStylebrand(detail.getStylebrand() != null ? detail.getStylebrand() : detailW.getStylebrand());
		
		detailW.setList_cost(detail.getListcost() != null ? detail.getListcost() : detailW.getList_cost());
		detailW.setFinal_cost(detail.getFinalcost() != null ? detail.getFinalcost() : detailW.getFinal_cost());
		detailW.setList_price(detail.getListprice() != null ? detail.getListprice() : detailW.getList_price());
		
		detailW.setTolerance(detail.getTolerance() != null ? detail.getTolerance() : detailW.getTolerance());
		detailW.setProduct_delivery_date(detail.getProductdeliverydate() != null ? detail.getProductdeliverydate().toGregorianCalendar().getTime() : detailW.getProduct_delivery_date());
		detailW.setObservation(detail.getObservation() != null ? detail.getObservation() : detailW.getObservation());
		detailW.setFreight_cost(detail.getFreightcost() != null ? detail.getFreightcost() : detailW.getFreight_cost());
		detailW.setFreight_weight(detail.getFreightweight() != null ? detail.getFreightweight() : detailW.getFreight_weight());
		detailW.setCostaftertaxes(detail.getCostaftertaxes() != null ? detail.getCostaftertaxes() : detailW.getCostaftertaxes());
		detailW.setInnerpack(detail.getInnerpack() != null ? detail.getInnerpack() : 1L);

		if (isUpdate) {
			detailW = detailServerLocal.updateDetail(detailW);
		} else {
			detailW = detailServerLocal.addDetail(detailW);
		}

		setPredistributions(orderW, detailW.getSkubuyer(), detail.getPosition(), detail.getPredistributions());
		setDetailDiscounts(detailW.getOrderid(), detailW.getSkubuyer(), detail.getPosition(), detail.getDiscounts());
		setDetailCharges(detailW.getOrderid(), detailW.getSkubuyer(), detail.getPosition(), detail.getCharges());
	}

	private void setDetailCharges(Long orderid, String skubuyer, int position, Charges charges)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		if (charges != null) {
			for (DiscountCharge item : charges.getCharge()) {
				DetailChargeW detailChargeW = new DetailChargeW();
				detailChargeW.setOrderid(orderid);
				detailChargeW.setSkubuyer(skubuyer);
				detailChargeW.setPosition(position);
				detailChargeW.setCode(item.getCode());
				detailChargeW.setDescription(item.getDescription());
				detailChargeW.setProcentaje(item.isPercentage());
				detailChargeW.setValue(item.getValue());
				detailChargeW = detailChargeServerLocal.addDetailCharge(detailChargeW);
			}
		}
	}

	private void setDetailDiscounts(Long orderid, String skubuyer, int position, Discounts discounts)
			throws AccessDeniedException, OperationFailedException, NotFoundException {
		if (discounts != null) {
			for (DiscountCharge item : discounts.getDiscount()) {
				DetailDiscountW detailDiscountW = new DetailDiscountW();
				detailDiscountW.setOrderid(orderid);
				detailDiscountW.setSkubuyer(skubuyer);
				detailDiscountW.setPosition(position);
				detailDiscountW.setCode(item.getCode());
				detailDiscountW.setDescription(item.getDescription());
				detailDiscountW.setProcentaje(item.isPercentage());
				detailDiscountW.setValue(item.getValue());
				detailDiscountW = detailDiscountServerLocal.addDetailDiscount(detailDiscountW);
			}
		}

	}

	private void setPredistributions(OrderW orderW, String skubuyer, int position, Predistributions predistributions)
			throws OperationFailedException, NotFoundException, AccessDeniedException {
		PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
		properties[0] = new PropertyInfoDTO("id.orderid", "order_id", orderW.getId());
		properties[1] = new PropertyInfoDTO("id.skubuyer", "sku_buyer", skubuyer);
		List<PredistributionW> listPredistributionW = predistributionServerLocal.getByProperties(properties);
		Map<String, Long> mapLocationW = new HashMap<String, Long>();
		// No hay predistribución para el sku
		if (predistributions != null) {
			if (listPredistributionW != null && listPredistributionW.size() == 0) {
				for (Predistribution predistribution : predistributions.getPredistribution()) {
					Long deliveryPlaceId = null;
					if (predistribution.getDeliveryplace() != null) {
						deliveryPlaceId = mapLocationW.get(predistribution.getDeliveryplace().getCode());
						if (deliveryPlaceId == null) {
							deliveryPlaceId = createLocationIfNotExist(orderW.getNumber().toString(), orderW.getBuyerid(),
									predistribution.getDeliveryplace());
							mapLocationW.put(predistribution.getDeliveryplace().getCode(), deliveryPlaceId);
						}
						addPredistribution(orderW, skubuyer, position, predistribution, deliveryPlaceId);
					}

				}
			} else {

				for (Predistribution predistribution : predistributions.getPredistribution()) {
					Long deliveryPlaceId = null;
					if (predistribution.getDeliveryplace() != null) {
						deliveryPlaceId = mapLocationW.get(predistribution.getDeliveryplace().getCode());
						if (deliveryPlaceId == null) {
							deliveryPlaceId = createLocationIfNotExist(orderW.getNumber().toString(), orderW.getBuyerid(),
									predistribution.getDeliveryplace());
							mapLocationW.put(predistribution.getDeliveryplace().getCode(), deliveryPlaceId);
						}
						Long copiadeliveryPlaceId = deliveryPlaceId;
						PredistributionW predistributionW = listPredistributionW.stream()
								.filter(o -> o.getLocalid() == copiadeliveryPlaceId).findFirst().get();
						if (predistributionW != null) {
							updatePredistribution(orderW, skubuyer, position, predistributionW, predistribution,
									deliveryPlaceId);
						} else {
							addPredistribution(orderW, skubuyer, position, predistribution, deliveryPlaceId);
						}
					}
				}

			}
		}
	}

	private void addPredistribution(OrderW orderW, String skubuyer, int position, Predistribution predistribution,
			Long deliveryPlaceId) throws OperationFailedException, NotFoundException, AccessDeniedException {
		setPredistribution(orderW, skubuyer, position, predistribution, null, deliveryPlaceId);
	}

	private void updatePredistribution(OrderW orderW, String skubuyer, int position, PredistributionW predistributionW,
			Predistribution predistribution, Long deliveryPlaceId)
			throws OperationFailedException, NotFoundException, AccessDeniedException {
		setPredistribution(orderW, skubuyer, position, predistribution, predistributionW, deliveryPlaceId);
	}

	private void setPredistribution(OrderW orderW, String skubuyer, int position, Predistribution predistribution,PredistributionW predistributionW, Long deliveryPlaceId) throws OperationFailedException, NotFoundException, AccessDeniedException {
		boolean update = (predistributionW != null);
		predistributionW = predistributionW == null ? new PredistributionW() : predistributionW;
		predistributionW.setOrderid(orderW.getId());
		predistributionW.setSkubuyer(skubuyer);
		predistributionW.setPosition(position);
		predistributionW.setLocalid(deliveryPlaceId);
		predistributionW.setQuantity(predistribution.getQuantity());
		predistributionW.setShipping_quantity(predistribution.getShippingQuantity());
		predistributionW.setReceived_quantity(predistribution.getReceivedQuantity());
		predistributionW.setPending_quantity(predistribution.getPendingQuantity());
		if (update) {
			predistributionServerLocal.updatePredistribution(predistributionW);
		} else {
			predistributionServerLocal.addPredistribution(predistributionW);
		}
	}

	private Long setClient(Long clientid, Client client) throws AccessDeniedException, OperationFailedException, NotFoundException {
		if (client != null) {
			ClientW clientW = null;
			if (clientid != null) {
				clientW = clientServerLocal.getById(clientid);
				clientW.setName(client.getName() != null ? client.getName() : clientW.getName());
				clientW.setIdentity(client.getIdentity() != null ? client.getIdentity() : clientW.getIdentity());
				clientW.setCheck_digit(client.getCheckdigit() != null ? client.getCheckdigit() : clientW.getCheck_digit());
				clientW.setPhone(client.getPhone() != null ? client.getPhone() : clientW.getPhone());
				clientW.setPhone2(client.getPhone2() != null ? client.getPhone2() : clientW.getPhone2());
				clientW.setAddress(client.getAddress() != null ? client.getAddress() : clientW.getAddress());
				clientW.setStreet_number(client.getStreetnumber() != null ? client.getStreetnumber() : clientW.getStreet_number());
				clientW.setDepartmet_number(client.getApartment() != null ? client.getApartment() : clientW.getDepartmet_number());
				clientW.setHouse_number(client.getHousenumber() != null ? client.getHousenumber() : clientW.getHouse_number());
				clientW.setRegion(client.getRegion() != null ? client.getRegion() : clientW.getRegion());
				clientW.setCommune(client.getCommune() != null ? client.getCommune() : clientW.getCommune());
				clientW.setCity(client.getCity() != null ? client.getCity() : clientW.getCity());
				clientW.setLocation(client.getLocation() != null ? client.getLocation() : clientW.getLocation());
				clientW.setObservation(client.getObservation() != null ? client.getObservation() : clientW.getObservation());
				clientW = clientServerLocal.updateClient(clientW);
			} else {
				clientW = new ClientW();
				clientW.setName(client.getName());
				clientW.setIdentity(client.getIdentity());
				clientW.setCheck_digit(client.getCheckdigit());
				clientW.setPhone(client.getPhone());
				clientW.setPhone2(client.getPhone2());
				clientW.setAddress(client.getAddress());
				clientW.setStreet_number(client.getStreetnumber());
				clientW.setDepartmet_number(client.getApartment());
				clientW.setHouse_number(client.getHousenumber());
				clientW.setRegion(client.getRegion());
				clientW.setCommune(client.getCommune());
				clientW.setCity(client.getCity());
				clientW.setLocation(client.getLocation());
				clientW.setObservation(client.getObservation());
				clientW = clientServerLocal.addClient(clientW);
			}
			return clientW.getId();
		}
		return null;
	}

	private void SendXML(Order orderCustomer) throws OperationFailedException, JAXBException, AccessDeniedException, NotFoundException {
		logger.info("Order: " + orderCustomer.getNumber() + " tipo: " + orderCustomer.getOrdertype() + " se envía a cola general, a la espera de llegada de EOC");
		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderCustomer, sw);
		String result = sw.toString();
		//Asegurarse después de enviarse la EOD a la cola general de detener el proceso de guardado en la bdd hasta que llegue la EOC.
		schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl", "OcCs",String.valueOf(orderCustomer.getNumber()), result, Charset.forName("UTF-8"));
	}

}
