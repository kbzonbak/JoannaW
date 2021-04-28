package bbr.b2b.regional.logistic.items.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.PropertyInfoDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.exceptions.ServiceException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.deliveries.report.classes.PendingItemDTO;
import bbr.b2b.regional.logistic.items.data.wrappers.ItemW;
import bbr.b2b.regional.logistic.items.entities.FlowType;
import bbr.b2b.regional.logistic.items.entities.Item;
import bbr.b2b.regional.logistic.items.entities.ItemClass;
import bbr.b2b.regional.logistic.items.entities.Unit;
import bbr.b2b.regional.logistic.items.report.classes.VendorItemDataW;

@Stateless(name = "servers/items/ItemServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemServer extends LogisticElementServer<Item, ItemW> implements ItemServerLocal {

	@EJB
	FlowTypeServerLocal flowtypeserver;

	@EJB
	ItemClassServerLocal itemclassserver;

	@EJB
	UnitServerLocal unitserver;

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
		wrapper.setFlowtypeid(entity.getFlowtype() != null ? new Long(entity.getFlowtype().getId()) : new Long(0));
		wrapper.setItemclassid(entity.getItemclass() != null ? new Long(entity.getItemclass().getId()) : new Long(0));
		wrapper.setUnitid(entity.getUnit() != null ? new Long(entity.getUnit().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(Item entity, ItemW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getFlowtypeid() != null && wrapper.getFlowtypeid() > 0) { 
			FlowType flowtype = flowtypeserver.getReferenceById(wrapper.getFlowtypeid());
			if (flowtype != null) { 
				entity.setFlowtype(flowtype);
			}
		}
		if (wrapper.getItemclassid() != null && wrapper.getItemclassid() > 0) { 
			ItemClass itemclass = itemclassserver.getReferenceById(wrapper.getItemclassid());
			if (itemclass != null) { 
				entity.setItemclass(itemclass);
			}
		}
		if (wrapper.getUnitid() != null && wrapper.getUnitid() > 0) { 
			Unit unit = unitserver.getReferenceById(wrapper.getUnitid());
			if (unit != null) { 
				entity.setUnit(unit);
			}
		}
	}
	
	public ItemW[] getItemsOfOrder(Long ordernumber) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"it.id, it.name, it.barcode, it.internalcode, it.ean1, it.ean2, it.ean3, it.trademark, it.color, it.dimension, it.size, " + //
				"it.innerpack, it.casepack, it.itemclass_id AS itemclassid, it.unit_id AS unitid, it.flowtype_id AS flowtypeid " + //
			"FROM " + //
				"logistica.item AS it JOIN " + //
				"logistica.orderdetail AS od ON od.item_id = it.id JOIN " + //
				"logistica.order AS o ON o.id = od.order_id " + //
			"WHERE " + //
				"o.number = :ordernumber";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ordernumber", ordernumber);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemW.class));

		List list = query.list();
		return (ItemW[]) list.toArray(new ItemW[list.size()]);
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Item";
	}
	@Override
	protected void setEntityname() {
		entityname = "Item";
	}
	
	public VendorItemDataW[] getItemsOfVendorAndVeV(Long vendorid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT " + //
				"it.id, " + //
				"it.name, " + //
				"it.barcode, " + //
				"it.internalcode, " + //
				"it.ean1, " + //
				"it.ean2, " + //
				"it.ean3, " + //
				"it.trademark, " + //
				"it.color, " + //
				"it.dimension, " + //
				"it.size, " + //
				"it.innerpack, " + //
				"it.casepack, " + //
				"it.vev, " + //
				"it.itemclass_id AS itemclassid, " + //
				"it.unit_id AS unitid, " + //
				"it.flowtype_id AS flowtypeid, " + //
				"vi.directdelivery, " + //
				"vi.vendoritemcode " + //
			"FROM " + //
				"logistica.item AS it JOIN " + //
				"logistica.vendoritem AS vi ON vi.item_id = it.id " +
			"WHERE " + //
				"vi.vendor_id = :vendorid AND it.vev IS TRUE";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("vendorid", vendorid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataW.class));

		List list = query.list();
		return (VendorItemDataW[]) list.toArray(new VendorItemDataW[list.size()]);
	}
	
	public List<String> getDeliveryItemsByOrders(Long[] orderids) {
		
		String SQL =
			"(SELECT DISTINCT " + //
				"i.internalcode " + //
			"FROM " + //
				"logistica.order AS o JOIN " + //
				"logistica.location AS dll ON dll.id = o.deliverylocation_id JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = o.id JOIN " + //
				"logistica.item AS i ON i.id = od.item_id " + //
			"WHERE " + //
				"o.id IN (:orderids) AND dll.code = '200') " + //
			"UNION " + //
			"(SELECT DISTINCT " + //
				"i.internalcode " + //
			"FROM " + //
				"logistica.order AS o JOIN " + //
				"logistica.location AS dll ON dll.id = o.deliverylocation_id JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = o.id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = od.order_id AND pdd.item_id = od.item_id JOIN " + //
				"logistica.location AS dsl ON dsl.id = pdd.location_id JOIN " + //
				"logistica.item AS i ON i.id = pdd.item_id " + //
			"WHERE " + //
				"o.id IN (:orderids) AND dll.code = '12' AND (dsl.code = '200' OR dsl.code = '12'))";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("orderids", orderids);
						
		return query.list();
	}
	
	public PendingItemDTO[] getPendingItemsData(Long[] orderids, List<String> skus) {
		
		String SQL =
			"(SELECT DISTINCT " + //
				"o.number AS ordernumber, " + //
				"dll.code AS deliverylocationcode, " + //
				"dll.name AS deliverylocationname, " + //
				"dsl.code AS destinationlocationcode, " + //
				"dsl.name AS destinationlocationname, " + //
				"it.internalcode AS iteminternalcode, " + //
				"it.name AS itemname " + //
			"FROM " + //
				"logistica.order AS o JOIN " + //
				"logistica.location AS dll ON dll.id = o.deliverylocation_id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = o.id JOIN " + //
				"logistica.location AS dsl ON dsl.id = pdd.location_id JOIN " + //
				"logistica.item AS it ON it.id = pdd.item_id " + //
			"WHERE " + //
				"o.id IN (:orderids) AND dll.code = '200' AND it.internalcode IN (:skus)) " + //
			"UNION " + //
			"(SELECT DISTINCT " + //
				"o.number AS ordernumber, " + //
				"dll.code AS deliverylocationcode, " + //
				"dll.name AS deliverylocationname, " + //
				"dsl.code AS destinationlocationcode, " + //
				"dsl.name AS destinationlocationname, " + //
				"it.internalcode AS iteminternalcode, " + //
				"it.name AS itemname " + //
			"FROM " + //
				"logistica.order AS o JOIN " + //
				"logistica.location AS dll ON dll.id = o.deliverylocation_id JOIN " + //
				"logistica.predeliverydetail AS pdd ON pdd.order_id = o.id JOIN " + //
				"logistica.location AS dsl ON dsl.id = pdd.location_id JOIN " + //
				"logistica.item AS it ON it.id = pdd.item_id " + //
			"WHERE " + //
				"o.id IN (:orderids) AND dll.code = '12' AND (dsl.code = '200' OR dsl.code = '12') AND it.internalcode IN (:skus)) " + //
			"ORDER BY " + //
				"ordernumber, deliverylocationcode, destinationlocationcode, iteminternalcode"; //

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("orderids", orderids);
		query.setParameterList("skus", skus);
		query.setResultTransformer(new LowerCaseResultTransformer(PendingItemDTO.class));

		List list = query.list();
		return (PendingItemDTO[]) list.toArray(new PendingItemDTO[list.size()]);
	}
	
	public ItemW[] getItemsByIds(Long[] ids) throws ServiceException {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.items.entities.Item.getItemsByIds");
		query.setParameterList("ids", ids);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemW.class));
		List list = query.list();
		ItemW[] result = (ItemW[]) list.toArray(new ItemW[list.size()]);
		return result;
	}
		
	public List<String> getBySKUs(List<String> codeList) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.items.entities.Item.getBySKUs");
		query.setParameterList("codelist", codeList);
		
		return (List<String>)query.list();
	}
	
	public VendorItemDataW[] getItemsByDelivery(Long deliveryid) throws OperationFailedException, NotFoundException {
		
		String SQL =
				"SELECT DISTINCT " + //
					"it.id, " + //
					"it.name, " + //
					"it.barcode, " + //
					"it.internalcode, " + //
					"it.ean1, " + //
					"it.ean2, " + //
					"it.ean3, " + //
					"it.trademark, " + //
					"it.color, " + //
					"it.dimension, " + //
					"it.size, " + //
					"it.innerpack, " + //
					"it.casepack, " + //
					"it.vev, " + //
					"it.itemclass_id AS itemclassid, " + //
					"it.unit_id AS unitid, " + //
					"it.flowtype_id AS flowtypeid, " + //
					"vi.directdelivery, " + //
					"vi.vendoritemcode " + //
				"FROM " + //
					"logistica.orderdeliverydetail AS odd JOIN " + //
					"logistica.order AS oc ON oc.id = odd.order_id JOIN " + //
					"logistica.item AS it ON it.id = odd.item_id JOIN " + //
					"logistica.vendoritem AS vi ON vi.item_id = it.id AND vi.vendor_id = oc.vendor_id " + //
				"WHERE " + //
					"odd.delivery_id = :deliveryid"; //
        
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(VendorItemDataW.class));

		List list = query.list();
		return (VendorItemDataW[]) list.toArray(new VendorItemDataW[list.size()]);
	}
	
	public ItemW[] getItemsByDirectOrder(Long directorderid) throws OperationFailedException, NotFoundException {
		StringBuilder sb = new StringBuilder(
				"SELECT " + //
					"item " + //
				"FROM " + //
					"Item AS item, " + //
					"DirectOrderDetail AS dod, " + //
					"DirectOrder AS do " + //
				"WHERE " + //
					"dod.item = item AND dod.directorder = do AND do.id = :directorderid");
		
		PropertyInfoDTO[] properties = new PropertyInfoDTO[1];
		properties[0] = new PropertyInfoDTO("do.id", "directorderid", directorderid);
		
		List list = getByQuery(sb.toString(), properties);
		
		return (ItemW[]) list.toArray(new ItemW[list.size()]);
	}
}
