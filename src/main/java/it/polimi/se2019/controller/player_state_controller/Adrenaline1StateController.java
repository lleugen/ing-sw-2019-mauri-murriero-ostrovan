package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

public class Adrenaline1StateController extends PlayerStateController {
  public Adrenaline1StateController(GameBoardController g, Player p, PlayerView c) {
    super(g, p, c);
    availableActions = 2;
  }

  @Override
  public String toString(){
    return "Adrenaline1State";
  }

  /**
   *
   */
  public void runAround() {

  }

  /**
   *
   */
  public void grabStuff() {
  }

  /**
   *
   */
  public void shootPeople() {
  }
}