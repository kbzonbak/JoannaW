package bbr.b2b.logistic.dvrdeliveries.report.classes;

import java.io.Serializable;

public class DvrDeliveryDetailTotalDataDTO implements Serializable {

	private Integer totalreg;
	private Double totalavailable;
	private Double totalreceived;

	public Integer getTotalreg() {
		return totalreg;
	}

	public void setTotalreg(Integer totalreg) {
		this.totalreg = totalreg;
	}

	public Double getTotalavailable() {
		return totalavailable;
	}

	public void setTotalavailable(Double totalavailable) {
		this.totalavailable = totalavailable;
	}

	public Double getTotalreceived() {
		return totalreceived;
	}

	public void setTotalreceived(Double totalreceived) {
		this.totalreceived = totalreceived;
	}

}
