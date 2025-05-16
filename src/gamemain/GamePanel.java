package gamemain;

import entity.Fish;
import entity.Me;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener
{
//	private BufferedImage backgroundImage;
	private List<Fish> fishes = new ArrayList<>();
	private final Me me;
	private boolean upPressed, downPressed, leftPressed, rightPressed;


	public GamePanel()
	{
		setFocusable(true);        // 允许获取焦点
		requestFocusInWindow();    // 尝试请求焦点
		addKeyListener(this);      // 注册键盘监听器

		this.me = new Me();
		me.setX(500);
		me.setY(300);

//		loadBackgroundIMG("/img/sea.jpg");
		fishes.add(me);

		controlTimer();
	}

	//ImageLoader在util里面
//	public void loadBackgroundIMG(String path)
//	{
//		backgroundImage = ImageLoader.loadImage(path);
//	}

	//下面这个函数会自己调用，不需要手动使用
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);


//		if (backgroundImage != null)
//		{
//			// 绘制背景图，填满整个面板
//			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
//		}

		// 背景底色（浅灰蓝）
		Color baseColor = new Color(180, 200, 220);
		g.setColor(baseColor);
		g.fillRect(0, 0, getWidth(), getHeight());

		// 条纹颜色（深一点的灰蓝）
		Color stripeColor = new Color(140, 160, 190);
		g.setColor(stripeColor);

		int stripeWidth = 10;  // 条纹宽度
		int spacing = 20;      // 条纹间隔（从一条开始到下一条开始的距离）

		for (int x = 0; x < getWidth(); x += spacing)
		{
			g.fillRect(x, 0, stripeWidth, getHeight());
		}

		// 绘制所有鱼
		for (Fish f : fishes)
		{
			f.draw(g);
		}
	}
	// 以下是 KeyListener 接口需要实现的三个方法
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_NUMPAD8, 224 -> upPressed = true;
			case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, 225 -> downPressed = true;
			case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_NUMPAD4, 226 -> leftPressed = true;
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_NUMPAD6, 227 -> rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_NUMPAD8, 224 -> upPressed = false;
			case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, 225 -> downPressed = false;
			case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_NUMPAD4, 226 -> leftPressed = false;
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_NUMPAD6, 227 -> rightPressed = false;
		}
	}




	@Override
	public void keyTyped(KeyEvent e)
	{
		// 不用处理
	}

	//专门处理Timer
	void controlTimer() {
		long[] lastTime = {System.nanoTime()};
		int delay = 1000 / 144; // 144 FPS（可以改成 120 或其他）

		Timer timer = new Timer(delay, e -> {
			long now = System.nanoTime();
			double deltaSeconds = (now - lastTime[0]) / 1_000_000_000.0;
			lastTime[0] = now;

			double speed = 300; // 每秒移动 300 像素
			double step = speed * deltaSeconds;

			if (upPressed) me.setY((int)(me.getY() - step));
			if (downPressed) me.setY((int)(me.getY() + step));
			if (leftPressed) {
				me.setX((int)(me.getX() - step));
				me.setFaceLeft(true);
			}
			if (rightPressed) {
				me.setX((int)(me.getX() + step));
				me.setFaceLeft(false);
			}

			repaint();
		});
		timer.start();
	}

}
