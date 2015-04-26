package com.allen.george.artificiallife.main.forms;

import com.allen.george.artificiallife.data.networking.WebServiceAPI;
import com.allen.george.artificiallife.utils.SimulationSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by George on 10/02/2015.
 */
public class LoginForm extends JFrame implements ActionListener {

    private JPanel mainPane;
    private JPanel logingPane = new JPanel();
    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField usernameText = new JTextField();
    private  JLabel passwordLabel = new JLabel("Password: ");
    private JTextField passwordText = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton cancelButton = new JButton("Cancel");
    private Properties properties;
    private MainGui parent;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            if(usernameText.getText().equals("") || passwordText.getText().equals("")){
                JOptionPane.showConfirmDialog(null, "Please enter a username and password", "Input Error", JOptionPane.CANCEL_OPTION);
            } else {
                //we want to login
                final String username = usernameText.getText();
                String password = passwordText.getText();

                OutputStream output = null;

                try{
                    if(WebServiceAPI.doesUserExist(username, password)){
                        //Set the properites for the user
                        output = new FileOutputStream("user.properties");
                        this.properties.setProperty("username", username);
                        this.properties.setProperty("password", password);
                        this.properties.store(output, null);
                        SimulationSettings.USERNAME = username;
                        //Show the main gui
                        this.setVisible(false);
                        if(parent == null){
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run () {
                                    MainGui g = new MainGui(new String[] { username });
                                    g.setVisible(true);
                                    g.setExtendedState(g.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                                    g.resizeOpenGL();
                                }
                            });
                        } else {
                           parent.reset();


                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "User does not exist!", "Input Error", JOptionPane.CANCEL_OPTION);
                    }
                } catch (Exception ex){
                    JOptionPane.showConfirmDialog(null, "User does not exist!", "Input Error", JOptionPane.CANCEL_OPTION);
                } finally {
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException ex) {
                            JOptionPane.showConfirmDialog(null, "Could not save properties", "File Save Error", JOptionPane.CANCEL_OPTION);
                        }
                    }

                }
            }
        } else if(e.getActionCommand().equals("Cancel")){
            if(parent == null){
                System.exit(1);
            } else {
                this.setVisible(false);
            }
        }
    }

    public LoginForm(Properties properties, MainGui parent){
        this.properties = properties;
        this.parent = parent;
        if(parent == null){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("login");
        mainPane = new JPanel();
        setContentPane(mainPane);
        mainPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 10;
        gbc.gridheight = 4;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.weightx = 1;
        mainPane.add(logingPane, gbc);
        logingPane.setLayout(new GridLayout(3, 6));


        logingPane.add(usernameLabel);
        logingPane.add(usernameText);
        logingPane.add(passwordLabel);
        logingPane.add(passwordText);
        cancelButton.addActionListener(this);
        logingPane.add(cancelButton);
        loginButton.addActionListener(this);
        logingPane.add(loginButton);

        pack();


    }

}
