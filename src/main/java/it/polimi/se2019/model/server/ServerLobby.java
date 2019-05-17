package it.polimi.se2019.model.server;

import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

import java.rmi.Remote;
import java.util.List;

public class ServerLobby implements Remote {
    public ServerLobby(Server s){
        server = s;
    }
    List<PlayerView> clients;
    Server server;
    public void connect(PlayerView client){
        //do magic

        clients.add(client);
        server.createNewPlayer(client);
    }
}
