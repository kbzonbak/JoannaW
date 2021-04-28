package bbr.esb.users.entities;

import java.util.Date;

import bbr.esb.users.data.interfaces.ICompany;

public class Company implements ICompany {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3984447401883188198L;

	private Long id;

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

	public String getAs2id() {
		return as2id;
	}

	public boolean getEncrypt() {
		return encrypt;
	}

	public String getEncryptpassword() {
		return encryptpassword;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRut() {
		return rut;
	}

	public void setAs2id(String as2id) {
		this.as2id = as2id;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public void setEncryptpassword(String encryptpassword) {
		this.encryptpassword = encryptpassword;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

}
