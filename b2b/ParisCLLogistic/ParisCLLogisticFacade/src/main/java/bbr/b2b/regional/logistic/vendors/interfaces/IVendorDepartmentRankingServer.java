package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRanking;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorDepartmentRankingW;
import bbr.b2b.regional.logistic.vendors.entities.VendorDepartmentRankingId;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorDepartmentRankingReportDataDTO;
public interface IVendorDepartmentRankingServer extends IGenericServer<VendorDepartmentRanking, VendorDepartmentRankingId, VendorDepartmentRankingW> {

	VendorDepartmentRankingW addVendorDepartmentRanking(VendorDepartmentRankingW vendordepartmentranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorDepartmentRankingW[] getVendorDepartmentRankings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorDepartmentRankingW updateVendorDepartmentRanking(VendorDepartmentRankingW vendordepartmentranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorDepartmentRankingReportDataDTO[] getVendorDepartmentRankingsByRankingId(Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorDepartmentRankingsCountByRankingId(Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorDepartmentRankingDetailReportDataDTO[] getOrderDeliveryEvaluationReportByVendorDepartmentRanking(Long vendorid, Long departmentid, Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getOrderDeliveryEvaluationCountByVendorDepartmentRanking(Long vendorid, Long departmentid, Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
