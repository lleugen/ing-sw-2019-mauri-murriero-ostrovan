package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

public class FirstFreneticStateController extends PlayerStateController {
  public FirstFreneticStateController(GameBoardController g, Player p, PlayerView c) {
    super(g,p,c);
    availableActions = 2;
  }

  @Override
  public String toString(){
    return "FirstFreneticState";
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