/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.controller.match;

import it.polimi.se2019.game.model.match.MatchModel;
import it.polimi.se2019.engine.controller.Controller;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

public final class MatchController extends
        Controller<MatchModel> {
  /**
   * Initialize a new MatchController.
   * See description for this method in Controller
   */
  public MatchController(VirtualView vv){
    super(".match", MatchModel::new, vv);

    this.notifyModelAllUsers();
  }
}
