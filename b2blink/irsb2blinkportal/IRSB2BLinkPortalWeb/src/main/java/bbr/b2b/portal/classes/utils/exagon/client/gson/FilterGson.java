package bbr.b2b.portal.classes.utils.exagon.client.gson;

public class FilterGson {

	private String Name;

	private String EntityName;

	private String ColumnName;

	private String DataType;

	private String Operator;

	private String[] Values;

	private Boolean AndFlag;

	private Boolean Prompt;

	private Boolean GroupWithNext;

	public Boolean getAndFlag() {
		return AndFlag;
	}

	public String getColumnName() {
		return ColumnName;
	}

	public String getDataType() {
		return DataType;
	}

	public String getEntityName() {
		return EntityName;
	}

	public Boolean getGroupWithNext() {
		return GroupWithNext;
	}

	public String getName() {
		return Name;
	}

	public String getOperator() {
		return Operator;
	}

	public Boolean getPrompt() {
		return Prompt;
	}

	public String[] getValues() {
		return Values;
	}

	public void setAndFlag(Boolean andFlag) {
		AndFlag = andFlag;
	}

	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

	public void setEntityName(String entityName) {
		EntityName = entityName;
	}

	public void setGroupWithNext(Boolean groupWithNext) {
		GroupWithNext = groupWithNext;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public void setPrompt(Boolean prompt) {
		Prompt = prompt;
	}

	public void setValues(String[] values) {
		Values = values;
	}
}
