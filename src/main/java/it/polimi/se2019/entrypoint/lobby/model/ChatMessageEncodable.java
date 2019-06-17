package it.polimi.se2019.entrypoint.lobby.model;

import it.polimi.se2019.communication.encodable.AbstractEncodable;

import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChatMessageEncodable extends AbstractEncodable {
  private String user;
  private String message;
  private long date;

  private static final String UUID = "ChatMessageEncodable.v1";

  /**
   * Create a new chat message
   *
   * @param user    Id of the user that posted the message
   * @param message Message posted
   * @param date    Timestamp (in ms) the message was posted
   */
  public ChatMessageEncodable(String user, String message, long date) {
    super(UUID);

    this.user = user;
    this.message = message;
    this.date = date;
  }

  public ChatMessageEncodable(){
    super(UUID);

    this.user = "";
    this.message = "";
    this.date = 0;
  }

  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.user));
    toReturn.add(encodeString(this.message));
    toReturn.add(encodeLong(this.date));

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.user = decodeString(it.next());
      this.message = decodeString(it.next());
      this.date = decodeLong(it.next());
    }
    catch (NoSuchElementException e){
      throw new InvalidDataException(e);
    }

    if (it.hasNext()){
      throw new InvalidDataException();
    }
  }

  public String getUser() {
    return user;
  }

  public String getMessage() {
    return this.message;
  }

//  public void setMessage(String message){
//    this.message = message;
//  }

  public long getDate() {
    return this.date;
  }

//  public void setDate(long date){
//    this.date = date;
//  }
}

