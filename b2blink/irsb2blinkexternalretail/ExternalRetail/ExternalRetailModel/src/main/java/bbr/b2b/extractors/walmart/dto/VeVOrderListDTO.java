package bbr.b2b.extractors.walmart.dto;

import java.util.ArrayList;

public class VeVOrderListDTO {

	ArrayList<VeVOrderDTO> list;

	String username;

	String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<VeVOrderDTO> getList() {
		return list;
	}

	public void setList(ArrayList<VeVOrderDTO> list) {
		this.list = list;
	}

}
