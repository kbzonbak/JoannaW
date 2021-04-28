package bbr.b2b.regional.logistic.buyorders.report.classes;


public class VeVPDOrderReportInitParamDTO extends OrderReportInitParamDTO {

	private String clientrut;
	private String requestnumber;

	public String getClientrut() {
		return clientrut;
	}

	public void setClientrut(String clientrut) {
		this.clientrut = clientrut;
	}

	public String getRequestnumber() {
		return requestnumber;
	}

	public void setRequestnumber(String requestnumber) {
		this.requestnumber = requestnumber;
	}	
}
