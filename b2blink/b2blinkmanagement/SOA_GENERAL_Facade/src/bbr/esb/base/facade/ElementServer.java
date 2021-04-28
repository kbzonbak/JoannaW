package bbr.esb.base.facade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.LockModeType;
import javax.persistence.Query;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IElement;
import bbr.common.adtclasses.interfaces.IElementServer;
import bbr.common.factories.BeanExtenderFactory;

public abstract class ElementServer<T extends IElement, W extends ElementDTO> extends BaseEJB3Server<T, Long, W> implements IElementServer<T, W> {

	@Override
	public W addElement(W wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
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
			throw new OperationFailedException("No existe método setKey", e);
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase " + entityBeanType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	@Override
	public void deleteElement(Long key) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T entity = findById(key);
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

	@Override
	public List<T> findAll() {
		if (isCacheable()) {
			String cachename = this.entityname + ".cache";
			List<T> result = getEntityManager().createQuery("select model from " + entityname + " model order by model.id").setHint("org.hibernate.cacheable", true).setHint("org.hibernate.cacheRegion", cachename).getResultList();
			return result;
		} else
			return super.findAll();
	}

	@Override
	public T findById(Long id) throws OperationFailedException, NotFoundException {
		if (isCacheable()) {
			List<T> list = findAll();
			Optional<T> aelement = list.stream().filter(element -> element.getId().equals(id)).findFirst();
			if (aelement.isPresent())
				return aelement.get();
			else
				return null;
		} else
			return super.findById(id);
	}

	@Override
	public T findById(Long id, LockModeType lockmodetype) throws OperationFailedException, NotFoundException {
		if (isCacheable() && lockmodetype.equals(LockModeType.NONE))
			return findById(id);
		else
			return super.findById(id, lockmodetype);
	}

	private boolean getPredicate(T entity, String propertyName, Object value) {
		return BeanExtenderFactory.compareProperty(entity, propertyName, value);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		if (isCacheable()) {
			List<T> list = findAll();
			List<T> result = list.stream().filter(t -> getPredicate(t, propertyName, value)).collect(Collectors.toList());
			return result;
		} else
			return super.findByProperty(propertyName, value);
	}

	protected boolean isCacheable() {
		return false;
	}

	protected boolean isKeyGenerated() {
		return true;
	}

	@Override
	protected void setTypes() {
		ParameterizedType genericType = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityBeanType = (Class<T>) genericType.getActualTypeArguments()[0];
		this.primaryKeyType = Long.class;
		this.wrapperType = (Class<W>) genericType.getActualTypeArguments()[1];
	}

	@Override
	public W updateElement(W wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
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
}
