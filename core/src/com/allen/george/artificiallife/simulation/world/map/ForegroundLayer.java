package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 25/06/2014.
 */
public class ForegroundLayer extends MapLayer {

    public ForegroundLayer(int width, int height, Map map){
        this.width = width;
        this.height = height;
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE;
            }
        }
    }

    public void addTile(int x, int y, int texId){
        tiles[x + y * width] = texId;
    }

    public int getTileAt(int x, int y){
        return  tiles[x + y * width];
    }

    @Override
    public void render(SpriteBatch spriteBatch, int scrollX, int scrollY, OrthographicCamera camera) {
        int camX = (int) (scrollX * (1 / camera.zoom)) / 32;
        int camY = (int) (scrollY * (1 / camera.zoom)) / 32;
        int viewPointX = (int) ((scrollX + camera.viewportWidth) / (1 / camera.zoom)) / 32;
        int viewPointY = (int) ((scrollY + camera.viewportHeight) / (1 / camera.zoom)) / 32;

        int minX = Math.max(0, camX - 1);
        int minY = Math.max(0, camY - 1);
        int maxX = Math.min(viewPointX + 1, width);
        int maxY = Math.min(viewPointY + 1, height);

        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                if (tiles[x + y * width] == Tile.LONG_ROCK_TOP) {
                    spriteBatch.draw(Content.longRockTop, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_bottom_rmiddle_left) {
                    spriteBatch.draw(Content.tree_bottom_rmiddle_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_bottom_rmiddle) {
                    spriteBatch.draw(Content.tree_bottom_rmiddle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_bottom_rmiddle_right) {
                    spriteBatch.draw(Content.tree_bottom_rmiddle_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_rmiddle) {
                    spriteBatch.draw(Content.tree_top_rmiddle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_rmiddle_right) {
                    spriteBatch.draw(Content.tree_top_rmiddle_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_rmiddle_left) {
                    spriteBatch.draw(Content.tree_top_rmiddle_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_left) {
                    spriteBatch.draw(Content.tree_top_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_right) {
                    spriteBatch.draw(Content.tree_top_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_top_middle) {
                    spriteBatch.draw(Content.tree_top_middle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }

                if (tiles[x + y * width] == Tile.tree_dead_bottom_rmiddle_left) {
                    spriteBatch.draw(Content.tree_dead_bottom_rmiddle_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_bottom_rmiddle) {
                    spriteBatch.draw(Content.tree_dead_bottom_rmiddle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_bottom_rmiddle_right) {
                    spriteBatch.draw(Content.tree_dead_bottom_rmiddle_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_rmiddle) {
                    spriteBatch.draw(Content.tree_dead_top_rmiddle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_rmiddle_right) {
                    spriteBatch.draw(Content.tree_dead_top_rmiddle_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_rmiddle_left) {
                    spriteBatch.draw(Content.tree_dead_top_rmiddle_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_left) {
                    spriteBatch.draw(Content.tree_dead_top_left, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_right) {
                    spriteBatch.draw(Content.tree_dead_top_right, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }
                if (tiles[x + y * width] == Tile.tree_dead_top_middle) {
                    spriteBatch.draw(Content.tree_dead_top_middle, x * map.TILE_SIZE - scrollX, y * map.TILE_SIZE - scrollY);
                }

            }

        }
    }
}
