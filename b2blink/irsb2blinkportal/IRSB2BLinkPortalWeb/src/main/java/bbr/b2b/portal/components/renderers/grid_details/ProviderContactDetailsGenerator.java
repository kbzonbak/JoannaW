package bbr.b2b.portal.components.renderers.grid_details;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.DetailsGenerator;

import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.ContactDataDTO;
import bbr.b2b.users.report.classes.ContactPhoneDTO;
import bbr.b2b.users.report.classes.ContactProviderPositionDTO;
import cl.bbr.core.classes.utilities.BbrUtils;

public class ProviderContactDetailsGenerator implements DetailsGenerator<ContactDataDTO> {

	private static final long serialVersionUID = -7307069774960676583L;


	@Override
	public Component apply(ContactDataDTO contact) 
	{
		HorizontalLayout layout = new HorizontalLayout();
		layout.setHeight("180px");
		layout.setMargin(false);
		layout.setSpacing(true);
		layout.setStyleName("cp-details-generator");

		String res_user = BbrUtils.getInstance().getPropertyValue(BbrUtilsResources.RES_B2B_PAGES, "default_user");
		Resource res = new ThemeResource(res_user);

		Image imgUser = new Image(null, res);
		imgUser.setWidth("100px");
		imgUser.setHeight("100px");

		Link linkEmail = new Link(contact.getContact().getEmail(), new ExternalResource("mailto:" + contact.getContact().getEmail()));

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setMargin(false);
		imageLayout.addComponents(imgUser,linkEmail);
		imageLayout.setExpandRatio(linkEmail, 1F);
		imageLayout.setComponentAlignment(imgUser, Alignment.TOP_CENTER);
		imageLayout.setComponentAlignment(linkEmail, Alignment.BOTTOM_CENTER);
		imageLayout.setWidth("490px");
		
		VerticalLayout columnPositions = new VerticalLayout();
		columnPositions.setWidth("360px");
		columnPositions.setHeight("180px");
		columnPositions.setMargin(false);
		columnPositions.setStyleName("bbr-overflow-auto override colum-details");
		Label lbl = new Label();
		lbl.setStyleName("colum-details");
		ContactProviderPositionDTO[] positions = contact.getContact().getPositionsAssigned();
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		for (ContactProviderPositionDTO position : positions)
		{
			sb.append("<li>" + position.getName()+"</li>");
		}
		sb.append("</ul>");
		lbl.setCaption(sb.toString());
		lbl.setCaptionAsHtml(true);
		columnPositions.addComponent(lbl);
		
		
		VerticalLayout columnPhones = new VerticalLayout();
		columnPhones.setWidth("240px");
		columnPhones.setHeight("180px");
		columnPhones.setMargin(false);
		columnPhones.setStyleName("bbr-overflow-auto override colum-details");
		Label lblPhone = new Label();
		lblPhone.setStyleName("colum-details");
		ContactPhoneDTO[] phones = contact.getContact().getPhones();
		StringBuilder sbPhone = new StringBuilder();
		sbPhone.append("<ul>");
		for (ContactPhoneDTO phone : phones)
		{
			sbPhone.append("<li>"+ phone.getNumber()+"</li>");
		}
		sbPhone.append("</ul>");
		lblPhone.setCaption(sbPhone.toString());
		lblPhone.setCaptionAsHtml(true);
		columnPhones.addComponent(lblPhone);
		
		VerticalLayout fill = new VerticalLayout();
		fill.setSizeFull();
		
		layout.addComponents(imageLayout);
		layout.addComponents(columnPositions);
		layout.addComponents(columnPhones);
		layout.addComponents(fill);
		layout.setComponentAlignment(imageLayout, Alignment.MIDDLE_LEFT);
		layout.setSpacing(false);


		return layout;
	}
}
