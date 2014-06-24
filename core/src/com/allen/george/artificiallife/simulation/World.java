package com.allen.george.artificiallife.simulation;

import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.Random;

/**
 * Created by George on 23/06/2014.
 */
public class World {

    private Random random = new Random();

    //World properties
    public int height, width;

    //Map propertiees
    public static final int TILE_SIZE = 32;
    private int[][] tiles;
    private final int numLayers = 4;
    private final int backgroundLayer = 0;
    private final int shadowLayer = 1;
    private final int interactiveLayer = 2;
    private final int foregroundLayer = 3;

    private int[] collisionMap;


    public World(int width, int height){
        this.width = width;
        this.height = height;
        generateNewWorld();
    }

    public void generateNewWorld(){
        //init the map
        tiles = new int[width * height][numLayers];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width][backgroundLayer] = Tiles.NULL_TILE;
                tiles[x + y * width][shadowLayer] = Tiles.NULL_TILE;
                tiles[x + y * width][interactiveLayer] = Tiles.NULL_TILE;
                tiles[x + y * width][foregroundLayer] = Tiles.NULL_TILE;
            }
        }

        //generate the map
        generateBackgroundLayer();
        generateObstacles(); //collision, middle layer
       // generateNests(); //interactive, middle layer
        //generateTrees(); //foreground layer
       // generateFood(); //interactive, middle layer

        generateCollisionMap();
    }

    private void generateCollisionMap(){
        collisionMap = new int[width * height];
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                if(tiles[x + y * width][interactiveLayer] != 0){
                    collisionMap[x + y * width] = 1;
                }
            }
        }
    }

    //generate the background
    private void generateBackgroundLayer(){
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                if (tiles[x + y * width][backgroundLayer] == Tiles.NULL_TILE) {
                    //generate grassy bits
                    if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width][backgroundLayer] = Tiles.GRASS_TILE_DETAIL1;
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width][backgroundLayer] = Tiles.GRASS_TILE_DETAIL2;
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width][backgroundLayer] = Tiles.GRASS_TILE_DETAIL3;
                    } else {
                        tiles[x + y * width][backgroundLayer] = Tiles.GRASS_TILE;
                    }
                }
            }
        }
    }


    private void generateWater(int xx, int yy){
        int w = 2 + (int)(Math.random() * ((4 - 2) + 1));
        if(w == 2){
            tiles[xx + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 3){
            tiles[xx + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 2) + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 4){
            tiles[xx + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 3) + yy * width][interactiveLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 3) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 3) * width][interactiveLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 2) + (yy + 3) * width][interactiveLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 3) + (yy + 1) * width][interactiveLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 2) * width][interactiveLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 3) * width][interactiveLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
    }

    private void  generateObstacles(){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++) {
                if (tiles[x + y * width][interactiveLayer] == Tiles.NULL_TILE) {
                    //water
                    if((random.nextInt(600) > 598) && ((x + 4) < width) && ((y + 4) < height)){
                        generateWater(x, y);
                    }
                    //big rock
                    if(( random.nextInt(600) > 598) && ((x + 2) < width) && ((y + 2) < height) &&
                            tiles[x + y * width][interactiveLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 1) + y * width][interactiveLayer] == Tiles.NULL_TILE &&
                            tiles[x + (y + 1) * width][interactiveLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 1) + (y + 1) * width][interactiveLayer] == Tiles.NULL_TILE){
                       generateBigRock(x, y);
                    }
                    if(( random.nextInt(600) > 598) && ((y + 2) < height) &&
                            tiles[x + y * width][interactiveLayer] == Tiles.NULL_TILE &&
                            tiles[x + (y + 1) * width][foregroundLayer] == Tiles.NULL_TILE ){
                        generateLongRock(x, y);
                    }
                    if(( random.nextInt(10) > 8) && ((x + 2) < width) && ((y + 3) < height) &&
                            tiles[(x + 1) + y * width][interactiveLayer] == Tiles.NULL_TILE &&
                            tiles[x + (y + 1)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 1) + (y + 1)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 2) + (y + 1)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x) + (y + 2)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 1) + (y + 2)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 2) + (y + 2)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x) + (y + 3)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 1) + (y + 3)  * width][foregroundLayer] == Tiles.NULL_TILE &&
                            tiles[(x + 2) + (y + 3)  * width][foregroundLayer] == Tiles.NULL_TILE){
                        generateTree(x, y);
                    }
                }
            }
        }
    }

    private void generateBigRock(int xx, int yy){
        tiles[xx + yy * width][interactiveLayer] = Tiles.BIG_ROCK_2;
        tiles[(xx + 1) + yy * width][interactiveLayer] = Tiles.BIG_ROCK_4;
        tiles[xx + (yy + 1) * width][interactiveLayer] = Tiles.BIG_ROCK_1;
        tiles[(xx + 1) + (yy + 1) * width][interactiveLayer] = Tiles.BIG_ROCK_3;
    }

    private void generateLongRock(int xx, int yy){
        tiles[xx + yy * width][interactiveLayer] = Tiles.LONG_ROCK_BASE;
        tiles[xx + (yy + 1) * width][foregroundLayer] = Tiles.LONG_ROCK_TOP;
    }

    private void generateTree(int xx, int yy){
        tiles[xx + yy * width][shadowLayer] = Tiles.tree_bottom_left;
        tiles[(xx + 1) + yy * width][interactiveLayer] = Tiles.tree_bottom_middle;
        tiles[(xx + 2) + yy  * width][shadowLayer] = Tiles.tree_bottom_right;
        tiles[xx + (yy + 1)  * width][foregroundLayer] = Tiles.tree_bottom_rmiddle_left;
        tiles[(xx + 1) + (yy + 1)  * width][foregroundLayer] = Tiles.tree_bottom_rmiddle;
        tiles[(xx + 2) + (yy + 1)  * width][foregroundLayer] = Tiles.tree_bottom_rmiddle_right;
        tiles[(xx) + (yy + 2)  * width][foregroundLayer] = Tiles.tree_top_rmiddle_left;
        tiles[(xx + 1) + (yy + 2)  * width][foregroundLayer] = Tiles.tree_top_rmiddle;
        tiles[(xx + 2) + (yy + 2)  * width][foregroundLayer] = Tiles.tree_top_rmiddle_right;
        tiles[(xx) + (yy + 3)  * width][foregroundLayer] = Tiles.tree_top_left;
        tiles[(xx + 1) + (yy + 3)  * width][foregroundLayer] = Tiles.tree_top_middle;
        tiles[(xx + 2) + (yy + 3)  * width][foregroundLayer] = Tiles.tree_top_right;
    }

    private void  generateNests(){}
    private void  generateTrees(){}


    private void  generateFood(){}

    public void update(){

    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY){
        renderBackgroundLayer(spriteBatch, scrollX, scrollY);
        renderShadowLayer(spriteBatch, scrollX, scrollY);
        renderInteractiveLayer(spriteBatch, scrollX, scrollY);
        renderForegroundLayer(spriteBatch, scrollX, scrollY);
    }

    public void renderBackgroundLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x + y * width][backgroundLayer] == Tiles.GRASS_TILE){
                    spriteBatch.draw(Content.grassTile,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][backgroundLayer] == Tiles.GRASS_TILE_DETAIL1){
                    spriteBatch.draw(Content.grassTileDeatail1,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][backgroundLayer] == Tiles.GRASS_TILE_DETAIL2){
                    spriteBatch.draw(Content.grassTileDeatail2,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][backgroundLayer] == Tiles.GRASS_TILE_DETAIL3){
                    spriteBatch.draw(Content.grassTileDeatail3,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
            }
        }
    }

    public void renderShadowLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x + y * width][shadowLayer] == Tiles.tree_bottom_left){
                    spriteBatch.draw(Content.tree_bottom_left,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][shadowLayer] == Tiles.tree_bottom_right){
                    spriteBatch.draw(Content.tree_bottom_right,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
            }
        }
    }


    public void renderInteractiveLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_BOTTOM_LEFT){
                    spriteBatch.draw(Content.waterBottomLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_TOP_RIGHT){
                    spriteBatch.draw(Content.waterTopRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_TOP_LEFT){
                    spriteBatch.draw(Content.waterTopLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_BOTTOM_RIGHT){
                    spriteBatch.draw(Content.waterBottomRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_LEFT){
                    spriteBatch.draw(Content.waterLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_RIGHT){
                    spriteBatch.draw(Content.waterRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_BOTTOM){
                    spriteBatch.draw(Content.waterBottom,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_TOP){
                    spriteBatch.draw(Content.waterTop,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.WATER_TILE_MIDDLE){
                    spriteBatch.draw(Content.waterMiddle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.BIG_ROCK_1){
                    spriteBatch.draw(Content.bigRock1,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.BIG_ROCK_2){
                    spriteBatch.draw(Content.bigRock2,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.BIG_ROCK_3){
                    spriteBatch.draw(Content.bigRock3,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.BIG_ROCK_4){
                    spriteBatch.draw(Content.bigRock4,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.LONG_ROCK_BASE){
                    spriteBatch.draw(Content.longRockBase,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][interactiveLayer] == Tiles.tree_bottom_middle){
                    spriteBatch.draw(Content.tree_bottom_middle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }

            }
        }
    }


    public void renderForegroundLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x + y * width][foregroundLayer] == Tiles.LONG_ROCK_TOP){
                    spriteBatch.draw(Content.longRockTop,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_bottom_rmiddle_left){
                    spriteBatch.draw(Content.tree_bottom_rmiddle_left,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_bottom_rmiddle){
                    spriteBatch.draw(Content.tree_bottom_rmiddle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_bottom_rmiddle_right){
                    spriteBatch.draw(Content.tree_bottom_rmiddle_right,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_rmiddle){
                    spriteBatch.draw(Content.tree_top_rmiddle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_rmiddle_right){
                    spriteBatch.draw(Content.tree_top_rmiddle_right,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_rmiddle_left){
                    spriteBatch.draw(Content.tree_top_rmiddle_left,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_left){
                    spriteBatch.draw(Content.tree_top_left,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_right){
                    spriteBatch.draw(Content.tree_top_right,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][foregroundLayer] == Tiles.tree_top_middle){
                    spriteBatch.draw(Content.tree_top_middle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
            }
        }
    }






}
