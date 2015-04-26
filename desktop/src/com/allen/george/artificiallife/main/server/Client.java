package com.allen.george.artificiallife.main.server;

import com.allen.george.artificiallife.main.forms.MainGui;

import javax.swing.*;

/**
 * Created by George on 17/02/2015.
 */
public class Client {

    private String id = "";
    private MainGui application;

    public Client(String id){
        System.out.println("Client: " + id + " has connected");
        this.id = id;
    }

    public void request(String request, String[] arguments){
        if(request.equals("Start")){
            this.start(arguments);
        } else if(request.equals("Stop")){
            this.stop();
        }
    }

    private void start(final String[] arguments){
        System.out.println("Client: " + id + " has started");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                application = new MainGui(arguments);
                application.setVisible(true);
                application.setExtendedState(application.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                application.resizeOpenGL();
                application.getArtificialLife().setRunning(true);
                application.setVisible(false);
            }
        });
    }

    private void stop(){
        System.out.println("Client: " + id + " has stopped. Disconnecting");
        application.getArtificialLife().setRunning(false);
        application.dispose();
        application = null;
    }

    public String getID(){
        return this.id;
    }

}
