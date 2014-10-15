package com.allen.george.artificiallife.simulation.world.map.generation;



/**
 * Created by George on 15/10/2014.
 */
public class ClusterPoint implements Comparable<ClusterPoint> {

    public float x, y;

    public ClusterPoint(float x, float y){
        this.x = x;
        this.y = y;
    }

    public ClusterPoint  plus    (ClusterPoint b) { return new ClusterPoint(x + b.x, y + b.y); }
    public ClusterPoint  minus   (ClusterPoint b) { return new ClusterPoint(x - b.x, y - b.y); }
    public ClusterPoint  times   (float s) { return new ClusterPoint(x * s, y * s); }
    public ClusterPoint  divide  (float s) { return new ClusterPoint(x / s, y / s); }
    public float dot     (ClusterPoint b) { return this.x * b.x + y * b.y; }
    public float cross   (ClusterPoint b) { return this.x * b.y - y * b.x; }

    public float norm    () {
        return this.dot(this);
    }

    public float length  () {

        return (float)Math.sqrt(norm());
    }

    public ClusterPoint  unit    () {
        return this.divide(length());
    }

    public ClusterPoint  rot90   () {
        return new ClusterPoint(-y, x);
    }

    public float dist    (ClusterPoint b) {
        return b.minus(this).length();
    }

    public ClusterPoint  midPoint(ClusterPoint a, ClusterPoint b) {
        return a.plus(b).divide(2);
    }

    public int compareTo(ClusterPoint b)
    {
        if (x==b.x && y==b.y) return 0;

        if (x < b.x) return -1;
        if (x > b.x) return 1;
        if (y < b.y) return -1;
        if (y > b.y) return 1;

        return 0;
    }

}
