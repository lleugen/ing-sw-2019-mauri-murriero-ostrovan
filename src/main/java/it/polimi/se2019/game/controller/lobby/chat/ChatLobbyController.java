/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.controller.lobby.chat;

import it.polimi.se2019.engine.controller.Controller;
/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

import it.polimi.se2019.game.model.lobby.chat.ChatLobbyModel;
import it.polimi.se2019.engine.network.virtualview.VirtualView;

public final class ChatLobbyController extends
        Controller<ChatLobbyModel> {
  /**
   * Initialize a new ChatLobbyController.
   * See description for this method in Controller
   */
  public ChatLobbyController(VirtualView vv){
    super(".lobby.chat", ChatLobbyModel::new, vv);

    this.subscribeCall("send", this::addChatMessage);

    this.notifyModelAllUsers();
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
    
    this.notifyModelAllUsers();
  }

}
