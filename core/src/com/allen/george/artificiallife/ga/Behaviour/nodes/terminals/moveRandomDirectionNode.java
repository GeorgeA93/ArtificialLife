package com.allen.george.artificiallife.ga.Behaviour.nodes.terminals;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 07/11/2014.
 */
public class moveRandomDirectionNode extends BehaviourTreeNode {

    private boolean complete = false;

    public moveRandomDirectionNode(){
        super(BehaviourTreeNodeType.TERMINAL, 3);
    }

    public boolean runFunction(LifeForm lifeForm){
        complete = false;

        while(!complete){
            complete =  lifeForm.moveInRandomDirection(false);
        }

        return true;
    }



}
