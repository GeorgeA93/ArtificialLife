package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Created by George on 23/06/2014.
 */
public class World {

    //World properties
    public int height, width;
    public Map map;


    private LifeForm lifeForm;


    public World(int width, int height){
        this.width = width;
        this.height = height;
        generateNewWorld();
    }

    public void generateNewWorld(){
        map = new Map(width, height);
        //generate population
        lifeForm = new LifeForm("0110011", this);
    }


    public void update(){
        lifeForm.update();
    }


    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){

        map.renderLayer(spriteBatch, scrollX, scrollY, camera, Map.backgroundLayer);

        lifeForm.render(spriteBatch, scrollX, scrollY);

        map.renderLayer(spriteBatch, scrollX, scrollY, camera, Map.shadowLayer);
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, Map.interactiveLayer);
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, Map.foregroundLayer);

        map.renderCollisionLayer(spriteBatch, scrollX, scrollY);
    }






}
