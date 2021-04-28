package bbr.esb.users.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface ICompany extends IElement {

	public String getAs2id();

	public boolean getEncrypt();

	public String getEncryptpassword();

	public String getName();

	public String getRut();

	public void setAs2id(String as2id);

	public void setEncrypt(boolean encrypt);

	public void setEncryptpassword(String encryptpassword);

	public void setName(String name);

	public void setRut(String rut);
	
	public boolean isClientavaliable();
	
	public void setClientavaliable(boolean clientavaliable);
	
	public boolean isMonitoreable();

	public void setMonitoreable(boolean monitoreable);

	public Date getLastclientcheck();

	public void setLastclientcheck(Date lastclientcheck);

	public Integer getDafaultmaxdelayendflow() ;


}
