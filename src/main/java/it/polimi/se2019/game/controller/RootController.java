package it.polimi.se2019.game.controller;

import it.polimi.se2019.game.controller.lobby.LobbyController;
import it.polimi.se2019.game.model.RootModel;
import it.polimi.se2019.game.controller.match.MatchController;
import it.polimi.se2019.utils.controller.Controller;

public final class RootController extends Controller<RootModel> {
  /**
   * Init the root controller
   */
  public RootController(){
    super(".", RootModel::new);
    this.init(null);

    new LobbyController();
    new MatchController();

    this.notifyModelUpdateAll();
  }
}
