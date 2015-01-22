package com.allen.george.artificiallife.main.desktop;


import com.allen.george.artificiallife.main.ArtificialLife;
import com.allen.george.artificiallife.main.forms.MainGui;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import javax.swing.*;



public class DesktopLauncher extends JFrame{
	public static void main (String[] arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run () {
               MainGui g = new MainGui();
               g.setVisible(true);
               g.resizeOpenGL();
            }
        });
	}
}
