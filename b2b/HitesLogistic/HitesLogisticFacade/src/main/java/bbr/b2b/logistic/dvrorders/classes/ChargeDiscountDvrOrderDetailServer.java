package bbr.b2b.logistic.dvrorders.classes;

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
import bbr.b2b.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderDetailW;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscount;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderDetail;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetail;
import bbr.b2b.logistic.dvrorders.entities.DvrOrderDetailId;
import bbr.b2b.logistic.dvrorders.report.classes.ChargeDiscountDataByDvrOrderDetailDTO;

@Stateless(name = "servers/dvrorders/ChargeDiscountDvrOrderDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChargeDiscountDvrOrderDetailServer extends BaseLogisticEJB3Server<ChargeDiscountDvrOrderDetail, ChargeDiscountDvrOrderDetailId, ChargeDiscountDvrOrderDetailW> implements ChargeDiscountDvrOrderDetailServerLocal {


	@EJB
	ChargeDiscountServerLocal chargediscountserver;

	@EJB
	DvrOrderDetailServerLocal dvrorderdetailserver;

	public ChargeDiscountDvrOrderDetailW addChargeDiscountDvrOrderDetail(ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderDetailW) addIdentifiable(chargediscountdvrorderdetail);
	}
	public ChargeDiscountDvrOrderDetailW[] getChargeDiscountDvrOrderDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderDetailW[]) getIdentifiables();
	}
	public ChargeDiscountDvrOrderDetailW updateChargeDiscountDvrOrderDetail(ChargeDiscountDvrOrderDetailW chargediscountdvrorderdetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderDetailW) updateIdentifiable(chargediscountdvrorderdetail);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ChargeDiscountDvrOrderDetail entity, ChargeDiscountDvrOrderDetailW wrapper) {
		wrapper.setChargediscountid(entity.getChargediscount() != null ? new Long(entity.getChargediscount().getId()) : new Long(0));
		wrapper.setDvrorderid(entity.getDvrorderdetail().getId() != null ? new Long(entity.getDvrorderdetail().getId().getDvrorderid()) : new Long(0));
		wrapper.setItemid(entity.getDvrorderdetail().getId() != null ? new Long(entity.getDvrorderdetail().getId().getItemid()) : new Long(0));
		wrapper.setPosition(entity.getDvrorderdetail().getId() != null ? new Integer(entity.getDvrorderdetail().getId().getPosition()) : new Integer(0));

	}
	@Override
	protected void copyRelationsWrapperToEntity(ChargeDiscountDvrOrderDetail entity, ChargeDiscountDvrOrderDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getChargediscountid() != null && wrapper.getChargediscountid() > 0) { 
			ChargeDiscount chargediscount = chargediscountserver.getReferenceById(wrapper.getChargediscountid());
			if (chargediscount != null) { 
				entity.setChargediscount(chargediscount);
			}
		}
		if ((wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) && (wrapper.getItemid() != null && wrapper.getItemid() > 0) && (wrapper.getPosition() != null && wrapper.getPosition() > 0)) {
			DvrOrderDetailId key = new DvrOrderDetailId();
			key.setDvrorderid(wrapper.getDvrorderid());
			key.setItemid(wrapper.getItemid());
			key.setPosition(wrapper.getPosition());
			DvrOrderDetail var = dvrorderdetailserver.getReferenceById(key);
			if (var != null) { 
				entity.setDvrorderdetail(var);
			}
		}
	}

	
	// Obtiene todos los cargos y descuentos asociados a una ´linea de DVR OrderDetail
	public ChargeDiscountDataByDvrOrderDetailDTO[] getChargeDiscountDataFromOrderDetail(Long dvrorderid, Long itemid, Integer position){
		
		String SQL = 	"SELECT " + //
						"	cd.charge, " + //
						"	cd.description, " + //
						"	cd.visualorder, " + //
						"	cd.ispercentage AS percentage, " + //
						"	cd.value, " + //
						"	cdod.dvrorder_id as dvrorderid, " + //
						"	cdod.item_id as itemid, " + //
						"	cdod.position " + //
		
						"from logistica.chargediscountdvrorderdetail as cdod " + //
						"join logistica.chargediscount as cd " + //
						"ON cd.id = cdod.chargediscount_id " + //
						"JOIN logistica.dvrorderdetail AS dvrod " + //
						"ON dvrod.dvrorder_id = cdod.dvrorder_id " + //
						"AND dvrod.item_id = cdod.item_id " + // 
						"AND dvrod.position = cdod.position " + //
			    	    "WHERE dvrod.dvrorder_id = :dvrorderid " + //
					    "AND cdod.item_id = :itemid " + //
					    "AND cdod.position = :position " + //
					    "ORDER BY visualorder "; 
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dvrorderid", dvrorderid);
		query.setLong("itemid", itemid);		
		query.setInteger("position", position);
		query.setResultTransformer(new LowerCaseResultTransformer(ChargeDiscountDataByDvrOrderDetailDTO.class));
		
		List<?> list = query.list();
		ChargeDiscountDataByDvrOrderDetailDTO[] result = (ChargeDiscountDataByDvrOrderDetailDTO[]) list.toArray(new ChargeDiscountDataByDvrOrderDetailDTO[list.size()]);		
		return result;	
		
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ChargeDiscountDvrOrderDetail";
	}
	@Override
	protected void setEntityname() {
		entityname = "ChargeDiscountDvrOrderDetail";
	}
}
