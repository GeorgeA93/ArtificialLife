package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 02/12/2014.
 */
public abstract class MutlipleObjectiveFitnessFunction extends FitnessFunction {

    FitnessFunction[] fitnessFunctions;

    public MutlipleObjectiveFitnessFunction(){

    }

    public MutlipleObjectiveFitnessFunction(FitnessFunction[] fitnessFunctions){
        this.fitnessFunctions = fitnessFunctions;
    }


    @Override
    public abstract double evaluate(LifeForm subject);
}
