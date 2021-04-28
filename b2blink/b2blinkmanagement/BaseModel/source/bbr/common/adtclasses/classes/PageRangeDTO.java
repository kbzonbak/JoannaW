package bbr.common.adtclasses.classes;

import java.io.Serializable;

public class PageRangeDTO implements Serializable {

	private static final long serialVersionUID = -6366447167651843030L;

	private int sincePage;

	private int untilPage;

	public int getSincePage() {
		return sincePage;
	}

	public void setSincePage(int sincePage) {
		this.sincePage = sincePage;
	}

	public int getUntilPage() {
		return untilPage;
	}

	public void setUntilPage(int untilPage) {
		this.untilPage = untilPage;
	}
}
