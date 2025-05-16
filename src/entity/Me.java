package entity;

import util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Me extends Fish
{
	private static final int ME_SIDE_LENGTH = 64;
	private final BufferedImage fishImage;


	public Me()
	{
		super(500,300, ME_SIDE_LENGTH,ME_SIDE_LENGTH, true,false);
		fishImage = ImageLoader.loadImage("/img/Me.png");
		this.speed=0;
		if (fishImage == null) {
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
		if (fishImage != null)
		{

//			g2.setColor(Color.BLUE);
//			g2.fillRect(x, y, width, height);

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

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setFaceLeft(boolean faceLeft) {
		this.isFaceLeft = faceLeft;
	}

	@Override
	public Rectangle getBounds()
	{
		return super.getBounds();
	}

}
