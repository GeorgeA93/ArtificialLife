package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 01/12/2014.
 */
public class ConditionNode extends Node {

    public ConditionNode(int function){
        this.setFunction(function);
        this.setNodeType(NodeType.CONDTION);
        this.setNumberOfChildren(2);
    }

    @Override
    public boolean runFunction(LifeForm lifeForm) {

        /*
        int val = getFunction();
        if (val < NUM_CONDITIONS){
            if(val == 0){
                return lifeForm.isHungry();
            }  else if(val == 1){
                return lifeForm.smellFood(lifeForm.getSmellingDistance());
            } else if(val == 2){
                return lifeForm.needsToGoToDen();
            } else if(val == 3){
                return lifeForm.hasEnergy();
            } else if(val == 4){
                return lifeForm.isThirsty();
            } else if(val == 5){
                return lifeForm.seeWater(lifeForm.getSeeingDistance());
            }
        }

        */
        int val = getFunction();
        if(val  < NUM_CONDITIONS){
            if(val == 0){
                return lifeForm.seeWater(lifeForm.getSeeingDistance());
            }  else if(val == 1){
                return lifeForm.smellFood(lifeForm.getSmellingDistance());
            } else if(val == 2){
                return lifeForm.canFindDen(lifeForm.getSeeingDistance());
            } else if(val == 3){
                lifeForm.TRIED_TO_WALK_ABOUT += 1;
                return true; //Walkabout question
            }
        }

        return false;
    }

    @Override
    public Node copy() {
        Node copy = new ConditionNode(getFunction());

        if(getLeftChild() != null){
            copy.setLeftChild(this.getLeftChild().copy());
        } else {
            copy.setLeftChild(null);
        }
        if(getRightChild() != null){
            copy.setRightChild(this.getRightChild().copy());
        } else {
            copy.setRightChild(null);
        }



        return copy;
    }



}
