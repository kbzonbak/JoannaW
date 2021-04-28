package bbr.b2b.regional.logistic.managers.classes;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.buyorders.classes.OrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVAuditServerLocal;
import bbr.b2b.regional.logistic.buyorders.classes.VeVUpdateTypeServerLocal;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.OrderStateTypeW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVUpdateTypeW;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.OrderStateTypeDTO;
import bbr.b2b.regional.logistic.couriers.classes.CommuneCourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.CourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.HiredCourierServerLocal;
import bbr.b2b.regional.logistic.couriers.classes.VendorAddressServerLocal;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CommuneCourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.CourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.HiredCourierW;
import bbr.b2b.regional.logistic.couriers.data.wrappers.VendorAddressW;
import bbr.b2b.regional.logistic.couriers.entities.HiredCourierId;
import bbr.b2b.regional.logistic.couriers.report.classes.CommuneCourierArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CommuneCourierDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierInformationResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.CourierResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierArrayResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.HiredCourierResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorAddressDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorAddressInitParamDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorAddressResultDTO;
import bbr.b2b.regional.logistic.couriers.report.classes.VendorHiredCourierResultsDTO;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryStateTypeDTO;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderStateTypeServerLocal;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderStateTypeW;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.directorders.report.classes.DirectOrderStateTypeDTO;
import bbr.b2b.regional.logistic.items.classes.VendorItemServerLocal;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportDataDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportInitParamDTO;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemReportResultDTO;
import bbr.b2b.regional.logistic.locations.classes.LocationServerLocal;
import bbr.b2b.regional.logistic.locations.data.wrappers.LocationW;
import bbr.b2b.regional.logistic.locations.entities.Location;
import bbr.b2b.regional.logistic.locations.report.classes.LocationLogArrayResultDTO;
import bbr.b2b.regional.logistic.locations.report.classes.LocationLogDTO;
import bbr.b2b.regional.logistic.locations.report.classes.LocationsLogArrayResultDTO;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticReportManagerLocal;
import bbr.b2b.regional.logistic.managers.interfaces.LogisticReportManagerRemote;
import bbr.b2b.regional.logistic.report.classes.AssignedDataInitParamsDTO;
import bbr.b2b.regional.logistic.report.classes.BaseResultComparator;
import bbr.b2b.regional.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.PropertyVendorServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorCodeConstraintServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorCodeGenServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.PropertyVendorW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorCodeConstraintW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;
import bbr.b2b.regional.logistic.vendors.report.classes.AddInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.DeleteInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.DepartmentArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.DepartmentDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.InvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.UpdateInvoiceVendorRollOutInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorLogDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationArrayResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationSummaryDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationUploadDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationUploadInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorWithoutInvoiceValidationUploadResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorsLogArrayResultDTO;

@Stateless(name = "managers/LogisticReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LogisticReportManager implements LogisticReportManagerLocal, LogisticReportManagerRemote {

	private static Logger logger = Logger.getLogger(LogisticReportManager.class);
	
	@EJB
	private LocationServerLocal locationserver;
	
	@EJB
	private DepartmentServerLocal departmentserver;
	
	@EJB
	private VendorServerLocal vendorserver;

	@EJB
	private OrderStateTypeServerLocal orderstatetypeserver;
	
	@EJB
	private DirectOrderStateTypeServerLocal directorderstatetypeserver;
	
	@EJB
	private VendorCodeGenServerLocal vendorcodegenserver;
	
	@EJB
	private VendorCodeConstraintServerLocal vendorcodeconstraintserver;
	
	@EJB
	private VendorItemServerLocal vendoritemserver;
	
	@EJB
	private DODeliveryStateTypeServerLocal dodeliverystatetypeserver;
	
	@EJB
	private HiredCourierServerLocal hiredcourierserver;
	
	@EJB
	private VendorAddressServerLocal vendoraddressserver;
	
	@EJB
	private CourierServerLocal courierserver;
	
	@EJB
	private CommuneCourierServerLocal communecourierserver;
	
	@EJB
	PropertyVendorServerLocal propertyvendorserver;
	
	@EJB
	VeVAuditServerLocal vevauditserver;
	
	@EJB
	VeVUpdateTypeServerLocal vevupdatetypeserver;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	public OrderStateTypeArrayResultDTO getShowableOrderStateTypes() {
		OrderStateTypeArrayResultDTO resultW = new OrderStateTypeArrayResultDTO();

		try {
			OrderStateTypeW[] orderstatetypes = orderstatetypeserver.getByPropertyAsArray("showable", true);
			if (orderstatetypes == null || orderstatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L800");
			}

			OrderStateTypeDTO[] orderstatetypedtos = new OrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, OrderStateTypeDTO.class);

			resultW.setOrderstatetypes(orderstatetypedtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public OrderStateTypeArrayResultDTO getOrderStateTypes() {
		OrderStateTypeArrayResultDTO resultW = new OrderStateTypeArrayResultDTO();

		try {
			OrderStateTypeW[] orderstatetypes = orderstatetypeserver.getAllAsArray();
			if (orderstatetypes == null || orderstatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L800");
			}

			OrderStateTypeDTO[] orderstatetypedtos = new OrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, OrderStateTypeDTO.class);

			resultW.setOrderstatetypes(orderstatetypedtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public DirectOrderStateTypeArrayResultDTO getShowableDirectOrderStateTypes() {
		DirectOrderStateTypeArrayResultDTO resultW = new DirectOrderStateTypeArrayResultDTO();

		try {
			DirectOrderStateTypeW[] orderstatetypes = directorderstatetypeserver.getByPropertyAsArray("showable", true);
			if (orderstatetypes == null || orderstatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L800");
			}

			DirectOrderStateTypeDTO[] orderstatetypedtos = new DirectOrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, DirectOrderStateTypeDTO.class);

			resultW.setOrderstatetypes(orderstatetypedtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public DirectOrderStateTypeArrayResultDTO getDirectOrderStateTypes() {
		DirectOrderStateTypeArrayResultDTO resultW = new DirectOrderStateTypeArrayResultDTO();

		try {
			DirectOrderStateTypeW[] orderstatetypes = directorderstatetypeserver.getAllAsArray();
			if (orderstatetypes == null || orderstatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L800");
			}

			DirectOrderStateTypeDTO[] orderstatetypedtos = new DirectOrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, DirectOrderStateTypeDTO.class);

			resultW.setOrderstatetypes(orderstatetypedtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public DODeliveryStateTypeArrayResultDTO getClosedDODeliveryStateTypes() {
		DODeliveryStateTypeArrayResultDTO resultW = new DODeliveryStateTypeArrayResultDTO();

		try {
			DODeliveryStateTypeW[] dodeliverystatetypes = dodeliverystatetypeserver.getByPropertyAsArray("closed", true);
			if (dodeliverystatetypes == null || dodeliverystatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L900");
			}

			DODeliveryStateTypeDTO[] dodeliverystatetypesdtos = new DODeliveryStateTypeDTO[dodeliverystatetypes.length];
			BeanExtenderFactory.copyProperties(dodeliverystatetypes, dodeliverystatetypesdtos, DODeliveryStateTypeDTO.class);

			resultW.setDodeliverystatetypes(dodeliverystatetypesdtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public DODeliveryStateTypeArrayResultDTO getShowableDODeliveryStateTypes() {
		
		DODeliveryStateTypeArrayResultDTO resultDTO = new DODeliveryStateTypeArrayResultDTO();

		try {
			DODeliveryStateTypeW[] dodeliverystatetypes = dodeliverystatetypeserver.getByPropertyAsArray("showable", true);
			if (dodeliverystatetypes == null || dodeliverystatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}

			DODeliveryStateTypeDTO[] dodeliverystatetypesdtos = new DODeliveryStateTypeDTO[dodeliverystatetypes.length];
			BeanExtenderFactory.copyProperties(dodeliverystatetypes, dodeliverystatetypesdtos, DODeliveryStateTypeDTO.class);

			resultDTO.setDodeliverystatetypes(dodeliverystatetypesdtos);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public DODeliveryStateTypeArrayResultDTO getModifiableDODeliveryStateTypes() {
		
		DODeliveryStateTypeArrayResultDTO resultDTO = new DODeliveryStateTypeArrayResultDTO();

		try {
			DODeliveryStateTypeW[] dodeliverystatetypes = dodeliverystatetypeserver.getByPropertyAsArray("modifiable", true);
			if (dodeliverystatetypes == null || dodeliverystatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}

			DODeliveryStateTypeDTO[] dodeliverystatetypesdtos = new DODeliveryStateTypeDTO[dodeliverystatetypes.length];
			BeanExtenderFactory.copyProperties(dodeliverystatetypes, dodeliverystatetypesdtos, DODeliveryStateTypeDTO.class);

			resultDTO.setDodeliverystatetypes(dodeliverystatetypesdtos);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	
	public DirectOrderStateTypeArrayResultDTO getVendorSelectableDirectOrderStateTypes() {
		DirectOrderStateTypeArrayResultDTO resultW = new DirectOrderStateTypeArrayResultDTO();

		try {
			DirectOrderStateTypeW[] orderstatetypes = directorderstatetypeserver.getByPropertyAsArray("vendorselectable", true);
			if (orderstatetypes == null || orderstatetypes.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L800");
			}

			DirectOrderStateTypeDTO[] orderstatetypedtos = new DirectOrderStateTypeDTO[orderstatetypes.length];
			BeanExtenderFactory.copyProperties(orderstatetypes, orderstatetypedtos, DirectOrderStateTypeDTO.class);

			resultW.setOrderstatetypes(orderstatetypedtos);
			return resultW;
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
	}
	
	public LocationLogArrayResultDTO getLocationsOrderByNameAndWarehouse() {
		LocationLogArrayResultDTO resultW = new LocationLogArrayResultDTO();
		LocationLogDTO[] locationsWs = null;
		try {
			List<LocationW> loList = locationserver.getByQuery("select lo from Location as lo order by warehouse desc, name"); 
			
			if (loList != null && loList.size() > 0){
				locationsWs = new LocationLogDTO[loList.size()];
				DateConverterFactory2.copyProperties(loList.toArray(new LocationW[loList.size()]), locationsWs, LocationW.class, LocationLogDTO.class);				
			}		
		} catch (NotFoundException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L700");
		}
		catch (OperationFailedException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1500");
		}	
		resultW.setLocations(locationsWs);
		return resultW;
	}
	
	public LocationsLogArrayResultDTO getWareHouseLocationsOrderByName() {
		LocationsLogArrayResultDTO resultW = new LocationsLogArrayResultDTO();
		LocationLogDTO[] locationsWs = null;
		try {
			List<LocationW> loList = locationserver.getByQuery("select lo from Location as lo where lo.warehouse = TRUE order by name asc"); 
			
			if (loList != null && loList.size() > 0){
				locationsWs = new LocationLogDTO[loList.size()];
				DateConverterFactory2.copyProperties(loList.toArray(new LocationW[loList.size()]), locationsWs, LocationW.class, LocationLogDTO.class);				
			}			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setLocations(locationsWs);
		return resultW;
	}
	
	public VendorsLogArrayResultDTO getDomesticVendorsOrderByName() {
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.getDomesticVendorsOrderByName();
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findDomesticVendorsByName(String name){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findDomesticVendorsByName(name);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorsLogArrayResultDTO findDomesticVendorsByRut(String rut){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findDomesticVendorsByRut(rut);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorsLogArrayResultDTO findDomesticVendorsByInternalCode(String code){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findDomesticVendorsByInternalCode(code);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
		
	public VendorsLogArrayResultDTO getNotDomesticVendorsOrderByName() {
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.getNotDomesticVendorsOrderByName();
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findNotDomesticVendorsByName(String name){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findNotDomesticVendorsByName(name);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorsLogArrayResultDTO findNotDomesticVendorsByRut(String rut){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findNotDomesticVendorsByRut(rut);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorsLogArrayResultDTO findNotDomesticVendorsByInternalCode(String code){
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		
		try {
			VendorLogDTO[] vendorsDTOs = vendorserver.findNotDomesticVendorsByInternalCode(code);
			
			resultDTO.setVendors(vendorsDTOs);
			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorsLogArrayResultDTO getVendorsOrderByName() {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			List<VendorW> vdList = vendorserver.getByQuery("select vd from Vendor as vd order by name asc"); 
			
			if (vdList != null && vdList.size() > 0){
				vendorsWs = new VendorLogDTO[vdList.size()];
				DateConverterFactory2.copyProperties(vdList.toArray(new VendorW[vdList.size()]), vendorsWs, VendorW.class, VendorLogDTO.class);				
			}			
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithCourier() {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			vendorsWs = vendorserver.getDomesticVendorsWithCourier(); 
				
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithOutCourier() {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			vendorsWs = vendorserver.getDomesticVendorsWithOutCourier(); 
				
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithCourierByName(String name) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithCourierByName(name); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByName(String name) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithOutCourierByName(name); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithCourierByCode(String rut) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithCourierByCode(rut); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByCode(String rut) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithOutCourierByCode(rut); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithCourierByInternalCode(String code) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithCourierByInternalCode(code); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO getDomesticVendorsWithOutCourierByInternalCode(String code) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendorsWs = null;
		try {
			VendorW[] vd = vendorserver.getDomesticVendorsWithOutCourierByInternalCode(code); 
			if (vd != null && vd.length > 0){
				vendorsWs = new VendorLogDTO[vd.length];
				DateConverterFactory2.copyProperties(vd, vendorsWs, VendorW.class, VendorLogDTO.class);				
			}	
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
		resultW.setVendors(vendorsWs);
		return resultW;
	}
	
	public VendorsLogArrayResultDTO findVendorsById(Long vendorId){
		
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		List<VendorW> vendorList = new ArrayList<VendorW>();
		VendorW[] vendors = null;
		VendorLogDTO[] vendordtos = null;
		try {
			VendorW vendor = vendorserver.getById(vendorId);
			vendorList.add(vendor);
			vendors = vendorList.toArray(new VendorW[vendorList.size()]);
			
			vendordtos = new VendorLogDTO[vendors.length];
			BeanExtenderFactory.copyProperties(vendors, vendordtos, VendorLogDTO.class);
			resultW.setVendors(vendordtos);
			return resultW;		
					
		}catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
	}
	
	public VendorsLogArrayResultDTO findVendorsByIds(Long[] vendorIds){
		
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;		
		try {
			vendordtos = vendorserver.getVendorsByIds(vendorIds);		
			resultW.setVendors(vendordtos);
			return resultW;	
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}			
	}
	
	public VendorsLogArrayResultDTO findVendorsByIdsCourier(Long[] vendorIds){
		
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;		
		try {
			vendordtos = vendorserver.getVendorsByIdsCourier(vendorIds);			
			resultW.setVendors(vendordtos);
			return resultW;	
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}			
	}
	
	public VendorsLogArrayResultDTO findVendorsByIds(Long[] vendorIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order) {
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorW[] vendors = null;
		VendorLogDTO[] vendordtos = null;
		try {
			vendors = vendorserver.getVendorsByIds(vendorIds, pagenumber, rows, isPaginated, order);
			vendordtos = new VendorLogDTO[vendors.length];
			BeanExtenderFactory.copyProperties(vendors, vendordtos, VendorLogDTO.class);
			
			resultDTO.setVendors(vendordtos);
			if(vendors.length <= 0){
				return resultDTO;
			}
			
			if(getPageInfo){				
				int totalproviders = vendorserver.countVendorsOfUser(vendorIds);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(pagenumber);
				pageInfo.setRows(vendors.length);
				pageInfo.setTotalpages((totalproviders % rows)!= 0 ? (totalproviders/rows)+1 : (totalproviders/rows));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo); //seteo info de pagina
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); // error interno
		}
		
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findVendorsByIdsCourier(Long[] vendorIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order) {
		
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		try {
			vendordtos = vendorserver.getVendorsByIdsCourier(vendorIds, pagenumber, rows, isPaginated, order);
			
			resultDTO.setVendors(vendordtos);
			if(vendordtos.length <= 0){
				return resultDTO;
			}
			
			if(getPageInfo){				
				int totalproviders = vendorserver.countVendorsOfUser(vendorIds);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(pagenumber);
				pageInfo.setRows(vendordtos.length);
				pageInfo.setTotalpages((totalproviders % rows)!= 0 ? (totalproviders/rows)+1 : (totalproviders/rows));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo); //seteo info de pagina
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); // error interno
		}
		
		return resultDTO;
	}
	
	public LocationsLogArrayResultDTO findLocationByIds(Long[] locationIds, int pagenumber, int rows, boolean getPageInfo, boolean isPaginated, OrderCriteriaDTO[] order) {
		
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		LocationW[] locations = null;
		LocationLogDTO[] locationdtos = null;
		try {
			locations = locationserver.getLocationByIds(locationIds, pagenumber, rows, isPaginated, order);
			locationdtos = new LocationLogDTO[locations.length];
			BeanExtenderFactory.copyProperties(locations, locationdtos, LocationLogDTO.class);
			
			resultDTO.setLocations(locationdtos);
			if(locations.length <= 0){
				return resultDTO;
			}
			
			if(getPageInfo){				
				int totalproviders = locationserver.countLocationOfUser(locationIds);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(pagenumber);
				pageInfo.setRows(locations.length);
				pageInfo.setTotalpages((totalproviders % rows)!= 0 ? (totalproviders/rows)+1 : (totalproviders/rows));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo); //seteo info de pagina
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1"); // error interno
		} 
		
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findVendorsByRut(String rut){
		
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorW[] vendors = null; 
		VendorLogDTO[] vendordtos = null;
		try {
			List<VendorW> vendorList = vendorserver.getLikeStringProperty("rut", rut, false);
			vendors = vendorList.toArray(new VendorW[vendorList.size()]);	
			
			vendordtos = new VendorLogDTO[vendors.length];
			for(int i=0; i<vendorList.size(); i++){
				
				LocalDateTime firstvevcddate = vendorList.get(i).getFirstvevcddate() != null ? vendorList.get(i).getFirstvevcddate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				LocalDateTime firstvevpddate = vendorList.get(i).getFirstvevpddate() != null ? vendorList.get(i).getFirstvevpddate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				
				VendorLogDTO vendorlogDTO = new VendorLogDTO();
				vendorlogDTO.setAddress(vendorList.get(i).getAddress());
				vendorlogDTO.setCity(vendorList.get(i).getCity());
				vendorlogDTO.setCode(vendorList.get(i).getCode());
				vendorlogDTO.setCommune(vendorList.get(i).getCommune());
				//vendorlogDTO.setCourier(vendorList.get(i).getCou);
				vendorlogDTO.setDomestic(vendorList.get(i).getDomestic());
				vendorlogDTO.setEmail(vendorList.get(i).getEmail());
				vendorlogDTO.setFax(vendorList.get(i).getFax());
				vendorlogDTO.setFirstvevcddate(firstvevcddate);
				vendorlogDTO.setFirstvevpddate(firstvevpddate);
				vendorlogDTO.setGln(vendorList.get(i).getGln());
				vendorlogDTO.setId(vendorList.get(i).getId());
				vendorlogDTO.setLastbulknumber(vendorList.get(i).getLastbulknumber());
				vendorlogDTO.setLogisticscode(vendorList.get(i).getLogisticscode());
				vendorlogDTO.setName(vendorList.get(i).getName());
				vendorlogDTO.setNumber(vendorList.get(i).getNumber());
				vendorlogDTO.setPhone(vendorList.get(i).getPhone());
				vendorlogDTO.setRut(vendorList.get(i).getRut());
				vendorlogDTO.setTradename(vendorList.get(i).getTradename());
				
				vendordtos[i] = vendorlogDTO; 
			}
			

			resultW.setVendors(vendordtos);
			return resultW;		
		}catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L111");
		}		
	}
	
	public VendorsLogArrayResultDTO findVendorsByName(String name){
		
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorW[] vendors = null; 
		VendorLogDTO[] vendordtos = null;
		try {
			List<VendorW> vendorList = vendorserver.getLikeStringProperty("name", name, false);
			vendors = vendorList.toArray(new VendorW[vendorList.size()]);			
		
			vendordtos = new VendorLogDTO[vendors.length];
			for(int i=0; i<vendorList.size(); i++){
				
				LocalDateTime firstvevcddate = vendorList.get(i).getFirstvevcddate() != null ? vendorList.get(i).getFirstvevcddate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				LocalDateTime firstvevpddate = vendorList.get(i).getFirstvevpddate() != null ? vendorList.get(i).getFirstvevpddate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
				
				VendorLogDTO vendorlogDTO = new VendorLogDTO();
				vendorlogDTO.setAddress(vendorList.get(i).getAddress());
				vendorlogDTO.setCity(vendorList.get(i).getCity());
				vendorlogDTO.setCode(vendorList.get(i).getCode());
				vendorlogDTO.setCommune(vendorList.get(i).getCommune());
				//vendorlogDTO.setCourier(vendorList.get(i).getCou);
				vendorlogDTO.setDomestic(vendorList.get(i).getDomestic());
				vendorlogDTO.setEmail(vendorList.get(i).getEmail());
				vendorlogDTO.setFax(vendorList.get(i).getFax());
				vendorlogDTO.setFirstvevcddate(firstvevcddate);
				vendorlogDTO.setFirstvevpddate(firstvevpddate);
				vendorlogDTO.setGln(vendorList.get(i).getGln());
				vendorlogDTO.setId(vendorList.get(i).getId());
				vendorlogDTO.setLastbulknumber(vendorList.get(i).getLastbulknumber());
				vendorlogDTO.setLogisticscode(vendorList.get(i).getLogisticscode());
				vendorlogDTO.setName(vendorList.get(i).getName());
				vendorlogDTO.setNumber(vendorList.get(i).getNumber());
				vendorlogDTO.setPhone(vendorList.get(i).getPhone());
				vendorlogDTO.setRut(vendorList.get(i).getRut());
				vendorlogDTO.setTradename(vendorList.get(i).getTradename());
				
				vendordtos[i] = vendorlogDTO; 
			}
			
			resultW.setVendors(vendordtos);
			return resultW;		
		
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}
			
	}	
	
	public LocationsLogArrayResultDTO findLocationsByCode(String code){
		LocationsLogArrayResultDTO resultW =  new LocationsLogArrayResultDTO();		
		LocationW[] localW = null;
		List<LocationW> locals = null;
		LocationLogDTO[] localtiondtos = null;
		try{
			locals = locationserver.getByProperty("code", code);
			localW = locals.toArray(new LocationW[locals.size()]);
			
			localtiondtos = new LocationLogDTO[localW.length];
			BeanExtenderFactory.copyProperties(localW, localtiondtos, LocationLogDTO.class);
			
		}catch (OperationFailedException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}catch (NotFoundException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1500");
		}		
		resultW.setLocations(localtiondtos);
		return resultW;
	}
	
	public LocationsLogArrayResultDTO findLocationsByName(String name){
		LocationsLogArrayResultDTO resultW =  new LocationsLogArrayResultDTO();		
		LocationW[] localW = null;
		List<LocationW> locals = null;
		LocationLogDTO[] localtiondtos = null;
		try{
			locals = locationserver.getLikeStringProperty("name", name, false);
			localW = locals.toArray(new LocationW[locals.size()]);
			
			localtiondtos = new LocationLogDTO[localW.length];
			BeanExtenderFactory.copyProperties(localW, localtiondtos, LocationLogDTO.class);
		}catch (OperationFailedException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}catch (NotFoundException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1500");
		}		
		
		resultW.setLocations(localtiondtos);
		return resultW;
	}
	
	public LocationsLogArrayResultDTO findLocationsByIds(Long[] localIds){
		LocationsLogArrayResultDTO resultW =  new LocationsLogArrayResultDTO();		
		LocationW[] locals = null;
		LocationLogDTO[] localtiondtos = null;
		try{
			locals = locationserver.getLocationsByIds(localIds);		
			localtiondtos = new LocationLogDTO[locals.length];
			BeanExtenderFactory.copyProperties(locals, localtiondtos, LocationLogDTO.class);			
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
		resultW.setLocations(localtiondtos);
		return resultW;
	}	
	
	public VendorsLogArrayResultDTO findVendorsOfUserByCode(String code, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}		
		
		try {
			vendordtos = vendorserver.findVendorsOfUserByCode(code, vendorids);
			
			resultW.setVendors(vendordtos);
			return resultW;
			
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}	
	}

	public VendorsLogArrayResultDTO findVendorsOfUserByName(String name, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}
		
		try {
			vendordtos = vendorserver.findVendorsOfUserByName(name, vendorids);
			resultW.setVendors(vendordtos);
			return resultW;
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
	}	
	
	public VendorsLogArrayResultDTO findVendorsOfUserByInternalCode(String code, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}
		
		try {
			vendordtos = vendorserver.findVendorsOfUserByInternalCode(code, vendorids);
			
			resultW.setVendors(vendordtos);
			return resultW;
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
	}	
	
	public VendorsLogArrayResultDTO findVendorsOfUserByCodeCourier(String code, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}
		
		
		try {
			vendordtos = vendorserver.findVendorsOfUserByCodeCourier(code, vendorids);
			
			resultW.setVendors(vendordtos);
			return resultW;
			
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}	
	}

	public VendorsLogArrayResultDTO findVendorsOfUserByNameCourier(String name, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}

		
		try {
			vendordtos = vendorserver.findVendorsOfUserByNameCourier(name, vendorids);
			
			resultW.setVendors(vendordtos);
			return resultW;
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
	}	
	
	public VendorsLogArrayResultDTO findVendorsOfUserByInternalCodeCourier(String code, String[] vendorcodes) {
		VendorsLogArrayResultDTO resultW = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
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
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");// problemas
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L443");// no existe 
		}
		
		
		try {
			vendordtos = vendorserver.findVendorsOfUserByInternalCodeCourier(code, vendorids);
			
			resultW.setVendors(vendordtos);
			return resultW;
		}catch (ServiceException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}		
	}	
	
	public Set getAllVendorIds(){
		Set vendorids = null;
		try {
			Vendor[] vendors = vendorserver.findAllAsArray();
			vendorids = new HashSet();
			
			for (int i = 0; i < vendors.length; i++) {
				vendorids.add(vendors[i].getId());
			}					
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
		return vendorids;
	}
	
	public Set getAllLocalIds(){
		Set localids = null;
		try {
			Location[] locations = locationserver.findAllAsArray();
			localids = new HashSet();
			
			for (int i = 0; i < locations.length; i++) {
				localids.add(locations[i].getId());
			}					
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
		return localids;
	}
	
	public VendorsLogArrayResultDTO findVendorsLogLikeNameAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
		try {
			vendordtos = vendorserver.findVendorsLogLikeNameAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setVendors(vendordtos);
			
			if(vendordtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = vendorserver.countVendorsLogLikeNameAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(vendordtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;	
	}
	
	public VendorsLogArrayResultDTO findVendorsLogLikeCodeAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
		try {
			vendordtos = vendorserver.findVendorsLogLikeCodeAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setVendors(vendordtos);
			
			if(vendordtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = vendorserver.countVendorsLogLikeCodeAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(vendordtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;		
	}
	
	public LocationsLogArrayResultDTO findLocalsLikeNameAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		LocationLogDTO[] locationdtos = null;
		
		try {
			locationdtos = locationserver.findLocationsLogLikeNameAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setLocations(locationdtos);
			
			if(locationdtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = locationserver.countLocationsLogLikeNameAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(locationdtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public LocationsLogArrayResultDTO findLocalsLikeCodeAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		LocationLogDTO[] locationdtos = null;
		
		try {
			locationdtos = locationserver.findLocationsLogLikeCodeAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setLocations(locationdtos);
			
			if(locationdtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = locationserver.countLocationsLogLikeCodeAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(locationdtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findVendorsLogLikeNameNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
		try {
			vendordtos = vendorserver.findVendorsLogLikeNameNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setVendors(vendordtos);
			
			if(vendordtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = vendorserver.countVendorsLogLikeNameNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(vendordtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public VendorsLogArrayResultDTO findVendorsLogLikeCodeNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		VendorLogDTO[] vendordtos = null;
		
		try {
			vendordtos = vendorserver.findVendorsLogLikeCodeNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setVendors(vendordtos);
			
			if(vendordtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = vendorserver.countVendorsLogLikeCodeNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(vendordtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public LocationsLogArrayResultDTO findLocalsLikeNameNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		LocationLogDTO[] locationdtos = null;
		
		try {
			locationdtos = locationserver.findLocationsLogLikeNameNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setLocations(locationdtos);
			
			if(locationdtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = locationserver.countLocationsLogLikeNameNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(locationdtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public LocationsLogArrayResultDTO findLocalsLikeCodeNotAssigned(AssignedDataInitParamsDTO initparams, Long[] assignedids){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		LocationLogDTO[] locationdtos = null;
		
		try {
			locationdtos = locationserver.findLocationsLogLikeCodeNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids, initparams.getPagenumber(), initparams.getRows(), initparams.isPaginated(), initparams.getOrder());
			resultDTO.setLocations(locationdtos);
			
			if(locationdtos.length <= 0){
				return resultDTO;
			}
			
			if(initparams.isGetPageInfo()){
				int totalproviders = locationserver.countLocationsLogLikeCodeNotAssigned(initparams.getSearchstring().toUpperCase(), assignedids);
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initparams.getPagenumber());
				pageInfo.setRows(locationdtos.length);
				pageInfo.setTotalpages((totalproviders % initparams.getRows()) != 0 ? (totalproviders/initparams.getRows()) + 1 : (totalproviders/initparams.getRows()));
				pageInfo.setTotalrows(totalproviders);
				
				resultDTO.setPageInfo(pageInfo);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}				
		return resultDTO;
	}
	
	public DepartmentArrayResultDTO getDepartments(){
		
		DepartmentArrayResultDTO resultW =  new DepartmentArrayResultDTO();		
		DepartmentW[] departmentW = null;
		List<DepartmentW> departments = null;
		DepartmentDTO[] departmentdtos = null;
		
		try{
			OrderCriteriaDTO orderCriteria = new OrderCriteriaDTO();
			orderCriteria.setPropertyname("name");
			orderCriteria.setAscending(true);
			
			departments = departmentserver.getAllOrdered(orderCriteria);
			
			departmentW = (DepartmentW[]) departments.toArray(new DepartmentW[departments.size()]);
			departmentdtos = new DepartmentDTO[departmentW.length];
			BeanExtenderFactory.copyProperties(departmentW, departmentdtos, DepartmentDTO.class);
			
			for(int i = 0; i < departmentdtos.length; i++){
				departmentdtos[i].setCodenamestr(departmentdtos[i].getCode() + " - " + departmentdtos[i].getName());
			}
			
		}catch (OperationFailedException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}catch (NotFoundException e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1501");
		}		
		
		resultW.setDepartments(departmentdtos);
		
		return resultW;
	}
	
	public DepartmentArrayResultDTO findDepartmentsByCode(String code){
				
		DepartmentArrayResultDTO resultW =  new DepartmentArrayResultDTO();	
		
		try{
			DepartmentDTO[] departments = departmentserver.findDepartmentsByCode(code);
			
			resultW.setDepartments(departments);
			
		}catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1501");
		}		
				
		return resultW;
	
	}
	
	public DepartmentArrayResultDTO findDepartmentsByName(String name){
		
		DepartmentArrayResultDTO resultW =  new DepartmentArrayResultDTO();	
		
		try{
			DepartmentDTO[] departments = departmentserver.findDepartmentsByName(name);
			
			resultW.setDepartments(departments);
			
		}catch (OperationFailedException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1");
		}catch (NotFoundException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultW, "L1501");
		}		
				
		return resultW;
	
	}
	
	public String getNextAvailableVendorCode() throws ServiceException {
        int maxretry = 100;
        for (int i = 0; i < maxretry; i++) {
              // Retornar el sgte. código generado
              String newcode = vendorcodegenserver.getNextCode();
              
              // Validar que el nuevo código tenga al menos una letra
              try{
                    Integer.parseInt(newcode);
                    continue;
              }catch (NumberFormatException e){
                    //si no se puede transformar a N°mero, esta OK
              }
              
              // Validar que el código generado no est� reservado
              try {
                    VendorCodeConstraintW[] constraints = vendorcodeconstraintserver.getByPropertyAsArray("name", newcode);
                    // Si el código est� reservado, intentar con el sgte
                    if (constraints != null && constraints.length > 0)
                         continue;
              } catch (NotFoundException e) {
                    // Si no existe, ok
              }
              return newcode;
        }
        // Si se ha llegado al max de intentos, lanzar excepción
        throw new OperationFailedException("No se pudo generar un código para el proveedor");
	}
	
	public VendorItemReportResultDTO getVendorItemsByFilter(VendorItemReportInitParamDTO initParams, boolean isByFilter){
		
		VendorItemReportResultDTO resultDTO = new VendorItemReportResultDTO();
		
		VendorItemReportDataDTO[] vendorItemDatasDTO = null;
		
		int totalreg = 0;
		int total = 0;
		
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
		
		
		try{
			switch(initParams.getFiltertype()){
			case 0:			// SKU Paris
				vendorItemDatasDTO = vendoritemserver.getVendorItemsByInternalCode(vendor.getId(), initParams.getItemsku(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);
				
				if (isByFilter && vendorItemDatasDTO.length > 0)
					totalreg = vendoritemserver.getVendorItemsCountByInternalCode(vendor.getId(), initParams.getItemsku());
				break;
			case 1:			// código Proveedor
				vendorItemDatasDTO = vendoritemserver.getVendorItemsByVendorItemCode(vendor.getId(), initParams.getVendoritemcode(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);
				
				if (isByFilter && vendorItemDatasDTO.length > 0)
					totalreg = vendoritemserver.getVendorItemsCountByVendorItemCode(vendor.getId(), initParams.getVendoritemcode());
				break;
			case 2:			// Descripción
				vendorItemDatasDTO = vendoritemserver.getVendorItemsByName(vendor.getId(), initParams.getDescription(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);
				
				if (isByFilter && vendorItemDatasDTO.length > 0)
					totalreg = vendoritemserver.getVendorItemsCountByName(vendor.getId(), initParams.getDescription());
				break;
			case 3:			// N°mero de OC
				vendorItemDatasDTO = vendoritemserver.getVendorItemsByOrderNumber(vendor.getId(), initParams.getOcnumber(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);
				
				if (isByFilter && vendorItemDatasDTO.length > 0)
					totalreg = vendoritemserver.getVendorItemsCountByOrderNumber(vendor.getId(), initParams.getOcnumber());
				break;
			default:		// SKU Paris
				vendorItemDatasDTO = vendoritemserver.getVendorItemsByInternalCode(vendor.getId(), initParams.getItemsku(), initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), true);
			
			if (isByFilter && vendorItemDatasDTO.length > 0)
				totalreg = vendoritemserver.getVendorItemsCountByInternalCode(vendor.getId(), initParams.getItemsku());
			break;	
		}
		
		resultDTO.setVendoritemdatas(vendorItemDatasDTO);
		
		if (isByFilter) {
			int rows = initParams.getRows();
			total = totalreg;
			PageInfoDTO pageInfo = new PageInfoDTO();
			pageInfo.setPagenumber(initParams.getPagenumber());
			pageInfo.setRows(vendorItemDatasDTO.length);
			pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
			pageInfo.setTotalrows(total);
			resultDTO.setPageInfo(pageInfo);
			
			resultDTO.setTotalreg(totalreg);
		}
				
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public HiredCourierResultDTO addHiredCourier(HiredCourierDTO hiredCourierDTO){
		HiredCourierResultDTO result = new HiredCourierResultDTO();
		
		try {
			VendorW vendor = vendorserver.getById(hiredCourierDTO.getVendorid());
			CourierW courier = courierserver.getById(hiredCourierDTO.getCourierid());
				
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", hiredCourierDTO.getVendorid());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("courier.id", "courierid", hiredCourierDTO.getCourierid());
			HiredCourierW[] hiredCourierWs = hiredcourierserver.getByPropertiesAsArray(prop1, prop2);
			if (hiredCourierWs != null && hiredCourierWs.length > 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1", "El proveedor " + vendor.getName() + " ya está asociado a la empresa de Courier: " + courier.getDescription(), false);
			}
			
			HiredCourierW hiredCourierW = new HiredCourierW();
			BeanExtenderFactory.copyProperties(hiredCourierDTO, hiredCourierW);
			
			Date date = new Date();
			hiredCourierW.setCreationdate(date);
			hiredCourierW.setUpdatedate(date);
			
			hiredCourierW = hiredcourierserver.addHiredCourier(hiredCourierW);
									
			BeanExtenderFactory.copyProperties(hiredCourierW, hiredCourierDTO);
			result.setHiredCourier(hiredCourierDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public HiredCourierResultDTO updateHiredCourier(HiredCourierDTO oldHiredCourierDTO, HiredCourierDTO newHiredCourierDTO){
		HiredCourierResultDTO result = new HiredCourierResultDTO();
		
		try {
			HiredCourierW newHiredCourierW = new HiredCourierW();
			HiredCourierW oldHiredCourierW = new HiredCourierW();
			
			BeanExtenderFactory.copyProperties(newHiredCourierDTO,newHiredCourierW);
			BeanExtenderFactory.copyProperties(oldHiredCourierDTO,oldHiredCourierW);
			newHiredCourierW.setUpdatedate(new Date());
			
			HiredCourierW hc = hiredcourierserver.getById(new HiredCourierId(oldHiredCourierW.getVendorid(), oldHiredCourierW.getCourierid()));
			newHiredCourierW.setCreationdate(hc.getCreationdate());
			if(oldHiredCourierDTO.getCourierid().equals(newHiredCourierDTO.getCourierid()) && oldHiredCourierDTO.getVendorid().equals(newHiredCourierDTO.getVendorid())){
				BeanExtenderFactory.copyProperties(hiredcourierserver.updateHiredCourier(newHiredCourierW), newHiredCourierDTO);
			}
			else{
				deleteHiredCourier(oldHiredCourierDTO);
				BeanExtenderFactory.copyProperties(hiredcourierserver.addHiredCourier(newHiredCourierW), newHiredCourierDTO);
			}
			
			result.setHiredCourier(newHiredCourierDTO);
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public HiredCourierArrayResultDTO findAllHiredCouriers(){
		
		HiredCourierArrayResultDTO result = new HiredCourierArrayResultDTO();
		
		try {
			BeanExtenderFactory.copyProperties(hiredcourierserver.getHiredCouriers(), result.getHiredCourier());
		
		} catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public HiredCourierResultDTO deleteHiredCourier(HiredCourierDTO hiredCourierDTO){
		HiredCourierResultDTO result = new HiredCourierResultDTO();
		
		try {
			// JPE 2018-04-03
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
	        PropertyInfoDTO pven = new PropertyInfoDTO("hiredcourier.id.vendorid", "vendorid", hiredCourierDTO.getVendorid());
	        properties[0] = pven;
	        PropertyInfoDTO pven2 = new PropertyInfoDTO("hiredcourier.id.courierid", "courierid", hiredCourierDTO.getCourierid());
	        properties[1] = pven2;
	        
			// Eliminar la dirección asociada al antiguo courier contratado
			vendoraddressserver.deleteVendorAddress(properties);
			
			pven = new PropertyInfoDTO("vendor.id", "vendorid", hiredCourierDTO.getVendorid());
	        properties[0] = pven;
	        pven2 = new PropertyInfoDTO("courier.id", "courierid", hiredCourierDTO.getCourierid());
	        properties[1] = pven2;
	        
			hiredcourierserver.deleteHiredCourier(properties);
			
			result.setHiredCourier(null);
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public HiredCourierResultDTO deleteHiredCourier(HiredCourierDTO[] hiredCouriersDTO){
		HiredCourierResultDTO result = new HiredCourierResultDTO();
		
		try {
			for(HiredCourierDTO hiredCourierDTO: hiredCouriersDTO){
				// JPE 2018-04-03
				PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
				PropertyInfoDTO pven = new PropertyInfoDTO("hiredcourier.id.vendorid", "vendorid", hiredCourierDTO.getVendorid());
				properties[0] = pven;
				PropertyInfoDTO pven2 = new PropertyInfoDTO("hiredcourier.id.courierid", "courierid", hiredCourierDTO.getCourierid());
				properties[1] = pven2;
				
				// Eliminar la dirección asociada al antiguo courier contratado
				vendoraddressserver.deleteVendorAddress(properties);
				
				pven = new PropertyInfoDTO("vendor.id", "vendorid", hiredCourierDTO.getVendorid());
				properties[0] = pven;
				pven2 = new PropertyInfoDTO("courier.id", "courierid", hiredCourierDTO.getCourierid());
				properties[1] = pven2;
				
				hiredcourierserver.deleteHiredCourier(properties);
			}
			
			result.setHiredCourier(null);
			
		} catch (Exception e) {
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public VendorHiredCourierResultsDTO getAllVendorsHiredCourier() {
		VendorHiredCourierResultsDTO result = new VendorHiredCourierResultsDTO();
		
		result.setVendorHiredCourier(hiredcourierserver.getAllVendorsHiredCourier());
		
		return result;
	}
	
	public CourierResultDTO findAllCouriers(){
		
		CourierResultDTO result = new CourierResultDTO();
		
		try {
			CourierW[] courierW = courierserver.getAllAsArray();
			CourierDTO[] courierDTO = new CourierDTO[courierW.length];
			DateConverterFactory2.copyProperties(courierW, courierDTO, CourierW.class, CourierDTO.class);
			result.setCouriers(courierDTO);
		
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public BaseResultDTO addCommuneCourier(CommuneCourierDTO communeCourier){
		BaseResultDTO result = new BaseResultDTO();
				
		try {
			CommuneCourierW target = new CommuneCourierW();
			DateConverterFactory2.copyProperties(communeCourier, target);
			
			CourierW courier = courierserver.getById(communeCourier.getCourierid());
			CommuneCourierW existence = communecourierserver.getCourierCommuneByCourierAndParisCommune(communeCourier.getCourierid(), communeCourier.getPariscommune());
			if (existence != null) {
				return RegionalLogisticStatusCodeUtils.getInstance()
				.setStatusCode(result, "L1", "La comuna "+communeCourier.getPariscommune()+" ya est� asociada a la empresa de courier: "+courier.getDescription(), false);
			}
		
			communecourierserver.addCommuneCourier(target);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}
		
		return result;
	}
	
	public CommuneCourierArrayResultDTO getAllCommuneCouriers(){
		
		CommuneCourierArrayResultDTO result = new CommuneCourierArrayResultDTO();
		CommuneCourierW[] courierArr = null;
		CommuneCourierDTO[] courierArrDTO = null;
		
		try {
			List<CourierW> couriers = courierserver.getAll();
			Map<Long,String> courierNames = new HashMap<Long, String>();
			for (CourierW courier : couriers) {
				courierNames.put(courier.getId(), courier.getDescription());
			}
				
			List<CommuneCourierW> couriersList = communecourierserver.getAll();
				
			if (couriersList!=null && !couriersList.isEmpty()) {
				courierArr = (CommuneCourierW[]) couriersList.toArray(new CommuneCourierW[couriersList.size()]);
				courierArrDTO = new CommuneCourierDTO[courierArr.length];
				DateConverterFactory2.copyProperties(courierArr, courierArrDTO, CommuneCourierW.class, CommuneCourierDTO.class);
			}
			
			for (CommuneCourierDTO i : courierArrDTO) {
				i.setCourier(courierNames.get(i.getCourierid()));
			}
			
			result.setCommuneCouriers(courierArrDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public BaseResultDTO updateCommuneCourier(CommuneCourierDTO communeCourier){
		BaseResultDTO result = new BaseResultDTO();
		
		try {
			CommuneCourierW target = new CommuneCourierW();
			DateConverterFactory2.copyProperties(communeCourier, target);
			
			communecourierserver.updateCommuneCourier(target);
		
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public BaseResultDTO deleteCommuneCourier(CommuneCourierDTO[] communeCourier){
		BaseResultDTO result = new BaseResultDTO();
		
		try {
			CommuneCourierW[] target = new CommuneCourierW[communeCourier.length];
			DateConverterFactory2.copyProperties(communeCourier, target,CommuneCourierDTO.class,CommuneCourierW.class);
			
			for(CommuneCourierW commune : target) {
				communecourierserver.deleteCommuneCourier(commune);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public CourierInformationResultDTO getCourierInformation(String vendorcode) {

		CourierInformationResultDTO result = new CourierInformationResultDTO();

		try {
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", vendorcode);
			if(vendorArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
			}
			VendorW vendorW = vendorArr[0];	
			
			CourierInformationDTO[] courierinformation = courierserver.getCourierInformation(vendorW.getId());
			result.setCourierinformation(courierinformation);
		
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
		}

		return result;
	}
	
	public HiredCourierArrayResultDTO getHiredCouriersByVendorId(String vendorcode) {

		HiredCourierArrayResultDTO resultDTO = new HiredCourierArrayResultDTO();

		try {
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", vendorcode);
			if(vendorArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
			}
			VendorW vendorW = vendorArr[0];			
			
			HiredCourierW[] hiredCourierWs = hiredcourierserver.getByPropertyAsArray("vendor.id", vendorW.getId());

			HiredCourierDTO[] hiredCourierDTOs = null;
			if (hiredCourierWs != null && hiredCourierWs.length > 0) {
				hiredCourierDTOs = new HiredCourierDTO[hiredCourierWs.length];
				BeanExtenderFactory.copyProperties(hiredCourierWs, hiredCourierDTOs, HiredCourierDTO.class);
			}

			resultDTO.setHiredCourier(hiredCourierDTOs);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public CommuneCourierArrayResultDTO getCommunesByCourierId(Long courierid) {

		CommuneCourierArrayResultDTO resultDTO = new CommuneCourierArrayResultDTO();

		try {
			CommuneCourierW[] communeCourierWs = communecourierserver.getByPropertyAsArray("courier.id", courierid);

			CommuneCourierDTO[] communeCourierDTOs = null;
			if (communeCourierWs != null && communeCourierWs.length > 0) {
				communeCourierDTOs = new CommuneCourierDTO[communeCourierWs.length];
				BeanExtenderFactory.copyProperties(communeCourierWs, communeCourierDTOs, CommuneCourierDTO.class);
			}

			resultDTO.setCommuneCouriers(communeCourierDTOs);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;

	}

	public VendorAddressResultDTO getVendorAddressByHiredCourierId(String vendorcode, Long courierid) {

		VendorAddressResultDTO resultDTO = new VendorAddressResultDTO();
		// Valida proveedor
		VendorW[] vendors = null;
		VendorW vendor = null;
		try{
			vendors = vendorserver.getByPropertyAsArray("rut", vendorcode);
			
		}	catch (Exception e) {
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		if(vendors == null || vendors.length == 0){
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");// no existe
		}
		vendor = vendors[0];
		
		try {
			PropertyInfoDTO prop1 = new PropertyInfoDTO("hiredcourier.vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("hiredcourier.courier.id", "courierid", courierid);
			List<VendorAddressW> vendorAddressWs = vendoraddressserver.getByProperties(prop1, prop2);

			VendorAddressDTO vendorAddressDTO = null;
			if (vendorAddressWs != null && vendorAddressWs.size() > 0) {
				vendorAddressDTO = new VendorAddressDTO();
				DateConverterFactory2.copyProperties(vendorAddressWs.get(0), vendorAddressDTO);
			}

			resultDTO.setVendorAdress(vendorAddressDTO);

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}

	public BaseResultDTO updateVendorAddress(VendorAddressInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtener los datos de retiro/despacho del proveedor
			VendorAddressW vendorAddressW = null;
			try {
				vendorAddressW = vendoraddressserver.getById(initParamDTO.getVendoraddress().getId());
			} catch (Exception e) {
			}
			
			if (vendorAddressW == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existe dirección para actualizar", false);
			}
			
			// Proveedor
			VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendoraddress().getVendorcode());
			if(vendorArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");
			}
			VendorW vendorW = vendorArr[0];
			
			// Obtener el tipo de actualización VeV 'Libreta configuraciones para Courier'
			VeVUpdateTypeW vevUpdateType = vevupdatetypeserver.getByPropertyAsSingleResult("code", "COURIER_CONFIG_NOTEBOOK");
			
			Date now = new Date();
						
			// Se agregan los registros de auditor�a
			VeVAuditW vevAudit = new VeVAuditW();
			vevAudit.setWhen(now);
			vevAudit.setUsername(initParamDTO.getUsername());
			vevAudit.setUsertype(initParamDTO.getUsertype());
			vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
			vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
			vevAudit.setInterfacecontent("");
			vevAudit.setItem("Libreta de direcciones Courier");
			vevAudit.setItemvalue("Descripción");
			vevAudit.setInitialdata(vendorAddressW.getDescription());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getDescription());
			vevAudit.setVendorid(vendorW.getId());
			vevAudit.setVevupdatetypeid(vevUpdateType.getId());					
			vevauditserver.addVeVAudit(vevAudit);			

			vevAudit.setItemvalue("Comuna");
			vevAudit.setInitialdata(vendorAddressW.getCommune());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getCommune());
			vevauditserver.addVeVAudit(vevAudit);
			
			vevAudit.setItemvalue("Calle");
			vevAudit.setInitialdata(vendorAddressW.getStreet());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getStreet());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("N°mero");
			vevAudit.setInitialdata(vendorAddressW.getNumber());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getNumber());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("Complemento");
			vevAudit.setInitialdata(vendorAddressW.getComment());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getComment());
			vevauditserver.addVeVAudit(vevAudit);
			
			vevAudit.setItemvalue("Tel�fono");
			vevAudit.setInitialdata(vendorAddressW.getPhone1());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getPhone1());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("Peso");
			vevAudit.setInitialdata(vendorAddressW.getWeight().toString());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getWeight().toString());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("Largo");
			vevAudit.setInitialdata(vendorAddressW.getLength().toString());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getLength().toString());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("Alto");
			vevAudit.setInitialdata(vendorAddressW.getHeight().toString());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getHeight().toString());
			vevauditserver.addVeVAudit(vevAudit);
						
			vevAudit.setItemvalue("Ancho");
			vevAudit.setInitialdata(vendorAddressW.getWidth().toString());
			vevAudit.setFinaldata(initParamDTO.getVendoraddress().getWidth().toString());
			vevauditserver.addVeVAudit(vevAudit);
						
			// Se completan los datos y actualiza la dirección
			vendorAddressW = new VendorAddressW();
			DateConverterFactory2.copyProperties(initParamDTO.getVendoraddress(), vendorAddressW);
			vendorAddressW.setVendorid(vendorW.getId());
			
			vendorAddressW.setUser1(initParamDTO.getUsername());
			vendorAddressW.setUpdatedate(now);
			vendoraddressserver.updateVendorAddress(vendorAddressW);

		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "Operación fallida");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "No se encontr� el registro");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "Operación fallida");
		}

		return resultDTO;
	}

	public BaseResultDTO addVendorAddress(VendorAddressInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtener los datos de retiro/despacho del proveedor (si existen previamente en el sistema)
			VendorAddressW vendorAddressW = null;
			try {
				vendorAddressW = vendoraddressserver.getById(initParamDTO.getVendoraddress().getId());
			} catch (Exception e) {
			}

			// Agregar datos de retiro/despacho
			if (vendorAddressW == null) {
				// Proveedor
				VendorW[] vendorArr = vendorserver.getByPropertyAsArray("rut", initParamDTO.getVendoraddress().getVendorcode());
				if(vendorArr.length == 0) {
					return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L443");
				}
				VendorW vendorW = vendorArr[0];
				
				
				// Obtener el tipo de actualización VeV 'Libreta configuraciones para Courier'
				VeVUpdateTypeW vevUpdateType = vevupdatetypeserver.getByPropertyAsSingleResult("code", "COURIER_CONFIG_NOTEBOOK");
				
				Date now = new Date();
				
				// Se agregan los registros de auditor�a
				VeVAuditW vevAudit = new VeVAuditW();
				vevAudit.setWhen(now);
				vevAudit.setUsername(initParamDTO.getUsername());
				vevAudit.setUsertype(initParamDTO.getUsertype());
				vevAudit.setUserenterprisecode(initParamDTO.getUserenterprisecode());
				vevAudit.setUserenterprisename(initParamDTO.getUserenterprisename());
				vevAudit.setInterfacecontent("");
				vevAudit.setItem("Libreta de direcciones Courier");
				vevAudit.setItemvalue("Descripción");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getDescription());
				vevAudit.setVendorid(vendorW.getId());
				vevAudit.setVevupdatetypeid(vevUpdateType.getId());
				vevauditserver.addVeVAudit(vevAudit);
							
				vevAudit.setItemvalue("Comuna");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getCommune());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Calle");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getStreet());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("N°mero");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getNumber());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Complemento");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getComment());
				vevauditserver.addVeVAudit(vevAudit);
								
				vevAudit.setItemvalue("Tel�fono");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getPhone1());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Peso");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getWeight().toString());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Largo");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getLength().toString());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Alto");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getHeight().toString());
				vevauditserver.addVeVAudit(vevAudit);
				
				vevAudit.setItemvalue("Ancho");vevAudit.setInitialdata("");
				vevAudit.setInitialdata("");
				vevAudit.setFinaldata(initParamDTO.getVendoraddress().getWidth().toString());
				vevauditserver.addVeVAudit(vevAudit);
				
				// Se completan los datos y guarda la dirección
				vendorAddressW = new VendorAddressW();
				DateConverterFactory2.copyProperties(initParamDTO.getVendoraddress(), vendorAddressW);
				vendorAddressW.setUser1(initParamDTO.getUsername());
				vendorAddressW.setUpdatedate(now);
				vendoraddressserver.addVendorAddress(vendorAddressW);
			}
			// Actualizar datos de retiro/despacho
			else {
				updateVendorAddress(initParamDTO);
			}			

		} catch (OperationFailedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "Operación fallida");
		} catch (NotFoundException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "No se encontr� el registro");
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", "Operación fallida");
		}

		return resultDTO;
	}
	
	public InvoiceVendorRollOutArrayResultDTO getInvoiceVendorRollOutReport(InvoiceVendorRollOutInitParamDTO initParamDTO) {		
		InvoiceVendorRollOutArrayResultDTO resultDTO = new InvoiceVendorRollOutArrayResultDTO();
		
		try {
			InvoiceVendorRollOutDTO[] vendorrollouts = propertyvendorserver.getInvoiceVendorRollOutReport(initParamDTO.getOrderby());
			
			int totalreg = propertyvendorserver.countInvoiceVendorRollOutReport();
			
			resultDTO.setVendorrollouts(vendorrollouts);
			resultDTO.setTotalreg(totalreg);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public BaseResultDTO doAddInvoiceVendorRollOut(AddInvoiceVendorRollOutInitParamDTO initParamDTO, String username) {		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
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
			// Validar que el proveedor seleccionado no esté configurado previamente para 'INICIO_FACT_VEVPD'
			PropertyInfoDTO prop1 = new PropertyInfoDTO("vendor.id", "vendorid", vendor.getId());
			PropertyInfoDTO prop2 = new PropertyInfoDTO("code", "code", "INICIO_FACT_VEVPD");
			PropertyVendorW[] propertyvendors = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
			if (propertyvendors != null && propertyvendors.length > 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4508", "El proveedor " + vendor.getName() + " está configurado previamente. Favor validar.", false);
			}
			
			// JPE 20181213
			// Validar que el proveedor seleccionado no esté configurado previamente para 'DIAS_FACT_VEVPD'
			prop2 = new PropertyInfoDTO("code", "code", "DIAS_FACT_VEVPD");
			propertyvendors = propertyvendorserver.getByPropertiesAsArray(prop1, prop2);
			if (propertyvendors != null && propertyvendors.length > 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4508", "El proveedor " + vendor.getName() + " está configurado previamente. Favor validar.", false);
			}
			
			// JPE 20181213
			// Validar que el valor de días ingresado esté en el rango definido como mínimo y máximo, incluyendo los í�mites
			if (initParamDTO.getInvoicemaxpreviousdays() < Integer.valueOf(B2BSystemPropertiesUtil.getProperty("mindoinvoicepreviousdays")) ||
					initParamDTO.getInvoicemaxpreviousdays() > Integer.valueOf(B2BSystemPropertiesUtil.getProperty("maxdoinvoicepreviousdays"))) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4508", "Debe ingresar una cantidad de días mayor o igual a " + B2BSystemPropertiesUtil.getProperty("mindoinvoicepreviousdays") + " y menor o igual a " + B2BSystemPropertiesUtil.getProperty("maxdoinvoicepreviousdays"), false);
			}
			
			// Agregar el proveedor al modelo de Facturación
			Date now = new Date();
			
			// INICIO_FACT_VEVPD
			PropertyVendorW propertyvendor = new PropertyVendorW();
			propertyvendor.setCode("INICIO_FACT_VEVPD");
			propertyvendor.setDescription("Fecha inicio de Facturación OC VeV PD");
			propertyvendor.setValue(initParamDTO.getInvoicesince());
			propertyvendor.setCreated(now);
			propertyvendor.setCreator(username);
			propertyvendor.setLastmodified(now);
			propertyvendor.setModifier(username);
			propertyvendor.setVendorid(vendor.getId());
			
			propertyvendorserver.addPropertyVendor(propertyvendor);
			
			// DIAS_FACT_VEVPD
			propertyvendor.setCode("DIAS_FACT_VEVPD");
			propertyvendor.setDescription("Máx. días de rendición de Facturación OC VeV PD");
			propertyvendor.setValue(initParamDTO.getInvoicemaxpreviousdays().toString());
						
			propertyvendorserver.addPropertyVendor(propertyvendor);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public BaseResultDTO doUpdateInvoiceVendorRollOut(UpdateInvoiceVendorRollOutInitParamDTO initParamDTO, String username) {		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Obtener el proveedor rollout para 'INICIO_FACT_VEVPD'
			PropertyVendorW pvInvoiceSince = null;
			try {
				pvInvoiceSince = propertyvendorserver.getById(initParamDTO.getInvoicesincepropid());
			} catch (Exception e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4509");
			}
			
			// Obtener el proveedor rollout para 'DIAS_FACT_VEVPD'
			PropertyVendorW pvInvoiceMaxPreviousDays = null;
			try {
				pvInvoiceMaxPreviousDays = propertyvendorserver.getById(initParamDTO.getInvoicemaxpreviousdayspropid());
			} catch (Exception e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4509");
			}
			
			// JPE 20181213
			// Validar que el valor de días ingresado esté en el rango definido como mínimo y máximo, incluyendo los límites
			if (initParamDTO.getInvoicemaxpreviousdays() < Integer.valueOf(B2BSystemPropertiesUtil.getProperty("mindoinvoicepreviousdays")) ||
					initParamDTO.getInvoicemaxpreviousdays() > Integer.valueOf(B2BSystemPropertiesUtil.getProperty("maxdoinvoicepreviousdays"))) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4509", "Debe ingresar una cantidad de días mayor o igual a " + B2BSystemPropertiesUtil.getProperty("mindoinvoicepreviousdays") + " y menor o igual a " + B2BSystemPropertiesUtil.getProperty("maxdoinvoicepreviousdays"), false);
			}
			
			Date now = new Date();
			
			// Modificar el proveedor al modelo de Facturación para 'INICIO_FACT_VEVPD'
			pvInvoiceSince.setValue(initParamDTO.getInvoicesince());
			pvInvoiceSince.setLastmodified(now);
			pvInvoiceSince.setModifier(username);
						
			propertyvendorserver.updatePropertyVendor(pvInvoiceSince);
			
			// Modificar el proveedor al modelo de Facturación para 'DIAS_FACT_VEVPD'
			pvInvoiceMaxPreviousDays.setValue(initParamDTO.getInvoicemaxpreviousdays().toString());
			pvInvoiceMaxPreviousDays.setLastmodified(now);
			pvInvoiceMaxPreviousDays.setModifier(username);
						
			propertyvendorserver.updatePropertyVendor(pvInvoiceMaxPreviousDays);
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public BaseResultDTO doDeleteInvoiceVendorRollOutsByIds(DeleteInvoiceVendorRollOutInitParamDTO initParamDTO) {		
		BaseResultDTO resultDTO = new BaseResultDTO();
		
		try {
			// Validar que el usuario haya seleccionado al menos un registro
			if (initParamDTO.getVendorids() == null || initParamDTO.getVendorids().length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4510", "Debe seleccionar al menos un registro", false);
			}
			
			// Eliminar los registros de los proveedores para 'INICIO_FACT_VEVPD' y 'DIAS_FACT_VEVPD'
			propertyvendorserver.deleteByVendorsAndCodes(initParamDTO.getVendorids(), new String[] {"INICIO_FACT_VEVPD", "DIAS_FACT_VEVPD"});
						
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public VendorWithoutInvoiceValidationArrayResultDTO getVendorWithoutInvoiceValidationReport(VendorWithoutInvoiceValidationInitParamDTO initParamDTO, boolean getSummaryInfo) {
		VendorWithoutInvoiceValidationArrayResultDTO resultDTO = new VendorWithoutInvoiceValidationArrayResultDTO();
		
		try {
			VendorWithoutInvoiceValidationDTO[] vendors = propertyvendorserver.getVendorWithoutInvoiceValidationReport(initParamDTO.getOrderby());
			resultDTO.setVendors(vendors);
			
			if (getSummaryInfo) {
				VendorWithoutInvoiceValidationSummaryDTO summary = propertyvendorserver.countVendorWithoutInvoiceValidationReport();
				resultDTO.setSummary(summary);
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	
	public VendorWithoutInvoiceValidationUploadResultDTO doUploadVendorsWithoutInvoiceValidation(VendorWithoutInvoiceValidationUploadInitParamDTO initParamDTO) {
		VendorWithoutInvoiceValidationUploadResultDTO resultDTO = new VendorWithoutInvoiceValidationUploadResultDTO();
		
		try {
			// Validar los registros
			if (initParamDTO.getVendordata() == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4517");
			}
			
			// Validar que los proveedores indicados existan en el sistema
			List<BaseResultDTO> baseresultList = new ArrayList<BaseResultDTO>();
			String error = "";
			List<Long> vendorIdList = new ArrayList<Long>();
			VendorW[] vendors = null;
			for (VendorWithoutInvoiceValidationUploadDTO vendorData : initParamDTO.getVendordata()) {
				vendors = vendorserver.getByPropertyAsArray("code", vendorData.getVendorcode());
				if (vendors == null || vendors.length == 0) {
					error = "Fila " + vendorData.getLine() + ": El proveedor con código " + vendorData.getVendorcode() + " no existe en el sistema";
					baseresultList.add(RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L4517", error, false));
				}
				else {
					vendorIdList.add(vendors[0].getId());
				}
			}
			
			if (baseresultList.size() > 0) {
				// Ordenar errores
				BaseResultComparator comparator = new BaseResultComparator("statusmessage", true);
				Arrays.sort(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]), comparator);
				resultDTO.setValidationerrors(baseresultList.toArray(new BaseResultDTO[baseresultList.size()]));
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L4517");				
			}
			
			// Eliminar los registros de los proveedores para 'NO_VALIDA_FACTPL'
			int total = propertyvendorserver.deleteByProperty("code", "NO_VALIDA_FACTPL");
			
			logger.info("Se eliminaron " + total + " proveedores sin validaciones de factura en la carga de PL");
			
			// Agregar los nuevos proveedores con la condición 'NO_VALIDA_FACTPL'
			total = 0;
			if (initParamDTO.getVendordata().length > 0) {
				total = propertyvendorserver.addVendorsWithoutInvoiceValidation(vendorIdList, initParamDTO.getUsername(), new Date());
			}
			
			logger.info("Se agregaron " + total + " proveedores sin validaciones de factura en la carga de PL");
			
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}

		return resultDTO;
	}
	

	public VendorsLogArrayResultDTO getVendorsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues, Boolean isdomestic){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();

		try {
			// Obtiene proveedores
			VendorW[] vendorArr = vendorserver.getByPropertyAsArrayOrdered(1, maxvalues, "domestic", isdomestic, ordercriteria);
			if (vendorArr == null || vendorArr.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L444");
			}

			VendorLogDTO[] vendorDTOs = new VendorLogDTO[vendorArr.length];
			BeanExtenderFactory.copyProperties(vendorArr, vendorDTOs, VendorLogDTO.class);

			// Cantidad total de registros
			int total = vendorserver.getCountVendors();
			
			resultDTO.setTotal(total);
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public VendorsLogArrayResultDTO getVendorsLikeCode(String code, Boolean isdomestic) {
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		try{
			List<VendorW> vendorWList = vendorserver.getLikeStringProperty("code", code, false);
			VendorW[] vendorWs = vendorWList.stream().filter(ve -> ve.getDomestic().equals(isdomestic)).toArray(VendorW[]::new);

			if(vendorWs == null || vendorWs.length <=0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}
			VendorLogDTO[] vendorDTOs = new VendorLogDTO[vendorWs.length];
			BeanExtenderFactory.copyProperties(vendorWs, vendorDTOs, VendorLogDTO.class);
			
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public VendorsLogArrayResultDTO getVendorsLikeName(String name, Boolean isdomestic){
		VendorsLogArrayResultDTO resultDTO = new VendorsLogArrayResultDTO();
		try{

			List<VendorW> vendorWList = vendorserver.getLikeStringProperty("name", name, false);
			VendorW[] vendorWs = vendorWList.stream().filter(ve -> ve.getDomestic().equals(isdomestic)).toArray(VendorW[]::new);
			
			if(vendorWs == null || vendorWs.length <=0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L600");
			}
			VendorLogDTO[] vendorDTOs = new VendorLogDTO[vendorWs.length];
			BeanExtenderFactory.copyProperties(vendorWs, vendorDTOs, VendorLogDTO.class);
			
			resultDTO.setVendors(vendorDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}	

	
	public LocationsLogArrayResultDTO getLocationsLimitByMax(OrderCriteriaDTO[] ordercriteria, Integer maxvalues){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();

		try {
			// Obtiene locales
			LocationW[] locationArr = locationserver.getAllAsArrayOrdered(1, maxvalues, ordercriteria);
			if (locationArr == null || locationArr.length <= 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}

			LocationLogDTO[] locationsDTOs = new LocationLogDTO[locationArr.length];
			BeanExtenderFactory.copyProperties(locationArr, locationsDTOs, LocationLogDTO.class);

			// Cantidad total de registros
			int total = locationserver.getCountLocations();
			
			resultDTO.setTotal(total);
			resultDTO.setLocations(locationsDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}
	
	
	public LocationsLogArrayResultDTO getLocationsLikeCode(String code) {
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		try{
			List<LocationW> locationWList = locationserver.getLikeStringProperty("code", code, false);
			LocationW[] locationWs  = locationWList.toArray(new LocationW[locationWList.size()]);
			if(locationWs == null || locationWs.length <=0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}
			LocationLogDTO[] locationDTOs = new LocationLogDTO[locationWs.length];
			BeanExtenderFactory.copyProperties(locationWs, locationDTOs, LocationLogDTO.class);
			
			resultDTO.setLocations(locationDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	
	public LocationsLogArrayResultDTO getLocationsLikeName(String name){
		LocationsLogArrayResultDTO resultDTO = new LocationsLogArrayResultDTO();
		try{
			 
			List<LocationW> locationWList = locationserver.getLikeStringProperty("name", name, false);
			LocationW[] locationWs  = locationWList.toArray(new LocationW[locationWList.size()]);
			if(locationWs == null || locationWs.length <=0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L700");
			}
			LocationLogDTO[] locationDTOs = new LocationLogDTO[locationWs.length];
			BeanExtenderFactory.copyProperties(locationWs, locationDTOs, LocationLogDTO.class);
			
			resultDTO.setLocations(locationDTOs);
			return resultDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
	}
}
