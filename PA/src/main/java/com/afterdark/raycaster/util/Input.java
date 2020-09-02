package com.afterdark.raycaster.util;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{

    private long window;

    public Input(long window)
    {
        this.window = window;
    }

    public boolean getForward()
    {
        return getKeyDown(GLFW_KEY_W);
    }

    public boolean getBackward()
    {
        return getKeyDown(GLFW_KEY_S);
    }

    public boolean getLeft()
    {
        return getKeyDown(GLFW_KEY_A);
    }

    public boolean getRight()
    {
        return getKeyDown(GLFW_KEY_D);
    }

    public boolean getKeyDown(int key)
    {
        return glfwGetKey(window, key) == GLFW_TRUE;
    }


}
