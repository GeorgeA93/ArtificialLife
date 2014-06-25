package com.allen.george.artificiallife.utils;

/**
 * Created by George on 25/06/2014.
 */
public class MathHelper {

    public static int sign(double f) {
        if (f != f) throw new IllegalArgumentException("NaN");
        if (f == 0) return 0;
        f *= Double.POSITIVE_INFINITY;
        if (f == Double.POSITIVE_INFINITY) return +1;
        if (f == Double.NEGATIVE_INFINITY) return -1;

        //this should never be reached, but I've been wrong before...
        throw new IllegalArgumentException("Unfathomed double");
    }

}
