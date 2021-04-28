package bbr.common.adtclasses.classes;

import java.io.Serializable;

public class PageW<T> implements Serializable {

	private PageInfoDTO pageinfo;
	private T[] values;

	public PageInfoDTO getPageinfo() {
		return pageinfo;
	}

	public T[] getValues() {
		return values;
	}

	public void setPageinfo(PageInfoDTO pageinfo) {
		this.pageinfo = pageinfo;
	}

	public void setValues(T[] values) {
		this.values = values;
	}
}
