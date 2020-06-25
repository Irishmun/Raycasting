import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static java.lang.Math.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Program
{
    private static final double PI = 3.1415926535;
    private static final double P2 = PI / 2;
    private static final double P3 = 3 * PI / 2;
    private static final double DR = 0.0174533;//one degree in radians

    private float px, py, pdx, pdy, pa;
    private int mapX = 8, mapY = 8, mapS = 64;
    private int map[] =
            {
                    1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 2, 0, 0, 0, 0, 1,
                    1, 0, 2, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 1, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1
            };
    private long window;

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
        for (y = 0; y < mapY; y++) {
            for (x = 0; x < mapX; x++) {
                if (map[y * mapX + x] > 0) {
                    r = 1;
                    g = 1;
                    b = 1;
                } else {
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
        int r=0, mx=0, my=0, mp=0, dof=0;
        float rx=0, ry=0, ra=0, xo=0, yo=0, disT=0;
        ra = (float)(pa - DR * 30);
        if (ra < 0) { ra += 2 * PI; }
        if (ra > 2 * PI) { ra -= 2 * PI; }
        for (r = 0; r < 60; r++)
        {
            //horizontal line checking
            dof = 0;
            float disH = 1000000, hx = px, hy = py;
            float aTan = (float)(-1 / tan(ra));
            if (ra > PI)//looking up
            {
                ry = (float)((((int)py >> 6) << 6) - 0.0001);
                rx = (py - ry) * aTan + px;
                yo = -64;
                xo = -yo * aTan;
            }
            if (ra < PI)//looking down
            {
                ry = (((int)py >> 6) << 6) + 64;
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
                mx = (int)(rx) >> 6;
                my = (int)(ry) >> 6;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapX * mapY && map[mp] >0)
                {
                    mh = map[mp];
                    hx = rx;
                    hy = ry;
                    disH = dist(px, py, hx, hy, ra);
                    dof = 8;
                }
                else
                {
                    rx += xo;
                    ry += yo;
                    dof += 1;
                }
            }

            //Vertical Line Checking
            dof = 0;
            float disV = 1000000, vx = px, vy = py;
            float nTan = (float)-tan(ra);
            if (ra > P2&& ra < P3)//looking left
            {
                rx = (float)((((int)px >> 6) << 6) - 0.0001);
                ry = (px - rx) * nTan + py;
                xo = -64;
                yo = -xo * nTan;
            }
            if (ra < P2 || ra > P3)//looking right
            {
                rx = (((int)px >> 6) << 6) + 64;
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
                mx = (int)(rx) >> 6;
                my = (int)(ry) >> 6;
                mp = my * mapX + mx;
                if (mp > 0 && mp < mapX * mapY && map[mp]>0)
                {
                    mv = map[mp];
                    vx = rx;
                    vy = ry;
                    disV = dist(px, py, vx, vy, ra);
                    dof = 8;
                }
                else
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
                glColor3f(0.9f, 0, 0);
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
                glColor3f(0.7f, 0, 0);
                if (mh == 2)
                {
                    glColor3f(0, 0, 0.4f);
                }
            }
            glLineWidth(1);
            glBegin(GL_LINES);
            glVertex2f(px, py);
            glVertex2f(rx, ry);
            glEnd();

            float ca = pa - ra;
            if (ca < 0) { ca += 2 * PI; }
            if (ca > 2 * PI) { ca -= 2 * PI; }
            disT = (float)(disT * cos(ca));
            float lineH = (mapS * 320) / disT;//set line height
            if (lineH > 320) { lineH = 320; }
            float lineO = 160 - lineH / 2;
            glLineWidth(8);
            glBegin(GL_LINES);
            glVertex2i(r * 8 + 530, (int)lineO);
            glVertex2i(r * 8 + 530, (int)(lineH + lineO));
            glEnd();

            ra += DR;
            if (ra < 0) { ra += 2 * PI; }
            if (ra > 2 * PI) { ra -= 2 * PI; }
        }
    }

    private void buttons()
    {
        if (glfwGetKey(window,GLFW_KEY_A) == GL_TRUE) {
            pa -= 0.1;
            if (pa < 0) {
                pa += 2 * PI;
            }
            pdx = (float) cos(pa) * 5;
            pdy = (float) sin(pa) * 5;
        }
        if (glfwGetKey(window,GLFW_KEY_D) == GL_TRUE) {
            pa += 0.1;
            if (pa > 2 * PI) {
                pa -= 2 * PI;
            }
            pdx = (float) cos(pa) * 5;
            pdy = (float) sin(pa) * 5;
        }
        if (glfwGetKey(window,GLFW_KEY_W) == GL_TRUE) {
            px += pdx;
            py += pdy;
        }
        if (glfwGetKey(window,GLFW_KEY_S) == GL_TRUE) {
            px -= pdx;
            py -= pdy;
        }
    }

    private void run()
    {
        init();
        loop();
    }

    private void loop()
    {
        while (!glfwWindowShouldClose(window)) {
            buttons();
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            drawMap2D();
            drawRays2D();
            drawPlayer();
            /*glBegin(GL_QUADS);
            glColor4f(1, 0, 0, 0);
            glVertex2f(-0.5f, 0.5f);
            glColor4f(0, 1, 0, 0);
            glVertex2f(0.5f, 0.5f);
            glColor4f(0, 0, 1, 0);
            glVertex2f(0.5f, -0.5f);
            glColor4f(1, 1, 0, 0);
            glVertex2f(-0.5f, -0.5f);
            glEnd();*/
            glfwSwapBuffers(window);
        }
        glfwTerminate();
    }

    private void init()
    {
        if (!glfwInit()) {
            throw new IllegalStateException("failed to initlialize GLFW");
        }

        window = glfwCreateWindow(1024, 512, "Raycaster", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("failed to create window");
        }

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glClearColor(0.3f, 0.3f, 0.3f, 0f);
        glOrtho(0, 1024, 512, 0, -1, 1);
        px = 300;
        py = 300;
        pdx = (float) cos(pa) * 5;
        pdy = (float) sin(pa) * 5;

    }

    public static void main(String[] args)
    {
        new Program().run();
    }
}


//public class Program
//{
//    // The window handle
//    private long window;
//
//    public static void main(String[] args) {
//        new Program().run();
//    }
//
//    private void init() {
//        GLFWErrorCallback.createPrint(System.err).set();
//
//        if (!glfwInit())
//            throw new IllegalStateException("Unable to initialize GLFW");
//
//        glfwDefaultWindowHints();
//        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
//
//        // Create the window
//        window = glfwCreateWindow(1024, 512, "Raycaster", NULL, NULL);
//        if (window == NULL)
//            throw new RuntimeException("Failed to create the GLFW window");
//
//        // Setup a key callback.
//        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
//                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//        });
//
//        // Get the thread stack and push a new frame
//        try (MemoryStack stack = stackPush()) {
//            IntBuffer pWidth = stack.mallocInt(1);
//            IntBuffer pHeight = stack.mallocInt(1);
//        }
//
//        glfwMakeContextCurrent(window);
//        glfwSwapInterval(1);
//
//        glfwShowWindow(window);
//    }
//
//    private void loop() {
//        GL.createCapabilities();
//
//        // Set the clear color
//        glClearColor(0.3f, 0.3f, 0.3f, 0f);
//
//        while (!glfwWindowShouldClose(window)) {
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//            glfwSwapBuffers(window); // swap the color buffers
//
//            // Poll for window events. The key callback above will only be
//            // invoked during this call.
//            glfwPollEvents();
//        }
//    }
//
//    public void run() {
//
//        init();
//        loop();
//
//        // Free the window callbacks and destroy the window
//        glfwFreeCallbacks(window);
//        glfwDestroyWindow(window);
//
//        // Terminate GLFW and free the error callback
//        glfwTerminate();
//        glfwSetErrorCallback(null).free();
//    }
//}