package bbr.b2b.soa.mercadolibre.managers.classes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bbr.b2b.b2blink.logistic.xml.NotificacionReciboOrden.NotificacionReciboOrden;
import bbr.b2b.soa.mercadolibre.constants.Topic;
import bbr.b2b.soa.mercadolibre.entities.Company;
import bbr.b2b.soa.mercadolibre.entities.Notification;
import bbr.b2b.soa.mercadolibre.entities.NotificationDetail;
import bbr.b2b.soa.mercadolibre.managers.interfaces.INotificacionReciboOrdenMessageProcessor;
import bbr.b2b.soa.mercadolibre.repository.classes.CompanyRepository;
import bbr.b2b.soa.mercadolibre.repository.classes.NotificationDetailRepository;

@Service
@Transactional
public class NotificacionReciboOrdenMessageProcessor implements INotificacionReciboOrdenMessageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionReciboOrdenMessageProcessor.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private NotificationDetailRepository notificationDetailRepository;

	@Override
	public void processMessage(NotificacionReciboOrden notificacionReciboOrden) {
		// 1) Buscar la empresa asociada por código
		Optional<Company> optCompany = companyRepository.findByRut(notificacionReciboOrden.getCodproveedor());
		if (optCompany.isEmpty()) {
			LOGGER.error("No se encontró una empresa con el código : {}", notificacionReciboOrden.getCodproveedor());
			return;
		}
		Company company = optCompany.get();
		// 2) Buscar los detalles de notificacion de tipo orden con el resource correspondiente para la empresa
		String resource = "/orders/" + notificacionReciboOrden.getNumorden();
		List<NotificationDetail> list = notificationDetailRepository.findByCompanyUserIdAndResource(company.getUserId(), resource);
		for (NotificationDetail detail : list) {
			// Por cada detalle de notificacion, validar si se trata de una OC, si no, salir
			Notification notification = detail.getNotification();
			if (!Topic.ORDERS.equals(notification.getTopic())) {
				continue;
			}
			// marcar el detalle como recibido y regstrar la fecha de recepción en base a la fecha en el mensaje
			LocalDateTime dateReceived = notificacionReciboOrden.getFecha().toGregorianCalendar().toZonedDateTime().toLocalDateTime();
			detail.setReceived(true);
			detail.setDateReceived(dateReceived);
			notificationDetailRepository.save(detail);
		}

	}

}
