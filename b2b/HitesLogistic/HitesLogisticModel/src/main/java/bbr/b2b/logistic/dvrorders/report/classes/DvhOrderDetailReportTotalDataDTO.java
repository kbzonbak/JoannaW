package bbr.b2b.logistic.dvrorders.report.classes;

import java.io.Serializable;

public class DvhOrderDetailReportTotalDataDTO implements Serializable {

	private Integer totalreg;
	private Double totalbasecost;
	private Double totalfinalcost;
	private Double totalunits;
	private Double totalamount;

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public Double getTotalbasecost() {
		return totalbasecost;
	}

	public void setTotalbasecost(Double totalbasecost) {
		this.totalbasecost = totalbasecost;
	}

	public Double getTotalfinalcost() {
		return totalfinalcost;
	}

	public void setTotalfinalcost(Double totalfinalcost) {
		this.totalfinalcost = totalfinalcost;
	}

	public Double getTotalunits() {
		return totalunits;
	}

	public void setTotalunits(Double totalunits) {
		this.totalunits = totalunits;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

}
