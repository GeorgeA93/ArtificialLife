package com.allen.george.artificiallife.utils;

import com.allen.george.artificiallife.simulation.world.map.layers.MapLayer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by George on 31/07/2014.
 */
public class MapFile {

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

}
