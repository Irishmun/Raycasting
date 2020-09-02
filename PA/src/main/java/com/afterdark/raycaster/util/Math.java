package com.afterdark.raycaster.util;

import static java.lang.Math.*;

public class Math
{

    public float dist(float posX, float posY, float resX,float resY)
    {
        return (float)sqrt(pow((resX-posX),2)+pow((resY-posY),2));
    }
}
