package bbr.b2b.regional.logistic.vendors.classes;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.report.classes.DepartmentDTO;

@Stateless(name = "servers/vendors/DepartmentServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DepartmentServer extends LogisticElementServer<Department, DepartmentW> implements DepartmentServerLocal {


	public DepartmentW addDepartment(DepartmentW department) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DepartmentW) addElement(department);
	}
	public DepartmentW[] getDepartments() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DepartmentW[]) getIdentifiables();
	}
	public DepartmentW updateDepartment(DepartmentW department) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (DepartmentW) updateElement(department);
	}

	@Override
	protected void copyRelationsEntityToWrapper(Department entity, DepartmentW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Department entity, DepartmentW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Department";
	}
	@Override
	protected void setEntityname() {
		entityname = "Department";
	}
	
	public DepartmentDTO[] findDepartmentsByCode(String code) throws OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " +
				"ID, " +
				"CODE, " +
				"NAME, " +
				"CODE || ' - ' || NAME AS CODENAMESTR " +
			"FROM " +
				"LOGISTICA.DEPARTMENT " +
			"WHERE " +
				"CODE LIKE :code " +
			"ORDER BY "+
				"NAME ASC";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("code", "%" + code.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(DepartmentDTO.class));
		List list = query.list();
		
		return (DepartmentDTO[]) list.toArray(new DepartmentDTO[list.size()]);				
		
	}
	
	public DepartmentDTO[] findDepartmentsByName(String name) throws OperationFailedException, NotFoundException{
		
		String SQL =
			"SELECT DISTINCT " +
				"ID, " +
				"CODE, " +
				"NAME, " +
				"CODE || ' - ' || NAME AS CODENAMESTR " +
			"FROM " +
				"LOGISTICA.DEPARTMENT " +
			"WHERE " +
				"NAME LIKE :name " +
			"ORDER BY "+
				"NAME ASC";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setString("name", "%" + name.replace("%", "\\%") + "%");
		query.setResultTransformer(new LowerCaseResultTransformer(DepartmentDTO.class));
		List list = query.list();
		
		return (DepartmentDTO[]) list.toArray(new DepartmentDTO[list.size()]);		
		
	}
	
}
