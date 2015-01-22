package com.allen.george.artificiallife.ga;

import com.allen.george.artificiallife.data.GenerationObject;
import com.allen.george.artificiallife.data.GenerationWriter;
import com.allen.george.artificiallife.data.networking.WebServiceAPI;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.allen.george.geneticx.*;
import com.allen.george.geneticx.crossover.TreeCrossoverMethod;
import com.allen.george.geneticx.fitness.*;
import com.allen.george.geneticx.mutation.RandomGeneMutator;
import com.allen.george.geneticx.selection.TournamentParentSelection;
import com.allen.george.geneticx.util.GeneticXUtil;
import com.allen.george.geneticx.Gene;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by George Allen on 11/16/2014.
 */
public class GeneticEngine {

    private Configuration configuration;

    private World world;

    private ArrayList<LifeForm> lifeForms = new ArrayList<LifeForm>();

    private boolean evolved = false;
    private int generation = 0;

    private GeneticAlgorithm geneticAlgorithm;
    private  ArrayList<GenerationObject> generations;
    private GenerationWriter generationWriter = new GenerationWriter();


    public GeneticEngine(World world, ArrayList<GenerationObject> generations){
        this.world = world;
        this.generations = generations;
        lifeForms = null;
        Gene[] possibleGenes = new Gene[7];
        for(int i = 0; i < possibleGenes.length; i ++) {
            possibleGenes[i] = new Gene(i);
        }
       // configuration = new Configuration("Test", SimulationSettings.POPULATION_SIZE, new TestMutliple(new FitnessFunction[] { new FoodTestFitness(), new SleepTestFitness(), new RandomTestFitness(), new WaterTestFitness()}), new NormalFitnessEvaluator(), new TournamentParentSelection(), new TreeCrossoverMethod(), new RandomGeneMutator(), possibleGenes);
        configuration = new Configuration("Test", SimulationSettings.POPULATION_SIZE,  new TestFitnessFunction(), new NormalFitnessEvaluator(), new TournamentParentSelection(), new TreeCrossoverMethod(), new RandomGeneMutator(), possibleGenes);
        configuration.print();
        geneticAlgorithm = new GA(configuration, SimulationSettings.POPULATION_SIZE, this);
    }

    public GeneticEngine(World world){
        this.world = world;
        init();
    }

    private void init(){
        Gene[] possibleGenes = new Gene[7];
        for(int i = 0; i < possibleGenes.length; i ++) {
            possibleGenes[i] = new Gene(i);
        }

        for(int i = 0; i < SimulationSettings.POPULATION_SIZE; i ++){
            lifeForms.add(new LifeForm(world, GeneticXUtil.generateRandomGenesFromPossibleGenes(possibleGenes, 15)));
        }

        //configuration = new Configuration("Test", SimulationSettings.POPULATION_SIZE, new TestMutliple(new FitnessFunction[] { new FoodTestFitness(), new SleepTestFitness(), new RandomTestFitness(), new WaterTestFitness()}), new NormalFitnessEvaluator(), new TournamentParentSelection(), new TreeCrossoverMethod(), new RandomGeneMutator(), possibleGenes);
        configuration = new Configuration("Test", SimulationSettings.POPULATION_SIZE,  new TestFitnessFunction(), new NormalFitnessEvaluator(), new TournamentParentSelection(), new TreeCrossoverMethod(), new RandomGeneMutator(), possibleGenes);
        configuration.print();

        geneticAlgorithm = new GA(configuration, SimulationSettings.POPULATION_SIZE, this);



        OutputStreamWriter out = new OutputStreamWriter(System.out);
        for(int i = 0; i < SimulationSettings.POPULATION_SIZE; i ++){
           // GAUtil.printNode(lifeForms.get(i).getTest().getRoot());
            try{
                lifeForms.get(i).getTest().getRoot().printTree(out);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
           out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLifeForms(double timeSpeed){
       for(LifeForm lf : lifeForms){
            lf.update(timeSpeed);
       }
    }


    public void update(double timeSpeed){

        if(lifeForms == null){
            if(generation >= generations.size()){
                world.getArtificialLife().setRunning(false);
                return;
            }
            lifeForms = generations.get(generation).getLifeForms();
        }
        //update the lifeforms
        updateLifeForms(timeSpeed);


        if((world.getDayNightCycler().getCycles() % SimulationSettings.WHEN_TO_MUTATE == 0) ){
            if(generation == 0){
                if(!SimulationSettings.IS_OLD_SIM){
                    generation ++;
                    evolved = true;
                }
            }
            if(!evolved){

                if(SimulationSettings.IS_OLD_SIM){
                    world.getArtificialLife().getGui().getCustomDataSet().addData(generations.get(generation).getAverageFitness(), generations.get(generation).getFittesteGenotype(), generations.get(generation).getWorstGenotype(), generation);
                    generation ++;
                    lifeForms = generations.get(generation).getLifeForms();
                    evolved = true;
                } else {
                    geneticAlgorithm.run(lifeForms);

                    world.getArtificialLife().getGui().getCustomDataSet().addData(geneticAlgorithm.getAverageFitness(), geneticAlgorithm.getFittesteGenotype().getFitness().doubleValue(), geneticAlgorithm.getWorstGenotype().getFitness().doubleValue(), generation);
                    for(LifeForm lf : this.lifeForms){
                        lf.initAsChild();
                    }

                    if(SimulationSettings.SAVE_GENERATIONS){
                        generationWriter.start(lifeForms, generation, SimulationSettings.OUPUT_FILE_PATH + SimulationSettings.OUTPUT_FILE_NAME);
                    }

                    try{
                        WebServiceAPI.pushAverageData(geneticAlgorithm.getFittesteGenotype().getFitness().doubleValue(), geneticAlgorithm.getWorstGenotype().getFitness().doubleValue(),geneticAlgorithm.getAverageFitness(), generation);
                        WebServiceAPI.pushBestLifeFormData(geneticAlgorithm.getFittesteGenotype());
                    } catch (Exception e){

                    }




                    generation ++;
                    evolved = true;

                    geneticAlgorithm.cleanUp();
                }
            }
        } else {
            evolved = false;
        }
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        if(lifeForms == null)return;
        for(LifeForm lf : lifeForms){
            lf.render(spriteBatch, camera);
        }
    }


    public ArrayList<LifeForm> getPhenotypes() {
        return lifeForms;
    }

    public void setLifeForms(ArrayList<LifeForm> lifeForms){
        this.lifeForms = lifeForms;
    }

    public void setGeneration(int generation){
        if(generation >= generations.size()){
            world.getArtificialLife().setRunning(false);
            return;
        }
        for(int i = this.generation; i < generation; i ++){
            lifeForms = generations.get(i).getLifeForms();
            world.getArtificialLife().getGui().getCustomDataSet().addData(generations.get(generation).getAverageFitness(), generations.get(generation).getFittesteGenotype(), generations.get(generation).getWorstGenotype(), generation);
        }
        this.generation = generation;
        world.getDayNightCycler().setCycles(generation * SimulationSettings.WHEN_TO_MUTATE);
    }

    public int getGeneration(){
        return  this.generation;
    }

}
