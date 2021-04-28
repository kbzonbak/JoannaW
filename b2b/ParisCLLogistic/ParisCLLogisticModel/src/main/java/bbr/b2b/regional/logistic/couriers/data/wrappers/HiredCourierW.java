package bbr.b2b.regional.logistic.couriers.data.wrappers;

import java.util.Date;

import bbr.b2b.common.adtclasses.interfaces.IPrimaryKey;
import bbr.b2b.regional.logistic.couriers.data.interfaces.IHiredCourier;
import bbr.b2b.regional.logistic.couriers.data.interfaces.IHiredCourierPK;

public class HiredCourierW implements IHiredCourier, IHiredCourierPK {

	private String user;
	private String password;
	private String clientcode;	
	private Date creationdate;	
	private Date updatedate;
	private Long vendorid;
	private Long courierid;
	
	public Long getVendorid() {
		return vendorid;
	}

	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}

	public Long getCourierid() {
		return courierid;
	}

	public void setCourierid(Long courierid) {
		this.courierid = courierid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public int compareTo(IPrimaryKey o) {
		
		long result = 0;
		result = vendorid.longValue() - ((IHiredCourierPK) o).getVendorid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		result = courierid.longValue() - ((IHiredCourierPK) o).getCourierid().longValue();
		if (result != 0) {
			return (result > 0) ? 1 : -1;
		}
		return 0;
	}

}
