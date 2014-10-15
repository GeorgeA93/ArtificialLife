package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.ga.Behaviour.BehaviourTree;
import com.allen.george.artificiallife.ga.Chromosome;
import com.allen.george.artificiallife.ga.GAUtil;
import com.allen.george.artificiallife.ga.Genotype;
import com.allen.george.artificiallife.pathfinding.AStarPathFinder;
import com.allen.george.artificiallife.pathfinding.PathNode;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.ALSObject;
import com.allen.george.artificiallife.simulation.world.map.objects.Den;
import com.allen.george.artificiallife.simulation.world.map.objects.DistanceComparator;
import com.allen.george.artificiallife.simulation.world.map.objects.MapObject;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.simulation.world.map.objects.resources.Water;
import com.allen.george.artificiallife.utils.Content;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by George on 24/06/2014.
 */
public class LifeForm extends ALSObject{

    //================================================================================
    // PROPERTIES
    //================================================================================

    private World world;
    private double TIME_SPEED;
    private BehaviourTree tree;

    //PATHING PROPERTIES
    private int smellingDistance = 20;
    private int seeingDistance = 15;
    private ALSObject targetObject;
    private AStarPathFinder pathFinder;
    private ArrayList<PathNode> pathToObject;
    private Den den;

    //LIFE FORM PROPERTIES
    private float energy;
    private float maxEnergy;
    private float thirst;
    private float hunger;

    //ANIMATION
    private final int UP = 3;
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private int direction = UP;
    private int frame = 0;
    private final int maxFrames = 2;
    private int timer = 0;

    private Genotype genotype;

    //================================================================================
    // CONSTRUCTOR
    //================================================================================

    public LifeForm(World world){
        this.world = world; //init world

        //set the position
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        //init the path finding and objects
        this.pathFinder = new AStarPathFinder(world.getMap());
        this.pathToObject = null;
        this.targetObject = null;

        //init then den
        this.initDen();

        //init the properties of this life form
        this.maxEnergy = 200;
        this.energy = maxEnergy;
        this.thirst = 0;
        this.hunger = 0;

        //Create the genetic attributes
        Chromosome chromosomes = GAUtil.generateInitialChromsome(16);
        genotype = new Genotype(chromosomes, this);
        genotype.print();
        this.tree = GAUtil.generateTreeFromGenes(genotype.getChromosomes().getGenes(), this);
    }

    private void initDen(){
        ArrayList<Den> dens = new ArrayList<Den>();
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            ALSObject obj = world.getMap().getMapObjects().get(i);
            Den d;
            if(obj instanceof Den){
                d = (Den)obj;
                dens.add(d);
            }else {
                continue;
            }
        }

        this.den = dens.get((int)(Math.random() * ((dens.size() - 1 ) + 1))); //pick a random den out of all the dens on the map
    }


    public void move(int xa, int ya){
        //update ourselves on the collision map
        world.getMap().setCollisionAt(positionX, positionY, 0);
        //then move to the destination square
        positionX += xa;
        positionY += ya;
        //update our energy etc
        if(energy != 0) {
            energy -= 1;
        }
    }

    public void moveToObjectByPath(ALSObject object){
        if(object == null) return;

        if(!objectStillExists(object)){
            targetObject = null;
            pathToObject = null;
            return;
        }

        //Get the objects position. If its water we want to find an adjacent tile
        int objectX = 0;
        int objectY = 0;
        if(object instanceof Water){
            objectX = object.drinkPositionsX[(int)(Math.random() * ((object.drinkPositionsX.length - 1) + 1))];
            objectY = object.drinkPositionsY[(int)(Math.random() * ((object.drinkPositionsY.length - 1) + 1))];
        } else {
            objectX = object.positionX;
            objectY = object.positionY;
        }

        pathFinder.start(this, objectX, objectY); //start the thread to calculate the food path

        if (pathToObject != null) { //if it found a path
            if (pathToObject.size() > 0) { //and the paths length is greater than 0
                int nodeX = pathToObject.get(pathToObject.size() - 1).getX(); //get the next node
                int nodeY = pathToObject.get(pathToObject.size() - 1).getY();
                if (positionX < nodeX)
                    moveRight();
                if (positionX > nodeX)
                    moveLeft();
                if (positionY < nodeY)
                    moveUp();
                if (positionY > nodeY)
                    moveDown();
            }

            if (positionX == objectX && positionY == objectY) { //if we have found the food we stop
                pathToObject = null;
                targetObject = null;
            }
        } else{ // the pathfinder failed so we need to clean it up
            if(!(targetObject instanceof Den)){
                world.getMap().removeObject((MapObject)targetObject);
            }
            targetObject = null;
            pathToObject = null;
            pathFinder = new AStarPathFinder(world.getMap());
        }
    }

    public void moveToFoodByPath() {
       // System.out.println("FOOD");
        moveToObjectByPath(targetObject);

    }

    public void moveToWaterByPath(){
        //System.out.println("WATER");
        moveToObjectByPath(targetObject);
    }

    public void moveToDenByPath(){
       // System.out.println("DEN");
        targetObject = den; //set the target as our den
        moveToObjectByPath(targetObject);
    }


    public boolean collision(int xa, int ya){
        boolean solid = false;

        if(world.getMap().getCollisionAt(positionX + xa, positionY + ya) == 1) solid = true;

        return solid;
    }

    //method checks if the target object still exists on the map
    private boolean objectStillExists(ALSObject object){
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current item
            ALSObject obj = world.getMap().getMapObjects().get(i);
            Food f = null;
            Water w = null;
            Den d = null;
            if (obj instanceof Food) {
                f = (Food) obj;
            } else if(obj instanceof Water) {
                w = (Water) obj;
            } else if(obj instanceof Den) {
                d = (Den) obj;
            }else {
                continue;
            }

            if(f == object){
                return true;
            } else if(w == object){
                return true;
            }else if(d == object){
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

          // System.out.println("Energy: " + energy + ", hunger: " + hunger + ", thirst: " + thirst);

            /*
            if(energy > 0){ //we have energy
                if(!needsToGoToDen()){ //we dont need to go to our den
                    //increase hunger and thirst
                    hunger += 0.1;
                    thirst += 0.05;

                    //if we are hungry
                    if(isHungry() && !isThirsty()){
                        //if we can smell food
                       if(smellFood(smellingDistance)){
                           moveToFoodByPath();
                       } else {
                            //moveToRandomSquareOnMap();
                           moveInRandomDirection();
                       }
                    }
                    if(isThirsty() && !isHungry()){ //if we are thirsty
                        //we can see water
                        if(seeWater(seeingDistance)){
                            moveToWaterByPath();
                        } else {
                          //  moveToRandomSquareOnMap();
                            moveInRandomDirection();
                        }
                    }

                    //if we are hungry and thirsty
                    if(isHungry() && isThirsty()){

                        if(smellFood(smellingDistance)){
                            moveToFoodByPath();
                        } else if(seeWater(seeingDistance)){
                            moveToWaterByPath();
                        } else {
                           // moveToRandomSquareOnMap();
                            moveInRandomDirection();
                        }
                    }

                    //if we arent hungry or thirsty we need to walk to a random square on the map
                    if(!isHungry() && !isThirsty()){
                       //moveToRandomSquareOnMap();
                        moveInRandomDirection();
                    }
                } else {
                    //we need to move to our den!
                    moveToDenByPath();
                }
            } else {
                //punish that fitness value and reset energy etc
            }

            */
            hunger += 0.1;
            thirst += 0.05;
            tree.runRootNode();
            tree.getRootNode().mutate();

            //animation
            if(frame != maxFrames){
                frame ++; //increase the current frame
            } else {
                frame = 0; //reset to the start of the animation
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

    public void moveInRandomDirection(){
       // System.out.println("RANDOM");
        int dir = 1 + (int)(Math.random() * ((4 - 1) + 1));
        if(dir == 1){
            if(world.getMap().getCollisionAt(positionX, positionY + 1) == 1){
               return;
            } else {
                moveUp();
            }
        } else if(dir == 2){
            if(world.getMap().getCollisionAt(positionX, positionY - 1) == 1){
                return;
            } else {
                moveDown();
            }
        } else if(dir == 3){
            if(world.getMap().getCollisionAt(positionX - 1, positionY) == 1){
                return;
            } else {
                moveLeft();
            }
        } else if(dir == 4){
            if(world.getMap().getCollisionAt(positionX + 1, positionY) == 1){
                return;
            } else {
               moveRight();
            }
        }
    }


    public boolean smellFood(int radius){
        if(targetObject != null){
            return true;
        }
        ArrayList<ALSObject> closeFoods = new ArrayList<ALSObject>();
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
            int fx = f.positionX;
            int fy = f.positionY;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(fx - positionX);
            int dy = Math.abs(fy - positionY);

            // get the total distance
            double dis = Math.sqrt((dx * dx) + (dy * dy));
            if (dis <= radius) {
                f.distance = (int)dis;
                closeFoods.add(f);
            }
        }
        Collections.sort(closeFoods, new DistanceComparator());
        if(closeFoods.size() > 0){
             targetObject = closeFoods.get(0); //should be the closest object!
             return true;
        } else {
            return false;
        }
    }

    public boolean seeWater(int radius){
        if(targetObject != null){
            return true;
        }
        ArrayList<ALSObject> closeWaters = new ArrayList<ALSObject>();
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Object obj = world.getMap().getMapObjects().get(i);
            Water w = null;
            if(obj instanceof Water){
                w = (Water)obj;
            } else {
                continue;
            }
            // get the x and y coordinates of the food
            int wx = w.positionX;
            int wy = w.positionY;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(wx - positionX);
            int dy = Math.abs(wy - positionY);
            double dis = Math.sqrt((dx * dx) + (dy * dy));
            if (dis <= radius) {
                w.distance = (int)dis;
                closeWaters.add(w);
            }
        }
        Collections.sort(closeWaters, new DistanceComparator());
        if(closeWaters.size() > 0){
            targetObject = closeWaters.get(0); //should be the closest object!
            return true;
        } else {
            return false;
        }
    }

    public boolean needsToGoToDen(){

        // get the x and y coordinates of the den
        int ddx = this.den.positionX;
        int ddy = this.den.positionY;

        // get the x and y distance between the den and the bug
        int dx = Math.abs(ddx - positionX);
        int dy = Math.abs(ddy - positionY);

        // get the total distance
        double dis = Math.sqrt((dx * dx) + (dy * dy));
        if ((dis * 2) >= (this.energy  - 10)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isHungry(){
        if(this.hunger > 2){
            return true;
        } else {
            return false;
        }
    }

    public boolean isThirsty(){
        if(this.thirst > 3){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEnergy(){
        if(energy > 0 ) return true;

        return false;
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        spriteBatch.draw(Content.cat[frame][direction], positionX * Map.TILE_SIZE - (int)camera.position.x, positionY * Map.TILE_SIZE - (int)camera.position.y);
    }


    //================================================================================
    // GETTERS AND SETTERS
    //================================================================================

    public ALSObject getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(ALSObject targetObject) {
        this.targetObject = targetObject;
    }

    public ArrayList<PathNode> getPathToObject() {
        return pathToObject;
    }

    public void setPathToObject(ArrayList<PathNode> pathToObject) {
        this.pathToObject = pathToObject;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getThirst() {
        return thirst;
    }

    public void setThirst(float thirst) {
        this.thirst = thirst;
    }

    public float getHunger() {
        return hunger;
    }

    public void setHunger(float hunger) {
        this.hunger = hunger;
    }

    public float getMaxEnergy(){
        return this.maxEnergy;
    }

    public void setDen(Den den){
        this.den = den;
    }

    public int getSeeingDistance() {
        return seeingDistance;
    }

    public int getSmellingDistance() {
        return smellingDistance;
    }

    public BehaviourTree getTree(){
        return this.tree;
    }
}
