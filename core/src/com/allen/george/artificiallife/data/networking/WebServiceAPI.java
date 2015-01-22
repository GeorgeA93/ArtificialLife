package com.allen.george.artificiallife.data.networking;

import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by George on 14/01/2015.
 */
public class WebServiceAPI {

    private static final String DELETE_PREVIOUS_TABLES_URL = "http://allen-george.com/deleteAllPhp.php";
    private static final String DELETE_BEST_TREE_NODES_URL = "http://allen-george.com/deleteBestTreeNodes.php";
    private static final String AVERAGE_WEB_SERVICE_URL = "http://allen-george.com/uploadData.php";
    private static final String LIFEFORM_TREE_URL = "http://allen-george.com/uploadLifeFormTree.php";
    private static final String charset = "UTF-8";

    public static void deleteAllGenerations() throws Exception{
        URL url = new URL(DELETE_PREVIOUS_TABLES_URL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Deleted data" );
        } else {
            System.err.println("Failed to upload data");
        }
    }

    public static void deleteAllNodes() throws Exception{
        URL url = new URL(DELETE_BEST_TREE_NODES_URL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Deleted data" );
        } else {
            System.err.println("Failed to upload data");
        }
    }

    public static void pushAverageData(double bestFitness, double worstFitness, double averageFitness, int generation) throws Exception{
        String query = "BestFitness=" + bestFitness + "&WorstFitness=" + worstFitness + "&AverageFitness=" + averageFitness + "&Generation=" + generation;
        URL url = new URL(AVERAGE_WEB_SERVICE_URL + "?" + query);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Uploaded data");
        } else {
            System.err.println("Failed to upload data");
        }
    }
    static int index  = 0;

    public static void pushBestLifeFormData(LifeForm lifeForm) throws Exception{
        index = 0;
        deleteAllNodes();
        Tree tree = lifeForm.getTest();
        pushNode(tree.getRoot());
    }


    private static void pushNode(Node node) throws Exception{
        String query = "id=" + index + "&label=" + node.getFunctionName();
        URL url = new URL(LIFEFORM_TREE_URL + "?" + query);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("Uploaded data");
        } else {
            System.err.println("Failed to upload data");
        }

        if(node.getLeftChild() != null){
            index ++;
            pushNode(node.getLeftChild());
        }

        if(node.getRightChild() != null){
            index ++;
            pushNode(node.getRightChild());
        }
    }




}
