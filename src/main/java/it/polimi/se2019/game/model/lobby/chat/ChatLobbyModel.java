/*
 * Adrenalina: Milano Politecnico - 2019
 * author: Eugenio Ostrovan, Fabio Mauri, Riccardo Murriero
 * (c) All Rights Reserved
 */

package it.polimi.se2019.game.model.lobby.chat;

import it.polimi.se2019.engine.model.AbstractModel;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representation of a lobby chat 
 */
public class ChatLobbyModel 
        extends AbstractModel {
  /**
   * Maximum number of chat messages saved in the controller
   *
   * Default: 5
   */
  private static final int MAX_CHAT_MESSAGES = 5;

  /**
   * UUID for this model.
   * When decoding, this UUID will be checked agains decoded UUID.
   * If the UUID doesn't match, decoding will be rejected
   */
  private static final long UUID = 210_189_348_321_347_281L;

  /**
   * Contains all messages of the chat.
   * It contains up to MAX_CHAT_MESSAGES.
   * Old messages are automatically deleted.
   */
  private List<String> messages;

  /**
   * Create a new ChatLobbyModel
   * 
   * @param messages Messages to save in the chat
   */
  public ChatLobbyModel(List<String> messages) {
    this.messages = new LinkedList<>(messages);
  }

  /**
   * Create a new empty ChatLobbyModel
   */
  public ChatLobbyModel() {
    this.messages = new LinkedList<>();
  }

  /**
   * @return Messages of the chat
   */
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

  /**
   * Encode data.
   * See description for this method in AbstractModel
   */
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

  /**
   * Decodes encoded data
   * See description for this method in AbstractModel
   * 
   * @param inputData Input Data to decode
   * 
   * @return A new istance of ChatLobbyModel, initialized using inputData
   * 
   * @throws InvalidDataException if an error occurs while decoding data, 
   *                              or if encoded UUID doesn't match 
   *                              ChatLobbyModel's UUID
   */
  public static ChatLobbyModel decode(ByteBuffer inputData)
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

    return new ChatLobbyModel(messages);
  }
}
