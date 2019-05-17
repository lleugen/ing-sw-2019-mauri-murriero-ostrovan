package it.polimi.se2019.view;

import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(){
        PlayerView client = new PlayerView();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ServerLobby";
            Registry registry = LocateRegistry.getRegistry();
            ServerLobby lobby = (ServerLobby) registry.lookup(name);
            lobby.connect(client);
        } catch (Exception e) {
            System.err.println("client exception:");
            e.printStackTrace();
        }
    }
}
