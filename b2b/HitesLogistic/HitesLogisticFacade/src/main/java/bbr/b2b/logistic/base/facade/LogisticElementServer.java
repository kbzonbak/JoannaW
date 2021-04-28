package bbr.b2b.logistic.base.facade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import javax.persistence.LockModeType;
import javax.persistence.Query;

import bbr.b2b.common.adtclasses.classes.ElementDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElement;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.common.factories.BeanExtenderFactory;

public abstract class LogisticElementServer<T extends IElement, W extends ElementDTO> extends BaseLogisticEJB3Server<T, Long, W> implements IElementServer<T, W> {

	@SuppressWarnings("unchecked")
	@Override
	protected void setTypes() {
		ParameterizedType genericType = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityBeanType = (Class<T>) genericType.getActualTypeArguments()[0];
		this.primaryKeyType = Long.class;
		this.wrapperType = (Class<W>) genericType.getActualTypeArguments()[1];
	}

	protected boolean isKeyGenerated() {
		return true;
	}

	public W addElement(W wrapper) throws OperationFailedException, NotFoundException {
		try {
			T newentity = entityBeanType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, newentity);
			copyRelationsWrapperToEntity(newentity, wrapper);
			// El identificador de las entidades independientes debe ser nulo si
			// es autogenerado
			if (isKeyGenerated()) {
				Method method = entityBeanType.getMethod("setId", primaryKeyType);
				method.invoke(newentity, (Object) null);
			}
			newentity = persist(newentity);
			W newwrapper = getWrapperByEntity(newentity);
			return newwrapper;
		} catch (InvocationTargetException e) {
			throw new OperationFailedException("No es posible invocar el método setId", e);
		} catch (NoSuchMethodException e) {
			throw new OperationFailedException("No existe método setId", e);
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase " + entityBeanType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	public W updateElement(W wrapper) throws OperationFailedException, NotFoundException {
		try {
			T entity = entityBeanType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, entity);
			copyRelationsWrapperToEntity(entity, wrapper);
			entity = merge(entity);
			W newwrapper = getWrapperByEntity(entity);
			return newwrapper;
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase " + entityBeanType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	public void deleteElement(Long id) throws OperationFailedException, NotFoundException {
		T entity = findById(id, LockModeType.READ);
		remove(entity);
	}
	
	@Override
	public int deleteElements(Long[] ids) throws OperationFailedException, NotFoundException, AccessDeniedException {
		// TODO DVI TEST
		if (ids == null || ids.length == 0)
			throw new AccessDeniedException("Se debe especificar al menos un ID para borrar");
		StringBuffer buffer = new StringBuffer("delete from " + entityname + " where id in (");
		for (int i = 0; i < ids.length; i++) {
			Long id = ids[i];
			buffer.append(id);
			if (i < (ids.length - 1))
				buffer.append(", ");
		}
		buffer.append(")");
		String queryStr = buffer.toString();
		Query query = getEntityManager().createQuery(queryStr);
		int result = query.executeUpdate();
		return result;
	}
	
	
}
