package org.afterdark.legacy;

import org.lwjgl.opengl.*;
import org.afterdark.legacy.input.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class OldRaycaster
{
    private static final double PI = 3.1415926535;
    private static final double P2 = PI / 2;
    private static final double P3 = 3 * PI / 2;
    private static final double DR = 0.0174533;//one degree in radians
    private static Boolean debugMode = true;
    private float SpeedModifier = 0.35f;
    private float SensitivityModifier = .5f;
    private Keybinds binds;

    private float px, py, pdx, pdy, pa;
    private int mapX = 8, mapY = 8, mapS = 64;
    //private int map[] =
    //        {
    //                1, 1, 1, 1, 1, 1, 1, 1,
    //                1, 0, 1, 0, 0, 0, 0, 1,
    //                1, 0, 0, 0, 0, 1, 0, 1,
    //                1, 0, 0, 0, 0, 1, 0, 1,
    //                1, 0, 0, 0, 0, 0, 0, 1,
    //                1, 0, 0, 0, 1, 1, 1, 1,
    //                1, 1, 0, 0, 0, 0, 0, 1,
    //                1, 1, 1, 1, 1, 1, 1, 1
    //        };
    private int map[] =
            {
                    1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 0, 1, 1, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1
            };

    private long window;

    public static void main(String[] args)
    {
        new OldRaycaster().run();
    }

    private void drawPlayer()
    {
        //draw player from topdown perspective
        glColor3f(1, 1, 0);
        glPointSize(8);
        glBegin(GL_POINTS);
        glVertex2f(px, py);
        glEnd();

        //Draw players view direction line
        glLineWidth(3);
        glBegin(GL_LINES);
        glVertex2f(px, py);
        glVertex2f(px + pdx * 5, py + pdy * 5);
        glEnd();
    }

    private void drawMap2D()
    {
        int x, y, xo, yo;
        float r, g, b;
        for (y = 0; y < mapY; y++)
        {
            for (x = 0; x < mapX; x++)
            {
                if (map[y * mapX + x] > 0)
                {
                    r = 1;
                    g = 1;
                    b = 1;
                } else
                {
                    r = 0;
                    g = 0;
                    b = 0;
                }
                xo = x * mapS;
                yo = y * mapS;
                glBegin(GL_QUADS);
                glColor3f(r, g, b);
                glVertex2f(xo + 1, yo + 1);
                glVertex2f(xo + 1, yo + mapS - 1);
                glVertex2f(xo + mapS - 1, yo + mapS - 1);
                glVertex2f(xo + mapS - 1, yo + 1);
                glEnd();
            }
        }
    }

    float dist(float ax, float ay, float bx, float by, float ang)
    {
        return (float) (sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay)));
    }

    private void drawRays2D()
    {
        int r = 0, mx = 0, my = 0, mp = 0, dof = 0;
        float rx = 0, ry = 0, ra = 0, xo = 0, yo = 0, disT = 0;
        ra = (float) (pa - DR * 30);
        if (ra < 0)
        {
            ra += 2 * PI;
        }
        if (ra > 2 * PI)
        {
            ra -= 2 * PI;
        }
        for (r = 0; r < 60; r++)
        {
            //horizontal line checking
            dof = 0;
            float disH = 1000000, hx = px, hy = py;
            float aTan = (float) (-1 / tan(ra));
            if (ra > PI)//looking up
            {
                ry = (float) ((((int) py >> 6) << 6) - 0.0001);
                rx = (py - ry) * aTan + px;
                yo = -64;
                xo = -yo * aTan;
            }
            if (ra < PI)//looking down
            {
                ry = (((int) py >> 6) << 6) + 64;
                rx = (py - ry) * aTan + px;
                yo = 64;
                xo = -yo * aTan;
            }
            if (ra == 0 || ra == PI)
            {
                rx = px;
                ry = py;
                dof = 8;
            }
            int mv = 0, mh = 0;
            while (dof < 8)
            {
                mx = (int) (rx) >> 6;
                my = (int) (ry) >> 6;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapX * mapY && map[mp] > 0)
                {
                    mh = map[mp];
                    hx = rx;
                    hy = ry;
                    disH = dist(px, py, hx, hy, ra);
                    dof = 8;
                } else
                {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }

            //Vertical Line Checking
            dof = 0;
            float disV = 1000000, vx = px, vy = py;
            float nTan = (float) -tan(ra);
            if (ra > P2 && ra < P3)//looking left
            {
                rx = (float) ((((int) px >> 6) << 6) - 0.0001);
                ry = (px - rx) * nTan + py;
                xo = -64;
                yo = -xo * nTan;
            }
            if (ra < P2 || ra > P3)//looking right
            {
                rx = (((int) px >> 6) << 6) + 64;
                ry = (px - rx) * nTan + py;
                xo = 64;
                yo = -xo * nTan;
            }
            if (ra == 0 || ra == PI)
            {
                rx = px;
                ry = py;
                dof = 8;
            }
            while (dof < 8)
            {
                mx = (int) (rx) >> 6;
                my = (int) (ry) >> 6;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapX * mapY && map[mp] > 0)
                {
                    mv = map[mp];
                    vx = rx;
                    vy = ry;
                    disV = dist(px, py, vx, vy, ra);
                    dof = 8;
                } else
                {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }
            if (disV < disH)
            {
                rx = vx;
                ry = vy;
                disT = disV;
                glColor3f(0.5f, 0.5f, 0.5f);
                if (mv == 2)
                {
                    glColor3f(0, 0, 0.7f);
                }
            }
            if (disH < disV)
            {
                rx = hx;
                ry = hy;
                disT = disH;
                glColor3f(0.25f, 0.25f, 0.25f);
                if (mh == 2)
                {
                    glColor3f(0, 0, 0.4f);
                }
            }
            if (debugMode)
            {
                glLineWidth(1);
                glBegin(GL_LINES);
                glVertex2f(px, py);
                glVertex2f(rx, ry);
                glEnd();
            }

            float ca = pa - ra;
            if (ca < 0)
            {
                ca += 2 * PI;
            }
            if (ca > 2 * PI)
            {
                ca -= 2 * PI;
            }
            disT = (float) (disT * cos(ca));
            float lineH = (mapS * 320) / disT;//set line height
            if (lineH > 320)
            {
                lineH = 320;
            }
            float lineO = 160 - lineH / 2;
            glLineWidth(8);
            glBegin(GL_LINES);
            if (debugMode)
            {
                glVertex2i(r * 8 + 530, (int) lineO);
                glVertex2i(r * 8 + 530, (int) (lineH + lineO));
            } else
            {
                glVertex2i(r * 8, (int) lineO);
                glVertex2i(r * 8, (int) (lineH + lineO));
            }
            glEnd();

            ra += DR;
            if (ra < 0)
            {
                ra += 2 * PI;
            }
            if (ra > 2 * PI)
            {
                ra -= 2 * PI;
            }
        }
    }

    private void buttons()
    {
        //if (binds.getLeft())
        //{
        //    px += (pdx - 1.5 * PI) * SpeedModifier;
        //    py += (pdy - 1.5 * PI) * SpeedModifier;
        //}
        //if (binds.getRight())
        //{
        //    px += (pdx - PI / 2) * SpeedModifier;
        //    py += (pdy - PI / 2) * SpeedModifier;
        //}
        if (binds.getForward())
        {
            px += pdx * SpeedModifier;
            py += pdy * SpeedModifier;
        }
        if (binds.getBackward())
        {
            px -= pdx * SpeedModifier;
            py -= pdy * SpeedModifier;
        }
        if (binds.getKeyDown(GLFW_KEY_D))
        {
            pa += 0.1 * SensitivityModifier;
            if (pa > 2 * PI)
            {
                pa -= 2 * PI;
            }
            pdx = (float) cos(pa) * 5;
            pdy = (float) sin(pa) * 5;
        }
        if (binds.getKeyDown(GLFW_KEY_A))
        {
            pa -= 0.1 * SensitivityModifier;
            if (pa < 0)
            {
                pa += 2 * PI;
            }
            pdx = (float) (cos(pa) * 5);
            pdy = (float) (sin(pa) * 5);
        }
    }

    private void run()
    {
        init();
        loop();
    }

    private void loop()
    {
        while (!glfwWindowShouldClose(window))
        {
            buttons();
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            if (debugMode)
            {
                drawMap2D();
                drawPlayer();
            }
            drawRays2D();
            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }

    private void init()
    {
        int width;
        int height;

        if (!debugMode)
        {
            width = 476;
            height = 320;
        } else
        {
            width = 1024;
            height = 512;
        }
        if (!glfwInit())
        {
            throw new IllegalStateException("failed to initlialize GLFW");
        }

        window = glfwCreateWindow(width, height, "Raycaster", 0, 0);
        if (window == 0)
        {
            throw new IllegalStateException("failed to create window");
        }

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        //glClearColor(0.3f, 0.3f, 0.3f, 0f);
        glClearColor(0, 0, 0, 0f);
        glOrtho(0, width, height, 0, -1, 1);
        px = 300;
        py = 300;
        pdx = (float) cos(pa) * 5;
        pdy = (float) sin(pa) * 5;
        binds = new Keybinds(window);
    }
}
