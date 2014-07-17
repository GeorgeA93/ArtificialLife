package com.allen.george.artificiallife.main.forms;


import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.simulation.world.World;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.Gdx;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;


public class NewSimulation extends JFrame implements ActionListener{

    private JPanel mainPane;
    private JTextField worldHeightText;
    private JTextField worldWidthText;
    private JTextField worldNameText;
    private JTextField daysTextField;
    private JTextField speedText;
    private JPanel worldOptions = new JPanel();
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
    private  JPanel simulationOptions = new JPanel();
    private  JLabel daysLabel = new JLabel("Days:");
    private  JLabel speedLabel = new JLabel("Speed:");
    private JPanel evolutionOptions = new JPanel();
    private   JPanel dataOptions = new JPanel();
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
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 1000", "Days Input Error", JOptionPane.CANCEL_OPTION);
            return;
        }
        if(days < 1 || days > 1000){
            JOptionPane.showConfirmDialog(null, "Please enter numbers only, between 1 and 1000", " Days Input Error", JOptionPane.CANCEL_OPTION);
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

        //CREATE THE NEW WORLD
        parent.setArtificialLife(new ArtificialLife(parent));

        //CLOSE THE FORM
        this.setVisible(false);
    }


    public NewSimulation(MainGui parent) {
        this.parent = parent;
        setResizable(false);
        setTitle("Create a new simulation");
        setBounds(100, 100, 621, 408);
        mainPane = new JPanel();
        mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mainPane);
        mainPane.setLayout(null);

        worldOptions.setBorder(new TitledBorder(null, "World Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        worldOptions.setBounds(10, 11, 592, 200);
        mainPane.add(worldOptions);
        worldOptions.setLayout(null);


        worldWidthLabel.setToolTipText("The width of the world");
        worldWidthLabel.setBounds(10, 31, 89, 14);
        worldOptions.add(worldWidthLabel);


        worldHeightLabel.setToolTipText("The height of the world");
        worldHeightLabel.setBounds(10, 56, 89, 14);
        worldOptions.add(worldHeightLabel);

        worldWidthText = new JTextField();
        worldWidthText.setText("100");
        worldWidthText.setBounds(109, 28, 86, 20);
        worldOptions.add(worldWidthText);
        worldWidthText.setToolTipText("The widht of the world");
        worldWidthText.setColumns(10);
        worldWidthText.setBackground(Color.WHITE);

        worldHeightText = new JTextField();
        worldHeightText.setText("100");
        worldHeightText.setBounds(109, 53, 86, 20);
        worldOptions.add(worldHeightText);
        worldHeightText.setToolTipText("The height of the world");
        worldHeightText.setColumns(10);
        worldHeightText.setBackground(Color.WHITE);


        treesCheckBox.setSelected(true);
        treesCheckBox.setToolTipText("Do you want to generate trees?");
        treesCheckBox.setBounds(239, 27, 97, 23);
        worldOptions.add(treesCheckBox);


        waterCheckBox.setSelected(true);
        waterCheckBox.setToolTipText("Do you want to generate water?");
        waterCheckBox.setBounds(239, 52, 97, 23);
        worldOptions.add(waterCheckBox);


        deadTreeCheckBox.setSelected(true);
        deadTreeCheckBox.setToolTipText("Do you want to generate dead trees?");
        deadTreeCheckBox.setBounds(239, 78, 97, 23);
        worldOptions.add(deadTreeCheckBox);


        smallRockCheckBox.setSelected(true);
        smallRockCheckBox.setToolTipText("Do you want to generate small rocks?");
        smallRockCheckBox.setBounds(338, 27, 97, 23);
        worldOptions.add(smallRockCheckBox);


        largeRockCheckBox.setSelected(true);
        largeRockCheckBox.setToolTipText("Do you want to generate large rocks?");
        largeRockCheckBox.setBounds(338, 52, 97, 23);
        worldOptions.add(largeRockCheckBox);


        grassCheckBox.setSelected(true);
        grassCheckBox.setToolTipText("Do you want to generate grass?");
        grassCheckBox.setBounds(338, 78, 97, 23);
        worldOptions.add(grassCheckBox);


        worldNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        worldNameLabel.setToolTipText("The name of the world");
        worldNameLabel.setBounds(10, 82, 89, 14);
        worldOptions.add(worldNameLabel);

        worldNameText = new JTextField();
        worldNameText.setText("NewWorld");
        worldNameText.setToolTipText("The name of the world");
        worldNameText.setColumns(10);
        worldNameText.setBackground(Color.WHITE);
        worldNameText.setBounds(109, 81, 86, 20);
        worldOptions.add(worldNameText);


        weatherOptions.setBorder(new TitledBorder(null, "Weather Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        weatherOptions.setBounds(10, 221, 139, 110);
        mainPane.add(weatherOptions);
        weatherOptions.setLayout(null);


        rainCheckBox.setSelected(true);
        rainCheckBox.setToolTipText("Do you want rain?");
        rainCheckBox.setBounds(6, 21, 97, 23);
        weatherOptions.add(rainCheckBox);


        snowCheckBox.setSelected(true);
        snowCheckBox.setToolTipText("Do you want snow?");
        snowCheckBox.setBounds(6, 47, 97, 23);
        weatherOptions.add(snowCheckBox);


        simulationOptions.setBorder(new TitledBorder(null, "Simulation Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        simulationOptions.setBounds(159, 222, 139, 109);
        mainPane.add(simulationOptions);
        simulationOptions.setLayout(null);


        daysLabel.setToolTipText("The number of days to simulate");
        daysLabel.setBounds(10, 24, 46, 14);
        simulationOptions.add(daysLabel);

        daysTextField = new JTextField();
        daysTextField.setToolTipText("The number of days to simulate");
        daysTextField.setText("200");
        daysTextField.setBounds(51, 21, 78, 20);
        simulationOptions.add(daysTextField);
        daysTextField.setColumns(10);


        speedLabel.setToolTipText("The initial speed");
        speedLabel.setBounds(10, 52, 46, 14);
        simulationOptions.add(speedLabel);

        speedText = new JTextField();
        speedText.setToolTipText("The initial speed");
        speedText.setText("1");
        speedText.setColumns(10);
        speedText.setBounds(51, 49, 78, 20);
        simulationOptions.add(speedText);


        evolutionOptions.setLayout(null);
        evolutionOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Evolution Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        evolutionOptions.setBounds(307, 222, 139, 109);
        mainPane.add(evolutionOptions);


        dataOptions.setLayout(null);
        dataOptions.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Data Options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        dataOptions.setBounds(463, 222, 139, 109);
        mainPane.add(dataOptions);


        createButton.addActionListener(this);
        createButton.setToolTipText("Create the simulation");
        createButton.setBounds(513, 346, 89, 23);
        mainPane.add(createButton);


        restoreDefaultsButton.setToolTipText("Restore the default options");
        restoreDefaultsButton.setBounds(10, 346, 89, 23);
        mainPane.add(restoreDefaultsButton);
    }
}
