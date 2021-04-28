package bbr.b2b.soa.mercadolibre.listeners.classes;

import java.io.StringReader;
import java.util.Optional;

import javax.jms.Message;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes.SolicitudOrdenes;
import bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes.SolicitudProveedoresOrdenesPendientes;
import bbr.b2b.soa.mercadolibre.managers.classes.NotificacionReciboOrdenMessageProcessor;
import bbr.b2b.soa.mercadolibre.managers.classes.SolicitudOrdenesMessageProcessor;
import bbr.b2b.soa.mercadolibre.managers.classes.SolicitudReporteOrdenesPendientesMessageProcessor;
import bbr.b2b.soa.mercadolibre.utis.JMSMessageService;

@Component
@ConditionalOnBean(name = "primaryJMSConfiguration")
@ConditionalOnProperty(prefix = "mdp.jms.inbound.queue", value = "name")
public class SOAMessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SOAMessageListener.class);

	private static JAXBContext jcCtxSolicitudOrden = null;

	private static JAXBContext jcCtxNotificacionRecOC = null;

	private static JAXBContext jcCtxSolicitudProOrdenesPendientes = null;

	@Autowired
	private SolicitudOrdenesMessageProcessor solicitudOrdenesMessageProcessor;

	@Autowired
	private NotificacionReciboOrdenMessageProcessor notificacionReciboOrdenMessageProcessor;

	@Autowired
	private SolicitudReporteOrdenesPendientesMessageProcessor solicitudReporteOrdenesPendientesMessageProcessor;

	@Autowired
	private JMSMessageService messageservice;

	@Value("${mdp.jms.error.queue.name}")
	private String errorQueueName;

	@JmsListener(destination = "${mdp.jms.inbound.queue.name}", containerFactory = "defaultJmsListenerContainerFactory")
	@Transactional
	public void handleMessage(@Payload String content, Message message) {

		// Validar XSD del mensaje - SolicitudOrdenes
		JAXBContext jcSolicitudOC = getSolicitudOrdenes_JC();
		Optional<Object> optSolicitudOC = getUnmarshalObject(jcSolicitudOC, content);
		if (optSolicitudOC != null && optSolicitudOC.isPresent()) {
			SolicitudOrdenes solicitud = (SolicitudOrdenes) optSolicitudOC.get();
			solicitudOrdenesMessageProcessor.processMessage(solicitud);
			return;
		}

		// Validar XSD del mensaje - NotificacionReciboOrden
		JAXBContext jcNotificacionRecOCSolicitudOC = getNotificacionRecOC_JC();
		Optional<Object> optNotificacionReciboOrden = getUnmarshalObject(jcNotificacionRecOCSolicitudOC, content);
		if (optNotificacionReciboOrden != null && optNotificacionReciboOrden.isPresent()) {
			NotificacionReciboOrden notificacionReciboOrden = (NotificacionReciboOrden) optNotificacionReciboOrden.get();
			notificacionReciboOrdenMessageProcessor.processMessage(notificacionReciboOrden);
			return;
		}

		// Validar XSD del mensaje - SolicitudProveedoresOrdenesPendientes
		JAXBContext jcSolicitudOrdenesPendientes = getSolicitudOrdenesPendientes_JC();
		Optional<Object> optSolicitudOrdenesPendientes = getUnmarshalObject(jcSolicitudOrdenesPendientes, content);
		if (optSolicitudOrdenesPendientes != null && optSolicitudOrdenesPendientes.isPresent()) {
			SolicitudProveedoresOrdenesPendientes solicitudOrdenesPendientes = (SolicitudProveedoresOrdenesPendientes) optSolicitudOrdenesPendientes.get();
			solicitudReporteOrdenesPendientesMessageProcessor.processMessage(solicitudOrdenesPendientes);
			return;
		}

		// Si se llega a este punto el mensaje no es válido
		LOGGER.info("Mensaje no corresponde a ningún esquema definido");
		messageservice.send(errorQueueName, message);

	}

	private static JAXBContext getSolicitudOrdenes_JC() {
		if (jcCtxSolicitudOrden == null)
			try {
				jcCtxSolicitudOrden = JAXBContext.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudOrdenes");
			} catch (JAXBException e) {
				LOGGER.error("Error al obtener JAXBContext", e);
				throw new RuntimeException("Error al obtener JAXBContext", e);
			}
		return jcCtxSolicitudOrden;
	}

	private static JAXBContext getNotificacionRecOC_JC() {
		if (jcCtxNotificacionRecOC == null)
			try {
				jcCtxNotificacionRecOC = JAXBContext
						.newInstance("bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden");
			} catch (JAXBException e) {
				LOGGER.error("Error al obtener JAXBContext", e);
				throw new RuntimeException("Error al obtener JAXBContext", e);
			}
		return jcCtxNotificacionRecOC;
	}

	private static JAXBContext getSolicitudOrdenesPendientes_JC() {
		if (jcCtxSolicitudProOrdenesPendientes == null)
			try {
				jcCtxSolicitudProOrdenesPendientes = JAXBContext
						.newInstance("bbr.b2b.b2blink.logistic.xml.SolicitudProveedoresOrdenesPendientes");
			} catch (JAXBException e) {
				LOGGER.error("Error al obtener JAXBContext", e);
				throw new RuntimeException("Error al obtener JAXBContext", e);
			}
		return jcCtxSolicitudProOrdenesPendientes;
	}

	private Optional<Object> getUnmarshalObject(JAXBContext jc, String content) {
		try {
			Unmarshaller u = jc.createUnmarshaller();
			InputSource source = new InputSource(new StringReader(content));
			Object result = u.unmarshal(source);
			return Optional.of(result);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			return Optional.empty();
		}
	}

}