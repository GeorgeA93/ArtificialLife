package com.allen.george.artificiallife.ga;


import com.allen.george.artificiallife.data.networking.WebServiceAPI;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.allen.george.geneticx.Configuration;
import com.allen.george.geneticx.GeneticAlgorithm;


import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
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

    private void csvSave(int generationNum, ArrayList<LifeForm> populationToSave){
        try{
            FileWriter writer = new FileWriter("output/population_fitness_" + generationNum + ".csv");
            writer.append("ID");
            writer.append(',');
            writer.append("Fitness");
            writer.append('\n');

            int count = 0;
            for(LifeForm lifeForm : populationToSave){
                writer.append(String.valueOf(count));
                writer.append(',');
                writer.append(String.valueOf(lifeForm.getFitness().doubleValue()));
                writer.append('\n');
                count ++;
            }

            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void pushData(){

        csvSave(geneticEngine.getGeneration(), population);

        geneticEngine.getWorld().getArtificialLife().getGui().getCustomDataSet().addData(getAverageFitness(), getFittesteGenotype().getFitness().doubleValue(), getWorstGenotype().getFitness().doubleValue(), geneticEngine.getGeneration());

        if(SimulationSettings.PUSH_DATA_TO_WEB){
            try{
                WebServiceAPI.pushAverageData(getFittesteGenotype().getFitness().doubleValue(), getWorstGenotype().getFitness().doubleValue(), getAverageFitness(), geneticEngine.getGeneration(), geneticEngine.getWorld().getArtificialLife().getGui().getUserData().USERNAME);
                WebServiceAPI.pushBestLifeFormTree(getFittesteGenotype(), geneticEngine.getWorld().getArtificialLife().getGui().getUserData().USERNAME);
                String targetRatio = (SimulationSettings.WANTED_WATER_RATIO + "W" + SimulationSettings.WANTED_FOOD_RATIO + "F" + SimulationSettings.WANTED_SLEEP_RATIO + "S");
                WebServiceAPI.pushBestLifeFormData(getFittesteGenotype().getFitness().doubleValue(), getFittesteGenotype().WATER_DRUNK, getFittesteGenotype().FOOD_EATEN, getFittesteGenotype().SLEEPS_TAKEN, getFittesteGenotype().totalPenalty, getFittesteGenotype().adjustment, targetRatio, geneticEngine.getWorld().getArtificialLife().getGui().getUserData().USERNAME);
            } catch (Exception e){

            }
        }
    }



    @Override
    public void run(ArrayList<LifeForm> lifeForms) {


        population = lifeForms;
        calculateFitnessAll();
        sortPopulation();
        normalizeFitnessOfPopulation();

        pushData();

        newPopulation = new ArrayList<LifeForm>();

        //THE ELITE
        ArrayList<LifeForm> elite = new ArrayList<LifeForm>();
        int elitism = (int) Math.floor((SimulationSettings.ELITE_SELECTION_RATE * population.size()));
        for(int i = 0; i < elitism; i ++){
            elite.add(population.get(i));
            population.remove(i);
        }

        //THE NEXT BEST (RIVALS)
        ArrayList<LifeForm> rivals = new ArrayList<LifeForm>();
        int rivalAmount = (int) Math.floor((SimulationSettings.RIVALS_SELECTION_RATE * population.size()));
        for(int i = 0; i < rivalAmount; i ++){
            rivals.add(population.get(i));
            population.remove(i);
        }
        selectParents(rivals);
        ArrayList<LifeForm> rivalsChildren = new ArrayList<LifeForm>();
        for(int i = 0; i < rivals.size() / 2; i ++){
            rivalsChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }

        int rivalAmountM = (int) Math.ceil((SimulationSettings.MUTATION_RATE * rivalsChildren.size()));
        for(int i = 0; i < rivalAmountM; i ++){
            LifeForm lf = new LifeForm(rivalsChildren.get(i));
            rivalsChildren.remove(i);
            rivalsChildren.add(i, configuration.getMutateMethod().mutate(lf, configuration));
        }

        //THE NEXT NEXT BEST (AVERAGE ISH)
        ArrayList<LifeForm> averageOnes = new ArrayList<LifeForm>();
        int averageOnesAmount = (int) Math.floor((SimulationSettings.AVERAGE_SELECTION_RATE * population.size()));
        for(int i = 0; i < averageOnesAmount; i ++){
            averageOnes.add(population.get(i));
            population.remove(i);
        }
        selectParents(averageOnes);
        ArrayList<LifeForm> averageOnesChildren = new ArrayList<LifeForm>();
        for(int i = 0; i < averageOnes.size() / 2; i ++){
            averageOnesChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }

        int averageAmountM = (int) Math.ceil((SimulationSettings.MUTATION_RATE * averageOnesChildren.size()));
        for(int i = 0; i < averageAmountM; i ++){
            LifeForm lf = new LifeForm(averageOnesChildren.get(i));
            averageOnesChildren.remove(i);
            averageOnesChildren.add(i, configuration.getMutateMethod().mutate(lf, configuration));
        }


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
            LifeForm parent = new LifeForm(possibleParents.get(random.nextInt(possibleParents.size())));
            newParents.add(parent);
        }

        selectParents(newParents);
        for(int i = 0; i < newParents.size() / 2; i ++){
            newChildren.addAll(configuration.getCrossoverMethod().crossOverParents(configuration.getParentSelector().getParentsA()[i], configuration.getParentSelector().getParentsB()[i]));
        }

        int newChildrenAmountM = (int) Math.ceil((SimulationSettings.MUTATION_RATE * newChildren.size()));
        for(int i = 0; i < newChildrenAmountM; i ++){
            LifeForm lf = new LifeForm(newChildren.get(i));
            newChildren.remove(i);
            newChildren.add(i, configuration.getMutateMethod().mutate(lf, configuration));
        }

        //ADD THE NEW CHILDREN TO THE NEW POPULATION
        newPopulation.addAll(newChildren);

        population = newPopulation;
        sortPopulation();

        geneticEngine.setLifeForms(population);

        OutputStreamWriter out = new OutputStreamWriter(System.out);
        try{
          //  fittestGenotype.getTree().getRoot().printTree(out);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }

    }



    public void selectParents(ArrayList<LifeForm> lifeForms){
        configuration.getParentSelector().selectParents(lifeForms);
    }



}
