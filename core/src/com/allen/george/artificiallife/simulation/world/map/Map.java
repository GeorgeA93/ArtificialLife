package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.graphics.particles.BaseParticle;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.layers.*;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.allen.george.artificiallife.simulation.world.map.objects.Object;
import com.allen.george.artificiallife.simulation.world.map.objects.comparators.FoodComparator;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Apple;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.allen.george.artificiallife.simulation.world.map.objects.Object;
import com.badlogic.gdx.math.Vector2;
import com.sun.deploy.util.OrderedHashSet;
import sun.text.resources.et.CollationData_et;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public Map(World world){
        this.width = world.getWidth();
        this.height = world.getHeight();
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
        if(interactiveLayer != null){
            generateCollisionMap();
        }
        mapObjects.add(o);
    }

    public void removeObject(Object o){
        mapObjects.remove(o);
    }

    public void update(){

        if(random.nextInt(100) < 2){
           int x = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
           int y  = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));
            if(interactiveLayer.getTileAt(x, y) == 0){
                addObject(new Apple(32, 32, new Vector2(x, y), world));
            }
        }

        for (int i = 0; i < mapObjects.size(); i++) {
            Object o = mapObjects.get(i);
            if (o == null)
                break;
            if (!o.removed)
                o.update();
            if (o.removed) {
                mapObjects.remove(i--);
            }
        }
    }


    public int getCollisionAt(float x, float y){
        return collisionMap[(int)x ][(int)y ];
    }

    public void renderAll(SpriteBatch spriteBatch, OrthographicCamera camera) {
        renderLayer(spriteBatch,  camera, backgroundLayer);
        renderLayer(spriteBatch,  camera, shadowLayer);
        renderLayer(spriteBatch,  camera, interactiveLayer);
        renderLayer(spriteBatch,  camera, foregroundLayer);
    }

    public void renderLayer(SpriteBatch spriteBatch,  OrthographicCamera camera, MapLayer layer){
        layer.render(spriteBatch,  camera);
    }

    public void renderObjects(SpriteBatch spriteBatch, OrthographicCamera camera){
        for(Object o : mapObjects){
            o.render(spriteBatch, camera);
        }
    }

    public void renderCollisionLayer(SpriteBatch spriteBatch, OrthographicCamera camera){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                 if(collisionMap[x][y] == 1){
                     spriteBatch.draw(Content.collision,  x * TILE_SIZE - (int)camera.position.x,  y * TILE_SIZE - (int)camera.position.y);
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

    public ArrayList<Object> getMapObjects() {
        return mapObjects;
    }

    public int[][]getCollisionMap(){
        return collisionMap;
    }

    public ArrayList<Food> getFoodObjects(){
        ArrayList<Food> foods = new ArrayList<Food>();
        for(int i = 0; i < mapObjects.size(); i ++){
            if(mapObjects.get(i) instanceof Food){
                foods.add((Food)mapObjects.get(i));
            }
        }
        Collections.sort(foods, new FoodComparator());
        return foods;
    }
}
