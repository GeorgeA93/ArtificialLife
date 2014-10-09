package com.allen.george.artificiallife.pathfinding;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.map.Map;

import java.util.*;

/**
 * Created by George on 02/08/2014.
 */
public class AStarPathFinder implements Runnable{

    private ArrayList<PathNode> path = new ArrayList<PathNode>();
    private ArrayList<PathNode> openList = new ArrayList<PathNode>();
    private ArrayList<PathNode> closedList = new ArrayList<PathNode>();
    private PathNode currentNode;
    private int maxDepth = 10000;

    private Map map;
    private int[][] gameMap;

    private Thread thread;
    private LifeForm lifeForm;
    private int objectX, objectY;

    public void start(LifeForm lifeForm, int objectX, int objectY){
        this.lifeForm = lifeForm;
        this.objectX = objectX;
        this.objectY = objectY;
        this.thread = new Thread(this, "Path Finding");
        this.path = new ArrayList<PathNode>();
        this.openList = new ArrayList<PathNode>();
        this.closedList = new ArrayList<PathNode>();
        this.currentNode = null;
        this.thread.start();
    }

    public void run(){
       // if(lifeForm.getPathToFood() == null){

                lifeForm.setPathToObject(findPath(lifeForm.positionX, lifeForm.positionY, objectX, objectY));


      //  }
    }

    /**
     * Compares the fCosts of two nodes
     */
    private Comparator<PathNode> sortNodes = new Comparator<PathNode>() {
        @Override
        public int compare(PathNode node0, PathNode node1) {
            if (node1.getFCost() < node0.getFCost()) {
                return +1;
            }
            if (node1.getFCost() > node0.getFCost()) {
                return -1;
            }
            return 0;
        }
    };

    /**
     * Class constructor
     * @param map the current map being used
     */
    public AStarPathFinder(Map map) {
        this.map = map;
        this.gameMap = map.getCollisionMap();
    }

    /**
     * Finds the path between to points
     * @param xs the x starting point
     * @param ys the y starting point
     * @param xe the x ending point
     * @param ye the y ending point
     * @return the list of nodes to get from A to B
     * @see PathNode
     */
    int index = 0;
    public ArrayList<PathNode> findPath(int xs, int ys, int xe, int ye) {
        currentNode = new PathNode(xs, ys, null, 0, getDistance(xs,ys, xe, ye));
        openList.add(currentNode);

        index = 0;

        while (openList.size() > 0) {
            index ++;
            if(index == maxDepth){
               break;
            }

            // sort the open nodes by their fCost
            try {
                Collections.sort(openList, sortNodes);
            }catch(Exception e){
               return null;
            }
            if(openList.size() == 0) continue;
            currentNode = openList.get(0);

            if (currentNode.getX() == xe && currentNode.getY() == ye) {
                //reconstruct the path


                while(currentNode.getParent() != null){


                    path.add(currentNode);
                    currentNode = currentNode.getParent();
                }
                openList.clear();
                closedList.clear();
                return path;
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            // loop through the adjacent tiles
            for (int i = 0; i < 9; i++) {

                // skip these tiles to avoid diagonal movement
                if (i == 0)continue;
                if (i == 2)continue;
                if (i == 6)continue;
                if (i == 8)continue;
                // skip this tile as its the middle tile
                if (i == 4)continue;

                if(currentNode == null) continue;
                // get the current location
                int x = currentNode.getX();
                int y = currentNode.getY();

                // get the adjacency we want to check
                int xd = (i % 3) - 1;
                int yd = (i / 3) - 1;

                int currentChar = 0;

                if(x + xd >= 0 && x + xd <= map.getWorld().getWidth() - 1 && y + yd >= 0 && y + yd <= map.getWorld().getHeight() - 1){
                    currentChar = map.getCollisionAt(x + xd,y + yd);
                } else {
                    continue;
                }


                // if the current char is a 1 we want to skip due to collision
                if (currentChar == 1)continue;


                //calculate the gCost and hCost of the adjacent tile
                double gCost = currentNode.getGCost() + getDistance(currentNode.getX(), currentNode.getY(), x +xd, y + yd);
                double hCost = getDistance(x + xd, y + yd, xe, ye);

                //create the new node
                PathNode node = new PathNode(x + xd, y + yd, currentNode, gCost, hCost);

                if(isNodeInList(closedList, x + xd, y + yd) && gCost >= node.getGCost()) continue;
                if(!isNodeInList(openList, x + xd, y + yd) || gCost < node.getGCost()) openList.add(node);
            }
        }
        closedList.clear();
        return null;
    }

    /**
     * Checks if the x and y coordinates are inside a list
     * @param list the list to check
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if its in the list, false if not
     */
    private boolean isNodeInList(ArrayList<PathNode> list, int x, int y){
        for(int i = 0; i < list.size(); i ++){
            PathNode n = list.get(i);
            if(n == null) continue;
            if(n.getX() == x && n.getY() == y){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the distance between two points
     * @param xs the x coordinate of the first point
     * @param ys the y coordinate of the first point
     * @param xe the x coordinate of the second point
     * @param ye the y coordinate of the second point
     * @return a double, the distance between the points
     */
    private double getDistance(int xs, int ys, int xe, int ye) {
        double dx = xs - xe;
        double dy = ys - ye;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
