package com.afterdark.raycaster.Entities;

import com.afterdark.raycaster.Config;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity
{
    public float planeX = 0, planeY = 0.66f;//player camera plane
    public int health = 100;//percentage
    //WASD movement with strafing, QE for rotating

    private float playerR = 1, playerG = 0, playerB = 0;//players color
    private int playerSize = 8, playerLineWidth = 3;//size of player on map and size of directionline
    private float modX = 1, modY = 1;//adjustment for drawing the player in the correct grid spot for the window

    public Player()
    {
        posX = 22;
        posY = 12;
        modX = 1;
        modY = 1;
    }

    public Player(float posX, float posY, int mapWidth, int mapHeight)
    {
        this.posX = posX;
        this.posY = posY;
        modX = Config.getWindowWidth() / mapWidth;
        modY = Config.getWindowHeight() / mapHeight;
        dirX = -1; dirY = 0; //change starting viewDir
    }

    public void drawPlayerOn2DMap()
    {
        //float
        //draw player
        glColor3f(playerR, playerG, playerB);
        glPointSize(playerSize);
        glBegin(GL_POINTS);
        glVertex2f(posX * modX, posY * modY);
        glEnd();

        //draw player view direction as a line;
        glLineWidth(playerLineWidth);
        glBegin(GL_LINES);
        glVertex2f(posX * modX, posY * modY);//start of line
        glVertex2f(posX * modX + (20 * dirX), posY * modY + (20 * dirY));//end of line (TEMP)
        glEnd();
    }

    //checks for controls and applies actions accordingly
    public void moveMent()
    {

    }
}
