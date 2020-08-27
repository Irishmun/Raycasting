package org.afterdark.raycaster.util;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class Window
{

    public int windowWidth, windowHeight;
    private int FullScreen, ShareResources;
    private String Title;

    public Window()
    {
        this.windowWidth = 1280;
        this.windowHeight = 720;
        this.Title = "OpenGL Window";
        this.FullScreen = 0;
        this.ShareResources = 0;
    }

    public Window(int windowWidth, int windowHeight, String Title, int FullScreen, int ShareResources)
    {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.Title = Title;
        this.FullScreen = FullScreen;
        this.ShareResources = ShareResources;
    }

    public long createWindowAndSetCurrent()
    {
        long temp = CreateWindow();
        glfwShowWindow(temp);
        glfwMakeContextCurrent(temp);
        return temp;
    }

    private long CreateWindow()
    {
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        long temp = glfwCreateWindow(windowWidth, windowHeight, Title, FullScreen, ShareResources);
        if (temp != 0)
        {
            return temp;
        } else
        {
            glfwTerminate();
            throw new RuntimeException("Failed to create window");
        }
    }

    private void closeWindow(long window)
    {
        glfwDestroyWindow(window);
    }

    public long closeCurrentWindowAndCreateNew(long window, int windowWidth, int windowHeight, String Title, int FullScreen, int ShareResources)
    {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.Title = Title;
        this.FullScreen = FullScreen;
        this.ShareResources = ShareResources;
        closeWindow(window);
        long temp = createWindowAndSetCurrent();
        return temp;
    }
}
