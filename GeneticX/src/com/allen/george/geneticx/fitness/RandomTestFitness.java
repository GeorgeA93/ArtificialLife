package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 02/12/2014.
 */
public class RandomTestFitness extends FitnessFunction {

    @Override
    public double evaluate(LifeForm subject) {

        double res = subject.getNumberOfRandomStepsWhenHungry() + subject.getNumberOfRandomStepsWhenTired() +subject.getNumberOfRandomStepsWhenThirsty(); //steps when thirsty

        return 1;
    }

}
