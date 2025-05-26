package util;


import java.awt.*;

public class TrashBin
{
    public static void _$()
    {

    }
    //如果需要引入背景图片
//	public void loadBackgroundIMG(String path)
//	{
//		backgroundImage = ImageLoader.loadImage(path);
//	}
    //以下这俩废弃*****************************
    //专门处理Timer
//    void controlTimer1() {
//        long[] lastTime = {System.nanoTime()};
//        int delay = 1000 / 144; // 144 FPS（可以改成 120 或其他）
//
//        Timer timer = new Timer(delay, e -> {
//            long now = System.nanoTime();
//            double deltaSeconds = (now - lastTime[0]) / 1_000_000_000.0;
//            lastTime[0] = now;
//
//            double speed = 300; // 每秒移动 300 像素
//            double step = speed * deltaSeconds;
//
//            if (upPressed) me.setY((int) (me.getY() - step));
//            if (downPressed) me.setY((int) (me.getY() + step));
//            if (leftPressed) {
//                me.setX((int) (me.getX() - step));
//                me.setFaceLeft(true);
//            }
//            if (rightPressed) {
//                me.setX((int) (me.getX() + step));
//                me.setFaceLeft(false);
//            }
//
//            repaint();
//        });
//        timer.start();
//    }

//    void controlTimer2() {
//        Thread gameThread = new Thread(() -> {
//            int fps = 240;
//            long frameDuration = 1000 / fps;
//
//            while (true) {
//                long startTime = System.currentTimeMillis();
//
//                // 更新逻辑（移动角色）
//                int speed = 4;
//                if (upPressed) me.setY(me.getY() - speed);
//                if (downPressed) me.setY(me.getY() + speed);
//                if (leftPressed) {
//                    me.setX(me.getX() - speed);
//                    me.setFaceLeft(true);
//                }
//                if (rightPressed) {
//                    me.setX(me.getX() + speed);
//                    me.setFaceLeft(false);
//                }
//
//                // 重绘必须在 EDT 线程中调用
//                SwingUtilities.invokeLater(this::repaint);
//
//                long elapsed = System.currentTimeMillis() - startTime;
//                long sleepTime = frameDuration - elapsed;
//
//                if (sleepTime > 0) {
//                    try {
//                        Thread.sleep(sleepTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        gameThread.setDaemon(true);
//        gameThread.start();
//    }

    //以上这俩废弃*****************************
//    @Override
//    public void draw(Graphics g)
//    {
//        Graphics2D g2 = (Graphics2D) g;
//
//
//
//        if (fishSvg != null)
//        {
//            if (isFaceLeft)
//            {
//                g2.drawImage(fishImage, x, y, width, height, null);
//            }
//            else
//            {
//                // 水平翻转
//                g2.drawImage(fishImage, x + width, y, -width, height, null);
//            }
//        }
//        else
//        {
//            g2.setColor(Color.BLUE);
//            g2.fillRect(x, y, width, height);
//        }
//    }
//    @Override
//    public void draw(Graphics g)
//    {
//        Graphics2D g2 = (Graphics2D) g;
//
//
//
//        if (fishImage != null)
//        {
//            if (nowFaceLeft)
//            {
//                g2.drawImage(fishImage, x, y, width, height, null);
//            }
//            else
//            {
//                // 水平翻转
//                g2.drawImage(fishImage, x + width, y, -width, height, null);
//            }
//        }
//        else
//        {
//            g2.setColor(Color.BLUE);
//            g2.fillRect(x, y, width, height);
//        }
//    }
//    @Override
//    public void draw(Graphics g)
//    {
//        Graphics2D g2 = (Graphics2D) g;
//        if (fishImage != null)
//        {
//            if (isFaceLeft)
//            {
//                g2.drawImage(fishImage, x, y, width, height, null);
//            }
//            else
//            {
//                // 水平翻转
//                g2.drawImage(fishImage, x + width, y, -width, height, null);
//            }
//        }
//        else
//        {
//            g2.setColor(Color.BLUE);
//            g2.fillRect(x, y, width, height);
//        }
//    }
}
