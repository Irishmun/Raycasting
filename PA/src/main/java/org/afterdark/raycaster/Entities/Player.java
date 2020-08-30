package org.afterdark.raycaster.Entities;

import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity
{

    public int health = 100;//percentage
//WASD movement with strafing, QE for rotating

    float playerR = 1, playerG = 0, playerB = 0;//players color
    int playerSize = 8, playerLineWidth = 3;//size of player on map and size of directionline

    public Player()
    {
        posX = 50;
        posY = 50;
    }

    public Player(float PosX, float posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public void drawPlayerOn2DMap()
    {
        //draw player
        glColor3f(playerR, playerG, playerB);
        glPointSize(playerSize);
        glBegin(GL_POINTS);
        glVertex2f(posX, posY);
        glEnd();

        //draw player view direction as a line;
        glLineWidth(playerLineWidth);
        glBegin(GL_LINES);
        glVertex2f(posX, posY);//start of line
        glVertex2f(posX + 20, posY);//end of line (TEMP)
        glEnd();
    }

    //checks for controls and applies actions accordingly
    public void moveMent()
    {

    }
}
