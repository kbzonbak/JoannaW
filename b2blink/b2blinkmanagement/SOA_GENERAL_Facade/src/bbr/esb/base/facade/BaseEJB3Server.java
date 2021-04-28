package bbr.esb.base.facade;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import bbr.common.facade.GenericEJB3Server;

public abstract class BaseEJB3Server<T, ID extends Serializable, W extends Serializable> extends GenericEJB3Server<T, ID, W> {

	@PersistenceContext(unitName = "SoaPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	protected Session getSession() {
		return (Session) em.getDelegate();
	}

}
