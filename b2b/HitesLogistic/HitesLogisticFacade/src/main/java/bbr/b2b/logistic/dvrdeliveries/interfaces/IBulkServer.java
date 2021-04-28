package bbr.b2b.logistic.dvrdeliveries.interfaces;

import java.io.IOException;
import java.time.LocalDateTime;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkW;
import bbr.b2b.logistic.dvrdeliveries.entities.Bulk;
import bbr.b2b.logistic.dvrdeliveries.report.classes.AsnUploadErrorDTO;

public interface IBulkServer extends IElementServer<Bulk, BulkW> {

	BulkW addBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkW[] getBulks() throws AccessDeniedException, OperationFailedException, NotFoundException;
	BulkW updateBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException;

	/////////////////////////////////////
	// Carga PL
	/////////////////////////////////////
	void doCreateTempTableAsn();
	void doCreateTempTableError();
	int doLoadCSV(String filename) throws IOException;
	AsnUploadErrorDTO[] getErrorsOfASN();
	int doFillOrderData();
	int doFillLocationData();
	int doFillItemData();
	int doFillDeliveryData(Long dvrdeliveryid);
	int doFillDocumenttype();
	int doFillDvrOrderDetailPosition();
	int doCheckUniqueRows();
	int doCheckOrderExists();
	int doCheckOrderAssignedVendor(Long vendorid, String vendorname);
	int doCheckOCValidState();
	int doCheckOCExpirationDate(LocalDateTime now);
	int doCheckItemExists();
	int doCheckDeliveryLocationExists();
	int doCheckOCItemLocalOnDelivery();
	int doCheckUnits();
	int doCheckOCItemLocationUnits();
	int doCheckLPNFormatTotalLength(Integer bulktotallength);
	int doCheckLPNFormatStartsWith(Integer prefixsince, Integer prefixlength, String prefixvalue);
	int doCheckLPNFormatVendorCode(Integer lpnvendorsince, Integer lpnvendorlength);
	int doCheckLPNFormatSerialLength(Integer lpnserialsince, Integer lpnseriallength);
	int doCheckLPNFormatSerialType(Integer lpnserialsince);
	int doCheckLPNFormatSerialValue(Integer lpnserialsince, Integer mincorrelative);
	int doCheckLPNVendorCodeRelated(Integer lpnvendorsince, Integer lpnvendorlength, String vendorcode);
	int doCheckExistsLPN();
	int doCheckUniqueLPNOC();
	int doCheckUniqueLPNDocument();
	int doCheckUniqueLPNLocation();
	int doCheckUniqueSKUByLPN(String[] ordertypefilter);
	int doCheckDocumentType();
	int doCheckDocumentNumberTypeVendorNotExists(String vendorcode);
	int doCheckUniqueDocumentNumberTypeOCUnique();
	int doAddDocumentFromAsnData();
	int doFillDocumentid();
	int doCheckDocumentExists();
	int doUpdateDocumentFromAsnData() throws OperationFailedException;
	int doAddBulkFromAsnData();
	int doFillBulkId();
	int doAddBulkDetailFromAsnData();
	int doSetAvailableInDvrOrderDeliveryDetail(Long dvrdeliveryid, Long newvalue);
	int doUpdateAvailableInDvrOrderDeliveryDetail();
	int doCloseNotIncludedOrderDeliveries(Long dvrdeliveryid);
	
	int getTotalBulkQty();
	Double getTotalUnitsBulks();
	Long[] doGetDvrOrderIdsFromASN();	
}
