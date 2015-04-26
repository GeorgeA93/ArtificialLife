package com.allen.george.geneticx;

/**
 * Created by George Allen on 11/14/2014.
 */
public class Gene {

    private int allele;

    public Gene(int allele){
        this.allele = allele;
    }

    public int getAllele(){
        return this.allele;
    }

    public void setAllele(int allele){
        this.allele = allele;
    }

    public void SetAlleleRandom(int min, int max){
        this.allele = min + (int)(Math.random() * ((max - min) + 1));
    }

}
