package com.allen.george.artificiallife.data.networking;

import com.allen.george.artificiallife.ga.Node;
import com.allen.george.artificiallife.ga.Tree;
import com.allen.george.artificiallife.simulation.life.LifeForm;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.orsoncharts.util.json.JSONObject;
import org.lwjgl.Sys;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by George on 14/01/2015.
 */
public class WebServiceAPI {

    private static final String DELETE_PREVIOUS_TABLES_URL = "http://allen-george.com/deleteAllPhp.php";
    private static final String DELETE_BEST_TREE_NODES_URL = "http://allen-george.com/deleteBestTreeNodes.php";
    private static final String DELETE_BEST_DATA_URL = "http://allen-george.com/deleteLifeFormData.php";
    private static final String AVERAGE_WEB_SERVICE_URL = "http://allen-george.com/uploadData.php";
    private static final String LIFEFORM_TREE_URL = "http://allen-george.com/uploadLifeFormTree.php";
    private static final String LIFEFORM_DATA_URL = "http://allen-george.com/uploadLifeFormData.php";
    private static final String DOES_USER_EXIST_URL = "http://allen-george.com/doesUserExist.php";
    private static final String charset = "UTF-8";

    public static void deleteAllGenerations(final String username) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    URL url = new URL(DELETE_PREVIOUS_TABLES_URL + "?Username=" + username);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Deleted data" );
                    } else {
                        System.err.println("Failed to upload data");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void deleteAllNodes(final String username) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run()  {
                try{
                    URL url = new URL(DELETE_BEST_TREE_NODES_URL+ "?Username=" + username);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Deleted data" );
                    } else {
                        System.err.println("Failed to upload data");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void deleteLifeFormData(final String username) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run()  {
                try{
                    URL url = new URL(DELETE_BEST_DATA_URL+ "?Username=" + username);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Deleted data" );
                    } else {
                        System.err.println("Failed to upload data");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void pushAverageData(final double bestFitness,final double worstFitness,final double averageFitness,final int generation, final String username) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run()   {
                try{
                    String query = "BestFitness=" + bestFitness + "&WorstFitness=" + worstFitness + "&AverageFitness=" + averageFitness + "&Generation=" + generation + "&Username=" + username;
                    URL url = new URL(AVERAGE_WEB_SERVICE_URL + "?" + query);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Uploaded data");
                    } else {
                        System.err.println("Failed to upload data");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    static int index  = 0;

    public static void pushBestLifeFormTree(LifeForm lifeForm, String username) throws Exception{
        index = 0;
        deleteAllNodes(username);
        Tree tree = lifeForm.getTree();
        pushNode(tree.getRoot(), username);
    }

    public static void pushBestLifeFormData(final double fitness,final double waterScore,final double foodScore, final double sleepScore,final double totalPenalty,final double adjustment,final String targetRatio, final String username)throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run()   {
                try{
                    deleteLifeFormData(username);
                    String query = "Fitness=" + fitness + "&WaterFitness=" + waterScore + "&FoodFitness=" + foodScore + "&SleepFitness=" + sleepScore  + "&TotalPenalty=" + totalPenalty  + "&Adjustment=" + adjustment + "&TargetRatio=" + targetRatio + "&Username=" +username;
                    URL url = new URL(LIFEFORM_DATA_URL + "?" + query);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Uploaded data");
                    } else {
                        System.err.println("Failed to upload data");
                    }
                } catch (Exception e){

                }
            }
        }).start();

    }


    private static void pushNode(final Node node, final String username) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run()   {
                try{
                    String query = "id=" + index + "&label=" + node.getFunctionName() + "&username=" + username;
                    URL url = new URL(LIFEFORM_TREE_URL + "?" + query);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        System.out.println("Uploaded data");
                    } else {
                        System.err.println("Failed to upload data");
                    }

                    if(node.getLeftChild() != null){
                        index ++;
                        pushNode(node.getLeftChild(), username);
                    }

                    if(node.getRightChild() != null){
                        index ++;
                        pushNode(node.getRightChild(), username);
                    }
                } catch (Exception e){

                }
            }
        }).start();

    }


    public static boolean doesUserExist(String username, String password) throws Exception{
        boolean res;
        String query = "?username=" + username + "&password=" + password;
        URL url = new URL(DOES_USER_EXIST_URL + query);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){

           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           StringBuilder builder = new StringBuilder();
            String line ;
            while((line = br.readLine()) != null){
                builder.append(line + "\n");
            }
            br.close();

            if(builder.toString().contains("[{\"Username\":")){
                res = true;
            } else {
                res = false;
            }

        } else {
            res = false;
        }


        return res;
    }




}
