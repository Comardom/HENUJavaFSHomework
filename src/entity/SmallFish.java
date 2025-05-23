package entity;

import util.Default;
import util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;


public class SmallFish extends Fish
{
    private static final BufferedImage fishImage = ImageLoader.loadImage("/img/SmallFish.png");

    public SmallFish(int panelWidth, int panelHeight)
    {
        super(
                Math.random() < 0.5 ? -Default.getSmallSideLength() : panelWidth,
                (int)(Math.random() * (panelHeight - Default.getSmallSideLength())),
                Default.getSmallSideLength(), Default.getSmallSideLength(),
                Math.random() < 0.5,  // 是否朝左
                true                  // 可被吃
        );
        this.speed = (int) (2 + Math.random() * 4); // 2~5的速度
    }


    public boolean isOutOfScreen(int panelWidth)
    {
        return (isFaceLeft && x + width < 0) || (!isFaceLeft && x > panelWidth);
    }

    @Override
    public void move()
    {
        x += isFaceLeft ? -speed : speed;
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
