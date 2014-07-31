package com.allen.george.artificiallife.simulation.world.map.objects.comparators;

import com.allen.george.artificiallife.simulation.world.map.objects.food.Food;

import java.util.Comparator;

/**
 * Created by George on 31/07/2014.
 */
public class FoodComparator implements Comparator<Food> {

    public int compare(Food a, Food b){
        return a.position.x < b.position.x && a.position.y < b.position.y ? 1 : 0;
    }

}
