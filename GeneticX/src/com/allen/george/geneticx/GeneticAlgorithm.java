package com.allen.george.geneticx;

import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.geneticx.fitness.GenotypeFitnessComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by George on 29/11/2014.
 */
public abstract class GeneticAlgorithm {

    protected Configuration configuration;
    protected ArrayList<LifeForm> population;
    protected int populationSize;
    protected ArrayList<LifeForm> newPopulation;
    protected LifeForm fittestGenotype;
    protected LifeForm lastFittestGenotype;
    protected LifeForm secondFittestGenotype;
    protected LifeForm worstGenotype;
    protected double sumFitness;
    protected double bestFitness;
    protected int totalFitness;

    public abstract void run(ArrayList<LifeForm> lifeForms);

    public abstract ArrayList<LifeForm> crossover(LifeForm father, LifeForm mother);

    public abstract void mutate();

    protected void sortByFitness(ArrayList<LifeForm> genotypes){
        Collections.sort(genotypes, new GenotypeFitnessComparator(configuration.getFitnessEvaluator()));

    }

    protected void sortPopulation(){
        sortByFitness(population);
        fittestGenotype = population.get(0);
        secondFittestGenotype = population.get(1);
        worstGenotype = population.get(population.size() - 1);
    }

    protected void sortNewPopulation(){
       sortByFitness(newPopulation);
    }

    protected void calculateFitnessAll(){
        for(LifeForm lf : population){
            lf.calculateFitnessValue(configuration.getFitnessFunction());
            totalFitness += lf.getFitness().doubleValue();
        }
    }

    protected void calculateFitnessAll(ArrayList<LifeForm> subjects){
        for(LifeForm lf : subjects){
            lf.calculateFitnessValue(configuration.getFitnessFunction());
            totalFitness += lf.getFitness().doubleValue();
        }
    }

    protected void normalizeFitnessOfPopulation(ArrayList<LifeForm> subjects){
        double minFitness = 0.0;
        sumFitness = 0.0;

        for(int i = 0; i < subjects.size(); i ++){
            if(subjects.get(i).getFitness().doubleValue() < minFitness){
                minFitness = subjects.get(i).getFitness().doubleValue();
            }
            if(subjects.get(i).getFitness().doubleValue() > bestFitness){
                bestFitness = subjects.get(i).getFitness().doubleValue();
            }

        }
        for(int i = 0; i < subjects.size(); i ++){
            subjects.get(i).setScaledFitness(subjects.get(i).getFitness().doubleValue() - minFitness);
        }

        for(int i = 0; i < subjects.size(); i ++){
            sumFitness += subjects.get(i).getScaledFitness();
        }
    }

    protected void normalizeFitnessOfPopulation(){
        double minFitness = 0.0;
        sumFitness = 0.0;

        for(int i = 0; i < population.size(); i ++){
            if(population.get(i).getFitness().doubleValue() < minFitness){
                minFitness = population.get(i).getFitness().doubleValue();
            }
            if(population.get(i).getFitness().doubleValue() > bestFitness){
                bestFitness = population.get(i).getFitness().doubleValue();
            }

        }
        for(int i = 0; i < population.size(); i ++){
            population.get(i).setScaledFitness(population.get(i).getFitness().doubleValue() - minFitness);
        }

        for(int i = 0; i < population.size(); i ++){
            sumFitness += population.get(i).getScaledFitness();
        }
    }

    public double getAverageFitness(){
        double averageTotal = 0;
        for(int i = 0; i < population.size(); i ++){
            averageTotal += population.get(i).getFitness().doubleValue();
        }

        return averageTotal / population.size();
    }

    public LifeForm getFittesteGenotype(){
        this.sortPopulation();
        return fittestGenotype;
    }

    public LifeForm getWorstGenotype(){
        this.sortPopulation();
        return worstGenotype;
    }

    public void cleanUp(){
        this.population = null;
        this.newPopulation = null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ArrayList<LifeForm> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<LifeForm> population) {
        this.population = population;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public ArrayList<LifeForm> getNewPopulation() {
        return newPopulation;
    }

    public void setNewPopulation(ArrayList<LifeForm> newPopulation) {
        this.newPopulation = newPopulation;
    }
}
