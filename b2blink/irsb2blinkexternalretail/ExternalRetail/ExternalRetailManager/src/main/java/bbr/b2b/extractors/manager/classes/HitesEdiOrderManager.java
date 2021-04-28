package bbr.b2b.extractors.manager.classes;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.log4j.Logger;

import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Local;
import bbr.b2b.b2blink.logistic.xml.OC_customer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Predistributions.Predistribution;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.extractors.manager.interfaces.HitesEdiOrderManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.WsSoaService;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Section;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.JsonParser;
import bbr.b2b.logistic.utils.SFTPClient;
import bbr.esb.services.webservices.facade.InitParamCSDTO;

@Stateless(name = "managers/HitesEdiOrderManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class HitesEdiOrderManager  implements HitesEdiOrderManagerLocal {
	
	private static Logger logger = Logger.getLogger(HitesEdiOrderManager.class);
	private static Logger soaMessage = Logger.getLogger("SOAMessage");
	private static JAXBContext jc = null;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final String SITECODE= "CL0501";
	private static final String SITENAME = "HITES";
	
	@EJB
	SchedulerManagerLocal schmanager;
	
	@Resource
	private SessionContext ctx;
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	
	private static JAXBContext getJC() throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.OC_customer");
		return jc;
	}
	
	public void doProcess() throws Exception {
		try {
			logger.info("Empresa: " + SITENAME + " DESCARGANDO ÓRDENES DE SFTP");
			String service = "LOC";
			WsSoaService wsSoaService = new WsSoaService(); 
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);
			for (InitParamCSDTO credential : credentials) {
				logger.info("Empresa: " + SITENAME + " DESCARGANDO ÓRDENES DE " + credential.getCompanyname());
				String finalPath = B2BSystemPropertiesUtil.getProperty("FINALPATH") + "oc"+ File.separator + SITENAME + File.separator + credential.getAccesscode();
				Files.createDirectories(Paths.get(finalPath));
				finalPath += File.separator;
				JsonParser jsonParser = new JsonParser(); 
				HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "userSftp", "passwordSftp", "serverSftp", "port", "originPathSftp");
				SFTPClient sftpClient = new SFTPClient();
				logger.info("Empresa: " + SITENAME + " DESCARGANDO ÓRDENES DE " + credential.getCompanyname() + " CONECTANDOSE A: " + keys.get("serverSftp"));
				boolean isConnected = sftpClient.connect(SITENAME, credential.getCompanyname(), keys.get("userSftp"), keys.get("passwordSftp"), keys.get("serverSftp"),
						keys.get("port"), keys.get("originPathSftp"), finalPath);
				if(isConnected){
					doTransform(credential, finalPath);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void doTransform(InitParamCSDTO credential, String folder) throws DatatypeConfigurationException, JAXBException, IOException {
		try (Stream<Path> filePathStream=Files.walk(Paths.get(folder))) {
		    filePathStream.forEach(filePath -> {
		        if (Files.isRegularFile(filePath)) {
		            System.out.println(filePath);
		            try {
		            	List<String> allLines = Files.readAllLines(filePath, StandardCharsets.ISO_8859_1);
		            	convertToOC(credential, filePath.getFileName().toString(), allLines);
		            	backUpProcessedFile(filePath);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        } //else {
		        	//System.out.println(filePath + " no es una archivo regular");
		        //}
		    });
		}
	}
	
	  public void backUpProcessedFile(Path filePath) throws IOException {
		    LocalDateTime now = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		    String backUpFolder = B2BSystemPropertiesUtil.getProperty("BACKUPFOLDER");
		    String targetPath = backUpFolder + "HITES" + File.separator + "data" + File.separator + "in" + File.separator + now.format(formatter) + File.separator;
		    Files.createDirectories(Paths.get(targetPath));
		    Path target = Paths.get(targetPath + filePath.getFileName());
		    Files.move(filePath, target);
		  }
	
	public void convertToOC(InitParamCSDTO credential, String fileName, List<String> allLines) throws IOException, DatatypeConfigurationException, ParseException, JAXBException, OperationFailedException{
		ObjectFactory objFactory = new ObjectFactory();
		Order orderXML = objFactory.createOrder();
		if( allLines.size()> 0){
			String firstLine = allLines.get(0);
			
			orderXML.setBuyer(SITECODE);
			orderXML.setVendor(credential.getAccesscode());
			String[] cols = firstLine.split("\\|");
			orderXML.setNumber(cols[0]);
			
			String orderType = fileName.toString().contains("DDC") ? "DDC" : "DVH";
			
			orderXML.setEffectivdate(ClassUtilities.StringToXMLGregorianCalendar(cols[1], formatter));
			orderXML.setCommitmentdate(ClassUtilities.StringToXMLGregorianCalendar((orderType.equals("DVH") ? cols[10] : cols[2]), formatter));
			orderXML.setIssuedate(ClassUtilities.StringToXMLGregorianCalendar(cols[11], formatter));
			orderXML.setExpirationdate(ClassUtilities.StringToXMLGregorianCalendar((orderType.equals("DVH") ? cols[10] : cols[2]), formatter));
			orderXML.setOrdertype(orderType);
			orderXML.setOrdertypename(orderType);
			orderXML.setPreviousordertype("");
			orderXML.setComplete(true);
			orderXML.setReceiptnumber(cols[14].trim());
			
			
			//orderXML.setRequest(purchaseOrderDTO.getClient_purchase_order().getOrder_delivery_id());
			//orderXML.setExpirationdate(StringToGregorianCalendar(purchaseOrderDTO.getPurchase_order().getPo_expiration_date()));
			//orderXML.setPaymentcondition(purchaseOrderDTO.getPurchase_order().getPo_payment_method());
			//orderXML.setObservation(purchaseOrderDTO.getPurchase_order().getPo_obs());
			//orderXML.setResponsible("");
			//orderXML.setResponsibleemail("");
			//orderXML.setNumref1(cols[18]);
			
			String deliveryplace[] = cols[4].split(" ");
			orderXML.setDeliveryplace(setLocal(objFactory, deliveryplace[0], cols[4].replace(deliveryplace[0] + " ", "").trim()));
			orderXML.setSaleplace(setLocal(objFactory, cols[12].trim() , cols[13].trim()));
			orderXML.setClient(setCliente(objFactory, cols));
			
			Section section = objFactory.createOrderSection();
			section.setCode(cols[16].trim());
			section.setName(cols[17].trim());
			orderXML.setSection(section);
		}
		
		
		orderXML.setDetails(setDetails(objFactory, allLines));

		
		JAXBContext jc = getJC();
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		m.marshal(orderXML, sw);
		String msg = sw.toString();
		soaMessage.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber());
		soaMessage.info(msg);
		schmanager.doSendMessageQueue(SITENAME, credential.getCompanyname(), orderXML.getNumber(), msg, null);
		logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Order: " + orderXML.getNumber() + " enviada a MQ");
	}
	
	private Details setDetails(ObjectFactory objFactory, List<String> details) {
		Details orderDetails = objFactory.createOrderDetails();
		List<Detail> listDetail = orderDetails.getDetail();
		int position = 1;
		for (String line: details){
			String cols[] = line.split("\\|");
			Detail detail = objFactory.createOrderDetailsDetail();
			detail.setPosition(position);
			//detail.setSkuvendor(cols[6]);
			detail.setSkubuyer(cols[6]);
			detail.setEan13(cols[5]);
			detail.setProductdescription(cols[7].trim());
			detail.setListcost(Double.valueOf(cols[9].replace(",", ".")));
			//detail.setListprice(Double.valueOf(item.getItem_net_cost()));
			detail.setFinalcost(Double.valueOf(cols[28]));
			detail.setDescriptionumc("UN");
			detail.setDescriptionumb("unidad");
			detail.setQuantityumc(Integer.valueOf(cols[8].trim()));
			detail.setUmbXUmc(1);
			detail.setNumref1(cols[18].trim());
			//detail.setInnerpack(Integer.valueOf(cols[8].trim()));
			/*
			detail.setEan1(item.getVendor_ean13());
			detail.setItem("");
			
			detail.setEan2("");
			detail.setEan3("");
			detail.setStylecode("");
			detail.setStyledescription("");
			detail.setStylebrand("");
			detail.setTolerance("");
			detail.setProductdeliverydate("");
			detail.setFreightcost("");
			detail.setFreightweight("");
			detail.setObservation(item.getItem_obs());
		 	*/
			Predistributions predistributions = objFactory.createOrderDetailsDetailPredistributions();
			List<Predistribution> listPredistributions = predistributions.getPredistribution();
			Predistribution predistribution = new Predistribution();
			predistribution.setQuantity(Integer.valueOf(cols[8].trim()));
			predistribution.setDeliveryplace(setLocal(objFactory, cols[29].trim(), cols[4].replace(cols[29]+ " ", "").trim()));
			listPredistributions.add(predistribution);
			detail.setPredistributions(predistributions);
			listDetail.add(detail);
			position++;
		}
		return orderDetails;
	}

	public Local setLocal(ObjectFactory objFactory, String code, String name){
		Local local = objFactory.createLocal();
		local.setCode(code);
		local.setName(name);
		return local;
	}
	
	private Client setCliente(ObjectFactory objFactory, String[] cols){
		Client client = objFactory.createOrderClient();
		client.setName(cols[22].trim());
		if(!cols[22].isEmpty()){
			String[] rut = cols[21].split(" ");
			client.setIdentity(rut[0]);
			client.setCheckdigit(rut[1]);
		}
		
		client.setPhone(cols[25].trim());
		client.setPhone2(cols[26].trim());
		//TODO: email
		//client.setEmail(clientDTO.getClient_email());
		client.setAddress(cols[23].trim());
		//client.setStreetnumber("");
		//client.setDepartmetnumber("");
		//client.setHousenumber("");
		//client.setRegion(clientDTO.getClient_region());
		client.setCommune(cols[3].trim());
		client.setCity(cols[24].trim());
		//client.setLocation("");
		client.setObservation(cols[27].trim());
		
		return client;
	}
}
