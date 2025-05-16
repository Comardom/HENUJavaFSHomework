package logic;

import entity.Fish;

public class CollisionManager
{
	public static boolean isColliding(Fish a, Fish b)
	{
		return a.getBounds().intersects(b.getBounds());
	}
}
