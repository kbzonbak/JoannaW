package bbr.b2b.logistic.ddcdeliveries.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcDeliveryStateW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDelivery;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryState;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcDeliveryStateType;
import bbr.b2b.logistic.ddcorders.report.classes.DdcOrderDeliveryStateDataDTO;

@Stateless(name = "servers/ddcdeliveries/DdcDeliveryStateServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcDeliveryStateServer extends LogisticElementServer<DdcDeliveryState, DdcDeliveryStateW> implements DdcDeliveryStateServerLocal {

	private Map<String, String> mapGetOrderDeliveryStateSQL = new HashMap<String, String>();
	{
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYNUMBER", "ddcd.number");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATETYPEWHEN", "ddcds.when1");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATETYPECODE", "ddcdst.code");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATETYPENAME", "ddcdst.name");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATEUSERNAME", "ddcds.username");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATEUSERTYPE", "ddcds.usertype");
		mapGetOrderDeliveryStateSQL.put("DDCDELIVERYSTATECOMMENT", "ddcdeliverystatecomment");
	}
	
	@EJB
	DdcDeliveryServerLocal ddcdeliveryserver;

	@EJB
	DdcDeliveryStateTypeServerLocal ddcdeliverystatetypeserver;

	public DdcDeliveryStateW addDdcDeliveryState(DdcDeliveryStateW ddcdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateW) addElement(ddcdeliverystate);
	}
	public DdcDeliveryStateW[] getDdcDeliveryStates() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateW[]) getIdentifiables();
	}
	public DdcDeliveryStateW updateDdcDeliveryState(DdcDeliveryStateW ddcdeliverystate) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcDeliveryStateW) updateElement(ddcdeliverystate);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcDeliveryState entity, DdcDeliveryStateW wrapper) {
		wrapper.setDdcdeliveryid(entity.getDdcdelivery() != null ? new Long(entity.getDdcdelivery().getId()) : new Long(0));
		wrapper.setDdcdeliverystatetypeid(entity.getDdcdeliverystatetype() != null ? new Long(entity.getDdcdeliverystatetype().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcDeliveryState entity, DdcDeliveryStateW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcdeliveryid() != null && wrapper.getDdcdeliveryid() > 0) { 
			DdcDelivery ddcdelivery = ddcdeliveryserver.getReferenceById(wrapper.getDdcdeliveryid());
			if (ddcdelivery != null) { 
				entity.setDdcdelivery(ddcdelivery);
			}
		}
		if (wrapper.getDdcdeliverystatetypeid() != null && wrapper.getDdcdeliverystatetypeid() > 0) { 
			DdcDeliveryStateType ddcdeliverystatetype = ddcdeliverystatetypeserver.getReferenceById(wrapper.getDdcdeliverystatetypeid());
			if (ddcdeliverystatetype != null) { 
				entity.setDdcdeliverystatetype(ddcdeliverystatetype);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcDeliveryState";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcDeliveryState";
	}
	
	public DdcOrderDeliveryStateDataDTO[] getDdcOrderDeliveryStateByDdcOrder(Long ddcorderid, Long vendorid,  int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException {
		
		String SQL =
				"SELECT " + //
					"ddcd.id AS ddcdeliveryid, " + //
					"ddcd.number AS ddcdeliverynumber, " + //
					"ddcds.id AS ddcdeliverystateid, " + //
					"ddcds.when1 AS ddcdeliverystatetypewhen, " + //
					"ddcdst.id AS ddcdeliverystatetypeid, " + //
					"ddcdst.code AS ddcdeliverystatetypecode, " + //
					"ddcdst.name AS ddcdeliverystatetypename, " + //
					"ddcds.username AS ddcdeliverystateusername, " + //
					"ddcds.usertype AS ddcdeliverystateusertype, " + //
					"COALESCE(ddcds.comment, '') AS ddcdeliverystatecomment " + //
				"FROM " + //
					"logistica.ddcdelivery AS ddcd JOIN " + //
					"logistica.ddcdeliverystate AS ddcds ON ddcds.ddcdelivery_id = ddcd.id JOIN " + //
					"logistica.ddcdeliverystatetype AS ddcdst ON ddcdst.id = ddcds.ddcdeliverystatetype_id " + //
				"WHERE " + //
					"ddcd.ddcorder_id = :ddcorderid AND ddcd.vendor_id = :vendorid " + //
				getOrderByString(mapGetOrderDeliveryStateSQL, orderby);

		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("ddcorderid", ddcorderid);
		query.setLong("vendorid", vendorid);
		
		query.setResultTransformer(new LowerCaseResultTransformer(DdcOrderDeliveryStateDataDTO.class));
		
		if (ispaginated) {//si quiero el reporte paginado
			query.setFirstResult((pagenumber-1) * rows);
			query.setMaxResults(rows);
		}
		
		List<?> list = query.list();
		DdcOrderDeliveryStateDataDTO[] result = (DdcOrderDeliveryStateDataDTO[]) list.toArray(new DdcOrderDeliveryStateDataDTO[list.size()]);		
		
		return result;	
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
