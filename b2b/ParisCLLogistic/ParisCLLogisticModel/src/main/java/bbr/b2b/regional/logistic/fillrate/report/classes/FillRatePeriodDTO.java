package bbr.b2b.regional.logistic.fillrate.report.classes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class FillRatePeriodDTO implements Serializable {

	private Long id;
	private String name;
	private Date since;
	private LocalDateTime sinceldt;
	private Date until;
	private LocalDateTime untilldt;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getSinceldt() {
		return sinceldt;
	}
	public void setSinceldt(LocalDateTime sinceldt) {
		this.sinceldt = sinceldt;
	}
	public LocalDateTime getUntilldt() {
		return untilldt;
	}
	public void setUntilldt(LocalDateTime untilldt) {
		this.untilldt = untilldt;
	}
	public String getName(){ 
		return this.name;
	}
	public Date getSince(){ 
		return this.since;
	}
	public Date getUntil(){ 
		return this.until;
	}
	public void setName(String name){ 
		this.name = name;
	}
	public void setSince(Date since){ 
		this.since = since;
	}
	public void setUntil(Date until){ 
		this.until = until;
	}
}
