package com.allen.george.artificiallife.ga.Behaviour.nodes.conditions;

import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNode;
import com.allen.george.artificiallife.ga.Behaviour.nodes.bases.BehaviourTreeNodeType;
import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 07/11/2014.
 */
public class canSeeWaterNode extends BehaviourTreeNode {

    public canSeeWaterNode(){
        super(BehaviourTreeNodeType.CONDTION, 3);

    }

    public boolean runFunction(LifeForm lifeForm){
        return lifeForm.seeWater(lifeForm.getSeeingDistance());
    }



}
