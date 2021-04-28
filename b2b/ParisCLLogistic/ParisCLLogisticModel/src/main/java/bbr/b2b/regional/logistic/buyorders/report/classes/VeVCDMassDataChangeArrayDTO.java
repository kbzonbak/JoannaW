package bbr.b2b.regional.logistic.buyorders.report.classes;

import java.io.Serializable;

public class VeVCDMassDataChangeArrayDTO implements Serializable{

	private VeVCDMassDataChangeDTO[] data;

	public VeVCDMassDataChangeDTO[] getData() {
		return data;
	}

	public void setData(VeVCDMassDataChangeDTO[] data) {
		this.data = data;
	}
	
}
