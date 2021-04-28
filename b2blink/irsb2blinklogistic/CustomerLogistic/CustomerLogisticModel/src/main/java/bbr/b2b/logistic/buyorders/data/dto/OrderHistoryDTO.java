package bbr.b2b.logistic.buyorders.data.dto;

import bbr.b2b.common.adtclasses.classes.BaseResultDTO;

public class OrderHistoryDTO extends BaseResultDTO {
	
	
	private HistoryHeaderDTO historyheader;

	private HistoryDetailDTO[] history;

	private int totalregs;


	public HistoryHeaderDTO getHistoryheader() {
		return historyheader;
	}

	public void setHistoryheader(HistoryHeaderDTO historyheader) {
		this.historyheader = historyheader;
	}

	public HistoryDetailDTO[] getHistory() {
		return history;
	}

	public void setHistory(HistoryDetailDTO[] history) {
		this.history = history;
	}

	public int getTotalregs() {
		return totalregs;
	}

	public void setTotalregs(int totalregs) {
		this.totalregs = totalregs;
	}


	
}
