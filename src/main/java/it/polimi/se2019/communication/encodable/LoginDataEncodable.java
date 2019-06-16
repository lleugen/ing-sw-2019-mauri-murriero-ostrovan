package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LoginDataEncodable extends AbstractEncodable {
  private String user;

  private static final byte[] UUID = new byte[]{
          -40,-61,-123,-116,83,35,-11,127,123,-66,-126,72,89,-42,-45,-69
  };

  public LoginDataEncodable(String user) {
    super(UUID);

    this.user = user;
  }

  public LoginDataEncodable(){
    super(UUID);

    this.user = "";
  }

  protected Iterable<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new LinkedList<>();

    toReturn.add(encodeString(this.user));

    return toReturn;
  }

  protected void implementedDecode(Iterable<ByteBuffer> encoded)
          throws InvalidDataException {
    Iterator<ByteBuffer> it = encoded.iterator();

    try {
      this.user = decodeString(it.next());
    }
    catch (NoSuchElementException e){
      throw new InvalidDataException(e);
    }

    if (it.hasNext()){
      throw new InvalidDataException();
    }
  }

  public String getUser() {
    return this.user;
  }
}
