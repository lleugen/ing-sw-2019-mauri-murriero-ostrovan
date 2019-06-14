package it.polimi.se2019.network.encodable;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Encodable {
  private String ns;
  private String f;
  private ByteBuffer data;

  private static final byte[] UUID = new byte[]{
          -112,4,-13,-81,124,-12,6,90,63,-79,-115,69,10,-86,-62,-42
  };

  public FunctionCall(String ns, String f, ByteBuffer data) {
    super(UUID);
    this.setEncodingLogic(this::implementedEncode);
    this.setDecodingLogic(this::implementedDecode);

    this.ns = ns;
    this.f = f;
    this.data = data;
  }

  private List<ByteBuffer> implementedEncode() {
    List<ByteBuffer> toReturn = new ArrayList<>();

    toReturn.add(this.encodeString(this.ns));
    toReturn.add(this.encodeString(this.f));
    toReturn.add(this.encodeRaw(this.data));

    return toReturn;
  }

  public void implementedDecode(List<ByteBuffer> encoded)
          throws InvalidDataException {
    this.ns = this.decodeString(encoded.get(0));
    encoded.remove(0);

    this.f = this.decodeString(encoded.get(0));
    encoded.remove(0);

    this.data = this.decodeRaw(encoded.get(0));
    encoded.remove(0);

    if (!encoded.isEmpty()){
      throw new InvalidDataException();
    }
  }
}
