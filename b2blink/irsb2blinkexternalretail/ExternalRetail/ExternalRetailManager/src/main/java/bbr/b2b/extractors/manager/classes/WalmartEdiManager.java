package bbr.b2b.extractors.manager.classes;

import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.altova.io.StringInput;
import com.altova.io.StringOutput;

import bbr.b2b.extractors.manager.interfaces.WalmartEdiManagerLocal;
import bbr.b2b.extractors.mappings.MappingMapToOC_Customer;
import bbr.b2b.extractors.timers.manager.interfaces.SchedulerManagerLocal;

@Stateless(name = "managers/WalmartEdiManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WalmartEdiManager implements WalmartEdiManagerLocal {
	
	private static Logger logger = Logger.getLogger(WalmartEdiManager.class);
	private static Logger soaMessage = Logger.getLogger("SOAMessage");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
	private static final String SITENAME = "WALMART";
	
	
	@EJB
	SchedulerManagerLocal schmanager;
	
	public void EdiToOc(String message) {
		logger.info("Empresa: " + SITENAME + " PROCESAMIENTO ÓRDENES FORMATO EDI");
		try {
			String[] orders = message.split("UNZ")[0].split("UNH");
			for (int i = 1; i < orders.length; i++) {
				String newMessage = orders[0] + "UNH" + orders[i];
					try {
						MappingMapToOC_Customer mappingMapToOC_Customer = new MappingMapToOC_Customer();
						StringInput input = new StringInput(newMessage);
						StringOutput output = new StringOutput();
						mappingMapToOC_Customer.run(input, output);
						String msg = output.getString().toString();
						soaMessage.info("Empresa: " + SITENAME);
						soaMessage.info(msg);
						//schmanager.doSendMessageQueue(SITECODE, credential.getAccesscode(), file.getName(), msg, null);
						//logger.info("Empresa: " + SITENAME + " Proveedor: " + credential.getCompanyname() + " Ventas: " + file.getName() + " enviada a MQ");
					} catch (Exception e) {
						throw e;
					}
			}
			
			/*String service = "LOC";
			WsSoaService wsSoaService = new WsSoaService(); 
			List<InitParamCSDTO> credentials = wsSoaService.getCredentials(SITECODE, service);
			for (InitParamCSDTO credential : credentials) {
				try {
					JsonParser jsonParser = new JsonParser(); 
					HashMap<String, String> keys = jsonParser.JsonToHashMap(credential.getCredenciales(), "user", "password");
					
				} catch (Exception ex){
					logger.error("Empresa: " + SITENAME + " Proveedor: " + credential.getAccesscode() + " Error: " + ex.getMessage() );
				}
				
			} */
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
