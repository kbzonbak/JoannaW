package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class TruckDispatcherCheckTokenInitParamDTO implements Serializable {

	private Long userid;
	private String token;
	
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
