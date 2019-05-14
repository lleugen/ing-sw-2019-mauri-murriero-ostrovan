package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.PlayerView;

import java.rmi.Remote;

public interface ServerLobbyInterface extends Remote {
    void connect(PlayerView client);
}
