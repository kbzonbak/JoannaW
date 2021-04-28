package bbr.b2b.portal.components.modules.customer_service;

import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.wrappers.customer.ControlPanelCardInfo;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer;
import bbr.b2b.portal.components.basics.BbrHInputFieldContainer.BbrHInputFieldContainerBuilder;
import bbr.b2b.portal.constants.BbrUtilsResources;
import cl.bbr.core.components.basics.BbrUI;

public class CustomerInfoCard extends VerticalLayout
{
	private static final long	serialVersionUID				= -2378562799360447579L;
	public static final String	SCROLL_LAYOUT_STYLE				= "bbr-overflow-auto";
	public static final String	CARD_INFO_TITLE_STYLE			= "card-info-title";
	public static final String	ITEMS_LIST_COMPONENT_STYLE		= "items-list-component";
	public static final String	CARD_INFO_CONTENT_STYLE			= "card-info-content";
	public static final String	CARD_INFO_DATES_CONTAINER_STYLE	= "card-info-dates-container";
	public static final String	CARD_INFO_NUMBERS_BUTTON_STYLE	= "card-info-numbers-button";
	public static final String	CARD_INFO_TOTAL_STYLE			= "card-info-total";
	public static final String	ACTION_BUTTON_STYLE				= "control-panel-action-button";
	public static final String	TOTAL_ACTION_BUTTON_STYLE		= "total-control-panel-action-button";
	public static final String	IS_LATE_STYLE					= "late";
	public static final String	LABEL_SEPARATION_WITH			= "150px";

	public CustomerInfoCard(CustomerInfoCardBuilder customerInfoCardBuilder)
	{
		this.initializeView(customerInfoCardBuilder);
	}

	public void initializeView(CustomerInfoCardBuilder customerInfoCardBuilder)
	{
		String caption = customerInfoCardBuilder.caption != null ? customerInfoCardBuilder.caption : "";
		Label lbl_caption = new Label(caption);
		lbl_caption.setStyleName(CARD_INFO_TITLE_STYLE);

		VerticalLayout content = new VerticalLayout();
		content.setMargin(false);
		content.setSizeFull();
		content.setStyleName(CARD_INFO_CONTENT_STYLE);

		if (customerInfoCardBuilder.isTotalCard)
		{
			// CARD DE NUMEROS CON TOTALES
			VerticalLayout itemsListLayout = getItemsListLayout();
			for (ControlPanelCardInfo item : customerInfoCardBuilder.items)
			{
				item.setCaption(caption);
				item.setSelectedPanel(customerInfoCardBuilder.selectedPanel);

				Button btn_action = new Button(item.getValue());
				btn_action.setStyleName(CARD_INFO_NUMBERS_BUTTON_STYLE);
				btn_action.addStyleName(ACTION_BUTTON_STYLE);
				btn_action.setData(item);
				if (customerInfoCardBuilder.actionClickListener != null)
				{
					btn_action.addClickListener(customerInfoCardBuilder.actionClickListener);
				}

				BbrHInputFieldContainer info = new BbrHInputFieldContainerBuilder()
						.withCaption(item.getName())
						.withLabelWidth(LABEL_SEPARATION_WITH)
						.withComponent(btn_action)
						.build();
				info.setComponentAlignment(btn_action, Alignment.TOP_RIGHT);
				itemsListLayout.addComponent(info);
			}
			content.addComponent(itemsListLayout);
			content.setExpandRatio(itemsListLayout, 1F);

			Button btn_actionTotal = new Button(customerInfoCardBuilder.total != null ? String.valueOf(customerInfoCardBuilder.total) : "");
			btn_actionTotal.setStyleName(CARD_INFO_NUMBERS_BUTTON_STYLE);
			btn_actionTotal.addStyleName(ACTION_BUTTON_STYLE);
			btn_actionTotal.addStyleName(TOTAL_ACTION_BUTTON_STYLE);

			ControlPanelCardInfo itemTotal = new ControlPanelCardInfo();
			itemTotal.setClientId(-1L);// -1 Total/Todos
			itemTotal.setCaption(caption);
			itemTotal.setSelectedPanel(customerInfoCardBuilder.selectedPanel);
			itemTotal.setValue(String.valueOf(customerInfoCardBuilder.total));
			btn_actionTotal.setData(itemTotal);

			if (customerInfoCardBuilder.actionClickListener != null)
			{
				btn_actionTotal.addClickListener(customerInfoCardBuilder.actionClickListener);
			}

			BbrHInputFieldContainer total = new BbrHInputFieldContainerBuilder()
					.withCaption(customerInfoCardBuilder.totalCaption)
					.withLabelWidth(LABEL_SEPARATION_WITH)
					.withComponent(btn_actionTotal)
					.build();
			total.setStyleName(CARD_INFO_TOTAL_STYLE);
			if (customerInfoCardBuilder.items.size() > 4)
			{
				total.addStyleName("total_with_scroll");
			}
			total.setComponentAlignment(btn_actionTotal, Alignment.TOP_RIGHT);
			content.addComponent(total);
			content.setExpandRatio(total, 0.3F);
		}
		else
		{
			// CARD DE FECHAS
			VerticalLayout itemsListLayout = getItemsListLayout();
			for (ControlPanelCardInfo item : customerInfoCardBuilder.items)
			{
				item.setCaption(caption);
				item.setSelectedPanel(customerInfoCardBuilder.selectedPanel);
				Label dateItem = new Label(item.getValue());
				BbrHInputFieldContainer info = new BbrHInputFieldContainerBuilder()
						.withCaption(item.getName())
						.withLabelWidth(LABEL_SEPARATION_WITH)
						.withComponent(dateItem)
						.build();
				info.getLabel().addStyleName("item-label-left");
				if (item.isLate())
				{
					info.getLabel().addStyleName(IS_LATE_STYLE);
				}
				info.setComponentAlignment(info.getLabel(), Alignment.MIDDLE_CENTER);
				info.setComponentAlignment(dateItem, Alignment.MIDDLE_CENTER);
				itemsListLayout.addComponent(info);
			}
			content.addStyleName(CARD_INFO_DATES_CONTAINER_STYLE);
			content.addComponent(itemsListLayout);
		}

		this.addComponent(lbl_caption);
		this.addComponent(content);
		//
		this.setExpandRatio(content, 1F);
		this.setMargin(false);
	}

	private VerticalLayout getItemsListLayout()
	{
		VerticalLayout itemsListLayout = new VerticalLayout();
		itemsListLayout.setMargin(false);
		itemsListLayout.setSizeFull();
		itemsListLayout.setStyleName(ITEMS_LIST_COMPONENT_STYLE);
		itemsListLayout.addStyleName(SCROLL_LAYOUT_STYLE);
		return itemsListLayout;
	}

	public static class CustomerInfoCardBuilder
	{
		// Variables
		private String						selectedPanel		= null;
		private String						caption				= null;
		private List<ControlPanelCardInfo>	items				= null;
		private boolean						isTotalCard			= false;
		private Long						total				= null;
		private ClickListener				actionClickListener	= null;
		private BbrUI						bbrUIParent			= null;
		private String						totalCaption;

		public CustomerInfoCardBuilder withSelectedPanel(String selectedPanel)
		{
			this.selectedPanel = selectedPanel;
			return this;
		}

		public CustomerInfoCardBuilder withTotal(Long total)
		{
			this.total = total;
			this.isTotalCard = true;
			return this;
		}

		public CustomerInfoCardBuilder withActionClickListener(ClickListener actionClickListener)
		{
			this.actionClickListener = actionClickListener;
			return this;
		}

		public CustomerInfoCardBuilder withItems(List<ControlPanelCardInfo> items)
		{
			this.items = items;
			return this;
		}

		public CustomerInfoCardBuilder withCaption(String caption)
		{
			this.caption = caption;
			return this;
		}

		public CustomerInfoCardBuilder(BbrUI bbrUIParent)
		{
			this.bbrUIParent = bbrUIParent;
			this.totalCaption = I18NManager.getI18NString(this.bbrUIParent, BbrUtilsResources.RES_GENERIC, "total");
		}

		public CustomerInfoCard build()
		{
			return new CustomerInfoCard(this);
		}

	}

}
