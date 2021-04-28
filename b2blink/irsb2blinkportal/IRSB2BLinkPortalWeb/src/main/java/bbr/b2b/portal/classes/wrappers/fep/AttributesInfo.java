package bbr.b2b.portal.classes.wrappers.fep;

import java.util.Set;

import bbr.b2b.fep.common.data.classes.AttributeInitParamDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;
import bbr.b2b.fep.common.data.classes.UserDataTypeDTO;

public class AttributesInfo
{

	// =====================================================================================================================================
	// BEGINNING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	public AttributesInfo(AttributeSearchInfo attributeSearchInfo, Set<UserDataTypeDTO> selectedDataType, String type, ClientDTO selectedClient)
	{
		super();
		this.attributeSearchInfo = attributeSearchInfo;
		this.selectedDataType = selectedDataType;
		this.selectedClient = selectedClient;
		this.type = type;
	}

	public AttributesInfo()
	{
		super();
	}

	// =====================================================================================================================================
	// ENDING SECTION ----> CONSTRUCTORS
	// =====================================================================================================================================

	// =====================================================================================================================================
	// BEGINNING SECTION ----> PROPERTIES
	// =====================================================================================================================================

	private AttributeSearchInfo		attributeSearchInfo	= null;
	private Set<UserDataTypeDTO>	selectedDataType	= null;
	private String					type				= null;
	private ClientDTO 				selectedClient		= null;


	public AttributeSearchInfo getAttributeSearchInfo()
	{
		return attributeSearchInfo;
	}

	public void setAttributeSearchInfo(AttributeSearchInfo attributeSearchInfo)
	{
		this.attributeSearchInfo = attributeSearchInfo;
	}

	public Set<UserDataTypeDTO> getSelectedDataType()
	{
		return selectedDataType;
	}

	public void setSelectedDataType(Set<UserDataTypeDTO> selectedDataType)
	{
		this.selectedDataType = selectedDataType;
	}
	
	public ClientDTO getSelectedClient() {
		return selectedClient;
	}
	
	public void setSelectedClient(ClientDTO selectedClient) {
		this.selectedClient = selectedClient;
	}

	public AttributeInitParamDTO getAttributeInitParam()
	{
		AttributeInitParamDTO result = new AttributeInitParamDTO();
		if (this.attributeSearchInfo != null && this.selectedDataType != null)
		{
			result.setInternalname(this.attributeSearchInfo.getFilterState().getId().equals(0) ? this.attributeSearchInfo.getValue() : null);
			result.setVendorname(this.attributeSearchInfo.getFilterState().getId().equals(1) ? this.attributeSearchInfo.getValue() : null);
			result.setActive(this.attributeSearchInfo.getActiveOption().getValue()?true:null);
			result.setDatatypeids(this.selectedDataType.stream().map(a -> a.getId()).toArray(Long[]::new));
			result.setModuletypename(this.type);
			result.setClientname(this.selectedClient.getInternalname());
		}
		return result;
	}
	// =====================================================================================================================================
	// ENDING SECTION ----> PROPERTIES
	// =====================================================================================================================================

}
