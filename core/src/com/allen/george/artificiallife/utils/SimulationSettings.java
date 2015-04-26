package com.allen.george.artificiallife.utils;

/**
 * Created by George on 17/07/2014.
 */
public class SimulationSettings {

    public static String WORLD_NAME = "NewWorld";
    public static int WORLD_WIDTH = 200;
    public static int WORLD_HEIGHT = 200;
    public static boolean GEN_TREES = true;
    public static boolean GEN_WATER= true;
    public static boolean GEN_DEAD_TREES= true;
    public static boolean GEN_SMALL_ROCKS= true;
    public static boolean GEN_LARGE_ROCKS= true;
    public static boolean GEN_GRASS= true;
    public static boolean CAN_SNOW= true;
    public static boolean CAN_RAIN= true;
    public static int NUM_DAYS = 10000;
    public static int INIT_SPEED = 200;
    public static boolean SAVE_GENERATIONS = true;
    public static double MUTATION_RATE = 0.01;
    public static double ELITE_SELECTION_RATE = 0.01;
    public static double RIVALS_SELECTION_RATE = 0.24;
    public static double AVERAGE_SELECTION_RATE = 0.5;
    public static String CROSSOVER_RATE = "ALL";
    public static Boolean IS_OLD_SIM = false;
    public static int POPULATION_SIZE = 100;
    public static String OUPUT_FILE_PATH = "output/";
    public static String OUTPUT_FILE_NAME = "generations.xml";
    public static int WHEN_TO_MUTATE = 10;
    public static int MINIMUM_FOOD_ON_MAP = 60;
    public static int MAXIMUM_FOOD_ON_MAP = 100 ;
    public static int MINIMUM_WATER_ON_MAP = 100 ;
    public static int MAXIMUM_WATER_ON_MAP = 150 ;
    public static boolean CONSTRAIN_TREE = false;
    public static int MAX_TREE_DEPTH = 14;
    public static int START_TREE_DEPTH = 3;
    public static int WANTED_WATER_RATIO = 4;
    public static int WANTED_FOOD_RATIO = 2;
    public static int WANTED_SLEEP_RATIO = 1;
    public static int WANTED_WALKABOUT_RATIO = 0;
    public static boolean PUSH_DATA_TO_WEB = true;
    public static String USERNAME = "George";

}
