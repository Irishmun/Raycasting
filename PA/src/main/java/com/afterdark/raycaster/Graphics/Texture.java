package com.afterdark.raycaster.Graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.stb.STBImage.*;

public class Texture
{
    //PUT ON HIATUS, WILL BE CONTINUED WHEN UNTEXTURED RAYCASTER IS WORKING.
    //https://learnopengl.com/Getting-started/Textures
    private int texWrap = GL_CLAMP_TO_BORDER; //will make texture clamp to border if texCoords are outside 0-1 range
    private int texFilter = GL_LINEAR;//texture filtering
    private float texCoords[] = {
            0.0f, 0.0f,  // lower-left corner
            1.0f, 0.0f,  // lower-right corner
            1.0f, 1.0f,  // top-right corner
            0.0f, 1.0f   // top-left corner
    };

    public Texture()
    {
    }

    public Texture(float[] texCoords)
    {
        this.texCoords = texCoords;
        setTexParams();
    }

    public Texture(float[] texCoords, int texWrap)
    {
        this.texCoords = texCoords;
        this.texWrap = texWrap;
        setTexParams();
    }

    public Texture(float[] texCoords, int texWrap, int texFilter)
    {
        this.texCoords = texCoords;
        this.texWrap = texWrap;
        this.texFilter = texFilter;
        setTexParams();
    }

    public static float[] getDefaultTexCoords()
    {
        float[] dtc = {
                0.0f, 0.0f,  // lower-left corner
                1.0f, 0.0f,  // lower-right corner
                1.0f, 1.0f,  // top-right corner
                0.0f, 1.0f   // top-left corner
        };
        return dtc;
    }
    public static int getDefaultTextureWrapping()
    {
        return GL_CLAMP_TO_BORDER;
    }
    public static int getDefaultTextureFilter()
    {
        return GL_LINEAR;
    }

    private void setTexParams()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, texWrap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, texWrap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, texFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, texFilter);
    }


}
