package com.allen.george.geneticx.fitness;


import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George Allen on 11/15/2014.
 */
public abstract class FitnessFunction {

    private double lastComputedFitnessValue = -1.0000000d;

    public double getFitness(final LifeForm subject){
        double fitnessValue = evaluate(subject);
        if (fitnessValue < 0.00000000d) {
            throw new RuntimeException(
                    "Fitness values must be positive! Received value: "
                            + fitnessValue);
        }
        lastComputedFitnessValue = fitnessValue;
        return fitnessValue;
    }

    public double getLastComputedFitnessValue(){
        return this.lastComputedFitnessValue;
    }

    public abstract double evaluate(LifeForm subject);

}
