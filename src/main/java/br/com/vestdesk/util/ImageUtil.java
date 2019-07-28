package br.com.vestdesk.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil
{

	public static byte[] optimizeImage(byte[] image, String contentType)
	{
		byte[] imageOptimized = null;
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
		ByteArrayOutputStream out;
		try
		{
			out = new ByteArrayOutputStream();
			ImageWriter writer = ImageIO.getImageWritersByFormatName(contentType).next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(out);
			writer.setOutput(ios);

			BufferedImage bufferImage = ImageIO.read(byteArrayInputStream);

			ImageWriteParam param = writer.getDefaultWriteParam();
			if (param.canWriteCompressed())
			{
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.03f);
			}

			IIOImage nova = new IIOImage(bufferImage, null, null);

			writer.write(null, nova, param);
			ios.flush();
			out.flush();
			imageOptimized = out.toByteArray();

			out.close();
			ios.close();
			writer.dispose();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageOptimized;
	}

}
