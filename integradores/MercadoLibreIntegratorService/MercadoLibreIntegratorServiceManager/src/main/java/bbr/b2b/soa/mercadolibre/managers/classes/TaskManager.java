package bbr.b2b.soa.mercadolibre.managers.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.core.SchedulerLock;

@Component
public class TaskManager {

	private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private AuthorizationManager authorizationManager;

	@Autowired
	private NotificationManager notificationManager;

	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledRefreshAuthorizationTokens", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledRefreshAuthorizationTokens() {
		logger.info("Cron Refrescar Autorizaciones :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		try {
			authorizationManager.refreshAuthorizationTokens();
		} catch (Exception e) {
			logger.error("Error al realizar tarea", e);
		}
	}

	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledRequestPendingNotificationDetails", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledRequestPendingNotificationDetails() {
		logger.info("Cron Solicitar Detalle Notificaciones :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		try {
			notificationManager.requestPendingNotificationDetails();
		} catch (Exception e) {
			logger.error("Error al realizar tarea", e);
		}
	}

	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledSendUninformedNotificationDetails", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledSendUninformedNotificationDetails() {
		logger.info("Cron Enviar Notificaciones Pendientes :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
		try {
			notificationManager.sendUninformedNotificationDetails();
		} catch (Exception e) {
			logger.error("Error al realizar tarea", e);
		}
	}

}
