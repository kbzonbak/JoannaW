package bbr.b2b.regional.logistic.report.classes;

import java.io.Serializable;

public class UserDataInitParamDTO implements Serializable{

	private Long userid;
	private String username;
	private String usertype;
	private String userenterprisecode;
	private String userenterprisename;
	private String useremail;
		
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserenterprisecode() {
		return userenterprisecode;
	}
	public void setUserenterprisecode(String userenterprisecode) {
		this.userenterprisecode = userenterprisecode;
	}
	public String getUserenterprisename() {
		return userenterprisename;
	}
	public void setUserenterprisename(String userenterprisename) {
		this.userenterprisename = userenterprisename;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
}
