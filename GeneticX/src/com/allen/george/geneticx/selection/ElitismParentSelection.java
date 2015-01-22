package com.allen.george.geneticx.selection;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 29/11/2014.
 */
public class ElitismParentSelection extends ParentSelector {



    public void selectParents(ArrayList<LifeForm> genotypes){
        parentsA = new LifeForm[genotypes.size() / 2];
        parentsB = new LifeForm[genotypes.size() / 2];

        int index = 0;
        for(int i = 0; i < genotypes.size(); i += 2){
            parentsA[index] = genotypes.get(i);
            index ++;
        }

        index = 0;
        for(int i = 1; i < genotypes.size(); i += 2){
            parentsB[index] = genotypes.get(i);
            index ++;
        }



    }

}
