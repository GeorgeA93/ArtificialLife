package com.allen.george.artificiallife.ga.Behaviour;

import com.allen.george.artificiallife.ga.Gene;
import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.Random;

/**
 * Created by George on 07/10/2014.
 */
public class BehaviourTreeNode {

    public static int NUM_CONDITIONS = 6;
    public static int NUM_ACTIONS = 0;
    public static int NUM_TERMINALS = 4;

    private BehaviourTreeNode leftChild;
    private BehaviourTreeNode rightChild;
    private BehaviourTreeNode parent;

    private int function;

    private BehaviourTreeNodeType behaviourTreeNodeType;

    private boolean isReachable = true;

    public BehaviourTreeNode(BehaviourTreeNodeType behaviourTreeNodeType, int function){
        this.behaviourTreeNodeType = behaviourTreeNodeType;
        this.function = function;
    }

    public static BehaviourTreeNode createFromGene(Gene g){
        int val = g.getAllele();
        if (val < NUM_CONDITIONS)
            return new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, val);
        if (val < NUM_ACTIONS + NUM_CONDITIONS)
            return new BehaviourTreeNode(BehaviourTreeNodeType.ACTION, val - NUM_CONDITIONS);
        if (val < NUM_TERMINALS + NUM_ACTIONS + NUM_CONDITIONS)
            return new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, val - NUM_CONDITIONS - NUM_ACTIONS);

        return null;
    }

    public boolean runFunction(LifeForm lifeform){

        if (behaviourTreeNodeType == BehaviourTreeNodeType.CONDTION) {
            if (function == 0)
                return lifeform.isHungry();
            else if (function == 1)
                return lifeform.isThirsty();
            else if (function == 2)
                return lifeform.smellFood(lifeform.getSmellingDistance());
            else if (function == 3)
                return lifeform.seeWater(lifeform.getSeeingDistance());
            else if (function == 4)
                return lifeform.needsToGoToDen();
            else if (function == 5)
                return lifeform.hasEnergy();
        } else if (behaviourTreeNodeType == BehaviourTreeNodeType.ACTION) {

        } else if (behaviourTreeNodeType == BehaviourTreeNodeType.TERMINAL) {
            if (function == 0)
                lifeform.moveToFoodByPath();
            else if (function == 1)
                lifeform.moveToWaterByPath();
            else if (function == 2)
                lifeform.moveToDenByPath();
            else if (function == 3)
                lifeform.moveInRandomDirection();
        }

        return true;
    }

    public void mutate(){
        Random random = new Random();
        int r = random.nextInt(3);
        if (r == 0) {
            behaviourTreeNodeType = BehaviourTreeNodeType.CONDTION;
            function = random.nextInt(NUM_CONDITIONS);
        } else if (r == 1) {
          //  behaviourTreeNodeType = BehaviourTreeNodeType.ACTION;
            //function = random.nextInt(NUM_ACTIONS);
        } else if (r == 2) {
            behaviourTreeNodeType = BehaviourTreeNodeType.TERMINAL;
            function = random.nextInt(NUM_TERMINALS);
        }

        if (leftChild != null)
            if (leftChild.isReachable || random.nextInt(2) == 1)
                leftChild.mutate();

        if (rightChild != null)
            if (rightChild.isReachable || random.nextInt(2) == 1)
                rightChild.mutate();
    }

    public void markUnReachable(){
        isReachable = false;
        if(leftChild != null){
            leftChild.markUnReachable();
        }
        if(rightChild != null){
            rightChild.markUnReachable();
        }
    }

    public boolean isReachable(){
        return this.isReachable;
    }

    public BehaviourTreeNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BehaviourTreeNode leftChild) {
        this.leftChild = leftChild;
       //leftChild.parent = this;
    }

    public BehaviourTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BehaviourTreeNode rightChild) {
        this.rightChild = rightChild;
        //rightChild.parent = this;
    }

    public BehaviourTreeNode getParent() {
        return parent;
    }

    public void setParent(BehaviourTreeNode parent) {
        this.parent = parent;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public BehaviourTreeNodeType getBehaviourTreeNodeType() {
        return behaviourTreeNodeType;
    }

    public void setBehaviourTreeNodeType(BehaviourTreeNodeType behaviourTreeNodeType) {
        this.behaviourTreeNodeType = behaviourTreeNodeType;
    }

    public String getFunctionName() {

        if(!isReachable) return "NOT REACHABLE";

        if (behaviourTreeNodeType == BehaviourTreeNodeType.CONDTION) {
            if (function == 0)
                return "isHungry";
            else if (function == 1)
                return "isThirsty";
            else if (function == 2)
                return "canSmellFood";
            else if (function == 3)
                return "canSeeWater";
            else if (function == 4)
                return "needsToGoToDen";
            else if (function == 5)
                return "hasEnergy";
        } else if (behaviourTreeNodeType == BehaviourTreeNodeType.ACTION) {

        } else if (behaviourTreeNodeType == BehaviourTreeNodeType.TERMINAL) {
            if (function == 0)
                return "moveToFoodByPath";
            else if (function == 1)
                return "moveToWaterByPath";
            else if (function == 2)
                return "moveToDenByPath";
            else if (function == 3)
                return "moveToRandomSquare";
        }

        return "NULL";
    }
}
