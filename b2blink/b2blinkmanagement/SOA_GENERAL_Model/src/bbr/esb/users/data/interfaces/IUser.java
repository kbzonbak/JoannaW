package bbr.esb.users.data.interfaces;

import java.util.Date;

import bbr.common.adtclasses.interfaces.IElement;

public interface IUser extends IElement {

	public boolean getActive();

	public String getAnswer();

	public Boolean getBlocked();

	public Integer getCheckvolatile();

	public String getEmail();

	public Date getLastaccess();

	public Date getLastlogon();

	public String getLastname();

	public Date getLastpasschange();

	public String getName();

	public String getPassword();

	public String getQuestion();

	public String getRut();

	public String getSessionid();

	public Integer getTriescount();

	public void setActive(boolean active);

	public void setAnswer(String answer);

	public void setBlocked(Boolean blocked);

	public void setCheckvolatile(Integer checkvolatile);

	public void setEmail(String email);

	public void setLastaccess(Date lastaccess);

	public void setLastlogon(Date lastlogon);

	public void setLastname(String lastname);

	public void setLastpasschange(Date lastpasschange);

	public void setName(String name);

	public void setPassword(String password);

	public void setQuestion(String question);

	public void setRut(String rut);

	public void setSessionid(String sessionid);

	public void setTriescount(Integer triescount);

}
