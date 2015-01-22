package com.allen.george.geneticx.selection;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by George on 29/11/2014.
 */
public class TournamentParentSelection extends ParentSelector {

    public LifeForm selectParent(ArrayList<LifeForm> population, int tournamentSize){
        LifeForm[] tournament = new LifeForm[tournamentSize];
        double[] tournamentFitness = new double[tournamentSize];
        for(int i = 0; i < tournamentSize; i++){
            int index = (int) (Math.random() * population.size());
            tournament[i] = population.get(index);
            tournamentFitness[i] = population.get(index).getFitness().doubleValue();
        }

        LifeForm best = tournament[0];
        double bestFitness = tournamentFitness[0];
        for(int i = 1; i < tournamentSize; i ++){
            if(tournamentFitness[i] > bestFitness){
                best = tournament[i];
            }
        }

        return best;
    }


    public void selectParents(ArrayList<LifeForm> genotypes){

        ArrayList<LifeForm> population = new ArrayList<LifeForm>();

        for(int i = 0; i < genotypes.size(); i ++){
            population.add(genotypes.get(i));
        }

        parentsA = new LifeForm[genotypes.size() / 2];
        parentsB = new LifeForm[genotypes.size() / 2];

        int tournamentSize = 4;

        for(int i = 0; i < parentsA.length; i ++) {
            Collections.shuffle(population);
            LifeForm parent = selectParent(population, tournamentSize);
            population.remove(parent);
            parentsA[i] = parent;
            Collections.shuffle(population);
            LifeForm parent2 = selectParent(population, tournamentSize);
            population.remove(parent2);
            parentsB[i] = parent2;
        }

    }


}
