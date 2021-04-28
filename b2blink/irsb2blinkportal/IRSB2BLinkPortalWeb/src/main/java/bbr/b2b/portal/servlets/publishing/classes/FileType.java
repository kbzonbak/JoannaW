package bbr.b2b.portal.servlets.publishing.classes;

public enum FileType 
{
	IMAGE("IMAGES"),FILE("FILES");
	
	private String value = "";

	public String getValue() {
		return value;
	}
	
	private FileType(String value)
	{
		this.value = value;
	}
}
