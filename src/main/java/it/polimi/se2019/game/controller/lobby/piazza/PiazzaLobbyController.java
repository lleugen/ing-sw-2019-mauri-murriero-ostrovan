package it.polimi.se2019.game.controller.lobby.piazza;

import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.game.model.lobby.piazza.PiazzaLobbyModel;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PiazzaLobbyController extends
        Controller<PiazzaLobbyModel> {
  /**
   * Initialize a new PiazzaLobbyController.
   * See description for this method in Controller
   */
  public PiazzaLobbyController(){
    super(".lobby.piazza", PiazzaLobbyModel::new);
    this.init(this.getCbs());

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for a 
   * PiazzaLobbyController.
   *
   * @return All subscription callbacks for a 
   *         PiazzaLobbyController
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

    toReturn.put("joinMatch", this::join);

    return toReturn;
  }


  /**
   * Add the player to the selected match. If the player was registered to 
   * another match, it is deregistered from the old match 
   * 
   * @param user User who made the call 
   * @param room Room to add the player to (<mapId>-<skulls>) 
   */
  private void join(String user, String room){
    PiazzaLobbyModel model = this.getModel();

    List<String> players = model.addPlayer(user, room);
    
    this.notifyModelUpdateAll();

    if (players != null){
      players.forEach((String u) -> this.showViewUser(u, ".lobby"));
    }
  }

}
