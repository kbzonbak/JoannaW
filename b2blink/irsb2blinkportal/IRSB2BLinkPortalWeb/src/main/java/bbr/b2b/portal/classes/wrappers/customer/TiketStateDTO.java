package bbr.b2b.portal.classes.wrappers.customer;

public class TiketStateDTO
{

	private Long	id;
	private String	name;

	public TiketStateDTO(Long id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
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

}
