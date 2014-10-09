package com.allen.george.artificiallife.simulation.world.map.objects;

import java.util.Comparator;

/**
 * Created by George Allen on 10/3/2014.
 */
public class DistanceComparator implements Comparator<ALSObject> {

    @Override
    public int compare(ALSObject o1, ALSObject o2) {
        return (o1.distance < o2.distance ? -1 : (o1.distance==o2.distance ? 0 : 1));
    }
}
