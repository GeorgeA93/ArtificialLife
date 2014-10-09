package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.ALSObject;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 30/09/2014.
 */
public class Predator extends ALSObject{

    private World world;

    public Predator(World world){
        this.world = world;
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        this.width = Map.TILE_SIZE;
        this.height = Map.TILE_SIZE;


    }

    public void update(double timeSpeed){

    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
       // spriteBatch.draw(Content.cat[frame][direction], positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }


}
