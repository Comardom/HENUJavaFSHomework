package entity;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import util.Default;
import util.ImageLoader;
import util.SvgLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class SmallFish extends Fish
{
//    private static final BufferedImage fishImage = ImageLoader.loadImage("/img/SmallFish.png");
    private static final SVGDiagram fishSvg = SvgLoader.loadSvg("/img/SmallFish.svg");
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

        if (fishSvg != null)
        {
            AffineTransform oldTx = g2.getTransform();

            // 缩放因子
            double scaleX = (double) width / fishSvg.getWidth();
            double scaleY = (double) height / fishSvg.getHeight();

            g2.translate(x, y);
            if (!isFaceLeft)
            {
                // 水平翻转
                g2.scale(-scaleX, scaleY);
                g2.translate(-fishSvg.getWidth(), 0);
            }
            else
            {
                g2.scale(scaleX, scaleY);
            }

            try
            {
                fishSvg.render(g2);
            }
            catch (SVGException e)
            {
                throw new RuntimeException("Failed to render SVG for SmallFish", e);
            }

            g2.setTransform(oldTx);
        }
        else
        {
            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, width, height);
        }
    }
}
