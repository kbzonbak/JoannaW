package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.cp.data.classes.CPItemDTO;

public class ProductsAssignment
{
	private CPItemDTO			itemSelected	= null;
	private int 				count  			= 0;
	
	public CPItemDTO getItemSelected() {
		return itemSelected;
	}
	public void setItemSelected(CPItemDTO itemSelected) {
		this.itemSelected = itemSelected;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	

}
