package bbr.b2b.portal.classes.utils.fep;

import bbr.b2b.fep.cp.data.classes.CPItemAttributeValueDTO;
import bbr.b2b.fep.mp.data.classes.MPItemAttributeValueDTO;

public class FEPItemsFactory
{

	// ****************************************************************************************
	// BEGINNING SECTION ----> PROPERTIES
	// ****************************************************************************************
	// ****************************************************************************************
	// ENDING SECTION ----> PROPERTIES
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************
	// ****************************************************************************************
	// ENDING SECTION ----> CONSTRUCTORS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> INTERFACE IMPLEMENTATIONS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************
	// ****************************************************************************************
	// ENDING SECTION ----> EVENTS HANDLERS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************
	
	public static CPItemAttributeValueDTO getCPAttributeFromMPAttribute(MPItemAttributeValueDTO mpAttrib)
	{
		CPItemAttributeValueDTO result = new CPItemAttributeValueDTO();

		if(mpAttrib != null)
		{
			result.setAttributealeas(mpAttrib.getAttributealeas()); 
			result.setAttributeid(mpAttrib.getAttributeid()); 
			result.setAttributeinternalname(mpAttrib.getAttributeinternalname()); 
			result.setAttributevendorname(mpAttrib.getAttributevendorname()); 
			result.setBullet(mpAttrib.getBullet()); 
			result.setCanwrite(mpAttrib.getCanwrite()); 
			result.setForvariant(mpAttrib.getForvariant()); 
			result.setItemid(mpAttrib.getItemid()); 
			result.setMandatory(mpAttrib.getMandatory()); 
			result.setMetadata(mpAttrib.getMetadata()); 
			result.setPicking(mpAttrib.getPicking()); 
			result.setSelectedvalues(mpAttrib.getSelectedvalues()); 
			result.setTypeinternalname(mpAttrib.getTypeinternalname()); 
			result.setTypename(mpAttrib.getTypename()); 
			result.setUpdateable(mpAttrib.getUpdateable()); 
			result.setUserdatatypeid(mpAttrib.getUserdatatypeid()); 
			result.setUserdatatypeinternalname(mpAttrib.getUserdatatypeinternalname()); 
			result.setValue(mpAttrib.getValue()); 
			result.setValueid(mpAttrib.getValueid()); 
			result.setVisualorder(mpAttrib.getVisualorder()); 
		}
		
		return result;
	}

	// ****************************************************************************************
	// ENDING SECTION ----> PUBLIC METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// BEGINNING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

	// ****************************************************************************************
	// ENDING SECTION ----> PRIVATE METHODS
	// ****************************************************************************************

}
