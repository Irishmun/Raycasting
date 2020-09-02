package com.afterdark.raycaster;

import com.afterdark.raycaster.Entities.Player;
import com.afterdark.raycaster.World.Map;
import com.afterdark.raycaster.World.MapDrawing;
import com.afterdark.raycaster.util.Debug;
import com.afterdark.raycaster.util.Window;
import org.lwjgl.Version;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


//past "render distance", make each distance more white, adding fog
//add multiple floors by means of raycasting on multiple arrays, having each one be a different floor
public class Main
{
    long window;
    private String Title = "Raycaster";
    private float R = 0, G = 0, B = 0, A = 0;//opengl color range is between 0 and 1
    private Window win;
    private double time = 0, oldTime = 0;

    private MapDrawing map;
    private Player player;
    private Caster caster;

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
        Map _map = new Map();
        player = new Player(22,12,_map.getMapWidth(),_map.getMapHeight());
        map = new MapDrawing(_map);
        caster = new Caster(player,_map);
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
                map.drawIntMap(Config.getTexturedGame());
                player.drawPlayerOn2DMap();
            }
            caster.castRay();
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
            Config.setWindowWidth(W * 2);
            Debuglog("Enabled Top-view map visibility (this will double the window width)");
        }
        Debuglog("Done enabling settings (if \"Enabling settings\" show right above this message, no additional settings were enabled");
    }

    private void Debuglog(String log)
    {
        Debug.log("[Main]: " + log);
    }
}
