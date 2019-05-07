package it.polimi.se2019.controller.player_state;

public class NormalStateController extends PlayerStateController {
  public NormalStateController() {
    turnActionLimit = 2;
    availableActions = 2;
  }

  /**
   *
   */
  public void runAround() {
    move(3);
  }

  /**
   *
   */
  public void grabStuff() {
    move(1);
    grab();
  }

  /**
   *
   */
  public void shootPeople() {
  }
}