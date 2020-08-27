package org.afterdark.raycaster;

import org.afterdark.raycaster.World.Map;
import org.afterdark.raycaster.util.Debug;
import org.afterdark.raycaster.util.Window;
import org.afterdark.raycaster.Config.*;
import org.lwjgl.Version;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main
{
    long window;
    private String Title = "Raycaster";
    private float R = 0, G = 0, B = 0, A = 0;//opengl color range is between 0 and 1
    private Window win;
    private Map map = new Map();

    public static void main(String[] args)
    {
        new Main().Run();
    }

    void Run()
    {
        Debuglog("Detected LWJGL version: " + Version.getVersion());
        init(); //creates game window and sets up additional settings
        loop(); //actual game loop
    }

    void init()
    {
        setEnabled();
        win = new Window(Config.getWindowWidth(), Config.getWindowHeight(), Title, Config.getFullScreen(), Config.getShareResources());
        if (!glfwInit())
        {
            throw new IllegalStateException("failed to initialize GLFW");
        }
        window = win.createWindowAndSetCurrent();
        GL.createCapabilities();
        glClearColor(R, G, B, A);
        glOrtho(0, Config.getWindowWidth(), Config.getWindowHeight(), 0, -1, 1);
        Debuglog("Created window with width:" + Config.getWindowWidth() + ", height:" + Config.getWindowHeight() + " and background color(RGBA):" + R + "," + G + "," + B + "," + A);

    }

    void loop()
    {
        while (!glfwWindowShouldClose(window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //actual game loop
            if (Config.getShowWorldMap())
            {
                //map.drawIntMap(Config.getTexturedGame());
                map.drawCharMap(Config.getTexturedGame());
            }
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwTerminate();
    }

    private void setEnabled()
    {
        Debuglog("Enabling settings");
        if (Config.getTexturedGame())
        {
            glEnable(GL_TEXTURE_2D);
            Debuglog("Enabled Texture_2d");
        }
        if (Config.getShowWorldMap())
        {
            int W = Config.getWindowWidth();
            Config.setWindowWidth(W *2);
            Debuglog("Enabled Top-view map visibility (this will double the window width)");
        }
        Debuglog("Done enabling settings (if \"Enabling settings\" show right above this message, no additional settings were enabled");
    }

    private void Debuglog(String log)
    {
        Debug.log("[Main]: " + log);
    }
}
