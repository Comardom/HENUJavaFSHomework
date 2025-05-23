package util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader
{
    /**
     * 从资源路径加载字体文件并返回指定大小的字体对象。
     * @param resourcePath 资源路径，必须以 '/' 开头，比如 "/fonts/MyFont.ttf"
     * @param size 字体大小（float）
     * @return 加载成功返回字体对象，失败返回默认字体 Serif
     */
    public static Font loadFont(String resourcePath, float size)
    {
        try (InputStream is = FontLoader.class.getResourceAsStream(resourcePath))
        {
            if (is == null)
            {
                System.err.println("Font resource not found: " + resourcePath);
                return new Font("Serif", Font.PLAIN, (int)size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(size);

            // 注册字体到系统环境（可选）
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font;
        }
        catch (Exception e)
        {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return new Font("Serif", Font.PLAIN, (int)size);
        }
    }
}
