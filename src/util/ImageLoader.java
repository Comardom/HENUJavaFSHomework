package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader
{
	public static BufferedImage
	loadImage(String path)
	{
		InputStream is = ImageLoader.class.getResourceAsStream(path);

		if (is == null)
		{
			//用于判断是否是图片损坏了，如果是损坏了不会弹出来没找到
			System.err.println("Image not found: " + path);
			return null;
		}

		try
		{
			return ImageIO.read(is);
		}
		catch (IOException e)
		{
			System.err.println("Failed to load image: " + path);
			//noinspection CallToPrintStackTrace
			e.printStackTrace();
			return null;
		}
	}
}
