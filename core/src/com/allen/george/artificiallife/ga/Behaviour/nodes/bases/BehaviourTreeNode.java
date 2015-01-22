package com.allen.george.artificiallife.ga.Behaviour.nodes.bases;



import com.allen.george.artificiallife.ga.Behaviour.nodes.conditions.*;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveRandomDirectionNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToDenByPathNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToFoodByPathNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.terminals.moveToWaterByPathNode;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Gene;

import java.util.Random;

/**
 * Created by George on 07/10/2014.
 */
public abstract class BehaviourTreeNode {

    public static int NUM_CONDITIONS = 6;
    public static int NUM_ACTIONS = 0;
    public static int NUM_TERMINALS = 4;

    private BehaviourTreeNode leftChild;
    private BehaviourTreeNode rightChild;
    private BehaviourTreeNode parent;

    private int function;

    private BehaviourTreeNodeType behaviourTreeNodeType;

    private boolean isReachable = true;

    private int depth;



    public BehaviourTreeNode(){

    }

    public BehaviourTreeNode(BehaviourTreeNodeType behaviourTreeNodeType, int function){
        this.behaviourTreeNodeType = behaviourTreeNodeType;
        this.function = function;
    }

    public static BehaviourTreeNode createFromGene(Gene g){
        int val = g.getAllele();
        if (val < NUM_CONDITIONS){
            if(val == 0){
                return new isHungryNode();
            } else if(val == 1){
                return new isThirstyNode();
            } else if(val == 2){
                return new canSmellFoodNode();
            } else if(val == 3){
                return new canSeeWaterNode();
            }else if(val == 4){
                return new needsToGoToDenNode();
            } else if(val == 5){
                return new hasEnergyNode();
            }
        }
           // return new BehaviourTreeNode(BehaviourTreeNodeType.CONDTION, val);


        if (val < NUM_TERMINALS + NUM_ACTIONS + NUM_CONDITIONS){
            val = val - NUM_CONDITIONS;
            if(val == 0){
                return new moveToFoodByPathNode();
            } else if(val == 1){
                return new moveToWaterByPathNode();
            } else if(val == 2){
                return new moveToDenByPathNode();
            } else if(val == 3){
                return new moveRandomDirectionNode();
            }
        }

            //return new BehaviourTreeNode(BehaviourTreeNodeType.TERMINAL, val - NUM_CONDITIONS - NUM_ACTIONS);

        return null;
    }

    public abstract boolean runFunction(LifeForm lifeForm);


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
       leftChild.parent = this;
    }

    public BehaviourTreeNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BehaviourTreeNode rightChild) {
        this.rightChild = rightChild;
        rightChild.parent = this;
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

    public void setDepth(int depth){
        this.depth = depth;
    }

    public int getDepth(){
        return this.depth;
    }
}
