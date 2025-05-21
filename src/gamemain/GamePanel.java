package gamemain;

import entity.Fish;
import entity.Me;
import entity.SmallFish;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePanel extends JPanel implements KeyListener
{
//	private BufferedImage backgroundImage;
	private List<Fish> fishes = new ArrayList<>();
	private Me me;
	private SmallFish  smallFish;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private BufferedImage backgroundCache;


	public GamePanel()
	{
		me = new Me(800, 800); // 先写死

//		this.me = new Me();
//		this.me = new Me(this.getWidth(), this.getHeight());
//		setPreferredSize(new Dimension(800, 800));
//		addComponentListener(new ComponentAdapter()
//		{
//			@Override
//			public void componentResized(ComponentEvent e)
//			{
//				// 创建 Me 的正确时机
//				if (me == null)
//				{
//					me = new Me(getWidth(), getHeight());
//				}
//			}
//		});

		for (int i = 0; i < 10000; i++)
		{
			me.setX(me.getX() + 1);
			me.setX(me.getX() - 1);
		}
		for (int i = 0; i < 10000; i++)
		{
			me.setY(me.getY() + 1);
			me.setY(me.getY() - 1);
		}

//		for (int i = 0; i < 10000; i++) {
//			me.getBounds(); // 热启动一些关键函数
//		}

		setFocusable(true);        // 允许获取焦点
//		requestFocusInWindow();    // 尝试请求焦点
		setDoubleBuffered(true);
		addKeyListener(this);      // 注册键盘监听器
		// 延迟请求焦点，确保窗口激活后才请求
		SwingUtilities.invokeLater(this::requestFocusInWindow);

//		createBackgroundCache();

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
	private void createBackgroundCache()
	{
		if (getWidth() <= 0 || getHeight() <= 0)
		{
			return;
		}
		backgroundCache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = backgroundCache.createGraphics();

		Color baseColor = new Color(180, 200, 220);
		g2.setColor(baseColor);
		g2.fillRect(0, 0, getWidth(), getHeight());

		Color stripeColor = new Color(140, 160, 190);
		g2.setColor(stripeColor);

		int stripeWidth = 10;
		int spacing = 20;

		for (int x = 0; x < getWidth(); x += spacing)
		{
			g2.fillRect(x, 0, stripeWidth, getHeight());
		}

		g2.dispose();
	}


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

		if (backgroundCache == null || backgroundCache.getWidth() != getWidth() || backgroundCache.getHeight() != getHeight())
		{
			createBackgroundCache();
		}

		g.drawImage(backgroundCache, 0, 0, null);

		// 简单填充白色背景
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, getWidth(), getHeight());


		// 绘制所有鱼
		for (Fish f : fishes)
		{
			f.draw(g);
		}
		Toolkit.getDefaultToolkit().sync();
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
//以下这俩废弃*****************************
	//专门处理Timer
	void controlTimer1() {
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
	void controlTimer2()
	{
		Thread gameThread = new Thread(() -> {
			int fps = 240;
			long frameDuration = 1000 / fps;

			while (true)
			{
				long startTime = System.currentTimeMillis();

				// 更新逻辑（移动角色）
				int speed = 4;
				if (upPressed) me.setY(me.getY() - speed);
				if (downPressed) me.setY(me.getY() + speed);
				if (leftPressed) {
					me.setX(me.getX() - speed);
					me.setFaceLeft(true);
				}
				if (rightPressed) {
					me.setX(me.getX() + speed);
					me.setFaceLeft(false);
				}

				// 重绘必须在 EDT 线程中调用
				SwingUtilities.invokeLater(this::repaint);

				long elapsed = System.currentTimeMillis() - startTime;
				long sleepTime = frameDuration - elapsed;

				if (sleepTime > 0)
				{
					try
					{
						Thread.sleep(sleepTime);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		});

		gameThread.setDaemon(true);
		gameThread.start();
	}
//以上这俩废弃*****************************
    private void controlTimer() {
		int fps = 60;
		int delay = 1000 / fps;

		// 每几帧生成一条新的鱼
		AtomicInteger spawnCounter = new AtomicInteger();

        Timer timer = new Timer(delay, e -> {
            int speed = 4;
            if (upPressed) me.setY(me.getY() - speed);
            if (downPressed) me.setY(me.getY() + speed);
            if (leftPressed) {
                me.setX(me.getX() - speed);
                me.setFaceLeft(true);
            }
            if (rightPressed) {
                me.setX(me.getX() + speed);
                me.setFaceLeft(false);
            }
			// 小鱼移动和清除游出边界的鱼
			fishes.removeIf(f -> f instanceof SmallFish && ((SmallFish)f).isOutOfScreen(getWidth()));
			for (Fish f : fishes) {
				if (f instanceof SmallFish) {
					((SmallFish) f).move();
				}
			}
			// 每20帧生成一条小鱼
			spawnCounter.getAndIncrement();
			if (spawnCounter.get() >= 20) {
				spawnCounter.set(0);
				fishes.add(new SmallFish(getWidth(), getHeight()));
			}

			repaint();
        });
		timer.start();
	}

}
