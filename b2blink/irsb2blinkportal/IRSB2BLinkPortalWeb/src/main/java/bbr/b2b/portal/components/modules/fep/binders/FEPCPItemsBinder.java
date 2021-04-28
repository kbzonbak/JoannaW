package bbr.b2b.portal.components.modules.fep.binders;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.vaadin.addons.ComboBoxMultiselect;

import com.vaadin.data.BeanPropertySet;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.PropertySet;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.provider.Query;
import com.vaadin.event.selection.MultiSelectionEvent;
import com.vaadin.event.selection.MultiSelectionListener;
import com.vaadin.shared.Registration;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import bbr.b2b.common.adtclasses.exceptions.OperationFailedException;
import bbr.b2b.fep.common.data.classes.ArticleTypeResultDTO;
import bbr.b2b.fep.common.data.classes.AttributeDTO;
import bbr.b2b.fep.common.data.classes.AttributeValueDTO;
import bbr.b2b.fep.common.data.classes.AttributeValuesResultDTO;
import bbr.b2b.fep.common.data.classes.DefinableAttributeDataDTO;
import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.cp.data.classes.CPItemDTO;
import bbr.b2b.portal.classes.binders.BbrAbstractBinder;
import bbr.b2b.portal.classes.binders.BbrBinderEvent;
import bbr.b2b.portal.classes.constants.EnumToolbarButton;
import bbr.b2b.portal.classes.constants.HtmlConstants;
import bbr.b2b.portal.classes.factory.EJBFactory;
import bbr.b2b.portal.classes.i18n.I18NManager;
import bbr.b2b.portal.classes.utils.app.AppUtils;
import bbr.b2b.portal.classes.utils.fep.FEPAttributesComponentsUtils;
import bbr.b2b.portal.classes.utils.fep.FEPItemsFactory;
import bbr.b2b.portal.classes.wrappers.fep.ItemValueMetadata;
import bbr.b2b.portal.classes.wrappers.fep.ProductsAssignment;
import bbr.b2b.portal.classes.wrappers.fep.ProductsAssignmentList;
import bbr.b2b.portal.components.basics.BbrSystemException;
import bbr.b2b.portal.components.basics.BbrUserException;
import bbr.b2b.portal.components.modules.fep.AddRelatedProduct;
import bbr.b2b.portal.components.modules.fep.FEPFilesGallery;
import bbr.b2b.portal.components.modules.fep.RelatedProductEditor;
import bbr.b2b.portal.components.modules.fep.ViewAttributeImageFile;
import bbr.b2b.portal.constants.BbrUtilsResources;
import bbr.b2b.portal.constants.FEPConstants;
import bbr.b2b.portal.utils.FEPUtils;
import cl.bbr.core.classes.constants.CoreConstants;
import cl.bbr.core.classes.events.BbrEvent;
import cl.bbr.core.classes.events.BbrEventListener;
import cl.bbr.core.classes.sets.BbrSetsUtils;
import cl.bbr.core.classes.utilities.BbrThemeResourcesUtils;
import cl.bbr.core.components.basics.BbrComboBox;
import cl.bbr.core.components.basics.BbrDateTimeField;
import cl.bbr.core.components.basics.BbrHSeparator;
import cl.bbr.core.components.basics.BbrItemSelectField;
import cl.bbr.core.components.basics.BbrModule;
import cl.bbr.core.components.widgets.bbrtextfield.BbrTextField;

public class FEPCPItemsBinder extends BbrAbstractBinder<CPItemDTO, ArticleTypeResultDTO>
{
	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	
	private BbrTextField																					txtProvider								= null;
	private BbrTextField																					txtArticleNumber						= null;

	private Binder<CPItemDTO>																			binderProvider							= new Binder<>();
	private Binder<CPItemDTO>																			binderArticle							= new Binder<>();

	private Registration																					regProvider								= null;
	private Registration																					regArticle								= null;

	private List<Component>																				dynComponents							= new ArrayList<>();

	private VerticalLayout																				mainLayout								= null;

	private List<DefinableAttributeDataDTO>														listVariant								= null;
	private List<DefinableAttributeDataDTO>														listGeneric								= null;

	private Map<String, BinderComponentPair<CPItemDTO, DefinableAttributeDataDTO>>	mapAttributeBinderComponentPair	= null;
	private Map<Long, ArrayList<AttributeValueDTO>>												mapAttributesListValues				= new HashMap<>();
	private Map<Long, ArrayList<Component>>														mapRelatedAttribute					= new HashMap<>();
	private Map<Long, Component>																		mapAttributeComponent				= new HashMap<>();
	private BbrModule																						bbrModule								= null;
	private Boolean																						showArticleNumber 					= false;
	
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	public FEPCPItemsBinder(BbrModule bbrModule, List<DefinableAttributeDataDTO> listGeneric, List<DefinableAttributeDataDTO> listVariant, Boolean showArticleNumber)
	{
		super(bbrModule.getBbrUIParent());
		this.listGeneric = listGeneric;
		this.listVariant = listVariant;
		this.bbrModule = bbrModule;
		this.showArticleNumber = showArticleNumber;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************
	protected void initializeControls()
	{
		this.txtArticleNumber 	= this.getStaticBbrTextField(I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_MODULES_FEP, "col_xls_reqiest_number"));
		this.txtProvider 			= this.getStaticBbrTextField(I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_MODULES_FEP, "provider"));
	}


	public void updateBinders()
	{
		this.updateArticleBinder();
		this.updateProviderBinder();
	}


	public void clearView()
	{
		this.removeRegistrations();

		this.txtArticleNumber.setValue("");
		this.txtProvider.setValue("");

		this.modelObject = null;

		this.cleanDataFields();
	}


	public void removeRegistrations()
	{
		this.removeProviderRegistrations();
		this.removeArticleRegistrations();
	}


	public AbstractOrderedLayout getLayout()
	{
		initializeControls();
		mainLayout = new VerticalLayout();
		
		if(this.showArticleNumber)
		{
			mainLayout.addComponents(txtArticleNumber);
		}
		
		mainLayout.addComponents(txtProvider);
		mainLayout.addStyleName("bbr-binder-layout");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		mainLayout.setWidth("100%");

		this.updateDynBinders();

		return mainLayout;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> OVERRIDDEN METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	private void cmp_changeHandler(ValueChangeEvent<?> e, CPItemDTO selectedProduct, Binder<CPItemDTO> binder)
	{
		if (e.isUserOriginated())
		{
			if (selectedProduct != null)
			{
				binder.writeBeanIfValid(selectedProduct);
			}

			BbrBinderEvent<CPItemDTO> binderEvent = new BbrBinderEvent<>(BbrBinderEvent.ITEMS_CHANGED);
			binderEvent.setResultObject(selectedProduct);
			this.dispatchBbrBinderEvent(binderEvent);
		}
	}


	// Cambio en los BbrTextField
	private void cmp_changeDynBbrTFHandler(ValueChangeEvent<String> e, DefinableAttributeDataDTO attribute)
	{
		if (e.isUserOriginated())
		{
			this.updateModel(attribute, e.getValue(), null);
		}
	}


	// Cambio en los BbrDateTimeField
	private void cmp_changeDynBbrDTFHandler(ValueChangeEvent<LocalDateTime> e, DefinableAttributeDataDTO attribute)
	{
		if (e.isUserOriginated())
		{
			String value = FEPAttributesComponentsUtils.getFormattedDateByAttributeType(attribute.getAtributedatatypeinternalname(), e.getValue());

			this.updateModel(attribute, value, null);
		}
	}


	// Cambio en los BbrComboBox
	private void cmp_changeDynBbrCBHandler(ValueChangeEvent<AttributeValueDTO> e, DefinableAttributeDataDTO attribute)
	{
		AttributeValueDTO item = e.getValue();
		String value = (item != null) ? item.getValue() : "";
		Set<AttributeValueDTO> selectedSet = new HashSet<>();
		if (item != null)
		{
			selectedSet.add(item);
		}

		if (e.isUserOriginated())
		{
			this.updateModel(attribute, value, null);
			this.updateMSelectManualValue(selectedSet, attribute, true);
		}
		else
		{
			updateMSelectManualValue(selectedSet, attribute, false);
		}
	}


	private void cmp_changeDynMSelectHandler(MultiSelectionEvent<AttributeValueDTO> e, DefinableAttributeDataDTO attribute)
	{
		String value = "";
		Set<AttributeValueDTO> selectedItems = e.getAllSelectedItems();
		if (selectedItems != null)
		{
			if (e.isUserOriginated())
			{
				value = FEPAttributesComponentsUtils.getMultiAttributeStringValue(selectedItems);

				this.updateModel(attribute, value, null);

				this.updateMSelectManualValue(e.getAllSelectedItems(), attribute, true);
			}
			else
			{
				updateMSelectManualValue(e.getAllSelectedItems(), attribute, false);
			}
		}
	}


	@SuppressWarnings("unchecked")
	private void updateMSelectManualValue(Set<AttributeValueDTO> selectedItems, DefinableAttributeDataDTO attribute, boolean reset)
	{

		ArrayList<Component> components = mapRelatedAttribute.get(attribute.getAttributeid());
		if (components != null && !components.isEmpty())
		{
			for (Component component : components)
			{
				Object objAttribute = ((AbstractComponent) component).getData();
				if (objAttribute != null)
				{
					if (component instanceof BbrComboBox<?>)
					{
						DefinableAttributeDataDTO childAttribute = (DefinableAttributeDataDTO) objAttribute;

						AttributeValueDTO[] values = BbrSetsUtils.getInstance().toArray(selectedItems, AttributeValueDTO.class);

						values = (values != null) ? this.getAttributeRelatedValues(childAttribute.getAttributeid(), values) : new AttributeValueDTO[0];

						// Si es un atributo de Jerarquia, ordenar por Codigos 
						
						Boolean isHierarchyAttrib = (	attribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL) ||
																attribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_MUHIERARCHI_RETAIL));
						
						if(isHierarchyAttrib)
						{
							values = Arrays.asList(values).stream().sorted((v1,v2) -> this.getCodeStringFromCodedValue(v1).compareTo(this.getCodeStringFromCodedValue(v2))).toArray(AttributeValueDTO[]::new);
						}
						
						mapAttributesListValues.put(childAttribute.getAttributeid(), new ArrayList<>(Arrays.asList(values)));

						((BbrComboBox<AttributeValueDTO>) component).setItems(values);
						updateDataForm();

						if (reset)
						{
							this.updateModel(childAttribute, "", null);
							((BbrComboBox<AttributeValueDTO>) component).setSelectedItem(null);
						}
					}
					else if (component instanceof ComboBoxMultiselect<?>)
					{
						DefinableAttributeDataDTO childAttribute = (DefinableAttributeDataDTO) objAttribute;

						AttributeValueDTO[] values = BbrSetsUtils.getInstance().toArray(selectedItems, AttributeValueDTO.class);

						values = (values != null) ? this.getAttributeRelatedValues(childAttribute.getAttributeid(), values) : new AttributeValueDTO[0];

						mapAttributesListValues.put(childAttribute.getAttributeid(), new ArrayList<>(Arrays.asList(values)));

						((ComboBoxMultiselect<AttributeValueDTO>) component).setItems(values);
						((ComboBoxMultiselect<AttributeValueDTO>) component).deselectAll();

						if (reset)
						{
							this.updateModel(childAttribute, "", null);
						}
					}
				}
			}
		}
	}


	private void cmp_changeDynCKBHandler(ValueChangeEvent<Boolean> e, DefinableAttributeDataDTO attribute)
	{
		String value = (e.getValue() == true) ? I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_GENERIC, "yes_upper_nomark") : I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_GENERIC, "no_upper");

		if((e.getComponent() != null) && (e.getComponent() instanceof CheckBox))
		{
			((CheckBox)e.getComponent()).setCaption("(" + value + ")"); 
		}

		this.updateModel(attribute, value, null);
	}
	
	
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	private VerticalLayout getFieldContainer(Component component, DefinableAttributeDataDTO definableAttribute)
	{
		String caption = (definableAttribute.getMandatory()) ? definableAttribute.getAttributevendorname() + " " + HtmlConstants.RED_ASTERISK_BETWEEN_PARENTHESES : definableAttribute.getAttributevendorname();

		HorizontalLayout pnlComponent = new HorizontalLayout(component);

		Label lblCaption = new Label(caption, ContentMode.HTML);
		lblCaption.addStyleName("bbr-binder-caption");

		HorizontalLayout pnlCaption = new HorizontalLayout(lblCaption);
		pnlCaption.setWidth("100%");
		pnlCaption.setMargin(false);
		pnlCaption.setSpacing(false);
		pnlCaption.setExpandRatio(lblCaption, 1F);

		if (definableAttribute.getAttributeinfo() != null && definableAttribute.getAttributeinfo().getAttribute() != null)
		{
			AttributeDTO attribute = definableAttribute.getAttributeinfo().getAttribute();
			if (attribute.getFilename() != null)
			{
				Button btnImagetHelp = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUI(), CoreConstants.PATH_BASE_IMAGES_UTILS + "icon_ImageHelp_15x15.png"));
				btnImagetHelp.setDescription(I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_MODULES_FEP, "click_image"));
				btnImagetHelp.addClickListener(new ClickListener()
				{
					private static final long serialVersionUID = -8276024761987513693L;


					@Override
					public void buttonClick(ClickEvent event)
					{
						ViewAttributeImageFile imageWin = new ViewAttributeImageFile(getBbrUI(), attribute);
						imageWin.open(true, true, bbrModule);
					}
				});
				btnImagetHelp.addStyleName("panel-mini-button");
				pnlCaption.addComponent(btnImagetHelp);
				pnlCaption.setComponentAlignment(btnImagetHelp, Alignment.TOP_RIGHT);
			}
			
			if (definableAttribute.getAtributedatatypeinternalname().equals(FEPConstants.ATTRIBUTE_NAME_TYPE_RELATEDPRODUCTLIST))
			{
				Button btnRelatedProduct = new Button(BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUI(), CoreConstants.PATH_BASE_IMAGES_BUTTONS + "icon_AddTicket.png"));
				//Button btnRelatedProduct = new Button("", EnumToolbarButton.ADD_PRIMARY.getResource());
				btnRelatedProduct.setDescription("Seleccionar complementarios");
				btnRelatedProduct.addClickListener((ClickListener & Serializable) e -> this.bbrTextSelectFieldForRelatedProduct_selectedHandler(e, (TextArea) component));
				
				//btnRelatedProduct.addStyleName("bbr-binder-caption");
				//btnRelatedProduct.setStyleName("primary");
				btnRelatedProduct.addStyleName("toolbar-button");
				btnRelatedProduct.setWidth("40px");
				
				BbrHSeparator separator = new BbrHSeparator("5px");
				pnlComponent.addComponents(separator, btnRelatedProduct);
				
				pnlComponent.setComponentAlignment(btnRelatedProduct, Alignment.MIDDLE_CENTER);
			}

			if (attribute.getUserhelp() != null && attribute.getUserhelp().length() > 0)
			{
				Button btnTextHelp = new Button("", BbrThemeResourcesUtils.getInstance().getResource(this.getBbrUI(), CoreConstants.PATH_BASE_IMAGES_UTILS + "icon_Help_15x15.png"));
				btnTextHelp.setDescription(attribute.getUserhelp());
				btnTextHelp.addStyleName("panel-mini-button");
				pnlCaption.addComponent(btnTextHelp);
				pnlCaption.setComponentAlignment(btnTextHelp, Alignment.TOP_RIGHT);
			}
			if (FEPUtils.attributeIsaNumber(definableAttribute.getAtributedatatypeinternalname()) && definableAttribute.getAttributeinfo().getAttribute().getUnit().trim() != "")
			{

				Label lblUnit = new Label(definableAttribute.getAttributeinfo().getAttribute().getUnit());
				lblUnit.addStyleName("bbr-binder-caption");
				lblUnit.addStyleName("italic");
				lblUnit.setWidth("100px");

				BbrHSeparator separator = new BbrHSeparator("5px");

				pnlComponent.addComponents(separator, lblUnit);
				pnlComponent.setComponentAlignment(lblUnit, Alignment.BOTTOM_CENTER);
			}
		}

		pnlComponent.setWidth("100%");
		pnlComponent.setMargin(false);
		pnlComponent.setSpacing(false);
		pnlComponent.setExpandRatio(component, 1F);

		VerticalLayout result = new VerticalLayout(pnlCaption, pnlComponent);
		result.setExpandRatio(pnlComponent, 1F);

		result.setWidth("100%");
		result.setMargin(false);
		result.setSpacing(false);
		return result;
	}


	@SuppressWarnings("rawtypes")
	public void updateDynBinders()
	{
		this.cleanDynamicComponents();

		if (this.listGeneric != null && this.listGeneric.size() > 0)
		{
			for (DefinableAttributeDataDTO genericAttribute : listGeneric)
			{
				Component component = this.getDynComponent(genericAttribute);

				VerticalLayout fieldContainer = null;

				fieldContainer = getFieldContainer(component, genericAttribute);

				dynComponents.add(component);
				mainLayout.addComponent(fieldContainer);

				BinderComponentPair<CPItemDTO, DefinableAttributeDataDTO> bcPair = new BinderComponentPair<>(CPItemDTO.class, (HasValue) component, genericAttribute);
				mapAttributeBinderComponentPair.put(genericAttribute.getAttributeinternalname(), bcPair);

				this.mapAttributeComponent.put(genericAttribute.getAttributeid(), component);
				this.updateMapAttributeParent(genericAttribute, component);

			}
		}

		if (this.listVariant != null && this.listVariant.size() > 0)
		{
			for (DefinableAttributeDataDTO variantAttribute : listVariant)
			{
				Component component = this.getDynComponent(variantAttribute);

				dynComponents.add(component);

				VerticalLayout fieldContainer = null;

				fieldContainer = getFieldContainer(component, variantAttribute);

				mainLayout.addComponent(fieldContainer);

				BinderComponentPair<CPItemDTO, DefinableAttributeDataDTO> bcPair = new BinderComponentPair<>(CPItemDTO.class, (HasValue) component, variantAttribute);
				mapAttributeBinderComponentPair.put(variantAttribute.getAttributeinternalname(), bcPair);

				this.updateMapAttributeParent(variantAttribute, component);
			}
		}
	}


	public void updateView(CPItemDTO modelObject)
	{
		this.modelObject = modelObject;
		this.removeRegistrations();
		this.cleanDataFields();
		this.updateBinders();
		this.updateDataForm();
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************


	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	
	private void removeArticleRegistrations()
	{
		if (txtArticleNumber != null)
		{
			binderArticle.removeBinding(txtArticleNumber);

			if (regArticle != null)
			{
				regArticle.remove();
			}
		}
	}
	
	
	private void removeProviderRegistrations()
	{
		if (txtProvider != null)
		{
			binderProvider.removeBinding(txtProvider);
			
			if (regProvider != null)
			{
				regProvider.remove();
			}
		}
	}


	private void updateProviderBinder()
	{
		if (this.modelObject != null)
		{
			PropertySet<CPItemDTO> ps = BeanPropertySet.get(CPItemDTO.class);
			binderProvider = Binder.withPropertySet(ps);

			regProvider = txtProvider.addValueChangeListener((ValueChangeListener<String> & Serializable) e -> cmp_changeHandler(e, this.modelObject, binderProvider));

			txtProvider.setValue((this.modelObject.getProvidersocialreason() != null) ? this.modelObject.getProvidersocialreason() : "");

			binderProvider.forField(txtProvider).bind("providersocialreason");
		}
	}
	
	
	private void updateArticleBinder()
	{
		if (this.modelObject != null)
		{
			PropertySet<CPItemDTO> ps = BeanPropertySet.get(CPItemDTO.class);
			binderArticle = Binder.withPropertySet(ps);
			
			regArticle = txtArticleNumber.addValueChangeListener((ValueChangeListener<String> & Serializable) e -> cmp_changeHandler(e, this.modelObject, binderArticle));
			
			txtArticleNumber.setValue((this.modelObject.getId() != null) ? this.modelObject.getId().toString() : "");
			txtArticleNumber.setVisible((this.modelObject.getId() != null) && (this.modelObject.getId() > 0));
						
			binderArticle.forField(txtArticleNumber).withConverter(new StringToLongConverter("")).bind("id");
		}
	}


	@SuppressWarnings("unchecked")
	private void updateDataForm()
	{
		if (this.modelObject != null)
		{
			CPItemAttributeValueDTO[] attributes = this.modelObject.getAttributevalues();
			if (attributes != null && attributes.length > 0)
			{
				for (CPItemAttributeValueDTO attribute : attributes)
				{
					BinderComponentPair<CPItemDTO, DefinableAttributeDataDTO> componentBinder = mapAttributeBinderComponentPair.get(attribute.getAttributeinternalname());
					
					if(componentBinder != null)
					{
						if (componentBinder.getComponent() instanceof BbrTextField)
						{
							componentBinder.getComponent().setValue(attribute.getValue());
						}
						else if (componentBinder.getComponent() instanceof TextArea){
							
							componentBinder.getComponent().setValue(attribute.getValue());
						}
						else if (componentBinder.getComponent() instanceof BbrComboBox<?>)
						{
							AttributeValueDTO selectedItem = this.getSelectedAttributeValue(attribute.getAttributeid(), attribute.getValue());
							if (selectedItem != null)
							{
								((BbrComboBox<AttributeValueDTO>) componentBinder.getComponent()).setSelectedItem(selectedItem);
							}
						}
						else if (componentBinder.getComponent() instanceof ComboBoxMultiselect<?>)
						{
							AttributeValueDTO[] arrItems = ((ComboBoxMultiselect<AttributeValueDTO>) componentBinder.getComponent()).getDataProvider().fetch(new Query<>()).toArray(AttributeValueDTO[]::new);
							if (arrItems != null && arrItems.length > 0)
							{
								AttributeValueDTO[] arrSelecteds = FEPAttributesComponentsUtils.getAttributeFromStringValue(attribute.getValue(), arrItems);
								if (arrSelecteds != null && arrSelecteds.length > 0)
								{
									((ComboBoxMultiselect<AttributeValueDTO>) componentBinder.getComponent()).select(arrSelecteds);
								}
							}
						}
						else if (componentBinder.getComponent() instanceof CheckBox)
						{
							boolean value = (attribute.getValue().equals(I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_GENERIC, "yes_upper_nomark")));
							((CheckBox) componentBinder.getComponent()).setValue(value);
						}
						else if (componentBinder.getComponent() instanceof BbrDateTimeField)
						{
							LocalDateTime value = FEPAttributesComponentsUtils.getDateByFormattedDateAttributeType(attribute.getUserdatatypeinternalname(), attribute.getValue());
							((BbrDateTimeField) componentBinder.getComponent()).setValue(value);
						}
						else if (componentBinder.getComponent() instanceof BbrItemSelectField<?>)
						{
							((BbrItemSelectField<CPItemAttributeValueDTO>) componentBinder.getComponent()).setValue(attribute);
						}
						if (attribute.getCanwrite())
						{
							componentBinder.getComponent().setReadOnly(false);
						}
					}
				}
			}
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cleanDataFields()
	{
		if (mapAttributeBinderComponentPair != null && !mapAttributeBinderComponentPair.isEmpty())
		{
			for (BinderComponentPair<CPItemDTO, DefinableAttributeDataDTO> component : mapAttributeBinderComponentPair.values())
			{
				if (component.getComponent() instanceof BbrTextField)
				{
					((BbrTextField) component.getComponent()).setValue("");
				}
				else if (component.getComponent() instanceof BbrComboBox<?>)
				{
					((BbrComboBox) component.getComponent()).setSelectedItem(null);
				}
				else if (component.getComponent() instanceof ComboBoxMultiselect<?>)
				{
					if (((ComboBoxMultiselect) component.getComponent()).getDataProvider().size(new Query<>()) > 0)
					{
						((ComboBoxMultiselect) component.getComponent()).deselectAll();
					}
				}
				else if (component.getComponent() instanceof CheckBox)
				{
					((CheckBox) component.getComponent()).setValue(false);
				}
				else if (component.getComponent() instanceof BbrDateTimeField)
				{
					((BbrDateTimeField) component.getComponent()).setValue(null);
				}
				else if (component.getComponent() instanceof BbrItemSelectField)
				{
					((BbrItemSelectField) component.getComponent()).setValue(null);
				}

				boolean readOnly = (this.modelObject == null || !component.getData().getCanwrite());
				component.getComponent().setReadOnly(readOnly);
			}
		}
	}


	private void cleanDynamicComponents()
	{
		if (dynComponents != null && dynComponents.size() > 0 && mainLayout != null)
		{
			for (Component component : dynComponents)
			{
				mainLayout.removeComponent(component);
			}
		}

		this.mapAttributeBinderComponentPair = new HashMap<>();
	}


	private Component getDynComponent(DefinableAttributeDataDTO attribute)
	{
		Component result = null;
		if (attribute != null)
		{
			String attributeType = attribute.getAtributedatatypeinternalname();

			if (FEPAttributesComponentsUtils.isBbrTextFieldAttributeType(attributeType))
			{
				result = this.getBbrTextField(attribute);
			}
			else if (FEPAttributesComponentsUtils.isBbrComboBoxAttributeType(attributeType))
			{
				result = this.getBbrComboBox(attribute);
			}
			else if (FEPAttributesComponentsUtils.isComboBoxMultiselectAttributeType(attributeType))
			{
				result = this.getComboBoxMultiselect(attribute);
			}
			else if (FEPAttributesComponentsUtils.isBbrDateTimeFieldAttributeType(attributeType))
			{
				result = this.getBbrDateTimeField(attribute);
			}
			else if (FEPAttributesComponentsUtils.isCheckBoxAttributeType(attributeType))
			{
				result = this.getCheckBoxField(attribute);
			}
			else if (FEPAttributesComponentsUtils.isBbrItemSelectFieldAttributeType(attributeType))
			{
				result = this.getBbrItemSelectFieldForImage(attribute);
			}
			else if (FEPAttributesComponentsUtils.isBbrItemSelectFieldAttributeTypeRelatedProduct(attributeType))
			{
				result = this.getBbrItemSelectFieldForRelatedProduct(attribute);
			}
			else
			{
				BbrTextField txtNA = new BbrTextField();
				txtNA.setValue("N/A");
				txtNA.setEnabled(false);
				txtNA.addStyleName("bbr-binder-readonly-field");

				result = txtNA;
			}
		}

		return result;
	}


	private void updateModel(DefinableAttributeDataDTO attribute, String value, String metadata)
	{
		try
		{
			if (this.modelObject != null)
			{
				updateItemAttributeValue(this.modelObject, attribute, value, metadata);

				BbrBinderEvent<CPItemDTO> binderEvent = new BbrBinderEvent<>(BbrBinderEvent.DYN_ITEMS_CHANGED);
				binderEvent.setResultObject(this.modelObject);
				binderEvent.setAttribute(attribute);
				binderEvent.setAttributeValue(value);
				binderEvent.setResultObject(this.modelObject);
				this.dispatchBbrBinderEvent(binderEvent);
			}
		}
		catch (OperationFailedException e)
		{
			e.printStackTrace();
		}
	}


	private void updateItemAttributeValue(CPItemDTO item, DefinableAttributeDataDTO attribute, String value, String metadata) throws OperationFailedException
	{
		Map<String, CPItemAttributeValueDTO> mapAttribute = item.getAttributevaluesAsMap();

		CPItemAttributeValueDTO cpAttribute = mapAttribute.get(attribute.getAttributeinternalname());

		if (cpAttribute == null)
		{
			cpAttribute = FEPItemsFactory.getCPAttributeFromMPAttribute(CPItemAttributeValueDTO.getNewAttributevalueFromDefinable(attribute));
			cpAttribute.setItemid(item.getId());
		}

		cpAttribute.setValue(value);
		cpAttribute.setMetadata(metadata);

		mapAttribute.put(attribute.getAttributeinternalname(), cpAttribute);

		CPItemAttributeValueDTO[] attributes = mapAttribute.values().toArray(new CPItemAttributeValueDTO[mapAttribute.size()]);

		item.setAttributevalues(attributes);
	}


	private AttributeValueDTO getSelectedAttributeValue(Long attributeName, String value)
	{
		AttributeValueDTO result = null;

		ArrayList<AttributeValueDTO> arr_List = this.mapAttributesListValues.get(attributeName);
		if (arr_List != null && !arr_List.isEmpty())
		{
			Optional<AttributeValueDTO> item = arr_List.stream().filter(attribute -> attribute.getValue().equalsIgnoreCase(value)).findFirst();
			if (item.isPresent())
			{
				result = item.get();
			}
		}

		return result;
	}


	/**
	 * METODOS PARA CREAR LOS COMPONENTES POR EL TIPO DE ATRIBUTO
	 */

	private BbrTextField getStaticBbrTextField(String caption)
	{
		BbrTextField result = new BbrTextField(caption);
		result.addStyleName("bbr-binder-readonly-field");
		result.setWidth("100%");
		result.setReadOnly(true);

		return result;
	}


	private BbrTextField getBbrTextField(DefinableAttributeDataDTO attribute)
	{
		BbrTextField result = new BbrTextField();
		result.setWidth("100%");
		result.setReadOnly(true);
		
		if (attribute != null)
		{
			result.setData(attribute);

			String attributeType = attribute.getAtributedatatypeinternalname();
			Boolean isStringType = attributeType.equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_STRING);
			
			if (attribute.getCanwrite())
			{
				result.addStyleName((isStringType) ? "bbr-binder-field" : "bbr-binder-field-right");
				result.addValueChangeListener((ValueChangeListener<String> & Serializable) e -> cmp_changeDynBbrTFHandler(e, attribute));
				result.setRestrict(FEPAttributesComponentsUtils.getTextRestrictByAttributeType(attribute.getAtributedatatypeinternalname()));
			}
			else
			{
				result.addStyleName((isStringType) ? "bbr-binder-readonly-field" : "bbr-binder-readonly-field-right");
			}
		}

		return result;
	}


	private BbrDateTimeField getBbrDateTimeField(DefinableAttributeDataDTO attribute)
	{
		BbrDateTimeField result = new BbrDateTimeField();

		result = FEPAttributesComponentsUtils.updateBbrDateTimeFieldByAttributeType(attribute.getAtributedatatypeinternalname(), result);
		result.setWidth("100%");
		result.setReadOnly(true);

		if (attribute != null)
		{
			result.setData(attribute);

			if (attribute.getCanwrite())
			{
				result.addValueChangeListener((ValueChangeListener<LocalDateTime> & Serializable) e -> cmp_changeDynBbrDTFHandler(e, attribute));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-datefield");
			}
		}

		return result;
	}


	private BbrComboBox<AttributeValueDTO> getBbrComboBox(DefinableAttributeDataDTO attribute)
	{
		BbrComboBox<AttributeValueDTO> result = new BbrComboBox<>();
		result.setTextInputAllowed(false);
		result.setEmptySelectionAllowed(false);
		result.setItemCaptionGenerator(AttributeValueDTO::getValue);
		result.setWidth("100%");

		if (attribute != null)
		{
			result.setData(attribute);

			if (attribute.getAttributeinfo() != null)
			{
				if (attribute.getAttributeinfo().getRelatedattributes() == null)
				{
					// Si es un atributo de Jerarquia, ordenar por Codigos 
					
					Boolean isHierarchyAttrib = (attribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_HIERARCHI_RETAIL) ||
														  attribute.getAtributedatatypeinternalname().equalsIgnoreCase(FEPConstants.ATTRIBUTE_NAME_TYPE_MUHIERARCHI_RETAIL));
					
					AttributeValueDTO[] values = this.getAttributeRelatedValues(attribute.getAttributeid(), null);
					
					if (values != null && values.length > 0)
					{
						if(isHierarchyAttrib)
						{
							values = Arrays.asList(values).stream().sorted((v1,v2) -> this.getCodeStringFromCodedValue(v1).compareTo(this.getCodeStringFromCodedValue(v2))).toArray(AttributeValueDTO[]::new);
						}
						
						mapAttributesListValues.put(attribute.getAttributeid(), new ArrayList<>(Arrays.asList(values)));

						result.setItems(values);
					}
				}
			}

			if (attribute.getCanwrite())
			{
				result.addStyleName("bbr-binder-field");
				result.addValueChangeListener((ValueChangeListener<AttributeValueDTO> & Serializable) e -> cmp_changeDynBbrCBHandler(e, attribute));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-field");
			}
		}

		result.setReadOnly(true);

		return result;
	}
	
	
	private String getCodeStringFromCodedValue(AttributeValueDTO value)
	{
		String result = "";
		
		if(value != null)
		{
			result = FEPUtils.getCodeFromCodedValue(value.getValue(), "");
		}
		
		return result;
	}


	private ComboBoxMultiselect<AttributeValueDTO> getComboBoxMultiselect(DefinableAttributeDataDTO attribute)
	{
		ComboBoxMultiselect<AttributeValueDTO> result = new ComboBoxMultiselect<>();
		result.setTextInputAllowed(false);
		result.setItemCaptionGenerator(AttributeValueDTO::getValue);
		result.setWidth("100%");

		if (attribute != null)
		{
			result.setData(attribute);

			if (attribute.getAttributeinfo() != null)
			{
				if (attribute.getAttributeinfo().getRelatedattributes() == null)
				{
					AttributeValueDTO[] values = this.getAttributeRelatedValues(attribute.getAttributeid(), null);

					if (values != null && values.length > 0)
					{
						mapAttributesListValues.put(attribute.getAttributeid(), new ArrayList<>(Arrays.asList(values)));

						result.setItems(values);
					}
				}
			}

			if (attribute.getCanwrite())
			{
				result.addStyleName("bbr-binder-field");

				mapRelatedAttribute.get(attribute.getAttributeid());
				result.addSelectionListener((MultiSelectionListener<AttributeValueDTO> & Serializable) e -> cmp_changeDynMSelectHandler(e, attribute));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-field");
			}
		}

		result.setReadOnly(true);

		return result;
	}


	private CheckBox getCheckBoxField(DefinableAttributeDataDTO attribute)
	{
		CheckBox result = new CheckBox();
		result.setWidth("100%");
		result.setReadOnly(true);

		if (attribute != null)
		{
			result.setData(attribute);

			if (attribute.getCanwrite())
			{
				result.addStyleName("bbr-binder-field");
				result.addStyleName("bbr-binder-checkbox");
				result.setValue(false);
				result.setCaption("(" + I18NManager.getI18NString(this.getBbrUI(), BbrUtilsResources.RES_GENERIC, "no_upper") + ")");
				
				result.addValueChangeListener((ValueChangeListener<Boolean> & Serializable) e -> cmp_changeDynCKBHandler(e, attribute));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-field");
			}
		}

		return result;
	}


	private BbrItemSelectField<CPItemAttributeValueDTO> getBbrItemSelectFieldForImage(DefinableAttributeDataDTO attribute)
	{
		BbrItemSelectField<CPItemAttributeValueDTO> result = new BbrItemSelectField<>();
		result.setWidth("100%");
		result.setReadOnly(true);
		result.setFieldName("value");
		result.setDescriptionName("value");
		if (attribute != null)
		{
			result.setData(attribute);

			if (attribute.getCanwrite())
			{
				result.addStyleName("bbr-binder-field");
				result.addBbrEventListener((BbrEventListener & Serializable) e -> this.bbrItemSelectFieldForFile_selectedHandler(result, e));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-field");
			}
		}

		return result;
	}
	
	private TextArea getBbrItemSelectFieldForRelatedProduct(DefinableAttributeDataDTO attribute)
	{
//		BbrTextField result = new BbrTextField();
//		result.setWidth("100%");
//		result.setReadOnly(true);
//		result.setHeight("23px");
		
		TextArea result = new TextArea();
		result.setHeight("50px");
		result.setPlaceholder("");
		
		if (attribute != null)
		{
			result.setData(attribute);
			
			if (attribute.getCanwrite())
			{
				result.addStyleName("bbr-binder-field");
				//result.addValueChangeListener((ValueChangeListener<String> & Serializable) e -> this.bbrItemSelectFieldForRelatedProduct_selectedHandler(result, attribute));
			}
			else
			{
				result.addStyleName("bbr-binder-readonly-field");
			}
		}

		return result;
	}


	private void bbrItemSelectFieldForFile_selectedHandler(BbrItemSelectField<CPItemAttributeValueDTO> component, BbrEvent event)
	{
		if (component.getData() != null)
		{
			FEPFilesGallery winSelect = new FEPFilesGallery(getBbrUI(), (DefinableAttributeDataDTO) component.getData());

			winSelect.addBbrEventListener((BbrEventListener & Serializable) e -> this.image_selectedHandler(e, component));
			winSelect.open(true, true, this.bbrModule);
		}
	}
	
	/*private void bbrItemSelectFieldForRelatedProduct_selectedHandler(BbrTextField component, DefinableAttributeDataDTO attribute)
	{
		if (component.getData() != null)
		{
//			RelatedProductEditor winRelatedProd = new RelatedProductEditor(getBbrUI(), this.modelObject);
//			winRelatedProd.open(true, true, this.bbrModule);
			
//			AddRelatedProduct winRelatedProd = new AddRelatedProduct(getBbrUI(), this.modelObject);
//			winRelatedProd.addBbrEventListener((BbrEventListener & Serializable) e -> this.relatedProduct_selectedHandler(e, component));
//			
//			winRelatedProd.open(true, true, this.bbrModule);
		}
	}*/
	
	private void bbrTextSelectFieldForRelatedProduct_selectedHandler(ClickEvent event, TextArea component)
	{
		if (component.getData() != null)
		{
		
			AddRelatedProduct winRelatedProd = new AddRelatedProduct(getBbrUI(), this.modelObject);
			winRelatedProd.addBbrEventListener((BbrEventListener & Serializable) e -> this.relatedProduct_selectedHandler(e, component));
			winRelatedProd.open(true, true, this.bbrModule);
		}
	}
	
	private void relatedProduct_selectedHandler(BbrEvent e, TextArea component)
	{
		String value = "";
		String metadata = "";
		
		if (e.getEventType().equals(BbrEvent.ITEM_SELECTED) && e.getResultObject() != null)
		{
			DefinableAttributeDataDTO defAttribute = (DefinableAttributeDataDTO) component.getData();
			
			ProductsAssignmentList values = (ProductsAssignmentList) e.getResultObject();
			
			if (values != null && values.getValues() != null && values.getValues().length > 0)
			{
				value = Arrays.stream(values.getValues()).map(x -> x.getItemSelected().getSku() + " [" + x.getCount() + "]").collect(Collectors.joining (";"));
				metadata = Arrays.stream(values.getValues()).map(x -> x.getItemSelected().getId() + " [" + x.getCount() + "]").collect(Collectors.joining (";"));
			}

			updateModel(defAttribute, value, metadata);
			this.updateDataForm();
		}
	}


	private void image_selectedHandler(BbrEvent e, BbrItemSelectField<CPItemAttributeValueDTO> component)
	{
		if (e.getEventType().equals(BbrEvent.ITEM_SELECTED) && e.getResultObject() != null)
		{
			DefinableAttributeDataDTO defAttribute = (DefinableAttributeDataDTO) component.getData();
			ItemValueMetadata valueMetadata = (ItemValueMetadata) e.getResultObject();

			String filename = valueMetadata.getValue();
			String url = valueMetadata.getMetadata();

			updateModel(defAttribute, filename, url);

			this.updateDataForm();
		}
	}


	private void updateMapAttributeParent(DefinableAttributeDataDTO attribute, Component component)
	{
		if (attribute.getAttributeinfo() != null && attribute.getAttributeinfo().getRelatedattributes() != null && attribute.getAttributeinfo().getRelatedattributes().length > 0)
		{
			if (this.mapRelatedAttribute == null)
			{
				this.mapRelatedAttribute = new HashMap<>();
			}
			AttributeDTO parentAttribute = attribute.getAttributeinfo().getRelatedattributes()[0];

			ArrayList<Component> componentsList = this.mapRelatedAttribute.get(parentAttribute.getId());

			if (componentsList == null)
			{
				componentsList = new ArrayList<>();
			}

			componentsList.add(component);

			this.mapRelatedAttribute.put(parentAttribute.getId(), componentsList);
		}
	}


	private AttributeValueDTO[] getAttributeRelatedValues(Long attributeid, AttributeValueDTO[] parentValues)
	{
		AttributeValueDTO[] result = new AttributeValueDTO[0];
		if (attributeid != null)
		{
			try
			{
				AttributeValuesResultDTO reportResult = EJBFactory.getFEPEJBFactory().getFEPCommonDataManagerLocal(this.getBbrUI()).getAttributeRelatedValues(attributeid, parentValues, this.getBbrUI().getUser().getLocale().getLanguage());
				if (reportResult != null && reportResult.getValues() != null)
				{
					result = reportResult.getValues();
				}
			}
			catch (BbrUserException e)
			{
				AppUtils.getInstance().doLogOut(e.getMessage(), this.getBbrUI());
			}
			catch (BbrSystemException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}
	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************
}
