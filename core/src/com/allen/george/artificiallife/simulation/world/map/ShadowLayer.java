package com.allen.george.artificiallife.simulation.world.map;

import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 25/06/2014.
 */
public class ShadowLayer extends MapLayer{

    public ShadowLayer(int width, int height, Map map){
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

    @Override
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
                if(tiles[x + y * width] == Tile.tree_bottom_left){
                    spriteBatch.draw(Content.tree_bottom_left,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.tree_bottom_right){
                    spriteBatch.draw(Content.tree_bottom_right,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.tree_dead_bottom_left){
                    spriteBatch.draw(Content.tree_dead_bottom_left,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
                if(tiles[x + y * width] == Tile.tree_dead_bottom_right){
                    spriteBatch.draw(Content.tree_dead_bottom_right,  x * map.TILE_SIZE - scrollX,  y * map.TILE_SIZE - scrollY);
                }
            }
        }
    }

}
