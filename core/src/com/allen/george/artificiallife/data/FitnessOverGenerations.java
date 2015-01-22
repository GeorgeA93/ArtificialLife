package com.allen.george.artificiallife.data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import java.awt.geom.Ellipse2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by George on 19/11/2014.
 */
public class FitnessOverGenerations extends JPanel{


    public FitnessOverGenerations(CustomDataSet customDataSet, int type){

        XYDataset dataset;
        switch (type){
            case 0:
                dataset = customDataSet.getAverageSeriesCollection();
                break;
            case 1:
                dataset = customDataSet.getMinimumSeriesCollection();
                break;
            case 2:
                default:
                dataset = customDataSet.getMaximumSeriesCollection();
                break;
        }

        JFreeChart chart = createChart(dataset, type);
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(500, 200));
        add(panel);
    }

    public void updateData(CustomDataSet customDataSet, int type){
        removeAll();


        XYDataset dataset;
        switch (type){
            case 0:
                dataset = customDataSet.getAverageSeriesCollection();
                break;
            case 1:
                dataset = customDataSet.getMinimumSeriesCollection();
                break;
            case 2:
            default:
                dataset = customDataSet.getMaximumSeriesCollection();
                break;
        }

        JFreeChart chart = createChart(dataset, type);
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(500, 200));
        add(panel);
    }

    private static JFreeChart createChart(final XYDataset dataset, int type){
        JFreeChart chart;
        switch(type){
            case 0:
               chart = ChartFactory.createXYLineChart("Average Fitness Over Generations", "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
                break;
            case 1:
                chart = ChartFactory.createXYLineChart("Worst Fitness Over Generations", "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
                break;
            case 2:
            default:
                chart = ChartFactory.createXYLineChart("Best Fitness Over Generations", "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
                break;
        }


        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.getDomainAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        plot.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return chart;
    }



}
