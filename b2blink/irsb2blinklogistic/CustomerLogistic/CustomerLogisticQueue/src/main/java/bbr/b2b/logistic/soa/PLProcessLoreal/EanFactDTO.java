package bbr.b2b.logistic.soa.PLProcessLoreal;

import java.util.Objects;

public class EanFactDTO {
	
	private String ean13;
	private String nodoc;
	
	@Override
	public int hashCode() {
		 return Integer.valueOf(ean13.hashCode()) + Integer.valueOf(nodoc.hashCode());
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof EanFactDTO){
			EanFactDTO eanfact =(EanFactDTO) o;
			return eanfact.getEan13().equals(this.ean13) && eanfact.getNodoc().equals(this.nodoc);
		}
		return false;
	}
	
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	public String getNodoc() {
		return nodoc;
	}
	public void setNodoc(String nodoc) {
		this.nodoc = nodoc;
	}
	
	

}
