package org.afterdark.raycaster.World;

import org.afterdark.raycaster.Config;
import org.afterdark.raycaster.Graphics.Texture;
import org.afterdark.raycaster.Config.*;

import static org.afterdark.raycaster.util.Shapes.drawImage;
import static org.afterdark.raycaster.util.Shapes.drawRect;
import static org.lwjgl.opengl.GL11.glColor3f;

public class Map
{
    public int mapWidth = 16, mapHeight = 16;
    public int[] world = //easier in algorithm, more difficult/tedious to edit
            {
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
            };

    public String[] charWorld = //allows for easier editing, but not as useful within algorithm
            {
                    "1111111111111111",
                    "1              1",
                    "1  111         1",
                    "1  111         1",
                    "1  111         1",
                    "1              1",
                    "1              1",
                    "1              1",
                    "1              1",
                    "1              1",
                    "1              1",
                    "1              1",
                    "1         111111",
                    "1              1",
                    "1              1",
                    "1111111111111111"
            };

    public void drawIntMap(boolean textured)
    {
        int squareWidth = (Config.getWindowWidth() / 2) / mapWidth;
        int squareHeight = (Config.getWindowHeight() / mapHeight);
        if (!textured)
        {
            for (int y = 0; y < mapHeight; y++)
            {
                for (int x = 0; x < mapWidth; x++)
                {
                    if (world[y * mapWidth + x] == 0)
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
            for (int y = 0; y < mapHeight; y++)
            {
                for (int x = 0; x < mapWidth; x++)
                {
                    switch (world[y * mapWidth + x])
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

    public void drawCharMap(boolean textured)
    {

        char c;
        if (!textured)
        {
            for (int y = 0; y < mapHeight; y++)
            {
                for (int x = 0; x < charWorld[y].length(); x++)
                {
                    c = charWorld[y].charAt(x);

                    if (c == ' ' || c == '0')
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
            for (int y = 0; y < mapHeight; y++)
            {
                for (int x = 0; x < charWorld[y].length(); x++)
                {
                    c = charWorld[y].charAt(x);

                    switch (c)
                    {
                        case '1':
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
        int squareWidth = (Config.getWindowWidth() / 2) / mapWidth;
        int squareHeight = (Config.getWindowHeight() / mapHeight);
        int xo = x * squareWidth;
        int yo = y * squareHeight;
        if (wall)
        {
            glColor3f(1, 1, 1);
        } else
        {
            //glColor3f(.3f, .3f, .3f);
            glColor3f(0, 0, 0);
        }
        drawRect(xo, yo, squareWidth, squareHeight);
    }

    public void drawTexturedSquare(int x, int y, boolean wall, int TextureID)
    {
        int squareWidth = (Config.getWindowWidth() / 2) / mapWidth;
        int squareHeight = (Config.getWindowHeight() / mapHeight);
        drawImage(x, y, squareWidth, squareHeight);
    }

}
