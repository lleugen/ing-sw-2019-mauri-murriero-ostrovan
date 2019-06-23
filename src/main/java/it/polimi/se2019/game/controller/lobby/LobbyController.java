package it.polimi.se2019.game.controller.lobby;

import it.polimi.se2019.entrypoint.lobby.controller.lobby.chat.ChatLobbyController;
import it.polimi.se2019.entrypoint.lobby.controller.lobby.piazza.PiazzaLobbyController;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.game.model.lobby.LobbyModel;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public final class LobbyController extends
        Controller<LobbyModel> {
  /**
   * Initialize a new LobbyController.
   * See description for this method in Controller
   */
  public LobbyController(){
    super(".lobby", LobbyModel::new);
    this.init(this.getCbs());

    new ChatLobbyController();
    new PiazzaLobbyController();

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for a 
   * LobbyController.
   *
   * @return All subscription callbacks for a 
   *         LobbyController
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();



    return toReturn;
  }


}
