package org.afterdark.raycaster.World;

import org.afterdark.raycaster.Config;
import org.afterdark.raycaster.util.Window.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.lwjgl.opengl.GL11.*;

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
                            drawTexturedSquare(x, y, true, 1 );
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
                            drawTexturedSquare(x, y, true ,1);
                            break;
                        default:
                            drawTexturedSquare(x, y, false , 0);
                            break;
                    }
                }
            }
        }
    }

    private void drawUntexturedSquare(int x, int y, boolean wall)
    {
        int squareX = (Config.getWindowWidth() / 2) / mapWidth;
        int squareY = (Config.getWindowHeight() / mapHeight);
        int xo = x * squareX;
        int yo = y * squareY;
        if (wall)
        {
            glColor3f(1, 1, 1);
        } else
        {
            //glColor3f(.3f, .3f, .3f);
            glColor3f(0, 0, 0);
        }
        glBegin(GL_QUADS);
        glVertex2i(xo + 1, yo + 1);
        glVertex2i(xo + 1, yo + squareY - 1);
        glVertex2i(xo + squareX - 1, yo + squareY - 1);
        glVertex2i(xo + squareX - 1, yo + 1);
        glEnd();
    }

    private void drawTexturedSquare(int x, int y, boolean wall, int TextureID)
    {
        throw new NotImplementedException();
    }
    /*methods:
        -draw map from int array (bool textured)
        -draw map from char array (bool textured)
        -draw map textured
        -draw map untextured
     */


}
