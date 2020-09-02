package com.afterdark.raycaster.World;

import com.afterdark.raycaster.Config;

import static com.afterdark.raycaster.util.Shapes.drawImage;
import static com.afterdark.raycaster.util.Shapes.drawRect;
import static org.lwjgl.opengl.GL11.glColor3f;

public class MapDrawing
{
    private Map map;

    public MapDrawing(Map map)
    {
        this.map = map;
    }

    public void drawIntMap(boolean textured)
    {
        int squareWidth = (Config.getWindowWidth() / 2) / map.getMapWidth();
        int squareHeight = (Config.getWindowHeight() / map.getMapHeight());
        if (!textured)
        {
            for (int y = 0; y < map.getMapHeight(); y++)
            {
                for (int x = 0; x < map.getMapWidth(); x++)
                {
                    if (map.getWorld()[y][x] == 0)
                    {
                        drawUntexturedSquare(x, y, false);
                    } else
                    {

                        drawUntexturedSquare(x, y, true);
                    }
                }
            }
        } else
        {
            for (int y = 0; y < map.getMapHeight(); y++)
            {
                for (int x = 0; x < map.getMapWidth(); x++)
                {
                    switch (map.getWorld()[y][x])
                    {
                        case 1:
                            drawTexturedSquare(x, y, true, 1);
                            break;
                        default:
                            drawTexturedSquare(x, y, false, 0);
                            break;
                    }
                }
            }
        }
    }

    public void drawUntexturedSquare(int x, int y, boolean wall)
    {
        int squareWidth = (Config.getWindowWidth() / 2) / map.getMapWidth();
        int squareHeight = (Config.getWindowHeight() / map.getMapHeight());
        int xo = x * squareWidth;
        int yo = y * squareHeight;
        if (wall)
        {
            glColor3f(1, 1, 1);
        } else
        {
            glColor3f(.3f, .3f, .3f);
            //glColor3f(0, 0, 0);
        }
        drawRect(xo, yo, squareWidth, squareHeight);
    }

    public void drawTexturedSquare(int x, int y, boolean wall, int TextureID)
    {
        int squareWidth = (Config.getWindowWidth() / 2) / map.getMapWidth();
        int squareHeight = (Config.getWindowHeight() / map.getMapHeight());
        drawImage(x, y, squareWidth, squareHeight);
    }



}
