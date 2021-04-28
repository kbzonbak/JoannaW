package bbr.esb.events.data.classes;

public class SiteProgress{	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private String sitename;
	
	private String retailname;
	
	private Integer success;
	
	private Integer error;
	
	private Integer inprogress;

	public SiteProgress() {
		super();
		success=0;
		error=0;
		inprogress=0;		
	}
	
	
	public String getRetailname() {
		return retailname;
	}


	public void setRetailname(String retailname) {
		this.retailname = retailname;
	}


	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}


	public void addSuccess(){
		success++;
	}
	
	public void addError(){
		error++;		
	}
	
	public void addinprogress(){
		inprogress++;
	}
	
	public Integer getSuccess() {
		return success;
	}
	
	public void setSuccess(Integer success) {
		this.success = success;
	}
	
	public Integer getError() {
		return error;
	}
	
	public void setError(Integer error) {
		this.error = error;
	}
	
	public Integer getInprogress() {
		return inprogress;
	}
	
	public void setInprogress(Integer inprogress) {
		this.inprogress = inprogress;
	}
}
