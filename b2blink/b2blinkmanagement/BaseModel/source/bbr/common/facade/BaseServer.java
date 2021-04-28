package bbr.common.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bbr.common.adtclasses.classes.PropertyInfoDTO;
import bbr.common.adtclasses.interfaces.IBaseServer;

public abstract class BaseServer implements IBaseServer {

	protected EntityManager em;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public void clear() {
		// TODO DVI TEST
		getEntityManager().clear();
	}

	public int executeUpdate(String querystr) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		return query.executeUpdate();
	}

	public void flush() {
		// TODO DVI TEST
		getEntityManager().flush();
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public List getResultList(int pagenumber, int rows, String querystr) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		query.setFirstResult((pagenumber - 1) * rows);
		query.setMaxResults(rows);
		return query.getResultList();
	}

	public List getResultList(int pagenumber, int rows, String querystr, PropertyInfoDTO... propertyinfos) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		query.setFirstResult((pagenumber - 1) * rows);
		query.setMaxResults(rows);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		return query.getResultList();
	}

	public List getResultList(String querystr) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		return query.getResultList();
	}

	public List getResultList(String querystr, PropertyInfoDTO... propertyinfos) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		for (int i = 0; i < propertyinfos.length; i++) {
			PropertyInfoDTO property = propertyinfos[i];
			if (!property.isUnaryOperator())
				query.setParameter(property.getAlias(), property.getValue());
		}
		return query.getResultList();
	}

	public Object getSingleResult(String querystr) {
		// TODO DVI TEST
		Query query = getEntityManager().createQuery(querystr);
		return query.getSingleResult();
	}

	abstract public void setEntityManager(EntityManager em);

}
