package com.allen.george.artificiallife.simulation.world.map.generation;

import java.util.ArrayList;

/**
 * Created by George on 15/10/2014.
 */
public class TerrainEdge {

    public int x, y, terrainType, id;

    public TerrainEdge(int x, int y, int terrainType, int id){
        this.x = x;
        this.y = y;
        this.terrainType = terrainType;
        this.id = id;
    }

    public boolean isAtTile(int dx,int dy) {
        if (dx == x && dy == y) {
            return true;
        }

        return false;
    }

    public static final int GRASS_TERRAIN_TYPE = 0;
    public static final int SAND_TERRAIN_TYPE = 1;


}
