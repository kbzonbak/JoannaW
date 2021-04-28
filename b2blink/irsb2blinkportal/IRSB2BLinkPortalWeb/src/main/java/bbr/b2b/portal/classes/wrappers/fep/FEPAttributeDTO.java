package bbr.b2b.portal.classes.wrappers.fep;

import bbr.b2b.fep.common.data.classes.ArticleTypeDataDTO;
import bbr.b2b.fep.common.data.classes.ClientDTO;

public class FEPAttributeDTO
{
	private String							attributename			= null;
	private String							attributevendorname     = null;
	private String							attributetypename 		= null;
	


public FEPAttributeDTO(String attName, String attVendorname, String attTypename)
	{
		super();
		this.attributename		= attName;
		this.attributevendorname 	= attVendorname;
		this.attributetypename = attTypename;
	}



public String getAttributename() {
	return attributename;
}



public void setAttributename(String attributename) {
	this.attributename = attributename;
}



public String getAttributevendorname() {
	return attributevendorname;
}



public void setAttributevendorname(String attributevendorname) {
	this.attributevendorname = attributevendorname;
}



public String getAttributetypename() {
	return attributetypename;
}



public void setAttributetypename(String attributetypename) {
	this.attributetypename = attributetypename;
}

	
	
}
