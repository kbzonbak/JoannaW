package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IGenericServer;
import bbr.b2b.regional.logistic.vendors.entities.VendorRanking;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorRankingW;
import bbr.b2b.regional.logistic.vendors.entities.VendorRankingId;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingDetailReportDataDTO;
import bbr.b2b.regional.logistic.vendors.report.classes.VendorRankingReportDataDTO;
public interface IVendorRankingServer extends IGenericServer<VendorRanking, VendorRankingId, VendorRankingW> {

	VendorRankingW addVendorRanking(VendorRankingW vendorranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorRankingW[] getVendorRankings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorRankingW updateVendorRanking(VendorRankingW vendorranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorRankingReportDataDTO[] getVendorRankingsByRankingId(Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getVendorRankingsCountByRankingId(Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VendorRankingDetailReportDataDTO[] getOrderDeliveryEvaluationReportByVendorRanking(Long vendorid, Long rankingid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException;
	int getOrderDeliveryEvaluationCountByVendorRanking(Long vendorid, Long rankingid) throws AccessDeniedException, OperationFailedException, NotFoundException;
}
