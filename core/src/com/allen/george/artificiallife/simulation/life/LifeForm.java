package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.ga.BehaviourTree;
import com.allen.george.artificiallife.pathfinding.AStarPathFinder;
import com.allen.george.artificiallife.pathfinding.PathNode;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.org.glassfish.external.statistics.impl.TimeStatisticImpl;

import java.util.ArrayList;

/**
 * Created by George on 24/06/2014.
 */
public class LifeForm {

    private World world;
    public int positionX;
    public int positionY;
    private final int MOVE_SPEED = 1;
    private double TIME_SPEED;
    private BehaviourTree tree;

    private int smellingDistance = 10;
    private int seeingDistance = 5;
    private Food targetFood;

    private AStarPathFinder pathFinder;
    private ArrayList<PathNode> pathToFood;


    //ANIMATION
    private final int UP = 3;
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private int direction = UP;
    private int frame = 0;
    private final int maxFrames = 2;
    private int timer = 0;


    public LifeForm(String bitString, World world){
        this.world = world;
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        this.pathFinder = new AStarPathFinder(world.getMap());
        this.pathToFood = null;

        targetFood = null;

        this.tree = new BehaviourTree(this);
    }


    public void move(int xa, int ya){
        //update ourselves on the collision map
        world.getMap().setCollisionAt(positionX, positionY, 0);
        //then move to the destination square
        positionX += xa;
        positionY += ya;
    }

    public void moveToFoodByPath(Food food) {

        if(food == null) return;

        if(!foodStillExist(targetFood)){
            targetFood = null;
            pathToFood = null;
            return;
        }

        int foodX = (int)food.position.x;
        int foodY = (int)food.position.y;

        pathFinder.start(this, foodX, foodY); //start the thread to calculate the food path

        if (pathToFood != null) {
            if (pathToFood.size() > 0) {
                int nodeX = pathToFood.get(pathToFood.size() - 1).getX();
                int nodeY = pathToFood.get(pathToFood.size() - 1).getY();
                if (positionX < nodeX)
                    moveRight();
                if (positionX > nodeX)
                    moveLeft();
                if (positionY < nodeY)
                   moveUp();
                if (positionY > nodeY)
                   moveDown();
            }

            if (positionX == foodX && positionY == foodY) {
                pathToFood = null;
                targetFood = null;
            }
        }

    }

    public boolean collision(int xa, int ya){
        boolean solid = false;

        if(world.getMap().getCollisionAt(positionX + xa, positionY + ya) == 1) solid = true;

        return solid;
    }

    private boolean foodStillExist(Food food){
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Object obj = world.getMap().getMapObjects().get(i);
            Food f = null;
            if (obj instanceof Food) {
                f = (Food) obj;
            } else {
                continue;
            }

            if(f == food){
                return true;
            }
        }
        return false;
    }


    //TODO: Pretty dirt code for controlling the speed of movement. To FIX, may need to rework day/night system and timing etc
    public void update(double timeSpeed){
        //update the time speed
        this.TIME_SPEED = (int)((float)timeSpeed * 1000) + 1;
        timer += TIME_SPEED;
        //update ourselves on the collision map
        world.getMap().setCollisionAt(positionX, positionY, 1);

        if(timer > 100){
            timer = 0;

            // this.tree.evaluate();
            smellFood(smellingDistance);
            moveToFoodByPath(targetFood);

            //animation
            if(frame != maxFrames){
                frame ++;
            } else {
                frame = 0;
            }
        }
    }

    public void moveUp(){
        direction = UP;
        move(0, 1);
    }

    public void moveDown(){
        direction = DOWN;
        move(0, -1);
    }

    public void moveLeft(){
        direction = LEFT;
        move(-1, 0);
    }

    public void moveRight(){
        direction = RIGHT;
        move(1, 0);
    }


    //TODO: I believe that changing this method to read "mapobjects" and check "instanceof food" may be faster, needs further testing.
    public boolean canSmellFood(int radius){

        for (int i = 0; i < world.getMap().getFoodObjects().size(); i++) {
            // get the current food item
            Food f = world.getMap().getFoodObjects().get(i);
            // get the x and y coordinates of the food
            int fx = (int)f.position.x;
            int fy =  (int)f.position.y;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(fx - positionX);
            int dy = Math.abs(fy - positionY);

            // get the total distance
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            // int distance = dx + dy;
            if (distance <= radius) {
               targetFood = f;
               return true;
            }
        }
        return false;
    }

    public void smellFood(int radius){
        if(targetFood != null){
            return;
        }
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Object obj = world.getMap().getMapObjects().get(i);
            Food f = null;
            if(obj instanceof Food){
                f = (Food)obj;
            } else {
                continue;
            }
            // get the x and y coordinates of the food
            int fx = (int)f.position.x;
            int fy =  (int)f.position.y;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(fx - positionX);
            int dy = Math.abs(fy - positionY);

            // get the total distance
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            // int distance = dx + dy;
            if (distance <= radius) {
                targetFood = f;
            }
        }
    }


    public boolean canSeeFood(int radius){
        return canSmellFood(radius);
    }



    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        spriteBatch.draw(Content.cat[frame][direction], positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }


    public void setTargetFood(Food targetFood) {
        this.targetFood = targetFood;
    }

    public ArrayList<PathNode> getPathToFood() {
        return pathToFood;
    }

    public void setPathToFood(ArrayList<PathNode> pathToFood) {
        this.pathToFood = pathToFood;
    }
}
