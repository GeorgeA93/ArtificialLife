package com.allen.george.artificiallife.simulation.world.map.generation;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by George on 15/10/2014.
 */
public class KMeansCluster {

    public static ClusterOfPoints[] cluster(ClusterPoint[] p, int k){
        ClusterPoint[] center = new ClusterPoint[k];
        int[] parent = new int[p.length];

        Arrays.fill(parent, -1);

        float sx = 0f, sy = 0f;
        for (int i=0; i<p.length; i++)
        {
            sx += p[i].x;
            sy += p[i].y;
        }

        sx/=p.length;
        sy/=p.length;

        for (int i=0; i<k; i++)
            center[i] = new ClusterPoint(sx* (float)Math.random(), sy* (float)Math.random());


        while (true)
        {
            boolean changed = false;


            for (int x = 0; x<p.length; x++)
            {
                double bestDist = Double.MAX_VALUE;
                int inx = -1;
                for (int i=0; i<k; i++)
                {
                    double d = p[x].dist(center[i]);
                    if (inx == -1 || d<bestDist)
                    {
                        inx = i;
                        bestDist = d;
                    }
                }

                if (parent[x] != inx) changed = true;

                parent[x] = inx;
            }

            if (!changed) break;


            float[] sumX = new float[k];
            float[] sumY = new float[k];
            int[] count = new int[k];

            for (int i = 0; i<p.length; i++)
            {
                sumX[ parent[i] ] += p[i].x;
                sumY[ parent[i] ] += p[i].y;
                count[ parent[i] ]++;
            }

            for (int i = 0; i<k; i++)
                center[i] = new ClusterPoint(sumX[i]/count[i], sumY[i]/count[i]);
        }

        ArrayList<ClusterOfPoints> res = new ArrayList<ClusterOfPoints>();
        for (int i=0; i<k; i++)
            res.add(new ClusterOfPoints(center[i]));

        for (int i=0; i<p.length; i++)
            res.get(parent[i]).points.add(p[i]);

        for (int i=0; i<res.size(); i++)
            if (res.get(i).points.size() == 0)
                res.remove(i--);

        return res.toArray(new ClusterOfPoints[0]);

    }

}
