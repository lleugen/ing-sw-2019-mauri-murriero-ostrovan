package it.polimi.se2019.communication.encodable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class LoginData extends Encodable {
  private String user;

  private static final byte[] UUID = new byte[]{
          -40,-61,-123,-116,83,35,-11,127,123,-66,-126,72,89,-42,-45,-69
  };

  public LoginData(String user) {
    super(UUID);
    this.setEncodingLogic(this::implementedEncode);
    this.setDecodingLogic(this::implementedDecode);

    this.user = user;
  }

  public String getUser() {
    return this.user;
  }

  private List<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new ArrayList<>();

    toReturn.add(this.encodeString(this.user));

    return toReturn;
  }

  private void implementedDecode(List<ByteBuffer> encoded)
          throws InvalidDataException {
    this.user = this.decodeString(encoded.get(0));
    encoded.remove(0);

    if (!encoded.isEmpty()){
      throw new InvalidDataException();
    }
  }
}
