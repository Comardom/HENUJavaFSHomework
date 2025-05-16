package entity;

import java.awt.*;

public abstract class Fish
{
	protected int x,y;
	protected int width, height;
	protected int speed;
	protected boolean isFaceLeft;
	protected boolean edible;

	public Fish(int x, int y, int width, int height, boolean isFaceLeft,boolean edible)
	{
		// 初始化位置，大小和属性
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isFaceLeft = isFaceLeft;
		this.edible = edible;
	}

	public abstract void move();

	public abstract void draw(Graphics g);

	//这个是碰撞箱
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
