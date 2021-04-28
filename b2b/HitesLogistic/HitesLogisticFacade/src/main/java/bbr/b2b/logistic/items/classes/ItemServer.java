package bbr.b2b.logistic.items.classes;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.items.data.wrappers.ItemW;
import bbr.b2b.logistic.items.entities.Item;
import bbr.b2b.logistic.utils.ClassUtilities;
import bbr.b2b.logistic.vev.report.classes.AvailableStockSendDataWSDTO;
import bbr.b2b.logistic.vev.report.classes.VeVUploadErrorDTO;

@Stateless(name = "servers/items/ItemServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemServer extends LogisticElementServer<Item, ItemW> implements ItemServerLocal {


	public ItemW addItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemW) addElement(item);
	}
	public ItemW[] getItems() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemW[]) getIdentifiables();
	}
	public ItemW updateItem(ItemW item) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemW) updateElement(item);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Item entity, ItemW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Item entity, ItemW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Item";
	}
	@Override
	protected void setEntityname() {
		entityname = "Item";
	}
	
	
	public int doLoadCSV(String file) throws IOException {
		String[] linesDataArray = ClassUtilities.getLineDataArrayFromCSV(file, Charset.defaultCharset());
		List<String> list = new ArrayList<>(Arrays.asList(linesDataArray));
		//se revisa si la fila viene vacía -> tiene un solo dato(num linea), y se quita del array
		List<String> listRemoved = list.stream().filter(d -> d.length() > 3).collect(Collectors.toList());
		List<String> listLined = listRemoved.stream()
				.map(d -> Integer.sum(1, listRemoved.indexOf(d)+1) + "," + d)
				.collect(Collectors.toList());
		String[] linedDataArray = listLined.stream().toArray(String[]::new);

		if(listRemoved.isEmpty()){
			return 0;
		}
		
		String columns = "line, warehouse, sku, description, availablestock, dailystock";
		String[] columnsArray = columns.split(",");
		String tableName = "stockupload";
		String SQL = ClassUtilities.getQueryElementsTempTable(tableName, columnsArray, linedDataArray);
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		int total = query.executeUpdate(); 
		return total;
	}
	
	public void doCreateTempTableStock() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.Item.doCreateTempTableStock");
		query.executeUpdate();
	}

	public void doCreateTempTableError() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.Item.doCreateTempTableError");
		query.executeUpdate();
	}
	
	public void doCheckDocumentData() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.Item.doCheckDocumentData");
		query.executeUpdate();
	}
	
	public int doCleanErrorRowsFromStockUpload() {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.items.entities.Item.doCleanErrorRowsFromStockUpload");
		int total = query.executeUpdate();
		return total;
	}
	
	public VeVUploadErrorDTO[] getErrorsOfStockUpload() {
		String SQL =
				"SELECT " + //
					"line, " + //
					"error " + //
				"FROM " + //
					"uploaderror " + //
				"ORDER BY " + //
					"line";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(VeVUploadErrorDTO.class));
		List<?> list = query.list();
		VeVUploadErrorDTO[] result = (VeVUploadErrorDTO[]) list.toArray(new VeVUploadErrorDTO[list.size()]);
		return result;
	}
	
		
	public AvailableStockSendDataWSDTO[] getValidStockUpload() {
		String SQL =
				"SELECT " + //
					"sku AS itemcode, " + //
					"dailystock AS dailyrep, " + //
					"availablestock AS stockdisp, " + //
					"warehouse " + //
				"FROM " + //
					"stockupload"; //
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(AvailableStockSendDataWSDTO.class));
		List<?> list = query.list();
		AvailableStockSendDataWSDTO[] result = (AvailableStockSendDataWSDTO[]) list.toArray(new AvailableStockSendDataWSDTO[list.size()]);
		return result;
	}	
	
	public ItemW[] getItemByIds(Long[] itemids) throws NotFoundException, OperationFailedException {
		
		
		ItemW[] result = null;
		List<ItemW> resultList = new ArrayList<ItemW>();
		
		List<Long> itemidsList = Arrays.asList(itemids);
		String queryStr = 	"select it from Item as it " + // 
							"where " + //
							"it.id in (:itemids) ";
							 
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("it.id ", "itemids", itemidsList);
		
		resultList = getByQuery(queryStr, properties);
		result =(ItemW[]) resultList.toArray(new ItemW[resultList.size()]);
		return result;
		
		
	}
	
}
