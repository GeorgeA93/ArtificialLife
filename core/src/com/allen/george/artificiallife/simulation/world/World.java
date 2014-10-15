package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.ga.Evolver;
import com.allen.george.artificiallife.simulation.world.daynight.DayNightCycler;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.weather.WeatherManager;
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
    private WeatherManager weatherManager;
    private Evolver evolver;



    public World(int width, int height){
        this.width = width;
        this.height = height;
        generateNewWorld();
    }

    public void generateNewWorld(){
        dayNightCycler = new DayNightCycler(this);
        weatherManager = new WeatherManager(this);
        map = new Map(this);
        evolver = new Evolver(this);
    }

    public void resetWorld(){
        dayNightCycler = null;
        weatherManager = null;
        evolver = null;
    }


    public void update(double timeSpeed){
        evolver.update(timeSpeed);
        dayNightCycler.update();
        weatherManager.update(timeSpeed);
        map.update();
    }


    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){

        map.renderLayer(spriteBatch, camera, map.getBackgroundLayer());
        spriteBatch.begin();
        evolver.render(spriteBatch, camera);
        map.renderObjects(spriteBatch, camera);
        map.renderLayer(spriteBatch, camera, map.getShadowLayer());
        map.renderLayer(spriteBatch,  camera, map.getInteractiveLayer());
        map.renderLayer(spriteBatch,  camera, map.getForegroundLayer());
       // map.renderCollisionLayer(spriteBatch, camera);

        weatherManager.render(spriteBatch,  camera);

        spriteBatch.end();

        dayNightCycler.render(spriteBatch,camera);
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

    public Evolver getEvolver() {
        return evolver;
    }
}
