/*
 * Created by JFormDesigner on Wed Jun 25 17:55:45 BST 2014
 */

package com.allen.george.artificiallife.main.forms;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author George Allen
 */
public class NewSimulation extends JFrame{
    public NewSimulation() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - George Allen
        newSimulationForm = new JFrame();
        createButton = new JButton();
        randomizeButton = new JButton();

        //======== newSimulationForm ========
        {
            newSimulationForm.setTitle("Create New Simulation");
            newSimulationForm.setResizable(false);
            Container newSimulationFormContentPane = newSimulationForm.getContentPane();
            newSimulationFormContentPane.setLayout(null);

            //---- createButton ----
            createButton.setText("Create");
            createButton.setToolTipText("Press to create this simulation!");
            newSimulationFormContentPane.add(createButton);
            createButton.setBounds(new Rectangle(new Point(525, 365), createButton.getPreferredSize()));

            //---- randomizeButton ----
            randomizeButton.setText("Randomize");
            randomizeButton.setToolTipText("Press to randomize the world!");
            newSimulationFormContentPane.add(randomizeButton);
            randomizeButton.setBounds(new Rectangle(new Point(0, 365), randomizeButton.getPreferredSize()));

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < newSimulationFormContentPane.getComponentCount(); i++) {
                    Rectangle bounds = newSimulationFormContentPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = newSimulationFormContentPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                newSimulationFormContentPane.setMinimumSize(preferredSize);
                newSimulationFormContentPane.setPreferredSize(preferredSize);
            }
            newSimulationForm.pack();
            newSimulationForm.setLocationRelativeTo(newSimulationForm.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - George Allen
    private JFrame newSimulationForm;
    private JButton createButton;
    private JButton randomizeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
