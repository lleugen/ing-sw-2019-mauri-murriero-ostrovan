package it.polimi.se2019.controller;

import it.polimi.se2019.RMI.ControllerFacadeInterfaceRMI;
import it.polimi.se2019.view.player.GUIPlayerView;

import java.util.List;

public class ControllerFacadeImplementation implements ControllerFacadeInterfaceRMI {
    List<PlayerController> playerControllers;
    public ControllerFacadeImplementation(List<PlayerController> c){
        playerControllers = c;
    }
    private PlayerController identifyPlayer(GUIPlayerView client){
        PlayerController playerController = null;
        for(PlayerController p : playerControllers){
            if(p.getClient().equals(client)){
                playerController = p;
            }
        }
        return playerController;
    }

    @Override
    public void runFacade(GUIPlayerView client){
        PlayerController playerController = identifyPlayer(client);
        playerController.getState().runAround();
    }

    @Override
    public void grabFacade(GUIPlayerView client){
        PlayerController playerController = identifyPlayer(client);
        playerController.getState().grabStuff();
    }

    @Override
    public void shootFacade(GUIPlayerView client){
        PlayerController playerController = identifyPlayer(client);
        playerController.getState().shootPeople();
    }
}

