package util;

import java.awt.*;

public class Default
{
    private static final int WINDOW_WIDTH = 800; // px
    private static final int WINDOW_HEIGHT = 800; // px
    private static final int DEFAULT_X = 500; // px
    private static final int DEFAULT_Y = 200; // px
    private static final int ME_SIDE_LENGTH = 64;
    private static final int SMALL_SIDE_LENGTH = 32;
    private static final int BOSS_SIDE_LENGTH = 128;
    private static final Font MY_FONT = FontLoader.loadFont("/fonts/臺灣新細明體.ttf", 30f);
    private static final boolean devMode = true;

    public static int getWindowWidth()
    {
        return WINDOW_WIDTH;
    }
    public static int getWindowHeight()
    {
        return WINDOW_HEIGHT;
    }
    public static int getDefaultX()
    {
        return DEFAULT_X;
    }
    public static int getDefaultY()
    {
        return DEFAULT_Y;
    }
    public static int getMeSideLength()
    {
        return ME_SIDE_LENGTH;
    }
    public static int getSmallSideLength()
    {
        return SMALL_SIDE_LENGTH;
    }
    public static int getBossSideLength()
    {
        return BOSS_SIDE_LENGTH;
    }
    public static Font getMyFont()
    {
        return MY_FONT;
    }
    public static  boolean isDevMode()
    {
        return devMode;
    }
}
