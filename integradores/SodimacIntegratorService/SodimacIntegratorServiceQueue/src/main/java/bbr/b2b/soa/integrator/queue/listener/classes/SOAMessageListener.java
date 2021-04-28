package bbr.b2b.soa.integrator.queue.listener.classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.jms.Message;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.tools.ant.util.XmlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bbr.b2b.b2blink.commercial.xml.SolicitudInventario.SolicitudInventario;
import bbr.b2b.b2blink.commercial.xml.SolicitudVentas.SolicitudVentas;
import bbr.b2b.b2blink.logistic.xml.InventarioProveedor.InventarioProveedor;
import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.soa.integrator.queue.config.classes.JMSMessageService;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.InventoryToXmlMessageProcessor;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.OrderAckMessageProcessor;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.OrderToXmlMessageProcessor;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.PendingOrderToXmlMessageProcessor;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.SalesToXmlMessageProcessor;
import bbr.b2b.soa.integrator.queue.soa.manager.classes.StockUpdateMessageProcessor;
import bbr.b2b.soa.integrator.queue.utils.QueueDefinitions;

@Service
public class SOAMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SOAMessageListener.class);

	@Autowired
	private JMSMessageService messageService;

	@Autowired
	private OrderToXmlMessageProcessor orderToXmlMessageProcessor;
	
	@Autowired
	private SalesToXmlMessageProcessor salesToXmlMessageProcessor;
	
	@Autowired
	private InventoryToXmlMessageProcessor inventoryToXmlMessageProcessor;
	
	@Autowired
	private OrderAckMessageProcessor orderAckMessageProcessor; 
	
	@Autowired
	private PendingOrderToXmlMessageProcessor pendingOrderToXmlMessageProcessor;
	
	@Autowired
	private StockUpdateMessageProcessor stockUpdateMessageProcessor;
	
	private static JAXBContext solOcJc = null;
	private static JAXBContext solSalesJc = null;
	private static JAXBContext solInvJc = null;
	private static JAXBContext notOcJc = null;
	private static JAXBContext solProvJc = null;
	private static JAXBContext stockJc = null;

	private static JAXBContext getSolOcJC() throws JAXBException {
		if (solOcJc == null)
			solOcJc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes");
		return solOcJc;
	}
	
	private static JAXBContext getSolSalesJC() throws JAXBException {
		if (solSalesJc == null)
			solSalesJc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.SolicitudVentas");
		return solSalesJc;
	}
	
	private static JAXBContext getSolInvJC() throws JAXBException {
		if (solInvJc == null)
			solInvJc = JAXBContext.newInstance("bbr.b2b.b2blink.commercial.xml.SolicitudInventario");
		return solInvJc;
	}
	
	private static JAXBContext getNotOcJC() throws JAXBException {
		if (notOcJc == null)
			notOcJc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden");
		return notOcJc;
	}
	
	private static JAXBContext getSolProvJC() throws JAXBException {
		if (solProvJc == null)
			solProvJc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes");
		return solProvJc;
	}
	
	private static JAXBContext getStockJC() throws JAXBException {
		if (stockJc == null)
			stockJc = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.InventarioProveedor");
		return stockJc;
	}

	@JmsListener(destination = QueueDefinitions.Q_SOA_IN, containerFactory = "defaultJmsListenerContainerFactory")
	@Transactional
	public void receiveMessage(@Payload String content,
								@Header(name = JmsHeaders.CORRELATION_ID, defaultValue = "none") String correlationId, 
								@Header(name = "ticketNumber", defaultValue = "none") String ticketNumber,								
								Message message) {

		try {
			LOGGER.info("Mensaje recibido - Cola: " + QueueDefinitions.Q_SOA_IN + " - Contenido: " + content);
				
			// VALIDA EL ESQUEMA Y DIRIGE EL MENSAJE AL PROCESSOR CORRESPONDIENTE
			if (validateSolOcSchema(content)) {				
				
				SolicitudOrdenes request = (SolicitudOrdenes) getUnmarshalObject(getSolOcJC(), content);				
				
				orderToXmlMessageProcessor.processMessage(request);					
			}
			
			else if (validateSolSalesSchema(content)) {		
				
				SolicitudVentas request = (SolicitudVentas) getUnmarshalObject(getSolSalesJC(), content);		
				
				String vendorCode = request.getCodproveedor().trim();
				
				salesToXmlMessageProcessor.processMessage(vendorCode);					
			}		
			
			else if (validateSolInvSchema(content)) {		
				
				SolicitudInventario request = (SolicitudInventario) getUnmarshalObject(getSolInvJC(), content);		
				
				String vendorCode = request.getCodproveedor().trim();
				
				inventoryToXmlMessageProcessor.processMessage(vendorCode);					
			}	
			
			else if (validateNotOcSchema(content)) {
				
				NotificacionReciboOrden request = (NotificacionReciboOrden) getUnmarshalObject(getNotOcJC(), content);		
				
				orderAckMessageProcessor.processMessage(request);					
			}	
			
			else if (validateSolProvSchema(content)) {
				
				SolicitudProveedoresOrdenesPendientes request = (SolicitudProveedoresOrdenesPendientes) getUnmarshalObject(getSolProvJC(), content);		
								
				pendingOrderToXmlMessageProcessor.processMessage(request);					
			}	
			
			else if (validateStockSchema(content)) {
				
				InventarioProveedor request = (InventarioProveedor) getUnmarshalObject(getStockJC(), content);		
								
				stockUpdateMessageProcessor.processMessage(request, ticketNumber);					
			}	
			
			else {
				LOGGER.info("Mensaje no corresponde a ningún esquema definido");
				
				messageService.send(QueueDefinitions.Q_ERROR, message);		
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private Boolean validateSolOcSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema SolicitudOrdenes");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/SolicitudOrdenes.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a SolicitudOrdenes");					
			return false;
		}	
	}
	
	private Boolean validateSolSalesSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema SolicitudVentas");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/SolicitudVentas.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a SolicitudVentas");	
			return false;
		}	
	}

	private Boolean validateSolInvSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema SolicitudInventario");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/SolicitudInventario.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a SolicitudInventario");	
			return false;
		}	
	}
	
	private Boolean validateNotOcSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema NotificacionReciboOrden");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/NotificacionReciboOrden.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a NotificacionReciboOrden");	
			return false;
		}	
	}
	
	private Boolean validateSolProvSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema SolicitudProveedoresOrdenesPendientes");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/SolicitudProveedoresOrdenesPendientes.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a SolicitudProveedoresOrdenesPendientes");	
			return false;
		}	
	}
	
	private Boolean validateStockSchema(String content) {
		
		try {			
			LOGGER.info("Validando esquema InventarioProveedor");
			
			SchemaFactory factory = SchemaFactory.newInstance(XmlConstants.URI_XSD);
			InputStream xsd = this.getClass().getResourceAsStream("/bbr/b2b/b2blink/xsd/InventarioProveedor.xsd");
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(content)));
			return true;
			
		} catch (IOException | SAXException e) {
			LOGGER.info("Esquema no corresponde a InventarioProveedor");	
			return false;
		}	
	}
	
	private Object getUnmarshalObject(JAXBContext jc, String content) {
		try {
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			return u.unmarshal(source);		 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
