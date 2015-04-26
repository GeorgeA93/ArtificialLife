package com.allen.george.geneticx.crossover;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;

/**
 * Created by George Allen on 11/16/2014.
 */
public abstract class CrossoverMethod {

    public abstract ArrayList<LifeForm> crossOverParents(LifeForm a, LifeForm b);

}
