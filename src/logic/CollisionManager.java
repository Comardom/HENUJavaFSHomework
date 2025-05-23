package logic;

import entity.Fish;

import java.awt.*;

public class CollisionManager
{
	public static boolean isColliding(Fish a, Fish b)
	{
		return a.getBounds().intersects(b.getBounds());
	}
	public static boolean canEat(Fish me, Fish other)
	{
		Rectangle myBounds = me.getBounds();
		Rectangle otherBounds = other.getBounds();

		if (!myBounds.intersects(otherBounds))
		{
			return false;
		}

		if (me.getIsFaceLeft())
		{
			return other.getX() < me.getX(); // 鱼头朝左，other 在左边才算吃
		}
		else
		{
			return other.getX() > me.getX(); // 鱼头朝右，other 在右边才算吃
		}
	}

}
