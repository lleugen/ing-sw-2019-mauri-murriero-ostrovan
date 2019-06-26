package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.PlayerViewOnServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerLobbyInterface extends Remote {
    void connect(String client) throws RemoteException;
}
