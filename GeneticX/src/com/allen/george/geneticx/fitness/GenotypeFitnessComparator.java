package com.allen.george.geneticx.fitness;


import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.Comparator;

/**
 * Created by George Allen on 11/16/2014.
 */
public class GenotypeFitnessComparator implements Comparator {

    private FitnessEvaluator fitnessEvaluator;

    public GenotypeFitnessComparator(){
        this(new NormalFitnessEvaluator());
    }

    public GenotypeFitnessComparator(FitnessEvaluator fitnessEvaluator){
        if(fitnessEvaluator == null){
            System.err.println("Fitness Evaluator must not be null. In " + this.getClass());
            System.exit(1);
        }
        this.fitnessEvaluator = fitnessEvaluator;
    }

    public int compare(final Object a, final Object b){
        LifeForm genotypeOne = (LifeForm)a;
        LifeForm genotypeTwo = (LifeForm)b;

        if(fitnessEvaluator.isFitter(genotypeOne, genotypeTwo)){
            return -1;
        } else if(fitnessEvaluator.isFitter(genotypeTwo, genotypeOne)){
            return 1;
        } else {
            return 0;
        }
    }

}
