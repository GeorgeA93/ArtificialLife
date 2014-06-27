package com.allen.george.artificiallife.simulation.world.weather;

import com.allen.george.artificiallife.graphics.particles.ParticleManager;
import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/**
 * Created by George on 26/06/2014.
 */
public class WeatherManager {

    private boolean isRaining;
    private World world;

    private int[] cyclesToRain;

    private ParticleManager particleManager;

    public WeatherManager(World world){
        this.world = world;
        this.isRaining = false;
        this.particleManager = new ParticleManager(700, world, isRaining);
        cyclesToRain = new int[world.getDayNightCycler().getMaxCycles()];
        for(int i =0; i < world.getDayNightCycler().getMaxCycles(); i ++){
            cyclesToRain[i] = 1 + (int)(Math.random() * ( 10 -  1) + 1);
        }
    }

    public void update(){
        if(world.getDayNightCycler().getCycles() >= world.getDayNightCycler().getMaxCycles())return;
        if(cyclesToRain[world.getDayNightCycler().getCycles()] > 6){
            isRaining = true;
        } else {
            isRaining = false;
        }

        particleManager.update(isRaining);
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){

        particleManager.render(spriteBatch, scrollX, scrollY, camera, isRaining);

    }

    public boolean isRaining() {
        return isRaining;
    }
}