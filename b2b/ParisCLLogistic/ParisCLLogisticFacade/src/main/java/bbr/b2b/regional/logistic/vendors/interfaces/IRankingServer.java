package bbr.b2b.regional.logistic.vendors.interfaces;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.adtclasses.interfaces.IElementServer;
import bbr.b2b.regional.logistic.vendors.entities.Ranking;
import bbr.b2b.regional.logistic.vendors.data.wrappers.RankingW;
import bbr.b2b.regional.logistic.vendors.report.classes.RankingDTO;

public interface IRankingServer extends IElementServer<Ranking, RankingW> {

	RankingW addRanking(RankingW ranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	RankingW[] getRankings() throws AccessDeniedException, OperationFailedException, NotFoundException;
	RankingW updateRanking(RankingW ranking) throws AccessDeniedException, OperationFailedException, NotFoundException;
	RankingDTO getLatestRanking() throws AccessDeniedException, OperationFailedException, NotFoundException;
	RankingDTO getRankingById(Long id) throws AccessDeniedException, OperationFailedException, NotFoundException;

}
