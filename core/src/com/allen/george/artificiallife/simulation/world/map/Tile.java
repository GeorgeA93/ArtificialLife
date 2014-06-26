package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.lwjgl.Sys;

/**
 * Created by George on 23/06/2014.
 */
public class Tile {

    //OBJECT FOR RENDERING
    public static final Tile renderManager = new Tile();

    //NULL
    public static final Tile NULL_TILE = new Tile(0, Content.nullTile);

    //GRASS
    public static final Tile GRASS_TILE = new Tile(1, Content.grassTile);
    public static final Tile GRASS_TILE_DETAIL1 = new Tile(2, Content.grassTileDeatail1);
    public static final Tile GRASS_TILE_DETAIL2 = new Tile(3, Content.grassTileDeatail2);
    public static final Tile GRASS_TILE_DETAIL3 = new Tile(4, Content.grassTileDeatail3);


    //WATER
    public static final Tile WATER_TILE_TOP_LEFT = new Tile(5, Content.waterTopLeft);
    public static final Tile WATER_TILE_TOP_RIGHT = new Tile(6, Content.waterTopRight);
    public static final Tile WATER_TILE_BOTTOM_LEFT = new Tile(7, Content.waterBottomLeft);
    public static final Tile WATER_TILE_BOTTOM_RIGHT= new Tile(8, Content.waterBottomRight);
    public static final Tile WATER_TILE_LEFT= new Tile(9, Content.waterLeft);
    public static final Tile WATER_TILE_RIGHT= new Tile(10, Content.waterRight);
    public static final Tile WATER_TILE_BOTTOM= new Tile(11, Content.waterBottom);
    public static final Tile WATER_TILE_TOP= new Tile(12, Content.waterTop);
    public static final Tile WATER_TILE_MIDDLE= new Tile(13, Content.waterMiddle);

    //ROCKS
    public static final Tile BIG_ROCK_1 = new Tile(14, Content.bigRock1);
    public static final Tile BIG_ROCK_2 = new Tile(15, Content.bigRock2);
    public static final Tile BIG_ROCK_3 = new Tile(16, Content.bigRock3);
    public static final Tile BIG_ROCK_4 = new Tile(17, Content.bigRock4);
    public static final Tile LONG_ROCK_BASE = new Tile(18, Content.longRockBase);
    public static final Tile LONG_ROCK_TOP = new Tile(19, Content.longRockTop);

    //TREES
    public static final Tile tree_top_left = new Tile(20, Content.tree_top_left);
    public static final Tile tree_top_middle =  new Tile(21, Content.tree_top_middle);
    public static final Tile tree_top_right = new Tile(22, Content.tree_top_right);
    public static final Tile tree_top_rmiddle_left = new Tile(23, Content.tree_top_rmiddle_left);
    public static final Tile tree_top_rmiddle = new Tile(24, Content.tree_top_rmiddle);
    public static final Tile tree_top_rmiddle_right = new Tile(25, Content.tree_top_rmiddle_right);
    public static final Tile tree_bottom_rmiddle_left = new Tile(26, Content.tree_bottom_rmiddle_left);
    public static final Tile tree_bottom_rmiddle = new Tile(27, Content.tree_bottom_rmiddle);
    public static final Tile tree_bottom_rmiddle_right = new Tile(28, Content.tree_bottom_rmiddle_right);
    public static final Tile tree_bottom_left = new Tile(29, Content.tree_bottom_left);
    public static final Tile tree_bottom_middle = new Tile(30, Content.tree_bottom_middle);
    public static final Tile tree_bottom_right = new Tile(31, Content.tree_bottom_right);

    //DEAD TREE
    //TREES
    public static final Tile tree_dead_top_left = new Tile(32, Content.tree_dead_top_left);
    public static final Tile tree_dead_top_middle = new Tile(33, Content.tree_dead_top_middle);
    public static final Tile tree_dead_top_right = new Tile(34, Content.tree_dead_top_right);
    public static final Tile tree_dead_top_rmiddle_left = new Tile(35, Content.tree_dead_top_rmiddle_left);
    public static final Tile tree_dead_top_rmiddle = new Tile(36, Content.tree_dead_top_rmiddle);
    public static final Tile tree_dead_top_rmiddle_right = new Tile(37, Content.tree_dead_top_rmiddle_right);
    public static final Tile tree_dead_bottom_rmiddle_left = new Tile(38, Content.tree_dead_bottom_rmiddle_left);
    public static final Tile tree_dead_bottom_rmiddle = new Tile(39, Content.tree_dead_bottom_rmiddle);
    public static final Tile tree_dead_bottom_rmiddle_right = new Tile(40, Content.tree_dead_bottom_rmiddle_right);
    public static final Tile tree_dead_bottom_left = new Tile(41, Content.tree_dead_bottom_left);
    public static final Tile tree_dead_bottom_middle = new Tile(42, Content.tree_dead_bottom_middle);
    public static final Tile tree_dead_bottom_right = new Tile(43, Content.tree_dead_bottom_right);

    private static Tile[] tiles = {
            NULL_TILE,
            GRASS_TILE,
            GRASS_TILE_DETAIL1,
            GRASS_TILE_DETAIL2,
            GRASS_TILE_DETAIL3,
            WATER_TILE_TOP_LEFT,
            WATER_TILE_TOP_RIGHT,
            WATER_TILE_BOTTOM_LEFT,
            WATER_TILE_BOTTOM_RIGHT,
            WATER_TILE_LEFT,
            WATER_TILE_RIGHT,
            WATER_TILE_BOTTOM,
            WATER_TILE_TOP,
            WATER_TILE_MIDDLE,
            BIG_ROCK_1,
            BIG_ROCK_2,
            BIG_ROCK_3,
            BIG_ROCK_4,
            LONG_ROCK_BASE,
            LONG_ROCK_TOP,
            tree_top_left,
            tree_top_middle,
            tree_top_right,
            tree_top_rmiddle_left,
            tree_top_rmiddle,
            tree_top_rmiddle_right,
            tree_bottom_rmiddle_left,
            tree_bottom_rmiddle,
            tree_bottom_rmiddle_right,
            tree_bottom_left,
            tree_bottom_middle,
            tree_bottom_right,
            tree_dead_top_left,
            tree_dead_top_middle,
            tree_dead_top_right,
            tree_dead_top_rmiddle_left,
            tree_dead_top_rmiddle,
            tree_dead_top_rmiddle_right,
            tree_dead_bottom_rmiddle_left,
            tree_dead_bottom_rmiddle,
            tree_dead_bottom_rmiddle_right,
            tree_dead_bottom_left,
            tree_dead_bottom_middle,
            tree_dead_bottom_right,
    };


    //TILE CLASS
    private int tileID;
    private TextureRegion texture;

    //For the render manager
    public Tile(){

    }

    //For a new Tile
    public Tile(int tileID, TextureRegion texture){
        this.tileID = tileID;
        this.texture = texture;
    }

    public void renderTile(SpriteBatch spriteBatch, int id, int x, int y){
        if(id == 0) return;
        spriteBatch.draw( getTexture(id) ,x, y);
    }

    public int getTileID() {
        return tileID;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public TextureRegion getTexture(int id) {
        for(int i = 0; i < Tile.tiles.length; i ++){
            if(Tile.tiles[i].getTileID() == id){
                return Tile.tiles[i].getTexture();
            }
        }
        return null;
    }

}
