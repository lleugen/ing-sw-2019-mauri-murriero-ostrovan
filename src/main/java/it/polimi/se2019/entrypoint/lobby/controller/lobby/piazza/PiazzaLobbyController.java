package it.polimi.se2019.entrypoint.lobby.controller.lobby.piazza;

import it.polimi.se2019.entrypoint.lobby.model.lobby.piazza.PiazzaLobbyModel;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PiazzaLobbyController extends Controller<PiazzaLobbyModel> {
  /**
   * Init the Dashboard Chat controller
   */
  public PiazzaLobbyController(){
    super(".lobby.piazza", PiazzaLobbyModel::new);
    this.init(this.getCbs());

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for this controller
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

    toReturn.put("join", this::joinMatch);

    return toReturn;
  }

  /**
   * Add the player to the selected match
   *
   * @param user User to add to the match
   * @param room Room to add the player to
   */
  private void joinMatch(String user, String room){
    List<String> players = this.getModel().addPlayer(user, room);
    this.notifyModelUpdateAll();

    if (players != null){
      players.forEach((String u) -> this.showViewUser(u, ".lobby"));
    }
  }
}
