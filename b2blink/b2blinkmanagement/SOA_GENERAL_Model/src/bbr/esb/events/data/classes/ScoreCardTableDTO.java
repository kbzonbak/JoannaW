package bbr.esb.events.data.classes;

import java.util.Date;

import bbr.common.adtclasses.classes.ElementDTO;

public class ScoreCardTableDTO extends ElementDTO{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long numdoc;
	
	String sitio;
	
	Date fecha;
	
	String estado;
	
	String retail;
	
	Long siteid;
	
	Long companyid;
	
	Long serviceid;
	
	
	public Long getServiceid() {
		return serviceid;
	}
	public void setServiceid(Long serviceid) {
		this.serviceid = serviceid;
	}
	public Long getSiteid() {
		return siteid;
	}
	public void setSiteid(Long siteid) {
		this.siteid = siteid;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getRetail() {
		return retail;
	}
	public void setRetail(String retail) {
		this.retail = retail;
	}
	public Long getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(Long numdoc) {
		this.numdoc = numdoc;
	}
	public String getSitio() {
		return sitio;
	}
	public void setSitio(String sitio) {
		this.sitio = sitio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
