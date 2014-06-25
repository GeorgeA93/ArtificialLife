package com.allen.george.artificiallife.simulation.world.map.layers;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.objects.Tree;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by George on 25/06/2014.
 */
public class InteractiveLayer extends MapLayer{

    public InteractiveLayer(int width, int height, Map map){
        this.width = width;
        this.height = height;
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE;
            }
        }
        generate();
    }

    private void generate(){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++) {
                if (tiles[x + y * width] == Tile.NULL_TILE) {
                    //water
                    if((random.nextInt(600) > 598) && ((x + 4) < width) && ((y + 4) < height)){
                        generateWater(x, y);
                    }
                    //big rock
                    if(( random.nextInt(600) > 598) && ((x + 2) < width) && ((y + 2) < height) &&
                            tiles[x + y * width] == Tile.NULL_TILE &&
                            tiles[(x + 1) + y * width] == Tile.NULL_TILE &&
                            tiles[x + (y + 1) * width] == Tile.NULL_TILE &&
                            tiles[(x + 1) + (y + 1) * width] == Tile.NULL_TILE){
                        generateBigRock(x, y);
                    }
                    if(( random.nextInt(600) > 598) && ((y + 2) < height) &&
                            tiles[x + y * width] == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE){
                        generateLongRock(x, y);
                    }
                    if(( random.nextInt(30) > 28) && ((x + 2) < width) && ((y + 3) < height) &&
                            tiles[(x + 1) + y * width] == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 3) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 3) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 3) == Tile.NULL_TILE ){
                        generateTree(x, y);
                        map.addObject(new Tree(3, 4, new Vector2(x, y), map.getWorld(), false));
                    }
                    if(( random.nextInt(300) > 298) && ((x + 2) < width) && ((y + 3) < height) &&
                            tiles[(x + 1) + y * width] == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 1) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 2) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x, y + 3) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 1, y + 3) == Tile.NULL_TILE &&
                            map.getForegroundLayer().getTileAt(x + 2, y + 3) == Tile.NULL_TILE){
                        generateDeadTree(x, y);
                        map.addObject(new Tree(3, 4, new Vector2(x, y), map.getWorld(), true));
                    }
                }
            }
        }
    }

    private void generateWater(int xx, int yy){
        int w = 2 + (int)(Math.random() * ((4 - 2) + 1));
        if(w == 2){
            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 3){
            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width] = Tile.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width] = Tile.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width] = Tile.WATER_TILE_RIGHT;
            tiles[(xx + 2) + (yy + 2) * width] = Tile.WATER_TILE_TOP_RIGHT;
        }
        else if(w == 4){
            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT;
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM;
            tiles[(xx + 2) + yy * width] = Tile.WATER_TILE_BOTTOM;
            tiles[(xx + 3) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT;
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_LEFT;
            tiles[xx + (yy + 2) * width] = Tile.WATER_TILE_LEFT;
            tiles[xx + (yy + 3) * width] = Tile.WATER_TILE_TOP_LEFT;
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 2) * width] = Tile.WATER_TILE_MIDDLE;
            tiles[(xx + 1) + (yy + 3) * width] = Tile.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE;
            tiles[(xx + 2) + (yy + 3) * width] = Tile.WATER_TILE_TOP;
            tiles[(xx + 2) + (yy + 2) * width] = Tile.WATER_TILE_MIDDLE;
            tiles[(xx + 3) + (yy + 1) * width] = Tile.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 2) * width] = Tile.WATER_TILE_RIGHT;
            tiles[(xx + 3) + (yy + 3) * width] = Tile.WATER_TILE_TOP_RIGHT;
        }
    }

    private void generateBigRock(int xx, int yy){
        tiles[xx + yy * width] = Tile.BIG_ROCK_2;
        tiles[(xx + 1) + yy * width] = Tile.BIG_ROCK_4;
        tiles[xx + (yy + 1) * width] = Tile.BIG_ROCK_1;
        tiles[(xx + 1) + (yy + 1) * width] = Tile.BIG_ROCK_3;
    }

    private void generateLongRock(int xx, int yy){
        tiles[xx + yy * width] = Tile.LONG_ROCK_BASE;
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.LONG_ROCK_TOP);
    }

    public void generateTree(int xx, int yy){
        map.getShadowLayer().addTile(xx, yy, Tile.tree_bottom_left);
        tiles[(xx + 1) + yy * width] = Tile.tree_bottom_middle;
        map.getShadowLayer().addTile(xx + 2, yy, Tile.tree_bottom_right);
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.tree_bottom_rmiddle_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 1, Tile.tree_bottom_rmiddle);
        map.getForegroundLayer().addTile(xx + 2, yy + 1, Tile.tree_bottom_rmiddle_right);
        map.getForegroundLayer().addTile(xx, yy + 2, Tile.tree_top_rmiddle_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 2, Tile.tree_top_rmiddle);
        map.getForegroundLayer().addTile(xx + 2, yy + 2, Tile.tree_top_rmiddle_right);
        map.getForegroundLayer().addTile(xx, yy + 3, Tile.tree_top_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 3, Tile.tree_top_middle);
        map.getForegroundLayer().addTile(xx + 2, yy + 3, Tile.tree_top_right);
    }

    public void generateDeadTree(int xx, int yy){
        map.getShadowLayer().addTile(xx, yy, Tile.tree_dead_bottom_left);
        tiles[(xx + 1) + yy * width] = Tile.tree_dead_bottom_middle;
        map.getShadowLayer().addTile(xx + 2, yy, Tile.tree_dead_bottom_right);
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.tree_dead_bottom_rmiddle_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 1, Tile.tree_dead_bottom_rmiddle);
        map.getForegroundLayer().addTile(xx + 2, yy + 1, Tile.tree_dead_bottom_rmiddle_right);
        map.getForegroundLayer().addTile(xx, yy + 2, Tile.tree_dead_top_rmiddle_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 2, Tile.tree_dead_top_rmiddle);
        map.getForegroundLayer().addTile(xx + 2, yy + 2, Tile.tree_dead_top_rmiddle_right);
        map.getForegroundLayer().addTile(xx, yy + 3, Tile.tree_dead_top_left);
        map.getForegroundLayer().addTile(xx + 1, yy + 3, Tile.tree_dead_top_middle);
        map.getForegroundLayer().addTile(xx + 2, yy + 3, Tile.tree_dead_top_right);
    }

    public void addTile(int x, int y, int texId){
        tiles[x + y * width] = texId;
    }

    public int getTileAt(int x, int y){
        return  tiles[x + y * width];
    }

    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera) {
        int camX = (int)(scrollX * (1 / camera.zoom)) / 32;
        int camY = (int)(scrollY * (1 / camera.zoom)) / 32;
        int viewPointX = (int)((scrollX + camera.viewportWidth) / (1/ camera.zoom)) / 32;
        int viewPointY = (int)((scrollY + camera.viewportHeight) / (1/ camera.zoom)) / 32;

        int minX = Math.max(0, camX - 1);
        int minY = Math.max(0, camY - 1);
        int maxX = Math.min(viewPointX + 1, width);
        int maxY = Math.min(viewPointY + 1, height);

        for (int y = minY; y < maxY; y++){
            for (int x = minX; x < maxX ; x++){
                if(tiles[x + y * width] == Tile.WATER_TILE_BOTTOM_LEFT){
                    spriteBatch.draw(Content.waterBottomLeft,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_TOP_RIGHT){
                    spriteBatch.draw(Content.waterTopRight,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_TOP_LEFT){
                    spriteBatch.draw(Content.waterTopLeft,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_BOTTOM_RIGHT){
                    spriteBatch.draw(Content.waterBottomRight,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_LEFT){
                    spriteBatch.draw(Content.waterLeft,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_RIGHT){
                    spriteBatch.draw(Content.waterRight,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_BOTTOM){
                    spriteBatch.draw(Content.waterBottom,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_TOP){
                    spriteBatch.draw(Content.waterTop,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.WATER_TILE_MIDDLE){
                    spriteBatch.draw(Content.waterMiddle,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.BIG_ROCK_1){
                    spriteBatch.draw(Content.bigRock1,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.BIG_ROCK_2){
                    spriteBatch.draw(Content.bigRock2,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.BIG_ROCK_3){
                    spriteBatch.draw(Content.bigRock3,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.BIG_ROCK_4){
                    spriteBatch.draw(Content.bigRock4,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.LONG_ROCK_BASE){
                    spriteBatch.draw(Content.longRockBase,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.tree_bottom_middle){
                    spriteBatch.draw(Content.tree_bottom_middle,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_bottom_middle){
                    spriteBatch.draw(Content.tree_dead_bottom_middle,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }

            }
        }
    }

}
