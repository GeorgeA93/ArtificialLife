package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 07/10/2014.
 */
public class Evolver {

    private World world;

    private ArrayList<LifeForm> lifeForms = new ArrayList<LifeForm>();
    private int populationSize = 10;
    private Random random = new Random();

    public Evolver(World world){
        this.world = world;
        generatePopulation();
    }

    public void generatePopulation(){
        //generate population
        for(int i = 0; i < populationSize; i ++){
            LifeForm lf = new LifeForm(world);
            lifeForms.add(lf);
        }
    }

    public void update(double timeSpeed){
        for(LifeForm lf : lifeForms){
            lf.update(timeSpeed);
        }
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        for(LifeForm lf : lifeForms){
            lf.render(spriteBatch,camera);
        }
    }

    public ArrayList<LifeForm> getLifeForms() {
        return lifeForms;
    }

}
