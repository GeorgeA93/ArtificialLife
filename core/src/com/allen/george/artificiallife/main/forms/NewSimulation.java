package com.allen.george.artificiallife.main.forms;


import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.Gdx;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import javax.swing.border.TitledBorder;


public class NewSimulation extends JFrame implements ActionListener{
    private JPanel mainPane;

    private JPanel worldOptions = new JPanel();
    private JTextField worldHeightText;
    private JTextField worldWidthText;
    private JTextField worldNameText;
    private JLabel worldWidthLabel = new JLabel("World Width:");
    private JLabel worldHeightLabel = new JLabel("World Height:");
    private JCheckBox treesCheckBox = new JCheckBox("Trees");
    private JCheckBox waterCheckBox = new JCheckBox("Water");
    private JCheckBox deadTreeCheckBox = new JCheckBox("Dead Trees");
    private  JCheckBox smallRockCheckBox = new JCheckBox("Small Rocks");
    private JCheckBox largeRockCheckBox = new JCheckBox("Large Rocks");
    private  JCheckBox grassCheckBox = new JCheckBox("Grass");
    private   JLabel worldNameLabel = new JLabel("World Name:");
    private   JPanel weatherOptions = new JPanel();
    private  JCheckBox rainCheckBox = new JCheckBox("Rain");
    private JCheckBox snowCheckBox = new JCheckBox("Snow");
    private JLabel minimunFoodOnMapLabel = new JLabel("Minimum Food On Map: ");
    private JTextField minimumFoodOnMapText;
    private JLabel maximumFoodOnMapLabel = new JLabel("Maximum Food On Map: ");
    private JTextField maximumFoodOnMapText;
    private JLabel minimunWaterOnMapLabel = new JLabel("Minimum Water On Map: ");
    private JTextField minimumWaterOnMapText;
    private JLabel maximumWaterOnMapLabel = new JLabel("Maximum Water On Map: ");
    private JTextField maximumWaterOnMapText;

    private  JPanel simulationOptions = new JPanel();
    private  JLabel daysLabel = new JLabel("Days:");
    private  JLabel speedLabel = new JLabel("Speed:");
    private JTextField daysTextField;
    private JTextField speedText;

    private JPanel treeOptionsPanel = new JPanel();
    private JLabel startTreeDepthLabel = new JLabel("Tree Start Depth: ");
    private JTextField startTreeDepthText;
    private JLabel maxTreeDepthLabel = new JLabel("Tree Max Depth: ");
    private JTextField maxTreeDepthTextField;
    private JCheckBox constrainTreeCheckBox = new JCheckBox("Constrain Tree?");

    private JPanel algorithmOptions = new JPanel();
    private JLabel mutationRateLabel = new JLabel("Mutation Rate: ");
    private JTextField mutationRateText;
    private JLabel eliteSelectionRateLabel = new JLabel("Elite Selection Rate: ");
    private JTextField eliteSelectionText ;
    private JLabel rivalsSelectionRateLabel = new JLabel("Rivals Selection Rate: ");
    private JTextField rivalsSelectionText;
    private JLabel averageSelectionRateLabel = new JLabel("Underdogs Selection Rate: ");
    private JTextField averageSelectionText;
    private JLabel whenToEvolveLabel = new JLabel("When To Evolve: ");
    private JTextField whenToEvolveText;
    private JLabel waterDrunkRatioLabel = new JLabel("Target Water Ratio: ");
    private JTextField waterDrunkRatioText;
    private JLabel foodEatenRatioLabel = new JLabel("Target Food Ratio: ");
    private JTextField foodEathenRatioText;
    private JLabel sleepsTakenRatioLabel = new JLabel("Target Sleeps Ratio: ");
    private JTextField sleepesTakenRatioText;
    private JLabel walkaboutsRatioLabel = new JLabel("Target Walkabouts Ratio: ");
    private JTextField walksboutsRatioText;
    private JLabel populationSizeLabel = new JLabel("Population Size: ");
    private JTextField populationSizeText;


    private   JPanel dataOptions = new JPanel();
    private JCheckBox saveGenerationsCheckBox = new JCheckBox("Save Every Generation?");
    private JLabel outputFilePathLabel = new JLabel("Output File Path: ");
    private JLabel outputFileNameLabel = new JLabel("Output File Name: ");
    private JTextField outputFileNameText;
    private JCheckBox pushDataToWebCheckBox = new JCheckBox("Push Data To Website?");

    private  JButton createButton = new JButton("Create");
    private   JButton restoreDefaultsButton = new JButton("Reset");

    private MainGui parent;


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Create")) {
            validateAndCreate();
        }
    }

    private void validateAndCreate(){

        //CHECK THE WIDTH AND HEIGHT OF THE WORLD
        float width = 0, height = 0;
        try {
            width = Float.parseFloat(worldWidthText.getText());
            height = Float.parseFloat(worldHeightText.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 10 and 1000", "World Width/Height Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(width < 10 || width > 1000 || height < 10 || height > 1000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 10 and 1000", "World Width/Height Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.WORLD_WIDTH = (int)width;
            SimulationSettings.WORLD_HEIGHT = (int)height;
        }

       //CHECK THE NAME OF THE WORLD
        if(worldNameText.getText().equals("") || worldNameText.getText().contains(" ")){
            JOptionPane.showConfirmDialog(null, "Please give the world a new name, without spaces", "World Name Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.WORLD_NAME = worldNameText.getText();
        }

        //CHECK THE DAYS
        float days = 0;
        try {
            days = Float.parseFloat(daysTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 100000", "Days Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(days < 1 || days > 100000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 100000", " Days Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.NUM_DAYS = (int)days;
        }

        //CHECK THE SPEED
        float speed = 0;
        try {
            speed = Float.parseFloat(speedText.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 200", "Speed Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(speed < 1 || speed > 200){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 200", "Speed Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.INIT_SPEED = (int)speed;
        }

        //SET THE REMAINING SETTINGS
        SimulationSettings.CAN_RAIN = rainCheckBox.isSelected();
        SimulationSettings.CAN_SNOW = snowCheckBox.isSelected();
        SimulationSettings.GEN_DEAD_TREES = deadTreeCheckBox.isSelected();
        SimulationSettings.GEN_GRASS = grassCheckBox.isSelected();
        SimulationSettings.GEN_LARGE_ROCKS = largeRockCheckBox.isSelected();
        SimulationSettings.GEN_SMALL_ROCKS = smallRockCheckBox.isSelected();
        SimulationSettings.GEN_TREES = treesCheckBox.isSelected();
        SimulationSettings.GEN_WATER = waterCheckBox.isSelected();

        int minFoodOnMap = 0;
        try{
            minFoodOnMap = Integer.parseInt(minimumFoodOnMapText.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 2000", "Minimum Food Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        int maxFoodOnMap = 0;
        try{
            maxFoodOnMap = Integer.parseInt(maximumFoodOnMapText.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 3000", "Maximum Food Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(minFoodOnMap < 1 || minFoodOnMap > 2000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 2000", "Minimum Food Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(maxFoodOnMap < 1 || maxFoodOnMap > 3000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 3000", "Maximum Food Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(maxFoodOnMap < minFoodOnMap){
            JOptionPane.showConfirmDialog(null, "Maximum food on map cannot be less than the minimum food on map", "Maximum/Minimum Food Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.MINIMUM_FOOD_ON_MAP = minFoodOnMap;
            SimulationSettings.MAXIMUM_FOOD_ON_MAP = maxFoodOnMap;
        }


        int minWaterOnMap = 0;
        try{
            minWaterOnMap = Integer.parseInt(minimumWaterOnMapText.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 2000", "Minimum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        int maxWaterOnMap = 0;
        try{
            maxWaterOnMap = Integer.parseInt(maximumWaterOnMapText.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 3000", "Maximum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(minWaterOnMap < 1 || minWaterOnMap > 2000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 2000", "Minimum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(maxWaterOnMap < 1 || maxWaterOnMap > 3000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 3000", "Maximum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(maxWaterOnMap < minWaterOnMap){
            JOptionPane.showConfirmDialog(null, "Maximum Water on map cannot be less than the minimum Water on map", "Maximum/Minimum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else{
            SimulationSettings.MINIMUM_WATER_ON_MAP = minWaterOnMap;
            SimulationSettings.MAXIMUM_WATER_ON_MAP = maxWaterOnMap;
        }

        SimulationSettings.CONSTRAIN_TREE = constrainTreeCheckBox.isSelected();

        int treeStartDepth = 0;
        try{
            treeStartDepth = Integer.parseInt(startTreeDepthText.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 10", "Tree Start Depth Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(treeStartDepth < 1 | treeStartDepth > 10){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 10", "Tree Start Depth Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        int treeMaxDepth = 0;
        try{
            treeMaxDepth = Integer.parseInt(maxTreeDepthTextField.getText());
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 100", "Tree Max Depth Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(treeMaxDepth < 1 | treeMaxDepth > 100){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 100", "Tree Max Depth Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(treeMaxDepth < treeStartDepth){
            JOptionPane.showConfirmDialog(null, "Maximum tree depth cannot be less than the tree start depth", "Maximum/Minimum Water Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else {
            SimulationSettings.START_TREE_DEPTH = treeStartDepth;
            SimulationSettings.MAX_TREE_DEPTH = treeMaxDepth;
        }


        int populationSize = 0;
        try {
            populationSize = validateIntFromText(populationSizeText, 1, 1000);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 1 and 1000", "Population Size Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.POPULATION_SIZE = populationSize;

        int whenToEvolve = 0;
        try {
            whenToEvolve = validateIntFromText(whenToEvolveText, 1, 20);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 1 and 20", "When To Evolve Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(whenToEvolve > SimulationSettings.NUM_DAYS){
            JOptionPane.showConfirmDialog(null, "When to evolve cannot be greater than the number of days", "When To Evolve Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else {
            SimulationSettings.WHEN_TO_MUTATE = whenToEvolve;
        }

        double eliteSelectionRate = 0;
        try {
            eliteSelectionRate = valudateDoubleFromText(eliteSelectionText, 0.0, 1.0);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0.0 and 1.0", "Elite Selection Rate Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        double rivalsSelectionRate = 0;
        try {
            rivalsSelectionRate = valudateDoubleFromText(rivalsSelectionText, 0.0, 1.0);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0.0 and 1.0", "Rivals Selection Rate Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        double averageOnesSelectionRate = 0;
        try {
            averageOnesSelectionRate = valudateDoubleFromText(averageSelectionText, 0.0, 1.0);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0.0 and 1.0", "Underdogs Selection Rate Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }

        if(eliteSelectionRate + rivalsSelectionRate + averageOnesSelectionRate > 1.0){
            JOptionPane.showConfirmDialog(null, "Total selection rate cannot be greater than 1.0", "Selection Rate Input Error", JOptionPane.CANCEL_OPTION);
            return;
        } else {
            SimulationSettings.ELITE_SELECTION_RATE = eliteSelectionRate;
            SimulationSettings.RIVALS_SELECTION_RATE = rivalsSelectionRate;
            SimulationSettings.AVERAGE_SELECTION_RATE = averageOnesSelectionRate;
        }

        double mutationRate = 0;
        try {
            mutationRate = valudateDoubleFromText(mutationRateText, 0.0, 1.0);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0.0 and 1.0", "Mutation Rate Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.MUTATION_RATE = mutationRate;

        int targetWaterRatio = 0;
        try {
            targetWaterRatio = validateIntFromText(waterDrunkRatioText, 0, 20);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0 and 20", "Target Water Ratio Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.WANTED_WATER_RATIO = targetWaterRatio;

        int targetFoodRatio = 0;
        try {
            targetFoodRatio = validateIntFromText(foodEathenRatioText, 0, 20);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0 and 20", "Target Food Ratio Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.WANTED_FOOD_RATIO = targetFoodRatio;

        int targetSleepsRatio = 0;
        try {
            targetSleepsRatio = validateIntFromText(sleepesTakenRatioText, 0, 20);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0 and 20", "Target Sleep Ratio Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.WANTED_SLEEP_RATIO = targetSleepsRatio;

        int targetWalkaboutRatio = 0;
        try {
            targetWalkaboutRatio = validateIntFromText(walksboutsRatioText, 0, 20);
        } catch (Exception e){
            JOptionPane.showConfirmDialog(null, "Please enter a value between 0 and 20", "Target Walkabout Ratio Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        SimulationSettings.WANTED_WALKABOUT_RATIO = targetWalkaboutRatio;

        SimulationSettings.SAVE_GENERATIONS = saveGenerationsCheckBox.isSelected();
        SimulationSettings.PUSH_DATA_TO_WEB = pushDataToWebCheckBox.isSelected();

        SimulationSettings.OUTPUT_FILE_NAME = outputFileNameText.getText();

        //CREATE THE NEW WORLD
        parent.setArtificialLife(new ArtificialLife(parent));

        //CLOSE THE FORM
        this.setVisible(false);
    }

    public int validateIntFromText(JTextField text, int min, int max) throws Exception{
        int res;

        res = Integer.parseInt(text.getText());

        if(res < min || res > max){
            throw new Exception();
        } else {
            return res;
        }
    }

    public double valudateDoubleFromText(JTextField text, double min, double max) throws Exception{
        double res;

        res = Double.parseDouble(text.getText());

        if(res < min || res > max){
            throw new Exception();
        } else {
            return res;
        }
    }


    public NewSimulation(MainGui parent) {
        this.parent = parent;
        setResizable(false);
        setTitle("Create a new simulation");
        mainPane = new JPanel();
        setContentPane(mainPane);
        mainPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //WORLD OPTIONS PANE
        worldOptions.setBorder(new TitledBorder(null, "World Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        mainPane.add(worldOptions, gbc);
        worldOptions.setLayout(new GridLayout(6, 6));


        worldWidthLabel.setToolTipText("The width of the world");
        worldOptions.add(worldWidthLabel);

        worldWidthText = new JTextField();
        worldWidthText.setText("100");
        worldOptions.add(worldWidthText);
        worldWidthText.setToolTipText("The widht of the world");
        worldWidthText.setColumns(10);
        worldWidthText.setBackground(Color.WHITE);


        worldHeightLabel.setToolTipText("The height of the world");
        worldOptions.add(worldHeightLabel);

        worldHeightText = new JTextField();
        worldHeightText.setText("100");
        worldOptions.add(worldHeightText);
        worldHeightText.setToolTipText("The height of the world");
        worldHeightText.setColumns(10);
        worldHeightText.setBackground(Color.WHITE);


        treesCheckBox.setSelected(true);
        treesCheckBox.setToolTipText("Do you want to generate trees?");
        worldOptions.add(treesCheckBox);


        waterCheckBox.setSelected(true);
        waterCheckBox.setToolTipText("Do you want to generate water?");
        worldOptions.add(waterCheckBox);


        deadTreeCheckBox.setSelected(true);
        deadTreeCheckBox.setToolTipText("Do you want to generate dead trees?");
        worldOptions.add(deadTreeCheckBox);


        smallRockCheckBox.setSelected(true);
        smallRockCheckBox.setToolTipText("Do you want to generate small rocks?");
        worldOptions.add(smallRockCheckBox);


        largeRockCheckBox.setSelected(true);
        largeRockCheckBox.setToolTipText("Do you want to generate large rocks?");
        worldOptions.add(largeRockCheckBox);


        grassCheckBox.setSelected(true);
        grassCheckBox.setToolTipText("Do you want to generate grass?");
        worldOptions.add(grassCheckBox);


        worldNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        worldNameLabel.setToolTipText("The name of the world");
        worldOptions.add(worldNameLabel);

        worldNameText = new JTextField();
        worldNameText.setText("NewWorld");
        worldNameText.setToolTipText("The name of the world");
        worldNameText.setColumns(10);
        worldNameText.setBackground(Color.WHITE);
        worldOptions.add(worldNameText);

        minimunFoodOnMapLabel.setHorizontalAlignment(SwingConstants.LEFT);
        minimunFoodOnMapLabel.setToolTipText("The minimum number of food on the world");
        worldOptions.add(minimunFoodOnMapLabel);

        minimumFoodOnMapText = new JTextField();
        minimumFoodOnMapText.setText(String.valueOf(SimulationSettings.MINIMUM_FOOD_ON_MAP));
        minimumFoodOnMapText.setToolTipText("The minimum number of food on the world");
        minimumFoodOnMapText.setColumns(10);
        minimumFoodOnMapText.setBackground(Color.WHITE);
        worldOptions.add(minimumFoodOnMapText);

        maximumFoodOnMapLabel.setHorizontalAlignment(SwingConstants.LEFT);
        maximumFoodOnMapLabel.setToolTipText("The maximum number of food on the world");
        worldOptions.add(maximumFoodOnMapLabel);

        maximumFoodOnMapText = new JTextField();
        maximumFoodOnMapText.setText(String.valueOf(SimulationSettings.MAXIMUM_FOOD_ON_MAP));
        maximumFoodOnMapText.setToolTipText("The maximum number of food on the world");
        maximumFoodOnMapText.setColumns(10);
        maximumFoodOnMapText.setBackground(Color.WHITE);
        worldOptions.add(maximumFoodOnMapText);

        minimunWaterOnMapLabel.setHorizontalAlignment(SwingConstants.LEFT);
        minimunWaterOnMapLabel.setToolTipText("The minimum number of water on the world");
        worldOptions.add(minimunWaterOnMapLabel);

        minimumWaterOnMapText = new JTextField();
        minimumWaterOnMapText.setText(String.valueOf(SimulationSettings.MINIMUM_WATER_ON_MAP));
        minimumWaterOnMapText.setToolTipText("The minimum number of water on the world");
        minimumWaterOnMapText.setColumns(10);
        minimumWaterOnMapText.setBackground(Color.WHITE);
        worldOptions.add(minimumWaterOnMapText);

        maximumWaterOnMapLabel.setHorizontalAlignment(SwingConstants.LEFT);
        maximumWaterOnMapLabel.setToolTipText("The maximum number of water on the world");
        worldOptions.add(maximumWaterOnMapLabel);

        maximumWaterOnMapText = new JTextField();
        maximumWaterOnMapText.setText(String.valueOf(SimulationSettings.MAXIMUM_WATER_ON_MAP));
        maximumWaterOnMapText.setToolTipText("The maximum number of water on the world");
        maximumWaterOnMapText.setColumns(10);
        maximumWaterOnMapText.setBackground(Color.WHITE);
        worldOptions.add(maximumWaterOnMapText);


        //WEATHER OPTIONS PANE
        weatherOptions.setBorder(new TitledBorder(null, "Weather Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;

        mainPane.add(weatherOptions, gbc);
        weatherOptions.setLayout(new GridLayout(1, 2));

        rainCheckBox.setSelected(true);
        rainCheckBox.setToolTipText("Do you want rain?");
        weatherOptions.add(rainCheckBox);


        snowCheckBox.setSelected(true);
        snowCheckBox.setToolTipText("Do you want snow?");
        weatherOptions.add(snowCheckBox);

        //SIMULATION OPTIONS
        simulationOptions.setBorder(new TitledBorder(null, "Simulation Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        mainPane.add(simulationOptions, gbc);
        simulationOptions.setLayout(new GridLayout(1, 4));

        daysLabel.setToolTipText("The number of days to simulate");
        simulationOptions.add(daysLabel);

        daysTextField = new JTextField();
        daysTextField.setToolTipText("The number of days to simulate");
        daysTextField.setText(String.valueOf(SimulationSettings.NUM_DAYS));
        simulationOptions.add(daysTextField);
        daysTextField.setColumns(10);

        speedLabel.setToolTipText("The initial speed");
        simulationOptions.add(speedLabel);

        speedText = new JTextField();
        speedText.setToolTipText("The initial speed");
        speedText.setText(String.valueOf(SimulationSettings.INIT_SPEED));
        speedText.setColumns(10);
        simulationOptions.add(speedText);


        //TREE OPTIONS
        treeOptionsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Genetic Programming Tree Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        mainPane.add(treeOptionsPanel, gbc);
        treeOptionsPanel.setLayout(new GridLayout(1, 2));

        startTreeDepthLabel.setToolTipText("The initial depth of the trees");
        treeOptionsPanel.add(startTreeDepthLabel);

        startTreeDepthText = new JTextField();
        startTreeDepthText.setToolTipText("The initial depth of the trees");
        startTreeDepthText.setText(String.valueOf(SimulationSettings.START_TREE_DEPTH));
        startTreeDepthText.setColumns(10);
        treeOptionsPanel.add(startTreeDepthText);

        maxTreeDepthLabel.setToolTipText("The maximum depth of the trees");
        treeOptionsPanel.add(maxTreeDepthLabel);

        maxTreeDepthTextField = new JTextField();
        maxTreeDepthTextField.setToolTipText("The maximum depth of the trees");
        maxTreeDepthTextField.setText(String.valueOf(SimulationSettings.MAX_TREE_DEPTH));
        maxTreeDepthTextField.setColumns(10);
        treeOptionsPanel.add(maxTreeDepthTextField);

        constrainTreeCheckBox.setSelected(false);
        constrainTreeCheckBox.setToolTipText("Do you want to constrain the tree, or randomize them?");
        treeOptionsPanel.add(constrainTreeCheckBox);

        //ALGORITHM OPTIONS PANEL
        algorithmOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Genetic Programming Algorithm Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        mainPane.add(algorithmOptions, gbc);
        algorithmOptions.setLayout(new GridLayout(6, 6));

        populationSizeLabel.setToolTipText("The size of the population");
        algorithmOptions.add(populationSizeLabel);

        populationSizeText = new JTextField();
        populationSizeText.setToolTipText("The size of the population");
        populationSizeText.setText(String.valueOf(SimulationSettings.POPULATION_SIZE));
        populationSizeText.setColumns(10);
        algorithmOptions.add(populationSizeText);

        whenToEvolveLabel.setToolTipText("When to evolve the population");
        algorithmOptions.add(whenToEvolveLabel);

        whenToEvolveText = new JTextField();
        whenToEvolveText.setToolTipText("When to evolve the population");
        whenToEvolveText.setText(String.valueOf(SimulationSettings.WHEN_TO_MUTATE));
        whenToEvolveText.setColumns(10);
        algorithmOptions.add(whenToEvolveText);

        eliteSelectionRateLabel.setToolTipText("The percentage of the population to save");
        algorithmOptions.add(eliteSelectionRateLabel);

        eliteSelectionText = new JTextField();
        eliteSelectionText.setToolTipText("The percentage of the population to save");
        eliteSelectionText.setText(String.valueOf(SimulationSettings.ELITE_SELECTION_RATE));
        eliteSelectionText.setColumns(10);
        algorithmOptions.add(eliteSelectionText);

        rivalsSelectionRateLabel.setToolTipText("The percentage of the population to select (rivals)");
        algorithmOptions.add(rivalsSelectionRateLabel);

        rivalsSelectionText = new JTextField();
        rivalsSelectionText.setToolTipText("The percentage of the population to select (rivals)");
        rivalsSelectionText.setText(String.valueOf(SimulationSettings.RIVALS_SELECTION_RATE));
        rivalsSelectionText.setColumns(10);
        algorithmOptions.add(rivalsSelectionText);

        averageSelectionRateLabel.setToolTipText("The percentage of the population to select (underdogs)");
        algorithmOptions.add(averageSelectionRateLabel);

        averageSelectionText = new JTextField();
        averageSelectionText.setToolTipText("The percentage of the population to select (underdogs)");
        averageSelectionText.setText(String.valueOf(SimulationSettings.AVERAGE_SELECTION_RATE));
        averageSelectionText.setColumns(10);
        algorithmOptions.add(averageSelectionText);

        mutationRateLabel.setToolTipText("The percentage chance of mutation");
        algorithmOptions.add(mutationRateLabel);

        mutationRateText = new JTextField();
        mutationRateText.setToolTipText("The percentage chance of mutation");
        mutationRateText.setText(String.valueOf(SimulationSettings.MUTATION_RATE));
        mutationRateText.setColumns(10);
        algorithmOptions.add(mutationRateText);

        waterDrunkRatioLabel.setToolTipText("The number of times you want the life form to drink");
        algorithmOptions.add(waterDrunkRatioLabel);

        waterDrunkRatioText = new JTextField();
        waterDrunkRatioText.setToolTipText("The number of times you want the life form to drink");
        waterDrunkRatioText.setText(String.valueOf(SimulationSettings.WANTED_WATER_RATIO));
        waterDrunkRatioText.setColumns(10);
        algorithmOptions.add(waterDrunkRatioText);

        foodEatenRatioLabel.setToolTipText("The number of times you want the life form to eat");
        algorithmOptions.add(foodEatenRatioLabel);

        foodEathenRatioText = new JTextField();
        foodEathenRatioText.setToolTipText("The number of times you want the life form to eat");
        foodEathenRatioText.setText(String.valueOf(SimulationSettings.WANTED_FOOD_RATIO));
        foodEathenRatioText.setColumns(10);
        algorithmOptions.add(foodEathenRatioText);

        sleepsTakenRatioLabel.setToolTipText("The number of times you want the life form to sleep");
        algorithmOptions.add(sleepsTakenRatioLabel);

        sleepesTakenRatioText = new JTextField();
        sleepesTakenRatioText.setToolTipText("The number of times you want the life form to sleep");
        sleepesTakenRatioText.setText(String.valueOf(SimulationSettings.WANTED_SLEEP_RATIO));
        sleepesTakenRatioText.setColumns(10);
        algorithmOptions.add(sleepesTakenRatioText);

        walkaboutsRatioLabel.setToolTipText("The number of times you want the life form to walkabout");
        algorithmOptions.add(walkaboutsRatioLabel);

        walksboutsRatioText = new JTextField();
        walksboutsRatioText.setToolTipText("The number of times you want the life form to walkabout");
        walksboutsRatioText.setText(String.valueOf(SimulationSettings.WANTED_WALKABOUT_RATIO));
        walksboutsRatioText.setColumns(10);
        algorithmOptions.add(walksboutsRatioText);


        //DATA PANEL
        dataOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1;
        mainPane.add(dataOptions, gbc);
        dataOptions.setLayout(new GridLayout(3, 3));

        saveGenerationsCheckBox.setSelected(true);
        saveGenerationsCheckBox.setToolTipText("Do you want to save the generations?");
        dataOptions.add(saveGenerationsCheckBox);

        pushDataToWebCheckBox.setSelected(true);
        pushDataToWebCheckBox.setToolTipText("Do you want to push the data to the website");
        dataOptions.add(pushDataToWebCheckBox);

        outputFileNameLabel.setToolTipText("The name of the generations output xml file");
        dataOptions.add(outputFileNameLabel);

        outputFileNameText = new JTextField();
        outputFileNameText.setToolTipText("The name of the generations output xml file");
        outputFileNameText.setText(String.valueOf(SimulationSettings.OUTPUT_FILE_NAME));
        outputFileNameText.setColumns(10);
        dataOptions.add(outputFileNameText);

        outputFilePathLabel.setText("Output File Path: " + SimulationSettings.OUPUT_FILE_PATH);
        outputFilePathLabel.setToolTipText("The location of the generations xml file");
        dataOptions.add(outputFilePathLabel);

        createButton.addActionListener(this);
        createButton.setToolTipText("Create the simulation");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 10, 10);
        gbc.weightx = 1;
        mainPane.add(createButton, gbc);


        restoreDefaultsButton.setToolTipText("Restore the default options");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 0);
        gbc.weightx = 1;
        mainPane.add(restoreDefaultsButton, gbc);

        pack();
    }
}
