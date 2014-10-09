package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 25/06/2014.
 */
public class MapObject extends ALSObject {

    public World world;
    public boolean removed;

    public MapObject(int width, int height, Vector2 position, World world){
        this.width = width;
        this.height = height;
        this.positionX = (int)position.x;
        this.positionY = (int)position.y;
        this.world = world;
        this.removed = false;
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {}

    public void update(){}

    public void remove(){
        removed = true;
    }
}
