package entity;

import com.kitfox.svg.SVGDiagram;
import logic.CollisionManager;
import util.Default;
import util.ImageLoader;
import util.SvgLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Me extends Fish
{

//	private static final BufferedImage fishImage = ImageLoader.loadImage("/img/Me.png");
	private static final SVGDiagram fishSvg = SvgLoader.loadSvg("/img/Me.svg");

	private int panelWidth;
	private int panelHeight;
	private int score = 0;

	public Me()
	{
		super(Default.getDefaultX(),Default.getDefaultY(), Default.getMeSideLength(), Default.getMeSideLength(), true,false);
		init();
	}
	public Me(int panelWidth, int panelHeight)
	{

		super(Default.getDefaultX(),Default.getDefaultY(), Default.getMeSideLength(), Default.getMeSideLength(), true,false);
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		init();
	}
	private void init()
	{
		this.speed = 0;
		if (fishSvg == null)
		{
			System.err.println("Failed to load Me.svg");
		}
	}

	public void updateSizeByScore()
	{
		int baseSize = Default.getMeSideLength();
		int extra = score / 5; // 每5分长大一圈
		int newSize = baseSize + extra * 4;

		// 如果尺寸变化才更新（避免每帧都重新赋值）
		if (this.width != newSize || this.height != newSize)
		{
			this.width = newSize;
			this.height = newSize;
		}
	}


	@Override
	public void move()
	{

	}



	@Override
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		if (fishSvg != null)
		{
			if (g2.getClip() == null) {
				g2.setClip(0, 0, panelWidth, panelHeight); // 添加这一行，防止 NullPointerException
			}

			g2.translate(x, y); // 平移到当前位置

			if (!isFaceLeft)
			{
				// 水平翻转：缩放 -1，然后再平移回来
				g2.scale(-1, 1);
				g2.translate(-width, 0);
			}

			// 缩放到当前鱼的大小
			double scaleX = width / fishSvg.getWidth();
			double scaleY = height / fishSvg.getHeight();
			g2.scale(scaleX, scaleY);

			try
			{
				fishSvg.render(g2);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			// 还原 transform（重要，不然后续绘图错位）
			g2.setTransform(new AffineTransform());
		}
		else
		{
			g2.setColor(Color.BLUE);
			g2.fillRect(x, y, width, height);
		}
	}


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

	public void setFaceLeft(boolean faceLeft)
	{
		this.isFaceLeft = faceLeft;
	}

	public boolean canEat(Fish other)
	{
        return CollisionManager.canEat(this, other);
    }

	public void addScore(int addition)
	{
		this.score += addition;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	@Override
	public Rectangle getBounds() {
        return super.getBounds();
    }

}
