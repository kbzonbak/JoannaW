package bbr.b2b.regional.logistic.fillrate.classes;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.classes.OrderCriteriaDTO;
import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.BaseLogisticEJB3Server;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRateDetailW;
import bbr.b2b.regional.logistic.fillrate.entities.FillRate;
import bbr.b2b.regional.logistic.fillrate.entities.FillRateDetail;
import bbr.b2b.regional.logistic.fillrate.entities.FillRateDetailId;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRateDetailReportDataDTO;
import bbr.b2b.regional.logistic.items.classes.ItemServerLocal;
import bbr.b2b.regional.logistic.items.entities.Item;

@Stateless(name = "servers/fillrate/FillRateDetailServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FillRateDetailServer extends BaseLogisticEJB3Server<FillRateDetail, FillRateDetailId, FillRateDetailW> implements FillRateDetailServerLocal {

	private Map<String, String> mapGetFillRateDetailSQL = new HashMap<String, String>();
	{
		mapGetFillRateDetailSQL.put("ITEMSKU", "IT.INTERNALCODE");
		mapGetFillRateDetailSQL.put("NEEDEDUNITS", "FRD.TOTALUNITS");
		mapGetFillRateDetailSQL.put("RECEIVEDUNITS", "FRD.RECEIVEDUNITS");
		mapGetFillRateDetailSQL.put("UNITSFILLRATE", "UNITSFILLRATE");
		
	}		
	
	@EJB
	FillRateServerLocal fillrateserver;

	@EJB
	ItemServerLocal itemserver;

	public FillRateDetailW addFillRateDetail(FillRateDetailW fillratedetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateDetailW) addIdentifiable(fillratedetail);
	}

	public FillRateDetailW[] getFillRateDetails() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateDetailW[]) getIdentifiables();
	}

	public FillRateDetailW updateFillRateDetail(FillRateDetailW fillratedetail) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRateDetailW) updateIdentifiable(fillratedetail);
	}
	
	public FillRateDetailReportDataDTO[] getFillRateDetailReportByFillRate(Long fillrateid, int pagenumber, int rows, OrderCriteriaDTO[] orderby, boolean ispaginated) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getFillRateDetailQueryString(2, orderby);		
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fillrateid", fillrateid);
		query.setResultTransformer(new LowerCaseResultTransformer(FillRateDetailReportDataDTO.class));
		
		if(ispaginated){ // Si se quiere el reporte paginado
			query.setFirstResult((pagenumber - 1) * rows);
			query.setMaxResults(rows);
		}
		
		List list = query.list();
		
		FillRateDetailReportDataDTO[] result = (FillRateDetailReportDataDTO[]) list.toArray(new FillRateDetailReportDataDTO[list.size()]);		
		return result;	
	}
	
	public int getFillRateDetailCountByFillRate(Long fillrateid) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = getFillRateDetailQueryString(1, null);
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setLong("fillrateid", fillrateid);
				
		int totalreg = ((BigInteger)query.uniqueResult()).intValue();
		
		return totalreg;
	}

	@Override
	protected void copyRelationsEntityToWrapper(FillRateDetail entity, FillRateDetailW wrapper) {
		wrapper.setFillrateid(entity.getFillrate() != null ? new Long(entity.getFillrate().getId()) : new Long(0));
		wrapper.setItemid(entity.getItem() != null ? new Long(entity.getItem().getId()) : new Long(0));
	}

	@Override
	protected void copyRelationsWrapperToEntity(FillRateDetail entity, FillRateDetailW wrapper) throws OperationFailedException, NotFoundException {
		if (wrapper.getFillrateid() != null && wrapper.getFillrateid() > 0) {
			FillRate fillrate = fillrateserver.getReferenceById(wrapper.getFillrateid());
			if (fillrate != null) {
				entity.setFillrate(fillrate);
			}
		}
		if (wrapper.getItemid() != null && wrapper.getItemid() > 0) {
			Item item = itemserver.getReferenceById(wrapper.getItemid());
			if (item != null) {
				entity.setItem(item);
			}
		}
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "FillRateDetail";
	}

	@Override
	protected void setEntityname() {
		entityname = "FillRateDetail";
	}
	
	private String getFillRateDetailQueryString(int queryType, OrderCriteriaDTO[] orderby) throws OperationFailedException, AccessDeniedException{
		
		String SQL = "";
		String condicionOrderBy		= "";		

		if (queryType == 1){
			SQL = "SELECT " +
					"COUNT(*) ";			
		}
		else {
			SQL = "SELECT " +
					"IT.INTERNALCODE AS ITEMSKU, " +
					"FRD.TOTALUNITS AS NEEDEDUNITS, " +
					"FRD.RECEIVEDUNITS, " +
					"((FRD.RECEIVEDUNITS / FRD.TOTALUNITS) * 100) AS UNITSFILLRATE ";

			condicionOrderBy = getFillRateDetailByString(mapGetFillRateDetailSQL, orderby);
		}

		String fromSQL = "FROM " +
							"LOGISTICA.FILLRATEDETAIL FRD JOIN " +
							"LOGISTICA.ITEM IT ON IT.ID = FRD.ITEM_ID ";

		String whereSQL = "WHERE " +
							"FRD.FILLRATE_ID = :fillrateid ";

		SQL = SQL + 
				fromSQL +
					whereSQL +
						condicionOrderBy;		

		return SQL;	
	
	}
		
	private String getFillRateDetailByString(Map<String, String> mapQuery, OrderCriteriaDTO[] orderby) throws AccessDeniedException {
		if (orderby == null || orderby.length == 0)
			return "";
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		for (OrderCriteriaDTO ob : orderby) {
			String fieldname = mapQuery.get(ob.getPropertyname());
			if (fieldname == null)
				throw new AccessDeniedException("No se puede ordenar por el campo " + ob.getPropertyname());
			sb.append(fieldname);
			sb.append(ob.getAscending() ? " ASC, " : " DESC, ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}
}
