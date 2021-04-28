package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;

public class StockDetailWSDTO implements Serializable {

	private String code;
	private String description;
	private String propro;
	private Integer availablestock;
	private Integer dailyrep;
	private String warehouse;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
