package com.allen.george.geneticx.fitness;

import com.allen.george.artificiallife.ga.ConditionNode;
import com.allen.george.artificiallife.ga.GAUtil;
import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by George Allen on 11/15/2014.
 */
public class TestFitnessFunction extends FitnessFunction {

    @Override
    public double evaluate(LifeForm subject) {

        /*

        double x = subject.TRIED_TO_DRINK;
        double y = subject.TRIED_TO_EAT;
        double z = subject.TRIED_TO_SLEEP;

        double penalty = y + z - ((3/4) * x);
        penalty = Math.abs(penalty);

        double actualScore = subject.WATER_DRUNK + subject.FOOD_EATEN + subject.SLEEPS_TAKEN;

        double fitness = actualScore - penalty;

        return fitness;

        */

/*
        double waterShouldBe = subject.TRIED_TO_DRINK;
        double foodShouldBe = waterShouldBe / 2;
        double sleepShouldBe = foodShouldBe / 2;
        double ratioShouldbe = waterShouldBe + foodShouldBe + sleepShouldBe + subject.TRIED_TO_WALK_ABOUT;
        double difference = (subject.TRIED_TO_DRINK + subject.TRIED_TO_EAT + subject.TRIED_TO_SLEEP) - ratioShouldbe;
        difference = Math.abs(difference);
        double actualScore = subject.WATER_DRUNK + subject.FOOD_EATEN + subject.SLEEPS_TAKEN;

        double fitness = actualScore - difference;

        return fitness;

        */

        double drinks = 0;
        double eats = 0;
        double sleeps = 0;
        double walkabouts = 0;

        ArrayList<Node> nodes = subject.getTest().getNodes();
        for(Node node : nodes){
            if(node instanceof ConditionNode){
                if(node.getFunction() == 0){
                    drinks += 1;
                } else if(node.getFunction() == 1){
                    eats += 1;
                } else if(node.getFunction() == 2){
                    sleeps += 1;
                } else if(node.getFunction() == 3){
                    walkabouts += 1;
                }
            }
        }

        subject.EVENTS_PER_CYCLE = subject.getTest().getDepth();
        subject.EVENTS_PER_GENERATION = subject.EVENTS_PER_CYCLE * subject.CYCLES;

        double realRatioDrinks = drinks / subject.EVENTS_PER_CYCLE;
        double realRatioEats = eats / subject.EVENTS_PER_CYCLE;
        double realRatioSleeps = sleeps / subject.EVENTS_PER_CYCLE;
        double realAgainstPerfectDrinks = realRatioDrinks * 7;
        double realAgainstPerfectEats = realRatioEats * 7;
        double realAgainstPerfectSleeps = realRatioSleeps * 7;
        double differenceAgainstPerfectDrinks = 4 - realAgainstPerfectDrinks;
        double differenceAgainstPerfectEats = 2 - realAgainstPerfectEats;
        double differenceAgainstPerfectSleeps = 1 - realAgainstPerfectSleeps;

        double dPercentDrinks = 0;
        double dPercentEats = 0;
        double dPercentSleeps = 0;

        if(differenceAgainstPerfectDrinks == 0){
            dPercentDrinks = 0;
        } else if(differenceAgainstPerfectDrinks == 4){
            dPercentDrinks = 100;
        } else {
            dPercentDrinks = differenceAgainstPerfectDrinks / realAgainstPerfectDrinks * 100;
        }

        if(differenceAgainstPerfectEats == 0){
            dPercentEats = 0;
        } else if(differenceAgainstPerfectEats == 2){
            dPercentEats = 100;
        } else {
            dPercentEats = differenceAgainstPerfectEats / realAgainstPerfectEats * 100;
        }

        if(differenceAgainstPerfectSleeps == 0){
            dPercentSleeps = 0;
        } else if(differenceAgainstPerfectSleeps == 1){
            dPercentSleeps = 100;
        } else {
            dPercentSleeps = differenceAgainstPerfectSleeps / realAgainstPerfectSleeps * 100;
        }

        double drinkPenalty = subject.WATER_DRUNK * (dPercentDrinks / 100);
        double eatsPenalty = subject.FOOD_EATEN * (dPercentEats / 100);
        double sleepsPenalty = subject.SLEEPS_TAKEN * (dPercentSleeps / 100);

        drinkPenalty = Math.abs(drinkPenalty);
        eatsPenalty = Math.abs(eatsPenalty);
        sleepsPenalty = Math.abs(sleepsPenalty);

        double nodePenalty = (getWrongNodes(subject.getTest())  * 100);
        double totalPenalty = drinkPenalty + eatsPenalty + sleepsPenalty + nodePenalty;
        double score = subject.WATER_DRUNK + subject.FOOD_EATEN + subject.SLEEPS_TAKEN;
        double fitnessWithoutAdjustment = score - totalPenalty;



        if(nodePenalty > 0){
            System.out.println(nodePenalty);
            return totalPenalty * -1;
        }

        int minimumPerfectSetPerCycle = subject.EVENTS_PER_CYCLE / 7;
        double mpsAdjustment = 0;
        if(minimumPerfectSetPerCycle == 0){
            mpsAdjustment = subject.EVENTS_PER_CYCLE / 7;
        } else {
            mpsAdjustment = minimumPerfectSetPerCycle;
        }
        double adjustmentValue = mpsAdjustment * 7 / subject.EVENTS_PER_GENERATION;



        double fitnessWithAdjustment = fitnessWithoutAdjustment * adjustmentValue;


        System.out.println();
        System.out.println("Drinks: " + drinks + ", Eats: " + eats + ", Sleeps: " + sleeps);
        System.out.println("Events Per Cycle: " + subject.EVENTS_PER_CYCLE + ", Cycles: " + subject.CYCLES  + ", Events Per Generation: " + subject.EVENTS_PER_GENERATION);
        System.out.println("Drink Score: " + subject.WATER_DRUNK + ", Eat Score: " + subject.FOOD_EATEN + ", Sleep Score: " + subject.SLEEPS_TAKEN + ", TOTAL: " + score);
        System.out.println("PSA:: MPS: " + minimumPerfectSetPerCycle + ", MPSA: " + mpsAdjustment + ", AdjustmentValue: " + adjustmentValue);
        System.out.println("Real Drink Ratio: " + realRatioDrinks + ", Real Eat Ratio: " + realRatioEats + ", Real Sleep Ratio: " + realRatioSleeps);
        System.out.println("Real Drink Ratio Against Perfect: " + realAgainstPerfectDrinks + ", Real Eat Ratio Against Perfect: " + realAgainstPerfectEats + ", Real Sleep Ratio Against Perfect: " + realAgainstPerfectSleeps);
        System.out.println("Real Drink Ratio Difference: " + differenceAgainstPerfectDrinks + ", Real Eat Ratio Difference: " + differenceAgainstPerfectEats + ", Real Sleep Ratio Difference: " + differenceAgainstPerfectSleeps);
        System.out.println("Difference Percent: " + dPercentDrinks + ", Difference Percent: " + dPercentEats + ",Difference Percent: " + dPercentSleeps);
        System.out.println("Drink Penalty: " + drinkPenalty + ", Eats Penalty: " + eatsPenalty + ",Sleeps Penalty: " + sleepsPenalty + ", Node Penaly: " + nodePenalty + ", TOTAL: " + totalPenalty);
        System.out.println("Score Without Adjustment: " + fitnessWithoutAdjustment);
        System.out.println("Score WITH Adjustment: " + fitnessWithAdjustment);
        System.out.println();


        return fitnessWithAdjustment;


    }

    int wrongNodes = 0;
    private int getWrongNodes(Tree tree){
        wrongNodes = 0;
        count(tree.getRoot());
        return wrongNodes;
    }

    private void count(Node node){
        if(node instanceof ConditionNode){
            if(node.getLeftChild() != null){
                if(node.getFunction() == 0){ //see water
                    if(node.getLeftChild().getFunction() != 3){
                        wrongNodes +=1;
                    }
                } else if(node.getFunction() == 1){ //smell food
                    if(node.getLeftChild().getFunction() != 0){
                        wrongNodes +=1;
                    }
                } else if(node.getFunction() == 2){  //see den
                    if(node.getLeftChild().getFunction() != 1){
                        wrongNodes +=1;
                    }
                } else if(node.getFunction() == 3){ //walk about
                    if(node.getLeftChild().getFunction() != 2){
                        wrongNodes +=1;
                    }
                }
            }
        }
        if(node.getRightChild() != null){
            count(node.getRightChild());
        }
    }
}
