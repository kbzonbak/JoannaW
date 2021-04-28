package bbr.b2b.logistic.managers.classes;

import java.io.StringWriter;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.managers.interfaces.SOAManagerLocal;
import bbr.b2b.logistic.managers.interfaces.SchedulerManagerLocal;
import bbr.b2b.b2blink.commercial.xml.NotificacionInventario.NotificacionInventario;
import bbr.b2b.b2blink.commercial.xml.NotificacionVentas.NotificacionVentas;
import bbr.b2b.b2blink.commercial.xml.NotificacionVentas.ObjectFactory;

@Stateless(name = "managers/SOAManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SOAManager implements SOAManagerLocal {

	private static Logger logger_soa = Logger.getLogger("SOALog");
	private @Resource SessionContext ctx;
	
	@EJB
	SchedulerManagerLocal schedulermanager;
	
	
	public void doReportingSalesMsge() throws OperationFailedException {
		
		try {
//			
			ObjectFactory objFactory = new ObjectFactory();
			NotificacionVentas notification = objFactory.createNotificacionVentas();
			notification.setNombreportal("RIPLEY_COMERCIAL");
			
			JAXBContext jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.NotificacionVentas");
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(notification, sw);
			String result = sw.toString();

			
			// ENVIA EL MENSAJE A LA COLA
			schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl" , "NotificadoSales", "", result);

		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger_soa.error("Generaci�n de XML de rechazo via JAXB fallo. " + e2.getMessage());
			throw new OperationFailedException(e2.getMessage());
		} catch (Exception e) {
			ctx.setRollbackOnly();
			throw new OperationFailedException(e.getMessage());
		}
	}
	
	public void doReportingInventaryMsge() throws OperationFailedException {
		try {
			logger_soa.info("Reportando inventario");
			bbr.b2b.b2blink.commercial.xml.NotificacionInventario.ObjectFactory objFactory = new bbr.b2b.b2blink.commercial.xml.NotificacionInventario.ObjectFactory();
			NotificacionInventario notification = objFactory.createNotificacionInventario();
			notification.setNombreportal("RIPLEY_COMERCIAL");

			JAXBContext jc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.NotificacionInventario");
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(notification, sw);
			String result = sw.toString();

			
			// ENVIA EL MENSAJE A LA COLA
			schedulermanager.doAddMessageQueue("jboss/qcf_soa", "jboss/activemq/queue/q_esbgrl" , "NotificadoInv", "", result);
		} catch (JAXBException e2) {
			e2.printStackTrace();
			logger_soa.error("Generaci�n de XML de rechazo via JAXB fallo. " + e2.getMessage());
			throw new OperationFailedException(e2.getMessage());
		} catch (Exception e) {
			ctx.setRollbackOnly();
			throw new OperationFailedException(e.getMessage());
		}
	}
	
}
