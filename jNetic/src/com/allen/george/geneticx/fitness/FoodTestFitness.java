package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 01/12/2014.
 */
public class FoodTestFitness extends FitnessFunction {


    @Override
    public double evaluate(LifeForm subject) {

        double res = subject.getNumberOfFoodEatenWhenHungry() / (subject.getNumberOfFoodEaten() + 1);

        return res;
    }
}
