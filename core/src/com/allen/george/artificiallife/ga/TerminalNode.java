package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 01/12/2014.
 */
public class TerminalNode extends Node {

    private boolean complete;

    public TerminalNode(int function){
        this.setFunction(function);
        this.setBehaviourTreeNodeType(BehaviourTreeNodeType.TERMINAL);
        this.setNumberOfChildren(0);
    }

    @Override
    public boolean runFunction(LifeForm lifeForm) {

        int val = getFunction();
        if(val == 0){
            complete = false;
            while(!complete){
                complete =  lifeForm.moveToFoodByPath();
            }
            return true;
        } else if(val == 1){
            complete = false;
            while(!complete){
                complete =  lifeForm.moveToDenByPath();
            }
            return true;
        } else if(val == 2){
            complete = false;
            while(!complete){
                complete =  lifeForm.moveInRandomDirection(false);
            }
            return true;
        } else if(val == 3){
            complete = false;
            while(!complete){
                complete =  lifeForm.moveToWaterByPath();
            }
            return true;
        }

        return false;
    }

    @Override
    public Node copy() {
        Node copy = new TerminalNode(getFunction());
        copy.setLeftChild(null);
        copy.setRightChild(null);

        return copy;
    }

    @Override
    public Node getClone() {
        Node clone = new TerminalNode(getFunction());

        clone.setRightChild(getRightChild());
        clone.setLeftChild(getLeftChild());

        return clone;
    }
}
