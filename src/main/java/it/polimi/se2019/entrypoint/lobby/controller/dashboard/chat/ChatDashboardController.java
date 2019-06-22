package it.polimi.se2019.entrypoint.lobby.controller.dashboard.chat;

import it.polimi.se2019.entrypoint.lobby.model.RootModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.chat.ChatDashboardModel;
import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public final class ChatDashboardController extends Controller<ChatDashboardModel> {
  /**
   * Init the Dashboard Chat controller
   */
  public ChatDashboardController(){
    super(".dashboard.chat", RootModel.getDashboard()::getChat);
    this.init(this.getCbs());

    this.notifyModelUpdate(null);

    this.showView(null, ".dashboard.chat");
  }

  /**
   * Gets all subscription callbacks for this controller
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

    toReturn.put("send", this::addChatMessage);

    return toReturn;
  }

  /**
   * Add a new chat message to the lobby.
   *
   * @param user User who posted the message
   * @param msg  Msg posted by the user
   */
  private void addChatMessage(String user, String msg){
    this.getModel().addMessage(user, msg);
    this.notifyModelUpdate(null);
  }
}
