package bbr.b2b.logistic.vev.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AvailableAndDailyStockUpdateInitParamDTO implements Serializable{
	
	private String vendordni;
	private String date;
	private String dateformat;
	private String user;
	private String usertype;
	private AvailableStockSendDataWSDTO[] stock;
	
	public String getVendordni() {
		return vendordni;
	}
	public void setVendordni(String vendordni) {
		this.vendordni = vendordni;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateformat() {
		return dateformat;
	}
	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public AvailableStockSendDataWSDTO[] getStock() {
		return stock;
	}
	public void setStock(AvailableStockSendDataWSDTO[] stock) {
		this.stock = stock;
	}
	
	
	
}
