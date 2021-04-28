package bbr.common.facade;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bbr.common.adtclasses.interfaces.IBaseReportServer;

public abstract class BaseReportServer implements IBaseReportServer {

	protected EntityManager em;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public abstract void setEntityManager(EntityManager em);

	protected EntityManager getEntityManager() {
		return em;
	}

}
