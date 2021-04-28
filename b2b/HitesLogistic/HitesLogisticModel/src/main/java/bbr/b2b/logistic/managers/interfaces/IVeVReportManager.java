package bbr.b2b.logistic.managers.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.vev.report.classes.AvailableAndDailyStockUpdateInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.AvailableStockReportDataDTO;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVInitParamFileDTO;
import bbr.b2b.logistic.vev.report.classes.DoUploadStockVeVResultDTO;
import bbr.b2b.logistic.vev.report.classes.StockReportInitParamDTO;
import bbr.b2b.logistic.vev.report.classes.WSReportUploadDataDTO;

public interface IVeVReportManager {
	public AvailableStockReportDataDTO getAvailableStockReport(StockReportInitParamDTO initParamDTO);
	public WSReportUploadDataDTO updateAvailableAndDailyRepStock(AvailableAndDailyStockUpdateInitParamDTO initParamDTO, boolean isExcel);
	public DoUploadStockVeVResultDTO doUploadStockVeV(DoUploadStockVeVInitParamFileDTO initParamDTO) throws OperationFailedException, NotFoundException, AccessDeniedException;
}
