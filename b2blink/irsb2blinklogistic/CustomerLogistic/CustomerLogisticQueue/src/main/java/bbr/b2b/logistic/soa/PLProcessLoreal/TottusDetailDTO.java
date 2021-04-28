package bbr.b2b.logistic.soa.PLProcessLoreal;

public class TottusDetailDTO implements Comparable< TottusDetailDTO >{
	
	private String ean13;
	private String codlocalentrega;
	private String epu;
	private Integer unidades;
	private String bu;
	private String nodoctributario;
	
	@Override
	public int compareTo(TottusDetailDTO o) {
        return this.getNodoctributario().compareTo(o.getNodoctributario());
    }
	
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	public String getCodlocalentrega() {
		return codlocalentrega;
	}
	public void setCodlocalentrega(String codlocalentrega) {
		this.codlocalentrega = codlocalentrega;
	}
	public String getEpu() {
		return epu;
	}
	public void setEpu(String epu) {
		this.epu = epu;
	}
	public Integer getUnidades() {
		return unidades;
	}
	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
	}
	public String getNodoctributario() {
		return nodoctributario;
	}
	public void setNodoctributario(String nodoctributario) {
		this.nodoctributario = nodoctributario;
	}
	
}
