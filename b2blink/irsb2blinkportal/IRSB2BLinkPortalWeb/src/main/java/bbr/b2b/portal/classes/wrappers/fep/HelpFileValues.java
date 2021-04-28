package bbr.b2b.portal.classes.wrappers.fep;

import com.vaadin.server.Resource;

public class HelpFileValues
{
	private String		fileName	= null;
	private double		size		= 0;
	private int			width		= 0;
	private int			height		= 0;
	private Resource	resFile		= null;
	private boolean		isImage		= false;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public double getSize()
	{
		return size;
	}

	public void setSize(double kb)
	{
		this.size = kb;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int heigth)
	{
		this.height = heigth;
	}

	public Resource getResFile()
	{
		return resFile;
	}

	public void setResFile(Resource resFile)
	{
		this.resFile = resFile;
	}

	public boolean isImage()
	{
		return isImage;
	}

	public void setImage(boolean isImage)
	{
		this.isImage = isImage;
	}

}
