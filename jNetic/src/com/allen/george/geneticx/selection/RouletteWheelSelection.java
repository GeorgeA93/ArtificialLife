package com.allen.george.geneticx.selection;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George Allen on 11/16/2014.
 */
public class RouletteWheelSelection extends ParentSelector {


    int totalFitness;

    public RouletteWheelSelection(){

    }

    public LifeForm selectParent(ArrayList<LifeForm> population){
        /*

        double randNum = random.nextDouble() * totalFitness;
        int idx;
        for (idx=0; idx<population.size() && randNum>0; ++idx) {
            randNum -= population.get(idx).getFitness();
        }
        return population.get(idx - 1);

        */

        int i;
        double aux = 0;
        double limit = random.nextDouble() * totalFitness;

        for(i = 0; i < population.size() & aux < limit; i ++){
            aux += population.get(i).getFitness().doubleValue();
        }

        return population.get(i == 0 ? i : i - 1);
    }


    public void selectParents(ArrayList<LifeForm> genotypes){
        for(int i = 0; i < genotypes.size(); i ++){
            totalFitness += genotypes.get(i).getFitness().doubleValue();
        }

        ArrayList<LifeForm> population = new ArrayList<LifeForm>();

        for(int i = 0; i < genotypes.size(); i ++){
            population.add(genotypes.get(i));
        }

        parentsA = new LifeForm[genotypes.size() / 2];
        parentsB = new LifeForm[genotypes.size() / 2];


        for(int i = 0; i < parentsA.length; i ++){
            LifeForm parent = selectParent(population);
            population.remove(parent);
            parentsA[i] = parent;
            LifeForm parent2 = selectParent(population);
            population.remove(parent2);
            parentsB[i] = parent2;
        }



    }

}
