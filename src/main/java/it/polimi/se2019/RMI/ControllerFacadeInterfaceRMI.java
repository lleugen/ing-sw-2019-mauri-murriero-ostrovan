package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.GUIPlayerView;

import java.rmi.Remote;

public interface ControllerFacadeInterfaceRMI extends Remote {
    void runFacade(GUIPlayerView player);
    void grabFacade(GUIPlayerView player);
    void shootFacade(GUIPlayerView player);
}
