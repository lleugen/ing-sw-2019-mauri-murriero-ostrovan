package it.polimi.se2019.entrypoint.lobby.controller.dashboard.match;

import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.match.MatchDashboardModel;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MatchDashboardController extends Controller<MatchDashboardModel> {
  /**
   * Init the Dashboard Chat controller
   */
  public MatchDashboardController(){
    super(".dashboard.match", RootModel.getDashboard()::getMatch);
    this.init(this.getCbs());

    this.notifyModelUpdate(null);

    this.showView(null, ".dashboard.match");
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
    this.notifyModelUpdate(null);

    if (players != null){
      players.forEach((String u) -> this.showView(u, ".lobby"));
    }
  }
}
