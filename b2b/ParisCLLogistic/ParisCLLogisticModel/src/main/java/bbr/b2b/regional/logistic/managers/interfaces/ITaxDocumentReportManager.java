package bbr.b2b.regional.logistic.managers.interfaces;

import java.util.Date;
import java.util.Map;

import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionCSVReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelArrayResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailExcelInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDetailInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionDownloadInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionExcelReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.ReceptionReportResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentAddResultDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentIdsByPagesDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportInitParamDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.TaxDocumentReportResultDTO;
import bbr.b2b.regional.logistic.vendors.data.wrappers.VendorW;

public interface ITaxDocumentReportManager {

	TaxDocumentAddResultDTO doAddTaxDocumentsByOrders(Map<String, Double> tdOcMap, VendorW vendor, Date datingdate);
	ReceptionReportResultDTO getReceptionReportByVendorDeliveryLocationAndFilter(ReceptionReportInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated);
	ReceptionExcelReportResultDTO getExcelReceptionReportByTaxDocuments(ReceptionDownloadInitParamDTO initParamDTO);
	ReceptionCSVReportResultDTO getCSVReceptionReportByTaxDocuments(ReceptionDownloadInitParamDTO initParamDTO);
	TaxDocumentIdsByPagesDTO getReceptionReportByPages(ReceptionReportInitParamDTO initParamDTO, PageRangeDTO[] pageRangesDTO);
	ReceptionDetailArrayResultDTO getReceptionDetailByTaxDocument(ReceptionDetailInitParamDTO initParamDTO, boolean byFilter, boolean isPaginated);
	ReceptionDetailExcelArrayResultDTO getExcelReceptionDetailByTaxDocuments(ReceptionDetailExcelInitParamDTO initParamDTO);
	TaxDocumentReportResultDTO getTaxDocumentReportByDate(TaxDocumentReportInitParamDTO initParamDTO);
}
