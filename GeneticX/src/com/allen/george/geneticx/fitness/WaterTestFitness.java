package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 27/12/2014.
 */
public class WaterTestFitness extends FitnessFunction {

    @Override
    public double evaluate(LifeForm subject) {

        double res = subject.getNumberOfWaterDrunkWhenThirsty() / (subject.getNumberOfWaterDrunk() + 1);

        return res;
    }
}
