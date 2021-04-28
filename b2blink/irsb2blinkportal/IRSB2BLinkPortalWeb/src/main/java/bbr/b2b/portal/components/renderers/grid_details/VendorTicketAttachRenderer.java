//package bbr.b2b.portal.components.renderers.grid_details;
//
//import com.vaadin.server.Resource;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Image;
//
////import bbr.b2b.trac.report.classes.TicketReportDataDTO;
//import cl.bbr.core.classes.constants.CoreConstants;
//import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
//import cl.bbr.core.components.basics.BbrUI;
//import cl.bbr.core.components.grid.ComponentGenerator;
//
//public class VendorTicketAttachRenderer implements ComponentGenerator
//{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 4979404863431987238L;
//	private BbrUI bbrUIParent;
//
//	public VendorTicketAttachRenderer(BbrUI bbrUIParent)
//	{
//		this.bbrUIParent = bbrUIParent;
//	}
//	
//	@Override
//	public Component getComponent(Object bean) 
//	{
//		Component result = new HorizontalLayout();
//		if(bean != null)
//		{
//			TicketReportDataDTO item = (TicketReportDataDTO) bean ;
//			if(item != null && item.getAttached() == true) 
//			{
//				Resource res = BbrThemeResourcesUtils.getInstance().getResource(this.bbrUIParent,CoreConstants.PATH_BASE_IMAGES_UTILS+"icon_Attachment.png");
//
//				Image image = new Image(null, res);
//				HorizontalLayout imgLayout = new HorizontalLayout(image);
//				imgLayout.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
//				imgLayout.setWidth("100%");
//				result = imgLayout;
//			}
//
//		}
//
//		return result;
//	}
//
//}
