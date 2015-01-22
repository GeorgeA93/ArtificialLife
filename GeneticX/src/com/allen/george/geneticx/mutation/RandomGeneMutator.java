package com.allen.george.geneticx.mutation;

import com.allen.george.artificiallife.ga.GAUtil;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Chromosome;
import com.allen.george.geneticx.Configuration;
import com.allen.george.geneticx.Gene;


/**
 * Created by George Allen on 11/16/2014.
 */
public class RandomGeneMutator extends MutateMethod {

    public LifeForm mutate(LifeForm a, Configuration configuration){
        Gene[] genes = a.getChromosome().getGenes();
        Gene[] possibleGenes = configuration.getPossibleGenes();

        int mutationAmount = 3;

        for(int i = 0; i < mutationAmount; i ++){
            int index = this.random.nextInt(genes.length - 1);
            int newAllele = this.random.nextInt(possibleGenes.length - 1);
            genes[index] = possibleGenes[newAllele];
        }

        a.setChromosome(new Chromosome(genes));
        a.setTest(Tree.generateTreeFromGenes(genes, a));

       // a.setTree(GAUtil.generateTreeFromGenes(genes, a));

        return a;
    }


}
