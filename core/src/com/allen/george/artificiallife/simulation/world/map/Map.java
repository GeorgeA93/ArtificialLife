package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.layers.*;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.allen.george.artificiallife.simulation.world.map.objects.Object;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.allen.george.artificiallife.simulation.world.map.objects.Object;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 25/06/2014.
 */
public class Map {

    private Random random = new Random();

    private World world;

    //Map propertiees
    public static final int TILE_SIZE = 32;
    private MapLayer backgroundLayer;
    private MapLayer shadowLayer;
    private MapLayer interactiveLayer;
    private MapLayer foregroundLayer;
    private int[][] collisionMap;
    private int width, height;

    private ArrayList<Object> mapObjects = new ArrayList<Object>();

    public Map(int width, int height, World world){
        this.width = width;
        this.height = height;
        this.world = world;
        generateMap();
    }


    public void generateMap(){
        //generate the map
        backgroundLayer = new BackgroundLayer(width, height, this);
        shadowLayer = new ShadowLayer(width, height, this);
        foregroundLayer = new ForegroundLayer(width, height, this);
        interactiveLayer = new InteractiveLayer(width, height, this);
        generateCollisionMap();

    }

    private void generateCollisionMap(){
        collisionMap = new int[width][height];
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                if(x == 0){
                    collisionMap[x][y] = 1;
                }
                if(y == 0){
                    collisionMap[x][y] = 1;
                }
                if(x == width - 1){
                    collisionMap[x][y] = 1;
                }
                if(y == height - 1){
                    collisionMap[x][y] = 1;
                }
                if(interactiveLayer.getTileAt(x, y) != 0){
                    collisionMap[x][y] = 1;
                }
            }
        }
    }

    public void addObject(Object o){
        mapObjects.add(o);
    }

    public void update(){
        for(Object o : mapObjects){
            o.update();
        }
    }


    public int getCollisionAt(float x, float y){
        return collisionMap[(int)x / 32][(int)y / 32];
    }

    public void renderAll(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera) {
        renderLayer(spriteBatch, scrollX, scrollY, camera, backgroundLayer);
        renderLayer(spriteBatch, scrollX, scrollY, camera, shadowLayer);
        renderLayer(spriteBatch, scrollX, scrollY, camera, interactiveLayer);
        renderLayer(spriteBatch, scrollX, scrollY, camera, foregroundLayer);
    }

    public void renderLayer(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera, MapLayer layer){
        layer.render(spriteBatch, scrollX, scrollY, camera);
    }

    public void renderCollisionLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                 if(collisionMap[x][y] == 1){
                     spriteBatch.draw(Content.collision,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                 }
            }
        }
    }

    public MapLayer getBackgroundLayer() {
        return backgroundLayer;
    }

    public void setBackgroundLayer(MapLayer backgroundLayer) {
        this.backgroundLayer = backgroundLayer;
    }

    public MapLayer getShadowLayer() {
        return shadowLayer;
    }

    public void setShadowLayer(MapLayer shadowLayer) {
        this.shadowLayer = shadowLayer;
    }

    public MapLayer getInteractiveLayer() {
        return interactiveLayer;
    }

    public void setInteractiveLayer(MapLayer interactiveLayer) {
        this.interactiveLayer = interactiveLayer;
    }

    public MapLayer getForegroundLayer() {
        return foregroundLayer;
    }

    public void setForegroundLayer(MapLayer foregroundLayer) {
        this.foregroundLayer = foregroundLayer;
    }

    public World getWorld() {
        return world;
    }
}
