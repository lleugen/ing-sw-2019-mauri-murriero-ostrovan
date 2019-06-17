package it.polimi.se2019.entrypoint.lobby.model;

import it.polimi.se2019.communication.encodable.AbstractEncodable;

import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LobbyStateEncodable extends AbstractEncodable {
  private List<ChatMessageEncodable> chatMsgs;
  private List<String> cps;

  private static final String UUID = "LobbyStateEncodable.v1";

  /**
   * @param chatMsgs    List of chat messages on the lobby
   * @param cps         List of id of other connected players to the controller
   */
  public LobbyStateEncodable(
          List<ChatMessageEncodable> chatMsgs, List<String> cps
  ) {
    super(UUID);

    this.chatMsgs = new LinkedList<>(chatMsgs);
    this.cps = new LinkedList<>(cps);
  }

  public LobbyStateEncodable(){
    super(UUID);

    this.chatMsgs = new LinkedList<>();
    this.cps = new LinkedList<>();
  }

  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeIterable(
            this.chatMsgs.stream()
                    .map(ChatMessageEncodable::encode)
                    .collect(Collectors.toList())
            )
    );

    toReturn.add(encodeIterable(
            this.cps.stream()
                    .map(AbstractEncodable::encodeString)
                    .collect(Collectors.toList())
            )
    );

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.chatMsgs = new LinkedList<>();
      for (ByteBuffer item : decodeIterable(it.next())) {
        ChatMessageEncodable msg = new ChatMessageEncodable();
        msg.decode(item);
        this.chatMsgs.add(msg);
      }

      this.cps = new LinkedList<>();
      for (ByteBuffer item : decodeIterable(it.next())) {
        this.cps.add(decodeString(item));
      }
    }
    catch (NoSuchElementException e){
      throw new InvalidDataException(e);
    }

    if (it.hasNext()){
      throw new InvalidDataException();
    }
  }

  public List<ChatMessageEncodable> getChatMsgs() {
    return new LinkedList<>(this.chatMsgs);
  }

  public void setChatMsgs(List<ChatMessageEncodable> chatMsgs){
    this.chatMsgs = new LinkedList<>(chatMsgs);
  }

  public List<String> getCps() {
    return new LinkedList<>(this.cps);
  }
}

