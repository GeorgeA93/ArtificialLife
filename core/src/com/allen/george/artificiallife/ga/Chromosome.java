package com.allen.george.artificiallife.ga;

/**
 * Created by George on 09/10/2014.
 */
public class Chromosome {

    private Gene[] genes;

    public Chromosome(Gene[] genes){
        this.genes = genes;
    }

    public Gene[] getGenes(){
        return this.genes;
    }

    public void setGenes(Gene[] genes){
        this.genes = genes;
    }

    public Gene getGeneAtIndex(int index){
        return genes[index];
    }

    public int size(){
        return genes.length;
    }



}
