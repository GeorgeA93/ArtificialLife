package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.fitness.FitnessEvaluator;

import java.math.BigDecimal;

/**
 * Created by George Allen on 11/15/2014.
 */
public class NormalFitnessEvaluator implements FitnessEvaluator {

    public boolean isFitter(LifeForm a, LifeForm b){
        return isFitter(a.getFitness().doubleValue(), b.getFitness().doubleValue());
    }

    public boolean isFitter(double a, double b){
        return a > b;
    }

}
