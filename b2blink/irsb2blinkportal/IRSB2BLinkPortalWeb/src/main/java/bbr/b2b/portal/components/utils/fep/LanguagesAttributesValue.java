package bbr.b2b.portal.components.utils.fep;

import java.util.HashMap;

import bbr.b2b.fep.common.data.classes.AttributeValueDTO;

public class LanguagesAttributesValue
{

	private AttributeValueID id = null;
	
	private HashMap<Long, AttributeValueDTO> mapLanguages = null;

	public AttributeValueID getId()
	{
		return id;
	}

	public void setId(AttributeValueID id)
	{
		this.id = id;
	}

	public HashMap<Long, AttributeValueDTO> getMapLanguages()
	{
		return mapLanguages;
	}

	public void setMapLanguages(HashMap<Long, AttributeValueDTO> mapLanguages)
	{
		this.mapLanguages = mapLanguages;
	}

}
