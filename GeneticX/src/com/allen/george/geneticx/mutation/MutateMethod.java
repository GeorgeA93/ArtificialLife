package com.allen.george.geneticx.mutation;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.Configuration;

import java.util.Random;

/**
 * Created by George Allen on 11/16/2014.
 */
public abstract class MutateMethod {

    protected Random random = new Random();

    public abstract LifeForm mutate(LifeForm a, Configuration configuration);

}
