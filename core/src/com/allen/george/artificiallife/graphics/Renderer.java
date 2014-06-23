package com.allen.george.artificiallife.graphics;

import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 22/06/2014.
 */
public class Renderer {

    private ArtificialLife aLife;
    private static final int TILE_SIZE = 32;
    public static final int MAP_WIDTH = 100;
    public static final int MAP_HEIGHT = 100;


    public Renderer(ArtificialLife aLife){
        this.aLife = aLife;
        //Load Content
        Content.load();
    }

    public void renderMap(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < MAP_HEIGHT  ; y++){
            for (int x = 0; x < MAP_WIDTH  ; x++){
                 spriteBatch.draw(Content.grassTile,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
            }
        }
    }





}
