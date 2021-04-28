package bbr.esb.users.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;
import bbr.esb.users.data.interfaces.IUser;

public class UserDTO extends ElementDTO implements IUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8672614680947878543L;

	private boolean active;

	private String email;

	private String lastname;

	private String name;

	private String password;

	private String rut;

	private String answer;

	private Boolean blocked;

	private Integer checkvolatile;

	private Date lastaccess;

	private Date lastlogon;

	private Date lastpasschange;

	private String question;

	private String sessionid;

	private Integer triescount;

	private Long usertypeid;

	public boolean getActive() {
		return active;
	}

	public String getAnswer() {
		return answer;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public Integer getCheckvolatile() {
		return checkvolatile;
	}

	public String getEmail() {
		return email;
	}

	public Date getLastaccess() {
		return lastaccess;
	}

	public Date getLastlogon() {
		return lastlogon;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getLastpasschange() {
		return lastpasschange;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getQuestion() {
		return question;
	}

	public String getRut() {
		return rut;
	}

	public String getSessionid() {
		return sessionid;
	}

	public Integer getTriescount() {
		return triescount;
	}

	public Long getUsertypeid() {
		return usertypeid;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public void setCheckvolatile(Integer checkvolatile) {
		this.checkvolatile = checkvolatile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLastaccess(Date lastaccess) {
		this.lastaccess = lastaccess;
	}

	public void setLastlogon(Date lastlogon) {
		this.lastlogon = lastlogon;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setLastpasschange(Date lastpasschange) {
		this.lastpasschange = lastpasschange;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public void setTriescount(Integer triescount) {
		this.triescount = triescount;
	}

	public void setUsertypeid(Long usertypeid) {
		this.usertypeid = usertypeid;
	}

}
