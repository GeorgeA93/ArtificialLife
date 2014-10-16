package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.layers.*;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Apple;
import com.allen.george.artificiallife.utils.Content;
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
                        && interactiveLayer.getTileAt(x, y) != Tile.WATER_TILE_MIDDLE.getTileID()){
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

    public void removeObject(MapObject o){
        mapObjects.remove(o);
    }

    public void update(){

        if(random.nextInt(10) < 2){
           int x = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
           int y  = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));
            if(interactiveLayer.getTileAt(x, y) == 0){
                addObject(new Apple(32, 32, new Vector2(x, y), world));
            }
        }

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

}
