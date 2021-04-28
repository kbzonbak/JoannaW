package bbr.b2b.regional.logistic.kpi.managers.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.TransactionTimeout;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.FileDownloadInfoResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.couriers.classes.CourierScheduleLogServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevCDDetailServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevCDRuleServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevCDServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevCDTypeServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevComplianceFactorServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevExecutionStateServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevFinePeriodServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevFineServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevPDDetailServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevPDRuleServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevPDServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevPDTypeServerLocal;
import bbr.b2b.regional.logistic.kpi.classes.KPIvevParameterServerLocal;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIResetInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPISummaryDetailInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPISummaryInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIdetailInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIgeneralInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDTypeArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevCDTypeDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevComplianceInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineInitParamDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDComplianceDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDReportDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDetailArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryDetailReportDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDSummaryTotalDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDTypeArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevPDTypeDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.SaleStoreArrayResultDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.SaleStoreDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDDetailW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDRuleW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDTypeW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevCDW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevExecutionStateW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFinePeriodW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFineW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDDetailW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDRuleW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDTypeW;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevPDW;
import bbr.b2b.regional.logistic.kpi.managers.interfaces.KPIReportManagerLocal;
import bbr.b2b.regional.logistic.kpi.managers.interfaces.KPIReportManagerRemote;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorLogDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorsLogArrayResultDTO;
@Stateless(name = "managers/KPIReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class KPIReportManager implements KPIReportManagerLocal, KPIReportManagerRemote {

	private static Logger logger = Logger.getLogger(KPIReportManager.class);
	
	private static boolean isKPIVeVCDStarted = false;
	private static boolean isKPIVeVPDStarted = false;
	private static boolean isKPIVeVCDComplianceStarted = false;
	private static boolean isKPIVeVPDComplianceStarted = false;

	@EJB
	KPIvevParameterServerLocal kpivevparameterserverlocal;

	@EJB
	KPIvevCDDetailServerLocal kpivevcddetailserver;

	@EJB
	KPIvevCDTypeServerLocal kpivevcdtypeserver;
	
	@EJB
	KPIvevCDRuleServerLocal kpivevcdruleserver;

	@EJB
	KPIvevCDServerLocal kpivevcdserver;

	@EJB
	KPIvevPDDetailServerLocal kpivevpddetailserver;

	@EJB
	KPIvevPDTypeServerLocal kpivevpdtypeserver;
	
	@EJB
	KPIvevPDRuleServerLocal kpivevpdruleserver;

	@EJB
	KPIvevPDServerLocal kpivevpdserver;

	@EJB
	KPIvevFinePeriodServerLocal kpivevfineperiodserver;

	@EJB
	KPIvevFineServerLocal kpivevfineserver;

	@EJB
	KPIvevComplianceFactorServerLocal kpivevcompliancefactorserver;
	
	@EJB
	KPIvevExecutionStateServerLocal kpivevexecutionstateserverlocal;

	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DepartmentServerLocal departmentserver;

	@EJB
	LocationServerLocal locationserver;

	@EJB
	OrderStateTypeServerLocal orderstatetypeserver;

	@EJB
	DirectOrderStateTypeServerLocal directorderstatetypeserver;

	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;

	@EJB
	CourierScheduleLogServerLocal courierschedulelogserver;

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	public SaleStoreArrayResultDTO getSaleStores() {
		SaleStoreArrayResultDTO resultDTO = new SaleStoreArrayResultDTO();

		try {
			SaleStoreDTO[] salestores = locationserver.getSaleStoresOrderedByDescription();

			resultDTO.setSalestores(salestores);

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1502");
		}

		return resultDTO;
	}

	public SaleStoreArrayResultDTO findSaleStoresByCode(String code) {
		SaleStoreArrayResultDTO resultDTO = new SaleStoreArrayResultDTO();

		try {
			SaleStoreDTO[] salestores = locationserver.findSaleStoresByCode(code);

			resultDTO.setSalestores(salestores);

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1502");
		}

		return resultDTO;

	}

	public SaleStoreArrayResultDTO findSaleStoresByName(String name) {
		SaleStoreArrayResultDTO resultDTO = new SaleStoreArrayResultDTO();

		try {
			SaleStoreDTO[] salestores = locationserver.findSaleStoresByName(name);

			resultDTO.setSalestores(salestores);

		} catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1502");
		}

		return resultDTO;
	}

	// CD
	public VendorsLogArrayResultDTO getKPIvevCDVendors() {
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();

		VendorW[] vendorArray = null;
		VendorLogDTO[] vendorLogArray = null;

		try {
			Long[] vendorIDs = kpivevcdserver.getAllVendorIds();

			// Obtiene Vendors ordenados por nombre
			OrderCriteriaDTO[] orderbyArr = new OrderCriteriaDTO[1];
			OrderCriteriaDTO orderby = new OrderCriteriaDTO("NAME", true);
			orderbyArr[0] = orderby;
			vendorArray = vendorserver.getVendorsByIds(vendorIDs, 0, 0, false, orderbyArr);
			vendorLogArray = new VendorLogDTO[vendorArray.length];
			BeanExtenderFactory.copyProperties(vendorArray, vendorLogArray, VendorLogDTO.class);

			resultDTO.setVendors(vendorLogArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

/*	@TransactionTimeout(550)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doSetKPIvevCDData(Long vendorid, Date since, Date until) {

		if (isKPIVeVCDStarted) {
			logger.error("Existe otro cálculo de KPI CD en proceso");
			return false;
		}
		
		isKPIVeVCDStarted = true;
		
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("Comenzando cálculo de KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));

		try {
			// Eliminar KPI CD y sus detalles previos para el proveedor (pueden ser todos) y el per�odo
			kpivevcddetailserver.deleteByVendorAndPeriod(vendorid, since, until);
			kpivevcddetailserver.flush();

			kpivevcdserver.deleteByVendorAndPeriod(vendorid, since, until);
			kpivevcdserver.flush();

			HashMap<String, KPIvevCDW> kPIvevCDMap = new HashMap<String, KPIvevCDW>();
			KPIvevCDW kpivevCDw = null;
			HashMap<Long, List<KPIvevCDDetailW>> kPIvevCDDetailMap = new HashMap<Long, List<KPIvevCDDetailW>>();
			List<KPIvevCDDetailW> kPIvevCDDetailList = null;
			KPIvevCDDetailW kpivevCDDetailw = null;
			String key;

			// Buscar las OC VEV CD para el proveedor (pueden ser todos) y el per�odo
			KPIvevCDDetailW[] kpiDetailData = kpivevcddetailserver.getDataToCalculateKPIByPeriod(vendorid, since, until);
			logger.info("KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad de detalles: " + kpiDetailData.length);
			if (kpiDetailData != null && kpiDetailData.length > 0) {
				// Obtener un mapa de los detalles agrupados por OC
				for (KPIvevCDDetailW detail : kpiDetailData) {
					if (kPIvevCDDetailMap.containsKey(detail.getOrdernumber())) {
						kPIvevCDDetailList = kPIvevCDDetailMap.get(detail.getOrdernumber());
					} else {
						kPIvevCDDetailList = new ArrayList<KPIvevCDDetailW>();
					}

					kPIvevCDDetailList.add(detail);
					kPIvevCDDetailMap.put(detail.getOrdernumber(), kPIvevCDDetailList);
				}

				// Estados de órdenes
				OrderStateTypeW ostDeleted = orderstatetypeserver.getByPropertyAsSingleResult("code", "ELIMINADA");
				OrderStateTypeW ostReceived = orderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");

				// Tipos de KPI
				KPIvevCDTypeW kpistAccordingReceipt = kpivevcdtypeserver.getByPropertyAsSingleResult("code", "RECIBO_CONFORME");
				KPIvevCDTypeW kpistDelayedReceipt = kpivevcdtypeserver.getByPropertyAsSingleResult("code", "RECIBO_ATRASO");
				KPIvevCDTypeW kpistVendorDelay = kpivevcdtypeserver.getByPropertyAsSingleResult("code", "ATRASO_PROVEEDOR");
				KPIvevCDTypeW kpistCreditNote = kpivevcdtypeserver.getByPropertyAsSingleResult("code", "NOTA_CREDITO");

				Long kpivevcdtypeid;
				Integer finefactordays;
				for (Map.Entry<Long, List<KPIvevCDDetailW>> detailList : kPIvevCDDetailMap.entrySet()) {
					kpivevCDDetailw = detailList.getValue().get(0);

					// Generar/obtener el registro KPI
					key = kpivevCDDetailw.getVendorid() + "_" + kpivevCDDetailw.getDepartmentid() + "_" +
							(kpivevCDDetailw.getSalestoreid() != null ? kpivevCDDetailw.getSalestoreid().toString() : "") + "_" +
								sdfdate.format(kpivevCDDetailw.getFpi());

					if (kPIvevCDMap.containsKey(key)) {
						kpivevCDw = kPIvevCDMap.get(key);
					}
					else {
						kpivevCDw = new KPIvevCDW();
						kpivevCDw.setVendorid(kpivevCDDetailw.getVendorid());
						kpivevCDw.setDepartmentid(kpivevCDDetailw.getDepartmentid());
						kpivevCDw.setSalestoreid(kpivevCDDetailw.getSalestoreid());
						kpivevCDw.setFpi(kpivevCDDetailw.getFpi());
					}

					// Evaluar el cumplimiento y determinar los atrasos
					if (kpivevCDDetailw.getCurrentorderstatetypeid().equals(ostDeleted.getId())) { // Eliminada
						// JPE 20200720
						if (kpivevCDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
							// Atraso del Proveedor
							kpivevcdtypeid = kpistVendorDelay.getId();
							finefactordays = kpivevCDDetailw.getDeliverydelayeddays();
							kpivevCDw.setTotalproviderdelayed(kpivevCDw.getTotalproviderdelayed() + 1);
						}
						else { // Sin atraso en la entrega
							// Nota de Cr�dito
							kpivevcdtypeid = kpistCreditNote.getId();
							finefactordays = 0;
							kpivevCDw.setCreditnotes(kpivevCDw.getCreditnotes() + 1);
						}						
					}
					else if (kpivevCDDetailw.getCurrentorderstatetypeid().equals(ostReceived.getId())) { // Recepcionada
						if (kpivevCDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
							// Recibo con Atraso
							kpivevcdtypeid = kpistDelayedReceipt.getId();
							finefactordays = kpivevCDDetailw.getDeliverydelayeddays();
							kpivevCDw.setTotalreceiveddelayed(kpivevCDw.getTotalreceiveddelayed() + 1);
						}
						else { // Sin atraso en la entrega
							// Recibo Conforme
							kpivevcdtypeid = kpistAccordingReceipt.getId();
							finefactordays = 0;
							kpivevCDw.setTotalreceivedconformed(kpivevCDw.getTotalreceivedconformed() + 1);
						}
					}
					else { // Otro estado de la OC
						// Atraso del Proveedor
						kpivevcdtypeid = kpistVendorDelay.getId();
						finefactordays = kpivevCDDetailw.getDelayeddays();
						kpivevCDw.setTotalproviderdelayed(kpivevCDw.getTotalproviderdelayed() + 1);
					}

					for (KPIvevCDDetailW detail : detailList.getValue()) {
						detail.setKpivevcdtypeid(kpivevcdtypeid);
						detail.setFinefactordays(finefactordays);
					}

					kpivevCDw.setExecutiondate(new Date());
					kPIvevCDMap.put(key, kpivevCDw);
					kPIvevCDDetailMap.put(detailList.getKey(), detailList.getValue());
				}
			}

			// Almacenar los registros KPI y sus detalles
			for (Map.Entry<String, KPIvevCDW> kpi : kPIvevCDMap.entrySet()) {
				kpivevCDw = kpivevcdserver.addKPIvevCD(kpi.getValue());
				kPIvevCDMap.put(kpi.getKey(), kpivevCDw);
			}

			for (Map.Entry<Long, List<KPIvevCDDetailW>> detailList : kPIvevCDDetailMap.entrySet()) {
				for (KPIvevCDDetailW detail : detailList.getValue()) {
					key = detail.getVendorid() + "_" + detail.getDepartmentid() + "_" +
							(detail.getSalestoreid() != null ? detail.getSalestoreid().toString() : "") + "_" + sdfdate.format(detail.getFpi());

					detail.setKpivevcdid(kPIvevCDMap.get(key).getId());
					kpivevcddetailserver.addKPIvevCDDetail(detail);
				}
			}
			
			// Marcar el proceso como ejecutado
			KPIvevExecutionStateW executionstate = new KPIvevExecutionStateW();
			
			executionstate.setWhen1(new Date());
			executionstate.setType("CD");
			
			kpivevexecutionstateserverlocal.addKPIvevExecutionState(executionstate);
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVCDStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));

		isKPIVeVCDStarted = false;
		
		return true;
	}*/

	@TransactionTimeout(value = 550)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doSetKPIvevCDData(String vendorcode, Date since, Date until) {

		if (isKPIVeVCDStarted) {
			logger.error("Existe otro cálculo de KPI CD en proceso");
			return false;
		}
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		isKPIVeVCDStarted = true;

		Long vendorid = -1L;
		try {
			if(!"-1".equals(vendorcode)) {
				VendorW vendor = vendorserver.getByPropertyAsSingleResult("rut", vendorcode);
				vendorid = vendor.getId();
			}
			
			logger.info("Comenzando cálculo de KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
			
			// Eliminar KPI CD y sus detalles previos para el proveedor (pueden ser todos) y el per�odo
			kpivevcddetailserver.deleteByVendorAndPeriod(vendorid, since, until);
			kpivevcddetailserver.flush();

			kpivevcdserver.deleteByVendorAndPeriod(vendorid, since, until);
			kpivevcdserver.flush();			

			// Buscar las OC VEV CD para el proveedor (pueden ser todos) y el per�odo
			KPIvevCDDetailW[] kpiDetailData = kpivevcddetailserver.getDataToCalculateKPIByPeriod(vendorid, since, until);
			logger.info("KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad de detalles: " + kpiDetailData.length);
			
			if (kpiDetailData != null && kpiDetailData.length > 0) {
				// Estados de órdenes
				OrderStateTypeW ostDeleted = orderstatetypeserver.getByPropertyAsSingleResult("code", "ELIMINADA");
				OrderStateTypeW ostReceived = orderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");

				// Obtener mapa de los tipos de KPI VeV CD
				KPIvevCDTypeW[] kpiVeVCDTypes = kpivevcdtypeserver.getAllAsArray();
				HashMap<Long, KPIvevCDTypeW> kpiVeVCDTypeMap = new HashMap<Long, KPIvevCDTypeW>();
				for (KPIvevCDTypeW kpiVeVCDType : kpiVeVCDTypes) {
					kpiVeVCDTypeMap.put(kpiVeVCDType.getId(), kpiVeVCDType);
				}
				
				// Obtener mapa de las reglas de KPI VeV CD
				KPIvevCDRuleW[] kpiVeVCDRules = kpivevcdruleserver.getAllAsArray();
				HashMap<String, Long> kpiVeVCDRuleMap = new HashMap<String, Long>();
				for (KPIvevCDRuleW kpiVeVCDRule : kpiVeVCDRules) {
					kpiVeVCDRuleMap.put(kpiVeVCDRule.getOrderstatetypeid() + "_INTIME", kpiVeVCDRule.getIntimekpivevcdtypeid());
					kpiVeVCDRuleMap.put(kpiVeVCDRule.getOrderstatetypeid() + "_ONTIME", kpiVeVCDRule.getOntimekpivevcdtypeid());
					kpiVeVCDRuleMap.put(kpiVeVCDRule.getOrderstatetypeid() + "_DELAYED", kpiVeVCDRule.getDelayedkpivevcdtypeid());
				}
				
				// Obtener mapa de los detalles agrupados por OC
				HashMap<Long, List<KPIvevCDDetailW>> kPIvevCDDetailMap = new HashMap<Long, List<KPIvevCDDetailW>>();
				List<KPIvevCDDetailW> kPIvevCDDetailList = null;
				for (KPIvevCDDetailW detail : kpiDetailData) {
					if (kPIvevCDDetailMap.containsKey(detail.getOrdernumber())) {
						kPIvevCDDetailList = kPIvevCDDetailMap.get(detail.getOrdernumber());
					} else {
						kPIvevCDDetailList = new ArrayList<KPIvevCDDetailW>();
					}

					kPIvevCDDetailList.add(detail);
					kPIvevCDDetailMap.put(detail.getOrdernumber(), kPIvevCDDetailList);
				}
				
				HashMap<String, KPIvevCDW> kpiVeVCDMap = new HashMap<String, KPIvevCDW>();
				KPIvevCDW kpiVeVCDW = null;
				KPIvevCDDetailW kpiVeVCDDetailW = null;
				KPIvevCDTypeW kpiVeVCDType = null;
				Long kpiVeVCDTypeId = null;
				Integer delayedDays = 0;
				Integer fineFactorDays = 0;
				String key = null;
				String ruleType = null;
				for (Map.Entry<Long, List<KPIvevCDDetailW>> detailList : kPIvevCDDetailMap.entrySet()) {
					kpiVeVCDDetailW = detailList.getValue().get(0);

					// Generar/obtener el registro KPI
					key = kpiVeVCDDetailW.getVendorid() + "_" + kpiVeVCDDetailW.getDepartmentid() + "_" +
							(kpiVeVCDDetailW.getSalestoreid() != null ? kpiVeVCDDetailW.getSalestoreid().toString() : "") + "_" +
								sdfdate.format(kpiVeVCDDetailW.getFpi());

					if (kpiVeVCDMap.containsKey(key)) {
						kpiVeVCDW = kpiVeVCDMap.get(key);
					}
					else {
						kpiVeVCDW = new KPIvevCDW();
						kpiVeVCDW.setVendorid(kpiVeVCDDetailW.getVendorid());
						kpiVeVCDW.setDepartmentid(kpiVeVCDDetailW.getDepartmentid());
						kpiVeVCDW.setSalestoreid(kpiVeVCDDetailW.getSalestoreid());
						kpiVeVCDW.setFpi(kpiVeVCDDetailW.getFpi());
					}

					// Determinar c�mo considerar los d�as de atraso (hasta la fecha del �ltimo estado o hasta la fecha actual)
					delayedDays = kpiVeVCDDetailW.getCurrentorderstatetypeid().equals(ostDeleted.getId()) ||
								  kpiVeVCDDetailW.getCurrentorderstatetypeid().equals(ostReceived.getId()) ?
										    kpiVeVCDDetailW.getDeliverydelayeddays() :
										    kpiVeVCDDetailW.getDelayeddays();
										  
					// Determinar el tipo de regla a emplear seg�n el estado de la orden y los d�as de atraso
					ruleType = kpiVeVCDDetailW.getCurrentorderstatetypeid() + "_";
					if (delayedDays < 0) {
						ruleType += "INTIME";
					}
					else if (delayedDays > 0) {
						ruleType += "DELAYED";
					}
					else {
						ruleType += "ONTIME";
					}
										  
					// Evaluar el cumplimiento y determinar los atrasos
					kpiVeVCDTypeId = kpiVeVCDRuleMap.get(ruleType);
					kpiVeVCDType = kpiVeVCDTypeMap.get(kpiVeVCDTypeId);
					fineFactorDays = kpiVeVCDType.getFine() ? delayedDays : 0;
					
					// Recibo Conforme
					if (kpiVeVCDType.getCode().equals("RECIBO_CONFORME")) {
						kpiVeVCDW.setTotalreceivedconformed(kpiVeVCDW.getTotalreceivedconformed() + 1);
					}
					// Recibo con Atraso
					else if (kpiVeVCDType.getCode().equals("RECIBO_ATRASO")) {
						kpiVeVCDW.setTotalreceiveddelayed(kpiVeVCDW.getTotalreceiveddelayed() + 1);
					}
					// Atraso del Proveedor
					else if (kpiVeVCDType.getCode().equals("ATRASO_PROVEEDOR")) {
						kpiVeVCDW.setTotalproviderdelayed(kpiVeVCDW.getTotalproviderdelayed() + 1);
					}
					// Nota de Cr�dito
					else if (kpiVeVCDType.getCode().equals("NOTA_CREDITO")) {
						kpiVeVCDW.setCreditnotes(kpiVeVCDW.getCreditnotes() + 1);
					}

					for (KPIvevCDDetailW detail : detailList.getValue()) {
						detail.setKpivevcdtypeid(kpiVeVCDTypeId);
						detail.setFinefactordays(fineFactorDays);
					}

					kpiVeVCDW.setExecutiondate(new Date());
					kpiVeVCDMap.put(key, kpiVeVCDW);
					kPIvevCDDetailMap.put(detailList.getKey(), detailList.getValue());
				}
				
				// Almacenar los registros KPI y sus detalles
				for (Map.Entry<String, KPIvevCDW> kpi : kpiVeVCDMap.entrySet()) {
					kpiVeVCDW = kpivevcdserver.addKPIvevCD(kpi.getValue());
					kpiVeVCDMap.put(kpi.getKey(), kpiVeVCDW);
				}
				
				for (Map.Entry<Long, List<KPIvevCDDetailW>> detailList : kPIvevCDDetailMap.entrySet()) {
					for (KPIvevCDDetailW detail : detailList.getValue()) {
						key = detail.getVendorid() + "_" + detail.getDepartmentid() + "_" +
								(detail.getSalestoreid() != null ? detail.getSalestoreid().toString() : "") + "_" + sdfdate.format(detail.getFpi());

						detail.setKpivevcdid(kpiVeVCDMap.get(key).getId());
						kpivevcddetailserver.addKPIvevCDDetail(detail);
					}
				}
			}
			
			// Marcar el proceso como ejecutado
			KPIvevExecutionStateW executionstate = new KPIvevExecutionStateW();
			
			executionstate.setWhen1(new Date());
			executionstate.setType("CD");
			
			kpivevexecutionstateserverlocal.addKPIvevExecutionState(executionstate);
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVCDStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de KPI CD (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));

		isKPIVeVCDStarted = false;
		
		return true;
	}
	
	public BaseResultDTO doResetKPIvevCDData(KPIResetInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		// Valida proveedor
		Long vendorid = -1L;
		if (!initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			
			vendorid = vendors[0].getId();
		}
		
		try {
			Calendar today = Calendar.getInstance();
			
			Date since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			Date until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());

			// Validar que la fecha escogida sea anterior o igual al d�a actual
			if (until.after(today.getTime())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1103");
			}

			// Validar que el proveedor escogido posea órdenes de tipo VeV CD en la FPI indicada
			int count = kpivevcddetailserver.getCountDataToCalculateKPIByPeriod(vendorid, since, until);
			if (count <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1104");
			}
			
			if (isKPIVeVCDStarted) {
				logger.error("Existe otro cálculo de KPI CD en proceso");
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Existe otro cálculo de KPI CD en proceso. Espere unos minutos y vuelva a intentarlo", false);
			}

			// Calcular evaluaciones
			if (!doSetKPIvevCDData(initParamDTO.getVendorcode(), since, until)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}

		} catch (Exception e) {
			e.getStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevCDTypeArrayResultDTO getKPIvevCDTypes() {
		KPIvevCDTypeArrayResultDTO resultDTO = new KPIvevCDTypeArrayResultDTO();

		KPIvevCDTypeW[] typeArray = null;
		KPIvevCDTypeDTO[] typedtoArray = null;

		try {
			typeArray = kpivevcdtypeserver.getAllAsArray();

			typedtoArray = new KPIvevCDTypeDTO[typeArray.length];
			BeanExtenderFactory.copyProperties(typeArray, typedtoArray, KPIvevCDTypeDTO.class);

			resultDTO.setTypes(typedtoArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevCDReportArrayResultDTO getKPIvevCDDataForReport(KPIgeneralInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevCDReportArrayResultDTO resultDTO = new KPIvevCDReportArrayResultDTO();
		
		logger.info("getKPIvevCDDataForReport: vendorcode:"+initParamDTO.getVendorcode());
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		logger.info("getKPIvevCDDataForReport: vendorid:"+vendorid);		
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) { 
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("")|| initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			Float meta = null;
			try {
				meta = Float.parseFloat(B2BSystemPropertiesUtil.getProperty("meta"));
			} catch (Exception e) {
				logger.error("No se encontró la propiedad 'meta'. Colocando valor por defecto: 95.0");
				meta = 95F;
			}

			resultDTO.setMeta(meta);
			
			KPIvevCDReportDataDTO[] kpivevcdReportData = kpivevcdserver.getKPIGeneralData(meta, vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);
						
			KPIvevCDReportDataDTO[] kpivevcdReportDataTotal = kpivevcdserver.getKPIGeneralDataTotal(meta, vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getRows(), initParamDTO.getPageNumber(), null, isPaginated);

			KPIvevCDComplianceDTO compliancedata = null;
			int totalreg = 0;
			if (isByFilter) {
				compliancedata = kpivevcdserver.countKPIGeneralData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until);
				
				if (compliancedata != null) {
					totalreg = compliancedata.getTotalreg().intValue();
					
					if (compliancedata.getTotal() != 0) {
						resultDTO.setCompliancerate((compliancedata.getTotalreceivedconformed() * 100.0) / compliancedata.getTotalevaluated());
						resultDTO.setDefaultrate((compliancedata.getTotalevaluated() - compliancedata.getTotalreceivedconformed()) * 100.0 / compliancedata.getTotalevaluated());
					} else {
						resultDTO.setCompliancerate(0.0);
						resultDTO.setDefaultrate(0.0);
					}
				}
				
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
					pageInfo.setRows(kpivevcdReportData.length);
				}
				else {
					pageInfo.setRows(kpivevcdReportDataTotal.length);
				}
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setKpivevcdReportData(kpivevcdReportData);
			resultDTO.setKpivevcdReportDataTotal(kpivevcdReportDataTotal);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevCDDetailReportArrayResultDTO getKPIvevCDDetailData(KPIdetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevCDDetailReportArrayResultDTO resultDTO = new KPIvevCDDetailReportArrayResultDTO();

		// Valida proveedor
		Long vendorid = -1L;
		if (!initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			
			vendorid = vendors[0].getId();
		}
		
		int totalreg = 0;
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().length() == 0 || initParamDTO.getSince().equals("-1")) {
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().length() == 0 || initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			KPIvevCDDetailReportDTO[] kpivevcddetailreport = kpivevcddetailserver.getKPIvevCDDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until, initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);

			if (kpivevcddetailreport == null || kpivevcddetailreport.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1102");
			}

			if (isByFilter) {
				totalreg = kpivevcddetailserver.countKPIvevCDDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until);

				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(kpivevcddetailreport.length);
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setKpivevcddetailreport(kpivevcddetailreport);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevCDSummaryArrayResultDTO getKPIvevCDSummaryForReport(KPISummaryInitParamDTO initParamDTO) {
		KPIvevCDSummaryArrayResultDTO resultDTO = new KPIvevCDSummaryArrayResultDTO();

		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) { 
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("")|| initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();
			
			KPIvevCDSummaryDTO[] summary = kpivevcdserver.getKPIvevCDSummary(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getOrderby());

			if (summary != null && summary.length > 0) {
				KPIvevCDSummaryTotalDTO total = kpivevcdserver.countKPIvevCDSummary(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until);
			
				resultDTO.setTotal(total);
			}

			resultDTO.setSummary(summary);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevCDSummaryDetailArrayResultDTO getKPIvevCDSummaryDetailData(KPISummaryDetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevCDSummaryDetailArrayResultDTO resultDTO = new KPIvevCDSummaryDetailArrayResultDTO();

		int totalreg = 0;
		
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) { 
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("")|| initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();
			
			KPIvevCDSummaryDetailReportDTO[] summarydetail = kpivevcddetailserver.getKPIvevCDSummaryDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);

			if (summarydetail == null || summarydetail.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1102");
			}

			if (isByFilter) {
				totalreg = kpivevcddetailserver.countKPIvevCDSummaryDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until);

				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(summarydetail.length);
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setSummarydetail(summarydetail);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public FileDownloadInfoResultDTO getKPIvevCDDetailDataSourceReport(KPIdetailInitParamDTO initParamDTO, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		Long t1 = System.currentTimeMillis();
		System.err.println("**************Inicio dto fte KPI VeV CD");

		Long vendorid = -1L;
		try {
			// Obtener el proveedor
			String vendorname = "";
			if (!initParamDTO.getVendorcode().equals(String.valueOf(RegionalLogisticConstants.getInstance().getINT_TODOS()))) {
				//vendorname = vendorserver.getById(vendor.getId()).getName() + " ";
				// Valida proveedor
				VendorW[] vendors = null;
				VendorW vendor;
				try{
					vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
					
				}	catch (Exception e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				if(vendors == null || vendors.length == 0){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
				}
				vendor = vendors[0];
				vendorname = vendor.getName() + " ";
				vendorid = vendor.getId();
			}

			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().length() == 0 || initParamDTO.getSince().equals("-1")) {
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().length() == 0 || initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			resultDTO = kpivevcddetailserver.getKPIvevCDDetailDataSourceReport(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until, vendorname, userKey);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		Long t2 = System.currentTimeMillis();
		System.err.println("**************Fin dto fte KPI VeV CD [tiempo: " + +(t2 - t1) / 1000 + "]");

		return resultDTO;
	}
	
	// PD
	public VendorsLogArrayResultDTO getKPIvevPDVendors() {
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();

		VendorW[] vendorArray = null;
		VendorLogDTO[] vendorLogArray = null;

		try {
			Long[] vendorIDs = kpivevpdserver.getAllVendorIds();

			// Obtiene Vendors ordenados por nombre
			OrderCriteriaDTO[] orderbyArr = new OrderCriteriaDTO[1];
			OrderCriteriaDTO orderby = new OrderCriteriaDTO("NAME", true);
			orderbyArr[0] = orderby;
			vendorArray = vendorserver.getVendorsByIds(vendorIDs, 0, 0, false, orderbyArr);
			vendorLogArray = new VendorLogDTO[vendorArray.length];
			BeanExtenderFactory.copyProperties(vendorArray, vendorLogArray, VendorLogDTO.class);

			resultDTO.setVendors(vendorLogArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

/*	@TransactionTimeout(550)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doSetKPIvevPDData(Long vendorid, Date since, Date until, Boolean courier) {

		if (isKPIVeVPDStarted) {
			logger.error("Existe otro cálculo de KPI PD en proceso");
			return false;
		}
		
		isKPIVeVPDStarted = true;
		
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("Comenzando cálculo de KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));

		try {
			// Eliminar KPI PD y sus detalles previos para el proveedor (pueden ser todos) y el per�odo
			kpivevpddetailserver.deleteByVendorAndPeriod(vendorid, since, until, courier);
			kpivevpddetailserver.flush();

			kpivevpdserver.deleteByVendorAndPeriod(vendorid, since, until, courier);
			kpivevpdserver.flush();

			HashMap<String, KPIvevPDW> kPIvevPDMap = new HashMap<String, KPIvevPDW>();
			KPIvevPDW kpivevPDw = null;
			HashMap<Long, List<KPIvevPDDetailW>> kPIvevPDDetailMap = new HashMap<Long, List<KPIvevPDDetailW>>();
			List<KPIvevPDDetailW> kPIvevPDDetailList = null;
			KPIvevPDDetailW kpivevPDDetailw = null;
			String key;

			// Buscar las OC VEV PD para el proveedor (pueden ser todos) y el per�odo
			KPIvevPDDetailW[] kpiDetailData = kpivevpddetailserver.getDataToCalculateKPIByPeriod(vendorid, since, until, courier);
			logger.info("KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad de detalles: " + kpiDetailData.length);
			if (kpiDetailData != null && kpiDetailData.length > 0) {
				// Obtener un mapa de los detalles agrupados por OC
				for (KPIvevPDDetailW detail : kpiDetailData) {
					if (kPIvevPDDetailMap.containsKey(detail.getOrdernumber())) {
						kPIvevPDDetailList = kPIvevPDDetailMap.get(detail.getOrdernumber());
					}
					else {
						kPIvevPDDetailList = new ArrayList<KPIvevPDDetailW>();
					}

					kPIvevPDDetailList.add(detail);
					kPIvevPDDetailMap.put(detail.getOrdernumber(), kPIvevPDDetailList);
				}

				// Estados de Orden Directa
				DirectOrderStateTypeW dostReceived = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECEPCIONADA");
				DirectOrderStateTypeW dostCanceled = directorderstatetypeserver.getByPropertyAsSingleResult("code", "CANC_PARIS");
				DirectOrderStateTypeW dostRejected = directorderstatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
				DirectOrderStateTypeW dostReleased = directorderstatetypeserver.getByPropertyAsSingleResult("code", "LIBERADA");
				DirectOrderStateTypeW dostModified = directorderstatetypeserver.getByPropertyAsSingleResult("code", "MOD_PARIS");
				DirectOrderStateTypeW dostAccepted = directorderstatetypeserver.getByPropertyAsSingleResult("code", "ACEPTADA");
				DirectOrderStateTypeW dostMissingData = directorderstatetypeserver.getByPropertyAsSingleResult("code", "FALTAN_DATOS");
				DirectOrderStateTypeW dostClientRescheduled = directorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROG_CLIENTE");
				DirectOrderStateTypeW dostVendorRescheduled = directorderstatetypeserver.getByPropertyAsSingleResult("code", "REPROG_PROV");
				DirectOrderStateTypeW dostWithoutStock = directorderstatetypeserver.getByPropertyAsSingleResult("code", "SIN_STOCK");
				DirectOrderStateTypeW dostLost = directorderstatetypeserver.getByPropertyAsSingleResult("code", "EXTRAVIADO");
								
				// Estados de Despacho de Orden Directa
				DODeliveryStateTypeW dodstTagRequestPending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_POR_SOLICITAR");
				DODeliveryStateTypeW dodstTagRequested = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "ETIQ_SOLICITADA");
				DODeliveryStateTypeW dodstSchedulePending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_AGENDAR");
				DODeliveryStateTypeW dodstPickUpPending = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "PEND_RETIRO");
				DODeliveryStateTypeW dodstOnRoute = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EN_RUTA");
				DODeliveryStateTypeW dodstDamaged = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "DANO");
				DODeliveryStateTypeW dodstWrongAddress = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "DIR_ERR");
				DODeliveryStateTypeW dodstWrong = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EQUIVOCADO");
				DODeliveryStateTypeW dodstClientReason = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "MOTIVO_CLI");
				DODeliveryStateTypeW dodstTransportReason = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "MOTIVO_TRAN");
				DODeliveryStateTypeW dodstAbsentClient = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "SIN_MORADORES");
				DODeliveryStateTypeW dodstDelivered = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "REC_CONFORME");				
				DODeliveryStateTypeW dodstExpectations = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EXPECTATIVAS");
				DODeliveryStateTypeW dodstLost = dodeliverystatetypeserver.getByPropertyAsSingleResult("code", "EXTRAVIADO");
				
				// Tipos KPI
				KPIvevPDTypeW kpistDelivered = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "ENTREGADO");
				KPIvevPDTypeW kpistDelayedReceipt = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "RECIBO_ATRASO");
				KPIvevPDTypeW kpistCourierDelayedReceipt = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "RECIBO_ATRASO_COURIER");
				KPIvevPDTypeW kpistVendorDelay = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "ATRASO_PROVEEDOR");
				KPIvevPDTypeW kpistCourierDelay = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "ATRASO_COURIER");
				KPIvevPDTypeW kpistInconsistency = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "INCONSISTENCIA_ESTADO");
				KPIvevPDTypeW kpistResponsability = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "RESPONSABILIDAD_CLIENTE");
				KPIvevPDTypeW kpistLost = kpivevpdtypeserver.getByPropertyAsSingleResult("code", "EXTRAVIO");

				Long kpivevpdtypeid = 0L;
				Integer finefactordays = 0;
				for (Map.Entry<Long, List<KPIvevPDDetailW>> detailList : kPIvevPDDetailMap.entrySet()) {
					kpivevPDDetailw = detailList.getValue().get(0);

					// Generar/obtener el registro KPI
					key = kpivevPDDetailw.getVendorid() + "_" + kpivevPDDetailw.getDepartmentid() + "_" +
							(kpivevPDDetailw.getSalestoreid() != null ? kpivevPDDetailw.getSalestoreid().toString() : "") + "_" +
								sdfdate.format(kpivevPDDetailw.getFcm());

					if (kPIvevPDMap.containsKey(key)) {
						kpivevPDw = kPIvevPDMap.get(key);
					}
					else {
						kpivevPDw = new KPIvevPDW();
						kpivevPDw.setVendorid(kpivevPDDetailw.getVendorid());
						kpivevPDw.setDepartmentid(kpivevPDDetailw.getDepartmentid());
						kpivevPDw.setSalestoreid(kpivevPDDetailw.getSalestoreid());
						kpivevPDw.setFcm(kpivevPDDetailw.getFcm());
					}

					// Evaluar el cumplimiento y determinar los atrasos
					if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostReceived.getId())) { // OC Recepcionada
						// JPE 20200720
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId())) { // Despacho Entregado
							if (kpivevPDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
								// JPE 20180822
								if (kpivevPDDetailw.getCourierpendingdate() != null) { // El despacho actual pas� por el estado 'Pendiente de Retiro' (con Courier)
									if (kpivevPDDetailw.getRescheduleresponsibility() == null) { // No se ha indicado un motivo de reagendamiento
										if (kpivevPDDetailw.getCourierdeliverydelayeddays() == null || kpivevPDDetailw.getCourierdeliverydelayeddays() > 0) { // Responsabilidad del proveedor
											// Recibido con Atraso
											kpivevpdtypeid = kpistDelayedReceipt.getId();
											kpivevPDw.setTotalreceiveddelayed(kpivevPDw.getTotalreceiveddelayed() + 1);
										}
										else { // Responsabilidad de Courier
											// Recibido con Atraso por Courier
											kpivevpdtypeid = kpistCourierDelayedReceipt.getId();
											kpivevPDw.setTotalcourierreceiveddelayed(kpivevPDw.getTotalcourierreceiveddelayed() + 1);
										}
									}
									else { // Se indic� un motivo de reagendamiento
										if (kpivevPDDetailw.getRescheduleresponsibility().equals("Proveedor")) { // Responsabilidad del proveedor
											// Recibido con Atraso
											kpivevpdtypeid = kpistDelayedReceipt.getId();
											kpivevPDw.setTotalreceiveddelayed(kpivevPDw.getTotalreceiveddelayed() + 1);
										}
										else { // Responsabilidad de Courier
											// Recibido con Atraso por Courier
											kpivevpdtypeid = kpistCourierDelayedReceipt.getId();
											kpivevPDw.setTotalcourierreceiveddelayed(kpivevPDw.getTotalcourierreceiveddelayed() + 1);
										} 
									}
								}
								else { // El despacho actual no pas� por el estado 'Pendiente de Retiro' (sin Courier)
									// Recibido con Atraso
									kpivevpdtypeid = kpistDelayedReceipt.getId();
									kpivevPDw.setTotalreceiveddelayed(kpivevPDw.getTotalreceiveddelayed() + 1);
								}
								finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
							}
							else { // Entregado en tiempo
								// Entregado
								kpivevpdtypeid = kpistDelivered.getId();
								finefactordays = 0;
								kpivevPDw.setTotaldelivered(kpivevPDw.getTotaldelivered() + 1);
							}
						}
						else { // Despacho no Entregado (cualquier otro estado)
							// Inconsistencia de Estado
							kpivevpdtypeid = kpistInconsistency.getId();
							finefactordays = 0;
							kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostCanceled.getId()) || // OC Cancelada
							 kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostRejected.getId())) { // OC Rechazada
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId())) { // Despacho Entregado
							// Inconsistencia de Estado
							kpivevpdtypeid = kpistInconsistency.getId();
							finefactordays = 0;
							kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
						}
						else { // Despacho no Entregado (cualquier otro estado)
							// JPE 20200720
							if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin Despacho
								if (kpivevPDDetailw.getDelayeddays() > 0) { // Con atraso del despacho
									if (kpivevPDDetailw.getClientrescheduledate() != null &&
									   (kpivevPDDetailw.getVendorrescheduledate() == null ||
									    kpivevPDDetailw.getClientrescheduledate().after(kpivevPDDetailw.getVendorrescheduledate()))) { // OC con Reprogramación del Cliente
										// Responsabilidad del Cliente
										kpivevpdtypeid = kpistResponsability.getId();
										finefactordays = 0;
										kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
									}
									else { // OC no Reprogramada o con Reprogramación del Proveedor
										// Atraso Proveedor
										kpivevpdtypeid = kpistVendorDelay.getId();
										finefactordays = kpivevPDDetailw.getDelayeddays();
										kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
									}
								}
								else { // OC por despachar
									if (kpivevPDDetailw.getVendorrescheduledate() != null &&
									   (kpivevPDDetailw.getClientrescheduledate() == null ||
									   !kpivevPDDetailw.getClientrescheduledate().after(kpivevPDDetailw.getVendorrescheduledate()))) { // OC con Reprogramación del Proveedor
										// Atraso Proveedor
										kpivevpdtypeid = kpistVendorDelay.getId();
										finefactordays = kpivevPDDetailw.getDelayeddays();
										kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
									}
									else { // OC no Reprogramada o con Reprogramación del Cliente
										// Responsabilidad del Cliente
										kpivevpdtypeid = kpistResponsability.getId();
										finefactordays = 0;
										kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
									}
								}
							}
							else { // Con Despacho
								if (kpivevPDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
									if (kpivevPDDetailw.getClientrescheduledate() != null &&
									   (kpivevPDDetailw.getVendorrescheduledate() == null ||
										kpivevPDDetailw.getClientrescheduledate().after(kpivevPDDetailw.getVendorrescheduledate()))) { // Reprograma Cliente
										// Responsabilidad del Cliente
										kpivevpdtypeid = kpistResponsability.getId();
										finefactordays = 0;
										kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
									}
									else { // OC no Reprogramada o Reprograma Proveedor
										// Atraso Proveedor
										kpivevpdtypeid = kpistVendorDelay.getId();
										finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
										kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
									}
								}
								else { // Sin atraso en la entrega
									if (kpivevPDDetailw.getVendorrescheduledate() != null &&
									   (kpivevPDDetailw.getClientrescheduledate() == null ||
									   !kpivevPDDetailw.getClientrescheduledate().after(kpivevPDDetailw.getVendorrescheduledate()))) { // Reprograma Proveedor
										// Atraso Proveedor
										kpivevpdtypeid = kpistVendorDelay.getId();
										finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
										kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
									}
									else { // OC no Reprogramada o Reprograma Cliente
										// Responsabilidad del Cliente
										kpivevpdtypeid = kpistResponsability.getId();
										finefactordays = 0;
										kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
									}
								}
							}
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostReleased.getId()) || // OC Liberada
							 kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostModified.getId())) { // OC Modificada Paris
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin despacho
							// Atraso Proveedor
							kpivevpdtypeid = kpistVendorDelay.getId();
							finefactordays = kpivevPDDetailw.getDelayeddays();
							kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
						}
						else { // Con despacho
							// Inconsistencia de Estado
							kpivevpdtypeid = kpistInconsistency.getId();
							finefactordays = 0;
							kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostAccepted.getId())) { // OC Aceptada
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin Despacho
							// Atraso Proveedor
							kpivevpdtypeid = kpistVendorDelay.getId();
							finefactordays = kpivevPDDetailw.getDelayeddays();
							kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
						}
						// JPE 20200720
						else { // Con Despacho
							if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequestPending.getId()) || // Etiqueta por Solicitar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequested.getId()) || // Etiqueta Solicitada
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstSchedulePending.getId()) || // Pendiente de Agendar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstPickUpPending.getId()) || // Pendiente de Retiro
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDamaged.getId()) || // Da�o
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrong.getId()) || // Equivocado
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTransportReason.getId())) { // Por motivos de transporte
								// Atraso Proveedor
								kpivevpdtypeid = kpistVendorDelay.getId();
								finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstOnRoute.getId()) || // Despacho en ruta
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrongAddress.getId())) { // Dirección err�nea
								if (kpivevPDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
									// Atraso Proveedor
									kpivevpdtypeid = kpistVendorDelay.getId();
									finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
									kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
								}
								else { // Sin atraso en la entrega
									// Responsabilidad del Cliente
									kpivevpdtypeid = kpistResponsability.getId();
									finefactordays = 0;
									kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
								}
							}							
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstClientReason.getId()) || // Por motivos del cliente
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstAbsentClient.getId())) { // Cliente no est�
								// Responsabilidad del Cliente
								kpivevpdtypeid = kpistResponsability.getId();
								finefactordays = 0;
								kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId()) || // Despacho entregado
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstExpectations.getId()) || // Expectativas
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstLost.getId())) { // Extraviado
								// Inconsistencia de Estado
								kpivevpdtypeid = kpistInconsistency.getId();
								finefactordays = 0;
								kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
							}
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostMissingData.getId())) { // Faltan Datos en la OC
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin Despacho
							// Atraso Proveedor
							kpivevpdtypeid = kpistVendorDelay.getId();
							finefactordays = kpivevPDDetailw.getDelayeddays();
							kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
						}
						// JPE 20200720
						else { // Con Despacho
							if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequestPending.getId()) || // Etiqueta por Solicitar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequested.getId()) || // Etiqueta Solicitada
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstSchedulePending.getId()) || // Pendiente de Agendar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstPickUpPending.getId()) || // Pendiente de Retiro
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstOnRoute.getId()) || // Despacho en ruta
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDamaged.getId()) || // Da�o
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrongAddress.getId()) || // Dirección err�nea
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrong.getId()) || // Equivocado
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTransportReason.getId())) { // Por motivos de transporte
								// Atraso Proveedor
								kpivevpdtypeid = kpistVendorDelay.getId();
								finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);								
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstClientReason.getId()) || // Por motivos del cliente
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstAbsentClient.getId())) { // Cliente no est�
								if (kpivevPDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
									// Atraso Proveedor
									kpivevpdtypeid = kpistVendorDelay.getId();
									finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
									kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
								}
								else { // Sin atraso en la entrega
									// Responsabilidad del Cliente
									kpivevpdtypeid = kpistResponsability.getId();
									finefactordays = 0;
									kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
								}
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId()) || // Despacho entregado
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstExpectations.getId()) || // Expectativas
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstLost.getId())) { // Extraviado
								// Inconsistencia de Estado
								kpivevpdtypeid = kpistInconsistency.getId();
								finefactordays = 0;
								kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
							}
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostClientRescheduled.getId())) { // OC con Reprogramación del Cliente
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin Despacho
							if (kpivevPDDetailw.getDelayeddays() > 0) { // Con atraso del despacho
								// Atraso Proveedor
								kpivevpdtypeid = kpistVendorDelay.getId();
								finefactordays = kpivevPDDetailw.getDelayeddays();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
							}
							else { // OC por despachar
								// Responsabilidad del Cliente
								kpivevpdtypeid = kpistResponsability.getId();
								finefactordays = 0;
								kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
							}
						}
						else { // Con Despacho
							// JPE 20200720
							if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequestPending.getId()) || // Etiqueta por Solicitar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequested.getId()) || // Etiqueta Solicitada
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstSchedulePending.getId()) || // Pendiente de Agendar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstPickUpPending.getId()) || // Pendiente de Retiro
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstOnRoute.getId()) || // Despacho en ruta
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstClientReason.getId()) || // Por motivos del cliente
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstAbsentClient.getId())) { // Cliente no est�
								// Responsabilidad del Cliente
								kpivevpdtypeid = kpistResponsability.getId();
								finefactordays = 0;
								kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);								
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDamaged.getId()) || // Da�o
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrong.getId()) || // Equivocado
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTransportReason.getId())) { // Por motivos de transporte 
								// Atraso Proveedor
								kpivevpdtypeid = kpistVendorDelay.getId();
								finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);								
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrongAddress.getId())) { // Dirección err�nea
								if (kpivevPDDetailw.getDeliverydelayeddays() > 0) { // Con atraso en la entrega
									// Atraso Proveedor
									kpivevpdtypeid = kpistVendorDelay.getId();
									finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
									kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
								}
								else { // Sin atraso en la entrega
									// Responsabilidad del Cliente
									kpivevpdtypeid = kpistResponsability.getId();
									finefactordays = 0;
									kpivevPDw.setCustomerresponsabilities(kpivevPDw.getCustomerresponsabilities() + 1);
								}
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId()) || // Despacho entregado
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstExpectations.getId()) || // Expectativas
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstLost.getId())) { // Extraviado
								// Inconsistencia de Estado
								kpivevpdtypeid = kpistInconsistency.getId();
								finefactordays = 0;
								kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
							}
						}
					}
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostVendorRescheduled.getId()) || // OC con Reprogramación del Proveedor
							 kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostWithoutStock.getId())) { // OC Sin Stock
						if (kpivevPDDetailw.getCurrentdeliverystatetypeid() == null) { // Sin Despacho
							// Atraso Proveedor
							kpivevpdtypeid = kpistVendorDelay.getId();
							finefactordays = kpivevPDDetailw.getDelayeddays();
							kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
						}
						else { // Con Despacho
							// JPE 20200720
							if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequestPending.getId()) || // Etiqueta por Solicitar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTagRequested.getId()) || // Etiqueta Solicitada
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstSchedulePending.getId()) || // Pendiente de Agendar
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstPickUpPending.getId()) || // Pendiente de Retiro
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstOnRoute.getId()) || // Despacho en ruta
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDamaged.getId()) || // Da�o
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrongAddress.getId()) || // Dirección err�nea
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstWrong.getId()) || // Equivocado
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstClientReason.getId()) || // Por motivos del cliente
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstTransportReason.getId()) || // Por motivos de transporte
								kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstAbsentClient.getId())) { // Cliente no est�
								// Atraso Proveedor
								kpivevpdtypeid = kpistVendorDelay.getId();
								finefactordays = kpivevPDDetailw.getDeliverydelayeddays();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() + 1);
							}
							else if (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstDelivered.getId()) || // Despacho entregado
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstExpectations.getId()) || // Expectativas
									 kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstLost.getId())) { // Extraviado
								// Inconsistencia de Estado
								kpivevpdtypeid = kpistInconsistency.getId();
								finefactordays = 0;
								kpivevPDw.setInconsistencies(kpivevPDw.getInconsistencies() + 1);
							}
						}
					}
					// JPE 20180816
					else if (kpivevPDDetailw.getCurrentorderstatetypeid().equals(dostLost.getId())) { // OC Extraviada
						// Extrav�o
						kpivevpdtypeid = kpistLost.getId();
						finefactordays = kpivevPDDetailw.getDeliverydelayeddays(); // Las OCs extraviadas deben tener despachos asociados
						kpivevPDw.setTotallosts(kpivevPDw.getTotallosts() + 1);
					}
					
					// JPE 20180822
					if (kpivevPDDetailw.getCurrentdeliverystatetypeid() != null && // Con despacho,
						kpivevpdtypeid.equals(kpistVendorDelay.getId()) && // evaluado hasta ac� como 'Atraso Proveedor' y con despacho en estado
					   (kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstPickUpPending.getId()) || // Pendiente de Retiro o
						kpivevPDDetailw.getCurrentdeliverystatetypeid().equals(dodstOnRoute.getId()))) { // En Ruta
						if (kpivevPDDetailw.getRescheduleresponsibility() == null) { // No se ha indicado un motivo de reagendamiento
							if (kpivevPDDetailw.getCourierdeliverydelayeddays() != null && kpivevPDDetailw.getCourierdeliverydelayeddays() <= 0) { // Responsabilidad de Courier
								// Evaluado ahora como Atraso Courier
								kpivevpdtypeid = kpistCourierDelay.getId();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() - 1);
								kpivevPDw.setTotalcourierdelayed(kpivevPDw.getTotalcourierdelayed() + 1);
							}
						}
						else { // Se indic� un motivo de reagendamiento
							if (kpivevPDDetailw.getRescheduleresponsibility().equals("Courier")) { // Responsabilidad de Courier
								// Evaluado ahora como Atraso Courier
								kpivevpdtypeid = kpistCourierDelay.getId();
								kpivevPDw.setTotalproviderdelayed(kpivevPDw.getTotalproviderdelayed() - 1);
								kpivevPDw.setTotalcourierdelayed(kpivevPDw.getTotalcourierdelayed() + 1);
							}
						}
					}
					
					for (KPIvevPDDetailW detail : detailList.getValue()) {
						detail.setKpivevpdtypeid(kpivevpdtypeid);
						detail.setFinefactordays(finefactordays);
					}

					kpivevPDw.setExecutiondate(new Date());
					kPIvevPDMap.put(key, kpivevPDw);
					kPIvevPDDetailMap.put(detailList.getKey(), detailList.getValue());
				}
			}

			// Almacenar los registros KPI y sus detalles
			for (Map.Entry<String, KPIvevPDW> kpi : kPIvevPDMap.entrySet()) {
				kpivevPDw = kpivevpdserver.addKPIvevPD(kpi.getValue());
				kPIvevPDMap.put(kpi.getKey(), kpivevPDw);
			}

			for (Map.Entry<Long, List<KPIvevPDDetailW>> detailList : kPIvevPDDetailMap.entrySet()) {
				for (KPIvevPDDetailW detail : detailList.getValue()) {
					key = detail.getVendorid() + "_" + detail.getDepartmentid() + "_" +
							(detail.getSalestoreid() != null ? detail.getSalestoreid().toString() : "") + "_" + sdfdate.format(detail.getFcm());

					detail.setKpivevpdid(kPIvevPDMap.get(key).getId());
					kpivevpddetailserver.addKPIvevPDDetail(detail);
				}
			}
			
			// Marcar el proceso como ejecutado
			KPIvevExecutionStateW executionstate = new KPIvevExecutionStateW();
			
			executionstate.setWhen1(new Date());
			executionstate.setType(courier ? "PD Courier" : "PD");
			
			kpivevexecutionstateserverlocal.addKPIvevExecutionState(executionstate);

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVPDStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		isKPIVeVPDStarted = false;
		
		return true;
	}*/

	@TransactionTimeout(value = 550)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doSetKPIvevPDData(String vendorcode, Date since, Date until, Boolean courier) {

		if (isKPIVeVPDStarted) {
			logger.error("Existe otro cálculo de KPI PD en proceso");
			return false;
		}
		
		isKPIVeVPDStarted = true;
		
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		Long vendorid = -1L;

		try {
			
			if(!"-1".equals(vendorcode)) {
				VendorW vendor = vendorserver.getByPropertyAsSingleResult("rut", vendorcode);
				vendorid = vendor.getId();
			}
			
			logger.info("Comenzando cálculo de KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));

			
			// Eliminar KPI PD y sus detalles previos para el proveedor (pueden ser todos) y el per�odo
			kpivevpddetailserver.deleteByVendorAndPeriod(vendorid, since, until, courier);
			kpivevpddetailserver.flush();

			kpivevpdserver.deleteByVendorAndPeriod(vendorid, since, until, courier);
			kpivevpdserver.flush();			

			// Buscar las OC VEV PD para el proveedor (pueden ser todos) y el per�odo
			KPIvevPDDetailW[] kpiDetailData = kpivevpddetailserver.getDataToCalculateKPIByPeriod(vendorid, since, until, courier);
			logger.info("KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad de detalles: " + kpiDetailData.length);
			
			if (kpiDetailData != null && kpiDetailData.length > 0) {
				// Obtener mapa de los estados de orden directa
				DirectOrderStateTypeW[] directOrderStateTypes = directorderstatetypeserver.getAllAsArray();
				HashMap<String, DirectOrderStateTypeW> directOrderStateTypeMap = new HashMap<String, DirectOrderStateTypeW>();
				for (DirectOrderStateTypeW directOrderStateType : directOrderStateTypes) {
					directOrderStateTypeMap.put(directOrderStateType.getCode(), directOrderStateType);
				}
				
				// Obtener mapa de los estados de despacho de orden directa
				DODeliveryStateTypeW[] doDeliveryStateTypes = dodeliverystatetypeserver.getAllAsArray();
				HashMap<String, DODeliveryStateTypeW> doDeliveryStateTypeMap = new HashMap<String, DODeliveryStateTypeW>();
				for (DODeliveryStateTypeW doDeliveryStateType : doDeliveryStateTypes) {
					doDeliveryStateTypeMap.put(doDeliveryStateType.getCode(), doDeliveryStateType);
				}
				
				// Obtener mapa de los tipos de KPI VeV PD
				KPIvevPDTypeW[] kpiVeVPDTypes = kpivevpdtypeserver.getAllAsArray();
				HashMap<Long, KPIvevPDTypeW> kpiVeVPDTypeMap = new HashMap<Long, KPIvevPDTypeW>();
				KPIvevPDTypeW kpistCourierDelayedReceipt = null;
				KPIvevPDTypeW kpistResponsability = null;
				KPIvevPDTypeW kpistVendorDelay = null;	
				KPIvevPDTypeW kpistCourierDelay = null;
				for (KPIvevPDTypeW kpiVeVPDType : kpiVeVPDTypes) {
					kpiVeVPDTypeMap.put(kpiVeVPDType.getId(), kpiVeVPDType);
					if (kpiVeVPDType.getCode().equals("RECIBO_ATRASO_COURIER")) {
						kpistCourierDelayedReceipt = kpiVeVPDType;
					}
					if (kpiVeVPDType.getCode().equals("RESPONSABILIDAD_CLIENTE")) {
						kpistResponsability = kpiVeVPDType;
					}
					if (kpiVeVPDType.getCode().equals("ATRASO_PROVEEDOR")) {
						kpistVendorDelay = kpiVeVPDType;
					}
					if (kpiVeVPDType.getCode().equals("ATRASO_COURIER")) {
						kpistCourierDelay = kpiVeVPDType;
					}
				}
				
				// Obtener mapa de las reglas generales de KPI VeV PD
				KPIvevPDRuleW[] kpiVeVPDRules = kpivevpdruleserver.getAllAsArray();
				HashMap<String, Long> kpiVeVPDRuleMap = new HashMap<String, Long>();
				for (KPIvevPDRuleW kpiVeVPDRule : kpiVeVPDRules) {
					kpiVeVPDRuleMap.put(kpiVeVPDRule.getDirectorderstatetypeid() + "_" + kpiVeVPDRule.getCurrentdodeliverystatetypeid() + "_INTIME", kpiVeVPDRule.getIntimekpivevpdtypeid());
					kpiVeVPDRuleMap.put(kpiVeVPDRule.getDirectorderstatetypeid() + "_" + kpiVeVPDRule.getCurrentdodeliverystatetypeid() + "_ONTIME", kpiVeVPDRule.getOntimekpivevpdtypeid());
					kpiVeVPDRuleMap.put(kpiVeVPDRule.getDirectorderstatetypeid() + "_" + kpiVeVPDRule.getCurrentdodeliverystatetypeid() + "_DELAYED", kpiVeVPDRule.getDelayedkpivevpdtypeid());
				}
				
				// Reglas especiales
				String acceptedAndOnRouteCase = directOrderStateTypeMap.get("ACEPTADA").getId() + "_" + doDeliveryStateTypeMap.get("EN_RUTA").getId() + "_";
				String receivedAndDelayedCourierDeliveryCase = directOrderStateTypeMap.get("RECEPCIONADA").getId() + "_" + doDeliveryStateTypeMap.get("REC_CONFORME").getId() + "_DELAYED";
				String rejectedAndNotDeliveredCase = directOrderStateTypeMap.get("RECHAZADA").getId() + "_";
				String delayedAndPickUpPendingCase = doDeliveryStateTypeMap.get("PEND_RETIRO").getId() + "_";
				String delayedAndOnRouteCase = doDeliveryStateTypeMap.get("EN_RUTA").getId() + "_";
				
				// Obtener mapa de los detalles agrupados por OC
				HashMap<Long, List<KPIvevPDDetailW>> kPIvevPDDetailMap = new HashMap<Long, List<KPIvevPDDetailW>>();
				List<KPIvevPDDetailW> kPIvevPDDetailList = null;
				for (KPIvevPDDetailW detail : kpiDetailData) {
					if (kPIvevPDDetailMap.containsKey(detail.getOrdernumber())) {
						kPIvevPDDetailList = kPIvevPDDetailMap.get(detail.getOrdernumber());
					}
					else {
						kPIvevPDDetailList = new ArrayList<KPIvevPDDetailW>();
					}

					kPIvevPDDetailList.add(detail);
					kPIvevPDDetailMap.put(detail.getOrdernumber(), kPIvevPDDetailList);
				}

				HashMap<String, KPIvevPDW> kpiVeVPDMap = new HashMap<String, KPIvevPDW>();
				KPIvevPDW kpiVeVPDW = null;
				KPIvevPDDetailW kpiVevPDDetailW = null;
				KPIvevPDTypeW kpiVeVPDType = null;
				Long kpiVeVPDTypeId = null;
				Integer delayedDays = 0;
				Integer fineFactorDays = 0;
				String key = null;
				String ruleType = null;
				for (Map.Entry<Long, List<KPIvevPDDetailW>> detailList : kPIvevPDDetailMap.entrySet()) {
					kpiVevPDDetailW = detailList.getValue().get(0);

					// Generar/obtener el registro KPI
					key = kpiVevPDDetailW.getVendorid() + "_" + kpiVevPDDetailW.getDepartmentid() + "_" +
							(kpiVevPDDetailW.getSalestoreid() != null ? kpiVevPDDetailW.getSalestoreid().toString() : "") + "_" +
								sdfdate.format(kpiVevPDDetailW.getFcm());

					if (kpiVeVPDMap.containsKey(key)) {
						kpiVeVPDW = kpiVeVPDMap.get(key);
					}
					else {
						kpiVeVPDW = new KPIvevPDW();
						kpiVeVPDW.setVendorid(kpiVevPDDetailW.getVendorid());
						kpiVeVPDW.setDepartmentid(kpiVevPDDetailW.getDepartmentid());
						kpiVeVPDW.setSalestoreid(kpiVevPDDetailW.getSalestoreid());
						kpiVeVPDW.setFcm(kpiVevPDDetailW.getFcm());
					}
					
					// Determinar el tipo de regla a emplear seg�n el estado de la orden y los d�as de atraso
					ruleType = kpiVevPDDetailW.getCurrentorderstatetypeid() + "_" + kpiVevPDDetailW.getCurrentdeliverystatetypeid() + "_";
					
					// Determinar cómo considerar los días de atraso (hasta la fecha del último despacho o hasta la fecha actual)
					// JPE 20201109: Se penalizará al proveedor que no actualice despachos 'En Ruta' luego de la fecha de reprogramación
					delayedDays = kpiVevPDDetailW.getCurrentdeliverystatetypeid() == 0 || ruleType.startsWith(acceptedAndOnRouteCase) ?
										    kpiVevPDDetailW.getDelayeddays() :			// Sin despacho o 'Aceptada/En Ruta'
										    kpiVevPDDetailW.getDeliverydelayeddays();	// Con despacho en otra combinación

					if (delayedDays < 0) {
						ruleType += "INTIME";
					}
					else if (delayedDays > 0) {
						ruleType += "DELAYED";
					}
					else {
						ruleType += "ONTIME";
					}
					
					// Evaluar el cumplimiento seg�n las reglas generales
					kpiVeVPDTypeId = kpiVeVPDRuleMap.get(ruleType);
					
					// Casos especiales
					// Orden recepcionada y despacho entregado con atraso por responsabilidad de Courier
					if (ruleType.equals(receivedAndDelayedCourierDeliveryCase)) {
						// JPE 20180822
						if (kpiVevPDDetailW.getCourierpendingdate() != null) { // El despacho actual pas� por el estado 'Pendiente de Retiro' (con Courier)
							if (kpiVevPDDetailW.getRescheduleresponsibility() == null) { // No se ha indicado un motivo de reagendamiento
								if (kpiVevPDDetailW.getCourierdeliverydelayeddays() != null && kpiVevPDDetailW.getCourierdeliverydelayeddays() <= 0) { // Responsabilidad de Courier
									// Recibido con Atraso por Courier
									kpiVeVPDTypeId = kpistCourierDelayedReceipt.getId();
								}
							}
							else { // Se indic� un motivo de reagendamiento
								if (!kpiVevPDDetailW.getRescheduleresponsibility().equals("Proveedor")) { // Responsabilidad de Courier
									// Recibido con Atraso por Courier
									kpiVeVPDTypeId = kpistCourierDelayedReceipt.getId();
								} 
							}
						}
					}
					// Orden rechazada y despacho no entregado
					else if (ruleType.startsWith(rejectedAndNotDeliveredCase) &&
							 !ruleType.contains(doDeliveryStateTypeMap.get("REC_CONFORME").getId() + "_")) {
						// JPE 20200720
						if (ruleType.contains("DELAYED")) { // Con atraso
							if (kpiVevPDDetailW.getClientrescheduledate() != null &&
							   (kpiVevPDDetailW.getVendorrescheduledate() == null ||
								kpiVevPDDetailW.getClientrescheduledate().after(kpiVevPDDetailW.getVendorrescheduledate()))) { // OC con Reprogramación del Cliente
								// Responsabilidad del Cliente
								kpiVeVPDTypeId = kpistResponsability.getId();
							}
						}
						else { // Sin atraso
							if (kpiVevPDDetailW.getVendorrescheduledate() != null &&
							   (kpiVevPDDetailW.getClientrescheduledate() == null ||
							   !kpiVevPDDetailW.getClientrescheduledate().after(kpiVevPDDetailW.getVendorrescheduledate()))) { // OC con Reprogramación del Proveedor
								// Atraso Proveedor
								kpiVeVPDTypeId = kpistVendorDelay.getId();
							}
						}	
					}
					
					// JPE 20180822
					// Orden con despacho Pendiente de Retiro o En Ruta y con atraso por responsabilidad de Courier
					if (kpiVeVPDTypeId.equals(kpistVendorDelay.getId()) && // Evaluado hasta ac� como 'Atraso Proveedor' y con despacho en estado
					   (ruleType.contains(delayedAndPickUpPendingCase) || // Pendiente de Retiro o
					    ruleType.contains(delayedAndOnRouteCase))) { // En Ruta
						if (kpiVevPDDetailW.getRescheduleresponsibility() == null) { // No se ha indicado un motivo de reagendamiento
							if (kpiVevPDDetailW.getCourierdeliverydelayeddays() != null && kpiVevPDDetailW.getCourierdeliverydelayeddays() <= 0) { // Responsabilidad de Courier
								// Evaluado ahora como Atraso Courier
								kpiVeVPDTypeId = kpistCourierDelay.getId();
							}
						}
						else { // Se indic� un motivo de reagendamiento
							if (kpiVevPDDetailW.getRescheduleresponsibility().equals("Courier")) { // Responsabilidad de Courier
								// Evaluado ahora como Atraso Courier
								kpiVeVPDTypeId = kpistCourierDelay.getId();
							}
						}
					}
					
					kpiVeVPDType = kpiVeVPDTypeMap.get(kpiVeVPDTypeId);
					
					// Determinar los atrasos
					fineFactorDays = kpiVeVPDType.getFine() ? delayedDays : 0;
					
					// Totalizadores
					// Entregado
					if (kpiVeVPDType.getCode().equals("ENTREGADO")) {
						kpiVeVPDW.setTotaldelivered(kpiVeVPDW.getTotaldelivered() + 1);
					}
					// Recibido con Atraso
					else if (kpiVeVPDType.getCode().equals("RECIBO_ATRASO")) {
						kpiVeVPDW.setTotalreceiveddelayed(kpiVeVPDW.getTotalreceiveddelayed() + 1);
					}
					// Atraso Proveedor
					else if (kpiVeVPDType.getCode().equals("ATRASO_PROVEEDOR")) {
						kpiVeVPDW.setTotalproviderdelayed(kpiVeVPDW.getTotalproviderdelayed() + 1);
					}
					// Inconsistencia de Estado
					else if (kpiVeVPDType.getCode().equals("INCONSISTENCIA_ESTADO")) {
						kpiVeVPDW.setInconsistencies(kpiVeVPDW.getInconsistencies() + 1);
					}
					// Responsabilidad del Cliente
					else if (kpiVeVPDType.getCode().equals("RESPONSABILIDAD_CLIENTE")) {
						kpiVeVPDW.setCustomerresponsabilities(kpiVeVPDW.getCustomerresponsabilities() + 1);
					}
					// Recibido con Atraso Courier
					else if (kpiVeVPDType.getCode().equals("RECIBO_ATRASO_COURIER")) {
						kpiVeVPDW.setTotalcourierreceiveddelayed(kpiVeVPDW.getTotalcourierreceiveddelayed() + 1);
					}
					// Atraso Courier
					else if (kpiVeVPDType.getCode().equals("ATRASO_COURIER")) {
						kpiVeVPDW.setTotalcourierdelayed(kpiVeVPDW.getTotalcourierdelayed() + 1);
					}
					// Extrav�o
					else if (kpiVeVPDType.getCode().equals("EXTRAVIO")) {
						kpiVeVPDW.setTotallosts(kpiVeVPDW.getTotallosts() + 1);
					}
					// En Proceso de Despacho
					else if (kpiVeVPDType.getCode().equals("EN_DESPACHO")) {
						kpiVeVPDW.setTotaldelivering(kpiVeVPDW.getTotaldelivering() + 1);
					}
					// Nota de Crédito
					else if (kpiVeVPDType.getCode().equals("NOTA_CREDITO")) {
						kpiVeVPDW.setCreditnotes(kpiVeVPDW.getCreditnotes() + 1);
					}
					
					for (KPIvevPDDetailW detail : detailList.getValue()) {
						detail.setKpivevpdtypeid(kpiVeVPDTypeId);
						detail.setFinefactordays(fineFactorDays);
					}

					kpiVeVPDW.setExecutiondate(new Date());
					kpiVeVPDMap.put(key, kpiVeVPDW);
					kPIvevPDDetailMap.put(detailList.getKey(), detailList.getValue());
				}
				
				// Almacenar los registros KPI y sus detalles
				for (Map.Entry<String, KPIvevPDW> kpi : kpiVeVPDMap.entrySet()) {
					kpiVeVPDW = kpivevpdserver.addKPIvevPD(kpi.getValue());
					kpiVeVPDMap.put(kpi.getKey(), kpiVeVPDW);
				}

				for (Map.Entry<Long, List<KPIvevPDDetailW>> detailList : kPIvevPDDetailMap.entrySet()) {
					for (KPIvevPDDetailW detail : detailList.getValue()) {
						key = detail.getVendorid() + "_" + detail.getDepartmentid() + "_" +
								(detail.getSalestoreid() != null ? detail.getSalestoreid().toString() : "") + "_" + sdfdate.format(detail.getFcm());

						detail.setKpivevpdid(kpiVeVPDMap.get(key).getId());
						kpivevpddetailserver.addKPIvevPDDetail(detail);
					}
				}
			}

			// Marcar el proceso como ejecutado
			KPIvevExecutionStateW executionstate = new KPIvevExecutionStateW();
			
			executionstate.setWhen1(new Date());
			executionstate.setType(courier ? "PD Courier" : "PD");
			
			kpivevexecutionstateserverlocal.addKPIvevExecutionState(executionstate);

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVPDStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de KPI PD" + (courier ? " Courier" : "") + " (Vendor: " + vendorid.longValue() + ") desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		isKPIVeVPDStarted = false;
		
		return true;
	}

	public BaseResultDTO doResetKPIvevPDData(KPIResetInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		// Valida proveedor
		Long vendorid = -1L;
		if (!initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			
			vendorid = vendors[0].getId();
		}

		try {
			Calendar today = Calendar.getInstance();
			
			Date since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			Date until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());

			// Validar que la fecha escogida sea anterior o igual al d�a actual
			if (until.after(today.getTime())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1103");
			}

			// Validar que el proveedor escogido posea órdenes de tipo VeV PD en la FCM indicada
			int count = kpivevpddetailserver.getCountDataToCalculateKPIByPeriod(vendorid, since, until, initParamDTO.getCourier());
			if (count <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1105");
			}

			if (isKPIVeVPDStarted) {
				logger.error("Existe otro cálculo de KPI PD en proceso");
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Existe otro cálculo de KPI PD en proceso. Espere unos minutos y vuelva a intentarlo", false);
			}
			
			// Calcular evaluaciones
			if (!doSetKPIvevPDData(initParamDTO.getVendorcode(), since, until, initParamDTO.getCourier())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}

		} catch (Exception e) {
			e.getStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevPDTypeArrayResultDTO getKPIvevPDTypes() {
		KPIvevPDTypeArrayResultDTO resultDTO = new KPIvevPDTypeArrayResultDTO();

		KPIvevPDTypeW[] typeArray = null;
		KPIvevPDTypeDTO[] typedtoArray = null;

		try {
			typeArray = kpivevpdtypeserver.getAllAsArray();

			typedtoArray = new KPIvevPDTypeDTO[typeArray.length];
			BeanExtenderFactory.copyProperties(typeArray, typedtoArray, KPIvevPDTypeDTO.class);

			resultDTO.setTypes(typedtoArray);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevPDReportArrayResultDTO getKPIvevPDDataForReport(KPIgeneralInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevPDReportArrayResultDTO resultDTO = new KPIvevPDReportArrayResultDTO();
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
				
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) {
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("") || initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			Float meta = null;
			try {
				meta = Float.parseFloat(B2BSystemPropertiesUtil.getProperty("meta"));
			} catch (Exception e) {
				logger.error("No se encontr� la propiedad 'meta'. Colocando valor por defecto: 95.0");
				meta = 95F;
			}

			resultDTO.setMeta(meta);

			KPIvevPDReportDataDTO[] kpivevpdReportData = kpivevpdserver.getKPIGeneralData(meta, vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier(), initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);
			
			KPIvevPDReportDataDTO[] kpivevpdReportDataTotal = kpivevpdserver.getKPIGeneralDataTotal(meta, vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier(), initParamDTO.getRows(), initParamDTO.getPageNumber(), null, isPaginated);

			KPIvevPDComplianceDTO compliancedata = null;
			int totalreg = 0;
			if (isByFilter) {
				compliancedata = kpivevpdserver.countKPIGeneralData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier());
				
				if (compliancedata != null) {
					totalreg = compliancedata.getTotalreg().intValue();
					
					if (compliancedata.getTotal() != 0) {
						resultDTO.setCompliancerate((compliancedata.getTotaldelivered() * 100.0) / compliancedata.getTotalevaluated());
						resultDTO.setCourierdefaultrate((compliancedata.getTotalcourierdeliveries() * 100.0) / compliancedata.getTotalevaluated());
						resultDTO.setVendordefaultrate(((compliancedata.getTotalevaluated() - compliancedata.getTotaldelivered() - compliancedata.getTotalcourierdeliveries()) * 100.0) / compliancedata.getTotalevaluated());
					} else {
						resultDTO.setCompliancerate(0.0);
						resultDTO.setCourierdefaultrate(0.0);
						resultDTO.setVendordefaultrate(0.0);
					}
				}
				
				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				if (vendorid.longValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
					pageInfo.setRows(kpivevpdReportData.length);
				}
				else {
					pageInfo.setRows(kpivevpdReportDataTotal.length);
				}
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setKpivevpdReportData(kpivevpdReportData);
			resultDTO.setKpivevpdReportDataTotal(kpivevpdReportDataTotal);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevPDDetailReportArrayResultDTO getKPIvevPDDetailData(KPIdetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevPDDetailReportArrayResultDTO resultDTO = new KPIvevPDDetailReportArrayResultDTO();

		int totalreg = 0;
		
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().length() == 0 || initParamDTO.getSince().equals("-1")) {
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().length() == 0 || initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			KPIvevPDDetailReportDTO[] kpivevpddetailreport = kpivevpddetailserver.getKPIvevPDDetailData(vendor.getId(), initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until, initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);

			if (kpivevpddetailreport == null || kpivevpddetailreport.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1102");
			}

			if (isByFilter) {
				totalreg = kpivevpddetailserver.countKPIvevPDDetailData(vendor.getId(), initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until);

				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(kpivevpddetailreport.length);
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setKpivevpddetailreport(kpivevpddetailreport);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevPDSummaryArrayResultDTO getKPIvevPDSummaryForReport(KPISummaryInitParamDTO initParamDTO) {
		KPIvevPDSummaryArrayResultDTO resultDTO = new KPIvevPDSummaryArrayResultDTO();
		
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) { 
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("")|| initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();
			
			KPIvevPDSummaryDTO[] summary = kpivevpdserver.getKPIvevPDSummary(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier(), initParamDTO.getOrderby());

			if (summary != null && summary.length > 0) {
				KPIvevPDSummaryTotalDTO total = kpivevpdserver.countKPIvevPDSummary(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier());
			
				resultDTO.setTotal(total);
			}

			resultDTO.setSummary(summary);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevPDSummaryDetailArrayResultDTO getKPIvevPDSummaryDetailData(KPISummaryDetailInitParamDTO initParamDTO, boolean isByFilter, boolean isPaginated) {
		KPIvevPDSummaryDetailArrayResultDTO resultDTO = new KPIvevPDSummaryDetailArrayResultDTO();

		int totalreg = 0;
		
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParamDTO.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {
			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().equals("") || initParamDTO.getSince().equals("-1")) { 
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().equals("")|| initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();
			
			KPIvevPDSummaryDetailReportDTO[] summarydetail = kpivevpddetailserver.getKPIvevPDSummaryDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier(), initParamDTO.getRows(), initParamDTO.getPageNumber(), initParamDTO.getOrderby(), isPaginated);

			if (summarydetail == null || summarydetail.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1102");
			}

			if (isByFilter) {
				totalreg = kpivevpddetailserver.countKPIvevPDSummaryDetailData(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), since, until, initParamDTO.getCourier());

				int rows = initParamDTO.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(summarydetail.length);
				pageInfo.setTotalpages((totalreg % rows) != 0 ? (totalreg / rows) + 1 : (totalreg / rows));
				pageInfo.setTotalrows(totalreg);

				resultDTO.setPageinfo(pageInfo);
			}

			resultDTO.setSummarydetail(summarydetail);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public FileDownloadInfoResultDTO getKPIvevPDDetailDataSourceReport(KPIdetailInitParamDTO initParamDTO, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();

		Long t1 = System.currentTimeMillis();
		System.err.println("**************Inicio dto fte KPI VeV PD");

		Long vendorid = -1L;
		try {
			// Obtener el proveedor
			String vendorname = "";
			if (!initParamDTO.getVendorcode().equals(String.valueOf(RegionalLogisticConstants.getInstance().getINT_TODOS()))) {
				// Valida proveedor
				VendorW[] vendors = null;
				VendorW vendor;
				try{
					vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
					
				}	catch (Exception e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				if(vendors == null || vendors.length == 0){
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
				}
				vendor = vendors[0];
				vendorname = vendor.getName() + " ";
				vendorid = vendor.getId();
			}

			Date since = null;
			Date until = null;

			if (initParamDTO.getSince() == null || initParamDTO.getSince().length() == 0 || initParamDTO.getSince().equals("-1")) {
				since = new Date();
			}
			else {
				since = DateConverterFactory2.StrToDate(initParamDTO.getSince());
			}

			if (initParamDTO.getUntil() == null || initParamDTO.getUntil().length() == 0 || initParamDTO.getUntil().equals("-1")) {
				until = new Date();
			}
			else {
				until = DateConverterFactory2.StrToDate(initParamDTO.getUntil());
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(since);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			since = cal.getTime();
		
			cal.setTime(until);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			until = cal.getTime();

			resultDTO = kpivevpddetailserver.getKPIvevPDDetailDataSourceReport(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getSalestoreids(), initParamDTO.getKpitypeids(), since, until, vendorname, userKey, initParamDTO.getCourier());

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		Long t2 = System.currentTimeMillis();
		System.err.println("**************Fin dto fte KPI VeV PD [tiempo: " + +(t2 - t1) / 1000 + "]");

		return resultDTO;
	}

	// Multas por Incumplimiento de entrega
	@TransactionTimeout(value = 550)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean doSetKPIvevComplianceData() {
		
		try {
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int day1 = kpivevparameterserverlocal.getByPropertyAsSingleResult("code", "COMPLIANCE_EXEC_DAY").getValue().intValue();
			
			Calendar since = null;
			Calendar until = null;
			boolean executed = true;
			if (day <= day1) {
				// En este caso hay que recalcular el mes anterior
				since = Calendar.getInstance();
				since.add(Calendar.MONTH, -1);							
				since.set(Calendar.DAY_OF_MONTH, 1);
				since.set(Calendar.HOUR_OF_DAY, 0);
				since.set(Calendar.MINUTE, 0);
				since.set(Calendar.SECOND, 0);
				since.set(Calendar.MILLISECOND, 0);
				
				until = Calendar.getInstance();
				until.add(Calendar.MONTH, -1);
				until.set(Calendar.DAY_OF_MONTH, until.getActualMaximum(Calendar.DAY_OF_MONTH));
				until.set(Calendar.HOUR_OF_DAY, 0);
				until.set(Calendar.MINUTE, 0);
				until.set(Calendar.SECOND, 0);
				until.set(Calendar.MILLISECOND, 0);
				
				executed = doSetKPIvevCDComplianceData(since.getTime(), until.getTime()) && doSetKPIvevPDComplianceData(since.getTime(), until.getTime());
			}
			
			// Fecha de b�squeda de información, que corresponder� a todo el mes transcurrido
			if (executed) {
				since = Calendar.getInstance();
				since.set(Calendar.DAY_OF_MONTH, 1);
				since.set(Calendar.HOUR_OF_DAY, 0);
				since.set(Calendar.MINUTE, 0);
				since.set(Calendar.SECOND, 0);
				since.set(Calendar.MILLISECOND, 0);
				
				until = Calendar.getInstance();
				until.set(Calendar.HOUR_OF_DAY, 0);
				until.set(Calendar.MINUTE, 0);
				until.set(Calendar.SECOND, 0);
				until.set(Calendar.MILLISECOND, 0);
				
				executed = doSetKPIvevCDComplianceData(since.getTime(), until.getTime()) && doSetKPIvevPDComplianceData(since.getTime(), until.getTime());
			}
			
			if (executed) {
				// Marcar el proceso como ejecutado
				KPIvevExecutionStateW executionstate = new KPIvevExecutionStateW();
				
				executionstate.setWhen1(new Date());
				executionstate.setType("CUMPLIMIENTOS");
				
				kpivevexecutionstateserverlocal.addKPIvevExecutionState(executionstate);
			}
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return false;
		}

		return true;		
	}
		
	public boolean doSetKPIvevCDComplianceData(Date since, Date until) {

		if (isKPIVeVCDComplianceStarted) {
			logger.error("Existe otro cálculo de Cumplimientos de entrega CD en proceso");
			return false;
		}
		
		isKPIVeVCDComplianceStarted = true;
		
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("Comenzando cálculo de Cumplimientos de entrega CD desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		try {
			// Eliminar los cálculos previos de cumplimientos de entrega CD para el mismo per�odo
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("since", "since", since);
			properties[1] = new PropertyInfoDTO("vevtype", "vevtype", "VEVCD");

			KPIvevFinePeriodW[] kpiPreviousFinePeriods = kpivevfineperiodserver.getByPropertiesAsArray(properties);
			if (kpiPreviousFinePeriods != null && kpiPreviousFinePeriods.length > 0) {
				kpivevfineserver.deleteByProperty("kpivevfineperiod.id", kpiPreviousFinePeriods[0].getId());
				kpivevfineserver.flush();

				kpivevfineperiodserver.deleteByProperty("id", kpiPreviousFinePeriods[0].getId());
				kpivevfineperiodserver.flush();
			}

			// Registrar per�odo de multa para CD
			Date now = new Date();

			KPIvevFinePeriodW kpiFinePeriod = new KPIvevFinePeriodW();
			kpiFinePeriod.setExecutiondate(now);
			kpiFinePeriod.setSince(since);
			kpiFinePeriod.setUntil(until);
			kpiFinePeriod.setVevtype("VEVCD");

			kpiFinePeriod = kpivevfineperiodserver.addKPIvevFinePeriod(kpiFinePeriod);

			// Obtener los cumplimientos de entrega del mes para OC VEV CD (evaluadas como 'Recibo con Atraso' o 'Atraso Proveedor')
			KPIvevFineW[] kpiCDFines = kpivevfineserver.getKPIvevCDCompliancesByPeriod(since, until);
			logger.info("Cumplimientos de entrega CD desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad: " + kpiCDFines.length);
			
			// Almacenar los cumplimientos de entrega vincul�ndolos con el per�odo de multas correspondiente
			if (kpiCDFines != null && kpiCDFines.length > 0) {
				for (KPIvevFineW kpiCDFine : kpiCDFines) {
					kpiCDFine.setKpivevfineperiodid(kpiFinePeriod.getId());

					kpivevfineserver.addKPIvevFine(kpiCDFine);
				}
			}

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVCDComplianceStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de Cumplimientos de entrega CD desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		isKPIVeVCDComplianceStarted = false;
		
		return true;
	}

	public boolean doSetKPIvevPDComplianceData(Date since, Date until) {

		if (isKPIVeVPDComplianceStarted) {
			logger.error("Existe otro cálculo de Cumplimientos de entrega PD  en proceso");
			return false;
		}
		
		isKPIVeVPDComplianceStarted = true;
		
		SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("Comenzando cálculo de Cumplimientos de entrega PD  desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		try {
			// Eliminar los cálculos previos de cumplimientos de entrega PD para el mismo per�odo
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("since", "since", since);
			properties[1] = new PropertyInfoDTO("vevtype", "vevtype", "VEVPD");

			KPIvevFinePeriodW[] kpiPreviousFinePeriods = kpivevfineperiodserver.getByPropertiesAsArray(properties);
			if (kpiPreviousFinePeriods != null && kpiPreviousFinePeriods.length > 0) {
				kpivevfineserver.deleteByProperty("kpivevfineperiod.id", kpiPreviousFinePeriods[0].getId());
				kpivevfineserver.flush();

				kpivevfineperiodserver.deleteByProperty("id", kpiPreviousFinePeriods[0].getId());
				kpivevfineperiodserver.flush();
			}

			// Registrar per�odo de multa para PD
			Date now = new Date();

			KPIvevFinePeriodW kpiFinePeriod = new KPIvevFinePeriodW();
			kpiFinePeriod.setExecutiondate(now);
			kpiFinePeriod.setSince(since);
			kpiFinePeriod.setUntil(until);
			kpiFinePeriod.setVevtype("VEVPD");

			kpiFinePeriod = kpivevfineperiodserver.addKPIvevFinePeriod(kpiFinePeriod);

			// Obtener los cumplimientos de entrega del mes para OC VEV PD (evaluadas como 'Recibo con Atraso', 'Recibido con Atraso Courier', 
			// 'Atraso Proveedor', 'Atraso Courier' o 'En proceso de despacho')
			KPIvevFineW[] kpiPDFines = kpivevfineserver.getKPIvevPDCompliancesByPeriod(since, until);
			logger.info("Cumplimientos de entrega PD desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until) + ". Cantidad: " + kpiPDFines.length);

			// Almacenar los cumplimientos de entrega vincul�ndolos con el per�odo de multas correspondiente
			if (kpiPDFines != null && kpiPDFines.length > 0) {
				for (KPIvevFineW kpiPDFine : kpiPDFines) {
					kpiPDFine.setKpivevfineperiodid(kpiFinePeriod.getId());

					kpivevfineserver.addKPIvevFine(kpiPDFine);
				}
			}

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			isKPIVeVPDComplianceStarted = false;
			return false;
		}

		logger.info("Terminando cálculo de Cumplimientos de entrega PD desde " + sdfdate.format(since) + " hasta " + sdfdate.format(until));
		
		isKPIVeVPDComplianceStarted = false;
		
		return true;
	}

	public BaseResultDTO doResetKPIvevCDComplianceData(KPIvevComplianceInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			Calendar today = Calendar.getInstance();
			
			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar since = Calendar.getInstance();
			since.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			since.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			since.set(Calendar.DAY_OF_MONTH, 1);
			since.set(Calendar.HOUR_OF_DAY, 0);
			since.set(Calendar.MINUTE, 0);
			since.set(Calendar.SECOND, 0);
			since.set(Calendar.MILLISECOND, 0);

			// Validar que el per�odo escogido corresponda a lo más al mes y a�o en curso
			if (since.after(today)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1101");
			}

			Calendar until = Calendar.getInstance();
			until.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			until.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			until.set(Calendar.DAY_OF_MONTH, today.before(until) ? today.get(Calendar.DAY_OF_MONTH) : until.getActualMaximum(Calendar.DAY_OF_MONTH));
			until.set(Calendar.HOUR_OF_DAY, 0);
			until.set(Calendar.MINUTE, 0);
			until.set(Calendar.SECOND, 0);
			until.set(Calendar.MILLISECOND, 0);

			// Calcular cumplimientos de entrega CD
			if (isKPIVeVCDComplianceStarted) {
				logger.error("Existe otro cálculo de Cumplimientos de entrega CD en proceso");
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Existe otro cálculo de Cumplimientos de entrega CD en proceso. Espere unos minutos y vuelva a intentarlo", false);
			}
			
			if (!doSetKPIvevCDComplianceData(since.getTime(), until.getTime())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}	

		} catch (Exception e) {
			e.getStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public BaseResultDTO doResetKPIvevPDComplianceData(KPIvevComplianceInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			Calendar today = Calendar.getInstance();
			
			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar since = Calendar.getInstance();
			since.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			since.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			since.set(Calendar.DAY_OF_MONTH, 1);
			since.set(Calendar.HOUR_OF_DAY, 0);
			since.set(Calendar.MINUTE, 0);
			since.set(Calendar.SECOND, 0);
			since.set(Calendar.MILLISECOND, 0);

			// Validar que el per�odo escogido corresponda a lo más al mes y a�o en curso
			if (since.after(today)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1101");
			}

			Calendar until = Calendar.getInstance();
			until.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			until.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			until.set(Calendar.DAY_OF_MONTH, today.before(until) ? today.get(Calendar.DAY_OF_MONTH) : until.getActualMaximum(Calendar.DAY_OF_MONTH));
			until.set(Calendar.HOUR_OF_DAY, 0);
			until.set(Calendar.MINUTE, 0);
			until.set(Calendar.SECOND, 0);
			until.set(Calendar.MILLISECOND, 0);

			// Calcular cumplimientos de entrega PD
			if (isKPIVeVPDComplianceStarted) {
				logger.error("Existe otro cálculo de Cumplimientos de entrega PD en proceso");
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "Existe otro cálculo de Cumplimientos de entrega PD en proceso. Espere unos minutos y vuelva a intentarlo", false);
			}
				
			if (!doSetKPIvevPDComplianceData(since.getTime(), until.getTime())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}

		} catch (Exception e) {
			e.getStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevFineReportArrayResultDTO getKPIvevCDFineDataForReport(KPIvevFineInitParamDTO initParamDTO) {
		KPIvevFineReportArrayResultDTO resultDTO = new KPIvevFineReportArrayResultDTO();

		// Valida proveedor
		Long vendorid = -1L;
		if (!initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			
			vendorid = vendors[0].getId();
		}
		
		try {
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer d�a de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes d�as de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			cal.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();

			cal.add(Calendar.MONTH, 1);
			Date until = cal.getTime();
			
			// Proveedores y totales de multas
			KPIvevVendorDimensionDTO[] vendors = kpivevcddetailserver.getVendorsByKPIvevCDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine);

			// Departamentos
			KPIvevDepartmentDimensionDTO[] departments = kpivevcddetailserver.getDepartmentsByKPIvevCDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until);

			// Multas y cumplimiento
			KPIvevFineDataDTO[] finedata = kpivevcddetailserver.getFineDataByKPIvevCDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine);
			
			resultDTO.setVendors(vendors);
			resultDTO.setDepartments(departments);
			resultDTO.setFinedata(finedata);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevFineReportArrayResultDTO getKPIvevPDFineDataForReport(KPIvevFineInitParamDTO initParamDTO) {
		KPIvevFineReportArrayResultDTO resultDTO = new KPIvevFineReportArrayResultDTO();

		// Valida proveedor
		Long vendorid = -1L;
		if (!initParamDTO.getVendorcode().equals("-1")) {
			VendorW[] vendors = null;
			try {
				vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
				
			} catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
			}
			
			if (vendors == null || vendors.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			
			vendorid = vendors[0].getId();
		}
		
		try {
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer d�a de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes d�as de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			cal.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();

			cal.add(Calendar.MONTH, 1);
			Date until = cal.getTime();

			// Proveedores y totales de multas
			KPIvevVendorDimensionDTO[] vendors = kpivevpddetailserver.getVendorsByKPIvevPDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine, initParamDTO.getCourier());

			// Departamentos
			KPIvevDepartmentDimensionDTO[] departments = kpivevpddetailserver.getDepartmentsByKPIvevPDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until, initParamDTO.getCourier());

			// Multas y cumplimiento
			KPIvevFineDataDTO[] finedata = kpivevpddetailserver.getFineDataByKPIvevPDFinePeriod(vendorid, initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine, initParamDTO.getCourier());
			
			resultDTO.setVendors(vendors);
			resultDTO.setDepartments(departments);
			resultDTO.setFinedata(finedata);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public KPIvevOrderDetailReportArrayResultDTO getKPIvevCDOrderDetailDataForReport(KPIvevFineInitParamDTO initParamDTO) {
		KPIvevOrderDetailReportArrayResultDTO resultDTO = new KPIvevOrderDetailReportArrayResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer d�a de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes d�as de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			cal.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();

			cal.add(Calendar.MONTH, 1);
			Date until = cal.getTime();
			
			// Detalle de órdenes
			KPIvevOrderDetailDataDTO[] orderdetaildata = kpivevcddetailserver.getOrderDetailDataByKPIvevCDFinePeriod(vendor.getId(), initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine, initParamDTO.getOrderby());
			
			resultDTO.setOrderdetaildata(orderdetaildata);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public KPIvevOrderDetailReportArrayResultDTO getKPIvevPDOrderDetailDataForReport(KPIvevFineInitParamDTO initParamDTO) {
		KPIvevOrderDetailReportArrayResultDTO resultDTO = new KPIvevOrderDetailReportArrayResultDTO();

		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer d�a de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes d�as de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			// Determinar el rango de fecha de b�squeda de información (un mes completo)
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, initParamDTO.getMonth().intValue());
			cal.set(Calendar.YEAR, initParamDTO.getYear().intValue());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date since = cal.getTime();

			cal.add(Calendar.MONTH, 1);
			Date until = cal.getTime();
			
			// Detalle de órdenes
			KPIvevOrderDetailDataDTO[] orderdetaildata = kpivevpddetailserver.getOrderDetailDataByKPIvevPDFinePeriod(vendor.getId(), initParamDTO.getDepartmentids(), since, until, firstdayfine, nextdaysfine, initParamDTO.getCourier(), initParamDTO.getOrderby());
			
			resultDTO.setOrderdetaildata(orderdetaildata);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO getExcelKPIvevCDFineDataReport(KPIvevFineInitParamDTO initParamDTO, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		Long t1 = System.currentTimeMillis();
		logger.info("**************Inicio descarga multas KPI VeV CD");
		
		try {
			Long vendorid = -1L;
			if (!initParamDTO.getVendorcode().equals("-1")) {
				// Valida proveedor
				VendorW[] vendors = null;
				try {
					vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
					
				} catch (Exception e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				
				if (vendors == null || vendors.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
				}
				
				vendorid = vendors[0].getId();
			}
			
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer día de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes días de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			resultDTO = kpivevcddetailserver.getExcelKPIvevCDFineDataReport(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getMonth(), initParamDTO.getYear(), firstdayfine, nextdaysfine, userKey);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		Long t2 = System.currentTimeMillis();
		logger.info("**************FIN descarga multas KPI VeV CD " + (t2 - t1) / 1000);
		
		return resultDTO;
	}
	
	public FileDownloadInfoResultDTO getExcelKPIvevPDFineDataReport(KPIvevFineInitParamDTO initParamDTO, Long userKey) {
		FileDownloadInfoResultDTO resultDTO = new FileDownloadInfoResultDTO();
		
		Long t1 = System.currentTimeMillis();
		logger.info("**************Inicio descarga multas KPI VeV PD" + (initParamDTO.getCourier() ? " Courier" : ""));
		
		try {
			Long vendorid = -1L;
			if (!initParamDTO.getVendorcode().equals("-1")) {
				// Valida proveedor
				VendorW[] vendors = null;
				try {
					vendors = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendorcode());
					
				} catch (Exception e) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
				}
				
				if (vendors == null || vendors.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
				}
				
				vendorid = vendors[0].getId();
			}
			
			// Validar que se haya ingresado un valor válido para el porciento de multa del primer día de atraso
			if (initParamDTO.getFirstdayfinepercent() < 0.0 || initParamDTO.getFirstdayfinepercent() > 100.0 ||
					(initParamDTO.getFirstdayfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1113");
			}
			Double firstdayfine = initParamDTO.getFirstdayfinepercent() / 100;
			
			// Validar que se haya ingresado un valor válido para el porciento de multa de los siguieetes días de atraso
			if (initParamDTO.getNextdaysfinepercent() < 0.0 || initParamDTO.getNextdaysfinepercent() > 100.0 ||
					(initParamDTO.getNextdaysfinepercent().doubleValue() * 100 % 1 != 0)) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1114");
			}
			Double nextdaysfine = initParamDTO.getNextdaysfinepercent() / 100;

			resultDTO = kpivevpddetailserver.getExcelKPIvevPDFineDataReport(vendorid, initParamDTO.getDepartmentids(), initParamDTO.getMonth(), initParamDTO.getYear(), firstdayfine, nextdaysfine, initParamDTO.getCourier(), userKey);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		Long t2 = System.currentTimeMillis();
		logger.info("**************FIN descarga multas KPI VeV PD " + (initParamDTO.getCourier() ? "Courier " : "") + (t2 - t1) / 1000);
		
		return resultDTO;
	}

/*	public KPIvevComplianceFactorArrayResultDTO getKPIvevComplianceFactors() {
		KPIvevComplianceFactorArrayResultDTO resultDTO = new KPIvevComplianceFactorArrayResultDTO();

		try {
			OrderCriteriaDTO orderCriteria = new OrderCriteriaDTO();
			orderCriteria.setPropertyname("min");
			orderCriteria.setAscending(false);

			List<KPIvevComplianceFactorW> cfsList = kpivevcompliancefactorserver.getAllOrdered(orderCriteria);
			KPIvevComplianceFactorW[] cfs = (KPIvevComplianceFactorW[]) cfsList.toArray(new KPIvevComplianceFactorW[cfsList.size()]);

			KPIvevComplianceFactorDTO[] compliancefactors = new KPIvevComplianceFactorDTO[cfs.length];
			BeanExtenderFactory.copyProperties(cfs, compliancefactors, KPIvevComplianceFactorDTO.class);

			resultDTO.setCompliancefactors(compliancefactors);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public BaseResultDTO doUpdateKPIvevComplianceFactors(KPIvevComplianceFactorInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			SortedMap<Double, Double> rankMap = new TreeMap<Double, Double>();
			// Validar que se est� ingresando al menos un rango para evaluar
			if (initParamDTO.getCompliancefactors() != null && initParamDTO.getCompliancefactors().length > 0) {
				for (KPIvevComplianceFactorDTO cf : initParamDTO.getCompliancefactors()) {
					// Validar que est�n completos los datos de cada rango
					if (cf.getMin() == null || cf.getMax() == null || cf.getFactor() == null) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1108");
					}

					// Validar que todos los campos contengan valores mayores o iguales a cero
					if (cf.getMin() < 0.0 || cf.getMax() < 0.0 || cf.getFactor() < 0.0) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1109");
					}

					// Validar que para cada rango min < max
					if (cf.getMin() >= cf.getMax()) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1112");
					}

					// Validar que no se hayan ingresado rangos repetidos
					if (rankMap.containsKey(cf.getMin()) && rankMap.get(cf.getMin()).doubleValue() == cf.getMax().doubleValue()) {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1110");
					}
					rankMap.put(cf.getMin(), cf.getMax());
				}

				// Validar que se complete todo el rango desde 0 a 100
				if (rankMap.firstKey() > 0.0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1111");
				}

				double tmpmax = 0.0;
				for (SortedMap.Entry<Double, Double> entry : rankMap.entrySet()) {
					if (entry.getKey().doubleValue() <= tmpmax) {
						if (entry.getValue().doubleValue() > tmpmax) {
							tmpmax = entry.getValue().doubleValue();
						}
					} else {
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1111");
					}
				}

				if (tmpmax < 100.0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1111");
				}

			} else {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1107");
			}

			// Actualizar la tabla de factores de cumplimiento
			kpivevcompliancefactorserver.deleteAll(); // elimina los rangos previos

			KPIvevComplianceFactorW[] compliancefactors = new KPIvevComplianceFactorW[initParamDTO.getCompliancefactors().length];
			BeanExtenderFactory.copyProperties(initParamDTO.getCompliancefactors(), compliancefactors, KPIvevComplianceFactorW.class);
			for (KPIvevComplianceFactorW cf : compliancefactors) {
				kpivevcompliancefactorserver.addKPIvevComplianceFactor(cf); // agrega los nuevos rangos
			}

		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}*/
}
