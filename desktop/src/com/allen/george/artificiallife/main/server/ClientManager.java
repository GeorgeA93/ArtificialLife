package com.allen.george.artificiallife.main.server;

import java.util.ArrayList;

/**
 * Created by George on 20/02/2015.
 */
public class ClientManager {

    public static ArrayList<Client> clients = new ArrayList<Client>();

    public static Client getClientForUsername(String username){
        for(Client client : clients){
            if(client.getID().equals(username)){
                return client;
            }
        }

        return null;
    }

    public static boolean clientIsntRunning(String username){
        for(Client client : clients){
            if(client.getID().equals(username)){
                return false;
            }
        }
        return true;
    }

}
