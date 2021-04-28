package bbr.b2b.portal.classes.basics;

import java.io.Serializable;

import com.vaadin.server.Resource;

@SuppressWarnings("serial")
public class LocaleInfo implements Serializable
{
	private String language = "" ;
	private String country = "";
	private String caption = "";
	private Resource icon = null;
	
	
	public String getLanguage() 
	{
		return language;
	}
	public void setLanguage(String language) 
	{
		this.language = language;
	}
	
	public String getCountry() 
	{
		return country;
	}
	public void setCountry(String country) 
	{
		this.country = country;
	}
	
//	public Long getId() 
//	{
//		return id;
//	}
//	public void setId(Long id) 
//	{
//		this.id = id;
//	}
	
	public LocaleInfo(String caption, String language, String country, Resource icon) 
	{
		//this.id = id;
		this.caption = caption;
		this.language = language;
		this.country = country;
		this.icon = icon;
	}
	public Resource getIcon() {
		return icon;
	}
	public void setIcon(Resource icon) {
		this.icon = icon;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	

}
