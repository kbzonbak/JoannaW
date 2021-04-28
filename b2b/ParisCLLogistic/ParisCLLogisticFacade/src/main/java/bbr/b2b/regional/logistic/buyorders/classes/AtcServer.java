package bbr.b2b.regional.logistic.buyorders.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.buyorders.data.wrappers.AtcW;
import bbr.b2b.regional.logistic.buyorders.entities.Atc;

@Stateless(name = "servers/buyorders/AtcServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AtcServer extends LogisticElementServer<Atc, AtcW> implements AtcServerLocal {

	@Override
	protected void copyRelationsEntityToWrapper(Atc entity, AtcW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Atc entity, AtcW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Atc";
	}
	@Override
	protected void setEntityname() {
		entityname = "Atc";
	}
	
	public AtcW addAtc(AtcW atc) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (AtcW) addElement(atc);
	}
	
	public AtcW[] getAtcs() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (AtcW[]) getIdentifiables();
	}
	
	public AtcW updateAtc(AtcW atc) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (AtcW) updateElement(atc);
	}
	
	public List<String> getByATCCodes(List<String> codeList) {
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.buyorders.entities.Atc.getByATCCodes");
		query.setParameterList("codelist", codeList);
		
		return (List<String>)query.list();
	}
	
}
