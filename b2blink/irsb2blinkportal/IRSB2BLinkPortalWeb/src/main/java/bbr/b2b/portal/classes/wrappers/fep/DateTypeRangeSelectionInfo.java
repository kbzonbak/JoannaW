package bbr.b2b.portal.classes.wrappers.fep;

import java.time.LocalDateTime;

import cl.bbr.core.classes.constants.DownloadFormats;

public class DateTypeRangeSelectionInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public DateTypeRangeSelectionInfo(String type, LocalDateTime since, LocalDateTime until)
	{
		super();
		this.type = type;
		this.since = since;
		this.until = until;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private LocalDateTime	since		= null;
	private LocalDateTime	until		= null;
	private String		type		= null;
	private DownloadFormats	formatItem	= null;

	public DownloadFormats getFormatItem()
	{
		return formatItem;
	}

	public void setFormatItem(DownloadFormats formatItem)
	{
		this.formatItem = formatItem;
	}

	public LocalDateTime getSince()
	{
		return since;
	}

	public void setSince(LocalDateTime since)
	{
		this.since = since;
	}

	public LocalDateTime getUntil()
	{
		return until;
	}

	public void setUntil(LocalDateTime until)
	{
		this.until = until;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
