package bbr.b2b.logistic.msgregionalb2b;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.RejectedExecutionException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.jboss.annotation.IgnoreDependency;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderDetailPK;
import bbr.b2b.regional.logistic.datings.classes.DockServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ModuleServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ReserveDetailServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ModuleW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.report.classes.AsnDetailComparator;
import bbr.b2b.regional.logistic.deliveries.report.classes.AsnDetailW;
import bbr.b2b.regional.logistic.deliveries.report.classes.AsnPredeliveryDetailComparator;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataF0Comparator;
import bbr.b2b.regional.logistic.deliveries.report.classes.PredistributedPackingListDataJ0Comparator;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.msgregionalb2b.AsnToXmlRemote;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.regional.logistic.utils.BackUpUtils;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.xml.qasn.A2;
import bbr.b2b.regional.logistic.xml.qasn.A3;
import bbr.b2b.regional.logistic.xml.qasn.C3;
import bbr.b2b.regional.logistic.xml.qasn.D0;
import bbr.b2b.regional.logistic.xml.qasn.E12;
import bbr.b2b.regional.logistic.xml.qasn.E13;
import bbr.b2b.regional.logistic.xml.qasn.F0;
import bbr.b2b.regional.logistic.xml.qasn.G10;
import bbr.b2b.regional.logistic.xml.qasn.H0;
import bbr.b2b.regional.logistic.xml.qasn.J0;
import bbr.b2b.regional.logistic.xml.qasn.K11;
import bbr.b2b.regional.logistic.xml.qasn.L0;
import bbr.b2b.regional.logistic.xml.qasn.QASN;

@Stateless(name = "handlers/AsnToXml")
public class AsnToXml implements AsnToXmlLocal, AsnToXmlRemote {
	
	private static Logger logger = Logger.getLogger(AsnToXml.class);

	private static JAXBContext jc = null;

	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.regional.logistic.xml.qasn");
		return jc;
	}
	
	private HashMap<Long, List<AsnDetailW>> asnDetailMap;
	private Map<String, Double> ocItRefLoUnitMap;
	private Map<OrderDetailPK, Integer> odBdMap;
	private FlowTypeW flowType;
	private DeliveryTypeW deliveryType;
	private String deliveryLocationCode;
	private DockW arriveDock;
	private Date arrive = null;
	private Date leave = null;
	private int cells;
	
	@EJB
	@IgnoreDependency
	SchedulerManagerLocal schedulermanager;
	
	@EJB
	DeliveryServerLocal deliveryserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	ModuleServerLocal moduleserver;

	@EJB
	DockServerLocal dockserver;

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	BulkDetailServerLocal bulkdetailserver;

	@EJB
	DeliveryTypeServerLocal deliverytypeserver;

	@EJB
	ReserveDetailServerLocal reservedetailserver;

	CommonQueueUtils qutils = CommonQueueUtils.getInstance();

	// JPE 20191205
	public void processDeliveryMessages(DeliveryW delivery, DatingW dating, Long[] orderIds, Date requestedDate, int messageType, boolean isImported, boolean send) throws LoadDataException, ServiceException, Exception {
		
		// Detalles de bultos del despacho				
		AsnDetailW[] asnDetails = bulkdetailserver.getAsnDetailsByDelivery(delivery.getId());
		
		asnDetailMap = new HashMap<Long, List<AsnDetailW>>();
		ocItRefLoUnitMap = new HashMap<String, Double>();
		odBdMap = new TreeMap<OrderDetailPK, Integer>();
		List<AsnDetailW> asnDetailList = null;
		OrderDetailPK odKey = null;
		String ocItRefLo = "";
		Integer amount = 0;
		for (AsnDetailW asnDetail : asnDetails) {
			// Detalles del ASN x orden del despacho
			if (asnDetailMap.containsKey(asnDetail.getOrderid())) {
				asnDetailList = asnDetailMap.get(asnDetail.getOrderid());
			}
			else {
				asnDetailList = new ArrayList<AsnDetailW>();				
			}
			asnDetailList.add(asnDetail);
			asnDetailMap.put(asnDetail.getOrderid(), asnDetailList);
			
			// Cantidad de detalles de bulto x detalle de orden del despacho
			odKey = new OrderDetailPK(asnDetail.getOrderid(), asnDetail.getItemid());
			if (odBdMap.containsKey(odKey)) {
				amount = odBdMap.get(odKey);
			}
			else {
				amount = 0;
			}
			amount++;
			odBdMap.put(odKey, amount);
			
			// Totalizador de unidades por combinación orden-producto-referencia-local de destino
			ocItRefLo = asnDetail.getOrderid() + "_" + asnDetail.getItemid() + "_" + asnDetail.getRefdocument() + "_" + asnDetail.getDestinationlocationid();
			if (!ocItRefLoUnitMap.containsKey(ocItRefLo)) {
				ocItRefLoUnitMap.put(ocItRefLo, asnDetail.getUnits());
			} else {
				ocItRefLoUnitMap.put(ocItRefLo, ocItRefLoUnitMap.get(ocItRefLo) + asnDetail.getUnits());
			}
		}
		
		deliveryLocationCode = locationserver.getById(delivery.getLocationid()).getCode();
		
		flowType = flowtypeserver.getById(delivery.getFlowtypeid());
		
		deliveryType = deliverytypeserver.getById(delivery.getDeliverytypeid());
		
		// Obtener los m�dulos de la agenda
		ModuleW[] modules = moduleserver.getModules();
		Map<Long, Date> startsMap = new HashMap<Long, Date>();
		Map<Long, Date> endsMap = new HashMap<Long, Date>();
		for (ModuleW module : modules) {
			startsMap.put(module.getId(), module.getStarts());
			endsMap.put(module.getId(), module.getEnds());
		}
		
		ReserveDetailW[] reserveDetails = reservedetailserver.getByPropertyAsArray("reserve.id", dating.getId());
		cells = reserveDetails.length;
		
		Date moduleTime = null;
		Long arriveDockId = null;
		for (ReserveDetailW reserveDetail : reserveDetails) {
			// Obtener el horarios de llegada y salida
			// Obtener el and�n de llegada
			moduleTime = startsMap.get(reserveDetail.getModuleid());
			if (arrive == null) {
				arrive = moduleTime;
				arriveDockId = reserveDetail.getDockid();
			}
			else if (moduleTime.before(arrive)) {
				arrive = moduleTime;
				arriveDockId = reserveDetail.getDockid();
			}

			moduleTime = endsMap.get(reserveDetail.getModuleid());
			if (leave == null) {
				leave = moduleTime;
			}
			else if (moduleTime.after(leave)) {
				leave = moduleTime;
			}
		}
		
		arriveDock = dockserver.getById(arriveDockId);
		
		Calendar arriveDate = Calendar.getInstance();				
		arriveDate.setTime(dating.getWhen());
				
		Calendar arriveTime = Calendar.getInstance();
		arriveTime.setTime(arrive);
		
		arriveDate.set(Calendar.HOUR_OF_DAY, arriveTime.get(Calendar.HOUR_OF_DAY));
		arriveDate.set(Calendar.MINUTE, arriveTime.get(Calendar.MINUTE));
		arriveDate.set(Calendar.SECOND, 0);
		arriveDate.set(Calendar.MILLISECOND, 0);
		
		arrive = arriveDate.getTime();
		
		Calendar leaveDate = Calendar.getInstance();				
		leaveDate.setTime(dating.getWhen());
				
		Calendar leaveTime = Calendar.getInstance();
		leaveTime.setTime(leave);
		
		leaveDate.set(Calendar.HOUR_OF_DAY, leaveTime.get(Calendar.HOUR_OF_DAY));
		leaveDate.set(Calendar.MINUTE, leaveTime.get(Calendar.MINUTE));
		leaveDate.set(Calendar.SECOND, 0);
		leaveDate.set(Calendar.MILLISECOND, 0);
		
		leave = leaveDate.getTime();
		
		// Si el proveedor es 'Cencosud Importado' se envía un ASN x orden
		if(isImported) {
			for(Long orderId : orderIds) {
				// JPE 20200626
				// Validar que existan los detalles (podr�a ser orden de entrega cerrada y sin bultos)
				if (asnDetailMap.containsKey(orderId)) {
					asnDetailList = asnDetailMap.get(orderId);
					asnDetails = (AsnDetailW[]) asnDetailList.toArray(new AsnDetailW[asnDetailList.size()]);
					
					logger.info("Procesando mensaje para despacho " + delivery.getNumber() + " - orden " + asnDetails[0].getOrdernumber());
					
					processMessage(delivery, asnDetails, requestedDate, messageType, send);
				}
			}
		}
		// De lo contrario se envía un solo ASN x despacho
		else {
			logger.info("Procesando mensaje para despacho " + delivery.getNumber() + " (todas las órdenes)");
			
			processMessage(delivery, asnDetails, requestedDate, messageType, send);
		}		
	}
	
	// JPE 20191203
	private String processMessage(DeliveryW delivery, AsnDetailW[] asnDetails, Date requestedDate, int messageType, boolean send) throws LoadDataException, ServiceException, Exception {
		
		String result = "";

		if (messageType != 0 && messageType != 1) {
			throw new ServiceException("Par�metro type inválido");
		}

		// Definiciones que dependen del tipo de mensaje de ASN
		int B11 = 0;
		String G13 = "";
		int E8 = 0;
		switch (messageType) {
		case 0: // AGREGA
			B11 = 0;
			G13 = "02";
			E8 = 10;
			break;
		case 1: // ELIMINA
			B11 = 99;
			G13 = "03";
			E8 = 99;
			break;
		}
		
		String deliveryTypeName = "";
		// Ver VeV
		if (deliveryType.getCode().equals("STOCK")) {
			deliveryTypeName = "STK";
		}
		else if (deliveryType.getCode().equals("TIENDA")) {
			deliveryTypeName = "XDC";
		}
		else if (deliveryType.getCode().equals("VEVCD")) {
			deliveryTypeName = "VEV";
		}
		
		// Secuenciador
		Sequencenbr sequencenbr = new Sequencenbr();
		sequencenbr.setSequence(1);
		
		// Ordenamiento de los detalles de bulto
		Arrays.sort(asnDetails, new AsnDetailComparator());
		
		JAXBContext jc = getJC();
		bbr.b2b.regional.logistic.xml.qasn.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qasn.ObjectFactory();
		
		// ***********************************INICIO XML*********************************************//
		// Nodo ra�z
		QASN qasn = objFactory.createQASN();
		
		// A0: Message ID
		qasn.setA0("PO-05-Z-U");

		// A1: Sequence (a partir del N°mero de transacción)
		String transactionNumber = deliveryserver.getNextSequenceTransactionNumber(12);
		int n_secuencia = Integer.parseInt(transactionNumber);
		qasn.setA1(n_secuencia);

		// A2: Dating
		A2 a2Parser = objFactory.createA2();

			// B0: Shipment Type
			a2Parser.setB0("I");

			// B1: Shipment NBR
			// JPE 20190821 El N°mero de cita que se envía depende de si el proveedor es nacional o importado
			a2Parser.setB1(asnDetails[0].getAsnnumber().toString()); // es el mismo N°mero para los detalles de este ASN

			// B2: Warehouse
			a2Parser.setB2(qutils.addZeros(deliveryLocationCode, 3));
			
			// B3: Expect Start Date
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			a2Parser.setB3(df.format(arrive));

			// B4: Duration in Min
			int moduleInterval = 15; // 15 minutos por defecto
			int durationInMinutes = cells * moduleInterval; // OJO: esto estaba as� (N°mero de detalles de la agenda), no es la diferencia (leave - arrive)
			a2Parser.setB4(durationInMinutes);
			
			// B5: Ppt signature indication
			a2Parser.setB5(90);

			// B6: Door scheduled type
			a2Parser.setB6(flowType.getCode());
			
			// B7: Scheduled dock
			if (deliveryLocationCode.equalsIgnoreCase("400")) {
				a2Parser.setB7("RECJ");
			}
			else if (deliveryLocationCode.equalsIgnoreCase("200")) {
				a2Parser.setB7("RECI");
			}
			else {
				a2Parser.setB7("RECE");
			}
			
			// B8: Scheduled door
			a2Parser.setB8(arriveDock.getCode());
			
			// B9: Departure date
			a2Parser.setB9(df.format(leave));
			
			// B10: Appt Priority
			a2Parser.setB10(1);

			// B11: Status Code
			a2Parser.setB11(B11);
			
			// B12: Appt NBR
			a2Parser.setB12(qutils.trimZeros(asnDetails[0].getAsnnumber().toString())); // es el mismo N°mero para los detalles de este ASN

		qasn.setA2(a2Parser);
		
		// A3: ASN
		A3 a3parser = objFactory.createA3();

			// C0: Shipment NBR
			a3parser.setC0(asnDetails[0].getAsnnumber().toString()); // es el mismo N°mero para los detalles de este ASN

			// C1: To Location
			a3parser.setC1(qutils.addZeros(deliveryLocationCode, 3));

			// C2: Manifest NBR
			a3parser.setC2((asnDetails == null || asnDetails.length == 0) ? "" : asnDetails[0].getRefdocument());  // es el mismo documento para los detalles de este ASN

			// C3: Orders Block
			C3 c3Parser = objFactory.createC3();
				// 	D0: Order
				List<D0> d0List = c3Parser.getD0();
				D0 d0Parser = null;
				E12 e12Parser = null;
				List<J0> j0List = null;
				J0 j0Parser = null;
				K11 k11Parser = null;
				E13 e13Parser = null;
				List<L0> l0List = null;
				L0 l0Parser = null;
				
				Long actualOrderId = -1L;
				Long actualBulkId = -1L;
				int itemLpnSequence = 0;
				int lpns = 0;
				Double units = 0.0;
				for (AsnDetailW asnDetail : asnDetails) {
					// Si es una nueva orden
					if (!actualOrderId.equals(asnDetail.getOrderid())) {
						actualOrderId = asnDetail.getOrderid();
						
						if (d0Parser != null) {
							// Cases shipped
							d0Parser.setE2(lpns);
							
							// E3: Units shipped
							d0Parser.setE3(units);
							
								// Insertar K11 con todos los skus del �ltimo LPN de la orden anterior
								j0Parser.setK11(k11Parser);
								
								// Insertar J0 con el �ltimo LPN de la orden anterior
								j0List.add(j0Parser);
							
							// Insertar E13 con todos los LPNs de la orden anterior
							d0Parser.setE13(e13Parser);
							
							d0List.add(d0Parser);
						}
						
						// Inicializar contador de LPNs
						lpns = 0;
						
						// Inicializar las unidades totales de la orden
						units = 0.0;
						
						d0Parser = objFactory.createD0();
						
						// E0: Work Order NBR
						d0Parser.setE0(qutils.addZeros(asnDetail.getOrdernumber().toString(), 6));
						
						// E1: Cut NBR
						d0Parser.setE1(asnDetail.getVendorcode());
						
						// E4: Shipped Date
						d0Parser.setE4(df.format(delivery.getCreated()));
						
						// E5: Sched Start rcvdate
						d0Parser.setE5(df.format(requestedDate));
						
						// E6: Sched end recv date
						d0Parser.setE6(df.format(requestedDate));
						
						// E7: Reference 1
						d0Parser.setE7(asnDetail.getAsnnumber().toString());
						
						// E8: Status Code
						d0Parser.setE8(E8);
						
						// E9: Dock Door trailer type
						d0Parser.setE9(flowType.getCode());
						
						// E10: Asn Original Type
						d0Parser.setE10("P");
						
						// E11: Shipment Type
						d0Parser.setE11(deliveryTypeName);
						
						// E12: ASN Detail Block
							e12Parser = listaAsnDetails(asnDetail.getOrderid(), G13, sequencenbr);
							
							if (e12Parser == null || e12Parser.getF0() == null || e12Parser.getF0().size() == 0) {
								throw new ServiceException("No se puede enviar un Packing sin detalles");
							}
						
						d0Parser.setE12(e12Parser);
						
						// E13: Bulks Block
							e13Parser = objFactory.createE13();
							
							// J0: Bulk
							j0List = e13Parser.getJ0();		
								j0Parser = null;
					}
					
					// Si es el mismo bulto
					if (actualBulkId.equals(asnDetail.getBulkid())) {
						itemLpnSequence++;
						
						// Totalizar de unidades x orden
						units += asnDetail.getUnits();
					}
					// Si es un nuevo bulto
					else {
						actualBulkId = asnDetail.getBulkid();
						
						if (j0Parser != null) {
							// Insertar K11 con todos los skus del LPN anterior
							j0Parser.setK11(k11Parser);
							
							// Insertar J0 con el LPN anterior
							j0List.add(j0Parser);
						}
						
						// Contador de LPNs
						lpns++;
						
						// Totalizar de unidades x orden
						units += asnDetail.getUnits();
						
						j0Parser = objFactory.createJ0();
						
						// K0: Case NBR
						j0Parser.setK0(asnDetail.getBulkcode());
						
						// K1: Distro NBR (�ndice de despachos de la orden)
						j0Parser.setK1(getDistro4(asnDetail.getOrdernumber(), asnDetail.getOrderdeliveryindex()));
						
						// K2: Warehouse
						j0Parser.setK2(qutils.addZeros(deliveryLocationCode, 3));
						
						// K3: Original shipment NBR
						j0Parser.setK3(asnDetail.getAsnnumber().toString());
												
						// K4: Process immd needs
						j0Parser.setK4("Y");
						
						// K5: Po NBR
						j0Parser.setK5(qutils.addZeros(String.valueOf(asnDetail.getOrdernumber()), 6));
						
						// K6: Status Code
						j0Parser.setK6("00");
						
						// K7: Put away type
						j0Parser.setK7("");
						
						// K8: Vendor id
						j0Parser.setK8(asnDetail.getVendorcode());
						
						// K9: Company
						j0Parser.setK9("PAR");
						
						// K10: Division
						j0Parser.setK10("001");
						
						// K11: Items Block
						k11Parser = objFactory.createK11();
						
							// L0: Item
							l0List = k11Parser.getL0();
							
							itemLpnSequence = 1;
					}
					
							l0Parser = objFactory.createL0();
					
							// M0: Case NBR
							l0Parser.setM0(asnDetail.getBulkcode());
							
							// M1: Sku Sequence NBR
							l0Parser.setM1(itemLpnSequence);
							
							// M2: Company
							l0Parser.setM2("PAR");
							
							// M3: Division
							l0Parser.setM3("001");
					
							// M4: Cn try of orgn1
							l0Parser.setM4(asnDetail.getDestinationlocationcode());
							
							// M5: Size desc
							l0Parser.setM5(asnDetail.getIteminternalcode());
							
							// M6: Batch Number
							l0Parser.setM6(asnDetail.getDistributionordernumber());
							
							// M7: Original Quantity
							l0Parser.setM7(asnDetail.getUnits());
							
							// M8: Shipped asn quantity
							l0Parser.setM8(asnDetail.getUnits());
							
							// M9: Standard pack qty
							l0Parser.setM9(asnDetail.getIteminnerpack());
							
							// M10: Vendor item number
							l0Parser.setM10(asnDetail.getVendoritemcode());
							
							// M11: Poline NBR
							l0Parser.setM11(asnDetail.getPredeliverysequence());
							
						l0List.add(l0Parser);
				}
					
					// E2: Cases shipped
					d0Parser.setE2(lpns); // contador de la �ltima orden
						
					// Units shipped
					d0Parser.setE3(units); // totalizador de la �ltima orden
						
						// Insertar K11 con todos los skus del �ltimo LPN de la �ltima orden
						j0Parser.setK11(k11Parser);
					
						// Insertar J0 con el �ltimo LPN de la �ltima orden
						j0List.add(j0Parser);
						
					// Insertar E13 con todos los LPNs de la orden anterior
					d0Parser.setE13(e13Parser);
					
				d0List.add(d0Parser);

			a3parser.setC3(c3Parser);

		qasn.setA3(a3parser);
		
		// ***********************************FIN XML*********************************************//

		try {
			// Obtiene string XML para enviarlo a la cola
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(qasn, sw);
			result = sw.toString();
	
			// RESPALDA MENSAJE
			doBackUpMessage(result, asnDetails[0].getAsnnumber() + "_" + delivery.getNumber().toString(), "ASN");
	
			// Planifica el envio
			if (send) {
				schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_asn", "asn", delivery.getNumber().toString(), result);
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return result;
	}
	
	// JPE 20190514
	public String processPredistributedPackingListMessage(List<PredistributedPackingListDataDTO> pplData, String datingnumber, Long deliverylocationid, String datingdate, String datingtime, Integer durationinminutes, String dockcode) throws LoadDataException, ServiceException, Exception {
		
		String result = "";
		
		logger.info("Procesando mensaje. Orden " + pplData.get(0).getOrdernumber());
		
		JAXBContext jc = getJC();
		bbr.b2b.regional.logistic.xml.qasn.ObjectFactory objFactory = new bbr.b2b.regional.logistic.xml.qasn.ObjectFactory();

		// Local de despacho
		LocationW deliveryLocation = locationserver.getById(deliverylocationid);

		// Totalizadores
		Map<String, Integer> itRefLoUnitMap = new HashMap<String, Integer>();
		Map<Long, Integer> itLpnMap = new HashMap<Long, Integer>();
		String itRefLoKey = "";
		Integer amount;
		for (PredistributedPackingListDataDTO ppl : pplData) {
			// Acumula las unidades por orden-sku-documento tributario-tienda
			itRefLoKey = ppl.getItemid() + "_" + ppl.getTaxdocumentnumber() + "_" + ppl.getLocationid();
			if (!itRefLoUnitMap.containsKey(itRefLoKey)) {
				itRefLoUnitMap.put(itRefLoKey, (int)ppl.getUnits().doubleValue());
			} else {
				itRefLoUnitMap.put(itRefLoKey, itRefLoUnitMap.get(itRefLoKey) + (int)(ppl.getUnits().doubleValue()));
			}
			
			// Acumula los LPN embarcados por orden-sku
			if (itLpnMap.containsKey(ppl.getItemid())) {
				amount = itLpnMap.get(ppl.getItemid());
			}
			else {
				amount = 0;
			}
			amount++;
			itLpnMap.put(ppl.getItemid(), amount);
		}
		
		// ***********************************INICIO XML*********************************************//
		// Nodo ra�z
		QASN qasn = objFactory.createQASN();
		
		// A0: Message ID
		qasn.setA0("PO-05-Z-U");

		// A1: Sequence (a partir del N°mero de transacción)
		String transactionNumber = deliveryserver.getNextSequenceTransactionNumber(12);
		int n_secuencia = Integer.parseInt(transactionNumber);
		qasn.setA1(n_secuencia);

		// A2: Dating
		A2 a2Parser = objFactory.createA2();

			// B0: Shipment Type
			a2Parser.setB0("I");

			// B1: Shipment NBR
			a2Parser.setB1(datingnumber);

			// B2: Warehouse
			a2Parser.setB2(qutils.addZeros(deliveryLocation.getCode(), 3));

			// B3: Expect Start Date
			Calendar datingDate = Calendar.getInstance();
			datingDate.setTime(DateConverterFactory2.StrToDate(datingdate));
					
			Calendar datingTime = Calendar.getInstance();
			datingTime.setTime(DateConverterFactory2.StrToDate(datingtime));
			
			datingDate.set(Calendar.HOUR_OF_DAY, datingTime.get(Calendar.HOUR_OF_DAY));
			datingDate.set(Calendar.MINUTE, datingTime.get(Calendar.MINUTE));
			datingDate.set(Calendar.SECOND, 0);
			datingDate.set(Calendar.MILLISECOND, 0);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			a2Parser.setB3(df.format(datingDate.getTime()));

			// B4: Duration in Min
			a2Parser.setB4(durationinminutes);

			// B5: Ppt signature indication
			a2Parser.setB5(90);

			// B6: Door scheduled type
			// Tipo de flujo
			FlowTypeW flowType = flowtypeserver.getById(pplData.get(0).getFlowtypeid());
			a2Parser.setB6(flowType.getCode());

			// B7: Scheduled dock
			if (deliveryLocation.getCode().equalsIgnoreCase("400")) {
				a2Parser.setB7("RECJ");
			}
			else if (deliveryLocation.getCode().equalsIgnoreCase("200")) {
				a2Parser.setB7("RECI");
			}
			else {
				a2Parser.setB7("RECE");
			}
			
			// B8: Scheduled door
			a2Parser.setB8(dockcode);

			// B9: Departure date
			Calendar departureDate = Calendar.getInstance();
			departureDate.setTime(datingDate.getTime());
			departureDate.add(Calendar.MINUTE, durationinminutes);
			a2Parser.setB9(df.format(departureDate.getTime()));

			// B10: Appt Priority
			a2Parser.setB10(1);

			// B11: Status Code
			a2Parser.setB11(0); // Valor propio de CREAción

			// B12: Appt NBR
			a2Parser.setB12(datingnumber);

		qasn.setA2(a2Parser);

		// A3: ASN
		A3 a3parser = objFactory.createA3();

			// C0: Shipment NBR
			a3parser.setC0(datingnumber);

			// C1: To Location
			a3parser.setC1(qutils.addZeros(deliveryLocation.getCode(), 3));

			// C2: Manifest NBR
			a3parser.setC2(pplData == null || pplData.size() == 0 ? "" : pplData.get(0).getTaxdocumentnumber().toString());

			// C3: Orders Block
			C3 c3Parser = objFactory.createC3();
				// 	D0: Order
				List<D0> d0List = c3Parser.getD0();
				D0 d0Parser = objFactory.createD0();
				
					// E0: Work Order NBR
					d0Parser.setE0(qutils.addZeros(pplData.get(0).getOrdernumber().toString(), 6));
					
					// E1: Cut NBR
					d0Parser.setE1(pplData.get(0).getVendorcode());
					
					// E4: Shipped Date
					d0Parser.setE4(df.format(new Date()));

					// E5: Sched Start rcvdate
					d0Parser.setE5(df.format(datingDate.getTime()));
				
					// E6: Sched end recv date
					d0Parser.setE6(df.format(departureDate.getTime()));

					// E7: Reference 1
					d0Parser.setE7(datingnumber);

					// E8: Status Code
					d0Parser.setE8(10); // Valor propio de CREAción

					// E9: Dock Door trailer type
					d0Parser.setE9(flowType.getCode());

					// E10: Asn Original Type
					d0Parser.setE10("P");

					// E11: Shipment Type
					d0Parser.setE11("STK"); // Stock (tratada como Predistribuida)
					
				PredistributedPackingListDataF0Comparator f0Comparator = new PredistributedPackingListDataF0Comparator();
				Arrays.sort(pplData.toArray(new PredistributedPackingListDataDTO[pplData.size()]), f0Comparator);
				
				E12 e12Parser = null;
				List<F0> f0List = null;
				F0 f0Parser = null;
				G10 g10Parser = null;
				List<H0> h0List = null;
				H0 h0Parser = null;
				int localItemTaxDocumentSequence = 0;
				for (PredistributedPackingListDataDTO ppl : pplData) {
					// E12: ASN Detail Block
					if (e12Parser == null) {
						e12Parser = objFactory.createE12();
						
						// F0: ASN Details
						f0List = e12Parser.getF0();
					}
						
						if (f0Parser == null ||
								!f0Parser.getG3().equals(ppl.getIteminternalcode()) || !f0Parser.getG91().equals(ppl.getTaxdocumentnumber().toString())) {
							if (f0Parser != null) {
								// Insertar G10 con todos los locales de la combinación sku-documento tributario anterior
								f0Parser.setG10(g10Parser);
								
								// Insertar F0 con la combinación sku-documento tributario anterior
								f0List.add(f0Parser);
							}
							
							// Inicializar la secuencia de locales de cada combinación sku-documento tributario
							localItemTaxDocumentSequence = 0;
							
							f0Parser = objFactory.createF0();
							
							// G0: Shipment NBR
							f0Parser.setG0(datingnumber);

							// G1: Company
							f0Parser.setG1("PAR");

							// G2: Division
							f0Parser.setG2("001");

							// G3: Size Desc
							f0Parser.setG3(ppl.getIteminternalcode());

							// G4: Batch Number
							f0Parser.setG4(ppl.getDistributionordernumber());
							
							// G5: Standard pack qty
							f0Parser.setG5(ppl.getInnerpack());
							
							// G6: Cases shipped
							f0Parser.setG6(itLpnMap.get(ppl.getItemid()));

							// G7: Process immd needs
							f0Parser.setG7("Y");

							// G8: Vendor item number
							f0Parser.setG8(ppl.getVendoritemcode());

							// G9: Po NBR
							f0Parser.setG9(qutils.addZeros(ppl.getOrdernumber().toString(), 6));

							// G91: Ref doc
							f0Parser.setG91(ppl.getTaxdocumentnumber().toString());

							// G10: Locals Block
							g10Parser = objFactory.createG10();
								
								// H0: Local
								h0List = g10Parser.getH0();
								h0Parser = null;
							
							// G11: Put away type
							f0Parser.setG11("");

							// G12: Vendor id
							f0Parser.setG12(ppl.getVendorcode());

							// G13: Action code
							f0Parser.setG13("02"); // Valor propio de CREAción
						}
						
						if (h0Parser == null || !h0Parser.getI0().equals(ppl.getLocationcode())) {
								h0Parser = objFactory.createH0();
									
									// I0: Cn try of orgn
									h0Parser.setI0(ppl.getLocationcode());

									// I1: Poline NBR
									h0Parser.setI1(ppl.getSequence());

									// I2: Units shipped
									h0Parser.setI2(itRefLoUnitMap.get(ppl.getItemid() + "_" + ppl.getTaxdocumentnumber() + "_" + ppl.getLocationid()));

									// I3: Sequence NBR
									h0Parser.setI3(++localItemTaxDocumentSequence);
									
								h0List.add(h0Parser);
						}
															
					d0Parser.setE12(e12Parser);
					
				} // E12 for end
							
							// Insertar G10 con todos los locales de la combinación sku-documento tributario
							f0Parser.setG10(g10Parser);
						
							// Insertar F0 con la �ltima combinación sku-documento tributario
							f0List.add(f0Parser);
				
					// Insertar E12 con todas las combinaciones sku-documento tributario
					d0Parser.setE12(e12Parser);
					
				PredistributedPackingListDataJ0Comparator j0Comparator = new PredistributedPackingListDataJ0Comparator();
				Arrays.sort(pplData.toArray(new PredistributedPackingListDataDTO[pplData.size()]), j0Comparator);
		
				E13 e13Parser = null;
				List<J0> j0List = null;
				J0 j0Parser = null;
				K11 k11Parser = null;
				List<L0> l0List = null;
				L0 l0Parser = null;
				int lpns = 0;
				int lpnItemSequence = 0;
				Double units = 0D;
				for (PredistributedPackingListDataDTO ppl : pplData) {
					// E13: Bulks Block
					if (e13Parser == null) {
						e13Parser = objFactory.createE13();
						
						// J0: Bulk
						j0List = e13Parser.getJ0();
					}

						if (j0Parser == null || !j0Parser.getK0().equals(ppl.getLpncode())) {
							if (j0Parser != null) {
								// Insertar K11 con todos los skus del LPN anterior
								j0Parser.setK11(k11Parser);
								
								// Insertar J0 con el LPN anterior
								j0List.add(j0Parser);
							}
							
							// Contador de LPNs
							lpns++;
							
							// Inicializar la secuencia de productos de cada LPN
							lpnItemSequence = 0;
							
							j0Parser = objFactory.createJ0();
							
							// K0: Case NBR
							j0Parser.setK0(ppl.getLpncode());
							
							// K1: Distro NBR (Validar "001", que se refiere al �ndice de despachos de la orden)
							j0Parser.setK1("O" + qutils.addZeros(String.valueOf(ppl.getOrdernumber()), 6) + "001");
						
							// K2: Warehouse
							j0Parser.setK2(qutils.addZeros(deliveryLocation.getCode(), 3));
							
							// K3: Original shipment NBR
							j0Parser.setK3(datingnumber);
							
							// K4: Process immd needs
							j0Parser.setK4("Y");

							// K5: Po NBR
							j0Parser.setK5(qutils.addZeros(String.valueOf(ppl.getOrdernumber()), 6));

							// K6: Status Code
							j0Parser.setK6("00");

							// K7: Put away type
							j0Parser.setK7("");

							// K8: Vendor id
							j0Parser.setK8(ppl.getVendorcode());

							// K9: Company
							j0Parser.setK9("PAR");

							// K10: Division
							j0Parser.setK10("001");
							
							// K11: Items Block
							k11Parser = objFactory.createK11();
							
								// L0: Item
								l0List = k11Parser.getL0();
						}
															
								l0Parser = objFactory.createL0();
								
									// Contador de unidades
									units += ppl.getUnits();
								
									// M0: Case NBR
									l0Parser.setM0(ppl.getLpncode());

									// M1: Sku Sequence NBR
									l0Parser.setM1(++lpnItemSequence);

									// M2: Company
									l0Parser.setM2("PAR");

									// M3: Division
									l0Parser.setM3("001");

									// M4: Cn try of orgn1
									l0Parser.setM4(ppl.getLocationcode());

									// M5: Size desc
									l0Parser.setM5(ppl.getIteminternalcode());

									// M6: Batch Number
									l0Parser.setM6(ppl.getDistributionordernumber());

									// M7: Original Quantity
									l0Parser.setM7(ppl.getUnits());

									// M8: Shipped asn quantity
									l0Parser.setM8(ppl.getUnits());

									// M9: Standard pack qty
									l0Parser.setM9(ppl.getInnerpack());

									// M10: Vendor item number
									l0Parser.setM10(ppl.getVendoritemcode());

									// M11: Poline NBR
									l0Parser.setM11(ppl.getSequence());

								l0List.add(l0Parser);

				} // E13 for end
							
							// Insertar K11 con todos los skus del �ltimo LPN
							j0Parser.setK11(k11Parser);
						
							// Insertar J0 con el �ltimo LPN
							j0List.add(j0Parser);
					
					// E2: Cases shipped
					d0Parser.setE2(lpns);

					// E3: Units shipped
					d0Parser.setE3(units);
					
					// Insertar E13 con todos los LPNs
					d0Parser.setE13(e13Parser);

				d0List.add(d0Parser);

			a3parser.setC3(c3Parser);

		qasn.setA3(a3parser);
		
		// ***********************************FIN XML*********************************************//

		// Obtiene string XML para enviarlo a la cola
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(qasn, sw);
		result = sw.toString();

		// RESPALDA MENSAJE
		doBackUpMessage(result, datingnumber + "_PLPREDIMP", "ASN");

		// Planifica el envio
		schedulermanager.doAddMessageQueue("jboss/qcf_pariscl", "jboss/wsmq/q_asn", "asn", datingnumber + "_PLPREDIMP", result);
			
		return result;
	}

	public class F0_PK implements Comparable {
		private Long itemid;

		private Long orderid;

		public F0_PK() {
		}

		public F0_PK(long orderid, long itemid) {
			this.orderid = new Long(orderid);
			this.itemid = new Long(itemid);
		}

		public int compareTo(Object arg0) {
			long result = orderid.longValue() - ((F0_PK) arg0).getOrderid().longValue();
			if (result != 0) {
				return (result > 0) ? 1 : -1;
			}
			result = itemid.longValue() - ((F0_PK) arg0).getItemid().longValue();
			if (result != 0) {
				return (result > 0) ? 1 : -1;
			}
			return 0;
		}

		public Long getItemid() {
			return itemid;
		}

		public Long getOrderid() {
			return orderid;
		}

		public void setItemid(Long long1) {
			itemid = long1;
		}

		public void setOrderid(Long long1) {
			orderid = long1;
		}
	}

	private class Sequencenbr {
		int sequence;

		public int getSequence() {
			return sequence;
		}

		public void setSequence(int i) {
			sequence = i;
		}
	}

	private E12 listaAsnDetails(Long orderId, String G13, Sequencenbr sequencenbr) throws JAXBException, ServiceException {

		int n_lpns_embarcados;
		OrderDetailPK odKey = null;
		
		bbr.b2b.regional.logistic.xml.qasn.ObjectFactory qasnObjFactory = new bbr.b2b.regional.logistic.xml.qasn.ObjectFactory();

		// Lista general de asndetails
		E12 listasndetailsE12 = qasnObjFactory.createE12();
		List<F0> asndetaillistF0 = listasndetailsE12.getF0();

		// Lista para cada codloc
		G10 listalocG10 = null;
		H0 locH0 = null;
		List loclistH0 = null;

		List<AsnDetailW> asnArr = (ArrayList<AsnDetailW>) asnDetailMap.get(orderId);
		if (asnArr == null) {
			throw new ServiceException("No pude encontrar el detalles de despacho");
		}

		AsnDetailW[] asnDetails = (AsnDetailW[]) asnArr.toArray(new AsnDetailW[asnArr.size()]);

		// Ordena la lista de los detalle bulto
		Arrays.sort(asnDetails, new AsnPredeliveryDetailComparator());

		F0 message = null;
		long order_id = -1;
		long item_id = -1;
		long location_id = -1;
		String refdoc = "";

		String oc_it_ref = "";
		String oc_it_ref_tmp = "";
		String ocItRefLo = "";

		List<Long> locationids = new ArrayList<Long>();

		for (AsnDetailW asnDetail : asnDetails) {
			// No tiene packing
			if (asnDetail.getUnits() <= 0) {
				continue;
			}

			order_id = asnDetail.getOrderid().longValue();
			item_id = asnDetail.getItemid().longValue();
			refdoc = asnDetail.getRefdocument();

			oc_it_ref_tmp = order_id + "_" + item_id + "_" + refdoc;

			// si cambia la "PK (orden, item, ref)", se crea nueva lista
			if (!oc_it_ref.equals(oc_it_ref_tmp)) {
				if (!oc_it_ref.equals("")) {
					message.setG10(listalocG10);
					asndetaillistF0.add(message);
				}

				locationids = new ArrayList<Long>();

				message = qasnObjFactory.createF0();

				listalocG10 = qasnObjFactory.createG10();
				loclistH0 = listalocG10.getH0();

				// Shipment NBR
				message.setG0(asnDetail.getAsnnumber().toString());

				// Company
				message.setG1("PAR");

				// Division
				message.setG2("001");

				// Size Desc
				message.setG3(asnDetail.getIteminternalcode());

				// Batch Number
				message.setG4(asnDetail.getDistributionordernumber());

				// Standard pack qty
				message.setG5(asnDetail.getIteminnerpack());

				odKey = new OrderDetailPK(asnDetail.getOrderid(), asnDetail.getItemid());

				Integer lpnInteger = ((Integer) odBdMap.get(odKey));
				if (lpnInteger == null)
					throw new ServiceException("No pude encontrar el lpn");

				n_lpns_embarcados = lpnInteger.intValue();

				// Cases shipped
				message.setG6(n_lpns_embarcados);

				// Process immd needs
				message.setG7("Y");

				// Vendor item number
				message.setG8(asnDetail.getVendoritemcode());

				// Po NBR
				message.setG9(qutils.addZeros(asnDetail.getOrdernumber().toString(), 6));

				// Numero de factura
				message.setG91(asnDetail.getRefdocument());

				// Put away type
				message.setG11("");

				// Vendor id
				message.setG12(asnDetail.getVendorcode());

				// Action code
				message.setG13(G13);

				// Iguala las llaves
				oc_it_ref = oc_it_ref_tmp;

				locH0 = qasnObjFactory.createH0();

				// Cn try of orgn
				locH0.setI0(asnDetail.getDestinationlocationcode()); // "12 para stock???"

				// Poline NBR
				locH0.setI1(asnDetail.getPredeliverysequence());

				// Units shipped
				ocItRefLo = oc_it_ref_tmp + "_" + asnDetail.getDestinationlocationid();
				locH0.setI2(ocItRefLoUnitMap.get(ocItRefLo).intValue());

				// Sequence NBR
				locH0.setI3(sequencenbr.getSequence());
				sequencenbr.setSequence(sequencenbr.getSequence() + 1);

				loclistH0.add(locH0);

				locationids.add(asnDetail.getDestinationlocationid());
			}
			else {
				if (location_id != asnDetail.getDestinationlocationid().longValue() && !locationids.contains(asnDetail.getDestinationlocationid())) {
					locH0 = qasnObjFactory.createH0();

					// Cn try of orgn
					locH0.setI0(asnDetail.getDestinationlocationcode()); // "12 para stock???"

					// Poline NBR
					locH0.setI1(asnDetail.getPredeliverysequence());

					// Units shipped
					ocItRefLo = oc_it_ref_tmp + "_" + asnDetail.getDestinationlocationid();
					locH0.setI2(ocItRefLoUnitMap.get(ocItRefLo).intValue());

					// Sequence NBR
					locH0.setI3(sequencenbr.getSequence());
					sequencenbr.setSequence(sequencenbr.getSequence() + 1);

					loclistH0.add(locH0);
					locationids.add(asnDetail.getDestinationlocationid());
				}
			}
			location_id = asnDetail.getDestinationlocationid().longValue();
		}

		message.setG10(listalocG10);
		asndetaillistF0.add(message);

		return listasndetailsE12;
	}

	private String getDistro4(Long ordernumber, Integer orderdeliveryindex) {

		String distro4 = "O";
		distro4 += qutils.addZeros(String.valueOf(ordernumber), 6);
		distro4 += qutils.addZeros(String.valueOf(orderdeliveryindex), 3);
		return distro4;
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

}
