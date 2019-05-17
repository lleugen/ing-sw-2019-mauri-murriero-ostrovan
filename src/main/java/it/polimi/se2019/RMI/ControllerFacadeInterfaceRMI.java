package it.polimi.se2019.RMI;

import it.polimi.se2019.view.player.PlayerView;

import java.rmi.Remote;

public interface ControllerFacadeInterfaceRMI extends Remote {
    void runFacade(PlayerView player);
    void grabFacade(PlayerView player);
    void shootFacade(PlayerView player);
}
