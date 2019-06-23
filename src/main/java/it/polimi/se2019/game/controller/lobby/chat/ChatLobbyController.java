package it.polimi.se2019.game.controller.lobby.chat;

import it.polimi.se2019.utils.controller.Controller;
import it.polimi.se2019.game.model.lobby.chat.ChatLobbyModel;
import it.polimi.se2019.utils.network.virtual_view.FunctionCallCallback;

import java.util.HashMap;
import java.util.Map;

public final class ChatLobbyController extends
        Controller<ChatLobbyModel> {
  /**
   * Initialize a new ChatLobbyController.
   * See description for this method in Controller
   */
  public ChatLobbyController(){
    super(".lobby.chat", ChatLobbyModel::new);
    this.init(this.getCbs());

    this.notifyModelUpdateAll();

    this.showViewAll("");
  }

  /**
   * Gets all subscription callbacks for a 
   * ChatLobbyController.
   *
   * @return All subscription callbacks for a 
   *         ChatLobbyController
   */
  private Map<String, FunctionCallCallback> getCbs() {
    Map<String, FunctionCallCallback> toReturn = new HashMap<>();

    toReturn.put("add", this::addChatMessage);

    return toReturn;
  }


  /**
   * Add a new chat message to the lobby. 
   * 
   * @param user User who made the call 
   * @param msg  Message posted by the user 
   */
  private void addChatMessage(String user, String msg){
    ChatLobbyModel model = this.getModel();
    
    model.addMessage(user, msg);
    
    this.notifyModelUpdateAll();
  }

}
