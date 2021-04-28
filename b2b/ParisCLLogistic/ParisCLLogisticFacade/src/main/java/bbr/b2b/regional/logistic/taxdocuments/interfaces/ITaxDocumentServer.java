package bbr.b2b.regional.logistic.taxdocuments.interfaces;

import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.TaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.entities.TaxDocument;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailTotalDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionExcelReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportDataDTO;

public interface ITaxDocumentServer extends IElementServer<TaxDocument, TaxDocumentW> {

	TaxDocumentW addTaxDocument(TaxDocumentW taxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;
	TaxDocumentW[] getTaxDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException;
	TaxDocumentW updateTaxDocument(TaxDocumentW taxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndDate(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndOrderNumber(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndItemInternalCode(Long vendorid, Long deliverylocationid, String code, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndNumber(Long vendorid, Long deliverylocationid, String number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	ReceptionReportDataDTO[] getReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndDate(Long vendorid, Long deliverylocationid, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndOrderNumber(Long vendorid, Long deliverylocationid, Long number) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndItemInternalCode(Long vendorid, Long deliverylocationid, String code) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndNumber(Long vendorid, Long deliverylocationid, String number) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndTaxDocumentNumber(Long vendorid, Long deliverylocationid, Long number) throws OperationFailedException, AccessDeniedException;
	int countReceptionReportByVendorDeliveryLocationAndTaxDocumentEmitted(Long vendorid, Long deliverylocationid, Date since, Date until) throws OperationFailedException, AccessDeniedException;
	ReceptionExcelReportDataDTO[] getExcelReceptionReportByTaxDocuments(Long[] taxdocumentids, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	ReceptionCSVReportDataDTO[] getCSVReceptionReportByTaxDocuments(Long[] taxdocumentids, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationDateAndPages(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationOrderNumberAndPages(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationItemInternalCodeAndPages(Long vendorid, Long deliverylocationid, String code, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationNumberAndPages(Long vendorid, Long deliverylocationid, String number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationTaxDocumentNumberAndPages(Long vendorid, Long deliverylocationid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	Long[] getReceptionIdsByVendorDeliveryLocationTaxDocumentEmittedAndPages(Long vendorid, Long deliverylocationid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws OperationFailedException, AccessDeniedException;
	ReceptionDetailDTO[] getReceptionDetailByTaxDocument(Long taxdocumentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException;
	ReceptionDetailTotalDTO countReceptionDetailByTaxDocument(Long taxdocumentid) throws AccessDeniedException;
	ReceptionDetailExcelDTO[] getExcelReceptionDetailByTaxDocuments(Long taxdocumentid, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	TaxDocumentReportDataDTO[] getTaxDocumentReportByDate(Date since, Date until, OrderCriteriaDTO[] orderby) throws AccessDeniedException;
	int countTaxDocumentReportByDate(Date since, Date until) throws AccessDeniedException;
	void deleteByIds(Long[] taxdocumentids);
	TaxDocumentW getByOrderDeliveryAndNumber(Long orderid, Long deliveryid, Long taxdocumentnumber) throws NotFoundException, OperationFailedException ;
	int deleteTaxDocumentsWithoutBulkDetailByDelivery(Long deliveryid, Long[] taxdocumentids);
}