package bbr.b2b.regional.logistic.managers.classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.ChilexpressCourierStateTmpServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierFileServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierStateServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierTagServerLocal;
import bbr.b2b.regional.logistic.couriers.data.wrappers.ChilexpressCourierStateTmpW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierFileW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierTagW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierStateFileDataDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.managers.interfaces.AddChilexpressStatesTimerLocal;
import bbr.b2b.regional.logistic.scheduler.managers.interfaces.SchedulerMailManagerLocal;

@Stateless(name = "managers/AddChilexpressStatesTimer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AddChilexpressStatesTimer implements AddChilexpressStatesTimerLocal {
	
	private static Logger logger = Logger.getLogger(AddChilexpressStatesTimer.class);
	private static Logger chileexpressStateslog = Logger.getLogger("chilexpressStateslog");
	
	private static boolean isServiceStarted = false;
	
	@EJB
	ChilexpressCourierStateTmpServerLocal chilexpresscourierstatetmpserver;
	
	@EJB
	DirectOrderServerLocal directorderserver;
	
	@EJB
	DODeliveryServerLocal dodeliveryserver;
	
	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;
	
	@EJB
	CourierServerLocal courierserver;
	
	@EJB
	CourierStateServerLocal courierstateserver;
	
	@EJB
	CourierFileServerLocal courierfileserver;
	
	@EJB
	CourierTagServerLocal couriertagserver;
	
	@EJB
	DODeliveryReportManagerLocal dodeliverymanager;
	
	@EJB
	SchedulerMailManagerLocal schedulermailmanager;
	
	@Resource
	private SessionContext ctx;

	public SessionContext getCtx() {
		return ctx;
	}
	
	public synchronized void scheduleTimer(long initialinterval, long milliseconds) {
		
		// Cancelar todos los timers previamente asociados a este manager
		TimerService ts = ctx.getTimerService();
		
		// Obtiene todos los timers asociados al bean
		Collection<Timer> timers = ts.getTimers();
		
		// ... y los cancela
		for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
			Timer timer = (Timer) iterator.next();
			timer.cancel();
		}
		
		// Crear el timerservice
		ctx.getTimerService().createTimer(initialinterval, milliseconds, "Cron para registro de estados Courier Chilexpress");
		isServiceStarted = true;
	}

	public void setCtx(SessionContext ctx) {
		this.ctx = ctx;
	}

	@Timeout
	@TransactionTimeout(value = 1200)
	public synchronized void timeoutHandler(Timer timer) {
		
		if (!isServiceStarted) {
			return;
		}
		
		ChilexpressCourierStateTmpW[] states = null;
		try {
			DODeliveryStateTypeW typedlvschpending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");
			DODeliveryStateTypeW typedlvschwithdraw = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_RETIRO");
			DODeliveryStateTypeW typedlvonroute = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");
			DODeliveryStateTypeW typedlvreceived = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "REC_CONFORME");
			DODeliveryStateTypeW typedlvexpectations = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EXPECTATIVAS");
			DODeliveryStateTypeW typedlvlost = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EXTRAVIADO");
			HashMap<String, DODeliveryStateTypeW> dodstMap = new HashMap<String, DODeliveryStateTypeW>();
			dodstMap.put("PEND_AGENDAR", typedlvschpending);
			dodstMap.put("PEND_RETIRO", typedlvschwithdraw);
			dodstMap.put("EN_RUTA", typedlvonroute);
			dodstMap.put("REC_CONFORME", typedlvreceived);
			dodstMap.put("EXPECTATIVAS", typedlvexpectations);
			dodstMap.put("EXTRAVIADO", typedlvlost);
			
			HashMap<Long, DirectOrderW> doMap = null;
		    HashMap<Long, DODeliveryW> dMap = null;
		    HashMap<Long, DirectOrderW> ddoMap = null;
		    HashMap<Long, CourierTagW> dctMap = null;
		    CourierFileW[] courierfiles = null;
		    CourierFileW courierfile = null;
		    DirectOrderW directorder = null;
			CourierTagW couriertag = null;
			String filename = "";
		    
			Date now = new Date();
			
			// Validar si existen archivos cargados sin registros en la tabla y en ese caso cerrarlos
			courierfiles = courierfileserver.getEmptyCourierFiles();
			if (courierfiles != null && courierfiles.length > 0) {
				for (CourierFileW cf : courierfiles) {
					cf.setDayloaded(true);
			    	courierfileserver.updateCourierFile(cf);
			    	
			    	chileexpressStateslog.info("Cerrando archivo de estados Courier Chilexpress " + filename);
				}				
			}
			
			CourierW courier = courierserver.getByPropertyAsSingleResult("code", "CE");
			
		    // Obtener los primeros X registros del primer archivo de la tabla que no hayan sido actualizados con su despacho
		    states = chilexpresscourierstatetmpserver.getFirstFileOutdatedChilexpressCourierStates();
		    if (states != null && states.length > 0) {
		    	filename = states[0].getFilename();
				
		    	// Crear registro si el archivo no est� en la tabla 'Archivo Courier'
				courierfiles = courierfileserver.getByPropertyAsArray("filename", filename);
				if (courierfiles == null || courierfiles.length <= 0) {
					courierfile = new CourierFileW();					
					courierfile.setDayloaded(false);
					courierfile.setFilename(filename);
					courierfile.setCourierid(courier.getId());
					courierfile.setUploaddate(now);
					
					courierfile = courierfileserver.addCourierFile(courierfile);
				}
				else {
					courierfile = courierfiles[0];
				}
				
		    	chileexpressStateslog.info("Iniciando validación parcial de estados Courier Chilexpress del archivo " + filename);
		    			    		
		    	try {
			    	////// Validaciones ///////			    						
					doMap = new HashMap<Long, DirectOrderW>();
					dMap = new HashMap<Long, DODeliveryW>();
				    dctMap = new HashMap<Long, CourierTagW>();
					List<Long> toDeleteStates = new ArrayList<Long>();
			    				
					List<String> errorList = new ArrayList<String>();
					for (ChilexpressCourierStateTmpW state : states) {
						if (!dodeliverymanager.doValidateChilexpressStateByFile(state, doMap, dMap, dctMap, dodstMap, toDeleteStates)) {
							errorList.add(state.getWorkorderreference() + "(archivo " + state.getFilename() + ")");
						}						
					} // END FOR OUTDATED REGISTERS
					
					if (errorList.size() > 0) {
						String description = "Error validando estados de las órdenes!!!:<br>";
							for (String ordernumber : errorList) {
								description += " - " + ordernumber + "<br>";
							}								
							description += "Considerar que el archivo permanece sin procesar<br>";
							sendErrorMail("validación de estados Courier Chilexpress", description, "COURIER_STATES", "ERROR-ADD-COURIER-STATES-CRON");
					}
					
					// Eliminar los registros que no pasaron la validación
					if (toDeleteStates.size() > 0) {
						int total = chilexpresscourierstatetmpserver.deleteByIds(toDeleteStates);
						chileexpressStateslog.info("Eliminados " + total + " registros tras la validación de estados Courier Chilexpress del archivo " + filename);
					}
					chilexpresscourierstatetmpserver.flush();
					
		    	} catch (Exception e1) {
					e1.printStackTrace();
					addLogAck(filename, "FRACASO", "", "", "", "Error en actualización de tabla temporal de estados Courier Chilexpress, archivo " + filename);
				}
		    	
		    	chileexpressStateslog.info("Terminada validación parcial de estados Courier Chilexpress del archivo " + filename);
		    	
				// Validar si no quedan registros del archivo por cargar, en ese caso cerrarlo
			    states = chilexpresscourierstatetmpserver.getChilexpressCourierStatesByFile(filename);
			    if (states == null || states.length <= 0) {
			    	courierfile.setDayloaded(true);
			    	courierfileserver.updateCourierFile(courierfile);
			    	
			    	chileexpressStateslog.info("No existen nuevos estados Courier Chilexpress en el archivo " + filename);
			    }
		    }
		    else {
		    	// Obtener los primeros X despachos del primer archivo actualizado de la tabla
			    states = chilexpresscourierstatetmpserver.getFirstFileUpdatedChilexpressCourierStates();
			    if (states != null && states.length > 0) {
			    	filename = states[0].getFilename();
			    	chileexpressStateslog.info("Iniciando registro parcial de estados Courier Chilexpress del archivo " + filename);
			    	
			    	try {
				    	// Validar si el d�a anterior hubo alg�n registro de archivo en la tabla 'Archivo Courier'
						// En caso de no ser as�, se debe ingresar un registro en la tabla
						Integer dayoff = courierfileserver.getDayOff();
						if (dayoff == null || dayoff == 0) {
							courierfile = new CourierFileW();
							
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.DAY_OF_YEAR, -1);
							cal.set(Calendar.HOUR_OF_DAY, 0);
							
							courierfile.setUploaddate(cal.getTime());
							courierfile.setDayloaded(true);
							courierfile.setCourierid(courier.getId());
							courierfile.setFilename("No se registraron archivos");
							
							courierfileserver.addCourierFile(courierfile);
						}
						
						// Validar si el archivo ya fue registrado en la tabla 'Archivo Courier'
						// Obtenerlo o generarlo
						courierfiles = courierfileserver.getByPropertyAsArray("filename", filename);
						if (courierfiles == null || courierfiles.length <= 0) {
							now = new Date();
							
							courierfile = new CourierFileW();
							
							courierfile.setDayloaded(false);
							courierfile.setFilename(filename);
							courierfile.setCourierid(courier.getId());
							courierfile.setUploaddate(now);
							
							courierfile = courierfileserver.addCourierFile(courierfile);
						}
						else {
							courierfile = courierfiles[0];
						}
				    	
				    	// Agrupar los estados por despacho
						HashMap<Long, List<CourierStateFileDataDTO>> dcsMap = new HashMap<Long, List<CourierStateFileDataDTO>>();
						ddoMap = new HashMap<Long, DirectOrderW>();
						dctMap = new HashMap<Long, CourierTagW>();
				    	List<CourierStateFileDataDTO> csfdList = null;    	
				    	CourierStateFileDataDTO courierStateFileData = null;
				    	for (ChilexpressCourierStateTmpW state : states) {
							if (!ddoMap.containsKey(state.getDodeliveryid())) {
								try {
									directorder = directorderserver.getById(state.getDirectorderid());
								} catch (Exception e) {
									continue;
								}
								ddoMap.put(state.getDodeliveryid(), directorder);
							}
							else {
								directorder = ddoMap.get(state.getDodeliveryid());
							}
							
							if (!dctMap.containsKey(state.getDodeliveryid())) {
								try {
									couriertag = couriertagserver.getById(state.getCouriertagid());
								} catch (Exception e) {
									continue;
								}
								dctMap.put(state.getDodeliveryid(), couriertag);
							}
							else {
								couriertag = dctMap.get(state.getDodeliveryid());
							}
							
				    		now = new Date();
				    		
							// Se agregan los datos de cada l�nea
							courierStateFileData = new CourierStateFileDataDTO();
							courierStateFileData.setLine(state.getLine());
							courierStateFileData.setDescription(state.getEvent());
							courierStateFileData.setStartdate(state.getEventfulldate());
							courierStateFileData.setUploaddate(now);
							courierStateFileData.setCouriertagsendnumber(couriertag.getSendnumber());
							courierStateFileData.setFilename(state.getFilename());
							courierStateFileData.setChilexpresscourierstatetmpid(state.getId());
							courierStateFileData.setDodeliveryid(state.getDodeliveryid());
							courierStateFileData.setCouriertagid(state.getCouriertagid());
							courierStateFileData.setCourierfileid(courierfile.getId());
													
							if (dcsMap.containsKey(state.getDodeliveryid())) {
								csfdList = dcsMap.get(state.getDodeliveryid());
							}
							else {
								csfdList = new ArrayList<CourierStateFileDataDTO>();
							}
							csfdList.add(courierStateFileData);
							
							dcsMap.put(state.getDodeliveryid(), csfdList);
					    }
				    	
						// Crear comparador de l�neas del archivo con la fecha real
						Comparator courierStateComparator = new Comparator<CourierStateFileDataDTO>() {
							public int compare(CourierStateFileDataDTO o1, CourierStateFileDataDTO o2) {
								return Long.compare(o1.getStartdate().getTime(), o2.getStartdate().getTime());
							}
					    };
				    	
					    List<String> errorList = new ArrayList<String>();
					    for (Map.Entry<Long, List<CourierStateFileDataDTO>> entry : dcsMap.entrySet()) {
							directorder = ddoMap.get(entry.getKey());
													
							// Ordenar los estados del Courier del archivo
							csfdList = entry.getValue();
							Arrays.sort(csfdList.toArray(new CourierStateFileDataDTO[csfdList.size()]), courierStateComparator);
							
							for (CourierStateFileDataDTO csfd : csfdList) {
								// Asociar los estados a los despachos en una transacción independiente
								if (!dodeliverymanager.doUploadChilexpressStateByFile(csfd, directorder.getId(), dodstMap)) {
									errorList.add(directorder.getNumber() + "(archivo " + csfd.getFilename() + ")");
								}
							}
						}
					    
					    if (errorList.size() > 0) {
							String description = "Error procesando estados de despachos de las órdenes!!!:<br>";
								for (String ordernumber : errorList) {
									description += " - " + ordernumber + "<br>";
								}								
								description += "Considerar que el archivo permanece sin procesar<br>";
								sendErrorMail("procesamiento de estados Courier Chilexpress", description, "COURIER_STATES", "ERROR-ADD-COURIER-STATES-CRON");
						}
					    					    
			    	} catch (Exception e1) {
						e1.printStackTrace();
						addLogAck(filename, "FRACASO", "", "", "", "Error en registro parcial de estados Courier Chilexpress, archivo " + filename);
					}
			    	
			    	chileexpressStateslog.info("Terminado registro parcial de estados Courier Chilexpress del archivo " + filename);
			    	
					// Validar si no quedan registros del archivo por cargar, en ese caso cerrarlo
				    states = chilexpresscourierstatetmpserver.getChilexpressCourierStatesByFile(filename);
				    if (states == null || states.length <= 0) {
				    	courierfile.setDayloaded(true);
				    	courierfileserver.updateCourierFile(courierfile);
				    	
				    	chileexpressStateslog.info("Terminado registro de todos los estados Courier Chilexpress del archivo " + filename);
				    }
			    }
		    }
		    
		} catch (Exception e) {
			e.printStackTrace();
			chileexpressStateslog.info("Error en procesamiento de estados Courier Chilexpress");
			sendErrorMail("procesamiento de estados Courier Chilexpress", "Error procesando estados!!! Considerar que el archivo permanece sin cerrar", "COURIER_STATES", "ERROR-ADD-COURIER-STATES-CRON");
		}		
	}
	
	private void sendErrorMail(String subjectAppendix, String description, String type, String code) {

		try {
			// Enviar correo para notificar fallos
			String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String subject = "B2B Paris: Fallo en " + subjectAppendix;
			String from = RegionalLogisticConstants.getInstance().getMailSender();
			String[] to = RegionalLogisticConstants.getInstance().getERROR_MAIL_TO().split(",");
			String cc[] = null;
			String bcc[] = null;

			if (to != null && !to[0].equals("")) {
				DateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

				Date now = new Date();

				String message =
					"<p>Ha ocurrido un fallo en " + subjectAppendix + " con fecha " + sdfDate.format(now) + ".<br>" + //
					"El detalle es el siguiente:</p>" + //
					"<p>" + description + "</p>" + //
					"<p>Atte.<br>" + //
					"B2B Paris</p>"; //

				schedulermailmanager.doAddMailToQueue(from, to, cc, bcc, subject, message, true, session, type, code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("No fue posible enviar email para notificar el fallo en " + subjectAppendix);
		}
	}
	
	private void addLogAck(String fileName, String result, String ocNumber, String dNumber, String sendNumber, String comment) {
		
		//Separador
		String s = ";";
		
		//Campos
		String tipoArchivo = "TrackingPushCXP";
		String identificador = fileName;
		String estadoActualizacion = result;
		String nroOc = ocNumber;
		String despacho = dNumber;
		String nroOt = sendNumber;
		String comentario = comment;	
		
		chileexpressStateslog.info(tipoArchivo + s + identificador + s + estadoActualizacion + s + nroOc + s + 
									despacho + s + nroOt + s + comentario);
	}
	
}