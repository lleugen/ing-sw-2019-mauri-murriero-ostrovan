package it.polimi.se2019.entrypoint.lobby.model.dashboard.chat;

import it.polimi.se2019.utils.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatDashboardModel extends AbstractModel {
  /**
   * Maximum number of chat messages saved in the controller
   *
   * Default: 5
   */
  private static final int MAX_CHAT_MESSAGES = 5;

  /**
   * UUID for this model
   */
  private static final long UUID = 944_171_328L;

  private List<String> messages;


  /**
   * Create a new chat model
   *
   * @param messages Messages to save in the chat
   */
  public ChatDashboardModel(List<String> messages) {
    this.messages = new LinkedList<>(messages);
  }

  public ChatDashboardModel() {
    this.messages = new LinkedList<>();
  }

  public List<String> getMessages() {
    return new LinkedList<>(this.messages);
  }

  /**
   * Add a new chat message to the lobby.
   * If more than MAX_CHAT_MESSAGES are saved in the lobby, only the
   * newest MAX_CHAT_MESSAGES are preserved
   *
   * @param user  Id of the user who posted the message
   * @param msg   Message to add to the list
   */
  public void addMessage(String user, String msg){
    this.messages.add("[" + user + "] " + msg);

    while (this.messages.size() > MAX_CHAT_MESSAGES) {
      this.messages.remove(0);
    }
  }

  @Override
  public ByteBuffer encode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeLong(UUID));
    toReturn.add(encodeIterable(
            this.messages.stream()
                    .map(AbstractModel::encodeString)
                    .map(AbstractModel::bufToArray)
                    .collect(Collectors.toList())
    ));

    return mergeBuffer(toReturn);
  }

  public static ChatDashboardModel decode(ByteBuffer inputData)
          throws InvalidDataException {
    inputData.clear();

    if (UUID != decodeLong(inputData)){
      throw new InvalidDataException();
    }

    List<String> messages = new LinkedList<>();
    for (byte[] item : decodeIterable(inputData)) {
      messages.add(
              decodeString(
                      AbstractModel.arrayToBuf(
                              item
                      )
              )
      );
    }

    return new ChatDashboardModel(messages);
  }
}

