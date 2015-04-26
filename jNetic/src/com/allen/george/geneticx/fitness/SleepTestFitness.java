package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 02/12/2014.
 */
public class SleepTestFitness extends FitnessFunction {

    @Override
    public double evaluate(LifeForm subject) {

        double res = subject.getNumberOfSleepsWhenTired() / (subject.getNumberOfSleeps() + 1);

        return res;
    }

}
