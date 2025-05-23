package gamemain;

import entity.Fish;
import entity.Me;
import entity.SmallFish;
import util.Defalt;
import util.FishSpawner;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
	//	private BufferedImage backgroundImage;
	private final List<Fish> fishes = new ArrayList<>();
	private final Me me;
//	private SmallFish smallFish;
	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private BufferedImage backgroundCache;
	private final FishSpawner fishSpawner;

	public GamePanel() {
		me = new Me(Defalt.getWindowWidth(), Defalt.getWindowHeight()); // 先写死
		fishSpawner = new FishSpawner(fishes, Defalt.getWindowWidth(), Defalt.getWindowHeight());


		setFocusable(true);        // 允许获取焦点
//		requestFocusInWindow();    // 尝试请求焦点
		setDoubleBuffered(true);
		addKeyListener(this);      // 注册键盘监听器
		// 延迟请求焦点，确保窗口激活后才请求
		SwingUtilities.invokeLater(this::requestFocusInWindow);

//		createBackgroundCache();

		me.setX(Defalt.getDefaultX());
		me.setY(Defalt.getDefaultY());

//		loadBackgroundIMG("/img/sea.jpg");
		fishes.add(me);

		controlTimer();
//		startFishSpawner();

	}

	//ImageLoader在util里面
//	public void loadBackgroundIMG(String path)
//	{
//		backgroundImage = ImageLoader.loadImage(path);
//	}
	private void createBackgroundCache() {
		if (getWidth() <= 0 || getHeight() <= 0) {
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

		for (int x = 0; x < getWidth(); x += spacing) {
			g2.fillRect(x, 0, stripeWidth, getHeight());
		}

		g2.dispose();
	}


	//下面这个函数会自己调用，不需要手动使用
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (backgroundCache == null || backgroundCache.getWidth() != getWidth() || backgroundCache.getHeight() != getHeight()) {
			createBackgroundCache();
		}

		g.drawImage(backgroundCache, 0, 0, null);


		// 绘制所有鱼
		for (Fish f : fishes) {
			f.draw(g);
		}
		// 显示分数
		g.setColor(Color.BLACK);
		g.setFont(new Font("Mono", Font.BOLD, 24));
		g.drawString("Score: " + me.getScore(), 20, 30);

		//解决诡异的可变刷新频率导致卡顿问题
		Toolkit.getDefaultToolkit().sync();
	}

	// 以下是 KeyListener 接口需要实现的三个方法
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_NUMPAD8, 224 -> upPressed = true;
			case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, 225 -> downPressed = true;
			case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_NUMPAD4, 226 -> leftPressed = true;
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_NUMPAD6, 227 -> rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP, KeyEvent.VK_W, KeyEvent.VK_NUMPAD8, 224 -> upPressed = false;
			case KeyEvent.VK_DOWN, KeyEvent.VK_S, KeyEvent.VK_NUMPAD2, 225 -> downPressed = false;
			case KeyEvent.VK_LEFT, KeyEvent.VK_A, KeyEvent.VK_NUMPAD4, 226 -> leftPressed = false;
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D, KeyEvent.VK_NUMPAD6, 227 -> rightPressed = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// 不用处理
	}


	private void controlTimer() {
		int fps = 60;
		int delay = 1000 / fps;

		// 每几帧生成一条新的鱼
//		AtomicInteger spawnCounter = new AtomicInteger();

		Timer timer = new Timer(delay, _ -> {
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

			//生成小鱼
			fishSpawner.update();

			// 小鱼移动和清除游出边界的鱼
			fishes.removeIf(f -> f instanceof SmallFish && ((SmallFish) f).isOutOfScreen(getWidth()));
			for (Fish f : fishes) {
				if (f instanceof SmallFish) {
					f.move();
				}
			}
			//加分机制
			List<Fish> toRemove = new ArrayList<>();
			for (Fish f : fishes) {
				if (f instanceof SmallFish && me.canEat(f)) {
					toRemove.add(f);
					me.addScore(1); // 你可以自己定义加几分
				}
			}
			fishes.removeAll(toRemove);


			repaint();
		});
		timer.start();
	}
}
