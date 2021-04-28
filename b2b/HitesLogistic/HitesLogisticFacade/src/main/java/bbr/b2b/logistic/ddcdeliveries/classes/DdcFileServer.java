package bbr.b2b.logistic.ddcdeliveries.classes;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.ddcdeliveries.data.wrappers.DdcFileW;
import bbr.b2b.logistic.ddcdeliveries.entities.DdcFile;
import bbr.b2b.logistic.ddcorders.classes.DdcOrderServerLocal;
import bbr.b2b.logistic.ddcorders.entities.DdcOrder;
import bbr.b2b.logistic.ddcorders.report.classes.DdcFileDTO;

@Stateless(name = "servers/ddcdeliveries/DdcFileServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DdcFileServer extends LogisticElementServer<DdcFile, DdcFileW> implements DdcFileServerLocal {

	@EJB
	DdcOrderServerLocal ddcorderserver;

	public DdcFileW addDdcFile(DdcFileW ddcfile) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcFileW) addElement(ddcfile);
	}
	public DdcFileW[] getDdcFiles() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcFileW[]) getIdentifiables();
	}
	public DdcFileW updateDdcFile(DdcFileW ddcfile) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DdcFileW) updateElement(ddcfile);
	}

	@Override
	protected void copyRelationsEntityToWrapper(DdcFile entity, DdcFileW wrapper) {
		wrapper.setDdcorderid(entity.getDdcorder() != null ? new Long(entity.getDdcorder().getId()) : new Long(0));
	}
	
	@Override
	protected void copyRelationsWrapperToEntity(DdcFile entity, DdcFileW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getDdcorderid() != null && wrapper.getDdcorderid() > 0) { 
			DdcOrder ddcorder = ddcorderserver.getReferenceById(wrapper.getDdcorderid());
			if (ddcorder != null) { 
				entity.setDdcorder(ddcorder);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "DdcFile";
	}
	@Override
	protected void setEntityname() {
		entityname = "DdcFile";
	}
	
	public DdcFileDTO[] getDdcFilesByDdcOrders(Long[] ddcorderids) {
				
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.ddcdeliveries.entities.DdcFile.getDdcFilesByDdcOrders");
		query.setParameterList("ddcorderids", ddcorderids);
		query.setResultTransformer(new LowerCaseResultTransformer(DdcFileDTO.class));
			
		List<?> list = query.list();
		DdcFileDTO[] result = list.toArray(new DdcFileDTO[list.size()]);
				
		return result;
	}
}
