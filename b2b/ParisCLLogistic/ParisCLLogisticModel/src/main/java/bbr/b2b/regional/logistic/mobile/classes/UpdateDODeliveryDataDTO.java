package bbr.b2b.regional.logistic.mobile.classes;

import java.io.Serializable;

public class UpdateDODeliveryDataDTO implements Serializable{

	private String dodstatuscode;
	private String dodcomment;
	private String image;
	
	public String getDodstatuscode() {
		return dodstatuscode;
	}
	public void setDodstatuscode(String dodstatuscode) {
		this.dodstatuscode = dodstatuscode;
	}
	public String getDodcomment() {
		return dodcomment;
	}
	public void setDodcomment(String dodcomment) {
		this.dodcomment = dodcomment;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	
}
