package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 25/06/2014.
 */
public class Tree extends Object {

    private boolean dead;
    private int cycleToChange;

    public Tree(int width, int height, Vector2 position, World world, boolean dead){
        super(width, height,position, world);
        this.dead = dead;
        this.cycleToChange = 1 + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - 1) + 1));

        System.out.println(cycleToChange);
    }

    public void update(){
        if(world.getDayNightCycler().getCycles() == cycleToChange){
            if(dead){
                dead = false;
                this.cycleToChange = (cycleToChange + 2) + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - (cycleToChange + 2)) + 1));
                world.getMap().getInteractiveLayer().generateTree((int)position.x, (int)position.y);
            } else {
                dead = true;
                this.cycleToChange = (cycleToChange + 2) + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - (cycleToChange + 2)) + 1));
                world.getMap().getInteractiveLayer().generateDeadTree((int)position.x, (int)position.y);
            }
        }
    }
}
