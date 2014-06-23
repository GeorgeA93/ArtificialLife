package com.allen.george.artificiallife.simulation;

import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    private final int terrainLayer = 1;
    private final int middleLayer = 2;
    private final int foregroundLayer = 3;


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
                tiles[x + y * width][terrainLayer] = Tiles.NULL_TILE;
                tiles[x + y * width][middleLayer] = Tiles.NULL_TILE;
                tiles[x + y * width][foregroundLayer] = Tiles.NULL_TILE;
            }
        }

        //generate the map
        generateBackgroundLayer();
        generateTerrain(); //terrain layer
        generateObstacles(); //collision, middle layer
        generateNests(); //interactive, middle layer
        generateTrees(); //foreground layer
        generateFood(); //interactive, middle layer
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

    //generate the terrain: water, paths etc
    private void  generateTerrain(){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                if(tiles[x + y * width][terrainLayer] == Tiles.NULL_TILE){
                    //generate water
                    if((random.nextInt(600) > 598) && ((x + 4) < width) && ((y + 4) < height)){
                       generateWater(x, y);
                    }
                }
            }
        }
    }

    private void generateWater(int xx, int yy){
        int w = 2 + (int)(Math.random() * ((4 - 2) + 1));
        if(w == 2){
            tiles[xx + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 3){
            tiles[xx + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 2) + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 4){
            tiles[xx + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM;
            tiles[(xx + 3) + yy * width][terrainLayer] = Tiles.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_LEFT;
            tiles[xx + (yy + 3) * width][terrainLayer] = Tiles.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 3) * width][terrainLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 2) + (yy + 3) * width][terrainLayer] = Tiles.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_MIDDLE;
            tiles[(xx + 3) + (yy + 1) * width][terrainLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 2) * width][terrainLayer] = Tiles.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 3) * width][terrainLayer] = Tiles.WATER_TILE_TOP_RIGHT;
        }
    }

    private void  generateObstacles(){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++) {
                if (tiles[x + y * width][middleLayer] == Tiles.NULL_TILE && tiles[x + y * width][terrainLayer] == Tiles.NULL_TILE) {
                    //big rock
                    if((random.nextInt(600) > 598) && ((x + 2) < width) && ((y + 2) < height)){
                       generateBigRock(x, y);
                    }
                }
            }
        }
    }

    private void generateBigRock(int xx, int yy){
        tiles[xx + yy * width][middleLayer] = Tiles.BIG_ROCK_1;
        //tiles[(xx + 1) + yy * width][middleLayer] = Tiles.BIG_ROCK_1;
       // tiles[xx + (yy + 1) * width][middleLayer] = Tiles.BIG_ROCK_1;
       // tiles[(xx + 1) + (yy + 1) * width][middleLayer] = Tiles.BIG_ROCK_1;
    }

    private void  generateNests(){}
    private void  generateTrees(){}


    private void  generateFood(){}
    public void update(){

    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY){
        renderBackgroundLayer(spriteBatch, scrollX, scrollY);
        renderTerrainLayer(spriteBatch, scrollX, scrollY);
        renderMiddleLayer(spriteBatch, scrollX, scrollY);
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

    public void renderTerrainLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_BOTTOM_LEFT){
                    spriteBatch.draw(Content.waterBottomLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_TOP_RIGHT){
                    spriteBatch.draw(Content.waterTopRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_TOP_LEFT){
                    spriteBatch.draw(Content.waterTopLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_BOTTOM_RIGHT){
                    spriteBatch.draw(Content.waterBottomRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_LEFT){
                    spriteBatch.draw(Content.waterLeft,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_RIGHT){
                    spriteBatch.draw(Content.waterRight,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_BOTTOM){
                    spriteBatch.draw(Content.waterBottom,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_TOP){
                    spriteBatch.draw(Content.waterTop,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width][terrainLayer] == Tiles.WATER_TILE_MIDDLE){
                    spriteBatch.draw(Content.waterMiddle,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
            }
        }
    }

    public void renderMiddleLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
                if(tiles[x * y][middleLayer] == Tiles.BIG_ROCK_1){
                   spriteBatch.draw(Content.bigRock1,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x * y][middleLayer] == Tiles.BIG_ROCK_2){
                   spriteBatch.draw(Content.bigRock2,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x * y][middleLayer] == Tiles.BIG_ROCK_3){
                   spriteBatch.draw(Content.bigRock3,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
                if(tiles[x * y][middleLayer] == Tiles.BIG_ROCK_4){
                   spriteBatch.draw(Content.bigRock4,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
                }
            }
        }
    }

    public void renderForegroundLayer(SpriteBatch spriteBatch, int scrollX, int scrollY){
        for (int y = 0; y < height  ; y++){
            for (int x = 0; x < width  ; x++){
//                if(tiles[x * y][foregroundLayer] == Tiles.GRASS_TILE){
                    // spriteBatch.draw(Content.grassTile,  x * TILE_SIZE - scrollX,  y * TILE_SIZE - scrollY);
 //               }
            }
        }
    }






}
