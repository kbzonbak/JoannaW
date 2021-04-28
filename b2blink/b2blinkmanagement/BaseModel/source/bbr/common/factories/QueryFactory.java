package bbr.common.factories;

import java.util.List;

import org.hibernate.SQLQuery;

import bbr.common.adtclasses.classes.PropertyParamQueryDTO;
import bbr.common.adtclasses.exceptions.OperationFailedException;

public class QueryFactory {


	public static void setParamQuery(List<PropertyParamQueryDTO> params, SQLQuery query) throws OperationFailedException {
		PropertyParamQueryDTO param = null;
		//Class c = null;
		for (int i = 0; i < params.size(); i++) {
			param = params.get(i);
			if(param.getValue() instanceof String)
				query.setString(param.getName(), (String)param.getValue());
			else if(param.getValue() instanceof Long)
				query.setLong(param.getName(), (Long)param.getValue());
			else if(param.getValue() instanceof Boolean)
				query.setBoolean(param.getName(), (Boolean)param.getValue());
			else if(param.getValue() instanceof Integer)
				query.setInteger(param.getName(), (Integer)param.getValue());
			else if(param.getValue() instanceof String[])
				query.setParameterList(param.getName(), (String[])param.getValue());				
			else if(param.getValue() instanceof Long[])
				query.setParameterList(param.getName(), (Long[])param.getValue());				
			else if(param.getValue() instanceof Boolean[])
				query.setParameterList(param.getName(), (Boolean[])param.getValue());		
			else if(param.getValue() instanceof Integer[])
				query.setParameterList(param.getName(), (Integer[])param.getValue());
			else
				throw new OperationFailedException("Error con tipo de datos al setear parámetros en la query");
			}
	}
	
	private QueryFactory() {
	}

}
