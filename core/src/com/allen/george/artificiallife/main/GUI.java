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

/**
 * Created by George on 22/06/2014.
 */
public class GUI extends JFrame implements ActionListener {

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

    public void setFPS(String fs){
        fps.setText("FPS: " + fs);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - George Allen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        file = new JMenu();
        exit = new JMenuItem();
        help = new JMenu();
        about = new JMenuItem();
        swingPanelLeft = new JPanel();
        swingPanelRight = new JPanel();
        openglPanel = new JPanel();
        swingPanelBottom = new JPanel();
        fps = new JLabel();

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
                swingPanelLeft.setLayout(new GridLayout());
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

                LwjglAWTCanvas canvas = new LwjglAWTCanvas(new ArtificialLife(this));
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
                fps.setBounds(new Rectangle(5, 5, 100, 100));

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
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem exit;
    private JMenu help;
    private JMenuItem about;
    private JPanel swingPanelLeft;
    private JPanel swingPanelRight;
    private JPanel openglPanel;
    private JPanel swingPanelBottom;
    private JLabel fps;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
