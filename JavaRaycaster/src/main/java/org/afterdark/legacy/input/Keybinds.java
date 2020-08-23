package org.afterdark.legacy.input;

import static org.lwjgl.glfw.GLFW.*;

public class Keybinds
{
    private long window;

    public Keybinds(long window)
    {
        this.window = window;
    }

    public boolean getForward()
    {
        return glfwGetKey(window, GLFW_KEY_W) == GLFW_TRUE;
    }
    public boolean getBackward()
    {
        return glfwGetKey(window, GLFW_KEY_S) == GLFW_TRUE;
    }
    public boolean getLeft()
    {
        return glfwGetKey(window, GLFW_KEY_A) == GLFW_TRUE;
    }
    public boolean getRight()
    {
        return glfwGetKey(window, GLFW_KEY_D) == GLFW_TRUE;
    }

    /**
     * Gets whether the requested key is pressed or not.
     * @param key the key that is pressed (can be int, prioritize GLW_KEY_*)
     */
    public boolean getKeyDown(int key)
    {
        return glfwGetKey(window, key) == GLFW_TRUE;
    }
}
