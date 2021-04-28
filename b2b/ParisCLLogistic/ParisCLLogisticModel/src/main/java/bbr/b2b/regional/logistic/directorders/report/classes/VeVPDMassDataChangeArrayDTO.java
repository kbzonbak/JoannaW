package bbr.b2b.regional.logistic.directorders.report.classes;

import java.io.Serializable;

public class VeVPDMassDataChangeArrayDTO implements Serializable{

	private VeVPDMassDataChangeDTO[] data;

	public VeVPDMassDataChangeDTO[] getData() {
		return data;
	}

	public void setData(VeVPDMassDataChangeDTO[] data) {
		this.data = data;
	}
	
}
