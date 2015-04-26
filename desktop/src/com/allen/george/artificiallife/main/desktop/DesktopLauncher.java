package com.allen.george.artificiallife.main.desktop;


import com.allen.george.artificiallife.main.forms.LoginForm;
import com.allen.george.artificiallife.main.forms.MainGui;
import com.allen.george.artificiallife.utils.SimulationSettings;
import com.badlogic.gdx.Gdx;
import org.lwjgl.Sys;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DesktopLauncher {


	public static void main (String[] arg) {

        final Properties properties = new Properties();
        InputStream inputStream =  null;

        try{

            inputStream = new FileInputStream("user.properties");

            properties.load(inputStream);

            if(properties.getProperty("username").equals("") || properties.getProperty("password").equals("")){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run () {
                        LoginForm g = new LoginForm(properties, null);
                        g.setVisible(true);
                    }
                });


            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run () {
                        SimulationSettings.USERNAME = properties.getProperty("username");
                        MainGui g = new MainGui(new String[] {"Username=" + SimulationSettings.USERNAME });
                        g.setVisible(true);
                        g.setExtendedState(g.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                        g.resizeOpenGL();
                    }
                });
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
