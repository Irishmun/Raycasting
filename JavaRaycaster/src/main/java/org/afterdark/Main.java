package org.afterdark;


//coords start at top left and move to bottom right (y goes down, x goes to the right)
//rotation starts at right and goes counterclockwise (N:90|E:0|S:270|W:180)
public class Main
{
    private int screenWidth = 320, screenHeight = 200;
    private int FOV = 60;//in degrees

    public static void main(String[] args)
    {
        new Main();
    }
}
