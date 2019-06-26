package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.Remote;

public interface ServerLobbyInterface extends Remote {
    void connect(PlayerViewOnServer client);
}
