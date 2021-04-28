package bbr.b2b.regional.logistic.taxdocuments.report.classes;

import java.io.Serializable;

public class ReceptionDetailTotalDTO implements Serializable {

	private Long registers;
	private Double	receivedunits;
	private Double	totalreceived;
	
	public Long getRegisters() {
		return registers;
	}
	public void setRegisters(Long registers) {
		this.registers = registers;
	}
	public Double getReceivedunits() {
		return receivedunits;
	}
	public void setReceivedunits(Double receivedunits) {
		this.receivedunits = receivedunits;
	}
	public Double getTotalreceived() {
		return totalreceived;
	}
	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}
	
}
