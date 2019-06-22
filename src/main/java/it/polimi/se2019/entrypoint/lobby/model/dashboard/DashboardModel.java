package it.polimi.se2019.entrypoint.lobby.model.dashboard;

import it.polimi.se2019.entrypoint.lobby.model.dashboard.match.MatchDashboardModel;
import it.polimi.se2019.utils.model.AbstractModel;
import it.polimi.se2019.entrypoint.lobby.model.dashboard.chat.ChatDashboardModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class DashboardModel extends AbstractModel {
  /**
   * UUID for this model
   */
  private static final long UUID = 328_600_283L;
  private ChatDashboardModel chat = new ChatDashboardModel();
  private MatchDashboardModel match = new MatchDashboardModel();

  public MatchDashboardModel getMatch() {
    return match;
  }


  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));

    return mergeBuffer(toReturn);
  }

  public static DashboardModel decode(ByteBuffer inputData)
          throws InvalidDataException {

    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    return new DashboardModel();
  }

  public ChatDashboardModel getChat() {
    return this.chat;
  }
}
