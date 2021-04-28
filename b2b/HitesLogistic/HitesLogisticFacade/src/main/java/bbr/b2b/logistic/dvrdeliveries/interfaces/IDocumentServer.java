package bbr.b2b.logistic.dvrdeliveries.interfaces;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.DocumentW;
import bbr.b2b.logistic.dvrdeliveries.entities.Document;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.ASNDetailDataToMessageDTO;
import bbr.b2b.logistic.dvrdeliveries.report.classes.DvrDeliveryDatingDocumentDetailReportDataDTO;

public interface IDocumentServer extends IElementServer<Document, DocumentW> {

	DocumentW addDocument(DocumentW document) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentW[] getDocuments() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DocumentW updateDocument(DocumentW document) throws AccessDeniedException, OperationFailedException, NotFoundException;
	Long getNextSequenceAsnNumber() throws OperationFailedException, NotFoundException;
	DvrDeliveryDatingDocumentDetailReportDataDTO[] getDvrDeliveryDatingDocumentDetailData(Long dvrdeliveryid, OrderCriteriaDTO[] orderby)throws AccessDeniedException, OperationFailedException;
	int getCountDvrDeliveryDatingDocumentDetailData(Long dvrdeliveryid) throws AccessDeniedException, OperationFailedException;
	DocumentW[] getDocumentByIds(Long[] documentids) throws NotFoundException, OperationFailedException;
	DocumentW[] getDocumentRelatedToOrderDelivery(Long dvrorderid, Long dvrdeliveryid) throws NotFoundException, OperationFailedException;
	DocumentW[] getDocumentRelatedToDelivery(Long dvrdeliveryid) throws NotFoundException, OperationFailedException;
	int getCorrelativeFromASNNumber(String documentnumber, Long vendorid);	
	ASNDataToMessageDTO[] getASNHeaderData(Long dvrdeliveryid, Long documentid);
	ASNDetailDataToMessageDTO[] getASNDetailData(Long dvrdeliveryid, Long documentid);
	ASNDataToMessageDTO[] getDeliveryASNHeaderData(Long dvrdeliveryid, Long[] documentids);
	ASNDetailDataToMessageDTO[] getDeliveryASNDetailData(Long dvrdeliveryid, Long[] documentids);
}
