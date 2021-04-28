package bbr.b2b.portal.classes.basics;

import bbr.b2b.common.adtclasses.classes.PageRangeDTO;
import cl.bbr.core.classes.events.BbrEvent;

public class PageRangesEvent extends BbrEvent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6592561189185174980L;
	public static final String SELECTED_ITEMS_OPTION = "selectedItemsOption";
	public static final String PAGE_RANGES_OPTION = "pageRangesOption";
	public static final String ALL_PAGES_OPTION = "allPagesOption";
	private String option;
	private PageRangeDTO[] pageRanges;
	
	public PageRangesEvent(String type)
	{
		super(type);
		this.option 		= PageRangesEvent.SELECTED_ITEMS_OPTION;
		this.setPageRanges(null);
	}

	public String getOption()
	{
		return option;
	}
	
	public void setOption(String option)
	{
		this.option = option;
	}

	public PageRangeDTO[] getPageRanges()
	{
		return pageRanges;
	}

	public void setPageRanges(PageRangeDTO[] pageRanges)
	{
		this.pageRanges = pageRanges;
	}
	
}
