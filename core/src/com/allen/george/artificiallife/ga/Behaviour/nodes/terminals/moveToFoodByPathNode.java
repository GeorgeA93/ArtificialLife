package com.allen.george.artificiallife.ga.Behaviour.nodes.terminals;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 07/11/2014.
 */
public class moveToFoodByPathNode extends BehaviourTreeNode {

    private boolean complete;

    public moveToFoodByPathNode(){
        super(BehaviourTreeNodeType.TERMINAL, 0);
    }

    public boolean runFunction(LifeForm lifeForm){
        complete = false;

        while(!complete){
            complete =  lifeForm.moveToFoodByPath();
        }

        return true;
    }

}
