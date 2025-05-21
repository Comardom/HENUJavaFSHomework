package entity;

import util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SmallFish extends Fish
{
    private static final int ME_SIDE_LENGTH = 32;
    private static final BufferedImage fishImage = ImageLoader.loadImage("/img/Me.png");
    private static final Random rand = new Random();
    private static final int SCREEN_WIDTH = 800;

    public SmallFish(int panelWidth, int panelHeight)
    {
        super(
                Math.random() < 0.5 ? -ME_SIDE_LENGTH : panelWidth,
                (int)(Math.random() * (panelHeight - ME_SIDE_LENGTH)),
                ME_SIDE_LENGTH, ME_SIDE_LENGTH,
                Math.random() < 0.5,  // 是否朝左
                true                  // 可被吃
        );
        this.speed = (int) (2 + Math.random() * 2); // 2~4的速度
    }


    public boolean isOutOfScreen(int panelWidth)
    {
        return (isFaceLeft && x + width < 0) || (!isFaceLeft && x > panelWidth);
    }

    @Override
    public void move()
    {
        if(isFaceLeft)
        {
            x -= speed;
        }
        else
        {
            x += speed;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        if (fishImage != null)
        {
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

}
