package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 25/06/2014.
 */
public class Object {

    public int width, height;
    public Vector2 position;
    public World world;
    public boolean removed;

    public Object(int width, int height, Vector2 position, World world){
        this.width = width;
        this.height = height;
        this.position = position;
        this.world = world;
        this.removed = false;
    }

    public void update(){};

    public void render(SpriteBatch spriteBatch, int xScroll, int yScroll, OrthographicCamera camera){};

    public void remove(){
        removed = true;
    }
}
