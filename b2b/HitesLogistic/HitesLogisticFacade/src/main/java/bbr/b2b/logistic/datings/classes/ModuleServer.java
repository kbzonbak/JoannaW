package bbr.b2b.logistic.datings.classes;

import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.logistic.base.facade.LogisticElementServer;
import bbr.b2b.logistic.datings.data.wrappers.ModuleW;
import bbr.b2b.logistic.datings.entities.Module;
import bbr.b2b.logistic.datings.report.classes.ModuleDataDTO;

@Stateless(name = "servers/datings/ModuleServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ModuleServer extends LogisticElementServer<Module, ModuleW> implements ModuleServerLocal {


	public ModuleW addModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW) addElement(module);
	}
	public ModuleW[] getModules() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW[]) getIdentifiables();
	}
	public ModuleW updateModule(ModuleW module) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (ModuleW) updateElement(module);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Module entity, ModuleW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Module entity, ModuleW wrapper) throws OperationFailedException, NotFoundException {
	}

	public ModuleDataDTO[] getModulesActiveByLocation(Long locationid){
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.logistic.datings.entities.Module.getModulesActiveByLocation");
		query.setLong("locationid", locationid);
		query.setResultTransformer(new LowerCaseResultTransformer(ModuleDataDTO.class));
		List<?> list = query.list();
		ModuleDataDTO[] result = (ModuleDataDTO[]) list.toArray(new ModuleDataDTO[list.size()]);		
		return result;
	}
	
	public ModuleW[] getModulesByInterval(LocalDateTime since, LocalDateTime until, boolean hasChangeDay){
		String queryStr;
		if(hasChangeDay){
			queryStr =  "WITH foo1 as ( " +
						"	select id, name, starts, ends," + 
						"	ROW_NUMBER () OVER (ORDER BY starts) as number " + 
						"   from logistica.module " + 
						"	where starts >= :since " + 
						"	), " +
						"foo2 as ( "  +
						"	select id, name, starts, ends, " +  
						"	10000 + ROW_NUMBER () OVER (ORDER BY starts) as number " +
						"	from logistica.module " + 
						"	where ends <= :until and starts <= :until " +
						"	), " +
						"foo3 as ( " +	
						"	select id, name, starts, ends, number from foo1 " +  
						"	union " +     
						"	select id, name, starts, ends, number from foo2 " + 
						"	order by number " +
						") " + 
						"select id, name, starts, ends from foo3 " ;
		}
		
		else{
			queryStr =  "select id, name, starts, ends from logistica.module " + 
						"where starts >= :since AND starts < :until " +  
						"AND ends > :since AND  ends <= :until " +  
						"order by starts ";
		}
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(queryStr);
		query.setParameter("since", since);
		query.setParameter("until", until);
		query.setResultTransformer(new LowerCaseResultTransformer(ModuleW.class));
		List<?> list = query.list();
		ModuleW[] result = (ModuleW[]) list.toArray(new ModuleW[list.size()]);		
		return result;
	}
	

	
	@Override
	protected void setEntitylabel() {
		entitylabel = "Module";
	}
	@Override
	protected void setEntityname() {
		entityname = "Module";
	}
}
