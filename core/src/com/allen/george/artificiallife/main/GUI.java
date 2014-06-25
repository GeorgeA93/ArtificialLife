/*
 * Created by JFormDesigner on Mon Jun 23 01:13:00 BST 2014
 */

package com.allen.george.artificiallife.main;

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
        fps.setText("FPS: " + fs);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - George Allen
        menuBar = new JMenuBar();
        file = new JMenu();
        exit = new JMenuItem();
        help = new JMenu();
        about = new JMenuItem();
        swingPanelLeft = new JPanel();
        cycleSpeedSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));
        spinnerLabel = new JLabel();
        separator1 = new JToolBar.Separator();
        swingPanelRight = new JPanel();
        openglPanel = new JPanel();
        swingPanelBottom = new JPanel();
        fps = new JLabel();
        artificialLife = new ArtificialLife(this);

        //======== GUI ========
        {
            setTitle("Artificial Life");
            Container GUIContentPane = getContentPane();
            GUIContentPane.setLayout(null);

            //======== menuBar ========
            {

                //======== file ========
                {
                    file.setText("File");

                    //---- exit ----
                    exit.setText("Exit");
                    file.add(exit);
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

                swingPanelLeft.setLayout(null);
                swingPanelLeft.add(cycleSpeedSpinner);
                cycleSpeedSpinner.setBounds(new Rectangle(new Point(100, 365), cycleSpeedSpinner.getPreferredSize()));

                //---- spinnerLabel ----
                spinnerLabel.setText("Cycle Speed");
                swingPanelLeft.add(spinnerLabel);
                spinnerLabel.setBounds(new Rectangle(new Point(25, 365), spinnerLabel.getPreferredSize()));
                swingPanelLeft.add(separator1);
                separator1.setBounds(5, 325, 140, 195);

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
            swingPanelLeft.setBounds(0, 0, 150, 535);

            //======== swingPanelRight ========
            {
                swingPanelRight.setLayout(new GridLayout());
            }
            GUIContentPane.add(swingPanelRight);
            swingPanelRight.setBounds(855, 0, 150, 535);

            //======== openglPanel ========
            {
                openglPanel.setLayout(new GridLayout());

                Container openglContainer = new Container();

                LwjglAWTCanvas canvas = new LwjglAWTCanvas(artificialLife);
                canvas.getCanvas().setSize(RENDER_WIDTH, RENDER_HEIGHT);

                openglContainer.add(canvas.getCanvas());

                openglPanel.add(openglContainer);
            }
            GUIContentPane.add(openglPanel);
            openglPanel.setBounds(150, 0, 705, 400);

            //======== swingPanelBottom ========
            {
                swingPanelBottom.setLayout(null);

                //---- fps ----
                fps.setText("FPS");
                swingPanelBottom.add(fps);
                fps.setBounds(new Rectangle(new Point(5, 5), fps.getPreferredSize()));

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
            swingPanelBottom.setBounds(150, 400, 705, 135);

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
    private ArtificialLife artificialLife;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem exit;
    private JMenu help;
    private JMenuItem about;
    private JPanel swingPanelLeft;
    private JSpinner cycleSpeedSpinner;
    private JLabel spinnerLabel;
    private JToolBar.Separator separator1;
    private JPanel swingPanelRight;
    private JPanel openglPanel;
    private JPanel swingPanelBottom;
    private JLabel fps;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
