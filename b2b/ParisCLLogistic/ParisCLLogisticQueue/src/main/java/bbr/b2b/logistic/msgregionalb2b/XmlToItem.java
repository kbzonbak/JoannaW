package bbr.b2b.logistic.msgregionalb2b;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.regional.logistic.buyorders.classes.AtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.ItemAtcServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.AtcW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ItemAtcW;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtcId;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemClassServerLocal;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.data.wrappers.FlowTypeW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemClassW;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.data.wrappers.VendorItemW;
import bbr.b2b.regional.logistic.items.entities.VendorItemId;
import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.xml.qitem.Dimension;
import bbr.b2b.regional.logistic.xml.qitem.Dimensiones;
import bbr.b2b.regional.logistic.xml.qitem.QPRODUCTO;


@Stateless(name = "handlers/XmlToItem")
public class XmlToItem implements XmlToItemLocal {

	@EJB
	ItemServerLocal itemserver;

	@EJB
	VendorItemServerLocal vendoritemserver;

	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	ItemClassServerLocal itemclassserver;
	
	@EJB
	FlowTypeServerLocal flowtypeserver;
	
	@EJB
	AtcServerLocal atcServer;
	
	@EJB
	ItemAtcServerLocal itemAtcServer;
	
	CommonQueueUtils qutils = CommonQueueUtils.getInstance();	
	private static Logger logger = Logger.getLogger("CargaMsgesLog");

	private void doValidateItemMessage(QPRODUCTO qproducto) throws LoadDataException {
				
		// N??MERO DE SECUENCIA
		qutils.datoObligatorio(qproducto.getSecuencia(), "No se especifica N??mero de secuencia");
		qutils.validaLargo(qproducto.getSecuencia(), 12, "El N??mero de secuencia debe tener un largo de a los m??s 12 d??gitos");		
		
		// SKU PRODUCTO
		qutils.datoObligatorio(qproducto.getCodProducto(), "No se especifica sku producto");
		qutils.validaLargo(qproducto.getCodProducto(), 9, "El sku producto debe tener un largo de a los m??s 9 caracter(es)");
		
		// DESCRIPci??n PRODUCTO
		qutils.datoObligatorio(qproducto.getDescripcion(), "No se especifica descripci??n de producto");
		qutils.validaLargo(qproducto.getDescripcion(), 50, "La descripci??n de producto debe tener un largo de a los m??s 50 caracter(es)");
		
		// c??digo CLASE
		qutils.datoObligatorio(qproducto.getCodclase(), "No se especifica c??digo clase");		
		qutils.validaLargo(qproducto.getCodclase(), 9, "El c??digo clase debe tener un largo de a los m??s 9 caracter(es)");
		
		// DESCRIPci??n CLASE		
		qutils.datoObligatorio(qproducto.getDescclase(), "No se especifica descripci??n clase");
		qutils.validaLargo(qproducto.getDescclase(), 30, "La descripci??n clase debe tener un largo de a los m??s 30 caracter(es)");
		
		// c??digo BARRA PARIS			
		qutils.datoObligatorio(qproducto.getCodigobarraparis(), "No se especifica c??digo de barras Paris");
		qutils.validaLargo(qproducto.getCodigobarraparis(), 13, "El c??digo de barras Paris debe tener un largo de a los m??s 13 caracter(es)");
	
		// c??digo PROVEEDOR		
		qutils.datoObligatorio(qproducto.getCodProv(), "No se especifica c??digo proveedor");
		qutils.validaLargo(qproducto.getCodProv(), 10, "El c??digo proveedor debe tener un largo de a los m??s 10 caracter(es)");
	
		// c??digo PRODUCTO PROVEEDOR		
		if (qproducto.getCodProdProv() != null)
			qutils.validaLargo(qproducto.getCodProdProv(), 15, "El c??digo producto proveedor debe tener un largo de a los m??s 15 caracter(es)");
		
		// CAPACIDAD
		if (qproducto.getCapacidad() != null)
			qutils.validaLargo(qproducto.getCapacidad(), 9, "La capacidad debe tener un largo de a los m??s 9 digito(s)");
		
		// LEADTIME
		if (qproducto.getLeadTime() != null)
			qutils.validaLargo(qproducto.getLeadTime(), 3, "El leadtime debe tener un largo de a los m??s 3 digito(s)");
		
		// DIMENSIONES
		if (qproducto.getDimensiones() != null){
			
			Dimensiones dimparser = qproducto.getDimensiones();
			
			List<Dimension> dimensiones = dimparser.getDimension();
			
			for (int j = 0 ; j < dimensiones.size(); j++){
				
				// TIPO DIMENSi??n
				qutils.datoObligatorio(dimensiones.get(j).getTipodim(), "No se especifica tipo de dimensi??n");
				qutils.validaLargo(dimensiones.get(j).getTipodim(), 3, "El tipo de dimensi??n debe tener un largo de a los m??s 3 caracter(es)");
								
				// c??digo DIMENSi??n
				//qutils.datoObligatorio(dimensiones.get(j).getCoddim(), "No se especifica c??digo de dimensi??n");
				qutils.validaLargo(dimensiones.get(j).getCoddim(), 4, "El c??digo de dimensi??n debe tener un largo de a los m??s 4 caracter(es)");
			
				// VALOR DIMENSi??n
				qutils.datoObligatorio(dimensiones.get(j).getValor(), "No se especifica valor de dimensi??n");
				qutils.validaLargo(dimensiones.get(j).getValor(), 20, "El valor de dimensi??n debe tener un largo de a los m??s 20 caracter(es)");				
			}			
		}
		
		// TIPO DE FLUJO
		//qutils.datoObligatorio(qproducto.getTipodeflujo(), "No se especifica tipo de flujo");
		qutils.validaLargo(qproducto.getTipodeflujo(), 3, "El tipo de flujo debe tener un largo de a los m??s 3 caracter(es)");
	
		// INNERPACK
		qutils.datoObligatorio(qproducto.getInnerpack(), "No se especifica innerpack");
		qutils.validaLargo(qproducto.getInnerpack(), 7, "El innerpack debe tener un largo de a los m??s 7 d??gitos");
	
		// CASEPACK
		qutils.datoObligatorio(qproducto.getCasepack(), "No se especifica casepack");
		qutils.validaLargo(qproducto.getCasepack(), 7, "El casepack debe tener un largo de a los m??s 7 d??gitos");
		
		//ATC
		if(qproducto.getAtc() != null && 
				(qproducto.getAtc().getCodAtc() == null || qproducto.getAtc().getCodAtc().isEmpty()) &&
				qproducto.getAtc().getCurva() != null)
			qutils.datoObligatorio(qproducto.getAtc().getCodAtc(), "No se indica c??digo de ATC para producto con SKU "+qproducto.getCodProducto());
		
		else if(qproducto.getAtc() != null && 
				(qproducto.getAtc().getCurva() == null) &&
				qproducto.getAtc().getCodAtc() != null && qproducto.getAtc().getCodAtc().isEmpty()){
			
			qutils.datoObligatorio(qproducto.getAtc().getCurva(), "No se indica valor de curva para producto con SKU "+qproducto.getCodProducto());		
		}
		
		if(qproducto.getAtc() != null && qproducto.getAtc().getCurva() != null)
			qutils.validaLargo(qproducto.getAtc().getCurva(), 4, "El valor de curva no debe ser mayor a 4 d??gitos para el SKU "+qproducto.getCodProducto());
		
		if(qproducto.getAtc() != null && qproducto.getAtc().getCodAtc() != null)
			qutils.validaLargo(qproducto.getAtc().getCodAtc(), 22, "El c??digo de ATC debe tener un m???ximo de 22 caracteres, producto con SKU "+qproducto.getCodProducto());
	}

	public void error(String msge) throws LoadDataException {
		qutils.error(msge);
	}

	public String processMessage(QPRODUCTO itemParser) throws LoadDataException, ServiceException {
		
		// Validaci??n del Mensaje
		doValidateItemMessage(itemParser);
		
		/** *************************************************************************************** */
		/** ***************************** CREACION DE NUEVO PRODUCTO ****************************** */
		/** *************************************************************************************** */		
		ItemW itemw = null;
		ItemClassW itemclass = null;
		FlowTypeW flowtype = null;
		VendorW vendor = null;
		VendorItemW vendoritem = null;
		
		String sku;
		String description;
		String classcode;
		String classdescription;
		String barcode;
		String vendorcode;
		String vendoritemcode;	
		String flowtypecode;
		Integer innerpack;
		Integer casepack;
		Boolean vev;
			
		
		// MAESTRO ITEM: c??digo PROVEEDOR
		try{
			vendorcode = itemParser.getCodProv().trim();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el c??digo de proveedor (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: SKU		
		try{
			sku = itemParser.getCodProducto().trim();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el sku (Maestro de Productos)");
		}
		
		VendorW[] vendorArr = vendorserver.getByPropertyAsArray("code", vendorcode);
		if (vendorArr == null || vendorArr.length <= 0)
			throw new LoadDataException("No se encuentra el proveedor rut " + vendorcode);
		
		vendor = vendorArr[0];
		
		if (!vendor.getDomestic()){
			vendor = vendorserver.getByPropertyAsSingleResult("code", "IMP");
		}
		
		ItemW[] itemArr = itemserver.getByPropertyAsArray("internalcode", sku);
		
		
		// MAESTRO ITEM: DESCRIPci??n	
		try{
			description = itemParser.getDescripcion().trim();				
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener la descripci??n de producto (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: c??digo CLASE	
		try{
			classcode = itemParser.getCodclase().trim();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el c??digo de clase de producto (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: DESCRIPci??n CLASE	
		try{
			classdescription = itemParser.getDescclase().trim();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener la descripci??n de clase de producto (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: c??digo BARRA	
		try{
			barcode = itemParser.getCodigobarraparis().trim();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el c??digo de barras (Maestro de Productos)");
		}
		


		
		// MAESTRO ITEM: c??digo PRODUCTO PROVEEDOR			
		try{
			vendoritemcode = itemParser.getCodProdProv().trim();
		}catch (Exception e) {
			vendoritemcode = "";
		}
		
		// MAESTRO ITEM: CAPACIDAD		
//		try{
//			itemParser.getCapacidad().intValue();
//		}catch (Exception e) {
//			;
//		}
		
		// MAESTRO ITEM: LEADTIME			
//		try{
//			itemParser.getLeadTime();
//		}catch (Exception e) {
//			;
//		}
		
		// MAESTRO ITEM: TIPO DE FLUJO 
		try{
			flowtypecode = itemParser.getTipodeflujo().trim();
		}catch (Exception e) {
			flowtypecode = "";
			//throw new LoadDataException("No es posible obtener el tipo de flujo (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: INNERPACK
		try{
			innerpack = itemParser.getInnerpack();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el innerpack (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: CASEPACK
		try{
			casepack = itemParser.getCasepack();
		}catch (Exception e) {
			throw new LoadDataException("No es posible obtener el casepack (Maestro de Productos)");
		}
		
		// MAESTRO ITEM: VEV
		try{
			vev = (itemParser.getVev().longValue() == 0) ? false : true;
		}catch (Exception e) {
			vev = false;
		}
		
		boolean hasAtc = false;
		//ATC
		String atcCode = "";
		Integer curve = 0;
		if(itemParser.getAtc() != null && itemParser.getAtc().getCodAtc() != null && !itemParser.getAtc().getCodAtc().isEmpty()
				&& itemParser.getAtc().getCurva() != null){
			hasAtc = true;
			atcCode = itemParser.getAtc().getCodAtc();
			curve = itemParser.getAtc().getCurva();
		}
		
		// AGREGA CLASE
		ItemClassW[] itemclassArr = itemclassserver.getByPropertyAsArray("code", classcode);
		if (itemclassArr == null || itemclassArr.length <= 0){
			itemclass = new ItemClassW();
			
			itemclass.setCode(classcode);
			itemclass.setName(classdescription);
			
			itemclass = itemclassserver.addItemClass(itemclass);				
		}
		else{
			itemclass = itemclassArr[0];			
			itemclass.setName(classdescription.equals("") ? itemclass.getName() : classdescription);
			
			itemclass = itemclassserver.updateItemClass(itemclass);					
		}
		
		// VERIFICA TIPO DE FLUJO
		FlowTypeW[] flowtypeArr = flowtypeserver.getByPropertyAsArray("code", flowtypecode);
		if (flowtypeArr == null || flowtypeArr.length <= 0){
			//throw new LoadDataException("No existe el tipo de flujo " + flowtypecode);
			flowtype = null;
		}
		else{
			flowtype = flowtypeArr[0];	
		}				
		
		
		if (itemArr == null || itemArr.length <= 0){
			itemw = new ItemW();
			
			itemw.setInternalcode(sku);				
			itemw.setName(description);
			itemw.setBarcode(barcode);
			itemw.setItemclassid(itemclass.getId());
			itemw.setFlowtypeid(flowtype == null ? null : flowtype.getId());
			itemw.setUnitid(-1L);
			itemw.setCasepack(casepack);
			itemw.setInnerpack(innerpack);
			itemw.setVev(vev);
			
			if ((casepack * innerpack) == 0){
				// ENVIAR CORREO
				sendCasePackInnerPackMail(sku, casepack.intValue(), innerpack.intValue());					
			}
		
			// MAESTRO ITEM : DIMENSIONES
			List<Dimension> disList = null;
			try{
				disList = itemParser.getDimensiones().getDimension();
			}catch (Exception e) {
				disList = null;
			}
			
			if (disList != null){
				if (disList.size() != 3){
					throw new LoadDataException("Deben venir solo 3 dimensiones, Producto " + sku);
				}
				
				String dimtype;
				String dimvalue;
				
				for (int j = 0; j < disList.size(); j++){
					// MAESTRO ITEM : TIPO DIMENSION
					try{
						dimtype = disList.get(j).getTipodim().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica tipo de dimensi??n");
					}
					
					// MAESTRO ITEM : c??digo DIMENSi??n
					try{
						disList.get(j).getCoddim().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica c??digo de dimensi??n");
					}
					
					// MAESTRO ITEM : VALOR DIMENSi??n
					try{
						dimvalue = disList.get(j).getValor().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica valor de dimensi??n");
					}
					
					if (dimtype.equalsIgnoreCase("TAL")){
						itemw.setSize(dimvalue);
					}
					else if (dimtype.equalsIgnoreCase("COL")){
						itemw.setColor(dimvalue);
					}
					else if (dimtype.equalsIgnoreCase("DIM")){
						itemw.setDimension(dimvalue);
					}						
				}					
			}
			
			// MAESTRO ITEM: EAN HOMOLOGADO
			List<String> eanList = null;
			try{
				eanList = itemParser.getListaEan().getEan();
			}catch (Exception e) {
				eanList = null;
			}
			
			if (eanList != null){
				
				String eancode;
				
				for (int j = 0; j < eanList.size(); j++){
					// MAESTRO ITEM : EAN
					try{
						eancode = eanList.get(j).trim();
					}catch (Exception e) {
						eancode = "";
					}
				
					if (!eancode.equals("")){
						itemw.setEan3(itemw.getEan2());
						itemw.setEan2(itemw.getEan1());
						itemw.setEan1(eancode);
					}						
				}
			}
								
			itemw = itemserver.addItem(itemw);				
		}
		else{
			itemw = itemArr[0];			
			
			itemw.setName(description.equals(itemw.getName()) ? itemw.getName() : description);
			itemw.setBarcode(barcode.equals(itemw.getBarcode()) ? itemw.getBarcode() : barcode);
			itemw.setItemclassid(itemclass.getId().equals(itemw.getItemclassid()) ? itemw.getItemclassid() : itemclass.getId());
			//itemw.setFlowtypeid(flowtype.getId().equals(itemw.getFlowtypeid()) ? itemw.getFlowtypeid() : flowtype.getId());
			if (flowtype == null)
				itemw.setFlowtypeid(itemw.getFlowtypeid());
			else
				itemw.setFlowtypeid(flowtype.getId().equals(itemw.getFlowtypeid()) ? itemw.getFlowtypeid() : flowtype.getId());
			itemw.setCasepack(casepack.intValue() == itemw.getCasepack() ? itemw.getCasepack() : casepack.intValue());
			itemw.setInnerpack(innerpack.intValue() == itemw.getInnerpack() ? itemw.getInnerpack() : innerpack.intValue());
			itemw.setVev(vev);
			
			if ((casepack * innerpack) == 0){
				// ENVIAR CORREO
				sendCasePackInnerPackMail(sku, casepack.intValue(), innerpack.intValue());
			}
		
			// MAESTRO ITEM : DIMENSIONES
			List<Dimension> disList = null;
			try{
				disList = itemParser.getDimensiones().getDimension();
			}catch (Exception e) {
				disList = null;
			}
			
			if (disList != null){
				if (disList.size() != 3){
					throw new LoadDataException("Deben venir solo 3 dimensiones, Producto " + sku);
				}
				
				String dimtype;
				String dimvalue;
				
				for (int j = 0; j < disList.size(); j++){
					// MAESTRO ITEM : TIPO DIMENSION
					try{
						dimtype = disList.get(j).getTipodim().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica tipo de dimensi??n");
					}
					
					// MAESTRO ITEM : c??digo DIMENSi??n
					try{
						disList.get(j).getCoddim().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica c??digo de dimensi??n");
					}
					
					// MAESTRO ITEM : VALOR DIMENSi??n
					try{
						dimvalue = disList.get(j).getValor().trim();
					}catch (Exception e) {
						throw new LoadDataException("No se especifica valor de dimensi??n");
					}
					
					if (dimtype.equalsIgnoreCase("TAL")){
						itemw.setSize(dimvalue.equals(itemw.getSize()) ? itemw.getSize() : dimvalue);
					}
					else if (dimtype.equalsIgnoreCase("COL")){
						itemw.setColor(dimvalue.equals(itemw.getColor()) ? itemw.getColor() : dimvalue);
					}
					else if (dimtype.equalsIgnoreCase("DIM")){
						itemw.setDimension(dimvalue.equals(itemw.getDimension()) ? itemw.getDimension() : dimvalue);
					}						
				}					
			}
			
			// MAESTRO ITEM: EAN HOMOLOGADO
			List<String> eanList = null;
			try{
				eanList = itemParser.getListaEan().getEan();
			}catch (Exception e) {
				eanList = null;
			}
			
			if (eanList != null){
				
				String eancode;
				
				for (int j = 0; j < eanList.size(); j++){
					// MAESTRO ITEM : EAN
					try{
						eancode = eanList.get(j).trim();
					}catch (Exception e) {
						eancode = "";
					}
				
					if (!eancode.equals("") && 
						!eancode.equals(itemw.getEan1()) && 
						!eancode.equals(itemw.getEan2()) &&
						!eancode.equals(itemw.getEan3())){
							
						itemw.setEan3(itemw.getEan2());
						itemw.setEan2(itemw.getEan1());
						itemw.setEan1(eancode);
					}						
				}
			}				
				
			itemw = itemserver.updateItem(itemw);					
		}		
		
		//Agregar ATC
		if(hasAtc){
			AtcW atc = null;
			try {
				atc = atcServer.getByPropertyAsSingleResult("code", atcCode);
			} catch (Exception e) {
				logger.debug("No se encontr??? el atc "+atcCode+". Ser??? creado.");
			}
			
			if(atc == null){
				atc = new AtcW();
				atc.setCode(atcCode);
				atc = atcServer.addElement(atc);
			}
			
			ItemAtcId id = new ItemAtcId(atc.getId(), itemw.getId());
			ItemAtcW itemAtc = null;
			try {
				itemAtc = itemAtcServer.getById(id);
			} catch (Exception e) {
				logger.debug("No se encontr??? la relaci??n del atc "+atcCode
						+" con el sku "+itemw.getInternalcode()+". Ser??? creada");
			}
			
			if(itemAtc == null){
				itemAtc = new ItemAtcW();
				itemAtc.setAtcid(atc.getId());
				itemAtc.setItemid(itemw.getId());
				itemAtc.setCurve(Long.valueOf(curve));
				itemAtcServer.addIdentifiable(itemAtc);
			}else if(!itemAtc.getCurve().equals(Long.valueOf(curve))){
				itemAtc.setCurve(Long.valueOf(curve));
				itemAtcServer.updateIdentifiable(itemAtc);
			}
		}
		
		// AGREGA VENDOR ITEM
		VendorItemId viKey = new VendorItemId(vendor.getId(), itemw.getId());
		VendorItemW viArr = null;
		try{
			viArr = vendoritemserver.getById(viKey);
			
			vendoritem = viArr;				
			vendoritem.setVendoritemcode(vendoritemcode.equals(vendoritem.getVendoritemcode()) ? vendoritem.getVendoritemcode() : vendoritemcode);			
		
			vendoritem = vendoritemserver.updateVendorItem(vendoritem);
		}catch (NotFoundException e) {
			
			vendoritem = new VendorItemW();
			vendoritem.setItemid(itemw.getId());
			vendoritem.setVendorid(vendor.getId());
			vendoritem.setDirectdelivery(false);
			vendoritem.setVendoritemcode(vendoritemcode);

			vendoritem = vendoritemserver.addVendorItem(vendoritem);
		}				
		vendoritemserver.flush();		
					
		
		logger.info("Producto sku " + sku + " cargado");			
		
		return sku;
	}
	
	private void sendCasePackInnerPackMail(String sku, Integer casepack, Integer innerpack){
//		try{
//			String[] mailreciever = new String[2];
//			String subject = "B2B Paris Chile: Productos con innerpack o casepack en cero";
//			Mailer mailer = Mailer.getInstance();
//			mailreciever[0] = "eborne@bbr.cl";
//			mailreciever[1] = "jmansilla@bbr.cl";			
//			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
//			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
//
//			String msgtext = "Estimado usuario:\n"
//					+ "El B2B recibio el producto "
//					+ sku
//					+ " que contiene los siguientes valores:\n"
//					+ "innerpack :"
//					+ innerpack
//					+ "\n"
//					+ "casepack :"
//					+ casepack
//					+ "\n"
//					+ "Atte.\n"
//					+ "Paris.";
//			mailer.sendMailBySession(mailreciever, mailreciever, null, mailsender, subject, msgtext, false, null, mailSession);	
//		}catch (Exception e) {
//			e.printStackTrace();
//		}		
	}	
}
