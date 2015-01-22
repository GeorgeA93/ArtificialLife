package com.allen.george.artificiallife.utils;

import com.allen.george.artificiallife.data.GenerationObject;
import com.allen.george.artificiallife.data.GenerationReader;
import com.allen.george.artificiallife.ga.GeneticEngine;
import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.simulation.world.map.Map;
import com.allen.george.artificiallife.simulation.world.map.layers.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by George on 31/07/2014.
 */
public class FileUtils {

    public static void loadGenerations(String filePath, ArtificialLife artificialLife) throws  IOException{

        GenerationReader generationReader = new GenerationReader(filePath, artificialLife.getWorld());
        ArrayList<GenerationObject> generationObjects = generationReader.getGenerations();

        GeneticEngine geneticEngine = new GeneticEngine(artificialLife.getWorld(), generationObjects);

        artificialLife.getWorld().setGeneticEngine(geneticEngine);
    }

    public static void loadSimulation(String filePath, ArtificialLife artificialLife) throws  IOException{
        String backgroundFilePath = "";
        String foregroundFilePath= "";
        String interactiveFilePath= "";
        String shadowFilePath= "";
        String generationsFilePath= "";


        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try{
            String line = reader.readLine();
            while (line != null){
                if(line.contains("Test_Background")){
                    backgroundFilePath = line;
                } else if(line.contains("Test_Foreground")){
                    foregroundFilePath= line;
                } else if(line.contains("Test_Interactive")){
                    interactiveFilePath = line;
                } else if(line.contains("Test_Shadow")){
                    shadowFilePath= line;
                } else if(line.contains("generations")){
                    generationsFilePath = line;
                }

                line = reader.readLine();
            }
        }finally {
            reader.close();
        }

        if(!backgroundFilePath.equals("") && !foregroundFilePath.equals("") && !interactiveFilePath.equals("") && !shadowFilePath.equals("") && !generationsFilePath.equals("")){


            GenerationReader generationReader = new GenerationReader(generationsFilePath, artificialLife.getWorld());
            ArrayList<GenerationObject> generationObjects = generationReader.getGenerations();

            GeneticEngine geneticEngine = new GeneticEngine(artificialLife.getWorld(), generationObjects);

            artificialLife.getWorld().setGeneticEngine(geneticEngine);
        }

    }

    public static void saveMap(ArrayList<MapLayer> layers, String fileName){
        for(MapLayer layer : layers){
            try{
                FileWriter saveFile = new FileWriter(fileName + "_" + layer.name + ".txt");

                for(int y = 0; y < layer.height; y ++){
                    for(int x = 0 ; x < layer.width; x ++){
                        if (x == layer.width - 1) {
                            saveFile.write("\n");
                        } else {
                            saveFile.append(layer.getTileAt(x, y) + " ");
                        }
                    }
                }

                saveFile.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static MapLayer loadLayer(String filePath){
        try{
            MapLayer layer = null;
            int width = getMapWidth(filePath);
            int height = getMapHeight(filePath);
            if(filePath.contains("Background")){
                layer = new BackgroundLayer(width, height);
            } else if(filePath.contains("Foreground")){
                layer = new ForegroundLayer(width, height);
            } else if(filePath.contains("Interactive")){
                layer = new InteractiveLayer(width, height);
            } else if(filePath.contains("Shadow")){
                layer = new ShadowLayer(width, height);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            int y = 0;
            while(line != null){
                String[] lineTiles = line.split(" ");
                for(int x = 0; x < lineTiles.length; x++){
                    layer.addTile(x, y, Integer.valueOf(lineTiles[x]));
                }
                y ++;
                line = reader.readLine();
            }

            reader.close();
            return layer;
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Could not load map layer: " + filePath);
        }
        return null;
    }

    private static int getMapWidth(String filePath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    private static int getMapHeight(String filePath) throws IOException{
        InputStream is = new BufferedInputStream(new FileInputStream(filePath));
        try{
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while((readChars = is.read(c)) != -1){
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

}
