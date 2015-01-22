package com.allen.george.geneticx.fitness;


import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.math.BigDecimal;

/**
 * Created by George Allen on 11/15/2014.
 */
public interface FitnessEvaluator {

    boolean isFitter(LifeForm a, LifeForm b);

    boolean isFitter(double a, double b);

}
