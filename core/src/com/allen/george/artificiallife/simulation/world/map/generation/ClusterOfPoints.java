package com.allen.george.artificiallife.simulation.world.map.generation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 15/10/2014.
 */
public class ClusterOfPoints {

    public ClusterPoint center;
    public ArrayList<ClusterPoint> points;
    public int terrainValue;

    public ClusterOfPoints(ClusterPoint a)
    {
        center = a;
        points = new ArrayList<ClusterPoint>();
        Random random = new Random();
        terrainValue =  (int)(Math.random() * ((2) + 1));
    }

    public ClusterPoint getCentroid()
    {
        float x = 0f, y = 0f;
        for (ClusterPoint a : points)
        {
            x += a.x;
            y += a.y;
        }

        return new ClusterPoint(x/points.size(), y/points.size());
    }


}
