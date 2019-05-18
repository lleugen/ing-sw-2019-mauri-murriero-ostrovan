package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.GUIPlayerView;

import java.rmi.Remote;

public interface ServerLobbyInterface extends Remote {
    void connect(GUIPlayerView client);
}
