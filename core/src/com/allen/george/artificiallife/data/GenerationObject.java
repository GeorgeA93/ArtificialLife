package com.allen.george.artificiallife.data;

import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by George on 15/12/2014.
 */
public class GenerationObject {

    private int number;
    private ArrayList<LifeForm> lifeForms;

    public GenerationObject(int number, ArrayList<LifeForm> lifeForms){
        this.number = number;
        this.lifeForms = lifeForms;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<LifeForm> getLifeForms() {
        return lifeForms;
    }

    public void setLifeForms(ArrayList<LifeForm> lifeForms) {
        this.lifeForms = lifeForms;
    }

    public double getAverageFitness(){
        double averageTotal = 0;
        for(int i = 0; i < lifeForms.size(); i ++){
            averageTotal += lifeForms.get(i).getFitness().doubleValue();
        }

        return averageTotal / lifeForms.size();
    }

    public double getFittesteGenotype(){
        double max = Double.MIN_VALUE;

        for(int i = 0; i < lifeForms.size(); i ++){
            if(lifeForms.get(i).getFitness().doubleValue() > max){
                max = lifeForms.get(i).getFitness().doubleValue();
            }
        }
        return max;
    }

    public double getWorstGenotype(){
        double min = Double.MAX_VALUE;

        for(int i = 0; i < lifeForms.size(); i ++){
            if(lifeForms.get(i).getFitness().doubleValue() < min){
                min = lifeForms.get(i).getFitness().doubleValue();
            }
        }
        return min;
    }
}
