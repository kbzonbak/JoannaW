package bbr.b2b.regional.logistic.kpi.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevDepartmentDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevFineDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevOrderDetailDataDTO;
import bbr.b2b.regional.logistic.kpi.data.classes.KPIvevVendorDimensionDTO;
import bbr.b2b.regional.logistic.kpi.data.wrappers.KPIvevFineW;
import bbr.b2b.regional.logistic.kpi.entities.KPIvevFine;

public interface IKPIvevFineServer extends IElementServer<KPIvevFine, KPIvevFineW>{
	
	KPIvevFineW addKPIvevFine(KPIvevFineW kpivevfine) throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevFineW[] getKPIvevFines() throws AccessDeniedException, OperationFailedException, NotFoundException;
	KPIvevFineW updateKPIvevFine(KPIvevFineW kpivevfine) throws AccessDeniedException, OperationFailedException, NotFoundException;
	
	KPIvevFineW[] getKPIvevCDCompliancesByPeriod(Date since, Date until) throws OperationFailedException;
	KPIvevFineW[] getKPIvevPDCompliancesByPeriod(Date since, Date until) throws OperationFailedException;
	KPIvevVendorDimensionDTO[] getVendorsByKPIvevCDFinePeriod(Long fineperiodid, Double uf);
	KPIvevVendorDimensionDTO[] getVendorsByKPIvevPDFinePeriod(Long fineperiodid, Double uf, boolean courier);
	KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevCDFinePeriod(Long fineperiodid);
	KPIvevDepartmentDimensionDTO[] getDepartmentsByKPIvevPDFinePeriod(Long fineperiodid, boolean courier);
	KPIvevFineDataDTO[] getFineDataByKPIvevCDFinePeriod(Long fineperiodid, Double uf);
	KPIvevFineDataDTO[] getFineDataByKPIvevPDFinePeriod(Long fineperiodid, Double uf, boolean courier);
	KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevCDFinePeriod(Date since, Date until, OrderCriteriaDTO[] orderCriteria) throws AccessDeniedException;
	KPIvevOrderDetailDataDTO[] getOrderDetailDataByKPIvevPDFinePeriod(Date since, Date until, boolean courier, OrderCriteriaDTO[] orderCriteria) throws AccessDeniedException;	
}