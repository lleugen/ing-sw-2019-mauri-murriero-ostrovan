package it.polimi.se2019.game.controller.match;

import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.game.model.match.MatchModel;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public final class MatchController extends
        Controller<MatchModel> {
  /**
   * Initialize a new MatchController.
   * See description for this method in Controller
   */
  public MatchController(){
    super(".match", MatchModel::new);
    this.init(this.getCbs());

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for a 
   * MatchController.
   *
   * @return All subscription callbacks for a 
   *         MatchController
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

    return toReturn;
  }

}
