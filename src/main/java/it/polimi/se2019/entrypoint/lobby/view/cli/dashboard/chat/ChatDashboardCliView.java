package it.polimi.se2019.entrypoint.lobby.view.cli.dashboard.chat;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.chat.ChatDashboardModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.io.CliIO;

public class ChatDashboardCliView extends AbstractView<ChatDashboardModel, CliIO> {
  public ChatDashboardCliView(){
    super(ChatDashboardModel::decode);
  }

  @Override
  protected void render(ChatDashboardModel data, CliIO io) {
    io.addCommand(
            "dashboard.chat",
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
