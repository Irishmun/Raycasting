package org.afterdark.raycaster.util;

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
        glBegin(GL_QUADS);
        glVertex2i(x + 1, y + 1);
        glVertex2i(x + 1, y + height - 1);
        glVertex2i(x + width - 1, y + height - 1);
        glVertex2i(x + width - 1, y + 1);
        glEnd();
    }
}
