package com.allen.george.geneticx.mutation;

import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Configuration;

import java.util.ArrayList;

/**
 * Created by George on 17/02/2015.
 */
public class TreeMutateMethod extends MutateMethod{

    public LifeForm mutate(LifeForm a, Configuration configuration){

        ArrayList<Node> nodes = a.getTree().getNodes();

        int index = random.nextInt(nodes.size());

        a.getTree().getNodes().set(index, nodes.get(index).mutate(random));

        return a;
    }



}
