package com.allen.george.artificiallife.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by George on 22/06/2014.
 */
public class Content {

    public static Texture grassTile;

    public static void load(){
       grassTile = new Texture(Gdx.files.internal("tiles/grass_tile.bmp"));
    }
}
