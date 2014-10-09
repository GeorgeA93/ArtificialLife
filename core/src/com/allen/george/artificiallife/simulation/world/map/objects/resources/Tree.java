package com.allen.george.artificiallife.simulation.world.map.objects.resources;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 25/06/2014.
 */
public class Tree extends MapObject {

    private boolean dead;
    private int cycleToChange;

    public Tree(int width, int height, Vector2 position, World world, boolean dead){
        super(width, height,position, world);
        this.dead = dead;
        this.cycleToChange = 1 + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - 1) + 1));
    }

    public void update(){
        if(world.getDayNightCycler().getCycles() == cycleToChange){
            if(dead && world.getWeatherManager().isRaining()){
                dead = false;
                this.cycleToChange = (cycleToChange + 2) + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - (cycleToChange + 2)) + 1));
                world.getMap().getInteractiveLayer().generateTree(positionX, positionY);
            } else if(!dead && !world.getWeatherManager().isRaining()){
                dead = true;
                this.cycleToChange = (cycleToChange + 2) + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - (cycleToChange + 2)) + 1));
                world.getMap().getInteractiveLayer().generateDeadTree(positionX, positionY);
            }
        }
    }
}
