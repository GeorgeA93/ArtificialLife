package com.allen.george.artificiallife.simulation.world.map.layers;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by George on 25/06/2014.
 */
public abstract class MapLayer {

    public Random random = new Random();

    public int width, height;
    public int[] tiles;
    public Map map;

    public void addTile(int x, int y, int texId){
    }

    public  int getTileAt(int x, int y){
        return 0;
    }

    public void generateTree(int xx, int yy){

    }
    public void generateDeadTree(int xx, int yy){

    }
    public void generateWater(int xx, int yy, int ww){

    }

    public void removeWater(com.allen.george.artificiallife.simulation.world.map.objects.Object o, int xx, int yy, int w){

    }

    public abstract void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera);

}
