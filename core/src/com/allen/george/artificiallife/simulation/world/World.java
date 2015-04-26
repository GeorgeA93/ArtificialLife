package com.allen.george.artificiallife.simulation.world;

import com.allen.george.artificiallife.ga.GeneticEngine;
import com.allen.george.artificiallife.main.ArtificialLife;
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
    private ArtificialLife artificialLife;

    private DayNightCycler dayNightCycler;
    private WeatherManager weatherManager;
    private GeneticEngine geneticEngine;

    public World(int width, int height, ArtificialLife artificialLife, int flag){
        this.artificialLife = artificialLife;
        this.width = width;
        this.height = height;
        dayNightCycler = new DayNightCycler(this);
        weatherManager = new WeatherManager(this);
    }

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
        geneticEngine = new GeneticEngine(this);
    }

    public void resetWorld(){
        dayNightCycler = null;
        weatherManager = null;
        geneticEngine = null;
    }


    public void update(double timeSpeed){
        geneticEngine.update(timeSpeed);
        dayNightCycler.update();
        weatherManager.update(timeSpeed);
        map.update(timeSpeed);
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        spriteBatch.begin();
        map.renderLayer(spriteBatch, camera, map.getBackgroundLayer());
        geneticEngine.render(spriteBatch, camera);
        map.renderObjects(spriteBatch, camera);
        map.renderLayer(spriteBatch, camera, map.getShadowLayer());
        map.renderLayer(spriteBatch,  camera, map.getInteractiveLayer());
        map.renderLayer(spriteBatch,  camera, map.getForegroundLayer());
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

    public GeneticEngine getGeneticEngine(){
        return this.geneticEngine;
    }

    public ArtificialLife getArtificialLife() {
        return artificialLife;
    }

    public void setArtificialLife(ArtificialLife artificialLife) {
        this.artificialLife = artificialLife;
    }

    public void setDayNightCycler(DayNightCycler dayNightCycler) {
        this.dayNightCycler = dayNightCycler;
    }

    public void setWeatherManager(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    public void setGeneticEngine(GeneticEngine geneticEngine) {
        this.geneticEngine = geneticEngine;
    }
}
