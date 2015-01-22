package com.allen.george.artificiallife.ga;


import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.allen.george.geneticx.Configuration;
import com.allen.george.geneticx.GeneticAlgorithm;

import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by George on 29/11/2014.
 */
public class GA extends GeneticAlgorithm{

    private Random random = new Random();
    private GeneticEngine geneticEngine;


    public GA(Configuration configuration, int populationSize, GeneticEngine geneticEngine){
        this.configuration = configuration;
        this.populationSize = populationSize;
        this.newPopulation = new ArrayList<LifeForm>();
        this.population = new ArrayList<LifeForm>();
        this.geneticEngine = geneticEngine;
    }

    private void test(){
      //  selectParents();

      //  newPopulation = new ArrayList<LifeForm>();

      //  ArrayList<LifeForm> children = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB());

       // newPopulation.addAll(children);

      //  HolderForGATest.mutateTrees(newPopulation);
    }

    @Override
    public void run(ArrayList<LifeForm> lifeForms) {

        /*

        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();

        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

       // ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
      //  int elitism = (int) Math.floor((0.01 * population.size()));
      //  for(int i = 0; i < elitism; i ++){
      //      newPopulation.add(population.get(i));
     //       population.remove(i);
      //  }
        selectParents();

        ArrayList<LifeForm> children = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        newPopulation.addAll(children);

     //   HolderForGATest.mutateTrees(newPopulation);

      //  newPopulation.addAll(elite);
       // newPopulation = crossover(null, null);
       // mutate();
      //  treeMutate();

       // if(lastFittestGenotype != null){
           // newPopulation.add(lastFittestGenotype);
       // }

        population = newPopulation;
        sortPopulation();

        geneticEngine.setLifeForms(population);

       if(fittestGenotype.getTest().getDepth() < 15){
           GAUtil.printNode(fittestGenotype.getTest().getRoot());
       }


      // System.out.println(population.size());
*/



        /*
        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();

        geneticEngine.setLifeForms(population);

        */

        //moreAccurrateElitism(lifeForms);
       // moreAccurrateElitism(lifeForms);

        elitismHolder(lifeForms);

    }

    private void lesserElistismHolder(ArrayList<LifeForm> lifeForms){
        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();
        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

        //THE ELITE
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
        int elitism = (int) Math.floor((0.01 * population.size()));
        for(int i = 0; i < elitism; i ++){
            elite.add(population.get(i));
            population.remove(i);
        }

        //THE NEXT BEST (RIVALS)
        ArrayList<LifeForm> rivals = new ArrayList<LifeForm>();
        int rivalAmount = (int) Math.floor((0.24 * population.size()));
        //calculateFitnessAll();
        //sortPopulation();
        //normalizeFitnessOfPopulation();
        for(int i = 0; i < rivalAmount; i ++){
            rivals.add(population.get(i));
            population.remove(i);
        }
        selectParents(rivals);
        ArrayList<LifeForm> rivalsChildren = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);
        HolderForGATest.mutateTrees(rivalsChildren);


        //ADD WHAT WE HAVE SO FAR TO THE NEW POPULATION
        newPopulation.addAll(elite);
        newPopulation.addAll(rivalsChildren);


        //THE NEXT NEXT BEST (AVERAGE ISH)
        ArrayList<LifeForm> averageOnes = new ArrayList<LifeForm>();
        int avaerageAmount = (int) Math.floor((1 * population.size()));
        for(int i = 0; i < avaerageAmount; i ++){
            averageOnes.add(population.get(i));
        }
        selectParents(averageOnes);
        ArrayList<LifeForm> averageOnesChildren = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);
        HolderForGATest.mutateTrees(averageOnesChildren);

        newPopulation.addAll(averageOnesChildren);

        population = null;
        population = newPopulation;
        // calculateFitnessAll();
        sortPopulation();


        geneticEngine.setLifeForms(population);

        if(fittestGenotype.getTest().getDepth() < 15){
            // GAUtil.printNode(fittestGenotype.getTest().getRoot());
        }

        OutputStreamWriter out = new OutputStreamWriter(System.out);

        try{
            fittestGenotype.getTest().getRoot().printTree(out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(population.size());

    }

    private void elitismHolder(ArrayList<LifeForm> lifeForms){
        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();
        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

        //THE ELITE
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
        int elitism = (int) Math.floor((0.01 * population.size()));
        for(int i = 0; i < elitism; i ++){
            elite.add(population.get(i));
            population.remove(i);
        }

        //THE NEXT BEST (RIVALS)
        ArrayList<LifeForm> rivals = new ArrayList<LifeForm>();
        int rivalAmount = (int) Math.floor((0.24 * population.size()));
        for(int i = 0; i < rivalAmount; i ++){
            rivals.add(population.get(i));
            population.remove(i);
        }
        selectParents(rivals);
        ArrayList<LifeForm> rivalsChildren = new ArrayList<LifeForm>();
        for(int i = 0; i < rivals.size() / 2; i ++){
            rivalsChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }
       // ArrayList<LifeForm> rivalsChildren = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);
        HolderForGATest.mutateTrees(rivalsChildren);

        //THE NEXT NEXT BEST (AVERAGE ISH)
        ArrayList<LifeForm> averageOnes = new ArrayList<LifeForm>();
        int averageOnesAmount = (int) Math.floor((0.5 * population.size()));
        for(int i = 0; i < averageOnesAmount; i ++){
            averageOnes.add(population.get(i));
            population.remove(i);
        }
        selectParents(averageOnes);
        ArrayList<LifeForm> averageOnesChildren = new ArrayList<LifeForm>();
        for(int i = 0; i < averageOnes.size() / 2; i ++){
            averageOnesChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }
      //  ArrayList<LifeForm> averageOnesChildren = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);
        HolderForGATest.mutateTrees(averageOnesChildren);


        //ADD WHAT WE HAVE SO FAR TO THE NEW POPULATION
        newPopulation.addAll(elite);
        newPopulation.addAll(rivalsChildren);
        newPopulation.addAll(averageOnesChildren);

        //KILL OFF THE REMAINING ONES
        population = null;

        //CREATE NEW CHILDREN TO FILL THE POPULATION
        ArrayList<LifeForm> newParents = new ArrayList<LifeForm>();
        ArrayList<LifeForm> newChildren = new ArrayList<LifeForm>();
        int numberOfPopulationMissing = SimulationSettings.POPULATION_SIZE - newPopulation.size();
        ArrayList<LifeForm> possibleParents = new ArrayList<LifeForm>();
        possibleParents.addAll(elite);
        possibleParents.addAll(rivals);
        for(int i = 0; i < numberOfPopulationMissing; i ++){
            // LifeForm parent = new LifeForm(elite.get(random.nextInt(elite.size())));
             LifeForm parent = new LifeForm(possibleParents.get(random.nextInt(possibleParents.size())));
             newParents.add(parent);
        }

        selectParents(newParents);
        for(int i = 0; i < newParents.size() / 2; i ++){
            newChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }
        //newChildren = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);
        HolderForGATest.mutateTrees(newChildren);

        //ADD THE NEW CHILDREN TO THE NEW POPULATION
        newPopulation.addAll(newChildren);

        population = newPopulation;
        sortPopulation();


        geneticEngine.setLifeForms(population);

        OutputStreamWriter out = new OutputStreamWriter(System.out);

        try{
            fittestGenotype.getTest().getRoot().printTree(out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(population.size());

    }


    private void lessAccurrateElitism(ArrayList<LifeForm> lifeForms){
        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();

        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

        //ELITISM
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
        int elitism = (int) Math.floor((0.01 * population.size()));
        for(int i = 0; i < elitism; i ++){
              elite.add(population.get(i));
              population.remove(i);
        }

        //WATER AND FOOD FITNESS
        ArrayList<LifeForm> waterAndFood = new ArrayList<LifeForm>();
        int waterAndFoodAmount = (int) Math.floor((0.1 * population.size()));
        calculateWaterAndFoodFitnesslessAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndFoodAmount; i ++){
            waterAndFood.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndFood);
        ArrayList<LifeForm> childrenWaterAndFood = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

       //WATER AND SLEEP
        ArrayList<LifeForm> waterAndSleep = new ArrayList<LifeForm>();
        int waterAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        calculateWaterAndSleepFitnesslessAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndSleepAmount; i ++){
            waterAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndSleep);
        ArrayList<LifeForm> childrenWaterAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        //FOOD AND SLEEP
        ArrayList<LifeForm> foodAndSleep = new ArrayList<LifeForm>();
        int foodAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        calculateFoodAndSleepFitnesslessAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < foodAndSleepAmount; i ++){
            foodAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(foodAndSleep);
        ArrayList<LifeForm> childrenFoodAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        //REMAINING POPULATION
        sortPopulation();
        normalizeFitnessOfPopulation();
        selectParents();
        ArrayList<LifeForm> childrenRemaining = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);


        newPopulation.addAll(childrenWaterAndFood);
        newPopulation.addAll(childrenWaterAndSleep);
        newPopulation.addAll(childrenFoodAndSleep);
        newPopulation.addAll(childrenRemaining);

        //HolderForGATest.mutateTrees(newPopulation);

        newPopulation.addAll(elite);

        population = newPopulation;
        calculateFitnessAll();
        sortPopulation();

        geneticEngine.setLifeForms(population);

        if(fittestGenotype.getTest().getDepth() < 15){
            GAUtil.printNode(fittestGenotype.getTest().getRoot());
        }

        System.out.println(population.size());

    }

    private void calculateWaterAndFoodFitnesslessAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double waterFitness = subject.getNumberOfWaterDrunkWhenThirsty() / (subject.getNumberOfWaterDrunk() + 1);
            double foodFitness = subject.getNumberOfFoodEatenWhenHungry() / (subject.getNumberOfFoodEaten() + 1);
            subject.setFitness(waterFitness + foodFitness);
        }
    }

    private void calculateWaterAndSleepFitnesslessAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double waterFitness = subject.getNumberOfWaterDrunkWhenThirsty() / (subject.getNumberOfWaterDrunk() + 1);
            double sleepFitness = subject.getNumberOfSleepsWhenTired() / (subject.getNumberOfSleeps() + 1);
            subject.setFitness(waterFitness + sleepFitness);
        }
    }

    private void calculateFoodAndSleepFitnesslessAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double foodFitness = subject.getNumberOfFoodEatenWhenHungry() / (subject.getNumberOfFoodEaten() + 1);
            double sleepFitness = subject.getNumberOfSleepsWhenTired() / (subject.getNumberOfSleeps() + 1);
            subject.setFitness(foodFitness + sleepFitness);
        }
    }


    private void moreAccurrateElitism(ArrayList<LifeForm> lifeForms){
        population = lifeForms;

        calculateFitnessAll();
        sortPopulation();

        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

        //ELITISM
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
      //  int elitism = (int) Math.floor((0.01 * population.size()));
        int elitism = 5;
        for(int i = 0; i < elitism; i ++){
            elite.add(population.get(i));
            population.remove(i);
        }
        System.out.println("Population Size: " + population.size());
        System.out.println("Elite Size: " + elite.size());

        //WATER AND FOOD FITNESS
        ArrayList<LifeForm> waterAndFood = new ArrayList<LifeForm>();
       // int waterAndFoodAmount = (int) Math.floor((0.1 * population.size()));
        int waterAndFoodAmount = 50;
        calculateWaterAndFoodFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndFoodAmount; i ++){
            waterAndFood.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndFood);
        ArrayList<LifeForm> childrenWaterAndFood = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Water and Food Size: " + waterAndFood.size());
        System.out.println("Water and Food CHILDREN Size: " + childrenWaterAndFood.size());

        //WATER AND SLEEP
        ArrayList<LifeForm> waterAndSleep = new ArrayList<LifeForm>();
       // int waterAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        int waterAndSleepAmount = 50;
        calculateWaterAndSleepFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndSleepAmount; i ++){
            waterAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndSleep);
        ArrayList<LifeForm> childrenWaterAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Water and Sleep Size: " + waterAndSleep.size());
        System.out.println("Water and Sleep CHILDREN Size: " + childrenWaterAndSleep.size());

        //FOOD AND SLEEP
        ArrayList<LifeForm> foodAndSleep = new ArrayList<LifeForm>();
       // int foodAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        int foodAndSleepAmount = 50;
        calculateFoodAndSleepFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < foodAndSleepAmount; i ++){
            foodAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(foodAndSleep);
        ArrayList<LifeForm> childrenFoodAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);


        System.out.println("Population Size: " + population.size());
        System.out.println("Food and Sleep Size: " + foodAndSleep.size());
        System.out.println("Food and Sleep CHILDREN Size: " + childrenFoodAndSleep.size());

        //REMAINING POPULATION
        sortPopulation();
        normalizeFitnessOfPopulation();
        selectParents(population);
        ArrayList<LifeForm> childrenRemaining = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Remaining Size: " + population.size());
        System.out.println("Remaining CHILDREN Size: " + childrenRemaining.size());

        newPopulation.addAll(childrenWaterAndFood);
        newPopulation.addAll(childrenWaterAndSleep);
        newPopulation.addAll(childrenFoodAndSleep);
        newPopulation.addAll(childrenRemaining);

        HolderForGATest.mutateTrees(newPopulation);

        newPopulation.addAll(elite);

        population.clear();
        population = newPopulation;

        System.out.println("Population Size: " + population.size());

        calculateFitnessAll();
        sortPopulation();

        for(int i = 0; i < newPopulation.size(); i ++){
            System.out.println(population.get(i).getFitness());
        }

        geneticEngine.setLifeForms(population);

        if(fittestGenotype.getTest().getDepth() < 15){
            this.fittestGenotype = elite.get(0);
            GAUtil.printNode(fittestGenotype.getTest().getRoot());
        }

        System.out.println("Population Size: " + population.size());
    }


    private void monkeyElitism(ArrayList<LifeForm> lifeForms){
        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();

        normalizeFitnessOfPopulation();

        newPopulation = new ArrayList<LifeForm>();

        //ELITISM
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
        int elitism = (int) Math.floor((0.01 * population.size()));
        for(int i = 0; i < elitism; i ++){
            elite.add(population.get(i));
            population.remove(i);
        }

        System.out.println("Population Size: " + population.size());
        System.out.println("Elite Size: " + elite.size());

        //WATER AND FOOD FITNESS
        ArrayList<LifeForm> waterAndFood = new ArrayList<LifeForm>();
        int waterAndFoodAmount = (int) Math.floor((0.1 * population.size()));
        calculateWaterAndFoodFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndFoodAmount; i ++){
            waterAndFood.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndFood);
        ArrayList<LifeForm> childrenWaterAndFood = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Water and Food Size: " + waterAndFood.size());
        System.out.println("Water and Food CHILDREN Size: " + childrenWaterAndFood.size());

        //WATER AND SLEEP
        ArrayList<LifeForm> waterAndSleep = new ArrayList<LifeForm>();
        int waterAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        calculateWaterAndSleepFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < waterAndSleepAmount; i ++){
            waterAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(waterAndSleep);
        ArrayList<LifeForm> childrenWaterAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Water and Sleep Size: " + waterAndSleep.size());
        System.out.println("Water and Sleep CHILDREN Size: " + childrenWaterAndSleep.size());

        //FOOD AND SLEEP
        ArrayList<LifeForm> foodAndSleep = new ArrayList<LifeForm>();
        int foodAndSleepAmount = (int) Math.floor((0.1 * population.size()));
        calculateFoodAndSleepFitnessAccurrateElitismmoreAccurrateElitism(population);
        sortPopulation();
        normalizeFitnessOfPopulation();
        for(int i = 0; i < foodAndSleepAmount; i ++){
            foodAndSleep.add(population.get(i));
            population.remove(i);
        }
        selectParents(foodAndSleep);
        ArrayList<LifeForm> childrenFoodAndSleep = HolderForGATest.crossoverTrees(configuration.getParentSelector().getParentsA(), configuration.getParentSelector().getParentsB(), population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Food and Sleep Size: " + foodAndSleep.size());
        System.out.println("Food and Sleep CHILDREN Size: " + childrenFoodAndSleep.size());

        //REMAINING POPULATION
        sortPopulation();
        normalizeFitnessOfPopulation();

        LifeForm[] eliteParents = new LifeForm[population.size() / 2];
        LifeForm[] remaining = new LifeForm[population.size() / 2];

        /*
        for(int i = 0; i < population.size() / 2; i ++){
            LifeForm copy = new LifeForm();
            copy.setChromosome(elite.get(0).getChromosome());
            copy.setTest(elite.get(0).getTest());
            copy.setWorld(elite.get(0).getWorld());
            copy.positionX = 2 + (int)(Math.random() * ((SimulationSettings.WORLD_WIDTH - 2) + 1));
            copy.positionY = 2 + (int)(Math.random() * ((SimulationSettings.WORLD_HEIGHT - 2) + 1));
            eliteParents[i] = copy;
            remaining[i] = population.get(i);
        }
        */

        ArrayList<LifeForm> childrenRemaining = HolderForGATest.crossoverTrees(eliteParents, remaining, population);

        System.out.println("Population Size: " + population.size());
        System.out.println("Remaining Size: " + population.size());
        System.out.println("Remaining CHILDREN Size: " + childrenRemaining.size());

        newPopulation.addAll(childrenWaterAndFood);
        newPopulation.addAll(childrenWaterAndSleep);
        newPopulation.addAll(childrenFoodAndSleep);
        newPopulation.addAll(childrenRemaining);

       // HolderForGATest.mutateTrees(newPopulation);

        newPopulation.addAll(elite);

        population = newPopulation;
        calculateFitnessAll();
        sortPopulation();

        geneticEngine.setLifeForms(population);

        if(fittestGenotype.getTest().getDepth() < 15){
            GAUtil.printNode(fittestGenotype.getTest().getRoot());
        }

        System.out.println("Population Size: " + population.size());
    }



    private void calculateWaterAndFoodFitnessAccurrateElitismmoreAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double waterFitness = subject.getNumberOfWaterDrunkWhenThirsty() / (subject.getNumberOfWaterDrunk() + 1);
            double foodFitness = subject.getNumberOfFoodEatenWhenHungry() / (subject.getNumberOfFoodEaten() + 1);

            subject.setFitness((waterFitness + foodFitness));
        }
    }

    private void calculateWaterAndSleepFitnessAccurrateElitismmoreAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double waterFitness = subject.getNumberOfWaterDrunkWhenThirsty() / (subject.getNumberOfWaterDrunk() + 1);
            double sleepFitness = subject.getNumberOfSleepsWhenTired() / (subject.getNumberOfSleeps() + 1);
            subject.setFitness((waterFitness + sleepFitness));
        }
    }

    private void calculateFoodAndSleepFitnessAccurrateElitismmoreAccurrateElitism(ArrayList<LifeForm> subjects){
        for(int i = 0; i < subjects.size(); i ++){
            LifeForm subject = subjects.get(i);
            double foodFitness = subject.getNumberOfFoodEatenWhenHungry() / (subject.getNumberOfFoodEaten() + 1);
            double sleepFitness = subject.getNumberOfSleepsWhenTired() / (subject.getNumberOfSleeps() + 1);
            subject.setFitness((sleepFitness + foodFitness));
        }
    }

    public void selectParents(){
        configuration.getParentSelector().selectParents(population);
    }

    public void selectParents(ArrayList<LifeForm> lifeForms){
        configuration.getParentSelector().selectParents(lifeForms);
    }

    @Override
    public ArrayList<LifeForm> crossover(LifeForm father, LifeForm mother) {

        LifeForm[] parentsA = configuration.getParentSelector().getParentsA();
        LifeForm[] parentsB = configuration.getParentSelector().getParentsB();
        ArrayList<LifeForm> childrenList = new ArrayList<LifeForm>();
        for(int i = 0; i < population.size() / 2; i ++){
           // LifeForm[] children = configuration.getCrossoverMethod().crossOverParents(parentsA[i], parentsB[i]);
           // childrenList.add(children[0]);
           // childrenList.add(children[1]);
        }
        return childrenList;
    }

    @Override
    public void mutate() {
        if(newPopulation.size() <= 0) return;
        int ran = 0 + (int)(Math.random() * ((10 - 0) + 1));

        for(int i = 0; i < ran; i ++){
            int r = 0 + (int)(Math.random() * (((newPopulation.size() - 1) - 0) + 1));
            newPopulation.set(r, configuration.getMutateMethod().mutate(newPopulation.get(r), configuration));
        }
    }

    public LifeForm mutate(LifeForm lifeForm){
        return configuration.getMutateMethod().mutate(lifeForm, configuration);
    }


}
