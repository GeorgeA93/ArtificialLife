package com.allen.george.artificiallife.simulation.world.map.layers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.Tile;
import com.allen.george.artificiallife.simulation.world.map.generation.ClusterOfPoints;
import com.allen.george.artificiallife.simulation.world.map.generation.ClusterPoint;
import com.allen.george.artificiallife.simulation.world.map.generation.KMeansCluster;
import com.allen.george.artificiallife.simulation.world.map.generation.TerrainEdge;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import org.lwjgl.opengl.GL11;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by George on 25/06/2014.
 */
public class BackgroundLayer extends MapLayer {

    private ClusterOfPoints[] clusters;

    public BackgroundLayer(int width, int height){
        this.width = width;
        this.height = height;
        this.name = "Background";
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE.getTileID();
            }
        }
    }

    public BackgroundLayer(int width, int height, Map map){
        this.width = width;
        this.height = height;
        this.name = "Background";
        this.map = map;
        tiles = new int[width * height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                tiles[x + y * width]= Tile.NULL_TILE.getTileID();
            }
        }

        Random random = new Random();
        int numPoints = 5000;
        ClusterPoint[] points = new ClusterPoint[numPoints];
        for ( int i = 0; i < numPoints; i++ ){
            int x = random.nextInt(100);
            int y = random.nextInt(100);
            points[i] = new ClusterPoint((float)x,(float)y);
        }

        clusters = KMeansCluster.cluster(points, 16);

        createMapFromMeans();
        calculateTerrainEdges();

        cleanUp();
    }

    private int[][] grassTiles;
    private int[][] sandTiles;
    private int[][] dirtTiles;
    private ArrayList<TerrainEdge> terrainEdges;



    private void cleanUp(){
        clusters = null;
        grassTiles = null;
        sandTiles = null;
        dirtTiles = null;
    }




    private void calculateTerrainEdges(){
        terrainEdges = new ArrayList<TerrainEdge>();

        for(int y = 0; y < height; y ++){
            for(int x = 0; x < width; x ++){
                if(x - 1 < 0 || x + 1 >= width  || y - 1 < 0  || y + 1 >= height) continue;
                if(grassTiles[x][y] == 1 && grassTiles[x + 1][y] == 0){
                    TerrainEdge e = new TerrainEdge(x + 1, y, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }
                if(grassTiles[x][y] == 1 && grassTiles[x - 1][y] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1, y, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }
                if(grassTiles[x][y] == 1 && grassTiles[x][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x, y + 1, TerrainEdge.GRASS_TERRAIN_TYPE,Tile.grass_top.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(grassTiles[x][y] == 1 && grassTiles[x][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x , y - 1, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_bottom.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }

                }
                if(grassTiles[x][y] == 1 && grassTiles[x + 1][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x + 1 , y + 1, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_top_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                else  if(grassTiles[x][y] == 1 && grassTiles[x + 1][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x + 1, y - 1, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_bottom_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(grassTiles[x][y] == 1 && grassTiles[x - 1][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1, y + 1, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_top_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(grassTiles[x][y] == 1 && grassTiles[x - 1][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1 , y - 1, TerrainEdge.GRASS_TERRAIN_TYPE, Tile.grass_bottom_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }

                if(sandTiles[x][y] == 1 && sandTiles[x + 1][y] == 0 ){
                    TerrainEdge e = new TerrainEdge(x + 1, y, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }
                if(sandTiles[x][y] == 1 && sandTiles[x - 1][y] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1, y, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }
                if(sandTiles[x][y] == 1 && sandTiles[x][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x, y + 1, TerrainEdge.SAND_TERRAIN_TYPE,Tile.grass_top.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(sandTiles[x][y] == 1 && sandTiles[x][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x , y - 1, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_bottom.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }

                }
                if(sandTiles[x][y] == 1 && sandTiles[x + 1][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x + 1 , y + 1, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_top_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                else  if(sandTiles[x][y] == 1 && sandTiles[x + 1][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x + 1, y - 1, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_bottom_right.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(sandTiles[x][y] == 1 && sandTiles[x - 1][y + 1] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1, y + 1, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_top_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);


                    }
                }
                if(sandTiles[x][y] == 1 && sandTiles[x - 1][y - 1] == 0){
                    TerrainEdge e = new TerrainEdge(x - 1 , y - 1, TerrainEdge.SAND_TERRAIN_TYPE, Tile.grass_bottom_left.getTileID());
                    if(!terrainEdges.contains(e)){
                        terrainEdges.add(e);

                    }
                }


            }
        }
    }

    private void createMapFromMeans(){
        grassTiles = new int[width][height];
        sandTiles = new int[width][height];
        dirtTiles = new int[width][height];
        for(int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {

                float[] distances = new float[clusters.length];
                int i = 0;
                for(ClusterOfPoints cluster : clusters){
                    distances[i++] = (y - cluster.getCentroid().y)*(y - cluster.getCentroid().y)+(x - cluster.getCentroid().x)*(x - cluster.getCentroid().x);
                }

                int terrainValue = clusters[getIndexOfMinimumCell(distances)].terrainValue;

                if(terrainValue == 0){ //GRASS
                    grassTiles[x][y] = 1;
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
                if(terrainValue == 1) { //SAND
                    sandTiles[x][y] = 1;
                    if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.sand_tile.getTileID();
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.sand_tile_detail2.getTileID();
                    } else {
                        tiles[x + y * width] = Tile.sand_tile_detail1.getTileID();
                    }
                }
                if(terrainValue == 2){ //DIRT
                    dirtTiles[x][y] = 1;
                    if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.dirt_tile.getTileID();
                    } else if((random.nextInt(20) > 18) && ((x + 1) < width) && ((y + 1) < height)){
                        tiles[x + y * width] = Tile.dirt_tile_detail2.getTileID();
                    } else {
                        tiles[x + y * width] = Tile.dirt_tile_detail1.getTileID();
                    }
                }


            }
        }
    }

    private int getIndexOfMinimumCell(float[] distances) {
        float minVal = Float.MAX_VALUE;
        int index = 0;

        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < minVal) {
                minVal = distances[i];
                index = i;
            }
        }

        return index;
    }


    @Override
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {


        int camX = (int)(camera.position.x * (1 / camera.zoom)) / 32;
        int camY = (int)(camera.position.y * (1 / camera.zoom)) / 32;
        int viewPointX = (int)((camera.position.x + camera.viewportWidth) / (1/ camera.zoom)) / 32;
        int viewPointY = (int)((camera.position.y + camera.viewportHeight) / (1/ camera.zoom)) / 32;

        int minX = Math.max(0, camX - 1);
        int minY = Math.max(0, camY - 1);
        int maxX = Math.min(viewPointX + 1, width);
        int maxY = Math.min(viewPointY + 1, height);


        for (int y = minY; y < maxY; y++){
            for (int x = minX; x < maxX ; x++){
                if(map.getWorld().getArtificialLife().getUseBlending()){
                    spriteBatch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA,Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
                }

                Tile.renderManager.renderTile(spriteBatch, tiles[x + y * width],  x * Map.TILE_SIZE - (int)camera.position.x, y * Map.TILE_SIZE - (int)camera.position.y);
                spriteBatch.flush();

                if(map.getWorld().getArtificialLife().getUseBlending()){
                    for(int i = 0; i < terrainEdges.size(); i ++){
                        if(terrainEdges.get(i).isAtTile(x, y)){
                            //alpha mask
                           Gdx.gl.glColorMask(false, false, false, true);
                           spriteBatch.setBlendFunction(Gdx.gl.GL_ONE,Gdx.gl.GL_ZERO);

                            Tile.renderManager.renderTile(spriteBatch, terrainEdges.get(i).id,  x * Map.TILE_SIZE - (int)camera.position.x, y * Map.TILE_SIZE - (int)camera.position.y);
                            spriteBatch.flush();

                            //foreground
                            Gdx.gl.glColorMask(true, true, true, true);
                            spriteBatch.setBlendFunction(Gdx.gl.GL_DST_ALPHA, Gdx.gl.GL_ONE_MINUS_DST_ALPHA);

                            //draw our sprite to be masked
                            if(terrainEdges.get(i).terrainType == TerrainEdge.GRASS_TERRAIN_TYPE){
                                Tile.renderManager.renderTile(spriteBatch, Tile.GRASS_TILE.getTileID(),  x * Map.TILE_SIZE - (int)camera.position.x, y * Map.TILE_SIZE - (int)camera.position.y);
                            } else if(terrainEdges.get(i).terrainType == TerrainEdge.SAND_TERRAIN_TYPE) {
                              //  Tile.renderManager.renderTile(spriteBatch, Tile.sand_tile.getTileID(),  x * Map.TILE_SIZE - (int)camera.position.x, y * Map.TILE_SIZE - (int)camera.position.y);
                               // System.out.println("SAND");
                            }


                            //remember to flush before changing GL states again
                            spriteBatch.flush();
                            spriteBatch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA); //reset?
                        }
                    }
                }

            }

        }
    }

}
