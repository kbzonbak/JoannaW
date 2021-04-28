package bbr.b2b.soa.integrator.manager.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import bbr.b2b.soa.integrator.extractors.manager.classes.SodimacOrderExtractorManager;
import bbr.b2b.soa.integrator.extractors.manager.classes.SodimacSalesExtractorManager;
import net.javacrumbs.shedlock.core.SchedulerLock;

@Component
public class TaskManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private SendSOANotificationManager sendSoaNotificationManager;
	
	@Autowired
	private SodimacOrderExtractorManager sodimacOrderExtractorManager;
	
	@Autowired
	private SodimacSalesExtractorManager sodimacSalesExtractorManager;

	@Value("${cron.notifications.enabled:true}")
	private boolean notEnabled;	
	
	@Value("${cron.extractors.enabled:true}")
	private boolean extEnabled;	
	
	// CADA 2 MINUTOS
	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledOcSendNotificationMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledOcSendNotificationMessages() {
		
		try {
			if (notEnabled) {
				LOGGER.info("Cron de Envío de Notificaciones de OC a SOA :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sendSoaNotificationManager.sendOcNotifications();
			}
			else
				LOGGER.info("Cron de Envío de Notificaciones de OC a SOA esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
	
	// CADA 2 MINUTOS
	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledSalesSendNotificationMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledSalesSendNotificationMessages() {
		try {
			if (notEnabled) {
				LOGGER.info("Cron de Envío de Notificaciones de Ventas a SOA :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sendSoaNotificationManager.sendSalesNotifications();				
			}
			else
				LOGGER.info("Cron de Envío de Notificaciones de Ventas a SOA esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
	
	// CADA 2 MINUTOS
	@Scheduled(cron = "0 0/2 * * * ?")
	@SchedulerLock(name = "TaskManager_scheduledInventorySendNotificationMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledInventorySendNotificationMessages() {		
		try {
			if (notEnabled) {
				LOGGER.info("Cron de Envío de Notificaciones de Inventario a SOA :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sendSoaNotificationManager.sendInventoryNotifications();
			}			
			else
				LOGGER.info("Cron de Envío de Notificaciones de Inventario a SOA esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
	
	// CADA 1 HORA
	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	@SchedulerLock(name = "TaskManager_scheduledExtractorEOCMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledExtractorEOCMessages() {		
		try {
			if (extEnabled) {
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOC :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sodimacOrderExtractorManager.doProcessOC();
			}			
			else
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOC esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
	
	// CADA 1 HORA
	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	@SchedulerLock(name = "TaskManager_scheduledExtractorEOEMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledExtractorEOEMessages() {		
		try {
			if (extEnabled) {
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOE :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sodimacOrderExtractorManager.doProcessOE();
			}			
			else
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOE esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
		
	// CADA 1 HORA
	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	@SchedulerLock(name = "TaskManager_scheduledExtractorEODMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledExtractorEODMessages() {		
		try {
			if (extEnabled) {
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOD :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sodimacOrderExtractorManager.doProcessOD();
			}			
			else
				LOGGER.info("Cron de Extracción de Mensajes de Tipo EOD esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
	
	// UNA VEZ AL DÍA, A LAS 13:00 HRS
	@Scheduled(cron = "0 0 13 1/1 * ?")
	@SchedulerLock(name = "TaskManager_scheduledExtractorVTAMessages", lockAtLeastForString = "PT2M", lockAtMostForString = "PT5M")
	public void scheduledExtractorVTAMessages() {		
		try {
			if (extEnabled) {
				LOGGER.info("Cron de Extracción de Mensajes de Tipo Venta/Inventario :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				sodimacSalesExtractorManager.doProcessVtaInv();
			}			
			else
				LOGGER.info("Cron de Extracción de Mensajes de Tipo Venta/Inventario esta deshabilitado");
		} catch (Exception e) {
			LOGGER.error("Error al realizar tarea", e);
		}
	}	
}
