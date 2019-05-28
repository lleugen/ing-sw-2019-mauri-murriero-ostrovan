package it.polimi.se2019.view;

import it.polimi.se2019.model.server.ServerLobby;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private PlayerView client;

    public void main(){
        client = new PlayerView();
        client.generateLoginInfo(this);
    }

    public void findLobby(){
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ServerLobby";
            Registry registry = LocateRegistry.getRegistry();
            ServerLobby lobby = (ServerLobby) registry.lookup(name);
            lobby.connect(client, client.getName(), client.getCharacter());
        } catch (Exception e) {
            System.err.println("client exception:");
            e.printStackTrace();
        }
    }
}
