package com.allen.george.artificiallife.simulation.world.map.layers;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.objects.*;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Apple;
import com.allen.george.artificiallife.utils.SimulationSettings;
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
        this.name = "Interactive";
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE.getTileID();
            }
        }
        generate();
    }


    private void generate(){
        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++) {
                if (tiles[x + y * width] == Tile.NULL_TILE.getTileID()) {
                    //water
                    if(SimulationSettings.GEN_WATER){
                        if((random.nextInt(600) > 598) && ((x + 4) < width) && ((y + 4) < height)){
                            generateWater(x, y, 0);
                        }
                    }
                    if(SimulationSettings.GEN_LARGE_ROCKS){
                        //big rock
                        if(( random.nextInt(600) > 598) && ((x + 2) < width) && ((y + 2) < height) &&
                                tiles[x + y * width] == Tile.NULL_TILE.getTileID() &&
                                tiles[(x + 1) + y * width] == Tile.NULL_TILE.getTileID() &&
                                tiles[x + (y + 1) * width] == Tile.NULL_TILE.getTileID() &&
                                tiles[(x + 1) + (y + 1) * width] == Tile.NULL_TILE.getTileID()){
                            generateBigRock(x, y);
                        }
                    }
                   if(SimulationSettings.GEN_SMALL_ROCKS){
                       if(( random.nextInt(600) > 598) && ((y + 2) < height) &&
                               tiles[x + y * width] == Tile.NULL_TILE.getTileID() &&
                               map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE.getTileID()){
                           generateLongRock(x, y);
                       }
                   }
                  if(SimulationSettings.GEN_TREES){
                      if(( random.nextInt(30) > 28) && ((x + 2) < width) && ((y + 3) < height) &&
                              tiles[(x + 1) + y * width] == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 3) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 3) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 3) == Tile.NULL_TILE.getTileID() ){
                          generateTree(x, y);
                          map.addObject(new Tree(3, 4, new Vector2(x, y), map.getWorld(), false));
                      }
                  }
                  if(SimulationSettings.GEN_DEAD_TREES){
                      if(( random.nextInt(300) > 298) && ((x + 2) < width) && ((y + 3) < height) &&
                              tiles[(x + 1) + y * width] == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 1) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 2) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x, y + 3) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 1, y + 3) == Tile.NULL_TILE.getTileID() &&
                              map.getForegroundLayer().getTileAt(x + 2, y + 3) == Tile.NULL_TILE.getTileID()){
                          generateDeadTree(x, y);
                          map.addObject(new Tree(3, 4, new Vector2(x, y), map.getWorld(), true));
                      }
                  }
                    if(( random.nextInt(300) > 298) && tiles[x  + y * width] == Tile.NULL_TILE.getTileID()){
                        tiles[x + y * width] = Tile.NULL_TILE.getTileID();
                        map.addObject(new Apple(32, 32, new Vector2(x , y ), map.getWorld()));
                    }
                }
            }
        }
    }

    public void generateWater(int xx, int yy, int ww){
        int w = ww;
        if(w == 0){
           w = 2 + (int)(Math.random() * ((4 - 2) + 1));
        }

        if(w == 2  &&
                tiles[xx + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + yy * width]  == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 1) * width]  == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 1) * width]  == Tile.NULL_TILE.getTileID()){

            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_TOP_LEFT.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_TOP_RIGHT.getTileID();
            map.addObject(new Water(w, w, new Vector2(xx, yy), map.getWorld()));
        }
        else if(w == 3 &&
                tiles[xx + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + (yy + 2) * width] == Tile.NULL_TILE.getTileID()){

            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM.getTileID();
            tiles[(xx + 2) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_LEFT.getTileID();
            tiles[xx + (yy + 2) * width] = Tile.WATER_TILE_TOP_LEFT.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE.getTileID();
            tiles[(xx + 1) + (yy + 2) * width] = Tile.WATER_TILE_TOP.getTileID();
            tiles[(xx + 2) + (yy + 1) * width] = Tile.WATER_TILE_RIGHT.getTileID();
            tiles[(xx + 2) + (yy + 2) * width] = Tile.WATER_TILE_TOP_RIGHT.getTileID();
            map.addObject(new Water(w, w, new Vector2(xx, yy), map.getWorld()));
        }
        else if(w == 4 &&
                tiles[xx + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 3) + yy * width] == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[xx + (yy + 3) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 1) + (yy + 3) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + (yy + 3) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 2) + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 3) + (yy + 1) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 3) + (yy + 2) * width] == Tile.NULL_TILE.getTileID() &&
                tiles[(xx + 3) + (yy + 3) * width] == Tile.NULL_TILE.getTileID()){


            tiles[xx + yy * width] = Tile.WATER_TILE_BOTTOM_LEFT.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.WATER_TILE_BOTTOM.getTileID();
            tiles[(xx + 2) + yy * width] = Tile.WATER_TILE_BOTTOM.getTileID();
            tiles[(xx + 3) + yy * width] = Tile.WATER_TILE_BOTTOM_RIGHT.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.WATER_TILE_LEFT.getTileID();
            tiles[xx + (yy + 2) * width] = Tile.WATER_TILE_LEFT.getTileID();
            tiles[xx + (yy + 3) * width] = Tile.WATER_TILE_TOP_LEFT.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE.getTileID();
            tiles[(xx + 1) + (yy + 2) * width] = Tile.WATER_TILE_MIDDLE.getTileID();
            tiles[(xx + 1) + (yy + 3) * width] = Tile.WATER_TILE_TOP.getTileID();
            tiles[(xx + 2) + (yy + 1) * width] = Tile.WATER_TILE_MIDDLE.getTileID();
            tiles[(xx + 2) + (yy + 3) * width] = Tile.WATER_TILE_TOP.getTileID();
            tiles[(xx + 2) + (yy + 2) * width] = Tile.WATER_TILE_MIDDLE.getTileID();
            tiles[(xx + 3) + (yy + 1) * width] = Tile.WATER_TILE_RIGHT.getTileID();
            tiles[(xx + 3) + (yy + 2) * width] = Tile.WATER_TILE_RIGHT.getTileID();
            tiles[(xx + 3) + (yy + 3) * width] = Tile.WATER_TILE_TOP_RIGHT.getTileID();
            map.addObject(new Water(w, w, new Vector2(xx, yy), map.getWorld()));
        }
    }

    private void generateBigRock(int xx, int yy){
        tiles[xx + yy * width] = Tile.BIG_ROCK_2.getTileID();
        tiles[(xx + 1) + yy * width] = Tile.BIG_ROCK_4.getTileID();
        tiles[xx + (yy + 1) * width] = Tile.BIG_ROCK_1.getTileID();
        tiles[(xx + 1) + (yy + 1) * width] = Tile.BIG_ROCK_3.getTileID();
    }

    private void generateLongRock(int xx, int yy){
        tiles[xx + yy * width] = Tile.LONG_ROCK_BASE.getTileID();
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.LONG_ROCK_TOP.getTileID());
    }

    public void generateTree(int xx, int yy){
        map.getShadowLayer().addTile(xx, yy, Tile.tree_bottom_left.getTileID());
        tiles[(xx + 1) + yy * width] = Tile.tree_bottom_middle.getTileID();
        map.getShadowLayer().addTile(xx + 2, yy, Tile.tree_bottom_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.tree_bottom_rmiddle_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 1, Tile.tree_bottom_rmiddle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 1, Tile.tree_bottom_rmiddle_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 2, Tile.tree_top_rmiddle_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 2, Tile.tree_top_rmiddle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 2, Tile.tree_top_rmiddle_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 3, Tile.tree_top_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 3, Tile.tree_top_middle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 3, Tile.tree_top_right.getTileID());
    }

    public void generateDeadTree(int xx, int yy){
        map.getShadowLayer().addTile(xx, yy, Tile.tree_dead_bottom_left.getTileID());
        tiles[(xx + 1) + yy * width] = Tile.tree_dead_bottom_middle.getTileID();
        map.getShadowLayer().addTile(xx + 2, yy, Tile.tree_dead_bottom_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 1, Tile.tree_dead_bottom_rmiddle_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 1, Tile.tree_dead_bottom_rmiddle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 1, Tile.tree_dead_bottom_rmiddle_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 2, Tile.tree_dead_top_rmiddle_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 2, Tile.tree_dead_top_rmiddle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 2, Tile.tree_dead_top_rmiddle_right.getTileID());
        map.getForegroundLayer().addTile(xx, yy + 3, Tile.tree_dead_top_left.getTileID());
        map.getForegroundLayer().addTile(xx + 1, yy + 3, Tile.tree_dead_top_middle.getTileID());
        map.getForegroundLayer().addTile(xx + 2, yy + 3, Tile.tree_dead_top_right.getTileID());
    }

    public void removeWater(com.allen.george.artificiallife.simulation.world.map.objects.Object o, int xx, int yy, int  w){
        map.removeObject(o);
        if(w == 4){
            tiles[xx + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 3) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 3) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 3) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + (yy + 3) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 3) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 3) + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 3) + (yy + 3) * width] = Tile.NULL_TILE.getTileID();
            generateWater(xx, yy, 3);
        } else if(w == 3){
            tiles[xx + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 2) + (yy + 2) * width] = Tile.NULL_TILE.getTileID();
            generateWater(xx, yy, 2);
        } else if(w == 2){
            tiles[xx + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + yy * width] = Tile.NULL_TILE.getTileID();
            tiles[xx + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
            tiles[(xx + 1) + (yy + 1) * width] = Tile.NULL_TILE.getTileID();
        }
    }

    public void addTile(int x, int y, int texId){
        tiles[x + y * width] = texId;
    }

    public int getTileAt(int x, int y){
        return  tiles[x + y * width];
    }


    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
     /*   int camX = (int)(scrollX * (1 / camera.zoom)) / 32;
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
