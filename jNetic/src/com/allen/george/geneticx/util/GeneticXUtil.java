package com.allen.george.geneticx.util;


import com.allen.george.geneticx.Gene;


/**
 * Created by George Allen on 11/15/2014.
 */
public class GeneticXUtil {

    public static Gene[] generateRandomGenesFromPossibleGenes(Gene[] possibleGenes, int length){
        Gene[] result = new Gene[length];

        for(int i = 0; i < length; i ++){
            int random = (int)(Math.random() * ((possibleGenes.length - 1) + 1));
            result[i] = possibleGenes[random];
        }
        return result;
    }



}
