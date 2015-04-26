package com.allen.george.artificiallife.simulation.life;

import com.allen.george.artificiallife.ga.*;
import com.allen.george.artificiallife.pathfinding.AStarPathFinder;
import com.allen.george.artificiallife.pathfinding.PathNode;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.objects.ALSObject;
import com.allen.george.artificiallife.simulation.world.map.objects.Den;
import com.allen.george.artificiallife.simulation.world.map.objects.DistanceComparator;
import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;
import com.allen.george.artificiallife.simulation.world.map.objects.resources.Water;
import com.allen.george.artificiallife.utils.Content;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.allen.george.geneticx.Chromosome;
import com.allen.george.geneticx.Gene;
import com.allen.george.geneticx.fitness.FitnessFunction;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by George on 24/06/2014.
 */
public class LifeForm extends ALSObject {

    //================================================================================
    // PROPERTIES
    //================================================================================

    private World world;
    private double TIME_SPEED;
    public String currentNode = "";

    //PATHING PROPERTIES
    private int smellingDistance = 20;
    private int seeingDistance = 20;
    private ALSObject targetObject;
    private AStarPathFinder pathFinder;
    private ArrayList<PathNode> pathToObject;
    private Den den;

    //LIFE FORM PROPERTIES
    private float energy;
    private float maxEnergy;
    private float thirst;
    private float hunger;
    private int numberOfSleeps;
    private int numberOfDeaths;
    private int numberOfSteps;
    private int numberOfFoodEaten;
    private int numberOfWaterDrunk;
    private int numberOfFoodEatenWhenHungry;
    private int numberOfWaterDrunkWhenThirsty;
    private int numberOfSleepsWhenTired;
    private boolean isTired;
    private boolean isHungry;
    private boolean isThirsty;
    private int numberOfRandomStepsWhenHungry;
    private int numberOfRandomStepsWhenTired;
    private int numberOfRandomStepsWhenThirsty;

    //ANIMATION
    private final int UP = 3;
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private int direction = UP;
    private int frame = 0;
    private final int maxFrames = 2;
    private int timer = 0;

    private Chromosome chromosome;
    private BigDecimal fitness;
    private double scaledFitness;

    private Tree tree;

    //NEW NUMBERS
    public int WATER_DRUNK = 0;
    public int FOOD_EATEN = 0;
    public int SLEEPS_TAKEN = 0;
    public int TRIED_TO_EAT = 0;
    public int TRIED_TO_DRINK = 0;
    public int TRIED_TO_SLEEP = 0;
    public int TRIED_TO_WALK_ABOUT = 0;
    public int EVENTS_PER_CYCLE = 0;
    public int CYCLES = 0;
    public int EVENTS_PER_GENERATION = 0;


    //================================================================================
    // CONSTRUCTOR
    //================================================================================

    public LifeForm(LifeForm lifeForm){
        this.fitness = lifeForm.getFitness();
        this.world = lifeForm.getWorld(); //init world

        //set the position
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        //init the path finding and objects
        this.pathFinder = new AStarPathFinder(world.getMap());
        this.pathToObject = null;
        this.targetObject = null;

        //init the den
        this.initDen();

        //init the properties of this life form
        this.maxEnergy = 200;
        this.energy = maxEnergy;
        this.thirst = 4;
        this.hunger = 6;
        this.numberOfDeaths = 0;
        this.numberOfSleeps = 0;
        this.numberOfSteps = 0;
        this.numberOfFoodEaten = 0;
        this.numberOfWaterDrunk = 0;
        this.numberOfFoodEatenWhenHungry = 0;
        this.numberOfWaterDrunkWhenThirsty = 0;
        this.numberOfSleepsWhenTired = 0;
        this.isTired = false;
        this.isHungry = false;

        this.chromosome = new Chromosome(lifeForm.getChromosome());
        this.tree = new Tree(lifeForm.getTree(), this, 0);

    }

    public LifeForm(int x, int y, Tree tree, double fitness, World world){
        this.positionX = x;
        this.positionY = y;
        this.tree = tree;
        this.tree.setLifeForm(this);
        this.fitness = new BigDecimal(fitness);
        this.world  = world;

        //init the path finding and objects
        this.pathFinder = new AStarPathFinder(world.getMap());
        this.pathToObject = null;
        this.targetObject = null;

        //init the den
        this.initDen();

        //init the properties of this life form
        this.maxEnergy = 200;
        this.energy = maxEnergy;
        this.thirst = 4;
        this.hunger = 6;
        this.numberOfDeaths = 0;
        this.numberOfSleeps = 0;
        this.numberOfSteps = 0;
        this.numberOfFoodEaten = 0;
        this.numberOfWaterDrunk = 0;
        this.numberOfFoodEatenWhenHungry = 0;
        this.numberOfWaterDrunkWhenThirsty = 0;
        this.numberOfSleepsWhenTired = 0;
        this.isTired = false;
        this.isHungry = false;
    }

    public LifeForm(World world, Gene[] genes){
        this.world = world; //init world

        //set the position
        this.positionX = (1 + (int)(Math.random() * (((world.getWidth() - 1) - 1) + 1)));
        this.positionY = (1 + (int)(Math.random() * (((world.getHeight() - 1) - 1) + 1)));

        //init the path finding and objects
        this.pathFinder = new AStarPathFinder(world.getMap());
        this.pathToObject = null;
        this.targetObject = null;

        //init the den
        this.initDen();

        //init the properties of this life form
        this.maxEnergy = 200;
        this.energy = maxEnergy;
        this.thirst = 4;
        this.hunger = 6;
        this.numberOfDeaths = 0;
        this.numberOfSleeps = 0;
        this.numberOfSteps = 0;
        this.numberOfFoodEaten = 0;
        this.numberOfWaterDrunk = 0;
        this.numberOfFoodEatenWhenHungry = 0;
        this.numberOfWaterDrunkWhenThirsty = 0;
        this.numberOfSleepsWhenTired = 0;
        this.isTired = false;
        this.isHungry = false;

        this.chromosome = new Chromosome(genes);

        this.tree = Tree.generateTree(SimulationSettings.START_TREE_DEPTH, this);
    }

    private Tree perfectTree(){
        Node sleepNode = new ConditionNode(2);

        sleepNode.setLeftChild(new TerminalNode(1));
        sleepNode.setRightChild(new ConditionNode(0));

        sleepNode.getRightChild().setLeftChild(new TerminalNode(3));
        sleepNode.getRightChild().setRightChild(new ConditionNode(0));

        sleepNode.getRightChild().getRightChild().setLeftChild(new TerminalNode(3));
        sleepNode.getRightChild().getRightChild().setRightChild(new ConditionNode(0));

        sleepNode.getRightChild().getRightChild().getRightChild().setLeftChild(new TerminalNode(3));
        sleepNode.getRightChild().getRightChild().getRightChild().setRightChild(new ConditionNode(0));

        sleepNode.getRightChild().getRightChild().getRightChild().getRightChild().setLeftChild(new TerminalNode(3));
        sleepNode.getRightChild().getRightChild().getRightChild().getRightChild().setRightChild(new ConditionNode(1));

        sleepNode.getRightChild().getRightChild().getRightChild().getRightChild().getRightChild().setLeftChild(new TerminalNode(0));
        sleepNode.getRightChild().getRightChild().getRightChild().getRightChild().getRightChild().setRightChild(new ConditionNode(1));

        sleepNode.getRightChild().getRightChild().getRightChild().getRightChild().getRightChild().getRightChild().setLeftChild(new TerminalNode(0));

        Tree t = new Tree(sleepNode, this);
        try{
            t.getRoot().printTree(new OutputStreamWriter(System.out));
        } catch (Exception e){
            e.printStackTrace();
        }

        return t;
    }


    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }


    public void initAsChild(){
        this.EVENTS_PER_CYCLE = this.tree.getDepth();
        this.CYCLES = 0;
        this.EVENTS_PER_GENERATION = 0;
        this.WATER_DRUNK = 0;
        this.FOOD_EATEN = 0;
        this.SLEEPS_TAKEN = 0;
        this.TRIED_TO_EAT = 0;
        this. TRIED_TO_DRINK = 0;
        this.TRIED_TO_SLEEP = 0;
        this.TRIED_TO_WALK_ABOUT = 0;

        this.setNumberOfSleeps(0);
        this.setNumberOfDeaths(0);
        this.setNumberOfSteps(0);
        this.setNumberOfFoodEaten(0);
        this.setNumberOfWaterDrunk(0);
        this.setNumberOfFoodEatenWhenHungry(0);
        this.setNumberOfSleepsWhenTired(0);
        this.setNumberOfRandomStepsWhenHungry(0);
        this.setNumberOfWaterDrunkWhenThirsty(0);
        this.setNumberOfRandomStepsWhenTired(0);
        this.setNumberOfRandomStepsWhenThirsty(0);
        this.setTired(false);
        this.setHungry(false);
        this.setHunger(6);
        this.setThirst(4);
        this.setEnergy(this.getMaxEnergy());
        this.targetObject = null;
        this.pathToObject = null;
        this.pathFinder = new AStarPathFinder(world.getMap());
        this.initDen();
    }

    private void initDen(){

        ArrayList<ALSObject> closeDens = new ArrayList<ALSObject>();
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Object obj = world.getMap().getMapObjects().get(i);
            Den d = null;
            if(obj instanceof Den){
                d = (Den)obj;
            } else {
                continue;
            }
            // get the x and y coordinates of the food
            int denX = d.positionX;
            int denY = d.positionY;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(denX - positionX);
            int dy = Math.abs(denY - positionY);

            // get the total distance
            double dis = Math.sqrt((dx * dx) + (dy * dy));
            d.distance = (int)dis;
            closeDens.add(d);
        }
        Collections.sort(closeDens, new DistanceComparator());
        if(closeDens.size() > 0){
            this.den = (Den)closeDens.get(0); //should be the closest object!
        } else {
           System.err.println("Could not find den");
        }
    }


    public void move(int xa, int ya){
        //update ourselves on the collision map
        world.getMap().setCollisionAt(positionX, positionY, 0);
        //then move to the destination square
        positionX += xa;
        positionY += ya;
        numberOfSteps += 1;
        //update our energy etc
        if(energy != 0) {
            energy -= 1;
        }
    }

    public boolean moveToObjectByPath(ALSObject object, int sender) throws Exception{
        if(object == null) {
            targetObject = null;
            pathToObject = null;
            pathFinder = new AStarPathFinder(world.getMap());
            return true;
        }

        if(sender == 0){
            if(!(object instanceof Food)) {
                targetObject = null;
                pathToObject = null;
                pathFinder = new AStarPathFinder(world.getMap());
                return true;
            }
        }
        else if(sender == 1){
            if(!(object instanceof Water)) {
                targetObject = null;
                pathToObject = null;
                pathFinder = new AStarPathFinder(world.getMap());
                return true;
            }
        }
        else if(sender == 2){
            if(!(object instanceof Den)){
                targetObject = null;
                pathToObject = null;
                pathFinder = new AStarPathFinder(world.getMap());
                return true;
            }
        }

        if(!objectStillExists(object)){
            targetObject = null;
            pathToObject = null;
            pathFinder = new AStarPathFinder(world.getMap());
            return true;
        }

        //Get the objects position. If its water we want to find an adjacent tile
        int objectX = object.positionX;
        int objectY = object.positionY;

        pathFinder.start( world.getMap(), this, objectX, objectY); //start the thread to calculate the food path

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
                if(sender == 0){
                    ((Food) object).apply(this);
                }
                if(sender == 1){
                    ((Water) object).apply(this);
                }
                if(sender == 2){
                    ((Den) object).apply(this);
                }

                pathToObject = null;
                pathFinder.cleanUp();
                return true;
            }
        } else{ // the pathfinder failed so we need to clean it up
            targetObject = null;
            pathToObject = null;
            pathFinder = new AStarPathFinder(world.getMap());
            return false;
        }

        return false;
    }

    public boolean moveToFoodByPath() {
        try{
            return moveToObjectByPath(targetObject, 0);
        } catch (Exception e){
            return true;
        }
    }

    public boolean moveToWaterByPath(){
        try{
            return moveToObjectByPath(targetObject, 1);
        } catch (Exception e){
            return true;
        }

    }

    public boolean moveToDenByPath(){
        try{
            return moveToObjectByPath(targetObject, 2);
        } catch (Exception e){
            return true;
        }
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

    public void update(double timeSpeed){
        //update the time speed
        this.TIME_SPEED = (int)((float)timeSpeed * 1000) + 1;
        timer += TIME_SPEED;
        //update ourselves on the collision map
        world.getMap().setCollisionAt(positionX, positionY, 1);

        setTired(needsToGoToDen());
        setHungry(isHungry());
        setThirsty(isThirsty());


        if(timer > 100){ //if thread == null?
            timer = 0;

            this.CYCLES += 1;

            if(energy <= 0) {
                this.energy = this.maxEnergy;
                numberOfDeaths += 1;
            }
            hunger += 0.1;
            thirst += 0.05;

             tree.runRootNode(); //new thread?
            //create thread
            //start thread

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

    public boolean moveInRandomDirection(boolean fromDen){
       // System.out.println("RANDOM");
        int dir = 1 + (int)(Math.random() * ((4 - 1) + 1));
        if(dir == 1){
            int numberOfSteps = 9 + (int)(Math.random() * ((18 - 9) + 1));
            for(int i = 1; i < numberOfSteps; i ++){
                if(world.getMap().getCollisionAt(positionX, positionY + i) == 1){
                    return true;
                } else {
                    numberOfSteps --;
                    if(isHungry()){
                        numberOfRandomStepsWhenHungry ++;
                    }
                    if(isTired()){
                        numberOfRandomStepsWhenTired ++;
                    }
                    if(isThirsty()){
                        numberOfRandomStepsWhenThirsty ++;
                    }

                    if(!fromDen){
                        this.WATER_DRUNK -= 1;
                        this.FOOD_EATEN -= 1;
                        this.SLEEPS_TAKEN -= 1;
                    }


                    moveUp();
                }
            }

        } else if(dir == 2){
            int numberOfSteps = 1 + (int)(Math.random() * ((9 - 1) + 1));
            for(int i = 1; i < numberOfSteps; i ++){
                if(world.getMap().getCollisionAt(positionX, positionY - i) == 1){
                    return true;
                } else {
                    numberOfSteps --;
                    if(isHungry()){
                        numberOfRandomStepsWhenHungry ++;
                    }
                    if(isTired()){
                        numberOfRandomStepsWhenTired ++;
                    }
                    if(isThirsty()){
                        numberOfRandomStepsWhenThirsty ++;
                    }

                    if(!fromDen){
                        this.WATER_DRUNK -= 1;
                        this.FOOD_EATEN -= 1;
                        this.SLEEPS_TAKEN -= 1;
                    }
                    moveDown();
                }
            }
        } else if(dir == 3){
            int numberOfSteps = 1 + (int)(Math.random() * ((9 - 1) + 1));
            for(int i = 1; i < numberOfSteps; i ++){
                if(world.getMap().getCollisionAt(positionX - i, positionY) == 1){
                    return true;
                } else {
                    numberOfSteps --;
                    if(isHungry()){
                        numberOfRandomStepsWhenHungry ++;
                    }
                    if(isTired()){
                        numberOfRandomStepsWhenTired ++;
                    }
                    if(isThirsty()){
                        numberOfRandomStepsWhenThirsty ++;
                    }
                    if(!fromDen){
                        this.WATER_DRUNK -= 1;
                        this.FOOD_EATEN -= 1;
                        this.SLEEPS_TAKEN -= 1;
                    }
                    moveLeft();
                }
            }
        } else if(dir == 4){
            int numberOfSteps = 1 + (int)(Math.random() * ((9 - 1) + 1));
            for(int i = 1; i < numberOfSteps; i ++){
                if(world.getMap().getCollisionAt(positionX + i, positionY) == 1){
                    return true;
                } else {
                    numberOfSteps --;
                    if(isHungry()){
                        numberOfRandomStepsWhenHungry ++;
                    }
                    if(isTired()){
                        numberOfRandomStepsWhenTired ++;
                    }
                    if(isThirsty()){
                        numberOfRandomStepsWhenThirsty ++;
                    }

                    if(!fromDen){
                        this.WATER_DRUNK -= 1;
                        this.FOOD_EATEN -= 1;
                        this.SLEEPS_TAKEN -= 1;
                    }
                    moveRight();
                }
            }

        }
        return true;
    }


    public boolean smellFood(int radius){
        this.TRIED_TO_EAT += 1;
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
                //SMELT FOOD
             return true;
        } else {
            targetObject = null;
            pathToObject = null;
            pathFinder = new AStarPathFinder(world.getMap());
            return false;
        }
    }

    public boolean seeWater(int radius){
        this.TRIED_TO_DRINK += 1;
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
            //SAW WATER
            return true;
        } else {
            return false;
        }
    }

    public boolean canFindDen(int radius){
        this.TRIED_TO_SLEEP += 1;
        ArrayList<ALSObject> closeDens = new ArrayList<ALSObject>();
        for (int i = 0; i < world.getMap().getMapObjects().size(); i++) {
            // get the current food item
            Object obj = world.getMap().getMapObjects().get(i);
            Den d = null;
            if(obj instanceof Den){
                d = (Den)obj;
            } else {
                continue;
            }
            // get the x and y coordinates of the food
            int wx = d.positionX;
            int wy = d.positionY;

            // get the x and y distance between the food and the bug
            int dx = Math.abs(wx - positionX);
            int dy = Math.abs(wy - positionY);
            double dis = Math.sqrt((dx * dx) + (dy * dy));

            if (dis <= radius) {
                d.distance = (int)dis;
                closeDens.add(d);
            }
        }
        Collections.sort(closeDens, new DistanceComparator());
        if(closeDens.size() > 0){
            targetObject = closeDens.get(0); //should be the closest object!
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
        if (dis  >= (this.energy  - 10)) {
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
        if(this.thirst > 2){
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


    public double totalPenalty;
    public double adjustment;



    public int getNumberOfSleeps() {
        return numberOfSleeps;
    }

    public void setNumberOfSleeps(int numberOfSleeps) {
        this.numberOfSleeps = numberOfSleeps;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public int getNumberOfFoodEaten() {
        return numberOfFoodEaten;
    }

    public void setNumberOfFoodEaten(int numberOfFoodEaten) {
        this.numberOfFoodEaten = numberOfFoodEaten;
    }

    public int getNumberOfWaterDrunk() {
        return numberOfWaterDrunk;
    }

    public void setNumberOfWaterDrunk(int numberOfWaterDrunk) {
        this.numberOfWaterDrunk = numberOfWaterDrunk;
    }

    public int getNumberOfDeaths() {
        return numberOfDeaths;
    }

    public void setNumberOfDeaths(int numberOfDeaths) {
        this.numberOfDeaths = numberOfDeaths;
    }

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
        if(thirst <= 0){
            this.thirst = 0;
        } else {
            this.thirst = thirst;
        }
    }

    public float getHunger() {
        return hunger;
    }

    public void setHunger(float hunger) {
        if(hunger <= 0){
            this.hunger = 0;
        } else {
            this.hunger = hunger;
        }

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

    public void calculateFitnessValue(FitnessFunction fitnessFunction){
        double f = fitnessFunction.evaluate(this);
        this.fitness = new BigDecimal(f);
        this.fitness.setScale(5, RoundingMode.HALF_UP);
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public BigDecimal getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = new BigDecimal(fitness);
        this.fitness.setScale(5, RoundingMode.HALF_UP);
    }


    public double getScaledFitness() {
        return scaledFitness;
    }

    public void setScaledFitness(double scaledFitness) {
        this.scaledFitness = scaledFitness;
    }


    public int getNumberOfFoodEatenWhenHungry() {
        return numberOfFoodEatenWhenHungry;
    }

    public void setNumberOfFoodEatenWhenHungry(int numberOfFoodEatenWhenHungry) {
        this.numberOfFoodEatenWhenHungry = numberOfFoodEatenWhenHungry;
    }

    public int getNumberOfSleepsWhenTired() {
        return numberOfSleepsWhenTired;
    }

    public void setNumberOfSleepsWhenTired(int numberOfSleepsWhenTired) {
        this.numberOfSleepsWhenTired = numberOfSleepsWhenTired;
    }

    public boolean isTired() {
        return isTired;
    }

    public void setTired(boolean isTired) {
        this.isTired = isTired;
    }

    public void setHungry(boolean isHungry) {
        this.isHungry = isHungry;
    }

    public boolean getIsHungry(){
        return isHungry;
    }

    public int getNumberOfRandomStepsWhenHungry() {
        return numberOfRandomStepsWhenHungry;
    }

    public void setNumberOfRandomStepsWhenHungry(int numberOfRandomStepsWhenHungry) {
        this.numberOfRandomStepsWhenHungry = numberOfRandomStepsWhenHungry;
    }

    public int getNumberOfRandomStepsWhenTired() {
        return numberOfRandomStepsWhenTired;
    }

    public void setNumberOfRandomStepsWhenTired(int numberOfRandomStepsWhenTired) {
        this.numberOfRandomStepsWhenTired = numberOfRandomStepsWhenTired;
    }

    public int getNumberOfWaterDrunkWhenThirsty() {
        return numberOfWaterDrunkWhenThirsty;
    }

    public void setNumberOfWaterDrunkWhenThirsty(int numberOfWaterDrunkWhenThirsty) {
        this.numberOfWaterDrunkWhenThirsty = numberOfWaterDrunkWhenThirsty;
    }

    public void setThirsty(boolean isThirsty) {
        this.isThirsty = isThirsty;
    }

    public boolean getIsThirty(){
        return this.isThirsty;
    }

    public int getNumberOfRandomStepsWhenThirsty() {
        return numberOfRandomStepsWhenThirsty;
    }

    public void setNumberOfRandomStepsWhenThirsty(int numberOfRandomStepsWhenThirsty) {
        this.numberOfRandomStepsWhenThirsty = numberOfRandomStepsWhenThirsty;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld(){
        return this.world;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

