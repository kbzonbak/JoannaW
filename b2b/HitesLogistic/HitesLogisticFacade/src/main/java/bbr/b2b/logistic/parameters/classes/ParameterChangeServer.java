package bbr.b2b.logistic.parameters.classes;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.parameters.entities.Parameter;
import bbr.b2b.logistic.parameters.entities.ParameterChange;
import bbr.b2b.logistic.parameters.data.wrappers.ParameterChangeW;

@Stateless(name = "servers/parameters/ParameterChangeServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ParameterChangeServer extends LogisticElementServer<ParameterChange, ParameterChangeW> implements ParameterChangeServerLocal {


	@EJB
	ParameterServerLocal parameterserver;

	public ParameterChangeW addParameterChange(ParameterChangeW parameterchange) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterChangeW) addElement(parameterchange);
	}
	public ParameterChangeW[] getParameterChanges() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterChangeW[]) getIdentifiables();
	}
	public ParameterChangeW updateParameterChange(ParameterChangeW parameterchange) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ParameterChangeW) updateElement(parameterchange);
	}

	@Override
	protected void copyRelationsEntityToWrapper(ParameterChange entity, ParameterChangeW wrapper) {
		wrapper.setParameterid(entity.getParameter() != null ? new Long(entity.getParameter().getId()) : new Long(0));
	}
	@Override
	protected void copyRelationsWrapperToEntity(ParameterChange entity, ParameterChangeW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getParameterid() != null && wrapper.getParameterid() > 0) { 
			Parameter parameter = parameterserver.getReferenceById(wrapper.getParameterid());
			if (parameter != null) { 
				entity.setParameter(parameter);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "ParameterChange";
	}
	@Override
	protected void setEntityname() {
		entityname = "ParameterChange";
	}
}
