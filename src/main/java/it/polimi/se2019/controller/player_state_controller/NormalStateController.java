package it.polimi.se2019.controller.player_state_controller;

public class NormalStateController extends PlayerStateController {
  public NormalStateController() {
    turnActionLimit = 2;
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
    move(1);
    grab();
  }

  /**
   *
   */
  public void shootPeople() {
  }
}