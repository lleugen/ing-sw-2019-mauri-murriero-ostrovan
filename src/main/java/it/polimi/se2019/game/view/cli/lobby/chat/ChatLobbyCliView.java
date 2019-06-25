/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.view.cli.lobby.chat;

import it.polimi.se2019.engine.io.CliIO;
import it.polimi.se2019.engine.view.AbstractView;
import it.polimi.se2019.game.model.lobby.chat.ChatLobbyModel;

public class ChatLobbyCliView 
        extends AbstractView<ChatLobbyModel, CliIO> {
  /**
   * Initialize a new ChatLobbyCliView.
   * See description for this method in AbstractView
   */ 
  public ChatLobbyCliView(){
    super(ChatLobbyModel::decode);
  }

  /**
   * Render the view.
   * See description for this method in AbstractView
   *
   * @param data Model data to render
   * @param io   IOInterface to use for rendering data
   */
  @Override
  protected void render(ChatLobbyModel data, CliIO io) {
    io.addCommand(
            "lobby.chat",
            "send",
            "<message>",
            "Send a message to the chat"
    );

    io.markSection("CHAT");

    if (data.getMessages().isEmpty()){
      io.writeLn("No Messages in chat... :(");
    }
    else {
      data.getMessages().forEach(
              io::writeLn
      );
    }
  }
}
