package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;

/**
 * Created by George on 02/12/2014.
 */
public class TestMutliple extends MutlipleObjectiveFitnessFunction {

    private FitnessFunction foodFitness;
    private FitnessFunction sleepFitness;
    private FitnessFunction randomFitness;
    private FitnessFunction waterFitness;

    public TestMutliple(FitnessFunction[] fitnessFunctions){
        super(fitnessFunctions);

        for(FitnessFunction fitnessFunction : fitnessFunctions){
            if(fitnessFunction instanceof FoodTestFitness){
                foodFitness = fitnessFunction;
            } else if(fitnessFunction instanceof SleepTestFitness){
                sleepFitness = fitnessFunction;
            } else if(fitnessFunction instanceof RandomTestFitness){
                randomFitness = fitnessFunction;
            } else if(fitnessFunction instanceof WaterTestFitness){
                waterFitness = fitnessFunction;
            }
        }
    }

    @Override
    public double evaluate(LifeForm subject) {
        //return (foodFitness.evaluate(subject) + waterFitness.evaluate(subject) + sleepFitness.evaluate(subject)) / (randomFitness.evaluate(subject) + 1);
        return (foodFitness.evaluate(subject) + waterFitness.evaluate(subject) + sleepFitness.evaluate(subject));
    }
}
