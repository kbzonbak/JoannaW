package bbr.b2b.soa.integrator.utils;

import lombok.Data;

@Data
public class Authentication {

	private String userName;
	private String password;
	
	public Authentication(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
}
