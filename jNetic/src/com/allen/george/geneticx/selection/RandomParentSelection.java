package com.allen.george.geneticx.selection;


import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;

/**
 * Created by George on 20/11/2014.
 */
public class RandomParentSelection extends ParentSelector {

    @Override
    public LifeForm selectParent(ArrayList<LifeForm> population) {
        return null;
    }

    public void selectParents(ArrayList<LifeForm> genotypes) {


        parentsA = new LifeForm[genotypes.size() / 2];
        parentsB = new LifeForm[genotypes.size() / 2];

        for(int i = 0; i < genotypes.size() / 2; i ++){
            parentsA[i] = genotypes.get(i);
        }

        int index  = 0;
        for(int i =  genotypes.size() / 2; i < genotypes.size(); i ++){

            parentsB[index] = genotypes.get(i);
            index ++;
        }

    }

}
