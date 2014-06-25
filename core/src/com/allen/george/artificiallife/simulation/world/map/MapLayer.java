package com.allen.george.artificiallife.simulation.world.map;

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


    public abstract void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera);

}
