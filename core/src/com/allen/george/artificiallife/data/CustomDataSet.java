package com.allen.george.artificiallife.data;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.Serializable;

/**
 * Created by George on 19/11/2014.
 */
public class CustomDataSet{

    private XYSeries averageSeries = new XYSeries("Average Fitness");
    private XYSeries miniumumSeries = new XYSeries("Worst Fitness");
    private XYSeries maximumSeries = new XYSeries("Best Fitness");

    public CustomDataSet(){
        clear();
    }

    public void addData(double averageFitness, double bestFitness, double worstFitness, int generation){
        averageSeries.add(generation, averageFitness);
        miniumumSeries.add(generation, worstFitness);
        maximumSeries.add(generation, bestFitness);

    }

    public XYSeriesCollection getAverageSeriesCollection(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(averageSeries);
        return dataset;
    }

    public XYSeriesCollection getMinimumSeriesCollection(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(miniumumSeries);
        return dataset;
    }

    public XYSeriesCollection getMaximumSeriesCollection(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(maximumSeries);
        return dataset;
    }

    public void clear(){
        averageSeries.clear();
        miniumumSeries.clear();
        maximumSeries.clear();
    }

}
