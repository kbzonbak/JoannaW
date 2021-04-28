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
import bbr.b2b.logistic.dvrorders.data.wrappers.ChargeDiscountDvrOrderW;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscount;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrder;
import bbr.b2b.logistic.dvrorders.entities.ChargeDiscountDvrOrderId;
import bbr.b2b.logistic.dvrorders.entities.DvrOrder;
import bbr.b2b.logistic.dvrorders.report.classes.DiscountByOrderDataDTO;

@Stateless(name = "servers/dvrorders/ChargeDiscountDvrOrderServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ChargeDiscountDvrOrderServer extends BaseLogisticEJB3Server<ChargeDiscountDvrOrder, ChargeDiscountDvrOrderId, ChargeDiscountDvrOrderW> implements ChargeDiscountDvrOrderServerLocal {


	@EJB
	ChargeDiscountServerLocal chargediscountserver;

	@EJB
	DvrOrderServerLocal dvrorderserver;

	public ChargeDiscountDvrOrderW addChargeDiscountDvrOrder(ChargeDiscountDvrOrderW chargediscountdvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderW) addIdentifiable(chargediscountdvrorder);
	}
	public ChargeDiscountDvrOrderW[] getChargeDiscountDvrOrders() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderW[]) getIdentifiables();
	}
	public ChargeDiscountDvrOrderW updateChargeDiscountDvrOrder(ChargeDiscountDvrOrderW chargediscountdvrorder) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ChargeDiscountDvrOrderW) updateIdentifiable(chargediscountdvrorder);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ChargeDiscountDvrOrder entity, ChargeDiscountDvrOrderW wrapper) {
		wrapper.setChargediscountid(entity.getChargediscount() != null ? new Long(entity.getChargediscount().getId()) : new Long(0));
		wrapper.setDvrorderid(entity.getDvrorder() != null ? new Long(entity.getDvrorder().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ChargeDiscountDvrOrder entity, ChargeDiscountDvrOrderW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getChargediscountid() != null && wrapper.getChargediscountid() > 0) { 
			ChargeDiscount chargediscount = chargediscountserver.getReferenceById(wrapper.getChargediscountid());
			if (chargediscount != null) { 
				entity.setChargediscount(chargediscount);
			}
		}
		if (wrapper.getDvrorderid() != null && wrapper.getDvrorderid() > 0) { 
			DvrOrder dvrorder = dvrorderserver.getReferenceById(wrapper.getDvrorderid());
			if (dvrorder != null) { 
				entity.setDvrorder(dvrorder);
			}
		}
	}

	
	public DiscountByOrderDataDTO[] getDiscountDataFromOrder(Long dvrorderid, Long vendorid) {
		
		String SQL = 	"SELECT " + //
						"	cd.visualorder, " + //
						"	cd.ispercentage as percentage, " + //
						"	cd.description, " + //
						"	cd.value " + //
						"FROM logistica.chargediscountdvrorder as cdoc " + //
						"join logistica.dvrorder as dvroc " + //  
						"ON cdoc.dvrorder_id = dvroc.id " + //
						"join logistica.order as oc " + //
						"ON oc.id = dvroc.id " + //
						"join logistica.chargediscount as cd " + //
						"ON cd.id = cdoc.chargediscount_id " + //
						
						"WHERE dvroc.id = :dvrorderid " + //
						"AND oc.vendor_id = :vendorid " + //			    
						"ORDER BY cd.visualorder "; 
	
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("dvrorderid", dvrorderid);
		query.setLong("vendorid", vendorid);		
		query.setResultTransformer(new LowerCaseResultTransformer(DiscountByOrderDataDTO.class));
		
		List<?> list = query.list();
		DiscountByOrderDataDTO[] result = (DiscountByOrderDataDTO[]) list.toArray(new DiscountByOrderDataDTO[list.size()]);		
		return result;	
			
	}

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "ChargeDiscountDvrOrder";
	}
	@Override
	protected void setEntityname() {
		entityname = "ChargeDiscountDvrOrder";
	}
}
