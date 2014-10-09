package com.allen.george.artificiallife.ga;

import java.util.Random;

/**
 * Created by George on 09/10/2014.
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

    public void setRandomValue(int min, int max){
        this.allele = min + (int)(Math.random() * ((max - min) + 1));
    }

}
