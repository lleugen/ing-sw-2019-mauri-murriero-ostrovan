package it.polimi.se2019.controller.player_state_controller;

import it.polimi.se2019.controller.GameBoardController;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.view.player.PlayerView;

public class SecondFreneticStateController extends PlayerStateController {
  public SecondFreneticStateController(GameBoardController g, Player p, PlayerView c) {
    super(g, p, c);
    availableActions = 1;
  }

  @Override
  public String toString(){
    return "SecondFreneticState";
  }

  /**
   *
   */
  @Override
  public void grabStuff() {
  }

  /**
   *
   */
  @Override
  public void runAround(){

  }

  /**
   *
   */
  @Override
  public void shootPeople() {
  }
}