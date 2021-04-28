package bbr.b2b.logistic.buyorders.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.buyorders.entities.Section;
import bbr.b2b.logistic.buyorders.data.wrappers.SectionW;

@Stateless(name = "servers/buyorders/SectionServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SectionServer extends LogisticElementServer<Section, SectionW> implements SectionServerLocal {


	public SectionW addSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW) addElement(section);
	}
	public SectionW[] getSections() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW[]) getIdentifiables();
	}
	public SectionW updateSection(SectionW section) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (SectionW) updateElement(section);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Section entity, SectionW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Section entity, SectionW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Section";
	}
	@Override
	protected void setEntityname() {
		entityname = "Section";
	}
}
