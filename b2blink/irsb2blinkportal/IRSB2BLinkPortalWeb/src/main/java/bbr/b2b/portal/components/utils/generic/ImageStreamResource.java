package bbr.b2b.portal.components.utils.generic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource.StreamSource;

public class ImageStreamResource implements StreamSource 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7813175926022322344L;
	private BufferedImage challengeImage = null;
	public ImageStreamResource(BufferedImage challengeImage) 
	{
		this.challengeImage = challengeImage;
		 
	}

	@Override
	public InputStream getStream() 
	{
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		try 
		{
			//BufferedImage challengeImage = BbrCaptchaManager.getInstance().getCaptchaChallenge(imageId);
			ImageIO.setUseCache(false);
			ImageIO.write(challengeImage, "png", result);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(result.toByteArray());
	}

	

}
