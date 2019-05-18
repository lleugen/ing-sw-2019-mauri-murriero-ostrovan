package it.polimi.se2019.view;

import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.GUIPlayerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private GUIPlayerView clientView;

    public void main(){
        clientView = new GUIPlayerView();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ServerLobby";
            Registry registry = LocateRegistry.getRegistry();
            ServerLobby lobby = (ServerLobby) registry.lookup(name);
            lobby.connect(clientView);
        } catch (Exception e) {
            System.err.println("client exception:");
            e.printStackTrace();
        }
    }
}
