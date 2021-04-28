package bbr.b2b.portal.components.modules.users.management.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox.NewItemProvider;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.components.basics.BbrButtonGroup;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.users.report.classes.PositionDTO;
import cl.bbr.core.classes.container.BbrHFieldContainer;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrUI;
import cl.bbr.core.components.basics.BbrWindow;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class NewPosition extends BbrWindow
{
	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================
	private static final long			serialVersionUID		= -8175764529639901821L;
	public static final String			BTN_ACCEPT				= "btnAccept";
	public static final String			BTN_CANCEL				= "btnCancel";
	private BbrTextField				txt_Value				= null;
	private BbrComboBox<PositionDTO>	cbx_AllPositions		= null;
	private List<PositionDTO>			allPositionsList		= null;
	private List<PositionDTO>			contactPositionsList	= new ArrayList<PositionDTO>();

	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================
	public NewPosition(BbrUI parent, List<PositionDTO> allPositionsList)
	{
		super(parent);
		this.allPositionsList = new ArrayList<>(allPositionsList);
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================
	@Override
	public void initializeView()
	{
		String labelWidth = "70px";

		this.txt_Value = new BbrTextField();
		this.txt_Value.addStyleName("bbr-fields");

		// positions combobox
		this.cbx_AllPositions = new BbrComboBox<PositionDTO>();
		this.cbx_AllPositions.setItemCaptionGenerator(PositionDTO::getName);
		this.cbx_AllPositions.setTextInputAllowed(true);
		this.cbx_AllPositions.setEmptySelectionAllowed(false);
		this.cbx_AllPositions.setPageLength(10);
		this.cbx_AllPositions.setPopupWidth("100%");
		this.cbx_AllPositions.setPlaceholder(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "define_position"));
		this.cbx_AllPositions.addValueChangeListener((ValueChangeListener<PositionDTO> & Serializable) e -> this.cbx_Position_ValueChangeHandler(e));
		this.cbx_AllPositions.setNewItemProvider((NewItemProvider<PositionDTO> & Serializable) e -> this.newItemHandler(e));
		this.cbx_AllPositions.setWidth("90%");
		this.cbx_AllPositions.focus();
		this.setContactPositionsGridDataProvider(this.allPositionsList);

		BbrHFieldContainer<BbrComboBox<PositionDTO>, String, String> fieldValue = new BbrHFieldContainer<>(this.getBbrUIParent(),
				I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "position"),
				this.cbx_AllPositions);
		fieldValue.setLabelWidth(labelWidth);
		fieldValue.setSizeFull();

		BbrButtonGroup bbrGroupButton = new BbrButtonGroup.BbrGroupButtonBuilder(this.getBbrUIParent())
				.withPrimaryButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "accept"))
				.withPrimaryButtonListener((ClickListener & Serializable) (e) -> this.btn_Accept_clickHandler())
				.withCancelButtonLabel(I18NManager.getI18NString(BbrUtilsResources.RES_GENERIC, "cancel"))
				.withCancelButtonListener((ClickListener & Serializable) (e) -> this.btn_Cancel_clickHandler()).build();
		bbrGroupButton.getPrimaryButton().setClickShortcut(KeyCode.ENTER);

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.addComponents(fieldValue, bbrGroupButton);
		mainLayout.setComponentAlignment(fieldValue, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(bbrGroupButton, Alignment.BOTTOM_CENTER);
		mainLayout.addStyleName("bbr-margin-windows");

		// Main Windows
		this.setWidth("430px");
		this.setHeight("200px");
		this.setResizable(false);
		String winTitle = I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "add_position");
		this.setCaption(winTitle);
		this.setContent(mainLayout);

	}

	// =====================================================================================================================================
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	private void btn_Accept_clickHandler()
	{
		this.getEvent(BTN_ACCEPT);
		this.close();
	}

	private void btn_Cancel_clickHandler()
	{
		this.getEvent(BTN_CANCEL);
		this.close();
	}

	private void getEvent(String eventType)
	{
		BbrEvent alertResponseEvent = new BbrEvent(eventType);
		if (eventType.equals(BTN_ACCEPT))
		{
			PositionDTO initParam = this.getInitParam();
			alertResponseEvent.setResultObject(initParam);
		}
		this.dispatchBbrEvent(alertResponseEvent);
	}

	private PositionDTO getInitParam()
	{
		PositionDTO initParam = new PositionDTO();
		PositionDTO resultItem = this.cbx_AllPositions.getSelectedValue();
		if (resultItem != null)
		{
			initParam = resultItem;
		}
		else
		{
			initParam.setName("");
		}
//		if (defaultPosition != null)
//		{
//			initParam.setContactproviderid(this.defaultPosition.getContactproviderid());
//			initParam.setObligatory(this.defaultPosition.getObligatory() != null ? this.defaultPosition.getObligatory() : false);
//			initParam.setPositionid(this.defaultPosition.getPositionid() != null ? this.defaultPosition.getPositionid() : -1);
//			initParam.setSelected(this.defaultPosition.getSelected() != null ? this.defaultPosition.getSelected() : false);
//		}

		return initParam;
	}

	private void cbx_Position_ValueChangeHandler(ValueChangeEvent<PositionDTO> e)
	{
		PositionDTO position = (PositionDTO) e.getValue();
		if (position != null && !position.getName().equalsIgnoreCase(I18NManager.getI18NString(BbrUtilsResources.RES_MODULES_MANAGEMENT, "define_position")))
		{
			PositionDTO contactPositionContained = this.getSamePositionContained(this.contactPositionsList, position);
			if (contactPositionContained == null)
			{
				if (position != null)
				{
					PositionDTO positionOfAll = this.getSamePositionContained(this.allPositionsList, position);
					if (positionOfAll != null)
					{
						// this.addToContactPositionsGrid(positionOfAll);
					}
					else
					{
						// this.btn_AddPosition_ClickHandler(position);
					}

				}
			}
			else
			{
				// this.dgd_ContactPositions.select(contactPositionContained);
			}
		}
	}

	private Optional<PositionDTO> newItemHandler(Object e)
	{
		String name = (String) e;
		PositionDTO newPosition = new PositionDTO();
		if (name != null)
		{
			newPosition.setName(name);
			PositionDTO positionContained = this.getSamePositionContained(this.allPositionsList, newPosition);
			if (positionContained == null)
			{
				this.allPositionsList.add(newPosition);
				cbx_AllPositions.setSelectedItem(newPosition);
			}
			else
			{
				cbx_AllPositions.setSelectedItem(positionContained);
			}
		}
		return Optional.of(newPosition);
	}

	private void setContactPositionsGridDataProvider(List<PositionDTO> positions)
	{
		ListDataProvider<PositionDTO> dataprovider = new ListDataProvider<>(positions);
		this.cbx_AllPositions.setDataProvider(dataprovider);
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> EVENTS HANDLERS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

	private PositionDTO getSamePositionContained(List<PositionDTO> positions, PositionDTO position)
	{
		if (positions != null && positions.size() > 0)
		{
			for (PositionDTO positionDTO : positions)
			{
				if (position != null)
				{
					if (positionDTO.getName().trim().equalsIgnoreCase(position.getName().trim()))
					{
						return positionDTO;
					}
				}
			}
		}
		return null;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PRIVATE METHODS
	// =====================================================================================================================================

}
