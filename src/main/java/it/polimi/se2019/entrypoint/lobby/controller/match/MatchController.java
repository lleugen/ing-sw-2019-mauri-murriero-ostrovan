package it.polimi.se2019.entrypoint.lobby.controller.match;

import it.polimi.se2019.entrypoint.lobby.model.match.MatchModel;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public final class MatchController extends Controller<MatchModel> {
  /**
   * Init a new Match Controller
   */
  public MatchController(){
    super(".match", MatchModel::new);
    this.init(this.getCbs());

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for this controller
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

//    toReturn.put("send", this::addChatMessage);

    return toReturn;
  }

//  /**
//   * Add a new chat message to the lobby.
//   *
//   * @param user User who posted the message
//   * @param msg  Msg posted by the user
//   */
//  private void addChatMessage(String user, String msg){
//    this.getModel().addMessage(user, msg);
//    this.notifyModelUpdateAll();
//  }
}
