package bbr.b2b.regional.logistic.taxdocuments.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;
//import org.jboss.annotation.IgnoreDependency;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.constants.RegionalLogisticConstants;
import bbr.b2b.regional.logistic.deliveries.report.classes.DODeliverySourceDataDTO;
import bbr.b2b.regional.logistic.directorders.classes.DirectOrderServerLocal;
import bbr.b2b.regional.logistic.directorders.entities.DirectOrder;
import bbr.b2b.regional.logistic.taxdocuments.data.wrappers.DOTaxDocumentW;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentStateType;
import bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocumentTicket;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOInvoicePendingDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentEvaluationValidationBean;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOTaxDocumentReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionCSVReportDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionDetailDataDTO;
import bbr.b2b.regional.logistic.taxdocuments.report.classes.DOVirtualReceptionReportDataDTO;
import bbr.b2b.regional.logistic.utils.ClassUtilities;
import bbr.b2b.regional.logistic.vendors.classes.VendorServerLocal;
import bbr.b2b.regional.logistic.vendors.entities.Vendor;

@Stateless(name = "servers/taxdocuments/DOTaxDocumentServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DOTaxDocumentServer extends LogisticElementServer<DOTaxDocument, DOTaxDocumentW> implements DOTaxDocumentServerLocal {

	@EJB
	VendorServerLocal vendorserver;
	
//	@IgnoreDependency
	@EJB
	DirectOrderServerLocal directorderserver;

	@EJB
	DOTaxDocumentStateTypeServerLocal currentstatetypeserver;
	
	@EJB
	DOTaxDocumentTicketServerLocal documentTicketServerLocal;
	
	private Map<String, String> mapGetDOTaxDocumentSQL = new HashMap<String, String>();
	{
		mapGetDOTaxDocumentSQL.put("DONUMBER", "o.number");
		mapGetDOTaxDocumentSQL.put("DOREQUESTNUMBER", "o.requestnumber");
		mapGetDOTaxDocumentSQL.put("DOEMITTED", "o.emitted");
		mapGetDOTaxDocumentSQL.put("DODELIVERYNUMBER", "d.number");
		mapGetDOTaxDocumentSQL.put("DODELIVERYCURRENTSTDATE", "d.currentstdate");
		mapGetDOTaxDocumentSQL.put("DOCURRENTSTDATE", "o.currentstatetypedate");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTNUMBER", "td.number");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTEMITTED", "td.emitted");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTSTCODE", "tdst.code");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTSTNAME", "tdst.name");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTSTDATE", "td.currentstatetypedate");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTSTUSERNAME", "tds.who");
		mapGetDOTaxDocumentSQL.put("DOTAXDOCUMENTACTION", "dotaxdocumentaction");
		mapGetDOTaxDocumentSQL.put("DOTOTALRECEIVED", "dototalreceived");
		mapGetDOTaxDocumentSQL.put("PDFURL", "pdfurl");
	}	
	
	private Map<String, String> mapGetDOVirtualReceptionSQL = new HashMap<String, String>();
	{
		mapGetDOVirtualReceptionSQL.put("VENDORTRADENAME", "v.tradename");
		mapGetDOVirtualReceptionSQL.put("DONUMBER", "o.number");
		mapGetDOVirtualReceptionSQL.put("DOREQUESTNUMBER", "o.requestnumber");
		mapGetDOVirtualReceptionSQL.put("DOEMITTED", "o.emitted");
		mapGetDOVirtualReceptionSQL.put("DODELIVERYNUMBER", "d.number");
		mapGetDOVirtualReceptionSQL.put("DODELIVERYCURRENTSTDATE", "d.currentstdate");
		mapGetDOVirtualReceptionSQL.put("DOCURRENTSTDATE", "o.currentstatetypedate");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTSTCODE", "tdst.code");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTSTNAME", "tdst.name");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTNUMBER", "td.number");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTEMITTED", "td.emitted");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTSTDATE", "td.currentstatetypedate");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTSTUSERNAME", "tds.who");
		mapGetDOVirtualReceptionSQL.put("DOTAXDOCUMENTACTION", "dotaxdocumentaction");
		mapGetDOVirtualReceptionSQL.put("DOCLIENTCOMMUNE", "o.clientcommune");
		mapGetDOVirtualReceptionSQL.put("DOCLIENTCITY", "o.clientcity");
		mapGetDOVirtualReceptionSQL.put("DODELIVERYAVAILABLEUNITS", "SUM(dd.availableunits)");
		mapGetDOVirtualReceptionSQL.put("DODELIVERYRECEIVEDUNITS", "SUM(dd.receivedunits)");
		mapGetDOVirtualReceptionSQL.put("PDFURL", "pdfurl");
	}	
		
	public DOTaxDocumentW addDOTaxDocument(DOTaxDocumentW dotaxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentW) addElement(dotaxdocument);
	}
	
	public DOTaxDocumentW[] getDOTaxDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentW[]) getIdentifiables();
	}
	
	public DOTaxDocumentW updateDOTaxDocument(DOTaxDocumentW dotaxdocument) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DOTaxDocumentW) updateElement(dotaxdocument);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DOTaxDocument entity, DOTaxDocumentW wrapper) {
		wrapper.setVendorid(entity.getVendor() != null ? new Long(entity.getVendor().getId()) : new Long(0));
		wrapper.setDirectorderid(entity.getDirectorder() != null ? new Long(entity.getDirectorder().getId()) : new Long(0));
		wrapper.setCurrentstatetypeid(entity.getCurrentstatetype() != null ? new Long(entity.getCurrentstatetype().getId()) : new Long(0));
		wrapper.setDotaxdocumentticketid(entity.getDoTaxDocumentTicket() != null ? new Long(entity.getDoTaxDocumentTicket().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DOTaxDocument entity, DOTaxDocumentW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getVendorid() != null && wrapper.getVendorid() > 0) { 
			Vendor vendor = vendorserver.getReferenceById(wrapper.getVendorid());
			if (vendor != null) { 
				entity.setVendor(vendor);
			}
		}
		if (wrapper.getDirectorderid() != null && wrapper.getDirectorderid() > 0) { 
			DirectOrder directorder = directorderserver.getReferenceById(wrapper.getDirectorderid());
			if (directorder != null) { 
				entity.setDirectorder(directorder);
			}
		}
		if (wrapper.getCurrentstatetypeid() != null && wrapper.getCurrentstatetypeid() > 0) { 
			DOTaxDocumentStateType currentstatetype = currentstatetypeserver.getReferenceById(wrapper.getCurrentstatetypeid());
			if (currentstatetype != null) { 
				entity.setCurrentstatetype(currentstatetype);
			}
		}
		
		if (wrapper.getDotaxdocumentticketid() != null && wrapper.getDotaxdocumentticketid() > 0) { 
			DOTaxDocumentTicket dotaxdocumentticket = documentTicketServerLocal.getReferenceById(wrapper.getDotaxdocumentticketid());
			if (dotaxdocumentticket != null) { 
				entity.setDoTaxDocumentTicket(dotaxdocumentticket);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "DOTaxDocument";
	}
	@Override
	protected void setEntityname() {
		entityname = "DOTaxDocument";
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndPending(Long vendorid, Date minemitteddate, Date maxcourierdate, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND tok.id IS NULL AND (" + //
												"(td.id IS NULL AND (ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
																    "cs.dodelivery_id IS NOT NULL)) OR " + //
												"tdst.code = 'DOC_FAL' OR " + //
												"tdst.code = 'PRO_DOC' OR " + //
												"tdst.code = 'REC_VEV' OR " + //
												"tdst.code = 'RM_REC' OR " + //
												"tdst.code = 'DTE_VAL') "; //
														
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, true, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndStateType(Long vendorid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
	
		if (dotaxdocumentstatetypeid != null && dotaxdocumentstatetypeid > 0) {
			whereCondition +=
				"td.currentstatetype_id = :dotaxdocumentstatetypeid "; //
		}
		else {
			whereCondition +=
				"tdst.showable = true "; //
		}
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		
		if (dotaxdocumentstatetypeid != null && dotaxdocumentstatetypeid > 0) {
			query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		}
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;
		
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndDeliveryDate(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
																				"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
																				"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
																				"cs.dodelivery_id IS NOT NULL) "; //
			    
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndOrderNumber(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND o.number = :ordernumber AND (" + //
													"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
													"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
													"cs.dodelivery_id IS NOT NULL) "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndNumber(Long vendorid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"td.number = :number "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setLong("number", number);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;
		
	}
	
	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByVendorAndEmissionDate(Long vendorid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = ""; 
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"td.emitted >= :since AND td.emitted < :until "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
						
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;
		
	}
	
	public int countDOTaxDocumentReportByVendorAndPending(Long vendorid, Date minemitteddate, Date maxcourierdate) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND tok.id IS NULL AND (" + //
												"(td.id IS NULL AND (ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
																    "cs.dodelivery_id IS NOT NULL)) OR " + //
												"tdst.code = 'DOC_FAL' OR " + //
												"tdst.code = 'PRO_DOC' OR " + //
												"tdst.code = 'REC_VEV' OR " + //
												"tdst.code = 'RM_REC' OR " + //
												"tdst.code = 'DTE_VAL') "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, true, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOTaxDocumentReportByVendorAndStateType(Long vendorid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		if (dotaxdocumentstatetypeid != null && dotaxdocumentstatetypeid > 0) {
			whereCondition +=
				"td.currentstatetype_id = :dotaxdocumentstatetypeid "; //
		}
		else {
			whereCondition +=
				"tdst.showable = true "; //
		}
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (dotaxdocumentstatetypeid != null && dotaxdocumentstatetypeid > 0) {
			query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		}
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
		
	}
	
	public int countDOTaxDocumentReportByVendorAndDeliveryDate(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
																				"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
																				"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
																				"cs.dodelivery_id IS NOT NULL) "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOTaxDocumentReportByVendorAndOrderNumber(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"o.emitted >= :minemitteddate AND o.number = :ordernumber AND (" + //
													"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
													"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
													"cs.dodelivery_id IS NOT NULL) "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, true, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("minemitteddate", minemitteddate);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOTaxDocumentReportByVendorAndNumber(Long vendorid, Long number) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"td.number = :number "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setLong("number", number);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
		
	}
	
	public int countDOTaxDocumentReportByVendorAndEmissionDate(Long vendorid, Date since, Date until) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		String vendorCondition = "";
		
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			vendorCondition = "AND o.vendor_id = :vendorid "; //
		}
		
		whereCondition +=
			"td.emitted >= :since AND td.emitted < :until "; //
		
		String SQL = getDOTaxDocumentReportQueryString(whereCondition, vendorCondition, false, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
						
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
		
	}
	
	private String getDOTaxDocumentReportQueryString(String whereCondition, String vendorCondition, Boolean courierStateFilters, Boolean pendingDocFilters, int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		String sqlView				= "";
		String sqlSelect 			= "";
		String sqlFrom 				= "";
		String sqlExtraFrom 		= "";
		String sqlWhere 			= "";
		String sql 					= "";
		String conditionOrderBy		= "";	
		
		// Condiciones adicionales a la query usadas solo en casos donde se valide el estado Courier
		if (courierStateFilters) {			
			sqlView =
				"WITH cs AS (" + //
						"SELECT DISTINCT " + //
							"ct.dodelivery_id " + //
						"FROM " + //
							"logistica.directorder AS o JOIN " + //
							"logistica.dodelivery AS d ON d.id = o.dodelivery_id JOIN " + //
							"logistica.couriertag AS ct ON ct.dodelivery_id  = d.id JOIN " + //
							"logistica.courierstate AS cs ON cs.couriertag_id = ct.id " + //
						"WHERE " + //
							"o.courierreceived IS TRUE AND cs.description = 'RECEPCIONADO' AND cs.startdate < :maxcourierdate " + //
							vendorCondition + //
						") "; //
			
			sqlExtraFrom =
				"LEFT JOIN cs ON cs.dodelivery_id = d.id "; //
		}
		
		// Condiciones adicionales a la query usadas solo en el caso del filtro por Documentos Pendientes
		if (pendingDocFilters) {
			sqlView += sqlView.equals("") ? "WITH " : ", ";
			
			sqlView +=
					"taxok AS (" + //
							"SELECT DISTINCT " + //
								"o.id " + //
							"FROM " + //
								"logistica.directorder AS o JOIN " + //
								"logistica.dotaxdocument AS td ON td.directorder_id = o.id JOIN " + //
								"logistica.dotaxdocumentstatetype AS tdst ON tdst.id = td.currentstatetype_id " + //
							"WHERE " + //
								"o.emitted >= :minemitteddate AND tdst.code IN ('W_RM_INV', 'C_INV', 'RM') " + //
								vendorCondition + //
							") ";

			sqlExtraFrom +=
					"LEFT JOIN taxok AS tok ON tok.id = o.id "; //
		}	
		
		if (queryType == 1) {
			sqlSelect =
					"SELECT " + //
						"COUNT(o.id) "; //
		}		
		else {
			sqlSelect =
					"SELECT " + //
						"o.id AS doid, " + //
						"o.number AS donumber, " + //
						"o.requestnumber AS dorequestnumber, " + //
						"logistica.tostr(o.emitted) AS doemitted, " + //
						"d.id AS dodeliveryid, " + //
						"d.number AS dodeliverynumber, " + //
						"logistica.tostr(d.currentstdate) AS dodeliverycurrentstdate, " + //
						"logistica.tostr(o.currentstatetypedate) AS docurrentstdate, " + //
						"td.id AS dotaxdocumentid, " + //
						"td.number AS dotaxdocumentnumber, " + //
						"logistica.tostr(td.emitted) AS dotaxdocumentemitted, " + //
						"td.currentstatetype_id AS dotaxdocumentstid, " + //
						"tdst.code AS dotaxdocumentstcode, " + //
						"tdst.name AS dotaxdocumentstname, " + //
						"logistica.tostr(td.currentstatetypedate) AS dotaxdocumentstdate, " + //
						"tds.who AS dotaxdocumentstusername, " + //
						"COALESCE(tdst.action, 'Ingrese Documento') AS dotaxdocumentaction, " + //
						"CASE WHEN o.totalreceived = 0.0 AND hc.vendor_id IS NOT NULL " + //
							 "THEN o.totalneed " + //
							 "ELSE o.totalreceived " + //
							 "END AS dototalreceived, " + //
						"td.pdfurl "; //

			conditionOrderBy = getOrderByString(mapGetDOTaxDocumentSQL, orderby);
		}
		
		sqlFrom =
			"FROM " + // 
				"logistica.directorder AS o JOIN " + //
				"logistica.directorderstatetype AS ost ON ost.id = o.currentstatetype_id LEFT JOIN " + //
				"logistica.dodelivery AS d ON d.id = o.dodelivery_id LEFT JOIN " + //
				"logistica.dodeliverystatetype AS dst ON dst.id = d.currentstatetype_id LEFT JOIN " + //
				"logistica.dotaxdocument AS td ON td.directorder_id = o.id LEFT JOIN " + //
				"logistica.dotaxdocumentstatetype AS tdst ON tdst.id = td.currentstatetype_id LEFT JOIN " + //
				"logistica.dotaxdocumentstate AS tds ON tds.dotaxdocument_id = td.id AND tds.dotaxdocumentstatetype_id = tdst.id AND " + //
													   "tds.when1 = td.currentstatetypedate LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = o.vendor_id "; //
		
		sqlWhere = 
			"WHERE " + //
				whereCondition + //
				vendorCondition; //
	
		sql = 
			sqlView +
			sqlSelect +
			sqlFrom + 
			sqlExtraFrom + 
			sqlWhere + 
			conditionOrderBy;
		
		return sql;
	}

	public DOTaxDocumentReportDataDTO[] getDOTaxDocumentReportByDirectOrders(Long[] directorderids, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL =
			"SELECT " + //
				"o.id AS doid, " + //
				"o.number AS donumber, " + //
				"o.requestnumber AS dorequestnumber, " + //
				"logistica.tostr(o.emitted) AS doemitted, " + //
				"d.id AS dodeliveryid, " + //
				"d.number AS dodeliverynumber, " + //
				"logistica.tostr(d.currentstdate) AS dodeliverycurrentstdate, " + //
				"logistica.tostr(o.currentstatetypedate) AS docurrentstdate, " + //
				"td.id AS dotaxdocumentid, " + //
				"td.number AS dotaxdocumentnumber, " + //
				"logistica.tostr(td.emitted) AS dotaxdocumentemitted, " + //
				"td.currentstatetype_id AS dotaxdocumentstid, " + //
				"tdst.code AS dotaxdocumentstcode, " + //
				"tdst.name AS dotaxdocumentstname, " + //				
				"logistica.tostr(td.currentstatetypedate) AS dotaxdocumentstdate, " + //
				"tds.who AS dotaxdocumentstusername, " + //
				"COALESCE(tdst.action, 'Ingrese Documento') AS dotaxdocumentaction, " + //	
				"CASE WHEN o.totalreceived = 0.0 AND hc.vendor_id IS NOT NULL " + //
					 "THEN o.totalneed " + //
					 "ELSE o.totalreceived " + //
					 "END AS dototalreceived, " + //
				"td.pdfurl " + //
			"FROM " + //
				"logistica.directorder AS o JOIN " + //
				"logistica.directorderstatetype AS ost ON ost.id = o.currentstatetype_id LEFT JOIN " + //
				"logistica.dodelivery AS d ON d.id = o.dodelivery_id LEFT JOIN " + //   
				"logistica.dotaxdocument AS td ON td.directorder_id = o.id LEFT JOIN " + //
				"logistica.dotaxdocumentstatetype AS tdst ON tdst.id = td.currentstatetype_id LEFT JOIN " + //
				"logistica.dotaxdocumentstate AS tds ON tds.dotaxdocument_id = td.id AND tds.dotaxdocumentstatetype_id = tdst.id AND " + //
				   									   "tds.when1 = td.currentstatetypedate LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = o.vendor_id " + //
			"WHERE " + //
				"o.id IN (:directorderids) " + //
			getOrderByString(mapGetDOTaxDocumentSQL, orderby); //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("directorderids", directorderids);
								
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentReportDataDTO.class));
		
		List list = query.list();
		DOTaxDocumentReportDataDTO[] result = (DOTaxDocumentReportDataDTO[]) list.toArray(new DOTaxDocumentReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorPendingAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndPending(vendorid, minemitteddate, maxcourierdate, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorStateTypeAndPages(Long vendorid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndStateType(vendorid, dotaxdocumentstatetypeid, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorDeliveryDateAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndDeliveryDate(vendorid, minemitteddate, maxcourierdate, since, until, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorOrderNumberAndPages(Long vendorid, Date minemitteddate, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndOrderNumber(vendorid, minemitteddate, maxcourierdate, ordernumber, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorNumberAndPages(Long vendorid, Long number, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndNumber(vendorid, number, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOTaxDocumentIdsByVendorEmissionDateAndPages(Long vendorid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOTaxDocumentReportDataDTO[] result;
		List<Object> totalDOTaxDocuments = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++){
			for (int j = pageranges[i].getSincePage() ; j <= pageranges[i].getUntilPage(); j++){
				result = getDOTaxDocumentReportByVendorAndEmissionDate(vendorid, since, until, j, rows, orderby, true);
				totalDOTaxDocuments.add(result);					
			}			
		}
		
		Object[] dotdArray = (Object[]) totalDOTaxDocuments.toArray(new Object[totalDOTaxDocuments.size()]);		
		DOTaxDocumentReportDataDTO[] dotaxdocuments = (DOTaxDocumentReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOTaxDocumentReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dotaxdocuments.length];
		for (int i = 0 ; i < dotaxdocuments.length; i++){
			directorderids[i] = dotaxdocuments[i].getDoid();
		}
		
		return directorderids;
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'DTE_VAL' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'W_RM_INV' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition = "";
		if (dotaxdocumentstatetypeid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition =
				"td.currentstatetype_id = :dotaxdocumentstatetypeid ";
		}
		else {
			whereCondition =
				"tdst.showable = true ";
		}
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		if(dotaxdocumentstatetypeid!=null &&  dotaxdocumentstatetypeid>0)
		query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
															"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
															"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
															"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"o.number = :ordernumber AND (" + //
									"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
									"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
									"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.number = :dotaxdocumentnumber ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setLong("dotaxdocumentnumber", dotaxdocumentnumber);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.emitted >= :since AND td.emitted < :until ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		
		if(ispaginated){//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedInDTE(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'DTE_VAL' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndInvoicedWithoutReception(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'W_RM_INV' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentStateType(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition="";
		if (dotaxdocumentstatetypeid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition =
				"td.currentstatetype_id = :dotaxdocumentstatetypeid ";
		}		
		else {
			whereCondition =
				"tdst.showable = true ";
		}
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		if(dotaxdocumentstatetypeid!=null &&  dotaxdocumentstatetypeid>0)
		query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndDeliveryDate(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
															"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
															"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
															"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndOrderNumber(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"o.number = :ordernumber AND (" + //
									"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
									"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
									"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentNumber(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.number = :dotaxdocumentnumber ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setLong("dotaxdocumentnumber", dotaxdocumentnumber);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentAndTaxDocumentEmissionDate(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.emitted >= :since AND td.emitted < :until ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;		
	}
	
	private String getDOVirtualReceptionReportQueryString(String whereCondition, String basicCondition, Boolean courierStateFilters, int queryType, OrderCriteriaDTO[] orderby) throws AccessDeniedException{
		String sqlView				= "";
		String SQL 					= "";
		String sqlExtraFrom 		= "";
		String conditionOrderBy		= "";	
		String conditionGroupBy		= "";	
		
		if (courierStateFilters) {			
			sqlView =
				"WITH cs AS (" + //
						"SELECT DISTINCT " + //
							"ct.dodelivery_id " + //
						"FROM " + //
							"logistica.directorder AS o JOIN " + //
							"logistica.dodelivery AS d ON d.id = o.dodelivery_id JOIN " + //
							"logistica.couriertag AS ct ON ct.dodelivery_id  = d.id JOIN " + //
							"logistica.courierstate AS cs ON cs.couriertag_id = ct.id " + //
						"WHERE " + //
							"o.courierreceived IS TRUE AND cs.description = 'RECEPCIONADO' AND cs.startdate < :maxcourierdate " + //
							basicCondition + //
						") "; //
			
			sqlExtraFrom =
				"LEFT JOIN cs ON cs.dodelivery_id = d.id "; //
		}
		
		if (queryType == 1){
			SQL = 
				sqlView + //
				"SELECT " + //
					"COUNT(DISTINCT (o.id, td.id)) "; //
		}
		else {
			SQL =
				sqlView + //
				"SELECT " + //
					"v.id AS vendorid, " + //
					"v.tradename AS vendortradename, " + //
					"o.id AS doid, " + //
					"o.number AS donumber, " + //
					"o.requestnumber AS dorequestnumber, " + //
					"logistica.tostr(o.emitted) AS doemitted, " + //
					"d.id AS dodeliveryid, " + //
					"d.number AS dodeliverynumber, " + //
					"logistica.tostr(d.currentstdate) AS dodeliverycurrentstdate, " + //
					"logistica.tostr(o.currentstatetypedate) AS docurrentstdate, " + //
					"td.id AS dotaxdocumentid, " + //
					"td.currentstatetype_id AS dotaxdocumentstid, " + //
					"tdst.code AS dotaxdocumentstcode, " + //
					"tdst.name AS dotaxdocumentstname, " + //
					"td.number AS dotaxdocumentnumber, " + //
					"logistica.tostr(td.emitted) AS dotaxdocumentemitted, " + //
					"logistica.tostr(td.currentstatetypedate) AS dotaxdocumentstdate, " + //
					"tds.who AS dotaxdocumentstusername, " + //
					"COALESCE(tdst.action, 'Ingrese Documento') AS dotaxdocumentaction, " + //					
					"o.client_id AS clientid, " + //
					"o.clientcommune AS doclientcommune, " + //
					"o.clientcity AS doclientcity, " + //
					"SUM(dd.availableunits) AS dodeliveryavailableunits, " + //
					"SUM(dd.receivedunits) AS dodeliveryreceivedunits, " + //
					"COALESCE(td.pdfurl, '') AS pdfurl "; //
			
			conditionOrderBy = getOrderByString(mapGetDOVirtualReceptionSQL, orderby);
			
			conditionGroupBy =
				"GROUP BY " + //
					"v.id, v.tradename, o.id, o.number, o.requestnumber, o.emitted, d.id, d.number, d.currentstdate, o.currentstatetypedate, " + //
					"td.id, td.currentstatetype_id, tdst.code, tdst.name, td.number, td.emitted, td.currentstatetypedate, tds.who, " + //
					"COALESCE(tdst.action, 'Ingrese Documento'), o.client_id, o.clientcommune, o.clientcity, td.pdfurl "; //
		}
		
		SQL += 
			"FROM " +   //
				"logistica.directorder o JOIN " + //
				"logistica.vendor v ON v.id = o.vendor_id JOIN " + //
				"logistica.directorderstatetype ost ON ost.id = o.currentstatetype_id LEFT JOIN " + //
				"logistica.dodelivery d ON d.id = o.dodelivery_id LEFT JOIN " + //
				"logistica.dodeliverystatetype dst ON dst.id = d.currentstatetype_id LEFT JOIN " + //
				"logistica.dodeliverydetail dd ON dd.dodelivery_id = d.id LEFT JOIN " + //
				"logistica.dotaxdocument td ON td.directorder_id = o.id LEFT JOIN " + //
				"logistica.dotaxdocumentstatetype tdst ON tdst.id = td.currentstatetype_id LEFT JOIN " + //
				"logistica.dotaxdocumentstate AS tds ON tds.dotaxdocument_id = td.id AND tds.dotaxdocumentstatetype_id = tdst.id AND " + //
				   									   "tds.when1 = td.currentstatetypedate LEFT JOIN " + //
				"logistica.hiredcourier AS hc ON hc.vendor_id = v.id " + //
				sqlExtraFrom + //
			"WHERE " + //
				whereCondition + //
				basicCondition + //
			conditionGroupBy + //
			conditionOrderBy; //
		
		return SQL;		
	}
	
	public DOVirtualReceptionDetailDataDTO[] getDOVirtualReceptionDetailByOrder(Long doid){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getCountOrderReportByOrder");
		query.setLong("doid", doid);
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionDetailDataDTO.class));
		
		List list = query.list();
		DOVirtualReceptionDetailDataDTO[] result = (DOVirtualReceptionDetailDataDTO[]) list.toArray(new DOVirtualReceptionDetailDataDTO[list.size()]);		
		
		return result;		
		
	}
	
	public DOVirtualReceptionCSVReportDataDTO[] getCSVDOVirtualReceptionReportByOrderIds(Long[] doids){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getCSVDOVirtualReceptionReportByOrderIds");
		query.setParameterList("doids", doids);
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionCSVReportDataDTO.class));
		
		List list = query.list();
		DOVirtualReceptionCSVReportDataDTO[] result = (DOVirtualReceptionCSVReportDataDTO[]) list.toArray(new DOVirtualReceptionCSVReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	public DOInvoicePendingDTO[] getDOInvoicePendingByDate(Date maxcourierdate, Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getDOInvoicePendingByDate");
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(DOInvoicePendingDTO.class));
		
		List list = query.list();
		DOInvoicePendingDTO[] result = (DOInvoicePendingDTO[]) list.toArray(new DOInvoicePendingDTO[list.size()]);		
		
		return result;
	}
	
	public int countDOInvoicePendingByDate(Date maxcourierdate, Date since, Date until) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.countDOInvoicePendingByDate");
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public DOTaxDocumentDTO[] getDOTaxDocumentsByOrderIdsAndWithoutReception(Long[] doids){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getDOTaxDocumentsByOrderIdsAndWithoutReception");
		query.setParameterList("doids", doids);
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentDTO.class));
		
		List list = query.list();
		DOTaxDocumentDTO[] result = (DOTaxDocumentDTO[]) list.toArray(new DOTaxDocumentDTO[list.size()]);		
		
		return result;
		
	}
	
	public DOTaxDocumentDTO[] getDOTaxDocumentsById(Long[] documentsId){
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getDOTaxDocumentsById");
		query.setParameterList("documentsId", documentsId);
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentDTO.class));
		
		List list = query.list();
		DOTaxDocumentDTO[] result = (DOTaxDocumentDTO[]) list.toArray(new DOTaxDocumentDTO[list.size()]);		
		
		return result;		
	}
	
	public DOTaxDocumentW[] getPendingDOTaxDocumentsByTicket(Long ticketid) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getPendingDOTaxDocumentsByTicket");
		query.setLong("ticketid", ticketid);
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentW.class));
		
		List list = query.list();
		DOTaxDocumentW[] result = (DOTaxDocumentW[]) list.toArray(new DOTaxDocumentW[list.size()]);		
		
		return result;
	}
	
	public DOTaxDocumentEvaluationValidationBean[] getDocumentsByNumbers(Long[] numbers) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.taxdocuments.entities.DOTaxDocument.getDocumentsByNumbers");
		query.setParameterList("numbers", numbers);
		query.setResultTransformer(new LowerCaseResultTransformer(DOTaxDocumentEvaluationValidationBean.class));
		
		List list = query.list();
		DOTaxDocumentEvaluationValidationBean[] result = (DOTaxDocumentEvaluationValidationBean[]) list.toArray(new DOTaxDocumentEvaluationValidationBean[list.size()]);		
		
		return result;
	}
	
	public DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByDirectOrders(Long vendorid, Long[] directorderids, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException {
		
		if (directorderids == null || directorderids.length <= 0) {
			throw new OperationFailedException("No se puede generar archivo. Parámetro nulo o vacío.");
		}
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND o.vendor_id = :vendorid ";
		}
		
		// Dividir el parámetro de entrada en bloques de 50 elementos
		int chunksize = 500;
		Object[] splitids = ClassUtilities.SplitArray(directorderids, Long.class, chunksize);
		Object[] reportArray = new Object[splitids.length];
		for (int i = 0; i < splitids.length; i++) {
			Object[] ids = (Object[]) splitids[i];
			if (ids.length <= 0) {
				reportArray[i] = new DOVirtualReceptionReportDataDTO[0];
				continue;
			}
			
			String SQL = getDOVirtualReceptionReportByDirectOrdersQueryString(2, whereCondition, orderby);		
			
			SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
			query.setParameterList("directorderids", ids);
			if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
				query.setLong("vendorid", vendorid);
			}
			query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
								
			List list = query.list();
			DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);
			reportArray[i] = result;
		}
		
		DOVirtualReceptionReportDataDTO[] virtualReceptionReport = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(reportArray, DOVirtualReceptionReportDataDTO.class);
		return virtualReceptionReport;
	}
	
	public int countDOVirtualReceptionReportByDirectOrders(Long vendorid, Long[] directorderids) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition += "AND o.vendor_id = :vendorid ";
		}
		
		String SQL = getDOVirtualReceptionReportByDirectOrdersQueryString(1, whereCondition, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("directorderids", directorderids);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	private String getDOVirtualReceptionReportByDirectOrdersQueryString(int queryType, String whereCondition, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		
		String SQL 					= "";
		String conditionGroupBy		= "";
		String conditionOrderBy		= "";
		
		if (queryType == 1){
			SQL =
				"SELECT " + //
					"COUNT(DISTINCT (o.id, td.id)) "; //
		}
		else {
			SQL =
				"SELECT " + //
					"v.id AS vendorid, " + //
					"v.tradename AS vendortradename, " + //
					"o.id AS doid, " + //
					"o.number AS donumber, " + //
					"o.requestnumber AS dorequestnumber, " + //
					"logistica.tostr(o.emitted) AS doemitted, " + //
					"d.id AS dodeliveryid, " + //
					"d.number AS dodeliverynumber, " + //
					"logistica.tostr(d.currentstdate) AS dodeliverycurrentstdate, " + //
					"logistica.tostr(o.currentstatetypedate) AS docurrentstdate, " + //
					"td.id AS dotaxdocumentid, " + //
					"td.currentstatetype_id AS dotaxdocumentstid, " + //
					"tdst.code AS dotaxdocumentstcode, " + //
					"tdst.name AS dotaxdocumentstname, " + //
					"td.number AS dotaxdocumentnumber, " + //
					"logistica.tostr(td.emitted) AS dotaxdocumentemitted, " + //
					"logistica.tostr(td.currentstatetypedate) AS dotaxdocumentstdate, " + //
					"tds.who AS dotaxdocumentstusername, " + //
					"COALESCE(tdst.action, 'Ingrese Documento') AS dotaxdocumentaction, " + //					
					"o.client_id AS clientid, " + //
					"o.clientcommune AS doclientcommune, " + //
					"o.clientcity AS doclientcity, " + //
					"SUM(dd.availableunits) AS dodeliveryavailableunits, " + //
					"SUM(dd.receivedunits) AS dodeliveryreceivedunits, " + //
					"COALESCE(td.pdfurl, '') AS pdfurl "; //
			
			conditionGroupBy =
				"GROUP BY " + //
						"v.id, v.tradename, o.id, o.number, o.requestnumber, o.emitted, d.id, d.number, d.currentstdate, o.currentstatetypedate, " + //
						"td.id, td.currentstatetype_id, tdst.code, tdst.name, td.number, td.emitted, td.currentstatetypedate, tds.who, " + //
						"COALESCE(tdst.action, 'Ingrese Documento'), o.client_id, o.clientcommune, o.clientcity, td.pdfurl "; //
						
			conditionOrderBy = getOrderByString(mapGetDOVirtualReceptionSQL, orderby);			
		}
		
		SQL += 
			"FROM " +   //
					"logistica.directorder o JOIN " + //
					"logistica.vendor v ON v.id = o.vendor_id JOIN " + //
					"logistica.directorderstatetype ost ON ost.id = o.currentstatetype_id LEFT JOIN " + //
					"logistica.dodelivery d ON d.id = o.dodelivery_id LEFT JOIN " + //
					"logistica.dodeliverydetail dd ON dd.dodelivery_id = d.id LEFT JOIN " + //
					"logistica.dotaxdocument td ON td.directorder_id = o.id LEFT JOIN " + //
					"logistica.dotaxdocumentstatetype tdst ON tdst.id = td.currentstatetype_id LEFT JOIN " + //
					"logistica.dotaxdocumentstate AS tds ON tds.dotaxdocument_id = td.id AND tds.dotaxdocumentstatetype_id = tdst.id AND " + //
					   									   "tds.when1 = td.currentstatetypedate " + //
			"WHERE " + //
					"o.id IN (:directorderids) " + //
					whereCondition + //
			conditionGroupBy + //
			conditionOrderBy; //
		
		return SQL;		
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedInDTEAndPages(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedInDTEAndPageRange(vendorid, salestoreids, departmentid, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);	
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(Long vendorid, Long[] salestoreids, Long departmentid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPageRange(vendorid, salestoreids, departmentid, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPageRange(vendorid, salestoreids, departmentid, dotaxdocumentstatetypeid, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentDeliveryDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentDeliveryDateAndPageRange(vendorid, salestoreids, departmentid, maxcourierdate, since, until, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentOrderNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentOrderNumberAndPageRange(vendorid, salestoreids, departmentid, maxcourierdate, ordernumber, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentNumberAndPageRange(vendorid, salestoreids, departmentid, dotaxdocumentnumber, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	public Long[] getDOVirtualReceptionIdsByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until, int pagenumber, int rows, OrderCriteriaDTO[] orderby, PageRangeDTO[] pageranges) throws AccessDeniedException, OperationFailedException {
		
		DOVirtualReceptionReportDataDTO[] result;
		List<Object> totalDOVirtualReceptions = new ArrayList<Object>();
		for (int i = 0 ; i < pageranges.length; i++) {
			result = getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPageRange(vendorid, salestoreids, departmentid, since, until, pageranges[i].getSincePage(), pageranges[i].getUntilPage(), rows, orderby);
			totalDOVirtualReceptions.add(result);
		}
		
		Object[] dotdArray = (Object[]) totalDOVirtualReceptions.toArray(new Object[totalDOVirtualReceptions.size()]);		
		DOVirtualReceptionReportDataDTO[] dovirtualreceptions = (DOVirtualReceptionReportDataDTO[]) ClassUtilities.UnsplitArrays(dotdArray, DOVirtualReceptionReportDataDTO.class);
		
		// Obtener solo los ids de las ordenes de las p�ginas solicitadas
		Long[] directorderids = new Long[dovirtualreceptions.length];
		for (int i = 0 ; i < dovirtualreceptions.length; i++) {
			directorderids[i] = dovirtualreceptions[i].getDoid();
		}
		
		return directorderids;
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedInDTEAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'DTE_VAL' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));		
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"tdst.code = 'W_RM_INV' ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition = "";
		if (dotaxdocumentstatetypeid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition =
				"td.currentstatetype_id = :dotaxdocumentstatetypeid ";
		}
		else {
			whereCondition =
				"tdst.showable = true ";
		}
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		
		if(dotaxdocumentstatetypeid!=null &&  dotaxdocumentstatetypeid>0)
		query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentDeliveryDateAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
															"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
															"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
															"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentOrderNumberAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"o.number = :ordernumber AND (" + //
									"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
									"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
									"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentNumberAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.number = :dotaxdocumentnumber ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setLong("dotaxdocumentnumber", dotaxdocumentnumber);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;		
	}
	
	private DOVirtualReceptionReportDataDTO[] getDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPageRange(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until, int sincepagenumber, int untilpagenumber, int rows, OrderCriteriaDTO[] orderby) throws AccessDeniedException, OperationFailedException{
		
		String whereCondition =
			"td.emitted >= :since AND td.emitted < :until ";
		
		String basicCondition = "";
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){ // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if(vendorid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("vendorid", vendorid);
		}
		if(salestoreids[0].intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setParameterList("salestoreids", salestoreids);
		}
		if(departmentid.intValue() != RegionalLogisticConstants.getInstance().getINT_TODOS()){
			query.setLong("departmentid", departmentid);
		}
		query.setDate("since", since);
		query.setDate("until", until);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DOVirtualReceptionReportDataDTO.class));
		query.setFirstResult((sincepagenumber - 1) * rows);
		query.setMaxResults((untilpagenumber - sincepagenumber + 1) * rows);
		
		List list = query.list();
		DOVirtualReceptionReportDataDTO[] result = (DOVirtualReceptionReportDataDTO[]) list.toArray(new DOVirtualReceptionReportDataDTO[list.size()]);		
		
		return result;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedInDTEAndPages(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "tdst.code = 'DTE_VAL' ";
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentInvoicedWithoutReceptionAndPages(Long vendorid, Long[] salestoreids, Long departmentid) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "tdst.code = 'W_RM_INV' ";
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentStateTypeAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentstatetypeid) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "";
		if (dotaxdocumentstatetypeid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			whereCondition = "td.currentstatetype_id = :dotaxdocumentstatetypeid ";
		}
		else {
			whereCondition = "tdst.showable = true ";
		}
		
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		if (dotaxdocumentstatetypeid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("dotaxdocumentstatetypeid", dotaxdocumentstatetypeid);
		}
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentDeliveryDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Date since, Date until) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "d.currentstdate >= :since AND d.currentstdate < :until AND (" + //
															"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
															"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
															"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentOrderNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date maxcourierdate, Long ordernumber) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "o.number = :ordernumber AND (" + //
											"ost.code IN ('RECEPCIONADA', 'EXTRAVIADO') OR " + //
											"dst.code IN ('REC_CONFORME', 'EXTRAVIADO') OR " + //
											"cs.dodelivery_id IS NOT NULL) "; //
		
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, true, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("maxcourierdate", maxcourierdate);
		query.setLong("ordernumber", ordernumber);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentNumberAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Long dotaxdocumentnumber) throws AccessDeniedException, OperationFailedException {
		
		String whereCondition = "td.number = :dotaxdocumentnumber ";
		
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dotaxdocumentnumber", dotaxdocumentnumber);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	public int countDOVirtualReceptionReportByVendorSaleStoreDepartmentTaxDocumentEmissionDateAndPages(Long vendorid, Long[] salestoreids, Long departmentid, Date since, Date until) throws AccessDeniedException, OperationFailedException {
		String whereCondition = "td.emitted >= :since AND td.emitted < :until ";
		
		String basicCondition = "";
		if (vendorid!= RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.vendor_id = :vendorid ";
		}
		if (salestoreids[0]!= RegionalLogisticConstants.getInstance().getINT_TODOS()) { // si es TODOS, viene en el primer elemento del arreglo
			basicCondition += "AND o.salestore_id IN (:salestoreids) ";
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			basicCondition += "AND o.department_id = :departmentid ";
		}
		
		String SQL = getDOVirtualReceptionReportQueryString(whereCondition, basicCondition, false, 1, null);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setDate("since", since);
		query.setDate("until", until);
		if (vendorid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("vendorid", vendorid);
		}
		if (salestoreids[0] != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setParameterList("salestoreids", salestoreids);
		}
		if (departmentid != RegionalLogisticConstants.getInstance().getINT_TODOS()) {
			query.setLong("departmentid", departmentid);
		}
		
		int total = ((BigInteger)query.list().get(0)).intValue();
		
		return total;
	}
	
	private String getOrderByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
	
}

