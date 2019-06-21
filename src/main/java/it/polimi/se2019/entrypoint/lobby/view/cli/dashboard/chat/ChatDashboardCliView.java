package it.polimi.se2019.entrypoint.lobby.view.cli.dashboard.chat;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.chat.ChatDashboardModel;
import it.polimi.se2019.utils.view.AbstractView;
import it.polimi.se2019.utils.view.CliIO;

public class ChatDashboardCliView extends AbstractView<ChatDashboardModel> {
  @Override
  protected void render(ChatDashboardModel data) {
    CliIO.update("Got messages:");
    data.getMessages().forEach(
            CliIO::update
    );
    CliIO.update("> You can send a message by typing \"dashboard.chat send <message>\"");
    CliIO.publish();
  }

  public ChatDashboardCliView(){
    super(ChatDashboardModel::decode);
  }
}
