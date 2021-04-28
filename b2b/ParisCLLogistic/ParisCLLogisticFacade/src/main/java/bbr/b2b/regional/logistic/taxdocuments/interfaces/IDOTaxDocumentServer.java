package bbr.b2b.regional.logistic.taxdocuments.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationValidationBean;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportDataDTO;

public interface IDOTaxDocumentServer extends IElementServer<DOTaxDocument, DOTaxDocumentW> {

	DOTaxDocumentW addDOTaxDocument(DOTaxDocumentW dotaxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOTaxDocumentW[] getDOTaxDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DOTaxDocumentW updateDOTaxDocument(DOTaxDocumentW dotaxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;

	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndPending(Long vendorid, Date minemitteddate, Date maxcourierdate, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndStateType(Long vendorid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndDeliveryDate(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndOrderNumber(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndNumber(Long vendorid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndEmissionDate(Long vendorid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndPending(Long vendorid, Date minemitteddate, Date maxcourierdate) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndStateType(Long vendorid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndDeliveryDate(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndOrderNumber(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndNumber(Long vendorid, Long number) throws AccessDeniedException, OperationFailedException;
	int countDOTaxDocumentReportByVendorAndEmissionDate(Long vendorid, Date since, Date until) throws AccessDeniedException, OperationFailedException;
	DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByDirectOrders(Long[] directorderids, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	Long[] getDOTaxDocumentIdsByVendorPendingAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOTaxDocumentIdsByVendorStateTypeAndPages(Long vendorid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOTaxDocumentIdsByVendorDeliveryDateAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOTaxDocumentIdsByVendorOrderNumberAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOTaxDocumentIdsByVendorNumberAndPages(Long vendorid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOTaxDocumentIdsByVendorEmissionDateAndPages(Long vendorid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until) throws AccessDeniedException, OperationFailedException;
	DOVirtualReceptionDetailDataDTO[] getDOVirtualReceptionDetailByOrder(Long doid);
	DOVirtualReceptionCSVReportDataDTO[] getCSVDOVirtualReceptionReportByOrderIds(Long[] doids);
	DOInvoicePendingDTO[] getDOInvoicePendingByDate(Date maxcourierdate, Date since, Date until);
	int countDOInvoicePendingByDate(Date maxcourierdate, Date since, Date until);
	DOTaxDocumentDTO[] getDOTaxDocumentsByOrderIdsAndWithoutReception(Long[] doids);
	DOTaxDocumentDTO[] getDOTaxDocumentsById(Long[] doids);
	DOTaxDocumentW[] getPendingDOTaxDocumentsByTicket(Long ticketid);
	DOTaxDocumentEvaluationValidationBean[] getDocumentsByNumbers(Long[] numbers);
	DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByDirectOrders(Long vendorid, Long[] directorderids, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByDirectOrders(Long vendorid, Long[] directorderids) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedInDTEAndPages(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentDeliveryDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentOrderNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedInDTEAndPages(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentDeliveryDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentOrderNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber) throws AccessDeniedException, OperationFailedException;
	int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until) throws AccessDeniedException, OperationFailedException;
}
