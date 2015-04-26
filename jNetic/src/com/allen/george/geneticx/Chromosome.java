package com.allen.george.geneticx;

/**
 * Created by George Allen on 11/14/2014.
 */
public class Chromosome {

    private Gene[] genes;

    public Chromosome(Chromosome chromosome){
        this.genes = chromosome.getGenes();
    }

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

    public void setGeneAlleleAtIndex(int index, int allele){
        genes[index].setAllele(allele);
    }

    public int size(){
        return genes.length;
    }


}
