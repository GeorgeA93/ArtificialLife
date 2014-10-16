package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.ga.Evolver;
import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.simulation.world.daynight.DayNightCycler;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.weather.WeatherManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;

/**
 * Created by George on 23/06/2014.
 */
public class World {

    //World properties
    private int height, width;
    private Map map;
    private ArtificialLife artificialLife;

    private DayNightCycler dayNightCycler;
    private WeatherManager weatherManager;
    private Evolver evolver;



    public World(int width, int height, ArtificialLife artificialLife){
        this.width = width;
        this.height = height;
        this.artificialLife = artificialLife;
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
        spriteBatch.renderCalls = 0;
        spriteBatch.begin();
        map.renderLayer(spriteBatch, camera, map.getBackgroundLayer());
        evolver.render(spriteBatch, camera);
        map.renderObjects(spriteBatch, camera);
        map.renderLayer(spriteBatch, camera, map.getShadowLayer());
        map.renderLayer(spriteBatch,  camera, map.getInteractiveLayer());
        map.renderLayer(spriteBatch,  camera, map.getForegroundLayer());
        weatherManager.render(spriteBatch,  camera);
        spriteBatch.end();

        dayNightCycler.render(spriteBatch,camera);

        int calls = spriteBatch.renderCalls;

        System.out.println(calls);
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

    public ArtificialLife getArtificialLife() {
        return artificialLife;
    }
}
