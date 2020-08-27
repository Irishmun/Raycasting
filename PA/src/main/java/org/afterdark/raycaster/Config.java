package org.afterdark.raycaster;

public class Config
{
    private static int windowWidth = 640, windowHeight = 480;
    private static int FullScreen = 0, ShareResources = 0;
    private static boolean TexturedGame = false, ShowWorldMap = true;

    public static int getWindowWidth()
    {
        return windowWidth;
    }

    public static void setWindowWidth(int width)
    {
        windowWidth = width;
    }

    public static int getWindowHeight()
    {
        return windowHeight;
    }

    public static void setWindowHeight(int height)
    {
        windowHeight = height;
    }

    public static int getFullScreen()
    {
        return FullScreen;
    }

    public static void setFullScreen(int value)
    {
        FullScreen = value;
    }

    public static void setFullScreen(boolean value)
    {
        if (value == true)
        {
            FullScreen = 1;
        } else
        {
            FullScreen = 0;
        }
    }


    public static int getShareResources()
    {
        return ShareResources;
    }

    public static void setShareResources(int value)
    {
        ShareResources = value;
    }

    public static void setShareResources(boolean value)
    {
        if (value == true)
        {
            ShareResources = 1;
        } else
        {
            ShareResources = 0;
        }
    }

    public static boolean getTexturedGame()
    {
        return TexturedGame;
    }

    public static void setTexturedGame(boolean value)
    {
        TexturedGame = value;
    }

    public static boolean getShowWorldMap()
    {
        return ShowWorldMap;
    }

    public static void setShowWorldMap(boolean value)
    {
        ShowWorldMap = value;
    }
}
