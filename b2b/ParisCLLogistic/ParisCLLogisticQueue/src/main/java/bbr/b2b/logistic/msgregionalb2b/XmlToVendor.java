//package bbr.b2b.logistic.msgregionalb2b;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionAttributeType;
//
//import org.apache.log4j.Logger;
//
//import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
//import bbr.b2b.common.adtclasses.exceptions.LoadDataException;
//import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
//import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
//import bbr.b2b.regional.logistic.buyorders.classes.VendorServerLocal;
//import bbr.b2b.regional.logistic.buyorders.data.classes.VendorW;
//import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
//import bbr.b2b.regional.logistic.utils.CommonQueueUtils;
//import bbr.b2b.regional.logistic.xml.vendor.V;
//import bbr.b2b.regional.logistic.xml.vendor.VENDORDWH;
//
//@Stateless(name = "handlers/XmlToVendor")
//public class XmlToVendor implements XmlToVendorLocal {
//
//	private static Logger logger = Logger.getLogger(XmlToVendor.class);
//
//	CommonQueueUtils qutils = CommonQueueUtils.getInstance();
//
//	@EJB
//	VendorServerLocal vendorserver = null;
//	
//	@EJB
//	BuyOrderReportManagerLocal buyorderreportlocal;
//
//	public void error(String msge) throws LoadDataException {
//		qutils.error(msge);
//	}
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	private int doAddVendor(VENDORDWH vendor) throws LoadDataException, OperationFailedException, NotFoundException, AccessDeniedException, IndexOutOfBoundsException {
//		int state = 0;
//		try {
//			boolean exists = true;
//			boolean change = false;
//			VendorW vendorw = null;
//			V vendorData = vendor.getVS().getV().get(0);
//
//			String vendrut = vendorData.getField1().trim();
//
//			// Verifica si el proveedor existe o no
//			VendorW[] vendorArr = vendorserver.getVendorsByRut(vendrut);
//			if (vendorArr == null || vendorArr.length == 0) {
//				vendorw = new VendorW();
//				exists = false;
//			} else {
//				vendorw = vendorArr[0];
//			}
//
//			// Se valida si hay cambios o no 
//			if (exists) {
//				if ((!vendorw.getName().equals(vendorData.getField2().trim())) || (!vendorw.getTradename().equals(vendorData.getField2().trim())) || (!vendorw.getAddress().equals(vendorData.getField3().trim())) || (!vendorw.getCity().equals(vendorData.getField4().trim())) || (!vendorw.getCountry().equals(vendorData.getField5().trim())) || (!vendorw.getPhone().equals(vendorData.getField6().trim())) || (!vendorw.getFax().equals(vendorData.getField7().trim()))) {
//					change = true;
//				}
//				if (!change)
//					return 2;
//			}
//
//			vendorw.setRut(vendrut);
//			vendorw.setName(vendorData.getField2().trim());
//			vendorw.setTradename(vendorData.getField2().trim());
//			vendorw.setAddress(vendorData.getField3().trim()); // Dirección
//			vendorw.setCity(vendorData.getField4().trim()); // Ciudad
//			vendorw.setCountry(vendorData.getField5().trim()); // Pa�s
//			vendorw.setPhone(vendorData.getField6().trim()); // Fono
//			vendorw.setFax(vendorData.getField7().trim()); // Fax
//
//			if (exists) {
//				vendorw = vendorserver.updateVendor(vendorw);
//				state = 1;
//			} else {
//				vendorw = vendorserver.addVendor(vendorw);
//				state = 0;
//			}
//			return state;
//		} catch (Exception e) {
//			logger.error("Error en la carga de la linea: " + vendor);
//			return 2;
//		}
//	}
//
//	private boolean doValidateCSVLine(VENDORDWH vendor) throws LoadDataException, IndexOutOfBoundsException {
//
//		boolean isValid = true;
//
//		try {
//			qutils.datoObligatorio(vendor.getVS().getV().get(0).getField1().trim(), "No se especifica código del proveedor");
//			qutils.datoObligatorio(vendor.getVS().getV().get(0).getField2().trim(), "No se especifica nombre del proveedor");
//		} catch (LoadDataException e) {
//			isValid = false;
//		}
//		return isValid;
//	}
//
//	public int processMessageVendors(VENDORDWH vendor) throws LoadDataException, OperationFailedException, NotFoundException, AccessDeniedException, IndexOutOfBoundsException {
//
//		boolean isValid;
//		int state = 2;
//
//		// Se valida linea
//		isValid = doValidateCSVLine(vendor);
//
//		if (isValid) {
//			state = doAddVendor(vendor);
//		}
//		return state;
//	}
//
//}
