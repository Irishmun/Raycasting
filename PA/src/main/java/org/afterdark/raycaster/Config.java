package org.afterdark.raycaster;

public class Config
{
    private static int windowWidth = 640, windowHeight = 480;//width and height of game window
    private static int FullScreen = 0, ShareResources = 0;//whether the window will render in fullscreen and whether the game shares its resources
    private static boolean TexturedGame = false, ShowWorldMap = true; //whether the game is textured and whether the worldmap is shown (will double window width)
    private static int textureSize = 64; //size of each wall tile, in both width and height
    private static int atlasX = 5, atlasY = 5;// how many tiles there are in the X and Y direction

    //<editor-fold desc="Window getters and setters">
    public static int getWindowWidth()
    {
        return windowWidth;
    }

    public static void setWindowWidth(int width)
    {
        windowWidth = width;
    }

    public static int getScreenWidthModifier()
    {
        return windowWidth / 2;
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

    //</editor-fold>
    //<editor-fold desc="game option getters and setters">
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
    //</editor-fold>
    //<editor-fold desc="texture settings getters and setters">
    public static int getTextureSize()
    {
        return textureSize;
    }

    public static void setTextureSize(int value)
    {
        textureSize = value;
    }

    public static int getAtlasX()
    {
        return atlasX;
    }

    public static void setAtlasX(int value)
    {
        atlasX = value;
    }

    public static int getAtlasY()
    {
        return atlasY;
    }

    public static void setAtlasY(int value)
    {
        atlasY = value;
    }
    //</editor-fold>
}