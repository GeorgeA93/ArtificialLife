package com.allen.george.artificiallife.main.forms;

import com.allen.george.artificiallife.data.CustomDataSet;
import com.allen.george.artificiallife.data.FitnessOverGenerations;
import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.allen.george.artificiallife.utils.UserData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by George on 16/07/2014.
 */
public class MainGui extends JFrame implements ActionListener, ChangeListener, ComponentListener {


    private JPanel mainPane;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            Gdx.app.exit();
        } else if (e.getActionCommand().equals("About")) {

        } else if (e.getActionCommand().equals("New")) {
            newSimulation.setVisible(true);
        } else if (e.getActionCommand().equals("Load Simulation")){
            String filePathToLoad = getFilePath();
            if(!filePathToLoad.equals("")){
                artificialLife.load(filePathToLoad);
            }

        } else if (e.getActionCommand().equals("Save Simulation Every Generation")){
            if(saveMenuItem.isSelected()){
                saveMenuItem.setSelected(false);
                SimulationSettings.SAVE_GENERATIONS = false;
            } else {
                saveMenuItem.setSelected(true);
                SimulationSettings.SAVE_GENERATIONS = true;
            }
        } else if (e.getActionCommand().equals("Use Blending")){
            if(useBlendingMenuItem.isSelected()){
                useBlendingMenuItem.setSelected(false);
                artificialLife.setUseBlending(false);
            } else {
                useBlendingMenuItem.setSelected(true);
                artificialLife.setUseBlending(true);
            }

        } else if (e.getActionCommand().equals("Running")) {
            if (!artificialLife.isRunning()) {
                artificialLife.setRunning(true);
            } else {
                artificialLife.setRunning(false);
            }
        } else if(e.getActionCommand().equals("Render")){
            if(renderMenuItem.isSelected()){
                renderMenuItem.setSelected(false);
                artificialLife.setRender(false);
            } else {
                renderMenuItem.setSelected(true);
                artificialLife.setRender(true);
            }
        } else if(e.getActionCommand().equals("Change User")){

            final Properties properties = new Properties();
            InputStream inputStream =  null;

            try{
                inputStream = new FileInputStream("user.properties");
                properties.load(inputStream);

            } catch(IOException ex){
                ex.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
            }

            this.setVisible(false);
            LoginForm g = new LoginForm(properties, this);
            g.setVisible(true);
        }
    }

    private String getFilePath(){
        JFileChooser chooser = new JFileChooser(); //the JFileChooser
        chooser.setCurrentDirectory(new java.io.File(".")); //Set to look in the project root directory
        chooser.setDialogTitle("Choose a .sim file");		//Set title of the File Chooser
        chooser.setAcceptAllFileFilterUsed(false); //dont accept all file filters
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {		//When the user clicks the load/open button
                return chooser.getSelectedFile().getAbsolutePath(); //get the path
        }
        return "";
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() instanceof  JSlider){
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int val = source.getValue();
                double speed = val * 0.0001;
                artificialLife.getWorld().getDayNightCycler().setTimeSpeed(speed);
            }
        } else {
            JSpinner sp = ((JSpinner)e.getSource());
            Integer value = (Integer)sp.getValue();
            artificialLife.getWorld().getGeneticEngine().setGeneration(value);
        }

    }

    public void componentResized(ComponentEvent e){
        resizeOpenGL();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void setFPS(String fs) {
        TitledBorder title;
        String blendingText = "";
        String savingText = "";
        if(useBlendingMenuItem.isSelected()){
            blendingText = "    Blending Enabled";
        } else {
            blendingText = "    Blending Disabled";
        }
        if(saveMenuItem.isSelected()){
            savingText = "      Saving Enabled";
        } else {
            savingText = "      Saving Disabled";
        }

        title = BorderFactory.createTitledBorder("FPS: " + fs +  blendingText + savingText);
        openglPanel.setBorder(title);
    }

    private SpinnerModel spinnerModel =  new SpinnerNumberModel(0, //initial value
            0, //min
            10000, //max
            1);//step
    private JSpinner spinner = new JSpinner(spinnerModel);

    public void setEvolutionPanelData(boolean oldSim){
        if(oldSim){
            spinner.setBorder(new TitledBorder("Skip Generations"));
            spinner.setVisible(true);
            spinner.setValue(artificialLife.getWorld().getGeneticEngine().getGeneration());
            populationSizeLabel.setText("Population Size: " + SimulationSettings.POPULATION_SIZE);
            simulationNameLabel.setText("Simulation Name: " + SimulationSettings.WORLD_NAME);
            mutationRateLabel.setText("Current Mutation Rate: " + SimulationSettings.MUTATION_RATE);
            crossoverRateLabel.setText("Current Crossover Rate: " + SimulationSettings.CROSSOVER_RATE);
            maxGenerations.setText("Maximum Generations: " + SimulationSettings.NUM_DAYS);
        } else {
           populationSizeLabel.setText("Population Size: " + SimulationSettings.POPULATION_SIZE);
           simulationNameLabel.setText("Simulation Name: " + SimulationSettings.WORLD_NAME);
           mutationRateLabel.setText("Current Mutation Rate: " + SimulationSettings.MUTATION_RATE);
           crossoverRateLabel.setText("Current Crossover Rate: " + SimulationSettings.CROSSOVER_RATE);
           maxGenerations.setText("Maximum Generations: " + SimulationSettings.NUM_DAYS);
           spinner.setVisible(false);
        }
    }



    public void setCurrentCycle(int c) {
        dayLabel.setText("Day: " + c);
    }

    public void setTime(String t) {
        timeLabel.setText("Time: " + t);
    }

    public void setWeather(String w) {
        weatherLabel.setText("Weather: " + w);
    }

    public void resizeOpenGL(){
        canvas.getCanvas().setSize((int)openglPanel.getSize().getWidth(), (int)openglPanel.getSize().getHeight());
    }

    public void setRunningRadio(boolean res){
        this.runningRadio.setSelected(res);
    }

    public void setSimulationSpeed(int speed){
        this.simulationSpeed.setValue(speed);
    }

    public void setLifeFormInformation(String function, int energy, float thirst, float hunger){
        this.lifeFormStateLabel.setText("Life Form State: " + function);
        this.lifeFormEnergyLabel.setText("Life Form Energy: " + energy);
        this.lifeFormThirstLabel.setText("Life Form Thirst: " + thirst);
        this.lifeFormHungerLabel.setText("Life Form Hunger: " + hunger);

    }

    private UserData userData;

    public UserData getUserData(){
        return this.userData;
    }

    /**
     * Create the frame.
     */
    public MainGui(String[] arguments) {

        if(arguments == null){
            return;
        } else {
            String username = "";
            for(String s : arguments){
                if(s.contains("Username")){
                    if(s.split("=").length == 1) return;
                    username = s.split("=")[1];
                }
            }
            if(username.equals("")) return;

            userData = new UserData(username);
        }

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            // handle exception
        }



        addComponentListener(this);

        setTitle("Artificial Life v0.2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 680, 420);

        setJMenuBar(menuBar);

        menuBar.add(fileMenu);
        saveMenuItem.setSelected(true);
        newMenuItem.addActionListener(this);
        loadMenutItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        fileMenu.add(newMenuItem);
        fileMenu.add(loadMenutItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        menuBar.add(helpMenu);

        aboutMenuItem.addActionListener(this);
        helpMenu.add(aboutMenuItem);

        menuBar.add(settingsMenu);
        useBlendingMenuItem.addActionListener(this);
        renderMenuItem.addActionListener(this);
        changeUserItem.addActionListener(this);
        settingsMenu.add(useBlendingMenuItem);
        settingsMenu.add(renderMenuItem);
        settingsMenu.add(changeUserItem);

        mainPane = new JPanel();
        setContentPane(mainPane);
        mainPane.setLayout(new BorderLayout(1, 1));

        evolutionControls.setBorder(new TitledBorder(null,
                "Evolution Settings", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        mainPane.add(evolutionControls, BorderLayout.WEST);
        evolutionControls.setLayout(new GridLayout(0, 1, 0, 0));
        evolutionControls.add(simulationNameLabel);
        evolutionControls.add(maxGenerations);
        evolutionControls.add(populationSizeLabel);
        evolutionControls.add(mutationRateLabel);
        evolutionControls.add(crossoverRateLabel);
        spinner.addChangeListener(this);
        spinner.setVisible(false);
        evolutionControls.add(spinner);

        openglPanel.setBorder(new TitledBorder(null, "FPS: ",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        openglContainer = new Container();



        artificialLife = new ArtificialLife(this);
        LwjglApplicationConfiguration.disableAudio = true;
        canvas = new LwjglAWTCanvas(artificialLife);
        canvas.getCanvas().setSize(0, 0);

        openglContainer.add(canvas.getCanvas());

        openglPanel.add(openglContainer);

        mainPane.add(openglPanel);
        openglPanel.setLayout(new GridLayout(1, 0, 0, 0));

        simulationControls.setBorder(new TitledBorder(UIManager
                .getBorder("TitledBorder.border"), "Simulation",
                TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        mainPane.add(simulationControls, BorderLayout.SOUTH);
        simulationControls.setLayout(new GridLayout(3, 5, 0, 0));

        timeLabel.setToolTipText("The current time");
        simulationControls.add(timeLabel);

        lifeFormStateLabel.setToolTipText("The selected life forms current state");
        simulationControls.add(lifeFormStateLabel);

        runningRadio.setToolTipText("Turn the simulation on/off");
        runningRadio.setSelected(false);
        runningRadio.setActionCommand("Running");
        runningRadio.addActionListener(this);
        simulationControls.add(runningRadio);

        dayLabel.setToolTipText("The current day");
        simulationControls.add(dayLabel);

        lifeFormEnergyLabel.setToolTipText("The selected life forms current energy");
        simulationControls.add(lifeFormEnergyLabel);

        simulationSpeed.setBorder(new TitledBorder(null, "Simulation Speed",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        simulationSpeed.setSnapToTicks(true);
        simulationSpeed.setValue(1);
        simulationSpeed.setToolTipText("Slide to change simulation speed");
        simulationSpeed.setMaximum(200);
        simulationSpeed.setMinimum(1);
        simulationSpeed.addChangeListener(this);
       simulationControls.add(simulationSpeed);

        weatherLabel.setToolTipText("The current weather");
        simulationControls.add(weatherLabel);

        lifeFormHungerLabel.setToolTipText("The selected life forms current hunger");
        simulationControls.add(lifeFormHungerLabel);
        lifeFormThirstLabel.setToolTipText("The selected life forms current thirst");
        simulationControls.add(lifeFormThirstLabel);

        dataPanel.setBorder(new TitledBorder(null, "Data...",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        mainPane.add(dataPanel, BorderLayout.EAST);
        dataPanel.setLayout(new GridLayout(0, 1, 0, 0));
        dataPanel.add(averageChartPanel);
        dataPanel.add(worstChartPanel);
        dataPanel.add(bestChartPanel);
    }

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private JMenuItem newMenuItem = new JMenuItem("New");
    private JMenuItem loadMenutItem = new JMenuItem("Load Simulation");
    private JMenuItem saveMenuItem = new JMenuItem("Save Simulation Every Generation");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem aboutMenuItem = new JMenuItem("About");
    private JMenu settingsMenu = new JMenu("Settings");
    private JMenuItem useBlendingMenuItem = new JMenuItem("Use Blending");
    private JMenuItem renderMenuItem = new JMenuItem("Render");
    private JMenuItem changeUserItem = new JMenuItem("Change User");
    private JPanel evolutionControls = new JPanel();
    private JLabel placeHolder1 = new JLabel("placeHolder");
    private JPanel openglPanel = new JPanel();
    private JPanel simulationControls = new JPanel();
    private JLabel timeLabel = new JLabel("Time: ");
    private JRadioButton runningRadio = new JRadioButton("Running");
    private JLabel dayLabel = new JLabel("Day:");
    private JSlider simulationSpeed = new JSlider();
    private JLabel weatherLabel = new JLabel("Weather:");
    private JLabel lifeFormStateLabel = new JLabel("Life Form State: ");
    private JLabel lifeFormEnergyLabel = new JLabel("Life Form Energy: ");
    private JLabel lifeFormHungerLabel = new JLabel("Life Form Hunger: ");
    private JLabel lifeFormThirstLabel = new JLabel("Life Form Thirst: ");

    //Evolution Panel
    private JLabel populationSizeLabel = new JLabel("Population Size: ");
    private JLabel simulationNameLabel = new JLabel("Simulation Name: ");
    private JLabel mutationRateLabel = new JLabel("Current Mutation Rate: ");
    private JLabel crossoverRateLabel = new JLabel("Current Crossover Rate: ");
    private JLabel maxGenerations = new JLabel("Maximum Generations: ");



    private CustomDataSet customDataSet = new CustomDataSet();
    private JPanel dataPanel = new JPanel();
    private FitnessOverGenerations averageChartPanel = new FitnessOverGenerations(customDataSet, 0);
    private FitnessOverGenerations worstChartPanel = new FitnessOverGenerations(customDataSet, 1);
    private FitnessOverGenerations bestChartPanel = new FitnessOverGenerations(customDataSet, 2);


    private ArtificialLife artificialLife;
    private LwjglAWTCanvas canvas;
    private Container openglContainer;

    public void reset(){
        this.setArtificialLife(new ArtificialLife(this));
        this.setVisible(true);
    }

    //Forms
    private NewSimulation newSimulation = new NewSimulation(this);

    public ArtificialLife getArtificialLife(){
        return this.artificialLife;
    }

    public void setArtificialLife(ArtificialLife artificialLife){
       this.artificialLife = artificialLife;
        canvas = new LwjglAWTCanvas(artificialLife);
        canvas.getCanvas().setSize(0, 0);
        openglContainer.remove(0);
        openglContainer.add(canvas.getCanvas());
        openglPanel.remove(0);
        openglPanel.add(openglContainer);
        resizeOpenGL();
    }

    public CustomDataSet getCustomDataSet(){
        return this.customDataSet;
    }


    public void updateData(){
        //averageChartPanel.updateData(customDataSet, 0);
        //worstChartPanel.updateData(customDataSet, 1);
        //bestChartPanel.updateData(customDataSet, 2);


        //averageChartPanel = new FitnessOverGenerations(customDataSet, 0);
        //worstChartPanel = new FitnessOverGenerations(customDataSet, 1);
       // bestChartPanel = new FitnessOverGenerations(customDataSet, 2);
    }





}
