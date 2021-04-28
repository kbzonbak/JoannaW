package bbr.b2b.regional.logistic.datings.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.datings.data.wrappers.DockTypeW;
import bbr.b2b.regional.logistic.datings.entities.DockType;
import bbr.b2b.regional.logistic.datings.report.classes.DockTypeDTO;
import bbr.b2b.regional.logistic.deliveries.report.classes.ProposedPackingListDataDTO;

@Stateless(name = "servers/datings/DockTypeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DockTypeServer extends LogisticElementServer<DockType, DockTypeW> implements DockTypeServerLocal {

	public DockTypeW addDockType(DockTypeW docktype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockTypeW) addElement(docktype);
	}
	public DockTypeW[] getDockTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockTypeW[]) getIdentifiables();
	}
	public DockTypeW updateDockType(DockTypeW docktype) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DockTypeW) updateElement(docktype);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DockType entity, DockTypeW wrapper) {
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DockType entity, DockTypeW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DockType";
	}
	@Override
	protected void setEntityname() {
		entityname = "DockType";
	}
	
	public DockTypeDTO[] getDockTypesByLocation(Long locationid) {
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.datings.entities.DockType.getDockTypesByLocation");
		query.setLong("locationid", locationid);
		query.setResultTransformer(new LowerCaseResultTransformer(DockTypeDTO.class));
		
		List list = query.list();
		DockTypeDTO[] result = (DockTypeDTO[]) list.toArray(new DockTypeDTO[list.size()]);
		return result;	
	}
}