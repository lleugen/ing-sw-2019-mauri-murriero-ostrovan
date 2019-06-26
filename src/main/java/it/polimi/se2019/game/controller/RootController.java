/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.controller;

import it.polimi.se2019.game.controller.lobby.LobbyController;
import it.polimi.se2019.game.model.RootModel;
import it.polimi.se2019.game.controller.match.MatchController;
import it.polimi.se2019.engine.controller.Controller;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

import java.util.LinkedList;
import java.util.List;

public final class RootController extends Controller<RootModel> {
  /**
   * Contains the list of running matches on the server
   */
  private List<MatchController> matches = new LinkedList<>();

  /**
   * Init the root controller
   */
  public RootController(VirtualView vv){
    super(".", RootModel::new, vv);

    new LobbyController(vv, this::createMatch);
    new MatchController(vv);

    this.notifyModelAllUsers();

    this.replaceViewAllUsers(".lobby");
    this.addViewAllUsers(".lobby.*");
  }

  /**
   * Create a new match.
   * Users' views are automatically changed to the proper view by this function
   *
   * @param users contains the list of players of the match
   * @param settings contains the settings for the game, in the form
   *                 <mapId>-<skulls>
   */
  private void createMatch(List<String> users, String settings){
    this.matches.add(
            new MatchController(
                    this.getVirtualView()
            )
    );

    users.forEach((String u) -> {
      this.replaceViewSingleUser(".match", u);
      this.addViewSingleUser(".match.*", u);
    });
  }
}
