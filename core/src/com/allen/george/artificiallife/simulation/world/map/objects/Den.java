package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 01/10/2014.
 */
public class Den extends MapObject {

    public Den(int width, int height, Vector2 position, World world) {
        super(width, height, position, world);
    }

    @Override
    public void update() {
        //check to see if a life form is over the apple
        if(world == null){
            System.out.println("WORLD NULL");
        }

        if(world.getGeneticEngine() == null){
            System.out.println("GENETIC EWNGINE NULL");
        }
        if(world.getGeneticEngine().getPhenotypes() == null){
            System.out.println("LIFEFORMS NULL");
        }


        for(int i = 0; i < world.getGeneticEngine().getPhenotypes().size(); i ++){
            LifeForm lifeForm = world.getGeneticEngine().getPhenotypes().get(i);
            int lx = world.getGeneticEngine().getPhenotypes().get(i).positionX;
            int ly = world.getGeneticEngine().getPhenotypes().get(i).positionY;

            if(lifeForm.getTargetObject() == null){
                continue;
            }

            if(lx == this.positionX && ly == this.positionY){
                this.apply((world.getGeneticEngine().getPhenotypes().get(i)));
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.draw(Content.denTile, positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }

    public void apply(LifeForm lifeForm){
        lifeForm.SLEEPS_TAKEN += 10;

        lifeForm.setTargetObject(null);
        lifeForm.setEnergy(lifeForm.getMaxEnergy());
        lifeForm.setThirst(6f);
        lifeForm.setHunger(4f);
        lifeForm.moveInRandomDirection(true);
    }


}
