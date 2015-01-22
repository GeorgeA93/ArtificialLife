package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.layers.*;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Apple;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.simulation.world.map.objects.resources.Tree;
import com.allen.george.artificiallife.simulation.world.map.objects.resources.Water;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector2;

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

    private ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();
    private int currentFoodOnMap = 0;
    private int currentWaterOnMap = 0;


    public Map(BackgroundLayer backgroundLayer, ForegroundLayer foregroundLayer, InteractiveLayer interactiveLayer, ShadowLayer shadowLayer, int width, int height){
        this.backgroundLayer =  backgroundLayer;
        this.foregroundLayer = foregroundLayer;
        this.interactiveLayer = interactiveLayer;
        this.shadowLayer = shadowLayer;
        this.width = width;
        this.height = height;

    }

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

                //Not to sure if the collision generation for the edges of the map is need yet
                //If so the one below appears to be wrong or atleast stuff can spawn on the collision
                //tiles, therefore either one needs changing!
                if(x == 0){
                  //  collisionMap[x][y] = 1;
                }
                if(y == 0){
                  //  collisionMap[x][y] = 1;
                }
                if(x == width - 1){
                  //  collisionMap[x][y] = 1;
                }
                if(y == height - 1){
                  //  collisionMap[x][y] = 1;
                }
                if(interactiveLayer.getTileAt(x, y) != 0 && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_TOP_LEFT.getTileID() && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_TOP_RIGHT.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_BOTTOM_LEFT.getTileID() && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_BOTTOM_RIGHT.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_LEFT.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_RIGHT.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_BOTTOM.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_TOP.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_MIDDLE.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.den_tile.getTileID()
                        && interactiveLayer.getTileAt(x, y) != Tile.apple_tile.getTileID()){
                   collisionMap[x][y] = 1;
                }
            }
        }
    }

    public void addObject(MapObject o){
        if(interactiveLayer != null){
            generateCollisionMap();
        }
        mapObjects.add(o);
    }

    private double TIME_SPEED;
    private int timer;

    private void getFoodAndWaterOnMapCount(){
        this.currentFoodOnMap = 0;
        this.currentWaterOnMap = 0;
        for (int i = 0; i < mapObjects.size(); i++) {
            MapObject o = mapObjects.get(i);
            if(o instanceof Water){
                this.currentWaterOnMap += 1;
            }else if(o instanceof Food){
                this.currentFoodOnMap += 1;
            }
        }
    }

    public void update(double timeSpeed){

        //update the time speed
        this.TIME_SPEED = (int)((float)timeSpeed * 1000) + 1;
        timer += TIME_SPEED;

       // if(timer % 100 == 0){
            timer = 0;

            for (int i = 0; i < mapObjects.size(); i++) {
                MapObject o = mapObjects.get(i);
                if (o == null)
                    break;
                if (!o.removed)
                    o.update();
                if (o.removed) {
                    mapObjects.remove(i--);
                }
            }

            this.getFoodAndWaterOnMapCount();

            if(this.currentFoodOnMap < SimulationSettings.MINIMUM_FOOD_ON_MAP){
                //find the number of food to generate
                int lowerBound = SimulationSettings.MINIMUM_FOOD_ON_MAP - this.currentFoodOnMap;
                int upperBound = SimulationSettings.MAXIMUM_FOOD_ON_MAP - this.currentFoodOnMap;
                int numFoodToGenerate = lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));

                for(int i = 0; i < numFoodToGenerate; i ++){
                    int foodX = 1 + (int)(Math.random() * (((width - 1) - 1) + 1));
                    int foodY = 1 + (int)(Math.random() * (((height - 1) - 1) + 1));
                    if(interactiveLayer.getTileAt(foodX, foodY) == 0){
                        addObject(new Apple(32, 32, new Vector2(foodX, foodY), world));
                        interactiveLayer.addTile(foodX, foodY, Tile.apple_tile.getTileID());
                    }
                }
            }

            if(this.currentWaterOnMap < SimulationSettings.MINIMUM_WATER_ON_MAP){
                //find the number of food to generate
                int lowerBound = SimulationSettings.MINIMUM_WATER_ON_MAP - this.currentWaterOnMap;
                int upperBound = SimulationSettings.MAXIMUM_WATER_ON_MAP - this.currentWaterOnMap;
                int numWaterToGenerate = lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));

                for(int i = 0; i < numWaterToGenerate; i ++){
                    int waterX = 5 + (int)(Math.random() * (((width - 5) - 5) + 1));;
                    int waterY = 5 + (int)(Math.random() * (((height - 5) - 5) + 1));;
                    interactiveLayer.generateWater(waterX, waterY, 0);
                }
            }

            /*
            timer = 0;
            int x = 0;
            int y = 0;
            for (int i = 0; i < mapObjects.size(); i++) {
                MapObject o = mapObjects.get(i);
                if(o instanceof Tree){
                    if(random.nextInt(10) > 8){
                        int dirX = random.nextInt(2);
                        int dirY = random.nextInt(2);
                        if(dirX == 1){
                            x = ((Tree) o).positionX + 1;
                        } else {
                            x = ((Tree) o).positionX - 1;
                        }
                        if(dirY == 1){
                            y = ((Tree) o).positionY + 1;
                        } else {
                            y = ((Tree) o).positionY - 1;
                        }

                        if(interactiveLayer.getTileAt(x, y) == 0){
                            addObject(new Apple(32, 32, new Vector2(x, y), world));
                        }
                    }
                }
            }

        */

        //}

    }


    public int getCollisionAt(float x, float y) {
        //if(x == world.getWidth() - 1) return 1;
        // if(x <0) return 1;
        //if(y == world.getHeight() - 1) return 1;
        // if(y < 0) return 1;
        if (x >= 0 && x <= world.getWidth() - 1 && y >= 0 && y <= world.getHeight() - 1) {
            return collisionMap[(int) x][(int) y];
        } else {
            return 1;
        }
    }

    public void setCollisionAt(float x, float y, int value){
        if(value != 0 || value != 1) return;

        collisionMap[(int)x][((int)y)] = value;
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
        for(int i = 0; i < mapObjects.size(); i ++){
            mapObjects.get(i).render(spriteBatch, camera);
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

    public ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }

    public int[][]getCollisionMap(){
        return collisionMap;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
