package com.allen.george.geneticx;

import com.allen.george.geneticx.crossover.CrossoverMethod;
import com.allen.george.geneticx.fitness.FitnessEvaluator;
import com.allen.george.geneticx.fitness.FitnessFunction;
import com.allen.george.geneticx.mutation.MutateMethod;
import com.allen.george.geneticx.selection.ParentSelector;

/**
 * Created by George Allen on 11/15/2014.
 */
public class Configuration {

    private int populationSize;
    private FitnessFunction fitnessFunction;
    private FitnessEvaluator fitnessEvaluator;
    private ParentSelector parentSelector;
    private CrossoverMethod crossoverMethod;
    private MutateMethod mutateMethod;
    private String name;
    private Gene[] possibleGenes;



    public Configuration(String name, int populationSize, FitnessFunction fitnessFunction, FitnessEvaluator fitnessEvaluator, ParentSelector parentSelector, CrossoverMethod crossoverMethod, MutateMethod mutateMethod, Gene[] possibleGenes){
        this.name = name;
        this.setPopulationSize(populationSize);
        this.fitnessFunction = fitnessFunction;
        this.fitnessEvaluator = fitnessEvaluator;
        this.parentSelector = parentSelector;
        this.crossoverMethod = crossoverMethod;
        this.mutateMethod = mutateMethod;
        this.possibleGenes = possibleGenes;
    }


    public void setPopulationSize(int populationSize){
        this.populationSize = populationSize;
    }

    public int getPopulationSize(){
        return this.populationSize;
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public FitnessEvaluator getFitnessEvaluator() {
        return fitnessEvaluator;
    }

    public void setFitnessEvaluator(FitnessEvaluator fitnessEvaluator) {
        this.fitnessEvaluator = fitnessEvaluator;
    }

    public ParentSelector getParentSelector() {
        return parentSelector;
    }

    public void setParentSelector(ParentSelector parentSelector) {
        this.parentSelector = parentSelector;
    }

    public CrossoverMethod getCrossoverMethod() {
        return crossoverMethod;
    }

    public void setCrossoverMethod(CrossoverMethod crossoverMethod) {
        this.crossoverMethod = crossoverMethod;
    }

    public MutateMethod getMutateMethod() {
        return mutateMethod;
    }

    public void setMutateMethod(MutateMethod mutateMethod) {
        this.mutateMethod = mutateMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gene[] getPossibleGenes() {
        return possibleGenes;
    }

    public void setPossibleGenes(Gene[] possibleGenes) {
        this.possibleGenes = possibleGenes;
    }

    public void print(){
        System.out.println("Configuration: " + name);
        System.out.println("Population of: " + populationSize);
        System.out.println("Using Fitness Function: " + fitnessFunction.getClass());
        System.out.println("Using Fitness Evaluator: " + fitnessEvaluator.getClass());
        System.out.println("Using Parent Selector: " + parentSelector.getClass());
        System.out.println("Using Crossover Method: " + crossoverMethod.getClass());
        System.out.println("Using Mutate Method: " + mutateMethod.getClass());
    }
}
