package it.polimi.se2019.model.server;

import it.polimi.se2019.RMI.ViewFacadeInterfaceRMI;
import it.polimi.se2019.view.player.GUIPlayerView;

import java.rmi.Remote;
import java.util.List;

public class ServerLobby implements Remote {
    public ServerLobby(Server s){
        server = s;
    }
    List<GUIPlayerView> clients;
    Server server;
    public void connect(GUIPlayerView client){
        //do magic

        clients.add(client);
        server.createNewPlayer(client);
    }
}
