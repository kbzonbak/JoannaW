package bbr.b2b.regional.logistic.vendors.classes;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.vendors.entities.Ranking;
import bbr.b2b.regional.logistic.vendors.report.classes.RankingDTO;
import bbr.b2b.regional.logistic.vendors.data.wrappers.RankingW;

@Stateless(name = "servers/vendors/RankingServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RankingServer extends LogisticElementServer<Ranking, RankingW> implements RankingServerLocal {

	public RankingW addRanking(RankingW ranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RankingW) addElement(ranking);
	}
	public RankingW[] getRankings() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RankingW[]) getIdentifiables();
	}
	public RankingW updateRanking(RankingW ranking) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (RankingW) updateElement(ranking);
	}
	
	public RankingDTO getLatestRanking() throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Ranking.getLatestRanking");
		query.setResultTransformer(new LowerCaseResultTransformer(RankingDTO.class));
		
		RankingDTO result = (RankingDTO)query.uniqueResult();		
				
		return result;
		
	}	
	
	public RankingDTO getRankingById(Long id) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.vendors.entities.Ranking.getRankingById");
		query.setResultTransformer(new LowerCaseResultTransformer(RankingDTO.class));
		query.setLong("rankingid", id);
		
		RankingDTO result = (RankingDTO) query.uniqueResult();
				
		return result;
	}
		
	@Override
	protected void copyRelationsEntityToWrapper(Ranking entity, RankingW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(Ranking entity, RankingW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "Ranking";
	}
	@Override
	protected void setEntityname() {
		entityname = "Ranking";
	}
}
