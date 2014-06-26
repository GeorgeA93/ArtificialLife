package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.graphics.particles.BaseParticle;
import com.allen.george.artificiallife.graphics.particles.Rain;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.daynight.DayNightCycler;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.weather.WeatherManager;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by George on 23/06/2014.
 */
public class World {

    //World properties
    private int height, width;
    private Map map;

    private DayNightCycler dayNightCycler;
    private WeatherManager weatherManager;

    private LifeForm lifeForm;


    public World(int width, int height){
        this.width = width;
        this.height = height;

        generateNewWorld();
    }

    public void generateNewWorld(){
        dayNightCycler = new DayNightCycler(this);
        weatherManager = new WeatherManager(this);
        map = new Map(this);
        //generate population
        lifeForm = new LifeForm("0110011", this);
    }

    public void update(){
        dayNightCycler.update();
        weatherManager.update();
        map.update();
        lifeForm.update();


    }


    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera){
        spriteBatch.begin();
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getBackgroundLayer());

        lifeForm.render(spriteBatch, scrollX, scrollY);
        map.renderObjects(spriteBatch, scrollX, scrollY, camera);
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getShadowLayer());
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getInteractiveLayer());
        map.renderLayer(spriteBatch, scrollX, scrollY, camera, map.getForegroundLayer());
        // map.renderCollisionLayer(spriteBatch, scrollX, scrollY);

        weatherManager.render(spriteBatch, scrollX, scrollY, camera);

        spriteBatch.end();

        dayNightCycler.render(spriteBatch, scrollX, scrollY, camera);
    }



    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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

    public WeatherManager getWeatherManager() {
        return weatherManager;
    }
}
