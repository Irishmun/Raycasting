import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

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

    public static void main(String[] args)
    {
        new Program().run();
    }

    private void run()
    {
        init();
        loop();
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
    }

    private void loop()
    {
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
        }
        glfwTerminate();
    }


}


//public class Program
//{
//    private static final double PI = 3.1415926535;
//    private static final double P2 = PI / 2;
//    private static final double P3 = 3 * PI / 2;
//    private static final double DR = 0.0174533;//one degree in radians
//    private float px, py, pdx, pdy, pa;
//    private int mapX = 8, mapY = 8, mapS = 64;
//    private int map[] =
//            {
//                    1, 1, 1, 1, 1, 1, 1, 1,
//                    1, 0, 2, 0, 0, 0, 0, 1,
//                    1, 0, 2, 0, 0, 0, 0, 1,
//                    1, 0, 0, 0, 0, 0, 0, 1,
//                    1, 0, 0, 0, 0, 0, 0, 1,
//                    1, 0, 0, 0, 0, 1, 0, 1,
//                    1, 0, 0, 0, 0, 0, 0, 1,
//                    1, 1, 1, 1, 1, 1, 1, 1
//            };
//
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