package com.allen.george.artificiallife.main.forms;

import com.allen.george.artificiallife.main.ArtificialLife;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
        } else if (e.getActionCommand().equals("Load Map")){
            artificialLife.loadMap();
        } else if (e.getActionCommand().equals("Save Map")){
            artificialLife.saveMap();
        } else if (e.getActionCommand().equals("Running")) {
            if (!artificialLife.isRunning()) {
                artificialLife.setRunning(true);
            } else {
                artificialLife.setRunning(false);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            int val = source.getValue();
            double speed = val * 0.0001;
            artificialLife.getWorld().getDayNightCycler().setTimeSpeed(speed);
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
        title = BorderFactory.createTitledBorder("FPS: " + fs);
        openglPanel.setBorder(title);
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

    /**
     * Create the frame.
     */
    public MainGui() {

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

        mainPane = new JPanel();
        setContentPane(mainPane);
        mainPane.setLayout(new BorderLayout(1, 1));

        evolutionControls.setBorder(new TitledBorder(null,
                "Evolution Controls", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        mainPane.add(evolutionControls, BorderLayout.WEST);
        evolutionControls.setLayout(new GridLayout(0, 2, 0, 0));

        evolutionControls.add(placeHolder1);

        openglPanel.setBorder(new TitledBorder(null, "FPS: ",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));

        openglContainer = new Container();

        artificialLife = new ArtificialLife(this);
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

        runningRadio.setToolTipText("Turn the simulation on/off");
        runningRadio.setSelected(false);
        runningRadio.setActionCommand("Running");
        runningRadio.addActionListener(this);
        simulationControls.add(runningRadio);

        dayLabel.setToolTipText("The current day");
        simulationControls.add(dayLabel);

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

        dataPanel.setBorder(new TitledBorder(null, "Data...",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        mainPane.add(dataPanel, BorderLayout.EAST);
        dataPanel.setLayout(new GridLayout(0, 2, 0, 0));

        dataPanel.add(placeHolder2);
    }

    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    private JMenuItem newMenuItem = new JMenuItem("New");
    private JMenuItem loadMenutItem = new JMenuItem("Load Map");
    private JMenuItem saveMenuItem = new JMenuItem("Save Map");
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem aboutMenuItem = new JMenuItem("About");
    private JPanel evolutionControls = new JPanel();
    private JLabel placeHolder1 = new JLabel("placeHolder");
    private JPanel openglPanel = new JPanel();
    private JPanel simulationControls = new JPanel();
    private JLabel timeLabel = new JLabel("Time: ");
    private JRadioButton runningRadio = new JRadioButton("Running");
    private JLabel dayLabel = new JLabel("Day:");
    private JSlider simulationSpeed = new JSlider();
    private JLabel weatherLabel = new JLabel("Weather:");
    private JPanel dataPanel = new JPanel();
    private JLabel placeHolder2 = new JLabel("placeHolder");

    private ArtificialLife artificialLife;
    private LwjglAWTCanvas canvas;
    private Container openglContainer;

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

}
