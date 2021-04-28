package bbr.b2b.regional.logistic.mobile.managers.classes;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

import org.apache.log4j.Logger;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.common.util.Mailer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryDetailServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryImageServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DODeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DOReceptionTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.TruckDispatcherServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryImageW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DODeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.TruckDispatcherW;
import bbr.b2b.regional.logistic.deliveries.managers.interfaces.DODeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliveryReportResultDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.DoChangeDODeliveryStateTypeInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryDetailMobileBackendInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryDetailMobileInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryItemsMobileDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryItemsMobileResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileBackendInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryMobileResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryReportDataMobileDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryReportResultMobileDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryTokenCreationInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryUpdateStatusMobileBackendInitParmDTO;
import bbr.b2b.regional.logistic.mobile.classes.DODeliveryUpdateStatusMobileInitParmDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherCheckTokenInitParamDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherInputDataDTO;
import bbr.b2b.regional.logistic.mobile.classes.TruckDispatcherResultDTO;
import bbr.b2b.regional.logistic.mobile.classes.UserTokenResultDTO;
import bbr.b2b.regional.logistic.mobile.managers.interfaces.MobileDeliveryReportManagerLocal;
import bbr.b2b.regional.logistic.mobile.managers.interfaces.MobileDeliveryReportManagerRemote;
import bbr.b2b.regional.logistic.utils.Base64Utils;
import bbr.b2b.regional.logistic.utils.EmailValidation;
import bbr.b2b.regional.logistic.utils.RandomGenerator;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

@Stateless(name = "managers/MobileDeliveryReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MobileDeliveryReportManager implements MobileDeliveryReportManagerLocal, MobileDeliveryReportManagerRemote {

	@EJB
	TruckDispatcherServerLocal truckdispatcherserver;
	
	@EJB
	VendorServerLocal vendorserver;

	@EJB
	DODeliveryStateTypeServerLocal dodeliverystatetypeserver;

	@EJB
	DODeliveryServerLocal dodeliveryserver;

	@EJB
	DODeliveryDetailServerLocal dodeliverydetailserver;

	@EJB
	DOReceptionTypeServerLocal doreceptiontypeserver;

	@EJB
	DODeliveryReportManagerLocal dodeliveryreportmanager;

	@EJB
	DODeliveryImageServerLocal dodeliveryimageserver;

	@Resource
	private javax.ejb.SessionContext mySessionCtx;

	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}

	private static Logger logger = Logger.getLogger(MobileDeliveryReportManager.class);

	public TruckDispatcherW getTruckDispatcherByEmail(String email) {
		TruckDispatcherW truckdispatcherW = new TruckDispatcherW();
		try {
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertyAsArray("email", email);
			if (truckdispatcherArr.length > 0) {
				return truckdispatcherArr[0];
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return truckdispatcherW;
	}

	public TruckDispatcherResultDTO doAddOrUpdateTruckDispatcher(TruckDispatcherInputDataDTO inputData) {

		TruckDispatcherResultDTO resultDTO = new TruckDispatcherResultDTO();

		try {
			// Fecha actual
			Date now = new Date();

			// 3.1 El usuario no ingreso datos para el campo �Nombre�.
			if (inputData.getName() == null || inputData.getName().length() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10001");
			}
			// 3.2 El usuario no ingreso datos para el campo �Tel�fono�.
			if (inputData.getPhone() == null || inputData.getPhone().length() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10002");
			}

			// 3.3 El usuario no ingreso datos para el campo �Correo�.
			if (inputData.getEmail() == null || inputData.getEmail().length() == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10003");
			}

			// 3.4 El dato ingresado para el campo �Correo� no corresponde a uno de correo
			if (!EmailValidation.isValid(inputData.getEmail())) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10004");
			}

			// Crea Nuevo codigo unico para transportista
			String uniquecode = RandomGenerator.generateRandomString(6);

			// Valida si existe transportista
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertyAsArray("email", inputData.getEmail());
			if (truckdispatcherArr.length == 0) {
				TruckDispatcherW newTruckdispatcherW = new TruckDispatcherW();
				newTruckdispatcherW.setName(inputData.getName());
				newTruckdispatcherW.setPhone(inputData.getPhone());
				newTruckdispatcherW.setEmail(inputData.getEmail());
				newTruckdispatcherW.setActivationdate(now);
				newTruckdispatcherW.setActive(true);
				newTruckdispatcherW.setValidationcode(uniquecode);
				newTruckdispatcherW.setGenerationcodedate(now);
				newTruckdispatcherW.setUsercreationdate(now);
				newTruckdispatcherW = truckdispatcherserver.addTruckDispatcher(newTruckdispatcherW);

				resultDTO.setEmail(newTruckdispatcherW.getEmail());
				resultDTO.setName(newTruckdispatcherW.getName());
				resultDTO.setPhone(newTruckdispatcherW.getName());
				resultDTO.setId(newTruckdispatcherW.getId());

			} else {
				TruckDispatcherW truckdispatcherW = truckdispatcherArr[0];
				truckdispatcherW.setName(inputData.getName());
				truckdispatcherW.setPhone(inputData.getPhone());
				truckdispatcherW.setValidationcode(uniquecode);
				truckdispatcherW.setGenerationcodedate(now);
				truckdispatcherW = truckdispatcherserver.updateTruckDispatcher(truckdispatcherW);

				resultDTO.setEmail(truckdispatcherW.getEmail());
				resultDTO.setName(truckdispatcherW.getName());
				resultDTO.setPhone(truckdispatcherW.getPhone());
				resultDTO.setId(truckdispatcherW.getId());
			}

			// Envia mail al correo indicado con el codigo generado
			String subject = "Gestor de Despachos: código de Verificación";
			Mailer mailer = Mailer.getInstance();
			String mailsender = RegionalLogisticConstants.getInstance().getMailSender();
			String mailSession = RegionalLogisticConstants.getInstance().getMAIL_SESSION();
			String[] mailreciever = new String[1];
			mailreciever[0] = inputData.getEmail();

			if (mailreciever[0] != null && !mailreciever[0].equals("")) {
				String msgText = "Estimado " + inputData.getName() + "<br><br>" + //
						"Ha solicitado acceso para la gestión de despachos. <br>" + // 
						"Para poder continuar favor haga click en el siguiente " + //
						"<a href=\"http://www.google.com\"> enlace </a><br>" + //
						"o ingrese el código de verificación en su aplicación: <br><br>" + // 
						"código de Verificación: " + uniquecode + "<br><br>" + //
						"Atte, <br><br>" + //
						"Gestor de Despachos.";
				try {
					mailer.sendMailBySession(mailreciever, null, null, mailsender, subject, msgText, true, null, mailSession);

				} catch (Exception e) {
					e.printStackTrace();
					logger.info("No fue posible enviar email con código de acceso");
				}
			}
			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public UserTokenResultDTO doCreateUserToken(DODeliveryTokenCreationInitParamDTO initParamDTO) {
		UserTokenResultDTO resultDTO = new UserTokenResultDTO();

		try {
			BaseResultDTO validationresultDTO = doCheckUserValidData(initParamDTO);
			if (!validationresultDTO.getStatuscode().equals("0")) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, validationresultDTO.getStatuscode());
			}

			// Crea token
			String uniquecode = new String();
			boolean flag = true;

			while (flag) {
				uniquecode = RandomGenerator.generateRandomString(32);
				TruckDispatcherW[] tokenArr = truckdispatcherserver.getByPropertyAsArray("token", uniquecode);
				// Si codigo ya existe, genera uno nuevo
				if (tokenArr.length == 0) {
					flag = false;
				}
			}
			TruckDispatcherW truckDispatcherW = truckdispatcherserver.getByPropertyAsSingleResult("email", initParamDTO.getEmail());
			truckDispatcherW.setToken(uniquecode);
			truckdispatcherserver.updateTruckDispatcher(truckDispatcherW);

			resultDTO.setToken(uniquecode);
			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public BaseResultDTO doCheckUserValidData(DODeliveryTokenCreationInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Valida que combinacion email - codigo validacion existe
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("email", "email", initParamDTO.getEmail());
			properties[1] = new PropertyInfoDTO("validationcode", "validationcode", initParamDTO.getValidationcode());
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertiesAsArray(properties);

			if (truckdispatcherArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10010");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");

		}
		return resultDTO;
	}

	public BaseResultDTO doCheckUserToken(TruckDispatcherCheckTokenInitParamDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Valida que combinacion email - codigo validacion existe
			PropertyInfoDTO[] properties = new PropertyInfoDTO[2];
			properties[0] = new PropertyInfoDTO("id", "id", initParamDTO.getUserid());
			properties[1] = new PropertyInfoDTO("token", "token", initParamDTO.getToken());
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertiesAsArray(properties);

			if (truckdispatcherArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10010");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		return resultDTO;
	}

	public DODeliveryReportResultMobileDTO getDODeliveriesMobileByTruckDispatcher(DODeliveryMobileInitParamDTO initParamDTO) {
		DODeliveryReportResultMobileDTO resultDTO = new DODeliveryReportResultMobileDTO();
		DODeliveryReportDataMobileDTO[] dodeliverydata = null;

		try {
			// Obtiene Truck Dispatcher
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertyAsArray("id", initParamDTO.getUserid());
			if (truckdispatcherArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10005");
			}
			TruckDispatcherW truckdispatcherW = truckdispatcherArr[0];

			// Consulta por información de despachos vigentes asociados al transportista
			DODeliveryStateTypeW[] dovalidState = dodeliverystatetypeserver.getByPropertyAsArray("closed", false);
			if (dovalidState.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}
			Long[] dovalidstateids = new Long[dovalidState.length];
			for (int i = 0; i < dovalidstateids.length; i++) {
				dovalidstateids[i] = dovalidState[i].getId();
			}

			dodeliverydata = dodeliveryserver.getDODeliveryDataByTruckDispatcher(truckdispatcherW.getEmail(), dovalidstateids);

			int total = dodeliverydata.length;

			resultDTO.setDodeliverydata(dodeliverydata);
			resultDTO.setTotalorderdelivery(total);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public DODeliveryReportResultMobileDTO getDODeliveriesMobileByTruckDispatcherBackend(DODeliveryMobileBackendInitParamDTO initParamDTO) {
		DODeliveryReportResultMobileDTO resultDTO = new DODeliveryReportResultMobileDTO();
		DODeliveryReportDataMobileDTO[] dodeliverydata = null;

		try {
			// Consulta por información de despachos vigentes asociados a un email
			DODeliveryStateTypeW[] dovalidState = dodeliverystatetypeserver.getByPropertyAsArray("closed", false);
			if (dovalidState.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}
			Long[] dovalidstateids = new Long[dovalidState.length];
			for (int i = 0; i < dovalidstateids.length; i++) {
				dovalidstateids[i] = dovalidState[i].getId();
			}

			dodeliverydata = dodeliveryserver.getDODeliveryDataByTruckDispatcher(initParamDTO.getUseremail(), dovalidstateids);
			
			// POR CADA DELIVERY, SETEAR EL RETAILERCODE Y RETAILERNAME Y SEARCHFIELD AGREGANDO ESTA INFORMACION
			for (int i = 0; i < dodeliverydata.length; i++) {
				dodeliverydata[i].setRetailercode(RegionalLogisticConstants.getInstance().getMOBILE_RETAILER_CODE());
				dodeliverydata[i].setRetailername(RegionalLogisticConstants.getInstance().getMOBILE_RETAILER_NAME());
				dodeliverydata[i].setSearchfield(dodeliverydata[i].getSearchfield() + " " + dodeliverydata[i].getRetailercode() + " " + dodeliverydata[i].getRetailername());
			}

			int total = dodeliverydata.length;

			resultDTO.setDodeliverydata(dodeliverydata);
			resultDTO.setTotalorderdelivery(total);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public DODeliveryMobileResultDTO getDODeliveryDataMobile(DODeliveryDetailMobileInitParamDTO initParamDTO) {
		DODeliveryMobileResultDTO resultDTO = new DODeliveryMobileResultDTO();

		try {
			// Consulta el despacho seleccionado
			// TODO DVI : SE DEBE CONSULTAR POR USERID Y DELIVERYID PARA ASEGURAR QUE EL USUARIO PUEDE VER EL DESPACHO
			DODeliveryMobileDataDTO dodeliverydata = dodeliveryserver.getDODeliveryDataById(initParamDTO.getDeliveryid());

			resultDTO.setDodeliverydata(dodeliverydata);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public DODeliveryMobileResultDTO getDODeliveryDataMobileBackend(DODeliveryDetailMobileBackendInitParamDTO initParamDTO) {
		DODeliveryMobileResultDTO resultDTO = new DODeliveryMobileResultDTO();

		try {
			// Consulta el despacho seleccionado
			// TODO DVI : SE DEBE CONSULTAR POR DISPATCHEREMAIL Y DELIVERYID PARA ASEGURAR QUE EL USUARIO PUEDE VER EL
			// DESPACHO
			DODeliveryMobileDataDTO dodeliverydata = dodeliveryserver.getDODeliveryDataById(initParamDTO.getDeliveryid());

			// SETEAR EL RETAILERCODE Y RETAILERNAME
			dodeliverydata.setRetailercode(RegionalLogisticConstants.getInstance().getMOBILE_RETAILER_CODE());
			dodeliverydata.setRetailername(RegionalLogisticConstants.getInstance().getMOBILE_RETAILER_NAME());

			resultDTO.setDodeliverydata(dodeliverydata);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public DODeliveryItemsMobileResultDTO getDODeliveryDetailItemsMobile(DODeliveryDetailMobileInitParamDTO initParamDTO) {
		DODeliveryItemsMobileResultDTO resultDTO = new DODeliveryItemsMobileResultDTO();

		try {
			// Items del despacho
			// TODO DVI : SE DEBE CONSULTAR POR USERID Y DELIVERYID PARA ASEGURAR QUE EL USUARIO PUEDE VER EL DESPACHO
			DODeliveryItemsMobileDataDTO[] dodeliveryitems = dodeliverydetailserver.getDODeliveryItemsReport(initParamDTO.getDeliveryid());
			resultDTO.setItemsdata(dodeliveryitems);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public DODeliveryItemsMobileResultDTO getDODeliveryDetailItemsMobileBackend(DODeliveryDetailMobileBackendInitParamDTO initParamDTO) {
		DODeliveryItemsMobileResultDTO resultDTO = new DODeliveryItemsMobileResultDTO();

		try {
			// Items del despacho
			// TODO DVI : SE DEBE CONSULTAR POR DISPATCHEREMAIL Y DELIVERYID PARA ASEGURAR QUE EL USUARIO PUEDE VER EL
			// DESPACHO
			DODeliveryItemsMobileDataDTO[] dodeliveryitems = dodeliverydetailserver.getDODeliveryItemsReport(initParamDTO.getDeliveryid());
			resultDTO.setItemsdata(dodeliveryitems);

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public BaseResultDTO doUpdateDODeliveryStatusMobile(DODeliveryUpdateStatusMobileInitParmDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
			// Obtiene Truck Dispatcher
			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertyAsArray("id", initParamDTO.getUserid());
			if (truckdispatcherArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10005");
			}
			TruckDispatcherW truckdispatcherW = truckdispatcherArr[0];

			// Obtiene Despacho
			DODeliveryW dodeliveryW = null;
			try {
				dodeliveryW = dodeliveryserver.getById(initParamDTO.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			} catch (Exception e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L3011");
			}

			// Estado del despacho
			DODeliveryStateTypeW[] newStArr = dodeliverystatetypeserver.getByPropertyAsArray("code", initParamDTO.getDodstatuscode());
			if (newStArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}
			DODeliveryStateTypeW newSt = newStArr[0];

			VendorW vendor = vendorserver.getById(dodeliveryW.getVendorid());
			
			// Llama a servicio que actualiza estado
			DoChangeDODeliveryStateTypeInitParamDTO initParamChangeState = new DoChangeDODeliveryStateTypeInitParamDTO();
			initParamChangeState.setComment(initParamDTO.getDodcomment());
			initParamChangeState.setDodeliveryid(initParamDTO.getDeliveryid());
			initParamChangeState.setDodeliverystatetypeid(newSt.getId());
			initParamChangeState.setReprogdate(DateConverterFactory2.DateToStr(dodeliveryW.getCommiteddate())); // mantiene fecha del despacho
			initParamChangeState.setVendorcode(vendor.getRut());
			initParamChangeState.setUsername(truckdispatcherW.getName());
			initParamChangeState.setUsertype("APLICAción MÓVIL");
			initParamChangeState.setUserenterprisecode(truckdispatcherW.getEmail());
			initParamChangeState.setUserenterprisename(truckdispatcherW.getEmail());

			DODeliveryReportResultDTO changestatusresultDTO = dodeliveryreportmanager.doChangeDODeliveryStateType(initParamChangeState, dodeliveryW, false);

			// Si actualizacion fue exitosa y existe foto, la agrega al despacho
			if ((changestatusresultDTO.getStatuscode().equals("0")) && (initParamDTO.getImagedata() != null && !initParamDTO.getImagedata().equals(""))) {

				// Crea DoDeliveryImage
				Date now = new Date();

				DODeliveryImageW dodeliveryimageW = new DODeliveryImageW();
				dodeliveryimageW.setDodeliveryid(dodeliveryW.getId());
				// DVI: 2019-08-27 LAS IM�GENES YA NO EST�N ASOCIADOS A UN TRUCKDISPATCH_ID, SE BUSCAN POR ODDELIVERY ->
				// DISPATCHEREMAIL
				// dodeliveryimageW.setTruckdispatcherid(truckdispatcherW.getId());
				// dodeliveryimageW.setImagedata(initParamDTO.getImagedata());
				dodeliveryimageW.setCurrenttimestamp(now);
				dodeliveryimageW.setReceivername("");
				dodeliveryimageW.setReceiverrut("");
				dodeliveryimageW.setReceptiondate(new Date());

				if (initParamDTO.getImagedata() != null && initParamDTO.getImagedata().length() > 0) {
					File imagefile = null;
					try {
						// almacena imagen en disco
						String filename = String.valueOf(System.nanoTime()) + "." + RegionalLogisticConstants.getInstance().getACTAENTREGA_EXTENSION_IMAGE();
						imagefile = Base64Utils.getInstance().saveToFile(initParamDTO.getImagedata(), RegionalLogisticConstants.getInstance().getACTAENTREGA_FOLDER_IMAGE(), filename);
					} catch (Exception e) {
						getSessionContext().setRollbackOnly();
						String error = "No es posible almacenar la imagen";
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", error);
					}
					dodeliveryimageW.setFilename(imagefile.getName());
				}

				dodeliveryimageserver.addDODeliveryImage(dodeliveryimageW);
			}

			resultDTO.setStatuscode(changestatusresultDTO.getStatuscode());
			resultDTO.setStatusmessage(changestatusresultDTO.getStatusmessage());

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

	public BaseResultDTO doUpdateDODeliveryStatusMobileBackend(DODeliveryUpdateStatusMobileBackendInitParmDTO initParamDTO) {
		BaseResultDTO resultDTO = new BaseResultDTO();

		try {
//			// JPE 20200615
//			// Obtener Truck Dispatcher (asumimos que se est� enviando username en el useremail)
//			TruckDispatcherW[] truckdispatcherArr = truckdispatcherserver.getByPropertyAsArray("name", initParamDTO.getUseremail().trim());
//			if (truckdispatcherArr.length == 0) {
//				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L10005");
//			}
//			TruckDispatcherW truckdispatcherW = truckdispatcherArr[0];
			
			// Obtiene Despacho por ID
			DODeliveryW dodeliveryW = null;
			try {
				dodeliveryW = dodeliveryserver.getById(initParamDTO.getDeliveryid(), LockModeType.PESSIMISTIC_WRITE);
			} catch (Exception e) {
				e.printStackTrace();
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L3011");
			}

			// Estado del despacho
			DODeliveryStateTypeW[] newStArr = dodeliverystatetypeserver.getByPropertyAsArray("code", initParamDTO.getDodstatuscode());
			if (newStArr.length == 0) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L900");
			}
			DODeliveryStateTypeW newSt = newStArr[0];
			
			VendorW vendor = vendorserver.getById(dodeliveryW.getVendorid());

			// Llama a servicio que actualiza estado
			DoChangeDODeliveryStateTypeInitParamDTO initParamChangeState = new DoChangeDODeliveryStateTypeInitParamDTO();
			initParamChangeState.setComment(initParamDTO.getDodcomment());
			initParamChangeState.setDodeliveryid(initParamDTO.getDeliveryid());
			initParamChangeState.setDodeliverystatetypeid(newSt.getId());
			initParamChangeState.setReprogdate(DateConverterFactory2.DateToStr(dodeliveryW.getCommiteddate())); // mantiene fecha del despacho
			initParamChangeState.setVendorcode(vendor.getRut());
			initParamChangeState.setUsername(""/*truckdispatcherW.getName()*/);
			initParamChangeState.setUsertype("APLICACIÓN MÓVIL");
			initParamChangeState.setUserenterprisecode(""/*truckdispatcherW.getEmail()*/);
			initParamChangeState.setUserenterprisename(""/*truckdispatcherW.getEmail()*/);

			DODeliveryReportResultDTO changestatusresultDTO = dodeliveryreportmanager.doChangeDODeliveryStateType(initParamChangeState, dodeliveryW, false);

			// Si actualizacion fue exitosa y existe foto, la agrega al despacho
			if ((changestatusresultDTO.getStatuscode().equals("0")) && (initParamDTO.getImagedata() != null && !initParamDTO.getImagedata().equals(""))) {

				// Crea DoDeliveryImage
				Date now = new Date();

				DODeliveryImageW dodeliveryimageW = new DODeliveryImageW();
				dodeliveryimageW.setDodeliveryid(dodeliveryW.getId());
				// DVI: 2019-08-27 LAS IM�GENES YA NO EST�N ASOCIADOS A UN TRUCKDISPATCH_ID, SE BUSCAN POR ODDELIVERY ->
				// DISPATCHEREMAIL
				// dodeliveryimageW.setTruckdispatcherid(truckdispatcherW.getId());
				// dodeliveryimageW.setImagedata(initParamDTO.getImagedata());
				dodeliveryimageW.setCurrenttimestamp(now);
				dodeliveryimageW.setReceivername("");
				dodeliveryimageW.setReceiverrut("");
				dodeliveryimageW.setReceptiondate(new Date());

				if (initParamDTO.getImagedata() != null && initParamDTO.getImagedata().length() > 0) {
					File imagefile = null;
					try {
						// almacena imagen en disco
						String filename = String.valueOf(System.nanoTime()) + "." + RegionalLogisticConstants.getInstance().getACTAENTREGA_EXTENSION_IMAGE();
						imagefile = Base64Utils.getInstance().saveToFile(initParamDTO.getImagedata(), RegionalLogisticConstants.getInstance().getACTAENTREGA_FOLDER_IMAGE(), filename);
					} catch (Exception e) {
						getSessionContext().setRollbackOnly();
						String error = "No es posible almacenar la imagen";
						return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", error);
					}
					dodeliveryimageW.setFilename(imagefile.getName());
				}

				dodeliveryimageserver.addDODeliveryImage(dodeliveryimageW);
			}

			resultDTO.setStatuscode(changestatusresultDTO.getStatuscode());
			resultDTO.setStatusmessage(changestatusresultDTO.getStatusmessage());

			return resultDTO;

		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
	}

}
