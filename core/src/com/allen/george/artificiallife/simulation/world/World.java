package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.daynight.DayNightCycler;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 23/06/2014.
 */
public class World {

    //World properties
    private int height, width;
    private Map map;

    private DayNightCycler dayNightCycler;

    private LifeForm lifeForm;


    public World(int width, int height){
        this.width = width;
        this.height = height;

        generateNewWorld();
    }

    public void generateNewWorld(){
        dayNightCycler = new DayNightCycler(width, height);
        map = new Map(width, height, this);
        //generate population
        lifeForm = new LifeForm("0110011", this);
    }

    public void update(){
        dayNightCycler.update();
        map.update();
        lifeForm.update();
    }


    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getBackgroundLayer());

        lifeForm.render(spriteBatch, scrollX, scrollY);

        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getShadowLayer());
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getInteractiveLayer());
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getForegroundLayer());
        // map.renderCollisionLayer(spriteBatch, scrollX, scrollY);

        dayNightCycler.render(spriteBatch, scrollX, scrollY, camera);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public DayNightCycler getDayNightCycler() {
        return dayNightCycler;
    }

    public void setDayNightCycler(DayNightCycler dayNightCycler) {
        this.dayNightCycler = dayNightCycler;
    }
}
