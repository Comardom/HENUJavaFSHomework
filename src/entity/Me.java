package entity;

import util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Me extends Fish
{
	private static final int ME_SIDE_LENGTH = 64;
	private static final BufferedImage fishImage = ImageLoader.loadImage("/img/Me.png");
	private int panelWidth;
	private int panelHeight;

	public Me()
	{

		super(500,300, ME_SIDE_LENGTH,ME_SIDE_LENGTH, true,false);
//		fishImage = ImageLoader.loadImage("/img/Me.png");
		this.speed=0;

		if (fishImage == null)
		{
			System.err.println("Failed to load Me.png");
		}

	}
	public Me(int panelWidth, int panelHeight)
	{

		super(500,300, ME_SIDE_LENGTH,ME_SIDE_LENGTH, true,false);
//		fishImage = ImageLoader.loadImage("/img/Me.png");
		System.out.println("Me created with panel size: " + panelWidth + " x " + panelHeight);

		this.speed=0;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		if (fishImage == null)
		{
			System.err.println("Failed to load Me.png");
		}

	}

	@Override
	public void move()
	{
		;
	}

	@Override
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		// 测试用纯色矩形，排除图片问题
//		g2.setColor(Color.BLUE);
//		g2.fillRect(x, y, width, height);


		if (fishImage != null)
		{
			if (isFaceLeft)
			{
				g2.drawImage(fishImage, x, y, width, height, null);
			}
			else
			{
				// 水平翻转
				g2.drawImage(fishImage, x + width, y, -width, height, null);
			}
		}
		else
		{
			g2.setColor(Color.BLUE);
			g2.fillRect(x, y, width, height);
		}
	}

//	public void setX(int x)
//	{
//		this.x = x;
//	}
//	public void setY(int y)
//	{
//		this.y = y;
//	}
	public void setX(int x)
	{
		if (x < 0) x = 0;
		if (x > panelWidth - width) x = panelWidth - width;
		this.x = x;
	}

	public void setY(int y)
	{
		if (y < 0) y = 0;
		if (y > panelHeight - height) y = panelHeight - height;
		this.y = y;
	}

	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public void setFaceLeft(boolean faceLeft)
	{
		this.isFaceLeft = faceLeft;
	}

	@Override
	public Rectangle getBounds()
	{
		return super.getBounds();
	}

}
