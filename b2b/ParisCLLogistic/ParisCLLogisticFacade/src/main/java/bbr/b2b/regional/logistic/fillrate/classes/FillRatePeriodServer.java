package bbr.b2b.regional.logistic.fillrate.classes;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.SQLQuery;

import bbr.b2b.common.adtclasses.exceptions.AccessDeniedException;
import bbr.b2b.common.adtclasses.exceptions.NotFoundException;
import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.common.factories.BeanExtenderFactory;
import bbr.b2b.common.factories.LowerCaseResultTransformer;
import bbr.b2b.regional.logistic.base.facade.LogisticElementServer;
import bbr.b2b.regional.logistic.fillrate.data.wrappers.FillRatePeriodW;
import bbr.b2b.regional.logistic.fillrate.entities.FillRatePeriod;
import bbr.b2b.regional.logistic.fillrate.report.classes.FillRatePeriodDTO;

@Stateless(name = "servers/fillrate/FillRatePeriodServer")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FillRatePeriodServer extends LogisticElementServer<FillRatePeriod, FillRatePeriodW> implements FillRatePeriodServerLocal {


	public FillRatePeriodW addFillRatePeriod(FillRatePeriodW fillrateperiod) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRatePeriodW) addElement(fillrateperiod);
	}
	public FillRatePeriodW[] getFillRatePeriods() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRatePeriodW[]) getIdentifiables();
	}
	public FillRatePeriodW updateFillRatePeriod(FillRatePeriodW fillrateperiod) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return (FillRatePeriodW) updateElement(fillrateperiod);
	}
	
	public FillRatePeriodW getFillRatePeriodByMonthAndYear(String month, int year) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		SQLQuery query = (SQLQuery) getSession().getNamedQuery("bbr.b2b.regional.logistic.fillrate.entities.FillRatePeriod.getFillRatePeriodsByMonthAndYear");
		
		query.setString("month", month);
		query.setInteger("year", Integer.valueOf(year));
		query.setResultTransformer(new LowerCaseResultTransformer(FillRatePeriodDTO.class));
		
		
		FillRatePeriodDTO fillRatePeriodDTO = (FillRatePeriodDTO) query.uniqueResult();
		fillRatePeriodDTO.setSince(Date.from(fillRatePeriodDTO.getSinceldt().atZone(ZoneId.systemDefault()).toInstant()));
		fillRatePeriodDTO.setUntil(Date.from(fillRatePeriodDTO.getUntilldt().atZone(ZoneId.systemDefault()).toInstant()));
		
		FillRatePeriodW result = new FillRatePeriodW();
		
		BeanExtenderFactory.copyProperties(fillRatePeriodDTO, result);	

		return result;
	}
	
	public FillRatePeriodW[] getFillRatePeriodsByLatest(int lastestperiods) throws AccessDeniedException, OperationFailedException, NotFoundException{
		
		String SQL = "SELECT " +
						"FRP.ID, " +
						"FRP.NAME, " +
						"FRP.SINCE as sinceldt, " +
						"FRP.UNTIL as untilldt" +
					"FROM " +
						"LOGISTICA.FILLRATEPERIOD FRP " +
					"ORDER BY FRP.SINCE DESC FETCH FIRST " + String.valueOf(lastestperiods) + " ROWS ONLY";
		
		SQLQuery query = (SQLQuery) getSession().createSQLQuery(SQL);
		query.setResultTransformer(new LowerCaseResultTransformer(FillRatePeriodDTO.class));
		
		List<FillRatePeriodDTO> dataList = query.list();
		
		//List<FillRatePeriodDTO> dataList = (List<FillRatePeriodDTO>)Arrays.asList(result);
		dataList.stream()
		            .forEach(s -> {
		                ((FillRatePeriodDTO) s).setSince(Date.from(((FillRatePeriodDTO) s).getSinceldt().atZone(ZoneId.systemDefault()).toInstant()));
		                ((FillRatePeriodDTO) s).setUntil(Date.from(((FillRatePeriodDTO) s).getUntilldt().atZone(ZoneId.systemDefault()).toInstant()));
		}); 
		FillRatePeriodW[] resultW = new FillRatePeriodW[dataList.size()];
		BeanExtenderFactory.copyProperties(dataList.toArray(new FillRatePeriodDTO[dataList.size()]), resultW, FillRatePeriodW.class);

		return resultW;
	}

	@Override
	protected void copyRelationsEntityToWrapper(FillRatePeriod entity, FillRatePeriodW wrapper) {
	}
	@Override
	protected void copyRelationsWrapperToEntity(FillRatePeriod entity, FillRatePeriodW wrapper) throws OperationFailedException, NotFoundException {
	}

	@Override
	protected void setEntitylabel() {
		entitylabel = "FillRatePeriod";
	}
	@Override
	protected void setEntityname() {
		entityname = "FillRatePeriod";
	}
}
