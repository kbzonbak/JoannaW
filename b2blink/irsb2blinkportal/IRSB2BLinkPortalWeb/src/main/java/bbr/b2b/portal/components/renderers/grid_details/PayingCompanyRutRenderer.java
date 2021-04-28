package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import bbr.b2b.users.report.classes.CompanyReportDataDTO;
import cl.bbr.core.components.grid.ComponentGenerator;

public class PayingCompanyRutRenderer implements ComponentGenerator
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4587106126251531664L;

	@Override
	public Component getComponent(Object bean) 
	{
		Component result = new Label("");
		if(bean != null)
		{
			CompanyReportDataDTO item = (CompanyReportDataDTO) bean ;
			if(item != null && item.getRut() != null && item.getDigit() != null) 
			{
				result = new Label(item.getRut()+ "-" +item.getDigit());
			}
		}
		 
		return result;
	}

}
