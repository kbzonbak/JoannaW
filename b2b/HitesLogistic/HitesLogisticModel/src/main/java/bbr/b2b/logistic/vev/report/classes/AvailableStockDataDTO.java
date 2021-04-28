package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class AvailableStockDataDTO implements Serializable{

	private String itemcode;
	private String itemdescription;
	private String propro;
	private Integer availablestock;
	private Integer dailyrep;
	private String warehouse;
	
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getItemdescription() {
		return itemdescription;
	}
	public void setItemdescription(String itemdescription) {
		this.itemdescription = itemdescription;
	}
	public String getPropro() {
		return propro;
	}
	public void setPropro(String propro) {
		this.propro = propro;
	}
	public Integer getAvailablestock() {
		return availablestock;
	}
	public void setAvailablestock(Integer availablestock) {
		this.availablestock = availablestock;
	}
	public Integer getDailyrep() {
		return dailyrep;
	}
	public void setDailyrep(Integer dailyrep) {
		this.dailyrep = dailyrep;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
}