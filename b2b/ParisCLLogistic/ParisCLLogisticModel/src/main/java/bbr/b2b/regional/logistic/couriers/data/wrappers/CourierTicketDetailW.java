package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTicketDetail;
import bbr.b2b.regional.logistic.couriers.data.interfaces.ICourierTicketDetailPK;


public class CourierTicketDetailW implements ICourierTicketDetailPK, ICourierTicketDetail {

	private Long courierticketid;
	private Long directorderid;
	private Boolean processed;
	private Date startdate;
	private Boolean success;
	private String result;

	
	public Long getCourierticketid() {
		return courierticketid;
	}


	public void setCourierticketid(Long courierticketid) {
		this.courierticketid = courierticketid;
	}


	public Long getDodeliveryid() {
		return directorderid;
	}


	public void setDodeliveryid(Long directorderid) {
		this.directorderid = directorderid;
	}


	public Boolean getProcessed() {
		return processed;
	}


	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}


	public Date getStartdate() {
		return startdate;
	}


	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}


	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public int compareTo(IPrimaryKey o) {
		
		long result = 0;
		result = courierticketid.longValue() - ((ICourierTicketDetailPK) o).getCourierticketid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = directorderid.longValue() - ((ICourierTicketDetailPK) o).getDodeliveryid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

}
