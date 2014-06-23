package com.allen.george.artificiallife.main.desktop;


import com.allen.george.artificiallife.main.GUI;

import javax.swing.*;



public class DesktopLauncher extends JFrame{
	public static void main (String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
               GUI g = new GUI();
               g.setVisible(true);
            }
        });
	}
}
