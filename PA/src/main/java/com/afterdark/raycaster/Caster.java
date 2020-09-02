package com.afterdark.raycaster;

import com.afterdark.raycaster.Entities.Player;
import com.afterdark.raycaster.World.Map;
import com.afterdark.raycaster.util.Shapes;

public class Caster
{
    private Player player = null;
    private Map map;
    private int width = 0, widthModifier = 0, height = 0;//screen width, screen width modifier value, height of screen.
    private int lineHeight = 0, drawStart = 0, drawEnd = 0;//total height of line, bottom y of line, top y of line
    private float r, g, b;

    public Caster(Player player, Map map)
    {
        this.player = player;
        this.map = map;
        //width = Config.getShowWorldMap() == true ? Config.getWindowWidth() / 2 : Config.getWindowWidth();
        width = Config.getWindowWidth();
        widthModifier = Config.getShowWorldMap() == true ? Config.getScreenWidthModifier() : 0;
        height = Config.getWindowHeight();
    }

    public void castRay()
    {
        float cameraX = 0;//maybe move to player (SOLID)
        float rayDirX = 0, rayDirY = 0;
        float deltaDistX = 0, deltaDistY = 0;
        int mapX = 0, mapY = 0;
        float sideDistX = 0, sideDistY = 0, perpWallDist = 0;
        int stepX = 0, stepY = 0;
        int hit, side = 0;


        for (int x = 0; x < width; x++)
        {
            //calculate ray position and direction
            cameraX = 2 * x / width - 1;
            rayDirX = player.dirX + player.planeX * cameraX;
            rayDirY = player.dirY + player.planeY * cameraX;
            //which map box the player is in
            mapX = (int) player.posX;
            mapY = (int) player.posY;
            //length of ray from one x or y-side to the next
            deltaDistX = pyth(rayDirX);
            deltaDistY = pyth(rayDirY);

            hit = 0;

            if (rayDirX < 0)
            {
                stepX = -1;
                sideDistX = (player.posX - mapX) * deltaDistX;
            } else
            {
                stepX = 1;
                sideDistX = (mapX + 1 - player.posX) * deltaDistX;
            }
            if (rayDirY < 0)
            {
                stepY = -1;
                sideDistY = (player.posY - mapY) * deltaDistY;
            } else
            {
                stepY = 1;
                sideDistY = (mapY + 1 - player.posY) * deltaDistY;
            }

            //DDA
            while (hit == 0)
            {
                if (sideDistX < sideDistY)
                {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else
                {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if (map.getWorld()[mapX][mapY] > 0)//check if ray hit wall
                {
                    hit = 1;
                }
            }
            //calculate projected distance on camera direction (euclidean would give fisheye)
            perpWallDist = side == 0 ? dist(mapX, stepX, player.posX, rayDirX) : dist(mapY, stepY, player.posY, rayDirY);

            setDrawValues(perpWallDist);
            setColor(mapX, mapY, side);

            if (Config.getShowWorldMap() == false)
            {
                Shapes.drawWall(drawStart, drawEnd, x);
            } else
            {
                Shapes.drawWall(drawStart, drawEnd, x + width);
            }

        }
    }

    //private void calcInitStepDist(float rayDir,int step,float sideDist,float playerPos, int map, float deltaDist)
    //{
    //    if(rayDir < 0)
    //    {
    //        step = -1;
    //        sideDist = (playerPos - map) * deltaDist;
    //    }
    //    else
    //    {
    //        step = 1;
    //        sideDist = (map + 1 -playerPos)* deltaDist;
    //    }
    //}

    private float pyth(float direction)
    {
        return Math.abs(1 / direction);
    }

    private float dist(int map, int step, float playerPos, float rayDir)
    {
        return (map - playerPos + (1 - step) / 2) / rayDir;
    }

    private void setDrawValues(float wallDist)
    {
        //calculate total height of the line to be drawn
        lineHeight = (int) (height / wallDist);
        //calculate lowest and highest point of line to draw
        drawStart = (-lineHeight / 2) + (height / 2);
        if (drawStart < 0)
        {
            drawStart = 0;
        }
        drawEnd = (lineHeight / 2 + height / 2);
        if (drawEnd >= height)
        {
            drawEnd = height - 1;
        }
    }

    private void setColor(int x, int y, int side)
    {
        switch (map.getWorld()[x][y])
        {
            case 1://red
                r = 1;
                g = 0;
                b = 0;
                break;
            case 2://green
                r = 0;
                g = 1;
                b = 0;
                break;
            case 3://blue
                r = 0;
                g = 0;
                b = 1;
                break;
            case 4://white
                r = 1;
                g = 1;
                b = 1;
                break;
            default://yellow
                r = 1;
                g = 1;
                b = 0;
                break;
        }

        if (side == 1)
        {
            r /= 2;
            g /= 2;
            b /= 2;
        }

    }
}