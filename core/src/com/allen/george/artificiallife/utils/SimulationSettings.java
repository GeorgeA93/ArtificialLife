package com.allen.george.artificiallife.utils;

/**
 * Created by George on 17/07/2014.
 */
public class SimulationSettings {

    public static String WORLD_NAME = "NewWorld";
    public static int WORLD_WIDTH = 200; //200
    public static int WORLD_HEIGHT = 200; //200
    public static boolean GEN_TREES = true;
    public static boolean GEN_WATER= true;
    public static boolean GEN_DEAD_TREES= true;
    public static boolean GEN_SMALL_ROCKS= true;
    public static boolean GEN_LARGE_ROCKS= true;
    public static boolean GEN_GRASS= true;
    public static boolean CAN_SNOW= true;
    public static boolean CAN_RAIN= true;
    public static int NUM_DAYS = 10000;
    public static int INIT_SPEED = 1;
    public static boolean SAVE_GENERATIONS = true;
    public static double MUTATION_RATE = 0.01;
    public static String CROSSOVER_RATE = "ALL";
    public static Boolean IS_OLD_SIM = false;
    public static int POPULATION_SIZE = 100;  //500
    public static String OUPUT_FILE_PATH = "output/";
    public static String OUTPUT_FILE_NAME = "generations.xml";
    public static int WHEN_TO_MUTATE = 10;
    public static int MINIMUM_FOOD_ON_MAP = 600;
    public static int MAXIMUM_FOOD_ON_MAP = 700 ;
    public static int MINIMUM_WATER_ON_MAP = 400 ;
    public static int MAXIMUM_WATER_ON_MAP = 500 ;

}
