package it.polimi.se2019.entrypoint.lobby.view.cli.lobby.chat;

import it.polimi.se2019.entrypoint.lobby.model.lobby.chat.ChatLobbyModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.io.CliIO;

public class ChatLobbyCliView extends AbstractView<ChatLobbyModel, CliIO> {
  public ChatLobbyCliView(){
    super(ChatLobbyModel::decode);
  }

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
