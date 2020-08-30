package org.afterdark.raycaster.Entities;

public class Entity
{
    public float posX=0,posY=0,Rotation=0;

    public boolean alwaysFacingCamera = false;//whether the object will have one sprite(true) or directional sprites(false)
    public boolean animated = false; //whether the object has animations(true) or not(false)
    //public Sprite[][] facings //two dimensional array of sprites, y for directions, x for animation cycle.
}

