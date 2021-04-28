package bbr.b2b.portal.classes.basics;

public class SiteExagoBIDTO
{
	private String name;
	private int id;
	
	public SiteExagoBIDTO(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
}
