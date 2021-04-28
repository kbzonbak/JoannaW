package bbr.b2b.regional.logistic.buyorders.classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.ItemAtcW;
import bbr.b2b.regional.logistic.buyorders.entities.Atc;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtc;
import bbr.b2b.regional.logistic.buyorders.entities.ItemAtcId;
import bbr.b2b.regional.logistic.buyorders.report.classes.ItemAtcDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;

@Stateless(name = "servers/buyorders/ItemAtcServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemAtcServer extends BaseLogisticEJB3Server<ItemAtc, ItemAtcId, ItemAtcW> implements ItemAtcServerLocal {
	
	@EJB
	AtcServerLocal atcserver;

	@EJB
	ItemServerLocal itemserver;

	@Override
	protected void copyRelationsEntityToWrapper(ItemAtc entity, ItemAtcW wrapper) {
		wrapper.setAtcid(entity.getAtc() != null ? new Long(entity.getAtc().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(ItemAtc entity, ItemAtcW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getAtcid() != null && wrapper.getAtcid() > 0) {
			Atc atc = atcserver.getReferenceById(wrapper.getAtcid());
			if (atc != null) {
				entity.setAtc(atc);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) {
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) {
				entity.setItem(item);
			}
		}
	}
	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ItemAtc";
	}

	@Override
	protected void setEntityname() {
		entityname = "ItemAtc";
	}
	
	public ItemAtcW addItemAtc(ItemAtcW itematc) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAtcW) addIdentifiable(itematc);
	}
	
	public ItemAtcW[] getItemAtcs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAtcW[]) getIdentifiables();
	}
	
	public ItemAtcW updateItemAtc(ItemAtcW itematc) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ItemAtcW) updateIdentifiable(itematc);
	}
	
	public List<ItemAtcDTO> getItemDataByATCCodes(List<String> atccodes) {
		
		String SQL =
			"SELECT DISTINCT " + //
				"atc.id AS atcid, " + //
				"atc.code AS atccode, " + //
				"it.id AS itemid, " + //
				"it.internalcode AS itemsku, " + //
				"it.flowtype_id AS flowtypeid, " + //
				"ia.curve AS curve " + //
			"FROM " + //
				"logistica.atc AS atc JOIN " + //
				"logistica.item_atc AS ia ON ia.atc_id = atc.id JOIN " + //
				"logistica.item AS it ON it.id = ia.item_id " + //				
			"WHERE " + //
				"atc.code IN (:atccodes)";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setParameterList("atccodes", atccodes);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemAtcDTO.class));
				
		return (List<ItemAtcDTO>)query.list();
	}
	
	public List<ItemAtcDTO> getItemDataByOrderAndATCCodes(Long orderid, List<String> atccodes) {
		
		String SQL =
			"SELECT DISTINCT " + //
				"atc.id AS atcid, " + //
				"atc.code AS atccode, " + //
				"it.id AS itemid, " + //
				"it.internalcode AS itemsku, " + //
				"it.flowtype_id AS flowtypeid, " + //
				"ia.curve AS curve " + //
			"FROM " + //
				"logistica.orderdetail AS od JOIN " + //
				"logistica.item AS it ON it.id = od.item_id JOIN " + //
				"logistica.item_atc AS ia ON ia.item_id = it.id JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id " + //	
			"WHERE " + //
				"od.order_id = :orderid AND od.hasatc IS TRUE AND atc.code IN (:atccodes)";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setParameterList("atccodes", atccodes);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemAtcDTO.class));
				
		return (List<ItemAtcDTO>)query.list();
	}
	
	public List<ItemAtcDTO> getItemDataByOrderAndSKUs(Long orderid, List<String> itemcodes) {
		
		String SQL =
			"SELECT " + //
				"atc.id AS atcid, " + //
				"atc.code AS atccode, " + //
				"it.id AS itemid, " + //
				"it.internalcode AS itemsku, " + //
				"it.flowtype_id AS flowtypeid, " + //
				"ia.curve AS curve " + //
			"FROM " + //
				"logistica.atc AS atc JOIN " + //
				"logistica.item_atc AS ia ON ia.atc_id = atc.id JOIN " + //
				"logistica.item AS it ON it.id = ia.item_id " + //
			"WHERE " + //
				"atc.id IN (SELECT DISTINCT " + //
								"ia.atc_id " + //
							"FROM " + //
								"logistica.orderdetail AS od JOIN " + //
								"logistica.item AS it ON it.id = od.item_id JOIN " + //
								"logistica.item_atc AS ia ON ia.item_id = it.id " + //
							"WHERE " + //
								"od.order_id = :orderid AND od.hasatc IS TRUE AND it.internalcode IN (:itemcodes))";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("orderid", orderid);
		query.setParameterList("itemcodes", itemcodes);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemAtcDTO.class));
				
		return (List<ItemAtcDTO>)query.list();
	}
	
	public List<ItemAtcDTO> getItemDataByDelivery(Long deliveryid) {
		
		String SQL =
			"SELECT DISTINCT " + //
				"atc.id AS atcid, " + //
				"atc.code AS atccode, " + //
				"it.id AS itemid, " + //
				"it.internalcode AS itemsku, " + //
				"it.flowtype_id AS flowtypeid, " + //
				"ia.curve AS curve " + //
			"FROM " + //
				"logistica.orderdelivery AS odl JOIN " + //
				"logistica.orderdetail AS od ON od.order_id = odl.order_id JOIN " + //
				"logistica.item AS it ON it.id = od.item_id JOIN " + //
				"logistica.item_atc AS ia ON ia.item_id = it.id JOIN " + //
				"logistica.atc AS atc ON atc.id = ia.atc_id " + //
			"WHERE " + //
				"odl.delivery_id = :deliveryid AND od.hasatc IS TRUE ";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("deliveryid", deliveryid);
		query.setResultTransformer(new LowerCaseResultTransformer(ItemAtcDTO.class));
				
		return (List<ItemAtcDTO>)query.list();
	}
	
	public List<Long> getItemIdsByATCCode(String atccode) {
		
		String SQL =
			"SELECT " + //
				"ia.item_id " + //
			"FROM " + //
				"logistica.atc AS atc JOIN " + //
				"logistica.item_atc AS ia ON ia.atc_id = atc.id " + //
			"WHERE " + //
				"atc.code = :atccode";

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("atccode", atccode);
						
		List<Long> result = new ArrayList<Long>();
		for (BigInteger data : (List<BigInteger>)query.list()) {
			result.add(data.longValue());
		}
		return result;
	}
	
}
