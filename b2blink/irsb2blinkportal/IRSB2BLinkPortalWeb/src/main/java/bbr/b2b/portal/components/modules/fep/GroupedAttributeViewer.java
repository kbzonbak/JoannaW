package bbr.b2b.portal.components.modules.fep;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.fep.common.data.classes.ItemAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPGroupedAttributeValueDTO;

public class GroupedAttributeViewer extends VerticalLayout
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	private static final long			serialVersionUID	= 8889869398476733801L;

	private CPGroupedAttributeValueDTO	groupedItem			= null;
	private static final String 			HTTP_PREFIX			= "http";

	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public GroupedAttributeViewer(CPGroupedAttributeValueDTO groupedItem)
	{
		super();
		this.groupedItem = groupedItem;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	public VerticalLayout getLayout()
	{
		this.initializeView();
		return this;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
	private void initializeView()
	{
		if (this.groupedItem != null)
		{
			// Sección Header
			Label lblTitle = new Label(this.groupedItem.getTypename());
			lblTitle.addStyleName("bold_style");

			HorizontalLayout pnlHeader = new HorizontalLayout();
			pnlHeader.setWidth("100%");
			pnlHeader.setHeight("30px");
			pnlHeader.setMargin(false);
			pnlHeader.addStyleName("data-panel");
			pnlHeader.addComponent(lblTitle);
			this.addComponent(pnlHeader);
			if (this.groupedItem.getValues() != null && !this.groupedItem.getValues().isEmpty())
			{
				for (ItemAttributeValueDTO attributeValue : this.groupedItem.getValues())
				{
					Label lblName = new Label(attributeValue.getAttributevendorname());
					lblName.setDescription(attributeValue.getAttributevendorname());
					lblName.addStyleName("data-panel-name-label");
					lblName.setWidth("200px");
					lblName.addStyleName("truncate");

					Link lnkValue = null;
					Label lblValue = null;
					ExternalResource resource = null;
					
					if(attributeValue.getMetadata() != null)
					{
						resource = new ExternalResource(attributeValue.getMetadata());
						lnkValue = new Link(attributeValue.getValue(), resource);
						lnkValue.setTargetName("_blank");
						lnkValue.addStyleName("data-panel-name-value");
						lnkValue.setSizeFull();	
					}
					else if((attributeValue.getValue() != null) && attributeValue.getValue().toLowerCase().startsWith(HTTP_PREFIX))
					{
						resource = new ExternalResource(attributeValue.getValue());
						lnkValue = new Link(attributeValue.getValue(), resource);
						lnkValue.setTargetName("_blank");
						lnkValue.addStyleName("data-panel-name-value");
						lnkValue.setSizeFull();
					}
					else
					{
						String value = ( attributeValue.getUnit() != null && !attributeValue.getUnit().isEmpty() ) 
								? ( attributeValue.getValue() + " " + attributeValue.getUnit() ) 
										: attributeValue.getValue();
						lblValue = new Label(value);
						lblValue.addStyleName("data-panel-name-value");
						lblValue.setSizeFull();
					}
					
					HorizontalLayout pnlAttribute = new HorizontalLayout();
					pnlAttribute.setMargin(false);
					pnlAttribute.setSpacing(false);
					pnlAttribute.setWidth("100%");
					pnlAttribute.setHeight("100%");
					pnlAttribute.addStyleName("data-panel-row");
					pnlAttribute.addComponents(lblName, ((lnkValue != null) ? lnkValue : lblValue));
					
					if(lnkValue != null)
					{
						pnlAttribute.setExpandRatio(lnkValue, 1F);
					}
					else
					{
						pnlAttribute.setExpandRatio(lblValue, 1F);
					}

					this.addComponent(pnlAttribute);
				}
			}

			this.setMargin(false);
			this.setSpacing(false);
			this.setId("pnl_id");
		}
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
