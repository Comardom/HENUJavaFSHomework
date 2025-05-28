package gamemain;

import entity.Boss;
import entity.Fish;
import entity.Me;
import entity.SmallFish;
import util.Default;
import util.FishSpawner;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener
{
	private final List<Fish> fishes = new ArrayList<>();
	private final Me me;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private BufferedImage backgroundCache;
	private final FishSpawner fishSpawner;
	private boolean paused = false;
	private boolean over = false;
	private Timer timer; // 把 timer 提升为成员变量，方便控制启动和停止
	private int lastBossRefreshScore = 0;//控制Boss方向用的



	public GamePanel()
	{
		me = new Me(Default.getWindowWidth(), Default.getWindowHeight()); // 先写死
		fishSpawner = new FishSpawner(fishes, Default.getWindowWidth(), Default.getWindowHeight());


		setFocusable(true);        // 允许获取焦点

		setDoubleBuffered(true);
		addKeyListener(this);      // 注册键盘监听器
		// 延迟请求焦点，确保窗口激活后才请求
		SwingUtilities.invokeLater(this::requestFocusInWindow);

		me.setX(Default.getDefaultX());
		me.setY(Default.getDefaultY());

		fishes.add(me);

		controlTimer();

	}


	private void createBackgroundCache() {
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

		if (backgroundCache == null || backgroundCache.getWidth() != getWidth() || backgroundCache.getHeight() != getHeight())
		{
			createBackgroundCache();
		}

		g.drawImage(backgroundCache, 0, 0, null);

		// 绘制所有鱼
		for (Fish f : fishes)
		{
			f.draw(g);
		}

		Graphics2D g2d = (Graphics2D) g;

		// 显示分数
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(Default.getMyFont());
		g2d.setColor(Color.BLACK);
		g2d.drawString("Score: " + me.getScore(), 30, 50);


		// 显示暂停提示
		if (paused)
		{
			g2d.setFont(Default.getMyFont().deriveFont(82f));
			g2d.setColor(new Color(100, 70, 140)); // Medium Orchid
			String msg = "遊戲暫停";
			FontMetrics fm = g2d.getFontMetrics();
			int msgWidth = fm.stringWidth(msg);
			int msgHeight = fm.getHeight();
			g2d.drawString(msg, (getWidth() - msgWidth) / 2, (getHeight() + msgHeight) / 2);
		}

		if (fishSpawner.isBossWarning())
		{
			int secondsLeft = 3 - fishSpawner.getWarningCounter() / 20; // 3秒倒计时
			g.drawString("Boss 即将出现：" + secondsLeft + " 秒", 50, 50);
		}


		//解决诡异的可变刷新频率导致卡顿问题
		Toolkit.getDefaultToolkit().sync();
	}

	// 以下是 KeyListener 接口需要实现的三个方法
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			paused = !paused;
			repaint(); // 确保立刻重绘
			return;
		}
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
			case KeyEvent.VK_C -> {
				if (Default.isDevMode())
				{
					me.addScore(10);
					me.updateSizeByScore();
					System.out.println("Cheat activated! Score: " + me.getScore());
				}
			}
			case KeyEvent.VK_Q -> {
				if (Default.isDevMode())
				{
					me.setScore(0);
					me.updateSizeByScore();
					fishSpawner.reset();
				}
			}
			case KeyEvent.VK_R -> {
				if(over)
				{
					over = false;
					fishSpawner.reset();
					me.setScore(0);
					me.updateSizeByScore();
					List<Fish> toRemove = new ArrayList<>();
					for (Fish f : fishes)
					{
						if (f instanceof SmallFish || f instanceof Boss)
						{
							toRemove.add(f);
						}
					}
					fishes.removeAll(toRemove);
				}
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent e)
	{
		// 不用处理
	}

	private void togglePause()
	{
		paused = !paused;
	}


	private void controlTimer()
	{
		int fps = 60;
		int delay = 1000 / fps;

		timer = new Timer(delay, _ -> {
			if (paused || over) return;

			updatePlayerMovement();
			spawnAndMoveFish();
			checkEatAndScore();
			me.updateSizeByScore();
			updateBossDirection();

			repaint();
		});
		timer.start();
	}
	private void updateBossDirection()
	{
		int score = me.getScore();
		if (score >= 20 && score % 10 == 0 && score != lastBossRefreshScore)
		{
			Boss.refreshRandom();
			lastBossRefreshScore = score;
		}
	}

	private void updatePlayerMovement()
	{
		int speed = 4;
		if (upPressed) me.setY(me.getY() - speed);
		if (downPressed) me.setY(me.getY() + speed);
		if (leftPressed)
		{
			me.setX(me.getX() - speed);
			me.setFaceLeft(true);
		}
		if (rightPressed)
		{
			me.setX(me.getX() + speed);
			me.setFaceLeft(false);
		}
	}
	private void spawnAndMoveFish()
	{
		// 生成鱼
		fishSpawner.update(me.getScore());

		// 移除游出屏幕的小鱼
		fishes.removeIf(f -> f instanceof SmallFish && ((SmallFish) f).isOutOfScreen(getWidth()));

		// 所有鱼都要 move（Boss 也要动）
		for (Fish f : fishes)
		{
			f.move();
		}
	}

	private void checkEatAndScore()
	{
		//加分机制
		List<Fish> toRemove = new ArrayList<>();
		for (Fish f : fishes)
		{
			if (f instanceof SmallFish && me.canEat(f))
			{
				toRemove.add(f);
				me.addScore(1); // 定义加几分
			}
			if (f instanceof Boss && ((Boss) f).canEat(me) && me.getScore()>=80)
			{
				toRemove.add(f);
				me.addScore(4);
			}
			if (f instanceof Boss && ((Boss) f).canEat(me) && me.getScore()<80)
			{
				over = true;
			}
		}
		fishes.removeAll(toRemove);
	}
}
