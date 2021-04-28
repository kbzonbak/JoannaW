package bbr.b2b.logistic.dvrdeliveries.classes;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.constants.LogisticConstants;
import bbr.b2b.logistic.dvrdeliveries.entities.Bulk;
import bbr.b2b.logistic.dvrdeliveries.entities.Document;
import bbr.b2b.logistic.dvrdeliveries.entities.DvrDelivery;
import bbr.b2b.logistic.dvrdeliveries.report.classes.AsnUploadErrorDTO;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.dvrdeliveries.data.wrappers.BulkW;

@Stateless(name = "servers/dvrdeliveries/BulkServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BulkServer extends LogisticElementServer<Bulk, BulkW> implements BulkServerLocal {


	@EJB
	DvrDeliveryServerLocal dvrdeliveryserver;

	@EJB
	DocumentServerLocal documentserver;
	
	public BulkW addBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW) addElement(bulk);
	}
	public BulkW[] getBulks() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW[]) getIdentifiables();
	}
	public BulkW updateBulk(BulkW bulk) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (BulkW) updateElement(bulk);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Bulk entity, BulkW wrapper) {
		wrapper.setDvrdeliveryid(entity.getDvrdelivery() != null ? new Long(entity.getDvrdelivery().getId()) : new Long(0));
		wrapper.setDocumentid(entity.getDocument() != null ? new Long(entity.getDocument().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Bulk entity, BulkW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDvrdeliveryid() != null && wrapper.getDvrdeliveryid() > 0) { 
			DvrDelivery dvrdelivery = dvrdeliveryserver.getReferenceById(wrapper.getDvrdeliveryid());
			if (dvrdelivery != null) { 
				entity.setDvrdelivery(dvrdelivery);
			}
		}
		if(wrapper.getDocumentid() != null && wrapper.getDocumentid() > 0) {
			Document document = documentserver.getReferenceById(wrapper.getDocumentid());
			if (document != null) { 
				entity.setDocument(document);
			}
		}
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 												CARGA PL																							//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void doCreateTempTableAsn(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCreateTempTableAsn");
		query.executeUpdate();
	}
	
	public void doCreateTempTableError(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCreateTempTableError");
		query.executeUpdate();		
	}
	
	public int doLoadCSV(String filename) throws IOException {
		String[] linesDataArray = ClassUtilities.getLineDataArrayFromCSV(filename, Charset.defaultCharset());
		String columns = "rownumber, ordernumber, locationcode, lpnnumber, itemcode, units, documentnumber, documenttype";
		String[] columnsArray = columns.split(",");
		String tableName = "asnupload";
		String SQL = ClassUtilities.getQueryElementsTempTable(tableName, columnsArray, linesDataArray);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate(); // no retorna el contador
		return total;
	}
	
	public AsnUploadErrorDTO[] getErrorsOfASN(){
		String SQL = "SELECT line, error FROM asnerror order by line ";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(AsnUploadErrorDTO.class));
		List<?> list = query.list();
		AsnUploadErrorDTO[] result = (AsnUploadErrorDTO[]) list.toArray(new AsnUploadErrorDTO[list.size()]);
		return result;
	}
	
	public int doFillOrderData(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillOrderData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillLocationData(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillLocationData");
		int total = query.executeUpdate();
		return total;		
	}
	
	public int doFillItemData(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillItemData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDeliveryData(Long dvrdeliveryid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillDeliveryData");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;
	}

	public int doFillDocumenttype() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillDocumenttype");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDvrOrderDetailPosition(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillDvrOrderDetailPosition");
		int total = query.executeUpdate();
		return total;		
	}

	public int doCheckUniqueRows() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueRows");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOrderExists(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOrderExists");
		int total = query.executeUpdate();
		return total;
	}

	public int doCheckOrderAssignedVendor(Long vendorid, String vendorname) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOrderAssignedVendor");
		query.setLong("vendorid", vendorid);
		query.setString("vendorname", vendorname);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOCValidState() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOCValidState");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOCExpirationDate(LocalDateTime now) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOCExpirationDate");
		query.setParameter("now", now);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckItemExists(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckItemExists");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDeliveryLocationExists(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckDeliveryLocationExists");
		int total = query.executeUpdate();
		return total;
	}

	public int doCheckOCItemLocalOnDelivery(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOCItemLocalOnDelivery");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUnits(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUnits");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckOCItemLocationUnits(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckOCItemLocationUnits");
		int total = query.executeUpdate();
		return total;
	}

	public int doCheckLPNFormatTotalLength(Integer bulktotallength){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatTotalLength");
		query.setInteger("bulktotallength", bulktotallength);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLPNFormatStartsWith(Integer prefixsince, Integer prefixlength, String prefixvalue) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatStartsWith");
		query.setInteger("prefixsince", prefixsince);
		query.setInteger("prefixlength", prefixlength);
		query.setString("prefixvalue", prefixvalue);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLPNFormatVendorCode(Integer lpnvendorsince, Integer lpnvendorlength) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatVendorCode");
		query.setInteger("lpnvendorsince", lpnvendorsince);
		query.setInteger("lpnvendorlength", lpnvendorlength);
		int total = query.executeUpdate();
		return total;	
	}
	
	public int doCheckLPNFormatSerialLength(Integer lpnserialsince, Integer lpnseriallength) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatSerialLength");
		query.setInteger("lpnserialsince", lpnserialsince);
		query.setInteger("lpnseriallength", lpnseriallength);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLPNFormatSerialType(Integer lpnserialsince) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatSerialType");
		query.setInteger("lpnserialsince", lpnserialsince);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLPNFormatSerialValue(Integer lpnserialsince, Integer mincorrelative) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNFormatSerialValue");
		query.setInteger("lpnserialsince", lpnserialsince);
		query.setInteger("mincorrelative", mincorrelative);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckLPNVendorCodeRelated(Integer lpnvendorsince, Integer lpnvendorlength, String vendorcode) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckLPNVendorCodeRelated");
		query.setInteger("lpnvendorsince", lpnvendorsince);
		query.setInteger("lpnvendorlength", lpnvendorlength);
		query.setString("vendorcode", vendorcode);
		int total = query.executeUpdate();
		return total;	
	}
	
	public int doCheckExistsLPN(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckExistsLPN");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueLPNOC() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueLPNOC");
		int total = query.executeUpdate();
		return total;
	}

	public int doCheckUniqueLPNDocument() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueLPNDocument");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueLPNLocation() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueLPNLocation");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueSKUByLPN(String[] ordertypefilter) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueSKUByLPN");
		query.setParameterList("ordertypefilter", ordertypefilter);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDocumentType() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckDocumentType");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDocumentNumberTypeVendorNotExists(String vendorcode) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckDocumentNumberTypeVendorNotExists");
		query.setString("vendorcode", vendorcode);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckUniqueDocumentNumberTypeOCUnique() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckUniqueDocumentNumberTypeOCUnique");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doAddDocumentFromAsnData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doAddDocumentFromAsnData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillDocumentid() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillDocumentid");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCheckDocumentExists() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCheckDocumentExists");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doUpdateDocumentFromAsnData() throws OperationFailedException {
		Double iva = LogisticConstants.getIVA();
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doUpdateDocumentFromAsnData");
		query.setDouble("iva", iva);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doAddBulkFromAsnData(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doAddBulkFromAsnData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doFillBulkId(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doFillBulkId");
		int total = query.executeUpdate();
		return total;
	}

	public int doAddBulkDetailFromAsnData(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doAddBulkDetailFromAsnData");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doSetAvailableInDvrOrderDeliveryDetail(Long dvrdeliveryid, Long newvalue){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doSetAvailableInDvrOrderDeliveryDetail");
		query.setLong("newvalue", newvalue);
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int doUpdateAvailableInDvrOrderDeliveryDetail(){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doUpdateAvailableInDvrOrderDeliveryDetail");
		int total = query.executeUpdate();
		return total;
	}
	
	public int doCloseNotIncludedOrderDeliveries(Long dvrdeliveryid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.dvrdeliveries.entities.Bulk.doCloseNotIncludedOrderDeliveries");
		query.setLong("dvrdeliveryid", dvrdeliveryid);
		int total = query.executeUpdate();
		return total;
	}
	
	public int getTotalBulkQty(){
		String SQL = "SELECT COUNT(DISTINCT lpnnumber) FROM asnupload";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = ((BigInteger)  query.list().get(0)).intValue();	
		return total;
	}
	
	public Double getTotalUnitsBulks(){
		String SQL = "SELECT SUM(units) FROM asnupload";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		Double total = (Double) query.list().get(0);
		return total;
	}

	
	public Long[] doGetDvrOrderIdsFromASN(){
		List<Long> longlist = new ArrayList<Long>();
		
		String SQL = "SELECT DISTINCT(dvrorder_id) FROM asnupload";
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		List<BigInteger> list = query.list();		
		longlist = list.stream().map(BigInteger::longValue).collect(Collectors.toList());
		Long[] result =  longlist.toArray(new Long[longlist.size()]);
		return result;		
	}
	
	
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Bulk";
	}
	@Override
	protected void setEntityname() {
		entityname = "Bulk";
	}
}
