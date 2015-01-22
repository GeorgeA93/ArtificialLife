package com.allen.george.artificiallife.ga.Behaviour.nodes.conditions;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 07/11/2014.
 */
public class isHungryNode extends BehaviourTreeNode {


    public isHungryNode(){
        super(BehaviourTreeNodeType.CONDTION, 0);

    }


    public boolean runFunction(LifeForm lifeForm){
        return lifeForm.isHungry();
    }

}
