package bbr.b2b.logistic.vev.managers.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.items.classes.ItemServerLocal;
import bbr.b2b.logistic.managers.interfaces.VeVReportManagerRemote;
import bbr.b2b.logistic.rest.client.HitesCLRestFulWSClient;
import bbr.b2b.logistic.utils.B2BSystemPropertiesUtil;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.utils.LogisticStatusCodeUtils;
import bbr.b2b.logistic.vev.managers.interfaces.VeVReportManagerLocal;
import bbr.b2b.logistic.vev.report.classes.AvailableAndDailyStockUpdateInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockDataDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockReportDataDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockSendDataWSDTO;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVInitParamFileDTO;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVResultDTO;
import bbr.b2b.logistic.vev.report.classes.StockDetailWSDTO;
import bbr.b2b.logistic.vev.report.classes.StockReportInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.StockWSReportDataDTO;
import bbr.b2b.logistic.vev.report.classes.VeVUploadErrorDTO;
import bbr.b2b.logistic.vev.report.classes.WSReportUploadDataDTO;
	
@Stateless(name = "managers/VeVReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VeVReportManager implements VeVReportManagerLocal, VeVReportManagerRemote {
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	@EJB
	ItemServerLocal itemserver;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	// CU 041: Filtrar Stock Disponible
	// CU 042: Listar Stock Disponible
	public AvailableStockReportDataDTO getAvailableStockReport(StockReportInitParamDTO initParamDTO){
		AvailableStockReportDataDTO resultDTO = new AvailableStockReportDataDTO();
		AvailableStockDataDTO[] availableStockArr = null;
		StockWSReportDataDTO wsresultDTO = new StockWSReportDataDTO();
		
		try {
			HitesCLRestFulWSClient client = HitesCLRestFulWSClient.getInstance();
			wsresultDTO = client.getStockWS(initParamDTO.getVendorrut());
			
			if (wsresultDTO.getStatuscode().equals("0")) {
				if (wsresultDTO.getStockwsdata() != null && wsresultDTO.getStockwsdata().getStockdetailws() != null) {
					// Carga reporte salida
					availableStockArr = new AvailableStockDataDTO[wsresultDTO.getStockwsdata().getStockdetailws().length];
					for (int i = 0; i < wsresultDTO.getStockwsdata().getStockdetailws().length; i++) {
						AvailableStockDataDTO availablestockdata = new AvailableStockDataDTO();
						availablestockdata.setItemcode(wsresultDTO.getStockwsdata().getStockdetailws()[i].getCode());
						availablestockdata.setItemdescription(wsresultDTO.getStockwsdata().getStockdetailws()[i].getDescription());
						availablestockdata.setPropro(wsresultDTO.getStockwsdata().getStockdetailws()[i].getPropro());
						availablestockdata.setAvailablestock(wsresultDTO.getStockwsdata().getStockdetailws()[i].getAvailablestock());
						availablestockdata.setDailyrep(wsresultDTO.getStockwsdata().getStockdetailws()[i].getDailyrep());
						availablestockdata.setWarehouse(wsresultDTO.getStockwsdata().getStockdetailws()[i].getWarehouse());
						availableStockArr[i] = availablestockdata;
					}
					
					// Leadtime report
					resultDTO.setAvailablestock(availableStockArr);
				}
			}
			else {
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
			}
			
		} catch(Exception e){
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L7004");
		}
		
		return resultDTO;
	}
			
	// CU 044: Modificar Stock Disponible 
	public WSReportUploadDataDTO updateAvailableAndDailyRepStock(AvailableAndDailyStockUpdateInitParamDTO initParamDTO, boolean isExcel) {
		WSReportUploadDataDTO resultDTO = new WSReportUploadDataDTO(); 
		WSReportUploadDataDTO wsresultDTO = new WSReportUploadDataDTO();

		try {
			// Enviar modificaciones vía WS
			HitesCLRestFulWSClient client = HitesCLRestFulWSClient.getInstance();
			wsresultDTO =  client.putAvailableAndDailyRepStockWS(initParamDTO, isExcel);
			
			// Error en la actualización
			if (wsresultDTO.getStatuscode().equals("0")) {
				if (isExcel) {
					// Registrar mensajes cuando  existan errores en algunos registros
					resultDTO.setErrorsws(wsresultDTO.getErrorsws());
					resultDTO.setNumRegsws(wsresultDTO.getNumRegsws());
					resultDTO.setNumErrorsws(wsresultDTO.getNumErrorsws());
				}
				else {
					if (wsresultDTO.getItemstatus() != 0) {
						// Error actualizando el registro
						resultDTO.setStatuscode(Integer.toString(wsresultDTO.getItemstatus()));
						resultDTO.setStatusmessage(wsresultDTO.getItemmessage());
					}
					
					resultDTO.setItemmessage(wsresultDTO.getItemmessage());
					resultDTO.setItemstatus(wsresultDTO.getItemstatus());
				}
			}
			else {
				resultDTO.setStatuscode(wsresultDTO.getStatuscode());
				resultDTO.setStatusmessage(wsresultDTO.getStatusmessage());
			}				
		
		} catch(Exception e) {
			e.printStackTrace();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;			
	}
		
	// CU 045: Actualización masiva de Stock Disponible
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public DoUploadStockVeVResultDTO doUploadStockVeV(DoUploadStockVeVInitParamFileDTO initParamDTO) throws OperationFailedException, NotFoundException, AccessDeniedException {
		DoUploadStockVeVResultDTO resultDTO = new DoUploadStockVeVResultDTO();

		// Almacenar los mensajes de error que se vayan produciendo
		List<BaseResultDTO> baseresultlist = new ArrayList<BaseResultDTO>();
		LogisticConstants.getInstance();
		int  maxErrors = LogisticConstants.getMAX_LIST_ERROR_TO_SHOW();

		try {
			String localFile = initParamDTO.getFilename();

			// Almacenar el mensaje de error que se envía al Portal
			String error = "";

			// Listado de errores (en caso que existan)
			VeVUploadErrorDTO[] errorArr;

			// Almacenar los totales de filas devueltos en cada query
			int total;

			// Crear tabla temporal para guardar Stock de Unidades VeV
			itemserver.doCreateTempTableStock();

			// Crear tabla temporal para guardar Errores
			itemserver.doCreateTempTableError();

			// Cargar archivo CSV
			try {
				total = itemserver.doLoadCSV(localFile);
			} catch (IOException e) {
				error = "Error procesando el archivo de stock";
				System.out.println(error + ": " + localFile);
				baseresultlist.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));//TODO revisar codigo
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004");
			}

			// No hay registros, ocurrió un error procesando el archivo
			if (total <= 0) {
				error = "El archivo que intenta cargar no tienen información de stock. Revise e intente nuevamente";
				System.out.println(error);
				baseresultlist.add(LogisticStatusCodeUtils.getInstance().setStatusCode(new BaseResultDTO(), "L1", error, false));
				getSessionContext().setRollbackOnly();
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004");
			}
			
			// Aplicar las validaciones a todo el archivo		
			itemserver.doCheckDocumentData();
			
			// Validar si existe algún error en la información cargada
			errorArr = itemserver.getErrorsOfStockUpload();
			if (errorArr.length > 0) {				
				int errorsSize = errorArr.length;
				if (errorArr.length > maxErrors) {
					errorsSize = maxErrors;
				}

				BaseResultDTO[] curResultArr = new BaseResultDTO[errorsSize];
				for (int i = 0; i < errorsSize; i++) {
					BaseResultDTO curResult = new BaseResultDTO();
					curResult.setStatuscode("L1");
					curResult.setStatusmessage("Fila " + errorArr[i].getLine() + ": " + errorArr[i].getError());
					curResultArr[i] = curResult;
				}
				
				itemserver.doCleanErrorRowsFromStockUpload();

				resultDTO.setBaseresults(curResultArr);
								
				return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L6004");
			}
			
			// Obtener los datos enviados en el archivo
			AvailableStockSendDataWSDTO[] stock = itemserver.getValidStockUpload();
			
			// Obtener listado de stock disponible
			HitesCLRestFulWSClient client = HitesCLRestFulWSClient.getInstance();
			StockWSReportDataDTO wsResultReportDTO = client.getStockWS(initParamDTO.getProveedor());

			if (wsResultReportDTO.getStatuscode().equals("0")) {
				HashMap<String, Integer> stockDetailList = new HashMap<String, Integer>();
				if (wsResultReportDTO.getStockwsdata() != null && wsResultReportDTO.getStockwsdata().getStockdetailws() != null) {
					for (StockDetailWSDTO stockDetail : wsResultReportDTO.getStockwsdata().getStockdetailws()) {
						stockDetailList.put(stockDetail.getCode(), stockDetail.getAvailablestock());
					}					
				}
				
				// Validar si hay actualizaciones en el archivo
				List<AvailableStockSendDataWSDTO> stockUpdateList = new ArrayList<AvailableStockSendDataWSDTO>();
				for (AvailableStockSendDataWSDTO stockItem : stock) {
					// Los productos que no cambian no son enviados
					if (stockItem.getStockdisp().equals(stockDetailList.get(stockItem.getItemcode()))) {
						continue;
					}
					
					stockUpdateList.add(stockItem);
				}
				
				// Si hay al menos un producto a actualizar
				if (stockUpdateList.size() > 0) {
					stock = (AvailableStockSendDataWSDTO[]) stockUpdateList.toArray(new AvailableStockSendDataWSDTO[stockUpdateList.size()]);
					
					// Dividir el arreglo en bloques de X elementos
					int chunkSize = Integer.parseInt(B2BSystemPropertiesUtil.getProperty("VEV_WS_CHUNK_SIZE").trim());
					
					Object[] splitStock = ClassUtilities.splitArray(stock, AvailableStockSendDataWSDTO.class, chunkSize);
					Object[] stockChunk = new Object[splitStock.length];
					AvailableAndDailyStockUpdateInitParamDTO initparam;
					WSReportUploadDataDTO wsResultUploadDTO;
					boolean updated = false;
					for (int i = 0; i < splitStock.length; i++) {
						Object[] split = (Object[]) splitStock[i];
						if (split.length <= 0) {
							stockChunk[i] = new BaseResultDTO[0];
							continue;
						}
						
						initparam = new AvailableAndDailyStockUpdateInitParamDTO();
						initparam.setStock((AvailableStockSendDataWSDTO[]) splitStock[i]);
						initparam.setDate(initParamDTO.getFecha());
						initparam.setDateformat(initParamDTO.getFormatoFecha());
						initparam.setUser(initParamDTO.getTipoUsuario());
						initparam.setUsertype(initParamDTO.getUsuario());
						initparam.setVendordni(initParamDTO.getProveedor());
						
						// Llamada al servicio para actualizar stock
						wsResultUploadDTO = updateAvailableAndDailyRepStock(initparam, true);
						
						if (wsResultUploadDTO.getStatuscode().equals("0")) {
							if (wsResultUploadDTO.getErrorsws() != null && wsResultUploadDTO.getErrorsws().length > 0) { 
								stockChunk[i] = wsResultUploadDTO.getErrorsws();
							}
							else {
								stockChunk[i] = new BaseResultDTO[0];
							}
							
							updated = true;
						}
						else {
							BaseResultDTO[] errorsWSChunk = new BaseResultDTO[((AvailableStockSendDataWSDTO[]) splitStock[i]).length];
							String itemcode;
							for (int j = 0; j < ((AvailableStockSendDataWSDTO[]) splitStock[i]).length; j++) {
								itemcode = ((AvailableStockSendDataWSDTO[]) splitStock[i])[j].getItemcode();
								
								errorsWSChunk[j] = new BaseResultDTO();
								errorsWSChunk[j].setStatuscode("1");
								errorsWSChunk[j].setStatusmessage("Error en llamada a WS al actualizar producto " + itemcode);
							}
							
							stockChunk[i] = errorsWSChunk;
							
							resultDTO.setStatuscode(wsResultUploadDTO.getStatuscode());
							resultDTO.setStatusmessage(wsResultUploadDTO.getStatusmessage());
						}				
					}
					
					BaseResultDTO[] errorsWS = (BaseResultDTO[]) ClassUtilities.unsplitArrays(stockChunk, BaseResultDTO.class);
								
					if (updated) {
						resultDTO.setBaseresults(errorsWS);
						resultDTO.setStatuscode("0");
						resultDTO.setStatusmessage("OK");
					}
				}
			}
			else {
				resultDTO.setStatuscode(wsResultReportDTO.getStatuscode());
				resultDTO.setStatusmessage(wsResultReportDTO.getStatusmessage());
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			getSessionContext().setRollbackOnly();
			return LogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
}
