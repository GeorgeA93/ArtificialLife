package com.allen.george.artificiallife.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import javax.xml.soap.Text;

/**
 * Created by George on 22/06/2014.
 */
public class Content {

    //TILES
    private static Texture tileSheet;
    private static Texture tree1sheet;
    private static Texture tree1DeadSheet;
    public static TextureRegion nullTile;
    public static TextureRegion grassTile;
    public static TextureRegion grassTileDeatail1;
    public static TextureRegion grassTileDeatail2;
    public static TextureRegion grassTileDeatail3;
    public static TextureRegion waterTopLeft;
    public static TextureRegion waterTopRight;
    public static TextureRegion waterBottomLeft;
    public static TextureRegion waterBottomRight;
    public static TextureRegion waterTop;
    public static TextureRegion waterLeft;
    public static TextureRegion waterBottom;
    public static TextureRegion waterRight;
    public static TextureRegion waterMiddle;
    public static TextureRegion bigRock1;
    public static TextureRegion bigRock2;
    public static TextureRegion bigRock3;
    public static TextureRegion bigRock4;
    public static TextureRegion longRockBase;
    public static TextureRegion longRockTop;
    public static TextureRegion tree_top_left;
    public static TextureRegion tree_top_middle;
    public static TextureRegion tree_top_right;
    public static TextureRegion tree_top_rmiddle_left;
    public static TextureRegion tree_top_rmiddle;
    public static TextureRegion tree_top_rmiddle_right;
    public static TextureRegion tree_bottom_rmiddle_left;
    public static TextureRegion tree_bottom_rmiddle;
    public static TextureRegion tree_bottom_rmiddle_right;
    public static TextureRegion tree_bottom_left;
    public static TextureRegion tree_bottom_middle;
    public static TextureRegion tree_bottom_right;


    public static TextureRegion tree_dead_top_left;
    public static TextureRegion tree_dead_top_middle;
    public static TextureRegion tree_dead_top_right;
    public static TextureRegion tree_dead_top_rmiddle_left;
    public static TextureRegion tree_dead_top_rmiddle;
    public static TextureRegion tree_dead_top_rmiddle_right;
    public static TextureRegion tree_dead_bottom_rmiddle_left;
    public static TextureRegion tree_dead_bottom_rmiddle;
    public static TextureRegion tree_dead_bottom_rmiddle_right;
    public static TextureRegion tree_dead_bottom_left;
    public static TextureRegion tree_dead_bottom_middle;
    public static TextureRegion tree_dead_bottom_right;
    public static TextureRegion sand_tile;
    public static TextureRegion sand_tile_detail_1;
    public static TextureRegion sand_tile_detail_2;
    public static TextureRegion dirt_tile;
    public static TextureRegion dirt_tile_detail_1;
    public static TextureRegion dirt_tile_detail_2;
    public static Texture particle;


    //LIFE FORMS
    public static Texture lifeForm;
    public static Texture catSheet;
    public static TextureRegion[][] cat;


    //objects
    public static Texture apple;
    public static Texture denTile;

    //TEST
    public static Texture collision;
    public static Texture path;
    public static Texture alphaMask;

    //TERRAIN EDGES
    public static TextureRegion grass_left;
    public static TextureRegion grass_right;
    public static TextureRegion grass_top;
    public static TextureRegion grass_bottom;
    public static TextureRegion grass_top_left;
    public static TextureRegion grass_top_right;
    public static TextureRegion grass_bottom_left;
    public static TextureRegion grass_bottom_right;
    public static Texture blend;

    //ATLAS TEST
    public static TextureAtlas atlas;


    public static void load(){

       // atlas = new TextureAtlas(Gdx.files.internal("tiles.atlas"));

        loadTileSheet();
        lifeForm = new Texture(Gdx.files.internal("tiles/lifeform.png"));
        collision = new Texture(Gdx.files.internal("tiles/collision.png"));
        path = new Texture(Gdx.files.internal("tiles/path.png"));
        nullTile = new TextureRegion();
        particle = new Texture(Gdx.files.internal("tiles/Dot.png"));
        apple = new Texture(Gdx.files.internal("apple.png"));
        denTile = new Texture(Gdx.files.internal("tiles/den.png"));

        catSheet = new Texture(Gdx.files.internal("cat.png"));
        cat = split(catSheet, 32, 32);
        alphaMask = new Texture(Gdx.files.internal("mask.png"));



    }

    private static void loadTileSheet(){
        tileSheet = new Texture(Gdx.files.internal("tiles/terrain.png"));
        TextureRegion[][] tileSheetMain = split(tileSheet, 32, 32);

        blend = new Texture(Gdx.files.internal("tiles/blendmask.png"));
       // blend.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion[][] blendMask = split(blend, 32, 32);


        waterTopLeft = tileSheetMain[27][2];
        waterTopRight = tileSheetMain[29][2];
        waterBottomLeft = tileSheetMain[27][4];
        waterBottomRight = tileSheetMain[29][4];
        waterTop = tileSheetMain[28][2];
        waterLeft = tileSheetMain[27][3];
        waterBottom = tileSheetMain[28][4];
        waterRight = tileSheetMain[29][3];
        waterMiddle = tileSheetMain[28][3];

/*
        waterTopLeft = atlas.findRegion("water_top_left");
        waterTopRight =  atlas.findRegion("water_top_right");
        waterBottomLeft = atlas.findRegion("water_bottom_left");
        waterBottomRight = atlas.findRegion("water_bottom_right");
        waterTop = atlas.findRegion("water_top");
        waterLeft = atlas.findRegion("water_left");
        waterBottom = atlas.findRegion("water_bottom");
        waterRight = atlas.findRegion("water_right");
        waterMiddle = atlas.findRegion("water_middle");
        */


        grassTile = tileSheetMain[1][9];
        grassTileDeatail1 = tileSheetMain[0][11];
        grassTileDeatail2 = tileSheetMain[1][11];
        grassTileDeatail3 = tileSheetMain[2][11];


        /*
        grassTile = atlas.findRegion("grass");
        grassTileDeatail1 = atlas.findRegion("grass_detail_two");
        grassTileDeatail2 = atlas.findRegion("grass_detail_one");
        grassTileDeatail3 = atlas.findRegion("grass_detail_three");
        */

        sand_tile = tileSheetMain[0][5];
        sand_tile_detail_1 = tileSheetMain[1][5];
        sand_tile_detail_2 = tileSheetMain[2][5];
        dirt_tile  = tileSheetMain[6][5];;
        dirt_tile_detail_1 = tileSheetMain[7][5];;
        dirt_tile_detail_2 = tileSheetMain[8][5];;

        /*
        sand_tile = atlas.findRegion("sand");
        sand_tile_detail_1 = atlas.findRegion("sand_detail_one");
        sand_tile_detail_2 = atlas.findRegion("sand_detail_two");
        dirt_tile  = atlas.findRegion("dirt");
        dirt_tile_detail_1 =atlas.findRegion("dirt_detail_one");
        dirt_tile_detail_2 = atlas.findRegion("dirt_detail_two");
        */


        bigRock1 = tileSheetMain[24][19];
        bigRock2 = tileSheetMain[24][20];
        bigRock3 = tileSheetMain[25][19];
        bigRock4 = tileSheetMain[25][20];

        /*
        bigRock1 = atlas.findRegion("big_rock_top_left");
        bigRock2 = atlas.findRegion("big_rock_bottom_left");
        bigRock3 = atlas.findRegion("big_rock_top_right");
        bigRock4 = atlas.findRegion("big_rock_bottom_right");
        */

        longRockBase = tileSheetMain[31][21];
        longRockTop = tileSheetMain[31][20];

        //Terrain edges
        grass_left = blendMask[0][1];
        grass_right = blendMask[2][1];
        grass_top = blendMask[1][0];
        grass_bottom = blendMask[1][2];
        grass_top_left = blendMask[0][0];
        grass_top_right = blendMask[2][0];
        grass_bottom_left = blendMask[0][2];
        grass_bottom_right = blendMask[2][2];


        //Tree
        tree1sheet = new Texture(Gdx.files.internal("tiles/tree1.png"));
        TextureRegion[][] tree1sheetMain = split(tree1sheet, 32, 32);


        tree_top_left = tree1sheetMain[0][0];
        tree_top_middle = tree1sheetMain[1][0];
        tree_top_right = tree1sheetMain[2][0];
        tree_top_rmiddle_left = tree1sheetMain[0][1];
        tree_top_rmiddle = tree1sheetMain[1][1];
        tree_top_rmiddle_right = tree1sheetMain[2][1];
        tree_bottom_rmiddle_left = tree1sheetMain[0][2];
        tree_bottom_rmiddle = tree1sheetMain[1][2];
        tree_bottom_rmiddle_right = tree1sheetMain[2][2];
        tree_bottom_left = tree1sheetMain[0][3];
        tree_bottom_middle = tree1sheetMain[1][3];
        tree_bottom_right = tree1sheetMain[2][3];

        /*
        tree_top_left = atlas.findRegion("tree_top_left");
        tree_top_middle = atlas.findRegion("tree_top_middle");
        tree_top_right = atlas.findRegion("tree_top_right");
        tree_top_rmiddle_left =atlas.findRegion("tree_top_middle_left");
        tree_top_rmiddle = atlas.findRegion("tree_top_middle_middle");
        tree_top_rmiddle_right = atlas.findRegion("tree_top_middle_right");
        tree_bottom_rmiddle_left = atlas.findRegion("tree_bottom_middle_left");
        tree_bottom_rmiddle =atlas.findRegion("tree_bottom_middle_middle");
        tree_bottom_rmiddle_right = atlas.findRegion("tree_bottom_middle_right");
        tree_bottom_left = atlas.findRegion("tree_bottom_left");
        tree_bottom_middle = atlas.findRegion("tree_bottom_middle");
        tree_bottom_right = atlas.findRegion("tree_bottom_right");
        */


        //Dead Tree
        tree1DeadSheet = new Texture(Gdx.files.internal("tiles/tree1dead.png"));
        TextureRegion[][] tree1deadSheetMain = split(tree1DeadSheet, 32, 32);

        tree_dead_top_left = tree1deadSheetMain[0][0];
        tree_dead_top_middle = tree1deadSheetMain[1][0];
        tree_dead_top_right = tree1deadSheetMain[2][0];
        tree_dead_top_rmiddle_left = tree1deadSheetMain[0][1];
        tree_dead_top_rmiddle = tree1deadSheetMain[1][1];
        tree_dead_top_rmiddle_right = tree1deadSheetMain[2][1];
        tree_dead_bottom_rmiddle_left = tree1deadSheetMain[0][2];
        tree_dead_bottom_rmiddle = tree1deadSheetMain[1][2];
        tree_dead_bottom_rmiddle_right = tree1deadSheetMain[2][2];
        tree_dead_bottom_left = tree1deadSheetMain[0][3];
        tree_dead_bottom_middle = tree1deadSheetMain[1][3];
        tree_dead_bottom_right = tree1deadSheetMain[2][3];

    }

    private static TextureRegion[][] split(Texture texture, int width, int height){
        int xSlices = texture.getWidth() / width;
        int ySlices = texture.getHeight() / height;
        TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
        for (int x = 0; x < xSlices; x++) {
            for (int y = 0; y < ySlices; y++) {
                res[x][y] = new TextureRegion(texture, x * width, y * height, width, height);
                res[x][y].flip(false, false);
            }
        }
        return res;
    }
}
