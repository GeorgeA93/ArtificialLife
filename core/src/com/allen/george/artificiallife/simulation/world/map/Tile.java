package com.allen.george.artificiallife.simulation.world.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by George on 23/06/2014.
 */
public class Tile {


    //NULL
    public static final int NULL_TILE = 0;

    //GRASS
    public static final int GRASS_TILE = 1;
    public static final int GRASS_TILE_DETAIL1 = 2;
    public static final int GRASS_TILE_DETAIL2 = 3;
    public static final int GRASS_TILE_DETAIL3 = 4;


    //WATER
    public static final int WATER_TILE_TOP_LEFT = 5;
    public static final int WATER_TILE_TOP_RIGHT = 6;
    public static final int WATER_TILE_BOTTOM_LEFT = 7;
    public static final int WATER_TILE_BOTTOM_RIGHT= 8;
    public static final int WATER_TILE_LEFT= 9;
    public static final int WATER_TILE_RIGHT= 10;
    public static final int WATER_TILE_BOTTOM= 11;
    public static final int WATER_TILE_TOP= 12;
    public static final int WATER_TILE_MIDDLE= 13;

    //ROCKS
    public static final int BIG_ROCK_1 = 14;
    public static final int BIG_ROCK_2 = 15;
    public static final int BIG_ROCK_3 = 16;
    public static final int BIG_ROCK_4 = 17;
    public static final int LONG_ROCK_BASE = 18;
    public static final int LONG_ROCK_TOP = 19;

    //TREES
    public static final int tree_top_left = 20;
    public static final int tree_top_middle = 21;
    public static final int tree_top_right = 22;
    public static final int tree_top_rmiddle_left = 23;
    public static final int tree_top_rmiddle = 24;
    public static final int tree_top_rmiddle_right = 25;
    public static final int tree_bottom_rmiddle_left = 26;
    public static final int tree_bottom_rmiddle = 27;
    public static final int tree_bottom_rmiddle_right = 28;
    public static final int tree_bottom_left = 29;
    public static final int tree_bottom_middle = 30;
    public static final int tree_bottom_right = 31;

    //DEAD TREE
    //TREES
    public static final int tree_dead_top_left = 32;
    public static final int tree_dead_top_middle = 33;
    public static final int tree_dead_top_right = 34;
    public static final int tree_dead_top_rmiddle_left = 35;
    public static final int tree_dead_top_rmiddle = 36;
    public static final int tree_dead_top_rmiddle_right = 37;
    public static final int tree_dead_bottom_rmiddle_left = 38;
    public static final int tree_dead_bottom_rmiddle = 39;
    public static final int tree_dead_bottom_rmiddle_right = 40;
    public static final int tree_dead_bottom_left = 41;
    public static final int tree_dead_bottom_middle = 42;
    public static final int tree_dead_bottom_right = 43;
}