package bbr.b2b.portal.classes.utils.app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesBufferedUtils
{
	public static BufferedImage getBufferedImageFromFile(String path)
	{
		BufferedImage bi = null;
		try
		{
			File file = new File(path);
			if (file.exists())
			{
				bi = ImageIO.read(file);
				System.out.println("Path:" + path + " Image size:" + bi.getHeight() + " " + bi.getWidth());
			}
			else
			{
				throw new IOException("No se encuentra el archivo en:" + path);
			}
		}
		catch (IOException e)
		{
			System.out.println("No se encuentra el archivo en: " + path);
			return bi;
		}
		return bi;
	}
}
