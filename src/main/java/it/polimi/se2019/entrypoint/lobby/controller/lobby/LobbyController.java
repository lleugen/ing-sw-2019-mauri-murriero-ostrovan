package it.polimi.se2019.entrypoint.lobby.controller.lobby;

import it.polimi.se2019.entrypoint.lobby.controller.lobby.chat.ChatLobbyController;
import it.polimi.se2019.entrypoint.lobby.controller.lobby.piazza.PiazzaLobbyController;
import it.polimi.se2019.entrypoint.lobby.model.lobby.LobbyModel;
import it.polimi.se2019.game.controller.match.MatchController;
import it.polimi.se2019.utils.controller.Controller;

public final class LobbyController extends Controller<LobbyModel> {
  /**
   * Init the Lobby controller
   *
   * @
   */
  public LobbyController(MatchController matchController){
    super(".lobby", LobbyModel::new);
    this.init(null);

    new ChatLobbyController();
    new PiazzaLobbyController();

    this.notifyModelUpdateAll();
  }
}
