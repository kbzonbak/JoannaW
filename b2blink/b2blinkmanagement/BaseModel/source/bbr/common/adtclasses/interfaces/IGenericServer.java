package bbr.common.adtclasses.interfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;

import bbr.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.common.adtclasses.classes.PageDTO;
import bbr.common.adtclasses.classes.PropertyInfoDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;

public interface IGenericServer<T, ID extends Serializable, W extends Serializable> extends IBaseServer {

	public W addIdentifiable(W wrapper) throws AccessDeniedException, OperationFailedException, NotFoundException;

	public int count() throws OperationFailedException, NotFoundException;

	public int countByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public int countByProperty(String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public int countByStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException;

	public int countByStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public int countLikeStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException;

	public int countLikeStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public int deleteAll() throws OperationFailedException;

	public int deleteByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public int deleteByProperty(String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public void deleteIdentifiable(Object ikey) throws OperationFailedException, NotFoundException;

	// public void deleteIdentifiables(Object[] ikey) throws OperationFailedException, NotFoundException;

	public List<T> findAll() throws OperationFailedException;

	public List<T> findAll(int pagenumber, int rows) throws OperationFailedException;

	public T[] findAllAsArray() throws OperationFailedException;

	public T[] findAllAsArray(int pagenumber, int rows) throws OperationFailedException;

	public T[] findAllAsArrayOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException;

	public T[] findAllAsArrayOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException;

	public PageDTO<T> findAllAsArrayPage(int pagenumber, int rows) throws OperationFailedException, NotFoundException;

	public List<T> findAllOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException;

	public List<T> findAllOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException;

	public T findById(ID id) throws OperationFailedException, NotFoundException;

	public T findById(ID id, LockModeType lockmodetype) throws OperationFailedException, NotFoundException;

	public T findByMaxId() throws OperationFailedException, NotFoundException;

	public T findByMinId() throws OperationFailedException, NotFoundException;

	public List<T> findByProperties(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public List<T> findByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public T[] findByPropertiesAsArray(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public T[] findByPropertiesAsArrayOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public List<T> findByPropertiesOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public List<T> findByProperty(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException;

	public List<T> findByProperty(String propertyName, final Object value) throws OperationFailedException;

	public T[] findByPropertyAsArray(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException;

	public T[] findByPropertyAsArray(String propertyName, final Object value) throws OperationFailedException;

	public PageDTO<T> findByPropertyAsArrayPage(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public T findByPropertyAsSingleResult(String propertyName, final Object value) throws OperationFailedException;

	public List<T> findByPropertyOrdered(int pagenumber, int rows, String propertyName, final Object value, OrderCriteriaDTO... criterias) throws OperationFailedException;

	public List<T> findByPropertyOrdered(String propertyName, final Object value, OrderCriteriaDTO... criterias) throws OperationFailedException;

	public List<T> findByQuery(int pagenumber, int rows, String querystr) throws OperationFailedException;

	public List<T> findByQuery(int pagenumber, int rows, String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException;

	public List<T> findByQuery(String querystr) throws OperationFailedException;

	public List<T> findByQuery(String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException;

	public List<T> findByStringProperty(int pagenumber, int rows, String propertyName, final String value) throws OperationFailedException;

	public List<T> findByStringProperty(int pagenumber, int rows, String propertyName, final String value, final boolean iscasesensitive) throws OperationFailedException;

	public List<T> findByStringProperty(String propertyName, final String value) throws OperationFailedException;

	public List<T> findByStringProperty(String propertyName, final String value, final boolean iscasesensitive) throws OperationFailedException;

	public List<T> findLikeStringProperty(int pagenumber, int rows, String propertyName, final String value) throws OperationFailedException;

	public List<T> findLikeStringProperty(int pagenumber, int rows, String propertyName, final String value, boolean iscasesensitive) throws OperationFailedException;

	public List<T> findLikeStringProperty(String propertyName, final String value) throws OperationFailedException;

	public List<T> findLikeStringProperty(String propertyName, final String value, boolean iscasesensitive) throws OperationFailedException;

	public T[] findLikeStringPropertyAsArray(String propertyName, final String value) throws OperationFailedException;

	public List<W> getAll() throws OperationFailedException, NotFoundException;

	public List<W> getAll(int pagenumber, int rows) throws OperationFailedException, NotFoundException;

	public W[] getAllAsArray() throws OperationFailedException, NotFoundException;

	public W[] getAllAsArray(int pagenumber, int rows) throws OperationFailedException, NotFoundException;

	public W[] getAllAsArrayOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public W[] getAllAsArrayOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public PageDTO<W> getAllAsArrayPage(int pagenumber, int rows) throws OperationFailedException, NotFoundException;

	public List<W> getAllOrdered(int pagenumber, int rows, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public List<W> getAllOrdered(OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public W getById(ID id) throws OperationFailedException, NotFoundException;

	public W getById(ID id, LockModeType lockmodetype) throws OperationFailedException, NotFoundException;

	public W getByMaxId() throws OperationFailedException, NotFoundException;

	public W getByMinId() throws OperationFailedException, NotFoundException;

	public List<W> getByProperties(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public List<W> getByProperties(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public W[] getByPropertiesAsArray(PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public W[] getByPropertiesAsArrayOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public PageDTO<W> getByPropertiesAsArrayPage(int pagenumber, int rows, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public List<W> getByPropertiesOrdered(PropertyInfoDTO[] propertyinfos, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public List<W> getByProperty(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public List<W> getByProperty(String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public W[] getByPropertyAsArray(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public W[] getByPropertyAsArray(String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public W[] getByPropertyAsArrayOrdered(int pagenumber, int rows, String propertyName, final Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public W[] getByPropertyAsArrayOrdered(String propertyName, final Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public PageDTO<W> getByPropertyAsArrayPage(int pagenumber, int rows, String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public W getByPropertyAsSingleResult(String propertyName, final Object value) throws OperationFailedException, NotFoundException;

	public List<W> getByPropertyOrdered(String propertyName, final Object value, OrderCriteriaDTO... criterias) throws OperationFailedException, NotFoundException;

	public List<W> getByQuery(int pagenumber, int rows, String querystr) throws OperationFailedException, NotFoundException;

	public List<W> getByQuery(int pagenumber, int rows, String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public List<W> getByQuery(String querystr) throws OperationFailedException, NotFoundException;

	public List<W> getByQuery(String querystr, PropertyInfoDTO... propertyinfos) throws OperationFailedException, NotFoundException;

	public W[] getByQueryAsArray(String querystr) throws OperationFailedException, NotFoundException;

	public List<W> getByStringProperty(int pagenumber, int rows, String propertyName, String value) throws OperationFailedException, NotFoundException;

	public List<W> getByStringProperty(int pagenumber, int rows, String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public List<W> getByStringProperty(String propertyName, String value) throws OperationFailedException, NotFoundException;

	public List<W> getByStringProperty(String propertyName, String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public T[] getEntityArrayByCollection(Collection<T> tcollection) throws OperationFailedException;

	public List<W> getLikeStringProperty(int pagenumber, int rows, String propertyName, final String value) throws OperationFailedException, NotFoundException;

	public List<W> getLikeStringProperty(int pagenumber, int rows, String propertyName, final String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public List<W> getLikeStringProperty(String propertyName, final String value) throws OperationFailedException, NotFoundException;

	public List<W> getLikeStringProperty(String propertyName, final String value, boolean iscasesensitive) throws OperationFailedException, NotFoundException;

	public W[] getLikeStringPropertyAsArray(String propertyName, final String value) throws OperationFailedException, NotFoundException;

	public T getReferenceById(ID id);

	public W[] getWrapperArrayByCollection(Collection<W> wlist) throws OperationFailedException;

	public W[] getWrapperArrayByEntities(List<T> entities) throws OperationFailedException, NotFoundException;

	public W getWrapperByEntity(T entity) throws OperationFailedException, NotFoundException;

	public List<W> getWrappersByEntities(List<T> entities) throws OperationFailedException, NotFoundException;

	public void lock(T entity, LockModeType lockmodetype) throws OperationFailedException;

	public void lock(W wrapper, LockModeType lockmodetype) throws OperationFailedException, NotFoundException;

	public T merge(T entity) throws OperationFailedException;

	public T persist(T entity) throws OperationFailedException;

	public void refresh(T entity) throws OperationFailedException;

	public void remove(T entity) throws OperationFailedException;

	public W updateIdentifiable(W wrapper) throws OperationFailedException, NotFoundException;

}
