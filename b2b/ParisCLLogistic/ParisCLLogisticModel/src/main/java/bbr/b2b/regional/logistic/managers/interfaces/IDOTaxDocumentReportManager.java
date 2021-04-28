package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.HashMap;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.directorders.data.wrappers.DirectOrderW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentStateTypeW;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingCSVReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentAddInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentAddResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationInitParam;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationUpdateInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentStateTypeArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DirectOrderIdsByPagesDTO;

public interface IDOTaxDocumentReportManager {

	DOTaxDocumentStateTypeArrayResultDTO getDOTaxDocumentStateTypes();
	DOTaxDocumentReportResultDTO getDOTaxDocumentReportByVendorAndFilter(DOTaxDocumentReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated);	
	DOTaxDocumentAddResultDTO doAddDOTaxDocumentsByDirectOrders(DOTaxDocumentAddInitParamDTO initParamDTO, boolean fromFile);
	DOTaxDocumentAddResultDTO doPaperlessValidationWS(HashMap<String, DOTaxDocumentW> dtdMap, HashMap<Long, DirectOrderW> doMap, String username, String usertype) throws NotFoundException, OperationFailedException, AccessDeniedException; 
	boolean doPaperlessValidationWSByCron(DOTaxDocumentW dotaxdocument, DirectOrderW directOrder, String vendorRUT, int invoiceMaxPreviousDays, Long ticketnumber, HashMap<String, DOTaxDocumentStateTypeW> dotdstMap); 
	DOTaxDocumentReportResultDTO getDOTaxDocumentReportByDirectOrders(DOTaxDocumentDownloadInitParamDTO initParamDTO);
	DirectOrderIdsByPagesDTO getDOTaxDocumentReportByPages(DOTaxDocumentReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO);
	DOTaxDocumentStateReportResultDTO getDOTaxDocumentStateReportByDOTaxDocuments(DOTaxDocumentStateInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated);
	DOVirtualReceptionReportResultDTO getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndFilter(DOVirtualReceptionReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated);
	DOVirtualReceptionDetailResultDTO getDOVirtualReceptionDetailByOrder(DOVirtualReceptionDetailInitParamDTO initParamDTO);
	DOVirtualReceptionReportResultDTO getDOVirtualReceptionReportByDirectOrders(DOVirtualReceptionDownloadInitParamDTO initParamDTO, boolean byPages);
	DirectOrderIdsByPagesDTO getDOVirtualReceptionReportByPages(DOVirtualReceptionReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO);
	DOVirtualReceptionCSVReportResultDTO getCSVDOVirtualReceptionReportByOrderIds(DOVirtualReceptionCSVReportInitParamDTO initParamDTO);
	DOInvoicePendingReportResultDTO getDOInvoicePendingReport(DOInvoicePendingCSVReportInitParamDTO initParamDTO);
	BaseResultDTO doChangeDOTaxDocumentsStateToConfirmedInvoice(DOVirtualReceptionCSVReportInitParamDTO initParamDTO);
		
	DOTaxDocumentAddResultDTO doEvaluateDocumentUpload(DOTaxDocumentEvaluationUpdateInitParamDTO initParamDTO);
	BaseResultDTO doEvaluateDocument(DOTaxDocumentEvaluationInitParam evaluation);
	DOTaxDocumentAddResultDTO doEvaluateDocumentUploadRM(DOTaxDocumentEvaluationUpdateInitParamDTO initParamDTO);
	BaseResultDTO doEvaluateDocumentRM(DOTaxDocumentEvaluationInitParam evaluation);	
}
