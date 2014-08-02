package com.allen.george.artificiallife.pathfinding;

/**
 * Created by George on 02/08/2014.
 */
public class PathNode {

    //position of the node
    private int x,  y;
    //link to the parent node
    private PathNode parent;
    //gCost = node to node cost
    //hCost = heuristic cost
    //fCost = combination of g and h cost
    private double fCost, gCost, hCost;

    /**
     *
     * @return the x coordinate of the node
     */
    public int getX(){
        return this.x;
    }

    /**
     *
     * @return the y coordinate of the node
     */
    public int getY(){
        return this.y;
    }

    /**
     *
     * @return the nodes parent
     */
    public PathNode getParent(){
        return this.parent;
    }

    /**
     *
     * @return the nodes fCost
     */
    public double getFCost(){
        return this.fCost;
    }

    /**
     *
     * @return the nodes gCost
     */
    public double getGCost(){
        return this.gCost;
    }

    /**
     *
     * @return the nodes hCost
     */
    public double getHCost(){
        return this.hCost;
    }

    /**
     * Sets the x coordinate of the node
     * @param x the value to set
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Sets the y coordinate of the node
     * @param y the value to set
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Sets the parent of the node
     * @param parent the node to set
     */
    public void setParent(PathNode parent){
        this.parent = parent;
    }

    /**
     * Sets the fCost of the node
     * @param cost the value to set
     */
    public void setFCost(double cost){
        this.fCost = cost;
    }

    /**
     * Sets the gCost of the node
     * @param cost the value to set
     */
    public void setGCost(double cost){
        this.gCost = cost;
    }

    /**
     * Sets the hCost of the node
     * @param cost the value to set
     */
    public void setHCost(double cost){
        this.hCost = cost;
    }

    /**
     * Class Constructor
     * @param x the x coordinate of the node
     * @param y the y coordinate of the node
     * @param parent the parent node
     * @param gCost  the gCost
     * @param hCost  the hCost
     */
    public PathNode(int x, int y, PathNode parent, double gCost, double hCost){
        this.x = x;
        this.y =y ;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
        this.fCost = this.gCost + this.hCost;
    }
}
