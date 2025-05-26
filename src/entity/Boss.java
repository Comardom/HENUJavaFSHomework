package entity;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import util.Default;
import util.SvgLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Boss extends Fish
{
//    private static final BufferedImage fishImage = ImageLoader.loadImage("/img/Boss.png");
    private static final SVGDiagram bossSvg = SvgLoader.loadSvg("/img/Boss.svg");
    private static boolean nowFaceLeft = Math.random() < 0.5;
    public Boss(int panelWidth, int panelHeight)
    {


        super(
                nowFaceLeft ? panelWidth : -Default.getBossSideLength(),  // 从相反方向进场
                (int)(Math.random() * (panelHeight - Default.getBossSideLength())),
                Default.getBossSideLength(), Default.getBossSideLength(),
                nowFaceLeft,
                false
        );
//        this.speed = (int) (2 + Math.random() * 4); // 2~5的速度
        this.speed = 1 + (int)(Math.random() * 2);  // 速度比小鱼慢些，比如1~2
    }

    @Override
    public void move()
    {
        x += nowFaceLeft ? -speed : speed;
    }


    @Override
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        if (bossSvg != null)
        {
            AffineTransform oldTx = g2.getTransform();

            // 缩放平移到指定位置和大小
            double scaleX = (double) width / bossSvg.getWidth();
            double scaleY = (double) height / bossSvg.getHeight();
            g2.translate(x, y);
            if (!nowFaceLeft)
            {
                // 如果朝右，水平翻转
                g2.scale(-scaleX, scaleY);
                g2.translate(-bossSvg.getWidth(), 0);
            }
            else
            {
                g2.scale(scaleX, scaleY);
            }

            try
            {
                bossSvg.render(g2);
            }
            catch (SVGException e)
            {
                throw new RuntimeException(e);
            }

            // 恢复变换
            g2.setTransform(oldTx);
        }
        else
        {
            g2.setColor(Color.BLUE);
            g2.fillRect(x, y, width, height);
        }
    }

}
