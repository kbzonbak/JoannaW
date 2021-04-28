package bbr.b2b.regional.logistic.kpi.data.classes;

import java.io.Serializable;

public class KPIvevVendorDimensionDTO implements Serializable{

	private Long id;
	private String code;
	private String name;
	private Double totalfine;
	private Double totalcurrencyfine;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getTotalfine() {
		return totalfine;
	}
	public void setTotalfine(Double totalfine) {
		this.totalfine = totalfine;
	}
	public Double getTotalcurrencyfine() {
		return totalcurrencyfine;
	}
	public void setTotalcurrencyfine(Double totalcurrencyfine) {
		this.totalcurrencyfine = totalcurrencyfine;
	}
				
}