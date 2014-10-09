package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;

/**
 * Created by George on 09/10/2014.
 */
public class Genotype {

    private Chromosome chromosomes;
    private LifeForm lifeForm;

    public Genotype(Chromosome initialChromosomes, LifeForm lifeForm){
        this.chromosomes = initialChromosomes;
        this.lifeForm = lifeForm;
    }

    public Chromosome getChromosomes(){
        return this.chromosomes;
    }


    public void evolve(){

    }

    public void print(){
        /*
        for(int i = 0; i < population.getChromosomes().size(); i ++){
            for(int j = 0; j < population.getChromosomes().get(i).getGenes().length; j ++){
                System.out.println(population.getChromosomes().get(i).getGenes()[j].getAllele());
            }
            System.out.println("");
        }
        */

        for(int i = 0; i < chromosomes.getGenes().length; i ++){
            System.out.println(chromosomes.getGenes()[i].getAllele());
        }


    }

}
