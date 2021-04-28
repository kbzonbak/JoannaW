package bbr.b2b.portal.classes.wrappers.management;

import java.util.Arrays;

import bbr.b2b.users.report.classes.CompanyClassificationDTO;
import cl.bbr.core.classes.basics.SearchCriterion;

public class CompanyClassificationFilterSectionInfo
{
	private Integer						filtertype				= null;
	private CompanyClassificationDTO[]	clasificationsSelected	= null;
	private String						value					= null;
	private SearchCriterion				selectedCriteria		= null;

	public SearchCriterion getSelectedCriteria()
	{
		return selectedCriteria;
	}

	public void setSelectedCriteria(SearchCriterion selectedCriteria)
	{
		this.selectedCriteria = selectedCriteria;
	}

	public Integer getFiltertype()
	{
		return filtertype;
	}

	public void setFiltertype(Integer filtertype)
	{
		this.filtertype = filtertype;
	}

	public CompanyClassificationDTO[] getClasificationsSelected()
	{
		return clasificationsSelected;
	}

	public void setClasificationsSelected(CompanyClassificationDTO[] clasificationsSelected)
	{
		this.clasificationsSelected = clasificationsSelected;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public CompanyClassificationFilterSectionInfo(Integer filtertype, CompanyClassificationDTO[] clasificationsSelected, String value, SearchCriterion selectedCriteria)
	{
		super();
		this.filtertype = filtertype;
		this.clasificationsSelected = clasificationsSelected;
		this.value = value;
		this.selectedCriteria = selectedCriteria;
	}

	public Long[] getClassificationsIds(CompanyClassificationDTO[] clasificationsSelected)
	{
		Long[] clasificationIds = clasificationsSelected != null ? Arrays.stream(clasificationsSelected).map(p -> p.getId()).toArray(Long[]::new) : new Long[0];
		return clasificationIds;
	}

	public String[] getClassificationsNames(CompanyClassificationDTO[] clasificationsSelected)
	{
		String[] clasificationNames = clasificationsSelected != null ? Arrays.stream(clasificationsSelected).map(p -> p.getName()).toArray(String[]::new) : new String[0];
		return clasificationNames;
	}

	public String getCaptionValueByType(Integer filtertype)
	{
		String result = "";
		switch (filtertype)
		{
			case -1:
				result = "";
				break;
			case 0:
				result = this.getClasificationsResultCaption(this.clasificationsSelected != null ? this.clasificationsSelected : new CompanyClassificationDTO[0]);
				break;
			case 1:
				result = this.getValue();
				break;
			case 2:
				result = this.getValue();
				break;
		}

		return result;
	}

	private String getClasificationsResultCaption(CompanyClassificationDTO[] clasificationsSelected)
	{
		String result = "";
		String[] classificationsNames = this.getClassificationsNames(clasificationsSelected);
		if (classificationsNames.length > 0)
		{
			String lastClassificationsName = classificationsNames[classificationsNames.length - 1];
			result = Arrays.stream(classificationsNames).map(name -> !name.equals(lastClassificationsName) ? name + ", " : name).reduce("", String::concat);
		}
		return result;
	}

}
