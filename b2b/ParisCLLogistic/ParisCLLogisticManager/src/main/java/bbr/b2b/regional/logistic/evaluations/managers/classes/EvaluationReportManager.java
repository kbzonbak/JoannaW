package bbr.b2b.regional.logistic.evaluations.managers.classes;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.classes.PageInfoDTO;
import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.DateConverterFactory2;
import bbr.b2b.regional.logistic.datings.classes.DatingServerLocal;
import bbr.b2b.regional.logistic.datings.data.wrappers.DatingW;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.DeliveryStateTypeServerLocal;
import bbr.b2b.regional.logistic.deliveries.classes.OrderDeliveryServerLocal;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateTypeW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryStateW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.DeliveryW;
import bbr.b2b.regional.logistic.deliveries.data.wrappers.OrderDeliveryW;
import bbr.b2b.regional.logistic.evaluations.classes.ErrorFactorServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.EvaluationCriteriaServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.EvaluationDetailServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionErrorServerLocal;
import bbr.b2b.regional.logistic.evaluations.classes.ReceptionEvaluationServerLocal;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ErrorFactorW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationCriteriaW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.EvaluationDetailW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionErrorW;
import bbr.b2b.regional.logistic.evaluations.data.wrappers.ReceptionEvaluationW;
import bbr.b2b.regional.logistic.evaluations.managers.interfaces.EvaluationReportManagerLocal;
import bbr.b2b.regional.logistic.evaluations.managers.interfaces.EvaluationReportManagerRemote;
import bbr.b2b.regional.logistic.evaluations.report.classes.EvaluationDetailReportDataDTO;
import bbr.b2b.regional.logistic.evaluations.report.classes.EvaluationDetailReportInitParamDTO;
import bbr.b2b.regional.logistic.evaluations.report.classes.EvaluationDetailReportResultDTO;
import bbr.b2b.regional.logistic.fillrate.classes.FillRateDetailServerLocal;
import bbr.b2b.regional.logistic.fillrate.classes.FillRatePeriodServerLocal;
import bbr.b2b.regional.logistic.fillrate.classes.FillRateServerLocal;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRatePeriodW;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateW;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateDetailInitParamDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateDetailReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateDetailReportResultDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateEvolutionInitParamDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateEvolutionReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateEvolutionReportResultDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateInitParamDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateReportDataDTO;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateReportResultDTO;
import bbr.b2b.regional.logistic.utils.RegionalLogisticStatusCodeUtils;
import bbr.b2b.regional.logistic.vendors.classes.DepartmentServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.RankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorDepartmentRankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorRankingServerLocal;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;
import bbr.b2b.regional.logistic.vendors.report.classes.RankingDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingDetailInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingDetailReportResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingReportResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingDetailInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingDetailReportResultDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingInitParamDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingReportResultDTO;

@Stateless(name = "managers/EvaluationReportManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EvaluationReportManager implements EvaluationReportManagerLocal, EvaluationReportManagerRemote{
	
	@EJB
	DatingServerLocal datingserver;
	
	@EJB
	DeliveryServerLocal deliveryserver;
	
	@EJB
	DeliveryStateTypeServerLocal deliverystatetypeserver;
	
	@EJB
	DeliveryStateServerLocal deliverystateserver;
	
	@EJB
	ReceptionEvaluationServerLocal receptionevaluationserver;
	
	@EJB
	EvaluationDetailServerLocal evaluationdetailserver;
	
	@EJB
	ReceptionErrorServerLocal receptionerrorserver;
	
	@EJB
	ErrorFactorServerLocal errorfactorserver;
	
	@EJB
	EvaluationCriteriaServerLocal evaluationcriteriaserver;
	
	@EJB
	OrderDeliveryServerLocal orderdeliveryserver;
	
	@EJB
	RankingServerLocal rankingserver;
	
	@EJB
	VendorRankingServerLocal vendorrankingserver;
	
	@EJB
	VendorDepartmentRankingServerLocal vendordepartmentrankingserver;
	
	@EJB
	VendorServerLocal vendorserver;
	
	@EJB
	DepartmentServerLocal departmentserver;
	
	@EJB
	FillRateServerLocal fillrateserver;
	
	@EJB
	FillRateDetailServerLocal fillratedetailserver;
	
	@EJB
	FillRatePeriodServerLocal fillrateperiodserver;
	
	@Resource
	private javax.ejb.SessionContext mySessionCtx;
	
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	
	public ReceptionEvaluationW doAddOrUpdateReceptionEvaluationAndDetails(ReceptionEvaluationW basereceptionevaluation, EvaluationDetailW... baseevaluationdetail) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		////////// 1 - Obtener los datos de la evaluación //////////
		
		Long datingId = basereceptionevaluation.getDatingid();
		
		// Obtener la cita asociada a la evaluación
		DatingW dating =  datingserver.getById(datingId);
		
		Long deliveryId = dating.getDeliveryid();
		
		////////// 2 - Obtener la evaluación almacenada en BD //////////

		ReceptionEvaluationW receptionEvaluation = null;
		
		// Agregar la evaluación si no existe
		if (basereceptionevaluation.getId() == null || basereceptionevaluation.getId().longValue() <= 0) {
			basereceptionevaluation.setScore(1.0);	// La evaluación parte inicialmente con puntaje de 1 (100%)
			receptionEvaluation = receptionevaluationserver.addReceptionEvaluation(basereceptionevaluation);
		} else
			receptionEvaluation = basereceptionevaluation;	// Aqu� el puntaje de la evaluación parte del almacenado

		Long receptionEvaluationId = receptionEvaluation.getId();
		
		////////// 3 - Obtener el detalle de evaluación almacenado en BD //////////
		
		// Completar la información del nuevo detalle de evaluación
		EvaluationDetailW[] evaluationDetail = baseevaluationdetail;
		
		for (int i = 0 ; i < evaluationDetail.length ; i++){
			evaluationDetail[i].setReceptionevaluationid(receptionEvaluationId);
			
			Long receptionErrorId = evaluationDetail[i].getReceptionerrorid();
			
			// Buscar alg�n detalle de evaluación previo con el mismo error
			PropertyInfoDTO property1 = new PropertyInfoDTO("receptionerror.id", "receptionerror", receptionErrorId);
			PropertyInfoDTO property2 = new PropertyInfoDTO("receptionevaluation.id", "receptionevaluation", receptionEvaluationId);
			List<EvaluationDetailW> EvaluationDetailList = evaluationdetailserver.getByProperties(property1, property2);
			
			// Agregar o actualizar el detalle de evaluación en dependencia de si ya existe uno previo	
			if(EvaluationDetailList == null || EvaluationDetailList.size() <= 0){
				evaluationdetailserver.addEvaluationDetail(evaluationDetail[i]);
			} else{
				evaluationdetailserver.updateEvaluationDetail(evaluationDetail[i]);
			}
		}		

		////////// 4 - Recalcular y actualizar el puntaje de evaluación //////////
		
		// Validar si el puntaje inicial es mayor que cero, recalcular y actualizar
		if (receptionEvaluation.getScore() > 0.0) {
			Double newScore = doCalculateEvaluationScore(receptionEvaluation);
			receptionEvaluation.setScore(newScore);
			receptionEvaluation = receptionevaluationserver.updateReceptionEvaluation(receptionEvaluation);
		}
		
		////////// 5 - Validar si el puntaje es menor o igual a cero //////////
		
		if (receptionEvaluation.getScore() <= 0.0) {
			// Obtener el despacho asociado a la cita
			DeliveryW delivery = deliveryserver.getById(deliveryId);
			
			// Obtener el tipo de estado 'Rechazada'
			DeliveryStateTypeW rejectedDeliveryStateType = deliverystatetypeserver.getByPropertyAsSingleResult("code", "RECHAZADA");
			
			Long rdst = rejectedDeliveryStateType.getId();
			
			// Validar que el estado actual del despacho no sea 'Rechazada'
			if (delivery.getCurrentstatetypeid().longValue() != rdst.longValue()){
				// Actualizar el estado del despacho a 'Rechazada'
				delivery.setCurrentstatetypedate(new Date());
				delivery.setCurrentstatetypeid(rdst);
				
				delivery = deliveryserver.updateDelivery(delivery);
				
				// Agregar el nuevo estado al historial
				DeliveryStateW deliveryState = new DeliveryStateW();
				
				deliveryState.setDeliveryid(delivery.getId());
				deliveryState.setDeliverystatetypeid(rdst);
				deliveryState.setWhen(new Date());
				
				deliveryState = deliverystateserver.addDeliveryState(deliveryState);
				
				// Obtener las entregas de órdenes y cerrarlas
				OrderDeliveryW[] orderdeliveries = orderdeliveryserver.getByPropertyAsArray("delivery.id", deliveryId);
				for (OrderDeliveryW orderdelivery : orderdeliveries) {
					orderdelivery.setClosed(true);
					orderdeliveryserver.updateOrderDelivery(orderdelivery);
				}
				orderdeliveryserver.flush();
			}
		}

		return receptionEvaluation;		
	}
	
	public VendorRankingReportResultDTO getVendorRankingsReportOfLatestRanking(VendorRankingInitParamDTO initParamDTO, boolean byFilter){
		
		VendorRankingReportResultDTO resultDTO = new VendorRankingReportResultDTO();
		
		RankingDTO latestRanking = null;
		VendorRankingReportDataDTO[] vendorRankingsDTO = null;
		
		int totalreg = 0; 
		int total = 0;
		
		try{
			latestRanking = rankingserver.getLatestRanking();
			
			if(latestRanking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			vendorRankingsDTO = vendorrankingserver.getVendorRankingsByRankingId(latestRanking.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
			
			if (byFilter){
				totalreg = vendorrankingserver.getVendorRankingsCountByRankingId(latestRanking.getId());
				
				int rows = initParamDTO.getRows();
				total = totalreg;
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(vendorRankingsDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}	
			
			resultDTO.setSince(latestRanking.getSince());
			resultDTO.setUntil(latestRanking.getUntil());
			resultDTO.setVendorrankings(vendorRankingsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorRankingDetailReportResultDTO getOrderDeliveryEvaluationReportByVendorRanking(VendorRankingDetailInitParamDTO initParamDTO, boolean byFilter){
		
		VendorRankingDetailReportResultDTO resultDTO = new VendorRankingDetailReportResultDTO();
		
		RankingDTO ranking = null;
		//VendorW vendor = null;
		VendorRankingDetailReportDataDTO[] vendorRankingDetailsDTO = null;
		
		int totalreg = 0; 
		int total = 0;
		
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
		
		try{
			ranking = rankingserver.getRankingById(initParamDTO.getRankingid());
			
			if(ranking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			//vendor = vendorserver.getById(vendor.getId());
			
			vendorRankingDetailsDTO = vendorrankingserver.getOrderDeliveryEvaluationReportByVendorRanking(vendor.getId(), initParamDTO.getRankingid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
						
			if (byFilter){
				totalreg = vendorrankingserver.getOrderDeliveryEvaluationCountByVendorRanking(vendor.getId(), initParamDTO.getRankingid());
				
				int rows = initParamDTO.getRows();
				total = totalreg;
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(vendorRankingDetailsDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}	
			
			resultDTO.setSince(ranking.getSince());
			resultDTO.setUntil(ranking.getUntil());
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setVendorrankingdetails(vendorRankingDetailsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
		
	}
	
	public VendorDepartmentRankingReportResultDTO getVendorDepartmentRankingsReportOfLatestRanking(VendorDepartmentRankingInitParamDTO initParamDTO, boolean byFilter){
		
		VendorDepartmentRankingReportResultDTO resultDTO = new VendorDepartmentRankingReportResultDTO();
		
		RankingDTO latestRanking = null;
		VendorDepartmentRankingReportDataDTO[] vendorDepartmentRankingsDTO = null;
		
		int totalreg = 0; 
		int total = 0;
		
		try{
			latestRanking = rankingserver.getLatestRanking();
			
			if(latestRanking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			vendorDepartmentRankingsDTO = vendordepartmentrankingserver.getVendorDepartmentRankingsByRankingId(latestRanking.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
						
			if (byFilter){
				totalreg = vendordepartmentrankingserver.getVendorDepartmentRankingsCountByRankingId(latestRanking.getId());
				
				int rows = initParamDTO.getRows();
				total = totalreg;
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(vendorDepartmentRankingsDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}	
			
			resultDTO.setSince(latestRanking.getSince());
			resultDTO.setUntil(latestRanking.getUntil());
			resultDTO.setVendordepartmentrankings(vendorDepartmentRankingsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
			
	}
	
	public VendorDepartmentRankingDetailReportResultDTO getOrderDeliveryEvaluationReportByVendorDepartmentRanking(VendorDepartmentRankingDetailInitParamDTO initParamDTO, boolean byFilter){
		
		VendorDepartmentRankingDetailReportResultDTO resultDTO = new VendorDepartmentRankingDetailReportResultDTO();
		
		RankingDTO ranking = null;
		//VendorW vendor = null;
		DepartmentW department = null;
		VendorDepartmentRankingDetailReportDataDTO[] vendorDepartmentRankingDetailsDTO = null;
		
		int totalreg = 0; 
		int total = 0;
		
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
		try{
			ranking = rankingserver.getRankingById(initParamDTO.getRankingid());
			
			if(ranking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			//vendor = vendorserver.getById(vendor.getId());
			
			department = departmentserver.getById(initParamDTO.getDepartmentid());
			
			vendorDepartmentRankingDetailsDTO = vendordepartmentrankingserver.getOrderDeliveryEvaluationReportByVendorDepartmentRanking(vendor.getId(), initParamDTO.getDepartmentid(), initParamDTO.getRankingid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
									
			if (byFilter){
				totalreg = vendordepartmentrankingserver.getOrderDeliveryEvaluationCountByVendorDepartmentRanking(vendor.getId(), initParamDTO.getDepartmentid(), initParamDTO.getRankingid());
				
				int rows = initParamDTO.getRows();
				total = totalreg;
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(vendorDepartmentRankingDetailsDTO.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}	
			
			resultDTO.setSince(ranking.getSince());
			resultDTO.setUntil(ranking.getUntil());
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setDepartmentname(department.getName());
			resultDTO.setVendordepartmentrankingdetails(vendorDepartmentRankingDetailsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorRankingReportResultDTO getExcelVendorRankingsReport(VendorRankingInitParamDTO initParamDTO){
		
		VendorRankingReportResultDTO resultDTO = new VendorRankingReportResultDTO();
		
		RankingDTO latestRanking = null;
		VendorRankingReportDataDTO[] vendorRankingsDTO = null;
		
		try{
			latestRanking = rankingserver.getLatestRanking();
			
			if(latestRanking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			vendorRankingsDTO = vendorrankingserver.getVendorRankingsByRankingId(latestRanking.getId(), 0, 0, null, false);
			
			resultDTO.setSince(latestRanking.getSince());
			resultDTO.setUntil(latestRanking.getUntil());
			resultDTO.setVendorrankings(vendorRankingsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorRankingDetailReportResultDTO getExcelVendorRankingDetailReport(VendorRankingDetailInitParamDTO initParamDTO){
		
		VendorRankingDetailReportResultDTO resultDTO = new VendorRankingDetailReportResultDTO();
		
		RankingDTO ranking = null;
		//VendorW vendor = null;
		VendorRankingDetailReportDataDTO[] vendorRankingDetailsDTO = null;
		
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
		
		try{
			ranking = rankingserver.getRankingById(initParamDTO.getRankingid());
			
			if(ranking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			//vendor = vendorserver.getById(vendor.getId());
			
			vendorRankingDetailsDTO = vendorrankingserver.getOrderDeliveryEvaluationReportByVendorRanking(vendor.getId(), initParamDTO.getRankingid(), 0, 0, null, false);
						
			resultDTO.setSince(ranking.getSince());
			resultDTO.setUntil(ranking.getUntil());
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setVendorrankingdetails(vendorRankingDetailsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorDepartmentRankingReportResultDTO getExcelVendorDepartmentRankingsReport(VendorDepartmentRankingInitParamDTO initParamDTO){
		
		VendorDepartmentRankingReportResultDTO resultDTO = new VendorDepartmentRankingReportResultDTO();
		
		RankingDTO latestRanking = null;
		VendorDepartmentRankingReportDataDTO[] vendorDepartmentRankingsDTO = null;
		
		try{
			latestRanking = rankingserver.getLatestRanking();
			
			if(latestRanking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			vendorDepartmentRankingsDTO = vendordepartmentrankingserver.getVendorDepartmentRankingsByRankingId(latestRanking.getId(), 0, 0, null, false);
						
			resultDTO.setSince(latestRanking.getSince());
			resultDTO.setUntil(latestRanking.getUntil());
			resultDTO.setVendordepartmentrankings(vendorDepartmentRankingsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public VendorDepartmentRankingDetailReportResultDTO getExcelVendorDepartmentRankingDetailReport(VendorDepartmentRankingDetailInitParamDTO initParamDTO){
		
		VendorDepartmentRankingDetailReportResultDTO resultDTO = new VendorDepartmentRankingDetailReportResultDTO();
		
		RankingDTO ranking = null;
		//VendorW vendor = null;
		DepartmentW department = null;
		VendorDepartmentRankingDetailReportDataDTO[] vendorDepartmentRankingDetailsDTO = null;
		
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
		
		try{
			ranking = rankingserver.getRankingById(initParamDTO.getRankingid());
			
			if(ranking == null)
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1", "No existen datos para generar el reporte", false);
			
			//vendor = vendorserver.getById(vendor.getId());
			
			department = departmentserver.getById(initParamDTO.getDepartmentid());
			
			vendorDepartmentRankingDetailsDTO = vendordepartmentrankingserver.getOrderDeliveryEvaluationReportByVendorDepartmentRanking(vendor.getId(), initParamDTO.getDepartmentid(), initParamDTO.getRankingid(), 0, 0, null, false);
									
			resultDTO.setSince(ranking.getSince());
			resultDTO.setUntil(ranking.getUntil());
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setDepartmentname(department.getName());
			resultDTO.setVendordepartmentrankingdetails(vendorDepartmentRankingDetailsDTO);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public FillRateReportResultDTO getFillRatesReportByFillRatePeriod(FillRateInitParamDTO initParamDTO, boolean byFilter){
		
		FillRateReportResultDTO resultDTO = new FillRateReportResultDTO();
		
		FillRatePeriodW fillRatePeriod = null;
		FillRateReportDataDTO[] fillRatesDTO = null;
		
		int totalreg = 0; 
		int total = 0;
		
		try{
			fillRatePeriod = fillrateperiodserver.getFillRatePeriodByMonthAndYear(initParamDTO.getMonth(), initParamDTO.getYear());
			
			if(fillRatePeriod == null){
				resultDTO.setMonth("");
				resultDTO.setYear(0);
			}
			else{	
				fillRatesDTO = fillrateserver.getFillRatesReportByFillRatePeriod(fillRatePeriod.getId(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
			
				if (byFilter){
					totalreg = fillrateserver.getFillRatesCountByFillRatePeriod(fillRatePeriod.getId());
					
					int rows = initParamDTO.getRows();
					total = totalreg;
					PageInfoDTO pageInfo = new PageInfoDTO();
					pageInfo.setPagenumber(initParamDTO.getPageNumber());
					pageInfo.setRows(fillRatesDTO.length);
					pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
					pageInfo.setTotalrows(total);
					resultDTO.setPageInfo(pageInfo);
				}	
				
				resultDTO.setMonth(initParamDTO.getMonth());
				resultDTO.setYear(initParamDTO.getYear());
				resultDTO.setFillrates(fillRatesDTO);
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
	
	public FillRateEvolutionReportResultDTO getFillRatesReportByVendorAndDepartmentAndLatestPeriods(FillRateEvolutionInitParamDTO initParamDTO){
		
		FillRateEvolutionReportResultDTO resultDTO = new FillRateEvolutionReportResultDTO();
		
		//VendorW vendor = null;
		DepartmentW department = null;
		FillRateEvolutionReportDataDTO[] fillRateEvolution = null;
		
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
		
		try{
			//vendor = vendorserver.getById(vendor.getId());
			
			department = departmentserver.getById(initParamDTO.getDepartmentid());
			
			fillRateEvolution = fillrateserver.getFillRatesReportByVendorAndDepartmentAndLatestPeriods(vendor.getId(), initParamDTO.getDepartmentid(), initParamDTO.getLatestperiods(), initParamDTO.getOrderby());
			
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setDepartmentname(department.getName());
			resultDTO.setFillrateevolution(fillRateEvolution);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public FillRateDetailReportResultDTO getFillRateDetailReportByFillRate(FillRateDetailInitParamDTO initParamDTO, boolean byFilter){
		
		FillRateDetailReportResultDTO resultDTO = new FillRateDetailReportResultDTO();
		
		FillRateW fillrate = null;
		FillRatePeriodW fillRatePeriod = null;
		//VendorW vendor = null;
		DepartmentW department = null;
		FillRateDetailReportDataDTO[] fillRateDetail = null;
		
		int totalreg = 0; 
		int total = 0;
		
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
		
		try{
			fillrate = fillrateserver.getById(initParamDTO.getFillrateid());
			fillRatePeriod = fillrateperiodserver.getById(fillrate.getFillrateperiodid());
			
			//vendor = vendorserver.getById(vendor.getId());
			
			department = departmentserver.getById(initParamDTO.getDepartmentid());
			
			fillRateDetail = fillratedetailserver.getFillRateDetailReportByFillRate(initParamDTO.getFillrateid(), initParamDTO.getPageNumber(), initParamDTO.getRows(), initParamDTO.getOrderby(), true);
			
			if (byFilter){
				totalreg = fillratedetailserver.getFillRateDetailCountByFillRate(initParamDTO.getFillrateid());
				
				int rows = initParamDTO.getRows();
				total = totalreg;
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParamDTO.getPageNumber());
				pageInfo.setRows(fillRateDetail.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				resultDTO.setPageInfo(pageInfo);
			}	
			
			Calendar date = Calendar.getInstance();
			date.setTime(fillRatePeriod.getSince());
			
			resultDTO.setMonth(date.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "CL")));
			resultDTO.setYear(date.get(Calendar.YEAR)); 
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setDepartmentname(department.getName());
			resultDTO.setFillratedetail(fillRateDetail);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}
	
	public FillRateReportResultDTO getExcelFillRatesReportByFillRatePeriod(FillRateInitParamDTO initParamDTO){
		
		FillRateReportResultDTO resultDTO = new FillRateReportResultDTO();
		
		FillRatePeriodW fillRatePeriod = null;
		FillRateReportDataDTO[] fillRatesDTO = null;
		
		try{
			fillRatePeriod = fillrateperiodserver.getFillRatePeriodByMonthAndYear(initParamDTO.getMonth(), initParamDTO.getYear());
			
			if(fillRatePeriod == null){
				resultDTO.setMonth("");
				resultDTO.setYear(0);
			}
			else{	
				fillRatesDTO = fillrateserver.getFillRatesReportByFillRatePeriod(fillRatePeriod.getId(), 0, 0, null, false);
			
				resultDTO.setMonth(initParamDTO.getMonth());
				resultDTO.setYear(initParamDTO.getYear());
				resultDTO.setFillrates(fillRatesDTO);
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
	
	public FillRateDetailReportResultDTO getExcelFillRateDetailReport(FillRateDetailInitParamDTO initParamDTO){
		
		FillRateDetailReportResultDTO resultDTO = new FillRateDetailReportResultDTO();
		
		FillRateW fillrate = null;
		FillRatePeriodW fillRatePeriod = null;
		//VendorW vendor = null;
		DepartmentW department = null;
		FillRateDetailReportDataDTO[] fillRateDetail = null;
		
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
		
		try{
			fillrate = fillrateserver.getById(initParamDTO.getFillrateid());
			fillRatePeriod = fillrateperiodserver.getById(fillrate.getFillrateperiodid());
			
			//vendor = vendorserver.getById(vendor.getId());
			
			department = departmentserver.getById(initParamDTO.getDepartmentid());
			
			fillRateDetail = fillratedetailserver.getFillRateDetailReportByFillRate(initParamDTO.getFillrateid(), 0, 0, null, false);
			
			Calendar date = Calendar.getInstance();
			date.setTime(fillRatePeriod.getSince());
			
			resultDTO.setMonth(date.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "CL")));
			resultDTO.setYear(date.get(Calendar.YEAR)); 
			resultDTO.setVendorname(vendor.getName());
			resultDTO.setDepartmentname(department.getName());
			resultDTO.setFillratedetail(fillRateDetail);
		
		} catch (ServiceException e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		} catch (Exception e) {
			e.printStackTrace();
			return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(resultDTO, "L1");
		}
		
		return resultDTO;
	}

	public EvaluationDetailReportResultDTO getEvaluationDetailReport(EvaluationDetailReportInitParamDTO initParams, boolean byFilter, boolean paginated){
		EvaluationDetailReportResultDTO result = new EvaluationDetailReportResultDTO();
		
		// Valida proveedor
		Long vendorid = -1L;
		if(! initParams.getVendorcode().equals("-1"))
		{
			VendorW[] vendors = null;
			try{
				vendors = vendorserver.getByPropertyAsArray("rut", initParams.getVendorcode());
				
			}	catch (Exception e) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L1");
			}
			if(vendors == null || vendors.length == 0){
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "L443");// no existe
			}
			vendorid = vendors[0].getId();
		}
		
		try {			
			EvaluationDetailReportDataDTO[] reportData = null;
			int total;

			reportData = evaluationdetailserver.getEvaluationDetailReport(Date.from(initParams.getSince().atZone( ZoneId.systemDefault()).toInstant()) , Date.from(initParams.getUntil().atZone( ZoneId.systemDefault()).toInstant()) , vendorid, initParams.getPagenumber(), initParams.getRows(), initParams.getOrderby(), paginated);

			if (reportData == null) {
				return RegionalLogisticStatusCodeUtils.getInstance().setStatusCode(result, "0");
			}

			result.setEvaluations(reportData);

			if (byFilter) {
				total = evaluationdetailserver.getEvaluationDetailCountReport(Date.from(initParams.getSince().atZone( ZoneId.systemDefault()).toInstant()) , Date.from(initParams.getUntil().atZone( ZoneId.systemDefault()).toInstant()), vendorid);

				int rows = initParams.getRows();
				PageInfoDTO pageInfo = new PageInfoDTO();
				pageInfo.setPagenumber(initParams.getPagenumber());
				pageInfo.setRows(reportData.length);
				pageInfo.setTotalpages((total % rows) != 0 ? (total / rows) + 1 : (total / rows));
				pageInfo.setTotalrows(total);
				result.setPageInfo(pageInfo);
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
	
	private Double doCalculateEvaluationScore(ReceptionEvaluationW receptionevaluation) throws NotFoundException, OperationFailedException {
		
		// Obtener los detalles de evaluación
		PropertyInfoDTO property = new PropertyInfoDTO("receptionevaluation.id", "receptionevaluation", receptionevaluation.getId());
		List<EvaluationDetailW> evaluationDetailList =  evaluationdetailserver.getByProperties(property);
		
		// Devolver puntaje 1.0 si no hay detalles de error
		if (evaluationDetailList == null || evaluationDetailList.size() <= 0)
			return 1.0;
		
		// Obtener los errores de recepción
		EvaluationDetailW[] evaluationDetails = (EvaluationDetailW[]) evaluationDetailList.toArray(new EvaluationDetailW[evaluationDetailList.size()]);	
		Map<Long, ReceptionErrorW> receptionErrorMap = new HashMap<Long, ReceptionErrorW>();
	
		for (EvaluationDetailW evaluationDetail : evaluationDetails) {
			Long receptionErrorId = evaluationDetail.getReceptionerrorid();
			
			if (!receptionErrorMap.containsKey(receptionErrorId)) {
				ReceptionErrorW receptionError = receptionerrorserver.getById(receptionErrorId);
				receptionErrorMap.put(receptionErrorId, receptionError);
			}
		}
		
		ReceptionErrorW[] receptionErrors = (ReceptionErrorW[]) receptionErrorMap.values().toArray(new ReceptionErrorW[receptionErrorMap.size()]);
		
		// Obtener los factores de error asociados a cada error de recepción
		Map<Long, EvaluationCriteriaW> evaluationCriteriaMap = new HashMap<Long, EvaluationCriteriaW>();
		
		for (ReceptionErrorW receptionError : receptionErrors){
			PropertyInfoDTO property1 = new PropertyInfoDTO("receptionerror.id", "receptionerror", receptionError.getId());
			List<ErrorFactorW> errorFactorList =  errorfactorserver.getByProperties(property1);
			
			ErrorFactorW[] errorFactors = (ErrorFactorW[]) errorFactorList.toArray(new ErrorFactorW[errorFactorList.size()]);	
						
			for(ErrorFactorW errorFactor : errorFactors){
				Long evaluationCriteriaId = errorFactor.getEvaluationcriteriaid();
			
				if (!evaluationCriteriaMap.containsKey(evaluationCriteriaId)) {
					EvaluationCriteriaW evaluationCriteria = evaluationcriteriaserver.getById(evaluationCriteriaId);
					evaluationCriteriaMap.put(evaluationCriteriaId, evaluationCriteria);
				}
			}
		}
		
		EvaluationCriteriaW[] evaluationCriterias = (EvaluationCriteriaW[]) evaluationCriteriaMap.values().toArray(new EvaluationCriteriaW[evaluationCriteriaMap.size()]);
		
		// Calcular el puntaje a partir de los errores de recepción
		int score = 100;
		for (EvaluationCriteriaW evaluationCriteria : evaluationCriterias){
			score -= (evaluationCriteria.getWeight() * 100);
		}
		
		return (double)score / 100;
	}
}
