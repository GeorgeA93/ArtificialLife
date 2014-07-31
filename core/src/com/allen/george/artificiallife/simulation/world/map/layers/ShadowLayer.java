package com.allen.george.artificiallife.simulation.world.map.layers;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 25/06/2014.
 */
public class ShadowLayer extends MapLayer{

    public ShadowLayer(int width, int height, Map map){
        this.width = width;
        this.height = height;
        this.name = "Shadow";
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE.getTileID();
            }
        }
    }

    public void addTile(int x, int y, int texId){
        tiles[x + y * width] = texId;
    }

    @Override
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
       /* int camX = (int)(scrollX * (1 / camera.zoom)) / 32;
        int camY = (int)(scrollY * (1 / camera.zoom)) / 32;
        int viewPointX = (int)((scrollX + camera.viewportWidth) / (1/ camera.zoom)) / 32;
        int viewPointY = (int)((scrollY + camera.viewportHeight) / (1/ camera.zoom)) / 32;

        int minX = Math.max(0, camX - 1);
        int minY = Math.max(0, camY - 1);
        int maxX = Math.min(viewPointX + 1, width);
        int maxY = Math.min(viewPointY + 1, height);

        for (int y = minY; y < maxY; y++){
            for (int x = minX; x < maxX ; x++){
                Tile.renderManager.renderTile(spriteBatch, tiles[x + y * width],  x * Map.TILE_SIZE - scrollX, y * Map.TILE_SIZE - scrollY);
            }
        }

        */

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile.renderManager.renderTile(spriteBatch, tiles[x + y * width],  x * Map.TILE_SIZE - (int)camera.position.x, y * Map.TILE_SIZE - (int)camera.position.y);
            }
        }
    }

}
