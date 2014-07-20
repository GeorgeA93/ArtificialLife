package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 16/07/2014.
 */
public class Evolver {

    private World world;

    private ArrayList<LifeForm> lifeForms = new ArrayList<LifeForm>();
    private int populationSize = 100;
    private Random random = new Random();

    public Evolver(World world){
        this.world = world;
        generatePopulation();
    }

    public void generatePopulation(){
        //generate population
        for(int i = 0; i < populationSize; i ++){
            int up = random.nextInt(2);
            int down = random.nextInt(2);
            int left = random.nextInt(2);
            int right = random.nextInt(2);
            String bit = up + "" + down + "" + left + "" + right;
            LifeForm lf = new LifeForm(bit, world);
            lifeForms.add(lf);
        }
    }

    public void update(double timeSpeed){
        for(LifeForm lf : lifeForms){
            lf.update(timeSpeed);
        }
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){
        for(LifeForm lf : lifeForms){
            lf.render(spriteBatch, scrollX, scrollY);
        }
    }

    public ArrayList<LifeForm> getLifeForms() {
        return lifeForms;
    }
}
