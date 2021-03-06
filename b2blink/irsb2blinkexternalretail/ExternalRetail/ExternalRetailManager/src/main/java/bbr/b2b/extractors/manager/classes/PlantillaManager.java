package bbr.b2b.extractors.manager.classes;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;

import org.apache.log4j.Logger;

import bbr.b2b.b2blink.logistic.xml.OC_customer.DiscountCharge;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Local;
import bbr.b2b.b2blink.logistic.xml.OC_customer.ObjectFactory;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Client;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Charges;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Details.Detail.Discounts;
import bbr.b2b.b2blink.logistic.xml.OC_customer.Order.Section;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.extractors.api.manager.classes.GenericResponseResultDTO;
import bbr.b2b.extractors.api.manager.classes.JsonUtils;
import bbr.b2b.extractors.api.manager.classes.RESTConnection;
import bbr.b2b.extractors.api.manager.classes.RESTContentType;
import bbr.b2b.extractors.manager.interfaces.PlantillaManagerLocal;
import bbr.b2b.extractors.manager.interfaces.WalmartWebSalesManagerLocal;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;
import bbr.b2b.extractors.utils.WsSoaService;

import bbr.b2b.logistic.utils.JsonParser;
import bbr.esb.services.webservices.facade.InitParamCSDTO;


@Stateless(name = "managers/PlantillaManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PlantillaManager  implements PlantillaManagerLocal {
	
	private static Logger logger = Logger.getLogger(PlantillaManager.class);
	private static JAXBContext jc = null;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
	private static final String SITECODE= "CL1301";
	private static final String SITENAME = "WALMART";
	
	
	@EJB
	SchedulerManagerLocal schmanager;
	
	@Resource
	private SessionContext ctx;
	
	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}
	
	public void doProcess() {
		try {
			String service = "LOC";
			WsSoaService wsSoaService = new WsSoaService(); 
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);
			for (InitParamCSDTO credential : credentials) {
				try {
					JsonParser jsonParser = new JsonParser(); 
					HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "user", "password");
					
				} catch (Exception ex){
					logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getAccesscode() + " Error: " + ex.getMessage() );
				}
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
