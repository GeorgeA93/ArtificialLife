package com.allen.george.artificiallife.simulation.world.map.objects.resources;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 27/06/2014.
 */
public class Water extends MapObject {

    private int cycleToChange;


    public Water(int width, int height, Vector2 position, World world){
        super(width,height, position, world);
        this.cycleToChange = 1 + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - 1) + 1));
    }

    public void update(){

        if(world.getDayNightCycler().getCycles() == cycleToChange){
            if(!world.getWeatherManager().isRaining()){
                world.getMap().getInteractiveLayer().removeWater(this, this.positionX, this.positionY, this.width);
            }
        }

        for(int i = 0; i < world.getGeneticEngine().getPhenotypes().size(); i ++){
            LifeForm lifeForm = world.getGeneticEngine().getPhenotypes().get(i);
            int lx = lifeForm.positionX;
            int ly = lifeForm.positionY;

            if(lifeForm.getTargetObject() == null) continue;
            if(lifeForm.getTargetObject() != this) continue;

            if(lx == this.positionX && ly == this.positionY){
                this.apply((lifeForm));
                world.getMap().getInteractiveLayer().removeWater(this, positionX, positionY, width);
                this.remove();
                lifeForm.setTargetObject(null);
            }
        }
    }

    public void apply(LifeForm lifeForm){

        lifeForm.WATER_DRUNK += 10;
        world.getMap().getInteractiveLayer().removeWater(this, positionX, positionY, width);
        this.remove();
        lifeForm.setTargetObject(null);
    }



}
