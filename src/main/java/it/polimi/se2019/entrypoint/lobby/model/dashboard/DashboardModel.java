package it.polimi.se2019.entrypoint.lobby.model.dashboard;

import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.chat.ChatDashboardModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class DashboardModel extends AbstractModel {
  private ChatDashboardModel chat = new ChatDashboardModel();

  @Override
  public ByteBuffer encode() {
    return mergeBuffer(new LinkedList<>());
  }

  public static DashboardModel decode(ByteBuffer encoded) {
    return new DashboardModel();
  }

  public ChatDashboardModel getChat() {
    return this.chat;
  }
}
