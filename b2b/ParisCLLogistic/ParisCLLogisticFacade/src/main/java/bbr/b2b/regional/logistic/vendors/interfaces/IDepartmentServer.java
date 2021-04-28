package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.data.wrappers.DepartmentW;
import bbr.b2b.regional.logistic.vendors.entities.Department;
import bbr.b2b.regional.logistic.vendors.report.classes.DepartmentDTO;

public interface IDepartmentServer extends IElementServer<Department, DepartmentW> {

	DepartmentW addDepartment(DepartmentW department) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DepartmentW[] getDepartments() throws AccessDeniedException, OperationFailedException, NotFoundException;
	DepartmentW updateDepartment(DepartmentW department) throws AccessDeniedException, OperationFailedException, NotFoundException;
	DepartmentDTO[] findDepartmentsByCode(String code) throws OperationFailedException, NotFoundException;
	DepartmentDTO[] findDepartmentsByName(String name) throws OperationFailedException, NotFoundException;

}
