package bbr.b2b.portal.fep.commercial.managers.interfaces;

import bbr.b2b.fep.commercial.data.classes.CategoryArrayResultDTO;
import bbr.b2b.portal.managers.interfaces.IGenericEJBInterface;


public interface IFEPCategoryProductManager extends IGenericEJBInterface{
	

	public CategoryArrayResultDTO getCategoryProductByProvider(String pvcode, Long uskey, boolean count);

}