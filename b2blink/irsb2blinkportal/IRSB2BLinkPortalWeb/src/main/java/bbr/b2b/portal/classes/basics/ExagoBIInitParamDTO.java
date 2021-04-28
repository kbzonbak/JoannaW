package bbr.b2b.portal.classes.basics;

import java.time.LocalDateTime;

public class ExagoBIInitParamDTO
{
	private SiteExagoBIDTO siteExagoBIDTO;
	private LocalDateTime startDate;
	private LocalDateTime untilDate;
	public SiteExagoBIDTO getSiteExagoBIDTO()
	{
		return siteExagoBIDTO;
	}
	public void setSiteExagoBIDTO(SiteExagoBIDTO siteExagoBIDTO)
	{
		this.siteExagoBIDTO = siteExagoBIDTO;
	}
	public LocalDateTime getStartDate()
	{
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate)
	{
		this.startDate = startDate;
	}
	public LocalDateTime getUntilDate()
	{
		return untilDate;
	}
	public void setUntilDate(LocalDateTime untilDate)
	{
		this.untilDate = untilDate;
	}
}
