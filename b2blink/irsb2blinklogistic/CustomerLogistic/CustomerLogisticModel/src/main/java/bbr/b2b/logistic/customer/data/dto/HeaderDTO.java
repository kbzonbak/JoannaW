package bbr.b2b.logistic.customer.data.dto;

public class HeaderDTO {
	
	private String nodoc;
	private String noc;
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof HeaderDTO){
			HeaderDTO header = (HeaderDTO)arg0;
			return this.nodoc.equals(header.getNodoc()) && this.noc.equals(header.getNoc());
		}
		return false;
	}
	
	
	@Override
	public int hashCode() {
		return this.nodoc.hashCode() + this.noc.hashCode();
	}
	
	public String getNodoc() {
		return nodoc;
	}
	public void setNodoc(String nodoc) {
		this.nodoc = nodoc;
	}
	public String getNoc() {
		return noc;
	}
	public void setNoc(String noc) {
		this.noc = noc;
	}
	
}
