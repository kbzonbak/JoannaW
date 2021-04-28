package bbr.common.adtclasses.classes;

import java.util.List;

public class QueryDataDTO {

	private String sql;

	private List<PropertyParamQueryDTO> params;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<PropertyParamQueryDTO> getParams() {
		return params;
	}

	public void setParams(List<PropertyParamQueryDTO> params) {
		this.params = params;
	}

	public QueryDataDTO(String sql, List<PropertyParamQueryDTO> params) {
		super();
		this.sql = sql;
		this.params = params;
	}

}
