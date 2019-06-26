/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.controller.lobby.piazza;

import it.polimi.se2019.game.model.lobby.piazza.PiazzaLobbyModel;
import it.polimi.se2019.engine.controller.Controller;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

import java.util.List;
import java.util.function.BiConsumer;

public final class PiazzaLobbyController extends
        Controller<PiazzaLobbyModel> {
  /**
   * Callback to fire when a new match is found.
   *
   * The first parameter is the list of players of the match
   * The second parameter are the settings for the game, in the form <mapId>-<skulls>
   */
  private BiConsumer<List<String>, String> matchFoundCb;

  /**
   * Initialize a new PiazzaLobbyController.
   * See description for this method in Controller
   *
   * @param matchFoundCb Callback to fire when a new match is found.
   *                     The first parameter is the list of players of the match
   *                     The second parameter are the settings for the game, in
   *                     the form <mapId>-<skulls>
   */
  public PiazzaLobbyController(
          VirtualView vv,
          BiConsumer<List<String>, String> matchFoundCb
  ){
    super(".lobby.piazza", PiazzaLobbyModel::new, vv);

    this.matchFoundCb = matchFoundCb;

    this.subscribeCall("join", this::joinMatch);

    this.notifyModelAllUsers();
  }

  /**
   * Add the player to the selected match. If the player was registered to 
   * another match, it is deregistered from the old match 
   * 
   * @param user User who made the call 
   * @param room Room to add the player to (<mapId>-<skulls>) 
   */
  private void joinMatch(String user, String room){
    PiazzaLobbyModel model = this.getModel();

    List<String> players = model.addPlayer(user, room);
    
    this.notifyModelAllUsers();

    if (players != null){
      this.matchFoundCb.accept(players, room);
    }
  }

}
