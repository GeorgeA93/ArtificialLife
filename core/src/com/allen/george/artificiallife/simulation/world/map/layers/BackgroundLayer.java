package com.allen.george.artificiallife.simulation.world.map.layers;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by George on 25/06/2014.
 */
public class BackgroundLayer extends MapLayer {

    public BackgroundLayer(int width, int height, Map map){
        this.width = width;
        this.height = height;
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE.getTileID();
            }
        }
        generateBackground();
    }

    //generate the background
    private void generateBackground(){
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                if (tiles[x + y * width] == Tile.NULL_TILE.getTileID()) {
                    //generate grassy bits
                    if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.GRASS_TILE_DETAIL1.getTileID();
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.GRASS_TILE_DETAIL2.getTileID();
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.GRASS_TILE_DETAIL3.getTileID();
                    } else {
                        tiles[x + y * width] = Tile.GRASS_TILE.getTileID();
                    }
                }
            }
        }
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
                Tile.renderManager.renderTile(spriteBatch, tiles[x + y * width],  x * Map.TILE_SIZE - scrollX, y * Map.TILE_SIZE - scrollY);
            }
        }
    }
}
