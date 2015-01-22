package com.allen.george.geneticx.selection;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George Allen on 11/16/2014.
 */
public abstract class ParentSelector {

    protected Random random = new Random();

    protected LifeForm[] parentsA;
    protected LifeForm[] parentsB;

    public abstract void selectParents(ArrayList<LifeForm> genotypes);

    public LifeForm[] getParentsA() {
        return parentsA;
    }

    public LifeForm[] getParentsB() {
        return parentsB;
    }
}
