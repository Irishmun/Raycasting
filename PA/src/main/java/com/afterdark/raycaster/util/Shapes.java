package com.afterdark.raycaster.util;

import com.afterdark.raycaster.Config;

import static org.lwjgl.opengl.GL11.*;

public class Shapes
{
    public static void drawRect(int x, int y, int width, int height)
    {
        glBegin(GL_QUADS);
        glVertex2i(x + 1, y + 1);
        glVertex2i(x + 1, y + height - 1);
        glVertex2i(x + width - 1, y + height - 1);
        glVertex2i(x + width - 1, y + 1);
        glEnd();
    }

    public static void drawImage(int x, int y, int width, int height)
    {
        //glBegin(GL_QUADS);
        //glVertex2i(x + 1, y + 1);
        //glVertex2i(x + 1, y + height - 1);
        //glVertex2i(x + width - 1, y + height - 1);
        //glVertex2i(x + width - 1, y + 1);
        //glEnd();
    }

    public static void drawWall(int drawStart, int drawEnd, int x)
    {
        glBegin(GL_QUADS);
        glVertex2i(x, drawStart);
        glVertex2i(x, drawEnd);
        glVertex2i(x + 1, drawEnd);
        glVertex2i(x + 1, drawStart);
        glEnd();
    }
}
