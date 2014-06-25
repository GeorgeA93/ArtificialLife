/*
 * Created by JFormDesigner on Mon Jun 23 01:13:00 BST 2014
 */

package com.allen.george.artificiallife.main;

import javax.swing.border.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by George on 22/06/2014.
 */
public class GUI extends JFrame implements ActionListener, ChangeListener {


    //frame size
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 600;
    //opengl canvas size
    public static final int RENDER_WIDTH = 705;
    public static final int RENDER_HEIGHT = 400;


    public GUI() {
        initComponents();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")) {
            Gdx.app.exit();
        } else if (e.getActionCommand().equals("About")) {

        } else if (e.getActionCommand().equals("New")){

        } else if(e.getActionCommand().equals("Running")){
            if(!artificialLife.isRunning()){
                artificialLife.setRunning(true);
            } else{
                artificialLife.setRunning(false);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner mySpinner = (JSpinner)(e.getSource());
        SpinnerModel myModel = mySpinner.getModel();
        int num = ((SpinnerNumberModel) myModel).getNumber().intValue();
        double speed = num * 0.0001;
        artificialLife.getWorld().getDayNightCycler().setTimeSpeed(speed);
    }

    public void setFPS(String fs){
        TitledBorder title;
        title = BorderFactory.createTitledBorder("FPS: " + fs);
        openglPanel.setBorder(title);
    }

    public void setCurrentCycle(int c){
        cyclesLabel.setText("Current Cycle: " + c);
    }

    public void setTime(String t){
        labelTime.setText("Time: " + t);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - George Allen
        menuBar = new JMenuBar();
        file = new JMenu();
        newMenuItem = new JMenuItem();
        newMenuItem.addActionListener(this);
        exitMenuItem = new JMenuItem();
        exitMenuItem.addActionListener(this);
        help = new JMenu();
        help.addActionListener(this);
        about = new JMenuItem();
        about.addActionListener(this);
        swingPanelLeft = new JPanel();
        cycleSpeedSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 200, 1));
        cycleSpeedSpinner.addChangeListener(this);
        spinnerLabel = new JLabel();
        swingPanelRight = new JPanel();
        openglPanel = new JPanel();
        swingPanelBottom = new JPanel();
        artificialLife = new ArtificialLife(this);
        runningRadioBtn = new JRadioButton();
        runningRadioBtn.setActionCommand("Running");
        runningRadioBtn.addActionListener(this);
        labelTime = new JLabel();
        cyclesLabel = new JLabel();
        panel1 = new JPanel();

        //======== GUI ========
        {
            setTitle("Artificial Life");
            setBackground(UIManager.getColor("Panel.background"));
            Container GUIContentPane = getContentPane();
            GUIContentPane.setLayout(null);

            //======== menuBar ========
            {

                //======== file ========
                {
                    file.setText("File");

                    //---- newMenuItem ----
                    newMenuItem.setText("New");
                    file.add(newMenuItem);

                    //---- exitMenuItem ----
                    exitMenuItem.setText("Exit");
                    file.add(exitMenuItem);
                }
                menuBar.add(file);

                //======== help ========
                {
                    help.setText("Help");

                    //---- about ----
                    about.setText("About");
                    help.add(about);
                }
                menuBar.add(help);
            }
            setJMenuBar(menuBar);

            //======== swingPanelLeft ========
            {
                swingPanelLeft.setBorder(new TitledBorder("Simluation Controls"));

                swingPanelLeft.setLayout(null);
                swingPanelLeft.add(cycleSpeedSpinner);
                cycleSpeedSpinner.setBounds(new Rectangle(new Point(90, 65), cycleSpeedSpinner.getPreferredSize()));

                //---- spinnerLabel ----
                spinnerLabel.setText("Cycle Speed");
                swingPanelLeft.add(spinnerLabel);
                spinnerLabel.setBounds(new Rectangle(new Point(20, 70), spinnerLabel.getPreferredSize()));

                //---- runningRadioBtn ----
                runningRadioBtn.setText("Running");
                swingPanelLeft.add(runningRadioBtn);
                runningRadioBtn.setBounds(new Rectangle(new Point(10, 30), runningRadioBtn.getPreferredSize()));

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < swingPanelLeft.getComponentCount(); i++) {
                        Rectangle bounds = swingPanelLeft.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = swingPanelLeft.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    swingPanelLeft.setMinimumSize(preferredSize);
                    swingPanelLeft.setPreferredSize(preferredSize);
                }
            }
            GUIContentPane.add(swingPanelLeft);
            swingPanelLeft.setBounds(0, 310, 150, 220);

            //======== swingPanelRight ========
            {
                swingPanelRight.setLayout(new GridLayout());
            }
            GUIContentPane.add(swingPanelRight);
            swingPanelRight.setBounds(855, 0, 150, 535);

            //======== openglPanel ========
            {
                openglPanel.setBorder(new TitledBorder("FPS:"));
                openglPanel.setLayout(new GridLayout());
                Container openglContainer = new Container();

                LwjglAWTCanvas canvas = new LwjglAWTCanvas(artificialLife);
                canvas.getCanvas().setSize(RENDER_WIDTH, RENDER_HEIGHT);

                openglContainer.add(canvas.getCanvas());

                openglPanel.add(openglContainer);
            }
            GUIContentPane.add(openglPanel);
            openglPanel.setBounds(150, 0, RENDER_WIDTH, RENDER_HEIGHT);

            //======== swingPanelBottom ========
            {
                swingPanelBottom.setBorder(new TitledBorder("Simulation Information"));
                swingPanelBottom.setLayout(null);

                //---- labelTime ----
                labelTime.setText("Time:");
                swingPanelBottom.add(labelTime);
                labelTime.setBounds(15, 25, 80, labelTime.getPreferredSize().height);

                //---- cyclesLabel ----
                cyclesLabel.setText("Current Cycle: ");
                swingPanelBottom.add(cyclesLabel);
                cyclesLabel.setBounds(15, 45, 185, cyclesLabel.getPreferredSize().height);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < swingPanelBottom.getComponentCount(); i++) {
                        Rectangle bounds = swingPanelBottom.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = swingPanelBottom.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    swingPanelBottom.setMinimumSize(preferredSize);
                    swingPanelBottom.setPreferredSize(preferredSize);
                }
            }
            GUIContentPane.add(swingPanelBottom);
            swingPanelBottom.setBounds(150, 400, 705, 130);

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder("Evolutution Controls"));
                panel1.setLayout(null);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            GUIContentPane.add(panel1);
            panel1.setBounds(0, 0, 150, 305);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < GUIContentPane.getComponentCount(); i++) {
                    Rectangle bounds = GUIContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = GUIContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                GUIContentPane.setMinimumSize(preferredSize);
                GUIContentPane.setPreferredSize(preferredSize);
            }
            pack();
            setLocationRelativeTo(getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - George Allen
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem newMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu help;
    private JMenuItem about;
    private JPanel swingPanelLeft;
    private JSpinner cycleSpeedSpinner;
    private JLabel spinnerLabel;
    private JRadioButton runningRadioBtn;
    private JPanel swingPanelRight;
    private JPanel openglPanel;
    private JPanel swingPanelBottom;
    private JLabel labelTime;
    private JLabel cyclesLabel;
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private ArtificialLife artificialLife;
}
