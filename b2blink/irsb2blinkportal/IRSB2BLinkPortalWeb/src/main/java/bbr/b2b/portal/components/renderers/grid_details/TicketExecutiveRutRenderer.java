//package bbr.b2b.portal.components.renderers.grid_details;
//
//import com.vaadin.ui.Component;
//import com.vaadin.ui.Label;
//
//import bbr.b2b.trac.report.classes.DedicatedAnalystDataDTO;
//import cl.bbr.core.classes.basics.Rut;
//import cl.bbr.core.components.grid.ComponentGenerator;
//
//public class TicketExecutiveRutRenderer implements ComponentGenerator
//{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 5359331586453486485L;
//
//	@Override
//	public Component getComponent(Object bean) 
//	{
//		Component result = new Label("");
//		if(bean != null)
//		{
//			DedicatedAnalystDataDTO item = (DedicatedAnalystDataDTO) bean ;
//			if(item != null && item.getUserrut() != null) 
//			{
//				result = new Label(Rut.getFormattedRut(item.getUserrut()));
//			}
//		}
//		 
//		return result;
//	}
//
//}
