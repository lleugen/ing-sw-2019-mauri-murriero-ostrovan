/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.controller.lobby;

import it.polimi.se2019.game.controller.lobby.chat.ChatLobbyController;
import it.polimi.se2019.game.controller.lobby.piazza.PiazzaLobbyController;
import it.polimi.se2019.game.model.lobby.LobbyModel;
import it.polimi.se2019.engine.controller.Controller;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

import java.util.List;
import java.util.function.BiConsumer;

public final class LobbyController extends
        Controller<LobbyModel> {
  /**
   * Initialize a new LobbyController.
   * See description for this method in Controller
   *
   * @param matchFoundCb Callback to fire when a new match is found.
   *                     The first parameter is the list of players of the match
   *                     The second parameter are the settings for the game, in
   *                     the form <mapId>-<skulls>
   */
  public LobbyController(
          VirtualView vv,
          BiConsumer<List<String>, String> matchFoundCb
  ){
    super(".lobby", LobbyModel::new, vv);
    new ChatLobbyController(vv);
    new PiazzaLobbyController(vv, matchFoundCb);

    this.notifyModelAllUsers();
  }
}
