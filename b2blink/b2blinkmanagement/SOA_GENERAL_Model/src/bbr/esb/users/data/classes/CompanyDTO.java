package bbr.esb.users.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.ICompany;

public class CompanyDTO extends ElementDTO implements ICompany {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2585778320386113878L;

	private String name;

	private String rut;

	private boolean encrypt;

	private String encryptpassword;

	private String as2id;

private boolean clientavaliable;
	
	private boolean monitoreable;
	
	private Date lastclientcheck;
	
	private Integer dafaultmaxdelayendflow;
	
	public boolean isClientavaliable() {
		return clientavaliable;
	}

	public void setClientavaliable(boolean clientavaliable) {
		this.clientavaliable = clientavaliable;
	}

	public boolean isMonitoreable() {
		return monitoreable;
	}

	public void setMonitoreable(boolean monitoreable) {
		this.monitoreable = monitoreable;
	}

	public Date getLastclientcheck() {
		return lastclientcheck;
	}

	public void setLastclientcheck(Date lastclientcheck) {
		this.lastclientcheck = lastclientcheck;
	}

	public Integer getDafaultmaxdelayendflow() {
		return dafaultmaxdelayendflow;
	}

	public void setDafaultmaxdelayendflow(Integer dafaultmaxdelayendflow) {
		this.dafaultmaxdelayendflow = dafaultmaxdelayendflow;
	}
	
	public String getName() {
		return name;
	}

	public String getRut() {
		return rut;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public boolean getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public String getEncryptpassword() {
		return encryptpassword;
	}

	public void setEncryptpassword(String encryptpassword) {
		this.encryptpassword = encryptpassword;
	}

	public String getAs2id() {
		return as2id;
	}

	public void setAs2id(String as2id) {
		this.as2id = as2id;
	}

}
