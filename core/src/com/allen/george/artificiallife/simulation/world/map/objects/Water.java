package com.allen.george.artificiallife.simulation.world.map.objects;

import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 27/06/2014.
 */
public class Water extends Object {

    private int cycleToChange;

    public Water(int width, int height, Vector2 position, World world){
        super(width,height, position, world);
        this.cycleToChange = 1 + (int)(Math.random() * ((world.getDayNightCycler().getMaxCycles() - 1) + 1));
    }

    public void update(){
        if(world.getDayNightCycler().getCycles() == cycleToChange){
            if(!world.getWeatherManager().isRaining()){
                world.getMap().getInteractiveLayer().removeWater(this, (int)this.position.x, (int)this.position.y, this.width);
            }
        }
    }
}
