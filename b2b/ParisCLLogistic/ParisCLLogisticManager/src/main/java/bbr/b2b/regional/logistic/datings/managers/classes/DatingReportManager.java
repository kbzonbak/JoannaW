package bbr.b2b.regional.logistic.datings.managers.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.common.factories.DateConverterMode;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.logistic.msgregionalb2b.AsnToXmlLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.OrderTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.PreDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderW;
import bbr.b2b.regional.logistic.buyorders.managers.interfaces.BuyOrderReportManagerLocal;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.datings.classes.DatingRequestServerLocal;
import bbr.b2b.regional.logistic.datings.classes.DatingResourceServerLocal;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.classes.DockServerLocal;
import bbr.b2b.regional.logistic.datings.classes.DockTypeServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ModuleServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ReserveDetailServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ReserveServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ResourceBlockingGroupServerLocal;
import bbr.b2b.regional.logistic.datings.classes.ResourceBlockingServerLocal;
import bbr.b2b.regional.logistic.datings.data.interfaces.IWeekDayConstants;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingRequestW;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingResourcePK;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingResourceW;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ModuleW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailPK;
import bbr.b2b.regional.logistic.datings.data.wrappers.ReserveDetailW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingGroupW;
import bbr.b2b.regional.logistic.datings.data.wrappers.ResourceBlockingW;
import bbr.b2b.regional.logistic.datings.managers.interfaces.DatingReportManagerLocal;
import bbr.b2b.regional.logistic.datings.managers.interfaces.DatingReportManagerRemote;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingAndDetailsInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingAndDetailsResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AddDatingRequestResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedDatingTotalizerByDockDTO;
import bbr.b2b.regional.logistic.datings.report.classes.AssignedResourceBlockingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DailyReportDatesDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingAsNotAttendedInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDataResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingDetailDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestContainerReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestOfDeliveryInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DatingRequestResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DeleteDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DoCancelDatingInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DockDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DockTypeDTO;
import bbr.b2b.regional.logistic.datings.report.classes.DocksArrayResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingReportInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingRequestReportData;
import bbr.b2b.regional.logistic.datings.report.classes.ExcelDatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingOrderInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestOrdersDeleteInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedDatingRequestReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedNonDeliveredOrderDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ImportedNonDeliveredOrderReportResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ModuleDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ModulesArrayResultDTO;
import bbr.b2b.regional.logistic.datings.report.classes.OrderUnitsDTO;
import bbr.b2b.regional.logistic.datings.report.classes.RejectDatingRequestInitParamDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailDataDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserveDetailKeyDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ReserverDetailReportDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ResourceBlockingGroupDTO;
import bbr.b2b.regional.logistic.datings.report.classes.ResourceBlokingGroupInitParamDTO;
import bbr.b2b.regional.logistic.deliveries.classes.BoxServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.BulkServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.PalletServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BoxW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.BulkW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryDetailW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.PalletW;
import bbr.b2b.regional.logistic.deliveries.entities.OrderDeliveryId;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.report.classes.BulkDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DeliveryReportDataDTO;
import bbr.b2b.regional.logistic.evaluations.classes.EvaluationDetailServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionErrorServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.data.classes.EvaluationReportDataResultDTO;
import bbr.b2b.regional.logistic.evaluations.data.classes.EvaluationReportInitParamDTO;
import bbr.b2b.regional.logistic.evaluations.data.classes.ReceptionErrorReportDataDTO;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.items.classes.FlowTypeServerLocal;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.report.classes.BaseResultsDTO;
import bbr.b2b.regional.logistic.taxdocuments.classes.TaxDocumentServerLocal;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/DatingReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DatingReportManager implements DatingReportManagerLocal, DatingReportManagerRemote {

	private Logger logger = Logger.getLogger(DatingReportManager.class);

	private static Logger logger_ack = Logger.getLogger("LogNotificacion");

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	@EJB
	DatingRequestServerLocal datingrequestserver;

	@EJB
	DatingServerLocal datingserver;

	@EJB
	ReserveDetailServerLocal reservedetailserver;

	@EJB
	ReserveServerLocal reserveserver;

	@EJB
	DockServerLocal dockserver;
	
	@EJB
	DockTypeServerLocal docktypeserver;

	@EJB
	OrderServerLocal orderserver;
	
	@EJB
	PreDeliveryDetailServerLocal predeliverydetailserver;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DeliveryServerLocal deliveryserver;

	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;

	@EJB
	DeliveryStateServerLocal deliverystateserver;

	@EJB
	ModuleServerLocal moduleserver;

	@EJB
	ResourceBlockingServerLocal resourceblockingserver;

	@EJB
	ResourceBlockingGroupServerLocal resourceblockinggroupserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	DeliveryTypeServerLocal deliveryTypeServer;

	@EJB
	FlowTypeServerLocal flowTypeServer;

	@EJB
	DatingResourceServerLocal datingresourceserver;

	@EJB
	BulkDetailServerLocal bulkdetailserver;

	@EJB
	BulkServerLocal bulkserver;

	@EJB
	BoxServerLocal boxserver;

	@EJB
	PalletServerLocal palletserver;

	@EJB
	TaxDocumentServerLocal taxdocumentserver;
	
	@EJB
	OrderDeliveryDetailServerLocal orderdeliverydetailserver;

	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;

	@EJB
	OrderTypeServerLocal ordertypeserver;

	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;

	@EJB
	ReceptionErrorServerLocal receptionerrorserver;

	@EJB
	BuyOrderReportManagerLocal buyorderreportmanager;

	@EJB
	DeliveryReportManagerLocal deliveryreportmanager;

	@EJB
	EvaluationDetailServerLocal evaluationdetailserver;
	
	@EJB
	AsnToXmlLocal asntoxml;

	public DocksArrayResultDTO getDocksOfLocation(String locationcode) throws AccessDeniedException, OperationFailedException, NotFoundException {
		DocksArrayResultDTO resultW = new DocksArrayResultDTO();
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", locationcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
		}
		location = locations[0];
		
		try {
			OrderCriteriaDTO[] orderby = new OrderCriteriaDTO[1];
			orderby[0] = new OrderCriteriaDTO();
			orderby[0].setPropertyname("visualorder");
			orderby[0].setAscending(true);
			
			DockW[] docks = dockserver.getByPropertyAsArrayOrdered("location.id", location.getId(), orderby);
			DockDTO[] docksDTO = new DockDTO[docks.length];
			BeanExtenderFactory.copyProperties(docks, docksDTO, DockDTO.class);

			resultW.setDocks(docksDTO);
		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		return resultW;
	}

	public ModulesArrayResultDTO getModules() {
		ModulesArrayResultDTO resultW = new ModulesArrayResultDTO();
		List<ModuleDataDTO> moduleList = new ArrayList<ModuleDataDTO>();
		
		try {
			OrderCriteriaDTO[] criteria = new OrderCriteriaDTO[1];
			criteria[0] = new OrderCriteriaDTO();
			criteria[0].setPropertyname("visualorder");
			criteria[0].setAscending(true);
			ModuleW[] modules = moduleserver.getAllAsArrayOrdered(criteria);
			
			ModuleDataDTO moduleData = null;
			for (int i = 0; i < modules.length; i++) {
				moduleData = new ModuleDataDTO();
				moduleData.setId(modules[i].getId());
				moduleData.setName(modules[i].getName());
				moduleData.setStarts(modules[i].getStarts().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				moduleData.setEnds(modules[i].getEnds().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				moduleList.add(moduleData);
			}
			resultW.setModules(moduleList.toArray(new ModuleDataDTO[moduleList.size()]));

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		return resultW;
	}

	public BaseResultDTO doAddDocksAndModules() throws AccessDeniedException, NotFoundException, OperationFailedException {
		try {
			BaseResultDTO resultW = new BaseResultDTO();
			DatingResourceW datingResource = null;
			DockW[] docks = dockserver.getAllAsArray();
			ModuleW[] modules = moduleserver.getAllAsArray();

			// Se agrega
			for (ModuleW module : modules) {

				for (DockW dockTmp : docks) {
					datingResource = new DatingResourceW();
					datingResource.setDockid(dockTmp.getId());
					datingResource.setModuleid(module.getId());
					datingResource.setActive(true);

					datingresourceserver.addDatingResource(datingResource);
				}
			}

			return resultW;
		} catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			throw e;
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			throw e;
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			throw e;
		}
	}

	public ExcelDatingReportDataDTO[] getExcelDatingReport(ExcelDatingReportInitParamDTO initParams) {
		ExcelDatingReportDataDTO[] result = null;
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		if(locations == null || locations.length == 0){
			return result;
		}
		location = locations[0];
		
		try {
			result = datingserver.getExcelDatingReport(DateConverterFactory2.StrToDate(initParams.getSince()), DateConverterFactory2.StrToDate(initParams.getUntil()), location.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ResourceBlockingGroupDTO doAddResourceBlockingGroupandDetails(ResourceBlokingGroupInitParamDTO initparam) {

		ResourceBlockingGroupDTO resultdto = new ResourceBlockingGroupDTO();
		try {
			ResourceBlockingGroupW result = null;
			
			LocationW[] locationArr = locationserver.getByPropertyAsArray("code", initparam.getBlockingroup().getLocationcode());
			if(locationArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L442");
			}
			LocationW locationW = locationArr[0];
			
			// Verificar que existan detalles
			if (initparam.getDetails() == null || initparam.getDetails().length <= 0)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L2410");

			Date now = new Date();
			// Agregar el bloqueo
			ResourceBlockingGroupW resouceBlockingGroupw = new ResourceBlockingGroupW();
			DateConverterFactory2.copyProperties(initparam.getBlockingroup(), resouceBlockingGroupw, DateConverterMode.STR_TO_DATE);
			resouceBlockingGroupw.setLocationid(locationW.getId());
			
			// validar fechas
			Calendar since1 = Calendar.getInstance();
			since1.setTime(resouceBlockingGroupw.getSince());
			Calendar today = Calendar.getInstance();

			since1.set(Calendar.HOUR_OF_DAY, 0);
			since1.set(Calendar.MINUTE, 0);
			since1.set(Calendar.SECOND, 0);
			since1.set(Calendar.MILLISECOND, 0);

			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);

			if (since1.before(today)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L2411");
			}
			if (since1.after(resouceBlockingGroupw.getUntil())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L1");
			}

			resouceBlockingGroupw.setCreated(now);
			result = resourceblockinggroupserver.addResourceBlockingGroup(resouceBlockingGroupw);
			// Agregar un d�a más a Until para que incluya el d�a final del rango
			Calendar cal = Calendar.getInstance();
			cal.setTime(result.getUntil());
			cal.add(Calendar.DATE, 1);
			Date newuntil = cal.getTime();
			// Obtener las fechas incluidas en la reserva, en base a las fechas de inicio y termino y los dias de la
			// semana
			// seleccionados.
			Date[] dates = getDatesinRangeWithRecurrence(result.getSince(), newuntil, result.getRecurrence());
			cal = Calendar.getInstance();
			ResourceBlockingW blocking = new ResourceBlockingW();
			// Identifica si se pudo crear algun bloqueo
			boolean isapplied = false;
			// Iterar por cada fecha inclu�da en la reserva
			for (int i = 0; i < dates.length; i++) {
				Date date = dates[i];
				cal.setTime(date);
				cal.add(Calendar.DATE, 1);
				Date nextdate = cal.getTime();
				DatingResourcePK[] resources = new DatingResourcePK[initparam.getDetails().length];
				for (int j = 0; j < initparam.getDetails().length; j++) {
					ReserveDetailW detail = new ReserveDetailW();
					DateConverterFactory2.copyProperties(initparam.getDetails()[j], detail, DateConverterMode.STR_TO_DATE);
					DatingResourcePK resource = new DatingResourcePK();
					resource.setDockid(new Long(detail.getDockid().longValue()));
					resource.setModuleid(new Long(detail.getModuleid().longValue()));
					resources[j] = resource;

				}
				// Obtener los recursos libres para el dia
				DatingResourcePK[] unusedresources = getUnusedDatingResourcesByDateandLocation(date, nextdate, resources, locationW.getId());
				// Si no hay modulos libres, no se crea un bloqueo para el dia
				if (unusedresources.length <= 0 || resources.length > unusedresources.length) {
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L2412");
				}
				isapplied = true;
				blocking.setBlockinggroupid(result.getId());
				blocking.setLocationid(result.getLocationid());
				blocking.setWhen(date);
				// Agregar el bloqueo
				blocking = resourceblockingserver.addResourceBlocking(blocking);
				// Agregar los detalles del bloqueo
				Long blockingid = new Long(blocking.getId());
				for (int j = 0; j < unusedresources.length; j++) {
					DatingResourcePK resource = unusedresources[j];
					ReserveDetailW detail = new ReserveDetailW();
					detail.setReserveid(blockingid);
					detail.setDockid(resource.getDockid());
					detail.setModuleid(resource.getModuleid());
					detail.setWhen(date);
					reservedetailserver.addReserveDetail(detail);
				}
			}
			if (!isapplied)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L2414");

			DateConverterFactory2.copyProperties(result, resultdto, DateConverterMode.DATE_TO_STR);
			resultdto.setStatusmessage("La reserva " + result.getId() + " fue creada exitosamente");
			return resultdto;
		} catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L1");
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultdto, "L1");
		}

	}

	private Date[] getDatesinRangeWithRecurrence(Date since, Date until, int recurrence) {
		Date adate = since;
		Calendar cal = Calendar.getInstance();
		// Obtener los d�as en la semana incluidos en la recurrencia
		boolean monday = (IWeekDayConstants.MONDAY & recurrence) == IWeekDayConstants.MONDAY;
		boolean tuesday = (IWeekDayConstants.TUESDAY & recurrence) == IWeekDayConstants.TUESDAY;
		boolean wednesday = (IWeekDayConstants.WEDNESDAY & recurrence) == IWeekDayConstants.WEDNESDAY;
		boolean thursday = (IWeekDayConstants.THURSDAY & recurrence) == IWeekDayConstants.THURSDAY;
		boolean friday = (IWeekDayConstants.FRIDAY & recurrence) == IWeekDayConstants.FRIDAY;
		boolean saturday = (IWeekDayConstants.SATURDAY & recurrence) == IWeekDayConstants.SATURDAY;
		boolean sunday = (IWeekDayConstants.SUNDAY & recurrence) == IWeekDayConstants.SUNDAY;

		List list = new ArrayList();
		do {
			cal.setTime(adate);
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
			boolean include = (dayofweek == Calendar.MONDAY && monday) || (dayofweek == Calendar.MONDAY && monday) || (dayofweek == Calendar.TUESDAY && tuesday) || (dayofweek == Calendar.WEDNESDAY && wednesday) || (dayofweek == Calendar.THURSDAY && thursday)
					|| (dayofweek == Calendar.FRIDAY && friday) || (dayofweek == Calendar.SATURDAY && saturday) || (dayofweek == Calendar.SUNDAY && sunday);
			if (include)
				list.add(adate);
			cal.add(Calendar.DATE, 1);
			adate = cal.getTime();
		} while (adate.compareTo(until) < 0);
		Date[] Result = new Date[list.size()];
		Result = (Date[]) list.toArray(Result);
		return Result;
	}

	private DatingResourcePK[] getUnusedDatingResourcesByDateandLocation(Date since, Date until, DatingResourcePK[] sourceresources, Long locationid) throws NotFoundException, OperationFailedException, AccessDeniedException {

		// Este metodo obtiene, partiendo de los recursos indicados como entrada,
		// los recursos (modulo, anden) no reservados en el dia especificado, es decir,
		// la diferencia de conjuntos: Sea A el conjunto de recursos de entrada, B el conjunto de
		// recursos reservados, se busca obtener A - B.
		// Agregar al conjunto todos los elementos del arreglo de entrada
		TreeSet<DatingResourcePK> A = new TreeSet<DatingResourcePK>();
		TreeSet<DatingResourcePK> B = new TreeSet<DatingResourcePK>();
		for (int i = 0; i < sourceresources.length; i++) {
			DatingResourcePK resource = sourceresources[i];
			A.add(resource);
		}
		// Se obtienen las reservas (citas y bloqueos) para el dia especificado (rango de un dia).
		ReserveDTO[] reserves = reserveserver.getReservesByDateAndLocation(since, until, locationid);
		Long reserveid = null;
		for (int i = 0; i < reserves.length; i++) {
			ReserveDTO reserve = reserves[i];
			reserveid = reserve.getId();
			ReserveDetailW[] details = reservedetailserver.getReserveDetailsofReserve(reserveid);
			for (int j = 0; j < details.length; j++) {
				ReserveDetailW detail = details[j];
				DatingResourcePK resource = new DatingResourcePK();
				resource.setDockid(new Long(detail.getDockid().longValue()));
				resource.setModuleid(new Long(detail.getModuleid().longValue()));
				B.add(resource);
			}
		}
		// Obtener la diferencia de conjuntos
		A.removeAll(B);
		DatingResourcePK[] Result = new DatingResourcePK[A.size()];
		Result = (DatingResourcePK[]) A.toArray(Result);
		return Result;
	}

	public AssignedDatingResultDTO getAssignedDatingByDate(AssignedDatingInitParamDTO initParamW, boolean isbyfilter, boolean isbyreport) {
		AssignedDatingResultDTO resultDTO = new AssignedDatingResultDTO();		
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParamW.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
		}
		location = locations[0];
		
		try {
			Locale locale = null;
			locale = new Locale("es", "CL");
			
			Date initdate = Date.from(initParamW.getDate().atZone(ZoneId.systemDefault()).toInstant());
			
			// Inicio del d�a
			Calendar sinceCal = Calendar.getInstance(locale);
			sinceCal.setTime(initdate);
			sinceCal.set(Calendar.HOUR_OF_DAY, 0);
			sinceCal.set(Calendar.MINUTE, 0);
			sinceCal.set(Calendar.SECOND, 0);

			// Fin del d�a
			Calendar untilCal = Calendar.getInstance(locale);
			untilCal.setTime(sinceCal.getTime());
			untilCal.add(Calendar.DATE, 1);

			Date since = sinceCal.getTime();
			Date until = untilCal.getTime();
			
			// Tipos de andenes
			Long docktypeid = initParamW.getDocktypeid();
			if (isbyfilter) {
				DockTypeDTO[] docktypes = docktypeserver.getDockTypesByLocation(location.getId());
				docktypeid = docktypes[0].getId();
						
				resultDTO.setDockTypeDTOs(docktypes);
			}

			// Obtener los detalles de reserva de las citas asignadas
			AssignedDatingDataDTO[] assignedDating = reservedetailserver.getAssignedDatingDetailsByDateAndLocation(since, until, location.getId(), docktypeid, isbyreport);
			resultDTO.setAssignedDatingDataWs(assignedDating);
			
			// Obtener los detalles de reserva de los bloqueos
			AssignedResourceBlockingDataDTO[] assignedResourceBlocking = reservedetailserver.getAssignedResourceBlockingDetailsByDateAndLocation(since, until, location.getId(), docktypeid, isbyreport);
			resultDTO.setAssignedBlockadeDataWs(assignedResourceBlocking);
			
			if ((assignedDating == null || assignedDating.length == 0) &&
					(assignedResourceBlocking == null || assignedResourceBlocking.length == 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "0", "No existen detalles de reserva", false); // No existen detalles de reserva
			}
			
			// Totalizadores por and�n
			AssignedDatingTotalizerByDockDTO[] assignedTotalizer = orderdeliverydetailserver.getAssignedDatingTotalizerByDateAndLocation(since, until, location.getId(), docktypeid, isbyreport);
			resultDTO.setAssignedDatingTotalizerDTOs(assignedTotalizer);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public ReserverDetailReportDTO getReserveDetailsDataofBlockingGroup(Long blockinggroupid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated, boolean ispageinfo) throws AccessDeniedException, NotFoundException, OperationFailedException {

		ReserverDetailReportDTO result = new ReserverDetailReportDTO();
		ReserveDetailDataDTO[] details = null;
		int total = 0;

		details = reservedetailserver.getReserveDetailsDataofBlockingGroup(blockinggroupid, pagenumber, rows, orderby, ispaginated);

		result.setReservesdatail(details);
		if (ispageinfo) {
			total = reservedetailserver.countReserveDetailsDataofBlockingGroup(blockinggroupid);

			PageInfoDTO pageinfo = new PageInfoDTO();
			pageinfo.setPagenumber(pagenumber);
			pageinfo.setRows(details.length);
			pageinfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
			pageinfo.setTotalrows(total);
			result.setPageinfo(pageinfo);
		}

		return result;

	}

	public BaseResultDTO doDeleteReserveDetailsofResourceBlockingGroup(Long reserveid, ReserveDetailKeyDTO[] detailsid) {

		BaseResultDTO result = new BaseResultDTO();
		try {
			TreeMap<Long, ResourceBlockingW> mapBlockings = new TreeMap();
			for (int i = 0; i < detailsid.length; i++) {
				ReserveDetailPK detailPK = new ReserveDetailPK();
				DateConverterFactory2.copyProperties(detailsid[i], detailPK);
				Long blockingid = detailPK.getReserveid();
				ResourceBlockingW blocking = null;
				// Buscar el bloqueo asociado si no est� en el mapa a�n
				if (!mapBlockings.containsKey(blockingid)) {
					blocking = resourceblockingserver.getById(blockingid, LockModeType.PESSIMISTIC_WRITE);
					mapBlockings.put(blockingid, blocking);
				} else
					blocking = (ResourceBlockingW) mapBlockings.get(blockingid);
				if (blocking == null || blocking.getBlockinggroupid().longValue() != reserveid.longValue())
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L2409");

				//reservedetailserver.deleteIdentifiable(detailPK);
				
				PropertyInfoDTO p1 = new PropertyInfoDTO("id.dockid", "dockid" ,detailPK.getDockid());
				PropertyInfoDTO p2 = new PropertyInfoDTO("id.moduleid", "moduleid" ,detailPK.getModuleid());
				PropertyInfoDTO p3 = new PropertyInfoDTO("id.reserveid", "reserveid" ,detailPK.getReserveid());
				reservedetailserver.deleteByProperties(p1, p2, p3);
				
			}
			reservedetailserver.flush();
			ReserveDetailW[] reservesdetails = reservedetailserver.getReserveDetailsofBlockingGroup(reserveid);
			if (reservesdetails == null || reservesdetails.length == 0) {
				resourceblockingserver.doDeleteResourceBlockingsofResourceBlockingGroup(reserveid);
				resourceblockinggroupserver.deleteElement(reserveid);
			}

		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}

	public BaseResultDTO doDeleteResourceBlockingGroup(Long resourceblockinggroupid) {

		BaseResultDTO result = new BaseResultDTO();
		try {
			// Se obtienen los bloqueos asociados al grupo
			ResourceBlockingW[] rbs = resourceblockingserver.getResourceBlockingsofResourceBlockingGroup(resourceblockinggroupid);
			if (rbs == null || rbs.length == 0)
				throw new OperationFailedException("Grupo de recursos bloqueados no existe");
			Long[] reserveids = new Long[rbs.length];
			for (int i = 0; i < rbs.length; i++) {
				ResourceBlockingW blocking = rbs[i];
				reserveids[i] = blocking.getId();
			}
			// Borrar detalles de los bloqueos
			reservedetailserver.doDeleteReserveDetailsOfReserves(reserveids);
			// Borrar los bloqueos
			resourceblockingserver.doDeleteResourceBlockingsofResourceBlockingGroup(resourceblockinggroupid);
			// Borrar las reservas
			reserveserver.doDeleteReserves(reserveids);
			// Borrar el grupo de bloqueo
			// blockinggroupserver.deleteIdentifiable(resourceblockinggroupid);
			resourceblockinggroupserver.deleteElement(resourceblockinggroupid);

			return result;
		} catch (AccessDeniedException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (NotFoundException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (OperationFailedException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public DatingDataResultDTO getDatingDataOfDelivery(DatingDataInitParamDTO initParamDTO) {
		DatingDataResultDTO resultW = new DatingDataResultDTO();
		try {

			HashMap<String, ReserveDataDTO> reserveMap = new HashMap<String, ReserveDataDTO>();
			ReserveDataDTO reserveData = null;
			int timeInMinStart = 0;
			int timeInMinEnd = 0;
			int timeInMinStartModule = 0;
			int timeInMinEndModule = 0;

			// Buscamos los estados posibles para mostrar cita de un delivery
			DeliveryStateTypeW dstConfirmed = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DeliveryStateTypeW dstInAgend = deliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_AGENDAMIENTO");
			DeliveryStateTypeW dstAgend = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			DeliveryStateTypeW dstAgendError = deliverystatetypeserver.getByPropertyAsSingleResult("code", "ERROR_AGENDAMIENTO");
			DeliveryStateTypeW dstReject = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
			DeliveryStateTypeW dstNotAttended = deliverystatetypeserver.getByPropertyAsSingleResult("code", "NO_ASISTE");
			DeliveryStateTypeW dstReceived = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");

			DeliveryW delivery = deliveryserver.getById(initParamDTO.getDeliveryid());

			// Si el despacho no est� en un estado con cita entonce se devuelve Objeto vac�o
			if (delivery.getCurrentstatetypeid().equals(dstConfirmed.getId()) && delivery.getCurrentstatetypeid().equals(dstInAgend.getId()) && delivery.getCurrentstatetypeid().equals(dstAgend.getId()) && delivery.getCurrentstatetypeid().equals(dstAgendError.getId())
					&& delivery.getCurrentstatetypeid().equals(dstReject.getId()) && delivery.getCurrentstatetypeid().equals(dstNotAttended.getId()) && delivery.getCurrentstatetypeid().equals(dstReceived.getId())) {
				return resultW;
			}

			// Informacion de la Cita
			DatingDataDTO dating = datingserver.getDatingDataByDelivery(initParamDTO.getDeliveryid());
			if (dating == null) {
				return resultW;
			}

			resultW.setDating(dating);

			// Informacion de Agenda
			ReserveDataDTO[] reserves = reserveserver.getReserveData(dating.getId());

			Calendar calendar = GregorianCalendar.getInstance();
			for (int i = 0; i < reserves.length; i++) {
				if (reserveMap.containsKey(reserves[i].getDockcode())) {
					reserveData = reserveMap.get(reserves[i].getDockcode());

					Date startDate = Date.from(reserveData.getStart().atZone(ZoneId.systemDefault()).toInstant());
					Date endDate   = Date.from(reserveData.getEnd().atZone(ZoneId.systemDefault()).toInstant());
					
					Date startDateModule = Date.from(reserves[i].getStart().atZone(ZoneId.systemDefault()).toInstant());
					Date endDateModule = Date.from(reserves[i].getEnd().atZone(ZoneId.systemDefault()).toInstant());
					
					calendar.setTime(startDate);
					timeInMinStart = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
					calendar.setTime(endDate);
					timeInMinEnd = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);

					calendar.setTime(startDateModule);
					timeInMinStartModule = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
					calendar.setTime(endDateModule);
					timeInMinEndModule = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);

					if (timeInMinEnd < timeInMinEndModule)
						reserveData.setEnd(reserves[i].getEnd());
					if (timeInMinStart > timeInMinStartModule)
						reserveData.setStart(reserves[i].getStart());

				} else {
					reserveMap.put(reserves[i].getDockcode(), reserves[i]);
				}
			}

			Iterator it = reserveMap.entrySet().iterator();
			List<DatingDetailDTO> datingDetailList = new ArrayList<DatingDetailDTO>();
			DatingDetailDTO datingDetail = null;
			int dif = 0;
			GregorianCalendar timeCal = null;
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

			while (it.hasNext()) {

				Map.Entry e = (Map.Entry) it.next();
				ReserveDataDTO res = (ReserveDataDTO) e.getValue();
				
				Date startDate = Date.from(res.getStart().atZone(ZoneId.systemDefault()).toInstant());
				Date endDate = Date.from(res.getEnd().atZone(ZoneId.systemDefault()).toInstant());
								
				datingDetail = new DatingDetailDTO();
				datingDetail.setDock(res.getDockcode());
				datingDetail.setTimeRange(formatter.format(startDate) + " - " + formatter.format(endDate));

				// Calculando la diferencia entre las dos horas
				
				calendar.setTime(startDate);
				timeInMinStart = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
				calendar.setTime(endDate);
				timeInMinEnd = (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
				dif = timeInMinEnd - timeInMinStart;

				timeCal = new GregorianCalendar();
				timeCal.set(Calendar.HOUR_OF_DAY, dif / 60);
				timeCal.set(Calendar.MINUTE, dif % 60);
				timeCal.set(Calendar.SECOND, 0);

				datingDetail.setTime(formatter.format(timeCal.getTime()));
				datingDetailList.add(datingDetail);
			}

			resultW.setDatingdetail((DatingDetailDTO[]) datingDetailList.toArray(new DatingDetailDTO[datingDetailList.size()]));

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}

		return resultW;
	}

	public DatingRequestResultDTO getDatingRequestOfDelivery(DatingRequestOfDeliveryInitParamDTO initParams) {
		DatingRequestResultDTO result = new DatingRequestResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());
			
			// Valida que coincida el proveedor
			if (!delivery.getVendorid().equals(vendor.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1904");
			}
			
			DatingRequestW[] datinrequestArr = datingrequestserver.getByPropertyAsArray("delivery.id", initParams.getDeliveryid());
			if (datinrequestArr != null && datinrequestArr.length > 0) {
				DatingRequestDTO datingrequest = new DatingRequestDTO();
				DateConverterFactory2.copyProperties(datinrequestArr[0], datingrequest, DateConverterMode.DATE_TO_STR);

				result.setDatingrequest(datingrequest);

				// JPE 20190514
				// Devuelve las unidades de la solicitud
				OrderUnitsDTO[] orderunits = orderdeliveryserver.getOrderUnitsRequestByDelivery(initParams.getDeliveryid());
				
				result.setOrderunits(orderunits);
			}

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}

	public AddDatingRequestResultDTO doAddDatingRequest(DatingRequestInitParamDTO initParams) {
		AddDatingRequestResultDTO result = new AddDatingRequestResultDTO();

		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		
		try {
			List<BaseResultsDTO> baseResultList = new ArrayList<BaseResultsDTO>();
			BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
			String error = "";

			Date requesteddate = DateConverterFactory2.StrToDate(initParams.getDatingrequest().getRequesteddate());

			// Obtener el lote asociado a la solicitud
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);

			// 28052009: Mariana Espinoza: No se puede solicitar cita para locales q no sean CD
			LocationW loc = locationserver.getById(delivery.getLocationid());

			if (!loc.getWarehouse()) {
				error = "No se puede solicitar cita ya que el local de entrega no es un Centro de Distribución";
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "LE", error, false));
			}

			// Validar que el estado del lote es "Predespacho" � "Cita Asignada"
			DeliveryStateTypeW dst_predels = deliverystatetypeserver.getByPropertyAsSingleResult("code", "PREDESPACHO");
			DeliveryStateTypeW dst_datrejs = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_RECHAZADA");
			DeliveryStateTypeW dst_datasgs = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DeliveryStateTypeW dst_datreqs = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");

			if (!delivery.getCurrentstatetypeid().equals(dst_predels.getId()) && !delivery.getCurrentstatetypeid().equals(dst_datrejs.getId()) && !delivery.getCurrentstatetypeid().equals(dst_datasgs.getId())) {

				error = "El lote no se encuentra en estado 'Predespacho', 'Cita Rechazada' o 'Cita Asignada'";
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "LE", error, false));
			}

			// Si el lote tiene asociada una solicitud previa, se borra
			DatingRequestW[] drArr = datingrequestserver.getByPropertyAsArray("delivery.id", delivery.getId());

			if (drArr != null && drArr.length > 0) {
				DatingRequestW dr = drArr[0];
				datingrequestserver.deleteElement(dr.getId());
			}

			// 20101026 Estibaliz: No se debe dejar solicitar cita para una fecha inferior a la vigencia de las OC's
			OrderW[] orders = orderserver.getByQueryAsArray("select oc from Order as oc, OrderDelivery as odl where odl.order = oc and odl.delivery.id = " + delivery.getId());
			Calendar calvalid = Calendar.getInstance();
			for (int i = 0; i < orders.length; i++) {
				// solo para las que no son VeV CD
				if (!orders[i].getVevcd()) {
					calvalid.setTime(orders[i].getValid());
					calvalid.set(Calendar.HOUR_OF_DAY, 0);
					calvalid.set(Calendar.MINUTE, 0);
					calvalid.set(Calendar.SECOND, 0);
					calvalid.set(Calendar.MILLISECOND, 0);
					if (calvalid.getTime().after(requesteddate)) {
						error = "La Orden " + orders[i].getNumber() + " a�n no entra en vigencia para el d�a seleccionado";
						baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "LE", error, false));
					}
				}
			}

			// Si hay errores hasta el momento, no se puede continuar
			if (baseResultList.size() > 0) {
				// Ordenar errores
				Arrays.sort(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]), comparator);
				result.setValidationerrors(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]));
				getSessionContext().setRollbackOnly();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6000");
			}

			DatingRequestDTO datingrequestDTO = initParams.getDatingrequest();

			// VALIDACION DATOS DE SOLICITUD CITA
			// Correo
			if (!isEmail(datingrequestDTO.getEmail().trim())) {// Debe ingresar un correo valido
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "L2203"));
			}
			// Camiones (Valor numerico)
			if (datingrequestDTO.getTrucks() <= 0) {// El numero de camiones debe ser mayor a cero
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "L2204"));
			}
			// Tiempo de descarga
			if (datingrequestDTO.getEstimatedboxes() <= 0) {// El numero de bultos debe ser mayor a cero
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "L2206"));
			}
			// Pallets
			if (datingrequestDTO.getEstimatedpallets() < 0) {// El numero de pallets debe ser mayor a cero
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "L2205"));
			}
			// Comentario
			if (datingrequestDTO.getComment() != null && datingrequestDTO.getComment().trim().length() > 200) {// El largo del comentario no puede exceder los 200 caracteres
				baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "L2214"));
			}

			DatingRequestW datingrequestW = new DatingRequestW();
			DateConverterFactory2.copyProperties(datingrequestDTO, datingrequestW, DateConverterMode.STR_TO_DATE);

			// Agregar solicitud
			datingrequestW.setNumber(datingrequestserver.getNextSequenceDatingRequestNumber());
			datingrequestW.setWhen(new Date());
			datingrequestW = datingrequestserver.addDatingRequest(datingrequestW);

			// Actualizar los despachos de orden, actualizando s�lo las unidades estimadas
			OrderDeliveryId pk = new OrderDeliveryId();
			pk.setDeliveryid(new Long(delivery.getId()));

			for (int i = 0; i < initParams.getOrderunits().length; i++) {
				OrderUnitsDTO orderunit = initParams.getOrderunits()[i];
				pk.setOrderid(orderunit.getOrderid().longValue());

				OrderDeliveryW orderdelivery = orderdeliveryserver.getById(pk);
				orderdelivery.setEstimatedunits(orderunit.getEstimatedunits());
				orderdelivery = orderdeliveryserver.updateOrderDelivery(orderdelivery);
			}

			DatingW[] dtArr = datingserver.getByPropertyAsArray("delivery.id", delivery.getId());

			// Si el lote ya tiene una cita asociada (Estado "Cita Asignada"), se borra.
			if (dtArr != null && dtArr.length > 0) {
				// Validar diferencia m�nima entre esta acción y la fecha programada de la cita a eliminar
				DatingW dating = dtArr[0];
				Calendar calnow = Calendar.getInstance();
				Calendar caldatingwhen = Calendar.getInstance();
				caldatingwhen.setTime(dating.getWhen());
				caldatingwhen.add(Calendar.DATE, -1);
				caldatingwhen.set(Calendar.HOUR_OF_DAY, 14);
				caldatingwhen.set(Calendar.MINUTE, 0);
				caldatingwhen.set(Calendar.SECOND, 0);
				caldatingwhen.set(Calendar.MILLISECOND, 0);
				if (calnow.after(caldatingwhen) || calnow.equals(caldatingwhen)) {
					error = "No se pudo ingresar la solicitud. S�lo se puede eliminar la cita hasta las 14:00 hrs. del d�a anterior asignado";
					baseResultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultsDTO(), "LE", error, false));

					Arrays.sort(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]), comparator);
					result.setValidationerrors(baseResultList.toArray(new BaseResultDTO[baseResultList.size()]));
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L6002");
				}

				Date now = new Date();
				Map<Long, Date> moduleMap = new HashMap<Long, Date>();
				ModuleW[] modules = moduleserver.getModules();
				Calendar calmod = null;
				calnow = Calendar.getInstance();
				calmod = Calendar.getInstance();
				for (int i = 0; i < modules.length; i++) {

					// Guardo el m�dulo con el dia de hoy
					calmod.setTime(modules[i].getStarts());
					calnow.setTime(now);

					calnow.set(Calendar.HOUR_OF_DAY, (calmod.get(Calendar.HOUR_OF_DAY)));
					calnow.set(Calendar.MINUTE, calmod.get(Calendar.MINUTE));
					calnow.set(Calendar.SECOND, calmod.get(Calendar.SECOND));
					calnow.set(Calendar.MILLISECOND, calmod.get(Calendar.MILLISECOND));
					moduleMap.put(modules[i].getId(), calnow.getTime());
				}

				// Agregar los detalles de cita
				Long modulepk = null;
				Date dmodule = null;
				Date arrive = null;

				ReserveDetailW[] reservedetails = reservedetailserver.getByPropertyAsArray("id.reserveid", dating.getId());

				for (int i = 0; i < reservedetails.length; i++) {
					ReserveDetailW detail = reservedetails[i];
					modulepk = detail.getModuleid();

					// Obtengo el minimo modulo para ver el horario de llegada
					dmodule = moduleMap.get(modulepk);
					if (arrive == null)
						arrive = dmodule;
					else if (dmodule.before(arrive))
						arrive = dmodule;
				}

				// ELIMINA DETALLES DE RESERVA
				reservedetailserver.deleteByProperty("id.reserveid", dating.getId());

				// ELIMINA CITA
				datingserver.deleteElement(dating.getId());

				// ENVIAR MAIL A CD INDICANDO REPROGRAMAción
				//VendorW vendor = vendorserver.getById(initParams.getVendorid());
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat dfHour = new SimpleDateFormat("HH:mm");

				String subject = "B2B Paris Chile: Solicitud de Nueva Cita";
				Mailer mailer = Mailer.getInstance();
				String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
				String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String[] mailreciever = RegionalLogisticConstants.getInstance().getREASSIGNMENT_MAIL();

				String msgtext = "Estimado(a) usuario(a):\n\nComunicamos a usted que el proveedor " + vendor.getName() + " ha solicitado una nueva cita para el despacho N° " + delivery.getNumber() + ".\nCon esta acción se ha eliminado la cita para el d�a: " + df.format(dating.getWhen()) + " a las "
						+ dfHour.format(arrive) + " hrs.\n\nAtte.\nParis.";
				mailer.sendMailBySession(mailreciever, mailreciever, null, mailsender, subject, msgtext, false, null, mailSession);
			}

			// Cambiar estado del lote a 'Cita Solicitada'
			delivery.setCurrentstatetypedate(new Date());
			delivery.setCurrentstatetypeid(dst_datreqs.getId());
			delivery = deliveryserver.updateDelivery(delivery);

			// Agregar el nuevo estado al historial
			DeliveryStateW deliverystate = new DeliveryStateW();
			deliverystate.setDeliveryid(delivery.getId());
			deliverystate.setDeliverystatetypeid(dst_datreqs.getId());
			deliverystate.setWhen(new Date());
			deliverystateserver.addDeliveryState(deliverystate);

			// ACTUALIZA REPORTE
			DeliveryReportDataDTO report = new DeliveryReportDataDTO();
			report.setDeliveryid(delivery.getId());
			report.setDeliverynumber(delivery.getNumber());
			report.setDeliverystatetypecode(dst_datreqs.getCode());
			report.setDeliverystatetypedesc(dst_datreqs.getName());
			report.setAction(dst_datreqs.getAction());
			result.setReport(report);

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}

	public DatingRequestReportResultDTO getDatingRequestReport(DatingRequestReportInitParamDTO initParams, String[] vendorcodes, boolean byFilter) {
		DatingRequestReportResultDTO result = new DatingRequestReportResultDTO();

		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L442");// no existe
		}
		location = locations[0];
		
		
		// Obtiene proveedores
		VendorW[] vendors = null;
		Long[] vendorids = null;
		try{
			PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
			properties[0] = new PropertyInfoDTO("v.rut", "codes", Arrays.asList(vendorcodes));
			List list = vendorserver.getByQuery("FROM Vendor v where v.rut IN (:codes)", properties);
			vendors = (VendorW[]) list.toArray(new VendorW[list.size()]);  
			
			vendorids = Stream.of(vendors).map(a -> a.getId()).toArray(Long[]::new);
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe 
		}
		

		Date sinceDate = Date.from(initParams.getSince().atZone(ZoneId.systemDefault()).toInstant());
		Date untilDate = Date.from(initParams.getUntil().atZone(ZoneId.systemDefault()).toInstant());
		
		try {
			DatingRequestReportDataDTO[] reportData = null;
			int total;

			reportData = datingrequestserver.getDatingRequestReport(location.getId(), initParams.getOrdertypeid(), initParams.getFlowtypeid(), vendorids, sinceDate, untilDate, initParams
					.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);

			if (reportData == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
			}

			result.setDatingrequestreport(reportData);

			if (byFilter) {
				total = datingrequestserver.getDatingRequestCountReport(location.getId(), initParams.getOrdertypeid(), initParams.getFlowtypeid(), vendorids, sinceDate, untilDate, initParams
						.getOrderby());

				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPagenumber());
				pageInfo.setRows(reportData.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				result.setPageinfo(pageInfo);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}

	public ImportedDatingRequestReportResultDTO getImportedDatingRequestReport(ImportedDatingRequestReportInitParamDTO initParams, String[] vendorcodes, boolean byFilter) {
		ImportedDatingRequestReportResultDTO result = new ImportedDatingRequestReportResultDTO();
		ImportedDatingRequestReportDataDTO[] reportData = null;
		int total = 0;
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L442");// no existe
		}
		location = locations[0];
		
		
		// Obtiene proveedores
		VendorW[] vendors = null;
		Long[] vendorids = null;
		try{
			PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
			properties[0] = new PropertyInfoDTO("v.rut", "codes", Arrays.asList(vendorcodes));
			List list = vendorserver.getByQuery("FROM Vendor v where v.rut IN (:codes)", properties);
			vendors = (VendorW[]) list.toArray(new VendorW[list.size()]);  
			
			vendorids = Stream.of(vendors).map(a -> a.getId()).toArray(Long[]::new);
		}catch(Exception e){
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe 
		}
		
		Date since = null;
		Date until = null;
		
		try {
			switch(initParams.getFiltertype()) {	
			case 1:
//				Calendar cal = null;
//				Date since = null;
//				Date until = null;
//				Locale locale = new Locale("es", "CL");
//				cal = Calendar.getInstance(locale);
//
//				since = DateConverterFactory2.StrToDate(initParams.getSince());
//				cal.setTime(since);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				cal.set(Calendar.MILLISECOND, 0);
//				since = cal.getTime();
//
//				until = DateConverterFactory2.StrToDate(initParams.getUntil());
//				cal.setTime(until);
//				cal.add(Calendar.DAY_OF_MONTH, 1);
//				cal.set(Calendar.HOUR_OF_DAY, 0);
//				cal.set(Calendar.MINUTE, 0);
//				cal.set(Calendar.SECOND, 0);
//				cal.set(Calendar.MILLISECOND, 0);
//				until = cal.getTime();
				
				
				since = Date.from(initParams.getSince().toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				until = Date.from(initParams.getUntil().toLocalDate().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

				// Validar que la fecha 'Desde' sea anterior o igual a la fecha 'Hasta'
				if(!until.after(since)){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L4500");
				}				
				break;
			case 2://B�squeda por contenedor
				if(initParams.getValue()== null || initParams.getValue().isEmpty() || initParams.getValue().length() > 20){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L4512");
				}			
				break;
			case 3:
			case 4:
				// Validar que el N°mero sea mayor que cero y con un m�ximo de 10 dígitos
				if(initParams.getNumber() <= 0 || initParams.getNumber() > 999999999){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L4501");
				}
			break;
			
			default:
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L4513");
			}
			
			reportData = null;

			reportData = datingrequestserver.getImportedDatingRequestReport(initParams.getValue(), 
													initParams.getNumber(), initParams.getFiltertype(), location.getId(), 
													initParams.getOrdertypeid(), initParams.getFlowtypeid(), vendorids, 
													since, 
													until,
													initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);

		if (reportData == null) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
		}
		
		try{
			DeliveryStateTypeW dss = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			for (ImportedDatingRequestReportDataDTO report : reportData) {
				PropertyInfoDTO[] props = new PropertyInfoDTO[2];
				props[0] = new PropertyInfoDTO("delivery.id", "delivery", report.getDeliveryid());
				props[1] = new PropertyInfoDTO("deliverystatetype.id", "deliverystatetype", dss.getId());
				List<DeliveryStateW> ds = deliverystateserver.getByProperties(props);
				
				if(ds != null && !ds.isEmpty())
					report.setScheduling(ds.size());
			}
		}catch(Exception e){
			
		}

		result.setDatingrequestreport(reportData);

		if (byFilter) {
			total = datingrequestserver.getImportedDatingRequestCountReport(initParams.getValue(), 
													initParams.getNumber(), initParams.getFiltertype(), location.getId(),
													initParams.getOrdertypeid(), initParams.getFlowtypeid(), vendorids, 
													since, 
													until,
													initParams.getOrderby());

			int rows = initParams.getRows();
			PageInfoDTO pageInfo = new PageInfoDTO();
			pageInfo.setPagenumber(initParams.getPagenumber());
			pageInfo.setRows(reportData.length);
			pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
			pageInfo.setTotalrows(total);
			result.setPageinfo(pageInfo);
		}

		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}
	
	public BaseResultDTO doDeleteOrdersFromImportedDatingRequest(ImportedDatingRequestOrdersDeleteInitParamDTO initParams) {
		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		List<Long> onList = new ArrayList<Long>();
		DatingRequestW datingRequest = null;
		DeliveryW delivery = null;
		
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
		}
		location = locations[0];
		
		try {
			// Validar que el proveedor sea internacional
//			VendorW vendor = null;
//			try {
//				vendor = vendorserver.getById(initParams.getVendorid());
//			
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1701");
//			}
			if (vendor.getDomestic()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2311");
			}
			
			// Validar que se haya seleccionado al menos una OC
			if (initParams.getOrderids() == null || initParams.getOrderids().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2304");
			}
			
			// Obtener la solicitud de cita
			try {
				datingRequest = datingrequestserver.getById(initParams.getDatingrequestid(), LockModeType.PESSIMISTIC_WRITE);
			
			} catch (Exception e1) {
				e1.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2305");
			}

			// Validar que la solicitud de cita pertenezca al proveedor
			PropertyInfoDTO prop1 = new PropertyInfoDTO("id", "id", datingRequest.getDeliveryid());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			DeliveryW[] deliveries = deliveryserver.getByPropertiesAsArray(prop1, prop2);
			if (deliveries == null || deliveries.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2306");
			}
			delivery = deliveries[0];
			
			// Validar que la solicitud de cita pertenezca al local
			if (!delivery.getLocationid().equals(location.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2307");
			}
			
			// Validar que las órdenes seleccionadas pertenezcan al despacho
			List<OrderDeliveryW> odList = orderdeliveryserver.getByProperty("delivery.id", delivery.getId());
			if (odList == null || odList.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2308");
			}
			List<Long> oList = new ArrayList<Long>();
			for (OrderDeliveryW od : odList) {
				oList.add(od.getOrderid());
			}
			for (Long orderid : initParams.getOrderids()) {
				if (!oList.contains(orderid)) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2309");
				}
				onList.add(orderserver.getById(orderid).getNumber());
			}
			
			// Validar que no se hayan seleccionado todas las OC de la solicitud
			if (initParams.getOrderids().length == oList.size()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L2310");
			}
			
			// Eliminar del despacho las órdenes de compra seleccionadas (pueden tener bultos asociados)
			bulkdetailserver.doDeleteByOrderDeliveries(delivery.getId(), initParams.getOrderids());

			BulkDTO[] bkArr = bulkserver.getBulksByOrderDeliveries(delivery.getId(), initParams.getOrderids());
			if (bkArr != null) {
				int deletedBoxes = 0;
				int deletedPallets = 0;
				for (BulkDTO bk : bkArr) {
					BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bk.getId());

					if (boxArr != null && boxArr.length > 0) {
						boxserver.deleteElement(bk.getId());
						deletedBoxes++;
					} else {
						palletserver.deleteElement(bk.getId());
						deletedPallets++;
					}
				}
				bulkserver.flush();
				
				// Recalcular el total de bultos de la solicitud de cita
				datingRequest.setEstimatedboxes(datingRequest.getEstimatedboxes() - deletedBoxes);
				datingRequest.setEstimatedpallets(datingRequest.getEstimatedpallets() - deletedPallets);
				datingRequest = datingrequestserver.updateDatingRequest(datingRequest);
			}			
						
			orderdeliverydetailserver.doDeleteByOrderDeliveries(delivery.getId(), initParams.getOrderids());
			orderdeliveryserver.doDeleteByOrderDeliveries(delivery.getId(), initParams.getOrderids());
			
			// Recalcular sus cantidades
			for (Long orderid : initParams.getOrderids()) {
				buyorderreportmanager.doCalculateTotalOfOrder(orderid);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		// Enviar un correo al usuario que gener� la solicitud de cita indicando la modificación
		String[] to = null;
		try {
			Mailer mailer = Mailer.getInstance();
			String session = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String from = RegionalLogisticConstants.getInstance().getMailSender();
			to = new String[] {datingRequest.getEmail()};
			String cc[] = null;
			String bcc[] = null;
							
			String subject = "B2B Paris: OC liberadas en Solicitud de cita para despacho " + delivery.getNumber();
											
			Date now = new Date();
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			String message =
				"<p>Estimado(a) usuario(a):</p>" + //
				"<p>Informamos que, con fecha " + sdfDate.format(now) + " a las " + sdfTime.format(now) + ", " + //
				"se han desvinculado de la <u>solicitud de cita</u> para despacho N° " + delivery.getNumber() + " " + //
				"las siguientes órdenes de compra:</p>" + //
				"<p>"; //
				
			for (Long on : onList) {
				message +=
					"&nbsp;&nbsp;&nbsp;* " + on + "<br>"; //
			}
			
			message +=
				"</p>" + // 
				"<p><i>Estas quedar�n liberadas para poder efectuar una nueva entrega, lo cual debe comprobar en reporte de órdenes de compra.</i></p><br>" + //
				"<p><strong><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></strong></p>" + //
				"<p>Atte.<br>" + //
				"B2B Paris</p>"; //
			
			mailer.sendMailBySession(to, cc, bcc, from, subject, message, true, null, session);
						
		} catch (Exception e1) {
			logger.info("No se pudo enviar el mail de OC liberadas en Solicitud de cita para despacho " + delivery.getNumber() + "!!!");
			for (String t : to) {
				logger.info("Mail destino: " + t);
			}
		}
		
		return resultDTO;		
	}
	
	public DatingRequestContainerReportResultDTO getDatingsByDatingRequestContainer(DatingRequestContainerInitParamDTO initParams) {
		DatingRequestContainerReportResultDTO resultDTO = new DatingRequestContainerReportResultDTO();

		try {
			DatingRequestContainerDTO[] containerDatings = datingserver.getDatingsByDatingRequestContainer(initParams.getDatingrequestid(), initParams.getOrderby());

			resultDTO.setContainerdatings(containerDatings);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public ImportedNonDeliveredOrderReportResultDTO getImportedNonDeliveredOrdersByDating(ImportedDatingOrderInitParamDTO initParams) {
		ImportedNonDeliveredOrderReportResultDTO resultDTO = new ImportedNonDeliveredOrderReportResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Validar que sea un proveedor 'Cencosud Importado'
			//VendorW vendor = vendorserver.getById(initParams.getVendorid());
			if (!vendor.getRut().equals("IMP")) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6015");
			}
			
			// Obtener las órdenes de compra incluidas en el despacho que a�n no se hayan entregado
			ImportedNonDeliveredOrderDTO[] nonDeliveredOrders = orderdeliveryserver.getNonDeliveredOrdersByDating(initParams.getDeliveryid());
			
			resultDTO.setNondeliveredorders(nonDeliveredOrders);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public BaseResultDTO doDeleteImportedDatingOrders(ImportedDatingOrderInitParamDTO initParams) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {
			// Validar que sea un proveedor 'Cencosud Importado'
			//VendorW vendor = vendorserver.getById(initParams.getVendorid());
			if (!vendor.getRut().equals("IMP")) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6015");
			}
			
			// Validar que el despacho asociado a la cita se encuentre en estado 'Agendada' o 'En Recepción'
			DeliveryStateTypeW dstScheduled = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			DeliveryStateTypeW dstInReception = deliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RECEPCION");
			
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			if (!delivery.getCurrentstatetypeid().equals(dstScheduled.getId()) &&
					!delivery.getCurrentstatetypeid().equals(dstInReception.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6016");
			}
			
			// Validar que se haya seleccionado al menos una orden a eliminar de la cita
			if (initParams.getOrderids() != null && initParams.getOrderids().length > 0) {
				PropertyInfoDTO prop1 = new PropertyInfoDTO("delivery.id", "deliveryid", initParams.getDeliveryid());
				PropertyInfoDTO prop2 = new PropertyInfoDTO("closed", "closed", false);
				OrderDeliveryW[] orderDeliveries = orderdeliveryserver.getByPropertiesAsArray(prop1, prop2);
				
				if (orderDeliveries == null || orderDeliveries.length <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La entrega no tiene órdenes que puedan ser desvinculadas", false);
				}
				
				// Validar si se est�n seleccionando todos los despachos de órdenes abiertos
				boolean all = false;
				if (initParams.getOrderids().length == orderDeliveries.length) {
					all = true;
				}				
				
				// Despacho 'Agendada'
				// Validar que no se hayan seleccionado todas las OC de la cita para eliminar
				if (delivery.getCurrentstatetypeid().equals(dstScheduled.getId()) && all) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6017");
				}
				
				HashMap<Long, OrderDeliveryW> odMap = new HashMap<Long, OrderDeliveryW>();
				for (OrderDeliveryW orderDelivery : orderDeliveries) {
					odMap.put(orderDelivery.getOrderid(), orderDelivery);
				}
								
				DatingW dating = datingserver.getById(initParams.getDatingid());
				
				// Obtener la solicitud de cita
				DatingRequestW datingRequest = datingrequestserver.getByPropertyAsSingleResult("delivery.id", delivery.getId());
								
				// Actualizar las cantidades disponibles a cero de aquellas OC seleccionadas y marcarlas como cerradas
				OrderW order = null;
				OrderDeliveryW orderDelivery = null;
				OrderDeliveryDetailW[] orderDeliveryDetails = null;
				for (Long orderid : initParams.getOrderids()) {
					order = orderserver.getById(orderid, LockModeType.PESSIMISTIC_WRITE);
					
					// Validar que el despacho de la orden est� abierto
					orderDelivery = odMap.get(order.getId());
					if (orderDelivery == null) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "La orden " + order.getNumber() + " no puede ser desvinculada, su despacho est� cerrado", false);
					}
					
					// Modificar las unidades disponibles del detalle de despacho a cero					
					prop1 = new PropertyInfoDTO("orderdelivery.delivery.id", "deliveryid", initParams.getDeliveryid());
					prop2 = new PropertyInfoDTO("orderdelivery.order.id", "orderid", order.getId());
					orderDeliveryDetails = orderdeliverydetailserver.getByPropertiesAsArray(prop1, prop2);
					for (OrderDeliveryDetailW orderDeliveryDetail : orderDeliveryDetails) {
						orderDeliveryDetail.setAvailableunits(0.0);
						
						orderdeliverydetailserver.updateOrderDeliveryDetail(orderDeliveryDetail);
					}
					orderdeliverydetailserver.flush();
					
					// Recalcular las cantidades de la orden
					buyorderreportmanager.doCalculateTotalOfOrder(order.getId());
					
					// Generar el ASN de eliminación correspondiente a la orden
					asntoxml.processDeliveryMessages(delivery, dating, new Long[]{order.getId()}, datingRequest.getRequesteddate(), 1, vendor.getCode().equalsIgnoreCase("IMP"), true);
					
					// Cerrar el despacho de la orden
					orderDelivery.setClosed(true);
					orderDelivery.setAsnimp(null);
					                
					orderdeliveryserver.updateOrderDelivery(orderDelivery);
					orderdeliveryserver.flush();
					
					// Eliminar los bultos y detalles asociados a la orden
					prop1 = new PropertyInfoDTO("id.deliveryid", "deliveryid", initParams.getDeliveryid());
					prop2 = new PropertyInfoDTO("id.orderid", "orderid", order.getId());
					bulkdetailserver.deleteByProperties(prop1, prop2);	
					
					prop1 = new PropertyInfoDTO("orderdelivery.delivery.id", "deliveryid", initParams.getDeliveryid());
					prop2 = new PropertyInfoDTO("orderdelivery.order.id", "orderid", order.getId());
					List<BulkW> bulkList = bulkserver.getByProperties(prop1, prop2);
					for (BulkW bulk : bulkList) {
						// Borrar las cajas o pallets seg�n el tipo de bulto
						List<BoxW> boxes = boxserver.getByProperty("id", bulk.getId());
						if (boxes != null && boxes.size() > 0) {
							boxserver.deleteElement(bulk.getId());
						}
						else {
							List<PalletW> pallets = palletserver.getByProperty("id", bulk.getId());
							if (pallets != null && pallets.size() > 0) {
								palletserver.deleteElement(bulk.getId());
							}
						}											
					}
					
					bulkserver.deleteByProperties(prop1, prop2);
				}
				
				String messagetype = "ASN";
				String numberStr = dating.getNumber().toString();
				String info = "ELIMINACION";
				String exception = "Despacho " + delivery.getNumber();

				// Registrar el resultado de carga de mensajes en un log particular
				logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
				
				Date now = new Date();
				
				// Despacho 'En Recepción'
				// Actualizar el despacho a 'Recepcionada' si se seleccionaron todas las OC de la cita para eliminar
				if (delivery.getCurrentstatetypeid().equals(dstInReception.getId()) && all) {
					DeliveryStateTypeW dstReceived = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
					
					delivery.setCurrentstatetypeid(dstReceived.getId());
					delivery.setCurrentstatetypedate(now);
					
					delivery = deliveryserver.updateDelivery(delivery);
					
					DeliveryStateW deliveryState = new DeliveryStateW();
					deliveryState.setDeliveryid(delivery.getId());
					deliveryState.setDeliverystatetypeid(dstReceived.getId());
					deliveryState.setWhen(now);
					
					deliverystateserver.addDeliveryState(deliveryState);
				}
				
				// Enviar correo al usuario que gener� la solicitud de cita, indicando de la modificación
				SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
				
				ImportedNonDeliveredOrderDTO[] nonDeliveredOrders = orderdeliveryserver.getNonDeliveredOrdersByDating(initParams.getDeliveryid());
				
				Mailer mailer = Mailer.getInstance();
				String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String from = RegionalLogisticConstants.getInstance().getMailSender();
				String[] to = {datingRequest.getEmail()};
				String[] cc = null;
				String[] bcc = null;
				
				String subject = "B2B Paris: ASN eliminados para despacho " + delivery.getNumber();
							
				String message =
					"<p>Estimado(a) usuario(a):</p>" + //
					"<p>Informamos que, con fecha " + sdfDate.format(now) + " a las " + sdfTime.format(now) + ", se han desvinculado " +//
					   "de la cita para la despacho N° " + delivery.getNumber() + " las siguientes órdenes de compra:</p>" + //
					"<table cellspacing='0' cellpadding='5' style='border:1px solid black; border-collapse:collapse; " + //
				    	"font-family:Arial, Helvetica, sans-serif; font-size:12px;'>" + //
				    "<tbody>" + //
				    	"<tr>" +  //
					    	"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N° Orden de Compra</strong></center></td>" + //
							"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>N° ASN</strong></center></td>" + //
							"<td align='center' bgcolor='#dbe3e4' style='border:1px solid black;'><center><strong>Proveedor</strong></center></td>" + //
						"</tr>"; //
				
				for (ImportedNonDeliveredOrderDTO nonDeliveredOrder : nonDeliveredOrders) {
					message +=
							"<tr>" + //
							"<td style='border:1px solid black;'>" + nonDeliveredOrder.getOrdernumber() + "</td>" + //
							"<td style='border:1px solid black;'>" + nonDeliveredOrder.getAsnimp() + "</td>" + //
							"<td style='border:1px solid black;'>" + nonDeliveredOrder.getOriginalvendorname() + "</td>" + //
						"</tr>"; //
				}
				
				message +=
					"</tbody>" + //
					"</table><br><br>" + //
					"<p>Estas quedar�n liberadas para poder efectuar nuevos despachos, lo cual debe comprobar en reporte de órdenes " + //
					    "de compra.</p><br>" + //
					"<p><b><i>Favor no responder este correo dado que es generado de manera autom�tica por el sistema B2B.</i></b></p><br>" + //
					"<p>Atte.<br>" + //
					"B2B Paris</p>"; //
					
				try {
					mailer.sendMailBySession(to, cc, bcc, from, subject, message, true, null, mailSession);
				} catch (Exception e) {
					logger.error("No se puede enviar el mail!");
					e.printStackTrace();
					for (int i = 0; i < to.length; i++)
						logger.error("Mail destino :" + to[i]);
				}
				
				logger.info("órdenes desvinculadas de cita: " + dating.getNumber());				
			}

		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	public AddDatingAndDetailsResultDTO doAddDatingAndDetails(AddDatingAndDetailsInitParamDTO initParams) {
		AddDatingAndDetailsResultDTO resultDTO = new AddDatingAndDetailsResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
		}
		location = locations[0];
		
		try {
			// Validar que los detalles de la cita hayan sido informados
			if (initParams.getDetails() == null || initParams.getDetails().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No se puede crear una cita sin detalles", false);
			}
			
			Date now = new Date();
			Date when = Date.from(initParams.getWhen().atZone( ZoneId.systemDefault()).toInstant());
			
			// Fecha actual
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date today = cal.getTime();

			// Fecha deseada
			Calendar calr = Calendar.getInstance();
			calr.setTime(when);
			calr.set(Calendar.HOUR_OF_DAY, 0);
			calr.set(Calendar.MINUTE, 0);
			calr.set(Calendar.SECOND, 0);
			calr.set(Calendar.MILLISECOND, 0);
			Date requesteddate = calr.getTime();

			// Validar que la fecha solicitada no sea anterior al día de hoy
			if (requesteddate.before(today)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4008");
			}
			
			// Obtener el despacho
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);

			// Validar que el despacho se encuentre en estado 'Cita Solicitada'
			DeliveryStateTypeW citSolSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			if (!delivery.getCurrentstatetypeid().equals(citSolSt.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
												"La solicitud seleccionada ha cambiado de estado. Favor actualice el listado.", false);
			}

			// Validar que el despacho no tenga una cita asociada
			List<DatingW> datingList = datingserver.getByProperty("delivery.id", initParams.getDeliveryid());
			if (!datingList.isEmpty()) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
												"El despacho " + delivery.getNumber() + " ya tiene cita asignada.", false);
			}
			
			// Tipo de orden 'Venta en Verde'
			OrderTypeW vevcdot = ordertypeserver.getByPropertyAsSingleResult("code", "V");
			
			// Obtener el proveedor
			//VendorW vendor = vendorserver.getById(initParams.getVendorid());

			// Obtener las entregas de órdenes
			OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("delivery.id", initParams.getDeliveryid());
			
			// Obtener las órdenes asociadas al despacho
			OrderW[] orders = orderserver.getOrdersByDelivery(initParams.getDeliveryid());
			HashMap<Long, OrderW> oMap = new HashMap<Long, OrderW>();
			for (OrderW order : orders) {
				oMap.put(order.getId(), order);
			}
			
			HashMap<Long, OrderDeliveryW> odMap = new HashMap<Long, OrderDeliveryW>();
			OrderW order = null;
			Date expiration = null;
			for (OrderDeliveryW orderdelivery : orderdeliveries) {
				// Actualizar la entrega con el N°mero de cita
				orderdelivery.setAsnimp(datingserver.getNextSequenceDatingNumber());
				orderdeliveryserver.updateOrderDelivery(orderdelivery);
				
				odMap.put(orderdelivery.getOrderid(), orderdelivery);
				
				order = oMap.get(orderdelivery.getOrderid());
				
				// IRA 20080117
				// Si el proveedor es nacional
				if (vendor.getDomestic()) {
					// Validar que las órdenes asociadas al despacho no est�n vencidas
					expiration = ClassUtilities.setFinalDay(order.getExpiration());
					if (expiration.compareTo(now) < 0) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
												"No se puede crear la cita porque existen órdenes vencidas asociadas al despacho", false);
					}
					
					
					// Si la orden no es de tipo 'Venta en Verde'
					if (!order.getOrdertypeid().equals(vevcdot.getId())) {
						// Validar que las órdenes permanezcan vigentes en la fecha deseada de la cita
						if (order.getValid().after(when)) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
												"No se puede crear la cita porque existen órdenes que no est�n vigentes para esa fecha", false);
						}
						
						// Validar que las órdenes no se hayan vencido en la fecha deseada de la cita
						if (order.getExpiration().before(when)) {
							return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1",
												"No se puede crear la cita porque existen órdenes que estar�n vencidas para esa fecha", false);
						}
					}
				}
			}

			// Crear la cita
			DatingW dating = new DatingW();
			dating.setDeliveryid(delivery.getId());
			dating.setVendorid(delivery.getVendorid());
			dating.setComment(initParams.getComment());
			dating.setLocationid(location.getId());
			dating.setWhen(when);
			dating.setNumber(datingserver.getNextSequenceDatingNumber());

			dating = datingserver.addDating(dating);

			// Obtener los m�dulos de la agenda
			ModuleW[] modules = moduleserver.getModules();
			Map<Long, Date> startsMap = new HashMap<Long, Date>();
			for (ModuleW module : modules) {
				startsMap.put(module.getId(), module.getStarts());
			}

			// Agregar los detalles de cita
			List<ReserveDetailW> rsDetailList = new ArrayList<ReserveDetailW>();
			ReserveDetailW detailW = new ReserveDetailW();
			ReserveDetailW[] busyDetail = null;
			Date moduleTime = null;
			Date arrive = null;
			for (ReserveDetailDTO detail : initParams.getDetails()) {
				// Validar que la celda no est� ocupada (por concurrencia)
				busyDetail = reservedetailserver.getDatingDetailsByDateLocationDockAndModule(dating.getWhen(), dating.getLocationid(), detail.getDockid(), detail.getModuleid());
				if (busyDetail != null && busyDetail.length > 0) {
					getSessionContext().setRollbackOnly();
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4102");
				}

				detail.setReserveid(dating.getId());

				// Obtener el horario de llegada
				moduleTime = startsMap.get(detail.getModuleid());
				if (arrive == null) {
					arrive = moduleTime;
				}
				else if (moduleTime.before(arrive)) {
					arrive = moduleTime;
				}

				// Agregar el detalle de la reserva
				DateConverterFactory2.copyProperties(detail, detailW);
				detailW.setWhen(dating.getWhen());
				reservedetailserver.addReserveDetail(detailW);
				rsDetailList.add(detailW);
			}
			reservedetailserver.flush();
			
			// Obtener el estado en que quedar� el despacho (depende del tipo de proveedor)
			// Nacional: Cita Asignada
			// Internacional: Agendada
			String statetypecode = vendor.getDomestic() ? "CITA_ASIGNADA" : "AGENDADA";
			DeliveryStateTypeW delstatetype = deliverystatetypeserver.getByPropertyAsSingleResult("code", statetypecode);
			
			// Actualizar el despacho
			delivery.setCurrentstatetypedate(now);
			delivery.setCurrentstatetypeid(delstatetype.getId());

			delivery = deliveryserver.updateDelivery(delivery);

			// Agregar el nuevo estado al historial
			DeliveryStateW deliverystate = new DeliveryStateW();
			deliverystate.setDeliveryid(delivery.getId());
			deliverystate.setDeliverystatetypeid(delstatetype.getId());
			deliverystate.setWhen(now);
			deliverystateserver.addDeliveryState(deliverystate);

			logger.info("Cita Id " + dating.getId() + " creada para lote de despacho " + delivery.getNumber());

			// Obtener el local de entrega
			LocationW deliveryLocation = locationserver.getById(location.getId());
			
			// Obtener la solicitud de cita
			List<DatingRequestW> drList = datingrequestserver.getByProperty("delivery.id", delivery.getId());
			DatingRequestW datingrequest = drList.get(0);
			
			// Enviar correo
			String[] mailReceivers = new String[1];
			try {
				Mailer mailer = Mailer.getInstance();
				String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
				String mailSender = RegionalLogisticConstants.getInstance().getMailSender();
				mailReceivers[0] = datingrequest.getEmail();
				String subject = "B2B Paris. Asignación de cita para despacho Nro. " + delivery.getNumber();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
				
				String vendorMsg =
					(vendor.getDomestic()
						?
							"Favor proceda a subir su Packing List y tener en consideración:<br/>" + //
							"<ul style=\"list-style-type:disc\">" + //
							"<li>La carga del PL debe ser generada como mínimo 24 hrs antes del despacho.</li>" + //
							"<li>El archivo de PL no puede superar las 2000 líneas.</li>" + //
							"<li>Transporte debe presentarse en CD como mínimo 25 minutos antes de la hora asignada.</li>" + //
							"<li>Si el transporte no es apto para sello (Furgón, van, auto, moto, etc.) debe gestionar con " + //
								"Depto. Log. prov nacionales o Logística Proveedores venta en verde una autorización vía mail " + //
								"según corresponda.</li>" + //
							"<li>Cada factura debe ingresar totalizada (sumar total unidades y bultos).</li>" + //
							"<li>Cada factura debe indicar el N° de cita.</li></ul>"
						:
							"El detalle de la importación es el siguiente:<br/> " + //
							" - Nro. Importación: " + (delivery.getImp() == null ? "" : delivery.getImp()) + "<br/> " + //
							" - Nro. Guía: " + delivery.getRefdocument() + "<br/> " + //
							" - Nro. Contenedor: " + (delivery.getContainer() == null ? "" : delivery.getContainer()) + "\n\n");

				String comment =
					(initParams.getComment().trim().equals("")
						?
							""
						:
							"Desde " + deliveryLocation.getName() + " se ha ingresado el siguiente comentario:<br/><br/><i>" + //
							initParams.getComment() + "</i>"); //
				
				String text =
					"Estimado(a) usuario(a):<br/><br/>" + //
					"Comunicamos a usted que se ha agendado cita al despacho Nro. " + delivery.getNumber() + //
					" para el día " + sdf.format(dating.getWhen()) + ", a las " + sdfHour.format(arrive) + ".<br/><br/>" + //
					vendorMsg + "<br/><br/>" + //
					comment + "<br/><br/><br/>" + //
					"<b>Cualquier incumplimiento que se genere será imputable al proveedor, y de ninguna manera quedará exento de multas, " + //
					"cobros, evaluación de desempeño o KPI que se apliquen según tipo de OC que entrega al Centro de Distribución.</b><br/><br/>" + //
					"Atte. B2B Paris"; //
				
				mailer.sendMailBySession(mailReceivers, null, null, mailSender, subject, text, true, null, mailSession);

			} catch (Exception e) {
				logger.info("No se puede enviar el mail!");
				for (String mailReceiver : mailReceivers)
					logger.info("Mail destino :" + mailReceiver);
			}

			// Si el proveedor es internacional enviar la interfaz ASN
			if (!vendor.getDomestic()) {
				asntoxml.processDeliveryMessages(delivery, dating, (Long[])oMap.keySet().toArray(new Long[oMap.keySet().size()]), datingrequest.getRequesteddate(), 0, vendor.getCode().equalsIgnoreCase("IMP"), true);
								
				String messageType = "ASN";
				String numberStr = dating.getNumber().toString();
				String info = "CREACION";
				String exception = "Despacho " + delivery.getNumber();

				// Registrar el resultado de carga de mensajes en un log particular
				logger_ack.info(",\"" + messageType + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
			}

			// Calcular el total de los bultos del despacho
			deliveryreportmanager.doCalculateTotalBulkDetailOfDelivery(delivery.getId());

		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	public BaseResultDTO doRejectDatingRequest(RejectDatingRequestInitParamDTO initParams) {
		BaseResultDTO result = new BaseResultDTO();
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L442");// no existe
		}
		location = locations[0];
		
		try {

			Date now = new Date();

			// VERIFICA QUE SOLICITUD DE CITA EXISTE
			DatingRequestW[] datingrequestArr = datingrequestserver.getByPropertyAsArray("delivery.id", initParams.getDeliveryid());

			// OBTIENE LA ENTREGA
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());

			// Se buscan los tipos de entrega
			DeliveryStateTypeW citsolSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DeliveryStateTypeW citRech = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_RECHAZADA");

			// LA ENTREGA DEBE ESTAR EN ESTADO CITA SOLICITADA
			if (!delivery.getCurrentstatetypeid().equals(citsolSt.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L2301");
			}

			// BORRA LA SOLICITUD
			datingrequestserver.deleteElement(datingrequestArr[0].getId());

			OrderDeliveryW[] odlArr = orderdeliveryserver.getByPropertyAsArray("id.deliveryid", initParams.getDeliveryid());

			for (OrderDeliveryW odl : odlArr) {
				// ABRE LOS ORDER-DELIVERY
				odl.setClosed(false);
				orderdeliveryserver.updateOrderDelivery(odl);
			}

			// CAMBIA ESTADO DE LA CITA A CITA RECHAZADA
			delivery.setCurrentstatetypedate(now);
			delivery.setCurrentstatetypeid(citRech.getId());

			delivery = deliveryserver.updateDelivery(delivery);

			// AGREGA ESTADO
			DeliveryStateW state = new DeliveryStateW();
			state.setDeliveryid(delivery.getId());
			state.setDeliverystatetypeid(citRech.getId());
			state.setWhen(now);

			state = deliverystateserver.addDeliveryState(state);

			// BUSCA LOCAL DESDE DONDE SE REALIZO LA ACción
			//LocationW location = locationserver.getById(location.getId());

			// ENVIA MAIL DE RECHAZO
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String[] mailreciever = new String[1];
			String subject = "B2B Paris: Cancelación de cita " + datingrequestArr[0].getNumber() + "(Despacho Nro. " + delivery.getNumber() + ")";
			Mailer mailer = Mailer.getInstance();
			mailreciever[0] = datingrequestArr[0].getEmail();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();

			String msgtext = "Estimado(a) Usuario(a): \n\n" + "Comunicamos a usted que desde " + location.getName() + " se ha cancelado la solicitud de cita Nro. " + datingrequestArr[0].getNumber() + " (Despacho Nro. " + delivery.getNumber() + "), para el día "
					+ sdf.format(datingrequestArr[0].getRequesteddate()) + ".\nSe ha indicado el siguiente comentario:\n\n" + initParams.getReason() + "\n\nAtte.\nB2B Paris.";
			try {
				mailer.sendMailBySession(mailreciever, null, null, mailsender, subject, msgtext, false, null, mailSession);
			} catch (Exception e) {
				logger.info("No se puede enviar el mail !");
				e.printStackTrace();
				for (int i = 0; i < mailreciever.length; i++)
					logger.info("Mail destino :" + mailreciever[i]);
			}
			logger.info("Solicitud rechazada para el despacho Id: " + delivery.getId());
			return result;
		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public BaseResultDTO doCancelDatingByVendor(DoCancelDatingInitParamDTO initParams) {
		BaseResultDTO result = new BaseResultDTO();
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			DeliveryStateTypeW dst_predel = deliverystatetypeserver.getByPropertyAsSingleResult("code", "PREDESPACHO");
			DeliveryStateTypeW dst_datasg = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");

			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());

			DatingW[] dtArr = datingserver.getByPropertyAsArray("delivery.id", delivery.getId());

			if (dtArr == null || dtArr.length == 0)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "No existe cita para el despacho", false);

			DatingW dating = dtArr[0];

			DatingRequestW[] drArr = datingrequestserver.getByPropertyAsArray("delivery.id", delivery.getId());

			if (drArr == null || drArr.length == 0)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "No existe solicitud de cita para el despacho", false);

			DatingRequestW datingrequest = drArr[0];

			// Verificar estado
			if (!delivery.getCurrentstatetypeid().equals(dst_datasg.getId()))
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L2302");

			// Validar diferencia m�nima entre esta acción y la fecha programada de la cita a eliminar
			Calendar calnow = Calendar.getInstance();
			Calendar caldatingwhen = Calendar.getInstance();
			caldatingwhen.setTime(dating.getWhen());
			caldatingwhen.add(Calendar.DATE, -1);
			caldatingwhen.set(Calendar.HOUR_OF_DAY, 14);
			caldatingwhen.set(Calendar.MINUTE, 0);
			caldatingwhen.set(Calendar.SECOND, 0);
			caldatingwhen.set(Calendar.MILLISECOND, 0);
			if (calnow.after(caldatingwhen) || calnow.equals(caldatingwhen))
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "S�lo se puede eliminar la cita hasta las 14:00 hrs. del d�a anterior asignado", false);

			Date now = new Date();
			Map<Long, Date> moduleMap = new HashMap<Long, Date>();
			ModuleW[] modules = moduleserver.getModules();
			Calendar calmod = null;
			calnow = Calendar.getInstance();
			calmod = Calendar.getInstance();
			for (int i = 0; i < modules.length; i++) {

				// Guardo el m�dulo con el dia de hoy
				calmod.setTime(modules[i].getStarts());
				calnow.setTime(now);

				calnow.set(Calendar.HOUR_OF_DAY, (calmod.get(Calendar.HOUR_OF_DAY)));
				calnow.set(Calendar.MINUTE, calmod.get(Calendar.MINUTE));
				calnow.set(Calendar.SECOND, calmod.get(Calendar.SECOND));
				calnow.set(Calendar.MILLISECOND, calmod.get(Calendar.MILLISECOND));
				moduleMap.put(modules[i].getId(), calnow.getTime());
			}

			// Agregar los detalles de cita
			Long modulepk = null;
			Date dmodule = null;
			Date arrive = null;

			ReserveDetailW[] reservedetails = reservedetailserver.getByPropertyAsArray("id.reserveid", dating.getId());

			for (int i = 0; i < reservedetails.length; i++) {
				ReserveDetailW detail = reservedetails[i];
				modulepk = detail.getModuleid();

				// Obtengo el minimo modulo para ver el horario de llegada
				dmodule = moduleMap.get(modulepk);
				if (arrive == null)
					arrive = dmodule;
				else if (dmodule.before(arrive))
					arrive = dmodule;
			}

			// ENVIAR MAIL A CD INDICANDO REPROGRAMAción
			//VendorW vendor = vendorserver.getById(initParams.getVendorid());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
			String subject = "B2B Paris: Cancelación de Cita";
			Mailer mailer = Mailer.getInstance();
			String[] sendTo = RegionalLogisticConstants.getInstance().getREASSIGNMENT_MAIL();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();

			String msgtext = "Estimado(a) usuario(a):\n\nComunicamos a usted que el proveedor " + vendor.getName() + " ha cancelado la cita para el despacho N° " + delivery.getNumber() + ".\n" + "Con esta acción se ha eliminado la cita para el d�a " + sdf.format(dating.getWhen()) + " a las "
					+ sdfHour.format(arrive) + " hrs.\n\nAtte.\nB2B Paris.";
			try {
				mailer.sendMailBySession(sendTo, null, null, mailsender, subject, msgtext, false, null, mailSession);
			} catch (Exception e) {
				logger.info("No se puede enviar el mail !");
				e.printStackTrace();
				for (int i = 0; i < sendTo.length; i++)
					logger.info("Mail destino :" + sendTo[i]);
			}

			// ELIMINA DETALLES DE RESERVA
			reservedetailserver.deleteByProperty("id.reserveid", dating.getId());

			// ELIMINA CITA
			datingserver.deleteElement(dating.getId());

			// ELIMINA SOLICITUD
			datingrequestserver.deleteElement(datingrequest.getId());

			// Actualizar el lote
			delivery.setCurrentstatetypedate(now);
			delivery.setCurrentstatetypeid(dst_predel.getId());
			delivery = deliveryserver.updateDelivery(delivery);

			// Agregar el nuevo estado al historial
			DeliveryStateW deliverystate = new DeliveryStateW();
			deliverystate.setDeliveryid(delivery.getId());
			deliverystate.setDeliverystatetypeid(dst_predel.getId());
			deliverystate.setWhen(now);
			deliverystateserver.addDeliveryState(deliverystate);

			return result;
		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
	}

	public BaseResultDTO doDeleteDating(DeleteDatingInitParamDTO initParamW) {
		BaseResultDTO resultW = new BaseResultDTO();

		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", initParamW.getLocationcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L442");// no existe
		}
		location = locations[0];
		
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			boolean agend = false;

			List<DatingW> datings = datingserver.getByProperty("delivery.id", initParamW.getDeliveryid());
			DatingW dating = datings.get(0);
			
			VendorW vendor = vendorserver.getById(dating.getVendorid());

			// LOCAL
			//LocationW location = locationserver.getById(location.getId());

			DeliveryW delivery = deliveryserver.getById(dating.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			DeliveryStateTypeW citSolSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_SOLICITADA");
			DeliveryStateTypeW citAsigSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DeliveryStateTypeW agendSt = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");

			// JPE 20200127
			// Solo se pueden eliminar citas en estado 'Cita Asignada' y 'Agendada'
			if (!delivery.getCurrentstatetypeid().equals(citAsigSt.getId()) &&
					!delivery.getCurrentstatetypeid().equals(agendSt.getId())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L4001");
			}

			// Obtener las órdenes asociadas al despacho
			OrderW[] orders = orderserver.getOrdersByDelivery(initParamW.getDeliveryid());
			HashMap<Long, OrderW> oMap = new HashMap<Long, OrderW>();
			for (OrderW order : orders) {
				oMap.put(order.getId(), order);
			}
						
			agend = delivery.getCurrentstatetypeid().equals(agendSt.getId());
			
			// ENVIA ASN
			if (agend) {
				// Obtener las entregas de órdenes
				OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("delivery.id", initParamW.getDeliveryid());
				HashMap<Long, OrderDeliveryW> odMap = new HashMap<Long, OrderDeliveryW>();
				for (OrderDeliveryW orderdelivery : orderdeliveries) {
					odMap.put(orderdelivery.getOrderid(), orderdelivery);
				}
				
				// Obtener la solicitud de cita
				List<DatingRequestW> drList = datingrequestserver.getByProperty("delivery.id", delivery.getId());
				DatingRequestW datingrequest = drList.get(0);
				
				asntoxml.processDeliveryMessages(delivery, dating, (Long[])oMap.keySet().toArray(new Long[oMap.keySet().size()]), datingrequest.getRequesteddate(), 1, vendor.getCode().equalsIgnoreCase("IMP"), agend);
				
				String messagetype = "ASN";
				String numberStr = dating.getNumber().toString();
				String info = "ELIMINACION";
				String exception = "Despacho " + delivery.getNumber();

				// Se registra el resultado de carga de mensajes en un log particular
				logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
			}
			
			// Si la cita est� agendada, con factura cargada o con factura despachada se deben eliminar los elementos
			// correspondientes

			if (agend && vendor.getDomestic()) {
				// JPE 20190904
				// Obtener las facturas asociadas a los detalles de bultos
				Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(initParamW.getDeliveryid());

				// ELIMINA LOS DETALLES DE BULTO
				bulkdetailserver.deleteByProperty("id.deliveryid", initParamW.getDeliveryid());
				
				// JPE 20190904
				// Eliminar las facturas asociadas a los detalles de bultos
				if (taxdocumentids != null && taxdocumentids.length > 0) {
					taxdocumentserver.deleteByIds(taxdocumentids);
				}
				
				// ANTIGUO
				// SE DEBEN BUSCAR LOS BULTOS POR DESPACHO
				BulkW[] bulks = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", initParamW.getDeliveryid());

				// ELIMINA LOS BULTOS
				for (int i = 0; i < bulks.length; i++) {
					BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bulks[i].getId());

					if (boxArr != null && boxArr.length > 0) {
						boxserver.deleteElement(bulks[i].getId());
					} else {
						palletserver.deleteElement(bulks[i].getId());
					}
				}

				bulkserver.flush();
			}

			// ELIMINA DETALLES DE CITA
			reservedetailserver.deleteByProperty("id.reserveid", dating.getId());

			// ELIMINA LA CITA
			datingserver.deleteElement(dating.getId());

			// SE ELIMINAN LOS DETALLES DE DESPACHO
			// orderdeliverydetailserver.deleteByProperty("id.deliveryid", initParamW.getDeliveryid());

			// ACTUALIZA EL DESPACHO
			delivery.setCurrentstatetypedate(now);
			delivery.setCurrentstatetypeid(citSolSt.getId());
			delivery = deliveryserver.updateDelivery(delivery);

			// AGREGA NUEVO ESTADO
			DeliveryStateW deliverystate = new DeliveryStateW();
			deliverystate.setDeliveryid(delivery.getId());
			deliverystate.setDeliverystatetypeid(citSolSt.getId());
			deliverystate.setWhen(now);
			deliverystateserver.addDeliveryState(deliverystate);

			// SE CALCULAN LOS BULTOS
			deliveryreportmanager.doCalculateTotalBulkDetailOfDelivery(delivery.getId());

			// SE CALCULAN LOS TOTALES POR OC
			for (Long ocid : oMap.keySet()) {
				buyorderreportmanager.doCalculateTotalOfOrder(ocid);
			}
			
			//Se eliminan los N°mero de cita a los orderdelivery luego de enviar el asn
			OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByQueryAsArray("select odl from OrderDelivery as odl where odl.delivery.id = " + delivery.getId());
			for (OrderDeliveryW orderDeliveryW : orderdeliveries) {
				orderDeliveryW.setAsnimp(null);
				orderdeliveryserver.updateIdentifiable(orderDeliveryW);
			}

			// Enviar mail con la causa del rechazo
			DatingRequestW[] datingrequests = datingrequestserver.getByPropertyAsArray("delivery.id", delivery.getId());
			DatingRequestW datingrequest = datingrequests[0];

			// Enviar mail con la causa del rechazo
			String[] mailreciever = new String[1];
			String[] mailcc = RegionalLogisticConstants.getInstance().getDELETEDATING_MAIL();
			String subject = "B2B Paris: Cancelación de cita Nro. " + dating.getNumber() + " (Despacho " + delivery.getNumber() + ")";
			Mailer mailer = Mailer.getInstance();
			mailreciever[0] = datingrequest.getEmail();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();

			String msgtext = "Estimado(a) usuario(a): \n\n" + "Comunicamos a usted que la cita Nro. " + dating.getNumber() + " (Despacho " + delivery.getNumber() + ") para el d�a " + sdf.format(dating.getWhen()) + " en " + location.getName() + " ha sido eliminada. "
					+ "El motivo del rechazo es el siguiente: \n\n" + initParamW.getReason() + (agend ? "\n\nEsta acción implica que su Packing List ingresado ha sido eliminado del sistema para que pueda volver a utilizarlos en una nueva cita." : "") + "\n\nAtte. B2B Paris.";

			mailer.sendMailBySession(mailreciever, mailcc, null, mailsender, subject, msgtext, false, null, mailSession);
			logger.info("Cita eliminada, id: " + dating.getId() + ", para la entrega id: " + delivery.getId());
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "0", "Cita eliminada exitosamente", false);

		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public BaseResultDTO doMarkDatingAsNotAttended(DatingAsNotAttendedInitParamDTO initParams) {
		BaseResultDTO resultW = new BaseResultDTO();
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			boolean agend = false;

			DeliveryStateTypeW dstCitAsig = deliverystatetypeserver.getByPropertyAsSingleResult("code", "CITA_ASIGNADA");
			DeliveryStateTypeW dstInAgend = deliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_AGENDAMIENTO");
			DeliveryStateTypeW dstAgend = deliverystatetypeserver.getByPropertyAsSingleResult("code", "AGENDADA");
			DeliveryStateTypeW dstNonAttend = deliverystatetypeserver.getByPropertyAsSingleResult("code", "NO_ASISTE");

			// Obtener el error "No Asiste (NPC)"
			ReceptionErrorW[] receptionerrors = receptionerrorserver.getByPropertyAsArray("code", "NPC");
			if (receptionerrors == null || receptionerrors.length == 0) {
				throw new OperationFailedException("Error de evaluación con el código NPC no fue encontrado");
			}
			ReceptionErrorW receptionerror = receptionerrors[0];
			
			// Cita
			List<DatingW> datings = datingserver.getByProperty("delivery.id", initParams.getDeliveryid());
			DatingW dating = datings.get(0);

			VendorW vendor = vendorserver.getById(dating.getVendorid());
			
			// Se obtiene el despacho asociado a la cita
			DeliveryW delivery = deliveryserver.getById(initParams.getDeliveryid());

			// Obtener las órdenes asociadas al despacho
			OrderW[] orders = orderserver.getOrdersByDelivery(initParams.getDeliveryid());
			HashMap<Long, OrderW> oMap = new HashMap<Long, OrderW>();
			for (OrderW order : orders) {
				oMap.put(order.getId(), order);
			}
			
			// Obtener la solicitud de cita
			List<DatingRequestW> drList = datingrequestserver.getByProperty("delivery.id", delivery.getId());
			DatingRequestW datingrequest = drList.get(0);
					
			// Se valida que el estado actual del despacho sea Cita Asignada, En Agendamiento o Agendada
			if (delivery.getCurrentstatetypeid().equals(dstCitAsig.getId()) || delivery.getCurrentstatetypeid().equals(dstInAgend.getId()) || delivery.getCurrentstatetypeid().equals(dstAgend.getId())) {
				BulkW[] bulks = null;
				agend = delivery.getCurrentstatetypeid().equals(dstAgend.getId());

				if (agend) {
					// ENVIA ASN
					asntoxml.processDeliveryMessages(delivery, dating, (Long[])oMap.keySet().toArray(new Long[oMap.keySet().size()]), datingrequest.getRequesteddate(), 1, vendor.getCode().equalsIgnoreCase("IMP"), agend);
					
					String messagetype = "ASN";
					String numberStr = dating.getNumber().toString();
					String info = "ELIMINACION";
					String exception = "Despacho " + delivery.getNumber();

					// Se registra el resultado de carga de mensajes en un log particular
					logger_ack.info(",\"" + messagetype + "\",\"" + numberStr + "\",\"" + info + "\",\"" + exception + "\"");
				}
								
				// JPE 20190904
				// Obtener las facturas asociadas a los detalles de bultos
				Long[] taxdocumentids = bulkdetailserver.getTaxDocumentIdsByDelivery(initParams.getDeliveryid());

				// Se borran los BulkDetails
				bulkdetailserver.deleteByProperty("id.deliveryid", initParams.getDeliveryid());
				
				// JPE 20190904
				// Eliminar las facturas asociadas a los detalles de bultos
				if (taxdocumentids != null && taxdocumentids.length > 0) {
					taxdocumentserver.deleteByIds(taxdocumentids);
				}
				
				// SE ELIMINAN BULTOS ASOCIADOS AL DESPACHO
				bulks = bulkserver.getByPropertyAsArray("orderdelivery.delivery.id", initParams.getDeliveryid());

				// ELIMINA LOS DETALLES DE BULTO
				for (int i = 0; i < bulks.length; i++) {
					BoxW[] boxArr = boxserver.getByPropertyAsArray("id", bulks[i].getId());

					if (boxArr != null && boxArr.length > 0) {
						boxserver.deleteElement(bulks[i].getId());
					} else {
						palletserver.deleteElement(bulks[i].getId());
					}
				}
				bulkserver.flush();
				
			}
			else {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L4103");
			}
			
			//Se revisa que no tenga evaluacion previa. si es as� se eliminan (2015-12-22 JMA)
			ReceptionEvaluationW[] evaluationPrev = receptionevaluationserver.getByPropertyAsArray("dating.id", dating.getId());
			if (evaluationPrev != null && evaluationPrev.length > 0){
				for (int i = 0; i < evaluationPrev.length; i++) {
					evaluationdetailserver.deleteByProperty("receptionevaluation.id", evaluationPrev[i].getId());
					receptionevaluationserver.deleteElement(evaluationPrev[i].getId());
				}
			}
			
			// Agregar la evaluación con su detalle de error "No Asiste"
			ReceptionEvaluationW evaluation = new ReceptionEvaluationW();
			evaluation.setDatingid(dating.getId());
			evaluation.setAutogenerated(true);
			evaluation.setComments("Proveedor no asiste a la cita");
			evaluation.setWhen(new Date());
			evaluation.setWho("Sistema");
			evaluation.setScore(0d);
			evaluation.setAutogenerated(true);
			evaluation = receptionevaluationserver.addReceptionEvaluation(evaluation);

			EvaluationDetailW detail = new EvaluationDetailW();
			detail.setReceptionevaluationid(new Long(evaluation.getId()));
			detail.setReceptionerrorid(new Long(receptionerror.getId()));
			evaluationdetailserver.addEvaluationDetail(detail);
						
			// El sistema deja el despacho en "No Asiste"
			DeliveryStateW delstate = new DeliveryStateW();
			delstate.setWhen(now);
			delstate.setDeliverystatetypeid(dstNonAttend.getId());
			delstate.setDeliveryid(delivery.getId());
			delstate = deliverystateserver.addDeliveryState(delstate);

			delivery.setCurrentstatetypeid(dstNonAttend.getId());
			delivery.setCurrentstatetypedate(now);

			delivery = deliveryserver.updateDelivery(delivery);						
			
			// Obtener las entregas de órdenes y cerrarlas
			OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("delivery.id", initParams.getDeliveryid());
			for (OrderDeliveryW orderdelivery : orderdeliveries) {
				orderdelivery.setClosed(true);
				orderdeliveryserver.updateOrderDelivery(orderdelivery);
			}
			orderdeliveryserver.flush();
			
			// RECALCULA LAS UNIDADES OC
			for (Long ocId : oMap.keySet()) {
				buyorderreportmanager.doCalculateTotalOfOrder(ocId);
			}

			// Enviar mail con la causa del rechazo
			String[] mailreciever = new String[1];
			String subject = "B2B Paris: Rechazo de despacho Nro. " + delivery.getNumber() + " � No asiste a la cita";
			Mailer mailer = Mailer.getInstance();
			mailreciever[0] = datingrequest.getEmail();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();

			String msgtext = "Estimado(a) usuario(a): \n\n" + "Comunicamos a usted que el despacho Nro " + delivery.getNumber() + " ha sido rechazado por no haber asistido a la cita programada para el d�a " + sdf.format(dating.getWhen()) + "\n\nAtte. B2B Paris.";

			mailer.sendMailBySession(mailreciever, null, null, mailsender, subject, msgtext, false, null, mailSession);

			// CALCULA LOS BULTOS
			deliveryreportmanager.doCalculateTotalBulkDetailOfDelivery(delivery.getId());

			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "0", "Se ha ingresado exitosamente la inasistencia del proveedor.", false);
		} catch (ServiceException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}

	public EvaluationReportDataResultDTO getEvaluationReportOfDating(EvaluationReportInitParamDTO initParams) {

		EvaluationReportDataResultDTO resultDTO = new EvaluationReportDataResultDTO();

		DatingW dating = null;
		ReceptionEvaluationW receptionEvaluation = null;
		ReceptionErrorReportDataDTO[] receptionErrorsDTO = null;

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		// Valida local
		Long locationid = -1L;
		if(! initParams.getLocationcode().equals("-1")) {
			
			LocationW[] locations = null;
			try{
				locations = locationserver.getByPropertyAsArray("code", initParams.getLocationcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(locations == null || locations.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L442");// no existe
			}
			locationid = locations[0].getId();
		}
		
		
		try {
//			// Chequea la existencia del proveedor
//			try {
//				vendorserver.getById(initParams.getVendorid());
//			} catch (NotFoundException e) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1902");
//			}

			// Chequea la existencia de la cita para ese proveedor
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("delivery.id", "delivery", initParams.getDeliveryid());
			properties[1] = new PropertyInfoDTO("vendor.id", "vendor", vendor.getId());
			List<DatingW> datings = datingserver.getByProperties(properties);

			if (datings == null || datings.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4006");
			}

			dating = datings.get(0);

			// Chequea la existencia del local de entrega (para CD)
			if (locationid.longValue() != -1) {
				try {
					locationserver.getById(locationid);
				} catch (NotFoundException e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1904");
				}

				// Chequea la existencia del despacho para ese local de entrega
				properties[0] = new PropertyInfoDTO("id", "id", dating.getDeliveryid());
				properties[1] = new PropertyInfoDTO("location.id", "location", locationid);
				List<DeliveryW> deliveries = deliveryserver.getByProperties(properties);

				if (deliveries == null || deliveries.size() <= 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4007");
				}
			}

			// Validar si existe evaluación de la recepción de la cita
			PropertyInfoDTO property = new PropertyInfoDTO("dating.id", "dating", dating.getId());
			List<ReceptionEvaluationW> receptionEvaluations = receptionevaluationserver.getByProperties(property);

			if (receptionEvaluations == null || receptionEvaluations.size() <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4300");
			}

			receptionEvaluation = receptionEvaluations.get(0);

			// Obtener los errores de recepción de la evaluación
			receptionErrorsDTO = receptionerrorserver.getReceptionErrorsOfEvaluation(receptionEvaluation.getId());

			// Llenar el reporte con los datos de la evaluación
			resultDTO.setDatingnumber(dating.getNumber());
			resultDTO.setScore(receptionEvaluation.getScore());
			resultDTO.setComment(receptionEvaluation.getComments());
			resultDTO.setReceptionerrors(receptionErrorsDTO);

		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); // Error Interno del
			// Servidor
		}

		return resultDTO;
	}

	public FileDownloadInfoResultDTO getCSVDatingToDeliveryReport(String locationcode, Long userID) throws OperationFailedException, NotFoundException {
		
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", locationcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L442");// no existe
		}
		location = locations[0];
		
		return datingserver.getCSVDatingToDeliveryReport(location.getId(), userID);
	}

	public FileDownloadInfoResultDTO getCSVDatingReport(String locationcode, LocalDateTime since, Long userID) throws OperationFailedException, NotFoundException {
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		
		// Valida local
		LocationW[] locations = null;
		LocationW location;
		try{
			locations = locationserver.getByPropertyAsArray("code", locationcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		if(locations == null || locations.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L442");// no existe
		}
		location = locations[0];
		
		try {
			Date sinced = Date.from( since.atZone( ZoneId.systemDefault()).toInstant());

			result = datingserver.getCSVDatingReport(location.getId(), sinced, userID);
		} catch (ServiceException e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		return result;
	}

	// M�todo para v�lidar correo electr�nico
	private boolean isEmail(String correo) {
		Pattern pat = null;
		Matcher mat = null;
		pat = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
		mat = pat.matcher(correo);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	public ExcelDatingRequestReportDataDTO getExcelDatingRequest(Long[] deliveryids) {

		ExcelDatingRequestReportDataDTO resultDTO = new ExcelDatingRequestReportDataDTO();

		ExcelDatingRequestReportData[] excelDoDatingReportData = null;

		try {

			excelDoDatingReportData = datingrequestserver.getExcelDatingRequest(deliveryids);
			resultDTO.setExcelDODatingReportData(excelDoDatingReportData);
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e2) {
			e2.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;

	}

	public FileDownloadInfoResultDTO doDailyReportDates(Date since, Date until, Long userid) throws OperationFailedException {
		FileDownloadInfoResultDTO result = new FileDownloadInfoResultDTO();
		SimpleDateFormat sds = new SimpleDateFormat("yyyyMMdd");
		String dateS = sds.format(since);
		try {

			Long code =	doGenerateFiles(since, until);

			String filepath = null;
			String newcode = null;

			String filename = "chi_paris_b2b_agendamiento_b2bage" + "_" + dateS + "_";
			if (code.toString().length() < 2) {
				newcode = "0" + code.toString();
			} else {
				newcode = code.toString();
			}
			filename += newcode;
			filepath = RegionalLogisticConstants.getInstance().getTMP_FILE_PATH() + filename;
			String filegz = filename + ".dat.gz";
			String filectr = filename + ".ctr";
			String filetrg = filename + ".trg";
			byte[] buf = new byte[1024];

			FileInputStream fin = new FileInputStream(filepath+".dat.gz");
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(filepath + "_"+userid.longValue()+".zip")));

			zos.putNextEntry(new ZipEntry(filegz));
			int len = 0;
			while ((len = fin.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();

			fin = new FileInputStream(filepath+".ctr");
			zos.putNextEntry(new ZipEntry(filectr));
			len = 0;
			while ((len = fin.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			
			fin = new FileInputStream(filepath+".dat.trg");
			zos.putNextEntry(new ZipEntry(filetrg));
			len = 0;
			while ((len = fin.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			fin.close();
			zos.close();
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss");
			String downloadfilename = "Reporte Vista Agendamiento "+sdf.format(cal.getTime())+".zip";
			
			//Envia al portal			
			String comando = "sh /b2b/shell/fileToDownload/simpleMoveFile.sh " + filename+"_"+userid.longValue()+".zip"  ;
			try {
				Process p = Runtime.getRuntime().exec(comando); // ejecuta el comando
				p.waitFor(); // espera que termine
			} catch (IOException e) {
				e.printStackTrace();
				throw new OperationFailedException("");
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new OperationFailedException("");
			}
			result.setRealfilename(filename+"_"+userid.longValue()+".zip");
			result.setDownloadfilename(downloadfilename);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException(e.getMessage());
		}

		return result;
	}

	public void doDailyReportDates(Date date) throws OperationFailedException {

		SimpleDateFormat sds = new SimpleDateFormat("yyyyMMdd");
		String dateS = sds.format(date);
		try {
			Long code =	doGenerateFiles(date, date);
			String newcode = null;
			String filename = "chi_paris_b2b_agendamiento_b2bage" + "_" + dateS + "_";
			if (code.toString().length() < 2) {
				newcode = "0" + code.toString();
			} else {
				newcode = code.toString();
			}
			filename += newcode;			
			String filegz = filename + ".dat.gz";
			String filectr = filename + ".ctr";
			String filetrg = filename + ".dat.trg";		
			
			
			// ejecutar shell que mueve los archivos al FTP
			String comando = "sh /b2b/shell/fileToFtp/moveFile.sh " + filegz + " " + filectr + " " + filetrg;
			logger.info(comando);

			Process p = Runtime.getRuntime().exec(comando); // ejecuta el comando
			p.waitFor(); // espera que termine
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException(e.getMessage());
		}
	}

	

	private Long doGenerateFiles(Date since, Date until) throws OperationFailedException {
		SimpleDateFormat sds = new SimpleDateFormat("yyyyMMdd");
		String dateS = sds.format(since);
		Long code = null;
		try {
			String filepath = null;			
			Long filesize = null;
			String newcode = null;
			DailyReportDatesDTO[] resultDTO = null;
			
			//si es por cron, no valida el hasta
			resultDTO = datingserver.getDailyReportDates(since, until);
			code = datingserver.getNextSequenceDailyReportDates();
			String filename = "chi_paris_b2b_agendamiento_b2bage" + "_" + dateS + "_";
			if (code.toString().length() < 2) {
				newcode = "0" + code.toString();
			} else {
				newcode = code.toString();
			}
			filename += newcode;

			filepath = RegionalLogisticConstants.getInstance().getTMP_FILE_PATH() + filename;
			/////archivo DAT /////
			File archivoDat = new File(filepath + ".dat");
			BufferedWriter bwdat = new BufferedWriter(new FileWriter(archivoDat));

			for (int i = 0; i < resultDTO.length; i++) {
				bwdat.write(resultDTO[i].getDatingdate().replace("|", " ") + "|" + resultDTO[i].getNumber().replace("|", " ") + "|" + resultDTO[i].getRut().replace("|", " ") + "|" + resultDTO[i].getBuynumber().replace("|", " ") + "|" + resultDTO[i].getCode().replace("|", " ") + "|"
						+ resultDTO[i].getInternalcode().replace("|", " ") + "|" + resultDTO[i].getAvailableunits() + "|" + resultDTO[i].getAmount() + "|" + resultDTO[i].getName().replace("|", " ") + "|" + resultDTO[i].getBatchname().replace("|", " ") + "\n");
			}
			bwdat.close();

			/////Comprimir archivo en GZip

			FileInputStream fis = new FileInputStream(archivoDat);
			String filegz = filepath + ".dat.gz";
			GZIPOutputStream gzipOS = new GZIPOutputStream(new FileOutputStream(new File(filegz)));
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) != -1) {
				gzipOS.write(buffer, 0, len);
			}
			gzipOS.close();
			fis.close();
			filesize = archivoDat.length();
			archivoDat.delete();

			/////archivo CTR /////
			String filectr = filepath + ".ctr";
			File archivoCtr = new File(filectr);
			BufferedWriter bwctr = new BufferedWriter(new FileWriter(archivoCtr));
			bwctr.write("chi|paris|b2b|agendamiento|b2bage|" + dateS + "|" + newcode + "|" + resultDTO.length + "|" + filesize);
			bwctr.close();

			/////archivo TRG /////
			String filetrg = filepath + ".dat.trg";
			File archivoTrg = new File(filetrg);
			BufferedWriter bwtrg = new BufferedWriter(new FileWriter(archivoTrg));
			bwtrg.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationFailedException(e.getMessage());
		}
		return code;
	}

}
