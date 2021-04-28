package bbr.b2b.regional.logistic.buyorders.interfaces;

import java.time.LocalDateTime;
import java.util.Date;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.VeVAuditW;
import bbr.b2b.regional.logistic.buyorders.entities.VeVAudit;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVAuditDetailDTO;
import bbr.b2b.regional.logistic.buyorders.report.classes.VeVUpdateAuditReportDataDTO;

public interface IVeVAuditServer extends IElementServer<VeVAudit, VeVAuditW> {

	VeVAuditW addVeVAudit(VeVAuditW vevaudit) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VeVAuditW[] getVeVAudits() throws AccessDeniedException, OperationFailedException, NotFoundException;
	VeVAuditW updateVeVAudit(VeVAuditW vevaudit) throws AccessDeniedException, OperationFailedException, NotFoundException;
	VeVUpdateAuditReportDataDTO[] getVeVUpdateAuditReportByVendorDateTypeAndSearchText(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext, int rows, int pagenumber, OrderCriteriaDTO[] orderby, boolean ispaginated) throws OperationFailedException, AccessDeniedException;
	int countVeVUpdateAuditReportByVendorDateTypeAndSearchText(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext) throws OperationFailedException, AccessDeniedException;
	VeVAuditDetailDTO[] getVeVAuditDetailData(Long vendorID, String date, String typeDescription) throws OperationFailedException, AccessDeniedException;
	VeVAuditDetailDTO[] getExcelVeVUpdateAuditReportData(Long vendorid, Date since, Date until, Long vevupdatetypeid, String searchtext) throws OperationFailedException, AccessDeniedException;
}
