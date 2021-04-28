package bbr.common.facade;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import bbr.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.common.adtclasses.classes.PageDTO;
import bbr.common.adtclasses.classes.PageInfoDTO;
import bbr.common.adtclasses.classes.PropertyInfoDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.common.adtclasses.interfaces.IGenericServer;
import bbr.common.factories.BeanExtenderFactory;

public abstract class GenericEJB3Server<T, ID extends Serializable, W extends Serializable> extends BaseServer implements IGenericServer<T, ID, W> {

	protected Class<T> entityBeanType;

	protected String entitylabel = null;

	protected String entityname = null;

	protected Class<ID> primaryKeyType;

	protected Class<W> wrapperType;

	public GenericEJB3Server() {
		setTypes();
		setEntityname();
		setEntitylabel();
		log.debug("GenericEJB3Server Instanciando Server de entidad : " + entityname + " (" + entitylabel + ")");
	}

	@Override
	public W addIdentifiable(W wrapper) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			T newentity = entityBeanType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, newentity);
			copyRelationsWrapperToEntity(newentity, wrapper);
			ID key = primaryKeyType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, key);
			// Verificar que no exista la entidad
			try {
				T oldentity = findById(key);
				if (oldentity != null)
					throw new AccessDeniedException("Ya existe un " + entitylabel + " con la llave primaria especificada");
			} catch (NotFoundException exc) {
			}
			Method method = entityBeanType.getMethod("setId", primaryKeyType);
			method.invoke(newentity, key);
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

	abstract protected void copyRelationsEntityToWrapper(T entity, W wrapper) throws OperationFailedException, NotFoundException;

	abstract protected void copyRelationsWrapperToEntity(T entity, W wrapper) throws OperationFailedException, NotFoundException;

	@Override
	public int count() throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		Long result = (Long) getSingleResult("select count(1) from " + entityname + " model");
		// En la práctica, la conversión es segura
		return result.intValue();
	}

	@Override
	public int countByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		StringBuffer buffer = new StringBuffer("select count(1) from " + entityname + " model where ");
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			buffer.append("model.");
			buffer.append(property.getName());
			buffer.append(" ");
			if (property.isUnaryOperator()) {
				buffer.append(property.getOperator());
			} else {
				buffer.append(property.getOperator());
				buffer.append(" :");
				buffer.append(property.getAlias());
			}
			if (i < (propertyinfos.length - 1))
				buffer.append(" and ");
		}
		String queryStr = buffer.toString();
		Query query = getEntityManager().createQuery(queryStr);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		Long result = (Long) query.getSingleResult();
		return result.intValue();
	}

	@Override
	public int countByProperty(String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		if (value != null) {
			final String queryString = "select count(1) from " + entityname + " model where model." + propertyName + " = :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			Long result = (Long) query.getSingleResult();
			return result.intValue();
		} else {
			final String queryString = "select count(1) from " + entityname + " model where model." + propertyName + " is null";
			Query query = getEntityManager().createQuery(queryString);
			Long result = (Long) query.getSingleResult();
			return result.intValue();
		}
	}

	@Override
	public int countByStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return countByStringProperty(propertyName, value, true);
	}

	@Override
	public int countByStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		value = iscasesensitive ? value : value.toUpperCase();
		final String queryString = iscasesensitive ? "select count(1) from " + entityname + " model where model." + propertyName + " = :propertyValue" : "select count(*) from " + entityname + " model where upper(model." + propertyName + ") = :propertyValue";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", value);
		return 0;
	}

	@Override
	public int countLikeStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return countLikeStringProperty(propertyName, value, true);
	}

	@Override
	public int countLikeStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		value = iscasesensitive ? value : value.toUpperCase();
		final String queryString = iscasesensitive ? "select count(1) from " + entityname + " model where model." + propertyName + " like :propertyValue" : "select count(*) from " + entityname + " model where upper(model." + propertyName + ") like :propertyValue";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", "%" + value + "%");
		return 0;
	}

	@Override
	public int deleteAll() throws OperationFailedException {
		// TODO DVI TEST
		final String queryString = "delete from " + entityname;
		Query query = getEntityManager().createQuery(queryString);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public int deleteByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		StringBuffer buffer = new StringBuffer("delete from " + entityname + " where ");
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			buffer.append(property.getName());
			buffer.append(" ");
			if (property.isUnaryOperator()) {
				buffer.append(property.getOperator());
			} else {
				buffer.append(property.getOperator());
				buffer.append(" :");
				buffer.append(property.getAlias());
			}
			if (i < (propertyinfos.length - 1))
				buffer.append(" and ");
		}
		String queryStr = buffer.toString();
		Query query = getEntityManager().createQuery(queryStr);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public int deleteByProperty(String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		if (value != null) {
			final String queryString = "delete from " + entityname + " where " + propertyName + " = :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			int result = query.executeUpdate();
			return result;
		} else {
			final String queryString = "delete from " + entityname + " where " + propertyName + " is null";
			Query query = getEntityManager().createQuery(queryString);
			int result = query.executeUpdate();
			return result;
		}
	}

	@Override
	public void deleteIdentifiable(Object ikey) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			ID key = primaryKeyType.newInstance();
			BeanExtenderFactory.copyProperties(ikey, key);
			T entity = findById(key);
			remove(entity);
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase " + primaryKeyType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	@Override
	public List<T> findAll() {
		// TODO DVI TEST
		List<T> result = getResultList("select model from " + entityname + " model order by model.id");
		return result;
	}

	@Override
	public List<T> findAll(int pagenumber, int rows) {
		// TODO DVI TEST
		List<T> result = getResultList(pagenumber, rows, "select model from " + entityname + " model order by model.id");
		return result;
	}

	@Override
	public T[] findAllAsArray() throws OperationFailedException {
		List<T> list = findAll();
		return getEntityArrayByCollection(list);
	}

	@Override
	public T[] findAllAsArray(int pagenumber, int rows) throws OperationFailedException {
		List<T> list = findAll(pagenumber, rows);
		return getEntityArrayByCollection(list);
	}

	@Override
	public T[] findAllAsArrayOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException {
		List<T> list = findAllOrdered(pagenumber, rows, criterias);
		return getEntityArrayByCollection(list);
	}

	@Override
	public T[] findAllAsArrayOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException {
		List<T> list = findAllOrdered(criterias);
		return getEntityArrayByCollection(list);
	}

	@Override
	public PageDTO<T> findAllAsArrayPage(int pagenumber, int rows) throws OperationFailedException, NotFoundException {
		List<T> list = findAll(pagenumber, rows);
		T[] entities = getEntityArrayByCollection(list);
		int count = count();
		PageInfoDTO info = new PageInfoDTO();
		info.setPagenumber(pagenumber);
		info.setRows(entities.length);
		info.setTotalpages((count % rows) != 0 ? (count / rows) + 1 : (count / rows));
		info.setTotalrows(count);
		PageDTO<T> page = new PageDTO<T>();
		page.setPageinfo(info);
		page.setValues(entities);
		return page;
	}

	@Override
	public List<T> findAllOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException {
		// TODO DVI TEST
		String order = getOrderCriteria(criterias);
		List<T> result = findByQuery(pagenumber, rows, "select model from " + entityname + " model order by " + order);
		return result;
	}

	@Override
	public List<T> findAllOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException {
		// TODO DVI TEST
		String order = getOrderCriteria(criterias);
		List<T> result = findByQuery("select model from " + entityname + " model order by " + order);
		return result;
	}

	@Override
	public T findById(ID id) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return findById(id, LockModeType.NONE);
	}

	@Override
	public T findById(ID id, LockModeType lockmodetype) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T result = getEntityManager().find(entityBeanType, id, lockmodetype);
		if (result == null)
			throw new NotFoundException("No existe " + entitylabel + " con la llave primaria especificada");
		return result;
	}

	@Override
	public T findByMaxId() throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T entity = (T) getSingleResult("select model from " + entityname + " model where model.id = (select max(o.id) from " + entityname + " o)");
		return entity;
	}

	@Override
	public T findByMinId() throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T entity = (T) getSingleResult("select model from " + entityname + " model where model.id = (select min(o.id) from " + entityname + " o)");
		return entity;
	}

	private List<T> findByProperties(boolean paginated, int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		StringBuffer buffer = new StringBuffer("select model from " + entityname + " model where ");
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			buffer.append("model.");
			buffer.append(property.getName());
			buffer.append(" ");
			if (property.isUnaryOperator()) {
				buffer.append(property.getOperator());
			} else {
				buffer.append(property.getOperator());
				buffer.append(" :");
				buffer.append(property.getAlias());
			}
			if (i < (propertyinfos.length - 1))
				buffer.append(" and ");
		}
		String queryStr = buffer.toString();
		Query query = getEntityManager().createQuery(queryStr);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		if (paginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findByProperties(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return findByProperties(true, pagenumber, rows, propertyinfos);
	}

	@Override
	public List<T> findByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return findByProperties(false, 0, 0, propertyinfos);
	}

	@Override
	public T[] findByPropertiesAsArray(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> list = findByProperties(propertyinfos);
		return getEntityArrayByCollection(list);
	}

	@Override
	public T[] findByPropertiesAsArrayOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> list = findByPropertiesOrdered(propertyinfos, criterias);
		return getEntityArrayByCollection(list);
	}

	private List<T> findByPropertiesOrdered(boolean paginated, int pagenumber, int rows, PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		String order = getOrderCriteria(criterias);
		StringBuffer buffer = new StringBuffer("select model from " + entityname + " model where ");
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			buffer.append("model.");
			buffer.append(property.getName());
			buffer.append(" ");
			if (property.isUnaryOperator()) {
				buffer.append(property.getOperator());
			} else {
				buffer.append(property.getOperator());
				buffer.append(" :");
				buffer.append(property.getAlias());
			}
			if (i < (propertyinfos.length - 1))
				buffer.append(" and ");
		}
		buffer.append(" order by " + order);
		String queryStr = buffer.toString();
		Query query = getEntityManager().createQuery(queryStr);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		if (paginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findByPropertiesOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return findByPropertiesOrdered(false, 0, 0, propertyinfos, criterias);
	}

	private List<T> findByProperty(boolean paginated, int pagenumber, int rows, String propertyName, Object value) {
		// TODO DVI TEST
		if (value != null) {
			final String queryString = "select model from " + entityname + " model where model." + propertyName + " = :propertyValue";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (paginated) {
				query.setFirstResult((pagenumber - 1) * rows);
				query.setMaxResults(rows);
			}
			return query.getResultList();
		} else {
			final String queryString = "select model from " + entityname + " model where model." + propertyName + " is null";
			Query query = getEntityManager().createQuery(queryString);
			if (paginated) {
				query.setFirstResult((pagenumber - 1) * rows);
				query.setMaxResults(rows);
			}
			return query.getResultList();
		}
	}

	@Override
	public List<T> findByProperty(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException {
		// TODO DVI TEST
		return findByProperty(true, pagenumber, rows, propertyName, value);
	}

	@Override
	public List<T> findByProperty(String propertyName, Object value) {
		// TODO DVI TEST
		return findByProperty(false, 0, 0, propertyName, value);
	}

	private T[] findByPropertyAsArray(boolean paginated, int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException {
		// TODO DVI TEST
		List<T> list = findByProperty(paginated, pagenumber, rows, propertyName, value);
		return getEntityArrayByCollection(list);
	}

	@Override
	public T[] findByPropertyAsArray(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException {
		// TODO DVI TEST
		return findByPropertyAsArray(true, pagenumber, rows, propertyName, value);
	}

	@Override
	public T[] findByPropertyAsArray(String propertyName, Object value) throws OperationFailedException {
		// TODO DVI TEST
		return findByPropertyAsArray(false, 0, 0, propertyName, value);
	}

	@Override
	public PageDTO<T> findByPropertyAsArrayPage(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> list = findByProperty(pagenumber, rows, propertyName, value);
		T[] entities = getEntityArrayByCollection(list);
		int count = countByProperty(propertyName, value);
		PageInfoDTO info = new PageInfoDTO();
		info.setPagenumber(pagenumber);
		info.setRows(entities.length);
		info.setTotalpages((count % rows) != 0 ? (count / rows) + 1 : (count / rows));
		info.setTotalrows(count);
		PageDTO<T> page = new PageDTO<T>();
		page.setPageinfo(info);
		page.setValues(entities);
		return page;
	}

	@Override
	public T findByPropertyAsSingleResult(String propertyName, Object value) throws OperationFailedException {
		// TODO DVI TEST
		final String queryString = "select model from " + entityname + " model where model." + propertyName + " = :propertyValue";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", value);
		try {
			T result = (T) query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			throw new OperationFailedException("No se encontró la entidad", e);
		} catch (NonUniqueResultException e) {
			throw new OperationFailedException("Existe más de una entidad con el atributo buscado", e);
		}
	}

	private List<T> findByPropertyOrdered(boolean paginated, int pagenumber, int rows, String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException {
		// TODO DVI TEST
		String order = getOrderCriteria(criterias);
		final String queryString = "select model from " + entityname + " model where model." + propertyName + "= :propertyValue order by " + order;
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", value);
		if (paginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findByPropertyOrdered(int pagenumber, int rows, String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException {
		// TODO DVI TEST
		return findByPropertyOrdered(true, pagenumber, rows, propertyName, value, criterias);
	}

	@Override
	public List<T> findByPropertyOrdered(String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException {
		// TODO DVI TEST
		return findByPropertyOrdered(false, 0, 0, propertyName, value, criterias);
	}

	@Override
	public List<T> findByQuery(int pagenumber, int rows, String querystr) throws OperationFailedException {
		// TODO DVI TEST
		List<T> result = getResultList(pagenumber, rows, querystr);
		return result;
	}

	@Override
	public List<T> findByQuery(int pagenumber, int rows, String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException {
		// TODO DVI TEST
		List<T> result = getResultList(pagenumber, rows, querystr, propertyinfos);
		return result;
	}

	@Override
	public List<T> findByQuery(String querystr) throws OperationFailedException {
		// TODO DVI TEST
		List<T> result = getResultList(querystr);
		return result;
	}

	@Override
	public List<T> findByQuery(String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException {
		// TODO DVI TEST
		List<T> result = getResultList(querystr, propertyinfos);
		return result;
	}

	private List<T> findByStringProperty(boolean paginated, int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		value = iscasesensitive ? value : value.toUpperCase();
		final String queryString = iscasesensitive ? "select model from " + entityname + " model where model." + propertyName + " = :propertyValue" : "select model from " + entityname + " model where upper(model." + propertyName + ") = :propertyValue";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", value);
		if (paginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findByStringProperty(int pagenumber, int rows, String propertyName, String value) throws OperationFailedException {
		// TODO DVI TEST
		return findByStringProperty(pagenumber, rows, propertyName, value, true);
	}

	@Override
	public List<T> findByStringProperty(int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		return findByStringProperty(true, pagenumber, rows, propertyName, value, iscasesensitive);
	}

	@Override
	public List<T> findByStringProperty(String propertyName, String value) throws OperationFailedException {
		// TODO DVI TEST
		return findByStringProperty(propertyName, value, true);
	}

	@Override
	public List<T> findByStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		return findByStringProperty(false, 0, 0, propertyName, value, iscasesensitive);
	}

	private List<T> findLikeStringProperty(boolean paginated, int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		value = iscasesensitive ? value : value.toUpperCase();
		final String queryString = iscasesensitive ? "select model from " + entityname + " model where model." + propertyName + " like :propertyValue" : "select model from " + entityname + " model where upper(model." + propertyName + ") like :propertyValue";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("propertyValue", "%" + value + "%");
		if (paginated) {
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}

	@Override
	public List<T> findLikeStringProperty(int pagenumber, int rows, String propertyName, String value) throws OperationFailedException {
		// TODO DVI TEST
		return findLikeStringProperty(pagenumber, rows, propertyName, value, true);
	}

	@Override
	public List<T> findLikeStringProperty(int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		return findLikeStringProperty(true, pagenumber, rows, propertyName, value, iscasesensitive);
	}

	@Override
	public List<T> findLikeStringProperty(String propertyName, String value) throws OperationFailedException {
		// TODO DVI TEST
		return findLikeStringProperty(propertyName, value, true);
	}

	@Override
	public List<T> findLikeStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException {
		// TODO DVI TEST
		return findLikeStringProperty(false, 0, 0, propertyName, value, iscasesensitive);
	}

	@Override
	public T[] findLikeStringPropertyAsArray(String propertyName, String value) throws OperationFailedException {
		// TODO DVI TEST
		List<T> list = findLikeStringProperty(propertyName, value);
		return getEntityArrayByCollection(list);
	}

	@Override
	public List<W> getAll() throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> allentities = findAll();
		List<W> result = getWrappersByEntities(allentities);
		return result;
	}

	@Override
	public List<W> getAll(int pagenumber, int rows) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> allentities = findAll(pagenumber, rows);
		List<W> result = getWrappersByEntities(allentities);
		return result;
	}

	@Override
	public W[] getAllAsArray() throws OperationFailedException, NotFoundException {
		List<W> list = getAll();
		return getWrapperArrayByCollection(list);
	}

	@Override
	public W[] getAllAsArray(int pagenumber, int rows) throws OperationFailedException, NotFoundException {
		List<W> list = getAll(pagenumber, rows);
		return getWrapperArrayByCollection(list);
	}

	@Override
	public W[] getAllAsArrayOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> list = getAllOrdered(pagenumber, rows, criterias);
		return getWrapperArrayByCollection(list);
	}

	@Override
	public W[] getAllAsArrayOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> list = getAllOrdered(criterias);
		return getWrapperArrayByCollection(list);
	}

	@Override
	public PageDTO<W> getAllAsArrayPage(int pagenumber, int rows) throws OperationFailedException, NotFoundException {
		List<T> list = findAll(pagenumber, rows);
		W[] wrappers = getWrapperArrayByEntities(list);
		int count = count();
		PageInfoDTO info = new PageInfoDTO();
		info.setPagenumber(pagenumber);
		info.setRows(wrappers.length);
		info.setTotalpages((count % rows) != 0 ? (count / rows) + 1 : (count / rows));
		info.setTotalrows(count);
		PageDTO<W> page = new PageDTO<W>();
		page.setPageinfo(info);
		page.setValues(wrappers);
		return page;
	}

	@Override
	public List<W> getAllOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> allentities = findAllOrdered(pagenumber, rows, criterias);
		List<W> result = getWrappersByEntities(allentities);
		return result;
	}

	@Override
	public List<W> getAllOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> allentities = findAllOrdered(criterias);
		List<W> result = getWrappersByEntities(allentities);
		return result;
	}

	@Override
	public W getById(ID id) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return getById(id, LockModeType.NONE);
	}

	@Override
	public W getById(ID id, LockModeType lockmodetype) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T entity = findById(id, lockmodetype);
		W wrapper = getWrapperByEntity(entity);
		return wrapper;
	}

	@Override
	public W getByMaxId() throws OperationFailedException, NotFoundException {
		T entity = findByMaxId();
		W wrapper = getWrapperByEntity(entity);
		return wrapper;
	}

	@Override
	public W getByMinId() throws OperationFailedException, NotFoundException {
		T entity = findByMinId();
		W wrapper = getWrapperByEntity(entity);
		return wrapper;
	}

	@Override
	public List<W> getByProperties(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByProperties(pagenumber, rows, propertyinfos);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public List<W> getByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByProperties(propertyinfos);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public W[] getByPropertiesAsArray(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> wrappers = getByProperties(propertyinfos);
		return getWrapperArrayByCollection(wrappers);
	}

	@Override
	public W[] getByPropertiesAsArrayOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> wrappers = getByPropertiesOrdered(propertyinfos, criterias);
		return getWrapperArrayByCollection(wrappers);
	}

	@Override
	public PageDTO<W> getByPropertiesAsArrayPage(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> list = findByProperties(pagenumber, rows, propertyinfos);
		W[] wrappers = getWrapperArrayByEntities(list);
		int count = countByProperties(propertyinfos);
		PageInfoDTO info = new PageInfoDTO();
		info.setPagenumber(pagenumber);
		info.setRows(wrappers.length);
		info.setTotalpages((count % rows) != 0 ? (count / rows) + 1 : (count / rows));
		info.setTotalrows(count);
		PageDTO<W> page = new PageDTO<W>();
		page.setPageinfo(info);
		page.setValues(wrappers);
		return page;
	}

	@Override
	public List<W> getByPropertiesOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByPropertiesOrdered(propertyinfos, criterias);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public List<W> getByProperty(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByProperty(pagenumber, rows, propertyName, value);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public List<W> getByProperty(String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByProperty(propertyName, value);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public W[] getByPropertyAsArray(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> list = getByProperty(pagenumber, rows, propertyName, value);
		return getWrapperArrayByCollection(list);
	}

	@Override
	public W[] getByPropertyAsArray(String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> list = getByProperty(propertyName, value);
		return getWrapperArrayByCollection(list);
	}

	@Override
	public W[] getByPropertyAsArrayOrdered(int pagenumber, int rows, String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByPropertyOrdered(pagenumber, rows, propertyName, value, criterias);
		return getWrapperArrayByEntities(entities);
	}

	@Override
	public W[] getByPropertyAsArrayOrdered(String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByPropertyOrdered(propertyName, value, criterias);
		return getWrapperArrayByEntities(entities);
	}

	@Override
	public PageDTO<W> getByPropertyAsArrayPage(int pagenumber, int rows, String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> list = findByProperty(pagenumber, rows, propertyName, value);
		W[] wrappers = getWrapperArrayByEntities(list);
		int count = countByProperty(propertyName, value);
		PageInfoDTO info = new PageInfoDTO();
		info.setPagenumber(pagenumber);
		info.setRows(wrappers.length);
		info.setTotalpages((count % rows) != 0 ? (count / rows) + 1 : (count / rows));
		info.setTotalrows(count);
		PageDTO<W> page = new PageDTO<W>();
		page.setPageinfo(info);
		page.setValues(wrappers);
		return page;
	}

	@Override
	public W getByPropertyAsSingleResult(String propertyName, Object value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		T entity = (T) findByPropertyAsSingleResult(propertyName, value);
		W wrapper = getWrapperByEntity(entity);
		return wrapper;
	}

	@Override
	public List<W> getByPropertyOrdered(String propertyName, Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByPropertyOrdered(propertyName, value, criterias);
		List<W> result = getWrappersByEntities(entities);
		return result;
	}

	@Override
	public List<W> getByQuery(int pagenumber, int rows, String querystr) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByQuery(pagenumber, rows, querystr);
		List<W> result = getWrappersByEntities(entities);
		return result;
	}

	@Override
	public List<W> getByQuery(int pagenumber, int rows, String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByQuery(pagenumber, rows, querystr, propertyinfos);
		List<W> result = getWrappersByEntities(entities);
		return result;
	}

	@Override
	public List<W> getByQuery(String querystr) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByQuery(querystr);
		List<W> result = getWrappersByEntities(entities);
		return result;
	}

	@Override
	public List<W> getByQuery(String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByQuery(querystr, propertyinfos);
		List<W> result = getWrappersByEntities(entities);
		return result;
	}

	@Override
	public W[] getByQueryAsArray(String querystr) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> result = getByQuery(querystr);
		return getWrapperArrayByCollection(result);
	}

	@Override
	public List<W> getByStringProperty(int pagenumber, int rows, String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return getByStringProperty(pagenumber, rows, propertyName, value, true);
	}

	@Override
	public List<W> getByStringProperty(int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByStringProperty(pagenumber, rows, propertyName, value, iscasesensitive);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public List<W> getByStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return getByStringProperty(propertyName, value, true);
	}

	@Override
	public List<W> getByStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findByStringProperty(propertyName, value, iscasesensitive);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	public T[] getEntityArrayByCollection(Collection<T> tcollection) throws OperationFailedException {
		// TODO DVI TEST
		T[] result = (T[]) Array.newInstance(entityBeanType, tcollection.size());
		int i = 0;
		for (Iterator<T> iterator = tcollection.iterator(); iterator.hasNext(); i++) {
			T t = iterator.next();
			result[i] = t;
		}
		return result;
	}

	protected Class<T> getEntityBeanType() {
		return entityBeanType;
	}

	protected W[] getIdentifiables() throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> allwrappers = getAll();
		return getWrapperArrayByCollection(allwrappers);
	}

	@Override
	public List<W> getLikeStringProperty(int pagenumber, int rows, String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return getLikeStringProperty(pagenumber, rows, propertyName, value, true);
	}

	@Override
	public List<W> getLikeStringProperty(int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findLikeStringProperty(pagenumber, rows, propertyName, value, iscasesensitive);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public List<W> getLikeStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		return getLikeStringProperty(propertyName, value, true);
	}

	@Override
	public List<W> getLikeStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<T> entities = findLikeStringProperty(propertyName, value, iscasesensitive);
		List<W> wrappers = getWrappersByEntities(entities);
		return wrappers;
	}

	@Override
	public W[] getLikeStringPropertyAsArray(String propertyName, String value) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> list = getLikeStringProperty(propertyName, value);
		return getWrapperArrayByCollection(list);
	}

	private String getOrderCriteria(OrderCriteriaDTO... criterias) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < criterias.length; i++) {
			OrderCriteriaDTO criteria = criterias[i];
			buffer.append("model.");
			buffer.append(criteria.getPropertyname());
			buffer.append(criteria.getAscending() ? " asc," : " desc,");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

	protected final ID getPrimaryKey(T entity) throws OperationFailedException {
		// TODO DVI TEST
		try {
			Class[] paramtypes = null;
			Method method = entityBeanType.getMethod("getId", paramtypes);
			ID result = (ID) method.invoke(entity, new Object[0]);
			return result;
		} catch (SecurityException e) {
			throw new OperationFailedException("Error de seguridad al acceder al método getId", e);
		} catch (IllegalArgumentException e) {
			throw new OperationFailedException("Error en los argumentos de llamada al método getId", e);
		} catch (NoSuchMethodException e) {
			throw new OperationFailedException("No existe el método getId", e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("Acceso ilegal al método getId", e);
		} catch (InvocationTargetException e) {
			throw new OperationFailedException("No es posible invocar el método getId", e);
		}
	}

	protected Class<ID> getPrimaryKeyType() {
		return primaryKeyType;
	}

	@Override
	public T getReferenceById(ID id) {
		// TODO DVI TEST
		T result = getEntityManager().getReference(entityBeanType, id);
		return result;
	}

	@Override
	public W[] getWrapperArrayByCollection(Collection<W> wcollection) throws OperationFailedException {
		// TODO DVI TEST
		W[] result = (W[]) Array.newInstance(wrapperType, wcollection.size());
		int i = 0;
		for (Iterator<W> iterator = wcollection.iterator(); iterator.hasNext(); i++) {
			W w = iterator.next();
			result[i] = w;
		}
		return result;
	}

	@Override
	public W[] getWrapperArrayByEntities(List<T> entities) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> wlist = getWrappersByEntities(entities);
		return getWrapperArrayByCollection(wlist);
	}

	@Override
	public W getWrapperByEntity(T entity) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			W wrapper = wrapperType.newInstance();
			ID key = getPrimaryKey(entity);
			BeanExtenderFactory.copyProperties(key, wrapper);
			BeanExtenderFactory.copyProperties(entity, wrapper);
			copyRelationsEntityToWrapper(entity, wrapper);
			return wrapper;
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase" + wrapperType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	@Override
	public List<W> getWrappersByEntities(List<T> entities) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<W> result = new ArrayList<W>();
		for (Iterator<T> iterator = entities.iterator(); iterator.hasNext();) {
			T entity = iterator.next();
			W wrapper = getWrapperByEntity(entity);
			result.add(wrapper);
		}
		return result;
	}

	protected Class<W> getWrapperType() {
		return wrapperType;
	}

	@Override
	public void lock(T entity, LockModeType lockmodetype) {
		// TODO DVI TEST
		getEntityManager().lock(entity, lockmodetype);
	}

	@Override
	public void lock(W wrapper, LockModeType lockmodetype) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			ID key = primaryKeyType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, key);
			T entity = getEntityManager().find(entityBeanType, key);
			if (entity == null)
				throw new NotFoundException("No existe " + entitylabel + " con la llave primaria especificada");
			getEntityManager().lock(entity, lockmodetype);
		} catch (InstantiationException e) {
			throw new OperationFailedException("No es posible instanciar un objeto de la clase " + entityBeanType.getName(), e);
		} catch (IllegalAccessException e) {
			throw new OperationFailedException("No es posible acceder a las propiedades del objeto", e);
		}
	}

	@Override
	public T merge(T entity) {
		// TODO DVI TEST
		T newentitty = getEntityManager().merge(entity);
		return newentitty;
	}

	@Override
	public T persist(T entity) {
		// TODO DVI TEST
		getEntityManager().persist(entity);
		return entity;
	}

	@Override
	public void refresh(T entity) throws OperationFailedException {
		// TODO DVI TEST
		getEntityManager().refresh(entity);
	}

	@Override
	public void remove(T entity) {
		// TODO DVI TEST
		getEntityManager().remove(entity);
	}

	abstract protected void setEntitylabel();

	abstract protected void setEntityname();

	protected void setTypes() {
		ParameterizedType genericType = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityBeanType = (Class<T>) genericType.getActualTypeArguments()[0];
		this.primaryKeyType = (Class<ID>) genericType.getActualTypeArguments()[1];
		this.wrapperType = (Class<W>) genericType.getActualTypeArguments()[2];
	}

	@Override
	public W updateIdentifiable(W wrapper) throws OperationFailedException, NotFoundException {
		// TODO DVI TEST
		try {
			T entity = entityBeanType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, entity);
			copyRelationsWrapperToEntity(entity, wrapper);
			ID key = primaryKeyType.newInstance();
			BeanExtenderFactory.copyProperties(wrapper, key);
			Method method = entityBeanType.getMethod("setId", primaryKeyType);
			method.invoke(entity, key);
			entity = merge(entity);
			W newwrapper = getWrapperByEntity(entity);
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
}