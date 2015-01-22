package com.allen.george.artificiallife.simulation.world.map.objects.food;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by George on 20/07/2014.
 */
public class Apple extends Food {

    public Apple(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    public void update() {

        if(world == null){
            System.out.println("WORLD NULL");
        }

        if(world.getGeneticEngine() == null){
            System.out.println("GENETIC EWNGINE NULL");
        }
        if(world.getGeneticEngine().getPhenotypes() == null){
            System.out.println("LIFEFORMS NULL");
        }



        //check to see if a life form is over the apple
        for(int i = 0; i < world.getGeneticEngine().getPhenotypes().size(); i ++){
            LifeForm lifeForm = world.getGeneticEngine().getPhenotypes().get(i);
            int lx = lifeForm.positionX;
            int ly = lifeForm.positionY;

            if(lifeForm.getTargetObject() == null) continue;
            if(lifeForm.getTargetObject() != this) continue;

            if(lx == this.positionX && ly == this.positionY){
                this.apply((lifeForm));
                this.world.getMap().getInteractiveLayer().addTile(this.positionX, this.positionY, Tile.NULL_TILE.getTileID());
                this.remove();
                lifeForm.setTargetObject(null);
            }
        }
    }

    public void render(SpriteBatch spriteBatch,OrthographicCamera camera) {
        spriteBatch.draw(Content.apple, positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }


}
